INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'RGSTRTNCER', 'Registration certificate of the licensee, from Regional Joint Director, Urban Affairs department', true, true , (select id from egbpa_mstr_checklist where checklisttype='STAKEHOLDERDOCUMENT'), 0, 1, now(), 1, now());
INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'ADHRCRD', 'Adhar card of the licensee -copy attested by a gazetted officer, attested within one month prior to the date of application, to be scanned and uploaded', true, true, (select id from egbpa_mstr_checklist where checklisttype='STAKEHOLDERDOCUMENT'), 0, 1, now(), 1, now());
INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'RCNTPHTO', 'Recent passport size photo - Taken within six months prior to the date of application shall be uploaded', true, true, (select id from egbpa_mstr_checklist where checklisttype='STAKEHOLDERDOCUMENT'), 0, 1, now(), 1, now());
INSERT INTO egbpa_mstr_chklistdetail(id, code, description, isactive, ismandatory, checklist, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL'), 'CPYATTSTD', 'Copy attested by a gazetted officer attested within one month prior to the date of application, to be scanned and uploaded', true, true, (select id from egbpa_mstr_checklist where checklisttype='STAKEHOLDERDOCUMENT'), 0, 1, now(), 1, now());



alter table egbpa_mstr_stakeholder add column source bigint;
alter table egbpa_mstr_stakeholder add column comments character varying(1024);