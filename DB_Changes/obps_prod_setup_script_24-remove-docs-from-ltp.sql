delete from chandigarh.egbpa_lp_files_common where lpdocument in (
select id from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'A site plan showing the position of Plot proposed to be built upon'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'))));

delete from chandigarh.egbpa_lp_files_common where lpdocument in (
select id from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Drainage plans'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'))));

delete from chandigarh.egbpa_lp_files_common where lpdocument in (
select id from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Elevations'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'))));

delete from chandigarh.egbpa_lp_files_common where lpdocument in (
select id from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Engineering drawings (structural)'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'))));

delete from chandigarh.egbpa_lp_files_common where lpdocument in (
select id from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Parking plan'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'))));

delete from chandigarh.egbpa_lp_files_common where lpdocument in (
select id from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Plans'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'))));

delete from chandigarh.egbpa_lp_files_common where lpdocument in (
select id from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Roof Plan'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'))));

delete from chandigarh.egbpa_lp_files_common where lpdocument in (
select id from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Sections'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'))));





delete from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'A site plan showing the position of Plot proposed to be built upon'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP')));

delete from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Drainage plans'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP')));

delete from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Elevations'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP')));

delete from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Engineering drawings (structural)'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP')));

delete from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Parking plan'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP')));

delete from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Plans'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP')));

delete from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Roof Plan'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP')));

delete from chandigarh.egbpa_lp_document_common 
where servicechecklist in (select id from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Sections'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP')));






delete from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'A site plan showing the position of Plot proposed to be built upon'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'));

delete from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Drainage plans'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'));

delete from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Elevations'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'));

delete from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Engineering drawings (structural)'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'));

delete from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Parking plan'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'));

delete from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Plans'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'));

delete from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Roof Plan'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'));

delete from chandigarh.egbpa_checklist_servicetype_mapping 
where checklist = (select id from chandigarh.eg_checklist 
where description = 'Sections'
and checklisttypeid = (select id from chandigarh.eg_checklist_type where code = 'LTP'));
