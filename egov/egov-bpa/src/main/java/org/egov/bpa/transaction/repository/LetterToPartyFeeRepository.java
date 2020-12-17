package org.egov.bpa.transaction.repository;

import java.util.List;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.LetterToPartyFee;
import org.egov.bpa.transaction.entity.PermitLetterToParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterToPartyFeeRepository extends JpaRepository<LetterToPartyFee, Long>{
	
	List<LetterToPartyFee> findByApplicationOrderByIdDesc(BpaApplication bpaApplication);
	
	List<LetterToPartyFee> findByPermitLetterToPartyOrderByIdDesc(PermitLetterToParty permitLetterToParty);
}
