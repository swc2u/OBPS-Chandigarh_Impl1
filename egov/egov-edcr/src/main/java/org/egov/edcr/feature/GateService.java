package org.egov.edcr.feature;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.springframework.stereotype.Service;
@Service
public class GateService  extends FeatureProcess {
	@Override
	    public Plan validate(Plan plan) {
	        return plan;
	    }

	    @Override
	    public Plan process(Plan pl) {
	    	
	    	ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
			scrutinyDetail.setKey("Common_Gate");
			scrutinyDetail.addColumnHeading(1, RULE_NO);
			scrutinyDetail.addColumnHeading(2, DESCRIPTION);
			scrutinyDetail.addColumnHeading(3, REQUIRED);
			scrutinyDetail.addColumnHeading(4, PROVIDED);
			scrutinyDetail.addColumnHeading(5, STATUS);
			Map<String, String> details = new HashMap<>();
			
			OccupancyTypeHelper mostRestrictiveOccupancyType = pl.getVirtualBuilding() != null
					? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
					: null;
			if(isGateNotApplicable(mostRestrictiveOccupancyType)) {
				
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
