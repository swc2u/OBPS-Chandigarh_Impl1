update chandigarh.eg_wf_matrix 
set validactions = 'Forward,Revert'
where nextdesignation = 'MC Architect' 
and nextstatus = 'Document Verification Completed'
and objecttype = 'BpaApplication'
and additionalrule = 'Medium Risk';

update chandigarh.eg_wf_matrix 
set validactions = 'Forward,Reject,Revert'
where nextdesignation = 'SDO Building MC' 
and nextstatus = 'Document Verification Completed'
and objecttype = 'BpaApplication'
and additionalrule = 'Medium Risk';

update chandigarh.eg_wf_matrix 
set validactions = 'Forward,Reject,Revert'
where nextdesignation = 'Additional Commissioner' 
and nextstatus = 'Document Verification Completed'
and objecttype = 'BpaApplication'
and additionalrule = 'Medium Risk';

update chandigarh.eg_wf_matrix 
set validactions = 'Approve,Reject,Revert'
where nextdesignation = 'Additional Commissioner' 
and nextstatus = 'Approved'
and objecttype = 'BpaApplication'
and additionalrule = 'Medium Risk'
and currentstate = 'Final Approval Process initiated';