package org.egov.collection.cdg.finance.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Voucher {
	
	private Long id;
	
	private String name;
	
    private String type;
	
    private String voucherNumber;
    
    private String description;
    
    private Date voucherDate;
    
    private Fund fund;
    
    private Function function;
    
    private String fiscalPeriod;
    
    private String status;
    
    private String originalVhId;
    
    private String refVhId;
    
    private String cgvn;
    
    private Long moduleId;
    
    private String department;
    
    private String source;
    
    private Scheme scheme;
    
    private String subScheme;
    
    private Functionary functionary;
    
    private String fundsource;
    
    private List<Ledgers> ledgers;
    
    private String tenantId;
    
    private String referenceDocument;
    
    private String serviceName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public String getFiscalPeriod() {
		return fiscalPeriod;
	}

	public void setFiscalPeriod(String fiscalPeriod) {
		this.fiscalPeriod = fiscalPeriod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOriginalVhId() {
		return originalVhId;
	}

	public void setOriginalVhId(String originalVhId) {
		this.originalVhId = originalVhId;
	}

	public String getRefVhId() {
		return refVhId;
	}

	public void setRefVhId(String refVhId) {
		this.refVhId = refVhId;
	}

	public String getCgvn() {
		return cgvn;
	}

	public void setCgvn(String cgvn) {
		this.cgvn = cgvn;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public String getSubScheme() {
		return subScheme;
	}

	public void setSubScheme(String subScheme) {
		this.subScheme = subScheme;
	}

	public Functionary getFunctionary() {
		return functionary;
	}

	public void setFunctionary(Functionary functionary) {
		this.functionary = functionary;
	}

	public String getFundsource() {
		return fundsource;
	}

	public void setFundsource(String fundsource) {
		this.fundsource = fundsource;
	}

	public List<Ledgers> getLedgers() {
		return ledgers;
	}

	public void setLedgers(List<Ledgers> ledgers) {
		this.ledgers = ledgers;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getReferenceDocument() {
		return referenceDocument;
	}

	public void setReferenceDocument(String referenceDocument) {
		this.referenceDocument = referenceDocument;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@JsonFormat(pattern="dd/MM/yyy")
	public Date getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
	}
	
	
    
}
