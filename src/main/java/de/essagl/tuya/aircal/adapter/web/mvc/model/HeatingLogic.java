package de.essagl.tuya.aircal.adapter.web.mvc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class HeatingLogic {
    private String heatingLogicMode;
    private double indoorTemp;
    private double outdoorTemp;
    private double targetIndoorTemp;
    private double heatingFlowTemp;
    private double standbyFlowTemp;
    private boolean heatPumpOnline;
}
