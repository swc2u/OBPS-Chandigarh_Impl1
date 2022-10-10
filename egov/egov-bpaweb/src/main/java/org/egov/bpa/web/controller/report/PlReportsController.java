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


import static org.egov.infra.utils.JsonUtils.toJSON;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.egov.bpa.master.entity.ApplicationSubType;
import org.egov.bpa.master.service.ApplicationSubTypeService;
import org.egov.bpa.master.service.ServiceTypeService;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationReport;
import org.egov.bpa.transaction.entity.dto.SearchPendingItemsForm;
import org.egov.bpa.transaction.service.report.PlReportsService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.web.controller.adaptor.SearchBpaApplicationFormAdaptor;
import org.egov.bpa.web.controller.adaptor.SearchBpaApplicationReportAdaptor;
import org.egov.bpa.web.controller.adaptor.SearchPLTaskAdaptor;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.infra.utils.DateUtils;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/reports")
public class PlReportsController extends BpaGenericApplicationController {
	private static final String SEARCH_PL_PENDING_ITEM_FORM = "searchPLPendingItemsForm";
	private static final String SEARCH_BPA_APPLICATION_FORM = "searchBpaApplicationForm";
    private static final String SEARCH_PL_PENDING_ITEM_FORM_GRAPH = "searchPLPendingItemsFormG";
	
	public static final Long BTK_APPTYPE = 3L;
    public static final Long ATK_APPTYPE = 5L;
    public static final Long RURAL_APP_ID = 4L;
	private static final String PLSTATUS_MODULETYPE = "INSPECTION";
	
	private static final String DATA = "{ \"data\":";
    
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
    	prepareFormDataForPLPendingItems(model,URBAN);
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
    	prepareFormDataForPLPendingItems(model,RURAL);
    	model.addAttribute(SEARCH_PL_PENDING_ITEM_FORM, new SearchPendingItemsForm());
    	model.addAttribute(SEARCH_PL_PENDING_ITEM_FORM_GRAPH, new SearchPendingItemsForm());
        return "search-pl-pending-task-rural";
    }
    
    @RequestMapping(value = "/servicewise-statusreport-pl/d/r", method = RequestMethod.GET)
   	public String searchStatusCountByServicetypeFormForRural(final Model model) {
    	prepareFormDataForPLItems(model,RURAL);
   		model.addAttribute(SEARCH_BPA_APPLICATION_FORM, new SearchBpaApplicationForm());
   		return "search-servicewise-status-report-pl-Rural";
   	}
   	
   	@RequestMapping(value = "/servicewise-statusreport-pl/d/u", method = RequestMethod.GET)
   	public String searchStatusCountByServicetypeFormForUrban(final Model model) {
   		prepareFormDataForPLItems(model,URBAN);
   		model.addAttribute(SEARCH_BPA_APPLICATION_FORM, new SearchBpaApplicationForm());
   		return "search-servicewise-status-report-pl-Urban";
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
    		
    	}else {
    		model.addAttribute("appTypes",applicationTypes.stream().filter(appType -> appType.getName().equalsIgnoreCase("Medium Risk"))
            .collect(Collectors.toList()));
    	}
    	model.addAttribute("applnStatusList", sttusList.stream().filter(status-> !status.getCode().matches("Order Issued to Applicant|Cancelled|Rejected")).collect(Collectors.toList()));
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
    
    @RequestMapping(value = "/servicewise-statusreport-pl/d/u", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getStatusCountByServicetypeResultForUrban(final Model model) {
		Map<String, Long> map=new HashMap<String, Long>();
		
		SearchBpaApplicationForm aboveTwoKanal=new SearchBpaApplicationForm();
		aboveTwoKanal.setApplicationTypeId(5L);//Above two Kanal 
		final List<SearchBpaApplicationReport> aboveTwoKanalResultList = plReportsService
				.getResultsByServicetypeAndStatus(aboveTwoKanal);
		map.put("Above two Kanal", getCount(aboveTwoKanalResultList));
		
		SearchBpaApplicationForm belowTwoKanal=new SearchBpaApplicationForm();
		belowTwoKanal.setApplicationTypeId(3L);//Below two Kanal
		final List<SearchBpaApplicationReport> belowTwoKanalResultList = plReportsService
				.getResultsByServicetypeAndStatus(belowTwoKanal);
		
		map.put("Below two Kanal", getCount(belowTwoKanalResultList));
		
		Gson gson = new Gson(); 
		String json = gson.toJson(map); 
		
		return json;
	}
	
	@RequestMapping(value = "/servicewise-statusreport-pl/d/r", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getStatusCountByServicetypeResultForRural(final Model model) {
		Map<String, Long> map=new HashMap<String, Long>();
		
		SearchBpaApplicationForm rural=new SearchBpaApplicationForm();
		rural.setApplicationTypeId(4L);//RURAL
		
		final List<SearchBpaApplicationReport> ruralResultList = plReportsService
				.getResultsByServicetypeAndStatus(rural);
		
		map.put("RURAL", getCount(ruralResultList));
		
		Gson gson = new Gson(); 
		String json = gson.toJson(map); 
		
		return json;
	}
	
	private Long getCount(List<SearchBpaApplicationReport> list) {
		Long result=0l;
		for(SearchBpaApplicationReport report:list) {
			if (report.getServiceType01() != null)
				result += report.getServiceType01();
			if (report.getServiceType02() != null)
				result += report.getServiceType02();
			if (report.getServiceType03() != null)
				result += report.getServiceType03();
			if (report.getServiceType04() != null)
				result += report.getServiceType04();
			if (report.getServiceType05() != null)
				result += report.getServiceType05();
			if (report.getServiceType06() != null)
				result += report.getServiceType06();
			if (report.getServiceType07() != null)
				result += report.getServiceType07();
			if (report.getServiceType08() != null)
				result += report.getServiceType08();
			if (report.getServiceType09() != null)
				result += report.getServiceType09();
			if (report.getServiceType14() != null)
				result += report.getServiceType14();
			if (report.getServiceType15() != null)
				result += report.getServiceType15();
		}
		return result;
	}

	
	@RequestMapping(value = "/servicewise-statusreport-pl-urban", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getStatusCountByServicetypeResultForUrban(@ModelAttribute final SearchBpaApplicationForm searchPLApplicationForm) {
		final List<SearchBpaApplicationReport> searchResultList = plReportsService
				.getResultsByServicetypeAndStatusForUrban(searchPLApplicationForm);
		return new StringBuilder(DATA)
				.append(toJSON(searchResultList, SearchBpaApplicationReport.class, SearchBpaApplicationReportAdaptor.class))
				.append("}")
				.toString();
	}

	
	@RequestMapping(value = "/servicewise-statusreport-pl/view/d/u", method = RequestMethod.GET)
	public String viewUrbanStatusCountByServicetypeDetails(@RequestParam final String applicantName,
													  @RequestParam final String applicationNumber,
													  @RequestParam final Long ward, @RequestParam final Date fromDate,
													  @RequestParam final Date toDate, @RequestParam final Long revenueWard, @RequestParam final Long electionWard,
													  @RequestParam final Long zoneId, @RequestParam final String status, @RequestParam final String serviceType,
													  @RequestParam final String zone, @RequestParam final String serviceTypeEnum,@RequestParam final String applicationTypeId,@RequestParam final String plotNumber,@RequestParam final String sector, final Model model) {
		model.addAttribute("applicantName", applicantName);
		model.addAttribute("applicationNumber", applicationNumber);
		model.addAttribute("applicationTypeId", applicationTypeId);
		model.addAttribute("plotNumber", plotNumber);
		model.addAttribute("sector", sector);
		model.addAttribute("ward", ward);
		if (fromDate == null) {
			model.addAttribute("fromDate", fromDate);
		} else {
			model.addAttribute("fromDate", DateUtils.toDefaultDateFormat(fromDate));
		}
		if (toDate == null) {
			model.addAttribute("toDate", toDate);
		} else {
			model.addAttribute("toDate", DateUtils.toDefaultDateFormat(toDate));
		}
		model.addAttribute("revenueWard", revenueWard);
		model.addAttribute("electionWard", electionWard);
		model.addAttribute("zone", zone);
		model.addAttribute("zoneId", zoneId);
		model.addAttribute("status", status);
		model.addAttribute("serviceType", serviceType);
		model.addAttribute("serviceTypeEnum", serviceTypeEnum);
		return "view-servicewise-appln-details-pl-urban";
	}

	
	@RequestMapping(value = "/servicewise-statusreport-pl-rural/view", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String viewStatusCountByServicetypeDetailsForRural(@ModelAttribute final SearchBpaApplicationForm searchPLApplicationForm) {
		
		if(searchPLApplicationForm.getApplicationTypeId()==null) 
			searchPLApplicationForm.setApplicationTypeId(RURAL_APP_ID);
		final List<SearchBpaApplicationForm> searchResultList = plReportsService.searchForServicewiseStatus(searchPLApplicationForm);
		return new StringBuilder(DATA)
				.append(toJSON(searchResultList, SearchBpaApplicationForm.class, SearchBpaApplicationFormAdaptor.class))
				.append("}")
				.toString();
	}
	
	
	
	@RequestMapping(value = "/servicewise-statusreport-pl-rural", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getStatusCountByServicetypeResultForRural(@ModelAttribute final SearchBpaApplicationForm searchPLApplicationForm) {
		final List<SearchBpaApplicationReport> searchResultList = plReportsService
				.getResultsByServicetypeAndStatusForRural(searchPLApplicationForm);
		return new StringBuilder(DATA)
				.append(toJSON(searchResultList, SearchBpaApplicationReport.class, SearchBpaApplicationReportAdaptor.class))
				.append("}")
				.toString();
	}

	
	@RequestMapping(value = "/servicewise-statusreport-pl/view/d/r", method = RequestMethod.GET)
	public String viewRuralStatusCountByServicetypeDetails(@RequestParam final String applicantName,
													  @RequestParam final String applicationNumber,
													  @RequestParam final Long ward, @RequestParam final Date fromDate,
													  @RequestParam final Date toDate, @RequestParam final Long revenueWard, @RequestParam final Long electionWard,
													  @RequestParam final Long zoneId, @RequestParam final String status, @RequestParam final String serviceType,
													  @RequestParam final String zone, @RequestParam final String serviceTypeEnum,@RequestParam final String applicationTypeId,@RequestParam final String plotNumber,@RequestParam final String sector, final Model model) {
		model.addAttribute("applicantName", applicantName);
		model.addAttribute("applicationNumber", applicationNumber);
		model.addAttribute("applicationTypeId", applicationTypeId);
		model.addAttribute("plotNumber", plotNumber);
		model.addAttribute("sector", sector);
		model.addAttribute("ward", ward);
		if (fromDate == null) {
			model.addAttribute("fromDate", fromDate);
		} else {
			model.addAttribute("fromDate", DateUtils.toDefaultDateFormat(fromDate));
		}
		if (toDate == null) {
			model.addAttribute("toDate", toDate);
		} else {
			model.addAttribute("toDate", DateUtils.toDefaultDateFormat(toDate));
		}
		model.addAttribute("revenueWard", revenueWard);
		model.addAttribute("electionWard", electionWard);
		model.addAttribute("zone", zone);
		model.addAttribute("zoneId", zoneId);
		model.addAttribute("status", status);
		model.addAttribute("serviceType", serviceType);
		model.addAttribute("serviceTypeEnum", serviceTypeEnum);
		return "view-servicewise-appln-details-pl-rural";
	}

	
	@RequestMapping(value = "/servicewise-statusreport-pl-urban/view", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String viewStatusCountByServicetypeDetailsForUrban(@ModelAttribute final SearchBpaApplicationForm searchPLApplicationForm) {
		List<SearchBpaApplicationForm> searchResultList=new ArrayList<>();
		
		if(searchPLApplicationForm.getApplicationTypeId()==null) {
			searchPLApplicationForm.setApplicationTypeId(BTK_APPTYPE);
	        	searchResultList = plReportsService.searchForServicewiseStatus(searchPLApplicationForm);
	        	searchPLApplicationForm.setApplicationTypeId(ATK_APPTYPE);
	        	List<SearchBpaApplicationForm> searchATKApplnResultList = plReportsService.searchForServicewiseStatus(searchPLApplicationForm);
	        	searchResultList.addAll(searchATKApplnResultList);
	        }else {
	        	searchResultList = plReportsService.searchForServicewiseStatus(searchPLApplicationForm);
	        }
//		final List<SearchBpaApplicationForm> searchResultList = searchBpaApplicationService.search(searchBpaApplicationForm);
		return new StringBuilder(DATA)
				.append(toJSON(searchResultList, SearchBpaApplicationForm.class, SearchBpaApplicationFormAdaptor.class))
				.append("}")
				.toString();
	}
	
    
}

