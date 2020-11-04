package org.egov.commons.repository;

import java.util.List;

import org.egov.common.entity.bpa.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "CommanChecklistRepository")
public interface CommanChecklistRepository extends JpaRepository<Checklist, Long>{
	
	//@Query("select c from Checklist c where c.checklistType = (select ct.id from ChecklistType ct where ct.code = :code)")
	List<Checklist> findByChecklistTypeCode(String code);

}
