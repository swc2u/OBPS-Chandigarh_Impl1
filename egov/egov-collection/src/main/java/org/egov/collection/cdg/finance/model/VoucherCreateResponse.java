package org.egov.collection.cdg.finance.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoucherCreateResponse {

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

	@JsonProperty("ResponseInfo")
	public ResponseInfo getResponseInfo() {
        return responseInfo;
    }

	@JsonProperty("ResponseInfo")
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