SELECT setval('chandigarh.seq_eg_demand_reason_master',(SELECT max(id) FROM chandigarh.eg_demand_reason_master));

INSERT INTO chandigarh.eg_demand_reason_master
(id, reasonmaster, category, isdebit, "module", code, "order", create_date, modified_date, isdemand)
VALUES(nextval('chandigarh.seq_eg_demand_reason_master'), 'Fee for excess coverage beyond zoning 6', 2, 'N', 444, 'CBZ', 2, '2021-08-11 14:22:55.609', '2021-08-11 14:22:55.609', true);

INSERT INTO chandigarh.eg_demand_reason_master
(id, reasonmaster, category, isdebit, "module", code, "order", create_date, modified_date, isdemand)
VALUES(nextval('chandigarh.seq_eg_demand_reason_master'), 'Transfer of building plan fee', 2, 'N', 444, 'TBP', 2, '2021-08-11 14:22:55.609', '2021-08-11 14:22:55.609', true);

INSERT INTO chandigarh.eg_demand_reason_master
(id, reasonmaster, category, isdebit, "module", code, "order", create_date, modified_date, isdemand)
VALUES(nextval('chandigarh.seq_eg_demand_reason_master'), 'Minor changes in doors and windows fee', 2, 'N', 444, 'MCDW', 2, '2021-08-11 14:22:55.609', '2021-08-11 14:22:55.609', true);

INSERT INTO chandigarh.eg_demand_reason_master
(id, reasonmaster, category, isdebit, "module", code, "order", create_date, modified_date, isdemand)
VALUES(nextval('chandigarh.seq_eg_demand_reason_master'), 'Lofts fee', 2, 'N', 444, 'LFT', 2, '2021-08-11 14:22:55.609', '2021-08-11 14:22:55.609', true);

INSERT INTO chandigarh.eg_demand_reason_master
(id, reasonmaster, category, isdebit, "module", code, "order", create_date, modified_date, isdemand)
VALUES(nextval('chandigarh.seq_eg_demand_reason_master'), 'Non standard gate fee', 2, 'N', 444, 'NSG', 2, '2021-08-11 14:22:55.609', '2021-08-11 14:22:55.609', true);

INSERT INTO chandigarh.eg_demand_reason_master
(id, reasonmaster, category, isdebit, "module", code, "order", create_date, modified_date, isdemand)
VALUES(nextval('chandigarh.seq_eg_demand_reason_master'), 'Niches on the common wall fee', 2, 'N', 444, 'NCW', 2, '2021-08-11 14:22:55.609', '2021-08-11 14:22:55.609', true);

INSERT INTO chandigarh.eg_demand_reason_master
(id, reasonmaster, category, isdebit, "module", code, "order", create_date, modified_date, isdemand)
VALUES(nextval('chandigarh.seq_eg_demand_reason_master'), 'DPC certificate missing fee', 2, 'N', 444, 'DCM', 2, '2021-08-11 14:22:55.609', '2021-08-11 14:22:55.609', true);

INSERT INTO chandigarh.eg_demand_reason_master
(id, reasonmaster, category, isdebit, "module", code, "order", create_date, modified_date, isdemand)
VALUES(nextval('chandigarh.seq_eg_demand_reason_master'), 'False Ceiling Fee', 2, 'N', 444, 'FCL', 2, '2021-08-11 14:22:55.609', '2021-08-11 14:22:55.609', true);

SELECT setval('chandigarh.seq_eg_demand_reason',(SELECT max(id) FROM chandigarh.eg_demand_reason));

INSERT INTO chandigarh.eg_demand_reason
(id, id_demand_reason_master, id_installment, percentage_basis, id_base_reason, create_date, modified_date, glcodeid)
VALUES(nextval('chandigarh.seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code = 'CBZ'), 7, NULL, NULL, '2022-04-06 12:41:32.753', '2022-04-06 12:41:32.753', 394);

INSERT INTO chandigarh.eg_demand_reason
(id, id_demand_reason_master, id_installment, percentage_basis, id_base_reason, create_date, modified_date, glcodeid)
VALUES(nextval('chandigarh.seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code = 'TBP'), 7, NULL, NULL, '2022-04-06 12:41:32.753', '2022-04-06 12:41:32.753', 394);

INSERT INTO chandigarh.eg_demand_reason
(id, id_demand_reason_master, id_installment, percentage_basis, id_base_reason, create_date, modified_date, glcodeid)
VALUES(nextval('chandigarh.seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code = 'MCDW'), 7, NULL, NULL, '2022-04-06 12:41:32.753', '2022-04-06 12:41:32.753', 394);

INSERT INTO chandigarh.eg_demand_reason
(id, id_demand_reason_master, id_installment, percentage_basis, id_base_reason, create_date, modified_date, glcodeid)
VALUES(nextval('chandigarh.seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code = 'LFT'), 7, NULL, NULL, '2022-04-06 12:41:32.753', '2022-04-06 12:41:32.753', 394);

INSERT INTO chandigarh.eg_demand_reason
(id, id_demand_reason_master, id_installment, percentage_basis, id_base_reason, create_date, modified_date, glcodeid)
VALUES(nextval('chandigarh.seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code = 'NSG'), 7, NULL, NULL, '2022-04-06 12:41:32.753', '2022-04-06 12:41:32.753', 394);

INSERT INTO chandigarh.eg_demand_reason
(id, id_demand_reason_master, id_installment, percentage_basis, id_base_reason, create_date, modified_date, glcodeid)
VALUES(nextval('chandigarh.seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code = 'NCW'), 7, NULL, NULL, '2022-04-06 12:41:32.753', '2022-04-06 12:41:32.753', 394);

INSERT INTO chandigarh.eg_demand_reason
(id, id_demand_reason_master, id_installment, percentage_basis, id_base_reason, create_date, modified_date, glcodeid)
VALUES(nextval('chandigarh.seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code = 'DCM'), 7, NULL, NULL, '2022-04-06 12:41:32.753', '2022-04-06 12:41:32.753', 394);

INSERT INTO chandigarh.eg_demand_reason
(id, id_demand_reason_master, id_installment, percentage_basis, id_base_reason, create_date, modified_date, glcodeid)
VALUES(nextval('chandigarh.seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code = 'FCL'), 7, NULL, NULL, '2022-04-06 12:41:32.753', '2022-04-06 12:41:32.753', 394);




