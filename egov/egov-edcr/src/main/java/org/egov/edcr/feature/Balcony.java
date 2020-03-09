package org.egov.edcr.feature;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.service.cdg.CDGAConstant;
import org.egov.edcr.service.cdg.CDGAdditionalService;
import org.egov.edcr.utility.DcrConstants;
import org.egov.edcr.utility.Util;
import org.springframework.stereotype.Service;

@Service
public class Balcony extends FeatureProcess {
	private static final Logger LOG = Logger.getLogger(Balcony.class);
	private static final String FLOOR = "Floor";
	private static final String RULE45_IV = "45-iv";
	private static final String WIDTH_BALCONY_DESCRIPTION = "Minimum width for balcony %s";
	private static final BigDecimal ONE_POINTTWO = BigDecimal.valueOf(1.2);

	private static final BigDecimal ONE_POINTEIGHT = BigDecimal.valueOf(1.8);
	private static final BigDecimal POINTNINE = BigDecimal.valueOf(0.9);

	@Override
	public Plan validate(Plan planDetail) {
		return planDetail;
	}

	@Override
	public Plan process(Plan planDetail) {

		boolean flage1 = true;

		if (flage1) {
			return planDetail;
		}

		for (Block block : planDetail.getBlocks()) {
			if (block.getBuilding() != null) {

				ScrutinyDetail scrutinyDetailLanding = new ScrutinyDetail();
				scrutinyDetailLanding.addColumnHeading(1, RULE_NO);
				scrutinyDetailLanding.addColumnHeading(2, FLOOR);
				scrutinyDetailLanding.addColumnHeading(3, DESCRIPTION);
				scrutinyDetailLanding.addColumnHeading(4, PERMISSIBLE);
				scrutinyDetailLanding.addColumnHeading(5, PROVIDED);
				scrutinyDetailLanding.addColumnHeading(6, STATUS);
				scrutinyDetailLanding.setKey("Block_" + block.getNumber() + "_" + "Balcony");
				List<Floor> floors = block.getBuilding().getFloors();

				String plotType = planDetail.getPlanInfoProperties().get(DxfFileConstants.PLOT_TYPE);
				OccupancyTypeHelper mostRestrictiveFarHelper = planDetail.getVirtualBuilding() != null
						? planDetail.getVirtualBuilding().getMostRestrictiveFarHelper()
						: null;

				for (Floor floor : floors) {
					boolean isTypicalRepititiveFloor = false;

					Map<String, Object> typicalFloorValues = Util.getTypicalFloorValues(block, floor,
							isTypicalRepititiveFloor);

					List<org.egov.common.entity.edcr.Balcony> balconies = floor.getBalconies();
					if (!balconies.isEmpty()) {
						for (org.egov.common.entity.edcr.Balcony balcony : balconies) {
							boolean isAccepted = false;
							List<BigDecimal> widths = balcony.getWidths();
							BigDecimal minWidth = widths.stream().reduce(BigDecimal::min).get();

							BigDecimal maxWidth = widths.stream().reduce(BigDecimal::max).get();

							minWidth = minWidth.setScale(DcrConstants.DECIMALDIGITS_MEASUREMENTS,
									DcrConstants.ROUNDMODE_MEASUREMENTS);

							minWidth = minWidth.setScale(DcrConstants.DECIMALDIGITS_MEASUREMENTS,
									DcrConstants.ROUNDMODE_MEASUREMENTS);

							if (getOccupancyTypeApplicable(mostRestrictiveFarHelper, plotType)) {

								if (DxfFileConstants.MARLA.equals(plotType) && DxfFileConstants.A_P
										.equals(mostRestrictiveFarHelper.getSubtype().getCode())) {

									if (maxWidth.compareTo(POINTNINE.setScale(DcrConstants.DECIMALDIGITS_MEASUREMENTS,
											DcrConstants.ROUNDMODE_MEASUREMENTS)) >= 0) {
										isAccepted = true;
									}
								} else if (maxWidth
										.compareTo(ONE_POINTEIGHT.setScale(DcrConstants.DECIMALDIGITS_MEASUREMENTS,
												DcrConstants.ROUNDMODE_MEASUREMENTS)) >= 0) {
									isAccepted = true;
								}

								String value = typicalFloorValues.get("typicalFloors") != null
										? (String) typicalFloorValues.get("typicalFloors")
										: " floor " + floor.getNumber();

							String bayLaws=CDGAdditionalService.getByLaws(mostRestrictiveFarHelper, CDGAConstant.BALCONY);			
										
								if (isAccepted) {
									if (DxfFileConstants.MARLA.equals(plotType) && DxfFileConstants.A_P
											.equals(mostRestrictiveFarHelper.getSubtype().getCode())) {
										setReportOutputDetailsFloorBalconyWise(planDetail, bayLaws, value,
												String.format(WIDTH_BALCONY_DESCRIPTION, balcony.getNumber()),
												POINTNINE.toString(), String.valueOf(maxWidth),
												Result.Accepted.getResultVal(), scrutinyDetailLanding);

									} else {
										setReportOutputDetailsFloorBalconyWise(planDetail, bayLaws, value,
												String.format(WIDTH_BALCONY_DESCRIPTION, balcony.getNumber()),
												ONE_POINTEIGHT.toString(), String.valueOf(maxWidth),
												Result.Accepted.getResultVal(), scrutinyDetailLanding);
									}

								} else {
									if (DxfFileConstants.MARLA.equals(plotType) && DxfFileConstants.A_P
											.equals(mostRestrictiveFarHelper.getSubtype().getCode())) {
										setReportOutputDetailsFloorBalconyWise(planDetail, bayLaws, value,
												String.format(WIDTH_BALCONY_DESCRIPTION, balcony.getNumber()),
												POINTNINE.toString(), String.valueOf(maxWidth),
												Result.Not_Accepted.getResultVal(), scrutinyDetailLanding);

									} else {
										setReportOutputDetailsFloorBalconyWise(planDetail, bayLaws, value,
												String.format(WIDTH_BALCONY_DESCRIPTION, balcony.getNumber()),
												ONE_POINTEIGHT.toString(), String.valueOf(maxWidth),
												Result.Not_Accepted.getResultVal(), scrutinyDetailLanding);
									}
								}

							}

						}
					}

				}

			}
		}

		return planDetail;
	}

	private boolean getOccupancyTypeApplicable(OccupancyTypeHelper occupancyTypeHelper, String plotType) {

		if (DxfFileConstants.MARLA.equals(plotType)) {
			if (DxfFileConstants.A_P.equals(occupancyTypeHelper.getSubtype().getCode()))
				return true;
		} else if (DxfFileConstants.ONE_KANAL.equals(plotType)) {
			if (DxfFileConstants.A_P.equals(occupancyTypeHelper.getSubtype().getCode()))
				return true;
		} else if (DxfFileConstants.TWO_KANAL.equals(plotType)) {
			if (DxfFileConstants.A_P.equals(occupancyTypeHelper.getSubtype().getCode()))
				return true;

		} else if (DxfFileConstants.ABOVE_TWO_KANAL.contentEquals(plotType)) {
			if (DxfFileConstants.A_P.equals(occupancyTypeHelper.getSubtype().getCode()))
				return true;
		} else {
			if (DxfFileConstants.A_G.equals(occupancyTypeHelper.getSubtype().getCode())
					|| DxfFileConstants.F_H.equals(occupancyTypeHelper.getSubtype().getCode())
					|| DxfFileConstants.F_M.equals(occupancyTypeHelper.getSubtype().getCode())
					|| DxfFileConstants.F_CFI.equals(occupancyTypeHelper.getSubtype().getCode())
					|| DxfFileConstants.F_BH.equals(occupancyTypeHelper.getSubtype().getCode())
					|| DxfFileConstants.F_BBM.equals(occupancyTypeHelper.getSubtype().getCode())
					|| DxfFileConstants.F_B.equals(occupancyTypeHelper.getSubtype().getCode())
					|| DxfFileConstants.P.equals(occupancyTypeHelper.getType().getCode())
					|| DxfFileConstants.B.equals(occupancyTypeHelper.getSubtype().getCode())
					|| DxfFileConstants.IT.equals(occupancyTypeHelper.getSubtype().getCode())
					|| DxfFileConstants.ITH.equals(occupancyTypeHelper.getSubtype().getCode())
					|| DxfFileConstants.IP.equals(occupancyTypeHelper.getSubtype().getCode())

			)
				return true;
		}

		return false;
	}

	// CGCL end according to 26jan

	private void setReportOutputDetailsFloorBalconyWise(Plan pl, String ruleNo, String floor, String description,
			String expected, String actual, String status, ScrutinyDetail scrutinyDetail) {
		Map<String, String> details = new HashMap<>();
		details.put(RULE_NO, ruleNo);
		details.put(FLOOR, floor);
		details.put(DESCRIPTION, description);
		details.put(PERMISSIBLE, expected);
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