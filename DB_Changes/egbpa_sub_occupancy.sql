--old change db start compare the sub-occupancy-code and color-code

-- suboccopancy Education city (Sarangpur) #colorcode- 10

UPDATE chandigarh.egbpa_sub_occupancy
SET code='B-EC', occupancy =(select id from chandigarh.egbpa_occupancy where code ='B'), name='Education city (Sarangpur)', 
description='Education city (Sarangpur)'
WHERE code ='G-SI'

-- suboccopancy Educational / Academic #colorcode- 4

UPDATE chandigarh.egbpa_sub_occupancy
SET code='B-HEI', occupancy =(select id from chandigarh.egbpa_occupancy where code ='B'), name='Educational/ Academic', 
description='Educational / Academic'
WHERE code ='B-HEI' and colorcode =4

-- suboccopancy Hostels #colorcode- 19

UPDATE chandigarh.egbpa_sub_occupancy
SET code='B-H', occupancy =(select id from chandigarh.egbpa_occupancy where code ='B'), name='Hostels', 
description='Hostels'
WHERE code ='A-HE'

--old change db end

--for Feature Servant quarter

INSERT INTO chandigarh.egbpa_sub_occupancy(id, code, name, ordernumber, isactive, createdby, createddate, lastmodifieddate, lastmodifiedby, version, maxcoverage, 
minfar, maxfar, occupancy, description,colorcode)  VALUES (nextval('seq_egbpa_sub_occupancy'), 'A-SQ', 'Servant quarter', 1, 't', 1, now(), now(), 1, 0, 65, 3, 4, (select id from egbpa_occupancy where code='A'), 'Servant quarter',35);

--for Feature STD/ PCO/ fax and photostat machine

UPDATE chandigarh.egbpa_sub_occupancy
SET code='A-S', occupancy =(select id from chandigarh.egbpa_occupancy where code ='A'), name='STD/ PCO/ fax and photostat machine', 
description='STD/ PCO/ fax and photostat machine'
WHERE code ='I-2' and colorcode =13

--for feature Creche and paying guest facility

UPDATE chandigarh.egbpa_sub_occupancy
SET code='A-PG', occupancy =(select id from chandigarh.egbpa_occupancy where code ='A'), name='Creche and paying guest facility', 
description='Creche and paying guest facility'
WHERE code ='B-HEI' and colorcode =15
