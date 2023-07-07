package de.essagl.tuya.aircal.adapter.service;

import de.essagl.tuya.aircal.adapter.ability.model.*;

import java.util.Arrays;
import java.util.List;


public interface HeatPumpService  {

   List<String> heatPumpModes = Arrays.asList("workingModeHotWater","workingModeHeating","workingModeCooling","workingModeHotWaterAndHeating","workingModeHotWaterAndCooling");

   /**
    *
    * @return Device class
    */
   Device getDeviceSpecification();
   /**
    * Switch working ON (true) or OFF (false)
    *
    * @return true = operation succeeded, false = operation failed
    */
   Boolean setSwitch(boolean value);

   /**
    * Select the working mode for the heat pump
    * for V2.01 water,heat,cool,water_heat,water_cool
    * for V2.02 Hot,Heating,Hot_Heating,Cooling,Hot_Cooling
    * @param value the value
    * @return true = operation succeeded, false = operation failed
    */
   Boolean setMode(String value);

   /**
    * Set the temperature of the heating water flow temperature in ℃. min:7,max:75,step:1
    * @param value the temperature value
    * @return true = operation succeeded, false = operation failed
    */
   Boolean setHeatingWaterFlowTemp(int value);

   Boolean setServiceWaterFlowTemp(int value);

   /**
    * Get the device information.
    * @return DeviceInformation
    */
   DeviceInformation getDeviceInformation();

   /**
    * Get the information if the pump is active or inactive
    * @return BooleanLabelValueUnit
    */
   BooleanLabelValueUnit getSwitch();

   /**
    * Get the value for the heating water flow temperature
    * @return DoubleLabelValueUnit
    */
   DoubleLabelValueUnit getHeatingWaterFlowTemp();

   /**
    * Get the value for the custom and drinking water flow temperature
    * @return DoubleLabelValueUnit
    */
   DoubleLabelValueUnit getServiceWaterFlowTemp();

   /**
    * Get the actual value of the custom and drinking water measured temperature
    * @return DoubleLabelValueUnit
    */
   DoubleLabelValueUnit getServiceWaterTemp();

   /**
    * Get the active working mode
    * @return StringLabelValueUnit
    */
   StringLabelValueUnit getMode();

   /**
    * Get the actual outside environment temperature by the heat pump
    * @return DoubleLabelValueUnit
    */
   DoubleLabelValueUnit getOutsideTemp();

   String getWorkingModeValue(String key);

   String getWorkingModeKey(String value);

   String getControlPanelVersion();

}
