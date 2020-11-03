---Comments size changes--

ALTER TABLE chandigarh.eg_wf_state_history ALTER COLUMN "comments" type varchar(4000);

ALTER TABLE state.eg_wf_state_history ALTER COLUMN "comments" type varchar(4000);

ALTER TABLE chandigarh.eg_wf_states ALTER COLUMN "comments" type varchar(4000);

ALTER TABLE state.eg_wf_states ALTER COLUMN "comments" type varchar(4000);

update chandigarh.egbpa_mstr_bpafee_common set "name"='Transfer fee', description='Transfer fee' where code='OTF';
update chandigarh.eg_demand_reason_master set reasonmaster='Transfer fee' where code='OTF';

update egbpa_mstr_bpafeemapping set applicationtype = 'PERMIT_APPLICATION' where bpafeecommon = (select id from chandigarh.egbpa_mstr_bpafee_common where code='OTF');

update chandigarh.egbpa_mstr_bpafee_common set "name"='Construction & Demolision', description='Construction & Demolision' where code='DF';
update chandigarh.eg_demand_reason_master set reasonmaster='Construction & Demolision' where code='DF';

delete from chandigarh.egbpa_mstr_bpafeemapping where applicationtype='PERMIT_APPLICATION' and feesubtype='SANCTION_FEE' and bpafeecommon=(select id from chandigarh.egbpa_mstr_bpafee_common where code='DF');

INSERT INTO chandigarh.egbpa_mstr_bpafeemapping (id,applicationtype,feesubtype,servicetype,calculationtype,bpafeecommon,amount,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,applicationsubtype) VALUES 
(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='01'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='DF'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='03'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='DF'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='04'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='DF'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='06'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='DF'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='07'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='DF'),0,0,1,now(),1,now(),NULL)
;

update chandigarh.egbpa_mstr_bpafee_common set "name"='Fee for allotment of new number', description='Fee for allotment of new number' where code='AFCW';
update chandigarh.eg_demand_reason_master set reasonmaster='Fee for allotment of new number' where code='AFCW';

delete from chandigarh.egbpa_mstr_bpafeemapping where applicationtype='PERMIT_APPLICATION' and feesubtype='SANCTION_FEE' and bpafeecommon=(select id from chandigarh.egbpa_mstr_bpafee_common where code='AFCW');

INSERT INTO chandigarh.egbpa_mstr_bpafeemapping (id,applicationtype,feesubtype,servicetype,calculationtype,bpafeecommon,amount,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,applicationsubtype) VALUES 
(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='01'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='AFCW'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='03'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='AFCW'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='04'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='AFCW'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='06'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='AFCW'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='07'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='AFCW'),0,0,1,now(),1,now(),NULL)
;

ALTER TABLE chandigarh.egbpa_application ADD COLUMN sector VARCHAR(20), ADD COLUMN plotnumber VARCHAR(20), ADD COLUMN filenumber VARCHAR(20);

----28-10-2020------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO chandigarh.egbpa_status (id,code,description,moduletype,isactive,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_egbpa_status'),'Accepted as Scrutinized','Accepted as Scrutinized','REGISTRATION',true,0,1,now(),NULL,now());

update chandigarh.eg_checklist set description = 'Self attested undertaking:- a) Regarding ownership from owner(s) / applicant(s) with specimen signature, latest photographs and ID proof. b) That there is no dispute / litigation is pending in any court of law and the properties free from all sorts of encumbrance and there is no stay / restraining order from any court of law with regard to sanction of Fresh / Revised Building Plan (c) Affidavit stating all the owner(s) are alive'
where code = 'DOCUMENTATION-09';

update chandigarh.eg_checklist set description = 'Self attested undertaking:- a) Regarding ownership from owner(s) / applicant(s) with specimen signature, latest photographs and ID proof. b) That there is no dispute / litigation is pending in any court of law and the properties free from all sorts of encumbrance and there is no stay / restraining order from any court of law with regard to sanction of Fresh / Revised Building Plan (c) Affidavit stating all the owner(s) are alive'
where code = 'LTP-19';

update chandigarh.eg_checklist set description = 'Indemnity Bond is required from each owner (s) duly attested from notary.'
where code = 'LTP-22';

update chandigarh.eg_checklist set description = 'Indemnity Bond is required from each owner (s) duly attested from notary.'
where code = 'DOCUMENTATION-12';

delete from chandigarh.eg_wf_matrix where objecttype = 'BpaApplication' and additionalrule = 'Low Risk';

INSERT INTO chandigarh.eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES 
(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','NEW','','Forward to section clerk is pending','','Low Risk','Property documents verification initiated','Forwarded to property documents verification','Building Assistant Urban','Registered','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Property documents verification initiated','Registered','Forwarded to property documents verification','','Low Risk','Fee details verification initiated','Forwarded to verify fee details','Head Draftsman Urban','','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Fee details verification initiated','','Forwarded to verify fee details','','Low Risk','Application Approval Pending','Forwarded to SDO Building for Approval','SDO Building Urban','','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Application Approval Pending','','Forwarded to SDO Building for Approval','','Low Risk','Record Approved','Permit fee collection pending','SDO Building Urban','Approved','Approve,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Record Approved','','Permit fee collection pending','','Low Risk','Record Approved','Forwarded to SDO Building','SDO Building Urban','Approved','Accept as Scrutinized',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Record Approved','','Forwarded to SDO Building','','Low Risk','END','END','SDO Building Urban','Accepted as Scrutinized','Accept as Scrutinized',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Rejected','','','','Low Risk','generate rejection notice','Application is rejected by approver','SDO Building Urban','Rejected','Generate Rejection Notice',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
;

delete from chandigarh.eg_wf_matrix where objecttype = 'BpaApplication' and additionalrule = 'High Risk';

INSERT INTO chandigarh.eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES 
(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','NEW','','Forward to section clerk is pending','','High Risk','Property documents verification initiated','Forwarded to property documents verification','Building Assistant Urban','Registered','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Property documents verification initiated','Registered','Forwarded to property documents verification','','High Risk','Fee details verification initiated','Forwarded to verify fee details','Head Draftsman Urban','Document Verification Completed','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Fee details verification initiated','','Forwarded to verify fee details','','High Risk','NOC updation in progress','Forwarded to check NOC updation','SDO Building Urban','Document Verification Completed','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','NOC updation in progress','','','','High Risk','AEE Application Approval Pending','Forwarded to E- Assistant Estate Officer for Approval','Assistant Estate Officer','Document Verification Completed','Forward,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','AEE Application Approval Pending','','Forwarded to E- Assistant Estate Officer for Approval','','High Risk','Final Approval Process initiated','Permit Fee Collection Pending','Estate Officer','Document Verification Completed','Forward,Initiate Rejection',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Final Approval Process initiated','','Permit Fee Collection Pending','','High Risk','Permit Fee Collected','Forwarded to update permit conditions','SDO Building Urban','Approved','Approve,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Permit Fee Collected','','Forwarded to update permit conditions','','High Risk','Record Approved','Forwarded to generate permit order','SDO Building Urban','Approved','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Permit Fee Collected','','Forwarded to generate permit order','','High Risk','Record Approved','Forwarded to generate permit order','SDO Building Urban','Approved','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Record Approved','','','','High Risk','END','END','SDO Building Urban','Order Issued to Applicant','Generate Permit Order',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','LP Initiated','','','','High Risk','LP Created','Letter To Party Reply Pending','Building Assistant Urban','Letter To Party Created','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','LP Created','','','','High Risk','LP Reply Received','Forward to LP Initiator pending','Building Assistant Urban','Letter To Party Reply Received','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Rejection Initiated','','Verify Rejection Reasons','','High Risk','SDO Building Approval Pending','Forwarded to SDO Building for Approval','SDO Building Urban','Approval Process Initiated','Forward,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Rejection Initiated','','Forwarded to SDO Building for Approval','','High Risk','AEE Application Approval Pending','Forwarded to E- Assistant Estate Officer for Approval','Assistant Estate Officer','Document Verification Completed','Forward,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','Rejected','','','','High Risk','generate rejection notice','Application is rejected by approver','SDO Building Urban','Rejected','Generate Rejection Notice',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
;

---For Report----------------------------------------------------------------------------------------------------

INSERT INTO state.eg_role (id,"name",description,createddate,createdby,lastmodifiedby,lastmodifieddate,"version",internal) VALUES 
(nextval('state.seq_eg_role'),'IT_SUPPORT_ROLE','Role for employee who can view the reports',now(),1,1,now(),0,false);

Insert into chandigarh.eg_roleaction values((select id from state.eg_role where name='IT_SUPPORT_ROLE'),(select id from chandigarh.eg_action where name='buildingplanscrutinysearchreport'));
Insert into chandigarh.eg_roleaction values((select id from state.eg_role where name='IT_SUPPORT_ROLE'),(select id from chandigarh.eg_action where name='Search BPA Application'));


