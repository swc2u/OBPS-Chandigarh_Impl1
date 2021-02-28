package org.egov.bpa.transaction.repository;

import org.egov.bpa.transaction.entity.NocEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NocEvaluationRepository extends JpaRepository<NocEvaluation, Long>{

}
