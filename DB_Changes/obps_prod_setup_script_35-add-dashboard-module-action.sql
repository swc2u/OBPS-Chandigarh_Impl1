--TO add Dashboard ,Rural,Urban Module
INSERT INTO chandigarh.eg_module (id, name, enabled, contextroot, parentmodule, displayname, ordernumber) 
VALUES (nextval('chandigarh.seq_eg_module'), 'Dashboard', true, 'Dashboard', null, 'Dashboard', 1);

INSERT INTO chandigarh.eg_module (id, name, enabled, contextroot, parentmodule, displayname, ordernumber) 
VALUES (nextval('chandigarh.seq_eg_module'), 'Urban', true, 'Urban', (select id from chandigarh.eg_module where name='Dashboard'), 'Urban', 1);

INSERT INTO chandigarh.eg_module (id, name, enabled, contextroot, parentmodule, displayname, ordernumber) 
VALUES (nextval('chandigarh.seq_eg_module'), 'Rural', true, 'Rural', (select id from chandigarh.eg_module where name='Dashboard'), 'Rural', 1);

--To add action to dashboard Module

INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Architects statistical', '/reports/architects-statistical', NULL, (select id from chandigarh.eg_module where name='Dashboard'), 1, 'Architects Statistical', true, 'edcr', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Dashboard'));

INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Architects statistical'));