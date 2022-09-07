package org.egov.collection.web.actions.citizen.reports;

import static org.egov.bpa.utils.BpaConstants.BOUNDARY_TYPE_CITY;
import static org.egov.bpa.utils.BpaConstants.OCCUPANCY_CERTIFICATE_NOTICE_TYPE;
import static org.egov.bpa.utils.BpaConstants.REVENUE_HIERARCHY_TYPE;
import static org.egov.bpa.utils.BpaConstants.WARD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.egov.bpa.master.entity.ApplicationSubType;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.entity.dto.SearchPendingItemsForm;
import org.egov.collection.constants.CollectionConstants;
import org.egov.collection.service.CollectionReportHeadWiseService;
import org.egov.eis.entity.Employee;
import org.egov.eis.entity.Jurisdiction;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.BoundaryType;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.web.support.ui.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import org.egov.bpa.master.service.ApplicationSubTypeService;

@Controller
@RequestMapping(value = "/collection-report")
public class ReceiptRegisterReportController {
	@Autowired
	ApplicationSubTypeService applicationTypeService;
	
	@Autowired
    private CollectionReportHeadWiseService reportService;
	
	final private String URBAN = "URBAN";
	final private String RURAL = "RURAL";
	
	private final Map<String, String> paymentModes = createPaymentModeList();
	
	private Map<String, String> createPaymentModeList() {
        final Map<String, String> paymentModesMap = new HashMap<String, String>(0);
        paymentModesMap.put(CollectionConstants.INSTRUMENTTYPE_CASH, CollectionConstants.INSTRUMENTTYPE_CASH);
        paymentModesMap.put(CollectionConstants.INSTRUMENTTYPE_CHEQUEORDD, CollectionConstants.INSTRUMENTTYPE_CHEQUEORDD);
        paymentModesMap.put(CollectionConstants.INSTRUMENTTYPE_CARD, CollectionConstants.INSTRUMENTTYPE_CARD);
        paymentModesMap.put(CollectionConstants.INSTRUMENTTYPE_BANK, CollectionConstants.INSTRUMENTTYPE_BANK);
        paymentModesMap.put(CollectionConstants.INSTRUMENTTYPE_ONLINE, CollectionConstants.INSTRUMENTTYPE_ONLINE);
        return paymentModesMap;
    }
	
	@RequestMapping(value = "/receiptRegisterReport/d/u", method = RequestMethod.GET)
	public String searchBpaRegisterForm(final Model model) {
		prepareUserBndryAndFormData(model,URBAN);
		return "receipt-register-report-urban";
	}

	@RequestMapping(value = "/receiptRegisterReport/d/u", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getUrbanRegisterResult(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
		List<Long> userIds = new ArrayList<>();
		return null;
	}
	
	
	private void prepareUserBndryAndFormData(Model model,String applicationType) {
		List<ApplicationSubType> applicationTypes = applicationTypeService.getBPAApplicationTypes();
    	if(applicationType.equals(URBAN))
    		model.addAttribute("appTypes",applicationTypes.stream().filter(appType -> !appType.getName().equalsIgnoreCase("Medium Risk"))
            .collect(Collectors.toList()));
    	else
    		model.addAttribute("appTypes",applicationTypes.stream().filter(appType -> appType.getName().equalsIgnoreCase("Medium Risk"))
            .collect(Collectors.toList()));
    	
    	model.addAttribute("paymentModes", paymentModes);
	}
	
	  @PostMapping(value = "/receiptRegisterReport/d/u", produces = MediaType.TEXT_PLAIN_VALUE)
	    @ResponseBody
	    public String showSearchPendingItemsRecordsUrban(@ModelAttribute final SearchPendingItemsForm searchPendingItemsFormG) {
	    	
	    	Map<String, Long> map=new HashMap<String, Long>(); 
	    	
//	    	fillterData(searchBpaApplicationService.pagedSearchForPendingTaskGraph(searchPendingItemsFormG), map);
	        
	        Gson gson = new Gson();  
			String json = gson.toJson(map); 
			return json;
	    }
	  
	  @PostMapping(value = "/receiptRegisterReport/u", produces = MediaType.TEXT_PLAIN_VALUE)
	    @ResponseBody
	    public String showSearchUrbanPendingItemsRecords(@ModelAttribute SearchPendingItemsForm searchPendingItemsForm) {
//		  reportService.getCollectionSummaryReportForUrban(getFromDate(), getToDate(), getPaymentMode(),
//	                getSource(), getGlCode(), getBranchId(),getApplicationTypeId(),appTypeList));
//	    	return new DataTable<>(searchBpaApplicationService.pagedSearchForUrbanPendingTask(searchPendingItemsForm),
//	        		searchPendingItemsForm.draw())
//	                        .toJson(SearchBpaPendingTaskAdaptor.class);
		  return null;
	    }
}
