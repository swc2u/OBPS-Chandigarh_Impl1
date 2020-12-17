-------------------------04-Dec-2020---------------------------

INSERT INTO chandigarh.eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES 
(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','LP Initiated','','','','Low Risk','LP Created','Letter To Party Reply Pending','SDO Building Urban','Letter To Party Created','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('seq_eg_wf_matrix'),'ANY','BpaApplication','LP Created','','','','Low Risk','LP Reply Received','Forward to LP Initiator pending','SDO Building Urban','Letter To Party Reply Received','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
;

update chandigarh.eg_wf_matrix set nextstatus = 'Document Verification Completed' where objecttype = 'BpaApplication' and additionalrule = 'Low Risk' and currentstate = 'Fee details verification initiated';