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

package org.egov.edcr.feature;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.Measurement;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.Room;
import org.egov.common.entity.edcr.RoomHeight;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.service.ProcessHelper;
import org.egov.edcr.service.cdg.CDGAConstant;
import org.egov.edcr.service.cdg.CDGADeviationConstant;
import org.egov.edcr.service.cdg.CDGAdditionalService;
import org.springframework.stereotype.Service;

@Service
public class HeightOfRoom extends FeatureProcess {

	private static final String SUBRULE_41_II_A = "41-ii-a";
	private static final String SUBRULE_41_II_B = "41-ii-b";

	private static final String SUBRULE_41_II_A_AC_DESC = "Minimum height of ac room";
	private static final String SUBRULE_41_II_A_REGULAR_DESC = "Minimum height of regular room";
	private static final String SUBRULE_41_II_B_AREA_DESC = "Total area of rooms";
	private static final String SUBRULE_41_II_B_TOTAL_WIDTH = "Minimum Width of room";
	private static final String SUBRULE_41_II_B_TOTAL_DIMENSION = "Minimum dimension of room";

	public static final BigDecimal MINIMUM_HEIGHT_3_6 = BigDecimal.valueOf(3.6);
	public static final BigDecimal MINIMUM_HEIGHT_3 = BigDecimal.valueOf(3);
	public static final BigDecimal MINIMUM_HEIGHT_2_75 = BigDecimal.valueOf(2.75);
	public static final BigDecimal MINIMUM_HEIGHT_2_4 = BigDecimal.valueOf(2.4);
	public static final BigDecimal MINIMUM_AREA_9_5 = BigDecimal.valueOf(9.5);// 9.29
	public static final BigDecimal MINIMUM_WIDTH_2_4 = BigDecimal.valueOf(2.4);
	public static final BigDecimal MINIMUM_WIDTH_2_1 = BigDecimal.valueOf(2.1);
	private static final String FLOOR = "Floor";
	private static final String ROOM_HEIGHT_NOTDEFINED = "Room height is not defined in layer ";
	private static final String LAYER_ROOM_HEIGHT = "BLK_%s_FLR_%s_%s";

	@Override
	public Plan validate(Plan pl) {
		Map<String, Integer> heightOfRoomFeaturesColor = pl.getSubFeatureColorCodesMaster().get("HeightOfRoom");
		for (Block block : pl.getBlocks()) {
			int regularRoomCount = 0;
			for (Floor floor : block.getBuilding().getFloors()) {
				// regularRoomCount=floor.getRegularRoom()!=null?regularRoomCount+floor.getRegularRoom().getRooms().size():regularRoomCount;
				List<Room> rooms = getRoom(floor.getRegularRooms(),
						heightOfRoomFeaturesColor.get(DxfFileConstants.COLOR_RESIDENTIAL_ROOM));
				if (!rooms.isEmpty()) {
					regularRoomCount = rooms.size() + regularRoomCount;
				}

			}
			if (isRoomMandatory(pl)) {
				if (regularRoomCount < 1)
					pl.addError("roomRequired" + block.getNumber(),
							"Habitable room is mandatory, Block " + block.getNumber());
			}
		}

		return pl;
	}

	@Override
	public Plan process(Plan pl) {
		

		if (pl.isRural()) {
			processRural(pl);
			return pl;
		}
		Map<String, Integer> heightOfRoomFeaturesColor = pl.getSubFeatureColorCodesMaster().get("HeightOfRoom");
		validate(pl);
		HashMap<String, String> errors = new HashMap<>();
		if (pl != null && pl.getBlocks() != null) {
			OccupancyTypeHelper mostRestrictiveOccupancy = pl.getVirtualBuilding() != null
					? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
					: null;
			if (mostRestrictiveOccupancy != null && mostRestrictiveOccupancy.getType() != null
					&& mostRestrictiveOccupancy.getSubtype() != null

			) {
				for (Block block : pl.getBlocks()) {
					if (block.getBuilding() != null && !block.getBuilding().getFloors().isEmpty()) {
						scrutinyDetail = new ScrutinyDetail();
						scrutinyDetail.addColumnHeading(1, RULE_NO);
						scrutinyDetail.addColumnHeading(2, DESCRIPTION);
						scrutinyDetail.addColumnHeading(3, FLOOR);
						scrutinyDetail.addColumnHeading(4, ROOM);
						scrutinyDetail.addColumnHeading(5, REQUIRED);
						scrutinyDetail.addColumnHeading(6, PROVIDED);
						scrutinyDetail.addColumnHeading(7, STATUS);

						scrutinyDetail.setKey("Block_" + block.getNumber() + "_" + "Room");

						int roomCount = 0;

						for (Floor floor : block.getBuilding().getFloors()) {
							if (floor.getNumber() < 0)
								continue;
							List<BigDecimal> roomAreas = new ArrayList<>();
							List<BigDecimal> roomWidths = new ArrayList<>();
							BigDecimal minimumHeight = BigDecimal.ZERO;
							BigDecimal totalArea = BigDecimal.ZERO;
							BigDecimal minWidth = BigDecimal.ZERO;
							String subRule = null;
							String subRuleDesc = null;
							String color = "";

							color = DxfFileConstants.COLOR_RESIDENTIAL_ROOM;

							if (!floor.getRegularRooms().isEmpty()) {
								List<BigDecimal> residentialRoomHeights = new ArrayList<>();

								for (Room room : floor.getRegularRooms()) {
									for (RoomHeight roomHeight : room.getHeights()) {
										if (heightOfRoomFeaturesColor.get(color) == roomHeight.getColorCode()) {
											residentialRoomHeights.add(roomHeight.getHeight());
										}
									}
								}
								List<Room> habitableRooms = getRoom(floor.getRegularRooms(),
										heightOfRoomFeaturesColor.get(DxfFileConstants.COLOR_RESIDENTIAL_ROOM));

//                                for (Measurement room : habitableRooms.) {
//                                    if (heightOfRoomFeaturesColor.get(color) == room.getColorCode()) {
//                                        roomAreas.add(room.getArea());
//                                        roomWidths.add(room.getWidth());
//                                    }
//                                }
								int roomNumber = 1;
								for (Room r : habitableRooms) {
									if (r.getRooms() != null && !r.getRooms().isEmpty() && r.getRooms().size() >= 1) {
										Measurement room = r.getRooms().get(0);
										minimumHeight = MINIMUM_HEIGHT_2_75;
										BigDecimal minimumArea = MINIMUM_AREA_9_5;
										minWidth = MINIMUM_WIDTH_2_4;
										BigDecimal providedHeight = BigDecimal.ZERO;
										try {
											providedHeight = r.getHeights().stream().map(RoomHeight::getHeight)
													.reduce(BigDecimal::min).get();
										} catch (Exception e) {

										}

										subRule = CDGAdditionalService.getByLaws(pl, CDGAConstant.HABITABLE_ROOM);
										subRuleDesc = SUBRULE_41_II_B_AREA_DESC;

										boolean valid = false;
										boolean isTypicalRepititiveFloor = false;
										Map<String, Object> typicalFloorValues = ProcessHelper
												.getTypicalFloorValues(block, floor, isTypicalRepititiveFloor);

										buildResultArea(pl, floor, roomNumber, minimumArea, subRule, subRuleDesc,
												room.getArea(), valid, typicalFloorValues);

										subRuleDesc = SUBRULE_41_II_B_TOTAL_WIDTH;
										buildResult(pl, floor, roomNumber,
												CDGADeviationConstant.addDeviation(minWidth,
														CDGADeviationConstant.ROOM_DEVIATION_WIDTH),
												subRule, subRuleDesc, room.getWidth(), valid, typicalFloorValues);

										subRuleDesc = SUBRULE_41_II_A_REGULAR_DESC;
										buildResult(pl, floor, roomNumber, minimumHeight, subRule, subRuleDesc,
												providedHeight, valid, typicalFloorValues);

										roomNumber++;
									}
								}

							}

						}
					}
				}
				pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
			}
		}
		return pl;

	}

	private List<Measurement> getRoom(Room room, int colorCode) {
		List<Measurement> spcRoom = new ArrayList<Measurement>();
		if (room != null) {
			for (Measurement r : room.getRooms()) {
				if (colorCode == r.getColorCode()) {
					spcRoom.add(r);
				}
			}
		}
		return spcRoom;
	}

	private List<Room> getRoom(List<Room> rooms, int colorCode) {
		List<Room> spcRoom = new ArrayList<Room>();
		if (rooms != null) {
			for (Room room : rooms) {
				if (room.getRooms() != null && !room.getRooms().isEmpty() && room.getRooms().size() >= 1) {
					Measurement r = room.getRooms().get(0);
					if (colorCode == r.getColorCode()) {
						spcRoom.add(room);
					}
				}
			}
		}
		return spcRoom;
	}

	private boolean isRoomMandatory(Plan plan) {
		boolean flage = false;
		OccupancyTypeHelper occupancyTypeHelper = plan.getVirtualBuilding().getMostRestrictiveFarHelper() != null
				? plan.getVirtualBuilding().getMostRestrictiveFarHelper()
				: null;
		if (DxfFileConstants.NEW_CONSTRUCTION.equals(plan.getServiceType())
				|| DxfFileConstants.RECONSTRUCTION.equals(plan.getServiceType())) {
			if (DxfFileConstants.A_P.equals(occupancyTypeHelper.getSubtype().getCode())
					|| DxfFileConstants.A_G.equals(occupancyTypeHelper.getSubtype().getCode()))
				flage = true;
		}

		return flage;
	}

	public Plan processRural(Plan pl) {
		Map<String, Integer> heightOfRoomFeaturesColor = pl.getSubFeatureColorCodesMaster().get("HeightOfRoom");
		HashMap<String, String> errors = new HashMap<>();
		if (pl != null && pl.getBlocks() != null) {
			OccupancyTypeHelper mostRestrictiveOccupancy = pl.getVirtualBuilding() != null
					? pl.getVirtualBuilding().getMostRestrictiveFarHelper()
					: null;
			if (mostRestrictiveOccupancy != null && mostRestrictiveOccupancy.getType() != null
					&& mostRestrictiveOccupancy.getSubtype() != null

			) {
				for (Block block : pl.getBlocks()) {
					if (block.getBuilding() != null && !block.getBuilding().getFloors().isEmpty()) {
						scrutinyDetail = new ScrutinyDetail();
						scrutinyDetail.addColumnHeading(1, RULE_NO);
						scrutinyDetail.addColumnHeading(2, DESCRIPTION);
						scrutinyDetail.addColumnHeading(3, FLOOR);
						scrutinyDetail.addColumnHeading(4, ROOM);
						scrutinyDetail.addColumnHeading(5, REQUIRED);
						scrutinyDetail.addColumnHeading(6, PROVIDED);
						scrutinyDetail.addColumnHeading(7, STATUS);

						scrutinyDetail.setKey("Block_" + block.getNumber() + "_" + "Room");

						for (Floor floor : block.getBuilding().getFloors()) {
							List<BigDecimal> roomAreas = new ArrayList<>();
							List<BigDecimal> roomWidths = new ArrayList<>();
							BigDecimal minimumHeight = BigDecimal.ZERO;
							BigDecimal totalArea = BigDecimal.ZERO;
							BigDecimal minWidth = BigDecimal.ZERO;
							String subRule = null;
							String subRuleDesc = null;
							String color = "";

//                            if (A.equalsIgnoreCase(mostRestrictiveOccupancy.getType().getCode()))
//                                color = DxfFileConstants.COLOR_RESIDENTIAL_ROOM;
//                            else if(F.equalsIgnoreCase(mostRestrictiveOccupancy.getType().getCode()))
//                                color = DxfFileConstants.COLOR_COMMERCIAL_ROOM;
//                            else if(G.equalsIgnoreCase(mostRestrictiveOccupancy.getType().getCode()))
//                                color = DxfFileConstants.COLOR_INDUSTRIAL_ROOM;
							color = DxfFileConstants.COLOR_RESIDENTIAL_ROOM;

//                            if (floor.getAcRoom() != null) {
//                                List<BigDecimal> residentialAcRoomHeights = new ArrayList<>();
//                                List<RoomHeight> acHeights = floor.getAcRoom().getHeights();
//                                List<Measurement> acRooms = floor.getAcRoom().getRooms();
//
//                                for (RoomHeight roomHeight : acHeights) {
//                                    if (heightOfRoomFeaturesColor.get(color) == roomHeight.getColorCode()) {
//                                        residentialAcRoomHeights.add(roomHeight.getHeight());
//                                    }
//                                }
//
//                                for (Measurement acRoom : acRooms) {
//                                    if (heightOfRoomFeaturesColor.get(color) == acRoom.getColorCode()) {
//                                        roomAreas.add(acRoom.getArea());
//                                        roomWidths.add(acRoom.getWidth());
//                                    }
//                                }
//
//                               
//                                    BigDecimal minHeight = residentialAcRoomHeights.stream().reduce(BigDecimal::min).get();
//
//                                    minimumHeight =new  BigDecimal("2.75");
//
//                                    subRule = CDGAdditionalService.getByLaws(pl, CDGAConstant.HABITABLE_ROOM);
//                                    subRuleDesc = SUBRULE_41_II_A_AC_DESC;
//                                    
//                                    
//                                    boolean valid = false;
//                                    boolean isTypicalRepititiveFloor = false;
//                                    Map<String, Object> typicalFloorValues = ProcessHelper.getTypicalFloorValues(block, floor,
//                                            isTypicalRepititiveFloor);
//                                    buildResult(pl, floor, minimumHeight, subRule, subRuleDesc, minHeight, valid,
//                                            typicalFloorValues);
//                                
//
//                            }

							if (!floor.getRegularRooms().isEmpty()) {
								List<BigDecimal> residentialRoomHeights = new ArrayList<>();

								List<Room> rooms = getRoom(floor.getRegularRooms(),
										heightOfRoomFeaturesColor.get(DxfFileConstants.COLOR_RESIDENTIAL_ROOM));

								for (Room room : floor.getRegularRooms()) {
									for (RoomHeight roomHeight : room.getHeights()) {
										if (heightOfRoomFeaturesColor.get(color) == roomHeight.getColorCode()) {
											residentialRoomHeights.add(roomHeight.getHeight());
										}
									}
								}

//                                for (Measurement room : rooms) {
//                                    if (heightOfRoomFeaturesColor.get(color) == room.getColorCode()) {
//                                        roomAreas.add(room.getArea());
//                                        roomWidths.add(room.getWidth());
//                                    }
//                                }
								int roomNumber = 1;
								for (Room r : rooms) {
									for (Measurement room : r.getRooms()) {
										totalArea = room.getArea();
										BigDecimal minRoomWidth = room.getWidth();

										minimumHeight = new BigDecimal("9.29");
										minWidth = new BigDecimal("2.2");

										subRule = CDGAdditionalService.getByLaws(pl, CDGAConstant.HABITABLE_ROOM);
										subRuleDesc = SUBRULE_41_II_B_AREA_DESC;

										boolean valid = false;
										boolean isTypicalRepititiveFloor = false;
										Map<String, Object> typicalFloorValues = ProcessHelper
												.getTypicalFloorValues(block, floor, isTypicalRepititiveFloor);
										buildResultArea(pl, floor, roomNumber, minimumHeight, subRule, subRuleDesc,
												totalArea, valid, typicalFloorValues);

										subRuleDesc = SUBRULE_41_II_B_TOTAL_DIMENSION;
										buildResult(pl, floor, roomNumber, minWidth, subRule, subRuleDesc, minRoomWidth,
												valid, typicalFloorValues);
										roomNumber++;
									}
								}

							}

						}
					}
				}
				pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
			}
		}
		return pl;

	}

	private void buildResult(Plan pl, Floor floor, int roomNumber, BigDecimal expected, String subRule,
			String subRuleDesc, BigDecimal actual, boolean valid, Map<String, Object> typicalFloorValues) {
		if (!(Boolean) typicalFloorValues.get("isTypicalRepititiveFloor")
				&& expected.compareTo(BigDecimal.valueOf(0)) > 0 && subRule != null && subRuleDesc != null) {

			String value = typicalFloorValues.get("typicalFloors") != null
					? (String) typicalFloorValues.get("typicalFloors")
					: " floor " + floor.getNumber();

			if (pl.getDrawingPreference().getInFeets()) {
				expected = CDGAdditionalService.meterToFoot(expected);
				actual = CDGAdditionalService.inchToFeet(actual);
			}
			if (actual.compareTo(expected) >= 0) {
				valid = true;
			}
			if (valid) {
				setReportOutputDetails(pl, subRule, subRuleDesc, value, roomNumber,
						CDGAdditionalService.viewLenght(pl, expected), CDGAdditionalService.viewLenght(pl, actual),
						Result.Accepted.getResultVal());
			} else {
				setReportOutputDetails(pl, subRule, subRuleDesc, value, roomNumber,
						CDGAdditionalService.viewLenght(pl, expected), CDGAdditionalService.viewLenght(pl, actual),
						Result.Not_Accepted.getResultVal());
			}
		}
	}

	private void buildResultArea(Plan pl, Floor floor, int room, BigDecimal expected, String subRule,
			String subRuleDesc, BigDecimal actual, boolean valid, Map<String, Object> typicalFloorValues) {
		if (!(Boolean) typicalFloorValues.get("isTypicalRepititiveFloor")
				&& expected.compareTo(BigDecimal.valueOf(0)) > 0 && subRule != null && subRuleDesc != null) {

			if (pl.getDrawingPreference().getInFeets()) {
				expected = CDGAdditionalService.meterToFootArea(
						CDGADeviationConstant.addDeviation(expected, CDGADeviationConstant.ROOM_DEVIATION_AREA));
				actual = CDGAdditionalService.inchtoFeetArea(actual);
			}

			if (actual.compareTo(expected) >= 0) {
				valid = true;
			}
			String value = typicalFloorValues.get("typicalFloors") != null
					? (String) typicalFloorValues.get("typicalFloors")
					: " floor " + floor.getNumber();
			if (valid) {
				setReportOutputDetails(pl, subRule, subRuleDesc, value, room,
						CDGAdditionalService.viewArea(pl, expected), CDGAdditionalService.viewArea(pl, actual),
						Result.Accepted.getResultVal());
			} else {
				setReportOutputDetails(pl, subRule, subRuleDesc, value, room,
						CDGAdditionalService.viewArea(pl, expected), CDGAdditionalService.viewArea(pl, actual),
						Result.Not_Accepted.getResultVal());
			}
		}
	}

	private boolean isOccupancyTypePlinthNotApplicable(OccupancyTypeHelper occupancyTypeHelper) {
		boolean flage = false;

		if (DxfFileConstants.F_TCIM.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.F_CD.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.B_EC.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.B_HEI.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.R1.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.ITH_H.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.ITH_C.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.ITH_CC.equals(occupancyTypeHelper.getSubtype().getCode())
				|| DxfFileConstants.T1.equals(occupancyTypeHelper.getSubtype().getCode()))
			flage = true;

		return flage;
	}

	private void setReportOutputDetails(Plan pl, String ruleNo, String ruleDesc, String floor, int room,
			String expected, String actual, String status) {
		Map<String, String> details = new HashMap<>();
		details.put(RULE_NO, ruleNo);
		details.put(DESCRIPTION, ruleDesc);
		details.put(FLOOR, floor);
		details.put(ROOM, room + "");
		details.put(REQUIRED, expected);
		details.put(PROVIDED, actual);
		details.put(STATUS, status);
		scrutinyDetail.getDetail().add(details);
		// pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
	}

	@Override
	public Map<String, Date> getAmendments() {
		return new LinkedHashMap<>();
	}
}