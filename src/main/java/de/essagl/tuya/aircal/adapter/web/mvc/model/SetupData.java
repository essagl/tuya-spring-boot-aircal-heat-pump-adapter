package de.essagl.tuya.aircal.adapter.web.mvc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
@NoArgsConstructor
public class SetupData {
    private String connectorAccessId;
    private String connectorSecret;
    private String heatPumpDeviceId;
    private String indoorThermometerDeviceId;
    private String connectorRegion = "EU";
    private String heatPumpControlPanelVersion="202";
    private String heatingLogicMode="OFF";
    private int heatingLogicRunningRate=60000;
    private double indoorDefaultSetTemperature=20.0;
    private double heatingFlowTemperature=40;
    private double standbyFlowTemperature=22;
    private String ipAddress;
    private String hint;
    public boolean isEmpty(){
        return StringUtils.isNotBlank(connectorAccessId)
                || StringUtils.isNotBlank(connectorSecret)
                || StringUtils.isNotBlank(heatPumpDeviceId)
                || StringUtils.isNotBlank(indoorThermometerDeviceId)
                || StringUtils.isNotBlank(connectorRegion);
    }
}
