--OC general documents

INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS'),'OCGENERALDOC-25','Electrical details',0,1,now(),1,now()),
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS'),'OCGENERALDOC-26','Fire safety details',0,1,now(),1,now()),
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS'),'OCGENERALDOC-27','Pollution control details', 0, 1,now(),1,now()),
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS'),'OCGENERALDOC-28','Public health details', 0, 1,now(),1,now());

--BPA Documents

INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION'),'DOCUMENTATION-37','Electrical details',0,1,now(),1,now())
,(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION'),'DOCUMENTATION-38','Fire safety details',0,1,now(),1,now())
,(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION'),'DOCUMENTATION-39','Pollution control details', 0, 1, now(), 1,now())
,(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION'),'DOCUMENTATION-40','Public health details',0,1,now(),1,now());

--BPA checklist servicetype mapping
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-37'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-38'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-39'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-40'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-37'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-38'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-39'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-40'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-37'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-38'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-39'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-40'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-37'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-38'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-39'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-40'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now());

--OC checklist servicetype mapping
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-25'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-26'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-27'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-28'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-25'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-26'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-27'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-28'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-25'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-26'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-27'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-28'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-25'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-26'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-27'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code='OCGENERALDOC-28'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now());

