INSERT INTO chandigarh.eg_module (id, name, enabled, contextroot, parentmodule, displayname, ordernumber) 
VALUES (nextval('chandigarh.seq_eg_module'), 'Re-stamp', true, 'bpa', (select id from chandigarh.eg_module where name='BPA'), 'Re-stamp BPA documents', 1);


INSERT INTO chandigarh.eg_action (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) 
VALUES  (nextval('chandigarh.seq_eg_action'), 'Re-stamp BPA documents', '/reStamp', NULL, (select id from chandigarh.eg_module where name='Re-stamp'), 1, 'Re-stamp BPA documents', true, 'bpa', 0, 1, now(), 1, now(), (select id from chandigarh.eg_module where name='Re-stamp'));


INSERT INTO chandigarh.eg_roleaction(roleid, actionid)
VALUES ((select id from state.eg_role where name='SYSTEM'), (select id from chandigarh.eg_action where name='Re-stamp BPA documents'));