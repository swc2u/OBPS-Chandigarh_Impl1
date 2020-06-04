package org.egov.collection.scheduler;

import org.egov.collection.integration.services.SchedularService;
import org.egov.infra.scheduler.quartz.AbstractQuartzJob;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;

@DisallowConcurrentExecution
public class PayUMoneyReconciliationJob extends AbstractQuartzJob{
	
	private static final long serialVersionUID = -8293830861860894611L;

    @Autowired
    private transient SchedularService schedularService;
    
    @Override
    public void executeJob() {
        schedularService.reconcilePayUMoeny();
    }

}
