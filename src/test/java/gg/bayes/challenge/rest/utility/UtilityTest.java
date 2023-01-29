package gg.bayes.challenge.rest.utility;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static gg.bayes.challenge.rest.utility.Utility.*;
import static org.junit.jupiter.api.Assertions.*;

public class UtilityTest {

    @Test
    public void callCleaningCombatLogWithAValidSimpleLog() {
        final String simpleLog = "[00:00:04.999] game state is now 2 [00:00:04.999] game state is now 9 [00:07:41.042] game state is now 3 [00:08:41.061] game state is now 8 [00:08:41.061] game state is now 10 [00:08:41.094] game state is now 4 [00:08:43.460] npc_dota_hero_pangolier casts ability pangolier_swashbuckle (lvl 1) on dota_unknown [00:08:46.693] npc_dota_hero_snapfire buys item item_clarity [00:08:46.759] npc_dota_hero_dragon_knight buys item item_quelling_blade [00:08:46.992] npc_dota_hero_dragon_knight buys item item_gauntlets [00:08:47.426] npc_dota_hero_dragon_knight buys item item_gauntlets [00:08:48.059] npc_dota_hero_dragon_knight buys item item_circlet [00:08:48.159] npc_dota_hero_puck buys item item_tango [00:08:49.758] npc_dota_hero_puck buys item item_sobi_mask [00:08:49.792] npc_dota_hero_puck buys item item_ring_of_basilius [00:08:49.792] npc_dota_hero_puck buys item item_recipe_ring_of_basilius [00:08:50.458] npc_dota_hero_puck buys item item_branches [00:10:41.998] npc_dota_hero_abyssal_underlord casts ability abyssal_underlord_firestorm (lvl 1) on dota_unknown [00:10:42.031] npc_dota_hero_bane hits npc_dota_hero_abyssal_underlord with dota_unknown for 51 damage (740->689) [00:10:42.031] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 18 damage (720->702) [00:10:42.031] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 5 damage (702->697) [00:10:43.064] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 5 damage (698->693) [00:10:43.064] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 18 damage (693->675) [00:10:43.631] npc_dota_hero_bane hits npc_dota_hero_abyssal_underlord with dota_unknown for 48 damage (694->646) [00:10:44.064] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 5 damage (677->672) [00:10:45.064] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 5 damage (674->669) [00:10:45.130] npc_dota_hero_death_prophet buys item item_circlet [00:10:45.130] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 18 damage (669->651) [00:10:45.130] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 5 damage (651->646) [00:10:45.830] npc_dota_hero_bane hits npc_dota_hero_abyssal_underlord with dota_unknown for 47 damage (626->579) [00:10:45.930] npc_dota_hero_bloodseeker casts ability bloodseeker_bloodrage (lvl 1) on npc_dota_hero_bloodseeker";
        final List<String> clearLog = cleaningCombatLog(simpleLog);
        assertFalse(clearLog.isEmpty());
        assertEquals(25, clearLog.size());
    }

    @Test
    public void callCleaningCombatLogWithADontValidSimpleLog() {
        final String simpleLog = "[00:00:04.999] game state is now 2 [00:00:04.999] game state is now 9 [00:07:41.042] game state is now 3 [00:08:41.061] game state is now 8 [00:08:41.061] game state is now 10";
        final List<String> clearLog = cleaningCombatLog(simpleLog);
        assertTrue(clearLog.isEmpty());
    }

    @Test
    public void callCleaningCombatLogWithANullLog() {
        final List<String> clearLog = cleaningCombatLog(null);
        assertTrue(clearLog.isEmpty());
    }

    @Test
    public void callCleaningCombatLogWithAEmptyLog() {
        final List<String> clearLog = cleaningCombatLog("");
        assertTrue(clearLog.isEmpty());
    }

    @Test
    public void callSplitLogStringBySpaceWithAValidSimpleLog() {
        final String simpleLog = "[00:00:04.999] game state is now 2 [00:00:04.999] game state is now 9 [00:07:41.042] game state is now 3 [00:08:41.061] game state is now 8 [00:08:41.061] game state is now 10 [00:08:41.094] game state is now 4 [00:08:43.460] npc_dota_hero_pangolier casts ability pangolier_swashbuckle (lvl 1) on dota_unknown [00:08:46.693] npc_dota_hero_snapfire buys item item_clarity [00:08:46.759] npc_dota_hero_dragon_knight buys item item_quelling_blade [00:08:46.992] npc_dota_hero_dragon_knight buys item item_gauntlets [00:08:47.426] npc_dota_hero_dragon_knight buys item item_gauntlets [00:08:48.059] npc_dota_hero_dragon_knight buys item item_circlet [00:08:48.159] npc_dota_hero_puck buys item item_tango [00:08:49.758] npc_dota_hero_puck buys item item_sobi_mask [00:08:49.792] npc_dota_hero_puck buys item item_ring_of_basilius [00:08:49.792] npc_dota_hero_puck buys item item_recipe_ring_of_basilius [00:08:50.458] npc_dota_hero_puck buys item item_branches [00:10:41.998] npc_dota_hero_abyssal_underlord casts ability abyssal_underlord_firestorm (lvl 1) on dota_unknown [00:10:42.031] npc_dota_hero_bane hits npc_dota_hero_abyssal_underlord with dota_unknown for 51 damage (740->689) [00:10:42.031] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 18 damage (720->702) [00:10:42.031] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 5 damage (702->697) [00:10:43.064] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 5 damage (698->693) [00:10:43.064] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 18 damage (693->675) [00:10:43.631] npc_dota_hero_bane hits npc_dota_hero_abyssal_underlord with dota_unknown for 48 damage (694->646) [00:10:44.064] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 5 damage (677->672) [00:10:45.064] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 5 damage (674->669) [00:10:45.130] npc_dota_hero_death_prophet buys item item_circlet [00:10:45.130] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 18 damage (669->651) [00:10:45.130] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 5 damage (651->646) [00:10:45.830] npc_dota_hero_bane hits npc_dota_hero_abyssal_underlord with dota_unknown for 47 damage (626->579) [00:10:45.930] npc_dota_hero_bloodseeker casts ability bloodseeker_bloodrage (lvl 1) on npc_dota_hero_bloodseeker";
        final List<String> splitLog = splitLogStringBySpace(simpleLog);
        assertFalse(splitLog.isEmpty());
    }

    @Test
    public void callSplitLogStringBySpaceWithNullLog() {
        final List<String> splitLog = splitLogStringBySpace(null);
        assertTrue(splitLog.isEmpty());
    }

    @Test
    public void callSplitLogStringBySpaceWithEmptyLog() {
        final List<String> splitLog = splitLogStringBySpace("");
        assertTrue(splitLog.isEmpty());
    }

    @Test
    public void callGetLongTimeStampWithAValidTimeLog() {
        final String simpleLog = "[01:15:04.999]";
        final Long timeMilli = getLongTimeStamp(simpleLog);
        assertNotNull(timeMilli);
        assertEquals(4504999L, timeMilli);
    }

    @Test
    public void callGetLongTimeStampWithEmptyBrackets() {
        final String simpleLog = "[]";
        final Long timeMilli = getLongTimeStamp(simpleLog);
        assertNull(timeMilli);
    }

    @Test
    public void callGetLongTimeStampWithEmptyTimeLog() {
        final String simpleLog = "";
        final Long timeMilli = getLongTimeStamp(simpleLog);
        assertNull(timeMilli);
    }

    @Test
    public void callGetLongTimeStampWithNullTimeLog() {
        final Long timeMilli = getLongTimeStamp(null);
        assertNull(timeMilli);
    }

    @Test
    public void callGetLongTimeStampWithInvalidTimeLog() {
        final String simpleLog = "[only a invalid timelog]";
        final Long timeMilli = getLongTimeStamp(simpleLog);
        assertNull(timeMilli);
    }

    @Test
    public void callGetHeroNameWithValidHeroName() {
        final String heroNameLog = "npc_dota_hero_abyssal_underlord";
        final String heroName = getHeroName(heroNameLog);
        assertNotNull(heroName);
        assertEquals("abyssal_underlord", heroName);
    }

    @Test
    public void callGetHeroNameWithInvalidHeroName() {
        final String heroNameLog = "npc_dota_dark_goblin_lord";
        final String heroName = getHeroName(heroNameLog);
        assertNull(heroName);
    }

    @Test
    public void callGetHeroNameWithEmptyHeroName() {
        final String heroNameLog = "";
        final String heroName = getHeroName(heroNameLog);
        assertNull(heroName);
    }

    @Test
    public void callGetHeroNameWithNullHeroName() {
        final String heroName = getHeroName(null);
        assertNull(heroName);
    }

    @Test
    public void callGetItemNameWithValidItemName() {
        final String itemNameLog = "item_scalibur_sword";
        final String itemName = getItemName(itemNameLog);
        assertNotNull(itemName);
        assertEquals("scalibur_sword", itemName);
    }

    @Test
    public void callGetItemNameWithInvalidItemName() {
        final String itemNameLog = "scalibur_sword";
        final String itemName = getItemName(itemNameLog);
        assertNull(itemName);
    }

    @Test
    public void callGetItemNameWithEmptyItemName() {
        final String itemNameLog = "";
        final String itemName = getItemName(itemNameLog);
        assertNull(itemName);
    }

    @Test
    public void callGetItemNameWithNullItemName() {
        final String itemName = getItemName(null);
        assertNull(itemName);
    }

    @Test
    public void callGetAbilityNameWithValidAbilityNameLikeSimpleLog() {
        final String abilityNameLog = "5)";
        final Integer abilityName = getAbilityLevel(abilityNameLog);
        assertNotNull(abilityName);
        assertEquals(5, abilityName);
    }

    @Test
    public void callGetAbilityNameWithValidAbilityName() {
        final String abilityNameLog = "4";
        final Integer abilityName = getAbilityLevel(abilityNameLog);
        assertNotNull(abilityName);
        assertEquals(4, abilityName);
    }

    @Test
    public void callGetAbilityNameWithEmptyAbilityName() {
        final String abilityNameLog = "";
        final Integer abilityName = getAbilityLevel(abilityNameLog);
        assertNull(abilityName);
    }

    @Test
    public void callGetAbilityNameWithNullAbilityName() {
        final Integer abilityName = getAbilityLevel(null);
        assertNull(abilityName);
    }

    @Test
    public void callGetUsedItemOrAbilityToHitWithValidAbilityName() {
        final CombatLogEntryEntity combatLogEntry = new CombatLogEntryEntity();
        getUsedItemOrAbilityToHit("abyssal_underlord", "abyssal_underlord_firestorm", combatLogEntry);
        assertNotNull(combatLogEntry);
        assertEquals("abyssal_underlord_firestorm", combatLogEntry.getAbility());
        assertNull(combatLogEntry.getItem());
    }

    @Test
    public void callGetUsedItemOrAbilityToHitWithValidItemName() {
        final CombatLogEntryEntity combatLogEntry = new CombatLogEntryEntity();
        getUsedItemOrAbilityToHit("abyssal_underlord", "item_scalibur_sword", combatLogEntry);
        assertNotNull(combatLogEntry);
        assertEquals("scalibur_sword", combatLogEntry.getItem());
        assertNull(combatLogEntry.getAbility());
    }

    @Test
    public void callGetUsedItemOrAbilityToHitWithEmptyItemOrAbilityName() {
        final CombatLogEntryEntity combatLogEntry = new CombatLogEntryEntity();
        getUsedItemOrAbilityToHit("abyssal_underlord", "", combatLogEntry);
        assertNotNull(combatLogEntry);
        assertNull(combatLogEntry.getItem());
        assertNull(combatLogEntry.getAbility());
    }

    @Test
    public void callGetUsedItemOrAbilityToHitWithNullItemOrAbilityName() {
        final CombatLogEntryEntity combatLogEntry = new CombatLogEntryEntity();
        getUsedItemOrAbilityToHit("abyssal_underlord", null, combatLogEntry);
        assertNotNull(combatLogEntry);
        assertNull(combatLogEntry.getItem());
        assertNull(combatLogEntry.getAbility());
    }

    @Test
    public void callGetUsedItemOrAbilityToHitWithInvalidItemName() {
        final CombatLogEntryEntity combatLogEntry = new CombatLogEntryEntity();
        getUsedItemOrAbilityToHit("abyssal_underlord", "scalibur_sword", combatLogEntry);
        assertNotNull(combatLogEntry);
        assertNull(combatLogEntry.getItem());
        assertNull(combatLogEntry.getAbility());
    }

    @Test
    public void callGetUsedItemOrAbilityToHitWithNullCombatLogEntryEntityObject() {
        final CombatLogEntryEntity combatLogEntry = null;
        getUsedItemOrAbilityToHit("abyssal_underlord", "item_scalibur_sword", combatLogEntry);
        assertNull(combatLogEntry);
    }

    @Test
    public void callGetUsedItemOrAbilityToHitWithEmptyHeroName() {
        final CombatLogEntryEntity combatLogEntry = new CombatLogEntryEntity();
        getUsedItemOrAbilityToHit("", "abyssal_underlord_firestorm", combatLogEntry);
        assertNotNull(combatLogEntry);
        assertNull(combatLogEntry.getAbility());
        assertNull(combatLogEntry.getItem());
    }

    @Test
    public void callGetUsedItemOrAbilityToHitWithNullHeroName() {
        final CombatLogEntryEntity combatLogEntry = new CombatLogEntryEntity();
        getUsedItemOrAbilityToHit(null, "abyssal_underlord_firestorm", combatLogEntry);
        assertNotNull(combatLogEntry);
        assertNull(combatLogEntry.getAbility());
        assertNull(combatLogEntry.getItem());
    }

    @Test
    public void callGetUsedItemOrAbilityToHitWithDifferentAbilityAndHeroName() {
        final CombatLogEntryEntity combatLogEntry = new CombatLogEntryEntity();
        getUsedItemOrAbilityToHit("dark_knight", "abyssal_underlord_firestorm", combatLogEntry);
        assertNotNull(combatLogEntry);
        assertNull(combatLogEntry.getAbility());
        assertNull(combatLogEntry.getItem());
    }
}
