package org.egov.bpa.web.controller.adaptor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import org.egov.bpa.transaction.entity.dto.CollectionSummaryReportHelper;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.web.support.json.adapter.DataTableJsonAdapter;
import org.egov.infra.web.support.ui.DataTable;

import java.lang.reflect.Type;
import java.util.List;

import static org.egov.infra.utils.StringUtils.defaultIfBlank;

public class CollectionSummaryReportAdaptor implements DataTableJsonAdapter<CollectionSummaryReportHelper> {
	protected static final String N_A = "N/A";

	@Override
	public JsonElement serialize(DataTable<CollectionSummaryReportHelper> baseCollectionResponse, final Type type,
            final JsonSerializationContext jsc) {
		final List<CollectionSummaryReportHelper> baseCollectionResult = baseCollectionResponse.getData();
        final JsonArray baseCollectionResultData = new JsonArray();
        baseCollectionResult.forEach(baseForm -> {
            final JsonObject baseCollectionJson = new JsonObject();
            baseCollectionJson.addProperty("source", defaultIfBlank(baseForm.getSource()));
            baseCollectionJson.addProperty("service", baseForm.getServiceName());
            baseCollectionJson.addProperty("cashReceipt", baseForm.getCashReceipt());
            baseCollectionJson.addProperty("cashAmount", baseForm.getCashAmount());
            baseCollectionJson.addProperty("chequeReceipt", baseForm.getChequeReceipt());
            baseCollectionJson.addProperty("chequeAmount", baseForm.getChequeAmount());
            baseCollectionJson.addProperty("cardReceipt", baseForm.getCardReceipt());
            baseCollectionJson.addProperty("cardAmount", baseForm.getCardAmount());
            baseCollectionJson.addProperty("onlineReceipt", baseForm.getOnlineReceipt());
            baseCollectionJson.addProperty("onlineAmount", baseForm.getOnlineAmount());
            baseCollectionJson.addProperty("bankReceipt", baseForm.getBankReceipt());
            baseCollectionJson.addProperty("bankAmount", baseForm.getBankAmount());
            baseCollectionJson.addProperty("totalReceipt", baseForm.getTotalReceipt());
            baseCollectionJson.addProperty("totalAmount", baseForm.getTotalAmount());
            baseCollectionJson.addProperty("fromDate", DateUtils.toDefaultDateFormat(baseForm.getFromDate()));
            baseCollectionJson.addProperty("toDate", DateUtils.toDefaultDateFormat(baseForm.getToDate()));
            

            baseCollectionResultData.add(baseCollectionJson);
        });
        return enhance(baseCollectionResultData, baseCollectionResponse);
	}
}
