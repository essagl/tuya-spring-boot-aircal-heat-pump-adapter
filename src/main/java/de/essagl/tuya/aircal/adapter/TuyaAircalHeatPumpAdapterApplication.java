package de.essagl.tuya.aircal.adapter;

import com.tuya.connector.open.messaging.autoconfig.EnableMessaging;
import com.tuya.connector.spring.annotations.ConnectorScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@ConnectorScan(basePackages = "de.essagl.tuya.aircal.adapter")
// @EnableMessaging
@SpringBootApplication
public class TuyaAircalHeatPumpAdapterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuyaAircalHeatPumpAdapterApplication.class, args);
    }

}
