package de.essagl.tuya.aircal.adapter.service.operationalControl;

import de.essagl.tuya.aircal.adapter.ability.model.BooleanLabelValueUnit;
import de.essagl.tuya.aircal.adapter.ability.model.DoubleLabelValueUnit;
import de.essagl.tuya.aircal.adapter.ability.model.StringLabelValueUnit;
import de.essagl.tuya.aircal.adapter.service.DeviceService;
import de.essagl.tuya.aircal.adapter.service.HeatPumpService;
import de.essagl.tuya.aircal.adapter.service.IndoorThermometerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.text.ParseException;

import static org.mockito.Mockito.*;

public class HeatingLogicServiceTest {

    @Mock
    private DeviceService deviceService;

    @Mock
    private IndoorThermometerService thermometerService;

    @Mock
    private HeatPumpService heatPumpService;

    private HeatingLogicService heatingLogicService;

    @BeforeEach
    public void setup() throws ParseException {
        MockitoAnnotations.openMocks(this);
        heatingLogicService = new HeatingLogicService(
                heatPumpService,
                thermometerService,
                18.0,
                HeatingLogicService.Mode.ON,
                50.0,
                20.0, "22:00", "06:00", "OFF"
        );
    }

    @Test
    public void testAdjustHeatingWaterTemperatureForVersion201() {
        when(heatPumpService.getControlPanelVersion()).thenReturn("201");


        // Mock deviceService
        BooleanLabelValueUnit switchValue = new BooleanLabelValueUnit("switch",true,"");
        when(heatPumpService.getSwitch()).thenReturn(switchValue);

        StringLabelValueUnit modeValue = new StringLabelValueUnit("mode","heat","");
        when(heatPumpService.getMode()).thenReturn(modeValue);

        DoubleLabelValueUnit flowTempValue = new DoubleLabelValueUnit("temp_set",45.0,"");
        //when(deviceService.getDoubleLabelValueForKey(eq("temp_set"),anyString())).thenReturn(flowTempValue);
        when(heatPumpService.getHeatingWaterFlowTemp()).thenReturn(flowTempValue);

        when(heatPumpService.getWorkingModeValue("workingModeHeating")).thenReturn("heat");
        when(heatPumpService.getWorkingModeValue("workingModeHotWaterAndHeating")).thenReturn("water_heat");
        when(heatPumpService.getWorkingModeKey("heat")).thenReturn("workingModeHeating");


        //when(deviceService.getDoubleValueForKey(eq("va_temperature"), anyString())).thenReturn(15.0 * 10d);
        DoubleLabelValueUnit indoorTemp = new DoubleLabelValueUnit("temperature",15.0,"");
        when(thermometerService.getTemperature()).thenReturn(indoorTemp);

        // Test with indoorTemp < indoorSetTemperature - 1d
        heatingLogicService.computeAndSetTemperature();

 //       verify(heatPumpService).setMode(eq("workingModeHotWaterAndHeating"));
        verify(heatPumpService).setMode(eq("workingModeHeating"));
        verify(heatPumpService).setHeatingWaterFlowTemp(eq(50));


        // Reset the mocks for the next test
        resetMocks();

        when(heatPumpService.getControlPanelVersion()).thenReturn("201");


        // Mock deviceService

        when(heatPumpService.getSwitch()).thenReturn(switchValue);
        when(heatPumpService.getMode()).thenReturn(modeValue);

        flowTempValue = new DoubleLabelValueUnit("temp_set",20.0,"");
        when(heatPumpService.getHeatingWaterFlowTemp()).thenReturn(flowTempValue);

        when(heatPumpService.getWorkingModeValue("workingModeHeating")).thenReturn("heat");
        when(heatPumpService.getWorkingModeValue("workingModeHotWaterAndHeating")).thenReturn("water_heat");
        when(heatPumpService.getWorkingModeKey("heat")).thenReturn("workingModeHeating");

        // Test with indoorTemp >= indoorSetTemperature
        DoubleLabelValueUnit indoorTemp1 = new DoubleLabelValueUnit("temperature",22.0,"");
        when(thermometerService.getTemperature()).thenReturn(indoorTemp1);

        heatingLogicService.computeAndSetTemperature();

        // Verify setWaterFlowTemp was called with standbyFlowTemperature value
 //       verify(heatPumpService).setMode(eq("workingModeHotWaterAndHeating"));
        verify(heatPumpService).setMode(eq("workingModeHeating"));
        verify(heatPumpService).setHeatingWaterFlowTemp(eq(20));
    }


    @Test
    public void testAdjustHeatingWaterTemperatureForVersion202() {
        when(heatPumpService.getControlPanelVersion()).thenReturn("202");


        // Mock deviceService
        BooleanLabelValueUnit switchValue = new BooleanLabelValueUnit("switch",true,"");
        when(heatPumpService.getSwitch()).thenReturn(switchValue);

        StringLabelValueUnit modeValue = new StringLabelValueUnit("mode","heat","");
        when(heatPumpService.getMode()).thenReturn(modeValue);

        DoubleLabelValueUnit flowTempValue = new DoubleLabelValueUnit("temp_set",45.0,"");
        //when(deviceService.getDoubleLabelValueForKey(eq("temp_set"),anyString())).thenReturn(flowTempValue);
        when(heatPumpService.getHeatingWaterFlowTemp()).thenReturn(flowTempValue);

        when(heatPumpService.getWorkingModeValue("workingModeHeating")).thenReturn("heat");
        when(heatPumpService.getWorkingModeValue("workingModeHotWaterAndHeating")).thenReturn("water_heat");
        when(heatPumpService.getWorkingModeKey("heat")).thenReturn("workingModeHeating");


        DoubleLabelValueUnit indoorTemp = new DoubleLabelValueUnit("temperature",15.0,"");
        when(thermometerService.getTemperature()).thenReturn(indoorTemp);


        // Test with indoorTemp < indoorSetTemperature - 1d
        heatingLogicService.computeAndSetTemperature();

        verify(heatPumpService,times(0)).setMode(eq("workingModeHotWaterAndHeating"));
        verify(heatPumpService,times(0)).setMode(eq("workingModeHeating"));
        verify(heatPumpService).setHeatingWaterFlowTemp(eq(50));


        // Reset the mocks for the next test
        resetMocks();

        when(heatPumpService.getControlPanelVersion()).thenReturn("202");


        // Mock deviceService

        when(heatPumpService.getSwitch()).thenReturn(switchValue);
        when(heatPumpService.getMode()).thenReturn(modeValue);

        flowTempValue = new DoubleLabelValueUnit("temp_set",20.0,"");
        when(heatPumpService.getHeatingWaterFlowTemp()).thenReturn(flowTempValue);

        when(heatPumpService.getWorkingModeValue("workingModeHeating")).thenReturn("heat");
        when(heatPumpService.getWorkingModeValue("workingModeHotWaterAndHeating")).thenReturn("water_heat");
        when(heatPumpService.getWorkingModeKey("heat")).thenReturn("workingModeHeating");

        // Test with indoorTemp >= indoorSetTemperature
        DoubleLabelValueUnit indoorTemp1 = new DoubleLabelValueUnit("temperature",22.0,"");
        when(thermometerService.getTemperature()).thenReturn(indoorTemp1);

        heatingLogicService.computeAndSetTemperature();

        // Verify setWaterFlowTemp was called with standbyFlowTemperature value
        verify(heatPumpService,times(0)).setMode(eq("workingModeHotWaterAndHeating"));
        verify(heatPumpService,times(0)).setMode(eq("workingModeHeating"));
        verify(heatPumpService).setHeatingWaterFlowTemp(eq(20));
    }
    private void resetMocks() {
        reset(deviceService,heatPumpService);
    }
}

