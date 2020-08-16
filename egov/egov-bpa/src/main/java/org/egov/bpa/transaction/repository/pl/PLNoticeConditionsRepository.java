package org.egov.bpa.transaction.repository.pl;

import java.util.List;

import org.egov.bpa.transaction.entity.enums.ConditionType;
import org.egov.bpa.transaction.entity.pl.PLNoticeConditions;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PLNoticeConditionsRepository extends JpaRepository<PLNoticeConditions, Long> {
	@Query("select plnc from PLNoticeConditions plnc where plnc.noticeCondition.type = :conditionType")
	List<PLNoticeConditions> findByTypeOrderByOrderNumberAsc(@Param("conditionType") ConditionType conditionType);

	@Query("select plnc from PLNoticeConditions plnc where plnc.noticeCondition.type = :conditionType and plnc.pl = :pl ")
	List<PLNoticeConditions> findByPlAndTypeOrderByOrderNumberAsc(@Param("pl") PlinthLevelCertificate pl, @Param("conditionType") ConditionType conditionType);
}
