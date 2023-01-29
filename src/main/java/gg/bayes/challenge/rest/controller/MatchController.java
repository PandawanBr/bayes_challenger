package gg.bayes.challenge.rest.controller;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.specification.model.ParametersRequest;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.rest.service.CreateMatchLogService;
import gg.bayes.challenge.rest.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/match")
@Validated
public class MatchController {

    @Autowired
    private CreateMatchLogService matchLogService;

    @Autowired
    private SearchService searchService;

    /**
     * Ingests a DOTA combat log file, parses and persists relevant events data. All events are associated with the same
     * match id.
     *
     * @param combatLog the content of the combat log file
     * @return the match id associated with the parsed events
     */
    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Long> ingestCombatLog(@RequestBody @NotBlank String combatLog) {
        final Long response = matchLogService.registerLogs(combatLog);
        return ResponseEntity.ok(response);
    }

    /**
     * Fetches the heroes and their kill counts for the given match.
     *
     * @param matchId the match identifier
     * @return a collection of heroes and their kill counts
     */
    @GetMapping(
            path = "{matchId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<HeroKills>> getMatch(@PathVariable("matchId") Long matchId) {
        final ParametersRequest parametersRequest = new ParametersRequest(matchId, null, CombatLogEntryEntity.Type.HERO_KILLED);
        final List<HeroKills> response = searchService.getMatchHeroKills(parametersRequest);
        if (response.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(response);
    }

    /**
     * For the given match, fetches the items bought by the named hero.
     *
     * @param matchId  the match identifier
     * @param heroName the hero name
     * @return a collection of items bought by the hero during the match
     */
    @GetMapping(
            path = "{matchId}/{heroName}/items",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<HeroItem>> getHeroItems(
            @PathVariable("matchId") Long matchId,
            @PathVariable("heroName") String heroName) {
        final ParametersRequest parametersRequest = new ParametersRequest(matchId, heroName, CombatLogEntryEntity.Type.ITEM_PURCHASED);
        final List<HeroItem> response = searchService.getMatchHeroItems(parametersRequest);
        if (response.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(response);
    }

    /**
     * For the given match, fetches the spells cast by the named hero.
     *
     * @param matchId  the match identifier
     * @param heroName the hero name
     * @return a collection of spells cast by the hero and how many times they were cast
     */
    @GetMapping(
            path = "{matchId}/{heroName}/spells",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<HeroSpells>> getHeroSpells(
            @PathVariable("matchId") Long matchId,
            @PathVariable("heroName") String heroName) {
        final ParametersRequest parametersRequest = new ParametersRequest(matchId, heroName, CombatLogEntryEntity.Type.SPELL_CAST);
        final List<HeroSpells> response = searchService.getMatchHeroSpells(parametersRequest);
        if (response.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(response);
    }

    /**
     * For a given match, fetches damage done data for the named hero.
     *
     * @param matchId  the match identifier
     * @param heroName the hero name
     * @return a collection of "damage done" (target, number of times and total damage) elements
     */
    @GetMapping(
            path = "{matchId}/{heroName}/damage",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<HeroDamage>> getHeroDamages(
            @PathVariable("matchId") Long matchId,
            @PathVariable("heroName") String heroName) {
        final ParametersRequest parametersRequest = new ParametersRequest(matchId, heroName, CombatLogEntryEntity.Type.DAMAGE_DONE);
        final List<HeroDamage> response = searchService.getMatchHeroDamage(parametersRequest);
        if (response.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(response);
    }
}
