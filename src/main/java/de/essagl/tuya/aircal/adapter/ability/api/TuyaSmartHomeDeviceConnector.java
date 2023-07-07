package de.essagl.tuya.aircal.adapter.ability.api;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.GET;
import com.tuya.connector.api.annotations.POST;
import com.tuya.connector.api.annotations.Path;
import com.tuya.connector.open.ability.device.model.request.DeviceCommandRequest;
import com.tuya.connector.open.ability.device.model.response.DeviceStatuses;
import de.essagl.tuya.aircal.adapter.ability.model.DeviceInformation;
import de.essagl.tuya.aircal.adapter.ability.model.Device;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p> call to the tuya smart home device system - device management api endpoints
 *
 * @author @author qiufeng.yu@tuya.com
 * @since 2021/4/1 10:10 下午
 */
@Controller
public interface
TuyaSmartHomeDeviceConnector {
    /**
     * read the information of a device identified by device id
     * @param deviceId the device id
     * @return all available device parameters
     */
   @GET("/v1.0/iot-03/devices/{device_id}/specification")
   Device getById(@Path("device_id") String deviceId);


   @GET("/v1.1/iot-03/devices/{device_id}")
   DeviceInformation getDeviceInformationById(@Path("device_id") String deviceId);

    @GET("/v1.1/iot-03/devices/{device_id}")
    Object getDeviceInformationAsObjectById(@Path("device_id") String deviceId);

    /**
     * select device status by deviceId
     *
     * @param deviceId
     * @return
     */
    @GET("/v1.0/iot-03/devices/{device_id}/status")
    List<DeviceStatuses.DeviceStatus> selectDeviceStatus(@Path("device_id") String deviceId);

    /**
     * Post commands to the device
     * @param deviceId  the device id
     * @param request DeviceCommandRequest
     * @return true or false
     */
    @POST("/v1.0/iot-03/devices/{device_id}/commands")
    Boolean commandDevice(@Path("device_id") String deviceId, @Body DeviceCommandRequest request);
    //Boolean commands(@Path("device_id") String deviceId, @Body Map<String, Object> commands);



}
