
update chandigarh.egbpa_mstr_bpafee_common set "name" = 'Fee for partitions on ground floor on multi-bays shops', description = 'Fee for partitions on ground floor on multi-bays shops' where code = 'CRC';

update chandigarh.egbpa_mstr_bpafeemapping set bpafeecommon = (select id from  chandigarh.egbpa_mstr_bpafee_common where  code ='AHF') where applicationtype in ('OCCUPANCY_CERTIFICATE') and  servicetype in (59,61,62,64) and  bpafeecommon = (select id from  chandigarh.egbpa_mstr_bpafee_common where code ='CSDC');



insert into chandigarh.egbpa_mstr_bpafee_common 
values (25, null, 'CBZ', 'Fee for excess coverage beyond zoning 6', 'Fee for excess coverage beyond zoning 6', 2, 0, 1, '2021-08-11 14:22:50.905', 1, '2021-08-11 14:22:50.905');


insert into chandigarh.egbpa_mstr_bpafeemapping 
values 
(224, 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 59, 'AUTO', 25, 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(225, 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 61, 'AUTO', 25, 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(226, 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 62, 'AUTO', 25, 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(227, 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 64, 'AUTO', 25, 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null);


-- plot number length increased 
alter table chandigarh.egbpa_sitedetail alter column mspplotnumber type varchar(128);

alter table chandigarh.egbpa_application alter column plotnumber type varchar(128);