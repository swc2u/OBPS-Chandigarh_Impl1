SELECT setval('chandigarh.seq_egbpa_mstr_bpafee_common',(SELECT max(id) FROM chandigarh.egbpa_mstr_bpafee_common));
SELECT setval('chandigarh.seq_egbpa_mstr_bpafeemapping ',(SELECT max(id) FROM chandigarh.egbpa_mstr_bpafeemapping));

insert into chandigarh.egbpa_mstr_bpafee_common 
values (nextval('chandigarh.seq_egbpa_mstr_bpafee_common'), null, 'TBP', 'Transfer of building plan fee', 'Transfer of building plan fee', 2, 0, 1, '2021-08-11 14:22:50.905', 1, '2021-08-11 14:22:50.905');
insert into chandigarh.egbpa_mstr_bpafee_common 
values (nextval('chandigarh.seq_egbpa_mstr_bpafee_common'), null, 'MCDW', 'Minor changes in doors and windows fee', 'Minor changes in doors and windows fee', 2, 0, 1, '2021-08-11 14:22:50.905', 1, '2021-08-11 14:22:50.905');
insert into chandigarh.egbpa_mstr_bpafee_common 
values (nextval('chandigarh.seq_egbpa_mstr_bpafee_common'), null, 'LFT', 'Lofts fee', 'Lofts fee', 2, 0, 1, '2021-08-11 14:22:50.905', 1, '2021-08-11 14:22:50.905');
insert into chandigarh.egbpa_mstr_bpafee_common 
values (nextval('chandigarh.seq_egbpa_mstr_bpafee_common'), null, 'NSG', 'Non standard gate fee', 'Non standard gate fee', 2, 0, 1, '2021-08-11 14:22:50.905', 1, '2021-08-11 14:22:50.905');
insert into chandigarh.egbpa_mstr_bpafee_common 
values (nextval('chandigarh.seq_egbpa_mstr_bpafee_common'), null, 'NCW', 'Niches on the common wall fee', 'Niches on the common wall fee', 2, 0, 1, '2021-08-11 14:22:50.905', 1, '2021-08-11 14:22:50.905');
insert into chandigarh.egbpa_mstr_bpafee_common 
values (nextval('chandigarh.seq_egbpa_mstr_bpafee_common'), null, 'DCM', 'DPC certificate missing fee', 'DPC certificate missing fee', 2, 0, 1, '2021-08-11 14:22:50.905', 1, '2021-08-11 14:22:50.905');
insert into chandigarh.egbpa_mstr_bpafee_common 
values (nextval('chandigarh.seq_egbpa_mstr_bpafee_common'), null, 'FCL', 'False Ceiling Fee', 'False Ceiling Fee', 2, 0, 1, '2021-08-11 14:22:50.905', 1, '2021-08-11 14:22:50.905');


insert into chandigarh.egbpa_mstr_bpafeemapping 
values (nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 59, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'TBP'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 61, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'TBP'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 62, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'TBP'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 64, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'TBP'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null),
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 59, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'MCDW'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 61, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'MCDW'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 62, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'MCDW'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 64, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'MCDW'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null),
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 59, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'LFT'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 61, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'LFT'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 62, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'LFT'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 64, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'LFT'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null),
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 59, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'NSG'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 61, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'NSG'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 62, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'NSG'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 64, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'NSG'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null),
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 59, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'NCW'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 61, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'NCW'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 62, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'NCW'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 64, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'NCW'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null),
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 59, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'DCM'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 61, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'DCM'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 62, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'DCM'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 64, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'DCM'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null),
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 59, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'FCL'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 61, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'FCL'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 62, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'FCL'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 64, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'FCL'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null),
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 59, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'AF'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 61, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'AF'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 62, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'AF'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null), 
(nextval('chandigarh.seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 64, 'AUTO', (select id from chandigarh.egbpa_mstr_bpafee_common where code = 'AF'), 0, 0, 1, '2020-09-18 05:55:20.745', 1, '2020-09-18 05:55:20.745', null);

