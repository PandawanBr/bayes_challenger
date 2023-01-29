package gg.bayes.challenge.rest.service;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.MatchEntity;
import gg.bayes.challenge.persistence.repository.CombatLogEntryRepository;
import gg.bayes.challenge.persistence.specification.CombatLogSpecification;
import gg.bayes.challenge.persistence.specification.model.ParametersRequest;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private CombatLogEntryRepository combatLogEntryRepository;

    @Mock
    private CombatLogSpecification specification;

    @InjectMocks
    private SearchService searchService;

    @BeforeEach
    void setup() {
        this.searchService = new SearchService(combatLogEntryRepository, specification);
    }

    @Test
    public void callGetMatchHeroKills() {
        final ParametersRequest mockRequest = new ParametersRequest(1L, null, CombatLogEntryEntity.Type.HERO_KILLED);
        final Specification<CombatLogEntryEntity> predicate = specification.startMethod(mockRequest);

        doReturn(getMockResult()).when(combatLogEntryRepository).findAll(predicate);
        final List<HeroKills> response = searchService.getMatchHeroKills(mockRequest);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(2, response.get(0).getKills());
    }

    @Test
    public void callGetMatchHeroKillsWithoutResultInFindAll() {
        final ParametersRequest mockRequest = new ParametersRequest(1L, null, CombatLogEntryEntity.Type.HERO_KILLED);
        final Specification<CombatLogEntryEntity> predicate = specification.startMethod(mockRequest);

        doReturn(List.of()).when(combatLogEntryRepository).findAll(predicate);
        final List<HeroKills> response = searchService.getMatchHeroKills(mockRequest);

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    public void callGetMatchHeroItems() {
        final ParametersRequest mockRequest = new ParametersRequest(1L, null, CombatLogEntryEntity.Type.ITEM_PURCHASED);
        final Specification<CombatLogEntryEntity> predicate = specification.startMethod(mockRequest);

        doReturn(getMockResult()).when(combatLogEntryRepository).findAll(predicate, Sort.by(Sort.Order.asc("timestamp")));
        final List<HeroItem> response = searchService.getMatchHeroItems(mockRequest);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(2, response.size());
        assertEquals("recipe_magic_wand", response.get(0).getItem());
        assertEquals(9000, response.get(0).getTimestamp());
        assertEquals("wind_lace", response.get(1).getItem());
        assertEquals(4500, response.get(1).getTimestamp());
    }

    @Test
    public void callGetMatchHeroItemsWithoutResultInFindAll() {
        final ParametersRequest mockRequest = new ParametersRequest(1L, null, CombatLogEntryEntity.Type.ITEM_PURCHASED);
        final Specification<CombatLogEntryEntity> predicate = specification.startMethod(mockRequest);

        doReturn(List.of()).when(combatLogEntryRepository).findAll(predicate, Sort.by(Sort.Order.asc("timestamp")));
        final List<HeroItem> response = searchService.getMatchHeroItems(mockRequest);

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    public void callGetMatchHeroSpells() {
        final ParametersRequest mockRequest = new ParametersRequest(1L, null, CombatLogEntryEntity.Type.SPELL_CAST);
        final Specification<CombatLogEntryEntity> predicate = specification.startMethod(mockRequest);

        doReturn(getMockResult()).when(combatLogEntryRepository).findAll(predicate);
        final List<HeroSpells> response = searchService.getMatchHeroSpells(mockRequest);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        assertEquals("abyssal_underlord_firestorm", response.get(0).getSpell());
        assertEquals(2, response.get(0).getCasts());
    }

    @Test
    public void callGetMatchHeroSpellsWithoutResultInFindAll() {
        final ParametersRequest mockRequest = new ParametersRequest(1L, null, CombatLogEntryEntity.Type.SPELL_CAST);
        final Specification<CombatLogEntryEntity> predicate = specification.startMethod(mockRequest);

        doReturn(List.of()).when(combatLogEntryRepository).findAll(predicate);
        final List<HeroSpells> response = searchService.getMatchHeroSpells(mockRequest);

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    public void callGetMatchHeroDamage() {
        final ParametersRequest mockRequest = new ParametersRequest(1L, null, CombatLogEntryEntity.Type.SPELL_CAST);

        doReturn(getHeroDamageMock()).when(combatLogEntryRepository).getHeroDamageInMatch(mockRequest.getMatchId(), mockRequest.getHeroName());
        final List<HeroDamage> response = searchService.getMatchHeroDamage(mockRequest);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(2, response.size());
        assertEquals("bloodseeker", response.get(0).getTarget());
        assertEquals(3, response.get(0).getDamageInstances());
        assertEquals(340, response.get(0).getTotalDamage());
        assertEquals("dark_knight", response.get(1).getTarget());
        assertEquals(7, response.get(1).getDamageInstances());
        assertEquals(1130, response.get(1).getTotalDamage());
    }

    @Test
    public void callGetMatchHeroDamageWithoutResultInFindAll() {
        final ParametersRequest mockRequest = new ParametersRequest(1L, null, CombatLogEntryEntity.Type.SPELL_CAST);

        doReturn(List.of()).when(combatLogEntryRepository).getHeroDamageInMatch(mockRequest.getMatchId(), mockRequest.getHeroName());
        final List<HeroDamage> response = searchService.getMatchHeroDamage(mockRequest);

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    public List<CombatLogEntryEntity> getMockResult() {
        final MatchEntity match = new MatchEntity();
        match.setId(1L);

        final CombatLogEntryEntity mock1 = new CombatLogEntryEntity();
        mock1.setMatch(match);
        mock1.setActor("npc_dota_hero_abyssal_underlord");
        mock1.setAbility("abyssal_underlord_firestorm");
        mock1.setItem("item_recipe_magic_wand");
        mock1.setDamage(90);
        mock1.setTimestamp(9000L);
        mock1.setTarget("npc_dota_hero_bloodseeker");
        mock1.setAbilityLevel(2);
        mock1.setType(CombatLogEntryEntity.Type.DAMAGE_DONE);

        final CombatLogEntryEntity mock2 = new CombatLogEntryEntity();
        mock2.setMatch(match);
        mock2.setActor("npc_dota_hero_abyssal_underlord");
        mock2.setAbility("abyssal_underlord_firestorm");
        mock2.setItem("item_wind_lace");
        mock2.setDamage(115);
        mock2.setTimestamp(4500L);
        mock2.setTarget("npc_dota_hero_bloodseeker");
        mock2.setAbilityLevel(3);
        mock2.setType(CombatLogEntryEntity.Type.DAMAGE_DONE);

        return List.of(mock1, mock2);
    }

    private List<Map<String, Object>> getHeroDamageMock() {
        final Map<String, Object> mockReturn1 = new HashMap<>();
        mockReturn1.put("TARGET", "npc_dota_hero_bloodseeker");
        mockReturn1.put("HIT_TIMES", "3");
        mockReturn1.put("TOTAL_DAMAGE", "340");

        final Map<String, Object> mockReturn2 = new HashMap<>();
        mockReturn2.put("TARGET", "npc_dota_hero_dark_knight");
        mockReturn2.put("HIT_TIMES", "7");
        mockReturn2.put("TOTAL_DAMAGE", "1130");

        return List.of(mockReturn1, mockReturn2);
    }
}
