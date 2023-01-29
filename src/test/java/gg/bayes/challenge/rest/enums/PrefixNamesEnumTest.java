package gg.bayes.challenge.rest.enums;

import org.junit.jupiter.api.Test;

import static gg.bayes.challenge.rest.enums.PrefixNamesEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrefixNamesEnumTest {

    @Test
    public void validEnumValues() {
        assertEquals("npc_dota_hero_", PREFIX_HERO_NAME.getPrefixName());
        assertEquals("npc_dota_dark_", PREFIX_DARK_NAME.getPrefixName());
        assertEquals("npc_dota_neutral_", PREFIX_NEUTRAL_HERO_NAME.getPrefixName());
        assertEquals("npc_dota_observer_", PREFIX_OBSERVER_HERO_NAME.getPrefixName());
        assertEquals("npc_dota_goodguys_", PREFIX_GOODGUYS_HERO_NAME.getPrefixName());
        assertEquals("npc_dota_badguys_", PREFIX_BADGUYS_HERO_NAME.getPrefixName());
        assertEquals("item_", PREFIX_ITEM_NAME.getPrefixName());
    }
}
