-- This is to delete BPA FEE COMMON - Code OF which is mapped twice in 'egbpa_mstr_bpafeemapping'
-- This Entries are used as a foreign key constraint in table egbpa_application_feedetails and need to be removed
-- from there first before deleting it from the 'egbpa_mstr_bpafeemapping' table

delete from chandigarh.egbpa_application_feedetails 
where bpafeemapping = (select MAX(id) from chandigarh.egbpa_mstr_bpafeemapping group by servicetype, applicationtype, bpafeecommon, feesubtype 
having applicationtype = 'OCCUPANCY_CERTIFICATE' and bpafeecommon = (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'OF') 
and servicetype = (select id from egbpa_mstr_servicetype where description = 'New Construction'));

delete from chandigarh.egbpa_application_feedetails 
where bpafeemapping = (select MAX(id) from chandigarh.egbpa_mstr_bpafeemapping group by servicetype, applicationtype, bpafeecommon, feesubtype 
having applicationtype = 'OCCUPANCY_CERTIFICATE' and bpafeecommon = (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'OF') 
and servicetype = (select id from egbpa_mstr_servicetype where description = 'Reconstruction'));

delete from chandigarh.egbpa_application_feedetails 
where bpafeemapping = (select MAX(id) from chandigarh.egbpa_mstr_bpafeemapping group by servicetype, applicationtype, bpafeecommon, feesubtype 
having applicationtype = 'OCCUPANCY_CERTIFICATE' and bpafeecommon = (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'OF') 
and servicetype = (select id from egbpa_mstr_servicetype where description = 'Addition or Extension'));

delete from chandigarh.egbpa_application_feedetails 
where bpafeemapping = (select MAX(id) from chandigarh.egbpa_mstr_bpafeemapping group by servicetype, applicationtype, bpafeecommon, feesubtype 
having applicationtype = 'OCCUPANCY_CERTIFICATE' and bpafeecommon = (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'OF') 
and servicetype = (select id from egbpa_mstr_servicetype where description = 'Change in occupancy'));

delete from chandigarh.egbpa_application_feedetails 
where bpafeemapping = (select MAX(id) from chandigarh.egbpa_mstr_bpafeemapping group by servicetype, applicationtype, bpafeecommon, feesubtype 
having applicationtype = 'OCCUPANCY_CERTIFICATE' and bpafeecommon = (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'OF') 
and servicetype = (select id from egbpa_mstr_servicetype where description = 'Alteration'));



delete from chandigarh.egbpa_mstr_bpafeemapping where id = 
(select MAX(id) from chandigarh.egbpa_mstr_bpafeemapping group by servicetype, applicationtype, bpafeecommon, feesubtype 
having applicationtype = 'OCCUPANCY_CERTIFICATE' and bpafeecommon = (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'OF') 
and servicetype = (select id from egbpa_mstr_servicetype where description = 'New Construction'));

delete from chandigarh.egbpa_mstr_bpafeemapping where id = 
(select MAX(id) from chandigarh.egbpa_mstr_bpafeemapping group by servicetype, applicationtype, bpafeecommon, feesubtype 
having applicationtype = 'OCCUPANCY_CERTIFICATE' and bpafeecommon = (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'OF') 
and servicetype = (select id from egbpa_mstr_servicetype where description = 'Reconstruction'));

delete from chandigarh.egbpa_mstr_bpafeemapping where id = 
(select MAX(id) from chandigarh.egbpa_mstr_bpafeemapping group by servicetype, applicationtype, bpafeecommon, feesubtype 
having applicationtype = 'OCCUPANCY_CERTIFICATE' and bpafeecommon = (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'OF') 
and servicetype = (select id from egbpa_mstr_servicetype where description = 'Addition or Extension'));

delete from chandigarh.egbpa_mstr_bpafeemapping where id = 
(select MAX(id) FROM chandigarh.egbpa_mstr_bpafeemapping group by servicetype, applicationtype, bpafeecommon, feesubtype 
having applicationtype = 'OCCUPANCY_CERTIFICATE' and bpafeecommon = (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'OF') 
and servicetype = (select id from egbpa_mstr_servicetype where description = 'Change in occupancy'));

delete from chandigarh.egbpa_mstr_bpafeemapping where id = 
(select MAX(id) from chandigarh.egbpa_mstr_bpafeemapping group by servicetype, applicationtype, bpafeecommon, feesubtype 
having applicationtype = 'OCCUPANCY_CERTIFICATE' and bpafeecommon = (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'OF') 
and servicetype = (select id from egbpa_mstr_servicetype where description = 'Alteration'));
WHERE id=208;