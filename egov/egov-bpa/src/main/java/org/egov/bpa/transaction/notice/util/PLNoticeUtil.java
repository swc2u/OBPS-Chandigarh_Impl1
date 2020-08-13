package org.egov.bpa.transaction.notice.util;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.infra.security.utils.SecureCodeUtils.generatePDF417Code;
import static org.egov.infra.utils.DateUtils.currentDateToDefaultDateFormat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.transaction.entity.PermitCoApplicant;
import org.egov.bpa.transaction.entity.common.NoticeCommon;
import org.egov.bpa.transaction.entity.pl.PLNotice;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.repository.pl.PLNoticeRepository;
import org.egov.bpa.transaction.service.pl.PlinthLevelCertificateService;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.reporting.engine.ReportRequest;
import org.egov.infra.reporting.engine.ReportService;
import org.egov.infra.reporting.util.ReportUtil;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PLNoticeUtil {
	public static final String ONE_NEW_LINE = "\n";
    private static final String N_A = "N/A";
    private static final String APPLICATION_PDF = "application/pdf";
    public static final String TWO_NEW_LINE = "\n\n";
    
    @Autowired
    private BpaNoticeUtil bpaNoticeUtil;
    @Autowired
    private BpaWorkFlowService bpaWorkFlowService;
    @Autowired
    private CityService cityService;
    @Autowired
    private PLNoticeRepository plNoticeRepository;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private PlinthLevelCertificateService plinthLevelCertificateService;
    @Autowired
    private ReportService reportService;
    
    public PLNotice findByPlAndNoticeType(PlinthLevelCertificate pl, String noticeType) {
        return plNoticeRepository.findByPlAndNoticeType(pl, noticeType);
    }
    
    public ReportOutput getReportOutput(PlinthLevelCertificate plinthLevelCertificate, PLNotice plNotice,
            String plRejectionFileName, String fileName, String plRejectionNoticeType) throws IOException {
        ReportOutput reportOutput = new ReportOutput();
        if (plNotice == null || plNotice.getNoticeCommon().getNoticeFileStore() == null) {
            final Map<String, Object> reportParams = buildParametersForPL(plinthLevelCertificate);
            reportParams.putAll(getUlbDetails());
            ReportRequest reportInput = new ReportRequest(plRejectionFileName, plinthLevelCertificate, reportParams);
            reportOutput = reportService.createReport(reportInput);
            savePlNotice(plinthLevelCertificate, fileName, reportOutput, plRejectionNoticeType);
        } else {
            final FileStoreMapper fmp = plNotice.getNoticeCommon().getNoticeFileStore();
            Path path = fileStoreService.fetchAsPath(fmp.getFileStoreId(), APPLICATION_MODULE_TYPE);
            reportOutput.setReportOutputData(Files.readAllBytes(path));
        }
        return reportOutput;
    }
    
    public PLNotice savePlNotice(PlinthLevelCertificate pl, String fileName, ReportOutput reportOutput, String noticeType) {
    	PLNotice plNotice = new PLNotice();
    	plNotice.setPl(pl);
        NoticeCommon noticeCommon = new NoticeCommon();
        noticeCommon.setNoticeGeneratedDate(new Date());
        noticeCommon.setNoticeType(noticeType);
        noticeCommon.setNoticeFileStore(
                fileStoreService.store(new ByteArrayInputStream(reportOutput.getReportOutputData()), fileName, APPLICATION_PDF,
                        APPLICATION_MODULE_TYPE));
        plNotice.setNoticeCommon(noticeCommon);
        pl.addNotice(plNotice);
        plinthLevelCertificateService.savePlinthLevelCertificate(pl);
        return plNotice;
    }
    
    public Map<String, Object> buildParametersForPL(PlinthLevelCertificate pl) {
        Map<String, Object> reportParams = new HashMap<>();
        reportParams.put("cityName", ApplicationThreadLocals.getCityName());
        reportParams.put("logoPath", cityService.getCityLogoAsStream());
        reportParams.put("stateLogo", ReportUtil.getImageURL(BpaConstants.STATE_LOGO_PATH));
        reportParams.put("ulbName", ApplicationThreadLocals.getMunicipalityName());
        reportParams.put("formRule", "FORM B");
        reportParams.put("plNumber", pl.getPlinthLevelCertificateNumber() == null ? EMPTY : pl.getPlinthLevelCertificateNumber());
        reportParams.put("aplnCreatedBy", pl.getCreatedBy().getName());
        reportParams.put("approvalDate", DateUtils.getDefaultFormattedDate(pl.getApprovalDate()));
        reportParams.put("currentDate", currentDateToDefaultDateFormat());
        reportParams.put("applicantName", pl.getParent().getApplicantName());
        reportParams.put("serviceType", pl.getParent().getServiceType().getDescription());
        reportParams.put("applicationDate", DateUtils.getDefaultFormattedDate(pl.getApplicationDate()));
        reportParams.put("applicationNumber", pl.getApplicationNumber());
        reportParams.put("noticeGenerationDate", currentDateToDefaultDateFormat());
        reportParams.put("qrCode", generatePDF417Code(buildQRCodeDetails(pl)));
        String amenities = pl.getParent().getApplicationAmenity().stream().map(ServiceType::getDescription).collect(Collectors.joining(", "));
        reportParams.put("amenities", StringUtils.isBlank(amenities) ? "N/A" : amenities);
        reportParams.put("occupancy", pl.getParent().getOccupanciesName());
        reportParams.put("applicantAddress", pl.getParent().getOwner() == null ? "Not Mentioned" : pl.getParent().getOwner().getAddress());        
        
        String coApplicantNames = "";        
        if(null != pl.getParent().getCoApplicants()) {
        	for(PermitCoApplicant coApplicant:pl.getParent().getCoApplicants()) {
        		coApplicantNames = coApplicantNames + coApplicant.getCoApplicant().getName() + ",";
        	}
        }
        reportParams.put("coApplicantName", coApplicantNames);
        reportParams.put("mobileNo", pl.getParent().getOwner().getUser().getMobileNumber());
        
        if (!pl.getParent().getSiteDetail().isEmpty()) {
        	reportParams.put("plotNo", pl.getParent().getSiteDetail().get(0).getMspPlotNumber() == null? EMPTY :  pl.getParent().getSiteDetail().get(0).getMspPlotNumber());
        	reportParams.put("sectorNo", pl.getParent().getSiteDetail().get(0).getAdminBoundary() == null? EMPTY :  pl.getParent().getSiteDetail().get(0).getAdminBoundary().getName());
        }else {
        	reportParams.put("plotNo", EMPTY);
        	reportParams.put("sectorNo", EMPTY);
        }       
        
        return reportParams;
    }
    
    public String getApproverName(final PlinthLevelCertificate plinthLevelCertificate) {
        String designation = bpaNoticeUtil.getApproverDesignation(plinthLevelCertificate.getApproverPosition());
        StateHistory<Position> stateHistory = plinthLevelCertificate.getStateHistory().stream()
                .filter(history -> designation
                        .equalsIgnoreCase(history.getOwnerPosition().getDeptDesig().getDesignation().getName()))
                .findAny().orElse(null);
        Position ownerPos = null;
        Date lastModifiedDate = null;
        if (stateHistory == null && plinthLevelCertificate.getState() != null && designation
                .equals(plinthLevelCertificate.getState().getOwnerPosition().getDeptDesig().getDesignation().getName())) {
            ownerPos = plinthLevelCertificate.getState().getOwnerPosition();
            lastModifiedDate = plinthLevelCertificate.getState().getLastModifiedDate();
        } else if (stateHistory != null) {
            ownerPos = stateHistory.getOwnerPosition();
            lastModifiedDate = stateHistory.getLastModifiedDate();
        }
        return ownerPos == null ? N_A
                : bpaWorkFlowService
                        .getApproverAssignmentByDate(ownerPos, lastModifiedDate)
                        .getEmployee().getName();
    }
    
    public String buildQRCodeDetails(final PlinthLevelCertificate pl) {
        StringBuilder qrCodeValue = new StringBuilder();
        qrCodeValue = isBlank(ApplicationThreadLocals.getMunicipalityName()) ? qrCodeValue.append("")
                : qrCodeValue.append(ApplicationThreadLocals.getMunicipalityName()).append(ONE_NEW_LINE);
        qrCodeValue = pl.getParent().getOwner() == null || isBlank(pl.getParent().getApplicantName())
                ? qrCodeValue.append("Applicant Name : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Applicant Name : ").append(pl.getParent().getApplicantName()).append(ONE_NEW_LINE);
        qrCodeValue = isBlank(pl.getApplicationNumber())
                ? qrCodeValue.append("Application number : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Application number : ").append(pl.getApplicationNumber()).append(ONE_NEW_LINE);
        if (!isBlank(pl.getParent().geteDcrNumber())) {
            qrCodeValue = qrCodeValue.append("Edcr number : ").append(pl.getParent().geteDcrNumber()).append(ONE_NEW_LINE);
        }
        qrCodeValue = isBlank(pl.getParent().getPlanPermissionNumber())
                ? qrCodeValue.append("Permit number : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Permit number : ").append(pl.getParent().getPlanPermissionNumber()).append(ONE_NEW_LINE);
        qrCodeValue = qrCodeValue.append("Approved by : ").append(bpaNoticeUtil.getApproverDesignation(pl.getApproverPosition())).append(ONE_NEW_LINE); 
        qrCodeValue = pl.getApprovalDate() == null
                ? qrCodeValue.append("Date of approval of pl : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Date of approval of pl : ").append(DateUtils.getDefaultFormattedDate(pl.getApprovalDate()))
                        .append(ONE_NEW_LINE);
        qrCodeValue = isBlank(getApproverName(pl)) ? qrCodeValue.append("Name of approver : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Name of approver : ").append(getApproverName(pl)).append(ONE_NEW_LINE);
        return qrCodeValue.toString();
    }
    
    public Map<String, Object> getUlbDetails() {
        final Map<String, Object> ulbDetailsReportParams = new HashMap<>();
        ulbDetailsReportParams.put("cityName", ApplicationThreadLocals.getCityName());
        ulbDetailsReportParams.put("logoPath", cityService.getCityLogoAsStream());
        ulbDetailsReportParams.put("ulbName", ApplicationThreadLocals.getMunicipalityName());
        return ulbDetailsReportParams;
    }
}
