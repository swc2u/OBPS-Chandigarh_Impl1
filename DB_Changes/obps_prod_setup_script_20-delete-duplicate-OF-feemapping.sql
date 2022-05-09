DELETE FROM chandigarh.egbpa_application_feedetails 
WHERE bpafeemapping = 164;
DELETE FROM chandigarh.egbpa_application_feedetails 
WHERE bpafeemapping = 175;
DELETE FROM chandigarh.egbpa_application_feedetails 
WHERE bpafeemapping = 186;
DELETE FROM chandigarh.egbpa_application_feedetails 
WHERE bpafeemapping = 197;
DELETE FROM chandigarh.egbpa_application_feedetails 
WHERE bpafeemapping = 208;

DELETE FROM chandigarh.egbpa_mstr_bpafeemapping
WHERE id=164;
DELETE FROM chandigarh.egbpa_mstr_bpafeemapping
WHERE id=175;
DELETE FROM chandigarh.egbpa_mstr_bpafeemapping
WHERE id=186;
DELETE FROM chandigarh.egbpa_mstr_bpafeemapping
WHERE id=197;
DELETE FROM chandigarh.egbpa_mstr_bpafeemapping
WHERE id=208;