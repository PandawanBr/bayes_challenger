package gg.bayes.challenge.rest.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HeroSpellsTest {

    @Test
    public void createObjectConstructor() {
        final HeroSpells heroSpells = new HeroSpells("abyssal_underlord_firestorm", 20);
        assertNotNull(heroSpells);
        assertEquals("abyssal_underlord_firestorm", heroSpells.getSpell());
        assertEquals(20, heroSpells.getCasts());
    }

    @Test
    public void validEqualsAndHashCode() {
        final HeroSpells heroSpellsA = new HeroSpells("abyssal_underlord_firestorm", 15);
        final HeroSpells heroSpellsB = new HeroSpells("abyssal_underlord_firestorm", 15);
        final boolean equalsResultTrue = heroSpellsA.equals(heroSpellsB);
        assertTrue(equalsResultTrue);

        final HeroSpells heroSpellsC = new HeroSpells("abyssal_underlord_firestorm", 15);
        final HeroSpells heroSpellsD = new HeroSpells("bloodseeker_bloodrage", 7);
        final boolean equalsResultFalse = heroSpellsC.equals(heroSpellsD);
        assertFalse(equalsResultFalse);
    }

    @Test
    public void objectToString() {
        final HeroSpells heroSpells = new HeroSpells("bloodseeker_bloodrage", 21);
        final String toString = heroSpells.toString();
        assertEquals("HeroSpells(spell=bloodseeker_bloodrage, casts=21)", toString);
    }
}
