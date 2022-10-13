--Old dashboard search BPA report**************************************************************************************************************************

INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Urban Search Application', '/application/searchBPAItems/d/u', NULL, (select id from chandigarh.eg_module where name='Urban'), 1, 'Search BPA Applications', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Urban Search Application'));


INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Rural Search Application', '/application/searchBPAItems/d/r', NULL, (select id from chandigarh.eg_module where name='Rural'), 1, 'Search BPA Applications', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Rural'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Rural Search Application'));


INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Rural Service type wise status report', '/reports/servicewise-statusreport/d/r', NULL, (select id from chandigarh.eg_module where name='Rural'), 1, 'Service type wise status report', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Rural'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Rural Service type wise status report'));


INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Urban Service type wise status report', '/reports/servicewise-statusreport/d/u', NULL, (select id from chandigarh.eg_module where name='Urban'), 1, 'Service type wise status report', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Urban'));


INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Urban Service type wise status report'));


--End of old report Script************************************************************************************************************************************

--OC application Search
INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Urban Search occupancy certificate', '/application/searchOCItems/d/u', NULL, (select id from chandigarh.eg_module where name='Urban'), 1, 'Search occupancy certificate', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Urban'));


INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Urban Search occupancy certificate'));


---Collection Module

INSERT INTO chandigarh.eg_module (id, name, enabled, contextroot, parentmodule, displayname, ordernumber) 
VALUES (nextval('chandigarh.seq_eg_module'), 'Collection-Urban', true, 'collection', (select id from chandigarh.eg_module where name='Urban'), 'Collection', 1);

INSERT INTO chandigarh.eg_module (id, name, enabled, contextroot, parentmodule, displayname, ordernumber) 
VALUES (nextval('chandigarh.seq_eg_module'), 'Collection-Rural', true, 'collection', (select id from chandigarh.eg_module where name='Rural'), 'Collection', 1);


INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Collection Summary-Head wise report-Urban', '/reports/collectionSummaryHeadwise/d/u', NULL, (select id from chandigarh.eg_module where name='Collection-Urban'), 1, 'Collection Summary-Head wise report', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Collection-Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Collection Summary-Head wise report-Urban'));


INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Collection Summary-Urban', '/reports/collectionSummary/d/u', NULL, (select id from chandigarh.eg_module where name='Collection-Urban'), 1, 'Collection Summary', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Collection-Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Collection Summary-Urban'));


INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'OC Collection Summary-Head wise report-Urban', '/reports/collectionSummaryHeadwiseOC/d/u', NULL, (select id from chandigarh.eg_module where name='Collection-Urban'), 1, 'Collection Summary-Head wise report for Occupancy Certificate', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Collection-Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='OC Collection Summary-Head wise report-Urban'));

--OC Servicetypewise search
INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Urban Search service type wise status occupancy certificate', '/reports/servicewise-statusreport-oc/d/u', NULL, (select id from chandigarh.eg_module where name='Urban'), 1, 'Service type wise status occupancy certificate', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Urban'));


INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Urban Search service type wise status occupancy certificate'));

--Reciept registery report
INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Receipt Register Report-Urban', '/reports/receiptRegister/d/u', NULL, (select id from chandigarh.eg_module where name='Collection-Urban'), 1, 'Receipt Register Report', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Collection-Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Receipt Register Report-Urban'));

--OC collection Summary report Urban
INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'OC Collection Summary-Urban', '/reports/collectionSummaryOC/d/u', NULL, (select id from chandigarh.eg_module where name='Collection-Urban'), 1, 'Collection Summary For Occupancy Certificate', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Collection-Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='OC Collection Summary-Urban'));


--OC Reciept registery report
INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'OC Receipt Register Report-Urban', '/reports/receiptRegister-oc/d/u', NULL, (select id from chandigarh.eg_module where name='Collection-Urban'), 1, 'Occupancy Certificate Receipt Register Report', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Collection-Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='OC Receipt Register Report-Urban'));


--Rural Dashboard

--Pending task
UPDATE chandigarh.eg_action
	SET url='/application/searchBPAItems/d/u'
	WHERE name='Urban Search Application';



INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Urban Search Pending Application Tasks', '/application/searchPendingItems/d/u', NULL, (select id from chandigarh.eg_module where name='Urban'), 1, 'Search Pending Applications', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Urban Search Pending Application Tasks'));




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


--servicewise status report for PL/DPC	
	
INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Urban Search service type wise status of PL certificate', '/reports/servicewise-statusreport-pl/d/u', NULL, (select id from chandigarh.eg_module where name='Urban'), 1, 'Service type wise status of PL/DPC certificate', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Urban'));


INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Urban Search service type wise status of PL certificate')),
((select id from state.eg_role where name='Urban Dashboard'), (select id from chandigarh.eg_action where name='Urban Search service type wise status of PL certificate'));


INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Rural Search service type wise status of PL certificate', '/reports/servicewise-statusreport-pl/d/r', NULL, (select id from chandigarh.eg_module where name='Rural'), 1, 'Service type wise status of PL/DPC certificate', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Rural'));


INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Rural Search service type wise status of PL certificate')),
((select id from state.eg_role where name='Rural Dashboard'), (select id from chandigarh.eg_action where name='Rural Search service type wise status of PL certificate'));


--Dashoard roles 

--Collection dashboard
INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Urban Dashboard'), (select id from chandigarh.eg_action where name='Collection Summary-Head wise report-Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Urban Dashboard'), (select id from chandigarh.eg_action where name='Collection Summary-Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Urban Dashboard'), (select id from chandigarh.eg_action where name='OC Collection Summary-Head wise report-Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Urban Dashboard'), (select id from chandigarh.eg_action where name='Urban Search service type wise status occupancy certificate'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Urban Dashboard'), (select id from chandigarh.eg_action where name='Receipt Register Report-Urban'));


INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Urban Dashboard'), (select id from chandigarh.eg_action where name='OC Collection Summary-Urban'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Urban Dashboard'), (select id from chandigarh.eg_action where name='OC Receipt Register Report-Urban'));

--Search Urban OC 
INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Urban Dashboard'), (select id from chandigarh.eg_action where name='Urban Search occupancy certificate'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Urban Dashboard'), (select id from chandigarh.eg_action where name='Urban Search Pending Application Tasks'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Urban Dashboard'), (select id from chandigarh.eg_action where name='Urban Search OC Pending Application Tasks'));

--Search Rural applications
INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Rural Dashboard'), (select id from chandigarh.eg_action where name='Rural Search Pending Application Tasks'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Rural Dashboard'), (select id from chandigarh.eg_action where name='Rural Search Pending OC Application Tasks'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Rural Dashboard'), (select id from chandigarh.eg_action where name='Rural Search OC Application Tasks'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Rural Dashboard'), (select id from chandigarh.eg_action where name='Rural Search service type wise status occupancy certificate'));

--Rural Collection dashboard
INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Rural Dashboard'), (select id from chandigarh.eg_action where name='Collection Summary-Head wise report-Rural'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Rural Dashboard'), (select id from chandigarh.eg_action where name='Collection Summary-Rural'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Rural Dashboard'), (select id from chandigarh.eg_action where name='Receipt Register Report-Rural'));

--Urban search Plinth level applications
INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Urban Dashboard'), (select id from chandigarh.eg_action where name='Urban Search PL Application Tasks'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Urban Dashboard'), (select id from chandigarh.eg_action where name='Urban Search PL Application Pending Tasks'));

--Rural search Plinth level applications 
INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Rural Dashboard'), (select id from chandigarh.eg_action where name='Rural Search PL Application Tasks'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='Rural Dashboard'), (select id from chandigarh.eg_action where name='Rural Search PL Application Pending Tasks'));


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