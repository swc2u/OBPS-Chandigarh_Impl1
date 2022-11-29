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