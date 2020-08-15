package org.egov.bpa.utils;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.bpa.utils.BpaConstants.YES;
import static org.egov.bpa.utils.PLConstants.PL_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED;

import java.util.List;

import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PLCertificateUtils {
	@Autowired
    private AppConfigValueService appConfigValueService;
    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource bpaMessageSource;
    
    public String getAppConfigValueByKeyName(String code) {
        List<AppConfigValues> appConfigValueList = appConfigValueService
                .getConfigValuesByModuleAndKey(APPLICATION_MODULE_TYPE, code);
        return appConfigValueList.isEmpty() ? "" : appConfigValueList.get(0).getValue();
    }
    
    public boolean isPLInspectionSchedulingIntegrationRequired() {
        return getAppConfigValueByKeyName(PL_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED).equalsIgnoreCase(YES);
    }
}
