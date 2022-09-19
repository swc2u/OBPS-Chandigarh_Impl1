/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
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
 */
package org.egov.bpa.transaction.service.report;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.egov.bpa.transaction.entity.dto.SearchPendingItemsForm;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.repository.pl.PlinthLevelCertificateRepository;
import org.egov.bpa.transaction.repository.specs.SearchPlApplnFormSpec;
import org.egov.bpa.transaction.service.WorkflowHistoryService;
import org.egov.infra.config.persistence.datasource.routing.annotation.ReadOnly;
import org.egov.infra.utils.DateUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PlReportsService {

    public static final String SECTION_CLERK = "SECTION CLERK";
    public static final Long BTK_APPTYPE = 3L;
    public static final Long ATK_APPTYPE = 5L;
    @Autowired
	private PlinthLevelCertificateRepository plinthLevelCertificateRepository;
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private WorkflowHistoryService workflowHistoryService;
    

    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    @ReadOnly
    public Page<SearchPendingItemsForm> pagedSearchForPLItemGraph(SearchPendingItemsForm searchRequest) {

       final Pageable pageable = new PageRequest(searchRequest.pageNumber(), searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());

        //Page<BpaApplication> bpaApplications = applicationBpaRepository.findAll(SearchBpaApplnFormSpec.searchSpecificationForPendingItems(searchRequest), pageable);
        List<PlinthLevelCertificate> plApplications = plinthLevelCertificateRepository.findAll(SearchPlApplnFormSpec.searchSpecificationForPlItems(searchRequest));
        List<SearchPendingItemsForm> searchResults = new ArrayList<>();
        for (PlinthLevelCertificate application : plApplications) {
        	if(null!=application.getState()) {
        		Date dateInfo = application.getState().getDateInfo();
        		if(null!=application.getState()) {
        			int days = DateUtils.daysBetween(dateInfo, new Date());
        			if(days>=0) {
	            		String pendingAction = application.getState().getNextAction();
	            		Map<String,String> map = getCurrentOwner(application);
	            		if(!StringUtils.isEmpty(searchRequest.getCurrentOwnerDesg())) {
	            			if(null!=map.get("designation") && searchRequest.getCurrentOwnerDesg().equalsIgnoreCase(map.get("designation"))) {
	            				searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
	            			}
	            		}else {
	            			searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
	            		}
        			}
        		}       		
        	}
        }
        return new PageImpl<>(searchResults, pageable, plApplications.size());
    }
    
    @ReadOnly
    public Page<SearchPendingItemsForm> pagedSearchForPLItemGraphRural(SearchPendingItemsForm searchRequest) {

       final Pageable pageable = new PageRequest(searchRequest.pageNumber(), searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());

        //Page<BpaApplication> bpaApplications = applicationBpaRepository.findAll(SearchBpaApplnFormSpec.searchSpecificationForPendingItems(searchRequest), pageable);
        List<PlinthLevelCertificate> plApplications = plinthLevelCertificateRepository.findAll(SearchPlApplnFormSpec.searchSpecificationForPlItemsRural(searchRequest));
        List<SearchPendingItemsForm> searchResults = new ArrayList<>();
        for (PlinthLevelCertificate application : plApplications) {
        	if(null!=application.getState()) {
        		Date dateInfo = application.getState().getDateInfo();
        		if(null!=application.getState()) {
        			int days = DateUtils.daysBetween(dateInfo, new Date());
        			if(days>=0) {
	            		String pendingAction = application.getState().getNextAction();
	            		Map<String,String> map = getCurrentOwner(application);
	            		if(!StringUtils.isEmpty(searchRequest.getCurrentOwnerDesg())) {
	            			if(null!=map.get("designation") && searchRequest.getCurrentOwnerDesg().equalsIgnoreCase(map.get("designation"))) {
	            				searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
	            			}
	            		}else {
	            			searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
	            		}
        			}
        		}       		
        	}
        }
        return new PageImpl<>(searchResults, pageable, plApplications.size());
    }
    
    private Map<String, String> getCurrentOwner(PlinthLevelCertificate application) {
    	Map<String, String> map;
        if (application.getState() != null && application.getState().getOwnerPosition() != null) {
        	map = workflowHistoryService.getUserDesignationAndPositionByPositionAndDate(application.getState().getOwnerPosition().getId(),application.getState().getLastModifiedDate());
        } else {
        	map = new HashMap<String, String>();
        	map.put("designation", "");
        	map.put("name", application.getLastModifiedBy().getName());
        }
        return map;
    }

    @ReadOnly
    public Page<SearchPendingItemsForm> pagedSearchForUrbanPLItem(SearchPendingItemsForm searchRequest) {

        final Pageable pageable = new PageRequest(searchRequest.pageNumber(), searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());
        Page<PlinthLevelCertificate> plApplications = plinthLevelCertificateRepository.findAll(SearchPlApplnFormSpec.searchSpecificationForPlItems(searchRequest), pageable);

        List<SearchPendingItemsForm> searchResults = new ArrayList<>();
        for (PlinthLevelCertificate application : plApplications) {
        	if(null!=application.getState()) {
        		Date dateInfo = application.getState().getDateInfo();
        		if(null!=application.getState()) {
        			int days = DateUtils.daysBetween(dateInfo, new Date());
        			if(days>=0) {
	            		String pendingAction = application.getState().getNextAction();
	            		Map<String,String> map = getCurrentOwner(application);
	            		if(!StringUtils.isEmpty(searchRequest.getCurrentOwnerDesg())) {
	            			if(null!=map.get("designation") && searchRequest.getCurrentOwnerDesg().equalsIgnoreCase(map.get("designation"))) {
	            				searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
	            			}
	            		}else {
	            			searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
	            		}
        			}
        		}       		
        	}
        }
        return new PageImpl<>(searchResults, pageable, plApplications.getTotalElements());
    }
    
    @ReadOnly
    public Page<SearchPendingItemsForm> pagedSearchForRuralPLItem(SearchPendingItemsForm searchRequest) {

        final Pageable pageable = new PageRequest(searchRequest.pageNumber(), searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());
        Page<PlinthLevelCertificate> plApplications = plinthLevelCertificateRepository.findAll(SearchPlApplnFormSpec.searchSpecificationForPlItems(searchRequest), pageable);

        List<SearchPendingItemsForm> searchResults = new ArrayList<>();
        for (PlinthLevelCertificate application : plApplications) {
        	if(null!=application.getState()) {
        		Date dateInfo = application.getState().getDateInfo();
        		if(null!=application.getState()) {
        			int days = DateUtils.daysBetween(dateInfo, new Date());
        			if(days>=0) {
	            		String pendingAction = application.getState().getNextAction();
	            		Map<String,String> map = getCurrentOwner(application);
	            		if(!StringUtils.isEmpty(searchRequest.getCurrentOwnerDesg())) {
	            			if(null!=map.get("designation") && searchRequest.getCurrentOwnerDesg().equalsIgnoreCase(map.get("designation"))) {
	            				searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
	            			}
	            		}else {
	            			searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
	            		}
        			}
        		}       		
        	}
        }
        return new PageImpl<>(searchResults, pageable, plApplications.getTotalElements());
    }
    
    @ReadOnly
	public Page<SearchPendingItemsForm> pagedSearchForPLPendingItemGraph(
			SearchPendingItemsForm searchRequest) {
		  final Pageable pageable = new PageRequest(searchRequest.pageNumber(), searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());

	        //Page<BpaApplication> bpaApplications = applicationBpaRepository.findAll(SearchBpaApplnFormSpec.searchSpecificationForPendingItems(searchRequest), pageable);
	        List<PlinthLevelCertificate> plApplications = plinthLevelCertificateRepository.findAll(SearchPlApplnFormSpec.searchSpecificationForPlPendingItemsRural(searchRequest));
	        List<SearchPendingItemsForm> searchResults = new ArrayList<>();
	        for (PlinthLevelCertificate application : plApplications) {
	        	if(null!=application.getState()) {
	        		Date dateInfo = application.getState().getDateInfo();
	        		if(null!=application.getState()) {
	        			int days = DateUtils.daysBetween(dateInfo, new Date());
	        			if(days>=0) {
		            		String pendingAction = application.getState().getNextAction();
		            		Map<String,String> map = getCurrentOwner(application);
		            		if(!StringUtils.isEmpty(searchRequest.getCurrentOwnerDesg())) {
		            			if(null!=map.get("designation") && searchRequest.getCurrentOwnerDesg().equalsIgnoreCase(map.get("designation"))) {
		            				searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
		            			}
		            		}else {
		            			searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
		            		}
	        			}
	        		}       		
	        	}
	        }
	        return new PageImpl<>(searchResults, pageable, plApplications.size());
	}
    
    @ReadOnly
  	public Page<SearchPendingItemsForm> pagedSearchForPLPendingItemGraphRural(
  			SearchPendingItemsForm searchRequest) {
  		  final Pageable pageable = new PageRequest(searchRequest.pageNumber(), searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());

  	        //Page<BpaApplication> bpaApplications = applicationBpaRepository.findAll(SearchBpaApplnFormSpec.searchSpecificationForPendingItems(searchRequest), pageable);
  	        List<PlinthLevelCertificate> plApplications = plinthLevelCertificateRepository.findAll(SearchPlApplnFormSpec.searchSpecificationForPlPendingItemsRural(searchRequest));
  	        List<SearchPendingItemsForm> searchResults = new ArrayList<>();
  	        for (PlinthLevelCertificate application : plApplications) {
  	        	if(null!=application.getState()) {
  	        		Date dateInfo = application.getState().getDateInfo();
  	        		if(null!=application.getState()) {
  	        			int days = DateUtils.daysBetween(dateInfo, new Date());
  	        			if(days>=0) {
  		            		String pendingAction = application.getState().getNextAction();
  		            		Map<String,String> map = getCurrentOwner(application);
  		            		if(!StringUtils.isEmpty(searchRequest.getCurrentOwnerDesg())) {
  		            			if(null!=map.get("designation") && searchRequest.getCurrentOwnerDesg().equalsIgnoreCase(map.get("designation"))) {
  		            				searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
  		            			}
  		            		}else {
  		            			searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
  		            		}
  	        			}
  	        		}       		
  	        	}
  	        }
  	        return new PageImpl<>(searchResults, pageable, plApplications.size());
  	}

@ReadOnly
public Page<SearchPendingItemsForm> pagedSearchForUrbanPLPendingItem(SearchPendingItemsForm searchRequest) {

    final Pageable pageable = new PageRequest(searchRequest.pageNumber(), searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());
    Page<PlinthLevelCertificate> plApplications = plinthLevelCertificateRepository.findAll(SearchPlApplnFormSpec.searchSpecificationForPlPendingItems(searchRequest), pageable);

    List<SearchPendingItemsForm> searchResults = new ArrayList<>();
    for (PlinthLevelCertificate application : plApplications) {
    	if(null!=application.getState()) {
    		Date dateInfo = application.getState().getDateInfo();
    		if(null!=application.getState()) {
    			int days = DateUtils.daysBetween(dateInfo, new Date());
    			if(days>=0) {
            		String pendingAction = application.getState().getNextAction();
            		Map<String,String> map = getCurrentOwner(application);
            		if(!StringUtils.isEmpty(searchRequest.getCurrentOwnerDesg())) {
            			if(null!=map.get("designation") && searchRequest.getCurrentOwnerDesg().equalsIgnoreCase(map.get("designation"))) {
            				searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
            			}
            		}else {
            			searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
            		}
    			}
    		}       		
    	}
    }
    return new PageImpl<>(searchResults, pageable, plApplications.getTotalElements());
}

@ReadOnly
public Page<SearchPendingItemsForm> pagedSearchForRuralPLPendingItem(SearchPendingItemsForm searchRequest) {

    final Pageable pageable = new PageRequest(searchRequest.pageNumber(), searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());
    Page<PlinthLevelCertificate> plApplications = plinthLevelCertificateRepository.findAll(SearchPlApplnFormSpec.searchSpecificationForPlPendingItemsRural(searchRequest), pageable);

    List<SearchPendingItemsForm> searchResults = new ArrayList<>();
    for (PlinthLevelCertificate application : plApplications) {
    	if(null!=application.getState()) {
    		Date dateInfo = application.getState().getDateInfo();
    		if(null!=application.getState()) {
    			int days = DateUtils.daysBetween(dateInfo, new Date());
    			if(days>=0) {
            		String pendingAction = application.getState().getNextAction();
            		Map<String,String> map = getCurrentOwner(application);
            		if(!StringUtils.isEmpty(searchRequest.getCurrentOwnerDesg())) {
            			if(null!=map.get("designation") && searchRequest.getCurrentOwnerDesg().equalsIgnoreCase(map.get("designation"))) {
            				searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
            			}
            		}else {
            			searchResults.add(new SearchPendingItemsForm(application, map.get("name"), map.get("designation"), pendingAction, days));
            		}
    			}
    		}       		
    	}
    }
    return new PageImpl<>(searchResults, pageable, plApplications.getTotalElements());
}
}
    

    