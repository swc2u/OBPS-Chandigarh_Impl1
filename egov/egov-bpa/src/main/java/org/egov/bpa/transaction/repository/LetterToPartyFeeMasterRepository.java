package org.egov.bpa.transaction.repository;

import java.util.List;

import org.egov.bpa.transaction.entity.LetterToPartyFeeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterToPartyFeeMasterRepository extends JpaRepository<LetterToPartyFeeMaster, Long>{
	List<LetterToPartyFeeMaster> findByIsActiveTrueOrderByIdAsc();
}
