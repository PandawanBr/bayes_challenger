package gg.bayes.challenge.persistence.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CombatLogEntryEntityTest {

    @Test
    public void createObjectWithEmptyConstructor() {
        final CombatLogEntryEntity combatLogEntry = new CombatLogEntryEntity();

        assertNotNull(combatLogEntry);
        assertNull(combatLogEntry.getId());
        assertNull(combatLogEntry.getMatch());
        assertNull(combatLogEntry.getTimestamp());
        assertNull(combatLogEntry.getType());
        assertNull(combatLogEntry.getActor());
        assertNull(combatLogEntry.getTarget());
        assertNull(combatLogEntry.getAbility());
        assertNull(combatLogEntry.getAbilityLevel());
        assertNull(combatLogEntry.getItem());
        assertNull(combatLogEntry.getDamage());
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

        assertNotNull(combatLogEntry);
        assertEquals(1L, combatLogEntry.getId());
        assertEquals(match, combatLogEntry.getMatch());
        assertEquals(987654L, combatLogEntry.getTimestamp());
        assertEquals(CombatLogEntryEntity.Type.DAMAGE_DONE, combatLogEntry.getType());
        assertEquals("abyssal_underlord", combatLogEntry.getActor());
        assertEquals("bloodseeker", combatLogEntry.getTarget());
        assertEquals("abyssal_underlord_firestorm", combatLogEntry.getAbility());
        assertEquals(2, combatLogEntry.getAbilityLevel());
        assertEquals("gold_gloves", combatLogEntry.getItem());
        assertEquals(140, combatLogEntry.getDamage());
    }
}
