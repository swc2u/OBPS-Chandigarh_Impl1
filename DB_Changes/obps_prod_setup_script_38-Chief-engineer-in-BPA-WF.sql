--To add new dsignation,department for Chief Engineer
INSERT INTO chandigarh.eg_designation (id,"name",description,chartofaccounts,"version",createddate,lastmodifieddate,createdby,lastmodifiedby,code) VALUES 
(nextval('chandigarh.SEQ_EG_DESIGNATION'),'Chief Engineer UT Admin','Chief Engineer UT Admin',NULL,0,now(),now(),1,1,'CEUTADMIN');


INSERT INTO chandigarh.eg_department(
	id, name, createddate, code, createdby, lastmodifiedby, lastmodifieddate, version)
	VALUES (nextval('chandigarh.SEQ_EG_DEPARTMENT'), 'Chief Engineer', now(), 'Chief Engineer', 1, 1, now(), 0);
	
INSERT INTO chandigarh.eg_position(
	name, id, deptdesig, createddate, lastmodifieddate, createdby, lastmodifiedby, ispostoutsourced, version)
	VALUES ('E_Chief_Engineer', nextval('chandigarh.SEQ_EG_POSITION'), (select id from chandigarh.eg_designation where name='Chief Engineer UT Admin'),now(), now(), 1, 1, false, 0);


INSERT INTO state.eg_user select nextval('state.seq_eg_user'),'state', NULL, NULL, NULL, 'en_IN', 'UTChiefEngineer', '$2a$10$IL1irWapqAnNX3TR/4IJ/OijyShWf5/bDXrjhF2RjDxzHzB.1BNk.', now(), '1233442212', NULL, NULL, now(), now(), 1, 1, true, 'Chief Engineer UT', 1, NULL, NULL, 'EMPLOYEE', 0, NULL, NULL, 'state' where not exists (select * from state.eg_user where username='UTChiefEngineer');

INSERT INTO state.eg_userrole(
	roleid, userid)
	VALUES ((select id from state.eg_role where name='BPA Approver'), (select id from state.eg_user where username='UTChiefEngineer')),
	((select id from state.eg_role where name='EMPLOYEE'), (select id from state.eg_user where username='UTChiefEngineer'));
	


--Workflow modification for Chief Engineer

UPDATE chandigarh.eg_wf_matrix 
SET nextdesignation = 'Chief Engineer UT Admin',nextaction='Forwarded to Chief engineer to check NOC updation',nextstate='NOC updation initiated'
Where currentstate = 'Fee details verification initiated' and nextdesignation='SDO Building Urban' and nextstate='NOC updation in progress' and objecttype='BpaApplication' and additionalrule='High Risk';


INSERT INTO chandigarh.eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES 
(nextval('chandigarh.SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','NOC updation initiated','','','','High Risk','NOC updation in progress','Forwarded to check NOC updation','SDO Building Urban','Document Verification Completed','Forward,Revert to HDM',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL);





