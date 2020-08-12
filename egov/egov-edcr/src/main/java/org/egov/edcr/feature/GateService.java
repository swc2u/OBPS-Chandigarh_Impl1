package org.egov.edcr.feature;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.egov.common.entity.edcr.Measurement;
import org.egov.common.entity.edcr.Occupancy;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.service.cdg.CDGAConstant;
import org.egov.edcr.service.cdg.CDGAdditionalService;
import org.springframework.stereotype.Service;
@Service
public class GateService  extends FeatureProcess {
	public static int COLOR_CODE_MAINGATE=1;
	public static int COLOR_CODE_WICKET_GATE=2;
	public static String MAINGATE_LAYER_NAME="MAIN_GATE";
	public static String WICKET_GATE_LAYER_NAME="WICKET_GATE";
	@Override
	    public Plan validate(Plan plan) {
	        return plan;
	    }

	    @Override
	    public Plan process(Plan pl) {
	    	
	    	List<Measurement> mainGate = new ArrayList<Measurement>();
			List<Measurement> wicketGate = new ArrayList<Measurement>();
	    	
	    	ScrutinyDetail scrutinyDetail = new ScrutinyDetail();// 1 - main gate 2 wckt ate
			scrutinyDetail.setKey("Common_Gate");
			scrutinyDetail.addColumnHeading(1, RULE_NO);
			scrutinyDetail.addColumnHeading(2, DESCRIPTION);
			scrutinyDetail.addColumnHeading(3, REQUIRED);
			scrutinyDetail.addColumnHeading(4, PROVIDED);
			scrutinyDetail.addColumnHeading(5, STATUS);
			OccupancyTypeHelper mostRestrictiveOccupancyType = pl.getVirtualBuilding() != null
					? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
					: null;
			BigDecimal expectedHeighMainGate=BigDecimal.ZERO;
			BigDecimal expectedHeightWicketGate=BigDecimal.ZERO;
			BigDecimal expectedWidthMainGate=BigDecimal.ZERO;
			BigDecimal expectedWidthWicketGate=BigDecimal.ZERO;
			
			if(DxfFileConstants.A_P.equals(mostRestrictiveOccupancyType.getSubtype().getCode())) {
				expectedHeighMainGate=new BigDecimal("1.8");
				expectedWidthMainGate=new BigDecimal("4.88");
				expectedHeightWicketGate=new BigDecimal("1.80");
				expectedWidthWicketGate=new BigDecimal("1.13");
			}else if(DxfFileConstants.A_G.equals(mostRestrictiveOccupancyType.getSubtype().getCode())) {
				expectedHeighMainGate=new BigDecimal("1.8");
				expectedWidthMainGate=new BigDecimal("4.88");
				expectedHeightWicketGate=new BigDecimal("1.80");
				expectedWidthWicketGate=new BigDecimal("1.15");
			}else if(DxfFileConstants.F_H.equals(mostRestrictiveOccupancyType.getSubtype().getCode())
					|| DxfFileConstants.F_M.equals(mostRestrictiveOccupancyType.getSubtype().getCode())
					|| DxfFileConstants.F_CFI.equals(mostRestrictiveOccupancyType.getSubtype().getCode())
					|| DxfFileConstants.F_BH.equals(mostRestrictiveOccupancyType.getSubtype().getCode())
					|| DxfFileConstants.F_BBM.equals(mostRestrictiveOccupancyType.getSubtype().getCode())
					|| DxfFileConstants.G_GBZP.equals(mostRestrictiveOccupancyType.getSubtype().getCode())
					|| DxfFileConstants.P.equals(mostRestrictiveOccupancyType.getType().getCode())
					|| DxfFileConstants.B.equals(mostRestrictiveOccupancyType.getType().getCode())
					|| DxfFileConstants.IT.equals(mostRestrictiveOccupancyType.getType().getCode())
					|| DxfFileConstants.ITH.equals(mostRestrictiveOccupancyType.getType().getCode())
					|| DxfFileConstants.IP.equals(mostRestrictiveOccupancyType.getType().getCode())
					) {
				expectedHeighMainGate=new BigDecimal("1.8");
				expectedWidthMainGate=new BigDecimal("4.88");
				expectedHeightWicketGate=new BigDecimal("0");//it's optional
				expectedWidthWicketGate=new BigDecimal("0");//it's optional
			}
			
			
			if(pl.getDrawingPreference().getInFeets()) {
				expectedHeighMainGate=CDGAdditionalService.meterToFoot(expectedHeighMainGate);
				expectedHeightWicketGate=CDGAdditionalService.meterToFoot(expectedHeightWicketGate);
				expectedWidthMainGate=CDGAdditionalService.meterToFoot(expectedWidthMainGate);
				expectedWidthWicketGate=CDGAdditionalService.meterToFoot(expectedWidthWicketGate);
			}
			
			
			
			if(!isGateNotApplicable(mostRestrictiveOccupancyType)) {
				
				if(pl.getGate()!=null) {
					
					for(Measurement measurement:pl.getGate().getGates()) {
						if(MAINGATE_LAYER_NAME.equals(measurement.getName()))
							mainGate.add(measurement);
						if(WICKET_GATE_LAYER_NAME.equals(measurement.getName()))
							wicketGate.add(measurement);
					}
				}
				
				if(!DxfFileConstants.MARLA.equals(pl.getPlanInfoProperties().get(DxfFileConstants.PLOT_TYPE)) && mainGate.isEmpty())
					pl.addError("mainGate", "Main gate not defined");
					
				if(!mainGate.isEmpty()) {
					int i=1;
					for(Measurement measurement:mainGate) {
						Map<String, String> details = new HashMap<>();
						BigDecimal providedHeightMainGate=measurement.getHeight();
						BigDecimal providedWidthMainGate=measurement.getWidth();
						
						if(pl.getDrawingPreference().getInFeets()) {
							providedHeightMainGate=CDGAdditionalService.inchToFeet(providedHeightMainGate);
							providedWidthMainGate=CDGAdditionalService.inchToFeet(providedWidthMainGate);
						}
						
						details.put(RULE_NO, CDGAdditionalService.getByLaws(mostRestrictiveOccupancyType,CDGAConstant.GATE));
						details.put(DESCRIPTION, "MainGate"+ i);
						details.put(REQUIRED, "height <="+CDGAdditionalService.viewLenght(pl, expectedHeighMainGate)+" and width <="+CDGAdditionalService.viewLenght(pl, expectedWidthMainGate));
						details.put(PROVIDED, "height = "+CDGAdditionalService.viewLenght(pl, providedHeightMainGate)+" and width = "+CDGAdditionalService.viewLenght(pl, providedWidthMainGate));
						
						if(providedHeightMainGate.compareTo(expectedHeighMainGate)<=0 && providedWidthMainGate.compareTo(expectedWidthMainGate)<=0)
							details.put(STATUS, Result.Accepted.getResultVal());
						else
							details.put(STATUS, Result.Not_Accepted.getResultVal());
						
						scrutinyDetail.addDetail(details);
						i++;
					}
					
				}
				
				
				if(!wicketGate.isEmpty()) {
					int i=1;
					for(Measurement measurement:wicketGate) {
						Map<String, String> details = new HashMap<>();
						BigDecimal providedHeightWicketGate=measurement.getHeight();
						BigDecimal providedWidthWicketGate=measurement.getWidth();
						
						if(pl.getDrawingPreference().getInFeets()) {
							providedHeightWicketGate=CDGAdditionalService.inchToFeet(providedHeightWicketGate);
							providedWidthWicketGate=CDGAdditionalService.inchToFeet(providedWidthWicketGate);
						}
						
						details.put(RULE_NO, CDGAdditionalService.getByLaws(mostRestrictiveOccupancyType,CDGAConstant.GATE));
						details.put(DESCRIPTION, "Wicket gate"+ i);
						details.put(REQUIRED, "height <="+CDGAdditionalService.viewLenght(pl, expectedHeightWicketGate)+" and width <="+CDGAdditionalService.viewLenght(pl, expectedWidthWicketGate));
						details.put(PROVIDED, "height = "+CDGAdditionalService.viewLenght(pl, providedHeightWicketGate)+" and width = "+CDGAdditionalService.viewLenght(pl, providedWidthWicketGate));
						
						if(providedHeightWicketGate.compareTo(expectedHeightWicketGate)<=0 && providedWidthWicketGate.compareTo(expectedWidthWicketGate)<=0)
							details.put(STATUS, Result.Accepted.getResultVal());
						else
							details.put(STATUS, Result.Not_Accepted.getResultVal());
						
						scrutinyDetail.addDetail(details);
						i++;
					}
					
				}
				
				pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
			}
			
	    	
	    	return pl;
	    }
	    
	    
	    private boolean isGateNotApplicable(OccupancyTypeHelper occupancyTypeHelper) {
			boolean flage = false;

			if (DxfFileConstants.F_SCO.equals(occupancyTypeHelper.getSubtype().getCode())
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
	    
	    
	    @Override
	    public Map<String, Date> getAmendments() {
	        return new LinkedHashMap<>();
	    }
}
