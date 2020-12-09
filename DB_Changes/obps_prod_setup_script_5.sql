CREATE TABLE chandigarh.eg_wf_file (
	id int8 NOT NULL,
	filestore int8 NOT NULL,
	staterefid varchar(100) NOT NULL,
	createdby int8 NOT NULL,
	createddate timestamp NOT NULL,
	lastmodifiedby int8 NOT NULL,
	lastmodifieddate timestamp NOT NULL,
	"version" int8 NULL DEFAULT 0,
	CONSTRAINT pk_eg_wf_file_id PRIMARY KEY (id)
);

ALTER TABLE chandigarh.eg_wf_file ADD CONSTRAINT fk_eg_wf_file_filestore FOREIGN KEY (filestore) REFERENCES chandigarh.eg_filestoremap(id);

CREATE SEQUENCE seq_eg_wf_files;

ALTER TABLE chandigarh.eg_wf_states ADD COLUMN reffileid varchar(100) default null;
ALTER TABLE chandigarh.eg_wf_state_history ADD COLUMN reffileid varchar(100) default null;

-------------------------04-Dec-2020---------------------------

INSERT INTO chandigarh.eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES 
(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','LP Initiated','','','','Low Risk','LP Created','Letter To Party Reply Pending','SDO Building Urban','Letter To Party Created','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','LP Created','','','','Low Risk','LP Reply Received','Forward to LP Initiator pending','SDO Building Urban','Letter To Party Reply Received','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
;

update chandigarh.eg_wf_matrix set nextstatus = 'Document Verification Completed' where objecttype = 'BpaApplication' and additionalrule = 'Low Risk' and currentstate = 'Fee details verification initiated';

update chandigarh.eg_checklist set description='A site plan showing the position of Plot proposed to be built upon' where code='LTP-01' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Elevations' where code='LTP-02' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Plans' where code='LTP-03' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Sections' where code='LTP-04' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Parking Plan' where code='LTP-05' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Roof Plan' where code='LTP-06' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Drainage plan' where code='LTP-07' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Engineering Drawings (Structure)' where code='LTP-08' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Details' where code='LTP-09' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Other Details' where code='LTP-10' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Form-A' where code='LTP-11' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Form-C' where code='LTP-12' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Form-K' where code='LTP-13' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Form-J' where code='LTP-14' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Checklist duly filled & signed by the Private Architect.' where code='LTP-15' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Authority letter in favour of private architect from the owner(s) / applicant(s) regarding submission of plan / Revised Building plan for sanction and making correspondence with Estate Office.' where code='LTP-16' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Proof of ownership i.e. copy of allotment letter / transfer letter not more than 3 months old.' where code='LTP-17' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='No dues certificate regarding payment of entire/full amount of premium of plot (including up to date annual Ground Rent / Lease Money (Self Attested)' where code='LTP-18' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Self attested undertaking:- a) Regarding ownership from owner(s) / applicant(s) with specimen signature, latest photographs and ID proof. b) That there is no dispute / litigation is pending in any court of law and the properties free from all sorts of encumbrance and there is no stay / restraining order from any court of law regarding sanction of Fresh / Revised Building Plan (c) Affidavit stating all the owner(s) are alive' where code='LTP-19' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Copy of Sewerage Connection / Occupation Certificate or extension in time limit for construction of the building.' where code='LTP-20' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='In case, sewerage connection/occupation certificate is not issued/obtained then furnish:- a) An attested copy of proof of construction i.e., water/electricity bills (prior to 22-1-1993 or construction of building within the stipulated period as per terms and conditions of allotment or within the extended period as the case may be), supported with a certificate issued by the Registered Architect on his/her letter head certifying that the building is constructed as per sanctioned plan and there is no building violations at Site/House. b) Certificate from the concerned water/electricity department certifying there in the date and year of release of permanent / regular water/electricity connection against the house/building in question.' where code='LTP-21' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Indemnity Bond is required from each owner (s) duly attested from notary.' where code='LTP-22' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='If applicant is GPA / SPA holder, then furnish: - a) Copy of GPA / SPA (attested by notary public). b) Affidavit regarding validity of GPA / SPA' where code='LTP-23' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='If there is no proof of previous sanctioned plan, an undertaking is must required from the owner (s) that my / our plan (s) be treated as fresh with applicable charges.' where code='LTP-24' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='If there is no proof of previous sanctioned plan / plans is treated as fresh, plot size may also got be verified from the Surveyor.' where code='LTP-25' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='In case of fresh building plan, the plot size may be got verified from the Surveyor.' where code='LTP-26' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='E-mail ID and address of owner (s), Private Architect and Structural Engineer is required on submitted plan (s).' where code='LTP-27' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Attested Copy of partnership deed, authorization letter in favour of the authorized signatory/ partner to apply for Revised Building Plan. (Applicable in case of property owned by Partnership Firm).' where code='LTP-28' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Attested copy of Memorandum of Articles and Association copy of resolution in favour of authorized signatory/ Director of the Company to apply for Revised Building Plan. (Application in case of property owned by the Company).' where code='LTP-29' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='In case of allotment of society, NOC from the Chandigarh Housing Board or Society Registrar required.' where code='LTP-30' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Structure design requirements: (a) Certification stating structural safety of existing, adjoining structures shall be ensured during dismantling and execution etc. (b) Certification stating structural design is as per prevailing IS Codal provision for safety, designing and execution' where code='LTP-31' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');

INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-32','Structure design requirements:(a) Staad file for building structure verification',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-33','Documents to be attached in case of plot amalgamation',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-34','Agreement from the owner and competent authority from Estate office if projections are in government land',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-35','Authority letter for private architect is required from owner (s) regarding submission / correspondence / receipt of sanctioned plan.',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-36','Documents to be attached in case of plot amalgamation',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-37','Agreement from the owner and competent authority from Estate office if projections are in government land',0,1,now(),1,now())
;

INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-32'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-33'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-34'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-35'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-36'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-37'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
;

INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-32'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-33'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-34'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-35'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-36'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-37'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
;

INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-32'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-33'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-34'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-35'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-36'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-37'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
;

INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-32'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-33'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-34'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-35'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-36'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-37'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
;



