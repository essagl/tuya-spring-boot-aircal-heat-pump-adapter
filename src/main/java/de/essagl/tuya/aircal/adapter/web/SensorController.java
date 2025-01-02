package de.essagl.tuya.aircal.adapter.web;

import de.essagl.tuya.aircal.adapter.web.mvc.IndexWebPageController;
import de.essagl.tuya.aircal.adapter.web.mvc.model.SensorData;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.pulsar.shade.io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/sensor")
@Api(value = "Sensors Controller")
public class SensorController {
     private final IndexWebPageController indexWebPageController;

    public SensorController(IndexWebPageController indexWebPageController) {
        this.indexWebPageController = indexWebPageController;
    }
    @Operation(summary = "Get the sensor data. Used for statistical purposes to store the data for diagrams. " +
            "Instead o f 0 return -27 if the sensor is not connected.")
    @GetMapping("/data")
    public SensorData getSensorData() {
        return indexWebPageController.getSensorData();
    }

}
