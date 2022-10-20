INSERT INTO state.eg_role (id,"name",description,createddate,createdby,lastmodifiedby,lastmodifieddate,"version",internal) VALUES 
(nextval('state.seq_eg_role'),'BPA_PLANNING_NOC_ROLE','Role for users who approve/provides a NOC from Urban Planning Department (planning) department',now(),1,1,now(),0,false)

INSERT INTO state.eg_user select nextval('state.seq_eg_user'), 'state', NULL, NULL, NULL, 'en_IN', 'planningnoc', '$2a$10$Yo7HoeymnnjmAR.kPU51Lu5OMd5bvYR52MtLoN9NLIjwJaG8RIdrS', '2025-12-31 00:00:00', 1112223334, NULL, NULL, now(), now(), 1, 1, true, 'Urban Planning Department(Planning)', NULL, NULL, NULL, 'BUSINESS', 0, NULL, NULL, 'state' where not exists (select * from state.eg_user where username='planningnoc');


INSERT into state.eg_userrole select (select id from state.eg_role where name = 'BPA_PLANNING_NOC_ROLE'), (select id from state.eg_user where username ='planningnoc') where not exists (SELECT * FROM state.eg_userrole WHERE roleid in (select id from state.eg_role where name = 'BPA_PLANNING_NOC_ROLE'));

INSERT into state.eg_userrole select (select id from state.eg_role where name = 'BUSINESS'), (select id from state.eg_user where username ='planningnoc');



INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='NOC'),'PLANNING NOC','Urban Planning Department (Planning)',0,1,now(),1,now());


INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLANNING NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC')),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLANNING NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC')),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLANNING NOC' and checklisttypeid=(select id from
chandigarh.eg_checklist_type where code='NOC')),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now()) ,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLANNING NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC')),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'PLANNING NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC')),(select id from chandigarh.egbpa_mstr_servicetype where code='07'),true,false,0,1,now(),1,now())
;

INSERT INTO chandigarh.egbpa_master_nocconfiguration (id,department,applicationtype,integrationtype,integrationinitiation,sla,isdeemedapproval) VALUES 
(nextval('chandigarh.SEQ_EGBPA_MSTR_NOCCONFIG'),'PLANNING NOC','Permit','INTERNAL','AUTO',15,false)
