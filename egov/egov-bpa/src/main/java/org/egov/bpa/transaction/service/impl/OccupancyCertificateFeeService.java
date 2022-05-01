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
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.egov.bpa.master.entity.BpaFeeMapping;
import org.egov.bpa.master.service.BpaFeeCommonService;
import org.egov.bpa.master.service.BpaFeeService;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.entity.oc.OccupancyFee;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
import org.egov.bpa.transaction.service.oc.OccupancyFeeService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Building;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.OCDataComparison;
import org.egov.common.entity.edcr.Occupancy;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.common.entity.edcr.TerraceUtility;
import org.egov.commons.service.OccupancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
public class OccupancyCertificateFeeService {
	// private static final String TOTAL_FLOOR_AREA = "totalFloorArea";
	// private static final String OTHERS = "Others";
	// private static final String RESIDENTIAL_DESC = "Residential";
	
	private static final BigDecimal ONE = BigDecimal.valueOf(1);
	private static final BigDecimal TEN = BigDecimal.valueOf(10);
	private static final BigDecimal TWENTY = BigDecimal.valueOf(20);
	private static final BigDecimal THIRTY = BigDecimal.valueOf(30);
	private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
	private static final BigDecimal FOUR_HUNDRED = BigDecimal.valueOf(400);
	private static final BigDecimal TEN_LAKH = BigDecimal.valueOf(1000000);
	private static final BigDecimal FIFTEEN_HUNDRED = BigDecimal.valueOf(1500);
	private static final BigDecimal TWENTY_FIVE_HUNDRED = BigDecimal.valueOf(2500);
	private static final BigDecimal THOUSAND = BigDecimal.valueOf(1000);
	private static final BigDecimal TWO_THOUSAND = BigDecimal.valueOf(2000);
	private static final BigDecimal FOUR_THOUSAND = BigDecimal.valueOf(4000);
	private static final BigDecimal TEN_THOUSAND = BigDecimal.valueOf(10000);
	private static final BigDecimal FIVE_THOUSAND = BigDecimal.valueOf(5000);
	private static final BigDecimal ONE_TWO_FIVE = BigDecimal.valueOf(1.25);
	private static final BigDecimal TWO_FIVE = BigDecimal.valueOf(2.5);
	private static final BigDecimal EIGHTEEN = BigDecimal.valueOf(18);
	private static final BigDecimal SQMT_SQFT_MULTIPLIER = BigDecimal.valueOf(10.764);
	private static final BigDecimal HALF_ACRE_IN_SQMT = BigDecimal.valueOf(2023.43);
	private static final BigDecimal SQINCH_SQFT_DIVIDER = new BigDecimal("144");
	private static final BigDecimal HALF_ACRE_FROM_SQFT = new BigDecimal("21780");
	private static final BigDecimal SEVEN_HUNDRED_FIFTY = BigDecimal.valueOf(750);
	private static final BigDecimal GST = BigDecimal.valueOf(0.18);


	private static final int FRONT_AND_REAR_COLOR_CODE = 37;
	private static final int SIDE_COLOR_CODE = 39;

	public static final int ROUND_UP_SCALE = 1;

	@Autowired
	protected BpaUtils bpaUtils;

	@Autowired
	protected OccupancyService occupancyService;
	
	@Autowired
	protected OccupancyCertificateService occupancyCertificateService;

	@Autowired
	protected OccupancyFeeService ocFeeService;

	@Autowired
	protected BpaFeeService bpaFeeService;

	@Autowired
	protected BpaFeeCommonService bpaFeeCommonService;

	@Autowired
	protected ApplicationBpaService applicationBpaService;

	public Map<String, String> calculateOCFees(OccupancyCertificate oc, OccupancyFee ocFee) {
		Map<String, String> feeDetails = new HashMap<String, String>();
		OccupancyTypeHelper mostRestrictiveFarHelper = new OccupancyTypeHelper();
		Plan bpaPlan = applicationBpaService.getPlanInfo(oc.getParent().geteDcrNumber());
		Plan ocPlan = applicationBpaService.getPlanInfo(oc.geteDcrNumber());
		if (null != ocPlan) {
			mostRestrictiveFarHelper = ocPlan.getVirtualBuilding() != null
					? ocPlan.getVirtualBuilding().getMostRestrictiveFarHelper()
					: null;
		}
		// Map<String, BigDecimal> ocProposedArea =
		// bpaUtils.getProposedBuildingAreasOfOC(oc.getBuildings());
		// Map<String, BigDecimal> totalProposedArea =
		// bpaUtils.getTotalProposedArea(oc.getParent().getBuildingDetail());
		// BigDecimal ocFloorArea = ocProposedArea.get(TOTAL_FLOOR_AREA) != null ?
		// ocProposedArea.get(TOTAL_FLOOR_AREA) : BigDecimal.ZERO;
		// BigDecimal proposedFloorArea = totalProposedArea.get(TOTAL_FLOOR_AREA) !=
		// null ? totalProposedArea.get(TOTAL_FLOOR_AREA) : BigDecimal.ZERO;
		// BigDecimal maxPermittedFloorArea =
		// proposedFloorArea.multiply(BigDecimal.valueOf(5)).divide(BigDecimal.valueOf(100)).add(proposedFloorArea);
		//
		// if (ocFloorArea.compareTo(proposedFloorArea) > 0 &&
		// ocFloorArea.compareTo(maxPermittedFloorArea) < 0) {
		// BigDecimal deviatedArea = ocFloorArea.subtract(proposedFloorArea);
		// calculateFeeByServiceType(oc, deviatedArea, ocFee);
		// }

		feeDetails = calculateFeeByServiceType(oc, bpaPlan, ocPlan, ocFee, mostRestrictiveFarHelper, feeDetails);
		return feeDetails;
	}

	public Map<String, String> calculateFeeByServiceType(OccupancyCertificate oc, Plan bpaPlan, Plan ocPlan,
			OccupancyFee ocFee, OccupancyTypeHelper mostRestrictiveFarHelper, Map<String, String> feeDetails) {
		boolean dpcCertificateflag = false;
		List<BpaFeeMapping> bpaFees = bpaFeeCommonService
				.getOCFeeForListOfServices(oc.getParent().getServiceType().getId());
		for (BpaFeeMapping bpaFee : bpaFees) {
			BigDecimal amount = BigDecimal.ZERO;
			if (BpaConstants.LABOURCESS.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
				amount = amount.add(getTotalLabourCess(bpaPlan, ocPlan, mostRestrictiveFarHelper));
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
			} else if (BpaConstants.PARTITIONS_ON_GROUND_FLOOR_FEE
					.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
				amount = amount.add(getTotalPartitionsOnGroundFee(bpaPlan, ocPlan));
			} else if (BpaConstants.BARSATI_FLOOR_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
				amount = amount.add(getTotalBarsatiFloorFee(bpaPlan, ocPlan));
			} else if (BpaConstants.STAIR_HEADWAY_HEIGHT_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
				amount = amount.add(getTotalStairHeadwayFee(bpaPlan, ocPlan));
			} else if (BpaConstants.WATER_TANK_LOCATION_FEE.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
				amount = amount.add(getTotalWaterTankFee(bpaPlan, ocPlan));
			} else if (BpaConstants.EXCESS_COVERAGE_BEYOND_ZONING_FEE
					.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
				amount = amount.add(getTotalCoverageBeyondZoningFee(bpaPlan, ocPlan));
			} else if (BpaConstants.MINOR_CHANGES_IN_DOORS_AND_WINDOWS_FEE
					.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
				amount = amount.add(getTotalMinorChangesInDoorsAndWindowsFee(bpaPlan, ocPlan));
			} else if (BpaConstants.LOFTS_FEE
					.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
				amount = amount.add(getTotalLoftsFee(bpaPlan, ocPlan));
			} else if (BpaConstants.NON_STANDARD_GATE_FEE
					.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
				amount = amount.add(getTotalNonStandardGateFee(bpaPlan, ocPlan));
			} else if (BpaConstants.NICHES_ON_THE_COMMON_WALL_FEE
					.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
				amount = amount.add(getTotalNichesOnTheCommonWallFee(bpaPlan, ocPlan));
			} else if (BpaConstants.DPC_CERTIFICATE_MISSING_FEE
					.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
				if(dpcCertificateflag) {
				amount = amount.add(getTotalDPCCertificateMissingFee(oc, ocPlan, mostRestrictiveFarHelper));
				}
			} else if (BpaConstants.FALSE_CEILING_FEE
					.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
				amount = amount.add(getTotalFalseCeilingFee(bpaPlan, ocPlan));
			} else if (BpaConstants.SECURITY_FEE
					.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
				if(ocPlan.getPlanInformation().getIsThisACaseOfOwnershipChange().equalsIgnoreCase(BpaConstants.YES)) {
					amount = amount.add(getTotalSecurityFee(ocPlan, mostRestrictiveFarHelper));
				}
			} else if (BpaConstants.TRANSFER_OF_BUILDING_PLAN_FEE
					.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getName())) {
				if(ocPlan.getPlanInformation().getIsThisACaseOfOwnershipChange().equalsIgnoreCase(BpaConstants.YES)) {
					amount = amount.add(getTotalTransferOfBuildingPlanFee(oc, mostRestrictiveFarHelper));
				}
			}

			if (amount.compareTo(BigDecimal.ZERO) > 0) {
				amount = amount.add(amount.multiply(GST).setScale(2, BigDecimal.ROUND_HALF_UP));
				ocFee.getApplicationFee()
						.addApplicationFeeDetail(buildApplicationFeeDetail(bpaFee, ocFee.getApplicationFee(), amount));
				feeDetails.put(bpaFee.getBpaFeeCommon().getName(), String.valueOf(amount));
			}
		}
		return feeDetails;
	}

	private BigDecimal getTotalTransferOfBuildingPlanFee(OccupancyCertificate oc, OccupancyTypeHelper mostRestrictiveFarHelper) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		if(BpaConstants.A_P.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())) {
			if(oc.getParent().getApplicationType().getName().equalsIgnoreCase(BpaConstants.APPLICATION_TYPE_LOWRISK)) {
				totalAmount = BigDecimal.valueOf(750);
			} else if(oc.getParent().getApplicationType().getName().equalsIgnoreCase(BpaConstants.APPLICATION_TYPE_HIGHRISK)) {
				totalAmount = BigDecimal.valueOf(1000);
			}
		}
		else {
			totalAmount = BigDecimal.valueOf(2000);
		}
		
		return totalAmount;
	}

	private BigDecimal getTotalSecurityFee(Plan ocPlan, OccupancyTypeHelper mostRestrictiveFarHelper) {
		boolean isFeeDynamic = false;
		BigDecimal multiplier = BigDecimal.ZERO;
		BigDecimal totalAmount = BigDecimal.ZERO;

		if (BpaConstants.A_P.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.F_SCO.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.G_GBAC.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.G_GBZP.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())) {
			isFeeDynamic = false;
			multiplier = TEN_THOUSAND;
		} else if (BpaConstants.F_B.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())) {
			isFeeDynamic = false;
			multiplier = FIVE_THOUSAND;
		} else {
			isFeeDynamic = true;
			multiplier = TEN_THOUSAND;
		}
		if (ocPlan.isRural()) {
			return BigDecimal.ZERO;
		}
		if (isFeeDynamic) {
			BigDecimal totalAreaOfPlot = ocPlan.getPlanInformation().getPlotArea();
			BigDecimal totalAreaInHalfAcre = BigDecimal.ZERO;
			if (ocPlan.getDrawingPreference().getInMeters()) {
				totalAreaInHalfAcre = totalAreaOfPlot.divide(HALF_ACRE_IN_SQMT, 2, BigDecimal.ROUND_HALF_UP);
			} else if (ocPlan.getDrawingPreference().getInFeets()) {

				totalAreaInHalfAcre = totalAreaOfPlot.divide(HALF_ACRE_FROM_SQFT,2,BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
			}

			int halfAcreCount = totalAreaInHalfAcre.intValue();
			halfAcreCount = (totalAreaInHalfAcre.compareTo(new BigDecimal(halfAcreCount)) >= 0) ? halfAcreCount + 1
					: halfAcreCount;
			totalAmount = totalAmount
					.add(multiplier.multiply(new BigDecimal(halfAcreCount)).setScale(2, BigDecimal.ROUND_HALF_UP));
		} else {
			totalAmount = multiplier;
		}
		//return totalAmount.divide(GST);
		return totalAmount;
	}

	private BigDecimal getTotalFalseCeilingFee(Plan bpaPlan, Plan ocPlan) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal multiplier = BigDecimal.valueOf(100);
		
		BigDecimal areaOfFalseCeiling = ocPlan.getPlanInformation().getAreaOfFalseCeiling();
		
		totalAmount = areaOfFalseCeiling.multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP);
		return totalAmount;
	}

	private BigDecimal getTotalDPCCertificateMissingFee(OccupancyCertificate oc, Plan ocPlan, OccupancyTypeHelper mostRestrictiveFarHelper) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		String plotType = ocPlan.getPlanInfoProperties().get(BpaConstants.PLOT_TYPE);
		
		if (BpaConstants.A_P.equals(mostRestrictiveFarHelper.getSubtype().getCode())) {
			if(BpaConstants.MARLA.equals(plotType)) {
				totalAmount = BigDecimal.valueOf(2500);
			}
			else if(oc.getParent().getApplicationType().getName().equalsIgnoreCase(BpaConstants.APPLICATION_TYPE_LOWRISK)
					|| oc.getParent().getApplicationType().getName().equalsIgnoreCase(BpaConstants.APPLICATION_TYPE_HIGHRISK)) {
				totalAmount = BigDecimal.valueOf(5000);
			}
		}
		else if (BpaConstants.F_B.equals(mostRestrictiveFarHelper.getSubtype().getCode())) {
			totalAmount = BigDecimal.valueOf(5000);
		}	
		else if (BpaConstants.P.equals(mostRestrictiveFarHelper.getType().getCode())) {
			if (BpaConstants.P_CNA.equals(mostRestrictiveFarHelper.getSubtype().getCode())
					|| BpaConstants.B_HEI.equals(mostRestrictiveFarHelper.getSubtype().getCode())) {
				totalAmount = BigDecimal.valueOf(7500);
			}
		}
		else if (BpaConstants.G.equals(mostRestrictiveFarHelper.getType().getCode())) {
			if (BpaConstants.F_SCO.equals(mostRestrictiveFarHelper.getSubtype().getCode())) {
				totalAmount = BigDecimal.valueOf(10000);
			}
		}
		else {
			totalAmount = BigDecimal.valueOf(10000);
		}
		
		return totalAmount;
	}

	private BigDecimal getTotalNichesOnTheCommonWallFee(Plan bpaPlan, Plan ocPlan) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal multiplier = BigDecimal.valueOf(500);
		
		BigDecimal numberOfNiches = ocPlan.getPlanInformation().getNumberOfNichesOnTheCommonWall();
		
		totalAmount = numberOfNiches.multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP);
		return totalAmount;
	}

	private BigDecimal getTotalNonStandardGateFee(Plan bpaPlan, Plan ocPlan) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal multiplier = BigDecimal.valueOf(500);
		
		BigDecimal numberOfGates = ocPlan.getPlanInformation().getNumberOfNonStandardGates();
		
		totalAmount = numberOfGates.multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP);
		return totalAmount;
	}

	private BigDecimal getTotalLoftsFee(Plan bpaPlan, Plan ocPlan) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal multiplier = BigDecimal.valueOf(500);
		
		BigDecimal numberOfLofts = ocPlan.getPlanInformation().getNumberOfLoftsConstructedBeyondPermit();
		
		totalAmount = numberOfLofts.multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP);
		return totalAmount;
	}

	private BigDecimal getTotalMinorChangesInDoorsAndWindowsFee(Plan bpaPlan, Plan ocPlan) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal multiplier = BigDecimal.valueOf(500);
		
		BigDecimal numberOfFloors = ocPlan.getPlanInformation().getNumberOfFloorsWithChangesInDoorsOrWindowsLocations();
		
		totalAmount = numberOfFloors.multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP);
		return totalAmount;
	}

	private BigDecimal getTotalCoverageBeyondZoningFee(Plan bpaPlan, Plan ocPlan) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal multiplier = BigDecimal.valueOf(800);
		BigDecimal deviation = ocPlan.getOcdataComparison().getOcdataComparison()
				.get(OCDataComparison.Excess_Coverage_Beyond_Zoning_6_INCH).getDeviation();

		if (deviation.compareTo(BigDecimal.valueOf(6)) > 0) {
			totalAmount = totalAmount.add(deviation.multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP));
		}

		return totalAmount;

	}

	public BigDecimal getTotalStairHeadwayFee(Plan bpaPlan, Plan ocPlan) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal multiplier = BigDecimal.valueOf(500);
		BigDecimal expectedMinWidth = meterToFoot(BigDecimal.valueOf(2.2));
		BigDecimal allowedMinWidth = BigDecimal.valueOf(6.9);
		int counter = 0;

		for (Block block : ocPlan.getBlocks()) {
			if (block.getBuilding() != null) {
				org.egov.common.entity.edcr.HeadRoom headRoom = block.getBuilding().getHeadRoom();
				if (headRoom != null) {
					List<BigDecimal> headRoomDimensions = headRoom.getHeadRoomDimensions();
					if (headRoomDimensions != null && headRoomDimensions.size() > 0) {
						for (BigDecimal headRoomDimension : headRoomDimensions) {
							if (headRoomDimension.compareTo(expectedMinWidth) < 0
									&& headRoomDimension.compareTo(allowedMinWidth) >= 0) {
								counter = (counter + 1);
							}
						}
					}
				}
			}
		}

		if (counter > 0) {
			totalAmount = BigDecimal.valueOf(counter).multiply(multiplier);
		}

		return totalAmount;
	}

	public BigDecimal getTotalWaterTankFee(Plan bpaPlan, Plan ocPlan) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal multiplier = BigDecimal.valueOf(500);
		BigDecimal expectedFrontAndRearDistance = new BigDecimal("4.0");// 3.048
		BigDecimal expectedSideDistance = new BigDecimal("4.0");
		int counterAAndB = 0;
		int counterA = 0;
		int counterB = 0;

		if (ocPlan.getBlocks() != null) {
			for (Block block : ocPlan.getBlocks()) {

				List<BigDecimal> frontAndRear = new ArrayList<BigDecimal>();
				List<BigDecimal> side = new ArrayList<BigDecimal>();

				for (TerraceUtility terraceUtility : block.getTerraceUtilities()) {
					if (terraceUtility.getColorCode() == FRONT_AND_REAR_COLOR_CODE) {
						frontAndRear.addAll(terraceUtility.getDistances());
					} else if (terraceUtility.getColorCode() == SIDE_COLOR_CODE) {
						side.addAll(terraceUtility.getDistances());
					}
				}

				for (BigDecimal frontAndRearNew : frontAndRear) {
					for (BigDecimal sideNew : side) {
						if (inchtoFeetArea(frontAndRearNew).compareTo(expectedFrontAndRearDistance) < 0
								&& inchtoFeetArea(sideNew).compareTo(expectedSideDistance) < 0) {
							counterAAndB = (counterAAndB + 1);
						}
					}
				}
				for (BigDecimal frontAndRearNew : frontAndRear) {
					if (inchtoFeetArea(frontAndRearNew).compareTo(expectedFrontAndRearDistance) < 0) {
						counterA = (counterA + 1);
					}
				}
				for (BigDecimal sideNew : side) {
					if (inchtoFeetArea(sideNew).compareTo(expectedSideDistance) < 0) {
						counterB = (counterB + 1);
					}
				}

			}

		}

		counterAAndB = (counterA + counterB - counterAAndB);
		if (counterAAndB > 0) {
			totalAmount = (BigDecimal.valueOf(counterAAndB).multiply(multiplier));
		}

		return totalAmount;

	}

	public BigDecimal getTotalAdditionalHeightFee(Plan bpaPlan, Plan ocPlan) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal multiplier = BigDecimal.valueOf(50);
		BigDecimal totalAdditionalHeightArea = ocPlan.getOcdataComparison().getOcdataComparison()
				.get(OCDataComparison.Additional_height_on_second_floor_of_SCFs_converted_into_SCOs).getDeviation();

		if (totalAdditionalHeightArea.compareTo(BigDecimal.ZERO) > 0) {
			totalAmount = totalAdditionalHeightArea.multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP);
		}

		return totalAmount;
	}

	public BigDecimal getTotalPartitionsOnGroundFee(Plan bpaPlan, Plan ocPlan) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal multiplier = BigDecimal.ZERO;
		BigDecimal oc = ocPlan.getOcdataComparison().getOcdataComparison()
				.get(OCDataComparison.Partitions_on_ground_floor_on_multi_bays_shops).getDeviation();

		if (ocPlan.getPlanInformation().getSectorNumber().equalsIgnoreCase("17")
				&& ocPlan.getPlanInformation().getSectorNumber().equalsIgnoreCase("34")) {
			multiplier = BigDecimal.valueOf(100);
			totalAmount = totalAmount.add(oc.multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP));
		} else {
			multiplier = BigDecimal.valueOf(80);
			totalAmount = totalAmount.add(oc.multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP));
		}

		return totalAmount;
	}

	public BigDecimal getTotalBarsatiFloorFee(Plan bpaPlan, Plan ocPlan) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal adder = BigDecimal.valueOf(500);
		BigDecimal deviation = ocPlan.getOcdataComparison().getOcdataComparison().get(OCDataComparison.Barsati_Floor)
				.getDeviation();

		if ((deviation.compareTo(BigDecimal.valueOf(6)) >= 0) && (deviation.compareTo(BigDecimal.valueOf(9)) <= 0)) {
			totalAmount = totalAmount.add(adder);
		}

		return totalAmount;
	}

	public BigDecimal getTotalInternalChangesFee(Plan bpaPlan, Plan ocPlan) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal multiplier = BigDecimal.valueOf(500);
		BigDecimal floorCount = BigDecimal.ZERO;
		for (Block block : ocPlan.getBlocks()) {
			for (Floor floor : block.getBuilding().getFloors()) {
				for (Occupancy occupancy : floor.getOccupancies()) {
					if (BpaConstants.OC_MIC.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
						floorCount = floorCount.add(ONE);
					}
					}
			}
		}
		totalAmount = multiplier.multiply(floorCount).setScale(2, BigDecimal.ROUND_HALF_UP);
		/*BigDecimal minorChangesDeviation = ocPlan.getOcdataComparison().getOcdataComparison()
				.get(OCDataComparison.Minor_Internal_Changes_During_Construction).getDeviation();

		if (minorChangesDeviation.compareTo(BigDecimal.ZERO) > 0) {
			totalAmount = minorChangesDeviation.multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP);
		}*/
		return totalAmount;
	}

	public BigDecimal getTotalExcessCoverageFee(Plan bpaPlan, Plan ocPlan) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal multiplier = BigDecimal.valueOf(100);
		BigDecimal additionalCoverageDeviation = ocPlan.getOcdataComparison().getOcdataComparison()
				.get(OCDataComparison.Additional_Coverage_In_RearCourtyard).getDeviation();

		if (additionalCoverageDeviation.compareTo(BigDecimal.valueOf(150)) < 0) {
			totalAmount = additionalCoverageDeviation.multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return totalAmount;
	}

	public BigDecimal getTotalGlazingVerandahFee(Plan bpaPlan, Plan ocPlan) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal Multiplier = BigDecimal.ZERO;
		BigDecimal deviation = BigDecimal.ZERO;

		OccupancyTypeHelper typeHelper = ocPlan.getVirtualBuilding().getMostRestrictiveFarHelper();

		if (BpaConstants.A_P.equals(typeHelper.getSubtype().getCode())) {
			Multiplier = BigDecimal.valueOf(500);
		}
		
		if (BpaConstants.A_P.equals(typeHelper.getSubtype().getCode()) && (ocPlan.getPlanInformation().getSectorNumber().equals("22A") || ocPlan.getPlanInformation().getSectorNumber().equals("22B") || ocPlan.getPlanInformation().getSectorNumber().equals("22C") || ocPlan.getPlanInformation().getSectorNumber().equals("22D") || ocPlan.getPlanInformation().getSectorNumber().equals("22"))) {
			Multiplier = BigDecimal.valueOf(200);
		}

		deviation = ocPlan.getOcdataComparison().getOcdataComparison().get(OCDataComparison.Glazing_Of_Verandah)
				.getDeviation();
		totalAmount = deviation.multiply(Multiplier).setScale(2, BigDecimal.ROUND_HALF_UP);
		return totalAmount;
	}

	private BigDecimal getTotalAmountForGlazingVerandahForSCO(Plan ocPlan) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal Multiplier = BigDecimal.valueOf(200);
		BigDecimal deviation = BigDecimal.ZERO;

		deviation = ocPlan.getOcdataComparison().getOcdataComparison().get(OCDataComparison.Glazing_Of_Verandah)
				.getDeviation();
		totalAmount = deviation.multiply(Multiplier).setScale(2, BigDecimal.ROUND_HALF_UP);
		return totalAmount;
	}

	public BigDecimal getTotalLabourCess(Plan bpaPlan, Plan ocPlan, OccupancyTypeHelper mostRestrictiveFarHelper) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal multiplier = BigDecimal.ZERO;
		BigDecimal estimatedAmount = BigDecimal.ZERO;
		BigDecimal totalCajjaAreaBpa = BigDecimal.ZERO;
		BigDecimal totalMumtyAreaBpa = BigDecimal.ZERO;
		BigDecimal totalCajjaAreaOc = BigDecimal.ZERO;
		BigDecimal totalMumtyAreaOc = BigDecimal.ZERO;
		BigDecimal totalCajjaAreaDeviation = BigDecimal.ZERO;
		BigDecimal totalMumtyAreaDeviation = BigDecimal.ZERO;
		if (BpaConstants.IT_MCL.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
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
		} else if (BpaConstants.F_B.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())) {
			multiplier = THOUSAND;
		} else if (BpaConstants.A_P.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.A_G.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.F_CFI.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())
				|| BpaConstants.F_SCO.equalsIgnoreCase(mostRestrictiveFarHelper.getSubtype().getCode())) {
			multiplier = FIFTEEN_HUNDRED;
		} else {
			multiplier = TWENTY_FIVE_HUNDRED;
		}

		if (multiplier.compareTo(BigDecimal.ZERO) > 0) {
			if (ocPlan.getDrawingPreference().getInFeets()) {
				estimatedAmount = estimatedAmount
						.add(ocPlan.getOcdataComparison().getOcdataComparison().get(OCDataComparison.Labour_Cess)
								.getDeviation().multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP));

			}

			if (ocPlan.getDrawingPreference().getInFeets()) {
				for (Block b : ocPlan.getBlocks()) {
					Building building = b.getBuilding();
					if (building != null) {
						for (Floor floor : building.getFloors()) {
							if (floor.getOverHangs() != null && !floor.getOverHangs().isEmpty()) {
								List<BigDecimal> areas = floor.getOverHangs().stream()
										.map(overhang -> overhang.getArea()).collect(Collectors.toList());
								totalCajjaAreaOc = totalCajjaAreaOc.add(areas.stream().filter(Objects::nonNull)
										.reduce(BigDecimal.ZERO, BigDecimal::add));

							}
						}
					}

					// Adding mumty area
					if (!CollectionUtils.isEmpty(b.getStairCoversArea())) {
						totalMumtyAreaOc = totalMumtyAreaOc
								.add(b.getStairCoversArea().stream().reduce(BigDecimal::add).get());
					}

				}
				if (bpaPlan.getDrawingPreference().getInFeets()) {
					for (Block b : bpaPlan.getBlocks()) {
						Building building = b.getBuilding();
						if (building != null) {
							for (Floor floor : building.getFloors()) {
								if (floor.getOverHangs() != null && !floor.getOverHangs().isEmpty()) {
									List<BigDecimal> areas = floor.getOverHangs().stream()
											.map(overhang -> overhang.getArea()).collect(Collectors.toList());
									totalCajjaAreaBpa = totalCajjaAreaBpa.add(areas.stream().filter(Objects::nonNull)
											.reduce(BigDecimal.ZERO, BigDecimal::add));
								}
							}
						}

						// Adding mumty area
						if (!CollectionUtils.isEmpty(b.getStairCoversArea())) {
							totalMumtyAreaBpa = totalMumtyAreaBpa
									.add(b.getStairCoversArea().stream().reduce(BigDecimal::add).get());
						}

					}

				}
			}
			totalCajjaAreaDeviation = (totalCajjaAreaOc.subtract(totalCajjaAreaBpa)).divide(SQINCH_SQFT_DIVIDER, 2,
					RoundingMode.HALF_UP);
			totalMumtyAreaDeviation = totalMumtyAreaOc.subtract(totalMumtyAreaBpa);
			estimatedAmount = estimatedAmount
					.add(totalCajjaAreaDeviation.divide(SQINCH_SQFT_DIVIDER, 2, RoundingMode.HALF_UP)
							.multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP));
			estimatedAmount = estimatedAmount
					.add(totalMumtyAreaDeviation.multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP));
			if (estimatedAmount.compareTo(BigDecimal.ZERO) > 0) {
				totalAmount = totalAmount.add((estimatedAmount.divide(HUNDRED)).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}
		return totalAmount;
	}

	public BigDecimal getTotalAdditionalFee(Plan bpaPlan, Plan ocPlan) {
		Boolean isAdditionalCovApplicable = true;
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal GF_MULTIPLIER = BigDecimal.ZERO;
		BigDecimal FF_MULTIPLIER = BigDecimal.ZERO;
		BigDecimal SF_MULTIPLIER = BigDecimal.ZERO;
		BigDecimal floor0Deviation = BigDecimal.ZERO;
		BigDecimal floor1Deviation = BigDecimal.ZERO;
		BigDecimal floor2Deviation = BigDecimal.ZERO;

//		if (plotSizeInSQF.compareTo(new BigDecimal("4500")) <= 0) {//for marla
//			GF_MULTIPLIER = new BigDecimal("100");
//			FF_MULTIPLIER = new BigDecimal("40");
//			SF_MULTIPLIER = new BigDecimal("40");
//		} else {//for other then marla
//			GF_MULTIPLIER = new BigDecimal("100");
//			FF_MULTIPLIER = new BigDecimal("100");
//			SF_MULTIPLIER = new BigDecimal("60");
//		}
		String plotType = ocPlan.getPlanInfoProperties().get(BpaConstants.PLOT_TYPE);
		if (BpaConstants.MARLA.equals(plotType)) {// for marla
			GF_MULTIPLIER = new BigDecimal("100");
			FF_MULTIPLIER = new BigDecimal("40");
			SF_MULTIPLIER = new BigDecimal("40");
		} else {// for other then marla
			GF_MULTIPLIER = new BigDecimal("100");
			FF_MULTIPLIER = new BigDecimal("100");
			SF_MULTIPLIER = new BigDecimal("60");
		}

		OccupancyTypeHelper typeHelper = ocPlan.getVirtualBuilding().getMostRestrictiveFarHelper();

		if (BpaConstants.F_SCO.equals(typeHelper.getSubtype().getCode())) {
			return getTotalAmountForAdditionalCoverageForSCO(ocPlan, isAdditionalCovApplicable);
		}

		if (isAdditionalCovApplicable) {
			for (Block block : ocPlan.getBlocks()) {
				for (Floor floor : block.getBuilding().getFloors()) {
					for (Occupancy occupancy : floor.getOccupancies()) {
						if (BpaConstants.A_AF.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
							if (floor.getNumber() == 0) {
								floor0Deviation = inchtoFeetArea(occupancy.getBuiltUpArea());
							} else if (floor.getNumber() == 1) {
								floor1Deviation = inchtoFeetArea(occupancy.getBuiltUpArea());
							} else if (floor.getNumber() == 2) {
								floor2Deviation = inchtoFeetArea(occupancy.getBuiltUpArea());
							}
						}

					}
				}
			}
			for (Block block : bpaPlan.getBlocks()) {
				for (Floor floor : block.getBuilding().getFloors()) {
					for (Occupancy occupancy : floor.getOccupancies()) {
						if (BpaConstants.A_AF.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
							if (floor.getNumber() == 0) {
								floor0Deviation = floor0Deviation.subtract(inchtoFeetArea(occupancy.getBuiltUpArea()));
							} else if (floor.getNumber() == 1) {
								floor1Deviation = floor1Deviation.subtract(inchtoFeetArea(occupancy.getBuiltUpArea()));
							} else if (floor.getNumber() == 2) {
								floor2Deviation = floor2Deviation.subtract(inchtoFeetArea(occupancy.getBuiltUpArea()));
							}
						}
					}
				}
			}
			totalAmount = totalAmount.add(floor0Deviation.multiply(GF_MULTIPLIER));
			totalAmount = totalAmount.add(floor1Deviation.multiply(FF_MULTIPLIER));
			totalAmount = totalAmount.add(floor2Deviation.multiply(SF_MULTIPLIER));
		}
		return totalAmount;
	}

	public BigDecimal getTotalAmountForAdditionalCoverageForSCO(Plan ocPlan, Boolean isAdditionalCovApplicable) {

		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal buildupAreaDeviation = ocPlan.getOcdataComparison().getOcdataComparison()
				.get(OCDataComparison.Additional_fee_Residential_plotted).getDeviation();
		Double newFar = ocPlan.getFarDetails().getProvidedFar();
		BigDecimal plotArea = ocPlan.getPlot().getArea();
		BigDecimal totalAdditinalArea = BigDecimal.ZERO;
		BigDecimal presentCollectorRate = ocPlan.getPlanInformation().getPresentCollectorRate();
		if (isAdditionalCovApplicable) {
			for (Block block : ocPlan.getBlocks()) {
				for (Floor floor : block.getBuilding().getFloors()) {
					for (Occupancy occupancy : floor.getOccupancies()) {
						if (BpaConstants.A_AF.equals(occupancy.getTypeHelper().getSubtype().getCode())) {

							if (ocPlan.getDrawingPreference().getInFeets()) {
								totalAdditinalArea = totalAdditinalArea
										.add(buildupAreaDeviation.divide(SQINCH_SQFT_DIVIDER, 2, RoundingMode.HALF_UP));
							} else {
								totalAdditinalArea = totalAdditinalArea.add(buildupAreaDeviation);
							}
						}
					}
				}
			}
		}
		double additonalAreaFar = 0.0;
		try {
			additonalAreaFar = totalAdditinalArea.divide(plotArea, 3, BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e) {

		}
		double oldFar = newFar - additonalAreaFar;

		totalAmount = presentCollectorRate.multiply(new BigDecimal("0.35")).multiply(totalAdditinalArea);
		if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
			totalAmount = totalAmount.divide(new BigDecimal(oldFar + ""), BigDecimal.ROUND_HALF_UP);
			totalAmount = totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		}

		return totalAmount;
	}

	public BigDecimal getTotalRuleFiveFee(Plan bpaPlan, Plan ocPlan) {

		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal totalArea = BigDecimal.ZERO;
		BigDecimal multiplier = BigDecimal.ZERO;
		if (BpaConstants.NEW_CONSTRUCTION.equals(ocPlan.getServiceType())
				|| BpaConstants.RECONSTRUCTION.equals(ocPlan.getServiceType()))
			multiplier = new BigDecimal("5");
		else if (BpaConstants.ALTERATION.equals(ocPlan.getServiceType())
				|| BpaConstants.ADDITION_OR_EXTENSION.equals(ocPlan.getServiceType()))
			multiplier = new BigDecimal("5");

//		if (isWorkStarted) {
//			totalArea = getTotalAreaForRule5(plan);
//		}

		OccupancyTypeHelper typeHelper = ocPlan.getVirtualBuilding().getMostRestrictiveFarHelper();
		if (BpaConstants.F.equals(typeHelper.getType().getCode())) {
			if (BpaConstants.NEW_CONSTRUCTION.equals(ocPlan.getServiceType()))
				multiplier = new BigDecimal("30");
			else if (BpaConstants.ALTERATION.equals(ocPlan.getServiceType())
					|| BpaConstants.ADDITION_OR_EXTENSION.equals(ocPlan.getServiceType())
					|| BpaConstants.RECONSTRUCTION.equals(ocPlan.getServiceType()))
				multiplier = new BigDecimal("5");
		}

		totalArea = ocPlan.getOcdataComparison().getOcdataComparison().get(OCDataComparison.RULE5).getDeviation();

		if (totalArea.compareTo(BigDecimal.ZERO) > 0 && multiplier.compareTo(BigDecimal.ZERO) > 0) {
			totalAmount = totalAmount.add(totalArea.multiply(multiplier).setScale(2, BigDecimal.ROUND_HALF_UP));
		}

		return totalAmount;
	}

	public static BigDecimal inchtoFeetArea(BigDecimal value) {
		try {
			if (value != null && value.longValue() != 0)
				value = value.divide(new BigDecimal(144), MathContext.DECIMAL32);
			value = value.setScale(ROUND_UP_SCALE, BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static BigDecimal inchtoFeet(BigDecimal value) {
		try {
			if (value != null && value.longValue() != 0)
				value = value.divide(new BigDecimal(12), MathContext.DECIMAL32);
			value = value.setScale(ROUND_UP_SCALE, BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static BigDecimal meterToFoot(BigDecimal value) {
		BigDecimal valueInFoot = value.multiply(new BigDecimal("3.281"));
		valueInFoot = valueInFoot.setScale(ROUND_UP_SCALE, BigDecimal.ROUND_HALF_UP);
		return valueInFoot;
	}

	protected ApplicationFeeDetail buildApplicationFeeDetail(final BpaFeeMapping bpaFee,
			final ApplicationFee applicationFee, BigDecimal amount) {
		ApplicationFeeDetail feeDetail = new ApplicationFeeDetail();
		feeDetail.setAmount(amount.setScale(0, BigDecimal.ROUND_HALF_UP));
		feeDetail.setBpaFeeMapping(bpaFee);
		feeDetail.setApplicationFee(applicationFee);
		return feeDetail;
	}
}