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
import org.egov.common.entity.bpa.SubOccupancy;
import org.egov.commons.service.OccupancyService;
import org.egov.commons.service.SubOccupancyService;
import org.egov.edcr.entity.PlotMaster;
import org.egov.edcr.service.PlotMasterService;
import org.egov.edcr.service.PlotService;
import org.egov.infra.admin.master.service.BoundaryService;
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

@Controller
@RequestMapping("plotMaster/create")
public class CreatePlotMasterDataController {

    private static final String PLOTMASTER_CREATE_VIEW = "plot-master-create";
    
    @Autowired
    PlotService plotService;
    
    @Autowired
    PlotMasterService plotMasterService;
    
    @Autowired
    OccupancyService occupancyService;
    
    @Autowired
    SubOccupancyService subOccupancyService;
    
    @Autowired
    BoundaryService boundaryService;
    
    @ModelAttribute
    public PlotMaster plotMaster() {
        return new PlotMaster();
    }

    @ModelAttribute("plots")
    public List<org.egov.edcr.entity.Plot> plot() {
        return plotService.getAllPlots();
    }
    @ModelAttribute("occupancy")
    public List<Occupancy> occupancy() {
        return occupancyService.findAll();
    }
//    @ModelAttribute("boundary")
//    public List<Boundary> boundary() {
//        return boundaryService.
//    }

    
    @GetMapping
    public String showCreatePlotMasterSearchForm(Model model) {
        model.addAttribute("search", true);
        return PLOTMASTER_CREATE_VIEW;
    }
 
    @GetMapping("{subOccupancyId}")
    public String showCreateBoundaryForm(@PathVariable Long subOccupancyId, Model model, RedirectAttributes redirectAttributes) {
        SubOccupancy subOccupancy =subOccupancyService.findById(subOccupancyId);
        if (subOccupancy==null) {
            redirectAttributes.addFlashAttribute("warning", "err.root.subOccupancy.invalid");
            return "redirect:/plotMaster/create";
        }
        model.addAttribute("subOccupancy", subOccupancy);
        model.addAttribute("search", false);
        return PLOTMASTER_CREATE_VIEW;
    }
    
    @PostMapping
    public String createBoundary(@Valid @ModelAttribute PlotMaster plotMaster, BindingResult errors,
                                 RedirectAttributes redirectAttributes, Model model,HttpServletRequest request) {
        if (errors.hasErrors()) {
            return PLOTMASTER_CREATE_VIEW;
        }
        plotMaster.getAllowedsuboccupancy().setSubOccupancy(Long.parseLong(request.getParameter("subOccupancyId")));
        plotMasterService.createPlotMasterData(plotMaster);
        redirectAttributes.addFlashAttribute("message", "msg.plotMaster.create.success");
        redirectAttributes.addFlashAttribute("create", true);
        return "redirect:/plotMaster/view/" + plotMaster.getId();
    }
}
