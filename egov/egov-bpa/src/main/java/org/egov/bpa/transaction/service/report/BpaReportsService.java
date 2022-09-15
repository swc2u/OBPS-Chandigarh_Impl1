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
package org.egov.bpa.transaction.service.report;

import static org.egov.bpa.utils.BpaConstants.ADDING_OF_EXTENSION;
import static org.egov.bpa.utils.BpaConstants.ALTERATION;
import static org.egov.bpa.utils.BpaConstants.AMENITIES;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CANCELLED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_ONEDAYPERMIT;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_REGULAR;
import static org.egov.bpa.utils.BpaConstants.CHANGE_IN_OCCUPANCY;
import static org.egov.bpa.utils.BpaConstants.DEMOLITION;
import static org.egov.bpa.utils.BpaConstants.DIVISION_OF_PLOT;
import static org.egov.bpa.utils.BpaConstants.NEW_CONSTRUCTION;
import static org.egov.bpa.utils.BpaConstants.PERM_FOR_HUT_OR_SHED;
import static org.egov.bpa.utils.BpaConstants.POLE_STRUCTURES;
import static org.egov.bpa.utils.BpaConstants.RECONSTRUCTION;
import static org.egov.bpa.utils.BpaConstants.TOWER_CONSTRUCTION;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.egov.bpa.master.entity.ApplicationSubType;
import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaAppointmentSchedule;
import org.egov.bpa.transaction.entity.PermitNocDocument;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.entity.SlotDetail;
import org.egov.bpa.transaction.entity.dto.BpaRegisterReportHelper;
import org.egov.bpa.transaction.entity.dto.CollectionSummaryHeadwiseReportHelper;
import org.egov.bpa.transaction.entity.dto.CollectionSummaryReportHelper;
import org.egov.bpa.transaction.entity.dto.ReceiptRegisterReportHelper;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationReport;
import org.egov.bpa.transaction.entity.dto.SlotDetailsHelper;
import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.notice.util.BpaNoticeUtil;
import org.egov.bpa.transaction.repository.ApplicationBpaRepository;
import org.egov.bpa.transaction.repository.SlotDetailRepository;
import org.egov.bpa.transaction.repository.specs.BpaReportsSearchSpec;
import org.egov.bpa.transaction.repository.specs.SearchBpaApplnFormSpec;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.BpaAppointmentScheduleService;
import org.egov.bpa.transaction.service.SearchBpaApplicationService;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.collection.constants.CollectionConstants;
import org.egov.collection.entity.CollectionSummaryHeadWiseReport;
import org.egov.collection.entity.CollectionSummaryHeadWiseReportResult;
import org.egov.collection.entity.CollectionSummaryReport;
import org.egov.collection.entity.CollectionSummaryReportResult;
import org.egov.collection.entity.ReceiptHeader;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.workflow.entity.State;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Position;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BpaReportsService {

    public static final String SECTION_CLERK = "SECTION CLERK";
    public static final Long BTK_APPTYPE = 3L;
    public static final Long ATK_APPTYPE = 5L;
    @Autowired
    private SearchBpaApplicationService searchBpaApplicationService;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private BpaNoticeUtil bpaNoticeUtil;
    @Autowired
    private PositionMasterService positionMasterService;
    @Autowired
    private BpaWorkFlowService bpaWorkFlowService;
    @Autowired
    private BpaAppointmentScheduleService bpaAppointmentScheduleService;
    @Autowired
    private ApplicationBpaService bpaCalculationService;
    @Autowired
    private ApplicationBpaRepository applicationBpaRepository;
    @Autowired
    private SlotDetailRepository slotDetailRepository;

    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    public List<SearchBpaApplicationReport> getResultsByServicetypeAndStatus(
            final SearchBpaApplicationForm searchBpaApplicationForm) {
        List<SearchBpaApplicationReport> searchBpaApplicationReportList = new ArrayList<>();
        List<SearchBpaApplicationForm> searchBpaApplnResultList = searchBpaApplicationService
                .search(searchBpaApplicationForm);
        Map<String, Map<String, Long>> resultMap = searchBpaApplnResultList.stream()
                .collect(Collectors.groupingBy(SearchBpaApplicationForm::getStatus,
                        Collectors.groupingBy(SearchBpaApplicationForm::getServiceType, Collectors.counting())));
        for (final Entry<String, Map<String, Long>> statusCountResMap : resultMap.entrySet()) {
            Long newConstruction = 0l;
            Long demolition = 0l;
            Long reConstruction = 0l;
            Long alteration = 0l;
            Long divisionOfPlot = 0l;
            Long addingExtension = 0l;
            Long changeInOccupancy = 0l;
            Long amenities = 0l;
            Long hut = 0l;
            Long towerConstruction = 0l;
            Long poleStructure = 0l;
            SearchBpaApplicationReport bpaApplicationReport = new SearchBpaApplicationReport();
            bpaApplicationReport.setStatus(statusCountResMap.getKey());
            for (final Entry<String, Long> statusCountMap : statusCountResMap.getValue().entrySet()) {
                if (NEW_CONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    newConstruction = newConstruction + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType01(newConstruction);
                } else if (DEMOLITION.equalsIgnoreCase(statusCountMap.getKey())) {
                    demolition = demolition + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType02(demolition);
                } else if (RECONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    reConstruction = reConstruction + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType03(reConstruction);
                } else if (ALTERATION.equalsIgnoreCase(statusCountMap.getKey())) {
                    alteration = alteration + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType04(alteration);
                } else if (DIVISION_OF_PLOT.equalsIgnoreCase(statusCountMap.getKey())) {
                    divisionOfPlot = divisionOfPlot + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType05(divisionOfPlot);
                } else if (ADDING_OF_EXTENSION.equalsIgnoreCase(statusCountMap.getKey())) {
                    addingExtension = addingExtension + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType06(addingExtension);
                } else if (CHANGE_IN_OCCUPANCY.equalsIgnoreCase(statusCountMap.getKey())) {
                    changeInOccupancy = changeInOccupancy + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType07(changeInOccupancy);
                } else if (AMENITIES.equalsIgnoreCase(statusCountMap.getKey())) {
                    amenities = amenities + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType08(amenities);
                } else if (PERM_FOR_HUT_OR_SHED.equalsIgnoreCase(statusCountMap.getKey())) {
                    hut = hut + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType09(hut);
                } else if (TOWER_CONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    towerConstruction = towerConstruction + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType14(towerConstruction);
                } else if (POLE_STRUCTURES.equalsIgnoreCase(statusCountMap.getKey())) {
                    poleStructure = poleStructure + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType15(poleStructure);
                }
            }
            searchBpaApplicationReportList.add(bpaApplicationReport);
        }
        return searchBpaApplicationReportList;
    }
    
    
    public List<SearchBpaApplicationReport> getResultsByServicetypeAndStatusForUrban(
            final SearchBpaApplicationForm searchBpaApplicationForm) {
        List<SearchBpaApplicationReport> searchBpaApplicationReportList = new ArrayList<>();
        List<SearchBpaApplicationForm> searchBpaApplnResultList = new ArrayList<>();
        if(searchBpaApplicationForm.getApplicationTypeId()==null) {
        	searchBpaApplicationForm.setApplicationTypeId(BTK_APPTYPE);
        	 searchBpaApplnResultList = searchBpaApplicationService.search(searchBpaApplicationForm);
        	searchBpaApplicationForm.setApplicationTypeId(ATK_APPTYPE);
        	List<SearchBpaApplicationForm> searchATKApplnResultList = searchBpaApplicationService.search(searchBpaApplicationForm);
        	searchBpaApplnResultList.addAll(searchATKApplnResultList);
        }else {
        	searchBpaApplnResultList = searchBpaApplicationService.search(searchBpaApplicationForm);
        }
        
       
        Map<String, Map<String, Long>> resultMap = searchBpaApplnResultList.stream()
                .collect(Collectors.groupingBy(SearchBpaApplicationForm::getStatus,
                        Collectors.groupingBy(SearchBpaApplicationForm::getServiceType, Collectors.counting())));
        for (final Entry<String, Map<String, Long>> statusCountResMap : resultMap.entrySet()) {
            Long newConstruction = 0l;
            Long demolition = 0l;
            Long reConstruction = 0l;
            Long alteration = 0l;
            Long divisionOfPlot = 0l;
            Long addingExtension = 0l;
            Long changeInOccupancy = 0l;
            Long amenities = 0l;
            Long hut = 0l;
            Long towerConstruction = 0l;
            Long poleStructure = 0l;
            SearchBpaApplicationReport bpaApplicationReport = new SearchBpaApplicationReport();
            bpaApplicationReport.setStatus(statusCountResMap.getKey());
            for (final Entry<String, Long> statusCountMap : statusCountResMap.getValue().entrySet()) {
                if (NEW_CONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    newConstruction = newConstruction + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType01(newConstruction);
                } else if (DEMOLITION.equalsIgnoreCase(statusCountMap.getKey())) {
                    demolition = demolition + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType02(demolition);
                } else if (RECONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    reConstruction = reConstruction + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType03(reConstruction);
                } else if (ALTERATION.equalsIgnoreCase(statusCountMap.getKey())) {
                    alteration = alteration + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType04(alteration);
                } else if (DIVISION_OF_PLOT.equalsIgnoreCase(statusCountMap.getKey())) {
                    divisionOfPlot = divisionOfPlot + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType05(divisionOfPlot);
                } else if (ADDING_OF_EXTENSION.equalsIgnoreCase(statusCountMap.getKey())) {
                    addingExtension = addingExtension + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType06(addingExtension);
                } else if (CHANGE_IN_OCCUPANCY.equalsIgnoreCase(statusCountMap.getKey())) {
                    changeInOccupancy = changeInOccupancy + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType07(changeInOccupancy);
                } else if (AMENITIES.equalsIgnoreCase(statusCountMap.getKey())) {
                    amenities = amenities + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType08(amenities);
                } else if (PERM_FOR_HUT_OR_SHED.equalsIgnoreCase(statusCountMap.getKey())) {
                    hut = hut + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType09(hut);
                } else if (TOWER_CONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    towerConstruction = towerConstruction + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType14(towerConstruction);
                } else if (POLE_STRUCTURES.equalsIgnoreCase(statusCountMap.getKey())) {
                    poleStructure = poleStructure + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType15(poleStructure);
                }
            }
            searchBpaApplicationReportList.add(bpaApplicationReport);
        }
        return searchBpaApplicationReportList;
    }
    

    public List<SearchBpaApplicationReport> getResultsForEachServicetypeByZone(
            final SearchBpaApplicationForm searchBpaApplicationForm) {
        List<SearchBpaApplicationReport> searchBpaApplicationReportList = new ArrayList<>();
        List<SearchBpaApplicationForm> searchBpaApplnResultList = searchBpaApplicationService
                .search(searchBpaApplicationForm);
        Map<String, Map<String, Long>> resultMap = searchBpaApplnResultList.stream()
                .collect(Collectors.groupingBy(SearchBpaApplicationForm::getServiceType,
                        Collectors.groupingBy(SearchBpaApplicationForm::getZone, Collectors.counting())));
        for (final Entry<String, Map<String, Long>> statusCountResMap : resultMap.entrySet()) {
            Long zone1 = 0l;
            Long zone2 = 0l;
            Long zone3 = 0l;
            Long zone4 = 0l;
            SearchBpaApplicationReport bpaApplicationReport = new SearchBpaApplicationReport();
            bpaApplicationReport.setServiceType(statusCountResMap.getKey());
            for (final Entry<String, Long> statusCountMap : statusCountResMap.getValue().entrySet()) {

                if ("ZONE-1 (MAIN OFFICE)".equalsIgnoreCase(statusCountMap.getKey())) {
                    zone1 = zone1 + statusCountMap.getValue();
                    bpaApplicationReport.setZone1N(zone1);
                } else if ("ZONE-2 (ELATHUR ZONAL OFFICE)".equalsIgnoreCase(statusCountMap.getKey())) {
                    zone2 = zone2 + statusCountMap.getValue();
                    bpaApplicationReport.setZone2(zone2);
                } else if ("ZONE-3 (BEYPORE ZONAL OFFICE)".equalsIgnoreCase(statusCountMap.getKey())) {
                    zone3 = zone3 + statusCountMap.getValue();
                    bpaApplicationReport.setZone3(zone3);
                } else if ("ZONE-4 (CHERUVANNURE NALLALAM ZONAL OFFICE)".equalsIgnoreCase(statusCountMap.getKey())) {
                    zone4 = zone4 + statusCountMap.getValue();
                    bpaApplicationReport.setZone4(statusCountMap.getValue());
                }
            }
            searchBpaApplicationReportList.add(bpaApplicationReport);
        }
        return searchBpaApplicationReportList;
    }

    public Page<SlotDetailsHelper> searchSlotDetails(final SlotDetailsHelper slotDetailsHelper, final String applicationType) {
        final Pageable pageable = new PageRequest(slotDetailsHelper.pageNumber(),
                slotDetailsHelper.pageSize(), slotDetailsHelper.orderDir(), slotDetailsHelper.orderBy());
        Page<SlotDetail> slotDetails = slotDetailRepository
                .findAll(BpaReportsSearchSpec.searchReportsSpecification(slotDetailsHelper), pageable);
        return new PageImpl<>(buildSlotDetailsResponse(slotDetails), pageable, slotDetails.getTotalElements());
    }

    private List<SlotDetailsHelper> buildSlotDetailsResponse(final Page<SlotDetail> slotDetails) {
        List<SlotDetailsHelper> slotDetailsHelperList = new ArrayList<>();
        for (SlotDetail slotDetail : slotDetails) {
            SlotDetailsHelper slotDetailsHelper = new SlotDetailsHelper();
            slotDetailsHelper.setApplicationType(slotDetail.getSlot().getType());
            slotDetailsHelper.setSlotId(slotDetail.getSlot().getId());
            slotDetailsHelper.setAppointmentDate(slotDetail.getSlot().getAppointmentDate());
            slotDetailsHelper.setZone(slotDetail.getSlot().getZone().getName());
            slotDetailsHelper.setZoneId(slotDetail.getSlot().getZone().getId());
            if (slotDetail.getSlot().getElectionWard() != null) {
                slotDetailsHelper.setElectionWard(slotDetail.getSlot().getElectionWard().getName());
                slotDetailsHelper.setElectionWardId(slotDetail.getSlot().getElectionWard().getId());
            }
            slotDetailsHelper.setSlotDetailsId(slotDetail.getId());
            slotDetailsHelper.setAppointmentTime(slotDetail.getAppointmentTime());
            slotDetailsHelper.setMaxScheduledSlots(slotDetail.getMaxScheduledSlots());
            slotDetailsHelper.setMaxRescheduledSlots(slotDetail.getMaxRescheduledSlots());
            slotDetailsHelper.setUtilizedScheduledSlots(slotDetail.getUtilizedScheduledSlots());
            slotDetailsHelper.setUtilizedRescheduledSlots(slotDetail.getUtilizedRescheduledSlots());
            slotDetailsHelperList.add(slotDetailsHelper);
        }
        return slotDetailsHelperList;
    }

    public Page<BpaRegisterReportHelper> getBpaRegisterReportDetails(final SearchBpaApplicationForm searchRequest,
            final List<Long> userIds) {
        final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());
        List<Long> positionIds = new ArrayList<>();
        for (Long userId : userIds) {
            Position position = getUserPositionByUserId(userId);
            if (position != null)
                positionIds.add(getUserPositionByUserId(userId).getId());
        }

        Page<BpaApplication> bpaApplications = applicationBpaRepository
                .findAll(SearchBpaApplnFormSpec.searchBpaRegisterSpecification(searchRequest, positionIds), pageable);
        return new PageImpl<>(new ArrayList<>(buildBpaRegisterReportResponse(bpaApplications, userIds)), pageable,
                bpaApplications.getTotalElements());
    }
    
    public Page<ReceiptRegisterReportHelper> getReceiptRegisterReportDetailsForUrban(SearchBpaApplicationForm searchRequest, List<Long> appTypeList) {
    	 final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                 searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());

    	 final Page<ReceiptRegisterReportHelper> receiptRegisterReportList = getReceiptRegisterData(searchRequest,pageable,appTypeList);
    	 
    	 receiptRegisterReportList.forEach(receiptReport->{
    		 List<ReceiptRegisterReportHelper> feeList = getFeeDetails(receiptReport.getId());
    		 feeList.forEach(fee->{
    			 if(fee.getSecurityFee()!=null && fee.getSecurityFee()!=0)
    				 receiptReport.setSecurityFee(fee.getSecurityFee());
    			 if(fee.getScrutinyFee()!=null && fee.getScrutinyFee()!=0)
    				 receiptReport.setScrutinyFee(fee.getScrutinyFee());
    			 if(fee.getGst()!=null && fee.getGst()!=0)
    				 receiptReport.setGst(fee.getGst());
    			 if(fee.getLabourCess()!=null && fee.getLabourCess()!=0)
    				 receiptReport.setLabourCess(fee.getLabourCess());
    			 if(fee.getAdditionFee()!=null && fee.getAdditionFee()!=0)
    				 receiptReport.setAdditionFee(fee.getAdditionFee());
    			 if(fee.getRule5()!=null && fee.getRule5()!=0)
    				 receiptReport.setRule5(fee.getRule5());
    		 });
    		 receiptReport.setTotalWithoutLaboutCess(Double.sum(receiptReport.getScrutinyFee(),
           Double.sum(receiptReport.getSecurityFee(),
           		Double.sum(receiptReport.getRule5(),Double.sum(receiptReport.getAdditionFee(),receiptReport.getGst())))));
    		 receiptReport.setTotal(Double.sum(receiptReport.getTotalWithoutLaboutCess(), receiptReport.getLabourCess()));

    	 });
//        return receiptRegisterReportList;
        return new PageImpl<>(receiptRegisterReportList.getContent(), pageable,
        		 receiptRegisterReportList.getTotalElements());
	}
    

	public Page<ReceiptRegisterReportHelper> getReceiptRegisterReportDetailsForOC(SearchBpaApplicationForm searchRequest,
			List<Long> appTypeList) {
		final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());

   	 final Page<ReceiptRegisterReportHelper> receiptRegisterReportList = getOCReceiptRegisterData(searchRequest,pageable,appTypeList);
   	 
   	 receiptRegisterReportList.forEach(receiptReport->{
   		 List<ReceiptRegisterReportHelper> feeList = getFeeDetails(receiptReport.getId());
   		 feeList.forEach(fee->{
   			 if(fee.getSecurityFee()!=null && fee.getSecurityFee()!=0)
   				 receiptReport.setSecurityFee(fee.getSecurityFee());
   			 if(fee.getScrutinyFee()!=null && fee.getScrutinyFee()!=0)
   				 receiptReport.setScrutinyFee(fee.getScrutinyFee());
   			 if(fee.getGst()!=null && fee.getGst()!=0)
   				 receiptReport.setGst(fee.getGst());
   			 if(fee.getLabourCess()!=null && fee.getLabourCess()!=0)
   				 receiptReport.setLabourCess(fee.getLabourCess());
   			 if(fee.getAdditionFee()!=null && fee.getAdditionFee()!=0)
   				 receiptReport.setAdditionFee(fee.getAdditionFee());
   			 if(fee.getRule5()!=null && fee.getRule5()!=0)
   				 receiptReport.setRule5(fee.getRule5());
   		 });
   		 receiptReport.setTotalWithoutLaboutCess(Double.sum(receiptReport.getScrutinyFee(),
          Double.sum(receiptReport.getSecurityFee(),
          		Double.sum(receiptReport.getRule5(),Double.sum(receiptReport.getAdditionFee(),receiptReport.getGst())))));
   		 receiptReport.setTotal(Double.sum(receiptReport.getTotalWithoutLaboutCess(), receiptReport.getLabourCess()));

   	 });
//       return receiptRegisterReportList;
       return new PageImpl<>(receiptRegisterReportList.getContent(), pageable,
       		 receiptRegisterReportList.getTotalElements());
	}
	

	private Set<BpaRegisterReportHelper> buildBpaRegisterReportResponse(final Page<BpaApplication> bpaApplications,
            final List<Long> userIds) {
        Set<BpaRegisterReportHelper> bpaRegisterReportHelperList = new HashSet<>();
        for (BpaApplication application : bpaApplications) {
            BpaRegisterReportHelper bpaRegister = new BpaRegisterReportHelper();
            bpaRegister.setId(application.getId());
            bpaRegister.setApplicationNumber(application.getApplicationNumber());
            bpaRegister.setServiceType(application.getServiceType().getDescription());
            bpaRegister.setApplicationType(application.getApplicationType().getDescription());
            bpaRegister.setDateOfAdmission(application.getApplicationDate());
            bpaRegister.setPermitType(
                    application.getIsOneDayPermitApplication() ? APPLICATION_TYPE_ONEDAYPERMIT
                            : APPLICATION_TYPE_REGULAR);
            bpaRegister.setDateOfAdmission(application.getApplicationDate());
            bpaRegister.setApplicantName(application.getOwner().getName());
            bpaRegister.setAddress(application.getOwner().getAddress());
            for (SiteDetail siteDetail : application.getSiteDetail()) {
                bpaRegister.setSurveyNumber(siteDetail.getReSurveyNumber());
                bpaRegister
                        .setVillage(siteDetail.getLocationBoundary() == null ? "" : siteDetail.getLocationBoundary().getName());
                bpaRegister.setRevenueWard(siteDetail.getAdminBoundary() == null ? "" : siteDetail.getAdminBoundary().getName());
                bpaRegister.setElectionWard(
                        siteDetail.getElectionBoundary() == null ? "" : siteDetail.getElectionBoundary().getName());
            }
            bpaRegister.setNatureOfOccupancy(application.getOccupanciesName());
            if (!application.getBuildingDetail().isEmpty()
                    && application.getBuildingDetail().get(0).getTotalPlintArea() != null) {
                bpaRegister.setNumberOfFloors(application.getBuildingDetail().get(0).getFloorCount());
                BigDecimal totalFloorArea = bpaCalculationService.getTotalFloorArea(application);
                BigDecimal existBldgFloorArea = bpaCalculationService.getExistBldgTotalFloorArea(application);
                totalFloorArea = totalFloorArea.add(existBldgFloorArea);
                bpaRegister.setTotalFloorArea(totalFloorArea.setScale(3, RoundingMode.HALF_UP));
            }

            bpaRegister.setFar(searchBpaApplicationService.getFar(application));
            bpaRegister.setApplicationFee(application.getAdmissionfeeAmount());
            if (!application.getSlotApplications().isEmpty())
                bpaRegister.setDocVerificationDate(
                        application.getSlotApplications().get(application.getSlotApplications().size() - 1)
                                .getSlotDetail().getSlot().getAppointmentDate());
            List<BpaAppointmentSchedule> appointmentScheduledList = bpaAppointmentScheduleService
                    .findByApplication(application, AppointmentSchedulePurpose.INSPECTION);
            if (!appointmentScheduledList.isEmpty())
                bpaRegister.setFieldVerificationDate(appointmentScheduledList.get(0).getAppointmentDate());
            bpaRegister.setNocDetails(getNocDetails(application));
            bpaRegister.setBuildingPermitNo(application.getPlanPermissionNumber());
            /*if (!application.getPermitFee().isEmpty()) {
                BigDecimal permitFee = BigDecimal.ZERO;
                BigDecimal additionalFee = BigDecimal.ZERO;
                BigDecimal otherFee = BigDecimal.ZERO;
                BigDecimal shelterFund = BigDecimal.ZERO;
                BigDecimal labourcess = BigDecimal.ZERO;
                BigDecimal developmentPermitFees = BigDecimal.ZERO;
                for (ApplicationFeeDetail appFeeDtl : application.getPermitFee().get(0).getApplicationFee()
                        .getApplicationFeeDetail()) {
                    if (appFeeDtl.getBpaFeeMapping().getBpaFeeCommon().getDescription().equalsIgnoreCase(BPA_ADDITIONAL_FEE))
                        additionalFee = appFeeDtl.getAmount();
                    else if (appFeeDtl.getBpaFeeMapping().getBpaFeeCommon().getDescription().equalsIgnoreCase(BPA_OTHER_FEE))
                        otherFee = appFeeDtl.getAmount();
                    else if (SHELTERFUND.equals(appFeeDtl.getBpaFeeMapping().getBpaFeeCommon().getName())) {
                        shelterFund = appFeeDtl.getAmount();
                    } else if (LABOURCESS.equals(appFeeDtl.getBpaFeeMapping().getBpaFeeCommon().getName())) {
                        labourcess = appFeeDtl.getAmount();
                    } else if (DEV_PERMIT_FEE.equals(appFeeDtl.getBpaFeeMapping().getBpaFeeCommon().getName())) {
                        developmentPermitFees = appFeeDtl.getAmount();
                    } else {
                        permitFee = permitFee.add(appFeeDtl.getAmount());
                    }
                }
                bpaRegister.setPermitFee(permitFee);
                bpaRegister.setAdditionalFee(additionalFee);
                bpaRegister.setOtherFee(otherFee);
                bpaRegister.setShelterFund(shelterFund);
                bpaRegister.setLabourcess(labourcess);
                bpaRegister.setDevelopmentPermitFees(developmentPermitFees);
            }*/
            if (application.getStatus().getCode().equalsIgnoreCase(APPLICATION_STATUS_CANCELLED)) {
                bpaRegister.setRejectionReason(bpaNoticeUtil.buildRejectionReasons(application, false));
                Assignment rejectedOwner;
                Assignment assignment = bpaWorkFlowService.getApproverAssignmentByDate(
                        application.getCurrentState().getPreviousOwner(), application.getState().getLastModifiedDate());
                if (application.getCurrentState().getOwnerUser() != null) {
                    List<Assignment> assignments = bpaWorkFlowService.getAssignmentByPositionAndUserAsOnDate(
                            application.getCurrentState().getOwnerPosition().getId(),
                            application.getCurrentState().getOwnerUser().getId(),
                            application.getCurrentState().getLastModifiedDate());
                    if (!assignments.isEmpty())
                        assignment = assignments.get(0);
                }

                if (assignment == null)
                    assignment = bpaWorkFlowService
                            .getAssignmentsByPositionAndDate(application.getCurrentState().getPreviousOwner().getId(),
                                    application.getState().getLastModifiedDate())
                            .get(0);

                User lastModifiedBy = application.getCurrentState().getLastModifiedBy();
                if (lastModifiedBy.getName().equalsIgnoreCase("System")) {
                    bpaRegister.setApprvd_rejected_by(lastModifiedBy.getName().concat(" :: ").concat(lastModifiedBy.getName()));
                } else {
                    if (assignment != null && assignment.getDesignation().getName().equalsIgnoreCase(SECTION_CLERK)) {
                        rejectedOwner = bpaWorkFlowService.getApproverAssignmentByDate(
                                application.getCurrentState().getOwnerPosition(), application.getState().getLastModifiedDate());
                        if (application.getCurrentState().getOwnerUser() != null) {
                            List<Assignment> assignments = bpaWorkFlowService.getAssignmentByPositionAndUserAsOnDate(
                                    application.getCurrentState().getOwnerPosition().getId(),
                                    application.getCurrentState().getOwnerUser().getId(),
                                    application.getCurrentState().getLastModifiedDate());
                            if (!assignments.isEmpty())
                                rejectedOwner = assignments.get(0);
                        }
                        if (rejectedOwner == null)
                            rejectedOwner = bpaWorkFlowService
                                    .getAssignmentsByPositionAndDate(application.getCurrentState().getOwnerPosition().getId(),
                                            application.getState().getLastModifiedDate())
                                    .get(0);
                    } else {
                        rejectedOwner = assignment;
                    }
                    bpaRegister.setApprvd_rejected_by(rejectedOwner == null ? "N/A"
                            : rejectedOwner.getEmployee().getName().concat(" :: ")
                                    .concat(rejectedOwner.getDesignation().getName()));
                }
            } else {
                bpaRegister.setFinalApprovalDate(application.getPlanPermissionDate());
                bpaRegister.setApprvd_rejected_by(bpaNoticeUtil.getApproverName(application).concat(" :: ").concat(bpaNoticeUtil
                        .getApproverDesignation(application.getApproverPosition())));
            }
            bpaRegisterReportHelperList.add(bpaRegister);
        }
        return bpaRegisterReportHelperList;
    }

    private String getNocDetails(final BpaApplication application) {
        StringBuilder nocDetails = new StringBuilder();
        int additionalOrder = 1;
        for (PermitNocDocument nocDoc : application.getPermitNocDocuments()) {
            nocDetails.append(String.valueOf(additionalOrder) + ") "
                    + nocDoc.getNocDocument().getServiceChecklist().getChecklist().getDescription() + " : "
                    + (nocDoc.getNocDocument().getNocStatus() != null ? nocDoc.getNocDocument().getNocStatus().getNocStatusVal()
                            : "N/A")
                    + "\n");
            additionalOrder++;
        }
        return nocDetails.toString();
    }

    public Boolean checkUserInWorkFlow(final BpaApplication application, final Long userId) {
        final State<Position> workflowState = application.getState();
        if (null != workflowState) {
            if (null != application.getStateHistory() && !application.getStateHistory().isEmpty())
                Collections.reverse(application.getStateHistory());
            Position userPosition = getUserPositionByUserId(userId);
            for (final StateHistory<Position> stateHistory : application.getStateHistory()) {
                Position owner = stateHistory.getOwnerPosition();
                if (userPosition != null && userPosition.getId().equals(owner.getId()))
                    return true;
            }
        }
        return false;
    }

    public Boolean checkApplicationPresentInInbox(final BpaApplication application, final Long userId) {
        final State<Position> workflowState = application.getState();
        if (null != workflowState) {
            Position userPosition = getUserPositionByUserId(userId);
            Position owner = workflowState.getOwnerPosition();
            if (userPosition != null && userPosition.getId().equals(owner.getId()))
                return true;
        }
        return false;
    }

    public Position getUserPositionByUserId(Long userId) {
        return positionMasterService.getPositionByUserId(userId);
    }
    
    private  Page<ReceiptRegisterReportHelper> getReceiptRegisterData(SearchBpaApplicationForm searchRequest, Pageable pageable, List<Long> appTypeList) {
    	final SimpleDateFormat fromDateFormatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        final SimpleDateFormat toDateFormatter = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        final StringBuilder defaultQueryStr = new StringBuilder(500);

//        final StringBuilder selectQueryStr = new StringBuilder(
//        		"SELECT APPLICATIONNUMBER,RECEIPT_NUMBER,PAYMENTDATE,DESCRIPTION" 
//        		);
        final StringBuilder subSelectQueryStr = new StringBuilder("SELECT "
        		+ "EGCL_COLLECTIONHEADER.ID as id, "
        		+ "	EGBPA_APPLICATION.APPLICATIONNUMBER as applicationNumber,"
        		+ "	EGBPA_APPLICATION.SECTOR as SECTOR,"
        		+ "	EGBPA_APPLICATION.PLOTNUMBER as PLOTNUMBER,"
        		+ "	 EGCL_COLLECTIONHEADER.RECEIPTNUMBER AS receiptNumber,"
        		+ "     EGCL_COLLECTIONHEADER.RECEIPTDATE AS PAYMENTDATE ,"
        		+ "	 EGBPA_APPLICATION.FILENUMBER as FILENUMBER"
        		+ "	 FROM "
        		+ "     EGCL_COLLECTIONHEADER EGCL_COLLECTIONHEADER "
        		+ "	 INNER JOIN EGCL_COLLECTIONINSTRUMENT EGCL_COLLECTIONINSTRUMENT "
        		+ "	 ON EGCL_COLLECTIONHEADER.ID = EGCL_COLLECTIONINSTRUMENT.COLLECTIONHEADER"
        		+ "     INNER JOIN EGF_INSTRUMENTHEADER EGF_INSTRUMENTHEADER ON EGCL_COLLECTIONINSTRUMENT.INSTRUMENTHEADER = EGF_INSTRUMENTHEADER.ID"
        		+ "     INNER JOIN EGW_STATUS EGW_STATUS ON EGCL_COLLECTIONHEADER.STATUS = EGW_STATUS.ID"
        		+ "     INNER JOIN EGF_INSTRUMENTTYPE EGF_INSTRUMENTTYPE ON EGF_INSTRUMENTHEADER.INSTRUMENTTYPE = EGF_INSTRUMENTTYPE.ID"
        		+ "     INNER JOIN EGCL_COLLECTIONMIS EGCL_COLLECTIONMIS ON EGCL_COLLECTIONHEADER.ID = EGCL_COLLECTIONMIS.COLLECTIONHEADER"
        		+ "     INNER JOIN EG_DEPARTMENT EG_DEPARTMENT ON EG_DEPARTMENT.ID = EGCL_COLLECTIONMIS.DEPARTMENT"
        		+ "     INNER JOIN EGCL_SERVICEDETAILS EGCL_SERVICEDETAILS ON EGCL_SERVICEDETAILS.ID = EGCL_COLLECTIONHEADER.SERVICEDETAILS"
        		+ "     INNER JOIN state.EG_USER EG_USER ON EG_USER.ID = EGCL_COLLECTIONHEADER.CREATEDBY"
        		+ "	 INNER JOIN EGBPA_APPLICATION EGBPA_APPLICATION on EGBPA_APPLICATION.APPLICATIONNUMBER = EGCL_COLLECTIONHEADER.CONSUMERCODE"
        		);       	  
		 
        final StringBuilder whereQueryStr = new StringBuilder(" WHERE ");
//        final StringBuilder whereQueryStr = new StringBuilder(" WHERE EGCL_COLLECTIONDETAILS.ISACTUALDEMAND = true ");
        
        final StringBuilder subOrderByQueryStr = new StringBuilder(" ORDER BY "+
        	     " EGCL_COLLECTIONHEADER.RECEIPTDATE,"+
        	     " EGCL_COLLECTIONHEADER.RECEIPTNUMBER,"+
        	     " EG_DEPARTMENT.NAME");
        
        if(appTypeList!=null) {
        	whereQueryStr.append("EGBPA_APPLICATION.APPLICATIONSUBTYPE in ( :appTypeList)");
        }
        if (searchRequest.getFromDate() != null && searchRequest.getToDate() != null) {
            whereQueryStr.append(" AND EGCL_COLLECTIONHEADER.RECEIPTDATE between to_timestamp('"
                    + fromDateFormatter.format(searchRequest.getFromDate()) + "', 'YYYY-MM-DD HH24:MI:SS') and " + " to_timestamp('"
                    + toDateFormatter.format(searchRequest.getToDate()) + "', 'YYYY-MM-DD HH24:MI:SS') ");
        }
       
        if (StringUtils.isNotBlank(searchRequest.getPaymentMode()) && !searchRequest.getPaymentMode().equals(CollectionConstants.ALL)) 
            whereQueryStr.append(" AND EGF_INSTRUMENTTYPE.TYPE in ( :paymentMode )");

        final StringBuilder finalQuery = new StringBuilder(subSelectQueryStr).append(whereQueryStr)
                .append(subOrderByQueryStr);
        
        System.out.println("finalQuery::::"+finalQuery);
        
        final SQLQuery registerQuery = (SQLQuery) getCurrentSession().createSQLQuery(finalQuery.toString())
                .addScalar("applicationNumber", org.hibernate.type.StringType.INSTANCE)
                .addScalar("sector", org.hibernate.type.StringType.INSTANCE)
                .addScalar("plotNumber", org.hibernate.type.StringType.INSTANCE)
                .addScalar("receiptNumber", org.hibernate.type.StringType.INSTANCE)
                .addScalar("paymentDate", org.hibernate.type.DateType.INSTANCE)
                .addScalar("fileNumber", org.hibernate.type.StringType.INSTANCE)
                .addScalar("id",LongType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(ReceiptRegisterReportHelper.class));

//        if (searchRequest.getApplicationTypeId() != null && searchRequest.getApplicationTypeId() !=-1) {
//        	registerQuery.setLong("applicationTypeId", searchRequest.getApplicationTypeId());
//	     }


        if (StringUtils.isNotBlank(searchRequest.getPaymentMode()) && !searchRequest.getPaymentMode().equals(CollectionConstants.ALL))
        	registerQuery.setString("paymentMode", searchRequest.getPaymentMode());
        
        if(appTypeList!=null)
        	registerQuery.setParameterList("appTypeList", appTypeList);
        
        List<ReceiptRegisterReportHelper> receiptReportResultList = populateQueryResults(registerQuery.list());
////        final receiptRegisterReportHelperResult collResult = new receiptRegisterReportHelperResult();
//        
//        collResult.setAggrCollectionSummaryReportList(aggrReportResults);
//        collResult.setRebateCollectionSummaryReportList(rebateReportResultList);
        
        
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), receiptReportResultList.size());
        final Page<ReceiptRegisterReportHelper> page = new PageImpl<>(receiptReportResultList.subList(start, end), pageable, receiptReportResultList.size());
        
        return page;
	}
    
    private  Page<ReceiptRegisterReportHelper> getOCReceiptRegisterData(SearchBpaApplicationForm searchRequest, Pageable pageable, List<Long> appTypeList) {
    	final SimpleDateFormat fromDateFormatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        final SimpleDateFormat toDateFormatter = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        final StringBuilder defaultQueryStr = new StringBuilder(500);

//        final StringBuilder selectQueryStr = new StringBuilder(
//        		"SELECT APPLICATIONNUMBER,RECEIPT_NUMBER,PAYMENTDATE,DESCRIPTION" 
//        		);
        final StringBuilder subSelectQueryStr = new StringBuilder("SELECT "
        		+ "EGCL_COLLECTIONHEADER.ID as id, "
        		+ "	EGBPA_APPLICATION.APPLICATIONNUMBER as applicationNumber,"
        		+ "	EGBPA_APPLICATION.SECTOR as SECTOR,"
        		+ "	EGBPA_APPLICATION.PLOTNUMBER as PLOTNUMBER,"
        		+ "	 EGCL_COLLECTIONHEADER.RECEIPTNUMBER AS receiptNumber,"
        		+ "     EGCL_COLLECTIONHEADER.RECEIPTDATE AS PAYMENTDATE ,"
        		+ "	 EGBPA_APPLICATION.FILENUMBER as FILENUMBER"
        		+ "	 FROM "
        		+ "     EGCL_COLLECTIONHEADER EGCL_COLLECTIONHEADER "
        		+ "	 INNER JOIN EGCL_COLLECTIONINSTRUMENT EGCL_COLLECTIONINSTRUMENT "
        		+ "	 ON EGCL_COLLECTIONHEADER.ID = EGCL_COLLECTIONINSTRUMENT.COLLECTIONHEADER"
        		+ "     INNER JOIN EGF_INSTRUMENTHEADER EGF_INSTRUMENTHEADER ON EGCL_COLLECTIONINSTRUMENT.INSTRUMENTHEADER = EGF_INSTRUMENTHEADER.ID"
        		+ "     INNER JOIN EGW_STATUS EGW_STATUS ON EGCL_COLLECTIONHEADER.STATUS = EGW_STATUS.ID"
        		+ "     INNER JOIN EGF_INSTRUMENTTYPE EGF_INSTRUMENTTYPE ON EGF_INSTRUMENTHEADER.INSTRUMENTTYPE = EGF_INSTRUMENTTYPE.ID"
        		+ "     INNER JOIN EGCL_COLLECTIONMIS EGCL_COLLECTIONMIS ON EGCL_COLLECTIONHEADER.ID = EGCL_COLLECTIONMIS.COLLECTIONHEADER"
        		+ "     INNER JOIN EG_DEPARTMENT EG_DEPARTMENT ON EG_DEPARTMENT.ID = EGCL_COLLECTIONMIS.DEPARTMENT"
        		+ "     INNER JOIN EGCL_SERVICEDETAILS EGCL_SERVICEDETAILS ON EGCL_SERVICEDETAILS.ID = EGCL_COLLECTIONHEADER.SERVICEDETAILS"
        		+ "     INNER JOIN state.EG_USER EG_USER ON EG_USER.ID = EGCL_COLLECTIONHEADER.CREATEDBY"
        		+ "	 	INNER JOIN EGBPA_OCCUPANCY_CERTIFICATE EGBPA_OCCUPANCY_CERTIFICATE on EGBPA_OCCUPANCY_CERTIFICATE.APPLICATIONNUMBER = EGCL_COLLECTIONHEADER.CONSUMERCODE"
        		+ " 	INNER JOIN EGBPA_APPLICATION EGBPA_APPLICATION ON EGBPA_APPLICATION.ID = EGBPA_OCCUPANCY_CERTIFICATE.PARENT ");       	  
		 
        final StringBuilder whereQueryStr = new StringBuilder(" WHERE ");
//        final StringBuilder whereQueryStr = new StringBuilder(" WHERE EGCL_COLLECTIONDETAILS.ISACTUALDEMAND = true ");
        
        final StringBuilder subOrderByQueryStr = new StringBuilder(" ORDER BY "+
        	     " EGCL_COLLECTIONHEADER.RECEIPTDATE,"+
        	     " EGCL_COLLECTIONHEADER.RECEIPTNUMBER,"+
        	     " EG_DEPARTMENT.NAME");
        
        if(appTypeList!=null) {
        	whereQueryStr.append("EGBPA_APPLICATION.APPLICATIONSUBTYPE in ( :appTypeList)");
        }
        if (searchRequest.getFromDate() != null && searchRequest.getToDate() != null) {
            whereQueryStr.append(" AND EGCL_COLLECTIONHEADER.RECEIPTDATE between to_timestamp('"
                    + fromDateFormatter.format(searchRequest.getFromDate()) + "', 'YYYY-MM-DD HH24:MI:SS') and " + " to_timestamp('"
                    + toDateFormatter.format(searchRequest.getToDate()) + "', 'YYYY-MM-DD HH24:MI:SS') ");
        }
       
        if (StringUtils.isNotBlank(searchRequest.getPaymentMode()) && !searchRequest.getPaymentMode().equals(CollectionConstants.ALL)) 
            whereQueryStr.append(" AND EGF_INSTRUMENTTYPE.TYPE in ( :paymentMode )");

        final StringBuilder finalQuery = new StringBuilder(subSelectQueryStr).append(whereQueryStr)
                .append(subOrderByQueryStr);
        
        System.out.println("finalQuery::::"+finalQuery);
        
        final SQLQuery registerQuery = (SQLQuery) getCurrentSession().createSQLQuery(finalQuery.toString())
                .addScalar("applicationNumber", org.hibernate.type.StringType.INSTANCE)
                .addScalar("sector", org.hibernate.type.StringType.INSTANCE)
                .addScalar("plotNumber", org.hibernate.type.StringType.INSTANCE)
                .addScalar("receiptNumber", org.hibernate.type.StringType.INSTANCE)
                .addScalar("paymentDate", org.hibernate.type.DateType.INSTANCE)
                .addScalar("fileNumber", org.hibernate.type.StringType.INSTANCE)
                .addScalar("id",LongType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(ReceiptRegisterReportHelper.class));

//        if (searchRequest.getApplicationTypeId() != null && searchRequest.getApplicationTypeId() !=-1) {
//        	registerQuery.setLong("applicationTypeId", searchRequest.getApplicationTypeId());
//	     }


        if (StringUtils.isNotBlank(searchRequest.getPaymentMode()) && !searchRequest.getPaymentMode().equals(CollectionConstants.ALL))
        	registerQuery.setString("paymentMode", searchRequest.getPaymentMode());
        
        if(appTypeList!=null)
        	registerQuery.setParameterList("appTypeList", appTypeList);
        
        List<ReceiptRegisterReportHelper> receiptReportResultList = populateQueryResults(registerQuery.list());
////        final receiptRegisterReportHelperResult collResult = new receiptRegisterReportHelperResult();
//        
//        collResult.setAggrCollectionSummaryReportList(aggrReportResults);
//        collResult.setRebateCollectionSummaryReportList(rebateReportResultList);
        
        
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), receiptReportResultList.size());
        final Page<ReceiptRegisterReportHelper> page = new PageImpl<>(receiptReportResultList.subList(start, end), pageable, receiptReportResultList.size());
        
        return page;
	}
    
    private List<ReceiptRegisterReportHelper> getFeeDetails(Long id) {
        final StringBuilder defaultQueryStr = new StringBuilder(500);

      final StringBuilder selectQueryStr = new StringBuilder(
    		  "SELECT "+
    			"(CASE WHEN EGCL_COLLECTIONDETAILS.DESCRIPTION like '%Security%' THEN EGCL_COLLECTIONDETAILS.actualcramounttobepaid END) AS SecurityFee,"+
    			"(CASE WHEN EGCL_COLLECTIONDETAILS.DESCRIPTION like '%Scrutiny%' THEN EGCL_COLLECTIONDETAILS.actualcramounttobepaid END) AS ScrutinyFee,"+
    			"(CASE WHEN EGCL_COLLECTIONDETAILS.DESCRIPTION like '%GST%' THEN EGCL_COLLECTIONDETAILS.actualcramounttobepaid END) AS gst,"+
    			"(CASE WHEN EGCL_COLLECTIONDETAILS.DESCRIPTION like '%Additional Coverage fee%' THEN EGCL_COLLECTIONDETAILS.actualcramounttobepaid END) AS AdditionFee,"+
    			"(CASE WHEN EGCL_COLLECTIONDETAILS.DESCRIPTION like '%Labour cess%' THEN EGCL_COLLECTIONDETAILS.actualcramounttobepaid END) AS LabourCess,"+
    			"(CASE WHEN EGCL_COLLECTIONDETAILS.DESCRIPTION like '%Rule 5%' THEN EGCL_COLLECTIONDETAILS.actualcramounttobepaid END) AS Rule5 "+

    		" FROM EGCL_COLLECTIONDETAILS EGCL_COLLECTIONDETAILS "
    		
    		  );       	  
      final StringBuilder whereQueryStr = new StringBuilder(" WHERE EGCL_COLLECTIONDETAILS.ISACTUALDEMAND = true and EGCL_COLLECTIONDETAILS.COLLECTIONHEADER="+id);
      


      final StringBuilder finalQuery = new StringBuilder(selectQueryStr).append(whereQueryStr);
      
      System.out.println("finalQuery::::"+finalQuery);
      
      final SQLQuery feeQuery = (SQLQuery) getCurrentSession().createSQLQuery(finalQuery.toString())
    		  .addScalar("SecurityFee", DoubleType.INSTANCE)
    		  .addScalar("ScrutinyFee", DoubleType.INSTANCE)
    		  .addScalar("gst", DoubleType.INSTANCE)
    		  .addScalar("AdditionFee", DoubleType.INSTANCE)
              .addScalar("LabourCess", DoubleType.INSTANCE)
              .addScalar("Rule5", DoubleType.INSTANCE)
              
              .setResultTransformer(Transformers.aliasToBean(ReceiptRegisterReportHelper.class));

      List<ReceiptRegisterReportHelper> feeList = populateQueryResults(feeQuery.list());
      
      return feeList;
	}

    
    public List<ReceiptRegisterReportHelper> populateQueryResults(final List<ReceiptRegisterReportHelper> queryResults) {
        for (final ReceiptRegisterReportHelper receiptRegisterReportHelper : queryResults) {
            if (receiptRegisterReportHelper.getScrutinyFee() == null)
                receiptRegisterReportHelper.setScrutinyFee(0.0);
                if (receiptRegisterReportHelper.getSecurityFee() == null)
                	receiptRegisterReportHelper.setSecurityFee(0.0);
                if (receiptRegisterReportHelper.getLabourCess() == null)
                	receiptRegisterReportHelper.setLabourCess(0.0);
                if (receiptRegisterReportHelper.getAdditionFee() == null)
                	receiptRegisterReportHelper.setAdditionFee(0.0);
                if (receiptRegisterReportHelper.getGst() == null)
                	receiptRegisterReportHelper.setGst(0.0);
                if (receiptRegisterReportHelper.getRule5() == null)
                	receiptRegisterReportHelper.setRule5(0.0);
            
//            receiptRegisterReportHelper.setTotalWithoutLaboutCess(Double.sum(receiptRegisterReportHelper.getScrutinyFee(),
//                    Double.sum(receiptRegisterReportHelper.getSecurityFee(),
//                    		Double.sum(receiptRegisterReportHelper.getRule5(),Double.sum(receiptRegisterReportHelper.getAdditionFee(),receiptRegisterReportHelper.getGst())))));
//            receiptRegisterReportHelper.setTotal(Double.sum(receiptRegisterReportHelper.getTotalWithoutLaboutCess(), receiptRegisterReportHelper.getLabourCess()));
        }
        return queryResults;
    }

	public Page<CollectionSummaryReportHelper> getCollectionSummaryReportDetailsForUrban(SearchBpaApplicationForm searchRequest,
			List<Long> appTypeList,String source) {
		
		 final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                 searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());

    	 final Page<CollectionSummaryReportHelper> collectionSummaryReportList = getCollectionSummaryData(searchRequest,pageable,appTypeList,source,"USERWISE");
    	 collectionSummaryReportList.forEach(collectionSummary->{
    		 collectionSummary.setFromDate(searchRequest.getFromDate());
    		 collectionSummary.setToDate(searchRequest.getToDate());
    		 collectionSummary.setPaymentMode(searchRequest.getPaymentMode()==null?"ALL":searchRequest.getPaymentMode());
    	 });
    	 return new PageImpl<>(collectionSummaryReportList.getContent(), pageable,
    			 collectionSummaryReportList.getTotalElements());
	}
	public Page<CollectionSummaryReportHelper> getCollectionSummaryReportDetailsForOCUrban(SearchBpaApplicationForm searchRequest,
			List<Long> appTypeList, String source) {
		final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());

   	 final Page<CollectionSummaryReportHelper> collectionSummaryReportList = getCollectionSummaryData(searchRequest,pageable,appTypeList,source,"USERWISE");
   	 collectionSummaryReportList.forEach(collectionSummary->{
   		 collectionSummary.setFromDate(searchRequest.getFromDate());
   		 collectionSummary.setToDate(searchRequest.getToDate());
   		collectionSummary.setPaymentMode(searchRequest.getPaymentMode()==null?"ALL":searchRequest.getPaymentMode());
   	 });
   	 return new PageImpl<>(collectionSummaryReportList.getContent(), pageable,
   			 collectionSummaryReportList.getTotalElements());
	}
	
	
	public Page<CollectionSummaryHeadwiseReportHelper> getCollectionSummaryHeadwiseReportDetailsForUrban(SearchBpaApplicationForm searchRequest,
			List<Long> appTypeList,String source) {
		
		 final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                 searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());

    	 final Page<CollectionSummaryHeadwiseReportHelper> collectionSummaryHeadwiseReportList = getCollectionSummaryHeadwiseData(searchRequest,pageable,appTypeList,source,"AGGREGATE");
    	 collectionSummaryHeadwiseReportList.forEach(collectionSummaryHeadwise->{
    		 collectionSummaryHeadwise.setFromDate(searchRequest.getFromDate());
    		 collectionSummaryHeadwise.setToDate(searchRequest.getToDate());
    		 if(!collectionSummaryHeadwise.getGlCode().equalsIgnoreCase("Rebate Amount")) {
    		 collectionSummaryHeadwise.setPaymentMode(searchRequest.getPaymentMode()==null?"ALL":searchRequest.getPaymentMode());
    		 if(searchRequest.getApplicationTypeId()!=null) {
    			 final ApplicationSubType applicationType = entityManager.find(ApplicationSubType.class,searchRequest.getApplicationTypeId());
    			 collectionSummaryHeadwise.setApplicationType(applicationType.getDescription());
    		 }else
    			 collectionSummaryHeadwise.setApplicationType("ALL");
    		 }
    	 });
    	 
    	 return new PageImpl<>(collectionSummaryHeadwiseReportList.getContent(), pageable,
    			 collectionSummaryHeadwiseReportList.getTotalElements());
	}
	
	public Page<CollectionSummaryHeadwiseReportHelper> getCollectionSummaryHeadwiseReportDetailsForOCUrban(SearchBpaApplicationForm searchRequest,
			List<Long> appTypeList,String source) {
		
		 final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                 searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());

    	 final Page<CollectionSummaryHeadwiseReportHelper> collectionSummaryHeadwiseReportList = getCollectionSummaryHeadwiseData(searchRequest,pageable,appTypeList,source,"AGGREGATE");
    	 collectionSummaryHeadwiseReportList.forEach(collectionSummaryHeadwise->{
    		 collectionSummaryHeadwise.setFromDate(searchRequest.getFromDate());
    		 collectionSummaryHeadwise.setToDate(searchRequest.getToDate());
    		 if(!collectionSummaryHeadwise.getGlCode().equalsIgnoreCase("Rebate Amount")) {
    		 collectionSummaryHeadwise.setPaymentMode(searchRequest.getPaymentMode()==null?"ALL":searchRequest.getPaymentMode());
    		 if(searchRequest.getApplicationTypeId()!=null) {
    			 final ApplicationSubType applicationType = entityManager.find(ApplicationSubType.class,searchRequest.getApplicationTypeId());
    			 collectionSummaryHeadwise.setApplicationType(applicationType.getDescription());
    		 }else
    			 collectionSummaryHeadwise.setApplicationType("ALL");
    		 }
    	 });
    	 
    	 return new PageImpl<>(collectionSummaryHeadwiseReportList.getContent(), pageable,
    			 collectionSummaryHeadwiseReportList.getTotalElements());
	}

	private Page<CollectionSummaryReportHelper> getCollectionSummaryData(SearchBpaApplicationForm searchRequest,
			Pageable pageable, List<Long> urbanApplicationTypeList,String source,String queryType) {

		final SimpleDateFormat fromDateFormatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        final SimpleDateFormat toDateFormatter = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        final StringBuilder defaultQueryStr = new StringBuilder(500);
        
        StringBuilder aggregateQuery = new StringBuilder();
        StringBuilder userwiseQuery = new StringBuilder();
        final StringBuilder finalUserwiseQuery = new StringBuilder();
        final StringBuilder finalAggregateQuery = new StringBuilder();
        
//        final StringBuilder selectQueryStr = new StringBuilder(
//        		"SELECT APPLICATIONNUMBER,RECEIPT_NUMBER,PAYMENTDATE,DESCRIPTION" 
//        		);
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

        if (!source.isEmpty() && !source.equals(CollectionConstants.ALL)) {
            whereQuery.append(" AND EGCL_COLLECTIONHEADER.SOURCE=:source");
        } else {
            userwiseQuery.setLength(0);
            userwiseQuery.append(aggregateQuery);
        }
        
        if(urbanApplicationTypeList!=null) {
        	 whereQuery.append(" AND EGBPA_APPLICATION.APPLICATIONSUBTYPE IN(:urbanApplicationTypeList)");
        }
        
        if (searchRequest.getServiceTypeId() != null && searchRequest.getServiceTypeId() != -1)
            whereQuery.append(" AND EGCL_COLLECTIONHEADER.SERVICEDETAILS =:serviceId");
//        if (status != -1)
//            whereQuery.append(" AND EGCL_COLLECTIONHEADER.STATUS =:searchStatus");
//        if (!searchRequest.getServiceType().equals(CollectionConstants.ALL))
//            whereQuery.append(" AND SER.SERVICETYPE =:serviceType");
        if (StringUtils.isNotBlank(searchRequest.getPaymentMode()) && !searchRequest.getPaymentMode().equals(CollectionConstants.ALL)) {
            whereQuery.append(" AND EGF_INSTRUMENTTYPE.TYPE in (:paymentMode)");
            if (searchRequest.getPaymentMode().equals(CollectionConstants.INSTRUMENTTYPE_ONLINE)) {
                userwiseQuery.setLength(0);
                userwiseQuery.append(aggregateQuery);
            }
            userwiseQuery.append(whereQuery).append(groupQuery);
            aggregateQuery.append(whereQuery).append(groupQuery);
        } else {
            userwiseQuery.append(whereQuery);
            aggregateQuery.append(whereQuery);
            userwiseQuery = prepareQueryForAllPaymentMode(userwiseQuery, groupQuery);
            aggregateQuery = prepareQueryForAllPaymentMode(aggregateQuery, groupQuery);
        }

        final StringBuilder finalSelectQuery = new StringBuilder(
                "SELECT cast(sum(cashReceipt) AS NUMERIC) AS cashReceipt,cast(sum(chequeReceipt) AS NUMERIC) AS chequeReceipt,cast(sum(onlineReceipt) AS NUMERIC) AS onlineReceipt,source,counterName,employeeName,serviceName,cast(sum(cashAmount) AS NUMERIC) AS cashAmount, cast(sum(chequeAmount) AS NUMERIC) AS chequeAmount, cast(sum(onlineAmount) AS NUMERIC) AS onlineAmount ,USERID,cast(sum(bankReceipt) AS NUMERIC) AS bankReceipt, cast(sum(bankAmount) AS NUMERIC) AS bankAmount, "
                        + "  cast(sum(cardReceipt) AS NUMERIC) AS cardReceipt, cast(sum(cardAmount) AS NUMERIC) AS cardAmount, cast(sum(totalReceipt) AS NUMERIC) as totalReceipt  FROM (");
        final StringBuilder finalGroupQuery = new StringBuilder(
                " ) AS RESULT GROUP BY RESULT.source,RESULT.counterName,RESULT.employeeName,RESULT.USERID,RESULT.serviceName order by RESULT.source,employeeName, serviceName ");

        finalUserwiseQuery.append(finalSelectQuery).append(userwiseQuery).append(finalGroupQuery);
        finalAggregateQuery.append(finalSelectQuery).append(aggregateQuery).append(finalGroupQuery);

        final SQLQuery userwiseSqluery = createSQLQuery(finalUserwiseQuery.toString());
        final SQLQuery aggregateSqlQuery = createSQLQuery(finalAggregateQuery.toString());

        if (!source.isEmpty() && !source.equals(CollectionConstants.ALL)) {
            userwiseSqluery.setString("source", source);
            aggregateSqlQuery.setString("source", source);
        }
        if (searchRequest.getServiceTypeId() != null && searchRequest.getServiceTypeId() != -1) {
            userwiseSqluery.setLong("serviceId", searchRequest.getServiceTypeId());
            aggregateSqlQuery.setLong("serviceId", searchRequest.getServiceTypeId());
        }
        if (urbanApplicationTypeList != null ) {
            userwiseSqluery.setParameterList("urbanApplicationTypeList", urbanApplicationTypeList);
            aggregateSqlQuery.setParameterList("urbanApplicationTypeList", urbanApplicationTypeList);
        }
//        if (status != -1) {
//            userwiseSqluery.setLong("searchStatus", status);
//            aggregateSqlQuery.setLong("searchStatus", status);
//        }

//        if (!searchRequest.getServiceType().equals(CollectionConstants.ALL)) {
//            userwiseSqluery.setString("serviceType", searchRequest.getServiceType());
//            aggregateSqlQuery.setString("serviceType", searchRequest.getServiceType());
//        }

        if (StringUtils.isNotBlank(searchRequest.getPaymentMode()) && !searchRequest.getPaymentMode().equals(CollectionConstants.ALL))
            if (searchRequest.getPaymentMode().equals(CollectionConstants.INSTRUMENTTYPE_CHEQUEORDD)) {
                userwiseSqluery.setParameterList("paymentMode", new ArrayList<>(Arrays.asList("cheque", "dd")));
                aggregateSqlQuery.setParameterList("paymentMode", new ArrayList<>(Arrays.asList("cheque", "dd")));
            } else {
                userwiseSqluery.setString("paymentMode", searchRequest.getPaymentMode());
                aggregateSqlQuery.setString("paymentMode", searchRequest.getPaymentMode());
            }
        List<CollectionSummaryReportHelper> reportResults = new ArrayList<>();
        if(queryType.equalsIgnoreCase("USERWISE")) {
        	reportResults = populateCollectionSummaryQueryResults(userwiseSqluery.list());
        }else {
        	reportResults = populateCollectionSummaryQueryResults(aggregateSqlQuery.list());
        }
        
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), reportResults.size());
        final Page<CollectionSummaryReportHelper> page = new PageImpl<>(reportResults.subList(start, end), pageable, reportResults.size());
        
        return page;
	}
	
	private Page<CollectionSummaryHeadwiseReportHelper> getCollectionSummaryHeadwiseData(SearchBpaApplicationForm searchRequest,
			Pageable pageable, List<Long> appTypeList,String source,String queryType) {

		final SimpleDateFormat fromDateFormatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        final SimpleDateFormat toDateFormatter = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        final StringBuilder defaultQueryStr = new StringBuilder(500);
        final StringBuilder aggregateQueryStr = new StringBuilder();
        StringBuilder rebateQueryStr = new StringBuilder("");
        StringBuilder revenueHeadQueryStr = new StringBuilder("");

        final StringBuilder selectQueryStr = new StringBuilder(
                "SELECT  (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='cash' THEN count(distinct(EGCL_COLLECTIONHEADER.id)) END) AS cashReceipt,  "
                        +
                        " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='cheque' THEN count(distinct(EGCL_COLLECTIONHEADER.id)) WHEN EGF_INSTRUMENTTYPE.TYPE='dd' THEN count(distinct(EGCL_COLLECTIONHEADER.id)) END) AS chequeReceipt, "
                        +
                        " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE= 'online' THEN count(distinct(EGCL_COLLECTIONHEADER.id)) END) AS onlineReceipt, "
                        +
                        " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='card' THEN count(distinct(EGCL_COLLECTIONHEADER.id)) END) AS cardReceipt, "
                        +
                        " EGCL_COLLECTIONHEADER.SOURCE ,CAO.NAME || '-' || CAO.GLCODE AS glCode,");
        final StringBuilder revSelectQueryStr = new StringBuilder(selectQueryStr).append(
                " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='cash' THEN SUM(EGCL_COLLECTIONDETAILS.CRAMOUNT) END) AS cashAmount, " +
                        " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='cheque' THEN SUM(EGCL_COLLECTIONDETAILS.CRAMOUNT) WHEN EGF_INSTRUMENTTYPE.TYPE='dd' THEN SUM(EGCL_COLLECTIONDETAILS.CRAMOUNT) END) AS chequeAmount,"
                        +
                        " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='card' THEN SUM(EGCL_COLLECTIONDETAILS.CRAMOUNT) END) AS cardAmount, "
                        +
                        " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE= 'online' THEN SUM(EGCL_COLLECTIONDETAILS.CRAMOUNT) END) AS onlineAmount");
        final StringBuilder rebateSelectQueryStr = new StringBuilder(selectQueryStr).append(
                " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='cash' THEN SUM(EGCL_COLLECTIONDETAILS.DRAMOUNT) END) AS cashAmount, " +
                        " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='cheque' THEN SUM(EGCL_COLLECTIONDETAILS.DRAMOUNT) WHEN EGF_INSTRUMENTTYPE.TYPE='dd' THEN SUM(EGCL_COLLECTIONDETAILS.DRAMOUNT) END) AS chequeAmount,"
                        +
                        " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE='card' THEN SUM(EGCL_COLLECTIONDETAILS.DRAMOUNT) END) AS cardAmount, "
                        +
                        " (CASE WHEN EGF_INSTRUMENTTYPE.TYPE= 'online' THEN SUM(EGCL_COLLECTIONDETAILS.DRAMOUNT) END) AS onlineAmount");
        final StringBuilder fromQueryStr = new StringBuilder();
        if(searchRequest.getServiceType().equalsIgnoreCase("BPA")) {
        	fromQueryStr.append(" FROM "
                + " EGCL_COLLECTIONHEADER EGCL_COLLECTIONHEADER INNER JOIN EGCL_COLLECTIONINSTRUMENT EGCL_COLLECTIONINSTRUMENT ON EGCL_COLLECTIONHEADER.ID = EGCL_COLLECTIONINSTRUMENT.COLLECTIONHEADER "
                + " INNER JOIN EGF_INSTRUMENTHEADER EGF_INSTRUMENTHEADER ON EGCL_COLLECTIONINSTRUMENT.INSTRUMENTHEADER = EGF_INSTRUMENTHEADER.ID "
                + " INNER JOIN EGW_STATUS EGW_STATUS ON EGCL_COLLECTIONHEADER.STATUS = EGW_STATUS.ID"
                + " INNER JOIN EGF_INSTRUMENTTYPE EGF_INSTRUMENTTYPE ON EGF_INSTRUMENTHEADER.INSTRUMENTTYPE = EGF_INSTRUMENTTYPE.ID"
                + " INNER JOIN EGCL_COLLECTIONMIS EGCL_COLLECTIONMIS ON EGCL_COLLECTIONHEADER.ID = EGCL_COLLECTIONMIS.COLLECTIONHEADER "
                + " INNER JOIN EGCL_COLLECTIONDETAILS EGCL_COLLECTIONDETAILS ON EGCL_COLLECTIONHEADER.ID = EGCL_COLLECTIONDETAILS.COLLECTIONHEADER "
                + " INNER JOIN CHARTOFACCOUNTS CAO ON CAO.ID = EGCL_COLLECTIONDETAILS.CHARTOFACCOUNT "
                + " INNER JOIN EGBPA_APPLICATION EGBPA_APPLICATION ON EGBPA_APPLICATION.APPLICATIONNUMBER= EGCL_COLLECTIONHEADER.CONSUMERCODE");
        }
        else {
        	fromQueryStr.append(" FROM "
                     + " EGCL_COLLECTIONHEADER EGCL_COLLECTIONHEADER INNER JOIN EGCL_COLLECTIONINSTRUMENT EGCL_COLLECTIONINSTRUMENT ON EGCL_COLLECTIONHEADER.ID = EGCL_COLLECTIONINSTRUMENT.COLLECTIONHEADER "
                     + " INNER JOIN EGF_INSTRUMENTHEADER EGF_INSTRUMENTHEADER ON EGCL_COLLECTIONINSTRUMENT.INSTRUMENTHEADER = EGF_INSTRUMENTHEADER.ID "
                     + " INNER JOIN EGW_STATUS EGW_STATUS ON EGCL_COLLECTIONHEADER.STATUS = EGW_STATUS.ID"
                     + " INNER JOIN EGF_INSTRUMENTTYPE EGF_INSTRUMENTTYPE ON EGF_INSTRUMENTHEADER.INSTRUMENTTYPE = EGF_INSTRUMENTTYPE.ID"
                     + " INNER JOIN EGCL_COLLECTIONMIS EGCL_COLLECTIONMIS ON EGCL_COLLECTIONHEADER.ID = EGCL_COLLECTIONMIS.COLLECTIONHEADER "
                     + " INNER JOIN EGCL_COLLECTIONDETAILS EGCL_COLLECTIONDETAILS ON EGCL_COLLECTIONHEADER.ID = EGCL_COLLECTIONDETAILS.COLLECTIONHEADER "
                     + " INNER JOIN CHARTOFACCOUNTS CAO ON CAO.ID = EGCL_COLLECTIONDETAILS.CHARTOFACCOUNT "
                     + " INNER JOIN EGBPA_OCCUPANCY_CERTIFICATE EGBPA_OCCUPANCY_CERTIFICATE ON EGBPA_OCCUPANCY_CERTIFICATE.APPLICATIONNUMBER= EGCL_COLLECTIONHEADER.CONSUMERCODE"
             		+ " INNER JOIN EGBPA_APPLICATION EGBPA_APPLICATION ON EGBPA_APPLICATION.id= EGBPA_OCCUPANCY_CERTIFICATE.PARENT");
        }
        StringBuilder whereQueryStr = new StringBuilder(" WHERE EGW_STATUS.DESCRIPTION != 'Cancelled' ");
        StringBuilder creditWhereQueryStr = new StringBuilder("  AND EGCL_COLLECTIONDETAILS.CRAMOUNT>0 ");
        StringBuilder debitWhereQueryStr = new StringBuilder(
                "  AND EGCL_COLLECTIONDETAILS.DRAMOUNT>0 AND CAO.purposeid in (select id from EGF_ACCOUNTCODE_PURPOSE where name ='"
                        + CollectionConstants.PURPOSE_NAME_REBATE+ "')");    
        final StringBuilder queryStrGroup = new StringBuilder(" GROUP BY EGCL_COLLECTIONHEADER.SOURCE,CAO.NAME,CAO.GLCODE,EGF_INSTRUMENTTYPE.TYPE ");
        final StringBuilder finalSelectQueryStr = new StringBuilder(
                "SELECT sum(cashReceipt) AS cashReceipt,sum(chequeReceipt) AS chequeReceipt,sum(onlineReceipt) AS onlineReceipt,source,glCode,sum(cashAmount) AS cashAmount, sum(chequeAmount) AS chequeAmount,  "
                        + "  sum(cardReceipt) AS cardReceipt, sum(cardAmount) AS cardAmount, sum(COALESCE(cashReceipt,0))+sum(COALESCE(chequeReceipt,0))+sum(COALESCE(onlineReceipt,0))+sum(COALESCE(cardReceipt,0)) as totalReceipt,sum(onlineAmount) AS onlineAmount  FROM (");
        final StringBuilder finalGroupQuery = new StringBuilder(
                " ) AS RESULT GROUP BY RESULT.SOURCE,RESULT.glCode order by RESULT.SOURCE,RESULT.glCode");

        if (searchRequest.getFromDate() != null && searchRequest.getToDate() != null) {
            whereQueryStr.append(" AND EGCL_COLLECTIONHEADER.RECEIPTDATE between to_timestamp('"
                    + fromDateFormatter.format(searchRequest.getFromDate()) + "', 'YYYY-MM-DD HH24:MI:SS') and " + " to_timestamp('"
                    + toDateFormatter.format(searchRequest.getToDate()) + "', 'YYYY-MM-DD HH24:MI:SS') ");
        }
        if (!source.isEmpty() && !source.equals(CollectionConstants.ALL)) {
            whereQueryStr.append(" AND EGCL_COLLECTIONHEADER.SOURCE=:source");
        }
//        if (glCode != null) {
//            whereQueryStr.append(" AND CAO.GLCODE =:glCode");
//        }
//        if (branchId != null && branchId != -1) {
//            whereQueryStr.append(" AND EGCL_COLLECTIONMIS.DEPOSITEDBRANCH=:branchId");
//        }
//        if (status != -1) {
//            whereQueryStr.append(" AND EGCL_COLLECTIONHEADER.STATUS =:searchStatus");
//        }

        if(appTypeList !=null && !appTypeList.isEmpty())  {
        	whereQueryStr.append(" AND EGBPA_APPLICATION.APPLICATIONSUBTYPE in(:appTypeList)");
        }
       
        if (StringUtils.isNotBlank(searchRequest.getPaymentMode()) && !searchRequest.getPaymentMode().equals(CollectionConstants.ALL)) {
            whereQueryStr.append(" AND EGF_INSTRUMENTTYPE.TYPE in (:paymentMode)");
            revenueHeadQueryStr.append(revSelectQueryStr).append(fromQueryStr).append(whereQueryStr)
                    .append(creditWhereQueryStr).append(queryStrGroup);
            rebateQueryStr.append(rebateSelectQueryStr).append(fromQueryStr).append(whereQueryStr)
                    .append(debitWhereQueryStr).append(queryStrGroup);
        } else {
            revenueHeadQueryStr.append(revSelectQueryStr).append(fromQueryStr).append(whereQueryStr)
                    .append(creditWhereQueryStr).append(" AND EGF_INSTRUMENTTYPE.TYPE = 'cash'").append(queryStrGroup);
            revenueHeadQueryStr.append(" union ");
            revenueHeadQueryStr.append(revSelectQueryStr).append(fromQueryStr).append(whereQueryStr)
                    .append(creditWhereQueryStr).append(" AND EGF_INSTRUMENTTYPE.TYPE  in( 'cheque','dd') ")
                    .append(queryStrGroup);
            revenueHeadQueryStr.append(" union ");
            revenueHeadQueryStr.append(revSelectQueryStr).append(fromQueryStr).append(whereQueryStr)
                    .append(creditWhereQueryStr).append(" AND EGF_INSTRUMENTTYPE.TYPE = 'card'").append(queryStrGroup);
            revenueHeadQueryStr.append(" union ");
            revenueHeadQueryStr.append(revSelectQueryStr).append(fromQueryStr).append(whereQueryStr)
                    .append(creditWhereQueryStr).append(" AND EGF_INSTRUMENTTYPE.TYPE = 'online'").append(queryStrGroup);

            rebateQueryStr.append(rebateSelectQueryStr).append(fromQueryStr).append(whereQueryStr)
                    .append(debitWhereQueryStr).append(" AND EGF_INSTRUMENTTYPE.TYPE = 'cash'").append(queryStrGroup);
            rebateQueryStr.append(" union ");
            rebateQueryStr.append(rebateSelectQueryStr).append(fromQueryStr).append(whereQueryStr)
                    .append(debitWhereQueryStr).append(" AND EGF_INSTRUMENTTYPE.TYPE  in( 'cheque','dd') ").append(queryStrGroup);
            rebateQueryStr.append(" union ");
            rebateQueryStr.append(rebateSelectQueryStr).append(fromQueryStr).append(whereQueryStr)
                    .append(debitWhereQueryStr).append(" AND EGF_INSTRUMENTTYPE.TYPE = 'card'").append(queryStrGroup);
            rebateQueryStr.append(" union ");
            rebateQueryStr.append(rebateSelectQueryStr).append(fromQueryStr).append(whereQueryStr)
                    .append(debitWhereQueryStr).append(" AND EGF_INSTRUMENTTYPE.TYPE = 'online'").append(queryStrGroup);
        }

        final StringBuilder finalRevQueryStr = new StringBuilder(finalSelectQueryStr).append(revenueHeadQueryStr)
                .append(finalGroupQuery);
        final StringBuilder finalRebateQueryStr = new StringBuilder(finalSelectQueryStr).append(rebateQueryStr)
                .append(finalGroupQuery);

        final SQLQuery aggrQuery = (SQLQuery) getCurrentSession().createSQLQuery(finalRevQueryStr.toString())
                .addScalar("cashReceipt", org.hibernate.type.StringType.INSTANCE).addScalar("cashAmount", BigDecimalType.INSTANCE)
                .addScalar("chequeReceipt", org.hibernate.type.StringType.INSTANCE)
                .addScalar("chequeAmount", BigDecimalType.INSTANCE)
                .addScalar("onlineReceipt", org.hibernate.type.StringType.INSTANCE).addScalar("onlineAmount", BigDecimalType.INSTANCE)
                .addScalar("source", org.hibernate.type.StringType.INSTANCE)
                .addScalar("glCode", org.hibernate.type.StringType.INSTANCE)
                .addScalar("cardAmount", BigDecimalType.INSTANCE).addScalar("cardReceipt", org.hibernate.type.StringType.INSTANCE)
                .addScalar("totalReceipt", org.hibernate.type.StringType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(CollectionSummaryHeadwiseReportHelper.class));

        final SQLQuery rebateQuery = (SQLQuery) getCurrentSession().createSQLQuery(finalRebateQueryStr.toString())
                .addScalar("cashReceipt", org.hibernate.type.StringType.INSTANCE).addScalar("cashAmount", BigDecimalType.INSTANCE)
                .addScalar("chequeReceipt", org.hibernate.type.StringType.INSTANCE)
                .addScalar("chequeAmount", BigDecimalType.INSTANCE)
                .addScalar("onlineReceipt", org.hibernate.type.StringType.INSTANCE).addScalar("onlineAmount", BigDecimalType.INSTANCE)
                .addScalar("source", org.hibernate.type.StringType.INSTANCE)
                .addScalar("glCode", org.hibernate.type.StringType.INSTANCE)
                .addScalar("cardAmount", BigDecimalType.INSTANCE).addScalar("cardReceipt", org.hibernate.type.StringType.INSTANCE)
                .addScalar("totalReceipt", org.hibernate.type.StringType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(CollectionSummaryHeadwiseReportHelper.class));
        if (!source.isEmpty() && !source.equals(CollectionConstants.ALL)) {
            aggrQuery.setString("source", source);
            rebateQuery.setString("source", source);
        }
//        if (glCode != null) {
//            aggrQuery.setString("glCode", glCode);
//            rebateQuery.setString("glCode", glCode);
//        }
        if (appTypeList != null && !appTypeList.isEmpty()) {
        	aggrQuery.setParameterList("appTypeList", appTypeList);
        	rebateQuery.setParameterList("appTypeList", appTypeList);
        }
//        else {
//	    	 aggrQuery.setString("rootBoundaryType", rootBoundaryType);
//	            rebateQuery.setString("rootBoundaryType", rootBoundaryType);
//	     }
//        if (status != -1) {
//            aggrQuery.setLong("searchStatus", status);
//            rebateQuery.setLong("searchStatus", status);
//        }

        if (StringUtils.isNotBlank(searchRequest.getPaymentMode()) && !searchRequest.getPaymentMode().equals(CollectionConstants.ALL))
            if (searchRequest.getPaymentMode().equals(CollectionConstants.INSTRUMENTTYPE_CHEQUEORDD)) {
                aggrQuery.setParameterList("paymentMode", new ArrayList<>(Arrays.asList("cheque", "dd")));
                rebateQuery.setParameterList("paymentMode", new ArrayList<>(Arrays.asList("cheque", "dd")));
            } else {
                aggrQuery.setString("paymentMode", searchRequest.getPaymentMode());
                rebateQuery.setString("paymentMode", searchRequest.getPaymentMode());
            }
        List<CollectionSummaryHeadwiseReportHelper> finalReportList = new ArrayList<>();
        if(queryType.equalsIgnoreCase("REBATE")) {
        	finalReportList = populateCollectionSummaryHeadwiseQueryResults(rebateQuery.list());
        	
        }else {
        	List<CollectionSummaryHeadwiseReportHelper> rebateResults = populateCollectionSummaryHeadwiseQueryResults(rebateQuery.list());
        	List<CollectionSummaryHeadwiseReportHelper> reportResults = populateCollectionSummaryHeadwiseQueryResults(aggrQuery.list());
        	 if (!reportResults.isEmpty()) {
                 rebateTotal(reportResults.get(0), rebateResults);
             }
        	  finalReportList= addRebateDataInREportData(reportResults,rebateResults);
        }
//        final CollectionSummaryReportResult collResult = new CollectionSummaryReportResult();
//        collResult.setCollectionSummaryReportList(reportResults);
//        collResult.setAggrCollectionSummaryReportList(aggrReportResults);
        
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), finalReportList.size());
        final Page<CollectionSummaryHeadwiseReportHelper> page = new PageImpl<>(finalReportList.subList(start, end), pageable, finalReportList.size());
        
        return page;
	}
	
	private List<CollectionSummaryHeadwiseReportHelper> addRebateDataInREportData(List<CollectionSummaryHeadwiseReportHelper> reportResults,
			List<CollectionSummaryHeadwiseReportHelper> rebateResults) {
		if(rebateResults.isEmpty()){
			CollectionSummaryHeadwiseReportHelper rebateData = new CollectionSummaryHeadwiseReportHelper();
			rebateData.setGlCode("Rebate Amount");
			rebateData.setCardAmount(BigDecimal.ZERO);
			rebateData.setCashAmount(BigDecimal.ZERO);
			rebateData.setOnlineAmount(BigDecimal.ZERO);
			rebateData.setChequeAmount(BigDecimal.ZERO);
			rebateData.setTotalAmount(BigDecimal.ZERO);
			rebateData.setCardReceipt("");
			rebateData.setCashReceipt("");
			rebateData.setOnlineReceipt("");
			rebateData.setChequeReceipt("");
			rebateData.setTotalReceipt("");
			rebateData.setSource(" ");
			reportResults.add(rebateData);
		}else {
		rebateResults.forEach(rabate->{
			rabate.setGlCode("Rebate Amount");
		});
		reportResults.addAll(rebateResults);
		}
		return reportResults;
	}

	public void rebateTotal(CollectionSummaryHeadwiseReportHelper collectionSummaryHeadWiseReport,
            List<CollectionSummaryHeadwiseReportHelper> rebateResultList) {
        for (final CollectionSummaryHeadwiseReportHelper rebate : rebateResultList) {
            if (!rebate.getCashAmount().equals(BigDecimal.ZERO))
                collectionSummaryHeadWiseReport.setTotalCashRebateAmount(
                        collectionSummaryHeadWiseReport.getTotalCashRebateAmount().add(rebate.getCashAmount()));
            if (!rebate.getChequeAmount().equals(BigDecimal.ZERO))
                collectionSummaryHeadWiseReport.setTotalChequeddRebateAmount(
                        collectionSummaryHeadWiseReport.getTotalChequeddRebateAmount().add(rebate.getChequeAmount()));
            if (!rebate.getCardAmount().equals(BigDecimal.ZERO))
                collectionSummaryHeadWiseReport.setTotalCardRebateAmount(
                       collectionSummaryHeadWiseReport.getTotalCardRebateAmount().add(rebate.getCardAmount()));
            if (!rebate.getOnlineAmount().equals(BigDecimal.ZERO))
                collectionSummaryHeadWiseReport.setTotalOnlineRebateAmount(
                        collectionSummaryHeadWiseReport.getTotalOnlineRebateAmount().add(rebate.getOnlineAmount()));
            if (!rebate.getTotalAmount().equals(BigDecimal.ZERO))
                collectionSummaryHeadWiseReport.setTotalRebateAmount(
                        collectionSummaryHeadWiseReport.getTotalRebateAmount().add(rebate.getTotalAmount()));
        }
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
//	                .addScalar("counterName", org.hibernate.type.StringType.INSTANCE)
//	                .addScalar("employeeName", org.hibernate.type.StringType.INSTANCE)
	                .addScalar("bankReceipt", org.hibernate.type.StringType.INSTANCE)
	                .addScalar("bankAmount", BigDecimalType.INSTANCE)
	                .addScalar("cardAmount", BigDecimalType.INSTANCE)
	                .addScalar("cardReceipt", org.hibernate.type.StringType.INSTANCE)
	                .addScalar("totalReceipt", org.hibernate.type.StringType.INSTANCE)
	                .setResultTransformer(Transformers.aliasToBean(CollectionSummaryReportHelper.class));
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
	    public List<CollectionSummaryHeadwiseReportHelper> populateCollectionSummaryHeadwiseQueryResults(final List<CollectionSummaryHeadwiseReportHelper> queryResults) {
	        for (final CollectionSummaryHeadwiseReportHelper collectionSummaryHeadWiseReport : queryResults) {
	            if (collectionSummaryHeadWiseReport.getCashReceipt() == null)
	                collectionSummaryHeadWiseReport.setCashReceipt("");
	            if (collectionSummaryHeadWiseReport.getChequeReceipt() == null)
	                collectionSummaryHeadWiseReport.setChequeReceipt("");
	            if (collectionSummaryHeadWiseReport.getOnlineReceipt() == null)
	                collectionSummaryHeadWiseReport.setOnlineReceipt("");
	            if (collectionSummaryHeadWiseReport.getCardReceipt() == null)
	                collectionSummaryHeadWiseReport.setCardReceipt("");
//	                collectionSummaryHeadWiseReport.setTotalReceipt("");
	            if (collectionSummaryHeadWiseReport.getCashAmount() == null)
	                collectionSummaryHeadWiseReport.setCashAmount(BigDecimal.ZERO);
	            if (collectionSummaryHeadWiseReport.getChequeAmount() == null)
	                collectionSummaryHeadWiseReport.setChequeAmount(BigDecimal.ZERO);
	            if (collectionSummaryHeadWiseReport.getOnlineAmount() == null)
	                collectionSummaryHeadWiseReport.setOnlineAmount(BigDecimal.ZERO);
	            if (collectionSummaryHeadWiseReport.getCardAmount() == null)
	                collectionSummaryHeadWiseReport.setCardAmount(BigDecimal.ZERO);
	            collectionSummaryHeadWiseReport.getOnlineAmount();
	            collectionSummaryHeadWiseReport.setTotalAmount(collectionSummaryHeadWiseReport.getCardAmount()
	                    .add(collectionSummaryHeadWiseReport.getOnlineAmount())
	                    .add(collectionSummaryHeadWiseReport.getChequeAmount()).add(collectionSummaryHeadWiseReport.getCashAmount()));
	            
	        
	        }
	        return queryResults;
	    }

		
}