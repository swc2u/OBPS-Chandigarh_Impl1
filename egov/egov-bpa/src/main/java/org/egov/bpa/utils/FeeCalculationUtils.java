package org.egov.bpa.utils;

import static org.egov.bpa.utils.BpaConstants.ADDING_OF_EXTENSION;
import static org.egov.bpa.utils.BpaConstants.ALTERATION;
import static org.egov.bpa.utils.BpaConstants.NEW_CONSTRUCTION;
import static org.egov.bpa.utils.BpaConstants.RECONSTRUCTION;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.bpa.master.entity.BpaFeeMapping;
import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.master.service.BpaFeeMappingService;
import org.egov.bpa.model.LetterToPartyFees;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.PermitFeeCalculationService;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeCalculationUtils {
	private static final BigDecimal TWO_FIVE = BigDecimal.valueOf(2.5);
	private static final BigDecimal ONE_TWO_FIVE = BigDecimal.valueOf(1.25);

	@Autowired
	private BpaFeeMappingService bpaFeeMappingService;
	@Autowired
	protected ApplicationBpaService applicationBpaService;
	@Autowired
	private PermitFeeCalculationService permitFeeCalculationService;

	public Map<String, String> calculateAllFeesWhileApplying(final BpaApplication application) {
		Map<String, String> fees = new HashMap<String, String>();
		if (application != null) {
			List<Long> serviceTypeList = new ArrayList<>();
			serviceTypeList.add(application.getServiceType().getId());

			if (!application.getApplicationAmenity().isEmpty()) {
				for (ServiceType serviceType : application.getApplicationAmenity()) {
					serviceTypeList.add(serviceType.getId());
				}
			}

			Plan plan = applicationBpaService.getPlanInfo(application.geteDcrNumber());
			if (null != plan) {
				OccupancyTypeHelper mostRestrictiveFarHelper = plan.getVirtualBuilding() != null
						? plan.getVirtualBuilding().getMostRestrictiveFarHelper()
						: null;
				// not required before submit
				// List<LetterToPartyFees> lpAreas = populateLPExtAreas(application);

				if (plan.isRural()) {
					for (Long serviceTypeId : serviceTypeList) {
						for (BpaFeeMapping bpaFee : bpaFeeMappingService.getPermitFeesByAppType(application,
								serviceTypeId)) {
							if (bpaFee != null) {
								if (BpaConstants.SECURITY_FEE
										.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())) {
									BigDecimal securityFeeAmount = permitFeeCalculationService
											.getTotalScruitnyFeeRural(plan);
									if (securityFeeAmount.compareTo(BigDecimal.ZERO) > 0) {
										fees.put(bpaFee.getBpaFeeCommon().getDescription(), String
												.valueOf(securityFeeAmount.setScale(0, BigDecimal.ROUND_HALF_UP)));
									}
								} else if (BpaConstants.DEVELOPMENT_CHARGES_OF_ROADS
										.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())) {
									BigDecimal totalAmount = permitFeeCalculationService
											.getTotalDevelopmentChargesOfRoads(plan);
									if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
										fees.put(bpaFee.getBpaFeeCommon().getDescription(),
												String.valueOf(totalAmount.setScale(0, BigDecimal.ROUND_HALF_UP)));
									}
								} else if (BpaConstants.CONVERSION_CHARGES
										.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())) {
									BigDecimal totalAmount = permitFeeCalculationService
											.getTotalConversionCharges(plan);
									if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
										fees.put(bpaFee.getBpaFeeCommon().getDescription(),
												String.valueOf(totalAmount.setScale(0, BigDecimal.ROUND_HALF_UP)));
									}
								} else if (BpaConstants.TRANSFER_FEE
										.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())) {
									BigDecimal totalAmount = permitFeeCalculationService.getTotalTransferFee(plan);
									if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
										fees.put(bpaFee.getBpaFeeCommon().getDescription(),
												String.valueOf(totalAmount.setScale(0, BigDecimal.ROUND_HALF_UP)));
									}
								} else if (BpaConstants.CONSTRUCTION_AND_DEMOLISION
										.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())) {
									BigDecimal totalAmount = permitFeeCalculationService
											.getTotalConstructionAndDemolisionFee(plan);
									if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
										fees.put(bpaFee.getBpaFeeCommon().getDescription(),
												String.valueOf(totalAmount.setScale(0, BigDecimal.ROUND_HALF_UP)));
									}
								} else if (BpaConstants.ALLOTMENT_OF_NEW_NUMBER
										.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())) {
									BigDecimal totalAmount = permitFeeCalculationService
											.getTotalAllotmentOfNewNumberFee(plan);
									if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
										fees.put(bpaFee.getBpaFeeCommon().getDescription(),
												String.valueOf(totalAmount.setScale(0, BigDecimal.ROUND_HALF_UP)));
									}
								}
							}
						}
					}
					return fees;
				}

				for (Long serviceTypeId : serviceTypeList) {
					BpaFeeMapping bpaGST = null;
					for (BpaFeeMapping fee : bpaFeeMappingService.getPermitFeesByAppType(application, serviceTypeId)) {
						if (fee != null) {
							if (BpaConstants.GST_18.equals(fee.getBpaFeeCommon().getDescription())
									&& (fee.getServiceType().getDescription().equalsIgnoreCase(NEW_CONSTRUCTION)
											|| fee.getServiceType().getDescription().equalsIgnoreCase(RECONSTRUCTION)
											|| fee.getServiceType().getDescription()
													.equalsIgnoreCase(ADDING_OF_EXTENSION)
											|| fee.getServiceType().getDescription().equalsIgnoreCase(ALTERATION))) {
								bpaGST = fee;
							}
						}
						System.out.println(fee.getBpaFeeCommon().getDescription());
					}
					BigDecimal totalGstApplicable=BigDecimal.ZERO;
					for (BpaFeeMapping bpaFee : bpaFeeMappingService.getPermitFeesByAppType(application,
							serviceTypeId)) {
						if (bpaFee != null) {
							if (bpaFee.getServiceType().getDescription().equalsIgnoreCase(NEW_CONSTRUCTION)
									|| bpaFee.getServiceType().getDescription().equalsIgnoreCase(RECONSTRUCTION)
									|| bpaFee.getServiceType().getDescription().equalsIgnoreCase(ADDING_OF_EXTENSION)
									|| bpaFee.getServiceType().getDescription().equalsIgnoreCase(ALTERATION)) {

								List<LetterToPartyFees> lpRecifiedAreas = new ArrayList<LetterToPartyFees>();
//								if (null != lpAreas) {
//									for (LetterToPartyFees lpArea : lpAreas) {
//										if (lpArea.getFeeName()
//												.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())
//												&& null != lpArea.getFloorarea()
//												&& lpArea.getFloorarea().compareTo(BigDecimal.ZERO) > 0) {
//											lpRecifiedAreas.add(lpArea);
//										}
//									}
//								}

								if (BpaConstants.SECURITY_FEE
										.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())) {
									BigDecimal securityFeeAmount = permitFeeCalculationService.getTotalSecurityFee(plan,
											mostRestrictiveFarHelper);
									if (securityFeeAmount.compareTo(BigDecimal.ZERO) >= 0) {
										fees.put(bpaFee.getBpaFeeCommon().getDescription(), String
												.valueOf(securityFeeAmount.setScale(0, BigDecimal.ROUND_HALF_UP)));
									}
								}
								if (BpaConstants.ADDITIONAL_HEIGHT_FEE
										.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())) {
									BigDecimal additionalHeightFeeAmount = permitFeeCalculationService.getAdditionalHeightFee(plan);
									if (additionalHeightFeeAmount.compareTo(BigDecimal.ZERO) >= 0) {
										fees.put(bpaFee.getBpaFeeCommon().getDescription(), String
												.valueOf(additionalHeightFeeAmount.setScale(0, BigDecimal.ROUND_HALF_UP)));
										
										if(BpaConstants.F.equals(mostRestrictiveFarHelper.getType().getCode()))
											totalGstApplicable=totalGstApplicable.add(additionalHeightFeeAmount);
									}
								}
								if (BpaConstants.SCRUTINY_FEE
										.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())) {
									BigDecimal multiplier = BigDecimal.ZERO;
									if (bpaFee.getServiceType().getDescription().equalsIgnoreCase(NEW_CONSTRUCTION)) {
										multiplier = TWO_FIVE;// 2.5
									} else if (bpaFee.getServiceType().getDescription()
											.equalsIgnoreCase(ADDING_OF_EXTENSION)
											|| bpaFee.getServiceType().getDescription().equalsIgnoreCase(ALTERATION)
											|| bpaFee.getServiceType().getDescription()
													.equalsIgnoreCase(RECONSTRUCTION)) {
										multiplier = ONE_TWO_FIVE;// 1.25
									}
									BigDecimal totalAmount = permitFeeCalculationService.getTotalScruitnyFee(plan,
											application.getBuildingDetail(), multiplier, lpRecifiedAreas);

									if (totalAmount.compareTo(BigDecimal.ZERO) >= 0) {
										fees.put(bpaFee.getBpaFeeCommon().getDescription(),
												String.valueOf(totalAmount.setScale(0, BigDecimal.ROUND_HALF_UP)));
//										if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
//											BigDecimal gstAmount = permitFeeCalculationService
//													.getTotalAmountOfGST(totalAmount);
//											fees.put(bpaGST.getBpaFeeCommon().getDescription(),
//													String.valueOf(gstAmount.setScale(0, BigDecimal.ROUND_HALF_UP)));
//										}
										totalGstApplicable=totalGstApplicable.add(totalAmount);
									}
								} else if (BpaConstants.LABOURCESS
										.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())) {
									BigDecimal totalAmount = permitFeeCalculationService.getTotalAmountOfLabourCess(
											application.getBuildingDetail(), plan, mostRestrictiveFarHelper,
											lpRecifiedAreas);
									if (totalAmount.compareTo(BigDecimal.ZERO) >= 0) {
										fees.put(bpaFee.getBpaFeeCommon().getDescription(),
												String.valueOf(totalAmount.setScale(0, BigDecimal.ROUND_HALF_UP)));
									}
									if(BpaConstants.F.equals(mostRestrictiveFarHelper.getType().getCode()))
										totalGstApplicable=totalGstApplicable.add(totalAmount);
								} else if (BpaConstants.RULE_5_FEE
										.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())) {
									BigDecimal rule5ExtraArea = BigDecimal.ZERO;
									if (null != lpRecifiedAreas) {
										for (LetterToPartyFees lprArea : lpRecifiedAreas) {
											if (lprArea.getFeeName()
													.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())
													&& null != lprArea.getFloorarea()
													&& lprArea.getFloorarea().compareTo(BigDecimal.ZERO) > 0) {
												rule5ExtraArea = rule5ExtraArea.add(lprArea.getFloorarea());
											}
										}
									}
									boolean isWorkAlreadyStarted = (null != checkIsWorkAlreadyStarted(application))
											? checkIsWorkAlreadyStarted(application)
											: false;
									BigDecimal totalAmount = BigDecimal.ZERO;
									if (isWorkAlreadyStarted) {
										totalAmount = permitFeeCalculationService.getTotalAmountOfRule5New(plan,
												lpRecifiedAreas, isWorkAlreadyStarted);
									} else {
//										if (rule5ExtraArea.compareTo(BigDecimal.ZERO) > 0) {
//											totalAmount = permitFeeCalculationService.getTotalAmountOfRule5New(plan,
//													lpRecifiedAreas, isWorkAlreadyStarted);
//										}
										totalAmount = permitFeeCalculationService.getTotalAmountOfRule5New(plan,
												lpRecifiedAreas, isWorkAlreadyStarted);
									}
									if (totalAmount.compareTo(BigDecimal.ZERO) >= 0) {
										fees.put(bpaFee.getBpaFeeCommon().getDescription(),
												String.valueOf(totalAmount.setScale(0, BigDecimal.ROUND_HALF_UP)));
									}
									
									if(BpaConstants.F.equals(mostRestrictiveFarHelper.getType().getCode()))
										totalGstApplicable=totalGstApplicable.add(totalAmount);
									
								} else if (BpaConstants.ADDITIONAL_COVERAGE_FEE
										.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())) {
									BigDecimal addCovExtraArea = BigDecimal.ZERO;
									if (null != lpRecifiedAreas) {
										for (LetterToPartyFees lprArea : lpRecifiedAreas) {
											if (lprArea.getFeeName()
													.equalsIgnoreCase(bpaFee.getBpaFeeCommon().getDescription())
													&& null != lprArea.getFloorarea()
													&& lprArea.getFloorarea().compareTo(BigDecimal.ZERO) > 0) {
												addCovExtraArea = addCovExtraArea.add(lprArea.getFloorarea());
											}
										}
									}
									BigDecimal totalAmount = BigDecimal.ZERO;
									if (plan.getIsAdditionalFeeApplicable()) {
										totalAmount = totalAmount.add(
												permitFeeCalculationService.getTotalAmountForAdditionalCoverage(plan,
														application.getBuildingDetail(), lpRecifiedAreas,
														Boolean.TRUE));
									} else {
										totalAmount = totalAmount.add(
												permitFeeCalculationService.getTotalAmountForAdditionalCoverage(plan,
														application.getBuildingDetail(), lpRecifiedAreas,
														Boolean.FALSE));
									}
									if (totalAmount.compareTo(BigDecimal.ZERO) >= 0) {
										fees.put(bpaFee.getBpaFeeCommon().getDescription(),
												String.valueOf(totalAmount.setScale(0, BigDecimal.ROUND_HALF_UP)));
									}
									if(BpaConstants.F.equals(mostRestrictiveFarHelper.getType().getCode()))
										totalGstApplicable=totalGstApplicable.add(totalAmount);
								}
							}
						}
					}
					
					//GST calculation
					if (totalGstApplicable.compareTo(BigDecimal.ZERO) > 0) {
						BigDecimal gstAmount = permitFeeCalculationService
								.getTotalAmountOfGST(totalGstApplicable);
						fees.put(bpaGST.getBpaFeeCommon().getDescription(),
								String.valueOf(gstAmount.setScale(0, BigDecimal.ROUND_HALF_UP)));
					}
					
				}		
			}
		}

		return fees;
	}

	public Boolean checkIsWorkAlreadyStarted(final BpaApplication application) {
		if (application.getSiteDetail() != null && application.getSiteDetail().size() > 0 &&application.getSiteDetail().get(0) != null)
			return application.getSiteDetail().get(0).getIsappForRegularization();
		return false;
	}

}
