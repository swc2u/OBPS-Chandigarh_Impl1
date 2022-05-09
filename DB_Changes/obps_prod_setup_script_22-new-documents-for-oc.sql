SELECT setval('chandigarh.seq_eg_checklist',(SELECT max(id) FROM chandigarh.eg_checklist));

INSERT INTO chandigarh.eg_checklist
(id, checklisttypeid, code, description, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_eg_checklist'), 2, 'OCGENERALDOC-26', 'Relevent Architecture control document (if applicable).', 0, 1, '2020-09-11 04:47:19.869', 1, '2020-09-11 04:47:19.869');
INSERT INTO chandigarh.eg_checklist
(id, checklisttypeid, code, description, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_eg_checklist'), 2, 'OCGENERALDOC-25', 'Previous sanctioned plan.', 0, 1, '2020-09-11 04:47:19.869', 1, '2020-09-11 04:47:19.869');

SELECT setval('chandigarh.seq_egbpa_checklist_servicetype_mapping',(SELECT max(id) FROM chandigarh.egbpa_checklist_servicetype_mapping));

INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping
(id, checklist, servicetype, isrequired, ismandatory, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'), (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-25'), 59, true, true, 0, 1, '2020-09-11 04:47:39.028', 1, '2020-09-11 04:47:39.028');
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping
(id, checklist, servicetype, isrequired, ismandatory, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'), (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-25'), 61, true, true, 0, 1, '2020-09-11 04:47:39.028', 1, '2020-09-11 04:47:39.028');
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping
(id, checklist, servicetype, isrequired, ismandatory, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'), (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-25'), 62, true, true, 0, 1, '2020-09-11 04:47:39.028', 1, '2020-09-11 04:47:39.028');
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping
(id, checklist, servicetype, isrequired, ismandatory, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'), (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-25'), 64, true, true, 0, 1, '2020-09-11 04:47:39.028', 1, '2020-09-11 04:47:39.028');
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping
(id, checklist, servicetype, isrequired, ismandatory, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'), (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-25'), 65, true, true, 0, 1, '2020-09-11 04:47:39.028', 1, '2020-09-11 04:47:39.028');

INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping
(id, checklist, servicetype, isrequired, ismandatory, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'), (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-26'), 59, true, false, 0, 1, '2020-09-11 04:47:39.028', 1, '2020-09-11 04:47:39.028');
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping
(id, checklist, servicetype, isrequired, ismandatory, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'), (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-26'), 61, true, false, 0, 1, '2020-09-11 04:47:39.028', 1, '2020-09-11 04:47:39.028');
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping
(id, checklist, servicetype, isrequired, ismandatory, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'), (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-26'), 62, true, false, 0, 1, '2020-09-11 04:47:39.028', 1, '2020-09-11 04:47:39.028');
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping
(id, checklist, servicetype, isrequired, ismandatory, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'), (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-26'), 64, true, false, 0, 1, '2020-09-11 04:47:39.028', 1, '2020-09-11 04:47:39.028');
INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping
(id, checklist, servicetype, isrequired, ismandatory, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextval('chandigarh.seq_egbpa_checklist_servicetype_mapping'), (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-26'), 65, true, false, 0, 1, '2020-09-11 04:47:39.028', 1, '2020-09-11 04:47:39.028');


