package de.essagl.tuya.aircal.adapter.ability.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoubleLabelValueUnit {
    private String key;
    private String name;
    private Double value;
    private String unit;

    public DoubleLabelValueUnit(String key, Double value, String unit) {
        this.key = key;
        this.value = value;
        this.unit = unit;
    }
}
