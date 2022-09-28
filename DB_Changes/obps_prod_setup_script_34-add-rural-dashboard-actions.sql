--Pending task
UPDATE chandigarh.eg_action
	SET url='/application/searchBPAItems/d/u'
	WHERE name='Urban Search Application';



INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Urban Search Pending Application Tasks', '/application/searchPendingItems/d/u', NULL, (select id from chandigarh.eg_module where name='Urban'), 1, 'Search Pending Applications', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Urban Search Pending Application Tasks'));


--Pending Task OC
UPDATE chandigarh.eg_action
	SET url='/application/searchOCItems/d/u'
	WHERE name='Urban Search occupancy certificate';



INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Urban Search OC Pending Application Tasks', '/application/searchOCPendingItems/d/u', NULL, (select id from chandigarh.eg_module where name='Urban'), 1, 'Search Occupancy Certificate Pending Applications', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Urban Search OC Pending Application Tasks'));

--Pending task Rural

UPDATE chandigarh.eg_action
	SET url='/application/searchBPAItems/d/r'
	WHERE name='Rural Search Application';



INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Rural Search Pending Application Tasks', '/application/searchPendingItems/d/r', NULL, (select id from chandigarh.eg_module where name='Rural'), 1, 'Search Pending Applications', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Rural'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Rural Search Pending Application Tasks'));


--Pending task Rural OC

INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Rural Search Pending OC Application Tasks', '/application/searchOCPendingItems/d/r', NULL, (select id from chandigarh.eg_module where name='Rural'), 1, 'Search Occupancy Certificate Pending Applications', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Rural'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Rural Search Pending OC Application Tasks'));


INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Rural Search OC Application Tasks', '/application/searchOCItems/d/r', NULL, (select id from chandigarh.eg_module where name='Rural'), 1, 'Search Occupancy Certificate Applications', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Rural'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Rural Search OC Application Tasks'));


--Service wise status report Rural

INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Rural Search service type wise status occupancy certificate', '/reports/servicewise-statusreport-oc/d/r', NULL, (select id from chandigarh.eg_module where name='Rural'), 1, 'Service type wise status report for occupancy certificate', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Rural'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Rural Search service type wise status occupancy certificate'));

--Collection reports Rural
INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Collection Summary-Head wise report-Rural', '/reports/collectionSummaryHeadwise/d/r', NULL, (select id from chandigarh.eg_module where name='Collection-Rural'), 1, 'Collection Summary-Head wise report', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Collection-Rural'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Collection Summary-Head wise report-Rural'));


INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Collection Summary-Rural', '/reports/collectionSummary/d/u', NULL, (select id from chandigarh.eg_module where name='Collection-Rural'), 1, 'Collection Summary', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Collection-Rural'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Collection Summary-Rural'));


--Reciept registery report rural
INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Receipt Register Report-Rural', '/reports/receiptRegister/d/r', NULL, (select id from chandigarh.eg_module where name='Collection-Rural'), 1, 'Receipt Register Report', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Collection-Rural'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Receipt Register Report-Rural'));


--DPC/Plith level Application Search
INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Urban Search PL Application Tasks', '/reports/searchPLItems/d/u', NULL, (select id from chandigarh.eg_module where name='Urban'), 1, 'Search DPC/ Plinth level Certificate Applications', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Urban Search PL Application Tasks'));

--DPC/Plith level Application Pending Search
INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Urban Search PL Application Pending Tasks', '/reports/searchPLPendingItems/d/u', NULL, (select id from chandigarh.eg_module where name='Urban'), 1, 'Search DPC/ Plinth level Certificate Pending Applications', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Urban Search PL Application Pending Tasks'));

--DPC/Plith level Application Search Rural
INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Rural Search PL Application Tasks', '/reports/searchPLItems/d/r', NULL, (select id from chandigarh.eg_module where name='Rural'), 1, 'Search DPC/ Plinth level Certificate Applications', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Rural'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Rural Search PL Application Tasks'));

--DPC/Plith level Application Pending Search Rural
INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Rural Search PL Application Pending Tasks', '/reports/searchPLPendingItems/d/r', NULL, (select id from chandigarh.eg_module where name='Rural'), 1, 'Search DPC/ Plinth level Certificate Pending Applications', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Rural'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Rural Search PL Application Pending Tasks'));
