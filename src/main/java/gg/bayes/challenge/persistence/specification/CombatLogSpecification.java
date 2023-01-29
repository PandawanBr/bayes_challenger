package gg.bayes.challenge.persistence.specification;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.specification.model.ParametersRequest;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@NoArgsConstructor
public class CombatLogSpecification {

    private ParametersRequest parametersRequest;

    public Specification<CombatLogEntryEntity> startMethod(ParametersRequest parametersRequest) {
        if (isNull(parametersRequest)) return null;
        this.parametersRequest = parametersRequest;
        return this::toPredicate;
    }

    public Predicate toPredicate(Root<CombatLogEntryEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        final List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(root.get("type").as(CombatLogEntryEntity.Type.class), parametersRequest.getType()));

        if (nonNull(parametersRequest.getMatchId())) {
            predicates.add(builder.equal(root.get("match").get("id"), parametersRequest.getMatchId()));
        }

        if (!isBlank(parametersRequest.getHeroName())) {
            predicates.add(builder.like(root.get("actor").as(String.class), "%" + parametersRequest.getHeroName() + "%"));
        }

        return andTogether(predicates, builder);
    }

    private Predicate andTogether(
            final List<Predicate> predicates,
            final CriteriaBuilder builder) {
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
