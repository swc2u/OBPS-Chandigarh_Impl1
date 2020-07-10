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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.Measurement;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.service.cdg.CDGAConstant;
import org.egov.edcr.service.cdg.CDGAdditionalService;
import org.springframework.stereotype.Service;

@Service
public class Parapet extends FeatureProcess {

	private static final Logger LOG = Logger.getLogger(Parapet.class);
	private static final String RULE_41_V = "41-v";
	public static final String PARAPET_DESCRIPTION = "Parapet";
	public static final int GENERAL_PARAPET = 3;
	public static final int STRAIRCASE_PARAPET = 2;
	public static final int SERVICE_ZONE_PARAPET = 1;

	@Override
	public Plan validate(Plan pl) {

		return pl;
	}

	// CGCL start according to 26jan

	/*
	 * @Override public Plan process(Plan pl) {
	 * 
	 * ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
	 * scrutinyDetail.setKey("Common_Parapet"); scrutinyDetail.addColumnHeading(1,
	 * RULE_NO); scrutinyDetail.addColumnHeading(2, DESCRIPTION);
	 * scrutinyDetail.addColumnHeading(3, REQUIRED);
	 * scrutinyDetail.addColumnHeading(4, PROVIDED);
	 * scrutinyDetail.addColumnHeading(5, STATUS);
	 * 
	 * Map<String, String> details = new HashMap<>(); details.put(RULE_NO,
	 * RULE_41_V); details.put(DESCRIPTION, PARAPET_DESCRIPTION);
	 * 
	 * BigDecimal minHeight = BigDecimal.ZERO;
	 * 
	 * for (Block b : pl.getBlocks()) { if (b.getParapets() != null &&
	 * !b.getParapets().isEmpty()) { minHeight =
	 * b.getParapets().stream().reduce(BigDecimal::min).get();
	 * 
	 * if (minHeight.compareTo(new BigDecimal(1.2)) >= 0 && minHeight.compareTo(new
	 * BigDecimal(1.5)) <= 0) {
	 * 
	 * details.put(REQUIRED, "Height >= 1.2 and height <= 1.5");
	 * details.put(PROVIDED, "Height >= " + minHeight + " and height <= " +
	 * minHeight); details.put(STATUS, Result.Accepted.getResultVal());
	 * scrutinyDetail.getDetail().add(details);
	 * pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
	 * 
	 * } else { details.put(REQUIRED, "Height >= 1.2 and height <= 1.5");
	 * details.put(PROVIDED, "Height >= " + minHeight + " and height <= " +
	 * minHeight); details.put(STATUS, Result.Not_Accepted.getResultVal());
	 * scrutinyDetail.getDetail().add(details);
	 * pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail); } } }
	 * 
	 * return pl; }
	 */

	// @Override
	public Plan process_old(Plan pl) {

		ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.setKey("Common_Parapet");
		scrutinyDetail.addColumnHeading(1, RULE_NO);
		scrutinyDetail.addColumnHeading(2, DESCRIPTION);
		scrutinyDetail.addColumnHeading(3, REQUIRED);
		scrutinyDetail.addColumnHeading(4, PROVIDED);
		scrutinyDetail.addColumnHeading(5, STATUS);

		Map<String, String> details = new HashMap<>();
		details.put(RULE_NO, CDGAdditionalService.getByLaws(pl, CDGAConstant.PARAPET));
		details.put(DESCRIPTION, PARAPET_DESCRIPTION);

		BigDecimal minHeight = BigDecimal.ZERO;
		BigDecimal maxHeight = BigDecimal.ZERO;

		OccupancyTypeHelper mostRestrictiveOccupancy = pl.getVirtualBuilding() != null
				? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;

		if (mostRestrictiveOccupancy != null && mostRestrictiveOccupancy.getSubtype() != null
				&& !isOccupancyTypeNotApplicable(mostRestrictiveOccupancy)) {
			for (Block b : pl.getBlocks()) {
				if (b.getParapets() != null && !b.getParapets().isEmpty()) {
					minHeight = b.getParapets().stream().reduce(BigDecimal::min).get();
					maxHeight = b.getParapets().stream().reduce(BigDecimal::max).get();
					BigDecimal expectedMinHeight = new BigDecimal(1.0);
					BigDecimal expectedMaxheight = new BigDecimal(1.2);

					if (pl.getDrawingPreference().getInFeets()) {
						minHeight = CDGAdditionalService.inchToFeet(minHeight);
						maxHeight = CDGAdditionalService.inchToFeet(maxHeight);
						expectedMinHeight = CDGAdditionalService.meterToFoot(expectedMinHeight);
						expectedMaxheight = CDGAdditionalService.meterToFoot(expectedMaxheight);
					}

					if (minHeight.compareTo(expectedMinHeight) >= 0 && maxHeight.compareTo(expectedMaxheight) <= 0) {
						details.put(REQUIRED, "Height >= " + CDGAdditionalService.viewLenght(pl, expectedMinHeight)
								+ " and height <= " + CDGAdditionalService.viewLenght(pl, expectedMaxheight));
						// details.put(PROVIDED, "Height >= " + minHeight + " and height <= " +
						// maxHeight);
						details.put(PROVIDED, "Minimum height = " + CDGAdditionalService.viewLenght(pl, minHeight)
								+ " and Maximum height = " + CDGAdditionalService.viewLenght(pl, maxHeight));
						details.put(STATUS, Result.Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
						pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

					} else {
						details.put(REQUIRED, "Height >= " + CDGAdditionalService.viewLenght(pl, expectedMinHeight)
								+ " and height <= " + CDGAdditionalService.viewLenght(pl, expectedMaxheight));
						details.put(PROVIDED, "Height >= " + minHeight + " and height <= " + maxHeight);
						details.put(PROVIDED, "Minimum height = " + CDGAdditionalService.viewLenght(pl, minHeight)
								+ " and Maximum height = " + CDGAdditionalService.viewLenght(pl, maxHeight));
						details.put(STATUS, Result.Not_Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
						pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
					}
				}
			}
		}

		if (pl.isRural()) {
			for (Block b : pl.getBlocks()) {
				if (b.getParapets() != null && !b.getParapets().isEmpty()) {
					minHeight = b.getParapets().stream().reduce(BigDecimal::min).get();
					maxHeight = b.getParapets().stream().reduce(BigDecimal::max).get();

					BigDecimal expectedMaxHeight = new BigDecimal(0.9);

					if (pl.getDrawingPreference().getInFeets()) {
						minHeight = CDGAdditionalService.inchToFeet(minHeight);
						maxHeight = CDGAdditionalService.inchToFeet(maxHeight);
						expectedMaxHeight = CDGAdditionalService.meterToFoot(expectedMaxHeight);
					}

					if (maxHeight.compareTo(expectedMaxHeight) <= 0) {
						details.put(REQUIRED, "Height <= " + CDGAdditionalService.viewLenght(pl, expectedMaxHeight));
						// details.put(PROVIDED, "Height >= " + minHeight + " and height <= " +
						// maxHeight);
						details.put(PROVIDED, "Minimum height = " + CDGAdditionalService.viewLenght(pl, minHeight)
								+ " and Maximum height = " + CDGAdditionalService.viewLenght(pl, maxHeight));
						details.put(STATUS, Result.Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
						pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

					} else {
						details.put(REQUIRED, "Height <= " + CDGAdditionalService.viewLenght(pl, expectedMaxHeight));
						// details.put(PROVIDED, "Height >= " + minHeight + " and height <= " +
						// maxHeight);
						details.put(PROVIDED, "Minimum height = " + CDGAdditionalService.viewLenght(pl, minHeight)
								+ " and Maximum height = " + CDGAdditionalService.viewLenght(pl, maxHeight));
						details.put(STATUS, Result.Not_Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
						pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
					}
				}
			}
		}

		return pl;
	}

	@Override
	public Plan process(Plan plan) {
		validateGenralParapet(plan);
		validateStaircaseParapet(plan);
		validateServiceZoneParapet(plan);
		return plan;
	}

	private void validateGenralParapet(Plan pl) {

		OccupancyTypeHelper mostRestrictiveOccupancy = pl.getVirtualBuilding() != null
				? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;

		if (mostRestrictiveOccupancy != null && mostRestrictiveOccupancy.getSubtype() != null
				&& !isOccupancyTypeNotApplicable(mostRestrictiveOccupancy)) {
			for (Block block : pl.getBlocks()) {
				ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
				scrutinyDetail.addColumnHeading(1, RULE_NO);
				scrutinyDetail.addColumnHeading(2, DESCRIPTION);
				scrutinyDetail.addColumnHeading(3, REQUIRED);
				scrutinyDetail.addColumnHeading(4, PROVIDED);
				scrutinyDetail.addColumnHeading(5, STATUS);
				scrutinyDetail.setKey("Block_" + block.getNumber() + "_" + "General Parapet");

				if (!pl.isRural()) {// urban
					BigDecimal minHeight = BigDecimal.ZERO;
					BigDecimal maxHeight = BigDecimal.ZERO;
					BigDecimal expectedMinHeight = new BigDecimal(1.0);
					BigDecimal expectedMaxheight = new BigDecimal(1.2);

					try {
						minHeight = block.getParapetWithColor().stream()
								.filter(hm -> hm.getColorCode() == GENERAL_PARAPET).map(n -> n.getHeight())
								.reduce(BigDecimal::min).get();
						maxHeight = block.getParapetWithColor().stream()
								.filter(hm -> hm.getColorCode() == GENERAL_PARAPET).map(n -> n.getHeight())
								.reduce(BigDecimal::max).get();
					} catch (Exception e) {
						LOG.error(e);
					}

					if (pl.getDrawingPreference().getInFeets()) {
						minHeight = CDGAdditionalService.inchToFeet(minHeight);
						maxHeight = CDGAdditionalService.inchToFeet(maxHeight);
						expectedMinHeight = CDGAdditionalService.meterToFoot(expectedMinHeight);
						expectedMaxheight = CDGAdditionalService.meterToFoot(expectedMaxheight);
					}

					Map<String, String> details = new HashMap<>();
					details.put(RULE_NO, CDGAdditionalService.getByLaws(pl, CDGAConstant.PARAPET));
					details.put(DESCRIPTION, PARAPET_DESCRIPTION);

					if (minHeight.compareTo(expectedMinHeight) >= 0 && maxHeight.compareTo(expectedMaxheight) <= 0) {
						details.put(REQUIRED, "Height >= " + CDGAdditionalService.viewLenght(pl, expectedMinHeight)
								+ " and height <= " + CDGAdditionalService.viewLenght(pl, expectedMaxheight));
						// details.put(PROVIDED, "Height >= " + minHeight + " and height <= " +
						// maxHeight);
						details.put(PROVIDED, "Minimum height = " + CDGAdditionalService.viewLenght(pl, minHeight)
								+ " and Maximum height = " + CDGAdditionalService.viewLenght(pl, maxHeight));
						details.put(STATUS, Result.Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
						pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

					} else {
						details.put(REQUIRED, "Height >= " + CDGAdditionalService.viewLenght(pl, expectedMinHeight)
								+ " and height <= " + CDGAdditionalService.viewLenght(pl, expectedMaxheight));
						details.put(PROVIDED, "Height >= " + minHeight + " and height <= " + maxHeight);
						details.put(PROVIDED, "Minimum height = " + CDGAdditionalService.viewLenght(pl, minHeight)
								+ " and Maximum height = " + CDGAdditionalService.viewLenght(pl, maxHeight));
						details.put(STATUS, Result.Not_Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
						pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
					}

				}

				if (pl.isRural()) {// Rural
					BigDecimal maxHeight = BigDecimal.ZERO;
					BigDecimal minHeight = BigDecimal.ZERO;
					try {
						minHeight = block.getParapetWithColor().stream()
								.filter(hm -> hm.getColorCode() == GENERAL_PARAPET).map(n -> n.getHeight())
								.reduce(BigDecimal::min).get();
						maxHeight = block.getParapetWithColor().stream()
								.filter(hm -> hm.getColorCode() == GENERAL_PARAPET).map(n -> n.getHeight())
								.reduce(BigDecimal::max).get();
					} catch (Exception e) {
						LOG.error(e);
					}

					BigDecimal expectedMaxHeight = new BigDecimal(0.9);

					if (pl.getDrawingPreference().getInFeets()) {
						maxHeight = CDGAdditionalService.inchToFeet(maxHeight);
						expectedMaxHeight = CDGAdditionalService.meterToFoot(expectedMaxHeight);
					}

					Map<String, String> details = new HashMap<>();
					details.put(RULE_NO, CDGAdditionalService.getByLaws(pl, CDGAConstant.PARAPET));
					details.put(DESCRIPTION, PARAPET_DESCRIPTION);

					if (maxHeight.compareTo(expectedMaxHeight) <= 0) {
						details.put(REQUIRED, "Height <= " + CDGAdditionalService.viewLenght(pl, expectedMaxHeight));
						details.put(PROVIDED, "Minimum height = " + CDGAdditionalService.viewLenght(pl, minHeight)
								+ " and Maximum height = " + CDGAdditionalService.viewLenght(pl, maxHeight));
						details.put(STATUS, Result.Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
						pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

					} else {
						details.put(REQUIRED, "Height <= " + CDGAdditionalService.viewLenght(pl, expectedMaxHeight));
						details.put(PROVIDED, "Minimum height = " + CDGAdditionalService.viewLenght(pl, minHeight)
								+ " and Maximum height = " + CDGAdditionalService.viewLenght(pl, maxHeight));
						details.put(STATUS, Result.Not_Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
						pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
					}
				}

			}
		}

	}

	private void validateStaircaseParapet(Plan pl) {
		OccupancyTypeHelper mostRestrictiveOccupancy = pl.getVirtualBuilding() != null
				? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;

		if (mostRestrictiveOccupancy != null && mostRestrictiveOccupancy.getSubtype() != null
				&& !isOccupancyTypeNotApplicable(mostRestrictiveOccupancy)) {
			for (Block block : pl.getBlocks()) {
				ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
				scrutinyDetail.addColumnHeading(1, RULE_NO);
				scrutinyDetail.addColumnHeading(2, DESCRIPTION);
				scrutinyDetail.addColumnHeading(3, REQUIRED);
				scrutinyDetail.addColumnHeading(4, PROVIDED);
				scrutinyDetail.addColumnHeading(5, STATUS);
				scrutinyDetail.setKey("Block_" + block.getNumber() + "_" + "Staircase Parapet");

				if (!pl.isRural() && isRequiredToValidateStaircaseParapet(block)) {// urban
					BigDecimal minHeight = BigDecimal.ZERO;
					BigDecimal maxHeight = BigDecimal.ZERO;
					BigDecimal expectedMinHeight = new BigDecimal("0.9144");//3'->0.9144
					BigDecimal expectedMaxheight = new BigDecimal("1.0668");//3'6" ->0.9144+0.1524=1.0668

					try {
						minHeight = block.getParapetWithColor().stream()
								.filter(hm -> hm.getColorCode() == STRAIRCASE_PARAPET).map(n -> n.getHeight())
								.reduce(BigDecimal::min).get();
						maxHeight = block.getParapetWithColor().stream()
								.filter(hm -> hm.getColorCode() == STRAIRCASE_PARAPET).map(n -> n.getHeight())
								.reduce(BigDecimal::max).get();
					} catch (Exception e) {
						LOG.error(e);
					}

					if (pl.getDrawingPreference().getInFeets()) {
						minHeight = CDGAdditionalService.inchToFeet(minHeight);
						maxHeight = CDGAdditionalService.inchToFeet(maxHeight);
						expectedMinHeight = CDGAdditionalService.meterToFoot(expectedMinHeight);
						expectedMaxheight = CDGAdditionalService.meterToFoot(expectedMaxheight);
					}

					Map<String, String> details = new HashMap<>();
					details.put(RULE_NO, CDGAdditionalService.getByLaws(pl, CDGAConstant.PARAPET));
					details.put(DESCRIPTION, PARAPET_DESCRIPTION);

					if (minHeight.compareTo(expectedMinHeight) >= 0 && maxHeight.compareTo(expectedMaxheight) <= 0) {
						details.put(REQUIRED, "Height >= " + CDGAdditionalService.viewLenght(pl, expectedMinHeight)
								+ " and height <= " + CDGAdditionalService.viewLenght(pl, expectedMaxheight));
						// details.put(PROVIDED, "Height >= " + minHeight + " and height <= " +
						// maxHeight);
						details.put(PROVIDED, "Minimum height = " + CDGAdditionalService.viewLenght(pl, minHeight)
								+ " and Maximum height = " + CDGAdditionalService.viewLenght(pl, maxHeight));
						details.put(STATUS, Result.Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
						pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

					} else {
						details.put(REQUIRED, "Height >= " + CDGAdditionalService.viewLenght(pl, expectedMinHeight)
								+ " and height <= " + CDGAdditionalService.viewLenght(pl, expectedMaxheight));
						details.put(PROVIDED, "Height >= " + minHeight + " and height <= " + maxHeight);
						details.put(PROVIDED, "Minimum height = " + CDGAdditionalService.viewLenght(pl, minHeight)
								+ " and Maximum height = " + CDGAdditionalService.viewLenght(pl, maxHeight));
						details.put(STATUS, Result.Not_Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
						pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
					}

				}

			}
		}

	}

	private void validateServiceZoneParapet(Plan pl) {
		OccupancyTypeHelper mostRestrictiveOccupancy = pl.getVirtualBuilding() != null
				? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;

		if (mostRestrictiveOccupancy != null && mostRestrictiveOccupancy.getSubtype() != null
				&& !isOccupancyTypeNotApplicable(mostRestrictiveOccupancy)) {
			for (Block block : pl.getBlocks()) {
				ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
				scrutinyDetail.addColumnHeading(1, RULE_NO);
				scrutinyDetail.addColumnHeading(2, DESCRIPTION);
				scrutinyDetail.addColumnHeading(3, REQUIRED);
				scrutinyDetail.addColumnHeading(4, PROVIDED);
				scrutinyDetail.addColumnHeading(5, STATUS);
				scrutinyDetail.setKey("Block_" + block.getNumber() + "_" + "Service Zone Parapet");

				if (!pl.isRural() && isRequiredToValidateServiceZoneParapet(block)) {// urban
					BigDecimal minHeight = BigDecimal.ZERO;
					BigDecimal maxHeight = BigDecimal.ZERO;
					BigDecimal expectedMaxheight = new BigDecimal("0.1016");//4" -> 0.1016

					try {
						minHeight = block.getParapetWithColor().stream()
								.filter(hm -> hm.getColorCode() == SERVICE_ZONE_PARAPET).map(n -> n.getHeight())
								.reduce(BigDecimal::min).get();
						maxHeight = block.getParapetWithColor().stream()
								.filter(hm -> hm.getColorCode() == SERVICE_ZONE_PARAPET).map(n -> n.getHeight())
								.reduce(BigDecimal::max).get();
					} catch (Exception e) {
						LOG.error(e);
					}

					if (pl.getDrawingPreference().getInFeets()) {
						minHeight = CDGAdditionalService.inchToFeet(minHeight);
						maxHeight = CDGAdditionalService.inchToFeet(maxHeight);
						expectedMaxheight = CDGAdditionalService.meterToFoot(expectedMaxheight);
					}

					Map<String, String> details = new HashMap<>();
					details.put(RULE_NO, CDGAdditionalService.getByLaws(pl, CDGAConstant.PARAPET));
					details.put(DESCRIPTION, PARAPET_DESCRIPTION);

					if (maxHeight.compareTo(expectedMaxheight) <= 0) {
						details.put(REQUIRED, "Height <= " + CDGAdditionalService.viewLenght(pl, expectedMaxheight));
						details.put(PROVIDED, "Minimum height = " + CDGAdditionalService.viewLenght(pl, minHeight)
								+ " and Maximum height = " + CDGAdditionalService.viewLenght(pl, maxHeight));
						details.put(STATUS, Result.Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
						pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

					} else {
						details.put(REQUIRED, "Height <= " + CDGAdditionalService.viewLenght(pl, expectedMaxheight));
						details.put(PROVIDED, "Height >= " + minHeight + " and height <= " + maxHeight);
						details.put(PROVIDED, "Minimum height = " + CDGAdditionalService.viewLenght(pl, minHeight)
								+ " and Maximum height = " + CDGAdditionalService.viewLenght(pl, maxHeight));
						details.put(STATUS, Result.Not_Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
						pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
					}

				}

			}
		}

	}

	private boolean isRequiredToValidateStaircaseParapet(Block block) {
    	boolean flage=false;
    	for (Floor floor : block.getBuilding().getFloors()) {
			if(!floor.getGeneralStairs().isEmpty()) {
				flage=true;
				return flage;
			}
		}
    	return flage;
    }
	
	private boolean isRequiredToValidateServiceZoneParapet(Block block) {
    	boolean flage=false;
    	
    	List<Measurement> list=new ArrayList<Measurement>();
    	for(Measurement measurement:block.getParapetWithColor()) {
    		if(measurement.getColorCode()==SERVICE_ZONE_PARAPET)
    			list.add(measurement);
    	}
    	
    	if(!list.isEmpty())
    		flage=true;
    	
    	return flage;
    }
	
	public boolean isOccupancyTypeNotApplicable(OccupancyTypeHelper occupancyTypeHelper) {
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

	// CGCL end

	@Override
	public Map<String, Date> getAmendments() {
		return new LinkedHashMap<>();
	}

}
