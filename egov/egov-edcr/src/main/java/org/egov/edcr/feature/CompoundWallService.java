package org.egov.edcr.feature;

import static org.egov.edcr.utility.DcrConstants.OBJECTNOTDEFINED;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.lucene.search.spans.FilterSpans.AcceptStatus;
import org.egov.common.entity.edcr.Measurement;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.service.cdg.CDGAConstant;
import org.egov.edcr.service.cdg.CDGAdditionalService;
import org.springframework.stereotype.Service;

import freemarker.core.BugException;

@Service
public class CompoundWallService extends FeatureProcess {

	private static Logger LOG = Logger.getLogger(CompoundWallService.class);
	private static final String RULE_1 = "1";
	private static final String RULE_2 = "2";
	private static final String RULE_3 = "3";
	private static final BigDecimal ONE_POINT_ONETHREE = new BigDecimal("1.13");
	private static final BigDecimal ONE_POINT_EIGHT = new BigDecimal("1.8");
	private static final BigDecimal POINT_SIXNINE = new BigDecimal("0.69");
	private static final BigDecimal POINT_NINE = new BigDecimal("0.90");
	
	private static final BigDecimal POINT_SEVENFIVE = new BigDecimal("0.75");

	private static final String COMPOUNDWALL = "CompoundWall";
	private static final String FRONT_HEIGHT = "FrontHeight";
	private static final String REAR_HEIGHT = "RearHeight";
	private static final String RAILING_HEIGHT = "RailingHeight";

	private static final String WALL_HEIGHT_FRONT_DESCRIPTION = "Wall Front height";
	private static final String WALL_HEIGHT_REAR_DESCRIPTION = "Wall Rear height";
	private static final String WALL_RAILING_HIGHT_DESCRIPTION = "Wall Railing height";

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

		// write use color code LayerNameService.java or
		// plan.getSubFeatureColorCodesMaster

		HashMap<String, String> errors = new HashMap<>();
		
		OccupancyTypeHelper mostRestrictiveFarHelper = pl.getVirtualBuilding() != null
				? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;

		if(!isOccupancyNotApplicable(mostRestrictiveFarHelper)) {
			
			if(!isOccupancyOptional(mostRestrictiveFarHelper)) {
				if (pl.getCompoundWall() == null) {
					errors.put("compoundWallNotDefined",
							getLocaleMessage(OBJECTNOTDEFINED, " Compund wall not defined in plan"));
				} else {

					if (pl.getCompoundWall().getWallHeights().size() == 0) {
						errors.put("compoundWallWallHeightsNotDefined",
								getLocaleMessage(OBJECTNOTDEFINED, " Compund wall - WallHeight not defined in plan"));
					}
				}
			}

			if (errors.size() > 0) {
				pl.addErrors(errors);
				return pl;
			}

			if (pl.getCompoundWall() != null) {

				ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
				scrutinyDetail.setKey("Common_Compound Wall");
				scrutinyDetail.addColumnHeading(1, RULE_NO);
				scrutinyDetail.addColumnHeading(2, DESCRIPTION);
				scrutinyDetail.addColumnHeading(3, REQUIRED);
				scrutinyDetail.addColumnHeading(4, PROVIDED);
				scrutinyDetail.addColumnHeading(5, STATUS);

				processWallHeights(pl, scrutinyDetail);
				processRailingHeights(pl, scrutinyDetail);

			}
			
		}

		return pl;
	}

	private void processWallHeights(Plan pl, ScrutinyDetail scrutinyDetail) {

		Map<String, Integer> map = pl.getSubFeatureColorCodesMaster().get(COMPOUNDWALL);
		OccupancyTypeHelper mostRestrictiveOccupancyType = pl.getVirtualBuilding().getMostRestrictiveFarHelper();
		BigDecimal frontMinFrontHeight=BigDecimal.ZERO;
		BigDecimal frontMinRearHeight=BigDecimal.ZERO;
		 try {
			 frontMinFrontHeight = pl.getCompoundWall().getWallHeights().stream()
						.filter(hm -> hm.getColorCode() == map.get(FRONT_HEIGHT)).map(n -> n.getHeight())
						.reduce(BigDecimal::min).get();
				 frontMinRearHeight = pl.getCompoundWall().getWallHeights().stream()
						.filter(hm -> hm.getColorCode() == map.get(REAR_HEIGHT)).map(n -> n.getHeight()).reduce(BigDecimal::min)
						.get();
		 }catch (Exception e) {
			// TODO: handle exception
		}

		Map<String, String> details = new HashMap<>();

//		details.put(RULE_NO, RULE_1);
		details.put(RULE_NO, CDGAdditionalService.getByLaws(mostRestrictiveOccupancyType, CDGAConstant.COMPOUND_WALL_SERVICE));
		details.put(DESCRIPTION, WALL_HEIGHT_FRONT_DESCRIPTION);
		details.put(PROVIDED, frontMinFrontHeight.toString()+DxfFileConstants.METER);
		
		BigDecimal exceptedFrontMinFrontHeight=BigDecimal.ZERO;
		BigDecimal exceptedFrontMinRearHeight=BigDecimal.ZERO;
		
		OccupancyTypeHelper mostRestrictiveFarHelper = pl.getVirtualBuilding() != null
				? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;
		
		if(DxfFileConstants.A_P.equals(mostRestrictiveFarHelper.getSubtype().getCode())
				) {
			exceptedFrontMinFrontHeight=ONE_POINT_ONETHREE;
			exceptedFrontMinRearHeight=ONE_POINT_EIGHT;
		}
		
		else if(DxfFileConstants.A_G.equals(mostRestrictiveFarHelper.getSubtype().getCode())
		|| DxfFileConstants.F_H.equals(mostRestrictiveFarHelper.getSubtype().getCode())
		|| DxfFileConstants.F_M.equals(mostRestrictiveFarHelper.getSubtype().getCode())
		|| DxfFileConstants.F_CFI.equals(mostRestrictiveFarHelper.getSubtype().getCode())
		|| DxfFileConstants.F_BH.equals(mostRestrictiveFarHelper.getSubtype().getCode())
		|| DxfFileConstants.P_D.equals(mostRestrictiveFarHelper.getSubtype().getCode())
		|| DxfFileConstants.P_P.equals(mostRestrictiveFarHelper.getSubtype().getCode())
		|| DxfFileConstants.P_F.equals(mostRestrictiveFarHelper.getSubtype().getCode())
		|| DxfFileConstants.P_N.equals(mostRestrictiveFarHelper.getSubtype().getCode())
		|| DxfFileConstants.P_H.equals(mostRestrictiveFarHelper.getSubtype().getCode())
		|| DxfFileConstants.P_CC.equals(mostRestrictiveFarHelper.getSubtype().getCode())
		|| DxfFileConstants.P_SS.equals(mostRestrictiveFarHelper.getSubtype().getCode())
		|| DxfFileConstants.P_CNA.equals(mostRestrictiveFarHelper.getSubtype().getCode())
		|| DxfFileConstants.P_R.equals(mostRestrictiveFarHelper.getSubtype().getCode())
		|| DxfFileConstants.IT.equals(mostRestrictiveFarHelper.getType().getCode())
		|| DxfFileConstants.ITH.equals(mostRestrictiveFarHelper.getType().getCode())
		|| DxfFileConstants.IP.equals(mostRestrictiveFarHelper.getType().getCode())
				) {
			exceptedFrontMinFrontHeight=ONE_POINT_ONETHREE;
			exceptedFrontMinRearHeight=ONE_POINT_ONETHREE;
		}else {
			exceptedFrontMinFrontHeight=ONE_POINT_EIGHT;
			exceptedFrontMinRearHeight=ONE_POINT_EIGHT;
		}

		details.put(REQUIRED, "minimum height " + exceptedFrontMinFrontHeight + DxfFileConstants.METER);
		if (frontMinFrontHeight.compareTo(exceptedFrontMinFrontHeight) >= 0) {
			details.put(STATUS, Result.Accepted.getResultVal());
		} else {
			details.put(STATUS, Result.Not_Accepted.getResultVal());
		}
		scrutinyDetail.getDetail().add(details);
		pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

		Map<String, String> details2 = new HashMap<>();

//		details2.put(RULE_NO, RULE_2);
		details2.put(RULE_NO, CDGAdditionalService.getByLaws(mostRestrictiveOccupancyType, CDGAConstant.COMPOUND_WALL_SERVICE));
		details2.put(DESCRIPTION, WALL_HEIGHT_REAR_DESCRIPTION);
		details2.put(PROVIDED, frontMinRearHeight.toString()+DxfFileConstants.METER);

		details2.put(REQUIRED, "minimum height " + exceptedFrontMinRearHeight + DxfFileConstants.METER);
		if (frontMinRearHeight.compareTo(exceptedFrontMinRearHeight) >= 0) {
			details2.put(STATUS, Result.Accepted.getResultVal());
		} else {
			details2.put(STATUS, Result.Not_Accepted.getResultVal());
		}
		scrutinyDetail.getDetail().add(details2);
		pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

	}

	private void processRailingHeights(Plan pl, ScrutinyDetail scrutinyDetail) {

		Map<String, Integer> map = pl.getSubFeatureColorCodesMaster().get(COMPOUNDWALL);
		OccupancyTypeHelper mostRestrictiveOccupancyType = pl.getVirtualBuilding().getMostRestrictiveFarHelper();

		BigDecimal frontMinRailingHeight = pl.getCompoundWall().getRailingHeights().stream()
				.filter(hm -> hm.getColorCode() == map.get(RAILING_HEIGHT)).map(n -> n.getHeight())
				.reduce(BigDecimal::min).get();
		
		BigDecimal exceptedFrontMinRailingHeight=BigDecimal.ZERO;
		
		if(DxfFileConstants.B.equals(mostRestrictiveOccupancyType.getType().getCode())
				) {
			exceptedFrontMinRailingHeight=POINT_NINE;
		}else {
			exceptedFrontMinRailingHeight=POINT_SIXNINE;
		}

		Map<String, String> details = new HashMap<>();

//		details.put(RULE_NO, RULE_3);
		details.put(RULE_NO, CDGAdditionalService.getByLaws(mostRestrictiveOccupancyType, CDGAConstant.COMPOUND_WALL_SERVICE));
		details.put(DESCRIPTION, WALL_RAILING_HIGHT_DESCRIPTION);
		details.put(PROVIDED, frontMinRailingHeight.toString()+DxfFileConstants.METER);

		details.put(REQUIRED, "minimum height " + exceptedFrontMinRailingHeight + DxfFileConstants.METER);
		if (frontMinRailingHeight.compareTo(exceptedFrontMinRailingHeight) >= 0) {
			details.put(STATUS, Result.Accepted.getResultVal());
		} else {
			details.put(STATUS, Result.Not_Accepted.getResultVal());
		}
		scrutinyDetail.getDetail().add(details);
		pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

	}

	private boolean isOccupancyNotApplicable(OccupancyTypeHelper occupancyTypeHelper) {
		boolean flage = false;

		if (DxfFileConstants.F_SCO.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_B.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_TS.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_TCIM.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_PP.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_CD.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.G_GBAC.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.R1.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.T1.equals(occupancyTypeHelper.getSubtype().getCode()))
			flage = true;

		return flage;
	}
	
	private boolean isOccupancyOptional(OccupancyTypeHelper occupancyTypeHelper) {
		boolean flage = false;

		if (DxfFileConstants.F_H.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_M.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_CFI.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_BH.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.B.equals(occupancyTypeHelper.getType().getCode())
				|| DxfFileConstants.IT.equals(occupancyTypeHelper.getType().getCode())
				|| DxfFileConstants.ITH.equals(occupancyTypeHelper.getType().getCode())
				)
			flage = true;

		return flage;
	}

}
