package com.pwc.XlsxReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.HashMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.pwc.XlsxReader.entity.BackYardConstruction;
import com.pwc.XlsxReader.entity.DxfConstent;
import com.pwc.XlsxReader.entity.Far;
import com.pwc.XlsxReader.entity.Master;
import com.pwc.XlsxReader.entity.MyShorting;
import com.pwc.XlsxReader.entity.NoOfStory;
import com.pwc.XlsxReader.entity.PermissibleBuildingHeight;
import com.pwc.XlsxReader.entity.SetBack;

public class ExcelReader {

	public static final String SHEET_NAME_MASTER = "Master";
	public static final String SHEET_NAME_FAR = "FAR";
	public static final String SHEET_NAME_NO_OF_STOREYS = "No of storeys";
	public static final String SHEET_NAME_BACK_YARD_COMSTRUCTIONS = "Back Yard construction";
	public static final String SHEET_NAME_SETBACKS = "Setbacks";
	public static final String SHEET_NAME_PERMISSIBLE_BUILDING_HEIGHT = "Height";
	// public static final String SAMPLE_XLSX_FILE_PATH =
	// "/home/root1/Desktop/doc/project ref doc/march/Master rule book v-18.xlsx";
//	public static final String SAMPLE_XLSX_FILE_PATH="/XlsxReader2/src/main/java/com/pwc/xlsx/Master rule book v-18.xlsx";
//	public static final String SAMPLE_XLSX_FILE_PATH="src/main/java/com/pwc/xlsx/44A&44C v3.xlsx";
//	public static final String SAMPLE_XLSX_FILE_PATH="src/main/java/com/pwc/xlsx/freedy-01Aug2020.xlsx";
//	public static final String SAMPLE_XLSX_FILE_PATH = "C:\\Workspace\\chandigarh\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\33C MASTER DATA-updated for 28 Feb 2021.xlsx";
//	public static final String SAMPLE_XLSX_FILE_PATH  = "C:\\Workspace\\Chandigarh\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\MASTER DATA FOR COMBINED PLOT_6sep21.xlsx";
//	public static final String SAMPLE_XLSX_FILE_PATH  = "C:\\Workspace\\Chandigarh\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\35D  MASTER DATA.xlsx";
//	public static final String SAMPLE_XLSX_FILE_PATH  = "C:\\Workspace\\Chandigarh\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\master data for sector 18 and 22.xlsx";
//	public static final String SAMPLE_XLSX_FILE_PATH  = "C:\\Workspace\\Chandigarh\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\master data_8A_8B_8C and commercial_10-20 & above 40 V3.xlsx";
//	public static final String SAMPLE_XLSX_FILE_PATH  = "C:\\Workspace\\Chandigarh\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\master data for sector 15A,B, 23c and booth.xlsx";
//	public static final String SAMPLE_XLSX_FILE_PATH  = "C:\\Workspace\\Chandigarh\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\Updated Mater data.xlsx";
//	public static final String SAMPLE_XLSX_FILE_PATH  = "C:\\Workspace\\Chandigarh\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\Master data 013-07-2022.xlsx";
//	public static final String SAMPLE_XLSX_FILE_PATH  = "C:\\Workspace\\Chandigarh\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\Materdata_05-08-22 _industrial.xlsx";
//	public static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\Mater data 05-08-22 -industrial.xlsx";
//	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\Master data 23D, & 7-1-1608-residential.xlsx";
//	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\MASTER DATA TO UPDATE 26, 20-residential-0209.xlsx";
//	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\GOLF-0709-residential.xlsx";
//	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\Master data 12-09-2022-Residential.xlsx";
//	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\MASTER DATA 17-09-2022_residential.xlsx";
//	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\MASTER DATA 21-09-2022-residential.xlsx";
//	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\4-10-2022 Master data-Residential.xlsx";
//	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\12-10-2022 Master data-commercial.xlsx";
//	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\master data 18-10-2022-residential.xlsx";
//	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\master data 20-10-2022-residential.xlsx";
//	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\master data-25-10-commercial.xlsx";
//	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\Master data 27-10-2022-residential.xlsx";
//	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\Parking master data-residetial.xlsx";
//	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\Booth master data 2-11-2022-residential.xlsx";
//	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\Master data 14-11-2022-residential.xlsx";
	static final String SAMPLE_XLSX_FILE_PATH  = "D:\\Chandigarh_new\\OBPS-Chandigarh_Impl1\\MasterDataReader-Project\\XlsxReader2\\src\\main\\java\\com\\pwc\\xlsx\\NAC MANIMAJARA AND 17B 17-11-2022-residential.xlsx";
	
	public static void main(String[] args) throws IOException, InvalidFormatException {
		System.out.println("==================Start===================");
		Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));
		Sheet sheet = workbook.getSheetAt(workbook.getSheetIndex(SHEET_NAME_MASTER));
		processMaster(sheet);
		workbook.close();
		System.out.println("===================End===================");
	}

	private static void processMaster(Sheet sheet) {

		DataFormatter dataFormatter = new DataFormatter();
		List<Master> masters = new ArrayList<Master>();
		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;
			char key = 65;
			Master master = new Master();
			for (Cell cell : row) {
				String cellValue = dataFormatter.formatCellValue(cell);
				cellValue = DxfConstent.validateValue(cellValue);
				switch (key) {
				case 'A':
					break;
				case 'D':
					master.setCode(cellValue);
					break;
				case 'F':
					master.setSector(cellValue);
					break;
				case 'G':
					master.setPlotNo(cellValue);
					break;
				case 'H':
					master.setBackCourtyardConstructionWidth(cellValue);
					break;
				case 'I':
					master.setBackCourtyardConstructionHeight(cellValue);
					break;
				case 'J':
					master.setPlotAreaInSqm(cellValue);
					break;
				case 'K':
					master.setPlotAreaInSqy(cellValue);
					break;
				case 'L':
					master.setAreaType(cellValue);
					break;
				case 'M':
					master.setPlotDepth(cellValue);
					break;
				case 'N':
					master.setPlotWidth(cellValue);
					break;
				case 'O':
					master.setPermissibleBuildingStories(cellValue);
					break;
				case 'P':
					master.setPermissibleBuildingHeight(cellValue);
					break;
				case 'Q':
					master.setMaxmimumPermissibleFAR(cellValue);
					break;
				case 'R':
					master.setMinimumPermissibleSetback_Front(cellValue);
					break;
				case 'S':
					master.setMinimumPermissibleSetback_Rear(cellValue);
					break;
				case 'T':
					master.setMinimumPermissibleSetback_left(cellValue);
					break;
				case 'U':
					master.setMinimumPermissibleSetback_Right(cellValue);
					break;
				default:
					break;
				}

				key++;
			}
			master.setKey(master.getCode() + "." + master.getSector() + "." + master.getPlotNo() + "."
					+ master.getAreaType());
			masters.add(master);

		}
		processBackYardConstruction(masters);
		System.out.println();
		processFar(masters);
		System.out.println();
		processNoOfStory(masters);
		System.out.println();
		processPermissibleBuildingHight(masters);
		System.out.println();
		processSetBack(masters);
	}

	private static void processFar(List<Master> masters) {
		System.out.println("----------------start processFar ------------------");
		List<Far> fars = new ArrayList<Far>();

		for (Master master : masters) {
			Far far = new Far();
			far.setKey(Far.FAR_PREFIX + "." + master.getKey());
			far.setMaxmimumPermissibleFAR(master.getMaxmimumPermissibleFAR());
			fars.add(far);
		}

		HashMap<String, String> properties = new HashMap<String, String>();
		for (Far far : fars) {
			String s = properties.put(far.getKey(), far.getMaxmimumPermissibleFAR());
		}
		System.out.println("Master "+masters.size()+" | properties "+properties.size());
		Utill.writeToPropertiesFile(properties, "Far.properties");
		System.out.println("----------------end processFar ------------------");
	}

	private static void processNoOfStory(List<Master> masters) {
		System.out.println("----------------start processNoOfStory ------------------");
		List<NoOfStory> noOfStories = new ArrayList<NoOfStory>();

		for (Master master : masters) {
			NoOfStory noOfStory = new NoOfStory();
			noOfStory.setKey(NoOfStory.NO_OF_STORY_PREFIX + "." + master.getKey());
			noOfStory.setPermissibleBuildingStories(master.getPermissibleBuildingStories());
			noOfStories.add(noOfStory);
		}

		HashMap<String, String> properties = new HashMap<String, String>();
		for (NoOfStory far : noOfStories) {
			properties.put(far.getKey(), far.getPermissibleBuildingStories());
		}
		System.out.println("Master "+masters.size()+" | properties "+properties.size());
		Utill.writeToPropertiesFile(properties, "NoOfStory.properties");
		System.out.println("----------------end processNoOfStory ------------------");
	}

	private static void processPermissibleBuildingHight(List<Master> masters) {
		System.out.println("----------------start processPermissibleBuildingHight ------------------");
		List<PermissibleBuildingHeight> permissibleBuildingHeights = new ArrayList<PermissibleBuildingHeight>();

		for (Master master : masters) {
			PermissibleBuildingHeight permissibleBuildingHeight = new PermissibleBuildingHeight();
			permissibleBuildingHeight
					.setKey(PermissibleBuildingHeight.PERMISSIBLE_BUILDING_HEIGHT_PREFIX + "." + master.getKey());
			permissibleBuildingHeight.setPermissibleBuildingHeight(master.getPermissibleBuildingHeight());
			permissibleBuildingHeights.add(permissibleBuildingHeight);
		}

		HashMap<String, String> properties = new HashMap<String, String>();
		for (PermissibleBuildingHeight buildingHeight : permissibleBuildingHeights) {
			properties.put(buildingHeight.getKey(), buildingHeight.getPermissibleBuildingHeight());
		}
		System.out.println("Master "+masters.size()+" | properties "+properties.size());
		Utill.writeToPropertiesFile(properties, "PermissibleBuildingHeight.properties");
		System.out.println("----------------end processBackYardConstruction ------------------");
	}

	private static void processSetBack(List<Master> masters) {
		System.out.println("----------------start processSetBack ------------------");
		List<SetBack> setBacks = new ArrayList<SetBack>();

		for (Master master : masters) {
			SetBack setBack = new SetBack();

			setBack.setKeyFront(SetBack.SETBACK_PREFIX + "." + master.getKey() + "." + SetBack.FRONT_SUFFIX);
			setBack.setKeyRear(SetBack.SETBACK_PREFIX + "." + master.getKey() + "." + SetBack.REAR_SUFFIX);

			setBack.setMinimumPermissibleSetback_Front(master.getMinimumPermissibleSetback_Front());
			setBack.setMinimumPermissibleSetback_Rear(master.getMinimumPermissibleSetback_Rear());

			if (master.getMinimumPermissibleSetback_left() != null
					&& !master.getMinimumPermissibleSetback_left().contains("*")) {
				setBack.setMinimumPermissibleSetback_left(master.getMinimumPermissibleSetback_left());
				setBack.setKeyLeft(SetBack.SETBACK_PREFIX + "." + master.getKey() + "." + SetBack.LEFT_SUFFIX);
			} else if (master.getMinimumPermissibleSetback_left() != null) {
				DxfConstent.processSetBackLeft(master.getMinimumPermissibleSetback_left(), setBack);
				setBack.setKeyLeftArea(SetBack.SETBACK_PREFIX + "." + master.getKey() + "." + SetBack.LEFT_SUFFIX + "."
						+ SetBack.Area_SUFFIX);
				setBack.setKeyLeftDepth(SetBack.SETBACK_PREFIX + "." + master.getKey() + "." + SetBack.LEFT_SUFFIX + "."
						+ SetBack.DEPTH_SUFFIX);
				setBack.setKeyLeftWidth(SetBack.SETBACK_PREFIX + "." + master.getKey() + "." + SetBack.LEFT_SUFFIX + "."
						+ SetBack.WIDTH_SUFFIX);
			}

			if (master.getMinimumPermissibleSetback_Right() != null
					&& !master.getMinimumPermissibleSetback_Right().contains("*")) {
				setBack.setMinimumPermissibleSetback_Right(master.getMinimumPermissibleSetback_Right());
				setBack.setKeyRight(SetBack.SETBACK_PREFIX + "." + master.getKey() + "." + SetBack.RIGHT_SUFFIX);
			} else if (master.getMinimumPermissibleSetback_Right() != null) {
				DxfConstent.processSetBackRight(master.getMinimumPermissibleSetback_Right(), setBack);
				setBack.setKeyRightArea(SetBack.SETBACK_PREFIX + "." + master.getKey() + "." + SetBack.RIGHT_SUFFIX
						+ "." + SetBack.Area_SUFFIX);
				setBack.setKeyRightDepth(SetBack.SETBACK_PREFIX + "." + master.getKey() + "." + SetBack.RIGHT_SUFFIX
						+ "." + SetBack.DEPTH_SUFFIX);
				setBack.setKeyRightWidth(SetBack.SETBACK_PREFIX + "." + master.getKey() + "." + SetBack.RIGHT_SUFFIX
						+ "." + SetBack.WIDTH_SUFFIX);
			}

			setBacks.add(setBack);
		}

		HashMap<String, String> properties = new HashMap<String, String>();
		for (SetBack setBack : setBacks) {
			properties.put(setBack.getKeyFront(), setBack.getMinimumPermissibleSetback_Front());
			properties.put(setBack.getKeyRear(), setBack.getMinimumPermissibleSetback_Rear());

			if (setBack.getKeyRight() != null) {
				properties.put(setBack.getKeyRight(), setBack.getMinimumPermissibleSetback_Right());
			} else {
				if (setBack.getMinimumPermissibleSetback_Right_Area() != null)
					properties.put(setBack.getKeyRightArea(), setBack.getMinimumPermissibleSetback_Right_Area());
				if (setBack.getMinimumPermissibleSetback_Right_Depth() != null)
					properties.put(setBack.getKeyRightDepth(), setBack.getMinimumPermissibleSetback_Right_Depth());
				if (setBack.getMinimumPermissibleSetback_Right_Width() != null)
					properties.put(setBack.getKeyRightWidth(), setBack.getMinimumPermissibleSetback_Right_Width());
			}

			if (setBack.getKeyLeft() != null) {
				properties.put(setBack.getKeyLeft(), setBack.getMinimumPermissibleSetback_left());
			} else {
				if (setBack.getMinimumPermissibleSetback_left_Area() != null)
					properties.put(setBack.getKeyLeftArea(), setBack.getMinimumPermissibleSetback_left_Area());
				if (setBack.getMinimumPermissibleSetback_left_Depth() != null)
					properties.put(setBack.getKeyLeftDepth(), setBack.getMinimumPermissibleSetback_left_Depth());
				if (setBack.getMinimumPermissibleSetback_left_Width() != null)
					properties.put(setBack.getKeyLeftWidth(), setBack.getMinimumPermissibleSetback_left_Width());
			}
		}
		System.out.println("Master "+masters.size()+" | properties "+properties.size());
		Utill.writeToPropertiesFile(properties, "Setback.properties");
		System.out.println("----------------end processSetBack ------------------");
	}

	private static void processBackYardConstruction(List<Master> masters) {
		
		System.out.println("----------------start processBackYardConstruction ------------------");

		List<BackYardConstruction> backYardConstructions = new ArrayList<BackYardConstruction>();

		for (Master master : masters) {
			BackYardConstruction backYardConstruction = new BackYardConstruction();

			backYardConstruction.setKeyWidth(BackYardConstruction.BACKCOURTYARDCONSTRUCTION_PREFIX + "."
					+ master.getKey() + "." + BackYardConstruction.BACKCOURTYARDCONSTRUCTIONWIDTH_SUFFIX);
			;
			backYardConstruction.setKeyHight(BackYardConstruction.BACKCOURTYARDCONSTRUCTION_PREFIX + "."
					+ master.getKey() + "." + BackYardConstruction.BACKCOURTYARDCONSTRUCTIONHIGHT_SUFFIX);

			backYardConstruction.setBackCourtyardConstructionWidth(master.getBackCourtyardConstructionWidth());
			backYardConstruction.setBackCourtyardConstructionHeight(master.getBackCourtyardConstructionHeight());

			backYardConstructions.add(backYardConstruction);
		}

		HashMap<String, String> properties = new HashMap<String, String>();
		for (BackYardConstruction backYardConstruction : backYardConstructions) {
			properties.put(backYardConstruction.getKeyWidth(),
					backYardConstruction.getBackCourtyardConstructionWidth());
			properties.put(backYardConstruction.getKeyHight(),
					backYardConstruction.getBackCourtyardConstructionHeight());
		}
		System.out.println("Master "+masters.size()+" | properties "+properties.size());
		Utill.writeToPropertiesFile(properties, "BackYardConstruction.properties");
		System.out.println("----------------end processBackYardConstruction ------------------");
	}

	private static void printCellValue(Cell cell) {
		switch (cell.getCellTypeEnum()) {
		case BOOLEAN:
			System.out.print(cell.getBooleanCellValue());
			break;
		case STRING:
			System.out.print(cell.getRichStringCellValue().getString());
			break;
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				System.out.print(cell.getDateCellValue());
			} else {
				System.out.print(cell.getNumericCellValue());
			}
			break;
		case FORMULA:
			System.out.print(cell.getCellFormula());
			break;
		case BLANK:
			System.out.print("");
			break;
		default:
			System.out.print("");
		}

		System.out.print("\t");
	}
}
