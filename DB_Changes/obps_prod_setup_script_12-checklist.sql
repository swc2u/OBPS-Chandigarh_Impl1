INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES
	 (nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION'),'DOCUMENTATION-STRUCTURAL-CALCULATION-SHEET','Structural calculation sheet',0,1,now(),1,now());
	 

INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES
	 (nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION'),'NET-SAFE-BEARING-CAPACITY-FROM-CERTIFIED-ENGINEER','Net safe bearing capacity from certified engineer',0,1,now(),1,now());
	
select setval('seq_egbpa_checklist_servicetype_mapping', (select max(id) from egbpa_checklist_servicetype_mapping )) 

INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-STRUCTURAL-CALCULATION-SHEET'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-STRUCTURAL-CALCULATION-SHEET'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-STRUCTURAL-CALCULATION-SHEET'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-STRUCTURAL-CALCULATION-SHEET'),(select id from chandigarh.egbpa_mstr_servicetype where code='05'),true,false,0,1,now(),1,now());


INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='NET-SAFE-BEARING-CAPACITY-FROM-CERTIFIED-ENGINEER'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='NET-SAFE-BEARING-CAPACITY-FROM-CERTIFIED-ENGINEER'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='NET-SAFE-BEARING-CAPACITY-FROM-CERTIFIED-ENGINEER'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='NET-SAFE-BEARING-CAPACITY-FROM-CERTIFIED-ENGINEER'),(select id from chandigarh.egbpa_mstr_servicetype where code='05'),true,false,0,1,now(),1,now());
