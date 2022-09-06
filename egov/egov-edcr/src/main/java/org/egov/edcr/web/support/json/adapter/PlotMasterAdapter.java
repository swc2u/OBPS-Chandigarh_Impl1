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

package org.egov.edcr.web.support.json.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.egov.edcr.entity.PlotMaster;

import java.lang.reflect.Type;


public class PlotMasterAdapter implements JsonSerializer<PlotMaster> {

    @Override
    public JsonElement serialize(final PlotMaster plotMaster, final Type type, final JsonSerializationContext jsc) {
        JsonObject plotMasterJson = new JsonObject();
        plotMasterJson.addProperty("pmId", plotMaster.getId());
        plotMasterJson.addProperty("plotId", plotMaster.getAllowedsuboccupancy().getPlot().getId());
        plotMasterJson.addProperty("plotNum", plotMaster.getAllowedsuboccupancy().getPlot().getPlotNum());
        plotMasterJson.addProperty("code", plotMaster.getCode());
        plotMasterJson.addProperty("phase", plotMaster.getAllowedsuboccupancy().getPlot().getPhase());
        plotMasterJson.addProperty("sector", plotMaster.getAllowedsuboccupancy().getPlot().getBoundary().getName());
        plotMasterJson.addProperty("plotNumber", plotMaster.getAllowedsuboccupancy().getPlot().getPlotNum());
        plotMasterJson.addProperty("backCourtyardWidth", plotMaster.getBackCourtyardWidth());
        plotMasterJson.addProperty("backCourtyardHeight", plotMaster.getBackCourtyardHeight());
        plotMasterJson.addProperty("plotArea", plotMaster.getAllowedsuboccupancy().getPlot().getPlotArea());
        plotMasterJson.addProperty("areaType", plotMaster.getAllowedsuboccupancy().getPlot().getAreaType());
        plotMasterJson.addProperty("plotDepth", plotMaster.getAllowedsuboccupancy().getPlot().getPlotDepth());
        plotMasterJson.addProperty("plotWidth", plotMaster.getAllowedsuboccupancy().getPlot().getPlotWidth());
        plotMasterJson.addProperty("permissibleBuildingStories", plotMaster.getPermissibleBuildingStories());
        plotMasterJson.addProperty("permissibleBuildingHeight", plotMaster.getPermissibleBuildingHeight());
        plotMasterJson.addProperty("maxmimumPermissibleFAR", plotMaster.getMaxmimumPermissibleFAR());
        plotMasterJson.addProperty("minimumPermissibleSetback_Front", plotMaster.getMinimumPermissibleSetback_Front());
        plotMasterJson.addProperty("minimumPermissibleSetback_Rear", plotMaster.getMinimumPermissibleSetback_Rear());
        plotMasterJson.addProperty("minimumPermissibleSetback_Left", plotMaster.getMinimumPermissibleSetback_Left());
        plotMasterJson.addProperty("minimumPermissibleSetback_Left_depth", plotMaster.getMinimumPermissibleSetback_Left_depth());
        plotMasterJson.addProperty("minimumPermissibleSetback_Left_width", plotMaster.getMinimumPermissibleSetback_Left_width());
        plotMasterJson.addProperty("minimumPermissibleSetback_Right", plotMaster.getMinimumPermissibleSetback_Right());
        plotMasterJson.addProperty("minimumPermissibleSetback_Right_depth", plotMaster.getMinimumPermissibleSetback_Right_depth());
        plotMasterJson.addProperty("minimumPermissibleSetback_Right_width", plotMaster.getMinimumPermissibleSetback_Right_width());
       
        return plotMasterJson;
    }
}
