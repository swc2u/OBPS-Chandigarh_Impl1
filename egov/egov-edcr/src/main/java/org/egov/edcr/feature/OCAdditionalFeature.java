package org.egov.edcr.feature;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.egov.common.entity.dcr.helper.EdcrApplicationInfo;
import org.egov.common.entity.edcr.AccessoryBlock;
import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Data;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.OCDataComparison;
import org.egov.common.entity.edcr.Occupancy;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.service.EdcrExternalService;
import org.egov.edcr.service.cdg.CDGAdditionalService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*
 * This class contain only OC related feature validation 
 */
@Service
public class OCAdditionalFeature extends FeatureProcess {
	private static final Logger LOG = Logger.getLogger(AdditionalFeature2.class);
	@Autowired
	private EdcrExternalService edcrExternalService;
	@Autowired
	private RestTemplate restTemplate;

	private static final String EDCR_PLANIFO_RESTURL = "%s/bpa/application/findby-permit-number?permitNumber=%s";

	private String getDcrNumberByPermitNumber(String permitNumber) {
		final String url = String.format(EDCR_PLANIFO_RESTURL, ApplicationThreadLocals.getDomainURL(), permitNumber);
		String str = restTemplate.getForObject(url, String.class);
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		String dcrNumber = null;
		try {
			json = (JSONObject) parser.parse(str);
			System.out.println(json.get("dcrNumber"));
			dcrNumber = json.get("dcrNumber").toString();
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return dcrNumber;
	}

	private Plan getPlanByPermitNumber(final String permitNumber) {
		Plan plan = null;
		EdcrApplicationInfo edcrApplicationInfo = edcrExternalService
				.loadEdcrApplicationDetails(getDcrNumberByPermitNumber(permitNumber));
		if (edcrApplicationInfo != null) {
			plan = edcrApplicationInfo.getPlan();
		}
		return plan;
	}

	@Override
	public Map<String, Date> getAmendments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Plan validate(Plan pl) {

		return pl;
	}

	@Override
	public Plan process(Plan pl) {
		if (DxfFileConstants.APPLICATION_TYPE_OCCUPANCY_CERTIFICATE.equals(pl.getApplicationType())) {
			minorInternalChanges(pl);
			excessCoverageBeyondZoning6Inch(pl);
			glazingOfVerandah(pl);
			OCAddtionalComparation(pl);
		}
		partitionsOnGroundFloorOnMulti_baysShops(pl);
		return pl;
	}

	public void minorInternalChanges(Plan pl) {// no validation , show buildupArea colorcode 3
		ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.setKey("Common_OC Minor Internal Changes");
		scrutinyDetail.addColumnHeading(1, BLOCK);
		scrutinyDetail.addColumnHeading(2, FLOOR);
		scrutinyDetail.addColumnHeading(3, PROVIDED);
		scrutinyDetail.addColumnHeading(4, STATUS);

		boolean isParsent = false;
		for (Block block : pl.getBlocks()) {
			for (Floor floor : block.getBuilding().getFloors()) {
				for (Occupancy occupancy : floor.getOccupancies()) {
					if (DxfFileConstants.OC_MIC.equals(occupancy.getTypeHelper().getSubtype().getCode())) {

						isParsent = true;
						Map<String, String> details = new HashMap<>();
						details.put(BLOCK, "block " + block.getNumber());
						details.put(FLOOR, "floor " + floor.getNumber());
						details.put(PROVIDED, CDGAdditionalService.viewArea(pl,
								CDGAdditionalService.inchtoFeetArea(occupancy.getBuiltUpArea())));
						details.put(STATUS, Result.Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
					}

				}
			}

		}
		pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

	}

	public void excessCoverageBeyondZoning6Inch(Plan pl) {// take from plan key //
															// EXCESS_COVERAGE_6_INCH_BEYOND_BUILD_UP_AREA= number

		String excessCoverag = "";

		if (null == pl.getPlanInfoProperties().get(DxfFileConstants.EXCESS_COVERAGE_6_INCH_BEYOND_BUILD_UP_AREA))
			pl.addError("EXCESS_COVERAGE_6_INCH_BEYOND_BUILD_UP_AREA",
					"EXCESS_COVERAGE_6_INCH_BEYOND_BUILD_UP_AREA is not defined in plan info.");
		else if ("NA".equalsIgnoreCase(DxfFileConstants.EXCESS_COVERAGE_6_INCH_BEYOND_BUILD_UP_AREA)
				|| DxfFileConstants.YES.equalsIgnoreCase(
						pl.getPlanInfoProperties().get(DxfFileConstants.EXCESS_COVERAGE_6_INCH_BEYOND_BUILD_UP_AREA))
				|| DxfFileConstants.NO.equalsIgnoreCase(
						pl.getPlanInfoProperties().get(DxfFileConstants.EXCESS_COVERAGE_6_INCH_BEYOND_BUILD_UP_AREA)))
			excessCoverag = pl.getPlanInfoProperties()
					.get(DxfFileConstants.EXCESS_COVERAGE_6_INCH_BEYOND_BUILD_UP_AREA);
		else
			pl.addError("EXCESS_COVERAGE_6_INCH_BEYOND_BUILD_UP_AREA",
					"EXCESS_COVERAGE_6_INCH_BEYOND_BUILD_UP_AREA value is not valid in plan info.");

		ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.setKey("Common_OC Excess coverage beyond zoning 6'");
		scrutinyDetail.addColumnHeading(1, DESCRIPTION);
		// scrutinyDetail.addColumnHeading(2, );
		scrutinyDetail.addColumnHeading(2, PROVIDED);
		scrutinyDetail.addColumnHeading(3, STATUS);

		boolean isParsent = true;
		if (isParsent) {

			Map<String, String> details = new HashMap<>();
			details.put(DESCRIPTION, "Excess coverage beyond zoning 6'");
			details.put(PROVIDED, excessCoverag);
			details.put(STATUS, Result.Verify.getResultVal());
			scrutinyDetail.getDetail().add(details);

			pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
		}

	}

	public void glazingOfVerandah(Plan pl) {// Glazing of verandah color code 5
		ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.setKey("Common_OC Glazing of verandah");
		scrutinyDetail.addColumnHeading(1, BLOCK);
		scrutinyDetail.addColumnHeading(2, FLOOR);
		scrutinyDetail.addColumnHeading(3, PROVIDED);
		scrutinyDetail.addColumnHeading(4, STATUS);

		boolean isParsent = false;
		for (Block block : pl.getBlocks()) {
			for (Floor floor : block.getBuilding().getFloors()) {
				for (Occupancy occupancy : floor.getOccupancies()) {
					if (DxfFileConstants.OC_GOV.equals(occupancy.getTypeHelper().getSubtype().getCode())) {

						isParsent = true;
						Map<String, String> details = new HashMap<>();
						details.put(BLOCK, "block " + block.getNumber());
						details.put(FLOOR, "floor " + floor.getNumber());
						details.put(PROVIDED, CDGAdditionalService.viewArea(pl,
								CDGAdditionalService.inchtoFeetArea(occupancy.getBuiltUpArea())));
						details.put(STATUS, Result.Accepted.getResultVal());
						scrutinyDetail.getDetail().add(details);
					}

				}
			}

		}
		pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
	}

	public void partitionsOnGroundFloorOnMulti_baysShops(Plan pl) {// Partitions on ground floor on multi-bays shops
																	// -> key MULTI_BAY_PARTITIONS_NUMBER= it's for
																	// both and optional

		String excessCoverag = "";
		OccupancyTypeHelper mostRestrictiveOccupancyType = pl.getVirtualBuilding() != null
				? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;
		try {

			if (DxfFileConstants.F_SCO.equals(mostRestrictiveOccupancyType.getSubtype().getCode())
					|| DxfFileConstants.F_B.equals(mostRestrictiveOccupancyType.getSubtype().getCode())) {
				BigDecimal valdData = new BigDecimal(
						pl.getPlanInfoProperties().get(DxfFileConstants.MULTI_BAY_PARTITIONS_NUMBER));
			}
			excessCoverag = pl.getPlanInfoProperties().get(DxfFileConstants.MULTI_BAY_PARTITIONS_NUMBER);
		} catch (NullPointerException e) {
			pl.addError("MULTI_BAY_PARTITIONS_NUMBER", "MULTI_BAY_PARTITIONS_NUMBER is not defined in plan info.");
			return;
		} catch (NumberFormatException e) {
			pl.addError("MULTI_BAY_PARTITIONS_NUMBER", "MULTI_BAY_PARTITIONS_NUMBER value is not valid in plan info.");
			return;
		}

		ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.setKey("Common_OC Partitions on ground floor on multi-bays shops");
		scrutinyDetail.addColumnHeading(1, BLOCK);
		scrutinyDetail.addColumnHeading(2, FLOOR);
		scrutinyDetail.addColumnHeading(3, PROVIDED);
		scrutinyDetail.addColumnHeading(4, STATUS);

		boolean isParsent = false;
		if (isParsent) {

			Map<String, String> details = new HashMap<>();
			details.put(BLOCK, "");
			details.put(FLOOR, "floor");
			details.put(PROVIDED, excessCoverag + "'");
			details.put(STATUS, Result.Accepted.getResultVal());
			scrutinyDetail.getDetail().add(details);

			pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
		}
	}

	private void validateDrawingPreference(Plan ocPlan, Plan permitPlan) {
//		if (ocPlan.getDrawingPreference().getInFeets() != permitPlan.getDrawingPreference().getInFeets())
//			ocPlan.addError("DrawingPreference", "drawing Preference is not matching");
	}

	public void OCAddtionalComparation(Plan ocPlan) {
		Plan permitPlan = getPlanByPermitNumber(ocPlan.getPlanPermissionNumber());

		ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.setKey("Common_Building permit and OC data comparison");
		scrutinyDetail.addColumnHeading(1, DESCRIPTION);
		scrutinyDetail.addColumnHeading(2, OC);
		scrutinyDetail.addColumnHeading(3, PERMIT);
		scrutinyDetail.addColumnHeading(4, DEVIATION);
		scrutinyDetail.addColumnHeading(5, STATUS);

		validateDrawingPreference(ocPlan, permitPlan);

		additionalFeeResidentialPlotted(ocPlan, permitPlan, scrutinyDetail);
		labourcess(ocPlan, permitPlan, scrutinyDetail);
		additionalCoverageInRearCourtyard(ocPlan, permitPlan, scrutinyDetail);
		rule5(ocPlan, permitPlan, scrutinyDetail);
		minorInternalChangesDuringConstruction(ocPlan, permitPlan, scrutinyDetail);
		excessCoverageBeyondZoning6Inch(ocPlan, permitPlan, scrutinyDetail);
		glazingOfVerandah(ocPlan, permitPlan, scrutinyDetail);
		additionalHeightOnSecondFloorOfSCFsConvertedIntoSCOs(ocPlan, permitPlan, scrutinyDetail);
		PartitionsOnGroundFloorOnMulti_baysShops(ocPlan, permitPlan, scrutinyDetail);
		
		ocPlan.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
	}
	
	

	public void PartitionsOnGroundFloorOnMulti_baysShops(Plan ocPlan, Plan permitPlan, ScrutinyDetail scrutinyDetail) {
		BigDecimal ocValue = BigDecimal.ZERO;
		OccupancyTypeHelper mostRestrictiveOccupancyType = ocPlan.getVirtualBuilding() != null
				? ocPlan.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;
		try {
			if (DxfFileConstants.F_SCO.equals(mostRestrictiveOccupancyType.getSubtype().getCode())
					|| DxfFileConstants.F_B.equals(mostRestrictiveOccupancyType.getSubtype().getCode())) {
				ocValue = new BigDecimal(
						ocPlan.getPlanInfoProperties().get(DxfFileConstants.MULTI_BAY_PARTITIONS_NUMBER));
			}
		} catch (Exception e) {} 
		
		ocPlan.getOcdataComparison().addData(OCDataComparison.Partitions_on_ground_floor_on_multi_bays_shops,
				new Data(ocValue,
						BigDecimal.ZERO, ocValue));
		
		String description = OCDataComparison.Partitions_on_ground_floor_on_multi_bays_shops;
		String oc = CDGAdditionalService.viewArea(ocPlan, ocValue);
		
		String deviation = CDGAdditionalService.viewArea(ocPlan, ocValue);
		String status = validateDeviation(ocValue) ? Result.Verify.getResultVal()
				: Result.Not_Accepted.getResultVal();
		setReport(scrutinyDetail, description, oc, "", deviation, status);
	}

	public void additionalHeightOnSecondFloorOfSCFsConvertedIntoSCOs(Plan ocPlan, Plan permitPlan,
			ScrutinyDetail scrutinyDetail) {
		BigDecimal ocAdditionalHeightOnSecondFloorOfSCFsConvertedIntoSCOs = BigDecimal.ZERO;
		BigDecimal permitAdditionalHeightOnSecondFloorOfSCFsConvertedIntoSCOs = BigDecimal.ZERO;
		BigDecimal deviationArea = BigDecimal.ZERO;

		// need to take rule from neetika

		deviationArea = ocAdditionalHeightOnSecondFloorOfSCFsConvertedIntoSCOs
				.subtract(permitAdditionalHeightOnSecondFloorOfSCFsConvertedIntoSCOs);
		ocPlan.getOcdataComparison().addData(OCDataComparison.Glazing_Of_Verandah,
				new Data(ocAdditionalHeightOnSecondFloorOfSCFsConvertedIntoSCOs,
						permitAdditionalHeightOnSecondFloorOfSCFsConvertedIntoSCOs, deviationArea));
		if (ocPlan.getDrawingPreference().getInFeets()) {
			ocAdditionalHeightOnSecondFloorOfSCFsConvertedIntoSCOs = CDGAdditionalService
					.inchtoFeetArea(ocAdditionalHeightOnSecondFloorOfSCFsConvertedIntoSCOs);
			deviationArea = CDGAdditionalService.inchtoFeetArea(deviationArea);
		}
		String description = OCDataComparison.Minor_Internal_Changes_During_Construction;
		String oc = CDGAdditionalService.viewArea(ocPlan, ocAdditionalHeightOnSecondFloorOfSCFsConvertedIntoSCOs);
		String permit = CDGAdditionalService.viewArea(permitPlan,
				permitAdditionalHeightOnSecondFloorOfSCFsConvertedIntoSCOs);
		String deviation = CDGAdditionalService.viewArea(ocPlan, deviationArea);
		String status = validateDeviation(deviationArea) ? Result.Verify.getResultVal()
				: Result.Not_Accepted.getResultVal();
		setReport(scrutinyDetail, description, oc, permit, deviation, status);
	}

	public void glazingOfVerandah(Plan ocPlan, Plan permitPlan, ScrutinyDetail scrutinyDetail) {
		BigDecimal ocGlazingOfVerandahArea=BigDecimal.ZERO;
		BigDecimal permitGlazingOfVerandahArea=BigDecimal.ZERO;
		BigDecimal deviationArea=BigDecimal.ZERO;
		
		for (Block block : ocPlan.getBlocks()) {
			for (Floor floor : block.getBuilding().getFloors()) {
				for (Occupancy occupancy : floor.getOccupancies()) {
					if (DxfFileConstants.OC_GOV.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
						ocGlazingOfVerandahArea=ocGlazingOfVerandahArea.add(occupancy.getBuiltUpArea());
					}

				}
			}
		}
		deviationArea=ocGlazingOfVerandahArea.subtract(permitGlazingOfVerandahArea);
		ocPlan.getOcdataComparison().addData(OCDataComparison.Glazing_Of_Verandah,
				new Data(ocGlazingOfVerandahArea, permitGlazingOfVerandahArea, deviationArea));
		if (ocPlan.getDrawingPreference().getInFeets()) {
			ocGlazingOfVerandahArea = CDGAdditionalService.inchtoFeetArea(ocGlazingOfVerandahArea);
			deviationArea = CDGAdditionalService.inchtoFeetArea(deviationArea);
		}
		String description = OCDataComparison.Minor_Internal_Changes_During_Construction;
		String oc = CDGAdditionalService.viewArea(ocPlan, ocGlazingOfVerandahArea);
		String permit = CDGAdditionalService.viewArea(permitPlan, permitGlazingOfVerandahArea);
		String deviation = CDGAdditionalService.viewArea(ocPlan, deviationArea);
		String status = validateDeviation(deviationArea) ? Result.Verify.getResultVal()
				: Result.Not_Accepted.getResultVal();
		setReport(scrutinyDetail, description, oc, permit, deviation, status);
	}

	public void excessCoverageBeyondZoning6Inch(Plan ocPlan, Plan permitPlan, ScrutinyDetail scrutinyDetail) {
		// DxfFileConstants.EXCESS_COVERAGE_6_INCH_BEYOND_BUILD_UP_AREA

//		if(DxfFileConstants.YES.equalsIgnoreCase(
//						ocPlan.getPlanInfoProperties().get(DxfFileConstants.EXCESS_COVERAGE_6_INCH_BEYOND_BUILD_UP_AREA)){
//			
//			
//			String description = OCDataComparison.Excess_Coverage_Beyond_Zoning_6_INCH;
//			String oc = CDGAdditionalService.viewArea(ocPlan, ocMinorInternalChangesArea);
//			String permit = CDGAdditionalService.viewArea(permitPlan, permitMinorInternalChangesArea);
//			String deviation = CDGAdditionalService.viewArea(ocPlan, deviationArea);
//			String status = validateDeviation(deviationArea) ? Result.Verify.getResultVal()
//					: Result.Not_Accepted.getResultVal();
//			setReport(scrutinyDetail, description, oc, permit, deviation, status);
//			
//		}

	}

	public void minorInternalChangesDuringConstruction(Plan ocPlan, Plan permitPlan, ScrutinyDetail scrutinyDetail) {
		BigDecimal ocMinorInternalChangesArea = BigDecimal.ZERO;
		BigDecimal permitMinorInternalChangesArea = BigDecimal.ZERO;
		BigDecimal deviationArea = ocMinorInternalChangesArea.subtract(permitMinorInternalChangesArea);
		for (Block block : ocPlan.getBlocks()) {
			for (Floor floor : block.getBuilding().getFloors()) {
				for (Occupancy occupancy : floor.getOccupancies()) {
					if (DxfFileConstants.OC_MIC.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
						ocMinorInternalChangesArea = ocMinorInternalChangesArea.add(occupancy.getBuiltUpArea());
					}

				}
			}
		}
		ocPlan.getOcdataComparison().addData(OCDataComparison.Minor_Internal_Changes_During_Construction,
				new Data(ocMinorInternalChangesArea, BigDecimal.ZERO, BigDecimal.ZERO));
		if (ocPlan.getDrawingPreference().getInFeets()) {
			ocMinorInternalChangesArea = CDGAdditionalService.inchtoFeetArea(ocMinorInternalChangesArea);
			deviationArea = CDGAdditionalService.inchtoFeetArea(deviationArea);
		}
		String description = OCDataComparison.Minor_Internal_Changes_During_Construction;
		String oc = CDGAdditionalService.viewArea(ocPlan, ocMinorInternalChangesArea);
		String permit = CDGAdditionalService.viewArea(permitPlan, permitMinorInternalChangesArea);
		String deviation = CDGAdditionalService.viewArea(ocPlan, deviationArea);
		String status = validateDeviation(deviationArea) ? Result.Verify.getResultVal()
				: Result.Not_Accepted.getResultVal();
		setReport(scrutinyDetail, description, oc, permit, deviation, status);
	}

	public void rule5(Plan ocPlan, Plan permitPlan, ScrutinyDetail scrutinyDetail) {
		BigDecimal ocRule5 = BigDecimal.ZERO;
		BigDecimal permitRule5 = BigDecimal.ZERO;
		BigDecimal deviationArea = BigDecimal.ZERO;
		ocRule5 = getRule5Area(ocPlan);
		permitRule5 = getRule5Area(permitPlan);
		deviationArea = ocRule5.subtract(permitRule5);
		ocPlan.getOcdataComparison().addData(OCDataComparison.RULE5,
				new Data(ocRule5, permitRule5, deviationArea));

		if (ocPlan.getDrawingPreference().getInFeets()) {
			ocRule5 = CDGAdditionalService.inchtoFeetArea(ocRule5);
			permitRule5 = CDGAdditionalService.inchtoFeetArea(permitRule5);
			deviationArea = CDGAdditionalService.inchtoFeetArea(deviationArea);
		}

		String description = OCDataComparison.RULE5;
		String oc = CDGAdditionalService.viewArea(ocPlan, ocRule5);
		String permit = CDGAdditionalService.viewArea(permitPlan, permitRule5);
		String deviation = CDGAdditionalService.viewArea(ocPlan, deviationArea);
		String status = validateDeviation(deviationArea) ? Result.Verify.getResultVal()
				: Result.Not_Accepted.getResultVal();
		setReport(scrutinyDetail, description, oc, permit, deviation, status);

	}

	public BigDecimal getRule5Area(Plan plan) {
		BigDecimal rule5Area = BigDecimal.ZERO;
		for (Block block : plan.getBlocks()) {
			for (Floor floor : block.getBuilding().getFloors()) {
				for (Occupancy occupancy : floor.getOccupancies()) {
					rule5Area = rule5Area.add(occupancy.getBuiltUpArea());
				}
			}
		}
		return rule5Area;
	}

	public void additionalCoverageInRearCourtyard(Plan ocPlan, Plan permitPlan, ScrutinyDetail scrutinyDetail) {
		BigDecimal ocCoverageInRearCourtyardArea = BigDecimal.ZERO;
		ocCoverageInRearCourtyardArea = getRearCourtyardArea(ocPlan);
		BigDecimal permitCoverageInRearCourtyardArea = BigDecimal.ZERO;
		permitCoverageInRearCourtyardArea = getRearCourtyardArea(permitPlan);
		BigDecimal deviationArea = ocCoverageInRearCourtyardArea.subtract(permitCoverageInRearCourtyardArea);

		if (ocPlan.getDrawingPreference().getInFeets()) {
			ocCoverageInRearCourtyardArea = CDGAdditionalService.inchtoFeetArea(ocCoverageInRearCourtyardArea);
			permitCoverageInRearCourtyardArea = CDGAdditionalService.inchtoFeetArea(permitCoverageInRearCourtyardArea);
			deviationArea = ocCoverageInRearCourtyardArea.subtract(permitCoverageInRearCourtyardArea);
		}
		ocPlan.getOcdataComparison().addData(OCDataComparison.Additional_Coverage_In_RearCourtyard,
				new Data(ocCoverageInRearCourtyardArea, permitCoverageInRearCourtyardArea,
						deviationArea));
		String description = OCDataComparison.Additional_Coverage_In_RearCourtyard;

		String oc = CDGAdditionalService.viewArea(ocPlan, ocCoverageInRearCourtyardArea);
		String permit = CDGAdditionalService.viewArea(permitPlan, permitCoverageInRearCourtyardArea);
		String deviation = CDGAdditionalService.viewArea(ocPlan, deviationArea);
		String status = validateDeviation(deviationArea) ? Result.Verify.getResultVal()
				: Result.Not_Accepted.getResultVal();
		setReport(scrutinyDetail, description, oc, permit, deviation, status);
	}

	public BigDecimal getRearCourtyardArea(Plan pl) {
		BigDecimal area = BigDecimal.ZERO;
		for (AccessoryBlock accessoryBlock : pl.getAccessoryBlocks()) {
			area = area.add(accessoryBlock.getArea());
		}
		return area;
	}

	public void additionalFeeResidentialPlotted(Plan ocPlan, Plan permitPlan, ScrutinyDetail scrutinyDetail) {

		BigDecimal ocAdditionalFeeArea = additionalFeeResidentialPlotted(ocPlan);
		BigDecimal permitAdditionalFeeArea = additionalFeeResidentialPlotted(permitPlan);
		BigDecimal deviationArea = ocAdditionalFeeArea.subtract(permitAdditionalFeeArea);
		String description = OCDataComparison.Additional_fee_Residential_plotted;
		ocPlan.getOcdataComparison().addData(OCDataComparison.Additional_fee_Residential_plotted,
				new Data(ocAdditionalFeeArea, permitAdditionalFeeArea, deviationArea));
		if (ocPlan.getDrawingPreference().getInFeets() && permitPlan.getDrawingPreference().getInFeets()) {
			ocAdditionalFeeArea = CDGAdditionalService.inchtoFeetArea(ocAdditionalFeeArea);
			permitAdditionalFeeArea = CDGAdditionalService.inchtoFeetArea(permitAdditionalFeeArea);
			deviationArea = CDGAdditionalService.inchtoFeetArea(deviationArea);
		}
		String oc = CDGAdditionalService.viewArea(ocPlan, ocAdditionalFeeArea);
		String permit = CDGAdditionalService.viewArea(permitPlan, permitAdditionalFeeArea);
		String deviation = CDGAdditionalService.viewArea(ocPlan, deviationArea);
		String status = validateDeviation(deviationArea) ? Result.Verify.getResultVal()
				: Result.Not_Accepted.getResultVal();
		setReport(scrutinyDetail, description, oc, permit, deviation, status);
	}

	public void labourcess(Plan ocPlan, Plan permitPlan, ScrutinyDetail scrutinyDetail) {
		BigDecimal ocLabourcessArea = labourcess(ocPlan);
		BigDecimal permitLabourcessArea = labourcess(permitPlan);
		BigDecimal deviationArea = ocLabourcessArea.subtract(permitLabourcessArea);
		String description = OCDataComparison.Labour_Cess;
		ocPlan.getOcdataComparison().addData(OCDataComparison.Labour_Cess,
				new Data(ocLabourcessArea, permitLabourcessArea, deviationArea));
		if (ocPlan.getDrawingPreference().getInFeets() && permitPlan.getDrawingPreference().getInFeets()) {
			ocLabourcessArea = CDGAdditionalService.inchtoFeetArea(ocLabourcessArea);
			permitLabourcessArea = CDGAdditionalService.inchtoFeetArea(permitLabourcessArea);
			deviationArea = CDGAdditionalService.inchtoFeetArea(deviationArea);
		}
		String oc = CDGAdditionalService.viewArea(ocPlan, ocLabourcessArea);
		String permit = CDGAdditionalService.viewArea(permitPlan, permitLabourcessArea);
		String deviation = CDGAdditionalService.viewArea(ocPlan, deviationArea);
		String status = validateDeviation(deviationArea) ? Result.Verify.getResultVal()
				: Result.Not_Accepted.getResultVal();
		setReport(scrutinyDetail, description, oc, permit, deviation, status);
	}

	public BigDecimal labourcess(Plan plan) {
		BigDecimal totalArea = BigDecimal.ZERO;
		for (Block block : plan.getBlocks()) {
			for (Floor floor : block.getBuilding().getFloors()) {
				totalArea = totalArea.add(floor.getArea());
			}
		}
		return totalArea;
	}

	private boolean validateDeviation(BigDecimal deviationArea) {
		if (deviationArea != null && deviationArea.compareTo(BigDecimal.ZERO) < 0)
			return false;
		return true;
	}

	public BigDecimal additionalFeeResidentialPlotted(Plan pl) {
		BigDecimal additionalArea = BigDecimal.ZERO;
		for (Block block : pl.getBlocks()) {
			for (Floor floor : block.getBuilding().getFloors()) {
				for (Occupancy occupancy : floor.getOccupancies()) {
					if (DxfFileConstants.A_AF.equals(occupancy.getTypeHelper().getSubtype().getCode())) {
						if (floor.getNumber() >= 0) {
							additionalArea = occupancy.getBuiltUpArea();
						}
					}
				}

			}
		}
		return additionalArea;
	}

	public void setReport(ScrutinyDetail scrutinyDetail, String description, String OC, String permit, String deviation,
			String status) {
		Map<String, String> details = new HashMap<>();
		details.put(DESCRIPTION, description);
		details.put(OC, OC);
		details.put(PERMIT, permit);
		details.put(DEVIATION, deviation);
		details.put(STATUS, status);
		scrutinyDetail.getDetail().add(details);
	}

}
