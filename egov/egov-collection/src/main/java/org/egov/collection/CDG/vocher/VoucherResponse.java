package org.egov.collection.CDG.vocher;

import java.util.ArrayList;
import java.util.List;

import org.egov.egf.contract.model.PageContract;
import org.egov.infra.microservice.contract.ResponseInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoucherResponse {

    @JsonProperty("Vouchers")
    private List<Voucher> vouchers = new ArrayList<>(0);
    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo;
    private PageContract page;

   

    public List<Voucher> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<Voucher> vouchers) {
		this.vouchers = vouchers;
	}

	public ResponseInfo getResponseInfo() {
        return responseInfo;
    }

    public void setResponseInfo(final ResponseInfo responseInfo) {
        this.responseInfo = responseInfo;
    }

    public PageContract getPage() {
        return page;
    }

    public void setPage(final PageContract page) {
        this.page = page;
    }

}