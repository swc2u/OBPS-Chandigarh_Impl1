---Comments size changes--

ALTER TABLE chandigarh.eg_wf_state_history ALTER COLUMN "comments" type varchar(4000);

ALTER TABLE state.eg_wf_state_history ALTER COLUMN "comments" type varchar(4000);

ALTER TABLE chandigarh.eg_wf_states ALTER COLUMN "comments" type varchar(4000);

ALTER TABLE state.eg_wf_states ALTER COLUMN "comments" type varchar(4000);

update chandigarh.egbpa_mstr_bpafee_common set "name"='Transfer fee', description='Transfer fee' where code='OTF';
update chandigarh.eg_demand_reason_master set reasonmaster='Transfer fee' where code='OTF';

update egbpa_mstr_bpafeemapping set applicationtype = 'PERMIT_APPLICATION' where bpafeecommon = (select id from chandigarh.egbpa_mstr_bpafee_common where code='OTF');

update chandigarh.egbpa_mstr_bpafee_common set "name"='Construction & Demolision', description='Construction & Demolision' where code='DF';
update chandigarh.eg_demand_reason_master set reasonmaster='Transfer fee' where code='DF';

delete from egbpa_mstr_bpafeemapping where applicationtype='PERMIT_APPLICATION' and feesubtype='SANCTION_FEE' and bpafeecommon=(select id from chandigarh.egbpa_mstr_bpafee_common where code='DF');

INSERT INTO chandigarh.egbpa_mstr_bpafeemapping (id,applicationtype,feesubtype,servicetype,calculationtype,bpafeecommon,amount,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,applicationsubtype) VALUES 
(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='01'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='DF'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='03'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='DF'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='04'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='DF'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='06'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='DF'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='07'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='DF'),0,0,1,now(),1,now(),NULL)
;

update chandigarh.egbpa_mstr_bpafee_common set "name"='Fee for allotment of new number', description='Fee for allotment of new number' where code='AFCW';
update chandigarh.eg_demand_reason_master set reasonmaster='Fee for allotment of new number' where code='AFCW';

delete from egbpa_mstr_bpafeemapping where applicationtype='PERMIT_APPLICATION' and feesubtype='SANCTION_FEE' and bpafeecommon=(select id from chandigarh.egbpa_mstr_bpafee_common where code='AFCW');

INSERT INTO chandigarh.egbpa_mstr_bpafeemapping (id,applicationtype,feesubtype,servicetype,calculationtype,bpafeecommon,amount,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,applicationsubtype) VALUES 
(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='01'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='AFCW'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='03'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='AFCW'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='04'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='AFCW'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='06'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='AFCW'),0,0,1,now(),1,now(),NULL)
,(nextval('seq_egbpa_mstr_bpafeemapping'),'PERMIT_APPLICATION','SANCTION_FEE',(select id from chandigarh.egbpa_mstr_servicetype where code='07'),'AUTO',(select id from chandigarh.egbpa_mstr_bpafee_common where code='AFCW'),0,0,1,now(),1,now(),NULL)
;


ALTER TABLE chandigarh.egbpa_application ADD COLUMN sector VARCHAR(20), ADD COLUMN plotnumber VARCHAR(20), ADD COLUMN filenumber VARCHAR(20);
