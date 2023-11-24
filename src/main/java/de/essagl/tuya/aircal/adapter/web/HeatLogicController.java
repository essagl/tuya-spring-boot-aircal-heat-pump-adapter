package de.essagl.tuya.aircal.adapter.web;


import de.essagl.tuya.aircal.adapter.ability.model.BooleanLabelValueUnit;
import de.essagl.tuya.aircal.adapter.ability.model.DoubleLabelValueUnit;
import de.essagl.tuya.aircal.adapter.ability.model.StringLabelValueUnit;
import de.essagl.tuya.aircal.adapter.service.operationalControl.HeatingLogicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


/**
 * <p> TODO
 *
 * @author qiufeng.yu@tuya.com
 * @since 2021/4/1 10:14 下午
 */
@RestController
@RequestMapping("/heatingLogic")
public class HeatLogicController {
    @Autowired
    private HeatingLogicService heatingLogicService;

    @Operation(summary = "Set the indoor target temperatur in ℃. min:10,max:28")
    @PostMapping("/indoor_target_temp/{value}")
    public void setHeatingWaterTemp(@Parameter(required = true, description = "Room indoor Temp.") @PathVariable("value") Double value) {
         heatingLogicService.setIndoorSetTemperature(value);
    }
    @Operation(summary = "Get the value for the indoor target temperature")
    @GetMapping("/indoor_target_temp")
    public DoubleLabelValueUnit getTemp_set() {
         return heatingLogicService.getIndoorSetTemperatureValue();
    }

    @Operation(summary = "Set the heating flow  temperatur in ℃. min:10,max:70")
    @PostMapping("/heating_flow_temp/{value}")
    public void setHeatingFlowWaterTemp(@Parameter(required = true, description = "Heating flow Temp.") @PathVariable("value") Double value) {
        heatingLogicService.setHeatingFlowTemperature(value);
    }
    @Operation(summary = "Get the value for the heating flow temperature")
    @GetMapping("/heating_flow_temp")
    public DoubleLabelValueUnit getHeatingFlowTemp() {
        return heatingLogicService.getHeatingFlowTemperatureValue();
    }

    @Operation(summary = "Set the standby flow  temperatur in ℃. min:10,max:heating flow temp")
    @PostMapping("/standby_flow_temp/{value}")
    public void setStandbyFlowWaterTemp(@Parameter(required = true, description = "Standby flow Temp.") @PathVariable("value") Double value) {
        heatingLogicService.setStandbyFlowTemperature(value);
    }
    @Operation(summary = "Get the value for the standby flow temperature")
    @GetMapping("/standby_flow_temp")
    public DoubleLabelValueUnit getStandbyFlowTemp() {
        return heatingLogicService.getStandbyFlowTemperatureValue();
    }

    @Operation(summary = "Enable or disable the heating logic.")
    @PostMapping("/mode/{value}")
    public void setMode(@Parameter(required = true,
            description = "Enable or disable the heating logic.",
            schema = @Schema(allowableValues = { "ON","OFF"}))  @PathVariable("value") String value) {
        heatingLogicService.setRunningMode(value);
    }
    @Operation(summary = "Get the running mode")
    @GetMapping("/mode")
    public StringLabelValueUnit getMode() {
        return heatingLogicService.getRunningModeValue();
    }

    @Operation(summary = "Enable or disable the night mode.")
    @PostMapping("/night_mode/{value}")
    public void setNightMode(@Parameter(required = true,
            description = "Enable or disable the night mode.",
            schema = @Schema(allowableValues = { "ON","OFF"}))  @PathVariable("value") String value) {
        heatingLogicService.setNightMode(value);
    }
    @Operation(summary = "Get the night mode value")
    @GetMapping("/night_mode")
    public StringLabelValueUnit getNightMode() {
        return heatingLogicService.getNightMode();
    }

    @Operation(summary = "Is the night mode active")
    @GetMapping("/night_mode_active")
    public BooleanLabelValueUnit getNightModeActive() {
        BooleanLabelValueUnit nightModeActive = new BooleanLabelValueUnit();
        nightModeActive.setValue(heatingLogicService.isNightModeActive());
        nightModeActive.setKey("nightModeActive");
        nightModeActive.setUnit("true/false");
        return nightModeActive;
    }

    @Operation(summary = "Set the night mode start hour and minutes in hh:mm ")
    @PostMapping("/night_mode_start/{value}")
    public void setNightModeStart(@Parameter(required = true, description = "night mode start hour and minutes") @PathVariable("value") String value) throws ParseException {
        heatingLogicService.setNightModeStart(value);
    }
    @Operation(summary = "Get the value the night mode start hour and minutes in hh:mm")
    @GetMapping("/night_mode_start")
    public StringLabelValueUnit getNightModeStart() {
        return heatingLogicService.getNightModeStart();
    }

    @Operation(summary = "Set the night mode end hour and minutes in hh:mm")
    @PostMapping("/night_mode_end/{value}")
    public void setNightModeEnd(@Parameter(required = true, description = "night mode end hour and minutes") @PathVariable("value") String value) throws ParseException {
        heatingLogicService.setNightModeEnd(value);
    }
    @Operation(summary = "Get the value the night mode end hour and minutes in hh:mm (of the next day)")
    @GetMapping("/night_mode_end")
    public StringLabelValueUnit getNightModeEnd() {
        return heatingLogicService.getNightModeEnd();
    }

    @Operation(summary = "Force hot water heating. Returns true if the command was accepted.")
    @PostMapping("/forceHotWaterHeating")
    public boolean forceHotWaterHeating() {
        return heatingLogicService.forceHotWaterHeating();
    }
    @Operation(summary = "Check if force hot water heating is active")
    @GetMapping("/isForceHotWaterHeating")
    public boolean isForceHotWaterHeating() {
        return heatingLogicService.isForceHotWaterHeating();
    }

    @Operation(summary = "Check if force hot water heating is possible")
    @GetMapping("/isForceHotWaterHeatingPossible")
    public boolean isForceHotWaterHeatingPossible() {
        return heatingLogicService.isForceHotWaterHeatingPossible();
    }
}
