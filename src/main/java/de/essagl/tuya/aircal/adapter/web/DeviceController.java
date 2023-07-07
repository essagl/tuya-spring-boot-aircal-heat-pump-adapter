package de.essagl.tuya.aircal.adapter.web;

import com.tuya.connector.open.ability.device.model.response.DeviceStatuses;
import de.essagl.tuya.aircal.adapter.ability.model.Device;
import de.essagl.tuya.aircal.adapter.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p> TODO
 *
 * @author qiufeng.yu@tuya.com
 * @since 2021/4/1 10:14 下午
 */
@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;


    @GetMapping("/{device_id}")
    public Device getById(@PathVariable("device_id") String deviceId) {
        return deviceService.getById(deviceId);
    }

    @GetMapping("/{device_id}/status")
    public List<DeviceStatuses.DeviceStatus> getDeviceStatusList(@PathVariable("device_id") String deviceId) {
        return deviceService.getDeviceStatusById(deviceId);
    }



/*    @PostMapping("/{device_id}")
    public Boolean updateName(@PathVariable("device_id") String deviceId, @RequestBody DeviceModifyRequest request) {
        return deviceService.updateName(deviceId, request);
    }*/

    @PostMapping("/{device_id}/{command}/{value}")
    public Boolean commands(@PathVariable("device_id") String deviceId,@PathVariable("command") String command,@PathVariable("value") String value) {
        return deviceService.sendCommand(deviceId, command, value);
    }

}
