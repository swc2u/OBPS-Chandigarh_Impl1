--------------- Plinth Level Certificate ---------------------------------------------------------------------------

CREATE TABLE chandigarh.egbpa_plinth_level_certificate (
	id int8 NOT NULL,
	parent int8 NOT NULL,
	applicationnumber varchar(128) NULL,
	plinthlevelcertificatenumber varchar(128) NULL,
	plinthlevelcertificateType varchar(50) NULL,
	applicationdate date NULL,
	"source" varchar(50) NULL,
	applicationtype varchar(128) NULL,
	status int8 NOT NULL,
	state_id int8 NULL,
	commenceddate date NULL,
	isconstructionreachedtopl bool NULL DEFAULT false,
	isconstructiondone bool NULL DEFAULT false,
	isrescheduledbycitizen bool NULL DEFAULT false,
	isrescheduledbyemployee bool NULL DEFAULT false,
	surveyorremarks varchar(5000) NULL,
	issurveyorinspectionrequire bool NULL DEFAULT false,
	createdby int8 NOT NULL,
	"version" numeric NULL DEFAULT 0,
	createddate timestamp NOT NULL,
	lastmodifieddate timestamp NOT NULL,
	lastmodifiedby int8 NOT NULL,
	approvaldate date NULL,
	approverposition int8 NULL,
	approveruser int8 NULL,
	CONSTRAINT pk_egbpa_plinth_level_certificate PRIMARY KEY (id)
);

ALTER TABLE chandigarh.egbpa_plinth_level_certificate ADD CONSTRAINT fk_egbpa_pl_appln FOREIGN KEY (parent) REFERENCES chandigarh.egbpa_application(id);
ALTER TABLE chandigarh.egbpa_plinth_level_certificate ADD CONSTRAINT fk_egbpa_pl_crtby FOREIGN KEY (createdby) REFERENCES state.eg_user(id);
ALTER TABLE chandigarh.egbpa_plinth_level_certificate ADD CONSTRAINT fk_egbpa_pl_mdfdby FOREIGN KEY (lastmodifiedby) REFERENCES state.eg_user(id);
ALTER TABLE chandigarh.egbpa_plinth_level_certificate ADD CONSTRAINT fk_egbpa_pl_states FOREIGN KEY (state_id) REFERENCES chandigarh.eg_wf_states(id);
ALTER TABLE chandigarh.egbpa_plinth_level_certificate ADD CONSTRAINT fk_egbpa_pl_status FOREIGN KEY (status) REFERENCES chandigarh.egbpa_status(id);
ALTER TABLE chandigarh.egbpa_plinth_level_certificate ADD CONSTRAINT fk_pl_appvr_pos FOREIGN KEY (approverposition) REFERENCES chandigarh.eg_position(id);
ALTER TABLE chandigarh.egbpa_plinth_level_certificate ADD CONSTRAINT fk_pl_appvr_user FOREIGN KEY (approveruser) REFERENCES state.eg_user(id);

CREATE SEQUENCE SEQ_EGBPA_PLINTH_LEVEL_CERTIFICATE;

update egbpa_mstr_applicationsubtype set enabled = true where name = 'Occupancy Certificate';

INSERT INTO chandigarh.egbpa_mstr_applicationsubtype (id,"name",description,enabled,createdby,createddate,lastmodifieddate,lastmodifiedby,"version",slotrequired) VALUES 
(nextval('seq_egbpa_mstr_applicationsubtype'),'Plinth Level Certificate','Plinth Level Certificate',true,1,now(),now(),1,0,true)
;

INSERT INTO chandigarh.eg_module (id,"name",enabled,contextroot,parentmodule,displayname,ordernumber) VALUES 
(nextval('seq_eg_module'),'BPA Plinth Level Certificate',true,'bpa',445,'Plinth Level Certificate',1)
;

INSERT INTO chandigarh.egcl_servicecategory (id,"name",code,isactive,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_egcl_servicecategory'),'Plinth Level Certificate','PLC',true,0,1,now(),1,now())
;

INSERT INTO chandigarh.egp_portalservice (id,"module",code,sla,"version",url,isactive,"name",userservice,businessuserservice,helpdoclink,createdby,createddate,lastmodifieddate,lastmodifiedby,moduleorder,serviceorder) VALUES 
(nextval('seq_egp_portalservice'),444,'Apply For Plinth Level Certificate',NULL,0,'/bpa/application/citizen/pl-certificate/apply',true,'Apply For Plinth Level Certificate',false,true,'/bpa/application/citizen/pl-certificate/apply',1,now(),now(),1,16,NULL)
;

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Apply For Plinth Level Certificate','/application/citizen/pl-certificate/apply',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Apply For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where code = 'Apply For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where code = 'Apply For Plinth Level Certificate'));

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'New Application For Plinth Level Certificate','/application/citizen/pl-certificate/new',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'New Application For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where code = 'New Application For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where code = 'New Application For Plinth Level Certificate'));

INSERT INTO chandigarh.egbpa_status (id,code,description,moduletype,isactive,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_egbpa_status'),'Field Inspection completed','Field Inspection completed','REGISTRATION',true,0,1,now(),NULL,now());


INSERT INTO eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES 
(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','NEW','','Plinth Level Certificate application creation pending','','High Risk','Registered','Forwarded to inspection','Junior Engineer Urban','Registered','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','Registered','Registered','Forwarded to inspection','','High Risk','Application details inspection in progress','Forwarded to inspect application details','SDO Building Urban','Field Inspection completed','Forward,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','Application details inspection in progress','Field Inspection completed','Forwarded to inspect application details','','High Risk','Record Approved','Forwarded to generate Plinth Level Certificate','SDO Building Urban','Approved','Approve,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','Record Approved',NULL,'Forwarded to generate Plinth Level Certificate','','High Risk','END','END','SDO Building Urban','Order Issued to Applicant','Generate Plinth Level Certificate',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
;

INSERT INTO eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES 
(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','NEW','','Plinth Level Certificate application creation pending','','Low Risk','Registered','Forwarded to inspection','Junior Engineer Urban','Registered','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','Registered','Registered','Forwarded to inspection','','Low Risk','Application details inspection in progress','Forwarded to inspect application details','SDO Building Urban','Field Inspection completed','Forward,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','Application details inspection in progress','Field Inspection completed','Forwarded to inspect application details','','Low Risk','Record Approved','Forwarded to generate Plinth Level Certificate','SDO Building Urban','Approved','Approve,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','Record Approved',NULL,'Forwarded to generate Plinth Level Certificate','','Low Risk','END','END','SDO Building Urban','Order Issued to Applicant','Generate Plinth Level Certificate',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
;


INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Submit Application For Plinth Level Certificate','/application/citizen/pl-certificate/create',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Submit Application For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where code = 'Submit Application For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where code = 'Submit Application For Plinth Level Certificate'));

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'View Application For Plinth Level Certificate','/application/citizen/plinth-level-certificate/update',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'View Application For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where code = 'View Application For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where code = 'View Application For Plinth Level Certificate'));

INSERT INTO chandigarh.eg_wf_types (id,"module","type",link,createdby,createddate,lastmodifiedby,lastmodifieddate,enabled,grouped,typefqn,displayname,"version") VALUES 
(nextval('seq_eg_wf_types'),444,'PlinthLevelCertificate','/bpa/application/plinth-level-certificate/update/:ID',1,now(),1,now(),true,false,'org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate','Plinth Level Certificate',0);

create sequence seq_egbpa_pl_notice;
create table chandigarh.egbpa_pl_notice(
	id bigint NOT NULL,
	plinthlevelcertificate bigint NOT NULL,
	notice bigint NOT NULL,
	createdby bigint NOT NULL,
	createddate timestamp without time zone NOT NULL,
	lastmodifieddate timestamp without time zone,
	lastmodifiedby bigint,
	version numeric NOT NULL,
	CONSTRAINT pk_notice_pl PRIMARY KEY (id),
	CONSTRAINT fk_notice_pl FOREIGN KEY (plinthlevelcertificate) REFERENCES chandigarh.egbpa_plinth_level_certificate (id),
	CONSTRAINT fk_notice_cmn FOREIGN KEY (notice) REFERENCES chandigarh.egbpa_notice_common (id),
	CONSTRAINT fk_notice_pl_crtby FOREIGN KEY (createdby) REFERENCES state.eg_user (id),
	CONSTRAINT fk_notice_pl_mdfdby FOREIGN KEY (lastmodifiedby) REFERENCES state.eg_user (id)
);

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Success Application For Plinth Level Certificate','/application/plinth-level-certificate/success',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Success Application For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where code = 'Success Application For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where code = 'Success Application For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where code = 'Success Application For Plinth Level Certificate'));

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Update Application For Plinth Level Certificate','/application/plinth-level-certificate/update',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Update Application For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where code = 'Update Application For Plinth Level Certificate'));


INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Update Submit Application For Plinth Level Certificate','/application/plinth-level-certificate/update-submit',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Update Submit Application For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where code = 'Update Submit Application For Plinth Level Certificate'));

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Generate Plinth Level Certificate','/application/plinth-level-certificate/generate-plinth-level-certificate',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Generate Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where code = 'Generate Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where code = 'Generate Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where code = 'Generate Plinth Level Certificate'));

CREATE SEQUENCE seq_egbpa_pl_appointment_schedule;
CREATE TABLE egbpa_pl_appointment_schedule
(
  id bigint NOT NULL,
  appointmentschedulecommon bigint NOT NULL,
  plinthlevelcertificate bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_pl_appointment_schedule PRIMARY KEY(id),
  CONSTRAINT fk_egbpa_pl_appmnt_schdle_cmn FOREIGN KEY (appointmentschedulecommon) REFERENCES chandigarh.egbpa_appointment_schedule_common (id),
  CONSTRAINT fk_egbpa_pl_appmnt_schdle_pl FOREIGN KEY (plinthlevelcertificate) REFERENCES chandigarh.egbpa_plinth_level_certificate (id),
  CONSTRAINT fk_egbpa_pl_appmnt_schdle_crtby FOREIGN KEY (createdby) REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_pl_appmnt_schdle_mdfdby FOREIGN KEY (lastmodifiedby) REFERENCES state.eg_user (id)
);

CREATE SEQUENCE seq_egbpa_pl_slot;
CREATE TABLE egbpa_pl_slot
(
  id bigint NOT NULL,
  plinthlevelcertificate bigint NOT NULL,
  scheduleappointmenttype character varying(50) NOT NULL,
  slotdetailid bigint NOT NULL,
  isactive boolean DEFAULT false,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_pl_slot PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_pl_slot_crtby FOREIGN KEY (createdby) REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_pl_slot_mdfdby FOREIGN KEY (lastmodifiedby) REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_pl_slot_pl FOREIGN KEY (plinthlevelcertificate) REFERENCES chandigarh.egbpa_plinth_level_certificate (id),
  CONSTRAINT fk_egbpa_pl_slot_slotdetail FOREIGN KEY (slotdetailid) REFERENCES chandigarh.egbpa_slotdetail (id)
);

CREATE SEQUENCE seq_egbpa_pl_inspection;
CREATE TABLE egbpa_pl_inspection
(
  id bigint NOT NULL,
  inspection bigint NOT NULL,
  plinthlevelcertificate bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_pl_inspection PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_pl_document_scrutiny_cmn FOREIGN KEY (inspection) REFERENCES chandigarh.egbpa_inspection_common (id),
  CONSTRAINT fk_egbpa_pl_inspection_pl FOREIGN KEY (plinthlevelcertificate) REFERENCES chandigarh.egbpa_plinth_level_certificate (id),
  CONSTRAINT fk_egbpa_pl_inspection_crtby FOREIGN KEY (createdby) REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_pl_inspection_mdfdby FOREIGN KEY (lastmodifiedby) REFERENCES state.eg_user (id)
);


INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Schedule Appointment Plinth Level Certificate','/application/plinth-level-certificate/schedule-appointment',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Schedule Appointment Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where code = 'Schedule Appointment Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where code = 'Schedule Appointment Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where code = 'Schedule Appointment Plinth Level Certificate'));

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Reschedule Appointment Plinth Level Certificate','/application/plinth-level-certificate/reschedule-appointment',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Reschedule Appointment Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where code = 'Reschedule Appointment Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where code = 'Reschedule Appointment Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where code = 'Reschedule Appointment Plinth Level Certificate'));

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'View Appointment Plinth Level Certificate','/application/plinth-level-certificate/appointment/view-details',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'View Appointment Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where code = 'View Appointment Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where code = 'View Appointment Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where code = 'View Appointment Plinth Level Certificate'));

INSERT INTO chandigarh.eg_checklist_type (id,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_eg_checklist_type'),'PLINSPNIMAGES','Plinth Level certificate application inspection images',0,1,now(),1,now())
;

SELECT setval('seq_eg_checklist', (SELECT max(id) FROM chandigarh.eg_checklist));
INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'),(select id from eg_checklist_type where code = 'PLINSPNIMAGES'),'PLINSPNIMAGES-01','Front Side',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from eg_checklist_type where code = 'PLINSPNIMAGES'),'PLINSPNIMAGES-02','Back Side',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from eg_checklist_type where code = 'PLINSPNIMAGES'),'PLINSPNIMAGES-03','Left Side',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from eg_checklist_type where code = 'PLINSPNIMAGES'),'PLINSPNIMAGES-04','Right Side',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from eg_checklist_type where code = 'PLINSPNIMAGES'),'PLINSPNIMAGES-05','Setbacks',0,1,now(),1,now())
;

INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-01' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-02' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-03' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-04' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-05' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-01' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-02' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-03' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-04' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-05' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-01' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-02' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-03' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-04' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-05' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-01' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-02' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-03' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-04' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLINSPNIMAGES-05' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLINSPNIMAGES')),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,true,0,1,now(),1,now())
;

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Create Inspection For Plinth Level Certificate','/application/plinth-level-certificate/create-inspection',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Create Inspection For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where code = 'Create Inspection For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where code = 'Create Inspection For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where code = 'Create Inspection For Plinth Level Certificate'));

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Result Inspection For Plinth Level Certificate','/application/plinth-level-certificate/success/view-inspection-details',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Result Inspection For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where code = 'Result Inspection For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where code = 'Result Inspection For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where code = 'Result Inspection For Plinth Level Certificate'));

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Show Inspection For Plinth Level Certificate','/application/plinth-level-certificate/show-inspection-details',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Show Inspection For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where code = 'Show Inspection For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where code = 'Show Inspection For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where code = 'Show Inspection For Plinth Level Certificate'));

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Update Inspection For Plinth Level Certificate','/application/plinth-level-certificate/update-inspection',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Update Inspection For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where code = 'Update Inspection For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where code = 'Update Inspection For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where code = 'Update Inspection For Plinth Level Certificate'));


INSERT INTO chandigarh.eg_appconfig (id,key_name,description,"version",createdby,lastmodifiedby,createddate,lastmodifieddate,"module") VALUES 
(nextval('seq_eg_appconfig'),'PL_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED','To enable/disable site inspection scheduling plinth level certificate application integration',0,NULL,NULL,NULL,NULL,444)
;

INSERT INTO chandigarh.eg_appconfig_values (id,key_id,effective_from,value,createddate,lastmodifieddate,createdby,lastmodifiedby,"version") VALUES 
(nextval('seq_eg_appconfig_values'),(select id from chandigarh.eg_appconfig where key_name = 'PL_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED'),now(),'YES',NULL,NULL,NULL,NULL,0)
;

INSERT INTO chandigarh.egbpa_status (id,code,description,moduletype,isactive,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_egbpa_status'),'Site Inspection completed','Site Inspection completed','REGISTRATION',true,0,1,now(),NULL,now());


create sequence seq_egbpa_pl_notice_conditions;
CREATE TABLE chandigarh.egbpa_pl_notice_conditions
(
  id bigint NOT NULL,
  plinthlevelcertificate bigint NOT NULL,
  noticecondition bigint NOT NULL,
  type character varying(30),
  conditionddate timestamp without time zone,
  conditionnumber character varying(30),
  ordernumber bigint,
  isrequired boolean DEFAULT true,
  additionalcondition character varying(600),
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_pl_notice_conditions_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_pl_notice_conditions_pl FOREIGN KEY (plinthlevelcertificate) REFERENCES chandigarh.egbpa_plinth_level_certificate (id),
  CONSTRAINT fk_egbpa_pl_notice_conditions_cndn FOREIGN KEY (noticecondition) REFERENCES chandigarh.egbpa_mstr_permit_conditions (id),
  CONSTRAINT fk_egbpa_pl_notice_conditions_crtby FOREIGN KEY (createdby) REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_pl_notice_conditions_mdfdby FOREIGN KEY (lastmodifiedby) REFERENCES state.eg_user (id)
);

delete from chandigarh.egbpa_pl_notice_conditions;
alter table chandigarh.egbpa_pl_notice_conditions drop constraint fk_egbpa_pl_notice_conditions_cndn;
alter table chandigarh.egbpa_pl_notice_conditions add constraint fk_egbpa_pl_notice_conditions_cndn FOREIGN KEY (noticecondition) REFERENCES chandigarh.egbpa_notice_conditions (id);
alter table chandigarh.egbpa_pl_notice_conditions drop column type;
alter table chandigarh.egbpa_pl_notice_conditions drop column conditionddate;
alter table chandigarh.egbpa_pl_notice_conditions drop column conditionnumber;
alter table chandigarh.egbpa_pl_notice_conditions drop column ordernumber;
alter table chandigarh.egbpa_pl_notice_conditions drop column isrequired;
alter table chandigarh.egbpa_pl_notice_conditions drop column additionalcondition;

INSERT INTO chandigarh.eg_checklist_type (id,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_eg_checklist_type'),'PLREJECTIONREASONS','Plinth level certificate rejection reasons',0,1,now(),1,now())
;

SELECT setval('seq_eg_checklist', (SELECT max(id) FROM chandigarh.eg_checklist));
INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'),(select id from eg_checklist_type where code = 'PLREJECTIONREASONS'),'PLREJECTIONREASON-01','The constructed building is in contradiction to any of the provisions of any of the law or order, rule, declaration or bye laws applicable.',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from eg_checklist_type where code = 'PLREJECTIONREASONS'),'PLREJECTIONREASON-02','The application for plinth level certificate does not contain the particulars or is not prepared in the manner required by these rules or bye law made under the acts concerned.',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from eg_checklist_type where code = 'PLREJECTIONREASONS'),'PLREJECTIONREASON-03','Any of the documents submitted do not conforms to the qualification requirements of the Architect, Engineer, Town Planner or supervisor or the owner/ applicant as required by the rule or byelaws concerned.',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from eg_checklist_type where code = 'PLREJECTIONREASONS'),'PLREJECTIONREASON-04','Any information or document or certificate as required by the permit approval system/ rule/ byelaws related has not been submitted properly or incorporated in an incorrect manner.',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from eg_checklist_type where code = 'PLREJECTIONREASONS'),'PLREJECTIONREASON-05','The owner of the land has not laid down and made street or streets or road or roads giving access to the site or sitesconnecting with an existing public or private streets while utilising , selling or leasing out or otherwise disposing of the land orany portion of the land or any portion or portions of the same site for construction of building.#The constructed building has made an encroachment upon a land belonging to the Government and / or the Corporationand/ or properties belonging to others than the applicant.',0,1,now(),1,now())
;

INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-01' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-02' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-03' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-04' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-05' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-01' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-02' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-03' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-04' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-05' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-01' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-02' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-03' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-04' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-05' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-01' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-02' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-03' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-04' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,true,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLREJECTIONREASON-05' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PLREJECTIONREASONS')),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,true,0,1,now(),1,now())
;

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Rejection For Plinth Level Certificate','/application/plinth-level-certificate/rejectionnotice',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Rejection For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where code = 'Rejection For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where code = 'Rejection For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where code = 'Rejection For Plinth Level Certificate'));

INSERT INTO chandigarh.eg_appconfig (id,key_name,description,"version",createdby,lastmodifiedby,createddate,lastmodifieddate,"module") VALUES 
(nextval('seq_eg_appconfig'),'SLA_PL_APPLICATION','Sla PL Application',0,NULL,NULL,NULL,NULL,444)
;

INSERT INTO chandigarh.eg_appconfig_values (id,key_id,effective_from,value,createddate,lastmodifieddate,createdby,lastmodifiedby,"version") VALUES 
(nextval('seq_eg_appconfig_values'),(select id from chandigarh.eg_appconfig where key_name = 'SLA_PL_APPLICATION'),now(),'15',NULL,NULL,NULL,NULL,0)
;

delete from eg_wf_matrix where objecttype = 'PlinthLevelCertificate';

INSERT INTO eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES 
(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','NEW','','Plinth Level Certificate application creation pending','','High Risk','Registered','Forwarded to JE for site inspection','Junior Engineer Urban','Registered','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','Registered','Registered','Forwarded to JE for site inspection','','High Risk','Final approval process initiated','Forwarded to approve the application','SDO Building Urban','Site Inspection completed','Forward,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','Final approval process initiated','Site Inspection completed','Forwarded to approve the application','','High Risk','Record Approved','Forwarded to generate Plinth Level Certificate','SDO Building Urban','Approved','Approve,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','Record Approved',NULL,'Forwarded to generate Plinth Level Certificate','','High Risk','END','END','SDO Building Urban','Order Issued to Applicant','Generate Plinth Level Certificate',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','Rejected','','Application is rejected by approver','','High Risk','generate rejection notice','Application is rejected by approver','SDO Building Urban','Rejected','Generate Rejection Notice',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
;

INSERT INTO eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES 
(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','NEW','','Plinth Level Certificate application creation pending','','Low Risk','Registered','Forwarded to JE for site inspection','Junior Engineer Urban','Registered','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','Registered','Registered','Forwarded to JE for site inspection','','Low Risk','Final approval process initiated','Forwarded to approve the application','SDO Building Urban','Site Inspection completed','Forward,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','Final approval process initiated','Site Inspection completed','Forwarded to approve the application','','Low Risk','Record Approved','Forwarded to generate Plinth Level Certificate','SDO Building Urban','Approved','Approve,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','Record Approved',NULL,'Forwarded to generate Plinth Level Certificate','','Low Risk','END','END','SDO Building Urban','Order Issued to Applicant','Generate Plinth Level Certificate',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','PlinthLevelCertificate','Rejected','','Application is rejected by approver','','Low Risk','generate rejection notice','Application is rejected by approver','SDO Building Urban','Rejected','Generate Rejection Notice',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
;



--- light & ventilation update accoring to 5.0.3 dcr jar start-----
insert into state.egdcr_layername(id,key,value,createdby,createddate,lastmodifiedby,lastmodifieddate,version) 
select nextval('state.seq_egdcr_layername'),'LAYER_NAME_ROOM_LIGHT_VENTILATION','BLK_%s_FLR_%s_ROOM_%s_LIGHT_VENTILATION_%s',1,now(),1,now(),0 where not exists(select key from state.egdcr_layername where key='LAYER_NAME_ROOM_LIGHT_VENTILATION');

insert into state.egdcr_layername(id,key,value,createdby,createddate,lastmodifiedby,lastmodifieddate,version) 
select nextval('state.seq_egdcr_layername'),'LAYER_NAME_ACROOM_LIGHT_VENTILATION','BLK_%s_FLR_%s_ACROOM_%s_LIGHT_VENTILATION_%s',1,now(),1,now(),0 where not exists(select key from state.egdcr_layername where key='LAYER_NAME_ACROOM_LIGHT_VENTILATION');

update state.egdcr_layername set value='BLK_%s_FLR_%s_REGULAR_ROOM_%s' where key='LAYER_NAME_REGULAR_ROOM';

update state.egdcr_layername set value='BLK_%s_FLR_%s_AC_ROOM_%s' where key='LAYER_NAME_AC_ROOM';
-----end----



update chandigarh.egbpa_sub_occupancy set occupancy = (select id from chandigarh.egbpa_occupancy where code ='F'),code='F-CIR' where code='A-CIR';

ALTER TABLE chandigarh.egbpa_sub_occupancy ADD COLUMN isfeature BOOLEAN DEFAULT FALSE;
update chandigarh.egbpa_sub_occupancy set isfeature = true where code in ('A-SQ','A-PO','A-S','A-PG','A-ICP','A-OCP','A-AF','A-GF');

INSERT INTO chandigarh.egbpa_sub_occupancy (id,code,"name",ordernumber,isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,"version",description,maxcoverage,minfar,maxfar,occupancy,colorcode,isfeature) VALUES 
(311,'A-R5','Rule 5',5,true,1,'2020-08-20 12:41:08.933','2020-08-20 12:41:08.933',1,0,'Rule 5',65,3,4,29,6,true)
;
update chandigarh.egbpa_sub_occupancy set isfeature = true where code in ('A-R5');

-------------------------------------------------------------------------------

update chandigarh.egbpa_mstr_applicationsubtype set description='DPC / Plinth Level Certificate' where "name" = 'Plinth Level Certificate';
update chandigarh.eg_module set displayname='DPC / Plinth Level Certificate' where "name" = 'BPA Plinth Level Certificate';
update chandigarh.egp_portalservice set "name"='Apply For DPC / Plinth Level Certificate' where code = 'Apply For Plinth Level Certificate';

-------------------------------------------------------------------------------

INSERT INTO chandigarh.egbpa_mstr_bpafeemapping (id,applicationtype,feesubtype,servicetype,calculationtype,bpafeecommon,amount,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,applicationsubtype) VALUES 
(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(SELECT id FROM chandigarh.egbpa_mstr_servicetype where code = '01'),'AUTO',1,1000,0,1,now(),1,now(),(SELECT id FROM chandigarh.egbpa_mstr_applicationsubtype where "name" = 'Low Risk'))
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(SELECT id FROM chandigarh.egbpa_mstr_servicetype where code = '01'),'AUTO',1,5000,0,1,now(),1,now(),(SELECT id FROM chandigarh.egbpa_mstr_applicationsubtype where "name" = 'Medium Risk'))
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(SELECT id FROM chandigarh.egbpa_mstr_servicetype where code = '01'),'AUTO',1,10000,0,1,now(),1,now(),(SELECT id FROM chandigarh.egbpa_mstr_applicationsubtype where "name" = 'High Risk'))
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(SELECT id FROM chandigarh.egbpa_mstr_servicetype where code = '03'),'AUTO',1,1000,0,1,now(),1,now(),(SELECT id FROM chandigarh.egbpa_mstr_applicationsubtype where "name" = 'Low Risk'))
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(SELECT id FROM chandigarh.egbpa_mstr_servicetype where code = '03'),'AUTO',1,5000,0,1,now(),1,now(),(SELECT id FROM chandigarh.egbpa_mstr_applicationsubtype where "name" = 'Medium Risk'))
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(SELECT id FROM chandigarh.egbpa_mstr_servicetype where code = '03'),'AUTO',1,10000,0,1,now(),1,now(),(SELECT id FROM chandigarh.egbpa_mstr_applicationsubtype where "name" = 'High Risk'))
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(SELECT id FROM chandigarh.egbpa_mstr_servicetype where code = '04'),'AUTO',1,1000,0,1,now(),1,now(),(SELECT id FROM chandigarh.egbpa_mstr_applicationsubtype where "name" = 'Low Risk'))
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(SELECT id FROM chandigarh.egbpa_mstr_servicetype where code = '04'),'AUTO',1,5000,0,1,now(),1,now(),(SELECT id FROM chandigarh.egbpa_mstr_applicationsubtype where "name" = 'Medium Risk'))
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(SELECT id FROM chandigarh.egbpa_mstr_servicetype where code = '04'),'AUTO',1,10000,0,1,now(),1,now(),(SELECT id FROM chandigarh.egbpa_mstr_applicationsubtype where "name" = 'High Risk'))
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(SELECT id FROM chandigarh.egbpa_mstr_servicetype where code = '06'),'AUTO',1,1000,0,1,now(),1,now(),(SELECT id FROM chandigarh.egbpa_mstr_applicationsubtype where "name" = 'Low Risk'))
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(SELECT id FROM chandigarh.egbpa_mstr_servicetype where code = '06'),'AUTO',1,5000,0,1,now(),1,now(),(SELECT id FROM chandigarh.egbpa_mstr_applicationsubtype where "name" = 'Medium Risk'))
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(SELECT id FROM chandigarh.egbpa_mstr_servicetype where code = '06'),'AUTO',1,10000,0,1,now(),1,now(),(SELECT id FROM chandigarh.egbpa_mstr_applicationsubtype where "name" = 'High Risk'))
;

delete from eg_wf_matrix where objecttype = 'BpaApplication' and additionalrule = 'Low Risk';

INSERT INTO eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES 
(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','NEW','','Forward to section clerk is pending','','Low Risk','Property documents verification initiated','Forwarded to property documents verification','Building Assistant Urban','Registered','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Rejected','','','','Low Risk','generate rejection notice','Application is rejected by approver','SDO Building Urban','Rejected','Generate Rejection Notice',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Application Approval Pending','','Forwarded to SDO Building for Approval','','Low Risk','Record Approved','Permit fee collection pending','SDO Building Urban','Approved','Approve,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Record Approved','','Forwarded to generate permit order','','Low Risk','END','END','SDO Building Urban','Order Issued to Applicant','Generate Permit Order',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Record Approved','','Permit fee collection pending','','Low Risk','Record Approved','Forwarded to generate permit order','SDO Building Urban','Approved','Generate Permit Order',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Property documents verification initiated','Registered','Forwarded to property documents verification','','Low Risk','Application Approval Pending','Forwarded to SDO Building for Approval','SDO Building Urban','','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
;

