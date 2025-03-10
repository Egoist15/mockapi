package com.mockapi.repository;

import com.mockapi.entity.EligibilityRequestEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EligibilityRequestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<EligibilityRequestEntity> findByRequestId(String requestId) {
        try {
            EligibilityRequestEntity entity = entityManager.createQuery(
                    "SELECT e FROM EligibilityRequestEntity e WHERE e.requestId = :requestId",
                    EligibilityRequestEntity.class
            ).setParameter("requestId", requestId).getSingleResult();
            return Optional.of(entity);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public void save(EligibilityRequestEntity entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void updateEligibility(String requestId, boolean eligible, String errorCode) {
        entityManager.createQuery(
                        "UPDATE EligibilityRequestEntity e SET e.eligible = :eligible, e.errorCode = :errorCode WHERE e.requestId = :requestId"
                )
                .setParameter("eligible", eligible)
                .setParameter("errorCode", errorCode)
                .setParameter("requestId", requestId)
                .executeUpdate();
    }
}
