package de.essagl.tuya.aircal.adapter.web;

import de.essagl.tuya.aircal.adapter.ability.model.DeviceInformation;
import de.essagl.tuya.aircal.adapter.ability.model.Device;
import de.essagl.tuya.aircal.adapter.ability.model.DoubleLabelValueUnit;
import de.essagl.tuya.aircal.adapter.ability.model.StringLabelValueUnit;
import de.essagl.tuya.aircal.adapter.service.IndoorThermometerService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.pulsar.shade.io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @author ulrich@muehlgasse.de
 */
@RestController
@RequestMapping("/indoorThermometer")
@Api(value = "Indoor Thermometer Controller")
public class IndoorThermometerController {
    private final IndoorThermometerService thermometerService;


    public IndoorThermometerController(IndoorThermometerService indoorThermometerService) {
        this.thermometerService = indoorThermometerService;
    }

    @Operation(summary = "Get the available device parameters.")
    @GetMapping()
    public Device getDeviceSpecification() {
        return thermometerService.getDeviceSpecification();
    }

     @Operation(summary = "Get the device information.")
    @GetMapping("/deviceInformation")
    public DeviceInformation getDeviceInformation() {
        return thermometerService.getDeviceInformation(); //.getName();
    }

    @Operation(summary = "Get the indoor temperature")
    @GetMapping("/temperature")
    public DoubleLabelValueUnit getTemperature() {
        return thermometerService.getTemperature();
    }

    @Operation(summary = "Get the indoor humidity in %")
    @GetMapping("/humidity")
    public DoubleLabelValueUnit getHumidity() {
        return thermometerService.getHumidity();
    }


    @Operation(summary = "Get the battery status \"low\",\"middle\",\"high\"")
    @GetMapping("/battery_status")
    public StringLabelValueUnit getBatteryStatus() {
         return thermometerService.getBatteryStatus();
    }


}

