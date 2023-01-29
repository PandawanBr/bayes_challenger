package gg.bayes.challenge.persistence.specification.model;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import lombok.*;

@Getter
@AllArgsConstructor
public class ParametersRequest {

    private Long matchId;
    private String heroName;
    private CombatLogEntryEntity.Type type;
}
