package org.egov.edcr.feature;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.Occupancy;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.service.cdg.CDGAdditionalService;
import org.springframework.stereotype.Service;

/*
 * This class contain only OC related feature validation 
 */
@Service
public class OCAdditionalFeature extends FeatureProcess{
	private static final Logger LOG = Logger.getLogger(AdditionalFeature2.class);
	
	@Override
	public Map<String, Date> getAmendments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Plan validate(Plan pl) {
		return null;
	}

	@Override
	public Plan process(Plan pl) {
		
		if(!DxfFileConstants.APPLICATION_TYPE_OCCUPANCY_CERTIFICATE.equals(pl.getApplicationType())) {
			minorInternalChanges(pl);
			excessCoverageBeyondZoning6Inch(pl);
			glazingOfVerandah(pl);
		}
			
		partitionsOnGroundFloorOnMulti_baysShops(pl);
		
		return pl;
	}
	
	public void minorInternalChanges(Plan pl) {//no validation , show buildupArea colorcode 3
		ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.setKey("Common_OC Minor Internal Changes");
		scrutinyDetail.addColumnHeading(1, BLOCK);
		scrutinyDetail.addColumnHeading(2, FLOOR);
		scrutinyDetail.addColumnHeading(3, PROVIDED);
		scrutinyDetail.addColumnHeading(4, STATUS);
		
		boolean isParsent = false;
		for (Block block : pl.getBlocks()) {
			for (Floor floor : block.getBuilding().getFloors()) {
				for (Occupancy occupancy : floor.getOccupancies()) {
					if (DxfFileConstants.A_R5.equals(occupancy.getTypeHelper().getSubtype().getCode())) {

						isParsent = true;
						Map<String, String> details = new HashMap<>();
						details.put(BLOCK, "block-" + block.getNumber());
						details.put(FLOOR, "floor-" + floor.getNumber());
						details.put(PROVIDED, CDGAdditionalService.viewArea(pl,
								CDGAdditionalService.inchtoFeetArea(occupancy.getBuiltUpArea())));
						details.put(STATUS, Result.Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
					}

				}
			}

		}
		
		
	}

	public void excessCoverageBeyondZoning6Inch(Plan plan) {//take from plan key // EXCESS_COVERAGE_6_INCH_BEYOND_BUILD_UP_AREA= number
		
	}
	
	public void glazingOfVerandah(Plan plan) {//Glazing of verandah color code 5
		
	}
	
	public void partitionsOnGroundFloorOnMulti_baysShops(Plan plan) {//Partitions on ground floor on multi-bays shops -> key MULTI_BAY_PARTITIONS_NUMBER= it's for both and optional
		
	}
}
