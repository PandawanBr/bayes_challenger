package gg.bayes.challenge.persistence.specification.model;

import org.junit.jupiter.api.Test;

import static gg.bayes.challenge.persistence.model.CombatLogEntryEntity.Type.HERO_KILLED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParametersRequestTest {

    @Test
    public void createObjectConstructor() {
        final ParametersRequest request = new ParametersRequest(1L, "bloodseeker", HERO_KILLED);

        assertNotNull(request);
        assertEquals(1L, request.getMatchId());
        assertEquals("bloodseeker", request.getHeroName());
        assertEquals(HERO_KILLED, request.getType());
    }
}
