package org.egov.edcr.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.egov.edcr.config.properties.EdcrApplicationSettings;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.contract.EdcrRequest;
import org.egov.edcr.entity.Amendment;
import org.egov.edcr.entity.AmendmentDetails;
import org.egov.edcr.entity.EdcrApplication;
import org.egov.edcr.entity.EdcrApplicationDetail;
import org.egov.edcr.feature.AccessoryBuildingService;
import org.egov.edcr.feature.Coverage;
import org.egov.edcr.feature.Far;
import org.egov.edcr.feature.FeatureProcess;
import org.egov.edcr.feature.FireStair;
import org.egov.edcr.feature.GeneralStair;
import org.egov.edcr.feature.OpenStairService;
import org.egov.edcr.feature.PassageService;
import org.egov.edcr.feature.Verandah;
import org.egov.edcr.service.cdg.CDGAdditionalService;
import org.egov.edcr.utility.DcrConstants;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	private boolean isMeterEnabled=false;
	private boolean isFeetEnabled=false;
	
	

	public PlanService(EdcrApplicationSettings  settings) {
		this.isMeterEnabled=Boolean.parseBoolean(settings.getValue(DxfFileConstants.KEY_METER_ENABLE));
		this.isFeetEnabled=Boolean.parseBoolean(settings.getValue(DxfFileConstants.KEY_FEET_ENABLE));
	}

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
		Plan plan=null;
		try {
			plan = extractService.extract(dcrApplication.getSavedDxfFile(), amd, asOnDate,
					featureService.getFeatures());
			setProperties(plan);
			plan = applyRules(plan, amd, cityDetails);
			setEDCRmandatoryNOC(plan);
		}catch (Exception e) {
			LOG.error(e.getMessage());
			plan=getAbortedSupportPlan(e);
		}
		
//		removeError(plan);
//		if(plan.getErrors().isEmpty()) {
//			plan = applyRules(plan, amd, cityDetails);
//			setEDCRmandatoryNOC(plan);
//		}
		
		if(plan!=null && checkUnits(plan)) {
			plan.setServiceType(dcrApplication.getServiceType());
			plan.setApplicationType(dcrApplication.getApplicationType().toString());
			plan.setPlanPermissionNumber(dcrApplication.getPlanPermitNumber());
		}
		
		InputStream reportStream = generateReport(plan, amd, dcrApplication);
		saveOutputReport(dcrApplication, reportStream, plan);
		return plan;
	}
	
	private Plan  getAbortedSupportPlan(Exception e) {
		Plan pl=new Plan();
		pl.addError("1", "Your drawing is aborted due to one of the following reasons:");
		pl.addError("2", "1. The FAR layers as mentioned in the drawing manual are not provided in the required occupancy colour in dxf file.");
		pl.addError("3", "2. The carpet area layer as mentioned in the drawing manual are not provided in.");
		pl.addError("4", "3. Building foot print layer for setback as mentioned in the drawing manual are not provided in the required occupancy colour in dxf file.");
		pl.addError("5", "4. Plan_Info layer data is not provided as per drawing manual requirements.");
		pl.addError("6", "5. Main Gate and Wicket Gate are not provided as per drawing manual.");
		pl.addError("7", "6. Passage is not provided in the drawing, but the layer is present in the dxf flie.");
		pl.addError("8", "7. Parapet is not provided in the drawing, but the layer is present in the dxf.");
		pl.addError("9", "8. All the layer that are not in use are not deleted from thed drawing.");
		
		return pl;
	}
	
	public boolean checkUnits(Plan plan) {
		boolean flage=false;
		if(plan.getDrawingPreference().getInMeters()) {
			if(isMeterEnabled)
				flage=true;
			else
				plan.addError("UNIT_error", "Meter is not allowed");
		}

		if(plan.getDrawingPreference().getInFeets()) {
			if(isFeetEnabled)
				flage=true;
			else
				plan.addError("UNIT_error", "Feet is not allowed");
		}
		return flage;
	}
	
	public void removeError(Plan plan) {
		if(DxfFileConstants.ALTERATION.equalsIgnoreCase(plan.getServiceType()) || DxfFileConstants.ADDITION_OR_EXTENSION.equalsIgnoreCase(plan.getServiceType())) {
			HashMap<String, String> errors=new HashMap<String, String>();
			for(String key:plan.getErrors().keySet()) {
				String value=plan.getErrors().get(key);
				  if(!value.contains("_STAIR_")) errors.put(key, value);
			}
			plan.addErrors(errors);
		}
	}
	
	private void setProperties(Plan pl) {
		pl.getPlanInformation().setFlushingUnitVolume(pl.getPlanInfoProperties().get(DxfFileConstants.FLUSHING_UNITS_VOLUME_ABOVE_SEVEN_LITRES)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.FLUSHING_UNITS_VOLUME_ABOVE_SEVEN_LITRES):"NA");
		pl.getPlanInformation().setPlotType(pl.getPlanInfoProperties().get(DxfFileConstants.PLOT_TYPE)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.PLOT_TYPE):"NA");
		pl.getPlanInformation().setTotalNumberOfBuildingUsers(pl.getPlanInfoProperties().get(DxfFileConstants.TOTAL_USERS)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.TOTAL_USERS):"NA");
		pl.getPlanInformation().setSolarPhotovoltaicKWP(pl.getPlanInfoProperties().get(DxfFileConstants.SOLAR_PHOTOVOLTAIC_KWP)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.SOLAR_PHOTOVOLTAIC_KWP):"NA");
		pl.getPlanInformation().setWhetherExistingBuildingConstructedWithoutBasement(pl.getPlanInfoProperties().get(DxfFileConstants.EXISTING_BUILDING_CONSTRUCTED_WITHOUT_BASEMENT)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.EXISTING_BUILDING_CONSTRUCTED_WITHOUT_BASEMENT):"NA");
		pl.getPlanInformation().setArchitectInformation(pl.getPlanInfoProperties().get(DxfFileConstants.ARTIFICIAL_AND_MECHANICAL_VENTILATION_PROVIDED)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.ARTIFICIAL_AND_MECHANICAL_VENTILATION_PROVIDED):"NA");
		pl.getPlanInformation().setSoilOrVentilatingPipe(pl.getPlanInfoProperties().get(DxfFileConstants.SOIL_OR_VENTILATING_PIPE_EXTERNAL_WALL)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.SOIL_OR_VENTILATING_PIPE_EXTERNAL_WALL):"NA");
		pl.getPlanInformation().setWhetherAnyStaircaseTouchingLiftShaft(pl.getPlanInfoProperties().get(DxfFileConstants.WHETHER_STAIRCASE_TOUCHING_LIFT_SHAFT)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.WHETHER_STAIRCASE_TOUCHING_LIFT_SHAFT):"NA");
		pl.getPlanInformation().setSolarPhotovoltaicKWP(pl.getPlanInfoProperties().get(DxfFileConstants.SOLAR_PHOTOVOLTAIC_KWP)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.SOLAR_PHOTOVOLTAIC_KWP):"NA");
		pl.getPlanInformation().setBasementServicePrintingPressACPlantsElectricalPanelFiltrationplantsLaundryplantsOrMachinesAutomated(pl.getPlanInfoProperties().get(DxfFileConstants.BASEMENT_SERVICES_PRINTING_PRESS_A_C_PLANTS_ELECTRICAL_PANELS_FILTRATIONPLANTS_LAUNDRYPLANTS_OR_MACHINES_AUTOMATED_STACK_PARKING)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.BASEMENT_SERVICES_PRINTING_PRESS_A_C_PLANTS_ELECTRICAL_PANELS_FILTRATIONPLANTS_LAUNDRYPLANTS_OR_MACHINES_AUTOMATED_STACK_PARKING):"NA");
		pl.getPlanInformation().setServiceFloorHeight(pl.getPlanInfoProperties().get(DxfFileConstants.SERVICE_FLOOR_HEIGHT_M)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.SERVICE_FLOOR_HEIGHT_M):"NA");
		pl.getPlanInformation().setFireSafetyProvisionsAsPerNbcDFPF(pl.getPlanInfoProperties().get(DxfFileConstants.FIRE_SAFETY_PROVISIONS_AS_PER_NBC_DFPF_FSA)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.FIRE_SAFETY_PROVISIONS_AS_PER_NBC_DFPF_FSA):"NA");
		pl.getPlanInformation().setExitRequirmentFireAndLifeSafetyAsPerNBC(pl.getPlanInfoProperties().get(DxfFileConstants.EXIT_REQUIREMENT_FIRE_AND_LIFE_SAFETY_AS_PER_NBC)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.EXIT_REQUIREMENT_FIRE_AND_LIFE_SAFETY_AS_PER_NBC):"NA");
		pl.getPlanInformation().setCombustibleMaterialInFireTower(pl.getPlanInfoProperties().get(DxfFileConstants.COMBUSTIBLE_MATERIAL_IN_FIRE_TOWER)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.COMBUSTIBLE_MATERIAL_IN_FIRE_TOWER):"NA");
		pl.getPlanInformation().setDampProofingAtBasement(pl.getPlanInfoProperties().get(DxfFileConstants.DAMP_PROOFING_AT_BASEMENT)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.DAMP_PROOFING_AT_BASEMENT):"NA");
		pl.getPlanInformation().setHospitalBedded(pl.getPlanInfoProperties().get(DxfFileConstants. Hospital_Bedded)!=null?pl.getPlanInfoProperties().get(DxfFileConstants. Hospital_Bedded):"NA");
		pl.getPlanInformation().setHospitalType(pl.getPlanInfoProperties().get(DxfFileConstants.HOSPITAL_TYPE)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.HOSPITAL_TYPE):"NA");
		pl.getPlanInformation().setSolorWaterHeatingInLtr(pl.getPlanInfoProperties().get(DxfFileConstants.SOLOR_WATER_HEATING_IN_LTR)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.SOLOR_WATER_HEATING_IN_LTR):"NA");
		pl.getPlanInformation().setResidentialNoOwner(pl.getPlanInfoProperties().get(DxfFileConstants.RESIDENTIAL_NO_OWNER)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.RESIDENTIAL_NO_OWNER):"NA");
		pl.getPlanInformation().setPlotNo(pl.getPlanInfoProperties().get(DxfFileConstants.PLOT_NO));
		pl.getPlanInformation().setSectorNumber(pl.getPlanInfoProperties().get(DxfFileConstants.SECTOR_NUMBER));
		pl.getPlanInformation().setZone(pl.getPlanInfoProperties().get(DxfFileConstants.ZONE));
		pl.getPlanInformation().setRootBoundaryType(pl.getPlanInfoProperties().get(DxfFileConstants.ROOT_BOUNDARY_TYPE));
		pl.getPlanInformation().setLocation(pl.getPlanInfoProperties().get(DxfFileConstants.LOCATION)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.LOCATION):"NA");
		pl.getPlanInformation().setPlotLength(pl.getPlanInfoProperties().get(DxfFileConstants.PLOT_LENGTH)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.PLOT_LENGTH):"NA");
		pl.getPlanInformation().setPlotWidth(pl.getPlanInfoProperties().get(DxfFileConstants.PLOT_WIDTH)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.PLOT_WIDTH):"NA");
		pl.getPlanInformation().setCommercialAreaOccupancyAsPerRule(pl.getPlanInfoProperties().get(DxfFileConstants.COMMERCIAL_AREA_OCCUPANCY_AS_PER_RULE)!=null?pl.getPlanInfoProperties().get(DxfFileConstants.COMMERCIAL_AREA_OCCUPANCY_AS_PER_RULE):"NA");
		
		try {
			pl.getPlanInformation().setDemolitionArea(pl.getPlanInfoProperties().get(DxfFileConstants.DEMOLITION_AREA)!=null?new BigDecimal(pl.getPlanInfoProperties().get(DxfFileConstants.DEMOLITION_AREA)):BigDecimal.ZERO);
		}catch (Exception e) {
			pl.addError("DEMOLITION_AREA", "DEMOLITION_AREA is invalid in planinfo layer.");
		}
		if(DxfFileConstants.RURAL.equals(pl.getPlanInfoProperties().get(DxfFileConstants.ROOT_BOUNDARY_TYPE)))
			pl.setRural(true);
		else
			pl.setRural(false);
		
		if(pl.isRural()) {
			if(pl.getPlanInfoProperties().get(DxfFileConstants.ROAD_LENGTH)!=null) {
				try {
					pl.getPlanInformation().setRoadLength(new BigDecimal(pl.getPlanInfoProperties().get(DxfFileConstants.ROAD_LENGTH)));
				}catch (Exception e) {
					pl.addError("ROAD_LENGTH", "ROAD_LENGTH is invalid in planinfo layer.");
				}
			}else {
				pl.addError("ROAD_LENGTH", "ROAD_LENGTH is not provided in planinfo layer.");
			}
			
			if(pl.getPlanInfoProperties().get(DxfFileConstants.ROAD_WIDTH)!=null) {
				try {
					pl.getPlanInformation().setRoadWidth(new BigDecimal(pl.getPlanInfoProperties().get(DxfFileConstants.ROAD_WIDTH)));
				}catch (Exception e) {
					pl.addError("ROAD_WIDTH", "ROAD_WIDTH is invalid in planinfo layer.");
				}
			}else {
				pl.addError("ROAD_WIDTH", "ROAD_WIDTH is not provided in planinfo layer.");
			}
						
			if(pl.getPlanInfoProperties().get(DxfFileConstants.ROAD_2_LENGTH)!=null) {
				try {
					pl.getPlanInformation().setRoadTwoLength(new BigDecimal(pl.getPlanInfoProperties().get(DxfFileConstants.ROAD_2_LENGTH)));
				}catch (Exception e) {}
			}
			
			if(pl.getPlanInfoProperties().get(DxfFileConstants.ROAD_2_WIDTH)!=null) {
				try {
					pl.getPlanInformation().setRoadTwoWidth(new BigDecimal(pl.getPlanInfoProperties().get(DxfFileConstants.ROAD_2_WIDTH)));
				}catch (Exception e) {}
			}
			
			if(null!=pl.getPlanInfoProperties().get(DxfFileConstants.CONVERSION_CHARGES_AREA)) {
				try {
					pl.getPlanInformation().setConversionChargesArea(new BigDecimal(pl.getPlanInfoProperties().get(DxfFileConstants.CONVERSION_CHARGES_AREA)));
				}catch (Exception e) {}
			}
			
			if(null!=pl.getPlanInfoProperties().get(DxfFileConstants.IS_CASE_OF_DEATH)
					&& DxfFileConstants.YES.equalsIgnoreCase(pl.getPlanInfoProperties().get(DxfFileConstants.IS_CASE_OF_DEATH)))
				pl.getPlanInformation().setIsDeathCase(true);
			
			if(null!=pl.getPlanInfoProperties().get(DxfFileConstants.ALLOTMENT_OF_NEW_NUMBER)
					&& DxfFileConstants.YES.equalsIgnoreCase(pl.getPlanInfoProperties().get(DxfFileConstants.ALLOTMENT_OF_NEW_NUMBER)))
				pl.getPlanInformation().setIsAllotmentOfNewNumber(true);
		}
		
		if(pl.getDrawingPreference().getInFeets())
			pl.getPlot().setPlotBndryArea(CDGAdditionalService.inchtoFeetArea(pl.getPlot().getPlotBndryArea()));
		
	}

	private void setEDCRmandatoryNOC(Plan plan) {		
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
			if(occupancyTypeHelper==null || occupancyTypeHelper.getSubtype() ==null)
				return;
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
		List<PlanFeature> serviceTypeFeatures =new ArrayList<PlanFeature>();
		if(DxfFileConstants.URBAN.equalsIgnoreCase(plan.getPlanInfoProperties().get(DxfFileConstants.ROOT_BOUNDARY_TYPE))) {
			serviceTypeFeatures = featureService.getFeatures();
			ListIterator<PlanFeature> features = serviceTypeFeatures.listIterator();

//			switch (plan.getServiceType()) {
//			case DxfFileConstants.NEW_CONSTRUCTION:
//
//				break;
//			case DxfFileConstants.ALTERATION:
//
//				while (features.hasNext()) {
//
//					PlanFeature feature = features.next();
//					
//					if (feature.getRuleClass().isAssignableFrom(GeneralStair.class)//1. All Staircases
//							|| feature.getRuleClass().isAssignableFrom(org.egov.edcr.feature.SpiralStair.class)
//							|| feature.getRuleClass().isAssignableFrom(FireStair.class)
//							|| feature.getRuleClass().isAssignableFrom(OpenStairService.class)
//							|| feature.getRuleClass().isAssignableFrom(Verandah.class)//2.  Light and Ventilation
//							|| feature.getRuleClass().isAssignableFrom(PassageService.class)//3. Width of passage corridor
//							|| feature.getRuleClass().isAssignableFrom(AccessoryBuildingService.class)//4. Construction in rear courtyard
//							) {
//						System.out.println(feature.getRuleClass());
//						features.remove();
//					}
//				}
//
//				break;
//			default:
//				break;
//			}
		}else if(plan.isRural()) {
			serviceTypeFeatures = featureService.getRuralFeatures();
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
			try {
				str = str.substring(0, 1).toLowerCase() + str.substring(1);
				LOG.debug("Looking for bean " + str);
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
					LOG.debug("Looking for bean resulted in " + rule.getClass().getSimpleName());
					rule.process(plan);
					LOG.debug("Completed Process " + rule.getClass().getSimpleName() + "  " + new Date());	
				}
				
				// check occupancy type is present or not
				if (ruleClass.getRuleClass().isAssignableFrom(Coverage.class)) {
					OccupancyTypeHelper occupancyTypeHelper = plan.getVirtualBuilding().getMostRestrictiveFarHelper() != null
							? plan.getVirtualBuilding().getMostRestrictiveFarHelper()
							: null;
					if (occupancyTypeHelper == null && !isOccupancyTypeHelperValid(occupancyTypeHelper)) {
						plan.addError("buildupArea-occ", DxfFileConstants.BLT_UP_AREA_ERROR_MSG);
						return plan;
					}
				}

				if (plan.getErrors().containsKey(DxfFileConstants.OCCUPANCY_ALLOWED_KEY)
						|| plan.getErrors().containsKey("units not in meters")
						|| plan.getErrors().containsKey(DxfFileConstants.OCCUPANCY_PO_NOT_ALLOWED_KEY))
					return plan;
			
			}catch (Exception e) {
				e.printStackTrace();
				plan.addError("Error "+str, "Error occured while processing "+str+" !");
			}
		}
		return plan;
	}
	
	private boolean isOccupancyTypeHelperValid(OccupancyTypeHelper occupancyTypeHelper) {
		boolean flage=false;
		if(occupancyTypeHelper!=null && occupancyTypeHelper.getType()!=null && occupancyTypeHelper.getType().getCode()!=null && occupancyTypeHelper.getSubtype()!=null && occupancyTypeHelper.getSubtype().getCode()!=null)
			flage=true;
		return flage;
	}

	private InputStream generateReport(Plan plan, Amendment amd, EdcrApplication dcrApplication) {

		String beanName = "PlanReportService";
		
		if(DxfFileConstants.URBAN.equalsIgnoreCase(plan.getPlanInfoProperties().get(DxfFileConstants.ROOT_BOUNDARY_TYPE))) {
			beanName = "PlanReportService";
		}else if(DxfFileConstants.RURAL.equalsIgnoreCase(plan.getPlanInfoProperties().get(DxfFileConstants.ROOT_BOUNDARY_TYPE))) {
			beanName = "PlanReportRuralService";
		}
		
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