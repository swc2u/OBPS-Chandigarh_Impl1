package org.egov.bpa.transaction.notice.impl;

import static org.egov.bpa.utils.BpaConstants.BPA_REJECTION_NOTICE_TYPE;
import static org.egov.bpa.utils.BpaConstants.PLREJECTIONFILENAME;

import java.io.IOException;

import org.egov.bpa.transaction.entity.pl.PLNotice;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.notice.PlinthLevelCertificateNoticesFormat;
import org.egov.bpa.transaction.notice.util.PLNoticeUtil;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.reporting.engine.ReportFormat;
import org.egov.infra.reporting.engine.ReportOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PlRejectionFormatImpl implements PlinthLevelCertificateNoticesFormat{
	@Autowired
    protected PLNoticeUtil plNoticeUtil;
	
	@Override
    public ReportOutput generateNotice(PlinthLevelCertificate plinthLevelCertificate) {
        String fileName = "pl_rejection_notice_" + plinthLevelCertificate.getApplicationNumber();
        ReportOutput reportOutput = new ReportOutput();
        PLNotice plNotice = plNoticeUtil.findByPlAndNoticeType(plinthLevelCertificate, BPA_REJECTION_NOTICE_TYPE);
		try {
			reportOutput = plNoticeUtil.getReportOutput(plinthLevelCertificate, plNotice, PLREJECTIONFILENAME, fileName, BPA_REJECTION_NOTICE_TYPE);
		} catch (IOException e) {
            throw new ApplicationRuntimeException("Error occurred, when reading file!!!!", e);
		}
        reportOutput.setReportFormat(ReportFormat.PDF);
        return reportOutput;
    }
}
