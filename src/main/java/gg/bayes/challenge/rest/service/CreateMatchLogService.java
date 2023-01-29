package gg.bayes.challenge.rest.service;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.MatchEntity;
import gg.bayes.challenge.persistence.repository.CombatLogEntryRepository;
import gg.bayes.challenge.persistence.repository.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static gg.bayes.challenge.rest.enums.PrefixNamesEnum.PREFIX_HERO_NAME;
import static gg.bayes.challenge.rest.utility.Utility.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class CreateMatchLogService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private CombatLogEntryRepository combatLogEntryRepository;

    public Long registerLogs(final String combatLog) {
        final List<String> combatLogList = cleaningCombatLog(combatLog);
        final MatchEntity match = createAndSaveMatch();
        createCombatMatch(combatLogList, match);
        return match.getId();
    }

    private MatchEntity createAndSaveMatch() {
        final MatchEntity match = new MatchEntity();
        return matchRepository.save(match);
    }

    private void createCombatMatch(final List<String> combatLogList, final MatchEntity match) {
        final List<CombatLogEntryEntity> combatLogEntryEntityList = new ArrayList<>();
        for (String log : combatLogList) {
            final CombatLogEntryEntity combatLogEntry = createCombatLog(log, match);
            if (nonNull(combatLogEntry)) combatLogEntryEntityList.add(combatLogEntry);
        }

        if (!combatLogEntryEntityList.isEmpty()) combatLogEntryRepository.saveAll(combatLogEntryEntityList);
    }

    private CombatLogEntryEntity createCombatLog(final String combatLog, final MatchEntity match) {
        if (combatLog.contains("buys item")) {
            return createItemPurchasedCombatLog(combatLog, newCombatLogWithType(CombatLogEntryEntity.Type.ITEM_PURCHASED, match));
        } else if (combatLog.contains("killed by")) {
            return createHeroKilledCombatLog(combatLog, newCombatLogWithType(CombatLogEntryEntity.Type.HERO_KILLED, match));
        } else if (combatLog.contains("casts ability")) {
            return createSpellCastCombatLog(combatLog, newCombatLogWithType(CombatLogEntryEntity.Type.SPELL_CAST, match));
        } else if (combatLog.contains("hits")) {
            return createDamageDoneCombatLog(combatLog, newCombatLogWithType(CombatLogEntryEntity.Type.DAMAGE_DONE, match));
        }
        return null;
    }

    private CombatLogEntryEntity newCombatLogWithType(final CombatLogEntryEntity.Type type, final MatchEntity match) {
        final CombatLogEntryEntity combatLogEntry = new CombatLogEntryEntity();
        combatLogEntry.setMatch(match);
        combatLogEntry.setType(type);
        return combatLogEntry;
    }

    private CombatLogEntryEntity createItemPurchasedCombatLog(final String combatLog, final CombatLogEntryEntity combatLogEntry) {
        final List<String> splitString = splitLogStringBySpace(combatLog);
        if (splitString.isEmpty()) return null;

        final Long timeMilli = getLongTimeStamp(splitString.get(0));
        final String heroName = splitString.get(1);
        final String itemName = splitString.get(4);

        if (isNull(timeMilli)) return null;

        combatLogEntry.setTimestamp(timeMilli);
        combatLogEntry.setActor(heroName);
        combatLogEntry.setItem(itemName);

        return combatLogEntry;
    }

    private CombatLogEntryEntity createHeroKilledCombatLog(final String combatLog, final CombatLogEntryEntity combatLogEntry) {
        final List<String> splitString = splitLogStringBySpace(combatLog);
        if (splitString.isEmpty()) return null;

        final Long timeMilli = getLongTimeStamp(splitString.get(0));
        final String heroTargetName = splitString.get(1);
        final String heroActorName = splitString.get(5);

        if (isNull(timeMilli) || !heroActorName.contains(PREFIX_HERO_NAME.getPrefixName()) ||
                !heroTargetName.contains(PREFIX_HERO_NAME.getPrefixName()))
            return null;

        combatLogEntry.setTimestamp(timeMilli);
        combatLogEntry.setActor(heroActorName);
        combatLogEntry.setTarget(heroTargetName);

        return combatLogEntry;
    }

    private CombatLogEntryEntity createSpellCastCombatLog(final String combatLog, final CombatLogEntryEntity combatLogEntry) {
        final List<String> splitString = splitLogStringBySpace(combatLog);
        if (splitString.isEmpty()) return null;

        final Long timeMilli = getLongTimeStamp(splitString.get(0));
        final String heroActorName = splitString.get(1);
        final String abilityName = splitString.get(4);
        final Integer abilityLevel = getAbilityLevel(splitString.get(6));
        final String heroTargetName = splitString.get(8);

        if (isNull(timeMilli) || !heroActorName.contains(PREFIX_HERO_NAME.getPrefixName()) ||
                !heroTargetName.contains(PREFIX_HERO_NAME.getPrefixName()))
            return null;

        combatLogEntry.setTimestamp(timeMilli);
        combatLogEntry.setActor(heroActorName);
        combatLogEntry.setAbility(abilityName);
        combatLogEntry.setAbilityLevel(abilityLevel);
        combatLogEntry.setTarget(heroTargetName);

        return combatLogEntry;
    }

    private CombatLogEntryEntity createDamageDoneCombatLog(final String combatLog, final CombatLogEntryEntity combatLogEntry) {
        final List<String> splitString = splitLogStringBySpace(combatLog);
        if (splitString.isEmpty()) return null;

        final Long timeMilli = getLongTimeStamp(splitString.get(0));
        final String heroActorName = splitString.get(1);
        final String heroTargetName = splitString.get(3);
        final Integer damage = Integer.parseInt(splitString.get(7));

        if (isNull(timeMilli) || !heroActorName.contains(PREFIX_HERO_NAME.getPrefixName()) ||
                !heroTargetName.contains(PREFIX_HERO_NAME.getPrefixName()))
            return null;

        getUsedItemOrAbilityToHit(heroActorName, splitString.get(5), combatLogEntry);

        combatLogEntry.setTimestamp(timeMilli);
        combatLogEntry.setActor(heroActorName);
        combatLogEntry.setTarget(heroTargetName);
        combatLogEntry.setDamage(damage);

        return combatLogEntry;
    }
}
