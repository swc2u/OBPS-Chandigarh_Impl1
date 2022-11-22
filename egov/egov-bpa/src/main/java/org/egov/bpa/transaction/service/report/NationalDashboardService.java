package org.egov.bpa.transaction.service.report;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_APPROVED;
import static org.egov.infra.utils.DateUtils.currentDateToDefaultDateFormat;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REGISTERED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SUBMITTED;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.bpa.entitiy.national.dashboard.ApplicationData;
import org.egov.bpa.entitiy.national.dashboard.GroupBy;
import org.egov.bpa.entitiy.national.dashboard.Metrics;
import org.egov.bpa.entitiy.national.dashboard.NationalDashboardResponse;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.dto.CollectionSummaryReportHelper;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.SearchBpaApplicationService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.collection.constants.CollectionConstants;
import org.egov.common.entity.bpa.Occupancy;
import org.egov.common.entity.bpa.SubOccupancy;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.commons.service.OccupancyService;
import org.egov.commons.service.SubOccupancyService;
import org.egov.infra.microservice.contract.RequestInfoWrapper;
import org.egov.infra.microservice.models.UserInfo;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NationalDashboardService {
	private static final Logger LOG = LoggerFactory.getLogger(NationalDashboardService.class);
	@Autowired
	SearchBpaApplicationService searchBpaApplicationService;
	@Autowired
	private OccupancyService occupancyService;
	@Autowired
    protected SubOccupancyService subOccupancyService;
	@Autowired
	private ApplicationBpaService applicationBpaService;
	@PersistenceContext
	private EntityManager entityManager;
	
	public Session getCurrentSession() {
		return entityManager.unwrap(Session.class);
	}
	
	public NationalDashboardResponse getDashboardData(NationalDashboardResponse response,SearchBpaApplicationForm bpaApplicationForm) {
//		List<String> statusList = new ArrayList<>();
//		statusList.addAll(Arrays.asList(BpaConstants.APPLICATION_STATUS_ACCEPTED,BpaConstants.APPLICATION_STATUS_ORDER_ISSUED, BpaConstants.APPROVED,"Previous Plan Data Updated"));
		String today = currentDateToDefaultDateFormat();
		response.setDate(today);
		
		List<ApplicationData> bpaApplications=fetchApplications(today);
        
//		response.setTotalPermitsIssued(bpaApplications.size());
        Metrics metrics = new Metrics();
        List<GroupBy> permitIssued = new ArrayList<>();
        
		GroupBy groupBy = new GroupBy();
		groupBy.setGroupBy("RiskType");
		groupBy.setBuckets(getApplicationsByRiskType(bpaApplications));
		
		permitIssued.add(groupBy);
		
		groupBy = new GroupBy();
		groupBy.setGroupBy("OccupancyType");
		groupBy.setBuckets(getApplicationsByOccupancyType(bpaApplications));
		permitIssued.add(groupBy);
		
		groupBy = new GroupBy();
		groupBy.setGroupBy("SubOccupancyType");
		groupBy.setBuckets(getApplicationsBySubOccupancyType(bpaApplications));
		permitIssued.add(groupBy);
		
		metrics.setPermitsIssued(permitIssued);
		
		List<GroupBy> paymentList = new ArrayList<GroupBy>();
		groupBy = new GroupBy();
		groupBy.setGroupBy("paymentMode");
		groupBy.setBuckets(getApplicationsCollectionDetails(bpaApplicationForm,bpaApplications));
		paymentList.add(groupBy);
//		groupBy = new GroupBy();
//		groupBy.setGroupBy("paymentMode");
//		groupBy.setBuckets(getOCApplicationsCollectionDetails(bpaApplicationForm,bpaApplications));
//		paymentList.add(groupBy);
		metrics.setTodaysCollection(paymentList);
		
		response.setMetrics(metrics);
		return response;
	}
	
	
	
	private List<ApplicationData> fetchApplications(String today) {
		StringBuilder query =new StringBuilder("select ea.applicationnumber applicationNumber, ema.name as applicationSubType,ea.edcrnumber edcrNumber  from chandigarh.egbpa_application ea "
				+ "inner join chandigarh.egbpa_mstr_applicationsubtype ema on ema.id = ea.applicationsubtype "
				+ "where ea.applicationnumber in (select applicationnumber from state.egp_inbox where pendingaction ='END')  "
				+ "and ea.status not in (select id from chandigarh.egbpa_status x "
				+ "WHERE code in ('Rejected','Cancelled')) ");
		
		if (today!=null)
			query.append(" and ea.lastmodifieddate>= '"+today+"'");
		 
		final SQLQuery sqlQuery = createApplicationQuery(query.toString());
		 List<ApplicationData> reportResults = sqlQuery.list();
		 return reportResults;
	}
	public SQLQuery createApplicationQuery(String query) {
	    return (SQLQuery) getCurrentSession().createSQLQuery(query)
	            .addScalar("applicationNumber", org.hibernate.type.StringType.INSTANCE)
	            .addScalar("applicationSubType", org.hibernate.type.StringType.INSTANCE)
	            .addScalar("edcrNumber", org.hibernate.type.StringType.INSTANCE)
	            .setResultTransformer(Transformers.aliasToBean(ApplicationData.class));
	}

	public List<JSONObject> getApplicationsByRiskType(List<ApplicationData> bpaApplications) {
		List<JSONObject> buckets = new ArrayList<>();
        	JSONObject lowRisk = new JSONObject();
        	lowRisk.put("name", BpaConstants.APPLICATION_TYPE_LOWRISK);
        	lowRisk.put("value",  bpaApplications.stream().filter(bpa->bpa.getApplicationSubType().equalsIgnoreCase(BpaConstants.APPLICATION_TYPE_LOWRISK)).count());
        	buckets.add(lowRisk);
        	
        	JSONObject midRisk = new JSONObject();
        	midRisk.put("name", BpaConstants.APPLICATION_TYPE_MEDIUMRISK);
        	midRisk.put("value",  bpaApplications.stream().filter(bpa->bpa.getApplicationSubType().equalsIgnoreCase(BpaConstants.APPLICATION_TYPE_MEDIUMRISK)).count());
        	buckets.add(midRisk);
        	
        	JSONObject highRisk = new JSONObject();
        	highRisk.put("name", BpaConstants.APPLICATION_TYPE_HIGHRISK);
        	highRisk.put("value",  bpaApplications.stream().filter(bpa->bpa.getApplicationSubType().equalsIgnoreCase(BpaConstants.APPLICATION_TYPE_HIGHRISK)).count());
        	buckets.add(highRisk);
        	
        return buckets;
}

public List<JSONObject> getApplicationsByOccupancyType(List<ApplicationData> bpaApplications) {
	List<JSONObject> buckets = new ArrayList<JSONObject>();
	 List<Occupancy> occupancyList = occupancyService.findAllOrderByOrderNumber();
	 occupancyList.forEach(occupancy->{
		 AtomicInteger counter = new AtomicInteger();
		 bpaApplications.forEach(bpa->{
			Plan plan = applicationBpaService.getPlanInfo(bpa.getEdcrNumber());
			if(plan!=null) {
				OccupancyTypeHelper mostRestrictiveFarHelper = plan.getVirtualBuilding() != null
						? plan.getVirtualBuilding().getMostRestrictiveFarHelper()
						: null;
						
						if(mostRestrictiveFarHelper!=null && mostRestrictiveFarHelper.getType().getCode().equalsIgnoreCase(occupancy.getCode())) {
							counter.getAndIncrement();
						}
			}else
				LOG.info("Plan is null for application: "+bpa.getApplicationNumber());
		 });
		 
		 JSONObject occupancyType = new JSONObject();
		 occupancyType.put("name", occupancy.getName());
		 occupancyType.put("value",  counter);
		 buckets.add(occupancyType);
	 });
	
	
	return buckets;
}

public List<JSONObject> getApplicationsBySubOccupancyType(
		List<ApplicationData> bpaApplications) {

    List<JSONObject> buckets = new ArrayList<JSONObject>();
	 List<SubOccupancy> subOccupancyList = subOccupancyService.findAll();
	 
//	List<BpaApplication> subOccupancyBPA;
	 subOccupancyList.forEach(subOccupancy->{
		 AtomicInteger counter = new AtomicInteger();
		 bpaApplications.forEach(bpa->{
			Plan plan = applicationBpaService.getPlanInfo(bpa.getEdcrNumber());
			if(plan!=null) {
				OccupancyTypeHelper mostRestrictiveFarHelper = plan.getVirtualBuilding() != null
						? plan.getVirtualBuilding().getMostRestrictiveFarHelper()
						: null;
						
						if(mostRestrictiveFarHelper!=null && mostRestrictiveFarHelper.getSubtype().getCode().equalsIgnoreCase(subOccupancy.getCode())) {
							counter.getAndIncrement();
						}
			}
		 });
		
		 JSONObject subOccupancyType = new JSONObject();
		 subOccupancyType.put("name", subOccupancy.getName());
		 subOccupancyType.put("value",  counter);
		 buckets.add(subOccupancyType);
	 });
	
	
	return buckets;
}

public List<JSONObject> getApplicationsCollectionDetails(SearchBpaApplicationForm bpaApplicationForm,
		List<ApplicationData> bpaApplications) {
	List<JSONObject> buckets = new ArrayList<JSONObject>();
	bpaApplicationForm.setServiceType("BPA");
	 List<CollectionSummaryReportHelper> bpaCollectionData = getCollectionData(bpaApplicationForm,"USERWISE");
	 return sortedCollectionData(bpaCollectionData,buckets);
		
}

public List<JSONObject> getOCApplicationsCollectionDetails(SearchBpaApplicationForm bpaApplicationForm,
		List<ApplicationData> bpaApplications) {
	List<JSONObject> buckets = new ArrayList<JSONObject>();
	bpaApplicationForm.setServiceType("OC");
	 List<CollectionSummaryReportHelper> bpaCollectionData = getCollectionData(bpaApplicationForm,"USERWISE");
	 return sortedCollectionData(bpaCollectionData,buckets);
}

public List<JSONObject> sortedCollectionData(List<CollectionSummaryReportHelper> bpaCollectionData,List<JSONObject> buckets) {
	 JSONObject paymentMode1 = new JSONObject();
	 paymentMode1.put("name", CollectionConstants.INSTRUMENTTYPE_CASH);
	 paymentMode1.put("value",  bpaCollectionData.get(0).getCashAmount());
	 buckets.add(paymentMode1);
	 
	 JSONObject paymentMode2 = new JSONObject();
	 paymentMode2.put("name", CollectionConstants.INSTRUMENTTYPE_CHEQUEORDD);
	 paymentMode2.put("value",  bpaCollectionData.get(0).getChequeAmount());
	 buckets.add(paymentMode2);
	 
	 JSONObject paymentMode3 = new JSONObject();
	 paymentMode3.put("name", CollectionConstants.INSTRUMENTTYPE_CARD);
	 paymentMode3.put("value",  bpaCollectionData.get(0).getCardAmount());
	 buckets.add(paymentMode3);
	 
	 JSONObject paymentMode4 = new JSONObject();
	 paymentMode4.put("name", CollectionConstants.INSTRUMENTTYPE_BANK);
	 paymentMode4.put("value",  bpaCollectionData.get(0).getBankAmount());
	 buckets.add(paymentMode4);
	 
	 JSONObject paymentMode5 = new JSONObject();
	 paymentMode5.put("name", CollectionConstants.INSTRUMENTTYPE_ONLINE);
	 paymentMode5.put("value",  bpaCollectionData.get(0).getOnlineAmount());
	 buckets.add(paymentMode5);
	 
 
return buckets;
}

// private List<String> createPaymentModeList() {
//        final List<String> paymentModes = new ArrayList<>();
//        paymentModes.add(CollectionConstants.INSTRUMENTTYPE_CASH);
//        paymentModes.add(CollectionConstants.INSTRUMENTTYPE_CHEQUEORDD);
//        paymentModes.add(CollectionConstants.INSTRUMENTTYPE_CARD);
//        paymentModes.add(CollectionConstants.INSTRUMENTTYPE_BANK);
//        paymentModes.add(CollectionConstants.INSTRUMENTTYPE_ONLINE);
//        return paymentModes;
//    }

private List<CollectionSummaryReportHelper> getCollectionData(SearchBpaApplicationForm searchRequest,String queryType) {

	final SimpleDateFormat fromDateFormatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    final SimpleDateFormat toDateFormatter = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
    final StringBuilder defaultQueryStr = new StringBuilder(500);
    
    StringBuilder aggregateQuery = new StringBuilder();
    StringBuilder userwiseQuery = new StringBuilder();
    final StringBuilder finalUserwiseQuery = new StringBuilder();
    final StringBuilder finalAggregateQuery = new StringBuilder();
    
    final StringBuilder selectQuery = new StringBuilder(
            "SELECT  EGCL_COLLECTIONHEADER.SOURCE , SER.NAME AS serviceName,"
            		+ "(CASE WHEN EGF_INSTRUMENTTYPE.TYPE='cash' THEN SUM(EGF_INSTRUMENTHEADER.INSTRUMENTAMOUNT) END) AS cashAmount, "
                    +
                    " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='cheque' THEN SUM(EGF_INSTRUMENTHEADER.INSTRUMENTAMOUNT) WHEN EGF_INSTRUMENTTYPE.TYPE='dd' THEN SUM(EGF_INSTRUMENTHEADER.INSTRUMENTAMOUNT) END) AS chequeAmount,"
                    +
                    " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE= 'online' THEN SUM(EGF_INSTRUMENTHEADER.INSTRUMENTAMOUNT) END) AS onlineAmount, "
                    +
                    " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='bankchallan' THEN SUM(EGF_INSTRUMENTHEADER.INSTRUMENTAMOUNT) END) AS bankAmount, "
                    +
                    " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='card' THEN SUM(EGF_INSTRUMENTHEADER.INSTRUMENTAMOUNT) END) AS cardAmount "
                    );
    final StringBuilder fromQuery = new StringBuilder();
    if(searchRequest.getServiceType().equalsIgnoreCase("BPA")) {
    	fromQuery.append(" FROM EGCL_COLLECTIONHEADER EGCL_COLLECTIONHEADER INNER JOIN EGCL_COLLECTIONINSTRUMENT EGCL_COLLECTIONINSTRUMENT ON EGCL_COLLECTIONHEADER.ID = EGCL_COLLECTIONINSTRUMENT.COLLECTIONHEADER"
                        +
                        " INNER JOIN EGF_INSTRUMENTHEADER EGF_INSTRUMENTHEADER ON EGCL_COLLECTIONINSTRUMENT.INSTRUMENTHEADER = EGF_INSTRUMENTHEADER.ID"
                        +
                        " INNER JOIN EGW_STATUS EGW_STATUS ON EGCL_COLLECTIONHEADER.STATUS = EGW_STATUS.ID" +
                        " INNER JOIN EGF_INSTRUMENTTYPE EGF_INSTRUMENTTYPE ON EGF_INSTRUMENTHEADER.INSTRUMENTTYPE = EGF_INSTRUMENTTYPE.ID"
                        +
                        " INNER JOIN EGCL_COLLECTIONMIS EGCL_COLLECTIONMIS ON EGCL_COLLECTIONHEADER.ID = EGCL_COLLECTIONMIS.COLLECTIONHEADER"
                        +
                        " INNER JOIN EGCL_SERVICEDETAILS SER ON SER.ID = EGCL_COLLECTIONHEADER.SERVICEDETAILS "
                        +
                        " INNER JOIN EGBPA_APPLICATION EGBPA_APPLICATION ON EGBPA_APPLICATION.APPLICATIONNUMBER=EGCL_COLLECTIONHEADER.CONSUMERCODE ");
    }
    else {
    	fromQuery.append(
    			 " FROM EGCL_COLLECTIONHEADER EGCL_COLLECTIONHEADER INNER JOIN EGCL_COLLECTIONINSTRUMENT EGCL_COLLECTIONINSTRUMENT ON EGCL_COLLECTIONHEADER.ID = EGCL_COLLECTIONINSTRUMENT.COLLECTIONHEADER"
                         +
                         " INNER JOIN EGF_INSTRUMENTHEADER EGF_INSTRUMENTHEADER ON EGCL_COLLECTIONINSTRUMENT.INSTRUMENTHEADER = EGF_INSTRUMENTHEADER.ID"
                         +
                         " INNER JOIN EGW_STATUS EGW_STATUS ON EGCL_COLLECTIONHEADER.STATUS = EGW_STATUS.ID" +
                         " INNER JOIN EGF_INSTRUMENTTYPE EGF_INSTRUMENTTYPE ON EGF_INSTRUMENTHEADER.INSTRUMENTTYPE = EGF_INSTRUMENTTYPE.ID"
                         +
                         " INNER JOIN EGCL_COLLECTIONMIS EGCL_COLLECTIONMIS ON EGCL_COLLECTIONHEADER.ID = EGCL_COLLECTIONMIS.COLLECTIONHEADER"
                         +
                         " INNER JOIN EGCL_SERVICEDETAILS SER ON SER.ID = EGCL_COLLECTIONHEADER.SERVICEDETAILS "
                         +
                         " INNER JOIN EGBPA_OCCUPANCY_CERTIFICATE EGBPA_OCCUPANCY_CERTIFICATE ON EGBPA_OCCUPANCY_CERTIFICATE.APPLICATIONNUMBER = EGCL_COLLECTIONHEADER.CONSUMERCODE "
                         +
                         " INNER JOIN EGBPA_APPLICATION EGBPA_APPLICATION ON EGBPA_APPLICATION.ID=EGBPA_OCCUPANCY_CERTIFICATE.PARENT ");
         
    }
    
    final StringBuilder whereQuery = new StringBuilder(" WHERE EGW_STATUS.DESCRIPTION != 'Cancelled'");
    final StringBuilder groupQuery = new StringBuilder(" GROUP BY  EGCL_COLLECTIONHEADER.SOURCE , counterName, employeeName, USERID,serviceName, "
            + "EGF_INSTRUMENTTYPE.TYPE");

    aggregateQuery.append(selectQuery)
            .append(" , '' AS counterName, '' AS employeeName, 0 AS USERID ")
            .append(fromQuery);

    userwiseQuery.append(selectQuery)
            .append(" , EG_LOCATION.NAME AS counterName, EG_USER.NAME AS employeeName, EG_USER.ID AS USERID")
            .append(fromQuery)
            .append(" LEFT JOIN EG_LOCATION EG_LOCATION ON EGCL_COLLECTIONHEADER.LOCATION = EG_LOCATION.ID "
                    + " INNER JOIN state.EG_USER EG_USER ON EGCL_COLLECTIONHEADER.CREATEDBY = EG_USER.ID ");

    if (searchRequest.getFromDate() != null && searchRequest.getToDate() != null) {
        whereQuery.append(" AND EGCL_COLLECTIONHEADER.RECEIPTDATE between to_timestamp('"
                + fromDateFormatter.format(searchRequest.getFromDate()) + "', 'YYYY-MM-DD HH24:MI:SS') and " + " to_timestamp('"
                + toDateFormatter.format(searchRequest.getToDate()) + "', 'YYYY-MM-DD HH24:MI:SS') ");
    }

        userwiseQuery.setLength(0);
        userwiseQuery.append(aggregateQuery);
    
    
        userwiseQuery.append(whereQuery);
        aggregateQuery.append(whereQuery);
        userwiseQuery = prepareQueryForAllPaymentMode(userwiseQuery, groupQuery);
        aggregateQuery = prepareQueryForAllPaymentMode(aggregateQuery, groupQuery);

    final StringBuilder finalSelectQuery = new StringBuilder(
            "SELECT source,counterName,employeeName,serviceName,cast(sum(cashAmount) AS NUMERIC) AS cashAmount, cast(sum(chequeAmount) AS NUMERIC) AS chequeAmount, cast(sum(onlineAmount) AS NUMERIC) AS onlineAmount ,USERID, cast(sum(bankAmount) AS NUMERIC) AS bankAmount, "
                    + "  cast(sum(cardAmount) AS NUMERIC) AS cardAmount  FROM (");
    final StringBuilder finalGroupQuery = new StringBuilder(
            " ) AS RESULT GROUP BY RESULT.source,RESULT.counterName,RESULT.employeeName,RESULT.USERID,RESULT.serviceName order by RESULT.source,employeeName, serviceName ");

    finalUserwiseQuery.append(finalSelectQuery).append(userwiseQuery).append(finalGroupQuery);
    finalAggregateQuery.append(finalSelectQuery).append(aggregateQuery).append(finalGroupQuery);
    
    final SQLQuery userwiseSqluery = createSQLQuery(finalUserwiseQuery.toString());
    final SQLQuery aggregateSqlQuery = createSQLQuery(finalAggregateQuery.toString());


    List<CollectionSummaryReportHelper> reportResults = new ArrayList<>();
    if(queryType.equalsIgnoreCase("USERWISE")) {
    	reportResults = populateCollectionSummaryQueryResults(userwiseSqluery.list());
    }else {
    	reportResults = populateCollectionSummaryQueryResults(aggregateSqlQuery.list());
    }
    
    
    return reportResults;
}

public StringBuilder prepareQueryForAllPaymentMode(StringBuilder query, StringBuilder groupQuery) {
    String unionString = " union ";
    StringBuilder queryString = new StringBuilder();
    queryString.append(query);
    queryString.append(" AND EGF_INSTRUMENTTYPE.TYPE = 'cash'");
    queryString.append(groupQuery);
    queryString.append(unionString);
    queryString.append(query);
    queryString.append(" AND EGF_INSTRUMENTTYPE.TYPE = 'bankchallan'");
    queryString.append(groupQuery);
    queryString.append(unionString);
    queryString.append(query);
    queryString.append(" AND EGF_INSTRUMENTTYPE.TYPE in( 'cheque','dd')");
    queryString.append(groupQuery);
    queryString.append(unionString);
    queryString.append(query);
    queryString.append(" AND EGF_INSTRUMENTTYPE.TYPE = 'online'");
    queryString.append(groupQuery);
    queryString.append(unionString);
    queryString.append(query);
    queryString.append(" AND EGF_INSTRUMENTTYPE.TYPE  = 'card' ");
    queryString.append(groupQuery);
    return queryString;
}

public List<CollectionSummaryReportHelper> populateCollectionSummaryQueryResults(final List<CollectionSummaryReportHelper> queryResults) {
    for (final CollectionSummaryReportHelper collectionSummaryReport : queryResults) {
        if (collectionSummaryReport.getCashAmount() == null)
            collectionSummaryReport.setCashAmount(BigDecimal.ZERO);
        if (collectionSummaryReport.getChequeAmount() == null)
            collectionSummaryReport.setChequeAmount(BigDecimal.ZERO);
        if (collectionSummaryReport.getOnlineAmount() == null)
            collectionSummaryReport.setOnlineAmount(BigDecimal.ZERO);
        if (collectionSummaryReport.getBankAmount() == null)
            collectionSummaryReport.setBankAmount(BigDecimal.ZERO);
        if (collectionSummaryReport.getCardAmount() == null)
            collectionSummaryReport.setCardAmount(BigDecimal.ZERO);
        collectionSummaryReport.setTotalAmount(collectionSummaryReport.getCardAmount()
                .add(collectionSummaryReport.getBankAmount()).add(collectionSummaryReport.getOnlineAmount())
                .add(collectionSummaryReport.getChequeAmount()).add(collectionSummaryReport.getCashAmount()));

    }
    return queryResults;
}

public SQLQuery createSQLQuery(String query) {
    return (SQLQuery) getCurrentSession().createSQLQuery(query)
            .addScalar("cashAmount", BigDecimalType.INSTANCE)
            .addScalar("chequeAmount", BigDecimalType.INSTANCE)
            .addScalar("onlineAmount", BigDecimalType.INSTANCE)
            .addScalar("source", org.hibernate.type.StringType.INSTANCE)
            .addScalar("serviceName", org.hibernate.type.StringType.INSTANCE)
//            .addScalar("counterName", org.hibernate.type.StringType.INSTANCE)
//            .addScalar("employeeName", org.hibernate.type.StringType.INSTANCE)
            .addScalar("bankAmount", BigDecimalType.INSTANCE)
            .addScalar("cardAmount", BigDecimalType.INSTANCE)
            .setResultTransformer(Transformers.aliasToBean(CollectionSummaryReportHelper.class));
}



public void validateUser(RequestInfoWrapper requestInfoWrapper) {
	System.out.println("getRequestInfo : "+requestInfoWrapper.getRequestInfo());
}

}
