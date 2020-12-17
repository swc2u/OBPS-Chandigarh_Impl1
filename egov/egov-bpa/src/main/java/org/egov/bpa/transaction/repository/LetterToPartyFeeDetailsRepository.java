package org.egov.bpa.transaction.repository;

import java.util.List;

import org.egov.bpa.transaction.entity.LetterToPartyFee;
import org.egov.bpa.transaction.entity.LetterToPartyFeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterToPartyFeeDetailsRepository extends JpaRepository<LetterToPartyFeeDetails, Long>{
	List<LetterToPartyFeeDetails> findByLetterToPartyFeeOrderByIdDesc(LetterToPartyFee letterToPartyFee);
}
