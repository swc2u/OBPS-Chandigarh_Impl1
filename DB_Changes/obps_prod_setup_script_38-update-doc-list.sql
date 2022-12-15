--To delete General Documents
Delete from chandigarh.egbpa_checklist_servicetype_mapping
where checklist in (select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code in('DOCUMENTATION-37','DOCUMENTATION-38','DOCUMENTATION-39','DOCUMENTATION-40'));

Delete from chandigarh.egbpa_checklist_servicetype_mapping
where checklist in (select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code in('OCGENERALDOC-25','OCGENERALDOC-26','OCGENERALDOC-27','OCGENERALDOC-28'));


Delete from chandigarh.eg_checklist
where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code in('DOCUMENTATION-37','DOCUMENTATION-38','DOCUMENTATION-39','DOCUMENTATION-40');

Delete from chandigarh.eg_checklist
where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code in('OCGENERALDOC-25','OCGENERALDOC-26','OCGENERALDOC-27','OCGENERALDOC-28');


------------If some docs already Uploaded then need to set isrequired value to false------

Update chandigarh.egbpa_checklist_servicetype_mapping
set isrequired='false'
where checklist in (select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code in('DOCUMENTATION-37','DOCUMENTATION-38','DOCUMENTATION-39','DOCUMENTATION-40'));

Update chandigarh.egbpa_checklist_servicetype_mapping
set isrequired='false'
where checklist in (select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCGENERALDOCUMENTS') and code in('OCGENERALDOC-25','OCGENERALDOC-26','OCGENERALDOC-27','OCGENERALDOC-28'));






--BPA DCR checklist

INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS'),'OCDCRDOC-12','Electrical details',0,1,now(),1,now()),
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS'),'OCDCRDOC-13','Fire safety details',0,1,now(),1,now()),
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS'),'OCDCRDOC-14','Pollution control details',0,1,now(),1,now()),
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS'),'OCDCRDOC-15','Public health details',0,1,now(),1,now());

--OC DCR checklist
INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS'),'BPADCRDOC-11','Electrical details',0,1,now(),1,now()),
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS'),'BPADCRDOC-12','Fire safety details',0,1,now(),1,now()),
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS'),'BPADCRDOC-13','Pollution control details',0,1,now(),1,now()),
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS'),'BPADCRDOC-14','Public health details',0,1,now(),1,now());

--LTP checklist
INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-38','Electrical details',0,1,now(),1,now()),
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-39','Fire safety details',0,1,now(),1,now()),
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-40','Pollution control details',0,1,now(),1,now()),
(nextval('chandigarh.seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-41','Public health details',0,1,now(),1,now());



--BPA checklist servicetype mapping
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-11'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-11'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-11'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-11'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-11'),(select id from chandigarh.egbpa_mstr_servicetype where code='07'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='07'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='07'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS') and code='BPADCRDOC-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='07'),true,false,0,1,now(),1,now());

--OC checklist servicetype mapping
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-15'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-15'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-15'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-15'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='07'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='07'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='07'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='OCDCRDOCUMENTS') and code='OCDCRDOC-15'),(select id from chandigarh.egbpa_mstr_servicetype where code='07'),true,false,0,1,now(),1,now());

--LTP checklist servicetype mapping
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-38'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-39'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-40'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-41'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-38'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-39'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-40'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-41'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-38'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-39'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-40'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-41'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-38'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-39'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-40'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-41'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-38'),(select id from chandigarh.egbpa_mstr_servicetype where code='07'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-39'),(select id from chandigarh.egbpa_mstr_servicetype where code='07'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-40'),(select id from chandigarh.egbpa_mstr_servicetype where code='07'),true,false,0,1,now(),1,now())
,(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-41'),(select id from chandigarh.egbpa_mstr_servicetype where code='07'),true,false,0,1,now(),1,now());
