package org.egov.bpa.transaction.service.pl;

import static org.egov.bpa.utils.PLConstants.PL_INSPECTION;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.egov.bpa.autonumber.InspectionNumberGenerator;
import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.master.service.ChecklistServicetypeMappingService;
import org.egov.bpa.transaction.entity.common.DocketCommon;
import org.egov.bpa.transaction.entity.common.DocketDetailCommon;
import org.egov.bpa.transaction.entity.common.InspectionCommon;
import org.egov.bpa.transaction.entity.common.InspectionFilesCommon;
import org.egov.bpa.transaction.entity.pl.PLInspection;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.repository.pl.PLInspectionRepository;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.oc.PlanScrutinyChecklistCommonService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.commons.service.CheckListTypeService;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.autonumber.AutonumberServiceBeanResolver;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;

@Service
@Transactional(readOnly = true)
public class PLInspectionService {
	private static final Logger LOGGER = Logger.getLogger(PLInspectionService.class);

	public static final List<String> CHECKLIST_TYPES = new ArrayList<String>() {

		private static final long serialVersionUID = -8357551578580968323L;

		{
			add(PL_INSPECTION);

		}
	};
	
	@Autowired
	private ChecklistServicetypeMappingService checklistServicetypeMappingService;
	@Autowired
	private AutonumberServiceBeanResolver beanResolver;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private PLInspectionRepository inspectionRepository;
	@Autowired
	private FileStoreService fileStoreService;
	@Autowired
	private SecurityUtils securityUtils;
	@Autowired
	private ApplicationBpaService applicationBpaService;
	@Autowired
	private CheckListTypeService checkListTypeService;
	
	public Session getCurrentSession() {
		return entityManager.unwrap(Session.class);
	}
	
	@Transactional
	public PLInspection save(final PLInspection plInspection) {
		InspectionCommon inspection = plInspection.getInspection();
		if (inspection.getId() == null) {
			inspection.setInspectionNumber(generateInspectionNumber());
			inspection.setInspectedBy(securityUtils.getCurrentUser());
		}
		if (inspection.getInspectionDate() == null)
			inspection.setInspectionDate(new Date());
		for (DocketCommon d : plInspection.getInspection().getDocket()) {
			d.setInspection(plInspection.getInspection());
			for (DocketDetailCommon ddc : d.getDocketDetail()) {
				ddc.setDocket(d);
			}
		}
		buildInspectionFiles(inspection);
		inspection.getDocket().get(0).setInspection(inspection);
		buildDocketDetails(plInspection.getInspection().getDocket());
		return inspectionRepository.save(plInspection);
	}
	
	public PLInspection findById(Long id) {
		return inspectionRepository.findOne(id);
	}

	public PLInspection findByPlApplicationNoAndInspectionNo(String applicationNo, String inspectionNo) {
		return inspectionRepository.findByPl_ApplicationNumberAndInspection_InspectionNumber(applicationNo, inspectionNo);
	}

	private void buildInspectionFiles(InspectionCommon inspection) {
		if (!inspection.getInspectionSupportDocs().isEmpty())
			for (InspectionFilesCommon filesCommon : inspection.getInspectionSupportDocs()) {
				filesCommon.setInspection(inspection);
				buildInspectionFiles(filesCommon);
			}
	}

	private void buildInspectionFiles(final InspectionFilesCommon inspectionFiles) {
		if (inspectionFiles.getFiles() != null && inspectionFiles.getFiles().length > 0) {
			Set<FileStoreMapper> existingFiles = new HashSet<>();
			existingFiles.addAll(inspectionFiles.getImages());
			existingFiles.addAll(applicationBpaService.addToFileStore(inspectionFiles.getFiles()));
			inspectionFiles.setImages(existingFiles);
		}
	}

	public List<PLInspection> findByIdOrderByIdAsc(final Long id) {
		return inspectionRepository.findByIdOrderByIdAsc(id);
	}

	public List<PLInspection> findByOcOrderByIdAsc(final PlinthLevelCertificate pl) {
		return inspectionRepository.findByPlOrderByIdDesc(pl);
	}

	private String generateInspectionNumber() {
		final InspectionNumberGenerator inspectionNUmber = beanResolver
				.getAutoNumberServiceFor(InspectionNumberGenerator.class);
		return inspectionNUmber.generateInspectionNumber("INSP");
	}

	public void buildDocketDetails(final List<DocketCommon> dockets) {
		for (DocketCommon docket : dockets) {
			for (final DocketDetailCommon dd : docket.getDocketDetail()) {
				final ChecklistServiceTypeMapping chkListDtl = checklistServicetypeMappingService
						.load(dd.getServiceChecklist().getId());
				dd.setServiceChecklist(chkListDtl);
				dd.setDocket(docket);
			}
		}
	}
	
	public List<DocketCommon> buildDocDetFromUI(final PLInspection plInspection) {
		List<DocketCommon> docket = new ArrayList<>();
		DocketCommon docObject = new DocketCommon();
		docObject.setInspection(plInspection.getInspection());
		final List<DocketDetailCommon> docketDetailList = buildDocketDetail(plInspection.getInspection());
		docObject.setDocketDetail(docketDetailList);
		docket.add(docObject);
		return docket;
	}

	public List<DocketDetailCommon> buildDocketDetail(final InspectionCommon inspection) {
		final List<DocketDetailCommon> docketDetailList = new ArrayList<>();
		docketDetailList.addAll(inspection.getDocketDetailLocList());
		return docketDetailList;
	}

	public void buildDocketDetailList(InspectionCommon inspection, final Long serviceTypeId) {

		List<ChecklistServiceTypeMapping> mappingList;
		List<DocketDetailCommon> commonList;
		DocketCommon docket;
		for (String type : CHECKLIST_TYPES) {
			mappingList = checklistServicetypeMappingService.findByActiveByServiceTypeAndChecklist(serviceTypeId, type);
			commonList = new ArrayList<>();
			for (final ChecklistServiceTypeMapping checkDet : mappingList) {
				commonList.add(new DocketDetailCommon(checkDet));
			}

			docket = new DocketCommon();
			if (mappingList != null && !mappingList.isEmpty())
				docket.setChecklistType(checkListTypeService.findByCode(type));
			docket.setDocketDetail(commonList);
			inspection.getDocket().add(docket);
		}
	}

	public void buildDocketDetailForModifyAndViewList(final InspectionCommon inspection, final Model model) {
		if (inspection != null && !inspection.getDocket().isEmpty())
			for (final DocketDetailCommon docketDet : inspection.getDocket().get(0).getDocketDetail()) {
				String checkListType = docketDet.getServiceChecklist().getChecklist().getChecklistType().getCode();
				if (PL_INSPECTION.equals(checkListType))
					inspection.getDocketDetailLocList().add(docketDet);
			}
		model.addAttribute("docketDetailLocList", inspection.getDocketDetailLocList());
		model.addAttribute("docketDetailMeasurementList", inspection.getDocketDetailMeasurementList());

	}

	public void prepareImagesForView(final PLInspection plInspection) {
		if (!plInspection.getInspection().getInspectionSupportDocs().isEmpty()) {
			plInspection.getInspection().getInspectionSupportDocs().forEach(docketFile -> {
				if (docketFile != null) {
					Map<Long, String> imageMap = new HashMap<>();
					docketFile.getImages().forEach(imageFilestore -> {
						final File file = fileStoreService.fetch(imageFilestore.getFileStoreId(),
								BpaConstants.FILESTORE_MODULECODE);
						if (file != null) {
							try {
								imageMap.put(imageFilestore.getId(),
										Base64.getEncoder().encodeToString(FileCopyUtils.copyToByteArray(file)));
							} catch (final IOException e) {
								LOGGER.error("Error while preparing the images for view", e);
							}
						}
					});
					docketFile.setEncodedImages(imageMap);
				}
			});
		}
	}
}
