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

import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "eg_plot_master_data")
@Audited
@SequenceGenerator(name = PlotMaster.SEQ_PLOT_MASTER, sequenceName = PlotMaster.SEQ_PLOT_MASTER, allocationSize = 1)
public class PlotMaster extends AbstractAuditable {
	private static final long serialVersionUID = 13233L;
	public static final String SEQ_PLOT_MASTER = "seq_eg_plot_master_data";
//    private static final long serialVersionUID = 3054956514161912026L;
    
    @Id
    @GeneratedValue(generator = SEQ_PLOT_MASTER, strategy = GenerationType.SEQUENCE)
    private Long id;

    @Length(max = 50)
    @SafeHtml
    @NotBlank
    private String code;
    
    private Double backCourtyardWidth;
    
    private Double backCourtyardHeight;
    
    private Long permissibleBuildingStories;
    
    private Double permissibleBuildingHeight;
    
    private Long maxmimumPermissibleFAR;
    
    private Double minimumPermissibleSetback_Front;
    
    private Double minimumPermissibleSetback_Rear;
    
    private Double minimumPermissibleSetback_Left;
    
    private Double minimumPermissibleSetback_Right;
    
    @ManyToOne
    @JoinColumn(name = "allowedsuboccupancyid", updatable = false)
    private AllowedSubOccupancyPlot allowedsuboccupancyid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getBackCourtyardWidth() {
		return backCourtyardWidth;
	}

	public void setBackCourtyardWidth(Double backCourtyardWidth) {
		this.backCourtyardWidth = backCourtyardWidth;
	}

	public Double getBackCourtyardHeight() {
		return backCourtyardHeight;
	}

	public void setBackCourtyardHeight(Double backCourtyardHeight) {
		this.backCourtyardHeight = backCourtyardHeight;
	}

	public Long getPermissibleBuildingStories() {
		return permissibleBuildingStories;
	}

	public void setPermissibleBuildingStories(Long permissibleBuildingStories) {
		this.permissibleBuildingStories = permissibleBuildingStories;
	}

	public Double getPermissibleBuildingHeight() {
		return permissibleBuildingHeight;
	}

	public void setPermissibleBuildingHeight(Double permissibleBuildingHeight) {
		this.permissibleBuildingHeight = permissibleBuildingHeight;
	}

	public Long getMaxmimumPermissibleFAR() {
		return maxmimumPermissibleFAR;
	}

	public void setMaxmimumPermissibleFAR(Long maxmimumPermissibleFAR) {
		this.maxmimumPermissibleFAR = maxmimumPermissibleFAR;
	}

	public Double getMinimumPermissibleSetback_Front() {
		return minimumPermissibleSetback_Front;
	}

	public void setMinimumPermissibleSetback_Front(Double minimumPermissibleSetback_Front) {
		this.minimumPermissibleSetback_Front = minimumPermissibleSetback_Front;
	}

	public Double getMinimumPermissibleSetback_Rear() {
		return minimumPermissibleSetback_Rear;
	}

	public void setMinimumPermissibleSetback_Rear(Double minimumPermissibleSetback_Rear) {
		this.minimumPermissibleSetback_Rear = minimumPermissibleSetback_Rear;
	}

	public Double getMinimumPermissibleSetback_Left() {
		return minimumPermissibleSetback_Left;
	}

	public void setMinimumPermissibleSetback_Left(Double minimumPermissibleSetback_Left) {
		this.minimumPermissibleSetback_Left = minimumPermissibleSetback_Left;
	}

	public Double getMinimumPermissibleSetback_Right() {
		return minimumPermissibleSetback_Right;
	}

	public void setMinimumPermissibleSetback_Right(Double minimumPermissibleSetback_Right) {
		this.minimumPermissibleSetback_Right = minimumPermissibleSetback_Right;
	}

	public AllowedSubOccupancyPlot getAllowedsuboccupancyid() {
		return allowedsuboccupancyid;
	}

	public void setAllowedsuboccupancyid(AllowedSubOccupancyPlot allowedsuboccupancyid) {
		this.allowedsuboccupancyid = allowedsuboccupancyid;
	}
    

}
