SELECT setval('seq_egcl_servicecategory', (SELECT max(id) FROM chandigarh.egcl_servicecategory));

Insert into chandigarh.egcl_servicecategory (id, name, code, isactive, version, createdby, createddate, lastmodifiedby, lastmodifieddate) values
(nextval('seq_egcl_servicecategory'), 'Paytm Payment Gateway', 'PTPG', true, 0,  (select id from state.eg_user where username='egovernments'), now(), (select id from state.eg_user where username='egovernments'), now());

SELECT setval('seq_egcl_servicedetails', (SELECT max(id) FROM chandigarh.egcl_servicedetails));

Insert into chandigarh.egcl_servicedetails (id, name, serviceurl, isenabled, callbackurl, servicetype, code, fund, fundsource, functionary, vouchercreation, scheme, subscheme, servicecategory, isvoucherapproved, vouchercutoffdate, created_by, created_date, modified_by, modified_date, ordernumber) values
(nextval('seq_egcl_servicedetails'), 'Paytm Payment Gateway', 'securegw-stage.paytm.in/order/process', true, 'https://obps-test.chandigarhsmartcity.in/collection/citizen/onlineReceipt-acceptMessageFromPaymentGateway.action', 'P', 'PTPG', (select id from chandigarh.fund where code='01'), null, null, false, null, null, (select id from chandigarh.egcl_servicecategory where code='PTPG'), false, now(), 1, now(), 1, now(), null);


INSERT INTO chandigarh.EGCL_SERVICE_INSTRUMENTACCOUNTS (id,instrumenttype,servicedetails,chartofaccounts,createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES
(4,(select id from chandigarh.egf_instrumenttype where "type"='online'),(select id from chandigarh.egcl_servicedetails where "name"='Paytm Payment Gateway'),(SELECT id FROM chandigarh.chartofaccounts where "name"='Receivables control accounts-Property Taxes'),1,now(),1,now());



--MIS Report start

INSERT INTO chandigarh.eg_feature(id, "name", description, "module", "version") values
(186, 'Service type wise status report', 'Service type wise status report', 444, 4),
(187, 'Search applications', 'Search applications', 444, 5),
(188, 'Search Pending applications', 'Search Pending applications', 444, 6);

INSERT INTO chandigarh.eg_feature_action("action", feature) values
(1923, 186),
(1871, 187),
(2203, 188);

--MIS Report end

delete from chandigarh.eg_wf_matrix where objecttype='BpaApplication' and additionalrule = 'Medium Risk';

INSERT INTO chandigarh.eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES
	 (310,'ANY','BpaApplication','Rejected','','','','Medium Risk','generate rejection notice','Application is rejected by approver','MC Architect','Rejected','Generate Rejection Notice',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL),
	 (312,'ANY','BpaApplication','Property documents verification initiated','Registered','Forwarded to property documents verification','','Medium Risk','NOC updation in progress','Forwarded to check NOC updation','SDO Building MC','Document Verification Completed','Forward,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL),
	 (313,'ANY','BpaApplication','NOC updation in progress','','','','Medium Risk','Final Approval Process initiated','Permit Fee Collection Pending','Additional Commissioner','Document Verification Completed','Forward,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL),
	 (314,'ANY','BpaApplication','Final Approval Process initiated','','Permit Fee Collection Pending','','Medium Risk','Permit Fee Collected','Forwarded to update permit conditions','Additional Commissioner','Approved','Approve,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL),
	 (315,'ANY','BpaApplication','Permit Fee Collected','','Forwarded to update permit conditions','','Medium Risk','Record Approved','Forwarded to generate permit order','Additional Commissioner','Approved','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL),
	 (316,'ANY','BpaApplication','Permit Fee Collected','','Forwarded to generate permit order','','Medium Risk','Record Approved','Forwarded to generate permit order','Additional Commissioner','Approved','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL),
	 (317,'ANY','BpaApplication','Record Approved','','','','Medium Risk','END','END','Additional Commissioner','Order Issued to Applicant','Generate Permit Order',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL),
	 (318,'ANY','BpaApplication','LP Initiated','','','','Medium Risk','LP Created','Letter To Party Reply Pending','MC Architect','Letter To Party Created','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL),
	 (319,'ANY','BpaApplication','LP Created','','','','Medium Risk','LP Reply Received','Forward to LP Initiator pending','MC Architect','Letter To Party Reply Received','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL),
	 (481,'ANY','BpaApplication','NEW','','Forward to section clerk is pending','','Medium Risk','Registered','Forward to junior engineer is pending','Junior Engineer Rural','Registered','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL);
INSERT INTO chandigarh.eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES
	 (482,'ANY','BpaApplication','Registered','','Forward to junior engineer is pending','','Medium Risk','Registered','Forward to tehsildar is pending','Tehsildar','Registered','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL),
	 (483,'ANY','BpaApplication','Registered','','Forward to tehsildar is pending','','Medium Risk','Property documents verification initiated','Forwarded to property documents verification','MC Architect','Document Verification Completed','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL);