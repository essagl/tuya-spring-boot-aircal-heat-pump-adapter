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
@RequestMapping({ "/", "/index" })
public class IndexWebPageController {
    private final HeatPumpService heatPumpService;
    private final IndoorThermometerService thermometerService;
    private final HeatingLogicService heatingLogicService;

    public IndexWebPageController(HeatPumpService heatPumpService, IndoorThermometerService thermometerService, HeatingLogicService heatingLogicService) {
        this.heatPumpService = heatPumpService;
        this.thermometerService = thermometerService;
        this.heatingLogicService = heatingLogicService;
    }

    @GetMapping
    public String main(Model model) {
        HeatingLogic heatingLogic = new HeatingLogic();
        heatingLogic.setHeatPumpOnline(heatPumpService.getDeviceInformation().getOnline());
        heatingLogic.setIndoorTemp(thermometerService.getTemperature().getValue());
        heatingLogic.setOutdoorTemp(heatPumpService.getOutsideTemp().getValue());
        heatingLogic.setEffectiveHotWaterTemp(heatPumpService.getServiceWaterTemp().getValue());
        setHeatPumpMode(heatingLogic);
        heatingLogic.setFanSpeed(heatPumpService.getFanSpeed().getValue());
        heatingLogic.setPowerConsumption(heatPumpService.getPowerConsumption().getValue());
        model.addAttribute("heatingLogic", heatingLogic);
        return "index";
    }

    @PostMapping
    public String save(HeatingLogic heatingLogic, Model model) {
        model.addAttribute("index", heatingLogic);
        heatingLogicService.setRunningMode(heatingLogic.getHeatingLogicMode());
        heatingLogicService.setHeatingFlowTemperature(heatingLogic.getHeatingFlowTemp());
        heatingLogicService.setStandbyFlowTemperature(heatingLogic.getStandbyFlowTemp());
        heatingLogicService.setIndoorSetTemperature(heatingLogic.getTargetIndoorTemp());
        return "saved";
    }

    private void setHeatPumpMode(HeatingLogic heatingLogic) {
        switch (heatPumpService.getMode().getValue()){
            case "workingModeHotWater":
                heatingLogic.setWorkingMode("HOT WATER");
                break;
            case "workingModeHotWaterAndHeating":
                heatingLogic.setWorkingMode("HOT WATER AND HEATING");
                break;
            case "workingModeHeating":
                heatingLogic.setWorkingMode("HEATING");
                break;
            case "workingModeCooling":
                heatingLogic.setWorkingMode("COOLING");
                break;
            case "workingModeHotWaterAndCooling":
                heatingLogic.setWorkingMode("HOT WATER AND COOLING");
                break;
        }
    }
}
