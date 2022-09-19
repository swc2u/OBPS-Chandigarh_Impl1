/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
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
 */
package org.egov.bpa.web.controller.report;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.egov.bpa.master.entity.ApplicationSubType;
import org.egov.bpa.master.service.ApplicationSubTypeService;
import org.egov.bpa.master.service.ServiceTypeService;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.dto.SearchPendingItemsForm;
import org.egov.bpa.transaction.service.report.PlReportsService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.web.controller.adaptor.SearchPLTaskAdaptor;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.infra.web.support.ui.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/reports")
public class PlReportsController extends BpaGenericApplicationController {
	private static final String SEARCH_PL_PENDING_ITEM_FORM = "searchPLPendingItemsForm";
    private static final String SEARCH_PL_PENDING_ITEM_FORM_GRAPH = "searchPLPendingItemsFormG";
	
	public static final Long BTK_APPTYPE = 3L;
    public static final Long ATK_APPTYPE = 5L;
    public static final Long RURAL_APP_ID = 4L;
	private static final String PLSTATUS_MODULETYPE = "INSPECTION";
    
    @Autowired
	private PlReportsService plReportsService;
	@Autowired
	private ServiceTypeService serviceTypeService;
	@Autowired
	private ApplicationSubTypeService applicationTypeService;
	
	private final String URBAN = "URBAN";
	private final String RURAL = "RURAL";
	
	

	 //Search PL Applications
    @GetMapping("/searchPLItems/d/u")
    public String showPLApplicationGraphUrban(final Model model) {
    	prepareFormDataForPLItems(model,URBAN);
    	model.addAttribute(SEARCH_PL_PENDING_ITEM_FORM, new SearchPendingItemsForm());
    	model.addAttribute(SEARCH_PL_PENDING_ITEM_FORM_GRAPH, new SearchPendingItemsForm());
        return "search-pl-task-urban";
    }
    
    //Search PL Pedning Applications
    @GetMapping("/searchPLPendingItems/d/u")
    public String showPLPndingApplicationGraphUrban(final Model model) {
    	prepareFormDataForPLItems(model,URBAN);
    	model.addAttribute(SEARCH_PL_PENDING_ITEM_FORM, new SearchPendingItemsForm());
    	model.addAttribute(SEARCH_PL_PENDING_ITEM_FORM_GRAPH, new SearchPendingItemsForm());
        return "search-pl-pending-task-urban";
    }
    
    //Search PL  Applications Rural
    @GetMapping("/searchPLItems/d/r")
    public String showPLApplicationGraphRural(final Model model) {
    	prepareFormDataForPLItems(model,RURAL);
    	model.addAttribute(SEARCH_PL_PENDING_ITEM_FORM, new SearchPendingItemsForm());
    	model.addAttribute(SEARCH_PL_PENDING_ITEM_FORM_GRAPH, new SearchPendingItemsForm());
        return "search-pl-task-rural";
    }
    
    //Search PL pending Applications-Rural
    @GetMapping("/searchPLPendingItems/d/r")
    public String showPLPndingApplicationGraphRural(final Model model) {
    	prepareFormDataForPLItems(model,RURAL);
    	model.addAttribute(SEARCH_PL_PENDING_ITEM_FORM, new SearchPendingItemsForm());
    	model.addAttribute(SEARCH_PL_PENDING_ITEM_FORM_GRAPH, new SearchPendingItemsForm());
        return "search-pl-pending-task-rural";
    }
    
    protected void prepareFormDataForPLItems(Model model,String applicationType) {
    	List<ApplicationSubType> applicationTypes = applicationTypeService.getBPAApplicationTypes();
    	if(applicationType.equals(URBAN))
    		model.addAttribute("appTypes",applicationTypes.stream().filter(appType -> !appType.getName().equalsIgnoreCase("Medium Risk"))
            .collect(Collectors.toList()));
    	else
    		model.addAttribute("appTypes",applicationTypes.stream().filter(appType -> appType.getName().equalsIgnoreCase("Medium Risk"))
            .collect(Collectors.toList()));
    	model.addAttribute("serviceTypeList", serviceTypeService.getAllActiveMainServiceTypes());
    	model.addAttribute("designations", BpaConstants.getAvailableDesignations());
    	model.addAttribute("applnStatusList", bpaStatusService.findAllByModuleType(PLSTATUS_MODULETYPE));
    }
    
    protected void prepareFormDataForPLPendingItems(Model model,String applicationType) {
    	List<ApplicationSubType> applicationTypes = applicationTypeService.getBPAApplicationTypes();
    	 List<BpaStatus> sttusList = bpaStatusService.findAllByModuleType(PLSTATUS_MODULETYPE);
    	if(applicationType.equals(URBAN)) {
    		model.addAttribute("appTypes",applicationTypes.stream().filter(appType -> !appType.getName().equalsIgnoreCase("Medium Risk"))
            .collect(Collectors.toList()));
    		model.addAttribute("applnStatusList", sttusList.stream().filter(status-> !status.getCode().equalsIgnoreCase("Order Issued to Applicant")).collect(Collectors.toList()));
    	}else {
    		model.addAttribute("appTypes",applicationTypes.stream().filter(appType -> appType.getName().equalsIgnoreCase("Medium Risk"))
            .collect(Collectors.toList()));
    		model.addAttribute("applnStatusList",sttusList);
    	}
    	model.addAttribute("serviceTypeList", serviceTypeService.getAllActiveMainServiceTypes());
    	model.addAttribute("designations", BpaConstants.getAvailableDesignations());
    	
    }
    
    @PostMapping(value = "/searchPLItems/d/u", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String showSearchPLItemsRecordsUrban(@ModelAttribute  SearchPendingItemsForm searchPendingItemsFormG) {
    	
    	Map<String, Long> map=new HashMap<String, Long>(); 
    	
    	fillterData(plReportsService.pagedSearchForPLItemGraph(searchPendingItemsFormG), map);
        
        Gson gson = new Gson();  
		String json = gson.toJson(map); 
		return json;
    }
    
    @PostMapping(value = "/searchPLPendingItems/d/u", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String showSearchPLPendingItemsRecordsUrban(@ModelAttribute  SearchPendingItemsForm searchPendingItemsFormG) {
    	
    	Map<String, Long> map=new HashMap<String, Long>(); 
    	
    	fillterData(plReportsService.pagedSearchForPLPendingItemGraph(searchPendingItemsFormG), map);
        
        Gson gson = new Gson();  
		String json = gson.toJson(map); 
		return json;
    }
    
    @PostMapping(value = "/searchPLItems/d/r", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String showSearchPLItemsRecordsRural(@ModelAttribute  SearchPendingItemsForm searchPendingItemsFormG) {
    	
    	Map<String, Long> map=new HashMap<String, Long>(); 
    	
    	fillterData(plReportsService.pagedSearchForPLItemGraphRural(searchPendingItemsFormG), map);
        
        Gson gson = new Gson();  
		String json = gson.toJson(map); 
		return json;
    }
    
    @PostMapping(value = "/searchPLPendingItems/d/r", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String showSearchPLPendingItemsRecordsRural(@ModelAttribute  SearchPendingItemsForm searchPendingItemsFormG) {
    	
    	Map<String, Long> map=new HashMap<String, Long>(); 
    	
    	fillterData(plReportsService.pagedSearchForPLPendingItemGraphRural(searchPendingItemsFormG), map);
        
        Gson gson = new Gson();  
		String json = gson.toJson(map); 
		return json;
    }
    
    @PostMapping(value = "/searchPLItems", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String showSearchUrbanPLItemsRecords(@ModelAttribute SearchPendingItemsForm searchPendingItemsForm) {
    	return new DataTable<>(plReportsService.pagedSearchForUrbanPLItem(searchPendingItemsForm),
        		searchPendingItemsForm.draw())
                        .toJson(SearchPLTaskAdaptor.class);
    }
    
    @PostMapping(value = "/searchPLPendingItems", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String showSearchUrbanPLPendingItemsRecords(@ModelAttribute SearchPendingItemsForm searchPendingItemsForm) {
    	return new DataTable<>(plReportsService.pagedSearchForUrbanPLPendingItem(searchPendingItemsForm),
        		searchPendingItemsForm.draw())
                        .toJson(SearchPLTaskAdaptor.class);
    }
    
    private void fillterData(Page<SearchPendingItemsForm> page,Map<String, Long> map) {
    	for(SearchPendingItemsForm pendingItemsForm:page) {
    		if(map.get(pendingItemsForm.getCurrentOwner())==null) {
    			map.put(pendingItemsForm.getCurrentOwner(), 1l);
    		}else {
    			map.put(pendingItemsForm.getCurrentOwner(), map.get(pendingItemsForm.getCurrentOwner())+1);
    		}
    	}
    }
    
    @PostMapping(value = "/searchPLItemsRural", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String showSearchRuralPLItemsRecords(@ModelAttribute SearchPendingItemsForm searchPendingItemsForm) {
    	return new DataTable<>(plReportsService.pagedSearchForRuralPLItem(searchPendingItemsForm),
        		searchPendingItemsForm.draw())
                        .toJson(SearchPLTaskAdaptor.class);
    }
    
    @PostMapping(value = "/searchPLPendingItemsRural", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String showSearchRuralPLPendingItemsRecords(@ModelAttribute SearchPendingItemsForm searchPendingItemsForm) {
    	return new DataTable<>(plReportsService.pagedSearchForRuralPLPendingItem(searchPendingItemsForm),
        		searchPendingItemsForm.draw())
                        .toJson(SearchPLTaskAdaptor.class);
    }
    
}

