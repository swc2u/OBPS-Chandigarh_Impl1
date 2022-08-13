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

package org.egov.edcr.web.controller.master;

import org.egov.common.entity.bpa.Occupancy;
import org.egov.commons.service.OccupancyService;
import org.egov.commons.service.SubOccupancyService;
import org.egov.edcr.entity.PlotMaster;
import org.egov.edcr.service.PlotMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("plotMaster/update")
public class UpdatePlotMasterController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UpdatePlotMasterController.class);
	
    private static final String PLOTMASTER_UPDATE_VIEW = "plot-master-update";
    @Autowired
    private PlotMasterService plotMasterService;
    
    @Autowired
    private OccupancyService occupancyService;
    
    @Autowired
    SubOccupancyService subOccupancyService;

    @ModelAttribute
    public PlotMaster plotMaster(@PathVariable Optional<String> plotId) {
        return plotId.isPresent() ? plotMasterService.getPlotMasterByPlotId(Long.parseLong(plotId.get())) : new PlotMaster();
    }

    @ModelAttribute("occupancy")
    public List<Occupancy> occupancy() {
        return occupancyService.findAll();
    }

    @GetMapping("/")
    public String showUpdatePlotMasterSearchForm(Model model) {
        model.addAttribute("search", true);
        return PLOTMASTER_UPDATE_VIEW;
    }

    @GetMapping("{plotId}")
    public String showUpdatePlotMasterForm(Model model) {
        model.addAttribute("search", false);
        return PLOTMASTER_UPDATE_VIEW;
    }

    @PostMapping("{id}")
    public String updatePlotMaster(@Valid @ModelAttribute PlotMaster plotMaster, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes, Model model,HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("subOccupancy", Long.parseLong(request.getParameter("allowedSOId")));
            return PLOTMASTER_UPDATE_VIEW;
        }
        plotMaster.setId(Long.parseLong(request.getParameter("pmId")));
        plotMaster.getAllowedsuboccupancy().setId(Long.parseLong(request.getParameter("allowedSOId")));
        plotMaster.getAllowedsuboccupancy().setSubOccupancy(Long.parseLong(request.getParameter("subOccupancyId")));
        plotMaster.getAllowedsuboccupancy().getPlot().setId(Long.parseLong(request.getParameter("plotId")));
        PlotMaster modifiedPM=plotMasterService.updatePlotMasterData(plotMaster);
        redirectAttributes.addFlashAttribute("message", "msg.pltmstr.update.success");
        redirectAttributes.addFlashAttribute("edit", true);
        return "redirect:/plotMaster/view/" + modifiedPM.getId().toString();
    }

}
