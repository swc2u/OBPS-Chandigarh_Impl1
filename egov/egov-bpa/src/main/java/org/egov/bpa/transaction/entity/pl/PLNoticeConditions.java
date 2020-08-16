package org.egov.bpa.transaction.entity.pl;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.egov.bpa.transaction.entity.common.NoticeCondition;
import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "egbpa_pl_notice_conditions")
@SequenceGenerator(name = PLNoticeConditions.SEQ_PL_NOTICE_CONDITIONS, sequenceName = PLNoticeConditions.SEQ_PL_NOTICE_CONDITIONS, allocationSize = 1)
public class PLNoticeConditions  extends AbstractAuditable {
	private static final long serialVersionUID = 771762227114807254L;

    public static final String SEQ_PL_NOTICE_CONDITIONS = "seq_egbpa_pl_notice_conditions";

    @Id
    @GeneratedValue(generator = SEQ_PL_NOTICE_CONDITIONS, strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plinthlevelcertificate", nullable = false)
    private PlinthLevelCertificate pl;
   
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "noticecondition", nullable = false)
    private NoticeCondition noticeCondition;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlinthLevelCertificate getPl() {
		return pl;
	}

	public void setPl(PlinthLevelCertificate pl) {
		this.pl = pl;
	}

	public NoticeCondition getNoticeCondition() {
		return noticeCondition;
	}

	public void setNoticeCondition(NoticeCondition noticeCondition) {
		this.noticeCondition = noticeCondition;
	}
}
