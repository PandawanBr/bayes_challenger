package gg.bayes.challenge.persistence.repository;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CombatLogEntryRepository extends JpaRepository<CombatLogEntryEntity, Long>, JpaSpecificationExecutor<CombatLogEntryEntity> {

    @Query(value = "SELECT TARGET, COUNT(TARGET) AS HIT_TIMES, SUM(DAMAGE) AS TOTAL_DAMAGE FROM DOTA_COMBAT_LOG WHERE MATCH_ID = :matchId AND ACTOR LIKE %:heroName% AND ENTRY_TYPE = 'DAMAGE_DONE' GROUP BY TARGET ORDER BY HIT_TIMES DESC", nativeQuery = true)
    List<Map<String, Object>> getHeroDamageInMatch(@Param("matchId") final Long matchId, @Param("heroName") final String heroName);
}
