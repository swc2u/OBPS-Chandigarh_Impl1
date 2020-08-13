package org.egov.bpa.transaction.repository.pl;

import java.util.List;

import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlinthLevelCertificateRepository 
			extends JpaRepository<PlinthLevelCertificate, Long>, JpaSpecificationExecutor<PlinthLevelCertificate> {
	PlinthLevelCertificate findByApplicationNumber(String applicationNumber);
	
	@Query("select plinthLevelCertificate from PlinthLevelCertificate plinthLevelCertificate where plinthLevelCertificate.status in :status order by createddate asc")
	List<PlinthLevelCertificate> findByStatusListOrderByCreatedDateAsc(@Param("status") List<BpaStatus> listOfBpaStatus);
	
	@Query("select plinthLevelCertificate from PlinthLevelCertificate plinthLevelCertificate where plinthLevelCertificate.parent.planPermissionNumber =:permitNumber order by plinthLevelCertificate.createdDate desc")
	List<PlinthLevelCertificate> findByPermitNumber(@Param("permitNumber") String permitNumber);
	
	@Query("select plinthLevelCertificate from PlinthLevelCertificate plinthLevelCertificate where plinthLevelCertificate.parent.applicationNumber =:applicationNumber order by plinthLevelCertificate.createdDate desc")
	List<PlinthLevelCertificate> findByBpaApplicaltionNumber(@Param("applicationNumber") String applicationNumber);
}
