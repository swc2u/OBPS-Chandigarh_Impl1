/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
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
package org.egov.bpa.web.controller.transaction.citizen;

import static org.egov.bpa.utils.BpaConstants.CHECKLIST_TYPE;
import static org.egov.bpa.utils.BpaConstants.DCR_CHECKLIST;
import static org.egov.bpa.utils.BpaConstants.WF_SEND_BUTTON;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.bpa.master.entity.ApplicationSubType;
import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.master.service.NocConfigurationService;
import org.egov.bpa.service.noc.NocIntegrationService;
import org.egov.bpa.transaction.entity.ApplicationFloorDetail;
import org.egov.bpa.transaction.entity.ApplicationStakeHolder;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.entity.ExistingBuildingDetail;
import org.egov.bpa.transaction.entity.ExistingBuildingFloorDetail;
import org.egov.bpa.transaction.entity.PermitDcrDocument;
import org.egov.bpa.transaction.entity.PermitDocument;
import org.egov.bpa.transaction.entity.common.DcrDocument;
import org.egov.bpa.transaction.entity.common.GeneralDocument;
import org.egov.bpa.transaction.entity.enums.ApplicantMode;
import org.egov.bpa.transaction.service.BpaDcrService;
import org.egov.bpa.transaction.service.BuildingFloorDetailsService;
import org.egov.bpa.transaction.service.PermitNocApplicationService;
import org.egov.bpa.transaction.service.SearchBpaApplicationService;
import org.egov.bpa.transaction.service.collection.GenericBillGeneratorService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.common.entity.edcr.Plan;
import org.egov.commons.entity.Source;
import org.egov.commons.service.SubOccupancyService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.custom.CustomImplProvider;
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

@Controller
@RequestMapping("/application/citizen")
public class CitizenPreviousSanctionPlanApplicationController extends BpaGenericApplicationController {

    private static final String ONLINE_PAYMENT_ENABLE = "onlinePaymentEnable";

    private static final String WORK_FLOW_ACTION = "workFlowAction";

    private static final String TRUE = "TRUE";

    private static final String CITIZEN_OR_BUSINESS_USER = "citizenOrBusinessUser";

    private static final String IS_CITIZEN = "isCitizen";

    private static final String MSG_PORTAL_FORWARD_REGISTRATION = "msg.portal.forward.registration";

    private static final String MESSAGE = "message";

    private static final String BPAAPPLICATION_CITIZEN = "citizen_suceess";

    private static final String COMMON_ERROR = "common-error";

    @Autowired
    private GenericBillGeneratorService genericBillGeneratorService;
    @Autowired
    private BuildingFloorDetailsService buildingFloorDetailsService;
    @Autowired
    private SearchBpaApplicationService searchBpaApplicationService;
    @Autowired
    private BpaDcrService bpaDcrService;
    @Autowired
    protected SubOccupancyService subOccupancyService;
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    private NocConfigurationService nocConfigurationService;
    @Autowired
    private PermitNocApplicationService permitNocService;
    
    @Autowired
    private NocIntegrationService nocIntegrationService;


    @GetMapping("/previous-sanction-form")
    public String showServiceSelectionForm(final Model model) {
    	return loadServiceSelectionForm( model);
    }
    
    @GetMapping("/previous-sanction-form-with-service-type/{serviceType}")
    public String showNewApplicationForm(Model model,@PathVariable Long serviceType,@ModelAttribute final BpaApplication bpaApplication,
            final HttpServletRequest request) {
    	ServiceType servcieType = serviceTypeService.findById(serviceType);
        setCityName(model, request);
        model.addAttribute("currentuser", securityUtils.getCurrentUser().getName());
        return loadNewForm(bpaApplication, model,servcieType.getCode());
    }

    private String loadServiceSelectionForm(Model model) {
    	prepareFormData(model);
		return "previous-plan-service-selection";
	}

	private void setCityName(final Model model, final HttpServletRequest request) {
        if (request.getSession().getAttribute("cityname") != null)
            model.addAttribute("cityName", request.getSession().getAttribute("cityname"));
    }

    private String loadNewForm(final BpaApplication bpaApplication, final Model model,String serviceCode) {
        User user = securityUtils.getCurrentUser();
        StakeHolder stkHldr = stakeHolderService.findById(user.getId());
//        if (validateStakeholderIsEligibleSubmitAppln(model, serviceCode, stkHldr))
//            return COMMON_ERROR;
//        if (user.getType().equals(BUSINESS) && stkHldr.getDemand() != null) {
//            List<AppConfigValues> appConfigValueList = appConfigValueService
//                    .getConfigValuesByModuleAndKey(APPLICATION_MODULE_TYPE, "BUILDING_LICENSEE_REG_FEE_REQUIRED");
//            if ((appConfigValueList.isEmpty() ? "" : appConfigValueList.get(0).getValue()).equalsIgnoreCase("YES")
//                    && stkHldr.getStatus() != null
//                    && BpaConstants.APPLICATION_STATUS_PENDNING.equalsIgnoreCase(stkHldr.getStatus().name())) {
//                return genericBillGeneratorService.generateBillAndRedirectToCollection(stkHldr, model);
//            }
//        }
        model.addAttribute("stakeHolderType", stkHldr.getStakeHolderType().getName());
        prepareFormData(model);
        bpaApplication.setApplicationDate(new Date());
        prepareCommonModelAttribute(model, bpaApplication.isCitizenAccepted());
        model.addAttribute("mode", "new");
        model.addAttribute("permitApplnFeeRequired", false);
        bpaApplication.setSource(Source.CITIZENPORTAL);
        bpaApplication.setApplicantMode(ApplicantMode.NEW);
        bpaApplication.setServiceType(serviceTypeService.getServiceTypeByCode(serviceCode));
        model.addAttribute("isEDCRIntegrationRequire", true);
        model.addAttribute("loadingFloorDetailsFromEdcrRequire", true);
        List<ChecklistServiceTypeMapping> list = checklistServiceTypeService
                .findByActiveChecklistAndServiceType(bpaApplication.getServiceType().getDescription(), CHECKLIST_TYPE);
        model.addAttribute("subOccupancyList", subOccupancyService.findAllOrderByOrderNumber());
        List<PermitDocument> appDocList = new ArrayList<>();
        for (ChecklistServiceTypeMapping chklistServiceType : list) {
            PermitDocument permitDoc = new PermitDocument();
            GeneralDocument documentsCommon = new GeneralDocument();
            documentsCommon.setServiceChecklist(chklistServiceType);
            permitDoc.setDocument(documentsCommon);
            appDocList.add(permitDoc);
        }
        List<ChecklistServiceTypeMapping> dcrCheckListDetail = checklistServiceTypeService
                .findByActiveChecklistAndServiceType(bpaApplication.getServiceType().getDescription(), DCR_CHECKLIST);
        if (bpaApplication.getPermitDcrDocuments().isEmpty()) {
            for (ChecklistServiceTypeMapping dcrChkDetails : dcrCheckListDetail) {
                PermitDcrDocument permitDcrDocument = new PermitDcrDocument();
                DcrDocument dcrDocument = new DcrDocument();
                dcrDocument.setServiceChecklist(dcrChkDetails);
                permitDcrDocument.setApplication(bpaApplication);
                permitDcrDocument.setDcrDocument(dcrDocument);
                bpaApplication.getPermitDcrDocuments().add(permitDcrDocument);
            }
        }
        model.addAttribute("isPermitApplFeeReq", "NO");
        model.addAttribute("permitApplFeeCollected", "NO");
       
        model.addAttribute("applicationDocumentList", appDocList);
        model.addAttribute("isPreviousPlan", true);
        getDcrDocumentsUploadMode(model);
        if (!bpaDcrService.isEdcrIntegrationRequireByService(serviceCode)) {
            BuildingDetail bldg = new BuildingDetail();
            bldg.setName("0");
            bldg.setNumber(0);
            bpaApplication.getBuildingDetail().add(bldg);
            ExistingBuildingDetail exstBldg = new ExistingBuildingDetail();
            exstBldg.setName("0");
            exstBldg.setNumber(0);
            bpaApplication.getExistingBuildingDetails().add(exstBldg);
        }
        return "citizenApplication-form";
    }

    @PostMapping("/previous-sanction-form-with-service-type/application-create")
    public String createNewConnection(@Valid @ModelAttribute final BpaApplication bpaApplication,
            final HttpServletRequest request, final Model model, final BindingResult errors,
            final RedirectAttributes redirectAttributes) {
    	
        String onedaypermit = BpaConstants.APPLICATION_TYPE_ONEDAYPERMIT.toUpperCase();
        List<ApplicationSubType> riskBasedAppTypes = applicationTypeService.getRiskBasedApplicationTypes();
        if (errors.hasErrors()) {
            buildingFloorDetailsService.buildNewlyAddedFloorDetails(bpaApplication);
            applicationBpaService.buildExistingAndProposedBuildingDetails(bpaApplication);
            prepareCommonModelAttribute(model, bpaApplication.isCitizenAccepted());
            return loadNewForm(bpaApplication, model, bpaApplication.getServiceType().getCode());
        }
        if (bpaApplication.getIsOneDayPermitApplication())
            bpaApplication.setApplicationType(applicationTypeService.findByName(onedaypermit));
        
        //Set Previous plan flag
       bpaApplication.setIsPreviousPlan(true);
        
        
        Map<String, String> eDcrApplDetails = bpaDcrService.checkIsEdcrUsedInBpaApplication(bpaApplication.geteDcrNumber());
        if(!eDcrApplDetails.isEmpty())
        if (eDcrApplDetails.get("isExists").equals("true")) {
            model.addAttribute("eDcrApplExistsMessage", eDcrApplDetails.get(BpaConstants.MESSAGE));
            return loadNewForm(bpaApplication, model, bpaApplication.getServiceType().getCode());
        }
        boolean isEdcrIntegrationRequire = bpaDcrService
                .isEdcrIntegrationRequireByService(bpaApplication.getServiceType().getCode());
        if (isEdcrIntegrationRequire && !eDcrApplDetails.isEmpty() && eDcrApplDetails.get("isExists").equals("true")) {
            buildingFloorDetailsService.buildNewlyAddedFloorDetails(bpaApplication);
            applicationBpaService.buildExistingAndProposedBuildingDetails(bpaApplication);
            prepareCommonModelAttribute(model, bpaApplication.isCitizenAccepted());
            model.addAttribute("eDcrApplExistsMessage", eDcrApplDetails.get(BpaConstants.MESSAGE));
            return loadNewForm(bpaApplication, model, bpaApplication.getServiceType().getCode());
        }        

        if (isEdcrIntegrationRequire) {
            bpaApplication.getBuildingDetail().clear();
            for (BuildingDetail bldg : bpaApplication.getBuildingDetailFromEdcr()) {
                List<BuildingDetail> bldgDetails = new ArrayList<>();
                List<ApplicationFloorDetail> floorDetails = new ArrayList<>();
                for (ApplicationFloorDetail floor : bldg.getBuildingFloorDetailsByEdcr())
                    floorDetails.add(floor);
                bldg.setApplicationFloorDetailsForUpdate(floorDetails);
                bldgDetails.add(bldg);
                bpaApplication.getBuildingDetail().addAll(bldgDetails);
            }
            if (!bpaApplication.getExistingBldgDetailFromEdcr().isEmpty()) {
                bpaApplication.getExistingBuildingDetails().clear();
                for (ExistingBuildingDetail existBldg : bpaApplication.getExistingBldgDetailFromEdcr()) {
                    List<ExistingBuildingDetail> existBldgDetails = new ArrayList<>();
                    List<ExistingBuildingFloorDetail> floorDetails = new ArrayList<>();
                    for (ExistingBuildingFloorDetail floor : existBldg.getExistingBldgFloorDetailsFromEdcr())
                        floorDetails.add(floor);
                    existBldg.setExistingBuildingFloorDetailsUpdate(floorDetails);
                    existBldgDetails.add(existBldg);
                    bpaApplication.getExistingBuildingDetails().addAll(existBldgDetails);
                }
            }
        }
    
        applicationBpaService.buildExistingAndProposedBuildingDetails(bpaApplication);
        bpaUtils.saveOrUpdateBoundary(bpaApplication);        
        
        Plan plan = applicationBpaService.getPlanInfo(bpaApplication.geteDcrNumber());
        if (!isEdcrIntegrationRequire && riskBasedAppTypes.contains(bpaApplication.getApplicationType())) {
        	String rootBoundaryType = BpaConstants.URBAN;
        	String plotType = BpaConstants.ABOVE_TWO_KANAL;
        		    		
    		if(null!=plan) {
    			if(null != plan.getPlanInfoProperties().get(BpaConstants.ROOT_BOUNDARY_TYPE)) {
    				rootBoundaryType = plan.getPlanInfoProperties().get(BpaConstants.ROOT_BOUNDARY_TYPE);
    			}
    			if(null != plan.getPlanInfoProperties().get(BpaConstants.PLOT_TYPE)) {
    				plotType = plan.getPlanInfoProperties().get(BpaConstants.PLOT_TYPE);
    			}
    		}
            ApplicationSubType applicationType = bpaUtils.getApplicationType(plotType, rootBoundaryType);
            bpaApplication.setApplicationType(applicationType);
        }
        //update sector, plotNumber & filenumber
        bpaApplication.setPlotNumber(plan.getPlanInfoProperties().get(BpaConstants.PLOT_NO));
        bpaApplication.setSector(plan.getPlanInfoProperties().get(BpaConstants.SECTOR_NUMBER));
        bpaApplication.setFileNumber(bpaApplication.getSiteDetail().get(0).getKhataNumber());
        
//        Long approvalPosition = null;
        String workFlowAction = BpaConstants.APPLICATION_STATUS_PREV_PLAN_UPDATED;
        
        
        Boolean isCitizen = request.getParameter(IS_CITIZEN) != null
                && request.getParameter(IS_CITIZEN).equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
        Boolean citizenOrBusinessUser = request.getParameter(CITIZEN_OR_BUSINESS_USER) != null
                && request.getParameter(CITIZEN_OR_BUSINESS_USER).equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
        if (citizenOrBusinessUser) {
            if (isCitizen) {
                List<ApplicationStakeHolder> applicationStakeHolders = new ArrayList<>();
                ApplicationStakeHolder applicationStakeHolder = new ApplicationStakeHolder();
                applicationStakeHolder.setApplication(bpaApplication);
                applicationStakeHolder.setStakeHolder(bpaApplication.getStakeHolder().get(0).getStakeHolder());
                applicationStakeHolders.add(applicationStakeHolder);
                bpaApplication.setStakeHolder(applicationStakeHolders);
            } else {
                User user = securityUtils.getCurrentUser();
                StakeHolder stakeHolder = stakeHolderService.findById(user.getId());
                ApplicationStakeHolder applicationStakeHolder = new ApplicationStakeHolder();
                applicationStakeHolder.setApplication(bpaApplication);
                applicationStakeHolder.setStakeHolder(stakeHolder);
                bpaApplication.getStakeHolder().add(applicationStakeHolder);
                Map<Boolean, String> shValidation = bpaApplicationValidationService
                        .checkStakeholderIsValid(bpaApplication);
                if (!shValidation.isEmpty())
                    for (Map.Entry<Boolean, String> keyset : shValidation.entrySet()) {
                        if (!keyset.getKey()) {
                            String message = keyset.getValue();
                            model.addAttribute("invalidStakeholder", message);
                            applicationBpaService.buildExistingAndProposedBuildingDetails(bpaApplication);
                            return loadNewForm(bpaApplication, model, bpaApplication.getServiceType().getCode());
                        }
                    }
            }
        } 
        
        bpaApplication.setAdmissionfeeAmount(BigDecimal.ZERO);
        
//        nocIntegrationService.pushNocRequest(bpaApplication);

        applicationBpaService.persistOrUpdateApplicationDocument(bpaApplication);
        if (bpaApplication.getOwner().getUser() != null && bpaApplication.getOwner().getUser().getId() == null)
            applicationBpaService.buildOwnerDetails(bpaApplication);

        bpaApplication.setSentToCitizen(workFlowAction != null && workFlowAction.equals(WF_SEND_BUTTON));
        
        
        if (bpaApplication.getPlanPermissionNumber() == null) {
        	bpaApplication.setPlanPermissionNumber(applicationBpaService.generatePlanPermissionNumber(bpaApplication));
        	bpaApplication.setPlanPermissionDate(new Date());
        }
        
        
        BpaApplication bpaApplicationRes = applicationBpaService.createNewApplication(bpaApplication, workFlowAction);

        if (citizenOrBusinessUser) {
            if (isCitizen)
                bpaUtils.createPreviousPlanPortalUserinbox(bpaApplicationRes, Arrays.asList(bpaApplicationRes.getOwner().getUser(),
                        bpaApplicationRes.getStakeHolder().get(0).getStakeHolder()));
            else {
                    bpaUtils.createPreviousPlanPortalUserinbox(bpaApplicationRes, Arrays.asList(bpaApplicationRes.getOwner().getUser(),
                            securityUtils.getCurrentUser()));
            }
        }

            redirectAttributes.addFlashAttribute(MESSAGE,
                    "Successfully saved with application number " + bpaApplicationRes.getApplicationNumber() + " and Plan permission number "+bpaApplicationRes.getPlanPermissionNumber()+".");
        
        return "redirect:/application/citizen/success/" + bpaApplicationRes.getApplicationNumber();
    }
    
  
}