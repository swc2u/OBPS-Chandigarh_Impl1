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
package org.egov.bpa.web.controller.transaction.occupancy;

import static org.egov.bpa.utils.BpaConstants.AUTO_CANCEL_UNATTENDED_DOCUMENT_SCRUTINY_OC;
import static org.egov.bpa.utils.BpaConstants.BOUNDARY_TYPE_CITY;
import static org.egov.bpa.utils.BpaConstants.BPASTATUS_MODULETYPE;
import static org.egov.bpa.utils.BpaConstants.REVENUE_HIERARCHY_TYPE;
import static org.egov.bpa.utils.BpaConstants.WARD;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.egov.bpa.master.entity.ApplicationSubType;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.entity.dto.SearchPendingItemsForm;
import org.egov.bpa.transaction.entity.oc.OCNocDocuments;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.entity.oc.OccupancyNocApplication;
import org.egov.bpa.transaction.service.oc.OCLetterToPartyService;
import org.egov.bpa.transaction.service.oc.OcInspectionService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateNocService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
import org.egov.bpa.transaction.service.oc.SearchOCService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.web.controller.adaptor.SearchBpaApplicationAdaptor;
import org.egov.bpa.web.controller.adaptor.SearchBpaPendingTaskAdaptor;
import org.egov.bpa.web.controller.adaptor.SearchOCPendingTaskAdaptor;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.eis.entity.Employee;
import org.egov.eis.entity.Jurisdiction;
import org.egov.eis.service.EmployeeService;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.BoundaryType;
import org.egov.infra.admin.master.service.BoundaryTypeService;
import org.egov.infra.admin.master.service.CrossHierarchyService;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.web.support.ui.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/application")
public class SearchOccupancyCertificateController extends BpaGenericApplicationController {

    private static final String APPLICATION_HISTORY = "applicationHistory";
    private static final String SEARCH_OC_PENDING_ITEM_FORM = "searchOCPendingItemsForm";
    private static final String SEARCH_OC_PENDING_ITEM_FORM_GRAPH = "searchOCPendingItemsFormG";
    private static final Long RURAL_ID = 4L;
    private static final String URBAN ="URBAN";
    private static final String RURAL ="RURAL";

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private BoundaryTypeService boundaryTypeService;
    @Autowired
    private CrossHierarchyService crossHierarchyService;
    @Autowired
    private SearchOCService searchOCService;
    @Autowired
    private OCLetterToPartyService lettertoPartyService;
    @Autowired
    private OccupancyCertificateService occupancyCertificateService;
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    private OccupancyCertificateNocService ocNocService;

    @GetMapping("/occupancy-certificate/search")
    public String showSearchApprovedforFee(final Model model) {
        prepareFormData(model);
        model.addAttribute("serviceTypeList", serviceTypeService.getOccupancyCertificateRequiredServiceTypes());
        model.addAttribute("searchBpaApplicationForm", new SearchBpaApplicationForm());
        return "search-occupany-certificate";
    }

    @PostMapping(value = "/occupancy-certificate/search", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String searchRegisterStatusMarriageRecords(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
        return new DataTable<>(searchOCService.pagedSearch(searchBpaApplicationForm),
                searchBpaApplicationForm.draw())
                        .toJson(SearchBpaApplicationAdaptor.class);
    }
    
    
    @GetMapping("/searchOCPendingItems/d/u")
    public String showSearchOCApprovedforFeeGrapUrban(final Model model) {
    	prepareOCReportFormData(model,URBAN);
    	model.addAttribute(SEARCH_OC_PENDING_ITEM_FORM, new SearchPendingItemsForm());
    	model.addAttribute(SEARCH_OC_PENDING_ITEM_FORM_GRAPH, new SearchPendingItemsForm());
        return "search-oc-pending-task-urban";
    }
    
    @GetMapping("/searchOCPendingItems/d/r")
    public String showSearchOCApprovedforFeeGrapRural(final Model model) {
    	prepareOCReportFormData(model,RURAL);
    	model.addAttribute("designations", BpaConstants.getAvailableDesignations());
    	 model.addAttribute(SEARCH_OC_PENDING_ITEM_FORM, new SearchPendingItemsForm());
    	 model.addAttribute(SEARCH_OC_PENDING_ITEM_FORM_GRAPH, new SearchPendingItemsForm());
          return "search-oc-pending-task-rural";
    }
    
    private void prepareOCReportFormData(Model model, String applicationType) {
    	model.addAttribute("designations", BpaConstants.getAvailableDesignations());
    	
    	List<ApplicationSubType> applicationTypes = applicationTypeService.getBPAApplicationTypes();
    	if(applicationType.equals(URBAN))
    		model.addAttribute("appTypes",applicationTypes.stream().filter(appType -> !appType.getName().equalsIgnoreCase("Medium Risk"))
            .collect(Collectors.toList()));
    	else
    		model.addAttribute("appTypes",applicationTypes.stream().filter(appType -> appType.getName().equalsIgnoreCase("Medium Risk"))
            .collect(Collectors.toList()));
    	model.addAttribute("applnStatusList", bpaStatusService.findAllByModuleType(BPASTATUS_MODULETYPE));
	}
    
    @PostMapping(value = "/searchOCPendingItems/d/u", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String showSearchOCPendingItemsRecordsUrban(@ModelAttribute final SearchPendingItemsForm searchPendingItemsFormG) {
    	
    	Map<String, Long> map=new HashMap<String, Long>(); 
    	fillterData(searchOCService.pagedSearchForPendingTaskGraph(searchPendingItemsFormG), map);
        
        Gson gson = new Gson();  
		String json = gson.toJson(map); 
		return json;
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
    
    @PostMapping(value = "/searchOCPendingItems/d/r", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String showSearchOCPendingItemsRecordsRural(@ModelAttribute final SearchPendingItemsForm searchPendingItemsFormG) {

    	Map<String, Long> map=new HashMap<String, Long>(); 
    	
    	fillterData(searchOCService.pagedSearchForPendingTaskGraph(searchPendingItemsFormG), map);
        
        Gson gson = new Gson();  
		String json = gson.toJson(map); 
		return json;
    }

    
    @PostMapping(value = "/searchOCPendingItems", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String showSearchOCPendingItemsRecords(@ModelAttribute SearchPendingItemsForm searchPendingItemsForm) {
        return new DataTable<>(searchOCService.pagedSearchForPendingTask(searchPendingItemsForm),
        		searchPendingItemsForm.draw())
                        .toJson(SearchOCPendingTaskAdaptor.class);
    }
    
    
    

    @GetMapping("/occupancycertificate/viewdetails/{applicationNumber}")
    public String editOccupancyCertificateApplication(@PathVariable final String applicationNumber, final Model model,
            final HttpServletRequest request) {
        OccupancyCertificate oc = occupancyCertificateService.findByApplicationNumber(applicationNumber);
        List<OccupancyNocApplication> nocApplication = ocNocService.findByOCApplicationNumber(applicationNumber);
        model.addAttribute("nocApplication",nocApplication);
        for (OCNocDocuments nocDocument : oc.getNocDocuments()) {
			for (OccupancyNocApplication ona : nocApplication) {
				if(nocDocument.getNocDocument().getServiceChecklist().getChecklist().getCode().equalsIgnoreCase(ona.getBpaNocApplication().getNocType())) {
					nocDocument.setOcNoc(ona);
				}
			}
		}
        model.addAttribute("occupancyCertificate", oc);
        model.addAttribute("citizenOrBusinessUser", bpaUtils.logedInuseCitizenOrBusinessUser());
        model.addAttribute(APPLICATION_HISTORY,
                workflowHistoryService.getHistoryForOC(oc.getAppointmentSchedules(), oc.getCurrentState(), oc.getStateHistory()));
        final OcInspectionService inspectionService = (OcInspectionService) specificNoticeService
                .find(OcInspectionService.class, specificNoticeService.getCityDetails());
        model.addAttribute("inspectionList", inspectionService.findByOcOrderByIdAsc(oc));
        model.addAttribute("letterToPartyList", lettertoPartyService.findAllByOC(oc));
        buildReceiptDetails(oc.getDemand().getEgDemandDetails(), oc.getReceipts());
        return "search-occupancy-certificate-view";
    }

    @GetMapping("/occupancy-certificate/search/document-scrutiny")
    public String showDocumentScrutinyPendingRecords(final Model model) {
        Set<Boundary> employeeMappedZone = new HashSet<>();
        Set<Boundary> mappedElectionWard = new HashSet<>();
        Set<Boundary> electionWards = new HashSet<>();
        Set<Boundary> revenueWards = new HashSet<>();
        Set<Boundary> revWards = new HashSet<>();
        String isUnattendedCancelled=bpaUtils.getAppconfigValueByKeyName(AUTO_CANCEL_UNATTENDED_DOCUMENT_SCRUTINY_OC);
        BoundaryType revenueType = boundaryTypeService.getBoundaryTypeByNameAndHierarchyTypeName(WARD, REVENUE_HIERARCHY_TYPE);
        final Employee employee = employeeService.getEmployeeById(securityUtils.getCurrentUser().getId());
        for (Jurisdiction jurisdiction : employee.getJurisdictions()) {
            if (!BOUNDARY_TYPE_CITY.equals(jurisdiction.getBoundaryType().getName())) {
                mappedElectionWard.add(jurisdiction.getBoundary());
            }
        }
        for (Boundary boundary : mappedElectionWard) {
            List<Boundary> b = crossHierarchyService
                    .getParentBoundaryByChildBoundaryAndParentBoundaryType(boundary.getId(), revenueType.getId());
            revenueWards.addAll(b);
        }

        for (Boundary boundary : revenueWards) {
            employeeMappedZone.add(boundary.getParent());
            revWards.addAll(boundaryService.getActiveChildBoundariesByBoundaryId(boundary.getParent().getId()));
            for (Boundary revenue : revWards) {
                electionWards.addAll(crossHierarchyService
                        .findChildBoundariesByParentBoundaryIdParentBoundaryTypeAndChildBoundaryType(WARD,
                                REVENUE_HIERARCHY_TYPE, WARD, revenue.getId()));
            }
        }
        model.addAttribute("searchBpaApplicationForm", new SearchBpaApplicationForm());
        model.addAttribute("employeeMappedZone", employeeMappedZone);
        model.addAttribute("mappedRevenueBoundries", revWards);
        model.addAttribute("mappedElectionBoundries", electionWards);
        model.addAttribute("isUnattendedCancelled",isUnattendedCancelled);
        return "search-document-scrutiny-oc";
    }

    @PostMapping(value = "/occupancy-certificate/search/document-scrutiny", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String searchDocumentScrutinyPendingRecords(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
        return new DataTable<>(searchOCService.searchForDocumentScrutinyPending(searchBpaApplicationForm),
                searchBpaApplicationForm.draw())
                        .toJson(SearchBpaApplicationAdaptor.class);
    }

}
