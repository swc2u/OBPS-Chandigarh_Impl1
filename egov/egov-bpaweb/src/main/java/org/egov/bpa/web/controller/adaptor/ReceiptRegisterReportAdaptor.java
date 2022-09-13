package org.egov.bpa.web.controller.adaptor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import org.egov.bpa.transaction.entity.dto.ReceiptRegisterReportHelper;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.web.support.json.adapter.DataTableJsonAdapter;
import org.egov.infra.web.support.ui.DataTable;

import java.lang.reflect.Type;
import java.util.List;

import static org.egov.infra.utils.StringUtils.defaultIfBlank;

public class ReceiptRegisterReportAdaptor implements DataTableJsonAdapter<ReceiptRegisterReportHelper> {
	protected static final String N_A = "N/A";

	@Override
	public JsonElement serialize(DataTable<ReceiptRegisterReportHelper> baseRegisterResponse, final Type type,
            final JsonSerializationContext jsc) {
		final List<ReceiptRegisterReportHelper> baseRegisterResult = baseRegisterResponse.getData();
        final JsonArray baseRegisterResultData = new JsonArray();
        baseRegisterResult.forEach(baseForm -> {
            final JsonObject baseRegisterJson = new JsonObject();
            baseRegisterJson.addProperty("applicationNumber", defaultIfBlank(baseForm.getApplicationNumber()));
            baseRegisterJson.addProperty("receiptNumber", baseForm.getReceiptNumber());
            baseRegisterJson.addProperty("paymentDate", DateUtils.toDefaultDateFormat(baseForm.getPaymentDate()));
            baseRegisterJson.addProperty("fileNumber", defaultIfBlank(baseForm.getFileNumber()));
            baseRegisterJson.addProperty("securityFee", baseForm.getSecurityFee());
            baseRegisterJson.addProperty("scrutinyFee", baseForm.getScrutinyFee());
            baseRegisterJson.addProperty("gst", baseForm.getGst());
            baseRegisterJson.addProperty("additionFee", baseForm.getAdditionFee());
            baseRegisterJson.addProperty("labourCess", baseForm.getLabourCess());
            baseRegisterJson.addProperty("rule5", baseForm.getRule5());
            baseRegisterJson.addProperty("total", baseForm.getTotal());
            baseRegisterJson.addProperty("totalWithoutLabourCess", baseForm.getTotalWithoutLaboutCess());
            baseRegisterJson.addProperty("sector", baseForm.getSector());
            baseRegisterJson.addProperty("plotNumber", baseForm.getPlotNumber());

            baseRegisterResultData.add(baseRegisterJson);
        });
        return enhance(baseRegisterResultData, baseRegisterResponse);
	}
}
