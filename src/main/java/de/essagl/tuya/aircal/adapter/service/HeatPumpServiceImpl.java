package de.essagl.tuya.aircal.adapter.service;

import de.essagl.tuya.aircal.adapter.ability.model.*;
import de.essagl.tuya.aircal.adapter.model.ControlParameter;
import de.essagl.tuya.aircal.adapter.model.ControlParameter201;
import de.essagl.tuya.aircal.adapter.model.ControlParameter202;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class HeatPumpServiceImpl implements HeatPumpService {
    private final DeviceService deviceService;
    private final String heatPumpDeviceId;

    private final Properties heatPumpCodeKeys;
    private final Map<String,String> heatPumpModes;

    private String heatPumpControlPanelVersion;


    public HeatPumpServiceImpl(DeviceService deviceService, @Value("${heatPumpDeviceId}") String heatPumpDeviceId,
                               @Value("${heatPumpControlPanelVersion}") String heatPumpControlPanelVersion) throws IOException {
        this.deviceService = deviceService;
        this.heatPumpDeviceId = heatPumpDeviceId;
        this.heatPumpControlPanelVersion = heatPumpControlPanelVersion;

        Resource propertiesResource = new ClassPathResource("/keyBinding/"+heatPumpControlPanelVersion+".properties");
        heatPumpCodeKeys = new Properties();
        heatPumpCodeKeys.load(propertiesResource.getInputStream());

        heatPumpModes = new HashMap<>();
        for (String modeName : HeatPumpService.heatPumpModes){
            heatPumpModes.put(modeName,heatPumpCodeKeys.getProperty(modeName));
        }

    }


    @Override
    public Device getDeviceSpecification() {
        return deviceService.getById(heatPumpDeviceId);
    }


    @Override
    public Boolean setSwitch(boolean value) {
        return deviceService.sendCommand(heatPumpDeviceId, heatPumpCodeKeys.getProperty("switch"),value);
    }

    @Override
    public Boolean setMode(String value) {
        return deviceService.sendCommand(heatPumpDeviceId, heatPumpCodeKeys.getProperty("mode"),getWorkingModeValue(value));
    }

    @Override
    public Boolean setHeating(Boolean OnOff) {
        if (OnOff){
            if (getMode().getValue().equals("workingModeHotWater")){
                return setMode("workingModeHotWaterAndHeating");
            } else if (getMode().getValue().equals("workingModeCooling")){
                return setMode("workingModeHotWaterAndCooling");
            } else {
                return true;
            }
        } else {
            if (getMode().getValue().equals("workingModeHotWaterAndHeating")){
                return setMode("workingModeHotWater");
            } else if (getMode().getValue().equals("workingModeHotWaterAndCooling")){
                return setMode("workingModeHotWater");
            } else {
                return true;
            }
        }
    }

    @Override
    public Boolean setHeatingWaterFlowTemp(int value) {
        return deviceService.sendCommand(heatPumpDeviceId, heatPumpCodeKeys.getProperty("heatingWaterFlowTemp"),value);
    }
    @Override
    public Boolean setServiceWaterFlowTemp(int value){
        return deviceService.sendCommand(heatPumpDeviceId, heatPumpCodeKeys.getProperty("serviceWaterFlowTemp"),value);
    }

    @Override
    public DeviceInformation getDeviceInformation() {
        return deviceService.getDeviceInformationById(heatPumpDeviceId);
    }
    @Override
    public BooleanLabelValueUnit getSwitch() {
        return deviceService.getBooleanLabelValueForKey(heatPumpCodeKeys.getProperty("switch"),heatPumpDeviceId);
    }

    @Override
    public DoubleLabelValueUnit getHeatingWaterFlowTemp() {
        DoubleLabelValueUnit heatingWaterFlowTemp = deviceService.getDoubleLabelValueForKey(heatPumpCodeKeys.getProperty("heatingWaterFlowTemp"), heatPumpDeviceId);
        heatingWaterFlowTemp.setName("heatingWaterFlowTemp");
        return heatingWaterFlowTemp;
    }

    @Override
    public DoubleLabelValueUnit getServiceWaterFlowTemp() {
        DoubleLabelValueUnit serviceWaterFlowTemp = deviceService.getDoubleLabelValueForKey(heatPumpCodeKeys.getProperty("serviceWaterFlowTemp"), heatPumpDeviceId);
        serviceWaterFlowTemp.setName("serviceWaterFlowTemp");
        return serviceWaterFlowTemp;
    }

    @Override
    public DoubleLabelValueUnit getRoomTemp() {
        return getControlParameter().getC26();
    }

    @Override
    public DoubleLabelValueUnit getServiceWaterTemp() {
        DoubleLabelValueUnit serviceWaterTemp =  deviceService.getDoubleLabelValueForKey(heatPumpCodeKeys.getProperty("serviceWaterTemp"), heatPumpDeviceId) ;
        serviceWaterTemp.setValue(serviceWaterTemp.getValue() / 10);
        serviceWaterTemp.setName("serviceWaterTemp");
        return serviceWaterTemp;
    }

    @Override
    public StringLabelValueUnit getMode() {
        StringLabelValueUnit workingMode =  deviceService.getStringLabelValueForKey(heatPumpCodeKeys.getProperty("mode"), heatPumpDeviceId);
        workingMode.setValue(getWorkingModeKey(workingMode.getValue()));
        workingMode.setUnit("[workingModeHotWater, workingModeHeating, workingModeCooling, workingModeHotWaterAndHeating, workingModeHotWaterAndCooling]");
        return workingMode;
    }

    @Override
    public DoubleLabelValueUnit getOutsideTemp() {
        DoubleLabelValueUnit outsideTemp =   deviceService.getDoubleLabelValueForKey(heatPumpCodeKeys.getProperty("outsideTemp"), heatPumpDeviceId);
        outsideTemp.setValue(outsideTemp.getValue() / 10);
        outsideTemp.setName("outsideTemp");
        return outsideTemp;
    }

    @Override
    public String getWorkingModeValue(String key) {
        if (heatPumpModes.containsKey(key)){
            return heatPumpModes.get(key);
        }
        return null;
    }

    @Override
    public String getWorkingModeKey(String value) {
        for (Map.Entry<String,String> entry : heatPumpModes.entrySet()){
            if (entry.getValue().equalsIgnoreCase(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public String getControlPanelVersion() {
        return heatPumpControlPanelVersion;
    }

    @Override
    public ControlParameter getControlParameter() {
        if (heatPumpControlPanelVersion.equals("202")){
            byte[] c1c30DecodedBytes = Base64.getDecoder().decode(deviceService.getStringValueForKey("c01_c30_reading",heatPumpDeviceId));
            byte[] c31c56DecodedBytes = Base64.getDecoder().decode(deviceService.getStringValueForKey("c31_c56_reading",heatPumpDeviceId));
            return new ControlParameter202(c1c30DecodedBytes,c31c56DecodedBytes);
        } else if (heatPumpControlPanelVersion.equals("201")) {
            return new ControlParameter201();
        }
        return null;
    }
}
