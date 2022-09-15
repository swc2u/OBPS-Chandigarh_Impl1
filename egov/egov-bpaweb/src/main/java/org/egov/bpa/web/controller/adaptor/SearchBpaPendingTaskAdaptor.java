package org.egov.bpa.web.controller.adaptor;

import static org.apache.commons.lang.StringUtils.defaultString;
import static org.egov.infra.utils.StringUtils.defaultIfBlank;

import java.lang.reflect.Type;
import java.util.List;

import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.entity.dto.SearchPendingItemsForm;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.web.support.json.adapter.DataTableJsonAdapter;
import org.egov.infra.web.support.ui.DataTable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public class SearchBpaPendingTaskAdaptor implements DataTableJsonAdapter<SearchPendingItemsForm>{

	@Override
	public JsonElement serialize(DataTable<SearchPendingItemsForm> baseRegisterResponse, final Type type,
            final JsonSerializationContext jsc) {
		final List<SearchPendingItemsForm> baseRegisterResult = baseRegisterResponse.getData();
        final JsonArray baseRegisterResultData = new JsonArray();
        baseRegisterResult.forEach(baseForm -> {
            final JsonObject baseRegisterJson = new JsonObject();
            baseRegisterJson.addProperty("applicantName", defaultIfBlank(baseForm.getApplicantName()));
            baseRegisterJson.addProperty("applicationNumber", baseForm.getApplicationNumber());
            baseRegisterJson.addProperty("applicationDate", defaultString(DateUtils.toDefaultDateFormat(baseForm.getApplicationDate()), "N/A"));
            baseRegisterJson.addProperty("serviceType", defaultIfBlank(baseForm.getServiceType()));
            baseRegisterJson.addProperty("occupancy", baseForm.getOccupancy());
            baseRegisterJson.addProperty("currentOwner", baseForm.getCurrentOwner());
            baseRegisterJson.addProperty("currentOwnerDesignation", baseForm.getCurrentOwnerDesg());
            baseRegisterJson.addProperty("pendingAction", baseForm.getPendingAction());
            baseRegisterJson.addProperty("status", baseForm.getStatus());
            baseRegisterJson.addProperty("applicationType", baseForm.getApplicationType());
            baseRegisterJson.addProperty("id", baseForm.getId());
            baseRegisterJson.addProperty("ellapseTime", baseForm.getEllapseTime());
            baseRegisterJson.addProperty("sector", baseForm.getSector());
            baseRegisterJson.addProperty("plotNumber", baseForm.getPlotNumber());

            baseRegisterResultData.add(baseRegisterJson);
        });
        return enhance(baseRegisterResultData, baseRegisterResponse);
	}
	
}
