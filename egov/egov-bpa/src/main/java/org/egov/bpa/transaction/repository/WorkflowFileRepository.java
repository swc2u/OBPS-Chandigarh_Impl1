package org.egov.bpa.transaction.repository;

import java.util.List;

import org.egov.bpa.transaction.entity.common.WorkflowFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowFileRepository extends JpaRepository<WorkflowFile, Long>{
	List<WorkflowFile> findByStateRefId(String stateRefId);
}
