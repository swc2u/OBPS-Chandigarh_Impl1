update chandigarh.eg_wf_matrix
set validactions='Forward For Payment,Reject,Revert to HDM'
where objecttype = 'BpaApplication' and currentstate = 'Application Approval Pending' 
and additionalrule = 'Low Risk' and nextstate = 'Record Approved' and nextdesignation = 'SDO Building Urban';
