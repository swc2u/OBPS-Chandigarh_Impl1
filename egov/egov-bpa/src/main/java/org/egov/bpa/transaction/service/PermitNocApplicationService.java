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
package org.egov.bpa.transaction.service;

import static org.egov.bpa.utils.BpaConstants.BPASTATUS_MODULETYPE;
import static org.egov.bpa.utils.BpaConstants.WF_NEW_STATE;
import static org.egov.bpa.utils.BpaConstants.NOCMODULE;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.egov.bpa.autonumber.NocNumberGenerator;
import org.egov.bpa.master.entity.Holiday;
import org.egov.bpa.master.entity.NocConfiguration;
import org.egov.bpa.master.service.HolidayListService;
import org.egov.bpa.master.service.NocConfigurationService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaNocApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.PermitNocApplication;
import org.egov.bpa.transaction.entity.PermitNocDocument;
import org.egov.bpa.transaction.entity.enums.NocIntegrationInitiationEnum;
import org.egov.bpa.transaction.entity.enums.NocIntegrationTypeEnum;
import org.egov.bpa.transaction.repository.PermitNocApplicationRepository;
import org.egov.bpa.transaction.service.messaging.BPASmsAndEmailService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.common.entity.dcr.helper.EdcrApplicationInfo;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.utils.ApplicationConstant;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@Transactional(readOnly = true)
public class PermitNocApplicationService {
    @Autowired
    private PermitNocApplicationRepository permitNocRepository;
    @Autowired
    private BpaStatusService statusService;
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private NocConfigurationService nocConfigurationService;
    @Autowired
    private UserService userService;
    @Autowired
    private NocNumberGenerator nocNumberGenerator;
    @Autowired
    public HolidayListService holidayListService;
    @Autowired
    private DcrRestService drcRestService;
    @Autowired
    private BPASmsAndEmailService bpaSmsAndEmailService;
    
    @Autowired
    private BpaStatusService bpaStatusService;

    @Transactional
    public PermitNocApplication save(final PermitNocApplication permitNoc) {
        return permitNocRepository.save(permitNoc);
    }

    @Transactional
    public List<PermitNocApplication> save(final List<PermitNocApplication> permitNoc) {
        return permitNocRepository.save(permitNoc);
    }

    public PermitNocApplication findByNocApplicationNumber(String appNo) {
        return permitNocRepository.findByNocApplicationNumber(appNo);
    }

    public List<PermitNocApplication> findByPermitApplicationNumber(String appNo) {
        return permitNocRepository.findByPermitApplicationNumber(appNo);
    }

    public List<PermitNocApplication> findInitiatedAppByType(final String nocType) {
        return permitNocRepository.findInitiatedAppByType(nocType);
    }

    public PermitNocApplication findByApplicationNumberAndType(String appNo, final String nocType) {
        return permitNocRepository.findByApplicationNumberAndType(appNo, nocType);
    }

    public PermitNocApplication createNocApplication(PermitNocApplication permitNoc, NocConfiguration nocConfig) {
        BpaStatus status = statusService.findByModuleTypeAndCode(BpaConstants.CHECKLIST_TYPE_NOC, BpaConstants.NOC_INITIATED);
        permitNoc.getBpaNocApplication().setNocApplicationNumber(nocNumberGenerator.generateNocNumber(nocConfig.getDepartment()));
        permitNoc.getBpaNocApplication().setNocType(nocConfig.getDepartment());
        if(!nocConfig.getDepartment().equalsIgnoreCase("STRUCTURE NOC")) {
        	permitNoc.getBpaNocApplication().setStatus(status);
        }
        addSlaEndDate(permitNoc.getBpaNocApplication(), nocConfig);
        PermitNocApplication nocApp = permitNocRepository.save(permitNoc);
        bpaSmsAndEmailService.sendSMSAndEmailForNocProcess(BpaConstants.NOC_INITIATED, nocApp);
        return nocApp;

    }

    public void initiateNoc(BpaApplication application) {
        Map<String, String> edcrNocMandatory = getEdcrNocMandatory(application.geteDcrNumber());
        for (PermitNocDocument nocDocument : application.getPermitNocDocuments()) {
        	if(nocDocument.getNocDocument().getNocSupportDocs().isEmpty()) {
	            PermitNocApplication permitNoc = new PermitNocApplication();
	            BpaNocApplication nocApplication = new BpaNocApplication();
	            List<User> nocUser = new ArrayList<>();
	            List<User> userList = new ArrayList<>();
	            NocConfiguration nocConfig = nocConfigurationService
	                    .findByDepartmentAndType(nocDocument.getNocDocument().getServiceChecklist().getChecklist().getCode(),
	                            BpaConstants.PERMIT);
	            if (nocConfig != null && nocConfig.getApplicationType().trim().equalsIgnoreCase(BpaConstants.PERMIT)
	                    && nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.INTERNAL.toString())
	                    && nocConfig.getIntegrationInitiation().equalsIgnoreCase(NocIntegrationInitiationEnum.AUTO.toString())
	                    && edcrNocMandatory.get(nocConfig.getDepartment()).equalsIgnoreCase("YES")) {
	                List<User> nocUsers = new ArrayList<User>(
	                        userService.getUsersByTypeAndTenantId(UserType.BUSINESS, ApplicationThreadLocals.getTenantID()));
	                userList = nocUsers.stream()
	                        .filter(usr -> usr.getRoles().stream()
	                                .anyMatch(usrrl -> usrrl.getName()
	                                        .equals(getNocRoles(application, nocConfig))))
	                        .collect(Collectors.toList());
	                if (userList.isEmpty()) {
	                    nocUsers = userService.getUsersByTypeAndTenantId(UserType.BUSINESS, ApplicationConstant.STATE_TENANTID);
	                    userList = nocUsers.stream()
	                            .filter(usr -> usr.getRoles().stream()
	                                    .anyMatch(usrrl -> usrrl.getName()
	                                            .equals(getNocRoles(application, nocConfig))))
	                            .collect(Collectors.toList());
	                }
	                nocUser.add(userList.get(0));
	                permitNoc.setBpaApplication(application);
	                permitNoc.setBpaNocApplication(nocApplication);
	                
	                if(nocConfig.getDepartment().equalsIgnoreCase("STRUCTURE NOC")) {
		                //Workflow initiated for NOC
	                	String workFlowAction = BpaConstants.NOC_INITIATED;
			            Long approvalPosition = null;
			            final WorkFlowMatrix wfMatrixNOC =bpaUtils.getWfMatrixByCurrentState(
			                   false, BpaConstants.BPA_NOC, WF_NEW_STATE,
			                   application.getApplicationType().getName());
			           if (wfMatrixNOC != null) {
			        	   approvalPosition= bpaUtils.getNOCUserPositionId(wfMatrixNOC.getNextDesignation());
			           }
			           bpaUtils.redirectToBpaNOCWorkFlow(approvalPosition, permitNoc, BpaConstants.WF_NEW_STATE,
			           		"NOC workflow initiated thorugh BPA service", BpaConstants.WF_FORWARD_BUTTON, null);
			           
			           if (workFlowAction != null && workFlowAction.equals(BpaConstants.NOC_INITIATED)) {
		                    final BpaStatus bpaStatus = getStatusByCodeAndModuleType(BpaConstants.NOC_INITIATED);
		                    permitNoc.getBpaNocApplication().setStatus(bpaStatus);
		                } else {
		                    final BpaStatus bpaStatus = getStatusByCodeAndModuleType(BpaConstants.NOC_FORWARDED);
		                    permitNoc.getBpaNocApplication().setStatus(bpaStatus);
		                }
		                
                }
	                
	                permitNoc = createNocApplication(permitNoc, nocConfig);
	                bpaUtils.createNocPortalUserinbox(permitNoc, nocUser, permitNoc.getBpaNocApplication().getStatus().getCode());
	               
	              
		           
	            }else if (nocConfig != null && nocConfig.getApplicationType().trim().equalsIgnoreCase(BpaConstants.PERMIT)
	                    && nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.THIRD_PARTY.toString())
	                    && nocConfig.getIntegrationInitiation().equalsIgnoreCase(NocIntegrationInitiationEnum.AUTO.toString())
	                    && edcrNocMandatory.get(nocConfig.getDepartment()).equalsIgnoreCase("YES")) {
	                permitNoc.setBpaApplication(application);
	                permitNoc.setBpaNocApplication(nocApplication);
	                permitNoc = createNocApplication(permitNoc, nocConfig);
	                nocUser.add(permitNoc.getBpaApplication().getOwner().getUser());
	                bpaUtils.createNocPortalUserinbox(permitNoc, nocUser, permitNoc.getBpaNocApplication().getStatus().getCode());
	            }
	            
	        }
        }
    }

    public BpaStatus getStatusByCodeAndModuleType(final String code) {
        return bpaStatusService.findByModuleTypeAndCode(NOCMODULE, code);
    }
    
    public PermitNocApplication createNoc(BpaApplication application, String nocType) {
        PermitNocApplication permitNoc = new PermitNocApplication();
        BpaNocApplication nocApplication = new BpaNocApplication();
        List<User> nocUser = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        NocConfiguration nocConfig = nocConfigurationService
                .findByDepartmentAndType(nocType, BpaConstants.PERMIT);
        if (nocConfig != null && nocConfig.getApplicationType().trim().equalsIgnoreCase(BpaConstants.PERMIT)
                && nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.INTERNAL.toString())
                && nocConfig.getIntegrationInitiation().equalsIgnoreCase(NocIntegrationInitiationEnum.MANUAL.toString())) {
            List<User> nocUsers = new ArrayList<User>(
                    userService.getUsersByTypeAndTenantId(UserType.BUSINESS, ApplicationThreadLocals.getTenantID()));
            userList = nocUsers.stream()
                    .filter(usr -> usr.getRoles().stream()
                            .anyMatch(usrrl -> usrrl.getName()
                            		.equals(getNocRoles(application, nocConfig))))
                    .collect(Collectors.toList());
            if (userList.isEmpty()) {
                nocUsers = userService.getUsersByTypeAndTenantId(UserType.BUSINESS, ApplicationConstant.STATE_TENANTID);
                userList = nocUsers.stream()
                        .filter(usr -> usr.getRoles().stream()
                                .anyMatch(usrrl -> usrrl.getName()
                                        .equals(getNocRoles(application, nocConfig))))
                        .collect(Collectors.toList());
            }
            nocUser.add(userList.get(0));
            permitNoc.setBpaApplication(application);
            permitNoc.setBpaNocApplication(nocApplication);
            permitNoc = createNocApplication(permitNoc, nocConfig);

            permitNoc.getBpaNocApplication().setOwnerUser(nocUser.get(0));

            bpaUtils.createNocPortalUserinbox(permitNoc, nocUser, permitNoc.getBpaNocApplication().getStatus().getCode());
        }else if (nocConfig != null && nocConfig.getApplicationType().trim().equalsIgnoreCase(BpaConstants.PERMIT)
                && nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.THIRD_PARTY.toString())
                && nocConfig.getIntegrationInitiation().equalsIgnoreCase(NocIntegrationInitiationEnum.MANUAL.toString())) {
        	nocUser.add(application.getOwner().getUser());
            permitNoc.setBpaApplication(application);
            permitNoc.setBpaNocApplication(nocApplication);
            permitNoc = createNocApplication(permitNoc, nocConfig);

            bpaUtils.createNocPortalUserinbox(permitNoc, nocUser, permitNoc.getBpaNocApplication().getStatus().getCode());
        }
        return permitNoc;
    }

    public void addSlaEndDate(BpaNocApplication nocApplication, NocConfiguration nocConfig) {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // todays date.
        c.add(Calendar.DATE, Integer.parseInt(nocConfig.getSla().toString()));

        List<Holiday> holiday = holidayListService.findByFromAndToDate(new Date(), c.getTime());
        c.add(Calendar.DATE, holiday.size());

        nocApplication.setSlaEndDate(c.getTime());
    }
    
    public String getNocRoles(BpaApplication application, NocConfiguration nocConfig) {
    	String nocRole = "";
    	if(BpaConstants.APPLICATION_TYPE_MEDIUMRISK.equalsIgnoreCase(application.getApplicationType().getName())) {
    		nocRole = BpaConstants.getNocRuralRole().get(nocConfig.getDepartment());
    	} else {
    		nocRole = BpaConstants.getNocRole().get(nocConfig.getDepartment());
    	}
    	return nocRole;
    }

    public Map<String, String> getEdcrNocMandatory(final String edcrNumber) {		
		EdcrApplicationInfo edcrPlanInfo = drcRestService.getDcrPlanInfo(edcrNumber, ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest());
		Map<String, String> nocTypeMap = new HashMap<>();	
		nocTypeMap.put(BpaConstants.FIRENOCTYPE, "NO");
		nocTypeMap.put(BpaConstants.PH7NOCTYPE, "NO");
		nocTypeMap.put(BpaConstants.TEHNOCTYPE, "NO");
		nocTypeMap.put(BpaConstants.PHNOCTYPE, "NO");
		nocTypeMap.put(BpaConstants.MANINOCTYPE, "NO");
		nocTypeMap.put(BpaConstants.ROAD2NOCTYPE, "NO");
		nocTypeMap.put(BpaConstants.PACNOCTYPE, "NO");
		nocTypeMap.put(BpaConstants.STRCNOCTYPE, "NO");
		nocTypeMap.put(BpaConstants.ELECNOCTYPE, "NO");
		nocTypeMap.put(BpaConstants.POLNOCTYPE, "NO");
		nocTypeMap.put(BpaConstants.PLANNINGNOCTYPE, "NO");
		if(edcrPlanInfo.getPlan()!=null){ 
			edcrPlanInfo.getPlan().getPlanInformation().setNocPACDept("NO");
			edcrPlanInfo.getPlan().getPlanInformation().setNocStructureDept("NO");
			edcrPlanInfo.getPlan().getPlanInformation().setNocFireDept("NO");
			edcrPlanInfo.getPlan().getPlanInformation().setNocTehsildarDept("NO");
			edcrPlanInfo.getPlan().getPlanInformation().setNocManimajaraDept("NO");
			edcrPlanInfo.getPlan().getPlanInformation().setNocElectricalDept("NO");
			edcrPlanInfo.getPlan().getPlanInformation().setNocPollutionDept("NO");
			edcrPlanInfo.getPlan().getPlanInformation().setNocPH7Dept("NO");
			edcrPlanInfo.getPlan().getPlanInformation().setNocPHDept("NO");
			edcrPlanInfo.getPlan().getPlanInformation().setNocRoad2Dept("NO");	
			edcrPlanInfo.getPlan().getPlanInformation().setNocPlanningDept("NO");	
			
			if(null!=edcrPlanInfo.getPlan()) {
				OccupancyTypeHelper occupancyTypeHelper = edcrPlanInfo.getPlan().getVirtualBuilding() != null
						? edcrPlanInfo.getPlan().getVirtualBuilding().getMostRestrictiveFarHelper()
						: null;
				String boundaryType = "";
				String plotType = "";
				if(null != edcrPlanInfo.getPlan().getPlanInfoProperties().get(BpaConstants.ROOT_BOUNDARY_TYPE)) {
					boundaryType = edcrPlanInfo.getPlan().getPlanInfoProperties().get(BpaConstants.ROOT_BOUNDARY_TYPE);
				}
				if(null != edcrPlanInfo.getPlan().getPlanInfoProperties().get(BpaConstants.PLOT_TYPE)) {
					plotType = edcrPlanInfo.getPlan().getPlanInfoProperties().get(BpaConstants.PLOT_TYPE);
				}
				if(boundaryType.equalsIgnoreCase(BpaConstants.URBAN)) {
					if(plotType.equalsIgnoreCase(BpaConstants.ABOVE_TWO_KANAL)) {
						if(BpaConstants.A_G.equalsIgnoreCase(occupancyTypeHelper.getSubtype().getCode())
							|| BpaConstants.A_P.equalsIgnoreCase(occupancyTypeHelper.getSubtype().getCode())){
							edcrPlanInfo.getPlan().getPlanInformation().setNocPACDept("YES");
							edcrPlanInfo.getPlan().getPlanInformation().setNocStructureDept("YES");
							edcrPlanInfo.getPlan().getPlanInformation().setNocPlanningDept("YES");
						}else {
							edcrPlanInfo.getPlan().getPlanInformation().setNocPACDept("YES");
							edcrPlanInfo.getPlan().getPlanInformation().setNocFireDept("YES");
							edcrPlanInfo.getPlan().getPlanInformation().setNocStructureDept("YES");
							edcrPlanInfo.getPlan().getPlanInformation().setNocElectricalDept("YES");
							edcrPlanInfo.getPlan().getPlanInformation().setNocPollutionDept("YES");
							edcrPlanInfo.getPlan().getPlanInformation().setNocPH7Dept("YES");
							edcrPlanInfo.getPlan().getPlanInformation().setNocPlanningDept("YES");
						}
					}
				}else if(boundaryType.equalsIgnoreCase(BpaConstants.RURAL)){
					edcrPlanInfo.getPlan().getPlanInformation().setNocElectricalDept("YES");
					edcrPlanInfo.getPlan().getPlanInformation().setNocFireDept("YES");
					edcrPlanInfo.getPlan().getPlanInformation().setNocPHDept("YES");
					//edcrPlanInfo.getPlan().getPlanInformation().setNocTehsildarDept("YES");
					if(edcrPlanInfo.getPlan().getPlanInfoProperties()!=null) {
						String village=edcrPlanInfo.getPlan().getPlanInfoProperties().get(BpaConstants.VILLAGE);
						if(village!=null && BpaConstants.MANIMAJRA.equalsIgnoreCase(village))
							edcrPlanInfo.getPlan().getPlanInformation().setNocManimajaraDept("YES");
					}
					//edcrPlanInfo.getPlan().getPlanInformation().setNocPollutionDept("YES");
					
					//edcrPlanInfo.getPlan().getPlanInformation().setNocRoad2Dept("YES");
				}
			}			
			nocTypeMap.put(BpaConstants.FIRENOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocFireDept());
			nocTypeMap.put(BpaConstants.PH7NOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocPH7Dept());
			nocTypeMap.put(BpaConstants.TEHNOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocTehsildarDept());
			nocTypeMap.put(BpaConstants.PHNOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocPHDept());
			nocTypeMap.put(BpaConstants.MANINOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocManimajaraDept());
			nocTypeMap.put(BpaConstants.ROAD2NOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocRoad2Dept());
			nocTypeMap.put(BpaConstants.PACNOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocPACDept());
			nocTypeMap.put(BpaConstants.STRCNOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocStructureDept());
			nocTypeMap.put(BpaConstants.ELECNOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocElectricalDept());
			nocTypeMap.put(BpaConstants.POLNOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocPollutionDept());
			nocTypeMap.put(BpaConstants.PLANNINGNOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocPlanningDept());
		}		 
        return nocTypeMap;
    }
}