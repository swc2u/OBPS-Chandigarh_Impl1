/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2017>  eGovernments Foundation
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
package org.egov.bpa.utils;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BpaConstants {
	public static final String IN_PROGRESS = "IN PROGRESS";
	public static final String PLOT_NO = "PLOT_NUMBER";
	public static final String AREA_ZONE = "ZONE";
	public static final String ROOT_BOUNDARY_TYPE = "ROOT_BOUNDARY_TYPE";
	public static final String SECTOR_NUMBER = "SECTOR_NUMBER";
	public static final String PLOT_TYPE = "PLOT_TYPE";
	public static final String KHATA_NO = "KHATA_NO";

	// ROOT_BOUNDARY_TYPE values
	public static final String URBAN = "URBAN";
	public static final String RURAL = "RURAL";
	
	// PLOT_TYPE values
	public static final String MARLA = "MARLA"; // Till 379.35sqm
	public static final String ONE_KANAL = "ONE_KANAL"; // 379.35sqm to less than 505.85 sqm
	public static final String TWO_KANAL = "TWO_KANAL"; // 505.85sqm to less than 1011.7sqm
	public static final String ABOVE_TWO_KANAL = "ABOVE_TWO_KANAL"; // Above 1011.7sqm
	
	// CGCL start
	// occupancies code
	public static final String A = "A"; // Residential
	public static final String F = "F"; // Mercantile / Commercial
	public static final String G = "G"; // Industrial
	public static final String P = "P"; // Public / Semi- Public Buildings
	public static final String B = "B"; // Educational
	public static final String IT = "IT";// IT Park
	public static final String R = "R";// Railway Station, Chandigarh
	public static final String ITH = "ITH";// IT Habitat
	public static final String IP = "IP";// Integrated projects
	public static final String T = "T";// Transit Oriented Development (TOD)

	// sub occupancies code
	public static final String A_P = "A-P";// Plotted
	public static final String A_G = "A-G";// Grouped

	public static final String B_EC = "B_EC";// Education city (Sarangpur)
	public static final String B_HEI = "B-HEI";// Higher Educational Institute //Educational/ Academic
	public static final String B_H = "B-H";// Hostels

	public static final String F_SCO = "F-SCO";// SCO’S/ SCF’S / BAYSHOP’S/ SEMI INDUSTRIAL
	public static final String F_B ="F-B"; // BOOTHS ETC.
	public static final String F_H = "F-H";// Hotels
	public static final String F_M = "F-M";// MULTIPLEX/MALLS (specifically earmarked sites)
	public static final String F_CFI = "F-CFI";// COMMERCIAL (converted from Industrial)
	public static final String F_BH = "F-BH";// Banquet hall/ marriage palaces
	public static final String F_BBM = "F-BBM";// Bulk building material
	public static final String F_TS = "F-TS";// Timber site (single storey)
	public static final String F_TCIM = "F-TCIM";// Comercial_Theatre converted into multiplex
	public static final String F_PP = "F-PP"; // Petrol Pump
	public static final String F_CD = "F-CD"; // Coal Depot

	public static final String G_GBAC = "G-BACG-";// Governed by Architectural Controls
	public static final String G_GBZP = "G-GBZP";// Governed by Zoning Plans

	public static final String P_D = "P-D"; // Dispensary
	public static final String P_P = "P-P";// Police Station
	public static final String P_F = "P-F"; // Fire Station
	public static final String P_N = "P-N"; // Nursing Home
	public static final String P_H = "P-H";// Hospital
	public static final String P_CC = "P-CC";// Community centre/Janj Ghar
	public static final String P_SS = "P-SS";// Sports Stadium
	public static final String P_CNA = "P-CNA";// Cultural and Non Academic institutional sites
	public static final String P_R = "P-R";// Religious

	public static final String IT_MCL = "IT-MCL";// Main Campus (above 6 acre)
	public static final String IT_MCM = "IT-MCM";// Small Campus (2 to 6 acre)
	public static final String IT_MCS = "IT-MCS";// built to suite site (2 acre or below)

	public static final String R1="R1"; // Railway Station, Chandigarh

	public static final String ITH_H = "ITH_H";// Hospital
	public static final String ITH_C = "ITH-C";// COMMERCIAL / HOTEL
	public static final String ITH_CC = "ITH-CC";// Club
	public static final String ITH_R = "ITH-R";// RESIDENTIAL
	public static final String ITH_GH = "ITH-GH";// Government housing

	public static final String IP_I = "IP-I";// Institutional (70%)
	public static final String IP_R = "IP-R";// Residential (25%)
	public static final String IP_C = "IP-C";// Commercial (5%)

	public static final String T1 = "T1";// Transit Oriented Development (TOD)
	// CGCL end
	
    // service type constants
    public static final String ST_CODE_01 = "01";
    public static final String ST_CODE_02 = "02";
    public static final String ST_CODE_03 = "03";
    public static final String ST_CODE_04 = "04";
    public static final String ST_CODE_05 = "05";
    public static final String ST_CODE_06 = "06";
    public static final String ST_CODE_07 = "07";
    public static final String ST_CODE_08 = "08"; // Amenities
    public static final String ST_CODE_09 = "09"; // Permission for Temporary hut or shed
    public static final String ST_CODE_10 = "10";
    public static final String ST_CODE_11 = "11";
    public static final String ST_CODE_12 = "12";
    public static final String ST_CODE_13 = "13";
    public static final String ST_CODE_14 = "14";
    public static final String ST_CODE_15 = "15"; // Pole Structures
    public static final String AMENITIES = "Amenities";
    public static final String PERM_FOR_HUT_OR_SHED = "Huts and Sheds";
    public static final String REGULARIZATION = "Regularization";
    public static final String CHANGE_IN_OCCUPANCY = "Change in Occupancy";
    public static final String ADDING_OF_EXTENSION = "Addition or Extension";
    public static final String ALTERATION = "Alteration";
    public static final String DIVISION_OF_PLOT = "Sub-Division of plot/Land Development";
    public static final String RECONSTRUCTION = "Reconstruction";
    public static final String DEMOLITION = "Demolition";
    public static final String NEW_CONSTRUCTION = "New Construction";
    public static final String TOWER_CONSTRUCTION = "Tower Construction";
    public static final String POLE_STRUCTURES = "Pole Structures";
    public static final String WELL = "Well";
    public static final String COMPOUND_WALL = "Compound Wall";
    public static final String SHUTTER_DOOR_CONVERSION = "Shutter or Door Conversion/Erection under rule 100 or 101";
    public static final String ROOF_CONVERSION = "Roof Conversion under rule 100 or 101";

    // user type constants
    public static final String ROLE_CITIZEN = "CITIZEN";
    public static final String USERNAME_ANONYMOUS = "anonymous";
    public static final String ROLE_BUSINESS_USER = "BUSINESS";
    // common constants
    public static final String BPA_APPLICATION = "bpaApplication";
    public static final String APPLICATION_HISTORY = "applicationHistory";
    public static final String EGMODULE_NAME = "BPA";
    public static final String NOCMODULE = "NOC";
    public static final String BPASTATUSMODULETYPE = "BPAAPPLICATION";
    public static final String YEARLY = "Yearly";
    // sequence name constants
    public static final String BPA_APPNO_SEQ = "SEQ_BPA_APPNO_";
    public static final String BPA_BILLNO_SEQ = "SEQ_BILLNO_";
    public static final String BPA_PLANPERMNO_SEQ = "SEQ_BPA_PLANPERMNO_";
    // demand related constants
    public static final Character DMD_STATUS_CHEQUE_BOUNCED = 'B';
    public static final String DEMANDISHISTORY = "N";
    public static final String STRING_VALIDATION = "Paid Amount is greater than Total Amount to be paid";
    // boundary related constants
    public static final String LOCALITY = "locality";
    public static final String ELECTIONWARD_BNDRY_TYPE = "Election Ward";
    public static final String LOCALITY_BNDRY_TYPE = "Locality";
    public static final String LOCATION_HIERARCHY_TYPE = "LOCATION";
    public static final String REVENUE_HIERARCHY_TYPE = "REVENUE";
    public static final String ADMINISTRATION_HIERARCHY_TYPE = "ADMINISTRATION";
    public static final String ZONE = "Zone";
    public static final String WARD = "Ward";
    public static final String BLOCK = "Block";
    public static final String STREET = "Street";
    public static final String BOUNDARY_TYPE_ZONE = "Zone";
    public static final String BOUNDARY_TYPE_CITY = "City";
    // workflow related constants
    public static final String ADDITIONALRULE = "additionalRule";
    public static final String APPLICATION_MODULE_TYPE = "BPA";
    public static final String BPASTATUS_MODULETYPE = "REGISTRATION";
    public static final String COLON_CONCATE = "::";
    public static final String NATURE_OF_WORK = "Building Plan Approval";
    public static final String NATURE_OF_WORK_OC = "Building Plan Approval::Occupancy Certificate";
    public static final String NATURE_OF_WORK_INSPECTION = "Inspection Application";
    public static final String WF_NEW_STATE = "NEW";
    public static final String WF_CREATED_STATE = "Created";
    public static final String WF_REJECT_STATE = "Rejected";
    public static final String WF_END_STATE = "END";
    public static final String CREATE_ADDITIONAL_RULE_CREATE = "CREATEBPAAPPLICATION";
    public static final String CREATE_ADDITIONAL_RULE_CREATE_ONEDAYPERMIT = "CREATEBPAAPPLICATION-ONEDAYPERMIT";
    public static final String FIELD_INSPECTION_COMPLETED = "Field Inspection completed";
    public static final String WF_TS_INSPECTION_INITIATED = "Town Surveyor Inspection Initiated";
    public static final String WF_AE_APPROVAL_PENDING = "AE Approval Pending";
    public static final String WF_TS_APPROVAL_PENDING = "Town Surveyor Approval Pending";
    public static final String FWD_TO_OVRSR_FOR_FIELD_INS = "Forwarded to Overseer for field inspection";
    public static final String FORWARDED_TO_NOC_UPDATE = "Forwarded to Superintendent for Noc Updation";
    public static final String FWD_TO_AE_FOR_FIELD_ISPECTION = "Forwarded to Assistant Engineer for field ispection";
    public static final String WF_PERMIT_FEE_COLL_PENDING = "Permit Fee Collection Pending";
    public static final String WF_DOC_SCRTNY_RE_SCHDLE_PENDING = "Document Scrutiny ReScheduling Pending";
    public static final String FWD_TO_AE_AFTER_TS_INSP = "Forwarded to Assistant Engineer";
    public static final String FWD_TO_OVERSEER_AFTER_TS_INSPN = "Forwarded to Overseer";
    public static final String REJECTION_INITIATED = "Rejection Initiated";
    public static final String WF_AUTO_RESCHEDULE_PENDING = "Pending For Auto Rescheduling Document Scrutiny";
    public static final String FWD_TO_AE_FOR_APPROVAL = "Forwarded to Assistant Engineer For Approval";
    public static final String FWD_TO_CLERK_PENDING = "Forward to section clerk is pending";
    public static final String FORWARDED_TO_CLERK = "Forwarded to section clerk";
    public static final String WF_DOC_SCRUTINY_SCHEDLE_PEND = "Document Scrutiny Scheduling Pending";
    public static final String WF_DOC_VERIFY_PEND = "Document verification pending";
    public static final String WF_INIT_AUTO_RESCHDLE = "Initiated For Auto Rescheduling Appointment";
    public static final String INSPECTION_MODULE_TYPE = "INSPECTION";
    public static final String WF_REVOKE_STATE ="Revoke";
    public static final String WF_ASST_ENG_APPROVED = "Assistant Engineer approved";
    public static final String WF_OWNERSHIP_FEE_PENDING = "Ownership transfer fee payment pending";
    public static final String OWNERSHIP_FEE_COLLECTED = "Ownership transfer fee payment done";
    
    public static final String WF_BA_VARIFICATION_INITIATED = "Forwarded to property documents verification";
    public static final String WF_BA_CHECK_NOC_UPDATION="Forwarded to check NOC updation";
    public static final String WF_BA_AE_APPROVAL="Forwarded to E- Assistant Estate Officer for Approval";
    public static final String WF_BA_INITIATE_GENERATE_PERMIT_ORDER="Initiated process for generate permit Order";
    public static final String WF_BA_SDO_APPROVAL="Forwarded to SDO Building for Approval";
    
    public static final String WF_BA_NOC_UPDATION_IN_PROGRESS="NOC updation in progress";
    public static final String WF_BA_FINAL_APPROVAL_PROCESS_INITIATED="Final Approval Process initiated";
    public static final String WF_BA_AEE_APPLICATION_APPROVAL_PENDING="AEE Application Approval Pending";
    public static final String WF_BA_FORWARD_TO_SDO_BUILDING="Forward to SDO Building";

    // application status constants
    public static final String APPROVED = "Approved";
    public static final String APPLICATION_STATUS_APPROVED = APPROVED;
    public static final String PAYMENT_PENDING = "PAYMENT_PENDING";
    public static final String APPLICATION_STATUS_PENDNING = PAYMENT_PENDING;
    public static final String APPLICATION_STATUS_DOC_VERIFY_COMPLETED = "Document Verification Completed";
    public static final String APPLICATION_STATUS_APPROVAL_PROCESS_INITIATED = "Approval Process Initiated";
    public static final String APPLICATION_STATUS_ORDER_ISSUED = "Order Issued to Applicant";
    public static final String APPLICATION_STATUS_DIGI_SIGNED = "Digitally signed";
    public static final String APPLICATION_STATUS_RECORD_APPROVED = "Record Approved";
    public static final String APPLICATION_STATUS_NOCUPDATED = "NOC Updated";
    public static final String APPLICATION_STATUS_CANCELLED = "Cancelled";
    public static final String APPLICATION_STATUS_REGISTERED = "Registered";
    public static final String APPLICATION_STATUS_REJECTED = "Rejected";
    public static final String APPLICATION_STATUS_DOC_VERIFIED = "Document Verified";
    public static final String APPLICATION_STATUS_SECTION_CLRK_APPROVED = "Section clerk approved";
    public static final String APPLICATION_STATUS_TS_INS = "Town Surveyor Inspected";
    public static final String APPLICATION_STATUS_SCHEDULED = "Scheduled For Document Scrutiny";
    public static final String APPLICATION_STATUS_RESCHEDULED = "Rescheduled For Document Scrutiny";
    public static final String APPLICATION_STATUS_PENDING_FOR_RESCHEDULING = "Pending For Rescheduling For Document Scrutiny";
    public static final String APPLICATION_STATUS_TS_INS_INITIATED = "Town Surveyor Inspection Initiated";
    public static final String APPLICATION_STATUS_REJECT_CLERK = "Rejection initiated by clerk";
    public static final String APPLICATION_APPROVAL_PENDING = "Secretary Application Approval Pending";
    public static final String RENEWAL_MODULE_TYPE = "RENEWAL";
    public static final String WF_INIT_OWNERSHIP = "Initiated for ownership transfer";
    public static final String APPLICATION_ACTION_VERIFY_RR = "Verify Rejection Reasons";
    
    // Checklist document type constants
    public static final String STAKE_HOLDER_CHECK_LIST_TYPE = "STAKEHOLDERDOCUMENT";
    public static final String CHECKLIST_TYPE_NOC = "NOC";
    public static final String FILESTORE_MODULECODE = "BPA";
    public static final String CHECKLIST_TYPE = "DOCUMENTATION";
    public static final String LP_CHECKLIST = "LTP";
    public static final String DCR_CHECKLIST = "BPADCRDOCUMENTS";
    
    public static final String PERMIT_DEFAULT_CONDITIONS = "PERMITDEFAULTCONDITIONS";
    // Sms And Email config constants
    public static final String SENDSMSFORBPA = "SENDSMSFROOMBPAMODULE";
    public static final String SENDEMAILFORBPA = "SENDEMAILFROOMBPAMODULE";
    // Sms And Email Code For New Connection
    public static final String SMSEMAILTYPENEWBPAREGISTERED = "newbparegistered";
    public static final String APPLICATION_STATUS_CREATED = "Created";
    public static final String APPLICATION_STATUS_SUBMITTED = "Submitted";
    public static final String SMSEMAILTYPELETTERTOPARTY = "Letter To Party";
    // Letter to party related constants
    public static final String LETTERTOPARTY_NUMBER_SEQ = "SEQ_BPA_LP_";
    public static final String LETTERTOPARTY_ACK_NUMBER_SEQ = "SEQ_BPA_LP_ACK_";
    public static final String CREATEDLETTERTOPARTY = "Letter To Party Created";
    public static final String LETTERTOPARTYSENT = "LP Sent to Applicant";
    public static final String LETTERTOPARTYINITIATED = "LP Initiated";
    public static final String LPREPLYRECEIVED = "Letter To Party Reply Received";
    public static final String FWDINGTOLPINITIATORPENDING = "Forward to LP Initiator pending";
    public static final String LPCREATED = "LP Created";
    public static final String LPREPLIED = "LP Reply Received";
    public static final String LETTERTOPARTYINITIATE = "LP Initiate";
    public static final String LETTERTOPARTYDETAILS = "lettertoparty";
    public static final String LETTERTOPARTY_REPLY_RECEIVED = "Letter To Party Reply Received";
    // inspection related constants
    public static final String WFINSPECTIONAPPLICATION = "InspectionApplication";
    public static final String INSPECTION_NUMBER_SEQ = "SEQ_BPA_INSPECTIONNUMBER";
    public static final String INSPECTIONLOCATION = "INSPECTIONLOCATION";
    public static final String INSPECTIONMEASUREMENT = "INSPECTIONMEASUREMENT";
    public static final String INSPECTIONACCESS = "INSPECTIONACCESS";
    public static final String INSPECTIONSURROUNDING = "INSPECTIONSURROUNDING";
    public static final String INSPECTIONTYPEOFLAND = "INSPECTIONTYPEOFLAND";
    public static final String INSPECTIONPROPOSEDSTAGEWORK = "INSPECTIONPROPOSEDSTAGEWORK";
    public static final String INSPECTIONWORKCOMPLETEDPERPLAN = "INSPECTIONWORKCOMPLETEDPERPLAN";
    public static final String INSPECTIONAREALOC = "INSPECTIONAREALOC";
    public static final String INSPECTIONLENGTHOFCOMPOUNDWALL = "INSPECTIONLENGTHOFCOMPOUNDWALL";
    public static final String INSPECTIONNUMBEROFWELLS = "INSPECTIONNUMBEROFWELLS";
    public static final String INSPECTIONERECTIONOFTOWER = "INSPECTIONERECTIONOFTOWER";
    public static final String INSPECTIONSHUTTER = "INSPECTIONSHUTTER";
    public static final String INSPECTIONROOFCONVERSION = "INSPECTIONROOFCONVERSION";
    public static final String INSPECTIONHGTBUILDABUTROAD = "INSPECTIONHGTBUILDABUTROAD";
    public static final String INITIATEINSPECTION = "Initiated Inspection";
    public static final String INSPECTIONAPPLICATION = "INSPECTIONAPPLICATION";


    // fee and receipt related constants
    public static final String BPAFEECOLLECT = "BPA Application fees collected";
    public static final String APPLICATIONFEEREASON = "APPLICATIONFEES";
    public static final String BPAFEETYPE = "ApplicationFee";
    public static final String BPAREGISTRATIONFEETYPE = "registrationfee";
    public static final String BPASTATUS_APPLICATIONFEE_MODULE = "APPLICATIONFEE";
    public static final String BPASTATUS_APPLICATIONFEE_APPROVED = APPROVED;
    public static final String FEETYPE_SANCTIONFEE = "SanctionFee";
    public static final String OCFEETYPE_SANCTIONFEE = "OCSanctionFee";
    public static final String AUTOCALCULATEFEEBYINSPECTION = "BPA_AUTOCALCULATE_FEE";
    public static final String BPASTATUS_MODULETYPE_REGISTRATIONFEE = "APPLICATIONFEE";
    public static final String BPASTATUS_REGISTRATIONFEE_APPROVED = APPROVED;
    public static final String BPA_ADM_FEE = "Application Fee";
    public static final String BPA_APP_FEE = "Application Fees";
    public static final String BPA_COMPOUND_FEE = "Charges for Compound Wall";
    public static final String STATE_LOGO_PATH = "/egi/resources/global/images/logo@2x.png";
    public static final String IMAGES_BASE_PATH = "/egi/resources/global/images/";
    public static final String IMAGE_CONTEXT_PATH = "/egi";
    public static final String BPADEMANDNOTICETITLE = "Demand Notice";
    public static final String DEMANDNOCFILENAME = "bpaDemandNotice";
    public static final String STRING_BPA_FUCNTION_CODE = "5100";
    public static final String ROLE_BILLCOLLECTOR = "Collection Operator";
    public static final String BPA_DEPARTMENT_CODE = "BPA_DEPARTMENT_CODE";
    public static final String BPA_DEFAULT_FUND_CODE = "BPA_DEFAULT_FUND_CODE";
    public static final String BPA_DEFAULT_FUNCTIONARY_CODE = "BPA_DEFAULT_FUNCTIONARY_CODE";
    public static final String BPA_DEFAULT_FUND_SRC_CODE = "BPA_DEFAULT_FUND_SRC_CODE";
    public static final String BPA_PERMIT_FEE_COLLECTED = "BPA Permit fees collected";
    public static final String PERMIT_APPLN_FEE_COLLECTION_REQUIRED = "PERMITAPPLNFEECOLLECTIONREQUIRED";
    public static final String BPA_REGISTRATION_FEE = "Registration Fees";
    public static final String OC_FEE = "Occupancy Certificate Fees";
    
    public static final String SECURITY_FEE = "Security fee";
    public static final String LABOURCESS = "Labour cess";
    public static final String SCRUTINY_FEE = "Scrutiny fee";
    public static final String GST_18 = "GST (18% of scrutiny fee)";
    public static final String ADDITIONAL_COVERAGE_FEE = "Additional Coverage fee";
    public static final String RULE_5_FEE = "Rule 5 fee";
    
    public static final String TP_DEPT = "TOWN PLANNING DEPARTMENT";
    public static final String DEV_PERMIT_FEE = "DPF";
    public static final String OWNERSHIP_FEE = "Ownership Transfer Fees";
    
    //NOC integration related
    public static final String FIRENOCTYPE = "FIRE NOC"; 
    public static final String PH7NOCTYPE = "PUB HEALTH 7 NOC"; 
    public static final String TEHNOCTYPE = "TEHSILDAR NOC"; 
    public static final String PHNOCTYPE = "PUB HEALTH NOC"; 
    public static final String MANINOCTYPE = "MANIMAJARA NOC";    
    public static final String ROAD2NOCTYPE = "ROAD 2 NOC";
    public static final String PACNOCTYPE = "PAC NOC";
    public static final String STRCNOCTYPE = "STRUCTURE NOC";
    public static final String ELECNOCTYPE = "ELECTRICAL NOC";
    public static final String POLNOCTYPE = "POL CONTROL NOC";    
    
    public static final String NOC_INITIATED = "Initiated";
    public static final String NOC_APPROVED = "Approved";
    public static final String NOC_REJECTED = "Rejected";
    public static final String NOC_DEEMED_APPROVED = "Deemed Approved";
    public static final String NOC_APPL_REJECTED= "Permit Application Rejected";
    public static final String PERMIT = "Permit";
    public static final String OC = "OC";
    
    public static final String FIREOCNOCTYPE = "FIRE OCNOC";
    public static final String AIRPORTOCNOCTYPE = "AAI OCNOC";
    public static final String NMAOCNOCTYPE = "NMA OCNOC";
    public static final String ENVOCNOCTYPE = "MOEF OCNOC";
    public static final String IRROCNOCTYPE = "IDA OCNOC";
    
    //NOC approver roles
    public static final String FIRENOCROLE = "BPA_FIRE_NOC_ROLE";
    public static final String PHNOCROLE = "BPA_PUB_HEALTH_NOC_ROLE";
    public static final String TEHNOCROLE = "BPA_TEHSILDAR_NOC_ROLE";
    public static final String PH7NOCROLE = "BPA_PUB_HEALTH_7_NOC_ROLE";
    public static final String ROAD2NOCROLE = "BPA_ROAD_2_NOC_ROLE";
    public static final String PACNOCROLE = "BPA_PAC_NOC_ROLE";
    public static final String STRCNOCROLE = "BPA_STRUCTURE_NOC_ROLE";
    public static final String ELECNOCROLE = "BPA_ELECTRICAL_NOC_ROLE";
    public static final String POLNOCROLE = "BPA_POL_CONTROL_NOC_ROLE";
    public static final String MANINOCROLE = "BPA_MANIMAJARA_NOC_ROLE";


    // designation constants
    public static final String DESIGNATION_AEE = "Assistant executive engineer";
    public static final String DESIGNATION_AE = "Assistant engineer";
    public static final String DESIGNATION_EE = "Executive engineer";
    public static final String DESIGNATION_SECRETARY = "Secretary";
    public static final String DESIGNATION_TOWN_SURVEYOR = "Town Surveyor";
    public static final String DESIGNATION_OVERSEER = "Town Planning Building Overseer";
    // occupancy type constants

    public static final String BPA_THATCHED_TILED_HOUSE = "Thatched / Tiled House";
    public static final String BPA_MERCANTILE_COMMERCIAL = "Mercantile / Commercial";
    public static final String BPA_INDUSTRIAL = "Industrial";
    public static final String BPA_RESIDENTIAL = "Residential";
    public static final String BPA_MIXED_OCCUPANCY = "Mixed";

    // Occupancy Constants
    public static final String MIXED_OCCUPANCY = "15";
    public static final String INDUSTRIAL = "G1";
    public static final String MERCANTILE_COMMERCIAL = "F";
    public static final String THATCHED_TILED_HOUSE = "14";
    public static final String RESIDENTIAL = "A";
    public static final String RESIDENTIAL_A1 = "A1";
    public static final String SPECIAL_RESIDENTIAL = "A2";
    public static final String HOSTEL_EDUCATIONAL = "A3";
    public static final String APARTMENT_FLAT = "A4";
    public static final String PROFESSIONAL_OFFICE = "A5";
    public static final String EDUCATIONAL = "B";
    public static final String EDUCATIONAL_B1 = "B1";
    public static final String EDUCATIONAL_HIGHSCHOOL = "B2";
    public static final String HIGHER_EDUCATIONAL_INSTITUTE = "B3";
    public static final String MEDIACL_HOSPITAL = "C";
    public static final String MEDIACL_IP = "C1";
    public static final String MEDIACL_OP = "C2";
    public static final String MEDICAL_ADMIN = "C3";
    public static final String ASSEMBLY = "D";
    public static final String ASSEMBLY_WORSHIP = "D1";
    public static final String BUS_TERMINAL = "D2";
    public static final String OFFICE_BUSINESS = "E";
    public static final String STORAGE = "H";

    // notices file name and type
    public static final String BUILDINGPERMITFILENAME = "buildingpermit";
    public static final String BUILDINGPERMITOTHERSFILENAME = "buildingpermitothers";
    public static final String BUILDINGDEVELOPPERMITFILENAME = "buildingdeveloppermit";
    public static final String BPAREJECTIONFILENAME = "bparejectionnotice";
    public static final String BPAREVOCATIONFILENAME = "bparevocationnotice";
    public static final String PERMIT_ORDER_NOTICE_TYPE = "PermitOrder";
    public static final String BPA_DEMAND_NOTICE_TYPE = "DemandNotice";
    public static final String BPA_REJECTION_NOTICE_TYPE = "RejectionNotice";
    public static final String BPA_REVOCATION_NOTICE_TYPE = "RevocationNotice";
    public static final String OCREJECTIONFILENAME = "ocrejectionnotice";
    public static final String OCDEMANDFILENAME = "ocdemandnotice";
    public static final String REGULARIZATIONFIILENAME = "regularization_permit";
    public static final String REGULARIZATIONOTHERSFIILENAME = "regularization_others_permit";
    public static final String REGULARIZATIONBUILDINGDEVELOPMENTFIILENAME = "regularization_buildingdevelopment_permit";
    public static final String RENEWAL_ORDER_NOTICE_TYPE = "RenewalOrder";
    public static final String OWNERSHIP_ORDER_NOTICE_TYPE = "OwnershipTransferOrder";


    // button name constants
    public static final String WF_APPROVE_BUTTON = "Approve";
    public static final String WF_REJECT_BUTTON = "REJECT";
    public static final String WF_REVOCATE_BUTTON = "Revocate";
    public static final String WF_CANCELAPPLICATION_BUTTON = "Cancel Application";
    public static final String GENERATEPERMITORDER = "Generate Permit Order";
    public static final String GENERATEREJECTNOTICE = "Generate Rejection Notice";
    public static final String GENERATEREVOCATIONNOTICE = "Revoke Permit";
    public static final String WF_LBE_SUBMIT_BUTTON = "Submit";
    public static final String WF_PAY_ONLINE_BUTTON = "Pay Online";
    public static final String WF_REVERT_BUTTON = "Revert";
    public static final String WF_SAVE_BUTTON = "Save";
    public static final String WF_SEND_BUTTON = "Send";
    public static final String WF_RESCHDLE_APPMNT_BUTTON = "Reschedule Appointment";
    public static final String WF_INITIATE_REJECTION_BUTTON = "Initiate Rejection";
    public static final String WF_INITIATE_REVOCATION_BUTTON = "Initiate Revocation";
    public static final String WF_AUTO_RESCHDLE_APPMNT_BUTTON = "Auto ReSchedule";
    public static final String GENERATE_OCCUPANCY_CERTIFICATE = "Generate Occupancy Certificate";
    public static final String WF_GENERATE_RENEWAL_ORDER = "Generate Permit Renewal Order";
    public static final String WF_GENERATE_OWNERSHIP_ORDER = "Generate Ownership Transfer Order";
    public static final String WF_FORWARD_BUTTON = "Forward";
    // building details related constants
    public static final String TOTAL_PLINT_AREA = "totalPlintArea";
    public static final String BUILDINGHEIGHT_GROUND = "buildingheightGround";
    public static final String FLOOR_COUNT = "floorCount";
    public static final String EXTENTINSQMTS = "extentinsqmts";
    // portal related constants
    public static final String ENABLEONLINEPAYMENT = "BPA_ONLINE_PAY";
    public static final String ENABLESTACKEHOLDERREGFEE = "BUILDING_LICENSEE_REG_FEE_REQUIRED";
    public static final String BPA_CITIZENACCEPTANCE_CHECK = "BPA_CITIZENACCEPTANCE_CHECK";
    public static final String DISCLIMER_MESSAGE_ONSAVE = "\n   Acceptance of building permit application in the system and DCR checking process does not confer a claim for building permit approval.";
    public static final String DISCLIMER_MESSAGE_ONEDAYPERMIT_ONSAVE = "\n   Acceptance of one day building permit application in the system and DCR checking process does not confer a claim for building permit approval.\n\n";
    // configuration value constants
    public static final String YES = "YES";
    public static final String NO = "NO";
    public static final String MANUAL = "MANUAL";
    public static final String AUTOFEECAL = "AUTOFEECAL";
    public static final String AUTOFEECALEDIT = "AUTOFEECAL_EDIT";
    // App config key name constants
    public static final String DCR_DOC_AUTO_POPULATE_UPLOAD = "DCR_DOC_AUTO_POPULATE_UPLOAD";
    public static final String DCR_DOC_MANUAL_UPLOAD = "DCR_DOC_MANUAL_UPLOAD";
    public static final String DCR_DOC_AUTO_POPULATE_AND_MANUAL_UPLOAD = "DCR_DOC_AUTO_POPULATE_AND_MANUAL_UPLOAD";
    public static final String DOC_SCRUTINY_INTEGRATION_REQUIRED = "DOCUMENT_SCRUTINY_INTEGRATION_REQUIRED";
    public static final String ONE_DAY_PERMIT_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED = "ONE_DAY_PERMIT_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED";
    public static final String REGULAR_PERMIT_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED = "REGULAR_PERMIT_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED";
    public static final String ONE_DAY_PERMIT_APPLN_INTEGRATION_REQUIRED = "ONE_DAY_PERMIT_APPLN_INTEGRATION_REQUIRED";
    public static final String DCR_SCRUTINIZED_PLANEXPIRYDAYS = "DCR_SCRUTINIZED_PLAN_EXPIRY_DAYS";
    public static final String OCFEECALULATION = "OC_FEE_CALCULATION";
    public static final String IS_AUTO_CANCEL_UNATTENDED_DOCUMENT_SCRUTINY_APPLICATION = "IS_AUTO_CANCEL_UNATTENDED_DOCUMENT_SCRUTINY_APPLICATION";
    public static final String DCR_BPA_INTEGRATION_REQUIRE = "DCR_INTEGRATION_REQUIRE_WITH_BPA";
    public static final String AUTO_CANCEL_UNATTENDED_DOCUMENT_SCRUTINY_OC = "AUTO_CANCEL_UNATTENDED_DOCUMENT_SCRUTINY_OC";
    // other
    public static final int SCALING_FACTOR = 2;
    public static final String MESSAGE = "message";
    public static final String STATELOGO = "STATE_LOGO";

    // elastic search constants
    public static final String APPL_INDEX_MODULE_NAME = "Building Plan Approval";
    // Application Type Constants
    public static final String APPLICATION_TYPE_REGULAR = "Regular";
    public static final String APPLICATION_TYPE_ONEDAYPERMIT = "One Day Permit";
    public static final String APPLICATION_TYPE_LOWRISK = "Low Risk";
    public static final String APPLICATION_TYPE_MEDIUMRISK = "Medium Risk";
    public static final String APPLICATION_TYPE_HIGHRISK = "High Risk";

    public static final String SLOT_TYPE_NORMAL = "Normal";
    public static final String SLOT_TYPE_ONE_DAY_PERMIT = "One Day Permit";
    public static final String AUTH_TO_SUBMIT_PLAN = "This application requires signed plan documents for document scrutiny";
    public static final String SHTR_DOOR_FEE = "Charges for Shutter or Door Conversion";
    public static final String ROOF_CNVRSN_FEE = "Charges for Roof Conversion";
    public static final String TOWER_CONSTRUCTION_FEE = "Charges for Tower Construction";
    public static final String POLE_CONSTRUCTION_FEE = "Charges for Pole Structures";
    public static final String SANCTION_FEE = "Sanction Fee";

    // Occupancy certificate related
    public static final String CREATE_ADDITIONAL_RULE_CREATE_OC = "OCCUPANCYCERTIFICATE";
    public static final String BPA_OC_NUMBER_SEQ = "SEQ_BPA_OC_NO_";

    // Occupancy certificate notice types
    public static final String OCCUPANCY_CERTIFICATE_NOTICE_TYPE = "Occupancy Certificate";
    public static final String NATURE_OF_WORK_STAKEHOLDER = "Stake Holder Registration";
    public static final String PERMIT_APPLICATION_NOTICE_TYPE = "Permit Application";

    public static final String BPAFEECALULATION = "BPA_FEE_CALCULATION";

    public static final String BPA_MODULE_NAME = "BPA";
    public static final String GENERIC_BOUNDARY_CONFIGURATION_KEY = "BPA_GENERIC_BOUNDARY_CONFIGURATION";
    public static final String WORKFLOW_EMPLOYEE_BOUNDARY_HIERARCHY = "BPA_WORKFLOW_EMPLOYEE_BOUNDARY_HIERARCHY";

    public static final String LOWRISK = "Low Risk";
    public static final String HIGHRISK = "High Risk";
    public static final String MEDIUMRISK = "Medium Risk";
    public static final String REGISTRATION_FEES_CODE = "RF";
    
    public static final String BPA_REVOKENO_SEQ="SEQ_BPA_REVOKENO_";
    public static final String APPLICATION_STATUS_INIT_REVOKE = "Revocation initiated";
    public static final String APPLICATION_STATUS_REVOKED ="Revocated";
    public static final String APPLICATION_STATUS_REVOKE_CANCELED ="Revocation cancelled";
    
    //Permit Renewal related
    public static final String PERMIT_RENEW_WFTYPE = "PermitRenewal";
    public static final String RENEWALSTATUS_MODULETYPE = "RENEWAL";
    public static final String NATURE_OF_WORK_RENEWAL = "Permit Renewal";
    
    //Ownership Transfer
    public static final String OWNERSHIP_TRANSFER_WFTYPE = "OwnershipTransfer";
    public static final String OWNERSHIPSTATUS_MODULETYPE = "OWNERSHIP";
    public static final String NATURE_OF_WORK_OWNERSHIP = "Ownership Transfer";

    
    private static final List<String> BUILDPERMIT = new ArrayList<>();
    static {
        BUILDPERMIT.add(ST_CODE_01); // New Construction
        BUILDPERMIT.add(ST_CODE_03); // Reconstruction
        BUILDPERMIT.add(ST_CODE_04); // Alteration
        BUILDPERMIT.add(ST_CODE_06); // Adding of Extension
        BUILDPERMIT.add(ST_CODE_07); // Change in Occupancy
    }

    private static final List<String> BUILDING_PERMIT_OTHERS = new ArrayList<>();
    static {
        BUILDING_PERMIT_OTHERS.add(ST_CODE_02); // Demolition
        BUILDING_PERMIT_OTHERS.add(ST_CODE_08);
        BUILDING_PERMIT_OTHERS.add(ST_CODE_09);
        BUILDING_PERMIT_OTHERS.add(ST_CODE_10);
        BUILDING_PERMIT_OTHERS.add(ST_CODE_11);
        BUILDING_PERMIT_OTHERS.add(ST_CODE_12);
        BUILDING_PERMIT_OTHERS.add(ST_CODE_13);
        BUILDING_PERMIT_OTHERS.add(ST_CODE_14);
        BUILDING_PERMIT_OTHERS.add(ST_CODE_15);
    };

    private static final List<String> DEVELOPPERMIT = new ArrayList<>();
    static {
        DEVELOPPERMIT.add(ST_CODE_05); // Sub-Division of plot/Land Development
    }

    private static final List<String> FLOORLIST = new ArrayList<>();
    static {
        FLOORLIST.add("Cellar Floor");
        FLOORLIST.add("Ground Floor");
        FLOORLIST.add("Upper Floor");
        FLOORLIST.add("Mezzanine Floor");
        FLOORLIST.add("Terrace Floor");
    }

    private static final Map<String, Map<String, BigDecimal>> STAKEHOLDERTYPE1RESTRICTIONS = new ConcurrentHashMap<>();
    static {
        Map<String, BigDecimal> stakeHolderType1Restrictions = new ConcurrentHashMap<>();
        stakeHolderType1Restrictions.put(EXTENTINSQMTS, BigDecimal.valueOf(10000));
        STAKEHOLDERTYPE1RESTRICTIONS.put("architect", stakeHolderType1Restrictions);
        STAKEHOLDERTYPE1RESTRICTIONS.put("chartered architect", stakeHolderType1Restrictions);
        STAKEHOLDERTYPE1RESTRICTIONS.put("building designer - a", stakeHolderType1Restrictions);
        STAKEHOLDERTYPE1RESTRICTIONS.put("engineer - a", stakeHolderType1Restrictions);
        STAKEHOLDERTYPE1RESTRICTIONS.put("chartered engineer", stakeHolderType1Restrictions);
    }

    private static final Map<String, Map<String, BigDecimal>> STAKEHOLDERTYPE2RESTRICTIONS = new ConcurrentHashMap<>();
    static {
        Map<String, BigDecimal> stakeHolderType2Restrictions = new ConcurrentHashMap<>();
        stakeHolderType2Restrictions.put(EXTENTINSQMTS, BigDecimal.valueOf(5000));
        stakeHolderType2Restrictions.put(TOTAL_PLINT_AREA, BigDecimal.valueOf(1000));
        stakeHolderType2Restrictions.put(FLOOR_COUNT, BigDecimal.valueOf(4));
        stakeHolderType2Restrictions.put(BUILDINGHEIGHT_GROUND, BigDecimal.valueOf(14.5));
        STAKEHOLDERTYPE2RESTRICTIONS.put("engineer - b", stakeHolderType2Restrictions);
    }

    private static final Map<String, Map<String, BigDecimal>> STAKEHOLDERTYPE3RESTRICTIONS = new ConcurrentHashMap<>();
    static {
        Map<String, BigDecimal> stakeHolderType3Restrictions = new ConcurrentHashMap<>();
        stakeHolderType3Restrictions.put(EXTENTINSQMTS, BigDecimal.valueOf(10000));
        stakeHolderType3Restrictions.put(TOTAL_PLINT_AREA, BigDecimal.valueOf(750));
        stakeHolderType3Restrictions.put(FLOOR_COUNT, BigDecimal.valueOf(3));
        stakeHolderType3Restrictions.put(BUILDINGHEIGHT_GROUND, BigDecimal.valueOf(11));
        STAKEHOLDERTYPE3RESTRICTIONS.put("supervisor - a", stakeHolderType3Restrictions);
    }

    private static final Map<String, Map<String, BigDecimal>> STAKEHOLDERTYPE4RESTRICTIONS = new ConcurrentHashMap<>();
    static {
        Map<String, BigDecimal> stakeHolderType4Restrictions = new ConcurrentHashMap<>();
        stakeHolderType4Restrictions.put(EXTENTINSQMTS, BigDecimal.valueOf(10000));
        stakeHolderType4Restrictions.put(TOTAL_PLINT_AREA, BigDecimal.valueOf(300));
        stakeHolderType4Restrictions.put(FLOOR_COUNT, BigDecimal.valueOf(2));
        stakeHolderType4Restrictions.put(BUILDINGHEIGHT_GROUND, BigDecimal.valueOf(7.5));
        STAKEHOLDERTYPE4RESTRICTIONS.put("supervisor - b", stakeHolderType4Restrictions);
    }

    private static final Map<String, Map<String, BigDecimal>> STAKEHOLDERTYPE5RESTRICTIONS = new ConcurrentHashMap<>();
    static {
        Map<String, BigDecimal> stakeHolderType5Restrictions = new ConcurrentHashMap<>();
        stakeHolderType5Restrictions.put(EXTENTINSQMTS, BigDecimal.valueOf(10000));
        stakeHolderType5Restrictions.put(TOTAL_PLINT_AREA, BigDecimal.valueOf(1000));
        stakeHolderType5Restrictions.put(FLOOR_COUNT, BigDecimal.valueOf(4));
        STAKEHOLDERTYPE5RESTRICTIONS.put("supervisor senior", stakeHolderType5Restrictions);
    }

    private static final Map<String, Map<String, BigDecimal>> STAKEHOLDERTYPE6RESTRICTIONS = new ConcurrentHashMap<>();
    static {
        Map<String, BigDecimal> stakeHolderType6Restrictions = new ConcurrentHashMap<>();
        stakeHolderType6Restrictions.put(EXTENTINSQMTS, BigDecimal.valueOf(5000));
        STAKEHOLDERTYPE1RESTRICTIONS.put("building designer - b", stakeHolderType6Restrictions);
    }

    private static final Map<String, Map<String, BigDecimal>> STAKEHOLDERTYPE7RESTRICTIONS = new ConcurrentHashMap<>();
    static {
        Map<String, BigDecimal> stakeHolderType7Restrictions = new ConcurrentHashMap<>();
        stakeHolderType7Restrictions.put(EXTENTINSQMTS, BigDecimal.valueOf(1000000)); // Unlimited
        STAKEHOLDERTYPE7RESTRICTIONS.put("town planner - a", stakeHolderType7Restrictions);
    }

    private static final Map<String, Map<String, BigDecimal>> STAKEHOLDERTYPE8RESTRICTIONS = new ConcurrentHashMap<>();
    static {
        Map<String, BigDecimal> stakeHolderType8Restrictions = new ConcurrentHashMap<>();
        stakeHolderType8Restrictions.put(EXTENTINSQMTS, BigDecimal.valueOf(10000));
        STAKEHOLDERTYPE8RESTRICTIONS.put("town planner - b", stakeHolderType8Restrictions);
    }

    private static final List<String> BPAFEECATEGORY1 = new ArrayList<>();
    static {
        BPAFEECATEGORY1.add(ST_CODE_01); // New Construction
        BPAFEECATEGORY1.add(ST_CODE_02); // Demolition
        BPAFEECATEGORY1.add(ST_CODE_03); // Reconstruction
        BPAFEECATEGORY1.add(ST_CODE_04); // Alteration
        BPAFEECATEGORY1.add(ST_CODE_06); // Adding of Extension
        BPAFEECATEGORY1.add(ST_CODE_07); // Change in Occupancy
    }

    private static final List<String> VALIDATIONPUPOSE = new ArrayList<>();
    static {
        VALIDATIONPUPOSE.add(ST_CODE_02); // Demolition
        VALIDATIONPUPOSE.add(ST_CODE_01); // New Construction
        VALIDATIONPUPOSE.add(ST_CODE_03); // Reconstruction
        VALIDATIONPUPOSE.add(ST_CODE_04); // Alteration
        VALIDATIONPUPOSE.add(ST_CODE_06); // Adding of Extension
        VALIDATIONPUPOSE.add(ST_CODE_07); // Change in Occupancy
    }

    private static final List<String> EDCRREQUIREDSERVICES = new ArrayList<>();
    static {
        EDCRREQUIREDSERVICES.add(ST_CODE_01); // New Construction
        EDCRREQUIREDSERVICES.add(ST_CODE_03); // Reconstruction
        EDCRREQUIREDSERVICES.add(ST_CODE_04); // Alteration
        EDCRREQUIREDSERVICES.add(ST_CODE_06); // Adding of Extension
        EDCRREQUIREDSERVICES.add(ST_CODE_07); // Change in Occupancy
    }

    private static final List<String> BPAFEECATEGORY2 = new ArrayList<>();

    static {
        BPAFEECATEGORY2.add(ST_CODE_05); // Sub-Division of Plot/Development of land
    }

    private static final Map<Integer, String> NOOFDAYS = new LinkedHashMap<>();
    static {
        NOOFDAYS.put(3, "Last 3 Days");
        NOOFDAYS.put(7, "Last 7 Days");
        NOOFDAYS.put(14, "Last 14 Days");
        NOOFDAYS.put(21, "Last 21 Days");
        NOOFDAYS.put(28, "Last 28 Days");
    }
    
    private static final Map<String, String> NOCTYPE = new ConcurrentHashMap<>();
    static {
    	NOCTYPE.put(FIRENOCTYPE, FIRENOCROLE);
    	NOCTYPE.put(PH7NOCTYPE, PH7NOCROLE);    	
    	NOCTYPE.put(TEHNOCTYPE, TEHNOCROLE);
    	NOCTYPE.put(PHNOCTYPE, PHNOCROLE); 
    	NOCTYPE.put(MANINOCTYPE, MANINOCROLE); 
    	NOCTYPE.put(ROAD2NOCTYPE, ROAD2NOCROLE);
    	NOCTYPE.put(PACNOCTYPE, PACNOCROLE);
    	NOCTYPE.put(STRCNOCTYPE, STRCNOCROLE); 
    	NOCTYPE.put(ELECNOCTYPE, ELECNOCROLE); 
    	NOCTYPE.put(POLNOCTYPE, POLNOCROLE);
    }
    
    private static final Map<String, String> OCNOCTYPE = new ConcurrentHashMap<>();
    static {
    	NOCTYPE.put(FIREOCNOCTYPE, FIRENOCROLE);
    	//NOCTYPE.put(AIRPORTOCNOCTYPE, AIRPORTNOCROLE);
    	//NOCTYPE.put(NMAOCNOCTYPE, NMANOCROLE);
    	//NOCTYPE.put(ENVOCNOCTYPE, ENVNOCROLE);
    }

    // Update and use this code if DCR integration require to particular service type and occupancy
    /*
     * private static final List<String> EDCRREQUIREDOCCUPANCY = new ArrayList<>(); static {
     * EDCRREQUIREDOCCUPANCY.add(RESIDENTIAL); } private static final Map<String, List<String>> EDCRREQUIREDSERVICES = new
     * ConcurrentHashMap<>(); static { EDCRREQUIREDSERVICES.put(ST_CODE_01, EDCRREQUIREDOCCUPANCY);
     * EDCRREQUIREDSERVICES.put(ST_CODE_06, EDCRREQUIREDOCCUPANCY); }
     */

    private BpaConstants() {
        // only invariants
    }

    public static Map<String, Map<String, BigDecimal>> getStakeholderType1Restrictions() {
        return Collections.unmodifiableMap(STAKEHOLDERTYPE1RESTRICTIONS);
    }

    public static Map<String, Map<String, BigDecimal>> getStakeholderType2Restrictions() {
        return Collections.unmodifiableMap(STAKEHOLDERTYPE2RESTRICTIONS);
    }

    public static Map<String, Map<String, BigDecimal>> getStakeholderType3Restrictions() {
        return Collections.unmodifiableMap(STAKEHOLDERTYPE3RESTRICTIONS);
    }

    public static Map<String, Map<String, BigDecimal>> getStakeholderType4Restrictions() {
        return Collections.unmodifiableMap(STAKEHOLDERTYPE4RESTRICTIONS);
    }

    public static Map<String, Map<String, BigDecimal>> getStakeholderType5Restrictions() {
        return Collections.unmodifiableMap(STAKEHOLDERTYPE5RESTRICTIONS);
    }

    public static Map<String, Map<String, BigDecimal>> getStakeholderType6Restrictions() {
        return Collections.unmodifiableMap(STAKEHOLDERTYPE6RESTRICTIONS);
    }

    public static Map<String, Map<String, BigDecimal>> getStakeholderType7Restrictions() {
        return Collections.unmodifiableMap(STAKEHOLDERTYPE7RESTRICTIONS);
    }

    public static Map<String, Map<String, BigDecimal>> getStakeholderType8Restrictions() {
        return Collections.unmodifiableMap(STAKEHOLDERTYPE8RESTRICTIONS);
    }

    public static Map<Integer, String> getSearchByNoOfDays() {
        return Collections.unmodifiableMap(NOOFDAYS);
    }

    public static List<String> getEdcrRequiredServices() {
        return Collections.unmodifiableList(EDCRREQUIREDSERVICES);
    }

    public static List<String> getBuildingFloorsList() {
        return Collections.unmodifiableList(FLOORLIST);
    }

    public static List<String> getServicesForBuildPermit() {
        return Collections.unmodifiableList(BUILDPERMIT);
    }

    public static List<String> getServicesForDevelopPermit() {
        return Collections.unmodifiableList(DEVELOPPERMIT);
    }

    public static List<String> getServicesForOtherPermit() {
        return Collections.unmodifiableList(BUILDING_PERMIT_OTHERS);
    }

    public static List<String> getBpaFeeCateory1() {
        return Collections.unmodifiableList(BPAFEECATEGORY1);
    }

    public static List<String> getBpaFeeCateory2() {
        return Collections.unmodifiableList(BPAFEECATEGORY2);
    }

    public static List<String> getServicesForValidation() {
        return Collections.unmodifiableList(VALIDATIONPUPOSE);
    }
    
    public static Map<String, String> getNocRole() {
        return Collections.unmodifiableMap(NOCTYPE);
    }
    
    public static Map<String, String> getOCNocRole() {
        return Collections.unmodifiableMap(OCNOCTYPE);
    }

}
