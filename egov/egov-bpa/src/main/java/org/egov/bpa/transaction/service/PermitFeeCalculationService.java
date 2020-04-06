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
package org.egov.bpa.transaction.service;


import static org.egov.bpa.utils.BpaConstants.ADDING_OF_EXTENSION;
import static org.egov.bpa.utils.BpaConstants.ALTERATION;
import static org.egov.bpa.utils.BpaConstants.NEW_CONSTRUCTION;
import static org.egov.bpa.utils.BpaConstants.RECONSTRUCTION;
import static org.egov.bpa.utils.BpaConstants.LOWRISK;
import static org.egov.bpa.utils.BpaConstants.IN_PROGRESS;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.egov.bpa.master.entity.BpaFeeMapping;
import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.master.service.BpaFeeMappingService;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.ApplicationFloorDetail;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.entity.ExistingBuildingDetail;
import org.egov.bpa.transaction.entity.ExistingBuildingFloorDetail;
import org.egov.bpa.transaction.entity.PermitFee;
import org.egov.bpa.transaction.service.collection.BpaDemandService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.common.entity.bpa.SubOccupancy;
import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Building;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.commons.Installment;
import org.egov.commons.dao.InstallmentDao;
import org.egov.demand.model.EgDemand;
import org.egov.demand.model.EgDemandDetails;
import org.egov.demand.model.EgDemandReason;
import org.egov.infra.admin.master.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author vinoth
 */
@Service
@Transactional(readOnly = true)
public class PermitFeeCalculationService implements ApplicationBpaFeeCalculation {
		private static final BigDecimal TEN = BigDecimal.valueOf(10);
		private static final BigDecimal TWENTY = BigDecimal.valueOf(20);
		private static final BigDecimal THIRTY = BigDecimal.valueOf(30);
		private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
	    private static final BigDecimal TEN_LAKH = BigDecimal.valueOf(1000000);
	    private static final BigDecimal FIFTEEN_HUNDRED = BigDecimal.valueOf(1500);
	    private static final BigDecimal TWENTY_FIVE_HUNDRED = BigDecimal.valueOf(2500);
	    private static final BigDecimal THOUSAND = BigDecimal.valueOf(1000);
	    private static final BigDecimal TEN_THOUSAND = BigDecimal.valueOf(10000);
	    private static final BigDecimal FIVE_THOUSAND = BigDecimal.valueOf(5000);
	    private static final BigDecimal ONE_TWO_FIVE = BigDecimal.valueOf(1.25);
	    private static final BigDecimal TWO_FIVE = BigDecimal.valueOf(2.5);
	    private static final BigDecimal EIGHTEEN = BigDecimal.valueOf(18);
	    private static final BigDecimal SQMT_SQFT_MULTIPLIER = BigDecimal.valueOf(10.764);
	    private static final BigDecimal HALF_ACRE_IN_SQMT = BigDecimal.valueOf(2023.43);

	    @Autowired
	    private PermitFeeService permitFeeService;
		@Autowired
		private BpaFeeMappingService bpaFeeMappingService;
		@Autowired
		protected ApplicationBpaService applicationBpaService;			 
        @Autowired
		private BpaDemandService bpaDemandService;
        @Autowired
		private InstallmentDao installmentDao;
		@Autowired
		private ModuleService moduleService;
		@Autowired
		private ApplicationFeeService applicationFeeService;		
	    
	    @Override
	    public BigDecimal calculateAdmissionFeeAmount(Long applicationTypeId, String edcrNo) {
	    	BigDecimal amount = BigDecimal.ZERO;
	    	if (null!=edcrNo && !"".equals(edcrNo)) {	    		
	    		Plan plan = applicationBpaService.getPlanInfo(edcrNo);	    		
	    		if(null!=plan) {
	    			OccupancyTypeHelper mostRestrictiveFarHelper = plan.getVirtualBuilding() != null
							? plan.getVirtualBuilding().getMostRestrictiveFarHelper()
							: null;
					amount = amount.add(getTotalSecurityFee(plan, mostRestrictiveFarHelper));
	    		}
	    	}
	    	return amount;
	    }
	    
	    @Override
	    public BigDecimal setAdmissionFeeAmount(final BpaApplication application, List<ServiceType> amenityList) {
		    return calculateAdmissionFeeAmount(application.getApplicationType().getId(), application.geteDcrNumber());
	    }	    
	    
	    protected PermitFee getbpaFee(final BpaApplication application) {
	        PermitFee permitFee = null;
	        if (application != null) {
	            List<PermitFee> permitFeeList = permitFeeService
	                    .getPermitFeeListByApplicationId(application.getId());
	            if (permitFeeList.isEmpty()) {
	                permitFee = new PermitFee();
	                permitFee.setApplicationFee(new ApplicationFee());
	                permitFee.setApplication(application);
	                return permitFee;
	            } else {
	                return permitFeeList.get(0);
	            }
	        }
	        return permitFee;
	    }

	    @Override
	    public PermitFee calculateBpaSanctionFees(final BpaApplication application) {
	        List<Long> serviceTypeList = new ArrayList<>();
	        // getting all service type and amenities to retrieve fee details
	        serviceTypeList.add(application.getServiceType().getId());
	        if (!application.getApplicationAmenity().isEmpty()) {
	            for (ServiceType serviceType : application.getApplicationAmenity()) {
	                serviceTypeList.add(serviceType.getId());
	            }
	        }
	        PermitFee permitFee = getbpaFee(application);
	        // If record rejected and recalculation required again, then this logic
	        // has to be change.
	        if (permitFee.getApplicationFee().getApplicationFeeDetail().isEmpty()) {
	        	calculateFeeByCityGrade(application, serviceTypeList, permitFee);
	        }

	        return permitFee;
	    }
	    
	    public void calculateFeeByCityGrade(final BpaApplication application, final List<Long> serviceTypeList, final PermitFee permitFee) {
	    	if (application != null) {	    		
	    		Plan plan = applicationBpaService.getPlanInfo(application.geteDcrNumber());	    		
	    		if(null!=plan) {
	    			OccupancyTypeHelper mostRestrictiveFarHelper = plan.getVirtualBuilding() != null
							? plan.getVirtualBuilding().getMostRestrictiveFarHelper()
							: null;
							
	    			for (Long serviceTypeId : serviceTypeList) {
		            	BpaFeeMapping bpaGST = null;
		            	for (BpaFeeMapping fee : bpaFeeMappingService.getPermitFeesByAppType(application, serviceTypeId)) {
		            		if (fee != null) {
		            			if (BpaConstants.GST_18.equals(fee.getBpaFeeCommon().getDescription())
										&& (fee.getServiceType().getDescription().equalsIgnoreCase(NEW_CONSTRUCTION)
												|| fee.getServiceType().getDescription().equalsIgnoreCase(RECONSTRUCTION)
												|| fee.getServiceType().getDescription().equalsIgnoreCase(ADDING_OF_EXTENSION)
												|| fee.getServiceType().getDescription().equalsIgnoreCase(ALTERATION))) {
		            				bpaGST = fee;
		            			}
		            		}
		            	}	            	
		            	for (BpaFeeMapping bpaFee : bpaFeeMappingService.getPermitFeesByAppType(application, serviceTypeId)) {
		                    if (bpaFee != null) {
		                    	if(bpaFee.getServiceType().getDescription().equalsIgnoreCase(NEW_CONSTRUCTION)
										|| bpaFee.getServiceType().getDescription().equalsIgnoreCase(RECONSTRUCTION)
										|| bpaFee.getServiceType().getDescription().equalsIgnoreCase(ADDING_OF_EXTENSION)
										|| bpaFee.getServiceType().getDescription().equalsIgnoreCase(ALTERATION)) {
		                    		if (BpaConstants.SCRUTINY_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())){		                    		
			                    		BigDecimal multiplier = BigDecimal.ZERO;
			                    		if(bpaFee.getServiceType().getDescription().equalsIgnoreCase(NEW_CONSTRUCTION)
			                    			|| bpaFee.getServiceType().getDescription().equalsIgnoreCase(RECONSTRUCTION)) {
			                    			multiplier = TWO_FIVE;
			                    		}else if(bpaFee.getServiceType().getDescription().equalsIgnoreCase(ADDING_OF_EXTENSION)
				                    			|| bpaFee.getServiceType().getDescription().equalsIgnoreCase(ALTERATION)) {
			                    			multiplier = ONE_TWO_FIVE;
			                    		}
			                    		
			                    		BigDecimal totalAmount = getTotalScruitnyFee(application.getBuildingDetail(), multiplier);
		                            	
		                            	if(totalAmount.compareTo(BigDecimal.ZERO)>=0) {
		                            		permitFee.getApplicationFee()
		                                    .addApplicationFeeDetail(
		                                            buildApplicationFeeDetail(bpaFee, permitFee.getApplicationFee(), totalAmount));
		                            		if(totalAmount.compareTo(BigDecimal.ZERO)>0) {
		                            			BigDecimal gstAmount = getTotalAmountOfGST(totalAmount);
		                            			permitFee.getApplicationFee()
		                                        .addApplicationFeeDetail(
		                                                buildApplicationFeeDetail(bpaGST, permitFee.getApplicationFee(), gstAmount));
		                            		}
		                            	}
		                            }else if (BpaConstants.LABOURCESS.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())) {
		                            	BigDecimal totalAmount = getTotalAmountOfLabourCess(application.getBuildingDetail(), plan, mostRestrictiveFarHelper);
		                            	if(totalAmount.compareTo(BigDecimal.ZERO)>=0) {
		                            		permitFee.getApplicationFee()
		                                    .addApplicationFeeDetail(
		                                            buildApplicationFeeDetail(bpaFee, permitFee.getApplicationFee(), totalAmount));
		                            	}                            	
		                            }else if (BpaConstants.RULE_5_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())) {
		                            	 boolean isWorkAlreadyStarted = (null!=checkIsWorkAlreadyStarted(application))?checkIsWorkAlreadyStarted(application):false;
		                            	 if(isWorkAlreadyStarted) {
		                            		 boolean isWorkInProgress = checkIsWorkInProgress(application);
		                            		 BigDecimal totalAmount = BigDecimal.ZERO;
		                            		 if(isWorkInProgress) {
		                            			 BigDecimal totalConstArea = new BigDecimal(application.getSiteDetail().get(0).getStateOfConstruction());
		                            			 totalConstArea = totalConstArea.setScale(2, BigDecimal.ROUND_HALF_UP);
		                            			 totalAmount = getTotalAmountOfRule5(application.getBuildingDetail(), plan, mostRestrictiveFarHelper, isWorkInProgress, totalConstArea);
		                            		 }else {
		                            			 totalAmount = getTotalAmountOfRule5(application.getBuildingDetail(), plan, mostRestrictiveFarHelper, isWorkInProgress, BigDecimal.ZERO);
		                            		 }
			                            	 if(totalAmount.compareTo(BigDecimal.ZERO)>=0) {
			                            		permitFee.getApplicationFee()
			                                    .addApplicationFeeDetail(
			                                            buildApplicationFeeDetail(bpaFee, permitFee.getApplicationFee(), totalAmount));
			                            	 }  
		                            	 }
		                            }else if (BpaConstants.ADDITIONAL_COVERAGE_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())) {
		                            	/*BigDecimal totalAmount = BigDecimal.ZERO;
										if(!bpaFee.getServiceType().getDescription().equalsIgnoreCase(NEW_CONSTRUCTION)) {
			                            	totalAmount = totalAmount.add(getTotalAmountForAdditionalCoverage(application.getBuildingDetail()));	                            	  
										}
										if(totalAmount.compareTo(BigDecimal.ZERO)>=0) {
											permitFee.getApplicationFee().addApplicationFeeDetail(buildApplicationFeeDetail(bpaFee, permitFee.getApplicationFee(), totalAmount));
										}*/
		                            }
		                    	}
		                    }
		            	}
		            }
	    		}
	    	}
	    }

	    protected ApplicationFeeDetail buildApplicationFeeDetail(final BpaFeeMapping bpaFee, final ApplicationFee applicationFee,
				BigDecimal amount) {
			ApplicationFeeDetail feeDetail = new ApplicationFeeDetail();
			feeDetail.setAmount(amount.setScale(0, BigDecimal.ROUND_HALF_UP));
			feeDetail.setBpaFeeMapping(bpaFee);
			feeDetail.setApplicationFee(applicationFee);
			return feeDetail;
		}

		protected BigDecimal calculatePermitFee(BigDecimal inputArea, BigDecimal feeAmount, Boolean isCharitable) {
			if(isCharitable)
			    return inputArea.multiply(feeAmount).multiply(BigDecimal.valueOf(50)).divide(BigDecimal.valueOf(100));
			else
				return inputArea.multiply(feeAmount);
		}

	    /**
	     * @param application
	     * @return is work already started or not ?
	     */
	    protected Boolean checkIsWorkAlreadyStarted(final BpaApplication application) {
	        return application.getSiteDetail().get(0).getIsappForRegularization();
	    }
	    
	    protected Boolean checkIsWorkInProgress(final BpaApplication application) {
	        String constStage = (null!=application.getSiteDetail().get(0).getConstStages())? application.getSiteDetail().get(0).getConstStages().getCode() : EMPTY;
	        return IN_PROGRESS.equalsIgnoreCase(constStage);
	    }
  
	    protected BigDecimal getBuiltUpInputUnitForEachServiceType(final BpaApplication application, final String serviceTypeCode) {
	        BigDecimal inputUnit = BigDecimal.ZERO;

	        if (BpaConstants.getBpaFeeCateory1().contains(serviceTypeCode)) {
	            inputUnit = getTotalBuiltUpArea(application);
	        } else if (BpaConstants.getBpaFeeCateory2().contains(serviceTypeCode)) { // Sub-Division
	                                                                                 // of
	                                                                                 // plot/Land
	                                                                                 // Development
	            inputUnit = application.getSiteDetail().get(0).getExtentinsqmts();
	        } else if ("10".equals(serviceTypeCode)) { // well
	            inputUnit = application.getSiteDetail().get(0).getDwellingunitnt();
	        } else if ("11".equals(serviceTypeCode)) { // Compound Wall
	            inputUnit = application.getSiteDetail().get(0).getLengthOfCompoundWall();
	        } else if ("14".equals(serviceTypeCode)) { // Tower Construction
	            inputUnit = application.getSiteDetail().get(0).getErectionoftower();
	        } else if ("12".equals(serviceTypeCode)) { // Shutter or Door
	                                                   // Conversion/Erection
	            inputUnit = application.getSiteDetail().get(0).getShutter();
	        } else if ("13".equals(serviceTypeCode)) { // Roof Conversion
	            inputUnit = application.getSiteDetail().get(0).getRoofConversion();
	        } else if ("15".equals(serviceTypeCode)) { // Pole Structures
	            inputUnit = application.getSiteDetail().get(0).getNoOfPoles();
	        } else if ("09".equals(serviceTypeCode)) { // hut or shed
	            inputUnit = application.getSiteDetail().get(0).getNoOfHutOrSheds();
	        }
	        return inputUnit;
	    }

	    /***
	     * Calculate Area for additional fee calculation.
	     * 
	     * @param application
	     * @return
	     */

	    public BigDecimal calculateAreaForAdditionalFeeCalculation(final BpaApplication application) {
	        BigDecimal extentOfLand = application.getSiteDetail().get(0).getExtentinsqmts();
	        BigDecimal minimumFARWithOutAdditionalFee;
	        BigDecimal minimumFARWithAdditionalFee;
	        BigDecimal weightageAvgFAR;
	        BigDecimal maximumPermittedFloorAreaWithAddnFee = BigDecimal.ZERO;
	        BigDecimal maximumPermittedFARWithAdditionalFee;
	        BigDecimal maximumPermittedFloorArea = BigDecimal.ZERO;
	        BigDecimal maximumPermittedFAR;
	        BigDecimal additionalFeeCalculationArea = BigDecimal.ZERO;

	        Map<SubOccupancy, BigDecimal> occFloorArea = getOccupancyWiseFloorArea(application.getBuildingDetail());
	        Map<SubOccupancy, BigDecimal> existBldgOccupancyWiseFloorArea = getExistBldgOccupancyWiseFloorArea(
	                application.getExistingBuildingDetails());
	        BigDecimal proposedBldgFloorArea = applicationBpaService.getTotalFloorArea(application);
	        BigDecimal existBldgFloorArea = applicationBpaService.getExistBldgTotalFloorArea(application);
	        BigDecimal totalFloorArea = proposedBldgFloorArea.add(existBldgFloorArea);
	        if (totalFloorArea.compareTo(BigDecimal.ZERO) > 0) {
	            if (extentOfLand.compareTo(new BigDecimal(5000)) <= 0) {

	                minimumFARWithOutAdditionalFee = minimumFARWithoutAdditionalFee(application);
	                minimumFARWithAdditionalFee = minimumFARWithAdditionalFee(application);
	                maximumPermittedFAR = minimumFARWithOutAdditionalFee.multiply(extentOfLand);

	                // Mean additional fee has to collect BUT CITIZEN NOT READY TO
	                // PAY ADDITIONAL TAX
	                // AdditionalFeePaymentAccepted condition need to be check if on
	                // submission suppose data captured
	                if (totalFloorArea.compareTo(maximumPermittedFAR) > 0) {
	                    maximumPermittedFARWithAdditionalFee = minimumFARWithAdditionalFee.multiply(extentOfLand);
	                    // Calclulate additional Fee.
	                    if (totalFloorArea.compareTo(maximumPermittedFARWithAdditionalFee) <= 0) {
	                        additionalFeeCalculationArea = proposedBldgFloorArea.subtract(maximumPermittedFAR);
	                    }
	                }
	            } else // above area greater than 5000sq.mt.
	            {
	                weightageAvgFAR = weightageAverageFarWithoutAdditionalFee(occFloorArea,
	                        existBldgOccupancyWiseFloorArea);
	                if (weightageAvgFAR != null)
	                    maximumPermittedFloorArea = weightageAvgFAR.multiply(extentOfLand);
	                // Mean Aggregate violation of area
	                // AdditionalFeePaymentAccepted check need to be added if
	                // require(i.e:application.getBuildingDetail().get(0).getAdditionalFeePaymentAccepted())
	                if (totalFloorArea.compareTo(maximumPermittedFloorArea) > 0) {
	                    weightageAvgFAR = weightageAverageFarWithAdditionalFee(occFloorArea,
	                            existBldgOccupancyWiseFloorArea);
	                    if (weightageAvgFAR != null)
	                        maximumPermittedFloorAreaWithAddnFee = weightageAvgFAR.multiply(extentOfLand);
	                    // Mean Aggregate violation of area
	                    if (totalFloorArea.compareTo(maximumPermittedFloorAreaWithAddnFee) <= 0) {
	                        // Calculate additional Fee.
	                        additionalFeeCalculationArea = proposedBldgFloorArea.subtract(maximumPermittedFloorArea);
	                    }
	                }
	            }
	        }
	        return additionalFeeCalculationArea.setScale(2, RoundingMode.HALF_UP);
	    }
	    
	    
	    public BigDecimal calculateBuiltUpAreaForAdditionalFeeCalculation(final BpaApplication application) {
	        BigDecimal extentOfLand = application.getSiteDetail().get(0).getExtentinsqmts();
	        BigDecimal minimumFARWithOutAdditionalFee;
	        BigDecimal minimumFARWithAdditionalFee;
	        BigDecimal weightageAvgFAR;
	        BigDecimal maximumPermittedBuiltUpAreaWithAddnFee = BigDecimal.ZERO;
	        BigDecimal maximumPermittedFARWithAdditionalFee;
	        BigDecimal maximumPermittedBuiltUpArea = BigDecimal.ZERO;
	        BigDecimal maximumPermittedFAR;
	        BigDecimal additionalFeeCalculationArea = BigDecimal.ZERO;

	        Map<SubOccupancy, BigDecimal> occBuiltUpArea = getOccupancyWiseBuiltUpArea(application.getBuildingDetail());
	        Map<SubOccupancy, BigDecimal> existBldgOccupancyWiseBuiltUpArea = getExistBldgOccupancyWiseBuiltUpArea(
	                application.getExistingBuildingDetails());
	        BigDecimal proposedBldgBuiltUpArea = getTotalBuiltUpArea(application);
	        BigDecimal existBldgBuiltUpArea = getExistBldgTotalBuiltUpArea(application);
	        BigDecimal totalBuiltUpArea = proposedBldgBuiltUpArea.add(existBldgBuiltUpArea);
	        if (totalBuiltUpArea.compareTo(BigDecimal.ZERO) > 0) {
	            if (extentOfLand.compareTo(new BigDecimal(5000)) <= 0) {

	                minimumFARWithOutAdditionalFee = minimumFARWithoutAdditionalFee(application);
	                minimumFARWithAdditionalFee = minimumFARWithAdditionalFee(application);
	                maximumPermittedFAR = minimumFARWithOutAdditionalFee.multiply(extentOfLand);

	                // Mean additional fee has to collect BUT CITIZEN NOT READY TO
	                // PAY ADDITIONAL TAX
	                // AdditionalFeePaymentAccepted condition need to be check if on
	                // submission suppose data captured
	                if (totalBuiltUpArea.compareTo(maximumPermittedFAR) > 0) {
	                    maximumPermittedFARWithAdditionalFee = minimumFARWithAdditionalFee.multiply(extentOfLand);
	                    // Calclulate additional Fee.
	                    if (totalBuiltUpArea.compareTo(maximumPermittedFARWithAdditionalFee) <= 0) {
	                        additionalFeeCalculationArea = proposedBldgBuiltUpArea.subtract(maximumPermittedFAR);
	                    }
	                }
	            } else // above area greater than 5000sq.mt.
	            {
	                weightageAvgFAR = weightageAverageFarWithoutAdditionalFee(occBuiltUpArea,
	                		existBldgOccupancyWiseBuiltUpArea);
	                if (weightageAvgFAR != null)
	                    maximumPermittedBuiltUpArea = weightageAvgFAR.multiply(extentOfLand);
	                // Mean Aggregate violation of area
	                // AdditionalFeePaymentAccepted check need to be added if
	                // require(i.e:application.getBuildingDetail().get(0).getAdditionalFeePaymentAccepted())
	                if (totalBuiltUpArea.compareTo(maximumPermittedBuiltUpArea) > 0) {
	                    weightageAvgFAR = weightageAverageFarWithAdditionalFee(occBuiltUpArea,
	                    		existBldgOccupancyWiseBuiltUpArea);
	                    if (weightageAvgFAR != null)
	                        maximumPermittedBuiltUpAreaWithAddnFee = weightageAvgFAR.multiply(extentOfLand);
	                    // Mean Aggregate violation of area
	                    if (totalBuiltUpArea.compareTo(maximumPermittedBuiltUpAreaWithAddnFee) <= 0) {
	                        // Calculate additional Fee.
	                        additionalFeeCalculationArea = proposedBldgBuiltUpArea.subtract(maximumPermittedBuiltUpArea);
	                    }
	                }
	            }
	        }
	        return additionalFeeCalculationArea.setScale(2, RoundingMode.HALF_UP);
	    }

	    /***
	     * Group block wise occupancy and floor area
	     * 
	     * @param buildingDetails
	     * @return
	     */
	    // Floor Area considered here.
	    public Map<SubOccupancy, BigDecimal> getOccupancyWiseFloorArea(List<BuildingDetail> buildingDetails) {
	        Map<SubOccupancy, BigDecimal> occupancyWiseFloorArea = new ConcurrentHashMap<>();
	        for (BuildingDetail building : buildingDetails) {
	            for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
	                if (occupancyWiseFloorArea.containsKey(floor.getSubOccupancy())) {
	                    occupancyWiseFloorArea.put(floor.getSubOccupancy(),
	                            occupancyWiseFloorArea.get(floor.getSubOccupancy()).add(floor.getFloorArea()));
	                } else {
	                    occupancyWiseFloorArea.put(floor.getSubOccupancy(), floor.getFloorArea());
	                }
	            }
	        }
	        return occupancyWiseFloorArea;
	    }
	    
	    
	    public Map<SubOccupancy, BigDecimal> getOccupancyWiseBuiltUpArea(List<BuildingDetail> buildingDetails) {
	        Map<SubOccupancy, BigDecimal> occupancyWiseBuiltUpArea = new ConcurrentHashMap<>();
	        for (BuildingDetail building : buildingDetails) {
	            for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
	                if (occupancyWiseBuiltUpArea.containsKey(floor.getSubOccupancy())) {
	                	occupancyWiseBuiltUpArea.put(floor.getSubOccupancy(),
	                			occupancyWiseBuiltUpArea.get(floor.getSubOccupancy()).add(floor.getPlinthArea()));
	                } else {
	                	occupancyWiseBuiltUpArea.put(floor.getSubOccupancy(), floor.getPlinthArea());
	                }
	            }
	        }
	        return occupancyWiseBuiltUpArea;
	    }
	    
	    public Map<SubOccupancy, BigDecimal> getExistBldgOccupancyWiseBuiltUpArea(List<ExistingBuildingDetail> existBldgDtls) {
	        Map<SubOccupancy, BigDecimal> occupancyWiseFloorArea = new ConcurrentHashMap<>();
	        for (ExistingBuildingDetail building : existBldgDtls) {
	            for (ExistingBuildingFloorDetail floor : building.getExistingBuildingFloorDetails()) {
	                if (occupancyWiseFloorArea.containsKey(floor.getSubOccupancy())) {
	                    occupancyWiseFloorArea.put(floor.getSubOccupancy(),
	                            occupancyWiseFloorArea.get(floor.getSubOccupancy()).add(floor.getPlinthArea()));
	                } else {
	                    occupancyWiseFloorArea.put(floor.getSubOccupancy(), floor.getPlinthArea());
	                }
	            }
	        }
	        return occupancyWiseFloorArea;
	    }


	    /***
	     * Group block wise occupancy and floor area
	     * 
	     * @param existBldgDtls
	     * @return
	     */
	    // Floor Area considered here.
	    public Map<SubOccupancy, BigDecimal> getExistBldgOccupancyWiseFloorArea(List<ExistingBuildingDetail> existBldgDtls) {
	        Map<SubOccupancy, BigDecimal> occupancyWiseFloorArea = new ConcurrentHashMap<>();
	        for (ExistingBuildingDetail building : existBldgDtls) {
	            for (ExistingBuildingFloorDetail floor : building.getExistingBuildingFloorDetails()) {
	                if (occupancyWiseFloorArea.containsKey(floor.getSubOccupancy())) {
	                    occupancyWiseFloorArea.put(floor.getSubOccupancy(),
	                            occupancyWiseFloorArea.get(floor.getSubOccupancy()).add(floor.getFloorArea()));
	                } else {
	                    occupancyWiseFloorArea.put(floor.getSubOccupancy(), floor.getFloorArea());
	                }
	            }
	        }
	        return occupancyWiseFloorArea;
	    }

	    public Map<String, Map<SubOccupancy, BigDecimal>> groupBlockOccupancyFloorArea(List<BuildingDetail> buildingDetails) {
	        Map<String, Map<SubOccupancy, BigDecimal>> groupByBlkOccupancyFloorArea = new ConcurrentHashMap<>();
	        for (BuildingDetail building : buildingDetails) {
	            Map<SubOccupancy, BigDecimal> subMap = new ConcurrentHashMap<>();
	            for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
	                if (subMap.containsKey(floor.getSubOccupancy())) {
	                    subMap.put(floor.getSubOccupancy(), subMap.get(floor.getSubOccupancy()).add(floor.getFloorArea()));
	                } else {
	                    subMap.put(floor.getSubOccupancy(), floor.getFloorArea());
	                }
	            }
	            groupByBlkOccupancyFloorArea.put(building.getName(), subMap);
	        }
	        return groupByBlkOccupancyFloorArea;
	    }
	    
	    public Map<String, Map<SubOccupancy, BigDecimal>> groupBlockOccupancyBuiltUpArea(List<BuildingDetail> buildingDetails) {
	        Map<String, Map<SubOccupancy, BigDecimal>> groupByBlkOccupancyFloorArea = new ConcurrentHashMap<>();
	        for (BuildingDetail building : buildingDetails) {
	            Map<SubOccupancy, BigDecimal> subMap = new ConcurrentHashMap<>();
	            for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
	                if (subMap.containsKey(floor.getSubOccupancy())) {
	                    subMap.put(floor.getSubOccupancy(), subMap.get(floor.getSubOccupancy()).add(floor.getPlinthArea()));
	                } else {
	                    subMap.put(floor.getSubOccupancy(), floor.getPlinthArea());
	                }
	            }
	            groupByBlkOccupancyFloorArea.put(building.getName(), subMap);
	        }
	        return groupByBlkOccupancyFloorArea;
	    }
	    
	    public BigDecimal getTotalBuiltUpArea(final BpaApplication application) {
	        BigDecimal totalBuiltUpArea = BigDecimal.ZERO;
	        for (BuildingDetail buildingDetail : application.getBuildingDetail())
	            for (ApplicationFloorDetail floorDetails : buildingDetail.getApplicationFloorDetails())
	            	totalBuiltUpArea = totalBuiltUpArea.add(floorDetails.getPlinthArea());
	        return totalBuiltUpArea;
	    }
	    
	    public BigDecimal getExistBldgTotalBuiltUpArea(final BpaApplication application) {
	        BigDecimal totalBuiltUpArea = BigDecimal.ZERO;
	        if (!application.getExistingBuildingDetails().isEmpty()
	                && application.getExistingBuildingDetails().get(0).getTotalPlintArea() != null)
	            for (ExistingBuildingFloorDetail floor : application.getExistingBuildingDetails().get(0)
	                    .getExistingBuildingFloorDetails()) {
	            	totalBuiltUpArea = totalBuiltUpArea.add(floor.getPlinthArea());
	            }
	        return totalBuiltUpArea;
	    }
	    

	    

	    /***
	     * Minimum FAR Without Additional Fee
	     * 
	     * @param application
	     * @return
	     */
	    public BigDecimal minimumFARWithoutAdditionalFee(final BpaApplication application) {
	        List<BigDecimal> minimumFARs = new ArrayList<>();
	        for (BuildingDetail bldg : application.getBuildingDetail()) {
	            for (ApplicationFloorDetail floor : bldg.getApplicationFloorDetails()) {
	                minimumFARs.add(floor.getSubOccupancy().getMinFar());
	            }
	        }
	        if (!application.getExistingBuildingDetails().isEmpty()
	                && application.getExistingBuildingDetails().get(0).getTotalPlintArea() != null)
	            for (ExistingBuildingDetail existBldg : application.getExistingBuildingDetails())
	                for (ExistingBuildingFloorDetail floorDetails : existBldg.getExistingBuildingFloorDetails())
	                    minimumFARs.add(floorDetails.getSubOccupancy().getMaxFar());
	        return Collections.min(minimumFARs);
	    }

	    /***
	     * Minimum FAR with Additional Fee
	     * 
	     * @param application
	     * @return
	     */
	    public BigDecimal minimumFARWithAdditionalFee(final BpaApplication application) {
	        List<BigDecimal> maximumFARs = new ArrayList<>();
	        for (BuildingDetail bldg : application.getBuildingDetail()) {
	            for (ApplicationFloorDetail floor : bldg.getApplicationFloorDetails()) {
	                maximumFARs.add(floor.getSubOccupancy().getMaxFar());
	            }
	        }
	        if (!application.getExistingBuildingDetails().isEmpty()
	                && application.getExistingBuildingDetails().get(0).getTotalPlintArea() != null)
	            for (ExistingBuildingDetail existBldg : application.getExistingBuildingDetails())
	                for (ExistingBuildingFloorDetail floorDetails : existBldg.getExistingBuildingFloorDetails())
	                    maximumFARs.add(floorDetails.getSubOccupancy().getMaxFar());
	        return Collections.min(maximumFARs);
	    }

	    /***
	     * Weightage Average FAR Without Additional Fee
	     * 
	     * @param occupancyWiseFloorArea
	     * @return
	     */
	    public BigDecimal weightageAverageFarWithoutAdditionalFee(Map<SubOccupancy, BigDecimal> occupancyWiseFloorArea,
	            Map<SubOccupancy, BigDecimal> existBldgOccupancyWiseFloorArea) {
	        BigDecimal maxPermittedFloorArea = BigDecimal.ZERO;
	        BigDecimal sumOfFloorArea = BigDecimal.ZERO;
	        for (Entry<SubOccupancy, BigDecimal> setOfOccupancy : occupancyWiseFloorArea.entrySet()) {
	            maxPermittedFloorArea = maxPermittedFloorArea
	                    .add(setOfOccupancy.getKey().getMinFar().multiply(setOfOccupancy.getValue()));
	            sumOfFloorArea = sumOfFloorArea.add(setOfOccupancy.getValue());
	        }
	        if (!existBldgOccupancyWiseFloorArea.isEmpty()) {
	            for (Entry<SubOccupancy, BigDecimal> occupancy : existBldgOccupancyWiseFloorArea.entrySet()) {
	                maxPermittedFloorArea = maxPermittedFloorArea
	                        .add(occupancy.getKey().getMinFar().multiply(occupancy.getValue()));
	                sumOfFloorArea = sumOfFloorArea.add(occupancy.getValue());
	            }
	        }
	        if (sumOfFloorArea.compareTo(BigDecimal.ZERO) > 0)
	            return maxPermittedFloorArea.divide(sumOfFloorArea, 6, RoundingMode.HALF_UP).setScale(6,
	                    RoundingMode.HALF_UP);

	        return null;
	    }

	    /***
	     * Weightage Average FAR With Additional Fee
	     * 
	     * @param occupancyWiseFloorArea
	     * @return
	     */
	    public BigDecimal weightageAverageFarWithAdditionalFee(Map<SubOccupancy, BigDecimal> occupancyWiseFloorArea,
	            Map<SubOccupancy, BigDecimal> existBldgOccupancyWiseFloorArea) {

	        BigDecimal maxPermittedFloorArea = BigDecimal.ZERO;
	        BigDecimal sumOfFloorArea = BigDecimal.ZERO;
	        for (Entry<SubOccupancy, BigDecimal> setOfOccupancy : occupancyWiseFloorArea.entrySet()) {
	            maxPermittedFloorArea = maxPermittedFloorArea
	                    .add(setOfOccupancy.getKey().getMaxFar().multiply(setOfOccupancy.getValue()));
	            sumOfFloorArea = sumOfFloorArea.add(setOfOccupancy.getValue());
	        }
	        if (!existBldgOccupancyWiseFloorArea.isEmpty()) {
	            for (Entry<SubOccupancy, BigDecimal> occupancy : existBldgOccupancyWiseFloorArea.entrySet()) {
	                maxPermittedFloorArea = maxPermittedFloorArea
	                        .add(occupancy.getKey().getMaxFar().multiply(occupancy.getValue()));
	                sumOfFloorArea = sumOfFloorArea.add(occupancy.getValue());
	            }
	        }
	        if (sumOfFloorArea.compareTo(BigDecimal.ZERO) > 0)
	            return maxPermittedFloorArea.divide(sumOfFloorArea, 6, RoundingMode.HALF_UP).setScale(6,
	                    RoundingMode.HALF_UP);
	        return null;
	    }

	    @Override
	    public EgDemand createDemand(final BpaApplication application) {
	        final Map<String, BigDecimal> feeDetails = new HashMap<>();
	        if (application.getApplicationType().getName().equals(LOWRISK)) {
	            applicationFeeService.setPermitFee(application, feeDetails);
	        }

	        EgDemand egDemand = null;
	        final Installment installment = installmentDao.getInsatllmentByModuleForGivenDateAndInstallmentType(
	                moduleService.getModuleByName(BpaConstants.EGMODULE_NAME), new Date(), BpaConstants.YEARLY);
	        
	        List<BpaFeeMapping> bpaAdmissionFees = bpaFeeMappingService
	                .getFeeForListOfServices(application.getServiceType().getId(), BpaConstants.BPA_APP_FEE);
   		   if(application.getApplicationType().getId() != null) 
   			  application.setAdmissionfeeAmount(setAdmissionFeeAmount(application, new ArrayList<>()));
         
	        
	        feeDetails.put(bpaAdmissionFees.get(0).getBpaFeeCommon().getCode(), application.getAdmissionfeeAmount());
	        BigDecimal baseDemandAmount = feeDetails.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);  
	        if (installment != null) {
	            final Set<EgDemandDetails> dmdDetailSet = new HashSet<>();
	            for (final Entry<String, BigDecimal> demandReason : feeDetails.entrySet())
	                dmdDetailSet.add(createDemandDetails(feeDetails.get(demandReason.getKey()), demandReason.getKey(), installment));
	            egDemand = new EgDemand();
	            egDemand.setEgInstallmentMaster(installment);
	            egDemand.getEgDemandDetails().addAll(dmdDetailSet);
	            egDemand.setIsHistory("N");
	            egDemand.setBaseDemand(baseDemandAmount);
	            egDemand.setCreateDate(new Date());
	            egDemand.setModifiedDate(new Date());
	        }

	        return egDemand;
	    }
	    
	    public EgDemand createDemandWhenFeeCollectionNotRequire(BpaApplication application) {
	        
	        
	        EgDemand egDemand = new EgDemand();
	         final Set<EgDemandDetails> dmdDetailSet = new HashSet<>();

	        final Installment installment = installmentDao.getInsatllmentByModuleForGivenDateAndInstallmentType(
	                moduleService.getModuleByName(BpaConstants.EGMODULE_NAME), new Date(), BpaConstants.YEARLY);
	        Map<String, BigDecimal> feeDetails = new  HashMap<>();
	        if (application.getApplicationType().getName().equals(LOWRISK) && installment!=null) {
	        	applicationFeeService.setPermitFee(application, feeDetails);
	         for (final Entry<String, BigDecimal> demandReason : feeDetails.entrySet())
	             dmdDetailSet.add(createDemandDetails(feeDetails.get(demandReason.getKey()), demandReason.getKey(), installment));
	        }
	        egDemand.setEgInstallmentMaster(installment);
	        egDemand.setIsHistory("N");
	        egDemand.setCreateDate(new Date());
	        if (application.getApplicationType().getName().equals(LOWRISK)) {
	            egDemand.getEgDemandDetails().addAll(dmdDetailSet);
	            egDemand.setBaseDemand(feeDetails.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add));
	        }
	        else {
	         egDemand.setEgDemandDetails(new HashSet<>());
	         egDemand.setBaseDemand(BigDecimal.ZERO);
	        }

	        egDemand.setModifiedDate(new Date());
	        return egDemand;
	    }
	    
	    private EgDemandDetails createDemandDetails(final BigDecimal amount, final String demandReason,
	            final Installment installment) {
	        final EgDemandReason demandReasonObj = bpaDemandService.getDemandReasonByCodeAndInstallment(demandReason,
	                installment);
	        final EgDemandDetails demandDetail = new EgDemandDetails();
	        demandDetail.setAmount(amount);
	        demandDetail.setAmtCollected(BigDecimal.ZERO);
	        demandDetail.setAmtRebate(BigDecimal.ZERO);
	        demandDetail.setEgDemandReason(demandReasonObj);
	        demandDetail.setCreateDate(new Date());
	        demandDetail.setModifiedDate(new Date());
	        return demandDetail;
	    }
	    
	    public BigDecimal getTotalScruitnyFee(List<BuildingDetail> buildingDetails, BigDecimal multiplier) {
	        BigDecimal totalAmount = BigDecimal.ZERO;
	        for (BuildingDetail building : buildingDetails) {
	            for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
	            	totalAmount = totalAmount.add(floor.getFloorArea().multiply(SQMT_SQFT_MULTIPLIER).multiply(multiplier).setScale(2, BigDecimal.ROUND_UP));
	            }
	        }
	        return totalAmount;
	    }
	    
	    public BigDecimal getTotalAmountOfGST(BigDecimal amount) {
	    	BigDecimal totalAmount = BigDecimal.ZERO;
	    	if(amount.compareTo(BigDecimal.ZERO)>0) {
	    		totalAmount = totalAmount.add((amount.multiply(EIGHTEEN).divide(HUNDRED)).setScale(2, BigDecimal.ROUND_HALF_UP));
	    	}
	        return totalAmount;
	    }
	    
	    public BigDecimal getTotalAmountOfLabourCess(List<BuildingDetail> buildingDetails, Plan plan, OccupancyTypeHelper mostRestrictiveFarHelper) {
	    	BigDecimal totalAmount = BigDecimal.ZERO;
	    	BigDecimal multiplier = BigDecimal.ZERO;	    	
	    	if(BpaConstants.IT_MCL.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.IT_MCM.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.IT_MCS.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
    			|| BpaConstants.R1.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
    			|| BpaConstants.F_H.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
    			|| BpaConstants.F_M.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
    			|| BpaConstants.F_TCIM.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
    			|| BpaConstants.F_BH.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.F_BBM.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.F_TS.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.F_PP.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.F_CD.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.G_GBAC.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.G_GBZP.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())					
				|| BpaConstants.P_D.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.P_P.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.P_F.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.P_N.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.P_H.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.P_CC.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.P_SS.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.P_CNA.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.P_R.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())					
				|| BpaConstants.B_EC.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.B_HEI.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.B_H.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())										
				|| BpaConstants.ITH_H.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.ITH_C.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.ITH_CC.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.ITH_R.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.ITH_GH.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())					
				|| BpaConstants.IP_I.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.IP_R.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.IP_C.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())) {
	    		multiplier = TWENTY_FIVE_HUNDRED;
	    	}else if(BpaConstants.F_B.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())) {
	    		multiplier = THOUSAND;
	    	}else if(BpaConstants.A_P.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
	    			|| BpaConstants.A_G.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())	    			
					|| BpaConstants.F_CFI.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())					
					|| BpaConstants.F_SCO.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())) {
	    		multiplier = FIFTEEN_HUNDRED;
	    	}else {
	    		multiplier = TWENTY_FIVE_HUNDRED;
	    	}
	    	
	    	if(multiplier.compareTo(BigDecimal.ZERO) > 0) {
		        BigDecimal estimatedAmount = BigDecimal.ZERO;
		        for (BuildingDetail building : buildingDetails) {
		            for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
		            	estimatedAmount = estimatedAmount.add(floor.getFloorArea().multiply(SQMT_SQFT_MULTIPLIER).multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP));
		            }
		        }
		        
		        for (Block b : plan.getBlocks()) {
		            Building building = b.getBuilding();
		            if (building != null) {
		                for (Floor floor : building.getFloors()) {
		                    if (floor.getOverHangs() != null && !floor.getOverHangs().isEmpty()) {
		                        List<BigDecimal> areas = floor.getOverHangs().stream().map(overhang -> overhang.getArea())
		                                .collect(Collectors.toList());
		                        BigDecimal totalCajjaArea = areas.stream()
		                                .filter(Objects::nonNull)
		                                .reduce(BigDecimal.ZERO, BigDecimal::add);
		                        estimatedAmount = estimatedAmount.add(totalCajjaArea.multiply(SQMT_SQFT_MULTIPLIER).multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP));
		                    }
		                }
		            }
		        }
		        if(estimatedAmount.compareTo(TEN_LAKH)>=0) {
		        	totalAmount = totalAmount.add((estimatedAmount.divide(HUNDRED)).setScale(2, BigDecimal.ROUND_HALF_UP));	
		        }
	    	}	        
	        return totalAmount;
	    }
	    
	    public BigDecimal getTotalAmountForAdditionalCoverage(List<BuildingDetail> buildingDetails) {
	        BigDecimal totalAmount = BigDecimal.ZERO;
	        for (BuildingDetail building : buildingDetails) {
	            for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
	            	if(floor.getFloorNumber() == 0) {
	            		totalAmount = totalAmount.add(floor.getFloorArea().multiply(SQMT_SQFT_MULTIPLIER).multiply(HUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP));
	            	}/*else if(floor.getFloorNumber() == 1 || floor.getFloorNumber() == 2) {
	            		totalAmount = totalAmount.add(floor.getFloorArea().multiply(SQMT_SQFT_MULTIPLIER).multiply(FORTY).setScale(2, BigDecimal.ROUND_HALF_UP));
	            	}*/
	            }
	        }
	        return totalAmount;
	    }
	    
	    public BigDecimal getTotalAmountOfRule5(List<BuildingDetail> buildingDetails, Plan plan, OccupancyTypeHelper mostRestrictiveFarHelper, boolean isWorkInProgress, BigDecimal totalConstructedArea) {
	    	BigDecimal totalAmount = BigDecimal.ZERO;
	    	BigDecimal totalArea = BigDecimal.ZERO;
	    	if(!isWorkInProgress) {
		    	BigDecimal totalCoveredArea = BigDecimal.ZERO;
		    	BigDecimal totalBasementArea = BigDecimal.ZERO;	    	
		    	for (Block b : plan.getBlocks()) {
		            Building building = b.getBuilding();
		            if (building != null) {
		            	if(null!=building.getCoverageArea()) {
		            		totalCoveredArea = totalCoveredArea.add(building.getCoverageArea());
		            	}
		                for (Floor floor : building.getFloors()) {
		                	if (floor.getNumber() < 0) {
		                		if(null!=floor.getArea()) {
		                			totalBasementArea = totalBasementArea.add(floor.getArea());
		                		}
		                	}
		                }
		            }
		        }
		    	totalArea = totalArea.add(totalCoveredArea).add(totalBasementArea);
	    	}else {
	    		totalArea = totalArea.add(totalConstructedArea);
	    	}
	    	BigDecimal multiplier = BigDecimal.ZERO;	    	
	    	if(BpaConstants.F_H.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
    			|| BpaConstants.F_M.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
    			|| BpaConstants.F_TCIM.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
    			|| BpaConstants.F_CFI.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())					
				|| BpaConstants.F_SCO.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.F_BH.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.F_BBM.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.F_TS.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.F_PP.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.F_CD.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())) {
	    		multiplier = THIRTY;
	    	}else if(BpaConstants.A_P.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())) {
	    		multiplier = TEN;
	    	}else if(BpaConstants.A_G.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
	    			|| BpaConstants.IT_MCL.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.IT_MCM.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.IT_MCS.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
	    			|| BpaConstants.R1.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.G_GBAC.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.G_GBZP.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())					
					|| BpaConstants.P_D.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.P_P.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.P_F.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.P_N.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.P_H.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.P_CC.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.P_SS.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.P_CNA.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.P_R.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())					
					|| BpaConstants.B_EC.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.B_HEI.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.B_H.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())										
					|| BpaConstants.ITH_H.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.ITH_C.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.ITH_CC.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.ITH_R.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.ITH_GH.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())					
					|| BpaConstants.IP_I.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.IP_R.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.IP_C.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
					) {
	    		multiplier = TWENTY;
	    	}
	    	if(totalArea.compareTo(BigDecimal.ZERO) > 0 && multiplier.compareTo(BigDecimal.ZERO) > 0) {
	    		totalAmount = totalAmount.add(totalArea.multiply(SQMT_SQFT_MULTIPLIER).multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP));
	    	}
	    	return totalAmount;
	    }
	    
	    public BigDecimal getTotalSecurityFee(Plan plan, OccupancyTypeHelper mostRestrictiveFarHelper) {
	    	boolean isFeeDynamic=false;
	    	BigDecimal multiplier = BigDecimal.ZERO;
	    	BigDecimal totalAmount = BigDecimal.ZERO;
	    	if(BpaConstants.A_P.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
	    	   || BpaConstants.F_SCO.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
	    	   || BpaConstants.G_GBAC.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
	    	   || BpaConstants.G_GBZP.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())) {
	    		isFeeDynamic=false;
	    		multiplier = TEN_THOUSAND;
	    	}else if(BpaConstants.F_B.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())) {
	    		isFeeDynamic=false;
	    		multiplier = FIVE_THOUSAND;
	    	}else {
	    		isFeeDynamic=true;
	    		multiplier = TEN_THOUSAND;
	    	}	    	
	    	if(isFeeDynamic) {
	    		BigDecimal totalAreaOfPlot = plan.getPlot().getArea();
	    		BigDecimal totalAreaInHalfAcre = totalAreaOfPlot.divide(HALF_ACRE_IN_SQMT, 2, BigDecimal.ROUND_HALF_UP);
	    		int halfAcreCount = totalAreaInHalfAcre.intValue();
	    		halfAcreCount = (totalAreaInHalfAcre.compareTo(new BigDecimal(halfAcreCount))>=0)?halfAcreCount+1:halfAcreCount;	    		
	    		totalAmount = totalAmount.add(multiplier.multiply(new BigDecimal(halfAcreCount)).setScale(2, BigDecimal.ROUND_HALF_UP));
	    	}else {
	    		totalAmount = multiplier;
	    	}
	    	return totalAmount;
	    }
}