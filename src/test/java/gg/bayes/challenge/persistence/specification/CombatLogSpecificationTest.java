package gg.bayes.challenge.persistence.specification;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.specification.model.ParametersRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class CombatLogSpecificationTest {

    @InjectMocks
    private CombatLogSpecification specification;

    @Test
    public void specificationReturnNullWhenParameterRequestNull() {
        final Specification<CombatLogEntryEntity> predicate = specification.startMethod(null);

        assertNull(predicate);
    }

    @Test
    public void specificationWithAllRequestParameters() {
        final ParametersRequest request = new ParametersRequest(1L, "bloodseeker", CombatLogEntryEntity.Type.ITEM_PURCHASED);
        final Specification<CombatLogEntryEntity> predicate = specification.startMethod(request);

        assertNotNull(predicate);
    }

    @Test
    public void specificationWithTypeInRequestParameters() {
        final ParametersRequest request = new ParametersRequest(null, null, CombatLogEntryEntity.Type.ITEM_PURCHASED);
        final Specification<CombatLogEntryEntity> predicate = specification.startMethod(request);

        assertNotNull(predicate);
    }
}
