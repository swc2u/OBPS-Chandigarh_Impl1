SELECT setval('chandigarh.seq_eg_checklist',(SELECT max(id) FROM chandigarh.eg_checklist));

INSERT INTO chandigarh.eg_checklist
(id, checklisttypeid, code, description, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_eg_checklist'), 5, 'OCDCRDOC-11', 'DPC Certificate.', 0, 1, '2020-09-11 04:47:19.869', 1, '2020-09-11 04:47:19.869');

SELECT setval('chandigarh.seq_egbpa_checklist_servicetype_mapping',(SELECT max(id) FROM chandigarh.egbpa_checklist_servicetype_mapping));

INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping
(id, checklist, servicetype, isrequired, ismandatory, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'), (select id from chandigarh.eg_checklist where code = 'OCDCRDOC-11'), 59, true, false, 0, 1, '2020-09-11 04:47:39.028', 1, '2020-09-11 04:47:39.028');
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping
(id, checklist, servicetype, isrequired, ismandatory, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'), (select id from chandigarh.eg_checklist where code = 'OCDCRDOC-11'), 61, true, false, 0, 1, '2020-09-11 04:47:39.028', 1, '2020-09-11 04:47:39.028');
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping
(id, checklist, servicetype, isrequired, ismandatory, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'), (select id from chandigarh.eg_checklist where code = 'OCDCRDOC-11'), 62, true, false, 0, 1, '2020-09-11 04:47:39.028', 1, '2020-09-11 04:47:39.028');
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping
(id, checklist, servicetype, isrequired, ismandatory, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'), (select id from chandigarh.eg_checklist where code = 'OCDCRDOC-11'), 64, true, false, 0, 1, '2020-09-11 04:47:39.028', 1, '2020-09-11 04:47:39.028');
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping
(id, checklist, servicetype, isrequired, ismandatory, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'), (select id from chandigarh.eg_checklist where code = 'OCDCRDOC-11'), 65, true, false, 0, 1, '2020-09-11 04:47:39.028', 1, '2020-09-11 04:47:39.028');
