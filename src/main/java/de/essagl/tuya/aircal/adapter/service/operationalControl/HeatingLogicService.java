package de.essagl.tuya.aircal.adapter.service.operationalControl;


import de.essagl.tuya.aircal.adapter.ability.model.DoubleLabelValueUnit;
import de.essagl.tuya.aircal.adapter.ability.model.StringLabelValueUnit;
import de.essagl.tuya.aircal.adapter.service.DeviceService;
import de.essagl.tuya.aircal.adapter.service.HeatPumpService;
import de.essagl.tuya.aircal.adapter.service.IndoorThermometerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;



@Service
@EnableScheduling
@Slf4j
public class HeatingLogicService {

    private boolean checkIsRunning = false;
    private final DeviceService deviceService;
    private final IndoorThermometerService thermometerService;

    private final HeatPumpService heatPumpService;



    /**
     * the value in °C for the heating flow temperature when the heat pump
     * should produce hot water for the radiators
     */
    private Double heatingFlowTemperature;
    /**
     * the value in °C for the heating flow temperature when the indoor set temperature
     * is reached and the heat pump should go in standby by reducing the
     * hot water temperature for the radiators
     */
    private Double standbyFlowTemperature;

    public enum Mode {ON,OFF}

    /**
     * Switch ON or OFF
     */
    private Mode runningMode;
    /**
     * can be overwritten by controller
     */
    private Double indoorSetTemperature;


    private Double actualHeatingWaterFlowTemperature;

    public HeatingLogicService(DeviceService deviceService,
                               HeatPumpService heatPumpService,
                               IndoorThermometerService thermometerService,
                               @Value("${indoorDefaultSetTemperature}")  Double indoorSetTemperature,
                               @Value("${heatingLogicMode}")  Mode runningMode,
                               @Value("${heatingFlowTemperature}")Double heatingFlowTemperature,
                               @Value("${standbyFlowTemperature}")Double standbyFlowTemperature) throws IOException {
        this.deviceService = deviceService;
        this.thermometerService = thermometerService;
        this.indoorSetTemperature = indoorSetTemperature;
        this.runningMode = runningMode;
        this.heatingFlowTemperature = heatingFlowTemperature;
        this.standbyFlowTemperature = standbyFlowTemperature;
        this.heatPumpService = heatPumpService;


        log.info("HeatingLogicService is {}. Panel version is {}",runningMode, heatPumpService.getControlPanelVersion());


    }

    public void setIndoorSetTemperature(Double indoorSetTemperature){
        if (indoorSetTemperature < 10 || indoorSetTemperature > 28){
            log.error("indoor set temperature must be between 10.0 and 28.0 °C");
            return;
        }
        this.indoorSetTemperature = indoorSetTemperature;
    }


    public DoubleLabelValueUnit getIndoorSetTemperatureValue() {
        DoubleLabelValueUnit dlvu = new DoubleLabelValueUnit();
        dlvu.setKey("indoor_temp_set");
        dlvu.setValue(indoorSetTemperature);
        dlvu.setUnit("℃");
        return dlvu;
    }


    public void setHeatingFlowTemperature(Double heatingFlowTemperature){
        if (heatingFlowTemperature < 10 || heatingFlowTemperature > 70) {
            log.error("maximum heating flow temperature must be between 10 and 70 °C");
            return;
        }
        this.heatingFlowTemperature = heatingFlowTemperature;
    }


    public DoubleLabelValueUnit getHeatingFlowTemperatureValue() {
        DoubleLabelValueUnit dlvu = new DoubleLabelValueUnit();
        dlvu.setKey("heatingFlowTemperature");
        dlvu.setValue(heatingFlowTemperature);
        dlvu.setUnit("℃");
        return dlvu;
    }

    public void setStandbyFlowTemperature(Double standbyFlowTemperature){
        if (standbyFlowTemperature < 10 || standbyFlowTemperature > heatingFlowTemperature) {
            log.error("standby flow temperature must be between 10 and {} °C",heatingFlowTemperature);
            return;
        }
        this.standbyFlowTemperature = standbyFlowTemperature;
    }


    public DoubleLabelValueUnit getStandbyFlowTemperatureValue() {
        DoubleLabelValueUnit dlvu = new DoubleLabelValueUnit();
        dlvu.setKey("standbyFlowTemperature");
        dlvu.setValue(standbyFlowTemperature);
        dlvu.setUnit("℃");
        return dlvu;
    }


    public StringLabelValueUnit getRunningModeValue() {
        StringLabelValueUnit dlvu = new StringLabelValueUnit();
        dlvu.setKey("mode");
        dlvu.setValue(runningMode.name());
        dlvu.setUnit("");
        return dlvu;
    }
    public void setRunningMode(String runningMode) {
        this.runningMode = Mode.valueOf(runningMode.toUpperCase());
    }

    /**
     * The logic runs every minute.
     */
    @Scheduled(fixedRateString = "${heatingLogicRunningRate}")
    @Async
    public void computeAndSetTemperature() {
        if (runningMode == Mode.OFF){
            return;
        }
         if (!heatPumpService.getSwitch().getValue()){
            log.info("Device is OFF. Heating logic will not be applied.");
            return;
        }
        try{
            if (checkIsRunning){
                log.info("Check routine already running. Skip this check");
                return;
            }
            checkIsRunning = true;

            adjustHeatingWaterFlowTemperature();

            checkIsRunning = false;
        } catch (Exception e){
            checkIsRunning = false;
            log.error("Exception {} occurred during computeAndSetTemperature processing!",e.getMessage());
        }
    }


    private void adjustHeatingWaterFlowTemperature(){
        // read the mode of the heat pump.
        String mode = heatPumpService.getMode().getValue();
        if (this.actualHeatingWaterFlowTemperature == null){
            this.actualHeatingWaterFlowTemperature = heatPumpService.getHeatingWaterFlowTemp().getValue();
        }
        double indoorTemp = thermometerService.getTemperature().getValue();

        if (indoorTemp < indoorSetTemperature - 1d && !actualHeatingWaterFlowTemperature.equals(heatingFlowTemperature)) {
            log.info("indoorTemperatur {} is more than 1℃ lower that the indoorTargetTemperatur {} -> increase temp_set to {}℃.",indoorTemp,indoorSetTemperature,heatingFlowTemperature.intValue());
            setHeatingWaterFlowTemp(heatingFlowTemperature.intValue(),mode);
        } else if (indoorTemp >= indoorSetTemperature && !actualHeatingWaterFlowTemperature.equals(standbyFlowTemperature)) {
            log.info("indoorTemperatur {} is equal or greater than indoorTargetTemperatur {} -> decrease temp_set to {}℃ ",indoorTemp,indoorSetTemperature,standbyFlowTemperature.intValue());
            setHeatingWaterFlowTemp(standbyFlowTemperature.intValue(),mode);
        } else {
            log.info("device mode {}, indoorTemperature {}℃, indoorTargetTemperatur {}℃, heatingFlowTemperature {}℃, standbyFlowTemperature {}℃, actualHeatingWaterFlowTemperature {}℃ \n " +
                            "No adjustment of waterHeatFlowTemperature needed.",
                    mode,indoorTemp,indoorSetTemperature,heatingFlowTemperature,standbyFlowTemperature,actualHeatingWaterFlowTemperature);
        }
    }

    // set flow temperature in version 2.01 by using workaround for bug that the temp could not be set
    private void setHeatingWaterFlowTemp(int value, String original_mode) {
        if (heatPumpService.getControlPanelVersion().equals("201")) {
            if (original_mode.equals(heatPumpService.getWorkingModeValue("workingModeHeating"))){
                heatPumpService.setMode("workingModeHotWaterAndHeating");
            } else {
                heatPumpService.setMode(heatPumpService.getWorkingModeValue("workingModeHeating"));
            }
        }

        heatPumpService.setHeatingWaterFlowTemp(value);
        if (heatPumpService.getControlPanelVersion().equals("201")) {
            heatPumpService.setMode(heatPumpService.getWorkingModeKey(original_mode));
        }

        this.actualHeatingWaterFlowTemperature = heatPumpService.getHeatingWaterFlowTemp().getValue();
    }


}

