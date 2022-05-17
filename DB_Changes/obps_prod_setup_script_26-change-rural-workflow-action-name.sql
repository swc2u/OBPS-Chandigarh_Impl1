UPDATE chandigarh.eg_wf_matrix
SET validactions='Forward,Revert To Previous Reviewer'
WHERE objecttype = 'BpaApplication' and currentstate = 'Registered' and nextstate ='Property documents verification initiated'
and nextdesignation = 'MC Architect' and additionalrule = 'Medium Risk';

UPDATE chandigarh.eg_wf_matrix
SET validactions='Forward,Reject,Revert To Previous Reviewer'
WHERE objecttype = 'BpaApplication' and currentstate = 'Property documents verification initiated' and nextstate ='NOC updation in progress'
and nextdesignation = 'SDO Building MC' and additionalrule = 'Medium Risk';

UPDATE chandigarh.eg_wf_matrix
SET validactions='Forward,Reject,Revert To Previous Reviewer'
WHERE objecttype = 'BpaApplication' and currentstate = 'NOC updation in progress' and nextstate ='Final Approval Process initiated'
and nextdesignation = 'Additional Commissioner' and additionalrule = 'Medium Risk';

UPDATE chandigarh.eg_wf_matrix
SET validactions='Approve,Reject,Revert To Previous Reviewer'
WHERE objecttype = 'BpaApplication' and currentstate = 'Final Approval Process initiated' and nextstate ='Permit Fee Collected'
and nextdesignation = 'Additional Commissioner' and additionalrule = 'Medium Risk';