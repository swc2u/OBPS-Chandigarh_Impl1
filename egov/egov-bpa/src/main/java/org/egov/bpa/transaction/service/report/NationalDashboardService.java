package org.egov.bpa.transaction.service.report;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.bpa.entitiy.national.dashboard.GroupBy;
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
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NationalDashboardService {
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
//		bpaApplicationForm.setStatus("Accepted as Scrutinized");
		List<String> statusList = new ArrayList<>();
		statusList.addAll(Arrays.asList(BpaConstants.APPLICATION_STATUS_ACCEPTED,BpaConstants.APPLICATION_STATUS_ORDER_ISSUED, BpaConstants.APPROVED,"Previous Plan Data Updated"));
		final Criteria criteria = buildSearchCriteria(bpaApplicationForm,statusList);
		List<JSONObject> buckets = new ArrayList<>();
        List<BpaApplication> bpaApplications = criteria.list();
        
//        SearchBpaApplicationForm  searchForm = new SearchBpaApplicationForm();
//		searchForm.setStatus("Accepted as Scrutinized");
		response.setTotalPermitsIssued(bpaApplications.size());
		
        
		GroupBy groupBy = new GroupBy();
		groupBy.setGroupBy("RiskType");
		groupBy.setBuckets(getApplicationsByRiskType(bpaApplicationForm,bpaApplications));
		response.setPermitsIssuedByRiskType(Arrays.asList(groupBy));
		
		groupBy = new GroupBy();
		groupBy.setGroupBy("OccupancyType");
		groupBy.setBuckets(getApplicationsByOccupancyType(bpaApplicationForm,bpaApplications));
		response.setPermitsIssuedByOccupancyType(Arrays.asList(groupBy));
		
		groupBy = new GroupBy();
		groupBy.setGroupBy("SubOccupancyType");
		groupBy.setBuckets(getApplicationsBySubOccupancyType(bpaApplicationForm,bpaApplications));
		response.setPermitsIssuedBySubOccupancyType(Arrays.asList(groupBy));
		
		List<GroupBy> paymentList = new ArrayList<GroupBy>();
		groupBy = new GroupBy();
		groupBy.setGroupBy("paymentMode");
		groupBy.setBuckets(getApplicationsCollectionDetails(bpaApplicationForm,bpaApplications));
		paymentList.add(groupBy);
		groupBy = new GroupBy();
		groupBy.setGroupBy("paymentMode");
		groupBy.setBuckets(getOCApplicationsCollectionDetails(bpaApplicationForm,bpaApplications));
		paymentList.add(groupBy);
		response.setTodaysCollection(paymentList);

		return response;
	}
	
	
	
	public List<JSONObject> getApplicationsByRiskType(SearchBpaApplicationForm bpaApplicationForm, List<BpaApplication> bpaApplications) {
//		final Criteria criteria = buildSearchCriteria(bpaApplicationForm);
		List<JSONObject> buckets = new ArrayList<>();
//        List<BpaApplication> bpaApplications = criteria.list();
        if(bpaApplicationForm.getRiskType()==null) {
        	JSONObject lowRisk = new JSONObject();
        	lowRisk.put("name", BpaConstants.APPLICATION_TYPE_LOWRISK);
        	lowRisk.put("value",  bpaApplications.stream().filter(bpa->bpa.getApplicationType().getName().equalsIgnoreCase(BpaConstants.APPLICATION_TYPE_LOWRISK)).count());
        	buckets.add(lowRisk);
        	
        	JSONObject midRisk = new JSONObject();
        	midRisk.put("name", BpaConstants.APPLICATION_TYPE_MEDIUMRISK);
        	midRisk.put("value",  bpaApplications.stream().filter(bpa->bpa.getApplicationType().getName().equalsIgnoreCase(BpaConstants.APPLICATION_TYPE_MEDIUMRISK)).count());
        	buckets.add(midRisk);
        	
        	JSONObject highRisk = new JSONObject();
        	highRisk.put("name", BpaConstants.APPLICATION_TYPE_HIGHRISK);
        	highRisk.put("value",  bpaApplications.stream().filter(bpa->bpa.getApplicationType().getName().equalsIgnoreCase(BpaConstants.APPLICATION_TYPE_HIGHRISK)).count());
        	buckets.add(highRisk);
        }else {
        	JSONObject riskType = new JSONObject();
        	riskType.put("name", bpaApplicationForm.getRiskType());
        	riskType.put("value",  bpaApplications.stream().filter(bpa->bpa.getApplicationType().getName().equalsIgnoreCase(bpaApplicationForm.getRiskType())).count());
        	buckets.add(riskType);
        }
        	
        return buckets;
}

public List<JSONObject> getApplicationsByOccupancyType(SearchBpaApplicationForm bpaApplicationForm, List<BpaApplication> bpaApplications) {
	List<JSONObject> buckets = new ArrayList<JSONObject>();
	 List<Occupancy> occupancyList = occupancyService.findAllOrderByOrderNumber();
	
	 occupancyList.forEach(occupancy->{
		 JSONObject occupancyType = new JSONObject();
		 occupancyType.put("name", occupancy.getName());
		 occupancyType.put("value",  bpaApplications.stream().filter(bpa->bpa.getOccupanciesName().equalsIgnoreCase(occupancy.getName())).count());
		 buckets.add(occupancyType);
	 });
	
	
	return buckets;
}

public List<JSONObject> getApplicationsBySubOccupancyType(SearchBpaApplicationForm bpaApplicationForm,
		List<BpaApplication> bpaApplications) {

    List<JSONObject> buckets = new ArrayList<JSONObject>();
	 List<SubOccupancy> subOccupancyList = subOccupancyService.findAllByActive();
	 
//	List<BpaApplication> subOccupancyBPA;
	 subOccupancyList.forEach(subOccupancy->{
		 AtomicInteger counter = new AtomicInteger();
		 bpaApplications.forEach(bpa->{
			Plan plan = applicationBpaService.getPlanInfo(bpa.geteDcrNumber());
			if(plan!=null) {
				OccupancyTypeHelper mostRestrictiveFarHelper = plan.getVirtualBuilding() != null
						? plan.getVirtualBuilding().getMostRestrictiveFarHelper()
						: null;
						
						if(mostRestrictiveFarHelper!=null && mostRestrictiveFarHelper.getSubtype().getCode().equalsIgnoreCase(subOccupancy.getCode())	) {
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
		List<BpaApplication> bpaApplications) {
	List<JSONObject> buckets = new ArrayList<JSONObject>();
	bpaApplicationForm.setServiceType("BPA");
	 List<CollectionSummaryReportHelper> bpaCollectionData = getCollectionData(bpaApplicationForm,"USERWISE");
	 return sortedCollectionData(bpaCollectionData,buckets);
		
}

public List<JSONObject> getOCApplicationsCollectionDetails(SearchBpaApplicationForm bpaApplicationForm,
		List<BpaApplication> bpaApplications) {
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
    
//    final StringBuilder selectQueryStr = new StringBuilder(
//    		"SELECT APPLICATIONNUMBER,RECEIPT_NUMBER,PAYMENTDATE,DESCRIPTION" 
//    		);
    final StringBuilder selectQuery = new StringBuilder(
            "SELECT (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='cash' THEN count(distinct(EGCL_COLLECTIONHEADER.ID)) END) AS cashReceipt,  "
                    +
                    "(CASE WHEN EGF_INSTRUMENTTYPE.TYPE='cheque' THEN count(distinct(EGCL_COLLECTIONHEADER.ID)) WHEN EGF_INSTRUMENTTYPE.TYPE='dd' THEN count(distinct(EGCL_COLLECTIONHEADER.ID)) END) AS chequeReceipt, "
                    +
                    " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE= 'online' THEN count(distinct(EGCL_COLLECTIONHEADER.ID)) END) AS onlineReceipt, "
                    +
                    " EGCL_COLLECTIONHEADER.SOURCE , SER.NAME AS serviceName," +
                    " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='cash' THEN SUM(EGF_INSTRUMENTHEADER.INSTRUMENTAMOUNT) END) AS cashAmount, "
                    +
                    " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='cheque' THEN SUM(EGF_INSTRUMENTHEADER.INSTRUMENTAMOUNT) WHEN EGF_INSTRUMENTTYPE.TYPE='dd' THEN SUM(EGF_INSTRUMENTHEADER.INSTRUMENTAMOUNT) END) AS chequeAmount,"
                    +
                    " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE= 'online' THEN SUM(EGF_INSTRUMENTHEADER.INSTRUMENTAMOUNT) END) AS onlineAmount, "
                    +
                    " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='bankchallan' THEN count(distinct(EGCL_COLLECTIONHEADER.ID)) END) AS bankReceipt, "
                    +
                    " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='bankchallan' THEN SUM(EGF_INSTRUMENTHEADER.INSTRUMENTAMOUNT) END) AS bankAmount, "
                    +
                    " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='card' THEN count(distinct(EGCL_COLLECTIONHEADER.ID)) END) AS cardReceipt, "
                    +
                    " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='card' THEN SUM(EGF_INSTRUMENTHEADER.INSTRUMENTAMOUNT) END) AS cardAmount, "
                    +
                    " count(distinct(EGCL_COLLECTIONHEADER.ID)) as totalReceipt ");
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
                        +" INNER JOIN EGBPA_APPLICATION EGBPA_APPLICATION ON EGBPA_APPLICATION.APPLICATIONNUMBER=EGCL_COLLECTIONHEADER.CONSUMERCODE ");
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
                         +" INNER JOIN EGBPA_OCCUPANCY_CERTIFICATE EGBPA_OCCUPANCY_CERTIFICATE ON EGBPA_OCCUPANCY_CERTIFICATE.APPLICATIONNUMBER = EGCL_COLLECTIONHEADER.CONSUMERCODE "
                         +" INNER JOIN EGBPA_APPLICATION EGBPA_APPLICATION ON EGBPA_APPLICATION.ID=EGBPA_OCCUPANCY_CERTIFICATE.PARENT ");
         
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
            "SELECT cast(sum(cashReceipt) AS NUMERIC) AS cashReceipt,cast(sum(chequeReceipt) AS NUMERIC) AS chequeReceipt,cast(sum(onlineReceipt) AS NUMERIC) AS onlineReceipt,source,counterName,employeeName,serviceName,cast(sum(cashAmount) AS NUMERIC) AS cashAmount, cast(sum(chequeAmount) AS NUMERIC) AS chequeAmount, cast(sum(onlineAmount) AS NUMERIC) AS onlineAmount ,USERID,cast(sum(bankReceipt) AS NUMERIC) AS bankReceipt, cast(sum(bankAmount) AS NUMERIC) AS bankAmount, "
                    + "  cast(sum(cardReceipt) AS NUMERIC) AS cardReceipt, cast(sum(cardAmount) AS NUMERIC) AS cardAmount, cast(sum(totalReceipt) AS NUMERIC) as totalReceipt  FROM (");
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
        if (collectionSummaryReport.getCashReceipt() == null)
            collectionSummaryReport.setCashReceipt("");
        if (collectionSummaryReport.getChequeReceipt() == null)
            collectionSummaryReport.setChequeReceipt("");
        if (collectionSummaryReport.getOnlineReceipt() == null)
            collectionSummaryReport.setOnlineReceipt("");
        if (collectionSummaryReport.getBankReceipt() == null)
            collectionSummaryReport.setBankReceipt("");
        if (collectionSummaryReport.getCardReceipt() == null)
            collectionSummaryReport.setCardReceipt("");
        if (collectionSummaryReport.getTotalReceipt() == null)
            collectionSummaryReport.setTotalReceipt("");
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
            .addScalar("cashReceipt", org.hibernate.type.StringType.INSTANCE)
            .addScalar("cashAmount", BigDecimalType.INSTANCE)
            .addScalar("chequeReceipt", org.hibernate.type.StringType.INSTANCE)
            .addScalar("chequeAmount", BigDecimalType.INSTANCE)
            .addScalar("onlineReceipt", org.hibernate.type.StringType.INSTANCE)
            .addScalar("onlineAmount", BigDecimalType.INSTANCE)
            .addScalar("source", org.hibernate.type.StringType.INSTANCE)
            .addScalar("serviceName", org.hibernate.type.StringType.INSTANCE)
//            .addScalar("counterName", org.hibernate.type.StringType.INSTANCE)
//            .addScalar("employeeName", org.hibernate.type.StringType.INSTANCE)
            .addScalar("bankReceipt", org.hibernate.type.StringType.INSTANCE)
            .addScalar("bankAmount", BigDecimalType.INSTANCE)
            .addScalar("cardAmount", BigDecimalType.INSTANCE)
            .addScalar("cardReceipt", org.hibernate.type.StringType.INSTANCE)
            .addScalar("totalReceipt", org.hibernate.type.StringType.INSTANCE)
            .setResultTransformer(Transformers.aliasToBean(CollectionSummaryReportHelper.class));
}

public Criteria buildSearchCriteria(final SearchBpaApplicationForm searchBpaApplicationForm, List<String> statusList) {
	final Criteria criteria = getCurrentSession().createCriteria(BpaApplication.class, "bpaApplication");

	if (searchBpaApplicationForm.getApplicationTypeId() != null) {
		criteria.createAlias("bpaApplication.applicationType", "applicationType");
		criteria.add(Restrictions.eq("applicationType.id", searchBpaApplicationForm.getApplicationTypeId()));
	}

	if (searchBpaApplicationForm.getApplicantName() != null) {
		criteria.createAlias("bpaApplication.owner", "owner");
		criteria.add(
				Restrictions.ilike("owner.name", searchBpaApplicationForm.getApplicantName(), MatchMode.ANYWHERE));
	}

	if (searchBpaApplicationForm.getApplicationNumber() != null) {
		criteria.add(Restrictions.eq("bpaApplication.applicationNumber",
				searchBpaApplicationForm.getApplicationNumber()));
	}
	if (searchBpaApplicationForm.getServiceTypeId() != null) {
		criteria.add(Restrictions.eq("bpaApplication.serviceType.id", searchBpaApplicationForm.getServiceTypeId()));
	}
	if (searchBpaApplicationForm.getServiceType() != null) {
		criteria.createAlias("bpaApplication.serviceType", "serviceType")
				.add(Restrictions.eq("serviceType.description", searchBpaApplicationForm.getServiceType()));
	}
	if (searchBpaApplicationForm.getStatusId() != null) {
		criteria.add(Restrictions.eq("bpaApplication.status.id", searchBpaApplicationForm.getStatusId()));
	}
	if (statusList != null) {
		criteria.createAlias("bpaApplication.status", "status")
				.add(Restrictions.in("status.code", statusList));
	}
	if (searchBpaApplicationForm.getOccupancyId() != null) {
		criteria.createAlias("bpaApplication.occupancy", "occupancy")
				.add(Restrictions.eq("occupancy.id", searchBpaApplicationForm.getOccupancyId()));
	}
	if (searchBpaApplicationForm.getFromDate() != null)
		criteria.add(Restrictions.ge("bpaApplication.applicationDate",
				searchBpaApplicationService.resetFromDateTimeStamp(searchBpaApplicationForm.getFromDate())));
	if (searchBpaApplicationForm.getToDate() != null)
		criteria.add(Restrictions.le("bpaApplication.applicationDate",
				searchBpaApplicationService.resetToDateTimeStamp(searchBpaApplicationForm.getToDate())));
	searchBpaApplicationService.buildCommonSearchCriterias(searchBpaApplicationForm, criteria);

	if (searchBpaApplicationForm.getPlotNumber() != null)
		criteria.add(Restrictions.eq("bpaApplication.plotNumber", searchBpaApplicationForm.getPlotNumber()));

	if (searchBpaApplicationForm.getSector() != null)
		criteria.add(Restrictions.eq("bpaApplication.sector", searchBpaApplicationForm.getSector()));
	
	if (searchBpaApplicationForm.getRiskType() != null) {
		criteria.createAlias("bpaApplication.applicationType", "applicationType");
		criteria.add(Restrictions.eq("applicationType.name", searchBpaApplicationForm.getRiskType()));
	}
	

	criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	return criteria;
}

}
