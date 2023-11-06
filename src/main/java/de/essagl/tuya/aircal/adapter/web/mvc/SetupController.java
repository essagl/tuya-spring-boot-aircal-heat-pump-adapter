package de.essagl.tuya.aircal.adapter.web.mvc;

import de.essagl.tuya.aircal.adapter.web.mvc.model.SetupData;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Properties;

@Controller
@RequestMapping({ "/setup" })
public class SetupController {


    @GetMapping
    public String main(Model model) throws IOException {
        SetupData setupdata = getSetupDataFromFile();
        model.addAttribute("setupData", setupdata);
        return "setup";
    }


    @PostMapping
    public String save(SetupData setupData, Model model) throws IOException {
        storeSetupDataInFile(setupData);
        model.addAttribute("setupData", setupData);
        return "setup";
    }
    public static SetupData getSetupDataFromFile() throws IOException {
        FileSystemResource fileSystemResource = new FileSystemResource("/home/pi/heatlogic/application.properties");
        Properties properties = new Properties();
        properties.load(fileSystemResource.getInputStream());
        SetupData setupData = new SetupData();
        setupData.setConnectorAccessId(properties.getProperty("connector.ak"));
        setupData.setConnectorSecret(properties.getProperty("connector.sk"));
        setupData.setConnectorRegion(properties.getProperty("connector.region"));
        setupData.setHeatPumpControlPanelVersion(properties.getProperty("heatPumpControlPanelVersion"));
        setupData.setHeatingLogicMode(properties.getProperty("heatingLogicMode"));
        setupData.setHeatingLogicRunningRate(Integer.parseInt(properties.getProperty("heatingLogicRunningRate")));
        setupData.setIndoorDefaultSetTemperature(Double.parseDouble(properties.getProperty("indoorDefaultSetTemperature")));
        setupData.setHeatingFlowTemperature(Double.parseDouble(properties.getProperty("heatingFlowTemperature")));
        setupData.setStandbyFlowTemperature(Double.parseDouble(properties.getProperty("standbyFlowTemperature")));
        setupData.setIpAddress(properties.getProperty("ipAddress"));
        setupData.setHeatPumpDeviceId(properties.getProperty("heatPumpDeviceId"));
        setupData.setIndoorThermometerDeviceId(properties.getProperty("indoorThermometerDeviceId"));
        // ...
        return setupData;
    }

    private void storeSetupDataInFile(SetupData setupData) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("connector.ak", setupData.getConnectorAccessId());
        properties.setProperty("connector.sk", setupData.getConnectorSecret());
        properties.setProperty("connector.region", setupData.getConnectorRegion());
        properties.setProperty("heatPumpControlPanelVersion", setupData.getHeatPumpControlPanelVersion());
        properties.setProperty("heatingLogicMode", setupData.getHeatingLogicMode());
        properties.setProperty("heatingLogicRunningRate", String.valueOf(setupData.getHeatingLogicRunningRate()));
        properties.setProperty("indoorDefaultSetTemperature", String.valueOf(setupData.getIndoorDefaultSetTemperature()));
        properties.setProperty("heatingFlowTemperature", String.valueOf(setupData.getHeatingFlowTemperature()));
        properties.setProperty("standbyFlowTemperature", String.valueOf(setupData.getStandbyFlowTemperature()));
        properties.setProperty("heatPumpDeviceId", setupData.getHeatPumpDeviceId());
        properties.setProperty("indoorThermometerDeviceId", setupData.getIndoorThermometerDeviceId());
        FileSystemResource fileSystemResource = new FileSystemResource("/home/pi/heatlogic/application.properties");
        properties.store(fileSystemResource.getOutputStream(), "HeatLogic Configuration");
    }
}
