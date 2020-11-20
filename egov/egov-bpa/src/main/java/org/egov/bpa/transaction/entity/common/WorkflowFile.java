package org.egov.bpa.transaction.entity.common;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "eg_wf_file")
@SequenceGenerator(name = WorkflowFile.SEQ_EG_WF_FILES, sequenceName = WorkflowFile.SEQ_EG_WF_FILES, allocationSize = 1)
public class WorkflowFile extends AbstractAuditable {
	
	private static final long serialVersionUID = 2469269874250739395L;
	public static final String SEQ_EG_WF_FILES = "seq_eg_wf_files";
	
    @Id
    @GeneratedValue(generator = SEQ_EG_WF_FILES, strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @NotNull
    private String stateRefId;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "filestore", nullable = false)
    private FileStoreMapper fileStoreMapper;
    
    private transient MultipartFile[] files;
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
    public FileStoreMapper getFileStoreMapper() {
        return fileStoreMapper;
    }

    public void setFileStoreMapper(FileStoreMapper fileStoreMapper) {
        this.fileStoreMapper = fileStoreMapper;
    }

	public String getStateRefId() {
		return stateRefId;
	}

	public void setStateRefId(String stateRefId) {
		this.stateRefId = stateRefId;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
}
