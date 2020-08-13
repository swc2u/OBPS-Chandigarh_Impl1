package org.egov.bpa.transaction.notice.impl;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.bpa.utils.BpaConstants.PL_CERTIFICATE_NOTICE_TYPE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.egov.bpa.transaction.entity.pl.PLNotice;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.notice.PlinthLevelCertificateNoticesFormat;
import org.egov.bpa.transaction.notice.util.PLNoticeUtil;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.reporting.engine.ReportFormat;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.reporting.engine.ReportRequest;
import org.egov.infra.reporting.engine.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PlinthLevelCertificateFormatImpl implements PlinthLevelCertificateNoticesFormat{
	private static final String REPORT_FILE_NAME = "plinthLevelcertificate";
	
	@Autowired
    @Qualifier("fileStoreService")
    protected FileStoreService fileStoreService;
    @Autowired
    protected ReportService reportService;
    @Autowired
    protected PLNoticeUtil plNoticeService;
    
    @Override
    public ReportOutput generateNotice(final PlinthLevelCertificate pl) {
        ReportOutput reportOutput = new ReportOutput();
        ReportRequest reportInput = null;
        PLNotice plNotice = plNoticeService.findByPlAndNoticeType(pl, PL_CERTIFICATE_NOTICE_TYPE);
        if (plNotice == null || plNotice.getNoticeCommon().getNoticeFileStore() == null) {
            Map<String, Object> reportParams = plNoticeService.buildParametersForPL(pl);
            reportInput = new ReportRequest(REPORT_FILE_NAME, pl == null
                    ? new PlinthLevelCertificate()
                    : pl, reportParams);
            reportOutput = reportService.createReport(reportInput);
            plNoticeService.savePlNotice(pl, pl.getApplicationNumber(), reportOutput, PL_CERTIFICATE_NOTICE_TYPE);
            List<PLNotice> plCertificate = pl.getPlNotices().stream()
                    .filter(plNotice1 -> plNotice1.getNoticeCommon().getNoticeType()
                            .equalsIgnoreCase(PL_CERTIFICATE_NOTICE_TYPE))
                    .collect(Collectors.toList());
            if (!plCertificate.isEmpty()) {
                final FileStoreMapper fmp = plCertificate.get(0).getNoticeCommon().getNoticeFileStore();
                Path path = fileStoreService.fetchAsPath(fmp.getFileStoreId(), APPLICATION_MODULE_TYPE);
                try {
                    reportOutput.setReportOutputData(Files.readAllBytes(path));
                } catch (IOException e) {
                    throw new ApplicationRuntimeException("Error occurred, when reading file!!!!", e);
                }
            }

        } else {
            final FileStoreMapper fmp = plNotice.getNoticeCommon().getNoticeFileStore();
            Path path = fileStoreService.fetchAsPath(fmp.getFileStoreId(), APPLICATION_MODULE_TYPE);
            try {
                reportOutput.setReportOutputData(Files.readAllBytes(path));
            } catch (IOException e) {
                throw new ApplicationRuntimeException("Error occurred, when reading file!!!!", e);
            }
        }
        reportOutput.setReportFormat(ReportFormat.PDF);
        return reportOutput;
    }
}
