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