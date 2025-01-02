# AirCal king heat pump adapter 
This service is tested with King Heat DC Inverter Heat Pump R290 monobloc control panel
version 2.01 and version 2.02.
![panel.jpg](documentation%2Fimages%2Fpanel.jpg)

See [www.aircalheatpump.com](https://www.aircalheatpump.com/product/detail/king-heat-dc-inverter-app-wifi-controller-heat-pump-r32-r290-monobloc-intelligent.html).

Should also be compatible with branded OEM heat pumps like the ones from [OEG](https://www.oeg.net/de/multifunktionale-leistungsgesteuerte-luft-wasser-waermepumpen?icp=tts)
in Germany.

**Note**: To enable the internal room thermometer of the heat pump, the system parameter P67 must be set to 1. In this case no external room thermometer is needed, but the 
heating water flow temperature is controlled by the heat pump's internal room thermometer and therefore the heating logic does not work and must be disabled.
See application.properties how to use an external thermometer instead of the internal one(for example this one ![thermometer](documentation%2Fimages%2Fthermometer.jpg)). 
Before the service can be used it is required that your heat pump and the thermometer is registered with the official Tuya Smart app 
and a developer account is created for using the Tuya cloud services. 
## Building from Source 
This project requires Java Version 1.8 and the Maven build tool.
The configuration file application.properties must contain the valid authorization key values
Access ID/Client ID (connector.ak) and Access Secret/Client Secret (connector.sk) of your created Tuya could project and the Version number of the heat pump's control panel
(stored in control parameter C56). 
To enable access to the devices the device id of the heat pump and the thermometer must be set in the application.properties file. (see screeshot)

**Note**: Even the Tuya's cloud messaging service is not needed, the service must be activated for the first 
run so that the partitions are created and linked. After this, the service can be disabled again. (otherwise you will get "Failed to authenticate" errors)

## Running as a Container Stack
The complete setup can be run as a docker stack. The docker-compose file is located in the compose directory of the project.
You need a docker installation on the machine that runs the heatpump adapter app to run the stack.

After the services are started, the setup page can be accessed at the [http://localhost:8080](http://localhost:8080).
Grafana Frontend ath Port 3000. 
A default dashboard configuration is provided in the grafana directory which can be used after the influxdb datasource is configured
to read data from the influxdb database.


## Linking a Tuya device

This method requires you to create a developer account on [iot.tuya.com](https://iot.tuya.com). It doesn't matter if the device(s) are currently registered in the Tuya Smart app or Smart Life app or not.

1. Create a new account on [iot.tuya.com](https://iot.tuya.com) and make sure you are logged in. **Select Europe as your country when signing up.** This seems to skip a [required verify step](https://github.com/codetheweb/tuyapi/issues/425).
2. Go to Cloud -> Development in the left nav drawer. If you haven't already, you will need to "purchase" the Trial Plan before you can proceed with this step. You will not have to add any form of payment, and the purchase is of no charge. Once in the Projects tab, click "Create". **Make sure you select "Smart Home" for both the "Industry" field and  the development method.** Select your country of use in the for the location access option, and feel free to skip the services option in the next window. After you've created a new project, click into it. The "Access ID/Client ID" and "Access Secret/Client Secret" are the API Key and API Secret values need in step 7.
3. Go to Cloud -> Development -> "MyProject" -> Service API -> "Go to authorize". "Select API" > click subscribe on "IoT Core", "Authorization", and "Smart Home Scene Linkage" in the dropdown. Click subscribe again on every service (also check your PopUp blocker). Click "basic edition" and "buy now" (basic edition is free). Check if the 3 services are listed under Cloud -> Projects -> "MyProject" -> API. If not, click "Add Authorization" and select them.
4. Go to App -> App SDK -> Development in the nav drawer. Click "Create" and enter whatever you want for the package names and Channel ID (for the Android package name, you must enter a string beginning with `com.`). Take note of the **Channel ID** you entered. This is equivalent to the `schema` value needed in step 7. Ignore any app key and app secret values you see in this section as they are not used.
5. Go to Cloud -> Development and click the project you created earlier. Then click "Link Device". Click the "Link devices by Apps" tab, and click "Add Apps". Check the app you just created and click "Ok".
6. Put your devices into linking.  This process is specific to each type of device, find instructions in the Tuya Smart app. Usually this consists of turning it on and off several times or holding down a button.
7. Your devices should link in under a minute and should be shown as under the devices tab of the cloud project you created. ![tuyaCloud.jpg](documentation%2Fimages%2FtuyaCloud.jpg)
8. To access all available information about the device, set the DP instruction mode for the heat pump device. In the default standard mode, only a subset of the needed data is available. 
9. Add the missing parameters in the application.properties file (connector.ak, connector.sk and connector.region (EU) ....).

### Troubleshooting

**`Received error from server: Failed to authenticate`**

This could mean that you did not activate the messaging service in tuya cloud. After the messaging service is activated and the client connection is successful
the service can be disabled again. The generated topics will remain on tuya's messaging servers.
This can also mean, that one of the parameters you're passing in (`api-key`, `api-secret`) is incorrect. Double check the values.

**`Device(s) failed to be registered! Error: Timed out waiting for devices to connect.`**

This can happen for a number of reasons. It means that the device never authenticated against Tuya's API (although it *does not* necessarily mean that the device could not connect to WiFi). Try the following:
- Making sure that your computer is connected to your network via WiFi **only** (unplug ethernet if necessary)
- Making sure that your network is 2.4 Ghz (devices will also connect if you have both 2.4 Ghz and 5 Ghz bands under the same SSID)
- Using a different OS
- Removing special characters from your network's SSID

## Running
The default service UI is running at port 8080.

![index.jpg](documentation%2Fimages%2Findex.jpg)

by selecting the HeatLogic Settings

![heatlogic.jpg](documentation%2Fimages%2Fheatlogic.jpg)

the automatic temperature control for the heating water flow can be enabled and set up. The current (simple)
implementation set the heating water flow temperature to the selected heating flow temperature until the 
target indoor temperature is reached. Then the flow temperature is reduced to the standby flow temperature.

All possible settings can be interactively called and set by using the Open Api UI available 
under the services link.