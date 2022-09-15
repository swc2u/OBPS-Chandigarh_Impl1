package org.egov.bpa.transaction.entity.dto;


import java.math.BigDecimal;
import java.util.Date;

import org.egov.infra.web.support.search.DataTableSearchRequest;

public class CollectionSummaryHeadwiseReportHelper extends DataTableSearchRequest {
    private Long id;
   private String source;
   private String glCode;
   private String cashReceipt;
   private BigDecimal cashAmount;
   private String chequeReceipt;
   private BigDecimal chequeAmount;
   private String onlineReceipt;
   private BigDecimal onlineAmount;
   private String cardReceipt;
   private BigDecimal cardAmount;
   private String totalReceipt;
   private BigDecimal totalAmount;
   private Date fromDate;
   private Date toDate;
   private String paymentMode;
   private String applicationType;
   
   private BigDecimal totalCashRebateAmount = BigDecimal.ZERO;
   private BigDecimal totalChequeddRebateAmount = BigDecimal.ZERO;
   private BigDecimal totalOnlineRebateAmount = BigDecimal.ZERO;
   private BigDecimal totalCardRebateAmount = BigDecimal.ZERO;
   private BigDecimal totalRebateAmount = BigDecimal.ZERO;
   
   

   public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getSource() {
	return source;
}
public void setSource(String source) {
	this.source = source;
}
public String getGlCode() {
	return glCode;
}
public void setGlCode(String glCode) {
	this.glCode = glCode;
}

public BigDecimal getTotalAmount() {
	return totalAmount;
}
public void setTotalAmount(BigDecimal totalAmount) {
	this.totalAmount = totalAmount;
}
public String getCashReceipt() {
	return cashReceipt;
}
public void setCashReceipt(String cashReceipt) {
	this.cashReceipt = cashReceipt;
}
public BigDecimal getCashAmount() {
	return cashAmount;
}
public void setCashAmount(BigDecimal cashAmount) {
	this.cashAmount = cashAmount;
}
public String getChequeReceipt() {
	return chequeReceipt;
}
public void setChequeReceipt(String chequeReceipt) {
	this.chequeReceipt = chequeReceipt;
}
public BigDecimal getChequeAmount() {
	return chequeAmount;
}
public void setChequeAmount(BigDecimal chequeAmount) {
	this.chequeAmount = chequeAmount;
}
public String getOnlineReceipt() {
	return onlineReceipt;
}
public void setOnlineReceipt(String onlineReceipt) {
	this.onlineReceipt = onlineReceipt;
}
public BigDecimal getOnlineAmount() {
	return onlineAmount;
}
public void setOnlineAmount(BigDecimal onlineAmount) {
	this.onlineAmount = onlineAmount;
}
public String getCardReceipt() {
	return cardReceipt;
}
public void setCardReceipt(String cardReceipt) {
	this.cardReceipt = cardReceipt;
}
public BigDecimal getCardAmount() {
	return cardAmount;
}
public void setCardAmount(BigDecimal cardAmount) {
	this.cardAmount = cardAmount;
}
public String getTotalReceipt() {
	return totalReceipt;
}
public void setTotalReceipt(String totalReceipt) {
	this.totalReceipt = totalReceipt;
}
public Date getFromDate() {
	return fromDate;
}
public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
}
public Date getToDate() {
	return toDate;
}
public void setToDate(Date toDate) {
	this.toDate = toDate;
}
public String getPaymentMode() {
	return paymentMode;
}
public void setPaymentMode(String paymentMode) {
	this.paymentMode = paymentMode;
}
public String getApplicationType() {
	return applicationType;
}
public void setApplicationType(String applicationType) {
	this.applicationType = applicationType;
}
public BigDecimal getTotalCashRebateAmount() {
	return totalCashRebateAmount;
}
public void setTotalCashRebateAmount(BigDecimal totalCashRebateAmount) {
	this.totalCashRebateAmount = totalCashRebateAmount;
}
public BigDecimal getTotalChequeddRebateAmount() {
	return totalChequeddRebateAmount;
}
public void setTotalChequeddRebateAmount(BigDecimal totalChequeddRebateAmount) {
	this.totalChequeddRebateAmount = totalChequeddRebateAmount;
}
public BigDecimal getTotalOnlineRebateAmount() {
	return totalOnlineRebateAmount;
}
public void setTotalOnlineRebateAmount(BigDecimal totalOnlineRebateAmount) {
	this.totalOnlineRebateAmount = totalOnlineRebateAmount;
}
public BigDecimal getTotalCardRebateAmount() {
	return totalCardRebateAmount;
}
public void setTotalCardRebateAmount(BigDecimal totalCardRebateAmount) {
	this.totalCardRebateAmount = totalCardRebateAmount;
}
public BigDecimal getTotalRebateAmount() {
	return totalRebateAmount;
}
public void setTotalRebateAmount(BigDecimal totalRebateAmount) {
	this.totalRebateAmount = totalRebateAmount;
}
   
   

}
