package gg.bayes.challenge.rest.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HeroItemTest {

    @Test
    public void createObjectConstructor() {
        final HeroItem heroItem = new HeroItem("scalibur_sword", 44530L);
        assertNotNull(heroItem);
        assertEquals("scalibur_sword", heroItem.getItem());
        assertEquals(44530L, heroItem.getTimestamp());
    }

    @Test
    public void validEqualsAndHashCode() {
        final HeroItem heroItemA = new HeroItem("scalibur_sword", 44530L);
        final HeroItem heroItemB = new HeroItem("scalibur_sword", 44530L);
        final boolean equalsResultTrue = heroItemA.equals(heroItemB);
        assertTrue(equalsResultTrue);

        final HeroItem heroItemC = new HeroItem("scalibur_sword", 44530L);
        final HeroItem heroItemD = new HeroItem("quelling_blade", 22710L);
        final boolean equalsResultFalse = heroItemC.equals(heroItemD);
        assertFalse(equalsResultFalse);
    }

    @Test
    public void objectToString() {
        final HeroItem heroItem = new HeroItem("scalibur_sword", 44530L);
        final String toString = heroItem.toString();
        assertEquals("HeroItem(item=scalibur_sword, timestamp=44530)", toString);
    }
}
