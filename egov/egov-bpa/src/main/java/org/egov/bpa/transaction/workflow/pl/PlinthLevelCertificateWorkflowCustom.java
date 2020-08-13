package org.egov.bpa.transaction.workflow.pl;

import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;

@FunctionalInterface
public interface PlinthLevelCertificateWorkflowCustom {
	public void createCommonWorkflowTransition(final PlinthLevelCertificate plinthLevelCertificate, final WorkflowBean wfBean);
}
