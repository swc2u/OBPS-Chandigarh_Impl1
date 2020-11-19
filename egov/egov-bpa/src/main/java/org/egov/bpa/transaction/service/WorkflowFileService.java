package org.egov.bpa.transaction.service;

import java.util.List;
import java.util.stream.Collectors;

import org.egov.bpa.transaction.entity.common.WorkflowFile;
import org.egov.bpa.transaction.repository.WorkflowFileRepository;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkflowFileService {
	
	private final WorkflowFileRepository workflowFileRepository;
	
	@Autowired
	public WorkflowFileService(final WorkflowFileRepository workflowFileRepository) {
		this.workflowFileRepository = workflowFileRepository;
	}
	
	public List<WorkflowFile> findAll(){
		return workflowFileRepository.findAll();	
	}
	
	public List<WorkflowFile> findByStateRefId(String stateRefId){
    	return workflowFileRepository.findByStateRefId(stateRefId);
    }
	
	public List<FileStoreMapper> getFileStoreMappers(String stateRefId){
		try {
			List<WorkflowFile> workflowFiles = workflowFileRepository.findByStateRefId(stateRefId);
			return workflowFiles.stream().map(wfFile ->wfFile.getFileStoreMapper()).collect(Collectors.toList());
		}catch (Exception e) {}
		
		return null;
    }
	
	@Transactional
    public WorkflowFile save(final WorkflowFile workflowFile) {    	
        return workflowFileRepository.save(workflowFile);
    }	
}
