package de.essagl.tuya.aircal.adapter.ability.model;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceInformation {
    Long active_time;
    int biz_type;
    String category;
    Long create_time;
    String icon;
    String id;
    String ip;
    String local_key;
    String lat;
    String lon;
    String model;
    String name;
    Boolean online;
    String owner_id;
    String product_id;
    String product_name;
    Boolean sub;
    String time_zone;
    String uid;
    Long update_time;
    String uuid;
}

