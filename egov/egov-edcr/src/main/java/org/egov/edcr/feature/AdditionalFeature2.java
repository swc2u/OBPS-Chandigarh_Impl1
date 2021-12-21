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

//debjit
package org.egov.edcr.feature;

import static org.egov.edcr.utility.DcrConstants.DECIMALDIGITS_MEASUREMENTS;
import static org.egov.edcr.utility.DcrConstants.ROUNDMODE_MEASUREMENTS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.Occupancy;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.service.cdg.CDGAConstant;
import org.egov.edcr.service.cdg.CDGAdditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdditionalFeature2 extends FeatureProcess {
	private static final Logger LOG = Logger.getLogger(AdditionalFeature2.class);

	private static final String RULE_38 = "38";
	private static final String RULE_39 = "39";
	private static final String RULE_41_I_A = "41-i-a";
	private static final String RULE_41_I_B = "41-i-b";
	private static final String RULE_47 = "47";
	private static final String RULE_50 = "50";
	private static final String RULE_56 = "56";
	private static final BigDecimal TWO = BigDecimal.valueOf(2);
	private static final BigDecimal ONE_POINTFIVE = BigDecimal.valueOf(1.5);
	private static final BigDecimal THREE = BigDecimal.valueOf(3);
	private static final BigDecimal FOUR = BigDecimal.valueOf(4);
	private static final BigDecimal SIX = BigDecimal.valueOf(6);
	private static final BigDecimal SEVEN = BigDecimal.valueOf(7);
	private static final BigDecimal TEN = BigDecimal.valueOf(10);
	private static final BigDecimal TWELVE = BigDecimal.valueOf(12);
	private static final BigDecimal NINETEEN = BigDecimal.valueOf(19);
	private static final BigDecimal FIFTY = BigDecimal.valueOf(50);
	private static final BigDecimal FIFTEEN = BigDecimal.valueOf(15);

	private static final BigDecimal ROAD_WIDTH_TWO_POINTFOUR = BigDecimal.valueOf(2.4);
	private static final BigDecimal ROAD_WIDTH_TWO_POINTFOURFOUR = BigDecimal.valueOf(2.44);
	private static final BigDecimal ROAD_WIDTH_THREE_POINTSIX = BigDecimal.valueOf(3.6);
	private static final BigDecimal ROAD_WIDTH_FOUR_POINTEIGHT = BigDecimal.valueOf(4.8);
	private static final BigDecimal ROAD_WIDTH_SIX_POINTONE = BigDecimal.valueOf(6.1);
	private static final BigDecimal ROAD_WIDTH_NINE_POINTONE = BigDecimal.valueOf(9.1);
	private static final BigDecimal ROAD_WIDTH_TWELVE_POINTTWO = BigDecimal.valueOf(12.2);

	private static final int PLOTAREA_100 = 100;
	private static final int PLOTAREA_300 = 300;
	private static final int PLOTAREA_500 = 500;
	private static final int PLOTAREA_1000 = 1000;
	private static final int PLOTAREA_3000 = 3000;
	/*
	 * private static final BigDecimal ROAD_WIDTH_EIGHTEEN_POINTTHREE =
	 * BigDecimal.valueOf(18.3); private static final BigDecimal
	 * ROAD_WIDTH_TWENTYFOUR_POINTFOUR = BigDecimal.valueOf(24.4); private static
	 * final BigDecimal ROAD_WIDTH_TWENTYSEVEN_POINTFOUR = BigDecimal.valueOf(27.4);
	 * private static final BigDecimal ROAD_WIDTH_THIRTY_POINTFIVE =
	 * BigDecimal.valueOf(30.5);
	 */

	public static final String OLD = "OLD";
	public static final String NEW = "NEW";
	public static final String OLD_AREA_ERROR = "road width old area";
	public static final String NEW_AREA_ERROR = "road width new area";
	public static final String OLD_AREA_ERROR_MSG = "No construction shall be permitted if the road width is less than 2.4m for old area.";
	public static final String NEW_AREA_ERROR_MSG = "No construction shall be permitted if the road width is less than 6.1m for new area.";
	public static final String NO_OF_FLOORS = "Maximum number of floors allowed";
	public static final String HEIGHT_BUILDING = "Maximum height of building allowed";
	public static final String MIN_PLINTH_HEIGHT = " >= 0.45";
	public static final String MIN_PLINTH_HEIGHT_MARALA = " >= 0.3";
	public static final String MIN_PLINTH_HEIGHT_ALL_AREA = " >= 0.3 and <= 1.2 ";
	public static final String MIN_PLINTH_HEIGHT_DESC = "Minimum plinth height";
	public static final String MAX_BSMNT_CELLAR = "Number of basement/cellar allowed";
	public static final String MIN_INT_COURT_YARD = "0.15";
	public static final String MIN_INT_COURT_YARD_DESC = "Minimum interior courtyard";
	public static final String BARRIER_FREE_ACCESS_FOR_PHYSICALLY_CHALLENGED_PEOPLE_DESC = "Barrier free access for physically challenged people";
	public static final String GREEN_BUILDINGS_AND_SUSTAINABILITY_PROVISIONS_ERROR_CODE = "Green buildings and sustainability provisions";
	public static final String GREEN_BUILDINGS_AND_SUSTAINABILITY_PROVISIONS_ERROR_MSG = "Green buildings and sustainability provision should be YES";
	public static final String GREEN_BUILDINGS_AND_SUSTAINABILITY = "Green buildings and sustainability provisions";
	public static final String FIRE_PROTECTION_AND_FIRE_SAFETY_REQUIREMENTS_DESC = "Fire Protection And Fire Safety Requirements";

	private static final String STILT_PARKING_DESCRIPTION = "Stilt parking %s for block %s";
	private static final String STILT_PARKING_REQUIRED_DESCRIPTION = "Stilt parking should be in ground floor";
	private static final String STILT_PARKING_PROVIDED_DESCRIPTION = "Stilt parking is in block %s and floor %s";

	private static final String optional = "Optional";
	@Autowired
	private CDGAdditionalService cDGAdditionalService;

	@Override
	public Plan validate(Plan pl) {
		return pl;
	}

	@Override
	public Plan process(Plan pl) {

		if (!pl.isRural()) {
			validateAncillaryFacilities(pl);
			validateCommunityFacilitiesInGroupHousingBuilding(pl);
			validateGalleryFloor(pl);
			validateGateAndCheckpost(pl);
			validateResidentialUse(pl);
			addAddtionalFee(pl);
			addRule5(pl);
		}

		return pl;
	}

	private void addRule5(Plan plan) {
		OccupancyTypeHelper mostRestrictiveOccupancyType = plan.getVirtualBuilding() != null
				? plan.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;
		ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.setKey("Common_Rule5 Fee");
		scrutinyDetail.addColumnHeading(1, BLOCK);
		scrutinyDetail.addColumnHeading(2, FLOOR);
		scrutinyDetail.addColumnHeading(3, PROVIDED);
		scrutinyDetail.addColumnHeading(4, STATUS);
		String applicableStr=plan.getPlanInfoProperties().get(DxfFileConstants.RULE_5_APPLICABLE);
		boolean isApplicable=applicableStr!=null && DxfFileConstants.YES.equals(applicableStr);
		boolean isParsent = false;
		for (Block block : plan.getBlocks()) {
			for (Floor floor : block.getBuilding().getFloors()) {
				for (Occupancy occupancy : floor.getOccupancies()) {
					if (DxfFileConstants.A_R5.equals(occupancy.getTypeHelper().getSubtype().getCode())) {

						isParsent = true;
						Map<String, String> details = new HashMap<>();
						details.put(BLOCK, "block-" + block.getNumber());
						details.put(FLOOR, "floor-" + floor.getNumber());
						details.put(PROVIDED, CDGAdditionalService.viewArea(plan,
								CDGAdditionalService.inchtoFeetArea(occupancy.getBuiltUpArea())));
						details.put(STATUS, Result.Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
					}

				}
			}

		}
		if(isParsent)
			plan.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
		else if(isApplicable) {
			plan.addError("RULE5", " Rule 5 polygon is mandatory");
		}
	}

	private void addAddtionalFee(Plan plan) {
		OccupancyTypeHelper mostRestrictiveOccupancyType = plan.getVirtualBuilding() != null
				? plan.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;
		ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.setKey("Common_Addtional Fee");
		scrutinyDetail.addColumnHeading(1, BLOCK);
		scrutinyDetail.addColumnHeading(2, FLOOR);
		scrutinyDetail.addColumnHeading(3, PROVIDED);
		scrutinyDetail.addColumnHeading(4, STATUS);
		boolean isParsent = false;
		for (Block block : plan.getBlocks()) {
			for (Floor floor : block.getBuilding().getFloors()) {
				for (Occupancy occupancy : floor.getOccupancies()) {
					if (DxfFileConstants.A_AF.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
//						if (floor.getNumber() >= 0) {
//							isParsent = true;
//							Map<String, String> details = new HashMap<>();
//							details.put(BLOCK, "block-" + block.getNumber());
//							details.put(FLOOR, "floor-" + floor.getNumber());
//							details.put(PROVIDED, CDGAdditionalService.viewArea(plan,
//									CDGAdditionalService.inchtoFeetArea(occupancy.getBuiltUpArea())));
//							details.put(STATUS, Result.Accepted.getResultVal());
//							scrutinyDetail.getDetail().add(details);
//						} else {
//							plan.addError("Addtional Fee", "Addtional Fee layer is not allowed in block "
//									+ block.getNumber() + " floor " + floor.getNumber());
//						}
							isParsent = true;
							Map<String, String> details = new HashMap<>();
							details.put(BLOCK, "block-" + block.getNumber());
							details.put(FLOOR, "floor-" + floor.getNumber());
							details.put(PROVIDED, CDGAdditionalService.viewArea(plan,
									CDGAdditionalService.inchtoFeetArea(occupancy.getBuiltUpArea())));
							details.put(STATUS, Result.Accepted.getResultVal());
							scrutinyDetail.getDetail().add(details);
						
					}
				}

			}
		}
//		if (isParsent && DxfFileConstants.A_P.equals(mostRestrictiveOccupancyType.getSubtype().getCode()))
//			plan.setIsAdditionalFeeApplicable(true);// is required at time of fee calculation
//		else if (isParsent)
//			plan.addError("Addtional Fee", "Addtional fee layer is not allowed.");
		plan.setIsAdditionalFeeApplicable(true);// is required at time of fee calculation
		plan.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

	}

	private void validateGateAndCheckpost(Plan pl) {
		OccupancyTypeHelper mostRestrictiveOccupancyType = pl.getVirtualBuilding() != null
				? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;
		List<Occupancy> inCheckPosts = new ArrayList<Occupancy>();
		List<Occupancy> outCheckPosts = new ArrayList<Occupancy>();
		if (mostRestrictiveOccupancyType != null && mostRestrictiveOccupancyType.getType() != null
				&& mostRestrictiveOccupancyType.getSubtype() != null
				&& mostRestrictiveOccupancyType.getSubtype().getCode() != null
				&& !isCheckPostNotApplicable(mostRestrictiveOccupancyType)) {

			for (Block block : pl.getBlocks()) {
				for (Floor floor : block.getBuilding().getFloors()) {
					if (floor.getNumber() == 0) {
						for (Occupancy occupancy : floor.getOccupancies()) {
							if (DxfFileConstants.A_ICP.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
								inCheckPosts.add(occupancy);
							}
							if (DxfFileConstants.A_OCP.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
								outCheckPosts.add(occupancy);
							}
						}
					} else {
						for (Occupancy occupancy : floor.getOccupancies()) {
							if (DxfFileConstants.A_ICP.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
								pl.addError("A_ICP " + block.getNumber() + floor.getNumber(),
										"In checkpost not allowed in block " + block.getNumber() + " floor "
												+ floor.getNumber());
							}
							if (DxfFileConstants.A_OCP.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
								pl.addError("A_OCP " + block.getNumber() + floor.getNumber(),
										"out checkpost not allowed in block " + block.getNumber() + " floor "
												+ floor.getNumber());
							}
						}
					}
				}
			}

//			for (Occupancy occupancy : pl.getOccupancies()) {
//				if (occupancy.getTypeHelper() != null & occupancy.getTypeHelper().getSubtype() != null
//						&& occupancy.getTypeHelper().getSubtype().getCode() != null) {
//					if (DxfFileConstants.A_CP.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
//						checkPosts.add(occupancy);
//					}
//				}
//			}
		}

		ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.setKey("Common_CheckPost");
		scrutinyDetail.addColumnHeading(1, RULE_NO);
		scrutinyDetail.addColumnHeading(2, DESCRIPTION);
		scrutinyDetail.addColumnHeading(3, REQUIRED);
		scrutinyDetail.addColumnHeading(4, PROVIDED);
		scrutinyDetail.addColumnHeading(5, STATUS);

		if (!inCheckPosts.isEmpty()) {
			Map<String, String> details = new HashMap<>();
//			if (checkPosts.size() < 2) {
//				pl.addError("CheckPost", "Minimum 2 checkPost is required. but provided: " + checkPosts.size());
//			}
			int i = 1;
			for (Occupancy occupancy : inCheckPosts) {
				BigDecimal requiredCheckpostArea = new BigDecimal("14");
				BigDecimal providedCheckpostArea = occupancy.getBuiltUpArea();

				if (pl.getDrawingPreference().getInFeets()) {
					requiredCheckpostArea = CDGAdditionalService.meterToFoot(requiredCheckpostArea);
					providedCheckpostArea = CDGAdditionalService.inchToFeet(providedCheckpostArea);
				}

				details.put(RULE_NO,
						CDGAdditionalService.getByLaws(mostRestrictiveOccupancyType, CDGAConstant.CHECKPOST));
				details.put(DESCRIPTION, "In checkpost " + i);
				details.put(REQUIRED, "<= " + CDGAdditionalService.viewArea(pl, requiredCheckpostArea));
				details.put(PROVIDED, CDGAdditionalService.viewArea(pl, providedCheckpostArea));

				if (providedCheckpostArea.compareTo(requiredCheckpostArea) <= 0) {
					details.put(STATUS, Result.Accepted.getResultVal());
				} else {
					details.put(STATUS, Result.Not_Accepted.getResultVal());
				}
				i++;
			}
			scrutinyDetail.getDetail().add(details);
		}

		if (!outCheckPosts.isEmpty()) {
			Map<String, String> details = new HashMap<>();
//			if (checkPosts.size() < 2) {
//				pl.addError("CheckPost", "Minimum 2 checkPost is required. but provided: " + checkPosts.size());
//			}
			int i = 1;
			for (Occupancy occupancy : outCheckPosts) {
				BigDecimal requiredCheckpostArea = new BigDecimal("14");
				BigDecimal providedCheckpostArea = occupancy.getBuiltUpArea();

				if (pl.getDrawingPreference().getInFeets()) {
					requiredCheckpostArea = CDGAdditionalService.meterToFoot(requiredCheckpostArea);
					providedCheckpostArea = CDGAdditionalService.inchToFeet(providedCheckpostArea);
				}

				details.put(RULE_NO,
						CDGAdditionalService.getByLaws(mostRestrictiveOccupancyType, CDGAConstant.CHECKPOST));
				details.put(DESCRIPTION, "Out checkpost " + i);
				details.put(REQUIRED, "<= " + CDGAdditionalService.viewArea(pl, requiredCheckpostArea));
				details.put(PROVIDED, CDGAdditionalService.viewArea(pl, providedCheckpostArea));

				if (providedCheckpostArea.compareTo(requiredCheckpostArea) <= 0) {
					details.put(STATUS, Result.Accepted.getResultVal());
				} else {
					details.put(STATUS, Result.Not_Accepted.getResultVal());
				}
				i++;
			}
			scrutinyDetail.getDetail().add(details);
		}

		pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
	}

	private boolean isCheckPostNotApplicable(OccupancyTypeHelper occupancyTypeHelper) {
		boolean flage = false;

		if (DxfFileConstants.A_P.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.A_G.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_SCO.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_B.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_TS.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_TCIM.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_PP.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_CD.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.G_GBAC.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.R1.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.ITH_R.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.ITH_GH.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.T1.equals(occupancyTypeHelper.getSubtype().getCode())) {
			flage = true;

		}
		return flage;
	}

	private void validateAncillaryFacilities(Plan pl) {

		ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.setKey("Common_Ancillary Facilities");
		scrutinyDetail.addColumnHeading(1, RULE_NO);
		scrutinyDetail.addColumnHeading(2, DESCRIPTION);
		scrutinyDetail.addColumnHeading(3, REQUIRED);
		scrutinyDetail.addColumnHeading(4, PROVIDED);
		scrutinyDetail.addColumnHeading(5, STATUS);
		Map<String, String> details = new HashMap<>();

		OccupancyTypeHelper mostRestrictiveOccupancyType = pl.getVirtualBuilding() != null
				? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;
		BigDecimal floorTotalFloorArea = BigDecimal.ZERO;
		BigDecimal ancillaryFacilitiesFar = BigDecimal.ZERO;
		boolean isProvided = false;
		if (mostRestrictiveOccupancyType == null || mostRestrictiveOccupancyType.getSubtype() == null)
			return;

		if (DxfFileConstants.IT.equals(mostRestrictiveOccupancyType.getType().getCode())) {

			for (Block block : pl.getBlocks()) {
				for (Floor floor : block.getBuilding().getFloors()) {
					for (Occupancy occupancy : floor.getOccupancies()) {
						if (DxfFileConstants.IT_AF.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
							floorTotalFloorArea = floorTotalFloorArea.add(occupancy.getBuiltUpArea());
							isProvided = true;
						}
					}
				}
			}

//			for (Occupancy occupancy : pl.getOccupancies()) {
//				if (DxfFileConstants.IT_AF.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
//					floorTotalFloorArea.add(occupancy.getBuiltUpArea());
//					isProvided = true;
//				}
//			}

			if (!isProvided || floorTotalFloorArea.doubleValue() <= 0) {
				pl.addError("AncillaryFacilities", "AncillaryFacilities is not defined");
				return;
			}

			BigDecimal plotArea = pl.getPlot() != null ? pl.getPlot().getArea() : BigDecimal.ZERO;
			;
			if (plotArea.doubleValue() > 0)
				ancillaryFacilitiesFar = calulateFar(floorTotalFloorArea, pl.getPlot().getArea(),
						pl.getDrawingPreference().getInFeets());

			double expectedFar = pl.getFarDetails().getPermissableFar() - pl.getFarDetails().getProvidedFar();

			if ((pl.getFarDetails().getPermissableFar() * 0.2) > expectedFar) {
				expectedFar = pl.getFarDetails().getPermissableFar() * 0.2;
			}

			// details.put(RULE_NO,
			// CDGAdditionalService.getByLaws(mostRestrictiveOccupancyType,
			// CDGAConstant.CHECKPOST));
			details.put(DESCRIPTION, "Ancillary Facilities ");
			details.put(REQUIRED, "Upto 20% within permissible FAR (" + expectedFar + ")");
			details.put(PROVIDED, CDGAdditionalService.roundBigDecimal(ancillaryFacilitiesFar) + "");

			if (ancillaryFacilitiesFar.compareTo(new BigDecimal(expectedFar)) <= 0) {
				details.put(STATUS, Result.Accepted.getResultVal());
			} else {
				details.put(STATUS, Result.Not_Accepted.getResultVal());
			}

			scrutinyDetail.getDetail().add(details);
			pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

		}
	}

	private BigDecimal calulateFar(BigDecimal floorArea, BigDecimal plotArea, boolean isInFeet) {
		BigDecimal far = BigDecimal.ZERO;
		if (isInFeet)
			far = CDGAdditionalService.inchtoFeetArea(floorArea).divide(plotArea, DECIMALDIGITS_MEASUREMENTS,
					ROUNDMODE_MEASUREMENTS);
		else
			far = floorArea.divide(plotArea, DECIMALDIGITS_MEASUREMENTS, ROUNDMODE_MEASUREMENTS);
		return far;
	}

	private void validateCommunityFacilitiesInGroupHousingBuilding(Plan pl) {// color code-> 120

		OccupancyTypeHelper mostRestrictiveOccupancyType = pl.getVirtualBuilding() != null
				? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;

		if (!DxfFileConstants.A_G.equals(mostRestrictiveOccupancyType.getSubtype().getCode())) {
			return;
		}
		scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.addColumnHeading(1, RULE_NO);
		scrutinyDetail.addColumnHeading(2, DESCRIPTION);
		scrutinyDetail.addColumnHeading(3, REQUIRED);
		scrutinyDetail.addColumnHeading(4, PROVIDED);
		scrutinyDetail.addColumnHeading(5, STATUS);
		scrutinyDetail.setKey("Common_Community Facilities");

		List<Occupancy> communityFacilities = new ArrayList<Occupancy>();
		BigDecimal totalArea = BigDecimal.ZERO;

		boolean isProvided = false;
		for (Block block : pl.getBlocks()) {
			for (Floor floor : block.getBuilding().getFloors()) {
				for (Occupancy occupancy : floor.getOccupancies()) {
					if (DxfFileConstants.A_CC.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
						communityFacilities.add(occupancy);
						totalArea = totalArea.add(occupancy.getBuiltUpArea());
						isProvided = true;
					}
				}
			}
		}

		if (!isProvided) {
			pl.addError("Community Facilities", "Community Facilities is not defined");
			return;
		}

		String sector = pl.getPlanInfoProperties().get(DxfFileConstants.SECTOR_NUMBER);

		if (!("49C".equalsIgnoreCase(sector) || "49D".equalsIgnoreCase(sector))) {
			pl.addError("Community Facilities error",
					"Community Facilities zoning plan is not available in system for " + sector);
			return;
		}

		// 557.4sqm -> 6000sqf
		BigDecimal expectedArea = new BigDecimal("557.4182");
		if (pl.getDrawingPreference().getInFeets()) {
			expectedArea = CDGAdditionalService.meterToFootArea(expectedArea);
			totalArea = CDGAdditionalService.inchtoFeetArea(totalArea);
		}

		if ((pl.getPlot().getArea().multiply(new BigDecimal("0.025"))).compareTo(expectedArea) < 0)
			expectedArea = pl.getPlot().getArea().multiply(new BigDecimal("0.025"));

		boolean isValid = false;
		if (totalArea.compareTo(expectedArea) <= 0)
			isValid = true;

		if (isValid)
			setReportOutputDetailsWithoutFloor(pl,
					CDGAdditionalService.getByLaws(mostRestrictiveOccupancyType,
							CDGAConstant.COMMERCAIL_OR_COMMUNLTY_FACLTITIES),
					"Community Facilities ", CDGAdditionalService.viewArea(pl, expectedArea),
					CDGAdditionalService.viewArea(pl, totalArea), Result.Accepted.getResultVal());
		else
			setReportOutputDetailsWithoutFloor(pl,
					CDGAdditionalService.getByLaws(mostRestrictiveOccupancyType,
							CDGAConstant.COMMERCAIL_OR_COMMUNLTY_FACLTITIES),
					"Community Facilities ", CDGAdditionalService.viewArea(pl, expectedArea),
					CDGAdditionalService.viewArea(pl, totalArea), Result.Not_Accepted.getResultVal());

	}

	private void validateGalleryFloor(Plan pl) {
		OccupancyTypeHelper mostRestrictiveOccupancyType = pl.getVirtualBuilding() != null
				? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;

		for (Block block : pl.getBlocks()) {
			scrutinyDetail = new ScrutinyDetail();
			scrutinyDetail.addColumnHeading(1, RULE_NO);
			scrutinyDetail.addColumnHeading(2, DESCRIPTION);
			scrutinyDetail.addColumnHeading(3, REQUIRED);
			scrutinyDetail.addColumnHeading(4, PROVIDED);
			scrutinyDetail.addColumnHeading(5, STATUS);
			scrutinyDetail.setKey("Block_" + block.getNumber() + "_" + "Gallery Floor");

			if (block.getBuilding() != null && !block.getBuilding().getFloors().isEmpty()) {
				for (Floor floor : block.getBuilding().getFloors()) {
					for (Occupancy occupancy : floor.getOccupancies()) {
						if (DxfFileConstants.A_GF.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
							BigDecimal providedHeight = BigDecimal.ZERO;
							try {
								providedHeight = floor.getFloorHeights().stream().reduce(BigDecimal::min).get();
							} catch (Exception e) {
								
							}
							boolean isValid = false;
							BigDecimal expectedHeight = new BigDecimal("2.4");

							if (pl.getDrawingPreference().getInFeets()) {
								expectedHeight = new BigDecimal("8.0");// in feet
								providedHeight = CDGAdditionalService.inchToFeet(providedHeight);
							}

							if (providedHeight.compareTo(expectedHeight) >= 0)
								isValid = true;
							if (isValid)
								setReportOutputDetails(pl,
										CDGAdditionalService.getByLaws(mostRestrictiveOccupancyType,
												CDGAConstant.CHECKPOST),
										"Gallery Floors Height", " floor " + floor.getNumber(),
										CDGAdditionalService.viewLenght(pl, expectedHeight),
										CDGAdditionalService.viewLenght(pl, providedHeight),
										Result.Accepted.getResultVal());
							else
								setReportOutputDetails(pl,
										CDGAdditionalService.getByLaws(mostRestrictiveOccupancyType,
												CDGAConstant.CHECKPOST),
										"Gallery Floors Height", " floor " + floor.getNumber(),
										CDGAdditionalService.viewLenght(pl, expectedHeight),
										CDGAdditionalService.viewLenght(pl, providedHeight),
										Result.Not_Accepted.getResultVal());

						}
					}
				}
			}

		}

	}

	private void validateResidentialUse(Plan pl) {

		ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.setKey("Common_Residential Use");
		scrutinyDetail.addColumnHeading(1, RULE_NO);
		scrutinyDetail.addColumnHeading(2, DESCRIPTION);
		scrutinyDetail.addColumnHeading(3, REQUIRED);
		scrutinyDetail.addColumnHeading(4, PROVIDED);
		scrutinyDetail.addColumnHeading(5, STATUS);
		Map<String, String> details = new HashMap<>();

		OccupancyTypeHelper mostRestrictiveOccupancyType = pl.getVirtualBuilding() != null
				? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;
		BigDecimal floorTotalFloorArea = BigDecimal.ZERO;
		BigDecimal residentialUseFar = BigDecimal.ZERO;
		if (mostRestrictiveOccupancyType == null || mostRestrictiveOccupancyType.getSubtype() == null)
			return;
		if (DxfFileConstants.G.equals(mostRestrictiveOccupancyType.getType().getCode())) {
			for (Occupancy occupancy : pl.getOccupancies()) {
				if (DxfFileConstants.A_RU.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
					floorTotalFloorArea = floorTotalFloorArea.add(occupancy.getBuiltUpArea());
				}
			}

			BigDecimal plotArea = pl.getPlot() != null ? pl.getPlot().getArea() : BigDecimal.ZERO;

			if (plotArea.doubleValue() > 0 && floorTotalFloorArea.compareTo(BigDecimal.ZERO) > 0)
				residentialUseFar = calulateFar(floorTotalFloorArea, pl.getPlot().getArea(),
						pl.getDrawingPreference().getInFeets());

			if (pl.getFarDetails().getPermissableFar() == null)
				pl.addError("ResidentialUse", "Residential Use  cannot be validated.");

			double expectedFar = 0;
			if (pl.getFarDetails().getPermissableFar() != null)
				expectedFar = pl.getFarDetails().getPermissableFar() - pl.getFarDetails().getProvidedFar();

			if (pl.getFarDetails().getPermissableFar() == null)
				return;

			if ((pl.getFarDetails().getPermissableFar() * 0.025) > expectedFar) {
				expectedFar = pl.getFarDetails().getPermissableFar() * 0.025;
			}

			// details.put(RULE_NO,
			// CDGAdditionalService.getByLaws(mostRestrictiveOccupancyType,
			// CDGAConstant.CHECKPOST));
			details.put(DESCRIPTION, "Residential Use");
			details.put(REQUIRED, "Maximum 2.5% of permissible FAR (" + expectedFar + ")");
			details.put(PROVIDED, CDGAdditionalService.roundBigDecimal(residentialUseFar) + "");

			if (residentialUseFar.compareTo(new BigDecimal(expectedFar)) <= 0) {
				details.put(STATUS, Result.Accepted.getResultVal());
			} else {
				details.put(STATUS, Result.Not_Accepted.getResultVal());
			}

			scrutinyDetail.getDetail().add(details);
			pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

		}

	}

	private void setReportOutputDetails(Plan pl, String ruleNo, String ruleDesc, String floor, String expected,
			String actual, String status) {
		Map<String, String> details = new HashMap<>();
		details.put(RULE_NO, ruleNo);
		details.put(DESCRIPTION, ruleDesc);
		details.put(FLOOR, floor);
		details.put(REQUIRED, expected);
		details.put(PROVIDED, actual);
		details.put(STATUS, status);
		scrutinyDetail.getDetail().add(details);
		pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
	}

	private void setReportOutputDetailsWithoutFloor(Plan pl, String ruleNo, String ruleDesc, String expected,
			String actual, String status) {
		Map<String, String> details = new HashMap<>();
		details.put(RULE_NO, ruleNo);
		details.put(DESCRIPTION, ruleDesc);
		details.put(REQUIRED, expected);
		details.put(PROVIDED, actual);
		details.put(STATUS, status);
		scrutinyDetail.getDetail().add(details);
		pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
	}

	@Override
	public Map<String, Date> getAmendments() {
		return new LinkedHashMap<>();
	}

}
