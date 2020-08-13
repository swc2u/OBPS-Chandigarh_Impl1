package org.egov.bpa.transaction.notice;

import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.infra.reporting.engine.ReportOutput;

@FunctionalInterface
public interface PlinthLevelCertificateNoticesFormat {
	ReportOutput generateNotice(final PlinthLevelCertificate pl);
}
