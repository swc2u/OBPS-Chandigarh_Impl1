SELECT setval('seq_egcl_servicecategory', (SELECT max(id) FROM chandigarh.egcl_servicecategory));

Insert into chandigarh.egcl_servicecategory (id, name, code, isactive, version, createdby, createddate, lastmodifiedby, lastmodifieddate) values
(nextval('seq_egcl_servicecategory'), 'Paytm Payment Gateway', 'PTPG', true, 0,  (select id from state.eg_user where username='egovernments'), now(), (select id from state.eg_user where username='egovernments'), now());

SELECT setval('seq_egcl_servicedetails', (SELECT max(id) FROM chandigarh.egcl_servicedetails));

Insert into chandigarh.egcl_servicedetails (id, name, serviceurl, isenabled, callbackurl, servicetype, code, fund, fundsource, functionary, vouchercreation, scheme, subscheme, servicecategory, isvoucherapproved, vouchercutoffdate, created_by, created_date, modified_by, modified_date, ordernumber) values
(nextval('seq_egcl_servicedetails'), 'Paytm Payment Gateway', 'securegw-stage.paytm.in/order/process', true, 'https://obps-test.chandigarhsmartcity.in/collection/citizen/onlineReceipt-acceptMessageFromPaymentGateway.action', 'P', 'PTPG', (select id from chandigarh.fund where code='01'), null, null, false, null, null, (select id from chandigarh.egcl_servicecategory where code='PTPG'), false, now(), 1, now(), 1, now(), null);


INSERT INTO chandigarh.EGCL_SERVICE_INSTRUMENTACCOUNTS (id,instrumenttype,servicedetails,chartofaccounts,createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES
(4,(select id from chandigarh.egf_instrumenttype where "type"='online'),(select id from chandigarh.egcl_servicedetails where "name"='Paytm Payment Gateway'),(SELECT id FROM chandigarh.chartofaccounts where "name"='Receivables control accounts-Property Taxes'),1,now(),1,now());