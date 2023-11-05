package de.essagl.tuya.aircal.adapter.web.mvc;

import de.essagl.tuya.aircal.adapter.web.mvc.model.HeatingLogic;
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
    public String save(SetupData setupData, Model model) {
        storeSetupDataInFile();
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
        // ...
        return setupData;
    }

    private void storeSetupDataInFile() {

        return;
    }
}
