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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.egov.bpa.master.entity.ApplicationSubType;
import org.egov.bpa.master.service.ApplicationSubTypeService;
import org.egov.bpa.master.service.ServiceTypeService;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationReport;
import org.egov.bpa.transaction.service.SearchBpaApplicationService;
import org.egov.bpa.transaction.service.oc.SearchOCService;
import org.egov.bpa.transaction.service.report.BpaReportsService;
import org.egov.bpa.transaction.service.report.OcReportsService;
import org.egov.bpa.web.controller.adaptor.CollectionSummaryHeadwiseReportAdaptor;
import org.egov.bpa.web.controller.adaptor.CollectionSummaryReportAdaptor;
import org.egov.bpa.web.controller.adaptor.ReceiptRegisterReportAdaptor;
import org.egov.bpa.web.controller.adaptor.SearchBpaApplicationFormAdaptor;
import org.egov.bpa.web.controller.adaptor.SearchBpaApplicationReportAdaptor;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.collection.constants.CollectionConstants;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.web.support.ui.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/reports")
public class OcReportsController extends BpaGenericApplicationController {
	private static final String SEARCH_BPA_APPLICATION_FORM = "searchBpaApplicationForm";
	private static final String DATA = "{ \"data\":";
	
	public static final Long BTK_APPTYPE = 3L;
    public static final Long ATK_APPTYPE = 5L;
    
    @Autowired
	private BpaReportsService bpaReportsService;
    
	@Autowired
	private OcReportsService ocReportsService;
	@Autowired
	private SearchOCService searchOCService;
	@Autowired
	private ServiceTypeService serviceTypeService;
	@Autowired
	private ApplicationSubTypeService applicationTypeService;
	
	private final String URBAN = "URBAN";
	private final String RURAL = "RURAL";
	private final String ALL = "ALL";
	
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
	

	@RequestMapping(value = "/servicewise-statusreport-oc/d/r", method = RequestMethod.GET)
	public String searchStatusCountByServicetypeFormForRural(final Model model) {
		prepareReportFormData(model,RURAL);
		model.addAttribute(SEARCH_BPA_APPLICATION_FORM, new SearchBpaApplicationForm());
		return "search-servicewise-status-report-oc-Rural";
	}
	
	@RequestMapping(value = "/servicewise-statusreport-oc/d/u", method = RequestMethod.GET)
	public String searchStatusCountByServicetypeFormForUrban(final Model model) {
		prepareReportFormData(model,URBAN);
		model.addAttribute(SEARCH_BPA_APPLICATION_FORM, new SearchBpaApplicationForm());
		return "search-servicewise-status-report-oc-Urban";
	}
	
	private void prepareReportFormData(Model model,String applicationType) {
		List<ApplicationSubType> applicationTypes = applicationTypeService.getBPAApplicationTypes();
    	if(applicationType.equals(URBAN))
    		model.addAttribute("appTypes",applicationTypes.stream().filter(appType -> !appType.getName().equalsIgnoreCase("Medium Risk"))
            .collect(Collectors.toList()));
    	else
    		model.addAttribute("appTypes",applicationTypes.stream().filter(appType -> appType.getName().equalsIgnoreCase("Medium Risk"))
            .collect(Collectors.toList()));
    	model.addAttribute("serviceTypeList", serviceTypeService.getAllActiveMainServiceTypes());
//    	model.addAttribute("designations", BpaConstants.getAvailableDesignations());
		
	}

	@RequestMapping(value = "/servicewise-statusreport-oc/d/u", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getStatusCountByServicetypeResultForUrban(final Model model) {
		Map<String, Long> map=new HashMap<String, Long>();
		
		SearchBpaApplicationForm aboveTwoKanal=new SearchBpaApplicationForm();
		aboveTwoKanal.setApplicationTypeId(5L);//Above two Kanal 
		final List<SearchBpaApplicationReport> aboveTwoKanalResultList = ocReportsService
				.getResultsByServicetypeAndStatus(aboveTwoKanal);
		map.put("Above two Kanal", getCount(aboveTwoKanalResultList));
		
		SearchBpaApplicationForm belowTwoKanal=new SearchBpaApplicationForm();
		belowTwoKanal.setApplicationTypeId(3L);//Below two Kanal
		final List<SearchBpaApplicationReport> belowTwoKanalResultList = ocReportsService
				.getResultsByServicetypeAndStatus(belowTwoKanal);
		
		map.put("Below two Kanal", getCount(belowTwoKanalResultList));
		
		Gson gson = new Gson(); 
		String json = gson.toJson(map); 
		
		return json;
	}
	
	@RequestMapping(value = "/servicewise-statusreport-oc/d/r", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getStatusCountByServicetypeResultForRural(final Model model) {
		Map<String, Long> map=new HashMap<String, Long>();
		
		SearchBpaApplicationForm rural=new SearchBpaApplicationForm();
		rural.setApplicationTypeId(4L);//RURAL
		
		final List<SearchBpaApplicationReport> ruralResultList = ocReportsService
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

	
	@RequestMapping(value = "/servicewise-statusreport-oc-urban", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getStatusCountByServicetypeResultForUrban(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
		final List<SearchBpaApplicationReport> searchResultList = ocReportsService
				.getResultsByServicetypeAndStatusForUrban(searchBpaApplicationForm);
		return new StringBuilder(DATA)
				.append(toJSON(searchResultList, SearchBpaApplicationReport.class, SearchBpaApplicationReportAdaptor.class))
				.append("}")
				.toString();
	}

	
	@RequestMapping(value = "/servicewise-statusreport-oc/view/d/u", method = RequestMethod.GET)
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
		return "view-servicewise-appln-details-oc-urban";
	}

	
	@RequestMapping(value = "/servicewise-statusreport-oc-urban/view", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String viewStatusCountByServicetypeDetailsForUrban(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
		List<SearchBpaApplicationForm> searchResultList=new ArrayList<>();
		
		if(searchBpaApplicationForm.getApplicationTypeId()==null) {
	        	searchBpaApplicationForm.setApplicationTypeId(BTK_APPTYPE);
	        	searchResultList = searchOCService.searchForServicewiseStatus(searchBpaApplicationForm);
	        	searchBpaApplicationForm.setApplicationTypeId(ATK_APPTYPE);
	        	List<SearchBpaApplicationForm> searchATKApplnResultList = searchOCService.searchForServicewiseStatus(searchBpaApplicationForm);
	        	searchResultList.addAll(searchATKApplnResultList);
	        }else {
	        	searchResultList = searchOCService.searchForServicewiseStatus(searchBpaApplicationForm);
	        }
//		final List<SearchBpaApplicationForm> searchResultList = searchBpaApplicationService.search(searchBpaApplicationForm);
		return new StringBuilder(DATA)
				.append(toJSON(searchResultList, SearchBpaApplicationForm.class, SearchBpaApplicationFormAdaptor.class))
				.append("}")
				.toString();
	}
	
	
	@RequestMapping(value = "/receiptRegister-oc/d/u", method = RequestMethod.GET)
	public String searchOCRegisteregisterForm(final Model model) {
		prepareReportFormData(model,URBAN);
		model.addAttribute("paymentModes", paymentModes);
		model.addAttribute(SEARCH_BPA_APPLICATION_FORM, new SearchBpaApplicationForm());
		return "receipt-register-report-oc-urban";
	}
	

	@RequestMapping(value = "/receiptRegister-oc/d/u", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getOCRegisterResultUrban(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
		 List<Long> AppTypeList = new ArrayList<>();  
		if (searchBpaApplicationForm.getApplicationTypeId() == null) {
			  AppTypeList.addAll(Arrays.asList(3L,5L));
	        }else
	        	AppTypeList.add(searchBpaApplicationForm.getApplicationTypeId());
		
		
		return new DataTable<>(bpaReportsService.getReceiptRegisterReportDetailsForOC(searchBpaApplicationForm,AppTypeList),
				searchBpaApplicationForm.draw())
				.toJson(ReceiptRegisterReportAdaptor.class);
	}

	@RequestMapping(value = "/collectionSummaryOC/d/u", method = RequestMethod.GET)
	public String searchCollectionSummaryForm(final Model model) {
		prepareReportFormData(model,URBAN);
		model.addAttribute("paymentModes", paymentModes);
		model.addAttribute(SEARCH_BPA_APPLICATION_FORM, new SearchBpaApplicationForm());
		return "collection-summary-report-oc-urban";
	}
	

	@RequestMapping(value = "/collectionSummaryOC/d/u", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getCollectionSummaryUrban(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
		 List<Long> AppTypeList = new ArrayList<>();  
			  AppTypeList.addAll(Arrays.asList(3L,5L));
			  String source=ALL;
			  searchBpaApplicationForm.setServiceType("OccupancyCertificate");
		
		return new DataTable<>(bpaReportsService.getCollectionSummaryReportDetailsForOCUrban(searchBpaApplicationForm,AppTypeList,source),
				searchBpaApplicationForm.draw())
				.toJson(CollectionSummaryReportAdaptor.class);
	}
	
	@RequestMapping(value = "/collectionSummaryHeadwiseOC/d/u", method = RequestMethod.GET)
	public String searchCollectionSummaryHeadwiseOCForm(final Model model) {
		prepareReportFormData(model,URBAN);
		model.addAttribute("paymentModes", paymentModes);
		model.addAttribute(SEARCH_BPA_APPLICATION_FORM, new SearchBpaApplicationForm());
		return "collection-summary-headwise-report-oc-urban";
	}
	

	@RequestMapping(value = "/collectionSummaryHeadwiseOC/d/u", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getCollectionSummaryHeadwiseOCUrban(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
		 List<Long> AppTypeList = new ArrayList<>();  
		 	if(searchBpaApplicationForm.getApplicationTypeId()!=null) 
		 		AppTypeList.add(searchBpaApplicationForm.getApplicationTypeId());
		 	else
			  AppTypeList.addAll(Arrays.asList(3L,5L));
		 	
			  String source=ALL;
			  searchBpaApplicationForm.setServiceType("OccupancyCertificate");
		
		return new DataTable<>(bpaReportsService.getCollectionSummaryHeadwiseReportDetailsForOCUrban(searchBpaApplicationForm,AppTypeList,source),
				searchBpaApplicationForm.draw())
				.toJson(CollectionSummaryHeadwiseReportAdaptor.class);
	}
}

