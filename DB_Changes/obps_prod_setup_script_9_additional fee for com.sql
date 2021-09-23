
select setval('seq_egbpa_mstr_bpafee_common',(select max(id) from egbpa_mstr_bpafee_common )) 

INSERT INTO chandigarh.egbpa_mstr_bpafee_common (id,glcode,code,"name",description,category,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES
	 (nextval('seq_egbpa_mstr_bpafee_common'),NULL,'AHF','Additional height fee','Additional height fee',2,0,1,now(),1,now());
	 

select setval('seq_eg_demand_reason_master',(select max(id) from eg_demand_reason_master )) 
	
INSERT INTO chandigarh.eg_demand_reason_master (id,reasonmaster,category,isdebit,"module",code,"order",create_date,modified_date,isdemand) VALUES
	 (nextval('seq_eg_demand_reason_master'),'Additional height fee',2,'N',444,'AHF',2,now(),now(),true);
	 

select setval('seq_egbpa_mstr_bpafeemapping',(select max(id) from EGBPA_MSTR_BPAFEEMAPPING )) 

INSERT INTO chandigarh.EGBPA_MSTR_BPAFEEMAPPING (id,applicationtype,feesubtype,servicetype,calculationtype,bpafeecommon,amount,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,applicationsubtype) VALUES
	 (nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype ems where description ='New Construction'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code ='AHF'),50.0,0,1,now(),1,now(),NULL);
	
INSERT INTO chandigarh.EGBPA_MSTR_BPAFEEMAPPING (id,applicationtype,feesubtype,servicetype,calculationtype,bpafeecommon,amount,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,applicationsubtype) VALUES
	 (nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype ems where description ='Reconstruction'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code ='AHF'),50.0,0,1,now(),1,now(),NULL);

INSERT INTO chandigarh.EGBPA_MSTR_BPAFEEMAPPING (id,applicationtype,feesubtype,servicetype,calculationtype,bpafeecommon,amount,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,applicationsubtype) VALUES
	 (nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype ems where description ='Alteration'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code ='AHF'),50.0,0,1,now(),1,now(),NULL);

INSERT INTO chandigarh.EGBPA_MSTR_BPAFEEMAPPING (id,applicationtype,feesubtype,servicetype,calculationtype,bpafeecommon,amount,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,applicationsubtype) VALUES
	 (nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype ems where description ='Addition or Extension'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code ='AHF'),50.0,0,1,now(),1,now(),NULL);

	
----

----
INSERT INTO chandigarh.chandigarh.eg_demand_reason (id,id_demand_reason_master,id_installment,percentage_basis,id_base_reason,create_date,modified_date,glcodeid) VALUES
	 (nextval('seq_eg_installment_master'),(select id from chandigarh.chandigarh.eg_demand_reason_master edrm where reasonmaster ='Additional height fee'),(select id from chandigarh.chandigarh.eg_installment_master eim where description ='BPA/21-22'),NULL,NULL,now(),now(),394);
