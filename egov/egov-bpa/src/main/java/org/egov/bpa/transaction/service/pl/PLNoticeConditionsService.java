package org.egov.bpa.transaction.service.pl;

import java.util.List;

import org.egov.bpa.transaction.entity.enums.ConditionType;
import org.egov.bpa.transaction.entity.pl.PLNoticeConditions;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.repository.pl.PLNoticeConditionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PLNoticeConditionsService {
	@Autowired
	private PLNoticeConditionsRepository plNoticeConditionsRepository;
	
	public List<PLNoticeConditions> findAllPlConditionsByType(ConditionType type) {
		return plNoticeConditionsRepository.findByTypeOrderByOrderNumberAsc(type);
	}

	public List<PLNoticeConditions> findAllPlConditionsByPlAndType(PlinthLevelCertificate pl, ConditionType type) {
		return plNoticeConditionsRepository.findByPlAndTypeOrderByOrderNumberAsc(pl, type);
	}

	@Transactional
	public void delete(List<PLNoticeConditions> plNoticeConditions) {
		plNoticeConditionsRepository.deleteInBatch(plNoticeConditions);
	}
}
