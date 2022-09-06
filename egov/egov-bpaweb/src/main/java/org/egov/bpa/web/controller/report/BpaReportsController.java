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

import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_ONEDAYPERMIT;
import static org.egov.bpa.utils.BpaConstants.BOUNDARY_TYPE_CITY;
import static org.egov.bpa.utils.BpaConstants.NOCMODULE;
import static org.egov.bpa.utils.BpaConstants.OCCUPANCY_CERTIFICATE_NOTICE_TYPE;
import static org.egov.bpa.utils.BpaConstants.REVENUE_HIERARCHY_TYPE;
import static org.egov.bpa.utils.BpaConstants.WARD;
import static org.egov.infra.utils.JsonUtils.toJSON;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.egov.bpa.master.entity.ApplicationSubType;
import org.egov.bpa.master.service.ApplicationSubTypeService;
import org.egov.bpa.master.service.NocConfigurationService;
import org.egov.bpa.master.service.ServiceTypeService;
import org.egov.bpa.transaction.entity.dto.NocDetailsHelper;
import org.egov.bpa.transaction.entity.dto.PersonalRegisterHelper;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationReport;
import org.egov.bpa.transaction.entity.dto.SlotDetailsHelper;
import org.egov.bpa.transaction.service.FailureInSchedulerService;
import org.egov.bpa.transaction.service.SearchBpaApplicationService;
import org.egov.bpa.transaction.service.report.BpaNocApplicationReportService;
import org.egov.bpa.transaction.service.report.BpaReportsService;
import org.egov.bpa.transaction.service.report.PersonalRegisterReportService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.web.controller.adaptor.BpaRegisterReportAdaptor;
import org.egov.bpa.web.controller.adaptor.NocDetailsAdaptor;
import org.egov.bpa.web.controller.adaptor.SearchBpaApplicationFormAdaptor;
import org.egov.bpa.web.controller.adaptor.SearchBpaApplicationReportAdaptor;
import org.egov.bpa.web.controller.adaptor.SearchPersonalRegisterAdaptor;
import org.egov.bpa.web.controller.adaptor.SlotDetailsAdaptor;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.commons.service.OccupancyService;
import org.egov.eis.entity.Employee;
import org.egov.eis.entity.Jurisdiction;
import org.egov.eis.service.EmployeeService;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.BoundaryType;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.BoundaryTypeService;
import org.egov.infra.admin.master.service.CrossHierarchyService;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.web.support.ui.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/reports")
public class BpaReportsController extends BpaGenericApplicationController {
	private static final String SEARCH_BPA_APPLICATION_FORM = "searchBpaApplicationForm";
	private static final String FAILURE_IN_SCHEDULER_REPORT = "search-scheduler-failure-records-report";
	private static final String PERSONAL_REGISTER_REPORT = "personal-register-report";
	private static final String DATA = "{ \"data\":";

	@Autowired
	private BpaReportsService bpaReportsService;
	@Autowired
	private SearchBpaApplicationService searchBpaApplicationService;
	@Autowired
	private FailureInSchedulerService failureInSchedulerService;
	@Autowired
	private BoundaryTypeService boundaryTypeService;
	@Autowired
	private CrossHierarchyService crossHierarchyService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ServiceTypeService serviceTypeService;
	@Autowired
	private OccupancyService occupancyService;
	@Autowired
	private SecurityUtils securityUtils;
	@Autowired
	private ApplicationSubTypeService applicationTypeService;
	@Autowired
	private PersonalRegisterReportService personalRegisterReportService;
	@Autowired
	private NocConfigurationService nocConfigService;
	@Autowired
	private BpaNocApplicationReportService nocReportService;
	
	private final String URBAN = "URBAN";
	private final String RURAL = "RURAL";
	

	@RequestMapping(value = "/servicewise-statusreport", method = RequestMethod.GET)
	public String searchStatusCountByServicetypeForm(final Model model) {
		prepareFormData(model);
		model.addAttribute(SEARCH_BPA_APPLICATION_FORM, new SearchBpaApplicationForm());
		return "search-servicewise-status-report";
	}
	
	@RequestMapping(value = "/servicewise-statusreport/d/r", method = RequestMethod.GET)
	public String searchStatusCountByServicetypeFormForRural(final Model model) {
		prepareReportFormData(model,RURAL);
		model.addAttribute(SEARCH_BPA_APPLICATION_FORM, new SearchBpaApplicationForm());
		return "search-servicewise-status-report-Rural";
	}
	
	@RequestMapping(value = "/servicewise-statusreport/d/u", method = RequestMethod.GET)
	public String searchStatusCountByServicetypeFormForUrban(final Model model) {
		prepareReportFormData(model,URBAN);
		model.addAttribute(SEARCH_BPA_APPLICATION_FORM, new SearchBpaApplicationForm());
		return "search-servicewise-status-report-Urban";
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

	@RequestMapping(value = "/servicewise-statusreport/d/u", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getStatusCountByServicetypeResultForUrban(final Model model) {
		Map<String, Long> map=new HashMap<String, Long>();
		
		SearchBpaApplicationForm aboveTwoKanal=new SearchBpaApplicationForm();
		aboveTwoKanal.setApplicationTypeId(5L);//Above two Kanal 
		final List<SearchBpaApplicationReport> aboveTwoKanalResultList = bpaReportsService
				.getResultsByServicetypeAndStatus(aboveTwoKanal);
		map.put("Above two Kanal", getCount(aboveTwoKanalResultList));
		
		SearchBpaApplicationForm belowTwoKanal=new SearchBpaApplicationForm();
		belowTwoKanal.setApplicationTypeId(3L);//Below two Kanal
		final List<SearchBpaApplicationReport> belowTwoKanalResultList = bpaReportsService
				.getResultsByServicetypeAndStatus(belowTwoKanal);
		
		map.put("Below two Kanal", getCount(belowTwoKanalResultList));
		
		Gson gson = new Gson(); 
		String json = gson.toJson(map); 
		
		return json;
	}
	
	@RequestMapping(value = "/servicewise-statusreport/d/r", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getStatusCountByServicetypeResultForRural(final Model model) {
		Map<String, Long> map=new HashMap<String, Long>();
		
		SearchBpaApplicationForm rural=new SearchBpaApplicationForm();
		rural.setApplicationTypeId(4L);//RURAL
		
		final List<SearchBpaApplicationReport> ruralResultList = bpaReportsService
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

	@RequestMapping(value = "/servicewise-statusreport", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getStatusCountByServicetypeResult(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
		final List<SearchBpaApplicationReport> searchResultList = bpaReportsService
				.getResultsByServicetypeAndStatus(searchBpaApplicationForm);
		return new StringBuilder(DATA)
				.append(toJSON(searchResultList, SearchBpaApplicationReport.class, SearchBpaApplicationReportAdaptor.class))
				.append("}")
				.toString();
	}

	@RequestMapping(value = "/statusreport-api", method = RequestMethod.GET)
	@ResponseBody
	public List<SearchBpaApplicationForm> getStatusReportRestAPI(
			@RequestParam(name = "appType", required = false) String appType,
			@RequestParam(name = "appNumber", required = false) String appNumber,
			@RequestParam(name = "appName", required = false) String appName,
			@RequestParam(name = "serviceType", required = false) String serviceType,
			@RequestParam(name = "fromDate", required = false) String fromDate,
			@RequestParam(name = "toDate", required = false) String toDate,
			@RequestParam(name = "status", required = false) String status,
			@RequestParam(name = "ward", required = false) String ward) {
		SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy");

		SearchBpaApplicationForm searchBpaApplicationForm = new SearchBpaApplicationForm();
		if (!StringUtils.isEmpty(appType)) {
			if (appType.equalsIgnoreCase("Above two Kanal"))
				searchBpaApplicationForm.setApplicationTypeId(5L);
			else if (appType.equalsIgnoreCase("Below two Kanal"))
				searchBpaApplicationForm.setApplicationTypeId(3L);
			else if (appType.equalsIgnoreCase("RURAL"))
				searchBpaApplicationForm.setApplicationTypeId(4L);
			else if (appType.equalsIgnoreCase("DPC / Plinth Level Certificate"))
				searchBpaApplicationForm.setApplicationTypeId(7L);
			else if (appType.equalsIgnoreCase("Occupancy Certificate"))
				searchBpaApplicationForm.setApplicationTypeId(6L);

		}
		if (!StringUtils.isEmpty(serviceType)) {
			if (serviceType.equalsIgnoreCase("New Construction"))
				searchBpaApplicationForm.setServiceTypeId(59L);
			else if (serviceType.equalsIgnoreCase("Reconstruction"))
				searchBpaApplicationForm.setServiceTypeId(61L);
			else if (serviceType.equalsIgnoreCase("Alteration"))
				searchBpaApplicationForm.setServiceTypeId(64L);
			else if (serviceType.equalsIgnoreCase("Addition or Extension"))
				searchBpaApplicationForm.setServiceTypeId(64L);
		}

		if (!StringUtils.isEmpty(appNumber)) {
			searchBpaApplicationForm.setApplicationNumber(appNumber);
		}
		if (!StringUtils.isEmpty(appName)) {
			searchBpaApplicationForm.setApplicantName(appName);
		}
		if (!StringUtils.isEmpty(status)) {
			searchBpaApplicationForm.setStatus(status);
		}
		if (!StringUtils.isEmpty(ward)) {
			searchBpaApplicationForm.setWard(ward);
		}

		try {
			if (!StringUtils.isEmpty(fromDate)) {
				searchBpaApplicationForm.setFromDate(mdyFormat.parse(fromDate));
			}
			if (!StringUtils.isEmpty(toDate)) {
				searchBpaApplicationForm.setToDate(mdyFormat.parse(toDate));
			}
		} catch (ParseException e) {}

		final List<SearchBpaApplicationForm> searchResultList = searchBpaApplicationService
				.search(searchBpaApplicationForm);
		
		return searchResultList;
	}

	@RequestMapping(value = "/servicewise-summary-api", method = RequestMethod.GET)
	@ResponseBody
	public List<SearchBpaApplicationReport> getStatusReportSummaryRestAPI(
			@RequestParam(name = "appType", required = false) String appType,
			@RequestParam(name = "appNumber", required = false) String appNumber,
			@RequestParam(name = "appName", required = false) String appName,
			@RequestParam(name = "serviceType", required = false) String serviceType,
			@RequestParam(name = "fromDate", required = false) String fromDate,
			@RequestParam(name = "toDate", required = false) String toDate) {
		SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy");

		SearchBpaApplicationForm searchBpaApplicationForm = new SearchBpaApplicationForm();
		if (!StringUtils.isEmpty(appType)) {
			if (appType.equalsIgnoreCase("Above two Kanal"))
				searchBpaApplicationForm.setApplicationTypeId(5L);
			else if (appType.equalsIgnoreCase("Below two Kanal"))
				searchBpaApplicationForm.setApplicationTypeId(3L);
			else if (appType.equalsIgnoreCase("RURAL"))
				searchBpaApplicationForm.setApplicationTypeId(4L);
			else if (appType.equalsIgnoreCase("DPC / Plinth Level Certificate"))
				searchBpaApplicationForm.setApplicationTypeId(7L);
			else if (appType.equalsIgnoreCase("Occupancy Certificate"))
				searchBpaApplicationForm.setApplicationTypeId(6L);

		}
		if (!StringUtils.isEmpty(serviceType)) {
			if (serviceType.equalsIgnoreCase("New Construction"))
				searchBpaApplicationForm.setServiceTypeId(59L);
			else if (serviceType.equalsIgnoreCase("Reconstruction"))
				searchBpaApplicationForm.setServiceTypeId(61L);
			else if (serviceType.equalsIgnoreCase("Alteration"))
				searchBpaApplicationForm.setServiceTypeId(64L);
			else if (serviceType.equalsIgnoreCase("Addition or Extension"))
				searchBpaApplicationForm.setServiceTypeId(64L);
		}

		if (!StringUtils.isEmpty(appNumber)) {
			searchBpaApplicationForm.setApplicationNumber(appNumber);
		}
		if (!StringUtils.isEmpty(appName)) {
			searchBpaApplicationForm.setApplicantName(appName);
		}
		try {
			if (!StringUtils.isEmpty(fromDate)) {
				searchBpaApplicationForm.setFromDate(mdyFormat.parse(fromDate));
			}
			if (!StringUtils.isEmpty(toDate)) {
				searchBpaApplicationForm.setToDate(mdyFormat.parse(toDate));
			}
		} catch (ParseException e) {}

		
		final List<SearchBpaApplicationReport> searchResultList = bpaReportsService.getResultsByServicetypeAndStatus(searchBpaApplicationForm);
		return searchResultList;
	}
	
	
	@RequestMapping(value = "/servicewise-statusreport/view", method = RequestMethod.GET)
	public String viewStatusCountByServicetypeDetails(@RequestParam final String applicantName,
													  @RequestParam final String applicationNumber,
													  @RequestParam final Long ward, @RequestParam final Date fromDate,
													  @RequestParam final Date toDate, @RequestParam final Long revenueWard, @RequestParam final Long electionWard,
													  @RequestParam final Long zoneId, @RequestParam final String status, @RequestParam final String serviceType,
													  @RequestParam final String zone, @RequestParam final String serviceTypeEnum, final Model model) {
		model.addAttribute("applicantName", applicantName);
		model.addAttribute("applicationNumber", applicationNumber);
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
		return "view-servicewise-appln-details";
	}
	
	@RequestMapping(value = "/servicewise-statusreport/view/d/u", method = RequestMethod.GET)
	public String viewUrbanStatusCountByServicetypeDetails(@RequestParam final String applicantName,
													  @RequestParam final String applicationNumber,
													  @RequestParam final Long ward, @RequestParam final Date fromDate,
													  @RequestParam final Date toDate, @RequestParam final Long revenueWard, @RequestParam final Long electionWard,
													  @RequestParam final Long zoneId, @RequestParam final String status, @RequestParam final String serviceType,
													  @RequestParam final String zone, @RequestParam final String serviceTypeEnum, final Model model) {
		model.addAttribute("applicantName", applicantName);
		model.addAttribute("applicationNumber", applicationNumber);
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
		return "view-servicewise-appln-details-urban";
	}

	@RequestMapping(value = "/servicewise-statusreport/view", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String viewStatusCountByServicetypeDetails(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
		final List<SearchBpaApplicationForm> searchResultList = searchBpaApplicationService.search(searchBpaApplicationForm);
		return new StringBuilder(DATA)
				.append(toJSON(searchResultList, SearchBpaApplicationForm.class, SearchBpaApplicationFormAdaptor.class))
				.append("}")
				.toString();
	}

	@RequestMapping(value = "/zonewisedetails", method = RequestMethod.GET)
	public String searchZoneWiseServicesForm(final Model model) {
		prepareFormData(model);
		model.addAttribute(SEARCH_BPA_APPLICATION_FORM, new SearchBpaApplicationForm());
		return "search-zonewise-report";
	}

	@RequestMapping(value = "/zonewisedetails", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getZoneWiseServicesResult(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
		final List<SearchBpaApplicationReport> searchResultList = bpaReportsService
				.getResultsForEachServicetypeByZone(searchBpaApplicationForm);
		return new StringBuilder(DATA)
				.append(toJSON(searchResultList, SearchBpaApplicationReport.class, SearchBpaApplicationReportAdaptor.class))
				.append("}")
				.toString();
	}

	@RequestMapping(value = "/slotdetails/{applicationType}", method = RequestMethod.GET)
	public String searchSlotDetailsForm(@PathVariable String applicationType, final Model model) {
		prepareFormData(model);
		model.addAttribute("slotDetailsHelper", new SlotDetailsHelper());
		model.addAttribute("applicationType", applicationType);
		List<ApplicationSubType> appTypes = applicationTypeService.getAllSlotRequiredApplicationTypes();
		List<ApplicationSubType> slotMappingApplTypes = new ArrayList<>();
                for (ApplicationSubType applType : appTypes)
                    if (applType.getName().equals(APPLICATION_TYPE_ONEDAYPERMIT))
                        continue;
                    else
                        slotMappingApplTypes.add(applType);
        model.addAttribute("slotMappingApplTypes", slotMappingApplTypes);                
		model.addAttribute("searchByNoOfDays", BpaConstants.getSearchByNoOfDays());
		if ("onedaypermit".equals(applicationType))
			return "search-onedaypermit-slotdetails-report";
		else
			return "search-regular-slotdetails-report";
	}

	@RequestMapping(value = "/slotdetails/{applicationType}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getSlotDetailsResult(@PathVariable String applicationType, @ModelAttribute final SlotDetailsHelper slotDetailsHelper) {
		return new DataTable<>(bpaReportsService.searchSlotDetails(slotDetailsHelper, applicationType),
				slotDetailsHelper.draw())
				.toJson(SlotDetailsAdaptor.class);
	}

	@RequestMapping(value = "/slotdetails/viewapplications", method = RequestMethod.GET)
	public String viewUtilizedSlotDetailsByApplicationHelper(@RequestParam final String applicationType, @RequestParam final String scheduleType,
															 @RequestParam final Date appointmentDate,
															 @RequestParam final String appointmentTime,
															 @RequestParam final Long zoneId,
															 @RequestParam final Long electionWardId, final Model model) {
		if (appointmentDate == null) {
			model.addAttribute("appointmentDate", appointmentDate);
		} else {
			model.addAttribute("appointmentDate", DateUtils.toDefaultDateFormat(appointmentDate));
		}
		model.addAttribute("appointmentTime", appointmentTime);
		model.addAttribute("zoneId", zoneId);
		model.addAttribute("electionWardId", electionWardId);
		model.addAttribute("applicationType", applicationType);
		model.addAttribute("scheduleType", scheduleType);
		return "view-slot-application-details";
	}

	@RequestMapping(value = "/slotdetails/viewapplications", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String viewUtilizedSlotDetailsByApplication(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
		final List<SearchBpaApplicationForm> searchResultList = searchBpaApplicationService.buildSlotApplicationDetails(searchBpaApplicationForm);
		return new StringBuilder(DATA)
				.append(toJSON(searchResultList, SearchBpaApplicationForm.class, SearchBpaApplicationFormAdaptor.class))
				.append("}")
				.toString();
	}

	@RequestMapping(value = "/failureinscheduler", method = RequestMethod.GET)
	public String getFailureInSchedulerRecordsForm(final Model model) {
		prepareUserBndryAndFormData(model);
		return FAILURE_IN_SCHEDULER_REPORT;
	}

	@RequestMapping(value = "/failureinscheduler", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String showApplicationFailureInSchedulerRecords(final SearchBpaApplicationForm searchBpaApplicationForm) {
		List<SearchBpaApplicationForm> searchResultList = failureInSchedulerService
				.getAllFailedApplications(searchBpaApplicationForm);
		return new StringBuilder(DATA)
				.append(toJSON(searchResultList, SearchBpaApplicationForm.class, SearchBpaApplicationFormAdaptor.class))
				.append("}")
				.toString();
	}

	@RequestMapping(value = "/personalregister", method = RequestMethod.GET)
	public String searchPersonalRegisterForm(final Model model) {
		prepareUserBndryAndFormData(model);
		return PERSONAL_REGISTER_REPORT;
	}

	@RequestMapping(value = "/personalregister", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getPersonalRegisterResult(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
		List<Long> userIds = new ArrayList<>();
		if (searchBpaApplicationForm.getUserId() == null)
			userIds.addAll(userService.getUserByTypeInOrder(UserType.EMPLOYEE).stream().map(User::getId).collect(Collectors.toList()));
		else
			userIds.add(searchBpaApplicationForm.getUserId());
		final List<PersonalRegisterHelper> searchResultList = personalRegisterReportService.searchPersonalRegisterDetail(searchBpaApplicationForm, userIds,securityUtils.getCurrentUser());
		return new StringBuilder(DATA)
				.append(toJSON(searchResultList, PersonalRegisterHelper.class, SearchPersonalRegisterAdaptor.class))
				.append("}")
				.toString();
	}

	private void prepareUserBndryAndFormData(Model model) {
		Set<Boundary> employeeMappedZone = new HashSet<>();
		Set<Boundary> mappedElectionWard = new HashSet<>();
		Set<Boundary> revenueWards = new HashSet<>();
		boolean isAdmin = Boolean.FALSE;
		User currentUser = securityUtils.getCurrentUser();
		final Employee employee = employeeService.getEmployeeById(currentUser.getId());
		if (employee == null) {
			isAdmin = currentUser.hasRole(UserType.SYSTEM.name());
		}
		// If logged in user is ADMIN, Populate all users otherwise show only logged in user
		if (UserType.EMPLOYEE.equals(currentUser.getType()) && !isAdmin) {
			BoundaryType revenueType = boundaryTypeService.getBoundaryTypeByNameAndHierarchyTypeName(WARD, REVENUE_HIERARCHY_TYPE);
			for (Jurisdiction jurisdiction : employee.getJurisdictions()) {
				if (!BOUNDARY_TYPE_CITY.equals(jurisdiction.getBoundaryType().getName())) {
					mappedElectionWard.add(jurisdiction.getBoundary());
				}
			}
			mappedElectionWard.stream().forEach(elecWard -> {
				revenueWards.addAll(crossHierarchyService.getParentBoundaryByChildBoundaryAndParentBoundaryType(elecWard.getId(), revenueType.getId()));
			});
			employeeMappedZone.addAll(revenueWards.stream().map(Boundary::getParent).collect(Collectors.toList()));
			model.addAttribute("serviceTypeList", serviceTypeService.getAllActiveMainServiceTypes());
			model.addAttribute("occupancyList", occupancyService.findAllOrderByOrderNumber());
			model.addAttribute("zones", employeeMappedZone.stream().sorted(Comparator.comparing(Boundary::getId)).collect(Collectors.toSet()));
			model.addAttribute("wards", revenueWards.stream().sorted(Comparator.comparing(Boundary::getId)).collect(Collectors.toSet()));
			model.addAttribute("electionwards", mappedElectionWard.stream().sorted(Comparator.comparing(Boundary::getId)).collect(Collectors.toSet()));
			model.addAttribute("userList", Arrays.asList(employee));
			List<ApplicationSubType> appTypes = applicationTypeService.getAllEnabledApplicationTypes();
			List<ApplicationSubType> applicationTypes = new ArrayList<>();
	                for (ApplicationSubType applType : appTypes)
	                    if (applType.getName().equals(OCCUPANCY_CERTIFICATE_NOTICE_TYPE))
	                        continue;
	                    else
	                    	applicationTypes.add(applType);
			model.addAttribute("applicationTypes", applicationTypes);
		} else {
			prepareFormData(model);
		}
		model.addAttribute(SEARCH_BPA_APPLICATION_FORM, new SearchBpaApplicationForm());
	}

	@RequestMapping(value = "/bparegister", method = RequestMethod.GET)
	public String searchBpaRegisterForm(final Model model) {
		prepareUserBndryAndFormData(model);
		return "bpa-register-report";
	}

	@RequestMapping(value = "/bparegister", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getBpaRegisterResult(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
		List<Long> userIds = new ArrayList<>();
		if (searchBpaApplicationForm.getUserId() == null)
			userIds.addAll(userService.getUserByTypeInOrder(UserType.EMPLOYEE).stream().map(User::getId).collect(Collectors.toList()));
		else
			userIds.add(searchBpaApplicationForm.getUserId());
		return new DataTable<>(bpaReportsService.getBpaRegisterReportDetails(searchBpaApplicationForm, userIds),
				searchBpaApplicationForm.draw())
				.toJson(BpaRegisterReportAdaptor.class);
	}
	
	
	@RequestMapping(value = "/nocclearance", method = RequestMethod.GET)
	public String searchNocClearanceForm(final Model model) {
		model.addAttribute("nocDetailsHelper", new NocDetailsHelper());
		model.addAttribute("nocTypes",nocConfigService.findAll());
		model.addAttribute("nocStatus",bpaStatusService.findAllByModuleType(NOCMODULE));
		return "noc-clearance-report";
	}
	
	@RequestMapping(value = "/nocclearance", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getNocClearanceResult(@ModelAttribute final NocDetailsHelper nocDetailsHelper) {
		final List<NocDetailsHelper> searchResultList = nocReportService.searchNocDetails(nocDetailsHelper);
		return new StringBuilder(DATA)
				.append(toJSON(searchResultList, NocDetailsHelper.class, NocDetailsAdaptor.class))
				.append("}")
				.toString();
		
	}
}

