package org.egov.collection.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.egov.collection.constants.CollectionConstants;
import org.egov.collection.entity.CollectionSummaryHeadWiseReport;
import org.egov.collection.entity.CollectionSummaryHeadWiseReportResult;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.springframework.stereotype.Service;

@Service
public class ReceiptRegisterService {
	public Object getCollectionSummaryReportForUrban(Date fromDate, Date toDate, String paymentMode, String source,
			String glCode, Integer branchId, Long applicationTypeId, List<Long> appTypeList) {
		final SimpleDateFormat fromDateFormatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        final SimpleDateFormat toDateFormatter = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        final StringBuilder defaultQueryStr = new StringBuilder(500);
        final StringBuilder aggregateQueryStr = new StringBuilder();
        StringBuilder rebateQueryStr = new StringBuilder("");
        StringBuilder revenueHeadQueryStr = new StringBuilder("");
        final CollectionSummaryHeadWiseReportResult collResult = new CollectionSummaryHeadWiseReportResult();

      
        return collResult;
	}
}
