/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 *
 */

package org.egov.bpa.transaction.repository.specs;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.entity.dto.SearchPendingItemsForm;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.infra.admin.master.entity.Boundary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public final class SearchPlApplnFormSpec {

    private static final Long RURAL_APPLICATION_ID = 4L;
    private static final String PL_END_STATUS = "Order Issued to Applicant";
    private static final String CANCELLED = "Cancelled";
    private static final String REJECTED = "Rejected";
    
    private SearchPlApplnFormSpec() {
        // static methods only
    }

    private static void commonSpecForPendingItems(SearchPendingItemsForm requestForm, Root<PlinthLevelCertificate> root, CriteriaBuilder builder, Predicate predicate) {
        Join<PlinthLevelCertificate, BpaApplication> bpaApplication = root.join("parent");
      if (requestForm.getApplicationNumber() != null)
          predicate.getExpressions()
                           .add(builder.like(root.get("applicationNumber"), requestForm.getApplicationNumber()+"%"));
      if (requestForm.getApplicantName() != null)
          predicate.getExpressions()
                  .add(builder.like(bpaApplication.get("owner").get("name"),
                          requestForm.getApplicantName()+"%"));
      
      if (requestForm.getServiceTypeId() != null)
          predicate.getExpressions()
                  .add(builder.equal(bpaApplication.get("serviceType").get("id"), requestForm.getServiceTypeId()));
      if (requestForm.getServiceType() != null)
          predicate.getExpressions()
                  .add(builder.equal(bpaApplication.get("serviceType").get("description"),
                          requestForm.getServiceTypeId()));
      
      if (requestForm.getFromDate() != null)
          predicate.getExpressions()
                  .add(builder.greaterThanOrEqualTo(root.get("applicationDate"), requestForm.getFromDate()));
      if (requestForm.getToDate() != null)
          predicate.getExpressions()
                  .add(builder.lessThanOrEqualTo(root.get("applicationDate"), requestForm.getToDate()));
      
      if (requestForm.getPlotNumber() != null)
          predicate.getExpressions()
                  .add(builder.equal(bpaApplication.get("plotNumber"), requestForm.getPlotNumber()));
      
      if (requestForm.getSector() != null)
          predicate.getExpressions()
                  .add(builder.equal(bpaApplication.get("sector"), requestForm.getSector())); 
      
    }

	public static Specification<PlinthLevelCertificate> searchSpecificationForPlItems(final SearchPendingItemsForm requestForm) {
        return (root, query, builder) -> {
            final Predicate predicate = builder.conjunction();
            commonSpecForPendingItems(requestForm, root, builder, predicate);
            Join<PlinthLevelCertificate, BpaApplication> bpaApplication = root.join("parent");
            
            //to differential Urban and Rural applications
            if (requestForm.getApplicationTypeId() != null)
                predicate.getExpressions()
                        .add(builder.equal(bpaApplication.get("applicationType").get("id"), requestForm.getApplicationTypeId()));
            else
            	predicate.getExpressions()
                .add(builder.notEqual(bpaApplication.get("applicationType").get("id"), RURAL_APPLICATION_ID));
    
            if (requestForm.getStatusId() != null)
                predicate.getExpressions().add(builder.equal(root.get("status").get("id"), requestForm.getStatusId()));
            
            query.distinct(true);
            return predicate;
        };
    }
	
	public static Specification<PlinthLevelCertificate> search(final SearchBpaApplicationForm requestForm) {
        return (root, query, builder) -> {
            final Predicate predicate = builder.conjunction();
            Join<OccupancyCertificate, BpaApplication> bpaApplication = root.join("parent");
            Join<BpaApplication, SiteDetail> siteDetailJoin = bpaApplication.join("siteDetail");
            Join<SiteDetail, Boundary> adminBoundaryJoin = siteDetailJoin.join("adminBoundary");
            if (requestForm.getApplicationNumber() != null)
                predicate.getExpressions()
                                 .add(builder.equal(root.get("applicationNumber"), requestForm.getApplicationNumber()));
            if (requestForm.getApplicantName() != null)
                predicate.getExpressions()
                        .add(builder.equal(bpaApplication.get("owner").get("name"),
                                requestForm.getApplicantName()));
            if(requestForm.getStatusId() !=null)
            	predicate.getExpressions()
                .add(builder.equal(root.get("status").get("id"), requestForm.getStatusId()));
            
            if(requestForm.getStatus() !=null)
            	predicate.getExpressions()
                .add(builder.equal(root.get("status").get("description"), requestForm.getStatus()));
            
            if (requestForm.getServiceTypeId() != null)
                predicate.getExpressions()
                        .add(builder.equal(bpaApplication.get("serviceType").get("id"), requestForm.getServiceTypeId()));
            if (requestForm.getServiceType() != null)
                predicate.getExpressions()
                        .add(builder.equal(bpaApplication.get("serviceType").get("description"),
                                requestForm.getServiceType()));

            if (requestForm.getOccupancyId() != null)
                predicate.getExpressions()
                        .add(builder.equal(bpaApplication.get("occupancy").get("id"), requestForm.getOccupancyId()));
            if(requestForm.getRevenueBoundary()!=null)
                predicate.getExpressions()
                .add(builder.equal(siteDetailJoin.get("adminBoundary").get("id"), requestForm.getRevenueBoundary()));
            if(requestForm.getAdminBoundary()!=null)
                predicate.getExpressions()
                .add(builder.equal(siteDetailJoin.get("electionBoundary").get("id"), requestForm.getAdminBoundary()));
            if(requestForm.getLocationBoundary()!=null)
                predicate.getExpressions()
                .add(builder.equal(siteDetailJoin.get("locationBoundary").get("id"), requestForm.getLocationBoundary()));
            
            if (requestForm.getFromDate() != null)
                predicate.getExpressions()
                        .add(builder.greaterThanOrEqualTo(root.get("applicationDate"), requestForm.getFromDate()));
            if (requestForm.getToDate() != null)
                predicate.getExpressions()
                        .add(builder.lessThanOrEqualTo(root.get("applicationDate"), requestForm.getToDate()));
            if (requestForm.getFromBuiltUpArea() != null)
                predicate.getExpressions()
                        .add(builder.greaterThanOrEqualTo(bpaApplication.get("totalBuiltUpArea"), requestForm.getFromBuiltUpArea()));
            if (requestForm.getToBuiltUpArea() != null)
                predicate.getExpressions()
                        .add(builder.lessThanOrEqualTo(bpaApplication.get("totalBuiltUpArea"), requestForm.getToBuiltUpArea()));
            if (requestForm.getApplicationTypeId() != null)
                predicate.getExpressions()
                        .add(builder.equal(bpaApplication.get("applicationType").get("id"), requestForm.getApplicationTypeId()));
            else
            	predicate.getExpressions()
                .add(builder.notEqual(bpaApplication.get("applicationType").get("id"), RURAL_APPLICATION_ID));
            
            query.distinct(true);
            return predicate;
        };
    }
	
	public static Specification<PlinthLevelCertificate> searchSpecificationForPlPendingItems(
			SearchPendingItemsForm requestForm) {
		 return (root, query, builder) -> {
	            final Predicate predicate = builder.conjunction();
	            commonSpecForPendingItems(requestForm, root, builder, predicate);
	            Join<PlinthLevelCertificate, BpaApplication> bpaApplication = root.join("parent");
	            
	            //to differential Urban and Rural applications
	            if (requestForm.getApplicationTypeId() != null)
	                predicate.getExpressions()
	                        .add(builder.equal(bpaApplication.get("applicationType").get("id"), requestForm.getApplicationTypeId()));
	            else
	            	predicate.getExpressions()
	                .add(builder.notEqual(bpaApplication.get("applicationType").get("id"), RURAL_APPLICATION_ID));
	    
	            if (requestForm.getStatusId() != null)
	                predicate.getExpressions().add(builder.equal(root.get("status").get("id"), requestForm.getStatusId()));
	            
	            else 
	            	predicate.getExpressions().add(builder.not(root.get("status").get("code").in(PL_END_STATUS,CANCELLED,REJECTED)));
	            
	            query.distinct(true);
	            return predicate;
	        };
	}
	
	public static Specification<PlinthLevelCertificate> searchSpecificationForPlItemsRural(final SearchPendingItemsForm requestForm) {
        return (root, query, builder) -> {
            final Predicate predicate = builder.conjunction();
            commonSpecForPendingItems(requestForm, root, builder, predicate);
            Join<PlinthLevelCertificate, BpaApplication> bpaApplication = root.join("parent");
            
            //to differential Urban and Rural applications
            if (requestForm.getApplicationTypeId() != null)
                predicate.getExpressions()
                        .add(builder.equal(bpaApplication.get("applicationType").get("id"), requestForm.getApplicationTypeId()));
            else
            	predicate.getExpressions()
                .add(builder.equal(bpaApplication.get("applicationType").get("id"), RURAL_APPLICATION_ID));
    
            if (requestForm.getStatusId() != null)
                predicate.getExpressions().add(builder.equal(root.get("status").get("id"), requestForm.getStatusId()));
            
            query.distinct(true);
            return predicate;
        };
    }

	public static Specification<PlinthLevelCertificate> searchSpecificationForPlPendingItemsRural(
			SearchPendingItemsForm requestForm) {
		 return (root, query, builder) -> {
	            final Predicate predicate = builder.conjunction();
	            commonSpecForPendingItems(requestForm, root, builder, predicate);
	            Join<PlinthLevelCertificate, BpaApplication> bpaApplication = root.join("parent");
	            
	            //to differential Urban and Rural applications
	            if (requestForm.getApplicationTypeId() != null)
	                predicate.getExpressions()
	                        .add(builder.equal(bpaApplication.get("applicationType").get("id"), requestForm.getApplicationTypeId()));
	            else
	            	predicate.getExpressions()
	                .add(builder.equal(bpaApplication.get("applicationType").get("id"), RURAL_APPLICATION_ID));
	    
	            if (requestForm.getStatusId() != null)
	                predicate.getExpressions().add(builder.equal(root.get("status").get("id"), requestForm.getStatusId()));
	            
	            else 
	               	predicate.getExpressions().add(builder.not(root.get("status").get("code").in(PL_END_STATUS,CANCELLED,REJECTED)));
	               	 
	            
	            query.distinct(true);
	            return predicate;
	        };
	}

}
