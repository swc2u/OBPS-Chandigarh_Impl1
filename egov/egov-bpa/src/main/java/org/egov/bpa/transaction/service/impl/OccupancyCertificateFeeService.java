/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2019>  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *      Further, all user interfaces, including but not limited to citizen facing interfaces,
 *         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *         derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *      For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *      For any further queries on attribution, including queries on brand guidelines,
 *         please contact contact@egovernments.org
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.transaction.service.impl;

import java.math.BigDecimal;
import org.egov.bpa.master.entity.BpaFeeMapping;
import org.egov.bpa.master.service.BpaFeeCommonService;
import org.egov.bpa.master.service.BpaFeeService;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.entity.oc.OccupancyFee;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.oc.OccupancyFeeService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.Plan;
import org.egov.commons.service.OccupancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OccupancyCertificateFeeService  {
	//    private static final String TOTAL_FLOOR_AREA = "totalFloorArea";
	//    private static final String OTHERS = "Others";
	//    private static final String RESIDENTIAL_DESC = "Residential";
    
    @Autowired
    protected BpaUtils bpaUtils;

    @Autowired
    protected OccupancyService occupancyService;

    @Autowired
    protected OccupancyFeeService ocFeeService;

    @Autowired
    protected BpaFeeService bpaFeeService;
    
    @Autowired
    protected BpaFeeCommonService bpaFeeCommonService;
    
    @Autowired
	protected ApplicationBpaService applicationBpaService;

    public void calculateOCFees(OccupancyCertificate oc, OccupancyFee ocFee) {
    	Plan bpaPlan = applicationBpaService.getPlanInfo(oc.getParent().geteDcrNumber());
    	Plan ocPlan = applicationBpaService.getPlanInfo(oc.geteDcrNumber());
    	
		//        Map<String, BigDecimal> ocProposedArea = bpaUtils.getProposedBuildingAreasOfOC(oc.getBuildings());
		//        Map<String, BigDecimal> totalProposedArea = bpaUtils.getTotalProposedArea(oc.getParent().getBuildingDetail());
		//        BigDecimal ocFloorArea = ocProposedArea.get(TOTAL_FLOOR_AREA) != null ? ocProposedArea.get(TOTAL_FLOOR_AREA) : BigDecimal.ZERO;
		//        BigDecimal proposedFloorArea = totalProposedArea.get(TOTAL_FLOOR_AREA) != null ? totalProposedArea.get(TOTAL_FLOOR_AREA) : BigDecimal.ZERO;
		//        BigDecimal maxPermittedFloorArea = proposedFloorArea.multiply(BigDecimal.valueOf(5)).divide(BigDecimal.valueOf(100)).add(proposedFloorArea);
		//        
		//        if (ocFloorArea.compareTo(proposedFloorArea) > 0 && ocFloorArea.compareTo(maxPermittedFloorArea) < 0) {
		//            BigDecimal deviatedArea = ocFloorArea.subtract(proposedFloorArea);
		//            calculateFeeByServiceType(oc, deviatedArea, ocFee);
		//        }
    	
    	calculateFeeByServiceType(oc, bpaPlan, ocPlan, ocFee);
    }
    
    public void calculateFeeByServiceType(OccupancyCertificate oc, Plan bpaPlan, Plan ocPlan, OccupancyFee ocFee) {
        for (BpaFeeMapping bpaFee : bpaFeeCommonService.getOCFeeForListOfServices(oc.getParent().getServiceType().getId())) {
        	BigDecimal amount = BigDecimal.ZERO;
        	if (BpaConstants.LABOURCESS.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
        		amount = amount.add(getTotalLabourCess(bpaPlan, ocPlan));
        	} else if (BpaConstants.ADDITIONAL_COVERAGE_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
        		amount = amount.add(getTotalAdditionalFee(bpaPlan, ocPlan));
        	} else if (BpaConstants.RULE_5_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
        		amount = amount.add(getTotalRuleFiveFee(bpaPlan, ocPlan));
        	} else if (BpaConstants.INTERNAL_CHANGES_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
        		amount = amount.add(getTotalInternalChangesFee(bpaPlan, ocPlan));
        	} else if (BpaConstants.EXCESS_COVERAGE_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
        		amount = amount.add(getTotalExcessCoverageFee(bpaPlan, ocPlan));
        	} else if (BpaConstants.GLAZING_OF_VERANDAH_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
        		amount = amount.add(getTotalGlazingVerandahFee(bpaPlan, ocPlan));
        	} else if (BpaConstants.ADDITIONAL_HEIGHT_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
        		amount = amount.add(getTotalAdditionalHeightFee(bpaPlan, ocPlan));
        	} else if (BpaConstants.PARTITIONS_ON_GROUND_FLOOR_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
        		amount = amount.add(getTotalPartitionsOnGroundFee(bpaPlan, ocPlan));
        	} else if (BpaConstants.BARSATI_FLOOR_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
        		amount = amount.add(getTotalBarsatiFloorFee(bpaPlan, ocPlan));
        	} else if (BpaConstants.STAIR_HEADWAY_HEIGHT_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
        		amount = amount.add(getTotalStairHeadwayFee(bpaPlan, ocPlan));
        	} else if (BpaConstants.WATER_TANK_LOCATION_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
        		amount = amount.add(getTotalWaterTankFee(bpaPlan, ocPlan));
        	}        	
        	
        	if (amount.compareTo(BigDecimal.ZERO) > 0) {
        		ocFee.getApplicationFee().addApplicationFeeDetail(buildApplicationFeeDetail(bpaFee, ocFee.getApplicationFee(), amount));
        	}
        }
    }
    
    public BigDecimal getTotalStairHeadwayFee(Plan bpaPlan, Plan ocPlan) {
    	return BigDecimal.valueOf(100);
    }
    
    public BigDecimal getTotalWaterTankFee(Plan bpaPlan, Plan ocPlan) {
    	return BigDecimal.valueOf(100);
    }
    
    public BigDecimal getTotalAdditionalHeightFee(Plan bpaPlan, Plan ocPlan) {
    	return BigDecimal.valueOf(100);
    }
    
    public BigDecimal getTotalPartitionsOnGroundFee(Plan bpaPlan, Plan ocPlan) {
    	return BigDecimal.valueOf(100);
    }
    
    public BigDecimal getTotalBarsatiFloorFee(Plan bpaPlan, Plan ocPlan) {
    	return BigDecimal.valueOf(100);
    }
    
    public BigDecimal getTotalInternalChangesFee(Plan bpaPlan, Plan ocPlan) {
    	return BigDecimal.valueOf(100);
    }
    
    public BigDecimal getTotalExcessCoverageFee(Plan bpaPlan, Plan ocPlan) {
    	return BigDecimal.valueOf(100);
    }
    
    public BigDecimal getTotalGlazingVerandahFee(Plan bpaPlan, Plan ocPlan) {
    	return BigDecimal.valueOf(100);
    }
    
    public BigDecimal getTotalLabourCess(Plan bpaPlan, Plan ocPlan) {
    	return BigDecimal.valueOf(100);
    }
    
    public BigDecimal getTotalAdditionalFee(Plan bpaPlan, Plan ocPlan) {
    	
    	return BigDecimal.valueOf(100);
    }
    
    public BigDecimal getTotalRuleFiveFee(Plan bpaPlan, Plan ocPlan) {
    	return BigDecimal.valueOf(100);
    }
    
    
    private BigDecimal getTotalAdditionalAreaInSQFFloorWise(Floor floor) {
    	BigDecimal totalAreaInSQF=BigDecimal.ZERO;
    	
    	return totalAreaInSQF;
    }
    
    protected ApplicationFeeDetail buildApplicationFeeDetail(final BpaFeeMapping bpaFee, final ApplicationFee applicationFee,
            BigDecimal amount) {
        ApplicationFeeDetail feeDetail = new ApplicationFeeDetail();
        feeDetail.setAmount(amount.setScale(0, BigDecimal.ROUND_HALF_UP));
        feeDetail.setBpaFeeMapping(bpaFee);
        feeDetail.setApplicationFee(applicationFee);
        return feeDetail;
    }
}