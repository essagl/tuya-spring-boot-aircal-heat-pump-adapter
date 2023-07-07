package de.essagl.tuya.aircal.adapter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuya.connector.open.ability.device.model.request.DeviceCommandRequest;
import com.tuya.connector.open.ability.device.model.response.DeviceStatuses;
import de.essagl.tuya.aircal.adapter.ability.api.TuyaSmartHomeDeviceConnector;
import de.essagl.tuya.aircal.adapter.ability.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * @author qiufeng.yu@tuya.com
 * @since 2021/4/1 10:13 下午
 */
@Service
public class DeviceService {
    private final TuyaSmartHomeDeviceConnector connector;
    private DeviceInformation deviceInformation;
    private ObjectMapper mapper = new ObjectMapper();

    private Map<String, Device> deviceMap = new HashMap<>();

    public DeviceService(TuyaSmartHomeDeviceConnector connector) {
        this.connector = connector;
    }

    public Device getById(String deviceId) {
        if (!deviceMap.containsKey(deviceId)){
            deviceMap.put(deviceId,connector.getById(deviceId));
        }
        return deviceMap.get(deviceId);
    }

    public DeviceInformation getDeviceInformationById(String deviceId) {
        if (this.deviceInformation == null) {
            this.deviceInformation = connector.getDeviceInformationById(deviceId);
        }
        return deviceInformation;
    }
    public List<DeviceStatuses.DeviceStatus> getDeviceStatusById(String deviceId) {
        return connector.selectDeviceStatus(deviceId);
    }

/*
    public Boolean updateName(String deviceId, DeviceModifyRequest request) {
        return connector.update(deviceId, request.);
    }*/

    public Boolean sendCommand(String deviceId, String code, Object value) {
        DeviceCommandRequest request = new DeviceCommandRequest();
        DeviceCommandRequest.Command command = new DeviceCommandRequest.Command();
        command.setCode(code);
        command.setValue(value);
        List<DeviceCommandRequest.Command> commandList = new ArrayList<>();
        commandList.add(command);
        request.setCommands(commandList);
        return connector.commandDevice(deviceId, request);
    }



    public Double getDoubleValueForKey(String statusKey, String deviceId) {
        return getDoubleValueForKey(statusKey, getDeviceStatusById(deviceId));
    }

    public Double getDoubleValueForKey(String statusKey, List<DeviceStatuses.DeviceStatus> statusList) {
        for(DeviceStatuses.DeviceStatus status : statusList){
            if (status.getCode().equals(statusKey)){
                return (Double)status.getValue();
            }
        }
        return null;
    }

    public String getStringValueForKey(String statusKey, String deviceId) {
        return getStringValueForKey(statusKey, getDeviceStatusById(deviceId));
    }
    public String getStringValueForKey(String statusKey,  List<DeviceStatuses.DeviceStatus> statusList) {
        for(DeviceStatuses.DeviceStatus status : statusList){
            if (status.getCode().equals(statusKey)){
                return (String)status.getValue();
            }
        }
        return null;
    }

    public Boolean getBooleanValueForKey(String statusKey,String deviceId) {
        return getBooleanValueForKey(statusKey, getDeviceStatusById(deviceId));
    }

    public Boolean getBooleanValueForKey(String statusKey,  List<DeviceStatuses.DeviceStatus> statusList) {
        for(DeviceStatuses.DeviceStatus status : statusList){
            if (status.getCode().equals(statusKey)){
                return (Boolean)status.getValue();
            }
        }
        return null;
    }
    public DoubleLabelValueUnit getDoubleLabelValueForKey(String statusKey, String deviceId) {
        DoubleLabelValueUnit doubleLabelValueUnit = new DoubleLabelValueUnit();
        doubleLabelValueUnit.setKey(statusKey);
        doubleLabelValueUnit.setValue(getDoubleValueForKey(statusKey, getDeviceStatusById(deviceId)));
        List<Map<String,Object>> statusList  = getById(deviceId).getStatus();
        for (Map<String,Object> statusMap : statusList){
            Collection<Object> values = statusMap.values();
            Iterator<Object> vi = values.iterator();
            int i = 0;
            boolean readValue = false;
            String type = null;
            String value = null;
            while (vi.hasNext()){
                Object res = vi.next();
                if (i == 0 && res.equals(statusKey) ){
                    System.out.println(statusKey);
                    readValue = true;
                }
                if (i == 2 && readValue){
                    type = (String)res;
                    if (!type.equalsIgnoreCase("Integer") && !type.equalsIgnoreCase("Bitmap")){
                        throw new RuntimeException("Type for double value must be of type Integer or Bitmap, but type was "+type);
                    }
                }
                if (i == 3 && readValue){
                    value = (String)res;
                }
                i++;
                if (i == 4 && readValue){
                    try {
                        Map<String,Object> map = mapper.readValue(value, Map.class);
                        if (type.equalsIgnoreCase("Integer")) {
                            doubleLabelValueUnit.setUnit(map.get("unit").toString());
                        }
                        if (type.equalsIgnoreCase("Bitmap")) {
                            doubleLabelValueUnit.setUnit(map.get("label").toString());
                        }

                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return doubleLabelValueUnit;
    }

    public StringLabelValueUnit getStringLabelValueForKey(String statusKey, String deviceId) {
        StringLabelValueUnit stringLabelValueUnit = new StringLabelValueUnit();
        stringLabelValueUnit.setKey(statusKey);
        stringLabelValueUnit.setValue(getStringValueForKey(statusKey, getDeviceStatusById(deviceId)));
        List<Map<String,Object>> statusList  = getById(deviceId).getStatus();
        for (Map<String,Object> statusMap : statusList){
            Collection<Object> values = statusMap.values();
            Iterator<Object> vi = values.iterator();
            int i = 0;
            boolean readValue = false;
            String type = null;
            String value = null;
            while (vi.hasNext()){
                Object res = vi.next();
                if (i == 0 && res.equals(statusKey) ){
                    System.out.println(statusKey);
                    readValue = true;
                }
                if (i == 2 && readValue){
                    type = (String)res;
                }
                if (i == 3 && readValue){
                    value = (String)res;
                }
                i++;
                if (i == 4 && readValue){
                    try {
                        Map<String,Object> map = mapper.readValue(value, Map.class);
                        if (type.equalsIgnoreCase("Enum")) {
                            stringLabelValueUnit.setUnit((String)map.get("range").toString());
                        }
                        if (type.equalsIgnoreCase("Bitmap")) {
                            stringLabelValueUnit.setUnit((String)map.get("range").toString());
                        }

                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return stringLabelValueUnit;
    }

    public BooleanLabelValueUnit getBooleanLabelValueForKey(String statusKey, String deviceId) {
        BooleanLabelValueUnit booleanLabelValueUnit = new BooleanLabelValueUnit();
        booleanLabelValueUnit.setKey(statusKey);
        booleanLabelValueUnit.setValue(getBooleanValueForKey(statusKey, getDeviceStatusById(deviceId)));
        booleanLabelValueUnit.setUnit("");
        return booleanLabelValueUnit;
    }

}
