--Adding Planning NOC in OC NOC list
INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='OCNOC'),'PLANNING NOC','Urban Planning Department (Planning)',0,1,now(),1,now());


INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLANNING NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='OCNOC')),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLANNING NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='OCNOC')),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLANNING NOC' and checklisttypeid=(select id from
chandigarh.eg_checklist_type where code='OCNOC')),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now()) ,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLANNING NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='OCNOC')),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLANNING NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='OCNOC')),(select id from chandigarh.egbpa_mstr_servicetype where code='07'),true,false,0,1,now(),1,now())
;

INSERT INTO chandigarh.egbpa_master_nocconfiguration (id,department,applicationtype,integrationtype,integrationinitiation,sla,isdeemedapproval) VALUES 
(nextval('chandigarh.SEQ_EGBPA_MSTR_NOCCONFIG'),'PLANNING NOC','OC','INTERNAL','AUTO',15,false);


--A2K OC modification

UPDATE chandigarh.eg_wf_matrix
SET validactions='Approve,Reject,Revert to BA'
WHERE objecttype='OccupancyCertificate' and additionalrule='High Risk' 
and currentstatus='AEE Approval Completed' and currentstate='Final Approval Process initiated' and pendingactions='Occupancy certificate fee collection pending';

UPDATE chandigarh.eg_wf_matrix
SET pendingactions='Application is rejected by approver'
WHERE objecttype='OccupancyCertificate' and additionalrule='High Risk' 
 and currentstate='Rejected' and nextstate='generate rejection notice';



--BPA A2K WF modification

UPDATE chandigarh.eg_wf_matrix
SET validactions='Forward,Initiate Rejection,Revert to SDO'
WHERE objecttype='BpaApplication' and additionalrule='High Risk' 
and currentstate='AEE Application Approval Pending' and pendingactions='Forwarded to E- Assistant Estate Officer for Approval';


