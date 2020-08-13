package org.egov.bpa.autonumber.impl;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.StringUtils.upperCase;

import java.time.LocalDateTime;

import org.egov.bpa.autonumber.PLCertificateNumberGenerator;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.utils.DateUtils;
import org.springframework.stereotype.Service;

@Service
public class PLCertificateNumberGeneratorImpl implements PLCertificateNumberGenerator{

	@Override
	public String generatePLCertificateNumber() {
		String cityCode = ApplicationThreadLocals.getCityCode();
        return String.format( "%s", new StringBuilder().append("PL").append(cityCode)
				  .append(String.valueOf(LocalDateTime.now().getMonthValue())).append(DateUtils.currentYear())
		  .append(upperCase(randomAlphanumeric(5))));
	}
}
