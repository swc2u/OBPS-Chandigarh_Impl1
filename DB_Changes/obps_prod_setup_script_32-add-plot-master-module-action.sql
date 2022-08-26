INSERT INTO chandigarh.eg_module (id, name, enabled, contextroot, parentmodule, displayname, ordernumber) 
VALUES (nextval('chandigarh.seq_eg_module'), 'Plot Master Data', true, 'edcr', (select id from chandigarh.eg_module where name='BPA'), 'Plot Master Data', 1);


INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Create Plot Master', '/plotMaster/create', NULL, (select id from chandigarh.eg_module where name='Plot Master Data'), 1, 'Create Plot Master', true, 'edcr', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Plot Master Data')),
(nextval('chandigarh.seq_eg_action'), 'View Plot Master', '/plotMaster/search', NULL, (select id from chandigarh.eg_module where name='Plot Master Data'), 1, 'View Plot Master', true, 'edcr', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Plot Master Data')),
(nextval('chandigarh.seq_eg_action'), 'Update Plot Master', '/plotMaster/update/', NULL, (select id from chandigarh.eg_module where name='Plot Master Data'), 1, 'Update Plot Master', true, 'edcr', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Plot Master Data'));


INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Create Plot Master')),
((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='View Plot Master')),
((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Update Plot Master'));