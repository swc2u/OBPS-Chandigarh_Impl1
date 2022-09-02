INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Urban Search occupancy certificate', '/application/searchOCPendingItems/d/u', NULL, (select id from chandigarh.eg_module where name='Urban'), 1, 'Search occupancy certificate', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Urban'));


INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Urban Search occupancy certificate'));