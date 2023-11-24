package de.essagl.tuya.aircal.adapter.service.operationalControl;


import de.essagl.tuya.aircal.adapter.ability.model.DoubleLabelValueUnit;
import de.essagl.tuya.aircal.adapter.ability.model.StringLabelValueUnit;
import de.essagl.tuya.aircal.adapter.service.HeatPumpService;
import de.essagl.tuya.aircal.adapter.service.IndoorThermometerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;


@Service
@EnableScheduling
@Slf4j
public class HeatingLogicService {

    private boolean checkIsRunning = false;

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

    private String nightModeStart;
    private String nightModeEnd;
    private String nightMode;


    private boolean forceHotWaterHeatingIsRunning = false;
    private Double serviceWaterFlowTemperatureBeforeForceHotWaterHeating = null;

    public HeatingLogicService(HeatPumpService heatPumpService,
                               IndoorThermometerService thermometerService,
                               @Value("${indoorDefaultSetTemperature}")  Double indoorSetTemperature,
                               @Value("${heatingLogicMode}")  Mode runningMode,
                               @Value("${heatingFlowTemperature}")Double heatingFlowTemperature,
                               @Value("${standbyFlowTemperature}")Double standbyFlowTemperature,
                               @Value("${nightModeStartHour}")String nightModeStart,
                               @Value("${nightModeEndHour}")String nightModeEnd,
                               @Value("${nightMode}")String nightMode) throws ParseException {
        this.thermometerService = thermometerService;
        this.indoorSetTemperature = indoorSetTemperature;
        this.runningMode = runningMode;
        this.heatingFlowTemperature = heatingFlowTemperature;
        this.standbyFlowTemperature = standbyFlowTemperature;
        this.heatPumpService = heatPumpService;

        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        dateFormat.parse(nightModeStart);
        dateFormat.parse(nightModeEnd);
        this.nightModeStart = nightModeStart;
        this.nightModeEnd = nightModeEnd;

        this.nightMode = nightMode;






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
        if (heatingFlowTemperature < 10 || heatingFlowTemperature > 55) {
            log.error("heating flow temperature must be between 10 and 55 °C");
            return;
        }
        this.actualHeatingWaterFlowTemperature = null;
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
        this.actualHeatingWaterFlowTemperature = null;
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

    public StringLabelValueUnit getNightModeStart() {
        StringLabelValueUnit nightModeStart = new StringLabelValueUnit();
        nightModeStart.setKey("night_mode_start");
        nightModeStart.setName("nightModeStart");
        nightModeStart.setValue(this.nightModeStart);
        nightModeStart.setUnit("hh:mm");
        return nightModeStart;
    }

    public void setNightModeStart(String nightModeStart) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        dateFormat.parse(nightModeStart);
        this.nightModeStart = nightModeStart;
        log.info("Night mode end is now {}",this.nightModeStart);
    }

    public StringLabelValueUnit getNightModeEnd() {
        StringLabelValueUnit nightModeEnd = new StringLabelValueUnit();
        nightModeEnd.setKey("night_mode_end");
        nightModeEnd.setName("nightModeEnd");
        nightModeEnd.setValue(this.nightModeEnd);
        nightModeEnd.setUnit("hh:mm");
        return nightModeEnd;
    }

    public void setNightModeEnd(String nightModeEnd) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        dateFormat.parse(nightModeEnd);
        this.nightModeEnd = nightModeEnd;
        log.info("Night mode end is now {}",this.nightModeEnd);
    }

    public StringLabelValueUnit getNightMode() {
        StringLabelValueUnit nightMode = new StringLabelValueUnit();
        nightMode.setKey("night_mode");
        nightMode.setName("nightMode");
        nightMode.setValue(this.nightMode);
        nightMode.setUnit("hh:mm");
        return nightMode;
    }

    public void setNightMode(String nightMode) {
        if (!nightMode.equals("ON")) {
            nightMode = "OFF";
        }
        this.nightMode = nightMode;
        log.info("Night mode is now {}",this.nightMode);
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
            checkIfForceHotWaterHeatingTargetTempIsReached();
            checkIsRunning = false;
        } catch (Exception e){
            checkIsRunning = false;
            log.error("Exception {} occurred during computeAndSetTemperature processing!",e.getMessage());
        }
    }

    private void checkIfForceHotWaterHeatingTargetTempIsReached() {
        if (forceHotWaterHeatingIsRunning){
            Double serviceWaterTemp = heatPumpService.getServiceWaterTemp().getValue();
            Double serviceWaterFlowTemp = heatPumpService.getServiceWaterFlowTemp().getValue();
            if (serviceWaterTemp >= serviceWaterFlowTemp){
                log.info("Force hot water heating target temp reached. Setting serviceWaterFlowTemp back to {}℃",serviceWaterFlowTemperatureBeforeForceHotWaterHeating.intValue());
                heatPumpService.setServiceWaterFlowTemp(serviceWaterFlowTemperatureBeforeForceHotWaterHeating.intValue());
                forceHotWaterHeatingIsRunning = false;
            }
        }
    }



    /**
     * force hot water heating if serviceWaterFlowTemp - serviceWaterTemp > 2,
     * then fall back to old serviceWaterFlowTemp
     * @return true if heating is started
     */
    public boolean forceHotWaterHeating(){
        if (runningMode == Mode.OFF){
            log.info("Heating Logic is disabled. Force hot water heating will not be applied.");
            return false;
        }
        Double serviceWaterTemp = heatPumpService.getServiceWaterTemp().getValue();
        Double serviceWaterFlowTemp = heatPumpService.getServiceWaterFlowTemp().getValue();
        double tempDifference = serviceWaterFlowTemp - serviceWaterTemp;
        if (tempDifference >= 2 && !forceHotWaterHeatingIsRunning){
            int forceTemp = serviceWaterFlowTemp.intValue() + (int) tempDifference + 1;
            log.info("Force hot water heating. Setting serviceWaterFlowTemp to {}℃", forceTemp);
            serviceWaterFlowTemperatureBeforeForceHotWaterHeating = serviceWaterFlowTemp;
            heatPumpService.setServiceWaterFlowTemp(forceTemp);
            forceHotWaterHeatingIsRunning = true;
            return true;
        }
        return false;
    }

    public boolean isForceHotWaterHeatingPossible(){
        Double serviceWaterTemp = heatPumpService.getServiceWaterTemp().getValue();
        Double serviceWaterFlowTemp = heatPumpService.getServiceWaterFlowTemp().getValue();
        Double tempDifference = serviceWaterFlowTemp - serviceWaterTemp;
        return tempDifference >= 2 && !forceHotWaterHeatingIsRunning && runningMode == Mode.ON;
    }

    public boolean isForceHotWaterHeating(){
        return forceHotWaterHeatingIsRunning;
    }

    private void adjustHeatingWaterFlowTemperature(){
        // read the mode of the heat pump.
        String mode = heatPumpService.getMode().getValue();
        if (this.actualHeatingWaterFlowTemperature == null){
            this.actualHeatingWaterFlowTemperature = heatPumpService.getHeatingWaterFlowTemp().getValue();
        }
        // if night mode is active reduce flow temperature to standbyFlowTemperature if not already done
        if (isNightModeActive() ){
            if (heatingFlowTemperature > standbyFlowTemperature ) {
                log.info("nightMode is active -> decrease heatingFlowTemperature to standby temp {}℃ ",standbyFlowTemperature.intValue());
                setHeatingWaterFlowTemp(standbyFlowTemperature.intValue(),mode);
            }
            return;
        }
        double indoorTemp = thermometerService.getTemperature().getValue();

        if (indoorTemp < indoorSetTemperature  && !actualHeatingWaterFlowTemperature.equals(heatingFlowTemperature)) {
            log.info("indoorTemperatur {} is lower that the indoorTargetTemperatur {} -> increase heatingFlowTemperature to {}℃.",indoorTemp,indoorSetTemperature,heatingFlowTemperature.intValue());
            setHeatingWaterFlowTemp(heatingFlowTemperature.intValue(),mode);
        } else if (indoorTemp >= indoorSetTemperature && !actualHeatingWaterFlowTemperature.equals(standbyFlowTemperature)) {
            log.info("indoorTemperatur {} is equal or greater than indoorTargetTemperatur {} -> decrease heatingFlowTemperature to {}℃ ",indoorTemp,indoorSetTemperature,standbyFlowTemperature.intValue());
            setHeatingWaterFlowTemp(standbyFlowTemperature.intValue(),mode);
        } else {
            log.info("device mode {}, indoorTemperature {}℃, indoorTargetTemperatur {}℃, heatingFlowTemperature {}℃, standbyFlowTemperature {}℃, actualHeatingWaterFlowTemperature {}℃ \n " +
                            "No adjustment of waterHeatFlowTemperature needed.",
                    mode,indoorTemp,indoorSetTemperature,heatingFlowTemperature,standbyFlowTemperature,actualHeatingWaterFlowTemperature);
        }
    }

    // set flow temperature in version 2.01 by using workaround for bug that the temp could not be set
    private void setHeatingWaterFlowTemp(int heatingFlowTemp, String original_mode) {
        if (heatPumpService.getControlPanelVersion().equals("201")) {
            if (original_mode.equals(heatPumpService.getWorkingModeValue("workingModeHotWater"))){
                heatPumpService.setMode("workingModeHotWaterAndHeating");
            } else {
                heatPumpService.setMode("workingModeHotWater");
            }
        }
        heatPumpService.setHeatingWaterFlowTemp(heatingFlowTemp);
        if (heatPumpService.getControlPanelVersion().equals("201")) {
            // get the correct value to pass as parameter
            heatPumpService.setMode(heatPumpService.getWorkingModeKey(original_mode));
        }

        this.actualHeatingWaterFlowTemperature = heatPumpService.getHeatingWaterFlowTemp().getValue();
    }

    public boolean isNightModeActive(){
        if (nightMode.equals("OFF")) {
            return false;
        }
        String[] result1 = nightModeStart.split(":");
        String[] result2 = nightModeEnd.split(":");

        ZonedDateTime now = Instant.now().atZone(ZoneId.systemDefault());

        ZonedDateTime nightModeStart = Instant.now().atZone(ZoneId.systemDefault()).withHour(Integer.decode(result1[0])).withMinute(Integer.decode(result1[1]));

        ZonedDateTime nightModeEnd = Instant.now().atZone(ZoneId.systemDefault()).withHour(Integer.decode(result2[0])).withMinute(Integer.decode(result2[1])).plus(1, ChronoUnit.DAYS);

        return now.isAfter(nightModeStart) && now.isBefore(nightModeEnd);
    }
}

