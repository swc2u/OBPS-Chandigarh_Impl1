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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Building;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.service.cdg.CDGAConstant;
import org.egov.edcr.service.cdg.CDGAdditionalService;
import org.springframework.stereotype.Service;

@Service
public class OverHangs extends FeatureProcess {

    private static final Logger LOG = Logger.getLogger(OverHangs.class);
    private static final String RULE_45 = "45";
    public static final String OVERHANGS_DESCRIPTION = "Minimum width of chajja";
    private static final String FLOOR = "Floor";

    @Override
    public Plan validate(Plan pl) {

        return pl;
    }

    @Override
    public Plan process(Plan pl) {
//    	boolean flage=true;
//    	if(flage) {
//    		return pl;
//    	}

        Map<String, String> details = new HashMap<>();
        details.put(RULE_NO, CDGAdditionalService.getByLaws(pl, CDGAConstant.CHHAJJA_OR_JAMBS));
        details.put(DESCRIPTION, OVERHANGS_DESCRIPTION);

        BigDecimal maxWidthExpected = BigDecimal.ZERO;
        BigDecimal minWidth=BigDecimal.ZERO;
        
        OccupancyTypeHelper occupancyTypeHelper=pl.getVirtualBuilding().getMostRestrictiveFarHelper();
        if(DxfFileConstants.A_P.equals(occupancyTypeHelper.getSubtype().getCode())) {
        	if(DxfFileConstants.MARLA.equals(pl.getPlanInfoProperties().get(DxfFileConstants.PLOT_TYPE))) {
        		maxWidthExpected=BigDecimal.valueOf(0.9);
        	}
        	else {
        		maxWidthExpected=BigDecimal.valueOf(1.8);
        	}
        }else {
    		maxWidthExpected=BigDecimal.valueOf(1.8);
        }
        

        for (Block b : pl.getBlocks()) {

            ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
            scrutinyDetail.setKey("Block_" + b.getNumber() + "_" + "Chajja");
            scrutinyDetail.addColumnHeading(1, RULE_NO);
            scrutinyDetail.addColumnHeading(2, FLOOR);
            scrutinyDetail.addColumnHeading(3, DESCRIPTION);
            scrutinyDetail.addColumnHeading(4, PERMISSIBLE);
            scrutinyDetail.addColumnHeading(5, PROVIDED);
            scrutinyDetail.addColumnHeading(6, STATUS);
            Building building = b.getBuilding();
            if (building != null) {
                for (Floor floor : building.getFloors()) {
                    if (floor.getOverHangs() != null && !floor.getOverHangs().isEmpty()) {
                        List<BigDecimal> widths = floor.getOverHangs().stream().map(overhang -> overhang.getWidth())
                                .collect(Collectors.toList());

                        minWidth = widths.stream().reduce(BigDecimal::min).get();
                        
                        minWidth=minWidth.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                        

                        if (minWidth.compareTo(maxWidthExpected) < 0) {
                            details.put(FLOOR, floor.getNumber().toString());
                            details.put(PERMISSIBLE, "<"+maxWidthExpected.floatValue()+DxfFileConstants.METER);
                            details.put(PROVIDED, minWidth.toString()+DxfFileConstants.METER);
                            details.put(STATUS, Result.Accepted.getResultVal());
                            scrutinyDetail.getDetail().add(details);
                            pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
                        } else {
                            details.put(FLOOR, floor.getNumber().toString());
                            details.put(PERMISSIBLE, "<"+maxWidthExpected.floatValue()+DxfFileConstants.METER);
                            details.put(PROVIDED, minWidth.toString()+DxfFileConstants.METER);
                            details.put(STATUS, Result.Not_Accepted.getResultVal());
                            scrutinyDetail.getDetail().add(details);
                            pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
                        }
                    }
                }
            }

        }
        return pl;
    }

    @Override
    public Map<String, Date> getAmendments() {
        return new LinkedHashMap<>();
    }

}
