--A2K OC workflow modiifcation

DELETE FROM chandigarh.eg_wf_matrix
WHERE objecttype='OccupancyCertificate' and additionalrule='High Risk' and currentstate in ('Registered','Application documents verification in progress','Forward to Head Draftsman') ;

UPDATE chandigarh.eg_wf_matrix
SET nextaction='Forwarded to check NOC updation'
WHERE objecttype='OccupancyCertificate' and additionalrule='High Risk' and nextaction='Forwarded to verify application documents' and currentstate='NEW';
	
UPDATE chandigarh.eg_wf_matrix
SET currentstate='Registered',currentstatus='Registered'
WHERE objecttype='OccupancyCertificate' and additionalrule='High Risk' and currentstate='NOC updation in progress' and currentstatus='Document Reviewed';

UPDATE chandigarh.eg_wf_matrix
SET nextaction='Occupancy certificate fee collection pending',nextdesignation='Assistant Estate Officer'
WHERE objecttype='OccupancyCertificate' and additionalrule='High Risk' and currentstate='AEE Application Approval Pending' and nextaction='Forwarded to generate Occupancy Certificate' and nextdesignation='Estate Officer';

UPDATE chandigarh.eg_wf_matrix
SET nextaction='Occupancy certificate fee collection pending',nextdesignation='Estate Officer'
WHERE objecttype='OccupancyCertificate' and additionalrule='High Risk' and currentstate='Final Approval Process initiated' and nextaction='Forwarded to generate Occupancy Certificate' and nextdesignation='SDO Building Urban';

UPDATE chandigarh.eg_wf_matrix
SET nextaction='Occupancy certificate fee collection pending',nextdesignation='Estate Officer'
WHERE objecttype='OccupancyCertificate' and additionalrule='High Risk' and currentstate='Final Approval Process initiated' and nextaction='Forwarded to generate Occupancy Certificate' and nextdesignation='SDO Building Urban';

UPDATE chandigarh.eg_wf_matrix
SET nextdesignation='Estate Officer'
WHERE objecttype='OccupancyCertificate' and additionalrule='High Risk' and currentstate='Record Approved' and nextdesignation='SDO Building Urban';

UPDATE chandigarh.eg_wf_matrix
SET nextdesignation='Building Assistant Urban'
WHERE objecttype='OccupancyCertificate' and additionalrule='High Risk' and currentstate='Rejected' and nextdesignation='SDO Building Urban';

UPDATE chandigarh.eg_wf_matrix
SET pendingactions='Forwarded to check NOC updation',currentstate='Registered'
WHERE objecttype='OccupancyCertificate' and additionalrule='High Risk' and pendingactions='Forwarded to E- Assistant Estate Officer for Approval' and currentstate='AEE Application Approval Pending'
	

--B2K OC workflow modification (adding new action Rever to BA )

UPDATE chandigarh.eg_wf_matrix
SET validactions='Approve,Reject,Revert to BA'
WHERE objecttype='OccupancyCertificate' and additionalrule='Low Risk' 
and currentstatus='AEE Approval Completed' and currentstate='Final Approval Process initiated';



--JE in workflow

UPDATE chandigarh.eg_wf_matrix
SET nextaction='Forwarded to JE inspection',nextstate='JE inspection',nextdesignation='Junior Engineer Urban',nextstatus='Approved'
WHERE objecttype='OccupancyCertificate' and additionalrule='Low Risk' and pendingactions='Forwarded to generate Occupancy Certificate' and currentstate='Record Approved';
	

INSERT INTO chandigarh.eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES 
(nextval('chandigarh.seq_eg_wf_matrix'),'ANY','OccupancyCertificate','JE inspection','','Forwarded to JE inspection','','Low Risk','END','END','Junior Engineer Urban','Order Issued to Applicant','Inspection Approved,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL);


UPDATE chandigarh.eg_wf_matrix
SET nextaction='Forwarded to JE inspection',nextstate='JE inspection',nextdesignation='Junior Engineer Urban',nextstatus='Approved'
WHERE objecttype='OccupancyCertificate' and additionalrule='High Risk' and pendingactions='Forwarded to generate Occupancy Certificate' and currentstate='Record Approved';
	

INSERT INTO chandigarh.eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES 
(nextval('chandigarh.seq_eg_wf_matrix'),'ANY','OccupancyCertificate','JE inspection','','Forwarded to JE inspection','','High Risk','END','END','Junior Engineer Urban','Order Issued to Applicant','Inspection Approved,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL);


--Adding role action to generate final OC certificate API
	
Insert into chandigarh.EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('chandigarh.SEQ_EG_ACTION'),'Generate final Occupancy Certificate','/application/occupancy-certificate/generate-final-occupancy-certificate',null,(select id from eg_module where name='BPA Transanctions'),1,'Generate final Occupancy Certificate','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into chandigarh.eg_roleaction (roleid,actionid) values((select id from state.eg_role where name='BPA Approver'),(select id from chandigarh.eg_action where name = 'Generate final Occupancy Certificate' and contextroot = 'bpa'));
Insert into chandigarh.eg_roleaction (roleid,actionid) values((select id from state.eg_role where name='CITIZEN'),(select id from chandigarh.eg_action where name = 'Generate final Occupancy Certificate' and contextroot = 'bpa'));
Insert into chandigarh.eg_roleaction (roleid,actionid) values((select id from state.eg_role where name='BUSINESS'),(select id from chandigarh.eg_action where name = 'Generate final Occupancy Certificate' and contextroot = 'bpa'));