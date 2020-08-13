package org.egov.bpa.transaction.repository.pl;

import org.egov.bpa.transaction.entity.pl.PLNotice;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PLNoticeRepository extends JpaRepository<PLNotice, Long> {
	
	@Query("select plNotice from PLNotice plNotice"
            + " where plNotice.pl = :pl and plNotice.noticeCommon.noticeType = :noticeType ")
	PLNotice findByPlAndNoticeType(@Param("pl") PlinthLevelCertificate pl, @Param("noticeType") String noticeType);
}
