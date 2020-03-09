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

package org.egov.edcr.feature;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.egov.common.entity.edcr.OccupancyType;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.service.cdg.CDGAConstant;
import org.egov.edcr.service.cdg.CDGAdditionalService;
import org.springframework.stereotype.Service;

@Service
public class SolarWaterHeating extends FeatureProcess {

	private static final Logger LOG = Logger.getLogger(SolarWaterHeating.class);

	// CGCL start according to 26jan

	private static final String RULE_51 = "51";
	private static final String RULE_51_DESCRIPTION = "Solar Water Heating";

	/*
	 * @Override public Plan validate(Plan pl) {
	 * 
	 * return pl; }
	 */

	public  boolean isOccupancyTypeNotApplicable(OccupancyTypeHelper occupancyTypeHelper) {
		boolean flage = false;

		if (DxfFileConstants.F_SCO.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_B.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_PP.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_CD.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.R1.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.T1.equals(occupancyTypeHelper.getSubtype().getCode()))
			flage = true;

		return flage;
	}
	@Override
	public Plan process(Plan pl) {

		String areaType = "";

		HashMap<String, String> errors = new HashMap<>();

		scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.addColumnHeading(1, RULE_NO);
		scrutinyDetail.addColumnHeading(2, DESCRIPTION);
		scrutinyDetail.addColumnHeading(3, REQUIRED);
		scrutinyDetail.addColumnHeading(4, PROVIDED);
		scrutinyDetail.addColumnHeading(5, STATUS);
		scrutinyDetail.setKey("Common_Solar Water Heating");
		String subRule = CDGAdditionalService.getByLaws(pl, CDGAConstant.SOLAR_WATER_HEATING_SYSTEM);
		String subRuleDesc = RULE_51_DESCRIPTION;
		BigDecimal expectedTankCapacity = BigDecimal.ZERO;

		boolean valid = false;
		BigDecimal plotArea = pl.getPlot() != null ? pl.getPlot().getArea() : BigDecimal.ZERO;
		OccupancyTypeHelper mostRestrictiveFarHelper = pl.getVirtualBuilding() != null
				? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;
		
		if(isOccupancyTypeNotApplicable(mostRestrictiveFarHelper))
			return pl;

		areaType = pl.getPlanInfoProperties().get(DxfFileConstants.AREA_TYPE);

		OccupancyTypeHelper mostRestrictiveOccupancyType = pl.getVirtualBuilding() != null
				? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;
				
				
		
		if (mostRestrictiveFarHelper != null)
			if (checkOccupancyTypeForSolarWaterHeating(mostRestrictiveFarHelper.getType().getCode(), areaType)) {
				BigDecimal roundOffPlotArea = plotArea.divide(BigDecimal.valueOf(100));
				if (areaType.equalsIgnoreCase(DxfFileConstants.ONE_KANAL)) {
					expectedTankCapacity = BigDecimal.valueOf(100)
							.multiply(roundOffPlotArea.setScale(0, BigDecimal.ROUND_HALF_UP));

				} else if (areaType.equalsIgnoreCase(DxfFileConstants.TWO_KANAL)
						|| areaType.equalsIgnoreCase(DxfFileConstants.ABOVE_TWO_KANAL)) {
					expectedTankCapacity = BigDecimal.valueOf(200)
							.multiply(roundOffPlotArea.setScale(0, BigDecimal.ROUND_HALF_UP));

				}

				if (pl.getUtility().getRainWaterHarvestingTankCapacity().compareTo(expectedTankCapacity) >= 0)
					valid = true;
				processSolarWaterTankCapacity(pl, "", subRule, subRuleDesc, expectedTankCapacity, valid);
			} else {
				processSolarWaterHeating(pl, "", subRule, subRuleDesc);
			}

		return pl;
	}

	private void processSolarWaterTankCapacity(Plan planDetail, String rule, String subRule, String subRuleDesc,
			BigDecimal expectedTankCapacity, Boolean valid) {
		if (expectedTankCapacity.compareTo(BigDecimal.valueOf(0)) > 0) {
			if (valid) {
				setReportOutputDetails(planDetail, subRule, "RAINWATER_HARVESTING_TANK_CAPACITY",
						expectedTankCapacity.toString(),
						planDetail.getUtility().getRainWaterHarvestingTankCapacity().toString(),
						Result.Accepted.getResultVal());
			} else {
				setReportOutputDetails(planDetail, subRule, "RAINWATER_HARVESTING_TANK_CAPACITY",
						expectedTankCapacity.toString() + "IN_LITRE",
						planDetail.getUtility().getRainWaterHarvestingTankCapacity().toString() + "IN_LITRE",
						Result.Not_Accepted.getResultVal());
			}
		}
	}

	private boolean processSolarWaterHeating(Plan planDetail, String rule, String subRule, String subRuleDesc) {
		if (!planDetail.getUtility().getSolarWaterHeatingSystems().isEmpty()) {
			setReportOutputDetails(planDetail, subRule, subRuleDesc, "", "OBJECTDEFINED_DESC",
					Result.Accepted.getResultVal());
			return true;
		} else if (planDetail.getUtility().getSolarWaterHeatingSystems().isEmpty()) {
			setReportOutputDetails(planDetail, subRule, subRuleDesc, "", "OBJECTDEFINED_DESC",
					Result.Not_Accepted.getResultVal());
			return true;
		}
		return false;
	}

	private void setReportOutputDetails(Plan pl, String ruleNo, String ruleDesc, String expected, String actual,
			String status) {
		Map<String, String> details = new HashMap<>();
		details.put(RULE_NO, CDGAdditionalService.getByLaws(pl, CDGAConstant.SOLAR_WATER_HEATING_SYSTEM));
		details.put(DESCRIPTION, ruleDesc);
		details.put(REQUIRED, expected);
		details.put(PROVIDED, actual);
		details.put(STATUS, status);
		scrutinyDetail.getDetail().add(details);
		pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
	}

	private boolean checkOccupancyTypeForSolarWaterHeating(String occupancyType, String areaType) {

		if (occupancyType.equals(OccupancyType.OCCUPANCY_A1_P.toString())
				&& (DxfFileConstants.ONE_KANAL.equalsIgnoreCase(areaType)
						|| DxfFileConstants.TWO_KANAL.equalsIgnoreCase(areaType))
				|| DxfFileConstants.ABOVE_TWO_KANAL.equalsIgnoreCase(areaType)) {
			return true;
		}

		return false;
	}

	// CGCL End

	@Override
	public Map<String, Date> getAmendments() {
		return new LinkedHashMap<>();
	}

	@Override
	public Plan validate(Plan pl) {
		// TODO Auto-generated method stub
		return pl;
	}

}
