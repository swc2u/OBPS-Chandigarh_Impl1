/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2019>  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *      Further, all user interfaces, including but not limited to citizen facing interfaces,
 *         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *         derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *      For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *      For any further queries on attribution, including queries on brand guidelines,
 *         please contact contact@egovernments.org
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.common.entity.edcr;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//These are the declarations of the applicant in the plan using PLAN_INFO layer.
public class PlanInformation implements Serializable {

    private static final String NA = "NA";
    public static final String SEQ_EDCR_PLANINFO = "SEQ_EDCR_PLANINFO";
    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(generator = SEQ_EDCR_PLANINFO, strategy = GenerationType.SEQUENCE)
    private Long id;
    // Plot area defined in PLAN_INFO layer. Using the same to measure coverage and small plot condition.This is the declared plot area in the plan.
    private BigDecimal plotArea = BigDecimal.ZERO;
    // Temporary field used to capture Owner Name
    private String ownerName;
    //Temporary field used to auto populate occupancy detail.
    private String occupancy;
    //Temporary field used for service type.
    private String serviceType;
    //Temporary field used to show amenities used in application.
    private String amenities;
    //Save architect who submitted application mentioned in plan info.
    private String architectInformation;
    // Applicant Name 
    private String applicantName;
    // Extracted from Plan info. Whether plot present in CRZ zone. Expecting default value as NO. 
    private Boolean crzZoneArea = true;
    //Extracted from Plan info. Demolition area to be mentioned in the plan info.
    private BigDecimal demolitionArea = BigDecimal.ZERO;
    //Extracted from Plan info. Depth cutting more than 1.5 Meter flag. 
    private transient Boolean depthCutting;
    //YES/NO/NA.Extracted from Plan info. Is building of type government or aided school.
    private transient Boolean governmentOrAidedSchool;
    //YES/NO/NA.Extracted from Plan info. Is plot comes under security zone flag.
    private transient Boolean securityZone = true;
    //YES/NO/NA.Extracted from Plan info.  Access width to the plot.
	private transient BigDecimal accessWidth;
    //Extracted from Plan info.  In case of medical occupancy, capture number of beds present in the building. Sanitation details are decided based on number of beds present.
	private transient BigDecimal noOfBeds;
	//YES/NO/NA.Extracted from Plan info. NOC received from the side owner.
	private transient String nocToAbutSideDesc = NA;
	//YES/NO/NA.Extracted from Plan info. NOC received from the rear owner.
	private transient String nocToAbutRearDesc = NA;
	//YES/NO/NA.Extracted from Plan info. Any opening on sides of building.
	private transient Boolean openingOnSide = false;
	//YES/NO/NA.Extracted from Plan info. Any opening on rear side of building.
	private transient Boolean openingOnRear = false;
	//Extracted from Plan info. Number of seats present in special occupancy 
	private transient Integer noOfSeats = 0;
	//Extracted from Plan info. Number of mechanical parking declared in the plan
	private transient Integer noOfMechanicalParking = 0;
	//YES/NO/NA.Extracted from Plan info.Is plan belongs to single family building. There are few rules relaxed for single family building 
	private transient Boolean singleFamilyBuilding;
	//Extracted from Plan info. Revenue survey number declared in the plan
    private String reSurveyNo;
    //Extracted from Plan info. Revenue ward name declared in the plan
    private String revenueWard;
    //Extracted from Plan info. Desam name declared in the plan
    private String desam;
    //Extracted from Plan info. Village name declared in the plan
    private String village;
    //Extracted from Plan info. Land Use zone. The value should be standard like RESIDENTIAL,COMMERCIAL,INDUSTRIAL,PUBLICANDSEMIPUBLIC etc.
    private transient String landUseZone;
    //YES/NO/NA. Extracted from Plan info. Is lease hold land
    private transient String leaseHoldLand;
    //Extracted from Plan info. Road width declared in the plan.
    private BigDecimal roadWidth = BigDecimal.ZERO;
    //Extracted from Plan info. Road length declared in the plan.
    private BigDecimal roadLength = BigDecimal.ZERO;
    //Extracted from Plan info. Type of area. Whether old or new area.
    private String typeOfArea;
    //Extracted from Plan info. Average plot depth.
    private BigDecimal depthOfPlot = BigDecimal.ZERO;
    //Extracted from Plan info. Average plot width.
    private BigDecimal widthOfPlot = BigDecimal.ZERO;
    //YES/NO/NA. Extracted from Plan info. Is building near to monument.
    private transient String buildingNearMonument = NA;
    //YES/NO/NA.Extracted from Plan info. Is building near to government building
    private transient String buildingNearGovtBuilding = NA;
    //YES/NO/NA.Extracted from Plan info. Building near monument and permitted with NOC
    private transient String nocNearMonument = NA;
    //YES/NO/NA.Extracted from Plan info. Building near airport and permitted with airport authority
    private transient String nocNearAirport = NA;
    //YES/NO/NA.Extracted from Plan info. Building near defence aerodrome and permitted with NOC
    private transient String nocNearDefenceAerodomes = NA;
    //YES/NO/NA.Extracted from Plan info. Permitted with state Environmental impact assessment study report
    private transient String nocStateEnvImpact = NA;
    //YES/NO/NA.Extracted from Plan info. Permitted with railway NOC
    private transient String nocRailways = NA;
    //YES/NO/NA.Extracted from Plan info. Permitted with noc issued by collector on govt. land
    private transient String nocCollectorGvtLand = NA;
    //YES/NO/NA.Extracted from Plan info. Permitted with irrigation report NOC
    private transient String nocIrrigationDept = NA;
    //YES/NO/NA.Extracted from Plan info. Permitted with fire department NOC
    private transient String nocFireDept = NA;
    //YES/NO/NA.Extracted from Plan info. Building near the river flag
    private transient String buildingNearToRiver = NA;
    //YES/NO/NA.Extracted from Plan info. Barrier free access for physically handicapped person provided.
    private transient String barrierFreeAccessForPhyChlngdPpl = NA;
    //YES/NO/NA.Extracted from Plan info. Provision for green building and sustainability provided in plan.Rainwater harvesting,solar,segregation of waste etc.
    private transient String provisionsForGreenBuildingsAndSustainability = NA;
    //YES/NO/NA.Extracted from Plan info. Fire Protection And Fire Safety Requirements declared in the plan.
    private transient String fireProtectionAndFireSafetyRequirements = NA;
    //Extracted from Plan info.Plot number.
    private String plotNo;
    //Extracted from Plan info.Khata number.
    private String khataNo;
    //Extracted from Plan info.Mauza number.
    private String mauza;
    //Extracted from Plan info.District name.
    private String district;
    //YES/NO/NA.Extracted from Plan info. Rain water declared in plan.
    private transient String rwhDeclared = NA;
    
    //YES/NO/NA.Extracted from Plan info. Permitted with Public health 7  department NOC
    private transient String nocPH7Dept = NA;
    //YES/NO/NA.Extracted from Plan info. Permitted with Tehsildar department NOC
    private transient String nocTehsildarDept = NA;
    //YES/NO/NA.Extracted from Plan info. Permitted with Public health department NOC
    private transient String nocPHDept = NA;
    //YES/NO/NA.Extracted from Plan info. Permitted with Sub-Office Manimajara department NOC
    private transient String nocManimajaraDept = NA;
    //YES/NO/NA.Extracted from Plan info. Permitted with Road 2 department NOC
    private transient String nocRoad2Dept = NA;
    //YES/NO/NA.Extracted from Plan info. Permitted with Urban Planning Department (PAC) department NOC
    private transient String nocPACDept = NA;
    //YES/NO/NA.Extracted from Plan info. Permitted with Structure department NOC
    private transient String nocStructureDept = NA;
    //YES/NO/NA.Extracted from Plan info. Permitted with Electrical department NOC
    private transient String nocElectricalDept = NA;
    //YES/NO/NA.Extracted from Plan info. Permitted with Pollution control department NOC
    private transient String nocPollutionDept = NA;
    
    //ROOT_BOUNDARY_TYPE
    private transient String rootBoundaryType =NA; 
    
    //ZONE
    private transient String zone=NA;
    
    //Sector Number 
    private transient String sectorNumber =NA;
    
    //FLUSHING_UNITS_VOLUME_ABOVE_SEVEN_LITRES
    private transient String flushingUnitVolume=NA;
    
    //PLOT_TYPE
    private transient String plotType =NA;
    
    //TOTAL_USERS
    private transient String totalNumberOfBuildingUsers=NA;
    
    //SOLAR_PHOTOVOLTAIC_KWP
    private transient String solarPhotovoltaicPanelscapacity =NA;
    
    //EXISTING_BUILDING_CONSTRUCTED_WITHOUT_BASEMENT
    private transient String whetherExistingBuildingConstructedWithoutBasement=NA;
    
    //EXISTING_BUILDING_CONSTRUCTED_WITHOUT_BASEMENT
    private transient String  whetherArtificialAndMechanicalVentilatorsProvided=NA;
    
    //SOIL_OR_VENTILATING_PIPE_EXTERNAL_WALL
    private transient String soilOrVentilatingPipe=NA;
    
    //WHETHER_STAIRCASE_TOUCHING_LIFT_SHAFT
    private transient String WhetherAnyStaircaseTouchingLiftShaft =NA; 
    
    //SOLAR_PHOTOVOLTAIC_KWP
    private transient String solarPhotovoltaicKWP=NA;
    
    //BASEMENT_SERVICES_PRINTING PRESS_A.C. PLANTS_ELECTRICAL PANELS_FILTRATIONPLANTS_LAUNDRYPLANTS_OR_MACHINES_ AUTOMATED/STACK PARKING
    private transient String basementServicePrintingPressACPlantsElectricalPanelFiltrationplantsLaundryplantsOrMachinesAutomated;
    
    //SERVICE_FLOOR_HEIGHT_M=
    private transient String serviceFloorHeight=NA;
    
    //FIRE_SAFETY_PROVISIONS_AS_PER_NBC_DFPF_FSA
    private transient String fireSafetyProvisionsAsPerNbcDFPF=NA;

    //EXIT_REQUIREMENT_FIRE_&_LIFE_SAFETY_AS_PER_NBC=
    private transient String exitRequirmentFireAndLifeSafetyAsPerNBC=NA;
    
    //COMBUSTIBLE_MATERIAL _IN_FIRE_TOWER
    private transient String combustibleMaterialInFireTower=NA;
    
    //DAMP_PROOFING_AT_BASEMENT
    private transient String dampProofingAtBasement=NA;
    
    // Hospital _Bedded 
    private transient String hospitalBedded=NA;
    
    //Hospital_type
    private transient String hospitalType=NA;

    //SOLOR_WATER_HEATING_IN_LTR
    private transient String solorWaterHeatingInLtr=NA;
    
    //RESIDENTIAL_NO_OWNER
    private transient String residentialNoOwner=NA;
    
	public String getRootBoundaryType() {
		return rootBoundaryType;
	}

	public void setRootBoundaryType(String rootBoundaryType) {
		this.rootBoundaryType = rootBoundaryType;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zoon) {
		this.zone = zoon;
	}

	public String getSectorNumber() {
		return sectorNumber;
	}

	public void setSectorNumber(String sectorNumber) {
		this.sectorNumber = sectorNumber;
	}

	public String getFlushingUnitVolume() {
		return flushingUnitVolume;
	}

	public void setFlushingUnitVolume(String flushingUnitVolume) {
		this.flushingUnitVolume = flushingUnitVolume;
	}

	public String getPlotType() {
		return plotType;
	}

	public void setPlotType(String plotType) {
		this.plotType = plotType;
	}

	public String getTotalNumberOfBuildingUsers() {
		return totalNumberOfBuildingUsers;
	}

	public void setTotalNumberOfBuildingUsers(String totalNumberOfBuildingUsers) {
		this.totalNumberOfBuildingUsers = totalNumberOfBuildingUsers;
	}

	public String getSolarPhotovoltaicPanelscapacity() {
		return solarPhotovoltaicPanelscapacity;
	}

	public void setSolarPhotovoltaicPanelscapacity(String solarPhotovoltaicPanelscapacity) {
		this.solarPhotovoltaicPanelscapacity = solarPhotovoltaicPanelscapacity;
	}

	public String getWhetherExistingBuildingConstructedWithoutBasement() {
		return whetherExistingBuildingConstructedWithoutBasement;
	}

	public void setWhetherExistingBuildingConstructedWithoutBasement(
			String whetherExistingBuildingConstructedWithoutBasement) {
		this.whetherExistingBuildingConstructedWithoutBasement = whetherExistingBuildingConstructedWithoutBasement;
	}

	public String getWhetherArtificialAndMechanicalVentilatorsProvided() {
		return whetherArtificialAndMechanicalVentilatorsProvided;
	}

	public void setWhetherArtificialAndMechanicalVentilatorsProvided(
			String whetherArtificialAndMechanicalVentilatorsProvided) {
		this.whetherArtificialAndMechanicalVentilatorsProvided = whetherArtificialAndMechanicalVentilatorsProvided;
	}

	public String getSoilOrVentilatingPipe() {
		return soilOrVentilatingPipe;
	}

	public void setSoilOrVentilatingPipe(String soilOrVentilatingPipe) {
		this.soilOrVentilatingPipe = soilOrVentilatingPipe;
	}

	public String getWhetherAnyStaircaseTouchingLiftShaft() {
		return WhetherAnyStaircaseTouchingLiftShaft;
	}

	public void setWhetherAnyStaircaseTouchingLiftShaft(String whetherAnyStaircaseTouchingLiftShaft) {
		WhetherAnyStaircaseTouchingLiftShaft = whetherAnyStaircaseTouchingLiftShaft;
	}

	public String getSolarPhotovoltaicKWP() {
		return solarPhotovoltaicKWP;
	}

	public void setSolarPhotovoltaicKWP(String solarPhotovoltaicKWP) {
		this.solarPhotovoltaicKWP = solarPhotovoltaicKWP;
	}

	public String getBasementServicePrintingPressACPlantsElectricalPanelFiltrationplantsLaundryplantsOrMachinesAutomated() {
		return basementServicePrintingPressACPlantsElectricalPanelFiltrationplantsLaundryplantsOrMachinesAutomated;
	}

	public void setBasementServicePrintingPressACPlantsElectricalPanelFiltrationplantsLaundryplantsOrMachinesAutomated(
			String basementServicePrintingPressACPlantsElectricalPanelFiltrationplantsLaundryplantsOrMachinesAutomated) {
		this.basementServicePrintingPressACPlantsElectricalPanelFiltrationplantsLaundryplantsOrMachinesAutomated = basementServicePrintingPressACPlantsElectricalPanelFiltrationplantsLaundryplantsOrMachinesAutomated;
	}

	public String getServiceFloorHeight() {
		return serviceFloorHeight;
	}

	public void setServiceFloorHeight(String serviceFloorHeight) {
		this.serviceFloorHeight = serviceFloorHeight;
	}

	public String getFireSafetyProvisionsAsPerNbcDFPF() {
		return fireSafetyProvisionsAsPerNbcDFPF;
	}

	public void setFireSafetyProvisionsAsPerNbcDFPF(String fireSafetyProvisionsAsPerNbcDFPF) {
		this.fireSafetyProvisionsAsPerNbcDFPF = fireSafetyProvisionsAsPerNbcDFPF;
	}

	public String getExitRequirmentFireAndLifeSafetyAsPerNBC() {
		return exitRequirmentFireAndLifeSafetyAsPerNBC;
	}

	public void setExitRequirmentFireAndLifeSafetyAsPerNBC(String exitRequirmentFireAndLifeSafetyAsPerNBC) {
		this.exitRequirmentFireAndLifeSafetyAsPerNBC = exitRequirmentFireAndLifeSafetyAsPerNBC;
	}

	public String getCombustibleMaterialInFireTower() {
		return combustibleMaterialInFireTower;
	}

	public void setCombustibleMaterialInFireTower(String combustibleMaterialInFireTower) {
		this.combustibleMaterialInFireTower = combustibleMaterialInFireTower;
	}

	public String getDampProofingAtBasement() {
		return dampProofingAtBasement;
	}

	public void setDampProofingAtBasement(String dampProofingAtBasement) {
		this.dampProofingAtBasement = dampProofingAtBasement;
	}

	public String getHospitalBedded() {
		return hospitalBedded;
	}

	public void setHospitalBedded(String hospitalBedded) {
		this.hospitalBedded = hospitalBedded;
	}

	public String getHospitalType() {
		return hospitalType;
	}

	public void setHospitalType(String hospitalType) {
		this.hospitalType = hospitalType;
	}

	public String getSolorWaterHeatingInLtr() {
		return solorWaterHeatingInLtr;
	}

	public void setSolorWaterHeatingInLtr(String solorWaterHeatingInLtr) {
		this.solorWaterHeatingInLtr = solorWaterHeatingInLtr;
	}

	public String getResidentialNoOwner() {
		return residentialNoOwner;
	}

	public void setResidentialNoOwner(String residentialNoOwner) {
		this.residentialNoOwner = residentialNoOwner;
	}

    public Boolean getGovernmentOrAidedSchool() {
        return governmentOrAidedSchool;
    }

    public void setGovernmentOrAidedSchool(Boolean governmentOrAidedSchool) {
        this.governmentOrAidedSchool = governmentOrAidedSchool;
    }

    public Boolean getCrzZoneArea() {
        return crzZoneArea;
    }

    public void setCrzZoneArea(Boolean crzZoneArea) {
        this.crzZoneArea = crzZoneArea;
    }

    public BigDecimal getPlotArea() {
        return plotArea;
    }

    public void setPlotArea(BigDecimal plotArea) {
        this.plotArea = plotArea;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getArchitectInformation() {
        return architectInformation;
    }

    public void setArchitectInformation(String architectInformation) {
        this.architectInformation = architectInformation;
    }

    public String getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }

    public Boolean getSecurityZone() {
        return securityZone;
    }

    public void setSecurityZone(Boolean securityZone) {
        this.securityZone = securityZone;
    }

    public BigDecimal getAccessWidth() {
        return accessWidth;
    }

    public Boolean getDepthCutting() {
        return depthCutting;
    }

    public void setDepthCutting(Boolean depthCutting) {
        this.depthCutting = depthCutting;
    }

    public void setAccessWidth(BigDecimal accessWidth) {
        this.accessWidth = accessWidth;
    }

    /*
     * public Boolean getNocToAbutSide() { return nocToAbutSide; } public void setNocToAbutSide(Boolean nocToAbutSide) {
     * this.nocToAbutSide = nocToAbutSide; }
     */
    /*
     * public Boolean getNocToAbutRear() { return nocToAbutRear; } public void setNocToAbutRear(Boolean nocToAbutRear) {
     * this.nocToAbutRear = nocToAbutRear; }
     */

    public Boolean getOpeningOnSide() {
        return openingOnSide;
    }

    public void setOpeningOnSide(Boolean openingOnSide) {
        this.openingOnSide = openingOnSide;
    }

    /*
     * public Boolean getOpeningOnSideBelow2mts() { return openingOnSideBelow2mts; }
     */

    /*
     * public void setOpeningOnSideBelow2mts(Boolean openingOnSideBelow2mts) { this.openingOnSideBelow2mts =
     * openingOnSideBelow2mts; }
     */
    /*
     * public Boolean getOpeningOnSideAbove2mts() { return openingOnSideAbove2mts; }
     */

    /*
     * public void setOpeningOnSideAbove2mts(Boolean openingOnSideAbove2mts) { this.openingOnSideAbove2mts =
     * openingOnSideAbove2mts; }
     */

    /*
     * public Boolean getOpeningOnRearBelow2mts() { return openingOnRearBelow2mts; }
     */

    /*
     * public void setOpeningOnRearBelow2mts(Boolean openingOnRearBelow2mts) { this.openingOnRearBelow2mts =
     * openingOnRearBelow2mts; }
     */
    /*
     * public Boolean getOpeningOnRearAbove2mts() { return openingOnRearAbove2mts; }
     */

    /*
     * public void setOpeningOnRearAbove2mts(Boolean openingOnRearAbove2mts) { this.openingOnRearAbove2mts =
     * openingOnRearAbove2mts; }
     */

    /*
     * public Boolean getNocToAbutAdjascentSide() { return nocToAbutAdjascentSide; } public void setNocToAbutAdjascentSide(Boolean
     * nocToAbutAdjascentSide) { this.nocToAbutAdjascentSide = nocToAbutAdjascentSide; }
     */

    public Boolean getOpeningOnRear() {
        return openingOnRear;
    }

    public void setOpeningOnRear(Boolean openingOnRear) {
        this.openingOnRear = openingOnRear;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public BigDecimal getNoOfBeds() {
        return noOfBeds;
    }

    public void setNoOfBeds(BigDecimal noOfBeds) {
        this.noOfBeds = noOfBeds;
    }

    public Integer getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(Integer noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public Integer getNoOfMechanicalParking() {
        return noOfMechanicalParking;
    }

    public void setNoOfMechanicalParking(Integer noOfMechanicalParking) {
        this.noOfMechanicalParking = noOfMechanicalParking;
    }

    public Boolean getSingleFamilyBuilding() {
        return singleFamilyBuilding;
    }

    public void setSingleFamilyBuilding(Boolean singleFamilyBuilding) {
        this.singleFamilyBuilding = singleFamilyBuilding;
    }

    public BigDecimal getDemolitionArea() {
        return demolitionArea;
    }

    public void setDemolitionArea(BigDecimal demolitionArea) {
        this.demolitionArea = demolitionArea;
    }

    public String getReSurveyNo() {
        return reSurveyNo;
    }

    public void setReSurveyNo(String reSurveyNo) {
        this.reSurveyNo = reSurveyNo;
    }

    public String getRevenueWard() {
        return revenueWard;
    }

    public void setRevenueWard(String revenueWard) {
        this.revenueWard = revenueWard;
    }

    public String getDesam() {
        return desam;
    }

    public void setDesam(String desam) {
        this.desam = desam;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getNocToAbutSideDesc() {
        return nocToAbutSideDesc;
    }

    public void setNocToAbutSideDesc(String nocToAbutSideDesc) {
        this.nocToAbutSideDesc = nocToAbutSideDesc;
    }

    public String getNocToAbutRearDesc() {
        return nocToAbutRearDesc;
    }

    public void setNocToAbutRearDesc(String nocToAbutRearDesc) {
        this.nocToAbutRearDesc = nocToAbutRearDesc;
    }

    public String getLandUseZone() {
        return landUseZone;
    }

    public void setLandUseZone(String landUseZone) {
        this.landUseZone = landUseZone;
    }

    public String getLeaseHoldLand() {
        return leaseHoldLand;
    }

    public void setLeaseHoldLand(String leaseHoldLand) {
        this.leaseHoldLand = leaseHoldLand;
    }

    public BigDecimal getRoadWidth() {
        return roadWidth;
    }

    public void setRoadWidth(BigDecimal roadWidth) {
        this.roadWidth = roadWidth;
    }

    public String getTypeOfArea() {
        return typeOfArea;
    }

    public void setTypeOfArea(String typeOfArea) {
        this.typeOfArea = typeOfArea;
    }

    public BigDecimal getDepthOfPlot() {
        return depthOfPlot;
    }

    public void setDepthOfPlot(BigDecimal depthOfPlot) {
        this.depthOfPlot = depthOfPlot;
    }

    public BigDecimal getWidthOfPlot() {
        return widthOfPlot;
    }

    public void setWidthOfPlot(BigDecimal widthOfPlot) {
        this.widthOfPlot = widthOfPlot;
    }

    public String getBuildingNearMonument() {
        return buildingNearMonument;
    }

    public void setBuildingNearMonument(String buildingNearMonument) {
        this.buildingNearMonument = buildingNearMonument;
    }

    public String getBuildingNearGovtBuilding() {
        return buildingNearGovtBuilding;
    }

    public void setBuildingNearGovtBuilding(String buildingNearGovtBuilding) {
        this.buildingNearGovtBuilding = buildingNearGovtBuilding;
    }

    public String getNocNearMonument() {
        return nocNearMonument;
    }

    public void setNocNearMonument(String nocNearMonument) {
        this.nocNearMonument = nocNearMonument;
    }

    public String getNocNearAirport() {
        return nocNearAirport;
    }

    public void setNocNearAirport(String nocNearAirport) {
        this.nocNearAirport = nocNearAirport;
    }

    public String getNocNearDefenceAerodomes() {
        return nocNearDefenceAerodomes;
    }

    public void setNocNearDefenceAerodomes(String nocNearDefenceAerodomes) {
        this.nocNearDefenceAerodomes = nocNearDefenceAerodomes;
    }

    public String getNocStateEnvImpact() {
        return nocStateEnvImpact;
    }

    public void setNocStateEnvImpact(String nocStateEnvImpact) {
        this.nocStateEnvImpact = nocStateEnvImpact;
    }

    public String getNocRailways() {
        return nocRailways;
    }

    public void setNocRailways(String nocRailways) {
        this.nocRailways = nocRailways;
    }

    public String getNocCollectorGvtLand() {
        return nocCollectorGvtLand;
    }

    public void setNocCollectorGvtLand(String nocCollectorGvtLand) {
        this.nocCollectorGvtLand = nocCollectorGvtLand;
    }

    public String getNocIrrigationDept() {
        return nocIrrigationDept;
    }

    public void setNocIrrigationDept(String nocIrrigationDept) {
        this.nocIrrigationDept = nocIrrigationDept;
    }

    public String getNocFireDept() {
        return nocFireDept;
    }

    public void setNocFireDept(String nocFireDept) {
        this.nocFireDept = nocFireDept;
    }

    public BigDecimal getRoadLength() {
        return roadLength;
    }

    public void setRoadLength(BigDecimal roadLength) {
        this.roadLength = roadLength;
    }

    public String getBuildingNearToRiver() {
        return buildingNearToRiver;
    }

    public void setBuildingNearToRiver(String buildingNearToRiver) {
        this.buildingNearToRiver = buildingNearToRiver;
    }

    public String getBarrierFreeAccessForPhyChlngdPpl() {
        return barrierFreeAccessForPhyChlngdPpl;
    }

    public void setBarrierFreeAccessForPhyChlngdPpl(String barrierFreeAccessForPhyChlngdPpl) {
        this.barrierFreeAccessForPhyChlngdPpl = barrierFreeAccessForPhyChlngdPpl;
    }

    public String getPlotNo() {
        return plotNo;
    }

    public void setPlotNo(String plotNo) {
        this.plotNo = plotNo;
    }

    public String getKhataNo() {
        return khataNo;
    }

    public void setKhataNo(String khataNo) {
        this.khataNo = khataNo;
    }

    public String getMauza() {
        return mauza;
    }

    public void setMauza(String mauza) {
        this.mauza = mauza;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvisionsForGreenBuildingsAndSustainability() {
        return provisionsForGreenBuildingsAndSustainability;
    }

    public void setProvisionsForGreenBuildingsAndSustainability(String provisionsForGreenBuildingsAndSustainability) {
        this.provisionsForGreenBuildingsAndSustainability = provisionsForGreenBuildingsAndSustainability;
    }

    public String getFireProtectionAndFireSafetyRequirements() {
        return fireProtectionAndFireSafetyRequirements;
    }

    public void setFireProtectionAndFireSafetyRequirements(String fireProtectionAndFireSafetyRequirements) {
        this.fireProtectionAndFireSafetyRequirements = fireProtectionAndFireSafetyRequirements;
    }

    public String getRwhDeclared() {
        return rwhDeclared;
    }

    public void setRwhDeclared(String rwhDeclared) {
        this.rwhDeclared = rwhDeclared;
    }

	public String getNocPH7Dept() {
		return nocPH7Dept;
	}

	public void setNocPH7Dept(String nocPH7Dept) {
		this.nocPH7Dept = nocPH7Dept;
	}

	public String getNocTehsildarDept() {
		return nocTehsildarDept;
	}

	public void setNocTehsildarDept(String nocTehsildarDept) {
		this.nocTehsildarDept = nocTehsildarDept;
	}

	public String getNocPHDept() {
		return nocPHDept;
	}

	public void setNocPHDept(String nocPHDept) {
		this.nocPHDept = nocPHDept;
	}

	public String getNocManimajaraDept() {
		return nocManimajaraDept;
	}

	public void setNocManimajaraDept(String nocManimajaraDept) {
		this.nocManimajaraDept = nocManimajaraDept;
	}

	public String getNocRoad2Dept() {
		return nocRoad2Dept;
	}

	public void setNocRoad2Dept(String nocRoad2Dept) {
		this.nocRoad2Dept = nocRoad2Dept;
	}

	public String getNocPACDept() {
		return nocPACDept;
	}

	public void setNocPACDept(String nocPACDept) {
		this.nocPACDept = nocPACDept;
	}

	public String getNocStructureDept() {
		return nocStructureDept;
	}

	public void setNocStructureDept(String nocStructureDept) {
		this.nocStructureDept = nocStructureDept;
	}

	public String getNocElectricalDept() {
		return nocElectricalDept;
	}

	public void setNocElectricalDept(String nocElectricalDept) {
		this.nocElectricalDept = nocElectricalDept;
	}

	public String getNocPollutionDept() {
		return nocPollutionDept;
	}

	public void setNocPollutionDept(String nocPollutionDept) {
		this.nocPollutionDept = nocPollutionDept;
	}

}
