
update chandigarh.egbpa_mstr_bpafee_common set "name" = 'Fee for partitions on ground floor on multi-bays shops', description = 'Fee for partitions on ground floor on multi-bays shops' where code = 'CRC';

update chandigarh.egbpa_mstr_bpafeemapping set bpafeecommon = (select id from  chandigarh.egbpa_mstr_bpafee_common where  code ='AHF') where applicationtype in ('OCCUPANCY_CERTIFICATE') and  servicetype in (59,61,62,64) and  bpafeecommon = (select id from  chandigarh.egbpa_mstr_bpafee_common where code ='CSDC');