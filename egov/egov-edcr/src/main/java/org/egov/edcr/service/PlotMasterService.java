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

package org.egov.edcr.service;


import java.math.BigDecimal;
import java.util.List;

import org.egov.edcr.contract.PlotMasterSearchRequest;
import org.egov.edcr.entity.Plot;
import org.egov.edcr.entity.PlotMaster;
import org.egov.edcr.repository.PlotMasterRepository;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.repository.BoundaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class PlotMasterService {

    private static final Logger LOG = LoggerFactory.getLogger(PlotMasterService.class);
    
    private final PlotMasterRepository plotMasterRepository;
    
    
    @Autowired
    private AllowedSubOccupancyPlotService allowedSubOccupancyPlotService;
    
    
    private final BoundaryRepository boundaryRepository;


    @Autowired
    public PlotMasterService(final PlotMasterRepository plotMasterRepository,final BoundaryRepository boundaryRepository) {
        this.plotMasterRepository = plotMasterRepository;
		this.boundaryRepository = boundaryRepository;
    }

	public PlotMaster searchPlotMasterData(final String occupancyCode, final String sector, final String plotNumber, final String plotType) {
		Long allowedPlotId = allowedSubOccupancyPlotService.searchAllowedSOPlot(plotNumber);
		return plotMasterRepository.findPlotMasterData(occupancyCode,allowedPlotId);
		
	}

	public PlotMaster getPlotMasterByName(String plotName) {
		return plotMasterRepository.findAllByPlotName(plotName);
	}
	
	public PlotMaster getPlotMasterByPlotId(Long plotId) {
		return plotMasterRepository.findAllByPlotId(plotId);
	}	
	
	@Transactional
	public PlotMaster createPlotMasterData(PlotMaster plotMaster) {
		Boundary boundary =boundaryRepository.findByName(plotMaster.getAllowedsuboccupancy().getPlot().getBoundary().getName());
		plotMaster.getAllowedsuboccupancy().getPlot().setBoundary(boundary);
		insertEnrichment(plotMaster);
		return plotMasterRepository.save(plotMaster);
		
	}
	
	public Page<PlotMaster> getPageOfPlotMaster(PlotMasterSearchRequest searchRequest) {
        Pageable pageable = new PageRequest(searchRequest.pageNumber(), searchRequest.pageSize(),
                searchRequest.orderDir(), searchRequest.orderBy());
        return plotMasterRepository.findBySubOccupancyId(searchRequest.getSubOccupancyId(), pageable);
	}
	
	public List<PlotMaster> getPlotMasterBySubOccupancyId(Long subOccupancyId) {
		return plotMasterRepository.findAllBySubOccupancyId(subOccupancyId);
	}
	
	@Transactional
	public PlotMaster updatePlotMasterData(PlotMaster plotMaster) {
		insertEnrichment(plotMaster);
		return plotMasterRepository.save(plotMaster);
	}

	private void insertEnrichment(PlotMaster plotMaster) {
		enrichPlotData(plotMaster.getAllowedsuboccupancy().getPlot());
		
		if(plotMaster.getAllowedsuboccupancy().getPlot()!=null) {
			plotMaster.getAllowedsuboccupancy().getPlot().setPlotDepth(plotMaster.getAllowedsuboccupancy().getPlot().getPlotDepth()!=null?converFeetToMeter(plotMaster.getAllowedsuboccupancy().getPlot().getPlotDepth().toString()):"0");
			plotMaster.getAllowedsuboccupancy().getPlot().setPlotWidth(plotMaster.getAllowedsuboccupancy().getPlot().getPlotWidth()!=null?converFeetToMeter(plotMaster.getAllowedsuboccupancy().getPlot().getPlotWidth().toString()):"0");
		}
		plotMaster.setBackCourtyardHeight(plotMaster.getBackCourtyardHeight()!=null?converFeetToMeter(plotMaster.getBackCourtyardHeight().toString()):"0");
		plotMaster.setBackCourtyardWidth(plotMaster.getBackCourtyardWidth()!=null?converFeetToMeter(plotMaster.getBackCourtyardWidth().toString()):"0");
		plotMaster.setMinimumPermissibleSetback_Front(plotMaster.getMinimumPermissibleSetback_Front()!=null?converFeetToMeter(plotMaster.getMinimumPermissibleSetback_Front().toString()):"0");
		plotMaster.setMinimumPermissibleSetback_Rear(plotMaster.getMinimumPermissibleSetback_Rear()!=null?converFeetToMeter(plotMaster.getMinimumPermissibleSetback_Rear().toString()):"0");
		plotMaster.setMinimumPermissibleSetback_Right(plotMaster.getMinimumPermissibleSetback_Right()!=null?converFeetToMeter(plotMaster.getMinimumPermissibleSetback_Right().toString()):"0");
		plotMaster.setMinimumPermissibleSetback_Left(plotMaster.getMinimumPermissibleSetback_Left()!=null?converFeetToMeter(plotMaster.getMinimumPermissibleSetback_Left().toString()):"0");
	}


	private void enrichPlotData(Plot plot) {
		if(plot.getName()==null) {
			String name= plot.getBoundary().getName()+"."+plot.getPlotNum();
			plot.setName(name);
			plot.setLocalName(name);
		}
	}

	private String converFeetToMeter(String str) {
			String result = "";
			if (str.toUpperCase().contains("F")) {
				String[] array = str.split("F");
				double fm = 0;
				double fi = 0;
				if (array.length > 0) {
					fm = feetToMeter(Double.parseDouble(array[0]));
					if (array.length == 2) {
						fi = inchToMeter(Double.parseDouble(array[1]));
					}
				}
				BigDecimal r = BigDecimal.valueOf((fm + fi));
				r=r.setScale(2,BigDecimal.ROUND_HALF_UP);
				result=r.toString();
			} else {
				result = str;
			}

			return result;
		}
	public static double feetToMeter(double feet) {
		BigDecimal result = BigDecimal.ZERO;
		result = BigDecimal.valueOf(feet * 0.3048);
		return result.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static double inchToMeter(double inch) {
		BigDecimal result = BigDecimal.ZERO;
		result = BigDecimal.valueOf(inch * 0.0254);
		return result.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
}