package gg.bayes.challenge.rest.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HeroKillsTest {

    @Test
    public void createObjectConstructor() {
        final HeroKills heroKills = new HeroKills("dark_knight", 15);
        assertNotNull(heroKills);
        assertEquals("dark_knight", heroKills.getHero());
        assertEquals(15, heroKills.getKills());
    }

    @Test
    public void validEqualsAndHashCode() {
        final HeroKills heroKillsA = new HeroKills("dark_knight", 15);
        final HeroKills heroKillsB = new HeroKills("dark_knight", 15);
        final boolean equalsResultTrue = heroKillsA.equals(heroKillsB);
        assertTrue(equalsResultTrue);

        final HeroKills heroKillsC = new HeroKills("dark_knight", 15);
        final HeroKills heroKillsD = new HeroKills("abyssal_underlord", 7);
        final boolean equalsResultFalse = heroKillsC.equals(heroKillsD);
        assertFalse(equalsResultFalse);
    }

    @Test
    public void objectToString() {
        final HeroKills heroKills = new HeroKills("dark_knight", 15);
        final String toString = heroKills.toString();
        assertEquals("HeroKills(hero=dark_knight, kills=15)", toString);
    }
}
