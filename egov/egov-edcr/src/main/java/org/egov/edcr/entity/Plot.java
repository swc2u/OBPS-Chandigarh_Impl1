/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 *
 */

package org.egov.edcr.entity;

import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.egov.infra.persistence.entity.AbstractPersistable;
import org.egov.infra.persistence.validator.annotation.Unique;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "eg_plot",schema="chandigarh")
@SequenceGenerator(name = Plot.SEQ_PLOT, sequenceName = Plot.SEQ_PLOT, allocationSize = 1)
@Unique(fields = "name", enableDfltMsg = true)
@Audited

public class Plot extends AbstractAuditable {

    public static final String SEQ_PLOT = "seq_eg_plot";
    private static final long serialVersionUID = 8237250445794L;
    @Id
    @GeneratedValue(generator = SEQ_PLOT, strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(name="plotnum")
    private String plotNum;

    @NotAudited
	@SafeHtml
	@Column(name="name")
    private String name;

    @NotAudited
	@SafeHtml
	@Column(name="localname")
    private String localName;
    
    @NotAudited
	@SafeHtml
	@Column(name="areatype")
    private String areaType;
    
    @Column(name="plotarea")
    private Double plotArea;
    
    @Column(name="plotdepth")
    private Double plotDepth;
    
    @Column(name="plotwidth")
    private Double plotWidth;
    
    @NotAudited
	@SafeHtml
	@Column(name="phase")
    private String phase;

    @ManyToOne
    @JoinColumn(name = "boundary")
    @NotAudited
    private Boundary boundary;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlotNum() {
		return plotNum;
	}

	public void setPlotNum(String plotNum) {
		this.plotNum = plotNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public Double getPlotArea() {
		return plotArea;
	}

	public void setPlotArea(Double plotArea) {
		this.plotArea = plotArea;
	}

	public Double getPlotDepth() {
		return plotDepth;
	}

	public void setPlotDepth(Double plotDepth) {
		this.plotDepth = plotDepth;
	}

	public Double getPlotWidth() {
		return plotWidth;
	}

	public void setPlotWidth(Double plotWidth) {
		this.plotWidth = plotWidth;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Boundary getBoundary() {
		return boundary;
	}

	public void setBoundary(Boundary boundary) {
		this.boundary = boundary;
	}

   
}
