package org.egov.bpa.transaction.entity.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.egov.infra.web.support.search.DataTableSearchRequest;
import org.hibernate.validator.constraints.SafeHtml;

public class ReceiptRegisterReportHelper extends DataTableSearchRequest {
    private Long id;
    @SafeHtml
    private String applicationNumber;
    @SafeHtml
    private String receiptNumber;
    @SafeHtml
    private Date paymentDate;
    
    private String Sector;
    
    private String plotNumber;
    
    private String fileNumber;
    
    private Double securityFee=new Double(0.0);
    private Double scrutinyFee=new Double(0.0);
    private Double gst=new Double(0.0);
    private Double additionFee=new Double(0.0);
    private Double labourCess=new Double(0.0);
    private Double rule5=new Double(0.0);
    private Double totalWithoutLaboutCess=new Double(0.0);
    private Double total = new Double(0.0);
    
    

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

   

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ReceiptRegisterReportHelper))
            return false;
        ReceiptRegisterReportHelper that = (ReceiptRegisterReportHelper) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getApplicationNumber(), that.getApplicationNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getApplicationNumber());
    }

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSector() {
		return Sector;
	}

	public void setSector(String sector) {
		Sector = sector;
	}

	public String getPlotNumber() {
		return plotNumber;
	}

	public void setPlotNumber(String plotNumber) {
		this.plotNumber = plotNumber;
	}

	public String getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

	public Double getSecurityFee() {
		return securityFee;
	}

	public void setSecurityFee(Double securityFee) {
		this.securityFee = securityFee;
	}

	public Double getScrutinyFee() {
		return scrutinyFee;
	}

	public void setScrutinyFee(Double scrutinyFee) {
		this.scrutinyFee = scrutinyFee;
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	public Double getAdditionFee() {
		return additionFee;
	}

	public void setAdditionFee(Double additionFee) {
		this.additionFee = additionFee;
	}

	public Double getLabourCess() {
		return labourCess;
	}

	public void setLabourCess(Double labourCess) {
		this.labourCess = labourCess;
	}

	public Double getRule5() {
		return rule5;
	}

	public void setRule5(Double rule5) {
		this.rule5 = rule5;
	}

	public Double getTotalWithoutLaboutCess() {
		return totalWithoutLaboutCess;
	}

	public void setTotalWithoutLaboutCess(Double totalWithoutLaboutCess) {
		this.totalWithoutLaboutCess = totalWithoutLaboutCess;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}


}
