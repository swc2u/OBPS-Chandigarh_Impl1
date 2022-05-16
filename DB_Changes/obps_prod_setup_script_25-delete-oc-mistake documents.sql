delete from chandigarh.egbpa_oc_documents 
where "document" in (select id from chandigarh.egbpa_general_document 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist in (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-25')));

delete from chandigarh.egbpa_oc_documents 
where "document" in (select id from chandigarh.egbpa_general_document 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist in (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-26')));

delete from chandigarh.egbpa_general_document_files  
where documentid  in (select id from chandigarh.egbpa_general_document 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist in (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-26')));

delete from chandigarh.egbpa_general_document_files  
where documentid  in (select id from chandigarh.egbpa_general_document 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist in (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-25')));

delete from chandigarh.egbpa_general_document 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist in (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-26'));

delete from chandigarh.egbpa_general_document 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist in (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-25'));

delete from chandigarh.egbpa_checklist_servicetype_mapping
where checklist = (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-25');

delete from chandigarh.egbpa_checklist_servicetype_mapping
where checklist = (select id from chandigarh.eg_checklist where code = 'OCGENERALDOC-26');

delete from chandigarh.eg_checklist where code = 'OCGENERALDOC-25';

delete from chandigarh.eg_checklist where code = 'OCGENERALDOC-25';