package de.essagl.tuya.aircal.adapter.web.mvc;

import de.essagl.tuya.aircal.adapter.service.HeatPumpService;
import de.essagl.tuya.aircal.adapter.service.IndoorThermometerService;
import de.essagl.tuya.aircal.adapter.web.mvc.model.HeatingLogic;
import de.essagl.tuya.aircal.adapter.service.operationalControl.HeatingLogicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/heatlogic" })
public class HeatLogicWebPageController {
    private final HeatPumpService heatPumpService;
    private final IndoorThermometerService thermometerService;
    private final HeatingLogicService heatingLogicService;

    public HeatLogicWebPageController(HeatPumpService heatPumpService, IndoorThermometerService thermometerService, HeatingLogicService heatingLogicService) {
        this.heatPumpService = heatPumpService;
        this.thermometerService = thermometerService;
        this.heatingLogicService = heatingLogicService;
    }

    @GetMapping
    public String main(Model model) {
        HeatingLogic heatingLogic = new HeatingLogic();

        heatingLogic.setIndoorTemp(thermometerService.getTemperature().getValue());
        heatingLogic.setOutdoorTemp(heatPumpService.getOutsideTemp().getValue());

        heatingLogic.setHeatingLogicMode(heatingLogicService.getRunningModeValue().getValue());
        heatingLogic.setTargetIndoorTemp(heatingLogicService.getIndoorSetTemperatureValue().getValue());
        heatingLogic.setStandbyFlowTemp(heatingLogicService.getStandbyFlowTemperatureValue().getValue());
        heatingLogic.setHeatingFlowTemp(heatingLogicService.getHeatingFlowTemperatureValue().getValue());
        model.addAttribute("heatingLogic", heatingLogic);
        return "heatlogic";
    }

    @PostMapping
    public String save(HeatingLogic heatingLogic, Model model) {
        model.addAttribute("heatingLogic", heatingLogic);
        heatingLogicService.setRunningMode(heatingLogic.getHeatingLogicMode());
        heatingLogicService.setHeatingFlowTemperature(heatingLogic.getHeatingFlowTemp());
        heatingLogicService.setStandbyFlowTemperature(heatingLogic.getStandbyFlowTemp());
        heatingLogicService.setIndoorSetTemperature(heatingLogic.getTargetIndoorTemp());
        return "saved";
    }
}
