package org.egov.bpa.transaction.repository.pl;

import java.util.List;

import org.egov.bpa.transaction.entity.pl.PLInspection;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PLInspectionRepository extends JpaRepository<PLInspection, Long>{
	List<PLInspection> findByPlOrderByIdDesc(PlinthLevelCertificate pl);

    List<PLInspection> findByIdOrderByIdAsc(Long id);

    PLInspection findByPl_ApplicationNumberAndInspection_InspectionNumber(String applicationNo, String inspectionNo);
}
