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
	

--B2K OC workflow modification (adding new action Rever to BA )

UPDATE chandigarh.eg_wf_matrix
SET validactions='Approve,Reject,Revert to BA'
WHERE objecttype='OccupancyCertificate' and additionalrule='Low Risk' 
and currentstatus='AEE Approval Completed' and currentstate='Final Approval Process initiated';