package de.essagl.tuya.aircal.adapter.service;

import de.essagl.tuya.aircal.adapter.ability.model.Device;
import de.essagl.tuya.aircal.adapter.ability.model.DeviceInformation;
import de.essagl.tuya.aircal.adapter.ability.model.DoubleLabelValueUnit;
import de.essagl.tuya.aircal.adapter.ability.model.StringLabelValueUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IndoorThermometerService {

    private final DeviceService deviceService;
    private final HeatPumpService heatPumpService;

    private final String indoorThermometerDeviceId;


    public IndoorThermometerService(DeviceService deviceService, HeatPumpService heatPumpService, @Value("${indoorThermometerDeviceId:0}") String indoorThermometerDeviceId) {
        this.deviceService = deviceService;
        this.heatPumpService = heatPumpService;
        this.indoorThermometerDeviceId = indoorThermometerDeviceId;
    }

    public Device getDeviceSpecification() {
        return deviceService.getById(indoorThermometerDeviceId);
    }
    public DeviceInformation getDeviceInformation() {
        if (indoorThermometerDeviceId.equals("0")) {
            DeviceInformation deviceInformation = new DeviceInformation();
            deviceInformation.setId("0");
            deviceInformation.setName("Indoor Thermometer (not configured)");
            deviceInformation.setOnline(false);
        }
        return deviceService.getDeviceInformationById(indoorThermometerDeviceId); //.getName();
    }

    public DoubleLabelValueUnit getTemperature() {
        if (indoorThermometerDeviceId.equals("0")) {
            return new DoubleLabelValueUnit("c26", "Heat pump thermometer", heatPumpService.getRoomTemp().getValue(), "℃");
        }
        DoubleLabelValueUnit dlv = deviceService.getDoubleLabelValueForKey("va_temperature", indoorThermometerDeviceId);
        dlv.setValue(dlv.getValue()/10);
        return dlv;
    }

    public DoubleLabelValueUnit getHumidity() {
        if (indoorThermometerDeviceId.equals("0")) {
            return new DoubleLabelValueUnit("va_humidity", "Indoor Thermometer (not configured)", 0d, "%");
        }
        DoubleLabelValueUnit dlv = deviceService.getDoubleLabelValueForKey("va_humidity", indoorThermometerDeviceId);
        return dlv;
    }

    public StringLabelValueUnit getBatteryStatus() {
        if (indoorThermometerDeviceId.equals("0")) {
            return new StringLabelValueUnit("battery_state", "Indoor Thermometer (not configured)", "unknown");
        }
        return deviceService.getStringLabelValueForKey("battery_state", indoorThermometerDeviceId);
    }
}
