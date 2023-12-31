package de.essagl.tuya.aircal.adapter.web;

import de.essagl.tuya.aircal.adapter.ability.model.*;
import de.essagl.tuya.aircal.adapter.model.ControlParameter;
import de.essagl.tuya.aircal.adapter.service.HeatPumpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.pulsar.shade.io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author ulrich@muehlgasse.de
 */
@RestController
@RequestMapping("/heatPump")
@Api(value = "AirCal HeatPumpController")
public class HeatPumpController {

    private final HeatPumpService heatPumpService;

    public HeatPumpController(HeatPumpService heatPumpService) throws IOException {

        this.heatPumpService = heatPumpService;
    }

    @Operation(summary = "Get the specifications and properties of the device.")
    @GetMapping()
    public Device getById() {
        return heatPumpService.getDeviceSpecification();
    }

    @Operation(summary = "Turn the pump active or inactive (sleep mode stops water pump)")
    @PostMapping("/switch/{value}")
    public Boolean setSwitch(@Parameter(required = true, description = "Switch working ON or OFF") @PathVariable("value") boolean value) {
        return heatPumpService.setSwitch(value);
    }

    @Operation(summary = "Turn the pump mode to heating without disabling hot water or cooling or disable heating without disabling hot water or cooling")
    @PostMapping("/heating/{value}")
    public Boolean setHeating(@Parameter(required = true, description = "Switch heating ON or OFF") @PathVariable("value") boolean value) {
        return heatPumpService.setHeating(value);
    }

    @Operation(summary = "Set the working mode")
    @PostMapping("/mode/{value}")
    public Boolean setMode( @PathVariable ("value")  @Parameter(required = true,
            description = "Select the working mode for the heat pump " +
                    "\"workingModeHotWater\",\"workingModeHeating\",\"workingModeCooling\",\"workingModeHotWaterAndHeating\",\"workingModeHotWaterAndCooling\" ",
            schema = @Schema(allowableValues = { "workingModeHotWater","workingModeHeating","workingModeCooling","workingModeHotWaterAndHeating","workingModeHotWaterAndCooling"})) String value) {
        return heatPumpService.setMode(value);
    }
    @Operation(summary = "Set the temperature of the heating water flow temperature in ℃. min:7,max:75,step:1")
    @PostMapping("/heatingWaterFlowTemp/{value}")
    public Boolean setHeatingWaterFlowTemp(@Parameter(required = true, description = "Heating water Temp.") @PathVariable("value") int value) {
        return heatPumpService.setHeatingWaterFlowTemp(value);
    }

    @Operation(summary = "Set the temperature of the custom and drinking water flow temperature in ℃. min:10,max:70,step:1")
    @PostMapping("/serviceWaterFlowTemp/{value}")
    public Boolean setServiceWaterFlowTemp(@Parameter(required = true, description = "Custom and drinking water Temp.") @PathVariable("value") int value) {
        return heatPumpService.setServiceWaterFlowTemp(value);
    }



    @Operation(summary = "Get the device information.")
    @GetMapping("/deviceInformation")
    public DeviceInformation getDeviceInformation() {
        return heatPumpService.getDeviceInformation();
    }
    @Operation(summary = "Get the information if the pump is active or inactive")
    @GetMapping("/switch")
    public BooleanLabelValueUnit getSwitch() {
        return heatPumpService.getSwitch();
    }

    @Operation(summary = "Get the value for the heating water flow temperature")
    @GetMapping("/heatingWaterFlowTemp")
    public DoubleLabelValueUnit getHeatingWaterFlowTemp() {
        return heatPumpService.getHeatingWaterFlowTemp();
    }

    @Operation(summary = "Get the value for the custom and drinking water flow temperature")
    @GetMapping("/serviceWaterFlowTemp")
    public DoubleLabelValueUnit getServiceWaterFlowTemp() {
        return  heatPumpService.getServiceWaterFlowTemp();
    }

    @Operation(summary = "Get the value for the room temperature")
    @GetMapping("/roomTemp")
    public DoubleLabelValueUnit getRoomTemp() {
        return heatPumpService.getRoomTemp();
    }

    @Operation(summary = "Get the actual value of the custom and drinking water measured temperature")
    @GetMapping("/serviceWaterTemp")
    public DoubleLabelValueUnit getServiceWaterTemp() {
        return heatPumpService.getServiceWaterTemp();
    }

    @Operation(summary = "Get the active working mode")
    @GetMapping("/mode")
    public StringLabelValueUnit getMode() {
         return heatPumpService.getMode();
    }

    @Operation(summary = "Get the actual outside environment temperature by the heat pump")
    @GetMapping("/outsideTemp")
    public DoubleLabelValueUnit getOutsideTemp() {
        return  heatPumpService.getOutsideTemp();
    }

    @Operation(summary = "Get the actual fan speed of the heat pump")
    @GetMapping("/fanSpeed")
    public DoubleLabelValueUnit getFanSpeed() {
        return heatPumpService.getFanSpeed();
    }

    @Operation(summary = "Get the power consumption of the heat pump")
    @GetMapping("/powerConsumption")
    public DoubleLabelValueUnit powerConsumption() {
        return heatPumpService.getPowerConsumption();
    }

    @Operation(summary = "unused")
    @GetMapping("/valve1Position")
    public Boolean getValve1Position() {
        return  heatPumpService.getValve1Position();
    }
    @Operation(summary = "Get the valve 2 position (false = heating warm water tank - true = heating radiators)")
    @GetMapping("/valve2Position")
    public Boolean getValve2Position() {
        return  heatPumpService.getValve2Position();
    }

    //################  control parameter #######################
    @Operation(summary = "Get the actual control parameter of the heat pump")
    @GetMapping("/controlParameter")
    public ControlParameter getControlParameter() {
        return  heatPumpService.getControlParameter();
    }
}
