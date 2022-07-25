package org.egov.edcr.service.cdg;

import java.io.FileReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.Occupancy;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.commons.entity.cdg.ServiceAvailabilityCheckConstant;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.entity.PlotMaster;
import org.egov.edcr.feature.AccessoryBuildingService;
import org.egov.edcr.feature.FireStair;
import org.egov.edcr.feature.GeneralStair;
import org.egov.edcr.feature.GeneralStairRural;
import org.egov.edcr.feature.OpenStairService;
import org.egov.edcr.feature.PassageService;
import org.egov.edcr.feature.SpiralStair;
import org.egov.edcr.feature.Verandah;
import org.egov.edcr.service.PlotMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CDGAdditionalService {

	public static final String FILE_FAR = "Far.properties";
	public static final String FILE_SETBACKS = "Setback.properties";
	public static final String FILE_PERMISSIBLE_BUILDING_HEIGHT = "PermissibleBuildingHeight.properties";
	public static final String FILE_NO_OF_STORY = "NoOfStory.properties";
	public static final String FILE_BACK_YARD_CONSTRUCTION = "BackYardConstruction.properties";
	public static final String FILE_BYLAWS = "ByLaws.properties";
	public static final String FILE_DRAWING_NUMBER="DrawingNumber.properties";
	public static final String FILE_JOB_NUMBER="JobNumber.properties";
	
	public static final String SETBACKS = "setBack";
	public static final String FAR = "far";
	public static final String PERMISSIBLE_BUILDING_HEIGHT = "pbh";
	public static final String NO_OF_STORY = "nos";
	public static final String BACK_YARD_CONSTRUCTION = "byc";
	public static final String DRAWING_NUMBER="dn";
	public static final String JOB_NUMBER="jn";
	

	public static final String OCCUPENCY_CODE = "OCC";
	public static final String SECTOR = "SECTOR";
	public static final String PLOT_NO = "PLOT_NUMBER";
	public static final String ROOT_BOUNDARY_TYPE = "ROOT_BOUNDARY_TYPE";// URBAN / RURAL
	public static final String PLOT_TYPE = "PLOT_TYPE"; // MARLA / ONE_KANAL / TWO_KANAL / ABOVE_TWO_KANAL
	public static final String ZONE = "ZONE"; // CENTER / EAST / SOUTH

	public static final String FRONT = "Front";
	public static final String REAR = "Rear";
	public static final String LEFT = "Left";
	public static final String RIGHT = "Right";

	public static final String SETBACK_FRONT = "Front";
	public static final String SETBACK_REAR = "Rear";
	public static final String SETBACK_LEFT = "Left";
	public static final String SETBACK_RIGHT = "Right";

	public static final String MAXMIUM_PERMISSIBLE_FAR = "Maxmimum Permissible FAR";
	public static final String PERMISSIBLE_BUILDING_STORIES = "Permissible building stories";
	public static final String BACK_COURTYARD_CONSTRUCTION_WIDTH = "backCourtyardConstructionWidth";
	public static final String BACK_COURTYARD_CONSTRUCTION_HEIGHT = "backCourtyardConstructionHeight";
	public static final int ROUND_UP_SCALE=1;
	

	private String featurePropertiesLocation;

	private Properties setbackProperties;
	private Properties farProperties;
	private Properties permissibleBuildingHightProperties;
	private Properties noOfStoryProperties;
	private Properties backYardConstructionProperties;
	private static Properties byLawsProperties;
	private static Properties drawingNumberProperties;
	private static Properties jobNumberProperties;
	
	@Autowired
	private PlotMasterService plotMasterService;

	@Autowired
	public void PwcService(@Value("${pwc.properties.dir}") String featurePropertiesLocation) {
		this.featurePropertiesLocation = featurePropertiesLocation;

		try {
			// read far properties
			FileReader setbackReader = new FileReader(featurePropertiesLocation + FILE_SETBACKS);
			setbackProperties = new Properties();
			setbackProperties.load(setbackReader);
			// read far properties
			FileReader farReader = new FileReader(featurePropertiesLocation + FILE_FAR);
			farProperties = new Properties();
			farProperties.load(farReader);

			// read permissible Building hight properties
			FileReader permissibleBuildingHightReader = new FileReader(
					featurePropertiesLocation + FILE_PERMISSIBLE_BUILDING_HEIGHT);
			permissibleBuildingHightProperties = new Properties();
			permissibleBuildingHightProperties.load(permissibleBuildingHightReader);

			// read no of story properties
			FileReader noOfStoryReader = new FileReader(featurePropertiesLocation + FILE_NO_OF_STORY);
			noOfStoryProperties = new Properties();
			noOfStoryProperties.load(noOfStoryReader);

			// read backyard properties
			FileReader backYardConstructionReader = new FileReader(
					featurePropertiesLocation + FILE_BACK_YARD_CONSTRUCTION);
			backYardConstructionProperties = new Properties();
			backYardConstructionProperties.load(backYardConstructionReader);

			FileReader byLawsReader = new FileReader(featurePropertiesLocation + FILE_BYLAWS);
			byLawsProperties = new Properties();
			byLawsProperties.load(byLawsReader);
			
			FileReader drawingNumberReader = new FileReader(featurePropertiesLocation + FILE_DRAWING_NUMBER);
			drawingNumberProperties = new Properties();
			drawingNumberProperties.load(drawingNumberReader);
			
			FileReader jobNumberReader = new FileReader(featurePropertiesLocation + FILE_JOB_NUMBER);
			jobNumberProperties = new Properties();
			jobNumberProperties.load(jobNumberReader);

		} catch (Exception e) {
			throw new RuntimeException("Properties file is required. // LOCATION:-" + featurePropertiesLocation
					+ "  and file name - " + FILE_SETBACKS + ", " + FILE_FAR + ", " + FILE_BACK_YARD_CONSTRUCTION + ", "
					+ FILE_NO_OF_STORY + ", " + FILE_PERMISSIBLE_BUILDING_HEIGHT);
		}
	}

	public Map<String, String> getFeatureValue(CDGAConstant featureName, Map<String, String> keyArrgument) {

		Map<String, String> map = new HashMap<String, String>();

		if (featureName.getCDGAConstantValue().equals(SETBACKS)) {
			String key = getBaseKey(SETBACKS, keyArrgument);
			String value1 = setbackProperties.getProperty(key + "." + RIGHT);
			String value2 = setbackProperties.getProperty(key + "." + LEFT);
			String value3 = setbackProperties.getProperty(key + "." + FRONT);
			String value4 = setbackProperties.getProperty(key + "." + REAR);
			map.put(SETBACK_RIGHT, value1 != null && value1.length() > 0 ? value1 : DxfFileConstants.DATA_NOT_FOUND);
			map.put(SETBACK_LEFT, value2 != null && value2.length() > 0 ? value2 : DxfFileConstants.DATA_NOT_FOUND);
			map.put(SETBACK_FRONT, value3 != null && value3.length() > 0 ? value3 : DxfFileConstants.DATA_NOT_FOUND);
			map.put(SETBACK_REAR, value4 != null && value4.length() > 0 ? value4 : DxfFileConstants.DATA_NOT_FOUND);
			
			PlotMaster dataFromDB = plotMasterService.searchPlotMasterData(keyArrgument.get(OCCUPENCY_CODE),keyArrgument.get(SECTOR),keyArrgument.get(PLOT_NO),keyArrgument.get(PLOT_TYPE));

		} else if (featureName.getCDGAConstantValue().equals(CDGAConstant.FAR.getCDGAConstantValue())) {
			String value = farProperties.getProperty(getBaseKey(CDGAConstant.FAR.getCDGAConstantValue(), keyArrgument));
			map.put(MAXMIUM_PERMISSIBLE_FAR,
					value != null && value.length() > 0 ? value : DxfFileConstants.DATA_NOT_FOUND);
		} else if (featureName.getCDGAConstantValue()
				.equals(CDGAConstant.PERMISSIBLE_BUILDING_HEIGHT.getCDGAConstantValue())) {
			String value = permissibleBuildingHightProperties.getProperty(
					getBaseKey(CDGAConstant.PERMISSIBLE_BUILDING_HEIGHT.getCDGAConstantValue(), keyArrgument));
			map.put(PERMISSIBLE_BUILDING_HEIGHT, value != null && value.length()>0? value : DxfFileConstants.DATA_NOT_FOUND);
		} else if (featureName.getCDGAConstantValue().equals(CDGAConstant.NO_OF_STORY.getCDGAConstantValue())) {
			String value = noOfStoryProperties
					.getProperty(getBaseKey(CDGAConstant.NO_OF_STORY.getCDGAConstantValue(), keyArrgument));
			map.put(PERMISSIBLE_BUILDING_STORIES,
					value != null && value.length() > 0 ? value : DxfFileConstants.DATA_NOT_FOUND);
		} else if (featureName.getCDGAConstantValue()
				.equals(CDGAConstant.BACK_YARD_CONSTRUCTION.getCDGAConstantValue())) {
			String value1 = backYardConstructionProperties
					.getProperty(getBaseKey(CDGAConstant.BACK_YARD_CONSTRUCTION.getCDGAConstantValue(), keyArrgument)
							+ "." + BACK_COURTYARD_CONSTRUCTION_WIDTH);
			String value2 = backYardConstructionProperties
					.getProperty(getBaseKey(CDGAConstant.BACK_YARD_CONSTRUCTION.getCDGAConstantValue(), keyArrgument)
							+ "." + BACK_COURTYARD_CONSTRUCTION_HEIGHT);
			map.put(BACK_COURTYARD_CONSTRUCTION_WIDTH,
					value1 != null && value1.length() > 0 ? value1 : DxfFileConstants.DATA_NOT_FOUND);
			map.put(BACK_COURTYARD_CONSTRUCTION_HEIGHT,
					value2 != null && value2.length() > 0 ? value2 : DxfFileConstants.DATA_NOT_FOUND);
		}else if(featureName.getCDGAConstantValue()
				.equals(CDGAConstant.JOB_NUMBER.getCDGAConstantValue())) {
			map.put(JOB_NUMBER, jobNumberProperties.getProperty(getBaseKeyCom(JOB_NUMBER, keyArrgument)));
		}else if(featureName.getCDGAConstantValue()
				.equals(CDGAConstant.DRAWING_NUMBER.getCDGAConstantValue())) {
			map.put(DRAWING_NUMBER, drawingNumberProperties.getProperty(getBaseKeyCom(DRAWING_NUMBER, keyArrgument)));
		}

		return map;
	}

	public static String getByLaws(OccupancyTypeHelper occupancyTypeHelper, CDGAConstant cdgaConstant) {

		String occkey = occupancyTypeHelper.getSubtype() != null ? occupancyTypeHelper.getSubtype().getCode() : "";
		String key = occkey + "." + cdgaConstant.getCDGAConstantValue().toUpperCase();
		String byLaws = byLawsProperties.getProperty(key);

		return byLaws != null ? byLaws : "";
	}

	public static String getByLaws(Plan pl, CDGAConstant cdgaConstant) {
		OccupancyTypeHelper occupancyTypeHelper = pl.getVirtualBuilding().getMostRestrictiveFarHelper();
		String occkey="";
		if(occupancyTypeHelper!=null)
		occkey = occupancyTypeHelper.getSubtype() != null ? occupancyTypeHelper.getSubtype().getCode() : "";
		String key = occkey + "." + cdgaConstant.getCDGAConstantValue();
		String byLaws = byLawsProperties.getProperty(key.toUpperCase());

		return byLaws != null ? byLaws : "";
	}

	public static int getSectorPhase(String sector) {
		int phase = -1;
		sector = sector.replaceAll("[^0-9]", "");
		if (sector == null || sector.isEmpty())
			return phase;
		long l = Long.parseLong(sector);

		if (l >= 1 && l <= 30)
			phase = 1;
		else if (l >= 31 && l <= 47)
			phase = 2;
		else if (l >= 48)
			phase = 3;

		return phase;
	}

	private String getBaseKey(String prefix, Map<String, String> keyArrgument) {

		StringBuffer stringBuffer = new StringBuffer(prefix + ".");
		stringBuffer.append(keyArrgument.get(OCCUPENCY_CODE) + ".");
		stringBuffer.append(getString(keyArrgument.get(SECTOR) + "."));
		stringBuffer.append(getString(keyArrgument.get(PLOT_NO) + "."));
		stringBuffer.append(getString(getAreaType(keyArrgument.get(PLOT_TYPE))));

		return stringBuffer.toString();
	}
	
	private String getBaseKeyCom(String prefix, Map<String, String> keyArrgument) {

		StringBuffer stringBuffer = new StringBuffer(prefix + ".");
		stringBuffer.append(keyArrgument.get(OCCUPENCY_CODE) + ".");
		stringBuffer.append(getString(keyArrgument.get(SECTOR) + "."));
		stringBuffer.append(getString(keyArrgument.get(PLOT_NO)));

		return stringBuffer.toString();
	}

	public static String getString(String str) {
		return str.replaceAll("[^a-zA-Z0-9,.,*,&,(,)]", "_").toUpperCase();
	}
	
	private static String getAreaType(String at) {
		if (at.equalsIgnoreCase(DxfFileConstants.ONE_KANAL)) {
			return DxfFileConstants.ONE_KANAL;
		} else if (at.equalsIgnoreCase(DxfFileConstants.TWO_KANAL)) {
			return DxfFileConstants.TWO_KANAL;
		} else if (at.equalsIgnoreCase(DxfFileConstants.ABOVE_TWO_KANAL)) {
			return DxfFileConstants.ABOVE_TWO_KANAL;
		} else {
			return DxfFileConstants.MARLA;
		}
	}

	public boolean verifyServices(Plan plan) {
		System.out.println("Service availability check start");
		boolean flage = false;

		if (ServiceAvailabilityCheckConstant.ROOT_BOUNDARY_TYPES
				.contains(plan.getPlanInfoProperties().get(DxfFileConstants.ROOT_BOUNDARY_TYPE))
				&& ServiceAvailabilityCheckConstant.PLOT_AREA_TYPES
						.contains(plan.getPlanInfoProperties().get(DxfFileConstants.PLOT_TYPE))) {
			flage = true;
		}
		System.out.println(plan.getPlanInfoProperties());
		System.out.println("Service availability check end. || result :- " + flage);
		return flage;
	}

	public static BigDecimal roundBigDecimal(BigDecimal number, int mathContext) {
		number=number.setScale(mathContext, BigDecimal.ROUND_HALF_UP);
		return number;

	}

	public static BigDecimal roundBigDecimal(BigDecimal number) {
		int mathContext = ROUND_UP_SCALE;
		return roundBigDecimal(number, mathContext);
	}

	public static BigDecimal getNumberOfPerson(Plan pl) {
		OccupancyTypeHelper mostRestrictiveOccupancyType = pl.getVirtualBuilding().getMostRestrictiveFarHelper();
		BigDecimal numberOfPerson = BigDecimal.ZERO;
		for (Block block : pl.getBlocks()) {
			for (Floor floor : block.getBuilding().getFloors()) {
				if (DxfFileConstants.F.equals(mostRestrictiveOccupancyType.getType().getCode())
						|| DxfFileConstants.ITH_C.equals(mostRestrictiveOccupancyType.getType().getCode())
						|| DxfFileConstants.ITH_CC.equals(mostRestrictiveOccupancyType.getType().getCode())
						|| DxfFileConstants.IP_C.equals(mostRestrictiveOccupancyType.getType().getCode())) {
					numberOfPerson
							.add(getNumberPerson(floor.getArea(), mostRestrictiveOccupancyType, floor.getNumber()));
				} else {
					numberOfPerson.add(getNumberPerson(floor.getArea(), mostRestrictiveOccupancyType));
				}
			}
		}

		return numberOfPerson;

	}

	private static BigDecimal getNumberPerson(BigDecimal bulidUpArea,
			OccupancyTypeHelper mostRestrictiveOccupancyType) {
		BigDecimal numberOfPerson = BigDecimal.ZERO;
		BigDecimal perPersonBuildupArea = BigDecimal.ZERO;
		if (DxfFileConstants.A.equals(mostRestrictiveOccupancyType.getType().getCode())
				|| DxfFileConstants.ITH_R.equals(mostRestrictiveOccupancyType.getType().getCode())
				|| DxfFileConstants.ITH_GH.equals(mostRestrictiveOccupancyType.getType().getCode())
				|| DxfFileConstants.IP_R.equals(mostRestrictiveOccupancyType.getType().getCode()))
			perPersonBuildupArea = BigDecimal.valueOf(12.5);
		else if (DxfFileConstants.G.equals(mostRestrictiveOccupancyType.getType().getCode())
				|| DxfFileConstants.IT.equals(mostRestrictiveOccupancyType.getType().getCode()))
			perPersonBuildupArea = BigDecimal.valueOf(10);
		else if (DxfFileConstants.P.equals(mostRestrictiveOccupancyType.getType().getCode())
				|| DxfFileConstants.ITH_H.equals(mostRestrictiveOccupancyType.getType().getCode())
				|| DxfFileConstants.IP_I.equals(mostRestrictiveOccupancyType.getType().getCode()))
			perPersonBuildupArea = BigDecimal.valueOf(15);
		else if (DxfFileConstants.B.equals(mostRestrictiveOccupancyType.getType().getCode()))
			perPersonBuildupArea = BigDecimal.valueOf(4);

		numberOfPerson = perPersonBuildupArea.divide(perPersonBuildupArea);

		return new BigDecimal(String.format("%.0f", numberOfPerson));

	}

	private static BigDecimal getNumberPerson(BigDecimal bulidUpArea, OccupancyTypeHelper mostRestrictiveOccupancyType,
			int floor) {
		BigDecimal numberOfPerson = BigDecimal.ZERO;
		BigDecimal perPersonBuildupArea = BigDecimal.ZERO;
		if ((DxfFileConstants.F.equals(mostRestrictiveOccupancyType.getType().getCode())
				|| DxfFileConstants.ITH_C.equals(mostRestrictiveOccupancyType.getType().getCode())
				|| DxfFileConstants.ITH_CC.equals(mostRestrictiveOccupancyType.getType().getCode())
				|| DxfFileConstants.IP_C.equals(mostRestrictiveOccupancyType.getType().getCode())) && floor <= 0)
			perPersonBuildupArea = BigDecimal.valueOf(1);
		else
			perPersonBuildupArea = BigDecimal.valueOf(6);

		numberOfPerson = perPersonBuildupArea.divide(perPersonBuildupArea);

		return new BigDecimal(String.format("%.0f", numberOfPerson));

	}

	
	public static BigDecimal meterToFoot(String value) {
		return meterToFoot(new BigDecimal(value));
	}
	
	public static BigDecimal meterToFoot(BigDecimal value) {
		BigDecimal valueInFoot=value.multiply(new BigDecimal("3.281"));
		valueInFoot=valueInFoot.setScale(ROUND_UP_SCALE, BigDecimal.ROUND_HALF_UP);
		return valueInFoot;
	}
	
	public static BigDecimal meterToFootArea(BigDecimal value) {
		BigDecimal valueInFoot=value.multiply(new BigDecimal("10.764"),MathContext.DECIMAL32);
		valueInFoot=valueInFoot.setScale(ROUND_UP_SCALE, BigDecimal.ROUND_HALF_UP);
		return valueInFoot;
	}
	
	public static BigDecimal meterToFootArea(String value) {
		return meterToFootArea(new BigDecimal(value));
	}
	
	public static BigDecimal inchToFeet(BigDecimal value) {
		BigDecimal feet=BigDecimal.ZERO;
		try {
			if(value==null || BigDecimal.ZERO.compareTo(value)>0)
				return feet;
			feet=value.divide(new BigDecimal("12"),MathContext.DECIMAL32);
			feet=feet.setScale(ROUND_UP_SCALE,BigDecimal.ROUND_HALF_UP);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return feet;
	}
	
	public static BigDecimal inchToFeet(String value) {
		if(value.length()<=0)
			return BigDecimal.ZERO;
		BigDecimal inch=new BigDecimal(value);
		return inchToFeet(inch);
	}
	
	public static BigDecimal feetToInch(String value) {
		if(value.length()<=0)
			return BigDecimal.ZERO;
		BigDecimal inch=new BigDecimal(value);
		return feetToInch(inch);
	}
	
	public static BigDecimal feetToInch(BigDecimal value) {
		BigDecimal inch=BigDecimal.ZERO;
		if(value==null || BigDecimal.ZERO.compareTo(value)>0)
			return inch;
		inch=value.multiply(new BigDecimal("12"),MathContext.DECIMAL32);
		inch = inch.setScale(ROUND_UP_SCALE, BigDecimal.ROUND_HALF_UP);
		return inch;
	}
	
	public static BigDecimal inchToMeter(BigDecimal value) {
		BigDecimal meter=BigDecimal.ZERO;
		if(value==null || BigDecimal.ZERO.compareTo(value)>0)
			return meter;
		meter=value.divide(new BigDecimal("39.37"),MathContext.DECIMAL32);
		meter=meter.setScale(ROUND_UP_SCALE, BigDecimal.ROUND_HALF_UP);
		return meter;
	}

	public static BigDecimal inchToMeter(String value) {
		if(value.length()<=0)
			return BigDecimal.ZERO;
		BigDecimal inch=new BigDecimal(value);
		return inchToMeter(inch);
	}
	
	public static BigDecimal inchToMeterArea(BigDecimal value) {
		BigDecimal meter=BigDecimal.ZERO;
		if(value==null || BigDecimal.ZERO.compareTo(value)>0)
			return meter;
		meter=value.divide(new BigDecimal("1550"),MathContext.DECIMAL32);
		meter=meter.setScale(ROUND_UP_SCALE, BigDecimal.ROUND_HALF_UP);
		return meter;
	}
	
	public static BigDecimal inchToMeterArea(String value) {
		if(value.length()<=0)
			return BigDecimal.ZERO;
		BigDecimal inch=new BigDecimal(value);
		inch=inch.setScale(ROUND_UP_SCALE, BigDecimal.ROUND_HALF_UP);
		return inchToMeterArea(inch);
	}
	
	public static BigDecimal feetToMeterArea(BigDecimal value) {
		BigDecimal meter=BigDecimal.ZERO;
		if(value==null || BigDecimal.ZERO.compareTo(value)>0)
			return meter;
		meter=value.divide(new BigDecimal("10.764"),MathContext.DECIMAL32);
		meter=meter.setScale(ROUND_UP_SCALE, BigDecimal.ROUND_HALF_UP);
		return meter;
	}
	
	public static BigDecimal feetToMeterArea(String value) {
		if(value.length()<=0)
			return BigDecimal.ZERO;
		BigDecimal inch=new BigDecimal(value);
		return feetToMeterArea(inch);
	}
	
	public static BigDecimal feetToMeter(BigDecimal value) {
		BigDecimal meter=BigDecimal.ZERO;
		if(value==null || BigDecimal.ZERO.compareTo(value)>0)
			return meter;
		meter=value.divide(new BigDecimal("30.48"),MathContext.DECIMAL32);
		meter=meter.setScale(ROUND_UP_SCALE, BigDecimal.ROUND_HALF_UP);
		return meter;
	}
	
	public static BigDecimal feetToMeter(String value) {
		if(value.length()<=0)
			return BigDecimal.ZERO;
		BigDecimal inch=new BigDecimal(value);
		return feetToMeter(inch);
	}
	
	
	public static String viewLenght(Plan pl,BigDecimal value) {
		String result=null;
		if(pl.getDrawingPreference().getInMeters()) {
			result= roundBigDecimal(value)+DxfFileConstants.METER;
		}else if(pl.getDrawingPreference().getInFeets()){
			result=getFeetAndInch(value);
		}
		return result;
	}
	
	public static String getFeetAndInch(BigDecimal feet) {
		String numberD = String.valueOf(feet.doubleValue());
		String result=numberD.substring(0,numberD.indexOf ( "." ))+DxfFileConstants.FEET;
		result=result+" "+feetToInch("0."+numberD.substring(numberD.indexOf ( "." )+1))+DxfFileConstants.INCH;
		return result;
	}
	
	public static String viewArea(Plan pl,BigDecimal value) {
		String result=null;
		if(pl.getDrawingPreference().getInMeters()) {
			result= roundBigDecimal(value)+" "+DxfFileConstants.METER_SQM;
		}else if(pl.getDrawingPreference().getInFeets()){
			result=value+" "+DxfFileConstants.FEET_SQM;
		}
		return result;
	}
	
	public static BigDecimal inchtoFeetArea(BigDecimal value) {
		try {
			if(value!=null && value.longValue()!=0)
				value=value.divide(new BigDecimal(144),MathContext.DECIMAL32);
				value=value.setScale(ROUND_UP_SCALE, BigDecimal.ROUND_HALF_UP);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static boolean isFeatureValidationRequired(Plan plan,Class clazz) {
		boolean flage=false;
		if(DxfFileConstants.ALTERATION.equals(plan.getServiceType()) || DxfFileConstants.ADDITION_OR_EXTENSION.equals(plan.getServiceType())) {
			if(clazz.isAssignableFrom(SpiralStair.class)) {
				for(Block block:plan.getBlocks()) {
					for (Floor floor : block.getBuilding().getFloors()) {
						if(floor.getSpiralStairs().size()!=0) {
							flage=true;
							return flage;
						}
					}
				}
			}else if(clazz.isAssignableFrom(GeneralStair.class)) {
				for(Block block:plan.getBlocks()) {
					for (Floor floor : block.getBuilding().getFloors()) {
						if(!floor.getGeneralStairs().isEmpty()) {
							flage=true;
							return flage;
						}
					}
				}
			}else if(clazz.isAssignableFrom(GeneralStairRural.class)) {
				for(Block block:plan.getBlocks()) {
					for (Floor floor : block.getBuilding().getFloors()) {
						if(!floor.getGeneralStairs().isEmpty()) {
							flage=true;
							return flage;
						}
					}
				}
			}else if(clazz.isAssignableFrom(FireStair.class)) {
				for(Block block:plan.getBlocks()) {
					for (Floor floor : block.getBuilding().getFloors()) {
						if(!floor.getFireStairs().isEmpty()) {
							flage=true;
							return flage;
						}
					}
				}
			}else if(clazz.isAssignableFrom(OpenStairService.class)) {
				return flage;
			}else if(clazz.isAssignableFrom(Verandah.class)) {
				for(Block block:plan.getBlocks()) {
					for (Floor floor : block.getBuilding().getFloors()) {
						if(floor.getVerandah()!=null) {
							flage=true;
							return flage;
						}
					}
				}
			}
			else if(clazz.isAssignableFrom(PassageService.class)) {
				for(Block block:plan.getBlocks()) {
						if(block.getBuilding().getPassage()!=null) {
							flage=true;
							return flage;
					}
				}
			}else if(clazz.isAssignableFrom(AccessoryBuildingService.class)) {
				if(!plan.getAccessoryBlocks().isEmpty()) {
					flage=true;
					return flage;
				}
			}
		}else {
			flage=true;
		}
		
		return flage;
	}
	
	public static boolean isOccupancyExcludedFromFar(OccupancyTypeHelper helper) {
		boolean flage=false;
		
		if(helper!=null && helper.getSubtype()!=null && helper.getSubtype().getCode()!=null) {
			if(DxfFileConstants.A_SQ.equals(helper.getSubtype().getCode())
					|| DxfFileConstants.A_PO.equals(helper.getSubtype().getCode())
					|| DxfFileConstants.A_S.equals(helper.getSubtype().getCode())
					|| DxfFileConstants.A_PG.equals(helper.getSubtype().getCode())
					|| DxfFileConstants.A_ICP.equals(helper.getSubtype().getCode())
					|| DxfFileConstants.A_OCP.equals(helper.getSubtype().getCode())
					|| DxfFileConstants.A_AF.equals(helper.getSubtype().getCode())
					|| DxfFileConstants.A_GF.equals(helper.getSubtype().getCode())
					|| DxfFileConstants.A_R5.equals(helper.getSubtype().getCode())
					|| DxfFileConstants.OC.equals(helper.getType().getCode())
													)
				flage=true;
		}
		
		return flage;
	}
	
}
