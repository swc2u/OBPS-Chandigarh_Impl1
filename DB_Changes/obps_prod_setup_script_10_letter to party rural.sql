INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-R-01','Form A (as prescribed in building bye-laws )',0,1,now(),1,now()),
(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-R-02','Affidavit',0,1,now(),1,now()),
(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-R-03','Sale deed/Mutation/Jamabandi/Fard attested by notary',0,1,now(),1,now()),
(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-R-04','Revenue Patwari report duly countersigned by tehsildar',0,1,now(),1,now()),
(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-R-05','Copy of latest Electricity bill',0,1,now(),1,now()),
(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-R-06','Specimen signatures along with photograph of owner/applicant duly signed and attested by a gazetted officer or executive magistrate - Mandatory',0,1,now(),1,now()),
(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-R-07','Indemnity Bond',0,1,now(),1,now());


--seq_egbpa_checklist_servicetype_mapping

INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-01'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-02'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-03'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-04'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-05'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-06'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-07'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now()),

(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-01'),(select id from chandigarh.egbpa_mstr_servicetype where code='02'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-02'),(select id from chandigarh.egbpa_mstr_servicetype where code='02'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-03'),(select id from chandigarh.egbpa_mstr_servicetype where code='02'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-04'),(select id from chandigarh.egbpa_mstr_servicetype where code='02'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-05'),(select id from chandigarh.egbpa_mstr_servicetype where code='02'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-06'),(select id from chandigarh.egbpa_mstr_servicetype where code='02'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-07'),(select id from chandigarh.egbpa_mstr_servicetype where code='02'),true,false,0,1,now(),1,now()),

(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-01'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-02'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-03'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-04'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-05'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-06'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-07'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now()),

(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-01'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-02'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-03'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-04'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-05'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-06'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now()),
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-R-07'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now());



--------------------------

INSERT INTO chandigarh.egbpa_lettertoparty_fee_mstr(id, feename, floornumber, isactive, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)VALUES
(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Development charges of roads', 0, true, 0, 1, now(), 1, now()),
(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Conversion charges', 0, true, 0, 1, now(), 1, now()),
(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Transfer fees', 0, true, 0, 1, now(), 1, now()),
(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'C & D (Construction & Demolition)', 0, true, 0, 1, now(), 1, now()),
(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Allotment of new number', 0, true, 0, 1, now(), 1, now());
