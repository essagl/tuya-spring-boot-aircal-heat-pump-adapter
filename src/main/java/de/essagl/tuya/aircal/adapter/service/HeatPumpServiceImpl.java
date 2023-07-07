package de.essagl.tuya.aircal.adapter.service;

import de.essagl.tuya.aircal.adapter.ability.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

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
        return deviceService.getDoubleLabelValueForKey(heatPumpCodeKeys.getProperty("heatingWaterFlowTemp"), heatPumpDeviceId);
    }

    @Override
    public DoubleLabelValueUnit getServiceWaterFlowTemp() {
        return deviceService.getDoubleLabelValueForKey(heatPumpCodeKeys.getProperty("serviceWaterFlowTemp"), heatPumpDeviceId);
    }

    @Override
    public DoubleLabelValueUnit getServiceWaterTemp() {
        DoubleLabelValueUnit serviceWaterTemp =  deviceService.getDoubleLabelValueForKey(heatPumpCodeKeys.getProperty("serviceWaterTemp"), heatPumpDeviceId) ;
        serviceWaterTemp.setValue(serviceWaterTemp.getValue() / 10);
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
}
