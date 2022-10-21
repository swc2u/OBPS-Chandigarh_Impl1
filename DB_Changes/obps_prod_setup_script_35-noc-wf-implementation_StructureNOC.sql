--Structure NOC workflow

INSERT INTO chandigarh.eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES 
(nextval('chandigarh.SEQ_EG_WF_MATRIX'),'ANY','BpaNOC','Rejected','','','','High Risk','Noc rejected','Application is rejected by NOC user','BPA_STRUCTURE_NOC_ROLE','Rejected','close',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('chandigarh.SEQ_EG_WF_MATRIX'),'ANY','BpaNOC','NEW','','Forward to noc is pending','','High Risk','Noc verification pending','Forwarded to noc verification','BPA_STRUCTURE_NOC_ROLE','Initiated','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('chandigarh.SEQ_EG_WF_MATRIX'),'ANY','BpaNOC','Noc verification pending','Initiated','Forwarded to noc verification','','High Risk','Pending Approve','Application is forwarded to approve', 'BPA_STRUCTURE_NOC_ROLE', 'Forwarded', 'Forward,Reject', NULL,NULL, '2019-01-01','2099-04-01',0,NULL, NULL, NULL,NULL,NULL)
,(nextval('chandigarh.SEQ_EG_WF_MATRIX'),'ANY','BpaNOC','Pending Approve','Forwarded','Application is forwarded to approve','','High Risk','END','END','BPA_STRUCTURE_NOC_ROLE','Approved','Approve',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL);



--Modification to add structure noc worflow user,postion

INSERT INTO chandigarh.eg_designation(
	id, name, description, chartofaccounts, version, createddate, lastmodifieddate, createdby, lastmodifiedby, code)
	VALUES (nextval('SEQ_EG_DESIGNATION'), 'Structure department', 'Structure department NOC', null, 0, now(),now(), 1, 1, 'STRUNOC');
	
	
INSERT INTO chandigarh.egbpa_status(
	id, code, description, moduletype, isactive, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
	VALUES (nextval('seq_egbpa_status'), 'Forwarded', 'When BPA forwarded to respective NOC department user', 'NOC', true, 0, 1, now(), 1, now());
	
INSERT INTO chandigarh.eg_department(
	id, name, createddate, code, createdby, lastmodifiedby, lastmodifieddate, version)
	VALUES (nextval('SEQ_EG_DEPARTMENT'), 'NOC', now(), 'NOC', 1, 1, now(), 0);

INSERT INTO chandigarh.eg_position(
	name, id, deptdesig, createddate, lastmodifieddate, createdby, lastmodifiedby, ispostoutsourced, version)
	VALUES ('StructureNOC', nextval('SEQ_EG_POSITION'), select id from chandigarh.eg_designation where name='Structure department',now(), now(), 1, 1, false, 0);

	
INSERT INTO chandigarh.egeis_employee(
	id, code, dateofappointment, dateofretirement, employeestatus, employeetype, version)
	VALUES (13,'structurenoc', null, null, 'EMPLOYED', 1, 0);
	
INSERT INTO chandigarh.egeis_assignment(
	id, fund, function, designation, functionary, department, "position", grade, lastmodifiedby, lastmodifieddate, createddate,
	createdby, fromdate, todate, version, employee, isprimary)
	VALUES (nextval('chandigarh.SEQ_EGEIS_ASSIGNMENT'), null, null, (select id from chandigarh.eg_designation where name='Structure department'), null, (select id from chandigarh.eg_department where code='NOC') , (select id from chandigarh.eg_position where name='StructureNOC' ), 
			null, 1, now(), now(), 1, '2022-10-17', '2026-02-28', 0, (select id from chandigarh.egeis_employee where code='structurenoc'), true);