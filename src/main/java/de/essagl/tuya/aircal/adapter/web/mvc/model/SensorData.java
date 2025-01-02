package de.essagl.tuya.aircal.adapter.web.mvc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is there for compatibility with existing ESP32 implementation.
 * So the influx db can be kept as it is for gathering new statistical values.
 */
@Setter
@Getter
@NoArgsConstructor
public class SensorData {
    //Solar water temperature (external thermometer not implemented yet)
    private double temp0;
    // outside temperature (heat pump)
    private double temp1;
    // room temperature (heat pump) or (external sensor)
    private double temp2;
    // hot water temperature (heat pump)
    private double temp3;
    // power consumption in watt (heat pump)
    private double powerConsumption;
}
