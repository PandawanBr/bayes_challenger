package gg.bayes.challenge.rest.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HeroDamageTest {

    @Test
    public void createObjectConstructor() {
        final HeroDamage heroDamage = new HeroDamage("abyssal_lord", 7, 1300);
        assertNotNull(heroDamage);
        assertEquals("abyssal_lord", heroDamage.getTarget());
        assertEquals(7, heroDamage.getDamageInstances());
        assertEquals(1300, heroDamage.getTotalDamage());
    }

    @Test
    public void validEqualsAndHashCode() {
        final HeroDamage heroDamageA = new HeroDamage("abyssal_lord", 7, 1300);
        final HeroDamage heroDamageB = new HeroDamage("abyssal_lord", 7, 1300);
        final boolean equalsResultTrue = heroDamageA.equals(heroDamageB);
        assertTrue(equalsResultTrue);

        final HeroDamage heroDamageC = new HeroDamage("abyssal_lord", 7, 1300);
        final HeroDamage heroDamageD = new HeroDamage("dark_knight", 6, 1100);
        final boolean equalsResultFalse = heroDamageC.equals(heroDamageD);
        assertFalse(equalsResultFalse);
    }

    @Test
    public void objectToString() {
        final HeroDamage heroDamage = new HeroDamage("abyssal_lord", 7, 1300);
        final String toString = heroDamage.toString();
        assertEquals("HeroDamage(target=abyssal_lord, damageInstances=7, totalDamage=1300)", toString);
    }
}
