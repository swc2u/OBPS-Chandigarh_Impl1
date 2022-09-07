/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
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
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
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
 *
 */
package org.egov.collection.web.actions.reports;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.egov.bpa.master.service.ApplicationSubTypeService;
import org.egov.collection.constants.CollectionConstants;
import org.egov.collection.utils.CollectionsUtil;
import org.egov.commons.Bankbranch;
import org.egov.commons.EgwStatus;
import org.egov.commons.entity.Source;
import org.egov.infra.admin.master.entity.Department;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.reporting.engine.ReportDataSourceType;
import org.egov.infra.reporting.engine.ReportFormat;
import org.egov.infra.web.struts.actions.ReportFormAction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Action class for the receipt register report
 */
@ParentPackage("egov")
@Results({
        @Result(name = ReceiptRegisterReportAction.INDEX, location = "receiptRegisterReportUrban-index.jsp"),
        @Result(name = ReceiptRegisterReportAction.REPORT, location = "receiptRegisterReportUrban-report.jsp") })
public class ReceiptRegisterReportActionUrban extends ReportFormAction {

    private static final long serialVersionUID = 1L;

    private static final String EGOV_FROM_DATE = "EGOV_FROM_DATE";
    private static final String EGOV_TO_DATE = "EGOV_TO_DATE";
    private static final String EGOV_DEPT_ID = "EGOV_DEPT_ID";
    private static final String EGOV_PAYMENT_MODE = "EGOV_PAYMENT_MODE";
    private static final String EGOV_STATUS_ID = "EGOV_STATUS_ID";
    private static final String EGOV_SOURCE = "EGOV_SOURCE";
    private static final String EGOV_SERVICE_ID = "EGOV_SERVICE_ID";
    private static final String EGOV_CLASSIFICATION = "EGOV_CLASSIFICATION";
    private static final String EGOV_BRANCH_ID = "EGOV_BRANCH_ID";
    public static final String DROPDOWN_BRANCHUSER_BRANCH = "bankBranchlist";
    public static final String EGOV_APP_TYPE_ID = "EGOV_APP_TYPE_ID";
    
    private Long applicationTypeId;
    private List<Long> appTypeList;
    
    private final Map<String, String> paymentModes = createPaymentModeList();
    private final Map<String, String> sources = createSourceList();
    private CollectionsUtil collectionsUtil;
    private TreeMap<String, String> serviceTypeMap = new TreeMap<String, String>();
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CityService cityService;
    @Autowired
    private ApplicationSubTypeService applicationTypeService;

    /**
     * @return the payment mode list to be shown to user in criteria screen
     */
    private Map<String, String> createPaymentModeList() {
        final Map<String, String> paymentModesMap = new HashMap<String, String>(0);
        paymentModesMap.put(CollectionConstants.INSTRUMENTTYPE_CASH, CollectionConstants.INSTRUMENTTYPE_CASH);
        paymentModesMap.put(CollectionConstants.INSTRUMENTTYPE_CHEQUEORDD, CollectionConstants.INSTRUMENTTYPE_CHEQUEORDD);
        paymentModesMap.put(CollectionConstants.INSTRUMENTTYPE_CARD, CollectionConstants.INSTRUMENTTYPE_CARD);
        paymentModesMap.put(CollectionConstants.INSTRUMENTTYPE_BANK, CollectionConstants.INSTRUMENTTYPE_BANK);
        paymentModesMap.put(CollectionConstants.INSTRUMENTTYPE_ONLINE, CollectionConstants.INSTRUMENTTYPE_ONLINE);
        return paymentModesMap;
    }

    private Map<String, String> createSourceList() {
        final Map<String, String> sourcesMap = new HashMap<String, String>(0);
        sourcesMap.put(Source.APONLINE.toString(), Source.APONLINE.toString());
        sourcesMap.put(Source.ESEVA.toString(), Source.ESEVA.toString());
        sourcesMap.put(Source.MEESEVA.toString(), Source.MEESEVA.toString());
        sourcesMap.put(Source.SYSTEM.toString(), Source.SYSTEM.toString());
        return sourcesMap;
    }

    /*
     * (non-Javadoc)
     * @see org.egov.infra.web.struts.actions.BaseFormAction#prepare()
     */
    @Override
    public void prepare() {
        final Query query = entityManager.createNamedQuery(CollectionConstants.QUERY_RECEIPT_BRANCH, Bankbranch.class);
        addDropdownData(DROPDOWN_BRANCHUSER_BRANCH, query.getResultList());
        setReportFormat(ReportFormat.PDF);
        setDataSourceType(ReportDataSourceType.SQL);
    }

    /**
     * @param collectionsUtil the collections utility object to set
     */
    public void setCollectionsUtil(final CollectionsUtil collectionsUtil) {
        this.collectionsUtil = collectionsUtil;
    }

    /**
     * @return the from date
     */
    public Date getFromDate() {
        return (Date) getReportParam(EGOV_FROM_DATE);
    }

    /**
     * @param fromDate the from date to set
     */
    public void setFromDate(final Date fromDate) {
        setReportParam(EGOV_FROM_DATE, fromDate);
    }

    /**
     * @return the do date
     */
    public String getSource() {
        return (String) getReportParam(EGOV_SOURCE);
    }

    /**
     * @param toDate the to date to set
     */
    public void setSource(final String source) {
        setReportParam(EGOV_SOURCE, source);
    }

    /**
     * @return the do date
     */
    public Date getToDate() {
        return (Date) getReportParam(EGOV_TO_DATE);
    }

    /**
     * @param toDate the to date to set
     */
    public void setToDate(final Date toDate) {
        setReportParam(EGOV_TO_DATE, toDate);
    }

    /**
     * @return the department id
     */
    public Integer getDeptId() {
        return (Integer) getReportParam(EGOV_DEPT_ID);
    }

    /**
     * @param deptId the department id to set
     */
    public void setDeptId(final Integer deptId) {
        setReportParam(EGOV_DEPT_ID, deptId);
    }

    /**
     * @return the payment mode (cash/cheque)
     */
    public String getPaymentMode() {
        return (String) getReportParam(EGOV_PAYMENT_MODE);
    }

    /**
     * @param paymentMode the payment mode to set (cash/cheque)
     */
    public void setPaymentMode(final String paymentMode) {
        setReportParam(EGOV_PAYMENT_MODE, paymentMode);
    }

    /**
     * @return the department id
     */
    public Integer getStatusId() {
        return (Integer) getReportParam(EGOV_STATUS_ID);
    }

    /**
     * @param deptId the department id to set
     */
    public void setStatusId(final Integer statusId) {
        setReportParam(EGOV_STATUS_ID, statusId);
    }

    /**
     * @return the payment modes
     */
    public Map<String, String> getPaymentModes() {
        return paymentModes;
    }
    
    /**
     * 
     * @return
     */
    public Long getApplicationTypeId() {
        return (Long) getReportParam(EGOV_APP_TYPE_ID);
    }

    /**
     * @param applicationTypeId the applicationType Id to set
     */
    public void setApplicationTypeId(final Long applicationTypeId) {
        setReportParam(EGOV_APP_TYPE_ID, applicationTypeId);
    }
    /**
     * 
     * @return
     */
    public List<Long> getappTypeList() {
        return (List<Long>) getReportParam(EGOV_APP_TYPE_ID);
    }

    /**
     * @param applicationTypeId the applicationType Id to set
     */
    public void setAppTypeList(final List<Long> applicationTypeList) {
        setReportParam(EGOV_APP_TYPE_ID, applicationTypeList);
    }

    /**
     * Action method for criteria screen
     *
     * @return index
     */
    @Override
    @Action(value = "/reports/receiptRegisterReportUrban-criteria")
    public String criteria() {
        // Setup drop down data for department list
        addRelatedEntity("department", Department.class, "name");
        addRelatedEntity("status", EgwStatus.class, "description");
        addDropdownData("servicetypeList",
                getPersistenceService().findAllByNamedQuery(CollectionConstants.QUERY_COLLECTION_SERVICS));
        setupDropdownDataExcluding();
        addDropdownData("applicationTypeList",applicationTypeService.getBPAApplicationTypes().stream().filter(appType -> !appType.getName().equalsIgnoreCase("Medium Risk")).collect(Collectors.toList()));

        // Set default values of criteria fields
        setReportParam(EGOV_FROM_DATE, new Date());
        setReportParam(EGOV_TO_DATE, new Date());
        serviceTypeMap.putAll(CollectionConstants.SERVICE_TYPE_CLASSIFICATION);
        serviceTypeMap.remove(CollectionConstants.SERVICE_TYPE_PAYMENT);
        return INDEX;
    }

    @Override
    @Action(value = "/reports/receiptRegisterReportUrban-report")
    public String report() {
    	if (getApplicationTypeId() != null) {
            setApplicationTypeId(getApplicationTypeId());
        }else {
        	setAppTypeList(Arrays.asList(3L,5L));
        }
        if (getSource() == null) {
            setSource("ALL");
        }

        setReportParam(CollectionConstants.LOGO_PATH, cityService.getCityLogoAsStream());
        return super.report();
    }

    @Override
    protected String getReportTemplateName() {
        return CollectionConstants.REPORT_TEMPLATE_RECEIPT_REGISTER;
    }

    public List getReceiptStatuses() {
        return collectionsUtil.getAllReceiptHeaderStatus();
    }

    public Map<String, String> getSources() {
        return sources;
    }

    public Long getServiceId() {
        return (Long) getReportParam(EGOV_SERVICE_ID);
    }

    public void setServiceId(final Long serviceId) {
        setReportParam(EGOV_SERVICE_ID, serviceId);
    }

    public String getClassificationType() {
        return (String) getReportParam(EGOV_CLASSIFICATION);
    }

    public void setClassificationType(final String classification) {
        setReportParam(EGOV_CLASSIFICATION, classification);
    }

    public TreeMap<String, String> getServiceTypeMap() {
        return serviceTypeMap;
    }

    public void setServiceTypeMap(final TreeMap<String, String> serviceTypeMap) {
        this.serviceTypeMap = serviceTypeMap;
    }

    public Long getBranchId() {
        return (Long) getReportParam(EGOV_BRANCH_ID);
    }

    public void setBranchId(final Long branchId) {
        setReportParam(EGOV_BRANCH_ID, branchId);
    }

}
