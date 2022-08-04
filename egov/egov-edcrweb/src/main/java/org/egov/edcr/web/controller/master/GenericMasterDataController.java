/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 *
 */

package org.egov.edcr.web.controller.master;

import org.apache.commons.io.IOUtils;
import org.egov.common.entity.bpa.Occupancy;
import org.egov.common.entity.bpa.SubOccupancy;
import org.egov.commons.service.OccupancyService;
import org.egov.commons.service.SubOccupancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Controller
public class GenericMasterDataController {

    private static final String DISPLAY_KEY = "Text";
    private static final String VALUE_KEY = "Value";
    
    @Autowired
    OccupancyService occupancyService;
    
    @Autowired
    SubOccupancyService subOccupancyService;
    
    @GetMapping("/suboccupancy/ajax/suboccupancylist-for-occupancy")
    @ResponseBody
    public void getBoundaryTypeByHierarchyType(@RequestParam Long occupancyId, HttpServletResponse response)
            throws IOException {
    	Occupancy occupancy = occupancyService.findById(occupancyId);
        final List<SubOccupancy> subOccupancy = subOccupancyService.findSubOccupanciesByOccupancy(occupancy.getName());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        IOUtils.write(buildJSONString(subOccupancy), response.getWriter());
    }
    
    
    
    private String buildJSONString(List<SubOccupancy> subOccupancies) {
        final JsonArray jsonArray = new JsonArray();
        for (final SubOccupancy subOccupancy : subOccupancies) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(DISPLAY_KEY, subOccupancy.getName());
            jsonObject.addProperty(VALUE_KEY, subOccupancy.getId());
            jsonArray.add(jsonObject);
        }

        return jsonArray.toString();
    }
    
}
