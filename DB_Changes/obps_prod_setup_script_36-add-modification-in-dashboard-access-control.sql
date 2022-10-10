INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Urban Dashboard'), (select id from chandigarh.eg_action where name='Urban Service type wise status report'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Rural Dashboard'), (select id from chandigarh.eg_action where name='Rural Service type wise status report'));


INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Urban Dashboard'), (select id from chandigarh.eg_action where name='Urban Search Application'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Rural Dashboard'), (select id from chandigarh.eg_action where name='Rural Search Application'));


DELETE FROM chandigarh.eg_roleaction
	WHERE roleid=(select id from state.eg_role where name='Urban Dashboard') 
	and actionid=(select id from chandigarh.eg_action where name='Rural Search Application');
	
DELETE FROM chandigarh.eg_roleaction
	WHERE roleid=(select id from state.eg_role where name='Rural Dashboard') 
	and actionid=(select id from chandigarh.eg_action where name='Urban Search Application');
	

DELETE FROM chandigarh.eg_roleaction
	WHERE roleid=(select id from state.eg_role where name='Urban Dashboard') 
	and actionid=(select id from chandigarh.eg_action where name='Rural Service type wise status report');


DELETE FROM chandigarh.eg_roleaction
	WHERE roleid=(select id from state.eg_role where name='Rural Dashboard') 
	and actionid=(select id from chandigarh.eg_action where name='Urban Service type wise status report');
	
DELETE FROM chandigarh.eg_roleaction
	WHERE roleid=(select id from state.eg_role where name='Urban Dashboard') 
	and actionid=(select id from chandigarh.eg_action where name='Urban Scrutiny Report');
	
DELETE FROM chandigarh.eg_roleaction
	WHERE roleid=(select id from state.eg_role where name='Rural Dashboard') 
	and actionid=(select id from chandigarh.eg_action where name='Rural Scrutiny Report');
	

--servicewise status report for PL/DPC	
	
INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Urban Search service type wise status of PL certificate', '/reports/servicewise-statusreport-pl/d/u', NULL, (select id from chandigarh.eg_module where name='Urban'), 1, 'Service type wise status of PL/DPC certificate', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Urban'));


INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Urban Search service type wise status of PL certificate'));


INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Rural Search service type wise status of PL certificate', '/reports/servicewise-statusreport-pl/d/r', NULL, (select id from chandigarh.eg_module where name='Rural'), 1, 'Service type wise status of PL/DPC certificate', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Rural'));


INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Rural Search service type wise status of PL certificate'));