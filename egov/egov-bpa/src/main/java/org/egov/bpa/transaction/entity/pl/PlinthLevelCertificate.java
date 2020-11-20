package org.egov.bpa.transaction.entity.pl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.common.WorkflowFile;
import org.egov.commons.entity.Source;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.workflow.entity.StateAware;
import org.egov.pims.commons.Position;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "EGBPA_PLINTH_LEVEL_CERTIFICATE")
@SequenceGenerator(name = PlinthLevelCertificate.SEQ_EGBPA_PLINTH_LEVEL_CERTIFICATE, sequenceName = PlinthLevelCertificate.SEQ_EGBPA_PLINTH_LEVEL_CERTIFICATE, allocationSize = 1)
public class PlinthLevelCertificate extends StateAware<Position> {
	public static final String SEQ_EGBPA_PLINTH_LEVEL_CERTIFICATE = "SEQ_EGBPA_PLINTH_LEVEL_CERTIFICATE";
    public static final String ORDER_BY_ID_ASC = "id ASC";
    private static final long serialVersionUID = 2655013364406241434L;
    
    @Id
    @GeneratedValue(generator = SEQ_EGBPA_PLINTH_LEVEL_CERTIFICATE, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent")
    private BpaApplication parent;

    @SafeHtml
    @Length(min = 1, max = 128)
    private String applicationNumber;
    
    @SafeHtml
    @Length(min = 1, max = 128)
    private String plinthLevelCertificateNumber;
    
    @SafeHtml
    @Length(min = 1, max = 50)
    private String plinthLevelCertificateType;

    @Temporal(value = TemporalType.DATE)
    private Date applicationDate;
    
    @SafeHtml
    @Length(min = 1, max = 128)
    private String applicationType;
    
    @Enumerated(EnumType.STRING)
    private Source source;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "status")
    private BpaStatus status;
    
    @Temporal(value = TemporalType.DATE)
    private Date commencedDate;
    
    private Boolean isConstructionReachedToPL = false;

    private Boolean isConstructionDone = false;

    private Boolean isRescheduledByCitizen = false;

    private Boolean isRescheduledByEmployee = false;
    
    @Length(min = 1, max = 5000)
    private String surveyorRemarks;

    private Boolean isSurveyorInspectionRequire = false;
    
    @Temporal(value = TemporalType.DATE)
    private Date approvalDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "approverPosition")
    private Position approverPosition;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "approverUser")
    private User approverUser;
    
    @OneToMany(mappedBy = "pl", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PLNotice> plNotices = new ArrayList<>(0);
    
    @OneToMany(mappedBy = "pl", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id DESC ")
    private List<PLInspection> inspections = new ArrayList<>();

    @OneToMany(mappedBy = "pl", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(ORDER_BY_ID_ASC)
    private List<PLSlot> plSlots = new ArrayList<>();

    @OneToMany(mappedBy = "pl", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(ORDER_BY_ID_ASC)
    private List<PLAppointmentSchedule> appointmentSchedules = new ArrayList<>();
    
    @OneToMany(mappedBy = "pl", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PLNoticeConditions> rejectionReasons = new ArrayList<>(0);

    @OrderBy(ORDER_BY_ID_ASC)
    @OneToMany(mappedBy = "pl", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PLNoticeConditions> additionalNoticeConditions = new ArrayList<>(0);
        
    private transient String planApplicationNumber;    
    private transient WorkflowBean workflowBean;
    private transient Long approvalDepartment;
    private transient Long zoneId;
    private transient Long wardId;
    private transient String approvalComent;
    private transient List<PLNoticeConditions> rejectionReasonsTemp = new ArrayList<>(0);
    private transient List<PLNoticeConditions> additionalRejectReasonsTemp = new ArrayList<>(0);
    
    private transient WorkflowFile workflowFile = new WorkflowFile();	
    private transient String wfFileRefId;
        
	@Override
	public String getStateDetails() {
		 return String.format("Applicant Name: %s Application Number %s Dated %s For the service type - %s.",
	                parent.getOwner() == null ? "Not Specified" : parent.getOwner().getName(),
	                applicationNumber,
	                applicationDate == null ? DateUtils.toDefaultDateFormat(new Date())
	                        : DateUtils.toDefaultDateFormat(applicationDate),
	                parent.getServiceType().getDescription() == null ? "" : parent.getServiceType().getDescription());
	}

	@Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String myLinkId() {
        return applicationNumber;
    }

    public BpaApplication getParent() {
        return parent;
    }

    public void setParent(BpaApplication parent) {
        this.parent = parent;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

	public String getPlinthLevelCertificateNumber() {
		return plinthLevelCertificateNumber;
	}

	public void setPlinthLevelCertificateNumber(String plinthLevelCertificateNumber) {
		this.plinthLevelCertificateNumber = plinthLevelCertificateNumber;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public BpaStatus getStatus() {
		return status;
	}

	public void setStatus(BpaStatus status) {
		this.status = status;
	}

	public Date getCommencedDate() {
		return commencedDate;
	}

	public void setCommencedDate(Date commencedDate) {
		this.commencedDate = commencedDate;
	}

	public Boolean getIsConstructionReachedToPL() {
		return isConstructionReachedToPL;
	}

	public void setIsConstructionReachedToPL(Boolean isConstructionReachedToPL) {
		this.isConstructionReachedToPL = isConstructionReachedToPL;
	}

	public Boolean getIsConstructionDone() {
		return isConstructionDone;
	}

	public void setIsConstructionDone(Boolean isConstructionDone) {
		this.isConstructionDone = isConstructionDone;
	}

	public Boolean getIsRescheduledByCitizen() {
		return isRescheduledByCitizen;
	}

	public void setIsRescheduledByCitizen(Boolean isRescheduledByCitizen) {
		this.isRescheduledByCitizen = isRescheduledByCitizen;
	}

	public Boolean getIsRescheduledByEmployee() {
		return isRescheduledByEmployee;
	}

	public void setIsRescheduledByEmployee(Boolean isRescheduledByEmployee) {
		this.isRescheduledByEmployee = isRescheduledByEmployee;
	}

	public String getSurveyorRemarks() {
		return surveyorRemarks;
	}

	public void setSurveyorRemarks(String surveyorRemarks) {
		this.surveyorRemarks = surveyorRemarks;
	}

	public Boolean getIsSurveyorInspectionRequire() {
		return isSurveyorInspectionRequire;
	}

	public void setIsSurveyorInspectionRequire(Boolean isSurveyorInspectionRequire) {
		this.isSurveyorInspectionRequire = isSurveyorInspectionRequire;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public Position getApproverPosition() {
		return approverPosition;
	}

	public void setApproverPosition(Position approverPosition) {
		this.approverPosition = approverPosition;
	}

	public User getApproverUser() {
		return approverUser;
	}

	public void setApproverUser(User approverUser) {
		this.approverUser = approverUser;
	}

	public String getPlanApplicationNumber() {
		return planApplicationNumber;
	}

	public void setPlanApplicationNumber(String planApplicationNumber) {
		this.planApplicationNumber = planApplicationNumber;
	}

	public String getPlinthLevelCertificateType() {
		return plinthLevelCertificateType;
	}

	public void setPlinthLevelCertificateType(String plinthLevelCertificateType) {
		this.plinthLevelCertificateType = plinthLevelCertificateType;
	}

	public WorkflowBean getWorkflowBean() {
		return workflowBean;
	}

	public void setWorkflowBean(WorkflowBean workflowBean) {
		this.workflowBean = workflowBean;
	}

	public Long getApprovalDepartment() {
		return approvalDepartment;
	}

	public void setApprovalDepartment(Long approvalDepartment) {
		this.approvalDepartment = approvalDepartment;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public Long getWardId() {
		return wardId;
	}

	public void setWardId(Long wardId) {
		this.wardId = wardId;
	}

	public String getApprovalComent() {
		return approvalComent;
	}

	public void setApprovalComent(String approvalComent) {
		this.approvalComent = approvalComent;
	}
	
	public void addNotice(PLNotice plNotice) {
        this.plNotices.add(plNotice);
    }

    public List<PLNotice> getPlNotices() {
        return plNotices;
    }

    public void setPlNotices(List<PLNotice> plNotices) {
        this.plNotices = plNotices;
    }

	public List<PLInspection> getInspections() {
		return inspections;
	}

	public void setInspections(List<PLInspection> inspections) {
		this.inspections = inspections;
	}

	public List<PLSlot> getPlSlots() {
		return plSlots;
	}

	public void setPlSlots(List<PLSlot> plSlots) {
		this.plSlots = plSlots;
	}

	public List<PLAppointmentSchedule> getAppointmentSchedules() {
		return appointmentSchedules;
	}

	public void setAppointmentSchedules(List<PLAppointmentSchedule> appointmentSchedules) {
		this.appointmentSchedules = appointmentSchedules;
	}

	public List<PLNoticeConditions> getRejectionReasons() {
		return rejectionReasons;
	}

	public void setRejectionReasons(List<PLNoticeConditions> rejectionReasons) {
		this.rejectionReasons = rejectionReasons;
	}

	public List<PLNoticeConditions> getAdditionalNoticeConditions() {
		return additionalNoticeConditions;
	}

	public void setAdditionalNoticeConditions(List<PLNoticeConditions> additionalNoticeConditions) {
		this.additionalNoticeConditions = additionalNoticeConditions;
	}

	public List<PLNoticeConditions> getRejectionReasonsTemp() {
		return rejectionReasonsTemp;
	}

	public void setRejectionReasonsTemp(List<PLNoticeConditions> rejectionReasonsTemp) {
		this.rejectionReasonsTemp = rejectionReasonsTemp;
	}

	public List<PLNoticeConditions> getAdditionalRejectReasonsTemp() {
		return additionalRejectReasonsTemp;
	}

	public void setAdditionalRejectReasonsTemp(List<PLNoticeConditions> additionalRejectReasonsTemp) {
		this.additionalRejectReasonsTemp = additionalRejectReasonsTemp;
	}

	public WorkflowFile getWorkflowFile() {
		return workflowFile;
	}

	public void setWorkflowFile(WorkflowFile workflowFile) {
		this.workflowFile = workflowFile;
	}

	public String getWfFileRefId() {
		return wfFileRefId;
	}

	public void setWfFileRefId(String wfFileRefId) {
		this.wfFileRefId = wfFileRefId;
	}
}
