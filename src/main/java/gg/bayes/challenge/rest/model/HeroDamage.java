package gg.bayes.challenge.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class HeroDamage {
    String target;
    @JsonProperty("damage_instances")
    Integer damageInstances;
    @JsonProperty("total_damage")
    Integer totalDamage;
}
