package gg.bayes.challenge.persistence.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MatchEntityTest {

    @Test
    public void createObjectWithEmptyConstructor() {
        final MatchEntity match = new MatchEntity();

        assertNotNull(match);
        assertNull(match.getId());
        assertNotNull(match.getCombatLogEntries());
        assertTrue(match.getCombatLogEntries().isEmpty());
    }

    @Test
    public void fillObjectWithSetters() {
        final MatchEntity match = new MatchEntity();
        match.setId(1L);

        final CombatLogEntryEntity combatLogEntry = new CombatLogEntryEntity();
        combatLogEntry.setId(1L);
        combatLogEntry.setMatch(match);
        combatLogEntry.setTimestamp(987654L);
        combatLogEntry.setType(CombatLogEntryEntity.Type.DAMAGE_DONE);
        combatLogEntry.setActor("abyssal_underlord");
        combatLogEntry.setTarget("bloodseeker");
        combatLogEntry.setAbility("abyssal_underlord_firestorm");
        combatLogEntry.setAbilityLevel(2);
        combatLogEntry.setItem("gold_gloves");
        combatLogEntry.setDamage(140);

        final Set<CombatLogEntryEntity> combatLogEntrySet = new HashSet<>();
        combatLogEntrySet.add(combatLogEntry);

        match.setCombatLogEntries(combatLogEntrySet);

        assertNotNull(match);
        assertEquals(1L, match.getId());
        assertNotNull(match.getCombatLogEntries());
        assertFalse(match.getCombatLogEntries().isEmpty());
        assertEquals(combatLogEntrySet, match.getCombatLogEntries());
    }
}