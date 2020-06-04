package org.egov.collection.CDG.vocher;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.egov.infra.microservice.models.RequestInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoucherRequest {

    @NotNull
    @JsonProperty("tenantId")
    private String tenantId="ch.chandigarh";

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("vouchers")
    private List<Voucher> vouchers = new ArrayList<>(0);

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(final RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    public void setVouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }

}