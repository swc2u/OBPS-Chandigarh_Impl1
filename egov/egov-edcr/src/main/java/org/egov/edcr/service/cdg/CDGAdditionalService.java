package org.egov.edcr.service.cdg;

import java.io.FileReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.commons.entity.cdg.ServiceAvailabilityCheckConstant;
import org.egov.edcr.constants.DxfFileConstants;
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

	public static final String SETBACKS = "setBack";
	public static final String FAR = "far";
	public static final String PERMISSIBLE_BUILDING_HEIGHT = "pbh";
	public static final String NO_OF_STORY = "nos";
	public static final String BACK_YARD_CONSTRUCTION = "byc";

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

	private String featurePropertiesLocation;

	private Properties setbackProperties;
	private Properties farProperties;
	private Properties permissibleBuildingHightProperties;
	private Properties noOfStoryProperties;
	private Properties backYardConstructionProperties;
	private static Properties byLawsProperties;

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
		String occkey = occupancyTypeHelper.getSubtype() != null ? occupancyTypeHelper.getSubtype().getCode() : "";
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

	public static String getString(String str) {
		return str.replaceAll("[^a-zA-Z0-9,.,*]", "_").toUpperCase();
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
		MathContext m = new MathContext(mathContext);
		return number.round(m);

	}

	public static BigDecimal roundBigDecimal(BigDecimal number) {
		int mathContext = 2;
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

	public static void main(String[] args) {
		System.out.println(BigDecimal.valueOf(1267.635).round(new MathContext(2)));
	}
}
