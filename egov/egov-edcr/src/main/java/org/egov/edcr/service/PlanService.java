package org.egov.edcr.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.egov.common.entity.edcr.EdcrPdfDetail;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.PlanFeature;
import org.egov.common.entity.edcr.PlanInformation;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.contract.EdcrRequest;
import org.egov.edcr.entity.Amendment;
import org.egov.edcr.entity.AmendmentDetails;
import org.egov.edcr.entity.EdcrApplication;
import org.egov.edcr.entity.EdcrApplicationDetail;
import org.egov.edcr.feature.AccessoryBuildingService;
import org.egov.edcr.feature.FeatureProcess;
import org.egov.edcr.feature.FireStair;
import org.egov.edcr.feature.GeneralStair;
import org.egov.edcr.feature.OpenStairService;
import org.egov.edcr.feature.PassageService;
import org.egov.edcr.feature.Verandah;
import org.egov.edcr.utility.DcrConstants;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class PlanService {
	private static final Logger LOG = Logger.getLogger(PlanService.class);
	@Autowired
	private PlanFeatureService featureService;
	@Autowired
	private FileStoreService fileStoreService;
	@Autowired
	private CustomImplProvider specificRuleService;
	@Autowired
	private EdcrApplicationDetailService edcrApplicationDetailService;
	@Autowired
	private EdcrPdfDetailService edcrPdfDetailService;
	@Autowired
	private ExtractService extractService;
	@Autowired
	private EdcrApplicationService edcrApplicationService;

	public Plan process(EdcrApplication dcrApplication, String applicationType) {
		Map<String, String> cityDetails = specificRuleService.getCityDetails();

		Date asOnDate = null;
		if (dcrApplication.getPermitApplicationDate() != null) {
			asOnDate = dcrApplication.getPermitApplicationDate();
		} else if (dcrApplication.getApplicationDate() != null) {
			asOnDate = dcrApplication.getApplicationDate();
		} else {
			asOnDate = new Date();
		}

		AmendmentService repo = (AmendmentService) specificRuleService.find("amendmentService");
		Amendment amd = repo.getAmendments();

		Plan plan = extractService.extract(dcrApplication.getSavedDxfFile(), amd, asOnDate,
				featureService.getFeatures());
		
		// add serviceType for validation
		plan.setServiceType(dcrApplication.getServiceType());

		System.out.println(plan.getBlocks());

		plan = applyRules(plan, amd, cityDetails);
		
		for(ScrutinyDetail scrutinyDetail:plan.getReportOutput().getScrutinyDetails()) {
			System.out.println(scrutinyDetail.getKey()+"  "+scrutinyDetail.getDetail());
		}
		setEDCRmandatoryNOC(plan);
		InputStream reportStream = generateReport(plan, amd, dcrApplication);
		saveOutputReport(dcrApplication, reportStream, plan);
		return plan;
	}

	private void setEDCRmandatoryNOC(Plan plan) {
		/*if(null != plan.getPlanInfoProperties().get(DxfFileConstants.FIRE_NOC))
			plan.getPlanInformation().setNocFireDept(plan.getPlanInfoProperties().get(DxfFileConstants.FIRE_NOC));
		if(null != plan.getPlanInfoProperties().get(DxfFileConstants.PUB_HEALTH_7_NOC))				
			plan.getPlanInformation().setNocPH7Dept(plan.getPlanInfoProperties().get(DxfFileConstants.PUB_HEALTH_7_NOC));
		if(null != plan.getPlanInfoProperties().get(DxfFileConstants.TEHSILDAR_NOC))
			plan.getPlanInformation().setNocTehsildarDept(plan.getPlanInfoProperties().get(DxfFileConstants.TEHSILDAR_NOC));
		if(null != plan.getPlanInfoProperties().get(DxfFileConstants.PUB_HEALTH_NOC))
			plan.getPlanInformation().setNocPHDept(plan.getPlanInfoProperties().get(DxfFileConstants.PUB_HEALTH_NOC));
		if(null != plan.getPlanInfoProperties().get(DxfFileConstants.MANIMAJARA_NOC))
			plan.getPlanInformation().setNocManimajaraDept(plan.getPlanInfoProperties().get(DxfFileConstants.MANIMAJARA_NOC));
		if(null != plan.getPlanInfoProperties().get(DxfFileConstants.ROAD_2_NOC))
			plan.getPlanInformation().setNocRoad2Dept(plan.getPlanInfoProperties().get(DxfFileConstants.ROAD_2_NOC));
		if(null != plan.getPlanInfoProperties().get(DxfFileConstants.PAC_NOC))
			plan.getPlanInformation().setNocPACDept(plan.getPlanInfoProperties().get(DxfFileConstants.PAC_NOC));
		if(null != plan.getPlanInfoProperties().get(DxfFileConstants.STRUCTURE_NOC))
			plan.getPlanInformation().setNocStructureDept(plan.getPlanInfoProperties().get(DxfFileConstants.STRUCTURE_NOC));
		if(null != plan.getPlanInfoProperties().get(DxfFileConstants.ELECTRICAL_NOC))
			plan.getPlanInformation().setNocElectricalDept(plan.getPlanInfoProperties().get(DxfFileConstants.ELECTRICAL_NOC));
		if(null != plan.getPlanInfoProperties().get(DxfFileConstants.POL_CONTROL_NOC))
			plan.getPlanInformation().setNocPollutionDept(plan.getPlanInfoProperties().get(DxfFileConstants.POL_CONTROL_NOC));*/
		
		plan.getPlanInformation().setNocPACDept("NO");
		plan.getPlanInformation().setNocStructureDept("NO");
		plan.getPlanInformation().setNocFireDept("NO");
		plan.getPlanInformation().setNocTehsildarDept("NO");
		plan.getPlanInformation().setNocManimajaraDept("NO");
		plan.getPlanInformation().setNocElectricalDept("NO");
		plan.getPlanInformation().setNocPollutionDept("NO");
		plan.getPlanInformation().setNocPH7Dept("NO");
		plan.getPlanInformation().setNocPHDept("NO");
		plan.getPlanInformation().setNocRoad2Dept("NO");
		if(null!=plan) {
			OccupancyTypeHelper occupancyTypeHelper = plan.getVirtualBuilding() != null
					? plan.getVirtualBuilding().getMostRestrictiveFarHelper()
					: null;
			String boundaryType = "";
			if(null != plan.getPlanInfoProperties().get(DxfFileConstants.ROOT_BOUNDARY_TYPE)) {
				boundaryType = plan.getPlanInfoProperties().get(DxfFileConstants.ROOT_BOUNDARY_TYPE);
			}
			if(boundaryType.equalsIgnoreCase(DxfFileConstants.URBAN)) {
				if(DxfFileConstants.A_G.equalsIgnoreCase(occupancyTypeHelper.getSubtype().getCode())
					|| DxfFileConstants.A_P.equalsIgnoreCase(occupancyTypeHelper.getSubtype().getCode())){
					plan.getPlanInformation().setNocPACDept("YES");
					plan.getPlanInformation().setNocStructureDept("YES");
				}else {
					plan.getPlanInformation().setNocPACDept("YES");
					plan.getPlanInformation().setNocFireDept("YES");
					plan.getPlanInformation().setNocStructureDept("YES");
					plan.getPlanInformation().setNocElectricalDept("YES");
					plan.getPlanInformation().setNocPollutionDept("YES");
					plan.getPlanInformation().setNocPH7Dept("YES");
				}
			}else if(boundaryType.equalsIgnoreCase(DxfFileConstants.RURAL)){
				plan.getPlanInformation().setNocFireDept("YES");
				plan.getPlanInformation().setNocTehsildarDept("YES");
				plan.getPlanInformation().setNocManimajaraDept("YES");
				plan.getPlanInformation().setNocElectricalDept("YES");
				plan.getPlanInformation().setNocPollutionDept("YES");
				plan.getPlanInformation().setNocPHDept("YES");
				plan.getPlanInformation().setNocRoad2Dept("YES");
			}
		}
	}
	
	public void savePlanDetail(Plan plan, EdcrApplicationDetail detail) {

		if (LOG.isInfoEnabled())
			LOG.info("*************Before serialization******************");
		File f = new File("plandetail.txt");
		try (FileOutputStream fos = new FileOutputStream(f); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			mapper.writeValue(f, plan);
			detail.setPlanDetailFileStore(
					fileStoreService.store(f, f.getName(), "text/plain", DcrConstants.APPLICATION_MODULE_TYPE));
			oos.flush();
		} catch (IOException e) {
			LOG.error("Unable to serialize!!!!!!", e);
		}
		if (LOG.isInfoEnabled())
			LOG.info("*************Completed serialization******************");

	}

	// method for returning list of applicable feature rule
	private List<PlanFeature> getPlanFeatures(Plan plan) {
		List<PlanFeature> serviceTypeFeatures = featureService.getFeatures();
		ListIterator<PlanFeature> features = serviceTypeFeatures.listIterator();

		switch (plan.getServiceType()) {
		case DxfFileConstants.NEW_CONSTRUCTION:

			break;
		case DxfFileConstants.ALTERATION:

			while (features.hasNext()) {

				PlanFeature feature = features.next();
				
				if (feature.getRuleClass().isAssignableFrom(GeneralStair.class)//1. All Staircases
						|| feature.getRuleClass().isAssignableFrom(org.egov.edcr.feature.SpiralStair.class)
						|| feature.getRuleClass().isAssignableFrom(FireStair.class)
						|| feature.getRuleClass().isAssignableFrom(OpenStairService.class)
						|| feature.getRuleClass().isAssignableFrom(Verandah.class)//2.  Light and Ventilation
						|| feature.getRuleClass().isAssignableFrom(PassageService.class)//3. Width of passage corridor
						|| feature.getRuleClass().isAssignableFrom(AccessoryBuildingService.class)//4. Construction in rear courtyard
						) {
					System.out.println(feature.getRuleClass());
					features.remove();
				}
			}

			break;
		default:
			break;
		}

		return serviceTypeFeatures;
	}

	private Plan applyRules(Plan plan, Amendment amd, Map<String, String> cityDetails) {

		// check whether valid amendments are present
		int index = -1;
		AmendmentDetails[] a = null;
		int length = amd.getDetails().size();
		if (!amd.getDetails().isEmpty()) {
			index = amd.getIndex(plan.getApplicationDate());
			a = new AmendmentDetails[amd.getDetails().size()];
			amd.getDetails().toArray(a);
		}

		// for (PlanFeature ruleClass : featureService.getFeatures()) {
		for (PlanFeature ruleClass : getPlanFeatures(plan)) {

			FeatureProcess rule = null;
			String str = ruleClass.getRuleClass().getSimpleName();
			str = str.substring(0, 1).toLowerCase() + str.substring(1);
			LOG.info("Looking for bean " + str);
			// when amendments are not present
			if (amd.getDetails().isEmpty() || index == -1)
				rule = (FeatureProcess) specificRuleService.find(ruleClass.getRuleClass().getSimpleName());
			// when amendments are present
			else {
				if (index >= 0) {
					// find amendment specific beans
					for (int i = index; i < length; i++) {
						if (a[i].getChanges().keySet().contains(ruleClass.getRuleClass().getSimpleName())) {
							String strNew = str + "_" + a[i].getDateOfBylawString();
							rule = (FeatureProcess) specificRuleService.find(strNew);
							if (rule != null)
								break;
						}
					}
					// when amendment specific beans not found
					if (rule == null) {
						rule = (FeatureProcess) specificRuleService.find(ruleClass.getRuleClass().getSimpleName());
					}

				}

			}

			if (rule != null) {
				LOG.info("Looking for bean resulted in " + rule.getClass().getSimpleName());
				rule.process(plan);
				LOG.info("Completed Process " + rule.getClass().getSimpleName() + "  " + new Date());
			}

			if (plan.getErrors().containsKey(DxfFileConstants.OCCUPANCY_ALLOWED_KEY)
					|| plan.getErrors().containsKey("units not in meters")
					|| plan.getErrors().containsKey(DxfFileConstants.OCCUPANCY_PO_NOT_ALLOWED_KEY))
				return plan;
		}
		return plan;
	}

	private InputStream generateReport(Plan plan, Amendment amd, EdcrApplication dcrApplication) {

		String beanName = "PlanReportService";
		PlanReportService service = null;
		int index = -1;
		AmendmentDetails[] amdArray = null;
		InputStream reportStream = null;
		int length = amd.getDetails().size();
		if (!amd.getDetails().isEmpty()) {
			index = amd.getIndex(plan.getApplicationDate());
			amdArray = new AmendmentDetails[amd.getDetails().size()];
			amd.getDetails().toArray(amdArray);
		}

		try {
			beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);

			if (amd.getDetails().isEmpty() || index == -1)
				service = (PlanReportService) specificRuleService.find(beanName);
			else if (index >= 0) {
				for (int i = index; i < length; i++) {

					service = (PlanReportService) specificRuleService
							.find(beanName + "_" + amdArray[i].getDateOfBylawString());
					if (service != null)
						break;
				}
			}
			if (service == null) {
				service = (PlanReportService) specificRuleService.find(beanName);
			}

			reportStream = service.generateReport(plan, dcrApplication);

		} catch (BeansException e) {
			LOG.error("No Bean Defined for the Rule " + beanName);
		}

		return reportStream;
	}

	@Transactional
	public void saveOutputReport(EdcrApplication edcrApplication, InputStream reportOutputStream, Plan plan) {

		List<EdcrApplicationDetail> edcrApplicationDetails = edcrApplicationDetailService
				.fingByDcrApplicationId(edcrApplication.getId());
		final String fileName = edcrApplication.getApplicationNumber() + "-v" + edcrApplicationDetails.size() + ".pdf";

		final FileStoreMapper fileStoreMapper = fileStoreService.store(reportOutputStream, fileName, "application/pdf",
				DcrConstants.FILESTORE_MODULECODE);

		buildDocuments(edcrApplication, null, fileStoreMapper, plan);

		PlanInformation planInformation = plan.getPlanInformation();
		edcrApplication.getEdcrApplicationDetails().get(0).setPlanInformation(planInformation);
		edcrApplicationDetailService.saveAll(edcrApplication.getEdcrApplicationDetails());
	}

	public void buildDocuments(EdcrApplication edcrApplication, FileStoreMapper dxfFile, FileStoreMapper reportOutput,
			Plan plan) {

		if (dxfFile != null) {
			EdcrApplicationDetail edcrApplicationDetail = new EdcrApplicationDetail();

			edcrApplicationDetail.setDxfFileId(dxfFile);
			edcrApplicationDetail.setApplication(edcrApplication);
			for (EdcrApplicationDetail edcrApplicationDetail1 : edcrApplication.getEdcrApplicationDetails()) {
				edcrApplicationDetail.setPlan(edcrApplicationDetail1.getPlan());
			}
			List<EdcrApplicationDetail> edcrApplicationDetails = new ArrayList<>();
			edcrApplicationDetails.add(edcrApplicationDetail);
			edcrApplication.setSavedEdcrApplicationDetail(edcrApplicationDetail);
			edcrApplication.setEdcrApplicationDetails(edcrApplicationDetails);
		}

		if (reportOutput != null) {
			EdcrApplicationDetail edcrApplicationDetail = edcrApplication.getEdcrApplicationDetails().get(0);

			if (plan.getEdcrPassed()) {
				edcrApplicationDetail.setStatus("Accepted");
				edcrApplication.setStatus("Accepted");
			} else {
				edcrApplicationDetail.setStatus("Not Accepted");
				edcrApplication.setStatus("Not Accepted");
			}
			edcrApplicationDetail.setCreatedDate(new Date());
			edcrApplicationDetail.setReportOutputId(reportOutput);
			List<EdcrApplicationDetail> edcrApplicationDetails = new ArrayList<>();
			edcrApplicationDetails.add(edcrApplicationDetail);
			savePlanDetail(plan, edcrApplicationDetail);

			ArrayList<org.egov.edcr.entity.EdcrPdfDetail> edcrPdfDetails = new ArrayList<>();

			if (plan.getEdcrPdfDetails() != null && !plan.getEdcrPdfDetails().isEmpty()) {
				for (EdcrPdfDetail edcrPdfDetail : plan.getEdcrPdfDetails()) {
					org.egov.edcr.entity.EdcrPdfDetail pdfDetail = new org.egov.edcr.entity.EdcrPdfDetail();
					pdfDetail.setLayer(edcrPdfDetail.getLayer());
					pdfDetail.setFailureReasons(edcrPdfDetail.getFailureReasons());
					pdfDetail.setStandardViolations(edcrPdfDetail.getStandardViolations());

					File convertedPdf = edcrPdfDetail.getConvertedPdf();
					if (convertedPdf != null) {
						FileStoreMapper fileStoreMapper = fileStoreService.store(convertedPdf, convertedPdf.getName(),
								"application/pdf", "Digit DCR");
						pdfDetail.setConvertedPdf(fileStoreMapper);
					}
				}
			}

			if (!edcrPdfDetails.isEmpty()) {
				for (org.egov.edcr.entity.EdcrPdfDetail edcrPdfDetail : edcrPdfDetails) {
					edcrPdfDetail.setEdcrApplicationDetail(edcrApplicationDetail);
				}

				edcrPdfDetailService.saveAll(edcrPdfDetails);
			}

			edcrApplication.setEdcrApplicationDetails(edcrApplicationDetails);
		}
	}

	public Plan extractPlan(EdcrRequest edcrRequest, MultipartFile dxfFile) {
		File planFile = edcrApplicationService.savePlanDXF(dxfFile);

		Date asOnDate = new Date();

		AmendmentService repo = (AmendmentService) specificRuleService.find(AmendmentService.class.getSimpleName());
		Amendment amd = repo.getAmendments();

		Plan plan = extractService.extract(planFile, amd, asOnDate, featureService.getFeatures());
		if (StringUtils.isNotBlank(edcrRequest.getApplicantName()))
			plan.getPlanInformation().setApplicantName(edcrRequest.getApplicantName());
		else
			plan.getPlanInformation().setApplicantName(DxfFileConstants.ANONYMOUS_APPLICANT);

		return plan;
	}
}