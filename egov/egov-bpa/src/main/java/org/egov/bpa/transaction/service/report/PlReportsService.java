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


import static org.egov.bpa.utils.BpaConstants.ADDING_OF_EXTENSION;
import static org.egov.bpa.utils.BpaConstants.ALTERATION;
import static org.egov.bpa.utils.BpaConstants.AMENITIES;
import static org.egov.bpa.utils.BpaConstants.CHANGE_IN_OCCUPANCY;
import static org.egov.bpa.utils.BpaConstants.DEMOLITION;
import static org.egov.bpa.utils.BpaConstants.DIVISION_OF_PLOT;
import static org.egov.bpa.utils.BpaConstants.NEW_CONSTRUCTION;
import static org.egov.bpa.utils.BpaConstants.PERM_FOR_HUT_OR_SHED;
import static org.egov.bpa.utils.BpaConstants.POLE_STRUCTURES;
import static org.egov.bpa.utils.BpaConstants.RECONSTRUCTION;
import static org.egov.bpa.utils.BpaConstants.TOWER_CONSTRUCTION;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationReport;
import org.egov.bpa.transaction.entity.dto.SearchPendingItemsForm;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.repository.pl.PlinthLevelCertificateRepository;
import org.egov.bpa.transaction.repository.specs.SearchPlApplnFormSpec;
import org.egov.bpa.transaction.service.WorkflowHistoryService;
import org.egov.bpa.transaction.service.oc.SearchOcSpec;
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
    public static final String WF_ACTION_END = "END";
    public static final Long BTK_APPTYPE = 3L;
    public static final Long ATK_APPTYPE = 5L;
    public static final Long RURAL_APPTYPE = 4L;
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
	        List<PlinthLevelCertificate> plApplications = plinthLevelCertificateRepository.findAll(SearchPlApplnFormSpec.searchSpecificationForPlPendingItems(searchRequest));
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
	        searchResults = searchResults.stream().filter(plApplication->!plApplication.getPendingAction().equalsIgnoreCase(WF_ACTION_END)).collect(Collectors.toList());
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
  	        searchResults = searchResults.stream().filter(plApplication->!plApplication.getPendingAction().equalsIgnoreCase(WF_ACTION_END)).collect(Collectors.toList());
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
    searchResults = searchResults.stream().filter(plApplication->!plApplication.getPendingAction().equalsIgnoreCase(WF_ACTION_END)).collect(Collectors.toList());
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
    searchResults = searchResults.stream().filter(plApplication->!plApplication.getPendingAction().equalsIgnoreCase(WF_ACTION_END)).collect(Collectors.toList());
    return new PageImpl<>(searchResults, pageable, plApplications.getTotalElements());
}

public List<SearchBpaApplicationForm> search(SearchBpaApplicationForm searchPLApplicationForm) {
    List<PlinthLevelCertificate> plCertificate = plinthLevelCertificateRepository.findAll(SearchPlApplnFormSpec.search(searchPLApplicationForm));
    List<SearchBpaApplicationForm> searchResults = new ArrayList<>();
    for (PlinthLevelCertificate application : plCertificate) {
    	String pendingAction = application.getState()== null ? "N/A" : application.getState().getNextAction();
    	searchResults.add(
                new SearchBpaApplicationForm(application, getProcessOwner(application), pendingAction));
    }
    return searchResults;
}

private String getProcessOwner(PlinthLevelCertificate plinthLevelCertificate) {
    String processOwner;
    if (plinthLevelCertificate.getState() != null && plinthLevelCertificate.getState().getOwnerPosition() != null)
        processOwner = workflowHistoryService
                .getUserPositionByPositionAndDate(plinthLevelCertificate.getState().getOwnerPosition().getId(),
                		plinthLevelCertificate.getState().getLastModifiedDate())
                .getName();
    else
        processOwner = plinthLevelCertificate.getLastModifiedBy().getName();
    return processOwner;
}

 public List<SearchBpaApplicationReport> getResultsByServicetypeAndStatus(
            final SearchBpaApplicationForm searchPLApplicationForm) {
        List<SearchBpaApplicationReport> searchOcApplicationReportList = new ArrayList<>();
        List<SearchBpaApplicationForm> searchPLApplnResultList = search(searchPLApplicationForm);
        Map<String, Map<String, Long>> resultMap = searchPLApplnResultList.stream()
                .collect(Collectors.groupingBy(SearchBpaApplicationForm::getStatus,
                        Collectors.groupingBy(SearchBpaApplicationForm::getServiceType, Collectors.counting())));
        for (final Entry<String, Map<String, Long>> statusCountResMap : resultMap.entrySet()) {
            Long newConstruction = 0l;
            Long demolition = 0l;
            Long reConstruction = 0l;
            Long alteration = 0l;
            Long divisionOfPlot = 0l;
            Long addingExtension = 0l;
            Long changeInOccupancy = 0l;
            Long amenities = 0l;
            Long hut = 0l;
            Long towerConstruction = 0l;
            Long poleStructure = 0l;
            SearchBpaApplicationReport ocApplicationReport = new SearchBpaApplicationReport();
            ocApplicationReport.setStatus(statusCountResMap.getKey());
            for (final Entry<String, Long> statusCountMap : statusCountResMap.getValue().entrySet()) {
                if (NEW_CONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    newConstruction = newConstruction + statusCountMap.getValue();
                    ocApplicationReport.setServiceType01(newConstruction);
                } else if (DEMOLITION.equalsIgnoreCase(statusCountMap.getKey())) {
                    demolition = demolition + statusCountMap.getValue();
                    ocApplicationReport.setServiceType02(demolition);
                } else if (RECONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    reConstruction = reConstruction + statusCountMap.getValue();
                    ocApplicationReport.setServiceType03(reConstruction);
                } else if (ALTERATION.equalsIgnoreCase(statusCountMap.getKey())) {
                    alteration = alteration + statusCountMap.getValue();
                    ocApplicationReport.setServiceType04(alteration);
                } else if (DIVISION_OF_PLOT.equalsIgnoreCase(statusCountMap.getKey())) {
                    divisionOfPlot = divisionOfPlot + statusCountMap.getValue();
                    ocApplicationReport.setServiceType05(divisionOfPlot);
                } else if (ADDING_OF_EXTENSION.equalsIgnoreCase(statusCountMap.getKey())) {
                    addingExtension = addingExtension + statusCountMap.getValue();
                    ocApplicationReport.setServiceType06(addingExtension);
                } else if (CHANGE_IN_OCCUPANCY.equalsIgnoreCase(statusCountMap.getKey())) {
                    changeInOccupancy = changeInOccupancy + statusCountMap.getValue();
                    ocApplicationReport.setServiceType07(changeInOccupancy);
                } else if (AMENITIES.equalsIgnoreCase(statusCountMap.getKey())) {
                    amenities = amenities + statusCountMap.getValue();
                    ocApplicationReport.setServiceType08(amenities);
                } else if (PERM_FOR_HUT_OR_SHED.equalsIgnoreCase(statusCountMap.getKey())) {
                    hut = hut + statusCountMap.getValue();
                    ocApplicationReport.setServiceType09(hut);
                } else if (TOWER_CONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    towerConstruction = towerConstruction + statusCountMap.getValue();
                    ocApplicationReport.setServiceType14(towerConstruction);
                } else if (POLE_STRUCTURES.equalsIgnoreCase(statusCountMap.getKey())) {
                    poleStructure = poleStructure + statusCountMap.getValue();
                    ocApplicationReport.setServiceType15(poleStructure);
                }
            }
            searchOcApplicationReportList.add(ocApplicationReport);
        }
        return searchOcApplicationReportList;
    }
    
    
    public List<SearchBpaApplicationReport> getResultsByServicetypeAndStatusForUrban(
            final SearchBpaApplicationForm searchPLApplicationForm) {
    	 List<SearchBpaApplicationReport> searchPLApplicationReportList = new ArrayList<>();
    	 List<SearchBpaApplicationForm> searchPLApplnResultList = new ArrayList<>();
        if(searchPLApplicationForm.getApplicationTypeId()==null) {
        	searchPLApplicationForm.setApplicationTypeId(BTK_APPTYPE);
        	searchPLApplnResultList = searchForServicewiseStatus(searchPLApplicationForm);
        	searchPLApplicationForm.setApplicationTypeId(ATK_APPTYPE);
        	List<SearchBpaApplicationForm> searchATKApplnResultList = searchForServicewiseStatus(searchPLApplicationForm);
        	searchPLApplnResultList.addAll(searchATKApplnResultList);
        }else {
        	searchPLApplnResultList = searchForServicewiseStatus(searchPLApplicationForm);
        }
       
        Map<String, Map<String, Long>> resultMap = searchPLApplnResultList.stream()
                .collect(Collectors.groupingBy(SearchBpaApplicationForm::getStatus,
                        Collectors.groupingBy(SearchBpaApplicationForm::getServiceType, Collectors.counting())));
        for (final Entry<String, Map<String, Long>> statusCountResMap : resultMap.entrySet()) {
            Long newConstruction = 0l;
            Long demolition = 0l;
            Long reConstruction = 0l;
            Long alteration = 0l;
            Long divisionOfPlot = 0l;
            Long addingExtension = 0l;
            Long changeInOccupancy = 0l;
            Long amenities = 0l;
            Long hut = 0l;
            Long towerConstruction = 0l;
            Long poleStructure = 0l;
            SearchBpaApplicationReport plApplicationReport = new SearchBpaApplicationReport();
            plApplicationReport.setStatus(statusCountResMap.getKey());
            for (final Entry<String, Long> statusCountMap : statusCountResMap.getValue().entrySet()) {
                if (NEW_CONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    newConstruction = newConstruction + statusCountMap.getValue();
                    plApplicationReport.setServiceType01(newConstruction);
                } else if (DEMOLITION.equalsIgnoreCase(statusCountMap.getKey())) {
                    demolition = demolition + statusCountMap.getValue();
                    plApplicationReport.setServiceType02(demolition);
                } else if (RECONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    reConstruction = reConstruction + statusCountMap.getValue();
                    plApplicationReport.setServiceType03(reConstruction);
                } else if (ALTERATION.equalsIgnoreCase(statusCountMap.getKey())) {
                    alteration = alteration + statusCountMap.getValue();
                    plApplicationReport.setServiceType04(alteration);
                } else if (DIVISION_OF_PLOT.equalsIgnoreCase(statusCountMap.getKey())) {
                    divisionOfPlot = divisionOfPlot + statusCountMap.getValue();
                    plApplicationReport.setServiceType05(divisionOfPlot);
                } else if (ADDING_OF_EXTENSION.equalsIgnoreCase(statusCountMap.getKey())) {
                    addingExtension = addingExtension + statusCountMap.getValue();
                    plApplicationReport.setServiceType06(addingExtension);
                } else if (CHANGE_IN_OCCUPANCY.equalsIgnoreCase(statusCountMap.getKey())) {
                    changeInOccupancy = changeInOccupancy + statusCountMap.getValue();
                    plApplicationReport.setServiceType07(changeInOccupancy);
                } else if (AMENITIES.equalsIgnoreCase(statusCountMap.getKey())) {
                    amenities = amenities + statusCountMap.getValue();
                    plApplicationReport.setServiceType08(amenities);
                } else if (PERM_FOR_HUT_OR_SHED.equalsIgnoreCase(statusCountMap.getKey())) {
                    hut = hut + statusCountMap.getValue();
                    plApplicationReport.setServiceType09(hut);
                } else if (TOWER_CONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    towerConstruction = towerConstruction + statusCountMap.getValue();
                    plApplicationReport.setServiceType14(towerConstruction);
                } else if (POLE_STRUCTURES.equalsIgnoreCase(statusCountMap.getKey())) {
                    poleStructure = poleStructure + statusCountMap.getValue();
                    plApplicationReport.setServiceType15(poleStructure);
                }
            }
            searchPLApplicationReportList.add(plApplicationReport);
        }
        return searchPLApplicationReportList;
    }
    
    public List<SearchBpaApplicationReport> getResultsByServicetypeAndStatusForRural(
            final SearchBpaApplicationForm searchPLApplicationForm) {
    	 List<SearchBpaApplicationReport> searchPLApplicationReportList = new ArrayList<>();
    	 List<SearchBpaApplicationForm> searchPLApplnResultList = new ArrayList<>();
        if(searchPLApplicationForm.getApplicationTypeId()==null) {
        	searchPLApplicationForm.setApplicationTypeId(RURAL_APPTYPE);
        }
        	searchPLApplnResultList = searchForServicewiseStatus(searchPLApplicationForm);
       
        Map<String, Map<String, Long>> resultMap = searchPLApplnResultList.stream()
                .collect(Collectors.groupingBy(SearchBpaApplicationForm::getStatus,
                        Collectors.groupingBy(SearchBpaApplicationForm::getServiceType, Collectors.counting())));
        for (final Entry<String, Map<String, Long>> statusCountResMap : resultMap.entrySet()) {
            Long newConstruction = 0l;
            Long demolition = 0l;
            Long reConstruction = 0l;
            Long alteration = 0l;
            Long divisionOfPlot = 0l;
            Long addingExtension = 0l;
            Long changeInOccupancy = 0l;
            Long amenities = 0l;
            Long hut = 0l;
            Long towerConstruction = 0l;
            Long poleStructure = 0l;
            SearchBpaApplicationReport plApplicationReport = new SearchBpaApplicationReport();
            plApplicationReport.setStatus(statusCountResMap.getKey());
            for (final Entry<String, Long> statusCountMap : statusCountResMap.getValue().entrySet()) {
                if (NEW_CONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    newConstruction = newConstruction + statusCountMap.getValue();
                    plApplicationReport.setServiceType01(newConstruction);
                } else if (DEMOLITION.equalsIgnoreCase(statusCountMap.getKey())) {
                    demolition = demolition + statusCountMap.getValue();
                    plApplicationReport.setServiceType02(demolition);
                } else if (RECONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    reConstruction = reConstruction + statusCountMap.getValue();
                    plApplicationReport.setServiceType03(reConstruction);
                } else if (ALTERATION.equalsIgnoreCase(statusCountMap.getKey())) {
                    alteration = alteration + statusCountMap.getValue();
                    plApplicationReport.setServiceType04(alteration);
                } else if (DIVISION_OF_PLOT.equalsIgnoreCase(statusCountMap.getKey())) {
                    divisionOfPlot = divisionOfPlot + statusCountMap.getValue();
                    plApplicationReport.setServiceType05(divisionOfPlot);
                } else if (ADDING_OF_EXTENSION.equalsIgnoreCase(statusCountMap.getKey())) {
                    addingExtension = addingExtension + statusCountMap.getValue();
                    plApplicationReport.setServiceType06(addingExtension);
                } else if (CHANGE_IN_OCCUPANCY.equalsIgnoreCase(statusCountMap.getKey())) {
                    changeInOccupancy = changeInOccupancy + statusCountMap.getValue();
                    plApplicationReport.setServiceType07(changeInOccupancy);
                } else if (AMENITIES.equalsIgnoreCase(statusCountMap.getKey())) {
                    amenities = amenities + statusCountMap.getValue();
                    plApplicationReport.setServiceType08(amenities);
                } else if (PERM_FOR_HUT_OR_SHED.equalsIgnoreCase(statusCountMap.getKey())) {
                    hut = hut + statusCountMap.getValue();
                    plApplicationReport.setServiceType09(hut);
                } else if (TOWER_CONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    towerConstruction = towerConstruction + statusCountMap.getValue();
                    plApplicationReport.setServiceType14(towerConstruction);
                } else if (POLE_STRUCTURES.equalsIgnoreCase(statusCountMap.getKey())) {
                    poleStructure = poleStructure + statusCountMap.getValue();
                    plApplicationReport.setServiceType15(poleStructure);
                }
            }
            searchPLApplicationReportList.add(plApplicationReport);
        }
        return searchPLApplicationReportList;
    }
    
    public List<SearchBpaApplicationForm> searchForServicewiseStatus(SearchBpaApplicationForm searchRequest) {
		 List<PlinthLevelCertificate> plCertificate = plinthLevelCertificateRepository.findAll(SearchPlApplnFormSpec.search(searchRequest));
	        List<SearchBpaApplicationForm> SearchPendingItemsFormList = new ArrayList<>();
	        for (PlinthLevelCertificate application : plCertificate) {
	        	String pendingAction = application.getState()== null ? "N/A" : application.getState().getNextAction();
	        	SearchPendingItemsFormList.add(
	                    new SearchBpaApplicationForm(application, getProcessOwner(application), pendingAction));
	        }
	        return SearchPendingItemsFormList;
	}
}
    

    