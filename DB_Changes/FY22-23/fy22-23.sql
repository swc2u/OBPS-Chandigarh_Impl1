Insert into chandigarh.eg_installment_master (ID,INSTALLMENT_NUM,INSTALLMENT_YEAR,START_DATE,END_DATE,ID_MODULE,LASTUPDATEDTIMESTAMP, DESCRIPTION,INSTALLMENT_TYPE) values (nextval('chandigarh.SEQ_EG_INSTALLMENT_MASTER'),201804,to_date('01-04-22','DD-MM-YY'),to_date('01-04-22','DD-MM-YY'),to_date('31-03-23','DD-MM-YY'), (select id from chandigarh.eg_module where name = 'BPA' and parentmodule is null), current_timestamp,'BPA/22-23','Yearly');



INSERT INTO chandigarh.financialyear (id, financialyear, startingdate, endingdate, isactive, createddate, lastmodifieddate,lastmodifiedby,createdby,version, isactiveforposting, isclosed, transferclosingbalance) VALUES (nextval('chandigarh.seq_financialyear'), '2022-23', '2022-04-01 00:00:00', '2023-03-31 00:00:00', true, now(), now(), 1,1,0, true, false, false);
	 
	 -- prod
	 
	 select setval('chandigarh.seq_eg_demand_reason', (select max(id) from chandigarh.EG_DEMAND_REASON )) 
	 
	 INSERT INTO chandigarh.eg_demand_reason (id,id_demand_reason_master,id_installment,percentage_basis,id_base_reason,create_date,modified_date,glcodeid) VALUES
	 (nextval('chandigarh.seq_eg_demand_reason'),72,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),73,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),74,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),76,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),76,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),77,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),71,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),78,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),79,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),80,8,NULL,NULL,current_timestamp,current_timestamp,394);
INSERT INTO chandigarh.eg_demand_reason (id,id_demand_reason_master,id_installment,percentage_basis,id_base_reason,create_date,modified_date,glcodeid) VALUES
	 (nextval('chandigarh.seq_eg_demand_reason'),81,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),83,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),84,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),86,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),86,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),87,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),90,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),92,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),93,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),94,8,NULL,NULL,current_timestamp,current_timestamp,394);
INSERT INTO chandigarh.eg_demand_reason (id,id_demand_reason_master,id_installment,percentage_basis,id_base_reason,create_date,modified_date,glcodeid) VALUES
	 (nextval('chandigarh.seq_eg_demand_reason'),70,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),88,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),89,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),91,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),95,8,NULL,NULL,current_timestamp,current_timestamp,394),
	 (nextval('chandigarh.seq_eg_demand_reason'),75,8,NULL,NULL,current_timestamp,current_timestamp,394);