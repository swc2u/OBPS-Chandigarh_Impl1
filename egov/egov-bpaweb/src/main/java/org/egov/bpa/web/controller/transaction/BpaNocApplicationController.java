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

package org.egov.bpa.web.controller.transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaNocApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.NocEvaluation;
import org.egov.bpa.transaction.entity.PermitDcrDocument;
import org.egov.bpa.transaction.entity.PermitDocument;
import org.egov.bpa.transaction.entity.PermitNocApplication;
import org.egov.bpa.transaction.entity.PermitNocDocument;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.repository.NocEvaluationRepository;
import org.egov.bpa.transaction.repository.PermitNocApplicationRepository;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.BpaStatusService;
import org.egov.bpa.transaction.service.PermitNocApplicationService;
import org.egov.bpa.transaction.service.messaging.BPASmsAndEmailService;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.common.entity.bpa.Checklist;
import org.egov.commons.service.CheckListService;
import org.egov.commons.service.OccupancyService;
import org.egov.eis.entity.Assignment;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.infra.workflow.service.SimpleWorkflowService;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping(value = "/nocapplication")
public class BpaNocApplicationController {
	private static final String WORK_FLOW_ACTION = "workFlowAction";

	@Autowired
	private OccupancyService occupancyService;

	@Autowired
	private BpaStatusService statusService;

	@Autowired
	private ApplicationBpaService applicationBpaService;

	@Autowired
	private BpaUtils bpaUtils;

	@Autowired
	private PermitNocApplicationService permitNocService;

	@Autowired
	private PermitNocApplicationRepository permitNocRepository;

	@Autowired
	private BPASmsAndEmailService bpaSmsAndEmailService;

	@Autowired
	private CheckListService checkListService;
	
	@Autowired
    protected ResourceBundleMessageSource messageSource;
	
	@Autowired
	@Qualifier("workflowService")
	private SimpleWorkflowService<BpaApplication> bpaApplicationWorkflowService;
	
	 @Autowired
    protected BpaWorkFlowService bpaWorkFlowService;

	@RequestMapping(value = "/create/{applicationNumber}", method = RequestMethod.GET)
	public String createNoc(@PathVariable final String applicationNumber, final Model model,
			final RedirectAttributes redirectAttributes) {
		String[] appArr = applicationNumber.split("~");
		BpaApplication bpaApplication = applicationBpaService.findByApplicationNumber(appArr[1]);
		PermitNocApplication permitNoc = permitNocService.createNoc(bpaApplication, appArr[0]);
		redirectAttributes.addFlashAttribute("message",
				"Noc Application is forwarded to " + permitNoc.getBpaNocApplication().getOwnerUser().getUsername()
						+ " with application number " + permitNoc.getBpaNocApplication().getNocApplicationNumber()
						+ ".");
		return "redirect:/nocapplication/success/" + permitNoc.getBpaNocApplication().getNocApplicationNumber();
	}

	@RequestMapping(value = "/update/{applicationNumber}", method = RequestMethod.GET)
	public String getNocApplication(@PathVariable final String applicationNumber, final Model model,
			final RedirectAttributes redirectAttributes) {
		PermitNocApplication permitNoc = permitNocService.findByNocApplicationNumber(applicationNumber);
		for (PermitNocDocument nocDocument : permitNoc.getBpaApplication().getPermitNocDocuments()) {
			if (nocDocument.getNocDocument().getServiceChecklist().getChecklist().getCode()
					.equals(permitNoc.getBpaNocApplication().getNocType()))
				model.addAttribute("nocDocs", nocDocument);
		}
		BpaApplication bpaApp=applicationBpaService.findByApplicationNumber(permitNoc.getBpaApplication().getApplicationNumber());
		for(PermitDocument p:bpaApp.getPermitDocuments()) {
			System.out.println(p.getDocument().getServiceChecklist().getChecklist().getDescription());
		}
		model.addAttribute("bpaApp",bpaApp);
		permitNoc.getBpaApplication().setPermitOccupanciesTemp(permitNoc.getBpaApplication().getPermitOccupancies());
		model.addAttribute("occupancyList", occupancyService.findAllOrderByOrderNumber());
		model.addAttribute("permitNocApplication", permitNoc);
		List<Checklist> checklists = getListOfEvaluation(permitNoc);
		model.addAttribute("checklists", checklists);
		if (permitNoc.getBpaNocApplication().getStatus().getCode().equals(BpaConstants.NOC_APPROVED))
			return "redirect:/nocapplication/view/" + permitNoc.getBpaNocApplication().getNocApplicationNumber();
		else
			return "noc-details";
	}

	public List<Checklist> getListOfEvaluation(final PermitNocApplication permitNocApplication) {
		if (permitNocApplication == null || permitNocApplication.getBpaNocApplication() == null)
			return null;
		List<Checklist> checklists = new ArrayList<Checklist>();

		checklists = checkListService
				.findChecklistByCheckListTypeCode(permitNocApplication.getBpaNocApplication().getNocType());

		return checklists;
	}
	@Autowired
	private NocEvaluationRepository nocEvaluationRepository;
	
	@Transactional
	@RequestMapping(value = "/updateNoc/{applicationNumber}", method = RequestMethod.POST)
	public String updateNoc(@ModelAttribute final PermitNocApplication permitNocApplication,
			@PathVariable final String applicationNumber, final HttpServletRequest request, final Model model,
			final RedirectAttributes redirectAttributes) {

		String workFlowAction = request.getParameter(WORK_FLOW_ACTION);
		Position pos =null;
		User wfUser=null;
		// evaluation
		List<NocEvaluation> evaluations = new ArrayList<NocEvaluation>();
		for (Checklist checklist : getListOfEvaluation(permitNocApplication)) {
			NocEvaluation nocEvaluation = new NocEvaluation();
			nocEvaluation.setChecklist(checklist);
			nocEvaluation.setNocapplication(permitNocApplication.getBpaNocApplication().getId());
			nocEvaluation.setComment(request.getParameter(checklist.getCode() + ".comment"));
			nocEvaluation.setRemarks(request.getParameter(checklist.getCode() + ".remark"));
			//nocEvaluationRepository.save(nocEvaluation);
			evaluations.add(nocEvaluation);
		}

		permitNocApplication.getBpaNocApplication().setNocEvaluations(evaluations);
		if(permitNocApplication.getBpaNocApplication().getState()!=null) {
			Long approvalPosition = null;
			if(BpaConstants.BPA_NOC_WF_ACTION_FORWARDED_TO_VERIFICATION.equalsIgnoreCase(permitNocApplication.getBpaNocApplication().getState().getNextAction())) {
				final WorkFlowMatrix wfMatrix =bpaApplicationWorkflowService.
						 getWfMatrix(BpaConstants.BPA_NOC, null, null, permitNocApplication.getBpaApplication().getApplicationType().getName(), BpaConstants.WF_NEW_STATE,
							null);
		        if (wfMatrix != null) {
//		        	approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
//		        			permitNocApplication.getBpaApplication().getSiteDetail().get(0) != null
//		                            && permitNocApplication.getBpaApplication().getSiteDetail().get(0).getAdminBoundary() != null
//		                                    ? permitNocApplication.getBpaApplication().getSiteDetail().get(0).getAdminBoundary().getId()
//		                                    : null);
		        	approvalPosition= bpaUtils.getNOCUserPositionId(wfMatrix.getNextDesignation());
		        }
			}
			else if(BpaConstants.BPA_NOC_WF_ACTION_FORWARDED_TO_APPROVE.equalsIgnoreCase(permitNocApplication.getBpaNocApplication().getState().getNextAction())) {
				final WorkFlowMatrix wfMatrix =bpaApplicationWorkflowService.
						 getWfMatrix(BpaConstants.BPA_NOC, null, null, permitNocApplication.getBpaApplication().getApplicationType().getName(), BpaConstants.BPA_NOC_WF_STATE_VERIFICZTION_PENDING,
							null);
		        if (wfMatrix != null) {
//		        	approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
//		        			permitNocApplication.getBpaApplication().getSiteDetail().get(0) != null
//		                            && permitNocApplication.getBpaApplication().getSiteDetail().get(0).getAdminBoundary() != null
//		                                    ? permitNocApplication.getBpaApplication().getSiteDetail().get(0).getAdminBoundary().getId()
//		                                    : null);
		        	approvalPosition= bpaUtils.getNOCUserPositionId(wfMatrix.getNextDesignation());
		        }
			}
			else if(BpaConstants.APPROVED.equalsIgnoreCase(workFlowAction)) {
				final WorkFlowMatrix wfMatrix =bpaApplicationWorkflowService.
						 getWfMatrix(BpaConstants.BPA_NOC, null, null, permitNocApplication.getBpaApplication().getApplicationType().getName(), BpaConstants.BPA_NOC_WF_STATE_PENDING_APPROVE,
							null);
		        if (wfMatrix != null) {
//		        	approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
//		        			permitNocApplication.getBpaApplication().getSiteDetail().get(0) != null
//		                            && permitNocApplication.getBpaApplication().getSiteDetail().get(0).getAdminBoundary() != null
//		                                    ? permitNocApplication.getBpaApplication().getSiteDetail().get(0).getAdminBoundary().getId()
//		                                    : null);
		        	approvalPosition= bpaUtils.getNOCUserPositionId(wfMatrix.getNextDesignation());
		        }
			}
	        
	        bpaUtils.redirectToBpaNOCWorkFlow(approvalPosition, permitNocApplication, permitNocApplication.getBpaNocApplication().getCurrentState().getValue(),
	        		permitNocApplication.getBpaNocApplication().getRemarks(), workFlowAction, null);
	        
				 List<Assignment> assignments;
			if (null == approvalPosition)
			    assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(
			    		permitNocApplication.getBpaNocApplication().getCurrentState().getOwnerPosition().getId(), new Date());
			else
			    assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(approvalPosition, new Date());
			 pos = assignments.get(0).getPosition();
			 wfUser = assignments.get(0).getEmployee();
		
		}
        
//        if(!"STRUCTURE NOC".equalsIgnoreCase(permitNocApplication.getBpaNocApplication().getNocType())) {
		BpaStatus status=null;	
		if(workFlowAction!=null && workFlowAction.equals(""))
			 status = statusService.findByModuleTypeAndCode(BpaConstants.NOCMODULE, BpaConstants.NOC_FORWARDED);
		else
			status = statusService.findByModuleTypeAndCode(BpaConstants.NOCMODULE, workFlowAction);
			
			permitNocApplication.getBpaNocApplication().setStatus(status);
//		}
			
		buildNocFiles(permitNocApplication.getBpaNocApplication());
		permitNocRepository.save(permitNocApplication);
		bpaUtils.updateNocPortalUserinbox(permitNocApplication, null,workFlowAction);
		if (workFlowAction.equalsIgnoreCase(BpaConstants.NOC_APPROVED)) {
			redirectAttributes.addFlashAttribute("message", "Noc Application is approved with application number "
					+ permitNocApplication.getBpaNocApplication().getNocApplicationNumber() + ".");
			bpaSmsAndEmailService.sendSMSAndEmailForNocProcess(BpaConstants.NOC_APPROVED, permitNocApplication);
		}else if(workFlowAction.equalsIgnoreCase(BpaConstants.NOC_REJECTED)){
			redirectAttributes.addFlashAttribute("message", "Noc Application is rejected with "
					+ permitNocApplication.getBpaNocApplication().getNocApplicationNumber() + ".");
			bpaSmsAndEmailService.sendSMSAndEmailForNocProcess(BpaConstants.NOC_REJECTED, permitNocApplication);
		}else {
			String message = messageSource.getMessage("Noc Application % forwarded to % .", new String[] {
                    wfUser == null ? ""
                            : wfUser.getUsername().concat("~").concat(getDesinationNameByPosition(pos)),
                            permitNocApplication.getBpaNocApplication().getNocApplicationNumber() }
					,LocaleContextHolder.getLocale());
			
			redirectAttributes.addFlashAttribute("message", message);
			bpaSmsAndEmailService.sendSMSAndEmailForNocProcess(BpaConstants.NOC_FORWARDED, permitNocApplication);
		}
		return "redirect:/nocapplication/success/"
				+ permitNocApplication.getBpaNocApplication().getNocApplicationNumber();
	}
	
	  protected String getDesinationNameByPosition(Position pos) {
	        return pos.getDeptDesig() != null && pos.getDeptDesig().getDesignation() == null
	                ? ""
	                : pos.getDeptDesig().getDesignation().getName();
	    }

	@RequestMapping(value = "/view/{applicationNumber}", method = RequestMethod.GET)
	public String viewNocApplication(@PathVariable final String applicationNumber, final Model model) {
		PermitNocApplication permitNoc = permitNocService.findByNocApplicationNumber(applicationNumber);
		for (PermitNocDocument nocDocument : permitNoc.getBpaApplication().getPermitNocDocuments()) {
			if (nocDocument.getNocDocument().getServiceChecklist().getChecklist().getCode()
					.equals(permitNoc.getBpaNocApplication().getNocType()))
				model.addAttribute("nocDocs", nocDocument);
		}
		model.addAttribute("bpaApp",
				applicationBpaService.findByApplicationNumber(permitNoc.getBpaApplication().getApplicationNumber()));
		permitNoc.getBpaApplication().setPermitOccupanciesTemp(permitNoc.getBpaApplication().getPermitOccupancies());
		model.addAttribute("occupancyList", occupancyService.findAllOrderByOrderNumber());
		model.addAttribute("permitNocApplication", permitNoc);
		return "view-noc-details";
	}

	@GetMapping("/success/{applicationNumber}")
	public String success(@PathVariable final String applicationNumber, final Model model) {
		PermitNocApplication noc = permitNocService.findByNocApplicationNumber(applicationNumber);
		model.addAttribute("noc", noc);
		return "noc-success";
	}

	private void buildNocFiles(final BpaNocApplication nocApplication) {
		if (ArrayUtils.isNotEmpty(nocApplication.getFiles())) {
			Set<FileStoreMapper> existingFiles = new HashSet<>();
			existingFiles.addAll(nocApplication.getNocSupportDocs());
			existingFiles.addAll(applicationBpaService.addToFileStore(nocApplication.getFiles()));
			nocApplication.setNocSupportDocs(existingFiles);
		}
	}

}
