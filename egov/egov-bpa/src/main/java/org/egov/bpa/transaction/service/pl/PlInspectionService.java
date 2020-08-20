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
import org.egov.bpa.transaction.entity.common.InspectionCommon;
import org.egov.bpa.transaction.entity.common.InspectionFilesCommon;
import org.egov.bpa.transaction.entity.pl.PLInspection;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.repository.pl.PLInspectionRepository;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.autonumber.AutonumberServiceBeanResolver;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

@Service
@Transactional(readOnly = true)
public class PlInspectionService {
	private static final Logger LOGGER = Logger.getLogger(PlInspectionService.class);

	public static final List<String> CHECKLIST_TYPES = new ArrayList<String>() {

		private static final long serialVersionUID = -8357551578580968323L;

		{
			add(PL_INSPECTION);

		}
	};

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

		buildInspectionFiles(inspection);
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

	public List<PLInspection> findByPlOrderByIdAsc(final PlinthLevelCertificate pl) {
		return inspectionRepository.findByPlOrderByIdDesc(pl);
	}

	private String generateInspectionNumber() {
		final InspectionNumberGenerator inspectionNUmber = beanResolver
				.getAutoNumberServiceFor(InspectionNumberGenerator.class);
		return inspectionNUmber.generateInspectionNumber("INSP");
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