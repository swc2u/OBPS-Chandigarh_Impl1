--To add service to portal
INSERT INTO chandigarh.egp_portalservice(
	id, module, code, sla, version, url, isactive, name, userservice, businessuserservice, helpdoclink, createdby, createddate, lastmodifieddate, lastmodifiedby, moduleorder, serviceorder)
	VALUES (nextval('seq_egp_portalservice'),(select id from eg_module where name='BPA'), 'Offline BPA application', 15, 0, '/bpa/application/citizen/previous-sanction-form',true,'Previous sanction plan application', false, true,'/bpa/application/citizen/previous-sanction-form', 1, now(), now(), 1, 2, null);

--New status for previous plan	
INSERT INTO chandigarh.egbpa_status(
	id, code, description, moduletype, isactive, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
	VALUES (nextval('chandigarh.seq_egbpa_status'), 'Previous Plan Data Updated', 'Previous Plan Data Updated', 'REGISTRATION', true, 0,1,now(),1, now());
	
	
--To insert new previous plan actions
INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Previous sanction plan Application', '/application/citizen/previous-sanction-form', NULL, (select id from chandigarh.eg_module where name='BPA Transanctions'), 1, 'Previous sanction plan Application', false, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='BPA'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='BUSINESS'), (select id from chandigarh.eg_action where name='Previous sanction plan Application'));


INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Previous sanction plan Application with service id', '/application/citizen/previous-sanction-form-with-service-type', NULL, (select id from chandigarh.eg_module where name='BPA Transanctions'), 1, 'Previous sanction plan Application with service id', false, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='BPA'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='BUSINESS'), (select id from chandigarh.eg_action where name='Previous sanction plan Application with service id'));