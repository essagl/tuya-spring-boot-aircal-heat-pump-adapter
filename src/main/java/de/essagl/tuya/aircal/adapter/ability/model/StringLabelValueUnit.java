package de.essagl.tuya.aircal.adapter.ability.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StringLabelValueUnit {
    private String key;
    private String name;
    private String value;
    private String unit;

    public StringLabelValueUnit(String key, String value, String unit) {
        this.key = key;
        this.value = value;
        this.unit = unit;
    }
}
