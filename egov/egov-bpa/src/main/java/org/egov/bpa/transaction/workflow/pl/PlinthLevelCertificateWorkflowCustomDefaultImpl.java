package org.egov.bpa.transaction.workflow.pl;

import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.springframework.transaction.annotation.Transactional;

public class PlinthLevelCertificateWorkflowCustomDefaultImpl extends PlinthLevelCertificateWorkflowCustomImpl{
	public PlinthLevelCertificateWorkflowCustomDefaultImpl() {}

	@Override
	@Transactional
	public void createCommonWorkflowTransition(final PlinthLevelCertificate pl, final WorkflowBean wfBean) {
		super.createCommonWorkflowTransition(pl, wfBean);
	}
}
