package org.egov.commons.repository;

import java.util.List;

import org.egov.common.entity.bpa.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface BpaChecklistRepository extends JpaRepository<Checklist, Long>{
	
	List<Checklist> findByChecklistTypeCode(String code);

}
