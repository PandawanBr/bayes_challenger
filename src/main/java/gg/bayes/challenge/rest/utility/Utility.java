package gg.bayes.challenge.rest.utility;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import lombok.extern.log4j.Log4j2;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static gg.bayes.challenge.rest.enums.PrefixNamesEnum.*;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Log4j2
public class Utility {

    public static List<String> cleaningCombatLog(final String combatLog) {
        if (isBlank(combatLog)) return new ArrayList<>();
        final List<String> logList = new LinkedList<>(Arrays.asList(combatLog.replace("[", "--[").split("--")));
        if (logList.isEmpty()) return new ArrayList<>();
        logList.remove(0);
        return logList.stream().filter(log -> !log.contains("game state") &&
                !log.contains(PREFIX_DARK_NAME.getPrefixName()) &&
                !log.contains(PREFIX_NEUTRAL_HERO_NAME.getPrefixName()) &&
                !log.contains(PREFIX_OBSERVER_HERO_NAME.getPrefixName()) &&
                !log.contains(PREFIX_GOODGUYS_HERO_NAME.getPrefixName()) &&
                !log.contains(PREFIX_BADGUYS_HERO_NAME.getPrefixName()) &&
                log.contains(PREFIX_HERO_NAME.getPrefixName())).toList();
    }

    public static List<String> splitLogStringBySpace(final String log) {
        if (isBlank(log)) return new ArrayList<>();
        return new LinkedList<>(Arrays.asList(log.split("\s")));
    }

    public static Long getLongTimeStamp(final String timeLog) {
        if (isBlank(timeLog)) return null;
        try {
            final String timeString = timeLog.substring(1, (timeLog.length() - 1));
            if (isBlank(timeString)) return null;
            final PeriodFormatter periodFormat = new PeriodFormatterBuilder()
                    .appendHours()
                    .appendSeparator(":")
                    .appendMinutes()
                    .appendSeparator(":")
                    .appendSeconds()
                    .appendSeparator(".")
                    .appendMillis3Digit()
                    .toFormatter();
            final Period result = Period.parse(timeString, periodFormat);
            return result.toStandardDuration().getMillis();
        } catch (Exception e) {
            log.error("Error during time conversion to milliseconds");
            return null;
        }
    }

    public static String getHeroName(final String heroNameLog) {
        if (isBlank(heroNameLog) || !heroNameLog.contains(PREFIX_HERO_NAME.getPrefixName())) return null;
        return heroNameLog.substring(PREFIX_HERO_NAME.getPrefixName().length());
    }

    public static String getItemName(final String itemNameLog) {
        if (isBlank(itemNameLog) || !itemNameLog.contains(PREFIX_ITEM_NAME.getPrefixName())) return null;
        return itemNameLog.substring(PREFIX_ITEM_NAME.getPrefixName().length());
    }

    public static Integer getAbilityLevel(final String abilityLevel) {
        if (isBlank(abilityLevel)) return null;
        final String level = abilityLevel.replace(")", "").trim();
        if (isBlank(level)) return null;
        return Integer.parseInt(level);
    }

    public static void getUsedItemOrAbilityToHit(final String heroName, final String itemOrAbilityName, final CombatLogEntryEntity combatLogEntry) {
        if (!isBlank(itemOrAbilityName) && nonNull(combatLogEntry)) {
            if (!isBlank(heroName) && itemOrAbilityName.contains(heroName)) {
                combatLogEntry.setAbility(itemOrAbilityName);
            } else if (itemOrAbilityName.contains(PREFIX_ITEM_NAME.getPrefixName())) {
                combatLogEntry.setItem(getItemName(itemOrAbilityName));
            }
        }
    }
}
