package gg.bayes.challenge.rest.service;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.repository.CombatLogEntryRepository;
import gg.bayes.challenge.persistence.specification.CombatLogSpecification;
import gg.bayes.challenge.persistence.specification.model.ParametersRequest;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static gg.bayes.challenge.rest.utility.Utility.getHeroName;
import static gg.bayes.challenge.rest.utility.Utility.getItemName;

@Service
@AllArgsConstructor
public class SearchService {

    @Autowired
    private CombatLogEntryRepository combatLogEntryRepository;

    @Autowired
    private CombatLogSpecification specification;

    public List<HeroKills> getMatchHeroKills(final ParametersRequest parametersRequest) {
        List<CombatLogEntryEntity> combatLogEntryEntityList = combatLogEntryRepository.findAll(specification.startMethod(parametersRequest));
        if (combatLogEntryEntityList.isEmpty()) return new ArrayList<>();

        final Map<String, Long> counts = combatLogEntryEntityList.stream().collect(Collectors.groupingBy(CombatLogEntryEntity::getActor, Collectors.counting()));
        final List<HeroKills> heroKillsList = new ArrayList<>();

        counts.forEach((key, value) -> heroKillsList.add(new HeroKills(getHeroName(key), value.intValue())));
        heroKillsList.sort(Comparator.comparingInt(HeroKills::getKills).reversed());
        return heroKillsList;
    }

    public List<HeroItem> getMatchHeroItems(final ParametersRequest parametersRequest) {
        final List<CombatLogEntryEntity> combatLogEntryEntityList = combatLogEntryRepository
                .findAll(specification.startMethod(parametersRequest), Sort.by(Sort.Order.asc("timestamp")));
        if (combatLogEntryEntityList.isEmpty()) return new ArrayList<>();

        final List<HeroItem> heroItemsList = new ArrayList<>();
        for (CombatLogEntryEntity logEntry : combatLogEntryEntityList) {
            heroItemsList.add(new HeroItem(getItemName(logEntry.getItem()), logEntry.getTimestamp()));
        }
        return heroItemsList;
    }

    public List<HeroSpells> getMatchHeroSpells(final ParametersRequest parametersRequest) {
        final List<CombatLogEntryEntity> combatLogEntryEntityList = combatLogEntryRepository.findAll(specification.startMethod(parametersRequest));
        if (combatLogEntryEntityList.isEmpty()) return new ArrayList<>();

        final Map<String, Long> counts = combatLogEntryEntityList.stream().collect(Collectors.groupingBy(CombatLogEntryEntity::getAbility, Collectors.counting()));
        final List<HeroSpells> heroSpellList = new ArrayList<>();

        counts.forEach((key, value) -> heroSpellList.add(new HeroSpells(key, value.intValue())));
        heroSpellList.sort(Comparator.comparingInt(HeroSpells::getCasts).reversed());
        return heroSpellList;
    }

    public List<HeroDamage> getMatchHeroDamage(final ParametersRequest parametersRequest) {
        final List<Map<String, Object>> spellList = combatLogEntryRepository
                .getHeroDamageInMatch(parametersRequest.getMatchId(), parametersRequest.getHeroName());
        if (spellList.isEmpty()) return new ArrayList<>();

        final List<HeroDamage> heroDamageList = new ArrayList<>();
        for (Map<String, Object> hero : spellList) {
            heroDamageList.add(new HeroDamage(
                    getHeroName(hero.get("TARGET").toString()),
                    Integer.valueOf(hero.get("HIT_TIMES").toString()),
                    Integer.valueOf(hero.get("TOTAL_DAMAGE").toString())));
        }
        return heroDamageList;
    }
}
