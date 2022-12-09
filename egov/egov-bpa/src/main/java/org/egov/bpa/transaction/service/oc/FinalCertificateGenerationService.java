/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.transaction.service.oc;

import org.apache.log4j.Logger;
import static org.egov.infra.utils.DateUtils.currentDateToDefaultDateFormat;
import org.egov.bpa.transaction.entity.oc.OCNotice;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.notice.OccupancyCertificateNoticesFormat;
import org.egov.bpa.transaction.notice.impl.OccupancyCertificateFinalFormatImpl;
import org.egov.bpa.transaction.service.messaging.oc.OcSmsAndEmailService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.reporting.engine.ReportOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class FinalCertificateGenerationService {

    private static final Logger logger = Logger.getLogger(FinalCertificateGenerationService.class);
    @Autowired
    private OccupancyCertificateService occupancyCertificateService;

    @Autowired
    private CustomImplProvider specificNoticeService;

    @Autowired
    private OcSmsAndEmailService ocSmsAndEmailService;

    @Transactional
    public void generateFinalOCCertificate() {
    	String today = currentDateToDefaultDateFormat();
		Date todayDate = null;
		try {
			todayDate = new SimpleDateFormat("dd/MM/yyyy").parse(today);
		} catch (ParseException e) {
			logger.info("***************date parse exception**************");
		}
        List<Long> ocIdList = occupancyCertificateService.findFinalOCGenerationApplications(todayDate);
        for (Long ocId : ocIdList) {
        	OccupancyCertificate occupancyCertificate= occupancyCertificateService.findById(ocId);
        	 List<OCNotice> finalOCApp = occupancyCertificate.getOcNotices().stream().filter(notice->notice.getNoticeCommon().getNoticeType().equalsIgnoreCase(BpaConstants.FINAL_OCCUPANCY_CERTIFICATE_NOTICE_TYPE)).collect(Collectors.toList());
        	 if(finalOCApp.isEmpty()) {
        		 OccupancyCertificateNoticesFormat ocNoticeFeature = (OccupancyCertificateNoticesFormat) specificNoticeService
                         .find(OccupancyCertificateFinalFormatImpl.class, specificNoticeService.getCityDetails());
                 ReportOutput reportOutput = ocNoticeFeature.generateNotice( occupancyCertificate);
                 ocSmsAndEmailService.sendSmsAndEmailOnFinalCertificateGeneration(occupancyCertificate, reportOutput);
        	 }
        		 
        }
    }

}
