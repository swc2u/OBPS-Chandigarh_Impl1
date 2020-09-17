/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency
,
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.common.entity.edcr.TerraceUtility;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.service.cdg.CDGAConstant;
import org.egov.edcr.service.cdg.CDGADeviationConstant;
import org.egov.edcr.service.cdg.CDGAdditionalService;
import org.egov.edcr.utility.Util;
import org.springframework.stereotype.Service;

import com.sun.el.stream.Stream;

@Service
public class TerraceUtilityService extends FeatureProcess {

	private static final Logger LOG = Logger.getLogger(TerraceUtility.class);
	private static final String RULE_34 = "43-1";
	public static final String TERRACEUTILITIESDISTANCE = "TerraceUtilitiesDistance";
	public static final BigDecimal THREE = BigDecimal.valueOf(3);
	public static final String ERROR_MSG = "Minimum_distance";
	private static final int FRONT_AND_REAR_COLOR_CODE = 37;
	private static final int SIDE_COLOR_CODE = 39;

	@Override
	public Map<String, Date> getAmendments() {
		return null;
	}

	@Override
	public Plan validate(Plan pl) {
		return pl;
	}

	@Override
	public Plan process(Plan pl) {

		BigDecimal expectedFrontAndRearDistance = new BigDecimal("3.0");//3.048
		BigDecimal expectedSideDistance = new BigDecimal("3.0");
		if (pl.getDrawingPreference().getInFeets()) {
			expectedFrontAndRearDistance=CDGADeviationConstant.addDeviation(expectedFrontAndRearDistance, CDGADeviationConstant.TERRACE_DEVIATION_MAX);
			expectedSideDistance=CDGADeviationConstant.addDeviation(expectedSideDistance, CDGADeviationConstant.TERRACE_DEVIATION_MAX);
		}

		if (pl.getIsRowHouse()) {
			expectedSideDistance = new BigDecimal("1.2");//1.219
			if (pl.getDrawingPreference().getInFeets()) {
				expectedSideDistance=CDGADeviationConstant.addDeviation(expectedSideDistance, CDGADeviationConstant.TERRACE_DEVIATION_MIN);
			}
		}

		if (pl.getDrawingPreference().getInFeets()) {
			expectedFrontAndRearDistance = CDGAdditionalService.meterToFoot(expectedFrontAndRearDistance);
			expectedSideDistance = CDGAdditionalService.meterToFoot(expectedSideDistance);
		}

		if (pl.getBlocks() != null) {
			for (Block block : pl.getBlocks()) {
				ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
				scrutinyDetail.setKey("Block_" + block.getNumber() + "_" + "Terrace Utility");
				scrutinyDetail.addColumnHeading(1, RULE_NO);
				scrutinyDetail.addColumnHeading(2, DESCRIPTION);
				scrutinyDetail.addColumnHeading(3, PERMITTED);
				scrutinyDetail.addColumnHeading(4, PROVIDED);
				scrutinyDetail.addColumnHeading(5, STATUS);

				List<BigDecimal> frontAndRear = new ArrayList<BigDecimal>();
				List<BigDecimal> side = new ArrayList<BigDecimal>();

				for (TerraceUtility terraceUtility : block.getTerraceUtilities()) {
					if (terraceUtility.getColorCode() == FRONT_AND_REAR_COLOR_CODE) {
						frontAndRear.addAll(terraceUtility.getDistances());
					} else if (terraceUtility.getColorCode() == SIDE_COLOR_CODE) {
						side.addAll(terraceUtility.getDistances());
					}
				}
				BigDecimal providedMinFront = BigDecimal.ZERO;
				BigDecimal providedMinSide = BigDecimal.ZERO;
				try {
					providedMinFront = frontAndRear.stream().reduce(BigDecimal::min).get();
					providedMinSide = side.stream().reduce(BigDecimal::min).get();
				} catch (Exception e) {
					
				}

				if (pl.getDrawingPreference().getInFeets()) {
					providedMinFront = CDGAdditionalService.inchToFeet(providedMinFront);
					providedMinSide = CDGAdditionalService.inchToFeet(providedMinSide);
				}

				boolean frontAccepted = false;
				boolean sideAccepted = false;
				if (providedMinFront.compareTo(expectedFrontAndRearDistance) >= 0) {
					frontAccepted = true;
				}
				if (providedMinSide.compareTo(expectedSideDistance) >= 0) {
					sideAccepted = true;
				}
				Map<String, String> details = new HashMap<>();
				details.put(RULE_NO, CDGAdditionalService.getByLaws(pl, CDGAConstant.SERVICE_ZONE_ON_TERRACE));
				details.put(DESCRIPTION, "Front & Rear Distance");
				details.put(PERMITTED, CDGAdditionalService.viewLenght(pl, expectedFrontAndRearDistance));
				details.put(PROVIDED, CDGAdditionalService.viewLenght(pl, providedMinFront));
				details.put(STATUS,
						frontAccepted ? Result.Accepted.getResultVal() : Result.Not_Accepted.getResultVal());
				scrutinyDetail.getDetail().add(details);

				Map<String, String> details1 = new HashMap<>();
				details1.put(RULE_NO, CDGAdditionalService.getByLaws(pl, CDGAConstant.SERVICE_ZONE_ON_TERRACE));
				details1.put(DESCRIPTION, "Side Distance");
				details1.put(PERMITTED, CDGAdditionalService.viewLenght(pl, expectedSideDistance));
				details1.put(PROVIDED, CDGAdditionalService.viewLenght(pl, providedMinSide));
				details1.put(STATUS,
						sideAccepted ? Result.Accepted.getResultVal() : Result.Not_Accepted.getResultVal());
				scrutinyDetail.getDetail().add(details1);

				pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

			}

		}
		return pl;
	}
}
