package org.egov.bpa.transaction.entity.dto;


import java.math.BigDecimal;
import java.util.Date;

import org.egov.infra.web.support.search.DataTableSearchRequest;

public class CollectionSummaryReportHelper extends DataTableSearchRequest {
    private Long id;
   private String source;
   private String serviceName;
   private String cashReceipt;
   private BigDecimal cashAmount;
   private String chequeReceipt;
   private BigDecimal chequeAmount;
   private String onlineReceipt;
   private BigDecimal onlineAmount;
   private String bankReceipt;
   private BigDecimal bankAmount;
   private String cardReceipt;
   private BigDecimal cardAmount;
   private String totalReceipt;
   private BigDecimal totalAmount;
   private Date fromDate;
   private Date toDate;
   private String paymentMode;
   

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
public String getServiceName() {
	return serviceName;
}
public void setServiceName(String serviceName) {
	this.serviceName = serviceName;
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
public String getBankReceipt() {
	return bankReceipt;
}
public void setBankReceipt(String bankReceipt) {
	this.bankReceipt = bankReceipt;
}
public BigDecimal getBankAmount() {
	return bankAmount;
}
public void setBankAmount(BigDecimal bankAmount) {
	this.bankAmount = bankAmount;
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
   
   

}
