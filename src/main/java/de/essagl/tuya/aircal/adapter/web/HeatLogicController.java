package de.essagl.tuya.aircal.adapter.web;


import de.essagl.tuya.aircal.adapter.ability.model.DoubleLabelValueUnit;
import de.essagl.tuya.aircal.adapter.ability.model.StringLabelValueUnit;
import de.essagl.tuya.aircal.adapter.service.operationalControl.HeatingLogicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



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



}
