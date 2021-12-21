package org.egov.bpa.transaction.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.egov.bpa.model.LetterToPartyFees;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.LetterToPartyFee;
import org.egov.bpa.transaction.entity.LetterToPartyFeeDetails;
import org.egov.bpa.transaction.entity.LetterToPartyFeeMaster;
import org.egov.bpa.transaction.entity.PermitLetterToParty;
import org.egov.bpa.transaction.repository.LetterToPartyFeeMasterRepository;
import org.egov.bpa.transaction.repository.LetterToPartyFeeRepository;
import org.egov.bpa.transaction.repository.LettertoPartyRepository;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.transaction.repository.LetterToPartyFeeDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LettertoPartyFeeService {
	private static final Logger LOG = LoggerFactory.getLogger(LettertoPartyFeeService.class);
	
	private final LetterToPartyFeeMasterRepository letterToPartyFeeMasterRepository;
	private final LetterToPartyFeeRepository letterToPartyFeeRepository;
	private final LetterToPartyFeeDetailsRepository letterToPartyFeeDetailsRepository;
	
	@Autowired
    public LettertoPartyFeeService(final LetterToPartyFeeMasterRepository letterToPartyFeeMasterRepository,
    							   final LetterToPartyFeeRepository letterToPartyFeeRepository,
    							   final LetterToPartyFeeDetailsRepository letterToPartyFeeDetailsRepository) {
        this.letterToPartyFeeMasterRepository = letterToPartyFeeMasterRepository;
        this.letterToPartyFeeRepository = letterToPartyFeeRepository;
        this.letterToPartyFeeDetailsRepository = letterToPartyFeeDetailsRepository;
    }
	
	public List<LetterToPartyFeeMaster> getActiveLPFees(String areaCategory) {
//        return letterToPartyFeeMasterRepository.findByIsActiveTrueOrderByIdAsc();
		List<LetterToPartyFeeMaster> letterToPartyFeeMasters=new ArrayList<LetterToPartyFeeMaster>();
		for(LetterToPartyFeeMaster letterToPartyFeeMaster:letterToPartyFeeMasterRepository.findByIsActiveTrueOrderByIdAsc()) {
			if(BpaConstants.RURAL.equals(areaCategory) && BpaConstants.LP_RURAL_FEES.contains(letterToPartyFeeMaster.getFeeName())) {
				letterToPartyFeeMasters.add(letterToPartyFeeMaster);
			}else if(BpaConstants.URBAN.equals(areaCategory) && !BpaConstants.LP_RURAL_FEES.contains(letterToPartyFeeMaster.getFeeName())){
				letterToPartyFeeMasters.add(letterToPartyFeeMaster);
			}
		}
		return letterToPartyFeeMasters;
    }
	
	public LetterToPartyFeeMaster getLPFeeMasterById(Long id) {
        return letterToPartyFeeMasterRepository.findOne(id);
    }
	
	public LetterToPartyFeeDetails getLPFeeDetailsById(Long id) {
        return letterToPartyFeeDetailsRepository.findOne(id);
    }
	
	public List<LetterToPartyFee> getLPFeesByApplication(BpaApplication application) {
        return letterToPartyFeeRepository.findByApplicationOrderByIdDesc(application);
    }
	
	public List<LetterToPartyFee> getLPFeesByLetterToParty(PermitLetterToParty permitLetterToParty) {
        return letterToPartyFeeRepository.findByPermitLetterToPartyOrderByIdDesc(permitLetterToParty);
    }
	
	public LetterToPartyFee getLPFeesByLP(PermitLetterToParty permitLetterToParty) {
		List<LetterToPartyFee> letterToPartyFee = letterToPartyFeeRepository.findByPermitLetterToPartyOrderByIdDesc(permitLetterToParty);
		if(null!=letterToPartyFee && !letterToPartyFee.isEmpty()) {
			return letterToPartyFee.get(0);
		}
		return null;
    }
	
	public List<LetterToPartyFeeDetails> getLPFeeDetailsByLetterToParty(PermitLetterToParty permitLetterToParty) {
		List<LetterToPartyFee> letterToPartyFee = letterToPartyFeeRepository.findByPermitLetterToPartyOrderByIdDesc(permitLetterToParty);
		if(null!=letterToPartyFee && !letterToPartyFee.isEmpty()) {
			return letterToPartyFee.get(0).getLetterToPartyFeeDetails();
		}
		return null;
    }
	
	public List<LetterToPartyFeeDetails> getLPFeeDetailsByApplication(BpaApplication bpaApplication) {
		List<LetterToPartyFee> letterToPartyFee = letterToPartyFeeRepository.findByApplicationOrderByIdDesc(bpaApplication);
		if(null!=letterToPartyFee && !letterToPartyFee.isEmpty()) {
			return letterToPartyFee.get(0).getLetterToPartyFeeDetails();
		}
		return null;
    }
	
	public List<LetterToPartyFeeDetails> getLPFeeDetailsByLetterToPartyFee(LetterToPartyFee letterToPartyFee) {
        return letterToPartyFeeDetailsRepository.findByLetterToPartyFeeOrderByIdDesc(letterToPartyFee);
    }
	
	public List<LetterToPartyFees> getLPFees(BpaApplication application,String areaCategory) {
		List<LetterToPartyFees> letterToPartyFeeList = new ArrayList<LetterToPartyFees>();
		List<LetterToPartyFeeMaster> letterToPartyFeeMasters = getActiveLPFees(areaCategory);
		List<LetterToPartyFee> letterToPartyFees = getLPFeesByApplication(application);
		for(LetterToPartyFeeMaster letterToPartyFeeMaster:letterToPartyFeeMasters) {
			LetterToPartyFees lpFees = new LetterToPartyFees();
			lpFees.setApplicationId(application.getId());
			lpFees.setFeeMstrId(letterToPartyFeeMaster.getId());
			lpFees.setFeeName(letterToPartyFeeMaster.getFeeName());
			lpFees.setFloorNumber(letterToPartyFeeMaster.getFloorNumber());
			lpFees.setIsActive(letterToPartyFeeMaster.getIsActive());			
			if(null!=letterToPartyFees && !letterToPartyFees.isEmpty()) {
				if(null!=letterToPartyFees.get(0).getLetterToPartyFeeDetails()) {
					lpFees.setLpFeeId(letterToPartyFees.get(0).getId());
					for(LetterToPartyFeeDetails letterToPartyFeeDetails:letterToPartyFees.get(0).getLetterToPartyFeeDetails()) {
						if(letterToPartyFeeDetails.getLetterToPartyFeeMaster().getId() == letterToPartyFeeMaster.getId()) {
							lpFees.setLpFeeDetailsId(letterToPartyFeeDetails.getId());
							lpFees.setFloorarea((null!=letterToPartyFeeDetails.getFloorarea())?letterToPartyFeeDetails.getFloorarea():BigDecimal.ZERO);
							lpFees.setRemarks(letterToPartyFeeDetails.getRemarks());
							lpFees.setIsMandatory(letterToPartyFeeDetails.getIsMandatory());
						}
					}
				}
			}			
			letterToPartyFeeList.add(lpFees);
		}		
        return letterToPartyFeeList;
    }
	
	@Transactional
    public LetterToPartyFee save(final LetterToPartyFee letterToPartyFee) {
        if (null!=letterToPartyFee) {
        	if(null!=letterToPartyFee.getLetterToPartyFeeDetails() && !letterToPartyFee.getLetterToPartyFeeDetails().isEmpty()) {
        		letterToPartyFeeRepository.save(letterToPartyFee);
        	}        	
        }
        return null;
    }
	
	@Transactional
    public void saveFeeDetails(final List<LetterToPartyFeeDetails> letterToPartyFeeDetails) {
        if (null!=letterToPartyFeeDetails && !letterToPartyFeeDetails.isEmpty()) {
        	letterToPartyFeeDetailsRepository.save(letterToPartyFeeDetails);        	
        }
    }
}
