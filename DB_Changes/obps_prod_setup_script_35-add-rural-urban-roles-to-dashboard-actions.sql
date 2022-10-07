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
