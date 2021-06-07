-- select the chandigarh first

SELECT setval('seq_bank', (SELECT max(id) FROM chandigarh.bank));

INSERT INTO bank (id, code, "name", narration, isactive, "type", createddate, lastmodifieddate, lastmodifiedby, "version", createdby) VALUES(nextval('seq_bank'), 'SBI', 'Sbi bank', '', true, 'Main branch', now(), now(), 1, 0, 1);

SELECT setval('seq_bankbranch', (SELECT max(id) FROM chandigarh.bankbranch));

insert into bankbranch (id, branchcode, branchname,  branchaddress1, branchcity, bankid, isactive, createddate, lastmodifieddate, lastmodifiedby, version, createdby) values(nextval('seq_bankbranch'), '001', 'Main branch', 'Chandigarh', 'Chandigarh', (select id from bank where code='SBI'), true, now(), now(), 1, 0, 1); 

delete from chartofaccounts where glcode like '45021%';

select setval('seq_chartofaccounts',(select max(id) from chartofaccounts )) 

INSERT INTO chartofaccounts (id, glcode, name, isactiveforposting, parentid, createddate, type, classification, functionreqd, budgetcheckreq, majorcode, createdby) VALUES (nextval('seq_chartofaccounts'), '4502101', 'Sbi Bank Chandigarh branch-918020071220208', true, (select id from chartofaccounts where glcode='45021'), now(), 'A', 4, false, false, '450', 1);


select setval('seq_bankaccount', (select max(id) from bankaccount )) 

insert into bankaccount (id, branchid, accountnumber, isactive, glcodeid, fundid, type, createdby, lastmodifiedby, createddate, lastmodifieddate, version) values(nextval('seq_bankaccount'), (select id from bankbranch where branchcode='001' and bankid = (select id from bank where code ='SBI')), '918020071220208', true, (select id from chartofaccounts where glcode='4502101'), (select id from fund where code='01'), 'RECEIPTS', 1, 1, now(), now(), 0);


update egf_instrumentaccountcodes set glcodeid = (select id from chartofaccounts where glcode='4502101') where typeid =(select id from egf_instrumenttype where type='online');

update egcl_service_instrumentaccounts set chartofaccounts = (select id from chartofaccounts where glcode='4502101') where servicedetails= (select id from egcl_servicedetails where code='APG'); 

update egcl_service_instrumentaccounts set chartofaccounts = (select id from chartofaccounts where glcode='4502101') where servicedetails= (select id from egcl_servicedetails where code='PUPG');

update egcl_service_instrumentaccounts set chartofaccounts = (select id from chartofaccounts where glcode='4502101') where servicedetails= (select id from egcl_servicedetails where code='SBI'); 

update egcl_service_instrumentaccounts set chartofaccounts = (select id from chartofaccounts where glcode='4502101') where servicedetails= (select id from egcl_servicedetails where code='PTPG'); 
