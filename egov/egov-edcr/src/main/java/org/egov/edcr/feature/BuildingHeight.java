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

import static org.egov.edcr.utility.DcrConstants.BUILDING_HEIGHT;
import static org.egov.edcr.utility.DcrConstants.HEIGHT_OF_BUILDING;
import static org.egov.edcr.utility.DcrConstants.OBJECTNOTDEFINED;
import static org.egov.edcr.utility.DcrConstants.SECURITY_ZONE;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.CulDeSacRoad;
import org.egov.common.entity.edcr.Lane;
import org.egov.common.entity.edcr.NonNotifiedRoad;
import org.egov.common.entity.edcr.NotifiedRoad;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.service.ProcessHelper;
import org.egov.edcr.service.cdg.CDGAConstant;
import org.egov.edcr.service.cdg.CDGAdditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingHeight extends FeatureProcess {
	private static final String RULE_EXPECTED_KEY = "buildingheight.expected";
	private static final String RULE_ACTUAL_KEY = "buildingheight.actual";
	private static final String SECURITYZONE_RULE_EXPECTED_KEY = "securityzone.expected";
	private static final String SECURITYZONE_RULE_ACTUAL_KEY = "securityzone.actual";

	private static final String SUB_RULE_32_1A = "32-1A";
	private static final String SUB_RULE_32_3 = "32-3";
	public static final String UPTO = "Up To";
	public static final String DECLARED = "Declared";
	private static final BigDecimal TWELVE = BigDecimal.valueOf(12);
	private static final BigDecimal TEN = BigDecimal.valueOf(10);

	@Autowired
	CDGAdditionalService cdgAdditionalService;

	@Override
	public Plan validate(Plan pl) {
		HashMap<String, String> errors = new HashMap<>();
		if (!ProcessHelper.isSmallPlot(pl)) {
			for (Block block : pl.getBlocks()) {
				if (!block.getCompletelyExisting()) {
					if (block.getBuilding() != null && (block.getBuilding().getBuildingHeight() == null
							|| block.getBuilding().getBuildingHeight().compareTo(BigDecimal.ZERO) <= 0)) {
						errors.put(BUILDING_HEIGHT + block.getNumber(), getLocaleMessage(OBJECTNOTDEFINED,
								BUILDING_HEIGHT + " for block " + block.getNumber()));
						pl.addErrors(errors);
					}
					// distance from end of road to foot print is mandatory.
					// CSCL comment start
					/*
					 * if
					 * (block.getBuilding().getDistanceFromBuildingFootPrintToRoadEnd().isEmpty()) {
					 * errors.put(SHORTESTDISTINACETOBUILDINGFOOTPRINT + block.getNumber(),
					 * getLocaleMessage(OBJECTNOTDEFINED, SHORTESTDISTINACETOBUILDINGFOOTPRINT +
					 * " for block " + block.getNumber())); pl.addErrors(errors); }
					 */
					// CSCL comment end
				}
			}
		}
		return pl;
	}

	@Override
	public Plan process(Plan Plan) {

		validate(Plan);
		scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.setKey("Common_Height of Building");
		scrutinyDetail.addColumnHeading(1, RULE_NO);
		scrutinyDetail.addColumnHeading(2, DESCRIPTION);
		scrutinyDetail.addColumnHeading(3, UPTO);
		scrutinyDetail.addColumnHeading(4, PROVIDED);
		scrutinyDetail.addColumnHeading(5, STATUS);

		if (!ProcessHelper.isSmallPlot(Plan)) {
			checkBuildingHeight(Plan);
		}
		// CSCL comment start
		// checkBuildingInSecurityZoneArea(Plan);
		// CSCL comment end
		return Plan;
	}

	private void checkBuildingHeight(Plan Plan) {
		String subRule = SUB_RULE_32_1A;
		String rule = HEIGHT_OF_BUILDING;

		BigDecimal maximumDistanceToRoad = BigDecimal.ZERO;

		// get maximum height from buildings.
		for (Block block : Plan.getBlocks()) {

			BigDecimal exptectedHeight = BigDecimal.ZERO;
			BigDecimal buildingHeight = BigDecimal.ZERO;

			OccupancyTypeHelper occupancyTypeHelper = Plan.getVirtualBuilding() != null
					? Plan.getVirtualBuilding().getMostRestrictiveFarHelper()
					: null;
			if (occupancyTypeHelper != null) {

				if (DxfFileConstants.F_SCO.equals(occupancyTypeHelper.getSubtype().getCode())
						|| DxfFileConstants.F_B.equals(occupancyTypeHelper.getSubtype().getCode())
						|| DxfFileConstants.F_H.equals(occupancyTypeHelper.getSubtype().getCode())
						|| DxfFileConstants.F_M.equals(occupancyTypeHelper.getSubtype().getCode())
						|| DxfFileConstants.F_CFI.equals(occupancyTypeHelper.getSubtype().getCode())
						|| DxfFileConstants.F_TCIM.equals(occupancyTypeHelper.getSubtype().getCode())
						|| DxfFileConstants.G.equals(occupancyTypeHelper.getType().getCode())) {

					String suboccTypeCode = occupancyTypeHelper.getSubtype().getCode();
					Map<String, String> keyArrgument = new HashMap<String, String>();
					keyArrgument.put(CDGAdditionalService.OCCUPENCY_CODE, suboccTypeCode);
					keyArrgument.put(CDGAdditionalService.SECTOR,
							Plan.getPlanInfoProperties().get(DxfFileConstants.SECTOR_NUMBER));
					keyArrgument.put(CDGAdditionalService.PLOT_TYPE,
							Plan.getPlanInfoProperties().get(DxfFileConstants.PLOT_TYPE));
					keyArrgument.put(CDGAdditionalService.PLOT_NO,
							Plan.getPlanInfoProperties().get(DxfFileConstants.PLOT_NO));

					Map<String, String> featureValues = cdgAdditionalService
							.getFeatureValue(CDGAConstant.PERMISSIBLE_BUILDING_HEIGHT, keyArrgument);
					String str=featureValues.get(CDGAdditionalService.PERMISSIBLE_BUILDING_HEIGHT);
					if(DxfFileConstants.DATA_NOT_FOUND.equals(str)) {
						Plan.addError("PERMISSIBLE_BUILDING_HEIGHT ", "PERMISSIBLE_BUILDING_HEIGHT, "+DxfFileConstants.DATA_NOT_FOUND);
						return;
					}
					exptectedHeight = new BigDecimal(
							featureValues.get(CDGAdditionalService.PERMISSIBLE_BUILDING_HEIGHT)!=null?featureValues.get(CDGAdditionalService.PERMISSIBLE_BUILDING_HEIGHT):"0");
				}

				if (DxfFileConstants.A.equals(occupancyTypeHelper.getType().getCode())) {

					String plotType = Plan.getPlanInfoProperties().get(DxfFileConstants.PLOT_TYPE);
					String sector = Plan.getPlanInfoProperties().get(DxfFileConstants.SECTOR_NUMBER);
					int phase = CDGAdditionalService.getSectorPhase(sector);

					if (DxfFileConstants.A_P.equals(occupancyTypeHelper.getSubtype().getCode())) {
						if (DxfFileConstants.MARLA.equals(plotType) && phase == 1)
							exptectedHeight = new BigDecimal("10.06");
						else if (DxfFileConstants.MARLA.equals(plotType) && phase == 2)
							exptectedHeight = new BigDecimal("9.83");
						else if(!DxfFileConstants.MARLA.equals(plotType))
							exptectedHeight = new BigDecimal("10.67");
					} else if (DxfFileConstants.A_G.equals(occupancyTypeHelper.getSubtype().getCode())) {
						if (DxfFileConstants.ONE_KANAL.equals(plotType) && (phase == 1 || phase == 2))
							exptectedHeight = new BigDecimal("14.25");
						if (DxfFileConstants.ABOVE_TWO_KANAL.equals(plotType) && phase == 3)
							exptectedHeight = new BigDecimal("22.63");
					}
				}

				if (DxfFileConstants.F.equals(occupancyTypeHelper.getType().getCode())) {

					if (DxfFileConstants.F_BH.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("10.97");
					else if (DxfFileConstants.F_BBM.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("7.54");
					else if (DxfFileConstants.F_TS.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("3.88");
					else if (DxfFileConstants.F_CD.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("3.89");
					else if (DxfFileConstants.F_CD.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("6.09");
				}

				if (DxfFileConstants.P.equals(occupancyTypeHelper.getType().getCode())) {
					if (DxfFileConstants.P_D.equals(occupancyTypeHelper.getSubtype().getCode())
							|| DxfFileConstants.P_P.equals(occupancyTypeHelper.getSubtype().getCode())
							|| DxfFileConstants.P_F.equals(occupancyTypeHelper.getSubtype().getCode())
							|| DxfFileConstants.P_N.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("14.86");
					else if (DxfFileConstants.P_H.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("17.52");
					else if (DxfFileConstants.P_CC.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("12.20");
					else if (DxfFileConstants.P_SS.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("14.86");
					else if (DxfFileConstants.P_CNA.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("14.86");
					else if (DxfFileConstants.P_R.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("10.97");
				}

				if (DxfFileConstants.B.equals(occupancyTypeHelper.getType().getCode())) {
					if (DxfFileConstants.B_EC.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("17.52");
					else if (DxfFileConstants.B_HEI.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("14.86");
					else if (DxfFileConstants.B_H.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("14.86");
				}

				if (DxfFileConstants.IT.equals(occupancyTypeHelper.getType().getCode())) {
					exptectedHeight = new BigDecimal("22.63");
				}

				if (DxfFileConstants.R.equals(occupancyTypeHelper.getType().getCode())) {
					exptectedHeight = new BigDecimal("24.00");
				}

				if (DxfFileConstants.ITH.equals(occupancyTypeHelper.getType().getCode())) {
					if (DxfFileConstants.ITH_H.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("17.52");
					else if (DxfFileConstants.ITH_C.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("22.63");
					else if (DxfFileConstants.ITH_CC.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("14.86");
					else if (DxfFileConstants.ITH_R.equals(occupancyTypeHelper.getSubtype().getCode())
							|| DxfFileConstants.ITH_GH.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("22.63");
				}

				if (DxfFileConstants.IP.equals(occupancyTypeHelper.getType().getCode())) {
					if (DxfFileConstants.IP_I.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("25.48");
					else if (DxfFileConstants.IP_R.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("22.86");
					else if (DxfFileConstants.IP_C.equals(occupancyTypeHelper.getSubtype().getCode()))
						exptectedHeight = new BigDecimal("22.86");

				}

				if (DxfFileConstants.T.equals(occupancyTypeHelper.getType().getCode())) {

					exptectedHeight = new BigDecimal("30.00");

				}

				buildingHeight = block.getBuilding().getBuildingHeight();

				if (exptectedHeight.compareTo(BigDecimal.ZERO) > 0) {
//					String actualResult = getLocaleMessage(RULE_ACTUAL_KEY, buildingHeight.toString());
//					String expectedResult = getLocaleMessage(RULE_EXPECTED_KEY, exptectedHeight.toString());
					
					String actualResult = buildingHeight.toString()+DxfFileConstants.METER;
					String expectedResult = "Upto "+exptectedHeight.toString()+DxfFileConstants.METER;

					if (buildingHeight.compareTo(exptectedHeight) > 0) {
						Map<String, String> details = new HashMap<>();
					//	details.put(RULE_NO, subRule);
						details.put(RULE_NO, CDGAdditionalService.getByLaws(occupancyTypeHelper, CDGAConstant.PERMISSIBLE_BUILDING_HEIGHT));
							details.put(DESCRIPTION, HEIGHT_OF_BUILDING + " for Block " + block.getNumber());
						details.put(UPTO, expectedResult);
						details.put(PROVIDED, actualResult);
						details.put(STATUS, Result.Not_Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
						Plan.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

					} else {
						Map<String, String> details = new HashMap<>();
					//	details.put(RULE_NO, subRule);
						details.put(RULE_NO, CDGAdditionalService.getByLaws(occupancyTypeHelper, CDGAConstant.PERMISSIBLE_BUILDING_HEIGHT));
						details.put(DESCRIPTION, HEIGHT_OF_BUILDING + " for Block " + block.getNumber());
						details.put(UPTO, expectedResult);
						details.put(PROVIDED, actualResult);
						details.put(STATUS, Result.Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
						Plan.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
					}
				}
			}
		}
	}

	private void checkBuildingInSecurityZoneArea(Plan Plan) {

		if (Plan.getPlanInformation().getSecurityZone()) {
			BigDecimal maxBuildingHeight = BigDecimal.ZERO;
			for (Block block : Plan.getBlocks()) {
				if (maxBuildingHeight.compareTo(BigDecimal.ZERO) == 0
						|| block.getBuilding().getBuildingHeight().compareTo(maxBuildingHeight) >= 0) {
					maxBuildingHeight = block.getBuilding().getBuildingHeight();
				}
			}
			if (maxBuildingHeight.compareTo(BigDecimal.ZERO) > 0) {

				scrutinyDetail = new ScrutinyDetail();
				scrutinyDetail.setKey("Common_Security Zone");
				scrutinyDetail.addColumnHeading(1, RULE_NO);
				scrutinyDetail.addColumnHeading(2, DESCRIPTION);
				scrutinyDetail.addColumnHeading(3, REQUIRED);
				scrutinyDetail.addColumnHeading(4, PROVIDED);
				scrutinyDetail.addColumnHeading(5, STATUS);

				String actualResult = getLocaleMessage(SECURITYZONE_RULE_ACTUAL_KEY, maxBuildingHeight.toString());
				String expectedResult = getLocaleMessage(SECURITYZONE_RULE_EXPECTED_KEY, TEN.toString());

				if (maxBuildingHeight.compareTo(TEN) <= 0) {
					Map<String, String> details = new HashMap<>();
					details.put(RULE_NO, SUB_RULE_32_3);
					details.put(DESCRIPTION, SECURITY_ZONE);
					details.put(REQUIRED, expectedResult);
					details.put(PROVIDED, actualResult);
					details.put(STATUS, Result.Verify.getResultVal());
					scrutinyDetail.getDetail().add(details);
					Plan.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
				} else {
					Map<String, String> details = new HashMap<>();
					details.put(RULE_NO, SUB_RULE_32_3);
					details.put(DESCRIPTION, SECURITY_ZONE);
					details.put(REQUIRED, expectedResult);
					details.put(PROVIDED, actualResult);
					details.put(STATUS, Result.Not_Accepted.getResultVal());
					scrutinyDetail.getDetail().add(details);
					Plan.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
				}
			}
		} else {
			scrutinyDetail = new ScrutinyDetail();
			scrutinyDetail.setKey("Common_Security Zone");
			scrutinyDetail.addColumnHeading(1, RULE_NO);
			scrutinyDetail.addColumnHeading(2, DESCRIPTION);
			scrutinyDetail.addColumnHeading(3, DECLARED);
			scrutinyDetail.addColumnHeading(4, STATUS);

			Map<String, String> details = new HashMap<>();
			details.put(RULE_NO, SUB_RULE_32_3);
			details.put(DESCRIPTION, SECURITY_ZONE);
			details.put(DECLARED, "No");
			details.put(STATUS, Result.Verify.getResultVal());
			scrutinyDetail.getDetail().add(details);
			Plan.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

		}

	}

	private BigDecimal getMaximumDistanceFromRoadEdge(BigDecimal maximumDistanceToRoadEdge, Block block) {
		if (block.getBuilding().getDistanceFromBuildingFootPrintToRoadEnd() != null) {
			for (BigDecimal distanceFromroadEnd : block.getBuilding().getDistanceFromBuildingFootPrintToRoadEnd()) {
				if (distanceFromroadEnd.compareTo(maximumDistanceToRoadEdge) > 0) {
					maximumDistanceToRoadEdge = distanceFromroadEnd;
				}
			}
		}
		return maximumDistanceToRoadEdge;
	}

	private BigDecimal getMaximumDistanceFromSetBackToBuildingLine(BigDecimal distanceFromSetbackToBuildingLine,
			Block block) {
		if (block.getBuilding().getDistanceFromSetBackToBuildingLine() != null) {
			for (BigDecimal distance : block.getBuilding().getDistanceFromSetBackToBuildingLine()) {
				if (distance.compareTo(distanceFromSetbackToBuildingLine) > 0) {
					distanceFromSetbackToBuildingLine = distance;
				}
			}
		}
		return distanceFromSetbackToBuildingLine;
	}

	private BigDecimal getMaximimShortestdistanceFromRoad(Plan Plan, BigDecimal maximumDistanceToRoad) {
		if (Plan.getNonNotifiedRoads() != null)
			for (NonNotifiedRoad nonnotifiedRoad : Plan.getNonNotifiedRoads())
				for (BigDecimal shortDistance : nonnotifiedRoad.getShortestDistanceToRoad())
					if (shortDistance.compareTo(maximumDistanceToRoad) > 0) {
						maximumDistanceToRoad = shortDistance;
					}
		if (Plan.getNotifiedRoads() != null)
			for (NotifiedRoad notifiedRoad : Plan.getNotifiedRoads())
				for (BigDecimal shortDistance : notifiedRoad.getShortestDistanceToRoad())
					if (shortDistance.compareTo(maximumDistanceToRoad) > 0) {
						maximumDistanceToRoad = shortDistance;
					}
		if (Plan.getCuldeSacRoads() != null)
			for (CulDeSacRoad culdRoad : Plan.getCuldeSacRoads())
				for (BigDecimal shortDistance : culdRoad.getShortestDistanceToRoad())
					if (shortDistance.compareTo(maximumDistanceToRoad) > 0) {
						maximumDistanceToRoad = shortDistance;
					}
		if (Plan.getLaneRoads() != null)
			for (Lane lane : Plan.getLaneRoads())
				for (BigDecimal shortDistance : lane.getShortestDistanceToRoad())
					if (shortDistance.compareTo(maximumDistanceToRoad) > 0) {
						maximumDistanceToRoad = shortDistance;
					}
		return maximumDistanceToRoad;
	}

	@Override
	public Map<String, Date> getAmendments() {
		return new LinkedHashMap<>();
	}

}
