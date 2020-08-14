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

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where "name" = 'Apply For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where "name" = 'Apply For Plinth Level Certificate'));

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'New Application For Plinth Level Certificate','/application/citizen/pl-certificate/new',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'New Application For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where "name" = 'New Application For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where "name" = 'New Application For Plinth Level Certificate'));

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

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where "name" = 'Submit Application For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where "name" = 'Submit Application For Plinth Level Certificate'));

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'View Application For Plinth Level Certificate','/application/citizen/plinth-level-certificate/update',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'View Application For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where "name" = 'View Application For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where "name" = 'View Application For Plinth Level Certificate'));

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

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where "name" = 'Success Application For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where "name" = 'Success Application For Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where "name" = 'Success Application For Plinth Level Certificate'));

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Update Application For Plinth Level Certificate','/application/plinth-level-certificate/update',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Update Application For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where "name" = 'Update Application For Plinth Level Certificate'));


INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Update Submit Application For Plinth Level Certificate','/application/plinth-level-certificate/update-submit',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Update Submit Application For Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where "name" = 'Update Submit Application For Plinth Level Certificate'));

INSERT INTO chandigarh.eg_action (id,"name",url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,application) VALUES 
(nextval('seq_eg_action'),'Generate Plinth Level Certificate','/application/plinth-level-certificate/generate-plinth-level-certificate',NULL,(select id from chandigarh.eg_module where name = 'BPA Plinth Level Certificate'),46,'Generate Plinth Level Certificate',false,'bpa',0,1,now(),1,now(),444)
;

INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'EMPLOYEE'),(select id from chandigarh.eg_action where "name" = 'Generate Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'BUSINESS'),(select id from chandigarh.eg_action where "name" = 'Generate Plinth Level Certificate'));
INSERT INTO chandigarh.eg_roleaction(roleid,actionid) values ((select id from state.eg_role where "name" = 'CITIZEN'),(select id from chandigarh.eg_action where "name" = 'Generate Plinth Level Certificate'));

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

