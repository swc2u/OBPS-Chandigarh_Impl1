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

import com.google.gson.annotations.Expose;

import org.egov.common.entity.bpa.SubOccupancy;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


//import static org.egov.infra.admin.master.entity.Sector.SEQ_Sector;

@Entity
@Table(name = "eg_plot_supoccupancy_allowed",schema="chandigarh")
@SequenceGenerator(name = AllowedSubOccupancyPlot.SEQ_PLOT_SO_ALLOWED, sequenceName = AllowedSubOccupancyPlot.SEQ_PLOT_SO_ALLOWED, allocationSize = 1)
public class AllowedSubOccupancyPlot extends AbstractAuditable {
    public static final String SEQ_PLOT_SO_ALLOWED = "seq_eg_plot_supoccupancy_allowed";
    private static final long serialVersionUID = 3054956514161912026L;
    @Expose
    @Id
    @GeneratedValue(generator = SEQ_PLOT_SO_ALLOWED, strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne(fetch = LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "plot")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Plot  plot;
   
   private Long subOccupancy;
    
    
//    private transient Plot savedPlot;
    
    public AllowedSubOccupancyPlot() {
    	this.plot = new Plot();
    }
    
    public AllowedSubOccupancyPlot(Plot plot) {
    	this.plot = plot;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Plot getPlot() {
		return plot;
	}

	public void setPlot(Plot plot) {
		this.plot = plot;
	}

	public Long getSubOccupancy() {
		return subOccupancy;
	}

	public void setSubOccupancy(Long subOccupancy) {
		this.subOccupancy = subOccupancy;
	}

//	public Plot getSavedPlot() {
//		return savedPlot;
//	}
//
//	public void setSavedPlot(Plot savedPlot) {
//		this.savedPlot = savedPlot;
//	}

}
