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

    private final String indoorThermometerDeviceId;


    public IndoorThermometerService(DeviceService deviceService, @Value("${indoorThermometerDeviceId}") String indoorThermometerDeviceId) {
        this.deviceService = deviceService;
        this.indoorThermometerDeviceId = indoorThermometerDeviceId;
    }

    public Device getDeviceSpecification() {
        return deviceService.getById(indoorThermometerDeviceId);
    }
    public DeviceInformation getDeviceInformation() {
        return deviceService.getDeviceInformationById(indoorThermometerDeviceId); //.getName();
    }

    public DoubleLabelValueUnit getTemperature() {
        DoubleLabelValueUnit dlv = deviceService.getDoubleLabelValueForKey("va_temperature", indoorThermometerDeviceId);
        dlv.setValue(dlv.getValue()/10);
        return dlv;
    }

    public DoubleLabelValueUnit getHumidity() {
        DoubleLabelValueUnit dlv = deviceService.getDoubleLabelValueForKey("va_humidity", indoorThermometerDeviceId);
        return dlv;
    }

    public StringLabelValueUnit getBatteryStatus() {
        return deviceService.getStringLabelValueForKey("battery_state", indoorThermometerDeviceId);
    }
}
