DELETE FROM chandigarh.eg_wf_matrix
WHERE additionalrule = 'Low Risk' AND objecttype = 'OccupancyCertificate';

SELECT setval('chandigarh.seq_eg_wf_matrix',(SELECT max(id) FROM chandigarh.eg_wf_matrix));

INSERT INTO chandigarh.eg_wf_matrix
(id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate, "version", enablefields, forwardenabled, smsemailenabled, nextref, rejectenabled)
VALUES(nextval('chandigarh.seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'NEW', '', 'Occupancy certificate application creation pending', '', 'Low Risk', 'Registered', 'Forwarded to verify application documents', 'Building Assistant Urban', 'Registered', 'Forward', NULL, NULL, current_date, '2099-04-01', 0, '', false, false, NULL, false);
INSERT INTO chandigarh.eg_wf_matrix
(id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate, "version", enablefields, forwardenabled, smsemailenabled, nextref, rejectenabled)
VALUES(nextval('chandigarh.seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Registered', 'Registered', 'Forwarded to verify application documents', '', 'Low Risk', 'Final Approval Process initiated', 'Occupancy certificate fee collection pending', 'Assistant Estate Officer', 'AEE Approval Completed', 'Forward', NULL, NULL, current_date, '2099-04-01', 0, '', false, false, NULL, false);
INSERT INTO chandigarh.eg_wf_matrix
(id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate, "version", enablefields, forwardenabled, smsemailenabled, nextref, rejectenabled)
VALUES(nextval('chandigarh.seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'AEE Approval Completed', 'Occupancy certificate fee collection pending', '', 'Low Risk', 'Record Approved', 'Occupancy certificate fee collection pending', 'Estate Officer', 'Approved', 'Approve,Reject', NULL, NULL, current_date, '2099-04-01', 0, '', false, false, NULL, false);
INSERT INTO chandigarh.eg_wf_matrix
(id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate, "version", enablefields, forwardenabled, smsemailenabled, nextref, rejectenabled)
VALUES(nextval('chandigarh.seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Record Approved', '', 'Occupancy certificate fee collection pending', '', 'Low Risk', 'Record Approved', 'Forwarded to generate Occupancy Certificate', 'Estate Officer', 'Approved', 'Generate Occupancy Certificate', NULL, NULL, current_date, '2099-04-01', 0, '', false, false, NULL, false);
INSERT INTO chandigarh.eg_wf_matrix
(id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate, "version", enablefields, forwardenabled, smsemailenabled, nextref, rejectenabled)
VALUES(nextval('chandigarh.seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Record Approved', '', 'Forwarded to generate Occupancy Certificate', '', 'Low Risk', 'END', 'END', 'Estate Officer', 'Order Issued to Applicant', 'Generate Occupancy Certificate', NULL, NULL, current_date, '2099-04-01', 0, '', false, false, NULL, false);
INSERT INTO chandigarh.eg_wf_matrix
(id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate, "version", enablefields, forwardenabled, smsemailenabled, nextref, rejectenabled)
VALUES(nextval('chandigarh.seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Rejected', '', 'Application is rejected by approver', '', 'Low Risk', 'generate rejection notice', 'Application is rejected by approver', 'Assistant Estate Officer', 'Rejected', 'Generate Rejection Notice', NULL, NULL, current_date, '2099-04-01', 0, '', false, false, NULL, false);
INSERT INTO chandigarh.eg_wf_matrix
(id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate, "version", enablefields, forwardenabled, smsemailenabled, nextref, rejectenabled)
VALUES(nextval('chandigarh.seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'LP Initiated', '', '', '', 'Low Risk', 'LP Created', 'Letter To Party Reply Pending', 'Assistant Estate Officer', 'Letter To Party Created', 'Forward', NULL, NULL, current_date, '2099-04-01', 0, '', false, false, NULL, false);
INSERT INTO chandigarh.eg_wf_matrix
(id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate, "version", enablefields, forwardenabled, smsemailenabled, nextref, rejectenabled)
VALUES(nextval('chandigarh.seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'LP Created', '', '', '', 'Low Risk', 'LP Reply Received', 'Forward to LP Initiator pending', 'Assistant Estate Officer', 'Letter To Party Reply Received', 'Forward', NULL, NULL, current_date, '2099-04-01', 0, '', false, false, NULL, false);
