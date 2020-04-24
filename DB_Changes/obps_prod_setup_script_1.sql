INSERT INTO chandigarh.revinfo ("timestamp",userid,ipaddress) VALUES (1583323483300,1,'127.0.0.1');

INSERT INTO chandigarh.eg_citypreferences (id,municipalitylogo,createdby,createddate,lastmodifiedby,lastmodifieddate,"version",municipalityname,municipalitycontactno,municipalityaddress,municipalitycontactemail,municipalitygislocation,municipalitycallcenterno,municipalityfacebooklink,municipalitytwitterlink,googleapikey,recaptchapk,recaptchapub) VALUES 
(nextval('SEQ_EG_CITYPREFERENCES'),NULL,1,now(),1,now(),1,'Chandigarh Administration',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'AIzaSyA1otT5_xEGe0qMrh2lemKKYH7Vo-pGOlA','6LfidggTAAAAANDSoCgfkNdvYm3Ugnl9HC8_68o0','6LfidggTAAAAADwfl4uOq1CSLhCkH8OE7QFinbVs');

INSERT INTO chandigarh.eg_city (domainurl,name,localname,id,active,"version",createdby,lastmodifiedby,createddate,lastmodifieddate,code,districtcode,districtname,longitude,latitude,preferences,regionname,grade) VALUES 
('ulb.chandigarh.local.org','Chandigarh','ulb',nextval('SEQ_EG_CITY'),true,1,1,1,now(),now(),'0002','002','Chandigarh',NULL,NULL,(select id from chandigarh.eg_citypreferences where municipalityname='Chandigarh Administration'),NULL,'Corp');

INSERT INTO chandigarh.eg_city_aud (id,rev,name,localname,active,domainurl,recaptchapk,recaptchapub,code,districtcode,districtname,longitude,latitude,revtype,regionname,grade) VALUES 
(1,72,'Chandigarh','ulb',true,'ulb.chandigarh.local.org',NULL,NULL,'0002','002','Chandigarh',NULL,NULL,1,NULL,'Corp');

-------------------------------------------------------------------

INSERT INTO chandigarh.eg_boundary_type (id,"hierarchy",parent,"name",hierarchytype,createddate,lastmodifieddate,createdby,lastmodifiedby,"version",localname,code) VALUES 
(nextval('SEQ_EG_BOUNDARY_TYPE'),1,NULL,'Area Category',1,now(),now(),1,1,0,NULL,NULL)
,(nextval('SEQ_EG_BOUNDARY_TYPE'),1,(select id from chandigarh.eg_boundary_type where "name"='Area Category'),'Zone or Location',1,now(),now(),1,1,0,NULL,NULL)
,(nextval('SEQ_EG_BOUNDARY_TYPE'),2,(select id from chandigarh.eg_boundary_type where "name"='Zone or Location'),'Sectors or Villages',1,now(),now(),1,1,0,NULL,NULL);

-------------------------------------------------------------------

INSERT INTO chandigarh.eg_boundary (id,boundarynum,parent,"name",boundarytype,localname,bndry_name_old,bndry_name_old_local,fromdate,todate,bndryid,longitude,latitude,materializedpath,active,createddate,lastmodifieddate,createdby,lastmodifiedby,"version",code) VALUES 
(nextval('seq_eg_boundary'),1,NULL,'URBAN',(select id from chandigarh.eg_boundary_type where "name"='Area Category'),'URBAN',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,'1',true,now(),now(),1,1,0,'REV_AREA_CAT')
,(nextval('seq_eg_boundary'),2,NULL,'RURAL',(select id from chandigarh.eg_boundary_type where "name"='Area Category'),'RURAL',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,'1',true,now(),now(),1,1,0,'REV_AREA_CAT_RURAL');

--------------------------------------------------------------------

INSERT INTO chandigarh.eg_boundary (id,boundarynum,parent,"name",boundarytype,localname,bndry_name_old,bndry_name_old_local,fromdate,todate,bndryid,longitude,latitude,materializedpath,active,createddate,lastmodifieddate,createdby,lastmodifiedby,"version",code) VALUES 
(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='URBAN'),'Center',(select id from chandigarh.eg_boundary_type where "name"='Zone or Location'),'Center',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'CENTER')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='URBAN'),'East',(select id from chandigarh.eg_boundary_type where "name"='Zone or Location'),'East',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'EAST')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='URBAN'),'South',(select id from chandigarh.eg_boundary_type where "name"='Zone or Location'),'South',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'SOUTH')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='RURAL'),'Sector 25 W store',(select id from chandigarh.eg_boundary_type where "name"='Zone or Location'),'Sector 25 W store',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Sector 25 W store')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='RURAL'),'Maloya CC',(select id from chandigarh.eg_boundary_type where "name"='Zone or Location'),'Maloya CC',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Maloya CC')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='RURAL'),'Sector 42 CC',(select id from chandigarh.eg_boundary_type where "name"='Zone or Location'),'Sector 42 CC',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Sector 42 CC')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='RURAL'),'Sector 34 CC',(select id from chandigarh.eg_boundary_type where "name"='Zone or Location'),'Sector 34 CC',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Sector 34 CC')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='RURAL'),'Sector 50 CC',(select id from chandigarh.eg_boundary_type where "name"='Zone or Location'),'Sector 50 CC',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Sector 50 CC')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='RURAL'),'Industrial area',(select id from chandigarh.eg_boundary_type where "name"='Zone or Location'),'Industrial area',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Industrial area')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='RURAL'),'Mani Majra',(select id from chandigarh.eg_boundary_type where "name"='Zone or Location'),'Mani Majra',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Mani Majra');

--------------------------------------------------------------------

INSERT INTO chandigarh.eg_boundary (id,boundarynum,parent,"name",boundarytype,localname,bndry_name_old,bndry_name_old_local,fromdate,todate,bndryid,longitude,latitude,materializedpath,active,createddate,lastmodifieddate,createdby,lastmodifiedby,"version",code) VALUES 
(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'42B',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'42B',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'42B')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'32A',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'32A',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'32A')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'32D',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'32D',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'32D')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'33A',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'33A',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'33A')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'33B',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'33B',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'33B')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'33C',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'33C',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'33C')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'33D',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'33D',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'33D')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'34C',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'34C',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'34C')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'34D',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'34D',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'34D')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'34',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'34',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'34')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'35A',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'35A',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'35A')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'35C',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'35C',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'35C')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'35D',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'35D',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'35D')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'36A',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'36A',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'36A')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'36B',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'36B',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'36B')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'36C',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'36C',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'36C')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'36D',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'36D',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'36D')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'37A',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'37A',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'37A')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'38 WEST',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'38 WEST',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'38 WEST')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'38A',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'38A',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'38A')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'39C',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'39C',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'39C')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'39D',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'39D',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'39D')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'40A',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'40A',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'40A')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'40B',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'40B',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'40B')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'40D',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'40D',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'40D')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'41B',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'41B',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'41B')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'43D',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'43D',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'43D')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'44C',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'44C',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'44C')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'45C',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'45C',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'45C')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'46A',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'46A',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'46A')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'46B',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'46B',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'46B')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'46D',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'46D',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'46D')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'49C',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'49C',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'49C')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='South'),'49D',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'49D',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'49D');

-------------------------------------------------------------------------------------

INSERT INTO chandigarh.eg_boundary (id,boundarynum,parent,"name",boundarytype,localname,bndry_name_old,bndry_name_old_local,fromdate,todate,bndryid,longitude,latitude,materializedpath,active,createddate,lastmodifieddate,createdby,lastmodifiedby,"version",code) VALUES 
(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='Center'),'11D',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'11D',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'11D')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='Center'),'10B',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'10B',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'10B')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='Center'),'10D',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'10D',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'10D')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='Center'),'11A',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'11A',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'11A')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='Center'),'11B',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'11B',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'11B')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='Center'),'11C',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'11C',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'11C')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='Center'),'15B',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'15B',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'15B')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='Center'),'17B',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'17B',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'17B')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='Center'),'23A',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'23A',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'23A')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='Center'),'24B',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'24B',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'24B');

----------------------------------------------------------------------------------

INSERT INTO chandigarh.eg_boundary (id,boundarynum,parent,"name",boundarytype,localname,bndry_name_old,bndry_name_old_local,fromdate,todate,bndryid,longitude,latitude,materializedpath,active,createddate,lastmodifieddate,createdby,lastmodifiedby,"version",code) VALUES
(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'27A',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'27A',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'27A')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'7B', (select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'7B',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'7B')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'19C',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'19C',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'19C')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'20A',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'20A',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'20A')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'20B',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'20B',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'20B')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'20C',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'20C',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'20C')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'20D',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'20D',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'20D')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'27B',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'27B',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'27B')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'28A',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'28A',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'28A')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'28D',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'28D',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'28D')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'30A',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'30A',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'30A')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'30C',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'30C',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'30C')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'31B',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'31B',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'31B')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'31C',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'31C',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'31C')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'47C',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'47C',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'47C')
,(nextval('seq_eg_boundary'),1,(select id from chandigarh.eg_boundary where "name"='East'),'IT PARK',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'IT PARK',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'IT PARK');

---------------------------------------------------------------------

INSERT INTO chandigarh.eg_boundary (id,boundarynum,parent,"name",boundarytype,localname,bndry_name_old,bndry_name_old_local,fromdate,todate,bndryid,longitude,latitude,materializedpath,active,createddate,lastmodifieddate,createdby,lastmodifiedby,"version",code) VALUES
(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Sector 25 W store'),'Sarangpur (R-1)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Sarangpur (R-1)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Sarangpur (R-1)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Sector 25 W store'),'Dadu Majra (R-3)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Dadu Majra (R-3)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Dadu Majra (R-3)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Sector 25 W store'),'Dhanas (R-3)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Dhanas (R-3)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Dhanas (R-3)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Sector 25 W store'),'Kaimbala (R-4)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Kaimbala (R-4)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Kaimbala (R-4)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Sector 25 W store'),'Kishangarh (R-1)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Kishangarh (R-1)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Kishangarh (R-1)');

INSERT INTO chandigarh.eg_boundary (id,boundarynum,parent,"name",boundarytype,localname,bndry_name_old,bndry_name_old_local,fromdate,todate,bndryid,longitude,latitude,materializedpath,active,createddate,lastmodifieddate,createdby,lastmodifiedby,"version",code) VALUES
(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Maloya CC'),'Maloya (R-4)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Maloya (R-4)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Maloya (R-4)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Maloya CC'),'Khudda Lohora (R-4)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Khudda Lohora (R-4)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Khudda Lohora (R-4)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Maloya CC'),'Khudda Jassu (R-4)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Khudda Jassu (R-4)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Khudda Jassu (R-4)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Maloya CC'),'Khudda Atisher (R-4)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Khudda Atisher (R-4)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Khudda Atisher (R-4)');

INSERT INTO chandigarh.eg_boundary (id,boundarynum,parent,"name",boundarytype,localname,bndry_name_old,bndry_name_old_local,fromdate,todate,bndryid,longitude,latitude,materializedpath,active,createddate,lastmodifieddate,createdby,lastmodifiedby,"version",code) VALUES
(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Sector 42 CC'),'Palsora (R-10)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Palsora (R-10)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Palsora (R-10)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Sector 42 CC'),'Badheri (R-10)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Badheri (R-10)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Badheri (R-10)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Sector 42 CC'),'Buterla (R-10)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Buterla (R-10)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Buterla (R-10)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Sector 42 CC'),'Adawa (R-10)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Adawa (R-10)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Adawa (R-10)');

INSERT INTO chandigarh.eg_boundary (id,boundarynum,parent,"name",boundarytype,localname,bndry_name_old,bndry_name_old_local,fromdate,todate,bndryid,longitude,latitude,materializedpath,active,createddate,lastmodifieddate,createdby,lastmodifiedby,"version",code) VALUES 
(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Sector 34 CC'),'Burail (R-13)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Burail (R-13)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Burail (R-13)');

INSERT INTO chandigarh.eg_boundary (id,boundarynum,parent,"name",boundarytype,localname,bndry_name_old,bndry_name_old_local,fromdate,todate,bndryid,longitude,latitude,materializedpath,active,createddate,lastmodifieddate,createdby,lastmodifiedby,"version",code) VALUES 
(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Sector 50 CC'),'Kajheri (R-10)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Kajheri (R-10)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Kajheri (R-10)');

INSERT INTO chandigarh.eg_boundary (id,boundarynum,parent,"name",boundarytype,localname,bndry_name_old,bndry_name_old_local,fromdate,todate,bndryid,longitude,latitude,materializedpath,active,createddate,lastmodifieddate,createdby,lastmodifiedby,"version",code) VALUES 
(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Industrial area'),'Hallo Majra (R-12)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Hallo Majra (R-12)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Hallo Majra (R-12)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Industrial area'),'Behlana (R-12)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Behlana (R-12)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Behlana (R-12)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Industrial area'),'Raipur Khurd (R-12)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Raipur Khurd (R-12)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Raipur Khurd (R-12)');

INSERT INTO chandigarh.eg_boundary (id,boundarynum,parent,"name",boundarytype,localname,bndry_name_old,bndry_name_old_local,fromdate,todate,bndryid,longitude,latitude,materializedpath,active,createddate,lastmodifieddate,createdby,lastmodifiedby,"version",code) VALUES 
(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Mani Majra'),'Raipur Kalau (R-6)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Raipur Kalau (R-6)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Raipur Kalau (R-6)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Mani Majra'),'Makhan Majra (R-6)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Makhan Majra (R-6)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Makhan Majra (R-6)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Mani Majra'),'Mauli Jagran (R-6)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Mauli Jagran (R-6)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Mauli Jagran (R-6)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Mani Majra'),'Daria (R-6)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Daria (R-6)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Daria (R-6)')
,(nextval('seq_eg_boundary'),2,(select id from chandigarh.eg_boundary where "name"='Mani Majra'),'Mani Majra (R-6)',(select id from chandigarh.eg_boundary_type where "name"='Sectors or Villages'),'Mani Majra (R-6)',NULL,NULL,'2019-04-01 00:00:00.000','2099-03-31 00:00:00.000',NULL,NULL,NULL,NULL,true,now(),now(),1,1,0,'Mani Majra (R-6)');

---------------------------------------------------------------------------------------

INSERT INTO chandigarh.eg_department (id,"name",createddate,code,createdby,lastmodifiedby,lastmodifieddate,"version") VALUES 
(nextval('SEQ_EG_DEPARTMENT'),'SDO Building',now(),'SDO',1,1,now(),0)
,(nextval('SEQ_EG_DEPARTMENT'),'Estate',now(),'EST',1,1,now(),0)
,(nextval('SEQ_EG_DEPARTMENT'),'Engineering (Road 2)',now(),'ENR',1,1,now(),0)
,(nextval('SEQ_EG_DEPARTMENT'),'SDO Building MC',now(),'SDO_BUILDING_MC',1,1,now(),0)
,(nextval('SEQ_EG_DEPARTMENT'),'MC Architect',now(),'MC_ARCHITECT',1,1,now(),0)
,(nextval('SEQ_EG_DEPARTMENT'),'Additional Commissioner',now(),'ADDITIONAL_COMMISSIONER',1,1,now(),0);

---------------------------------------------------------------------------------------

INSERT INTO chandigarh.eg_designation (id,"name",description,chartofaccounts,"version",createddate,lastmodifieddate,createdby,lastmodifiedby,code) VALUES 
(nextval('SEQ_EG_DESIGNATION'),'Building Assistant Urban','Building Assistant Urban',NULL,0,now(),now(),1,1,'BAU')
,(nextval('SEQ_EG_DESIGNATION'),'Junior Engineer Urban','Junior Engineer Urban',NULL,0,now(),now(),1,1,'JEU')
,(nextval('SEQ_EG_DESIGNATION'),'Surveyor','Surveyor',NULL,0,now(),now(),1,1,'SUR')
,(nextval('SEQ_EG_DESIGNATION'),'SDO Building Urban','SDO Building Urban',NULL,0,now(),now(),1,1,'SDOU')
,(nextval('SEQ_EG_DESIGNATION'),'Assistant Estate Officer','Assistant Estate Officer',NULL,0,now(),now(),1,1,'AEO')
,(nextval('SEQ_EG_DESIGNATION'),'CSCL Fee Collection Clerk','CSCL Fee Collection Clerk',NULL,0,now(),now(),1,1,'CFCC')
,(nextval('SEQ_EG_DESIGNATION'),'Estate Officer','Estate Officer',NULL,0,now(),now(),1,1,'EOC')
,(nextval('SEQ_EG_DESIGNATION'),'SDO Building MC','SDO Building MC',NULL,0,now(),now(),1,1,'SDOMC')
,(nextval('SEQ_EG_DESIGNATION'),'MC Architect','MC Architect',NULL,0,now(),now(),1,1,'MCARCH')
,(nextval('SEQ_EG_DESIGNATION'),'Additional Commissioner','Additional Commissioner',NULL,0,now(),now(),1,1,'ADDCOM');

INSERT INTO chandigarh.eg_designation (id,"name",description,chartofaccounts,"version",createddate,lastmodifieddate,createdby,lastmodifiedby,code) VALUES 
(nextval('SEQ_EG_DESIGNATION'),'Section managar','Section managar',NULL,0,now(),now(),1,1,'SM')
,(nextval('SEQ_EG_DESIGNATION'),'Zonal medical officer','Zonal medical officer',NULL,0,now(),now(),1,1,'AMO')
,(nextval('SEQ_EG_DESIGNATION'),'Medical store officer','Medical store officer',NULL,0,now(),now(),1,1,'MSO')
,(nextval('SEQ_EG_DESIGNATION'),'Sanitary worker','Sanitary worker',NULL,0,now(),now(),1,1,'SW')
,(nextval('SEQ_EG_DESIGNATION'),'Health inspector grade ii','Health inspector grade ii',NULL,0,now(),now(),1,1,'HIG')
,(nextval('SEQ_EG_DESIGNATION'),'Veternary assistant surgeon','Veternary assistant surgeon',NULL,0,now(),now(),1,1,'VAS')
,(nextval('SEQ_EG_DESIGNATION'),'Plumber','Plumber',NULL,0,now(),now(),1,1,'PLB')
,(nextval('SEQ_EG_DESIGNATION'),'Additional revenue officer','Additional revenue officer',NULL,0,now(),now(),1,1,'ARO')
,(nextval('SEQ_EG_DESIGNATION'),'License inspector','License inspector',NULL,0,now(),now(),1,1,'LI')
,(nextval('SEQ_EG_DESIGNATION'),'Assistant law officer','Assistant law officer',NULL,0,now(),now(),1,1,'ALO')
,(nextval('SEQ_EG_DESIGNATION'),'Assistant divisional electrical engineer','Assistant divisional electrical engineer',NULL,0,now(),now(),1,1,'LDEE')
,(nextval('SEQ_EG_DESIGNATION'),'Additional health officer','Additional health officer',NULL,0,now(),now(),1,1,'AHO')
,(nextval('SEQ_EG_DESIGNATION'),'Superintending  engineer','Superintending  engineer',NULL,0,now(),now(),1,1,'SE')
,(nextval('SEQ_EG_DESIGNATION'),'Assistan executive engineer','Assistan executive engineer',NULL,0,now(),now(),1,1,'AEE')
,(nextval('SEQ_EG_DESIGNATION'),'Gardener','Gardener',NULL,0,now(),now(),1,1,'GRD')
,(nextval('SEQ_EG_DESIGNATION'),'Zonal health officer','Zonal health officer',NULL,0,now(),now(),1,1,'ZHO')
,(nextval('SEQ_EG_DESIGNATION'),'Sweeper','Sweeper',NULL,0,now(),now(),1,1,'SWP')
,(nextval('SEQ_EG_DESIGNATION'),'Assistant accounts officer','Assistant accounts officer',NULL,0,now(),now(),1,1,'AAO')
,(nextval('SEQ_EG_DESIGNATION'),'Joint commissioner','Joint commissioner',NULL,0,now(),now(),1,1,'JC')
,(nextval('SEQ_EG_DESIGNATION'),'Li','Li',NULL,0,now(),now(),1,1,'LII')
,(nextval('SEQ_EG_DESIGNATION'),'Accounts officer','Accounts officer',NULL,0,now(),now(),1,1,'AO')
,(nextval('SEQ_EG_DESIGNATION'),'Assessor','Assessor',NULL,0,now(),now(),1,1,'ASS')
,(nextval('SEQ_EG_DESIGNATION'),'Assistant','Assistant',NULL,0,now(),now(),1,1,'ASST')
,(nextval('SEQ_EG_DESIGNATION'),'Assistant cashier','Assistant cashier',NULL,0,now(),now(),1,1,'ASSTCH')
,(nextval('SEQ_EG_DESIGNATION'),'Assistant commissioner','Assistant commissioner',NULL,0,now(),now(),1,1,'ASSTCO')
,(nextval('SEQ_EG_DESIGNATION'),'Assistant divisional engineer','Assistant divisional engineer',NULL,0,now(),now(),1,1,'ADE')
,(nextval('SEQ_EG_DESIGNATION'),'Assistant engineer','Assistant engineer',NULL,0,now(),now(),1,1,'AE')
,(nextval('SEQ_EG_DESIGNATION'),'Assistant executive engineer','Assistant executive engineer',NULL,0,now(),now(),1,1,'AEEN')
,(nextval('SEQ_EG_DESIGNATION'),'Assistant health officer','Assistant health officer',NULL,0,now(),now(),1,1,'AHOF')
,(nextval('SEQ_EG_DESIGNATION'),'Supervisor','Supervisor',NULL,0,now(),now(),1,1,'SUP')
,(nextval('SEQ_EG_DESIGNATION'),'Chief accounts officer','Chief accounts officer',NULL,0,now(),now(),1,1,'CAO')
,(nextval('SEQ_EG_DESIGNATION'),'Chief engineer','Chief engineer',NULL,0,now(),now(),1,1,'CENG')
,(nextval('SEQ_EG_DESIGNATION'),'City engineer','City engineer',NULL,0,now(),now(),1,1,'CYENG')
,(nextval('SEQ_EG_DESIGNATION'),'Computer operator','Computer operator',NULL,0,now(),now(),1,1,'COMPO')
,(nextval('SEQ_EG_DESIGNATION'),'Conservancy inspector','Conservancy inspector',NULL,0,now(),now(),1,1,'CINS')
,(nextval('SEQ_EG_DESIGNATION'),'Conservancy supervisor','Conservancy supervisor',NULL,0,now(),now(),1,1,'CSUP')
,(nextval('SEQ_EG_DESIGNATION'),'Divisional electrical engineer','Divisional electrical engineer',NULL,0,now(),now(),1,1,'DEE')
,(nextval('SEQ_EG_DESIGNATION'),'Driver','Driver',NULL,0,now(),now(),1,1,'DRI')
,(nextval('SEQ_EG_DESIGNATION'),'Electrician','Electrician',NULL,0,now(),now(),1,1,'ELC')
,(nextval('SEQ_EG_DESIGNATION'),'Executive engineer','Executive engineer',NULL,0,now(),now(),1,1,'EE')
,(nextval('SEQ_EG_DESIGNATION'),'Financial adviser','Financial adviser',NULL,0,now(),now(),1,1,'FA')
,(nextval('SEQ_EG_DESIGNATION'),'Head cashier','Head cashier',NULL,0,now(),now(),1,1,'HC')
,(nextval('SEQ_EG_DESIGNATION'),'Health officer','Health officer',NULL,0,now(),now(),1,1,'HO')
,(nextval('SEQ_EG_DESIGNATION'),'Junior engineer','Junior engineer',NULL,0,now(),now(),1,1,'JE')
,(nextval('SEQ_EG_DESIGNATION'),'Law officer','Law officer',NULL,0,now(),now(),1,1,'LO')
,(nextval('SEQ_EG_DESIGNATION'),'Licence inspector','Licence inspector',NULL,0,now(),now(),1,1,'LIN')
,(nextval('SEQ_EG_DESIGNATION'),'Operator','Operator',NULL,0,now(),now(),1,1,'OPR')
,(nextval('SEQ_EG_DESIGNATION'),'Public relation officer','Public relation officer',NULL,0,now(),now(),1,1,'PRO')
,(nextval('SEQ_EG_DESIGNATION'),'Sanitary inspector','Sanitary inspector',NULL,0,now(),now(),1,1,'SIN')
,(nextval('SEQ_EG_DESIGNATION'),'Sanitary officer','Sanitary officer',NULL,0,now(),now(),1,1,'SOFF')
,(nextval('SEQ_EG_DESIGNATION'),'Section manager','Section manager',NULL,0,now(),now(),1,1,'SMG')
,(nextval('SEQ_EG_DESIGNATION'),'Statistical assistant','Statistical assistant',NULL,0,now(),now(),1,1,'STASST')
,(nextval('SEQ_EG_DESIGNATION'),'Store keeper','Store keeper',NULL,0,now(),now(),1,1,'SKEEP')
,(nextval('SEQ_EG_DESIGNATION'),'Superintending engineer','Superintending engineer',NULL,0,now(),now(),1,1,'SUPE')
,(nextval('SEQ_EG_DESIGNATION'),'Tax collector','Tax collector',NULL,0,now(),now(),1,1,'TC')
,(nextval('SEQ_EG_DESIGNATION'),'Commissioner','Commissioner',NULL,0,now(),now(),1,1,'COMM')
,(nextval('SEQ_EG_DESIGNATION'),'Zonal officer','Zonal officer',NULL,0,now(),now(),1,1,'ZO')
,(nextval('SEQ_EG_DESIGNATION'),'Education officer','Education officer',NULL,0,now(),now(),1,1,'EO')
,(nextval('SEQ_EG_DESIGNATION'),'Financial advisor','Financial advisor',NULL,0,now(),now(),1,1,'FAA')
,(nextval('SEQ_EG_DESIGNATION'),'Deputy commissioner','Deputy commissioner',NULL,0,now(),now(),1,1,'DC')
,(nextval('SEQ_EG_DESIGNATION'),'Assistant revenue officer','Assistant revenue officer',NULL,0,now(),now(),1,1,'AROF')
,(nextval('SEQ_EG_DESIGNATION'),'Superintendent','Superintendent',NULL,0,now(),now(),1,1,'SUDT')
,(nextval('SEQ_EG_DESIGNATION'),'Vigilance inspector','Vigilance inspector',NULL,0,now(),now(),1,1,'VINS')
,(nextval('SEQ_EG_DESIGNATION'),'Superintendent of park','Superintendent of park',NULL,0,now(),now(),1,1,'SUPP')
,(nextval('SEQ_EG_DESIGNATION'),'Vigilance officer','Vigilance officer',NULL,0,now(),now(),1,1,'VO')
,(nextval('SEQ_EG_DESIGNATION'),'Data entry operator','Data entry operator',NULL,0,now(),now(),1,1,'DEO')
,(nextval('SEQ_EG_DESIGNATION'),'Revenue officer','Revenue officer',NULL,0,'2015-08-28 10:43:54.366','2015-08-28 10:43:54.366',1,1,'RO')
,(nextval('SEQ_EG_DESIGNATION'),'Bill Collector','Bill Collector',NULL,0,'2015-08-28 10:45:27.720','2015-08-28 10:45:27.720',1,1,'BC')
,(nextval('SEQ_EG_DESIGNATION'),'Revenue Inspector','Revenue Inspector',NULL,0,'2015-08-28 10:45:30.245','2015-08-28 10:45:30.245',1,1,NULL)
,(nextval('SEQ_EG_DESIGNATION'),'Revenue Clerk','Revenue Clerk',NULL,0,'2015-08-28 10:45:30.245','2015-08-28 10:45:30.245',1,1,'RC')
,(nextval('SEQ_EG_DESIGNATION'),'Manager','Manager',NULL,0,'2020-01-29 14:55:16.080','2020-01-29 14:55:16.080',1,1,NULL)
,(nextval('SEQ_EG_DESIGNATION'),'Examiner of Accounts','Examiner of Accounts',NULL,0,'2020-01-29 00:00:00.000','2020-01-29 00:00:00.000',1,1,NULL)
,(nextval('SEQ_EG_DESIGNATION'),'Town Planning Building Overseer','Town Planning Building Overseer',NULL,0,'2020-01-29 00:00:00.000','2020-01-29 00:00:00.000',1,1,'TPB')
,(nextval('SEQ_EG_DESIGNATION'),'Superintendent of NOC','Superintendent of NOC',NULL,0,'2020-01-29 00:00:00.000','2020-01-29 00:00:00.000',1,1,'SUPRNOC')
,(nextval('SEQ_EG_DESIGNATION'),'Superintendent of Approval','Superintendent of Approval',NULL,0,'2020-01-29 00:00:00.000','2020-01-29 00:00:00.000',1,1,'SUPRAPPRL')
,(nextval('SEQ_EG_DESIGNATION'),'Corporation Engineer','Corporation Engineer',NULL,0,'2020-01-29 00:00:00.000','2020-01-29 00:00:00.000',1,1,'CORPENG')
,(nextval('SEQ_EG_DESIGNATION'),'Secretary','Secretary',NULL,0,'2020-01-29 00:00:00.000','2020-01-29 00:00:00.000',1,1,'SEC')
,(nextval('SEQ_EG_DESIGNATION'),'Town Surveyor','Town Surveyor',NULL,0,'2020-01-29 00:00:00.000','2020-01-29 00:00:00.000',1,1,'TOWNSURV')
,(nextval('SEQ_EG_DESIGNATION'),'Section Clerk','Section Clerk',NULL,0,'2020-01-29 00:00:00.000','2020-01-29 00:00:00.000',1,1,'SECCRK')
;

----------------------------------------------------------------------------------------

update chandigarh.egbpa_mstr_servicetype set isactive=false, buildingplanapproval=false, isedcrmandatory=false;
update chandigarh.egbpa_mstr_servicetype set isactive=true, buildingplanapproval=true, isedcrmandatory=true where code in ('01','03','04','06');

----------------------------------------------------------------------------------------

update chandigarh.egbpa_mstr_applicationsubtype set description='Below two Kanal', enabled=true, slotrequired=false where "name"='Low Risk';
update chandigarh.egbpa_mstr_applicationsubtype set description='Above two Kanal', enabled=true, slotrequired=false where "name"='High Risk';
update chandigarh.egbpa_mstr_applicationsubtype set description='RURAL', enabled=true, slotrequired=false where "name"='Medium Risk';

----------------------------------------------------------------------------------------

delete from chandigarh.egbpa_usage;
delete from chandigarh.egbpa_sub_occupancy;
delete from chandigarh.egbpa_occupancy;

INSERT INTO egbpa_occupancy (id,code,"name",isactive,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,maxcoverage,minfar,maxfar,ordernumber,description,colorcode) VALUES 
(29,'A','Residential',true,0,1,'2020-03-20 18:26:51.949',1,'2020-03-20 18:26:51.949',65,3,4,1,'Residential',NULL)
,(30,'B','Educational',true,0,1,'2020-03-20 18:26:51.949',1,'2020-03-20 18:26:51.949',35,2.5,3,6,'Educational',NULL)
,(42,'IT','IT Park',true,0,1,'2020-03-20 18:26:51.949',1,'2020-03-20 18:26:51.949',65,3,4,1,'IT Park',NULL)
,(31,'C','Medical/Hospital',false,0,1,'2020-03-20 18:26:51.949',1,'2020-03-20 18:26:51.949',60,2.5,3.5,10,'Medical/Hospital',NULL)
,(32,'D','Assembly',false,0,1,'2020-03-20 18:26:51.949',1,'2020-03-20 18:26:51.949',40,1.5,2.5,14,'Assembly',NULL)
,(33,'E','Office/Business',false,0,1,'2020-03-20 18:26:51.949',1,'2020-03-20 18:26:51.949',70,3,4,17,'Office/ Business',7)
,(36,'H','Storage',false,0,1,'2020-03-20 18:26:51.949',1,'2020-03-20 18:26:51.949',80,3,4,24,'Storage -H',11)
,(37,'I','Hazardous',false,0,1,'2020-03-20 18:26:51.949',1,'2020-03-20 18:26:51.949',40,1.5,2.5,24,'Hazardous(I)',NULL)
,(38,'S','Socio',false,0,1,'2020-01-29 14:56:27.393',1,'2020-01-29 14:56:27.393',40,1.5,2.5,24,'Socio(S)',NULL)
,(167,'MIXED','Mixed',false,0,1,'2020-01-29 14:56:14.205',1,'2020-01-29 14:56:14.205',1,0,1,25,'Mixed',NULL)
,(34,'F','Commercial',true,0,1,'2020-03-20 18:26:51.949',1,'2020-03-20 18:26:51.949',70,3,4,18,'Commercial',8)
,(35,'G','Industrial',true,0,1,'2020-03-20 18:26:51.949',1,'2020-03-20 18:26:51.949',65,2.5,3,22,'Industrial',14)
,(40,'P','Public / Semi- Public Buildings',true,0,1,'2020-03-20 18:26:51.949',1,'2020-03-20 18:26:51.949',NULL,NULL,NULL,NULL,'Public / Semi- Public Buildings',NULL)
,(168,'ITH','IT Habitat',true,0,1,'2020-04-08 14:19:16.099',1,'2020-04-08 14:19:16.099',NULL,NULL,NULL,NULL,'IT Habitat',NULL)
,(169,'R','Railway Station',true,0,1,'2020-04-08 14:19:16.099',1,'2020-04-08 14:19:16.099',NULL,NULL,NULL,NULL,'Railway Station',NULL)
,(170,'IP','Integrated projects',true,0,1,'2020-04-08 14:19:16.099',1,'2020-04-08 14:19:16.099',NULL,NULL,NULL,NULL,'Integrated projects',NULL)
,(171,'T','Transit Oriented Development (TOD)',true,0,1,'2020-04-08 14:19:16.099',1,'2020-04-08 14:19:16.099',NULL,NULL,NULL,NULL,'Transit Oriented Development (TOD)',NULL);

INSERT INTO chandigarh.egbpa_sub_occupancy (id,code,"name",ordernumber,isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,"version",description,maxcoverage,minfar,maxfar,occupancy,colorcode) VALUES 
(224,'A-PO','Professional Office',5,true,1,now(),now(),1,0,'Residential A1 - Professional office',65,3,4,29,24)
,(223,'A-G','Grouped',4,true,1,now(),now(),1,0,'Residential Grouped',65,3,4,29,2)
,(227,'A-PG','Creche and paying guest facility',9,true,1,now(),now(),1,0,'Creche and paying guest facility',35,2.5,3,29,15)
,(221,'A-SR','Special Residential',2,true,1,now(),now(),1,0,'Special residential A2',65,2.5,4,29,3)
,(242,'A-S','STD/ PCO/ fax and photostat machine',26,true,1,now(),now(),1,0,'STD/ PCO/ fax and photostat machine',40,1.5,0,29,13)
,(281,'A-SQ','Servant quarter',1,true,1,now(),now(),1,0,'Servant quarter',65,3,4,29,35)
,(220,'A-P','Residential Plotted',1,true,1,now(),now(),1,0,'Residential Plotted',65,3,4,29,25)
,(225,'B-HEI','Educational/ Academic',6,true,1,now(),now(),1,0,'Educational / Academic',35,2.5,3,30,4)
,(222,'B-H','Hostels',3,true,1,now(),now(),1,0,'Hostels',65,5,4,30,19)
,(239,'B-EC','Education city (Sarangpur)',23,true,1,now(),now(),1,0,'Education city (Sarangpur)',75,3.5,4,30,10)
,(230,'C-MA','Medical Admin',13,true,1,now(),now(),1,0,'Medical -C (Floor area more than 150 m2) - Admin',60,2.5,3.5,31,21)
,(228,'C-MIP','Medical IP',11,true,1,now(),now(),1,0,'Medical -C (Floor area more than 150 m2) - IP section',60,2.5,3.5,31,5)
,(229,'C-MOP','Medical OP',12,true,1,now(),now(),1,0,'Medical -C (Floor area more than 150 m2) - OP section',60,2.5,3.5,31,20)
,(232,'D-BT','Bus Terminal',16,true,1,now(),now(),1,0,'Assembly (more than 150 m2)',40,1.5,2.5,32,22)
,(231,'D-AW','Assembly Worship',15,true,1,now(),now(),1,0,'Assembly (more than 150 m2)',40,1.5,2.5,32,16)
,(233,'E-OB','Office/Business',17,true,1,now(),now(),1,0,'Office/ Business (more than 300m2)',70,3,4,33,7)
,(271,'F-BH','Banquet hall/ marriage palaces',55,true,1,now(),now(),1,0,'Banquet hall/ marriage palaces',70,3,4,34,28)
,(235,'F-PA','Parking Appurtenant',20,true,1,now(),now(),1,0,'Appurtenant Parking',70,3,4,34,18)
,(236,'F-H','Hotels',21,true,1,now(),now(),1,0,'Hotels',70,3,4,34,23)
,(237,'F-CFI','COMMERCIAL (converted from Industrial)',29,true,1,now(),now(),1,0,'COMMERCIAL (converted from Industrial)',70,3,4,34,26)
,(234,'F-PP','Coal Depot and Petrol Pump',19,true,1,now(),now(),1,0,'Coal Depot and Petrol Pump',70,3,4,34,48)
,(273,'F-IT','IT/ITES Building',57,true,1,now(),now(),1,0,'IT/ITES Building',70,3,4,34,32)
,(278,'F-M','Commercial MULTIPLEX/MALLS',60,true,1,now(),now(),1,0,'MULTIPLEX/MALLS (specifically earmarked sites)',65,3,4,34,51)
,(279,'F-BBM','Bulk building material',61,true,1,now(),now(),1,0,'Commercial Bulk building material',65,3,4,34,29)
,(280,'F-TS','Timber site (single storey)',62,true,1,now(),now(),1,0,'Timber site (single storey)',65,3,4,34,45)
,(270,'F-TCIM','Comercial_Theatre converted into multiplex',54,true,1,now(),now(),1,0,'Comercial_Theatre converted into multiplex',70,3,4,34,31)
,(272,'F-SCO','SCO’S/ SCF’S / BAYSHOP’S/ SEMI INDUSTRIALETC ETC.',56,true,1,now(),now(),1,0,'SCO’S/ SCF’S / BAYSHOP’S/ SEMI INDUSTRIALETC ETC.',70,3,4,34,30)
,(277,'F-CD','Coal Depot',76,true,1,now(),now(),1,0,'Coal Depot',65,3,4,34,50)
,(276,'F-B','BOOTHS',75,true,1,now(),now(),1,0,'BOOTHS',65,3,4,34,60)
,(238,'G-LI','Large Industrial',22,true,1,now(),now(),1,0,'G1 (Large scale)',65,2.5,0,35,9)
,(274,'G-PHI','Polluting and hazardous industries',58,true,1,now(),now(),1,0,'Polluting and hazardous industries',NULL,NULL,0.5,35,33)
,(275,'G-NPHI','Non-polluting and household industries ',59,true,1,now(),now(),1,0,'Non-polluting and household industries ',NULL,NULL,1.5,35,34)
,(241,'I-1','Hazardous (I1)',25,true,1,now(),now(),1,0,'Hazardous (I-1)',45,2,0,37,12)
,(292,'P-SS','Sports Stadium',69,true,1,now(),now(),1,0,'Sports Stadium',65,3,4,40,248)
,(293,'P-CNA','Cultural and Non Academic institutional sites',70,true,1,now(),now(),1,0,'Cultural and Non Academic institutional sites',65,3,4,40,230)
,(294,'P-R','Religious',71,true,1,now(),now(),1,0,'Religious',65,3,4,40,240)
,(288,'P-P','Police Station',65,true,1,now(),now(),1,0,'Police Station',65,3,4,40,44)
,(289,'P-F','Fire Station',66,true,1,now(),now(),1,0,'Fire Station',65,3,4,40,43)
,(290,'P-H','Hospital',67,true,1,now(),now(),1,0,'Hospital',65,3,4,40,244)
,(291,'P-CC','Community centre/Janj Ghar',68,true,1,now(),now(),1,0,'Community centre/Janj Ghar',65,3,4,40,246)
,(286,'P-D','Dispensary',63,true,1,now(),now(),1,0,'Dispensary',65,3,4,40,243)
,(287,'P-N','Nursing Home',64,true,1,now(),now(),1,0,'Nursing Home',65,3,4,40,46)
,(295,'IT-MCL','Main Campus (above 6 acre)',72,true,1,now(),now(),1,0,'Main Campus (above 6 acre)',65,3,4,42,32)
,(297,'IT-MCS','Built to suite site (2 acre or below)',74,true,1,now(),now(),1,0,'Built to suite site (2 acre or below)',65,3,4,42,216)
,(296,'IT-MCM','Small Campus (2 to 6 acre)',73,true,1,now(),now(),1,0,'Small Campus (2 to 6 acre)',65,3,4,42,215);

INSERT INTO chandigarh.egbpa_usage (id,code,"name",description,isactive,ordernumber,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,suboccupancy,colorcode) VALUES 
(1,'A-P','Residential Plotted','Residential Plotted',true,1,0,1,now(),1,now(),220,NULL)
,(3,'A-G','Grouped','Residential Grouped',true,1,0,1,now(),1,now(),223,NULL)
,(170,'F-CFI','COMMERCIAL (converted from Industrial)','COMMERCIAL (converted from Industrial)',true,1,0,1,now(),1,now(),237,NULL)
,(173,'F-TCIM','Comercial_Theatre converted into multiplex','Comercial_Theatre converted into multiplex',true,1,0,1,now(),1,now(),270,NULL)
,(188,'F-BH','Banquet hall/ marriage palaces','Banquet hall/ marriage palaces',true,1,0,1,now(),1,now(),271,NULL)
,(172,'F-SCO','SCO’S/ SCF’S / BAYSHOP’S/ SEMI INDUSTRIALETC ETC.','SCO’S/ SCF’S / BAYSHOP’S/ SEMI INDUSTRIAL ETC',true,1,0,1,now(),1,now(),272,NULL)
,(187,'F-IT','IT/ITES Building','IT/ITES Building',true,1,0,1,now(),1,now(),273,NULL)
,(183,'F-M','Commercial MULTIPLEX/MALLS','MULTIPLEX/MALLS (specifically earmarked sites)',true,1,0,1,now(),1,now(),278,NULL)
,(185,'F-BBM','Bulk building material','Commercial Bulk building material',true,1,0,1,now(),1,now(),279,NULL)
,(178,'F-TS','Timber site (single storey)','Timber site (single storey)',true,1,0,1,now(),1,now(),280,NULL)
,(186,'P-D','Dispensary','Dispensary',true,1,0,1,now(),1,now(),286,NULL)
,(182,'P-N','Nursing Home','Nursing Home',true,1,0,1,now(),1,now(),287,NULL)
,(176,'P-P','Police Station','Police Station',true,1,0,1,now(),1,now(),288,NULL)
,(179,'IT-MCS','Built to suite site (2 acre or below)','Built to suite site (2 acre or below)',true,1,0,1,now(),1,now(),297,NULL)
,(184,'P-F','Fire Station','Fire Station',true,1,0,1,now(),1,now(),289,NULL)
,(169,'P-H','Hospital','Hospital',true,1,0,1,now(),1,now(),290,NULL)
,(171,'P-CC','Community centre/Janj Ghar','Community centre/Janj Ghar',true,1,0,1,now(),1,now(),291,NULL)
,(174,'P-SS','Sports Stadium','Sports Stadium',true,1,0,1,now(),1,now(),292,NULL)
,(181,'P-CNA','Cultural and Non Academic institutional sites','Cultural and Non Academic institutional sites',true,1,0,1,now(),1,now(),293,NULL)
,(189,'P-R','Religious','Religious',true,1,0,1,now(),1,now(),294,NULL)
,(175,'IT-MCL','Main Campus (above 6 acre)','Main Campus (above 6 acre)',true,1,0,1,now(),1,now(),295,NULL)
,(177,'IT-MCM','Small Campus (2 to 6 acre)','Small Campus (2 to 6 acre)',true,1,0,1,now(),1,now(),296,NULL)
,(190,'F-B','BOOTHS','BOOTHS',true,1,0,1,now(),1,now(),276,NULL)
,(180,'F-CD','Coal Depot','Coal Depot',true,1,0,1,now(),1,now(),277,NULL)
,(191,'A-SQ','Servant quarter','Servant quarter',true,1,0,1,now(),1,now(),281,NULL)
,(192,'A-S','STD/ PCO/ fax and photostat machine','STD/ PCO/ fax and photostat machine',true,1,0,1,now(),1,now(),242,NULL)
,(193,'B-EC','Education city (Sarangpur)','Education city (Sarangpur)',true,1,0,1,now(),1,now(),239,NULL)
,(194,'A-PG','Creche and paying guest facility','Creche and paying guest facility',true,1,0,1,now(),1,now(),227,NULL)
,(195,'B-H','Hostels','Hostels',true,1,0,1,now(),1,now(),222,NULL)
,(25,'B-HEI','Educational/ Academic','Educational/ Academic',true,1,0,1,now(),1,now(),225,NULL)
,(72,'F-PP','Petrol Pump','Coal Depot and Petrol Pump',true,1,0,1,now(),1,now(),234,NULL)
,(74,'F-H','Hotels','Hotels',true,1,0,1,now(),1,now(),236,NULL);

SELECT setval('seq_egbpa_usage', (SELECT max(id) FROM chandigarh.egbpa_usage));

INSERT INTO chandigarh.egbpa_usage (id,code,"name",description,isactive,ordernumber,"version",createdby,createddate,lastmodifiedby,lastmodifieddate,suboccupancy,colorcode) VALUES 
(nextval('seq_egbpa_usage'),'A-SR','Special Residential','Special residential A2',true,1,0,1,now(),1,now(),221,NULL)
,(nextval('seq_egbpa_usage'),'A-PO','Professional Office','Residential A1 - Professional office',true,1,0,1,now(),1,now(),224,NULL)
,(nextval('seq_egbpa_usage'),'C-MIP','Medical IP','Medical -C (Floor area more than 150 m2) - IP section',true,1,0,1,now(),1,now(),228,NULL)
,(nextval('seq_egbpa_usage'),'C-MOP','Medical OP','Medical -C (Floor area more than 150 m2) - OP section',true,1,0,1,now(),1,now(),229,NULL)
,(nextval('seq_egbpa_usage'),'C-MA','Medical Admin','Medical -C (Floor area more than 150 m2) - Admin',true,1,0,1,now(),1,now(),230,NULL)
,(nextval('seq_egbpa_usage'),'D-AW','Assembly Worship','Assembly (more than 150 m2)',true,1,0,1,now(),1,now(),231,NULL)
,(nextval('seq_egbpa_usage'),'D-BT','Bus Terminal','Assembly (more than 150 m2)',true,1,0,1,now(),1,now(),232,NULL)
,(nextval('seq_egbpa_usage'),'E-OB','Office/Business','Office/ Business (more than 300m2)',true,1,0,1,now(),1,now(),233,NULL)
,(nextval('seq_egbpa_usage'),'F-PA','Parking Appurtenant','Appurtenant Parking',true,1,0,1,now(),1,now(),235,NULL)
,(nextval('seq_egbpa_usage'),'G-LI','Large Industrial','G1 (Large scale)',true,1,0,1,now(),1,now(),238,NULL)
,(nextval('seq_egbpa_usage'),'I-1','Hazardous (I1)','Hazardous (I-1)',true,1,0,1,now(),1,now(),241,NULL)
,(nextval('seq_egbpa_usage'),'G-PHI','Polluting and hazardous industries','Polluting and hazardous industries',true,1,0,1,now(),1,now(),274,NULL)
,(nextval('seq_egbpa_usage'),'G-NPHI','Non-polluting and household industries ','Non-polluting and household industries ',true,1,0,1,now(),1,now(),275,NULL);

------------------------------------------------------------------------------------

delete from chandigarh.eg_wf_matrix where objecttype='BpaApplication' and additionalrule in ('Low Risk','High Risk','Medium Risk');

INSERT INTO chandigarh.eg_wf_matrix (id,department,objecttype,currentstate,currentstatus,pendingactions,currentdesignation,additionalrule,nextstate,nextaction,nextdesignation,nextstatus,validactions,fromqty,toqty,fromdate,todate,"version",enablefields,forwardenabled,smsemailenabled,nextref,rejectenabled) VALUES 
(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Rejected','','','','Medium Risk','generate rejection notice','Application is rejected by approver','MC Architect','Rejected','Generate Rejection Notice',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','NEW','','Forward to section clerk is pending','','Medium Risk','Property documents verification initiated','Forwarded to property documents verification','MC Architect','Registered','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Property documents verification initiated','Registered','Forwarded to property documents verification','','Medium Risk','NOC updation in progress','Forwarded to check NOC updation','SDO Building MC','Document Verification Completed','Forward,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','NOC updation in progress','','','','Medium Risk','Final Approval Process initiated','Permit Fee Collection Pending','Additional Commissioner','Document Verification Completed','Forward,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Final Approval Process initiated','','Permit Fee Collection Pending','','Medium Risk','Permit Fee Collected','Forwarded to update permit conditions','Additional Commissioner','Approved','Approve,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Permit Fee Collected','','Forwarded to update permit conditions','','Medium Risk','Record Approved','Forwarded to generate permit order','Additional Commissioner','Approved','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Permit Fee Collected','','Forwarded to generate permit order','','Medium Risk','Record Approved','Forwarded to generate permit order','Additional Commissioner','Approved','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Record Approved','','','','Medium Risk','END','END','Additional Commissioner','Order Issued to Applicant','Generate Permit Order',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','LP Initiated','','','','Medium Risk','LP Created','Letter To Party Reply Pending','MC Architect','Letter To Party Created','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','LP Created','','','','Medium Risk','LP Reply Received','Forward to LP Initiator pending','MC Architect','Letter To Party Reply Received','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Rejected','','','','High Risk','generate rejection notice','Application is rejected by approver','SDO Building Urban','Rejected','Generate Rejection Notice',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','NEW','','Forward to section clerk is pending','','High Risk','Property documents verification initiated','Forwarded to property documents verification','Building Assistant Urban','Registered','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Property documents verification initiated','Registered','Forwarded to property documents verification','','High Risk','NOC updation in progress','Forwarded to check NOC updation','SDO Building Urban','Document Verification Completed','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','NOC updation in progress','','','','High Risk','AEE Application Approval Pending','Forwarded to E- Assistant Estate Officer for Approval','Assistant Estate Officer','Document Verification Completed','Forward,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','AEE Application Approval Pending','','Forwarded to E- Assistant Estate Officer for Approval','','High Risk','Final Approval Process initiated','Permit Fee Collection Pending','Estate Officer','Document Verification Completed','Forward,Initiate Rejection',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Final Approval Process initiated','','Permit Fee Collection Pending','','High Risk','Permit Fee Collected','Forwarded to update permit conditions','SDO Building Urban','Approved','Approve,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Permit Fee Collected','','Forwarded to update permit conditions','','High Risk','Record Approved','Forwarded to generate permit order','SDO Building Urban','Approved','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Permit Fee Collected','','Forwarded to generate permit order','','High Risk','Record Approved','Forwarded to generate permit order','SDO Building Urban','Approved','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Record Approved','','','','High Risk','END','END','SDO Building Urban','Order Issued to Applicant','Generate Permit Order',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','LP Initiated','','','','High Risk','LP Created','Letter To Party Reply Pending','Building Assistant Urban','Letter To Party Created','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','LP Created','','','','High Risk','LP Reply Received','Forward to LP Initiator pending','Building Assistant Urban','Letter To Party Reply Received','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Rejection Initiated','','Verify Rejection Reasons','','High Risk','SDO Building Approval Pending','Forwarded to SDO Building for Approval','SDO Building Urban','Approval Process Initiated','Forward,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Rejection Initiated','','Forwarded to SDO Building for Approval','','High Risk','AEE Application Approval Pending','Forwarded to E- Assistant Estate Officer for Approval','Assistant Estate Officer','Document Verification Completed','Forward,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','NEW','','Forward to section clerk is pending','','Low Risk','Property documents verification initiated','Forwarded to property documents verification','Building Assistant Urban','Registered','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Property documents verification initiated','Registered','Forwarded to property documents verification','','Low Risk','SDO Building Approval Pending','Forwarded to SDO Building for Approval','SDO Building Urban','','Forward',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','SDO Building Approval Pending','','Forwarded to SDO Building for Approval','','Low Risk','Record Approved','Initiated process for generate permit Order','SDO Building Urban','','Approve,Reject',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Record Approved','','Initiated process for generate permit Order','','Low Risk','END','END','SDO Building Urban','Order Issued to Applicant','Generate Permit Order',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL)
,(nextval('SEQ_EG_WF_MATRIX'),'ANY','BpaApplication','Rejected','','','','Low Risk','generate rejection notice','Application is rejected by approver','SDO Building Urban','Rejected','Generate Rejection Notice',NULL,NULL,'2019-01-01','2099-04-01',0,NULL,NULL,NULL,NULL,NULL);

update chandigarh.egbpa_status set code='Document Verification Completed', description='Document Verification Completed' where code='Field Inspected' and moduletype='REGISTRATION';

------------------------------------------------------------------------------------------

update state.eg_role set "name"='BPA_FIRE_NOC_ROLE', description='Role for users who approve/provides a NOC from Fire department' where "name"='BPA_FIRE_NOC_ROLE';
update state.eg_role set "name"='BPA_PUB_HEALTH_7_NOC_ROLE', description='Role for users who approve/provides a NOC from Public health 7 Department' where "name"='BPA_AIPORT_AUTH_NOC_ROLE';
update state.eg_role set "name"='BPA_TEHSILDAR_NOC_ROLE', description='Role for users who approve/provides a NOC from Tehsildar department' where "name"='BPA_NMA_NOC_ROLE';
update state.eg_role set "name"='BPA_PUB_HEALTH_NOC_ROLE', description='Role for users who approve/provides a NOC from Public health department' where "name"='BPA_ENVIRONMENT_NOC_ROLE';

INSERT INTO state.eg_role (id,"name",description,createddate,createdby,lastmodifiedby,lastmodifieddate,"version",internal) VALUES 
(nextval('state.seq_eg_role'),'BPA_ROAD_2_NOC_ROLE','Role for users who approve/provides a NOC from Road 2 department',now(),1,1,now(),0,false)
,(nextval('state.seq_eg_role'),'BPA_PAC_NOC_ROLE','Role for users who approve/provides a NOC from Urban Planning Department (PAC) department',now(),1,1,now(),0,false)
,(nextval('state.seq_eg_role'),'BPA_STRUCTURE_NOC_ROLE','Role for users who approve/provides a NOC from Structure department',now(),1,1,now(),0,false)
,(nextval('state.seq_eg_role'),'BPA_ELECTRICAL_NOC_ROLE','Role for users who approve/provides a NOC from Electrical department',now(),1,1,now(),0,false)
,(nextval('state.seq_eg_role'),'BPA_POL_CONTROL_NOC_ROLE','Role for users who approve/provides a NOC from Pollution control department',now(),1,1,now(),0,false)
,(nextval('state.seq_eg_role'),'BPA_MANIMAJARA_NOC_ROLE','Role for users who approve/provides a NOC from Sub-Office Manimajara department',now(),1,1,now(),0,false);

update chandigarh.eg_checklist set code='PAC NOC', description='Urban Planning Department (PAC)' where code='NOC-06' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC');    
update chandigarh.eg_checklist set code='STRUCTURE NOC', description='Structure Department' where code='NOC-07' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC');    
update chandigarh.eg_checklist set code='ELECTRICAL NOC', description='Electrical Department' where code='NOC-08' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC');    
update chandigarh.eg_checklist set code='POL CONTROL NOC', description='Pollution control Department' where code='NOC-09' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC');    
update chandigarh.eg_checklist set code='PUB HEALTH 7 NOC', description='Public health 7 Department' where code='AAI NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC');    
update chandigarh.eg_checklist set code='TEHSILDAR NOC', description='Tehsildar' where code='NMA NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC');   
update chandigarh.eg_checklist set code='MANIMAJARA NOC', description='Sub-Office Manimajara' where code='IDA NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC'); 
update chandigarh.eg_checklist set code='PUB HEALTH NOC', description='Public health department' where code='MOEF NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC');

INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='NOC'),'ROAD 2 NOC','Road 2 Department',0,1,now(),1,now());

INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'ROAD 2 NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC')),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'ROAD 2 NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC')),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'ROAD 2 NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC')),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(nextval('seq_egbpa_checklist_servicetype_mapping'),(select id from chandigarh.eg_checklist where code = 'ROAD 2 NOC' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC')),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now());

update chandigarh.egbpa_checklist_servicetype_mapping set ismandatory=false, isrequired=true where checklist in (select id from chandigarh.eg_checklist where checklisttypeid=(select id from chandigarh.eg_checklist_type where code='NOC')) and servicetype in (select id from chandigarh.egbpa_mstr_servicetype where code in ('01','03','04','06'));

update state.eg_user set username = 'firenoc', "password" = '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', "name" = 'Fire NOC Department', mobilenumber='1112345678' where username = 'firenoc';
update state.eg_user set username = 'pubhealth7noc', "password" = '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', "name" = 'Public Health Seven NOC', mobilenumber='1112345677' where username = 'airportnoc';
update state.eg_user set username = 'tehsildarnoc', "password" = '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', "name" = 'Tehsildar NOC', mobilenumber='1112345676' where username = 'nmanoc';
update state.eg_user set username = 'pubhealthnoc', "password" = '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', "name" = 'Public Health NOC', mobilenumber='1112345675' where username = 'environmentnoc';

INSERT INTO state.eg_user select nextval('state.seq_eg_user'),'state', NULL, NULL, NULL, 'en_IN', 'road2noc', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', now(), NULL, NULL, NULL, now(), now(), 1, 1, true, 'Road 2 department', NULL, NULL, NULL, 'BUSINESS', 0, NULL, NULL, 'state' where not exists (select * from state.eg_user where username='road2noc');
INSERT INTO state.eg_user select nextval('state.seq_eg_user'), 'state', NULL, NULL, NULL, 'en_IN', 'pacnoc', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', now(), NULL, NULL, NULL, now(), now(), 1, 1, true, 'Urban Planning Department', NULL, NULL, NULL, 'BUSINESS', 0, NULL, NULL, 'state' where not exists (select * from state.eg_user where username='pacnoc');
INSERT INTO state.eg_user select nextval('state.seq_eg_user'), 'state', NULL, NULL, NULL, 'en_IN', 'structurenoc', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', now(), NULL, NULL, NULL, now(), now(), 1, 1, true, 'Structure department', NULL, NULL, NULL, 'BUSINESS', 0, NULL, NULL, 'state' where not exists (select * from state.eg_user where username='structurenoc');
INSERT INTO state.eg_user select nextval('state.seq_eg_user'), 'state', NULL, NULL, NULL, 'en_IN', 'electricalnoc', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', now(), NULL, NULL, NULL, now(), now(), 1, 1, true, 'Electrical department', NULL, NULL, NULL, 'BUSINESS', 0, NULL, NULL, 'state' where not exists (select * from state.eg_user where username='electricalnoc');
INSERT INTO state.eg_user select nextval('state.seq_eg_user'), 'state', NULL, NULL, NULL, 'en_IN', 'polcontrolnoc', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', now(), NULL, NULL, NULL, now(), now(), 1, 1, true, 'Pollution control department', NULL, NULL, NULL, 'BUSINESS', 0, NULL, NULL, 'state' where not exists (select * from state.eg_user where username='polcontrolnoc');
INSERT INTO state.eg_user select nextval('state.seq_eg_user'), 'state', NULL, NULL, NULL, 'en_IN', 'manimajaranoc', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', now(), NULL, NULL, NULL, now(), now(), 1, 1, true, 'Sub-Office Manimajara department', NULL, NULL, NULL, 'BUSINESS', 0, NULL, NULL, 'state' where not exists (select * from state.eg_user where username='manimajaranoc');

update state.eg_user set mobilenumber='1112345674' where username = 'structurenoc';
update state.eg_user set mobilenumber='1112345673' where username = 'pacnoc';
update state.eg_user set mobilenumber='1112345672' where username = 'manimajaranoc';
update state.eg_user set mobilenumber='1112345671' where username = 'polcontrolnoc';
update state.eg_user set mobilenumber='1112345670' where username = 'electricalnoc';
update state.eg_user set mobilenumber='1112345679' where username = 'road2noc';

update state.eg_user set pwdexpirydate='2025-12-31 00:00:00' where username = 'pubhealthnoc';
update state.eg_user set pwdexpirydate='2025-12-31 00:00:00' where username = 'tehsildarnoc';
update state.eg_user set pwdexpirydate='2025-12-31 00:00:00' where username = 'pubhealth7noc';
update state.eg_user set pwdexpirydate='2025-12-31 00:00:00' where username = 'firenoc';
update state.eg_user set pwdexpirydate='2025-12-31 00:00:00' where username = 'structurenoc';
update state.eg_user set pwdexpirydate='2025-12-31 00:00:00' where username = 'pacnoc';
update state.eg_user set pwdexpirydate='2025-12-31 00:00:00' where username = 'manimajaranoc';
update state.eg_user set pwdexpirydate='2025-12-31 00:00:00' where username = 'polcontrolnoc';
update state.eg_user set pwdexpirydate='2025-12-31 00:00:00' where username = 'electricalnoc';
update state.eg_user set pwdexpirydate='2025-12-31 00:00:00' where username = 'road2noc';



INSERT into state.eg_userrole select (select id from state.eg_role where name = 'BPA_ROAD_2_NOC_ROLE'), (select id from state.eg_user where username ='road2noc') where not exists (SELECT * FROM state.eg_userrole WHERE roleid in (select id from state.eg_role where name = 'BPA_ROAD_2_NOC_ROLE'));
INSERT into state.eg_userrole select (select id from state.eg_role where name = 'BPA_PAC_NOC_ROLE'), (select id from state.eg_user where username ='pacnoc') where not exists (SELECT * FROM state.eg_userrole WHERE roleid in (select id from state.eg_role where name = 'BPA_PAC_NOC_ROLE'));
INSERT into state.eg_userrole select (select id from state.eg_role where name = 'BPA_STRUCTURE_NOC_ROLE'), (select id from state.eg_user where username ='structurenoc') where not exists (SELECT * FROM state.eg_userrole WHERE roleid in (select id from state.eg_role where name = 'BPA_STRUCTURE_NOC_ROLE'));
INSERT into state.eg_userrole select (select id from state.eg_role where name = 'BPA_ELECTRICAL_NOC_ROLE'), (select id from state.eg_user where username ='electricalnoc') where not exists (SELECT * FROM state.eg_userrole WHERE roleid in (select id from state.eg_role where name = 'BPA_ELECTRICAL_NOC_ROLE'));
INSERT into state.eg_userrole select (select id from state.eg_role where name = 'BPA_POL_CONTROL_NOC_ROLE'), (select id from state.eg_user where username ='polcontrolnoc') where not exists (SELECT * FROM state.eg_userrole WHERE roleid in (select id from state.eg_role where name = 'BPA_POL_CONTROL_NOC_ROLE'));
INSERT into state.eg_userrole select (select id from state.eg_role where name = 'BPA_MANIMAJARA_NOC_ROLE'), (select id from state.eg_user where username ='manimajaranoc') where not exists (SELECT * FROM state.eg_userrole WHERE roleid in (select id from state.eg_role where name = 'BPA_MANIMAJARA_NOC_ROLE'));

INSERT into state.eg_userrole select (select id from state.eg_role where name = 'BUSINESS'), (select id from state.eg_user where username ='road2noc');
INSERT into state.eg_userrole select (select id from state.eg_role where name = 'BUSINESS'), (select id from state.eg_user where username ='pacnoc');
INSERT into state.eg_userrole select (select id from state.eg_role where name = 'BUSINESS'), (select id from state.eg_user where username ='structurenoc');
INSERT into state.eg_userrole select (select id from state.eg_role where name = 'BUSINESS'), (select id from state.eg_user where username ='electricalnoc');
INSERT into state.eg_userrole select (select id from state.eg_role where name = 'BUSINESS'), (select id from state.eg_user where username ='polcontrolnoc');
INSERT into state.eg_userrole select (select id from state.eg_role where name = 'BUSINESS'), (select id from state.eg_user where username ='manimajaranoc');

update chandigarh.egbpa_master_nocconfiguration set department='PUB HEALTH 7 NOC', applicationtype='Permit', integrationtype='INTERNAL', integrationinitiation='AUTO' where department='AAI NOC';
update chandigarh.egbpa_master_nocconfiguration set department='TEHSILDAR NOC', applicationtype='Permit', integrationtype='INTERNAL', integrationinitiation='AUTO' where department='NMA NOC';
update chandigarh.egbpa_master_nocconfiguration set department='MANIMAJARA NOC', applicationtype='Permit', integrationtype='INTERNAL', integrationinitiation='AUTO' where department='IDA NOC';
update chandigarh.egbpa_master_nocconfiguration set department='PUB HEALTH NOC', applicationtype='Permit', integrationtype='INTERNAL', integrationinitiation='AUTO' where department='MOEF NOC';
update chandigarh.egbpa_master_nocconfiguration set department='FIRE NOC', applicationtype='Permit', integrationtype='INTERNAL', integrationinitiation='AUTO' where department='FIRE NOC';

INSERT INTO chandigarh.egbpa_master_nocconfiguration (id,department,applicationtype,integrationtype,integrationinitiation,sla,isdeemedapproval) VALUES 
(nextval('SEQ_EGBPA_MSTR_NOCCONFIG'),'ROAD 2 NOC','Permit','INTERNAL','AUTO',15,false)
,(nextval('SEQ_EGBPA_MSTR_NOCCONFIG'),'PAC NOC','Permit','INTERNAL','AUTO',15,false)
,(nextval('SEQ_EGBPA_MSTR_NOCCONFIG'),'STRUCTURE NOC','Permit','INTERNAL','AUTO',15,false)
,(nextval('SEQ_EGBPA_MSTR_NOCCONFIG'),'ELECTRICAL NOC','Permit','INTERNAL','AUTO',15,false)
,(nextval('SEQ_EGBPA_MSTR_NOCCONFIG'),'POL CONTROL NOC','Permit','INTERNAL','AUTO',15,false);

----------------------------------------------------------------------------------

update chandigarh.eg_checklist set description='Permit issued based on the approval by Architect and TP Office as per the order no.' where code='PERMITNOCCONDITION-01' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS');
update chandigarh.eg_checklist set description='Permit issued based on the approval by Structure and Electrical Engineer as per the order no.' where code='PERMITNOCCONDITION-02' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS');
update chandigarh.eg_checklist set description='Permit issued based on the approval by Fire Department as per the order no.' where code='PERMITNOCCONDITION-03' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS');
update chandigarh.eg_checklist set description='Permit issued based on the approval by Pollution Control as per the order no.' where code='PERMITNOCCONDITION-04' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS');
update chandigarh.eg_checklist set description='Permit issued based on the approval by Public health Department as per the order no.' where code='PERMITNOCCONDITION-05' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS');
update chandigarh.eg_checklist set description='Permit issued based on the approval by Road 2 department as per the order no.' where code='PERMITNOCCONDITION-06' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS');
update chandigarh.eg_checklist set description='Permit issued based on the approval by Fire Department as per the order no.' where code='PERMITNOCCONDITION-07' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS');
update chandigarh.eg_checklist set description='Permit issued based on the approval by Tehsildar as per the order no.' where code='PERMITNOCCONDITION-08' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS');
update chandigarh.eg_checklist set description='Permit issued based on the approval by Sub-Office Manimajara as per the order no.' where code='PERMITNOCCONDITION-09' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS');
update chandigarh.eg_checklist set description='Permit issued based on the approval by Electrical Dept as per the order no.' where code='PERMITNOCCONDITION-10' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS');

INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS'),'PERMITNOCCONDITION-11','Permit issued based on the approval by Public Health Department as per the order no.',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS'),'PERMITNOCCONDITION-12','Permit issued based on the approval by Pollution Control Department as per the order no.',0,1,now(),1,now());

INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(3001,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS') and code='PERMITNOCCONDITION-11'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3002,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS') and code='PERMITNOCCONDITION-11'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3003,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS') and code='PERMITNOCCONDITION-11'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3004,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS') and code='PERMITNOCCONDITION-11'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3005,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS') and code='PERMITNOCCONDITION-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3006,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS') and code='PERMITNOCCONDITION-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3007,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS') and code='PERMITNOCCONDITION-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3008,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS') and code='PERMITNOCCONDITION-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now());

update chandigarh.egbpa_checklist_servicetype_mapping set ismandatory=false, isrequired=true where checklist in (select id from chandigarh.eg_checklist where checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITNOCCONDITIONS')) and servicetype in (select id from chandigarh.egbpa_mstr_servicetype where code in ('01','03','04','06'));

------------------------------------------------------------------------------

delete from chandigarh.egbpa_notice_conditions where checklistservicetype in (select id from chandigarh.egbpa_checklist_servicetype_mapping where checklist in (select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS') and code in ('PERMITGENERALCONDITION-14','PERMITGENERALCONDITION-15','PERMITGENERALCONDITION-16','PERMITGENERALCONDITION-17','PERMITGENERALCONDITION-18','PERMITGENERALCONDITION-19','PERMITGENERALCONDITION-20','PERMITGENERALCONDITION-21','PERMITGENERALCONDITION-22','PERMITGENERALCONDITION-23','PERMITGENERALCONDITION-24','PERMITGENERALCONDITION-25')));

delete from chandigarh.egbpa_checklist_servicetype_mapping
where checklist in (select id from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS') and code in ('PERMITGENERALCONDITION-14','PERMITGENERALCONDITION-15','PERMITGENERALCONDITION-16','PERMITGENERALCONDITION-17','PERMITGENERALCONDITION-18','PERMITGENERALCONDITION-19','PERMITGENERALCONDITION-20','PERMITGENERALCONDITION-21','PERMITGENERALCONDITION-22','PERMITGENERALCONDITION-23','PERMITGENERALCONDITION-24','PERMITGENERALCONDITION-25'));

delete from chandigarh.eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS') and 
code in ('PERMITGENERALCONDITION-14','PERMITGENERALCONDITION-15','PERMITGENERALCONDITION-16','PERMITGENERALCONDITION-17','PERMITGENERALCONDITION-18','PERMITGENERALCONDITION-19','PERMITGENERALCONDITION-20','PERMITGENERALCONDITION-21','PERMITGENERALCONDITION-22','PERMITGENERALCONDITION-23','PERMITGENERALCONDITION-24','PERMITGENERALCONDITION-25');

update chandigarh.eg_checklist set description='All the general requirements are incurred as per section 10.1 of Chandigarh Building Regulations 2017.' where code='PERMITGENERALCONDITION-01' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS');
update chandigarh.eg_checklist set description='Gallery floor and Mezzanine floor are designed as per section 10.2 of Chandigarh Building Regulations 2017.' where code='PERMITGENERALCONDITION-02' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS');
update chandigarh.eg_checklist set description='Damp proofing of the basement is done to avoid surrounding soil and moisture.' where code='PERMITGENERALCONDITION-03' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS');
update chandigarh.eg_checklist set description='Drainage of basement is planned as per section 10.4 of Chandigarh Building Regulations 2017.' where code='PERMITGENERALCONDITION-04' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS');
update chandigarh.eg_checklist set description='Fire safety provision as per fire prevention and safety act 1986 and National building code of India.' where code='PERMITGENERALCONDITION-05' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS');
update chandigarh.eg_checklist set description='Service floor provision as per rules mentioned in section 10.6 of Chandigarh Building Regulations 2017.' where code='PERMITGENERALCONDITION-06' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS');
update chandigarh.eg_checklist set description='FAR exemptions as per rules defined in section 10.7 of Chandigarh Building Regulations 2017.' where code='PERMITGENERALCONDITION-07' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS');
update chandigarh.eg_checklist set description='Occupant load calculated as per rules mentioned in 10.8 of Chandigarh Building Regulations 2017.' where code='PERMITGENERALCONDITION-08' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS');
update chandigarh.eg_checklist set description='Planning for differently abled as per Chandigarh Building Regulations 2017.' where code='PERMITGENERALCONDITION-09' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS');
update chandigarh.eg_checklist set description='Public health installations as per Chandigarh Building Regulations 2017.' where code='PERMITGENERALCONDITION-10' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS');
update chandigarh.eg_checklist set description='Structural materials used for construction as per National Building Code.' where code='PERMITGENERALCONDITION-11' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS');
update chandigarh.eg_checklist set description='Environment conditions for building and construction as given in Chandigarh Building Regulations 2017.' where code='PERMITGENERALCONDITION-12' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS');
update chandigarh.eg_checklist set description='Green building and sustainability provisions.' where code='PERMITGENERALCONDITION-13' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS');

update chandigarh.egbpa_checklist_servicetype_mapping set ismandatory=false, isrequired=true where checklist in (select id from chandigarh.eg_checklist where checklisttypeid=(select id from chandigarh.eg_checklist_type where code='PERMITGENERALCONDITIONS')) and servicetype in (select id from chandigarh.egbpa_mstr_servicetype where code in ('01','03','04','06'));

-----------------------------------------------------------------------------------------------------

update chandigarh.eg_checklist set description='Plans' where code='BPADCRDOC-01' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS');
update chandigarh.eg_checklist set description='Roof Plan' where code='BPADCRDOC-02' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS');
update chandigarh.eg_checklist set description='Parking plan' where code='BPADCRDOC-03' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS');
update chandigarh.eg_checklist set description='Sections' where code='BPADCRDOC-04' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS');
update chandigarh.eg_checklist set description='Drainage plans' where code='BPADCRDOC-05' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS');
update chandigarh.eg_checklist set description='Engineering drawings (structural)' where code='BPADCRDOC-06' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS');
update chandigarh.eg_checklist set description='A site plan showing the position of Plot proposed to be built upon' where code='BPADCRDOC-07' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS');
update chandigarh.eg_checklist set description='Details' where code='BPADCRDOC-08' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS');
update chandigarh.eg_checklist set description='Other Details' where code='BPADCRDOC-09' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS');
update chandigarh.eg_checklist set description='Elevations' where code='BPADCRDOC-10' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='BPADCRDOCUMENTS');

update chandigarh.eg_checklist set description='Form-A (applicable for plots above 2 kanal)' where code='DOCUMENTATION-01' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='Form-C (applicable for plots above 2 kanal)' where code='DOCUMENTATION-02' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='Form-J (applicable for plots above 2 kanal)' where code='DOCUMENTATION-03' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='Form-K (applicable for below 2 kanal.)' where code='DOCUMENTATION-04' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='Form-J (applicable for below 2 kanal.)' where code='DOCUMENTATION-05' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='Authority letter in favor of private architect from the owner(s) / applicant(s) regarding submission of plan / Revised Building plan for sanction and making correspondence with Estate Office.' where code='DOCUMENTATION-06' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='Proof of ownership i.e. copy of allotment letter / transfer letter not more than 3 months old.' where code='DOCUMENTATION-07' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='No dues certificate with regard to payment of entire/full amount of premium of plot (including up to date annual Ground Rent / Lease Money (Self Attested)' where code='DOCUMENTATION-08' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='Self attested undertaking:- a) Regarding ownership from owner(s) / applicant(s) with specimen signature, latest photographs and ID proof. b) That there is no dispute / litigation is pending in any court of law and the properties free from all sorts of encumbrance and there is no stay / restraining order from any court of law with regard to sanction of Fresh / Revised Building Plan' where code='DOCUMENTATION-09' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='Copy of Sewerage Connection / Occupation Certificate or extension in time limit for construction of the building.' where code='DOCUMENTATION-10' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='In case, sewerage connection/occupation certificate is not issued/obtained then furnish: a) An attested copy of proof of construction i.e., water/electricity bills (prior to 22-1-1993 or construction of building within the stipulated period as per terms and conditions of allotment or within the extended period as the case may be), supported with a certificate issued by the Registered Architect on his/her letter head certifying that the building is constructed as per sanctioned plan and there is no building violations at Site/House. b) Certificate from the concerned water/electricity department certifying there in the date and year of release of permanent / regular water/electricity connection against the house/building in question.' where code='DOCUMENTATION-11' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='Indemnity Bond is required from each owner (s) duly attested for attorney.' where code='DOCUMENTATION-12' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='If applicant is GPA / SPA holder, then furnish: a)      Copy of GPA / SPA (attested by notary public). b)     Affidavit regarding validity of GPA / SPA' where code='DOCUMENTATION-13' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='If there is no proof of previous sanctioned plan, an undertaking is must require from the owner (s) that my / our plan (s) be treated as fresh with applicable charges.' where code='DOCUMENTATION-14' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='If there is no proof of previous sanctioned plan / plans is treated as fresh, plot size may also got be verified from the Surveyor.' where code='DOCUMENTATION-15' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='In case of fresh building plan, the plot size may be got verified from the Surveyor.' where code='DOCUMENTATION-16' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='E-mail ID and address of owner (s), Private Architect and Structural Engineer is required on submitted plan (s) (Applicable F\for Plots Above 2 Kanal)' where code='DOCUMENTATION-17' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='Attested Copy of partnership deed, authorization letter in favor of the authorized signatory/ partner to apply for Revised Building Plan. (Applicable in case of property owned by Partnership Firm). Applicable for plots above 2 kanal' where code='DOCUMENTATION-18' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='Attested copy of Memorandum of Articles and Association copy of resolution in favor of authorized signatory/ Director of the Company to apply for Revised Building Plan. (Application in case of property owned by the Company). Applicable for plots above 2 Kanal' where code='DOCUMENTATION-19' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='In case of allotment of society, PLAN INSPECTION from the Chandigarh Housing Board or Society Registrar required. (applicable for plots above 2 kanal)' where code='DOCUMENTATION-20' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');
update chandigarh.eg_checklist set description='Authority letter for private architect is required from owner (s) regarding submission / correspondence / receipt of sanctioned plan. (applicable for plots below 2 kanal)' where code='DOCUMENTATION-21' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='DOCUMENTATION');

INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
 (3009,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3010,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3011,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3012,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-15'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3013,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-16'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3014,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-17'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3015,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-18'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3016,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-19'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3017,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-20'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3018,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-21'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3019,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3020,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3021,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3022,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-15'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3023,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-16'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3024,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-17'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3025,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-18'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3026,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-19'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3027,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-20'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3028,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-21'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3029,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3030,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3031,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3032,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-15'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3033,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-16'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3034,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-17'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3035,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-18'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3036,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-19'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3037,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-20'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3038,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-21'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3039,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3040,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3041,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3042,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-15'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3043,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-16'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3044,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-17'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3045,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-18'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3046,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-19'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3047,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-20'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3048,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='DOCUMENTATION') and code='DOCUMENTATION-21'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now());

update chandigarh.egbpa_checklist_servicetype_mapping set ismandatory=false, isrequired=true where checklist in (select id from chandigarh.eg_checklist where checklisttypeid in (select id from chandigarh.eg_checklist_type where code in ('DOCUMENTATION','BPADCRDOCUMENTS'))) and servicetype in (select id from chandigarh.egbpa_mstr_servicetype where code in ('01','03','04','06'));

update chandigarh.egbpa_checklist_servicetype_mapping set ismandatory=true where checklist in (select id from chandigarh.eg_checklist where code in ('DOCUMENTATION-06','DOCUMENTATION-07','DOCUMENTATION-08','DOCUMENTATION-09','DOCUMENTATION-17','BPADCRDOC-01','BPADCRDOC-04','BPADCRDOC-05','BPADCRDOC-06','BPADCRDOC-07','BPADCRDOC-08','BPADCRDOC-10')) and servicetype in (select id from chandigarh.egbpa_mstr_servicetype where code in ('01','03','04','06'));

----------------------------------------------------------------------------------------------------------

update chandigarh.eg_checklist set description='Plans' where code='LTP-01' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Roof Plan' where code='LTP-02' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Parking plan' where code='LTP-03' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Sections' where code='LTP-04' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Drainage plans' where code='LTP-05' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Engineering drawings (structural)' where code='LTP-06' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='A site plan showing the position of Plot proposed to be built upon' where code='LTP-07' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Details' where code='LTP-08' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Other Details' where code='LTP-09' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Elevations' where code='LTP-10' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Form-A (applicable for plots above 2 kanal)' where code='LTP-11' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Form-C (applicable for plots above 2 kanal)' where code='LTP-12' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Form-J (applicable for plots above 2 kanal)' where code='LTP-13' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Form-K (applicable for below 2 kanal.)' where code='LTP-14' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Form-J (applicable for below 2 kanal.)' where code='LTP-15' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Authority letter in favor of private architect from the owner(s) / applicant(s) regarding submission of plan / Revised Building plan for sanction and making correspondence with Estate Office.' where code='LTP-16' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='Proof of ownership i.e. copy of allotment letter / transfer letter not more than 3 months old.' where code='LTP-17' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');
update chandigarh.eg_checklist set description='No dues certificate with regard to payment of entire/full amount of premium of plot (including up to date annual Ground Rent / Lease Money (Self Attested)' where code='LTP-18' and checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP');

INSERT INTO chandigarh.eg_checklist (id,checklisttypeid,code,description,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-19','Self attested undertaking:- a) Regarding ownership from owner(s) / applicant(s) with specimen signature, latest photographs and ID proof. b) That there is no dispute / litigation is pending in any court of law and the properties free from all sorts of encumbrance and there is no stay / restraining order from any court of law with regard to sanction of Fresh / Revised Building Plan',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-20','Copy of Sewerage Connection / Occupation Certificate or extension in time limit for construction of the building.',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-21','In case, sewerage connection/occupation certificate is not issued/obtained then furnish: a) An attested copy of proof of construction i.e., water/electricity bills (prior to 22-1-1993 or construction of building within the stipulated period as per terms and conditions of allotment or within the extended period as the case may be), supported with a certificate issued by the Registered Architect on his/her letter head certifying that the building is constructed as per sanctioned plan and there is no building violations at Site/House. b) Certificate from the concerned water/electricity department certifying there in the date and year of release of permanent / regular water/electricity connection against the house/building in question.',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-22','Indemnity Bond is required from each owner (s) duly attested for attorney.',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-23','If applicant is GPA / SPA holder, then furnish: a) Copy of GPA / SPA (attested by notary public). b) Affidavit regarding validity of GPA / SPA',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-24','If there is no proof of previous sanctioned plan, an undertaking is must require from the owner (s) that my / our plan (s) be treated as fresh with applicable charges.',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-25','If there is no proof of previous sanctioned plan / plans is treated as fresh, plot size may also got be verified from the Surveyor.',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-26','In case of fresh building plan, the plot size may be got verified from the Surveyor.',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-27','E-mail ID and address of owner (s), Private Architect and Structural Engineer is required on submitted plan (s) (Applicable F\for Plots Above 2 Kanal)',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-28','Attested Copy of partnership deed, authorization letter in favor of the authorized signatory/ partner to apply for Revised Building Plan. (Applicable in case of property owned by Partnership Firm). Applicable for plots above 2 kanal',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-29','Attested copy of Memorandum of Articles and Association copy of resolution in favor of authorized signatory/ Director of the Company to apply for Revised Building Plan. (Application in case of property owned by the Company). Applicable for plots above 2 Kanal',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-30','In case of allotment of society, PLAN INSPECTION from the Chandigarh Housing Board or Society Registrar required. (applicable for plots above 2 kanal)',0,1,now(),1,now())
,(nextval('seq_eg_checklist'),(select id from chandigarh.eg_checklist_type where code='LTP'),'LTP-31','Authority letter for private architect is required from owner (s) regarding submission / correspondence / receipt of sanctioned plan. (applicable for plots below 2 kanal)',0,1,now(),1,now());


INSERT INTO chandigarh.egbpa_checklist_servicetype_mapping (id,checklist,servicetype,isrequired,ismandatory,"version",createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(3050,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-09'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3051,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-10'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3052,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-11'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3053,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3054,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3055,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3056,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-15'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3057,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-16'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3058,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-17'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3059,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-18'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3060,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-19'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3061,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-20'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3062,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-21'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3063,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-22'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3064,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-23'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3065,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-24'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3066,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-25'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3067,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-26'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3068,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-27'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3069,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-28'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3070,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-29'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3071,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-30'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3072,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-31'),(select id from chandigarh.egbpa_mstr_servicetype where code='01'),true,false,0,1,now(),1,now())
,(3073,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-09'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3074,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-10'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3075,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-11'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3076,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3077,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3078,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3079,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-15'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3080,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-16'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3081,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-17'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3082,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-18'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3083,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-19'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3084,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-20'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3085,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-21'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3086,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-22'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3087,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-23'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3088,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-24'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3089,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-25'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3090,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-26'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3091,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-27'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3092,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-28'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3093,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-29'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3094,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-30'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3095,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-31'),(select id from chandigarh.egbpa_mstr_servicetype where code='03'),true,false,0,1,now(),1,now())
,(3096,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-09'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3097,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-10'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3098,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-11'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3099,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3100,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3101,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3102,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-15'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3103,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-16'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3104,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-17'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3105,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-18'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3106,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-19'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3107,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-20'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3108,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-21'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3109,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-22'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3110,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-23'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3111,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-24'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3112,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-25'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3113,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-26'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3114,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-27'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3115,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-28'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3116,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-29'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3117,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-30'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3118,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-31'),(select id from chandigarh.egbpa_mstr_servicetype where code='04'),true,false,0,1,now(),1,now())
,(3119,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-09'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3120,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-10'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3121,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-11'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3122,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-12'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3123,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-13'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3124,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-14'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3125,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-15'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3126,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-16'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3127,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-17'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3128,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-18'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3129,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-19'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3130,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-20'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3131,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-21'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3132,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-22'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3133,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-23'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3134,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-24'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3135,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-25'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3136,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-26'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3137,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-27'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3138,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-28'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3139,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-29'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3140,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-30'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now())
,(3141,(select id from eg_checklist where checklisttypeid = (select id from chandigarh.eg_checklist_type where code='LTP') and code='LTP-31'),(select id from chandigarh.egbpa_mstr_servicetype where code='06'),true,false,0,1,now(),1,now());

update chandigarh.egbpa_checklist_servicetype_mapping set ismandatory=false, isrequired=true where checklist in (select id from chandigarh.eg_checklist where checklisttypeid=(select id from chandigarh.eg_checklist_type where code='LTP')) and servicetype in (select id from chandigarh.egbpa_mstr_servicetype where code in ('01','03','04','06'));

--------------------------------------------------------------------------------------

update chandigarh.egbpa_mstr_bpafee_common set "name"='Scrutiny fee', description='Scrutiny fee' where code='PF';
update chandigarh.egbpa_mstr_bpafee_common set "name"='Application Fees', description='Security fee' where code='AF';
update chandigarh.egbpa_mstr_bpafee_common set "name"='Additional Coverage fee', description='Additional Coverage fee' where code='ADF';
update chandigarh.egbpa_mstr_bpafee_common set "name"='GST 18%', description='GST 18%' where code='SF';
update chandigarh.egbpa_mstr_bpafee_common set "name"='Rule 5 fee', description='Rule 5 fee' where code='DPF';

update chandigarh.eg_demand_reason_master set reasonmaster='Scrutiny fee' where code='PF';
update chandigarh.eg_demand_reason_master set reasonmaster='Security fee' where code='AF';
update chandigarh.eg_demand_reason_master set reasonmaster='Additional Coverage fee' where code='ADF';
update chandigarh.eg_demand_reason_master set reasonmaster='GST 18%' where code='SF';
update chandigarh.eg_demand_reason_master set reasonmaster='Rule 5 fee' where code='DPF';

--------------------------------------------------------------------------------------

Insert into chandigarh.egcl_servicecategory (id, name, code, isactive, version, createdby, createddate, lastmodifiedby, lastmodifieddate) values (nextval('seq_egcl_servicecategory'), 'Atom Payment Gateway', 'APG', true, 0,  (select id from state.eg_user where username='egovernments'), now(), (select id from state.eg_user where username='egovernments'), now());

Insert into chandigarh.egcl_servicedetails (id, name, serviceurl, isenabled, callbackurl, servicetype, code, fund, fundsource, functionary, vouchercreation, scheme, subscheme, servicecategory, isvoucherapproved, vouchercutoffdate, created_by, created_date, modified_by, modified_date, ordernumber) values
(nextval('seq_egcl_servicedetails'), 'Atom Payment Gateway', 'https://paynetzuat.atomtech.in/paynetz/epi/fts', true, 'http://ulb.chandigarh.local.org:8080/collection/citizen/onlineReceipt-acceptMessageFromPaymentGateway.action', 'P', 'APG', (select id from chandigarh.fund where code='01'), null, null, false, null, null, (select id from chandigarh.egcl_servicecategory where code='APG'), false, now(), 1, now(), 1, now(), null);

update chandigarh.egcl_servicecategory set isactive=false where "name"='Punjab National Bank Payment Gateway';
update chandigarh.egcl_servicecategory set isactive=false where "name"='HDFC Bank Payment Gateway';
update chandigarh.egcl_servicedetails set isenabled=false where "name"='HDFC Bank Payment Gateway';

INSERT INTO chandigarh.schedulemapping (id,reporttype,schedule,schedulename,repsubtype,createdby,createddate,lastmodifiedby,lastmodifieddate,isremission) VALUES 
(1,'BS','B-01','Fund Balance',NULL,1,now(),NULL,NULL,NULL)
,(2,'BS','B-02','EARMARKED FUNDS',NULL,1,now(),NULL,NULL,NULL)
,(3,'BS','B-03','RESERVE',NULL,1,now(),NULL,NULL,NULL)
,(4,'BS','B-04','GRANTS, CONTRIBUTION FOR SPECIFIC PURPOSES',NULL,1,now(),NULL,NULL,NULL)
,(5,'BS','B-05','SECURED LOANS',NULL,1,now(),NULL,NULL,NULL)
,(6,'BS','B-06','UNSECURED LOANS',NULL,1,now(),NULL,NULL,NULL)
,(7,'BS','B-07','DEPOSITS RECEIVED',NULL,1,now(),NULL,NULL,NULL)
,(8,'BS','B-08','DEPOSIT WORKS',NULL,1,now(),NULL,NULL,NULL)
,(9,'BS','B-09','OTHER LIABILITIES',NULL,1,now(),NULL,NULL,NULL)
,(10,'BS','B-10','PROVISIONS',NULL,1,now(),NULL,NULL,NULL)
,(11,'BS','B-11','FIXED ASSETS',NULL,1,now(),NULL,NULL,NULL)
,(12,'BS','B-12','ACCUMULATED DEPRECIATION',NULL,1,now(),NULL,NULL,NULL)
,(13,'BS','B-13','CAPITAL WORK IN PROGRESS',NULL,1,now(),NULL,NULL,NULL)
,(14,'BS','B-14','INVESTMENT GENERAL FUND',NULL,1,now(),NULL,NULL,NULL)
,(15,'BS','B-15','INVESTMENT OTHER FUNDS',NULL,1,now(),NULL,NULL,NULL)
,(16,'BS','B-16','STOCK IN HAND',NULL,1,now(),NULL,NULL,NULL)
,(17,'BS','B-17','SUNDRY DEBTORS (RECEIVABLES)',NULL,1,now(),NULL,NULL,NULL)
,(18,'BS','B-18','ACCUMULATED PROVISIONS AGANIST DEBTORS',NULL,1,now(),NULL,NULL,NULL)
,(19,'BS','B-19','PREPAID EXPENSES',NULL,1,now(),NULL,NULL,NULL)
,(20,'BS','B-20','CASH AND BANK BALANCES',NULL,1,now(),NULL,NULL,NULL)
,(21,'BS','B-21','LOANS, ADVANCES  AND  DEPOSITS',NULL,1,now(),NULL,NULL,NULL)
,(22,'BS','B-22','ACCUMULATED PROVISONS AGAINST LOANS, ADVANCES  AND  DEPOSITS',NULL,1,now(),NULL,NULL,NULL)
,(23,'BS','B-23','OTHER ASSETS',NULL,1,now(),NULL,NULL,NULL)
,(24,'BS','B-24','MISCELLANEOUS EXPENDITURE TO BE WRITTEN OFF',NULL,1,now(),NULL,NULL,NULL)
,(25,'IE','I-01','TAX REVENUE',NULL,1,now(),NULL,NULL,NULL)
,(26,'IE','I-02','ASSIGNED REVENUE  AND  COMPENSATIONS',NULL,1,now(),NULL,NULL,NULL)
,(27,'IE','I-03','RENTAL INCOME FROM MUNICIPAL PROPERTIES',NULL,1,now(),NULL,NULL,NULL)
,(28,'IE','I-04','FEES  AND  USER CHARGES',NULL,1,now(),NULL,NULL,NULL)
,(29,'IE','I-05','SALE  AND  HIRE CHARGES',NULL,1,now(),NULL,NULL,NULL)
,(30,'IE','I-06','Revenue Grants, Contribution and Subsidies',NULL,1,now(),NULL,NULL,NULL)
,(31,'IE','I-07','Income from Investment',NULL,1,now(),NULL,NULL,NULL)
,(32,'IE','I-08','INTEREST EARNED',NULL,1,now(),NULL,NULL,NULL)
,(33,'IE','I-09','OTHER INCOME',NULL,1,now(),NULL,NULL,NULL)
,(34,'IE','I-10','Establishment Expenses',NULL,1,now(),NULL,NULL,NULL)
,(35,'IE','I-11','Administrative Expenses',NULL,1,now(),NULL,NULL,NULL)
,(36,'IE','I-12','OPERATION AND  MAINTENANCE EXPENSES',NULL,1,now(),NULL,NULL,NULL)
,(37,'IE','I-13','INTEREST  AND  FINANCE CHARGES',NULL,1,now(),NULL,NULL,NULL)
,(38,'IE','I-14','Programme Expenses',NULL,1,now(),NULL,NULL,NULL)
,(39,'IE','I-15','Revenue Grants and Contributions',NULL,1,now(),NULL,NULL,NULL)
,(40,'IE','I-16','PROVISIONS AND WRITE OFF',NULL,1,now(),NULL,NULL,NULL)
,(41,'IE','I-17','MISCELLANEOUS EXPENSES',NULL,1,now(),NULL,NULL,NULL)
,(42,'IE','I-18','Depreciation',NULL,1,now(),NULL,NULL,NULL)
,(43,'IE','I-19','PRIOR PERIOD ITEMS',NULL,1,now(),NULL,NULL,NULL)
,(44,'IE','I-20','Transfer to reserve funds',NULL,1,now(),NULL,NULL,NULL);

INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1224,'1','Income',NULL,false,NULL,NULL,NULL,'I',NULL,0,false,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1225,'2','Expenses',NULL,false,NULL,NULL,NULL,'E',NULL,0,false,NULL,NULL,NULL,NULL,NULL,NULL,'2',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1226,'4','Assets',NULL,false,NULL,NULL,NULL,'A',NULL,0,false,NULL,NULL,NULL,NULL,NULL,NULL,'4',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1227,'3','Liabilities',NULL,false,NULL,NULL,NULL,'L',NULL,0,false,NULL,NULL,NULL,NULL,NULL,NULL,'3',NULL,NULL,NULL,NULL,NULL,NULL,0);

INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1,'110','Tax Revenue',NULL,false,1224,NULL,'A','I',NULL,1,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(2,'120','Assigned Revenues & Compensations',NULL,false,1224,NULL,'A','I',NULL,1,NULL,NULL,26,NULL,NULL,NULL,NULL,'120',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(3,'130','Rental Income from Municipal Properties',NULL,false,1224,NULL,'A','I',NULL,1,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(4,'140','Fees & User Charges',NULL,false,1224,NULL,'A','I',NULL,1,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(5,'150','Sale & Hire Charges',NULL,false,1224,NULL,'A','I',NULL,1,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(6,'160','Revenue Grants, Contribution and Subsidies',NULL,false,1224,NULL,'A','I',NULL,1,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(7,'170','Income from Investments',NULL,false,1224,NULL,'A','I',NULL,1,NULL,NULL,31,NULL,NULL,NULL,NULL,'170',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(8,'171','Interest Earned',NULL,false,1224,NULL,'A','I',NULL,1,NULL,NULL,32,NULL,NULL,NULL,NULL,'171',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(9,'210','Establishment Expenses',NULL,false,1225,NULL,'A','E',NULL,1,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(10,'220','Administrative Expenses',NULL,false,1225,NULL,'A','E',NULL,1,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(11,'230','Operations & Maintenance',NULL,false,1225,NULL,'A','E',NULL,1,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(12,'240','Interest & Finance Charges',NULL,false,1225,NULL,'A','E',NULL,1,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(13,'250','Programme Expenses',NULL,false,1225,NULL,'A','E',NULL,1,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(14,'260','Revenue Grants, Contribution and Subsidies',NULL,false,1225,NULL,'A','E',NULL,1,NULL,NULL,39,NULL,NULL,NULL,NULL,'260',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(15,'270','Provisions and Write off',NULL,false,1225,NULL,'A','E',NULL,1,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(16,'271','Miscellaneous Expenses',NULL,false,1225,NULL,'A','E',NULL,1,NULL,NULL,41,NULL,NULL,NULL,NULL,'271',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(17,'272','Depreciation',NULL,false,1225,17,'A','E',NULL,1,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(18,'280','Prior Period Item',NULL,false,1225,NULL,'A','E',NULL,1,NULL,NULL,43,NULL,NULL,NULL,NULL,'280',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(19,'290','Transfer to Reserve Funds',NULL,false,1225,NULL,'A','E',NULL,1,NULL,NULL,44,NULL,NULL,NULL,NULL,'290',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(20,'310','Municipal Fund',NULL,false,1227,NULL,'A','L',NULL,1,NULL,NULL,1,NULL,NULL,NULL,NULL,'310',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(21,'311','Earmarked Funds',NULL,false,1227,NULL,'A','L',NULL,1,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(22,'312','Reserves',NULL,false,1227,18,'A','L',NULL,1,NULL,NULL,3,NULL,NULL,NULL,NULL,'312',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(23,'320','Grants , Contribution for specific purposes',NULL,false,1227,NULL,'A','L',NULL,1,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(24,'330','Secured Loans',NULL,false,1227,NULL,'A','L',NULL,1,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(25,'331','Unsecured Loans',NULL,false,1227,NULL,'A','L',NULL,1,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(26,'340','Deposits Received',NULL,false,1227,NULL,'A','L',NULL,1,NULL,NULL,7,NULL,NULL,NULL,NULL,'340',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(27,'341','Deposit works',NULL,false,1227,NULL,'A','L',NULL,1,NULL,NULL,8,NULL,NULL,NULL,NULL,'341',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(28,'350','Other Liabilities',NULL,false,1227,NULL,'A','L',NULL,1,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(29,'360','Provisions',NULL,false,1227,NULL,'A','L',NULL,1,NULL,NULL,10,NULL,NULL,NULL,NULL,'360',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(30,'410','Fixed Assets',NULL,false,1226,11,'A','A',NULL,1,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(31,'411','Accumulated Depreciation',NULL,false,1226,15,'A','A',NULL,1,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(32,'412','Capital Work-In-Progress',NULL,false,1226,NULL,'A','A',NULL,1,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(33,'420','Investments - General Fund',NULL,false,1226,NULL,'A','A',NULL,1,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(34,'421','Investments - Other Funds',NULL,false,1226,NULL,'A','A',NULL,1,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(35,'430','Stock - in- hand',NULL,false,1226,NULL,'A','A',NULL,1,NULL,NULL,16,NULL,NULL,NULL,NULL,'430',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(36,'431','Sundry Debtors (Receivables)',NULL,false,1226,NULL,'A','A',NULL,1,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(37,'432','Accumulated Provisions against Debtors (Receivables)',NULL,false,1226,NULL,'A','A',NULL,1,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(38,'440','Pre-paid Expenses',NULL,false,1226,NULL,'A','A',NULL,1,NULL,NULL,19,NULL,NULL,NULL,NULL,'440',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(39,'450','Cash and Bank balance',NULL,false,1226,NULL,'A','A',NULL,1,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(40,'460','Loans, Advances and Deposits',NULL,false,1226,NULL,'A','A',NULL,1,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(41,'461','Accumulated Provisions against Loans, Advances and Deposits',NULL,false,1226,NULL,'A','A',NULL,1,NULL,NULL,22,NULL,NULL,NULL,NULL,'461',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(42,'470','Other Assets',NULL,false,1226,NULL,'A','A',NULL,1,NULL,NULL,23,NULL,NULL,NULL,NULL,'470',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(43,'480','Miscellaneous Expenditure to be written off',NULL,false,1226,NULL,'A','A',NULL,1,NULL,NULL,24,NULL,NULL,NULL,NULL,'480',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(44,'11001','Property Tax',NULL,false,1,NULL,'A','I',NULL,2,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(45,'11002','Water Tax',NULL,false,1,NULL,'A','I',NULL,2,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(46,'11003','Sewerage Tax',NULL,false,1,NULL,'A','I',NULL,2,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(47,'11004','Conservancy Tax',NULL,false,1,NULL,'A','I',NULL,2,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(48,'11005','Lighting Tax',NULL,false,1,NULL,'A','I',NULL,2,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(49,'11006','Education Tax',NULL,false,1,NULL,'A','I',NULL,2,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(50,'11007','Vehicle Tax',NULL,false,1,NULL,'A','I',NULL,2,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(51,'11008','Tax on Animals',NULL,false,1,NULL,'A','I',NULL,2,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(52,'11011','Advertisement Tax',NULL,false,1,NULL,'A','I',NULL,2,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(53,'11012','Pilgrimage Tax',NULL,false,1,NULL,'A','I',NULL,2,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(54,'11051','Octroi and Toll',NULL,false,1,NULL,'A','I',NULL,2,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(55,'11052','Cess',NULL,false,1,NULL,'A','I',NULL,2,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(56,'11080','Others Taxes',NULL,false,1,NULL,'A','I',NULL,2,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(57,'11090','Tax Remission and Refund',NULL,false,1,NULL,'A','I',NULL,2,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(58,'12010','Taxes and Duties collected by others',NULL,false,2,NULL,'A','I',NULL,2,NULL,NULL,26,NULL,NULL,NULL,NULL,'120',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(59,'12020','Compensation in lieu of Taxes and duties',NULL,false,2,NULL,'A','I',NULL,2,NULL,NULL,26,NULL,NULL,NULL,NULL,'120',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(60,'12030','Compensations in lieu of Concessions',NULL,false,2,NULL,'A','I',NULL,2,NULL,NULL,26,NULL,NULL,NULL,NULL,'120',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(61,'13010','Rent from Civic Amenities',NULL,false,3,NULL,'A','I',NULL,2,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(62,'13020','Rent from Office Buildings',NULL,false,3,NULL,'A','I',NULL,2,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(63,'13030','Rent from Guest Houses',NULL,false,3,NULL,'A','I',NULL,2,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(64,'13040','Rent from lease of lands',NULL,false,3,NULL,'A','I',NULL,2,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(65,'13080','Other rents',NULL,false,3,NULL,'A','I',NULL,2,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(66,'13090','Rent remission and refund',NULL,false,3,NULL,'A','I',NULL,2,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(67,'14010','Empanelment and Registration Charges',NULL,false,4,NULL,'A','I',NULL,2,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(68,'14011','Licensing Fees',NULL,false,4,NULL,'A','I',NULL,2,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(69,'14012','Fees for Grant of Permit',NULL,false,4,NULL,'A','I',NULL,2,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(70,'14013','Fees for Certificate or Extract',NULL,false,4,NULL,'A','I',NULL,2,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(71,'14014','Development Charges',NULL,false,4,NULL,'A','I',NULL,2,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(72,'14015','Regularization Fees',NULL,false,4,NULL,'A','I',NULL,2,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(73,'14020','Penalties and Fines',NULL,false,4,NULL,'A','I',NULL,2,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(74,'14040','Other Fees',NULL,false,4,NULL,'A','I',NULL,2,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(75,'14050','User Charges',NULL,false,4,NULL,'A','I',NULL,2,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(76,'14060','Entry Fees',NULL,false,4,NULL,'A','I',NULL,2,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(77,'14070','Service Administrative Charges',NULL,false,4,NULL,'A','I',NULL,2,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(78,'14080','Other Charges',NULL,false,4,NULL,'A','I',NULL,2,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(79,'14090','Fees Remission and Refund',NULL,false,4,NULL,'A','I',NULL,2,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(80,'15010','Sale of Products',NULL,false,5,NULL,'A','I',NULL,2,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(81,'15011','Sale of Forms and Publications',NULL,false,5,NULL,'A','I',NULL,2,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(82,'15012','Sale of stores and scrap',NULL,false,5,NULL,'A','I',NULL,2,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(83,'15030','Sale of others',NULL,false,5,NULL,'A','I',NULL,2,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(84,'15040','Hire Charges for Vehicles',NULL,false,5,NULL,'A','I',NULL,2,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(85,'15041','Hire Charges on Equipments',NULL,false,5,NULL,'A','I',NULL,2,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(86,'16010','Revenue Grant',NULL,false,6,NULL,'A','I',NULL,2,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(87,'16020','Re-imbursement of expenses',NULL,false,6,NULL,'A','I',NULL,2,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(88,'16030','Contribution towards schemes',NULL,false,6,NULL,'A','I',NULL,2,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(89,'17010','Interest',NULL,false,7,NULL,'A','I',NULL,2,NULL,NULL,31,NULL,NULL,NULL,NULL,'170',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(90,'17020','Dividend',NULL,false,7,NULL,'A','I',NULL,2,NULL,NULL,31,NULL,NULL,NULL,NULL,'170',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(553,'180','Other Income',NULL,false,1224,NULL,'A','I',NULL,1,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(91,'17030','Income from projects taken up on commercial basis',NULL,false,7,NULL,'A','I',NULL,2,NULL,NULL,31,NULL,NULL,NULL,NULL,'170',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(92,'17040','Profit in Sale of Investments',NULL,false,7,NULL,'A','I',NULL,2,NULL,NULL,31,NULL,NULL,NULL,NULL,'170',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(93,'17080','Others',NULL,false,7,NULL,'A','I',NULL,2,NULL,NULL,31,NULL,NULL,NULL,NULL,'170',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(94,'17110','Interest from Bank Accounts',NULL,false,8,NULL,'A','I',NULL,2,NULL,NULL,32,NULL,NULL,NULL,NULL,'171',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(95,'17120','Interest on Loans and advances to Employees',NULL,false,8,NULL,'A','I',NULL,2,NULL,NULL,32,NULL,NULL,NULL,NULL,'171',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(96,'17130','Interest on loans to others',NULL,false,8,NULL,'A','I',NULL,2,NULL,NULL,32,NULL,NULL,NULL,NULL,'171',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(97,'17180','Other Interest',NULL,false,8,NULL,'A','I',NULL,2,NULL,NULL,32,NULL,NULL,NULL,NULL,'171',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(98,'18010','Deposits Forfeited',NULL,false,553,NULL,'A','I',NULL,2,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(99,'18011','Lapsed Deposits',NULL,false,553,NULL,'A','I',NULL,2,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(100,'18020','Insurance Claim Recovery',NULL,false,553,NULL,'A','I',NULL,2,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(101,'18030','Profit on Disposal of Fixed assets',NULL,false,553,NULL,'A','I',NULL,2,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(102,'18040','Recovery from Employees',NULL,false,553,NULL,'A','I',NULL,2,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(103,'18050','Unclaimed Refund Payable Liabilities Written Back',NULL,false,553,NULL,'A','I',NULL,2,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(104,'18060','Excess Provisions written back',NULL,false,553,NULL,'A','I',NULL,2,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(105,'18080','Miscellaneous Income',NULL,false,553,NULL,'A','I',NULL,2,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(106,'31010','General Fund',NULL,false,20,NULL,'A','L',NULL,2,NULL,NULL,1,NULL,NULL,NULL,NULL,'310',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(107,'31090','Excess of Income over Expenditure',NULL,false,20,NULL,'A','L',NULL,2,NULL,NULL,1,NULL,NULL,NULL,NULL,'310',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(108,'31110','Special Funds',NULL,false,21,NULL,'A','L',NULL,2,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(109,'31150','Sinking Funds',NULL,false,21,NULL,'A','L',NULL,2,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(110,'31170','Trust or Agency Funds',NULL,false,21,NULL,'A','L',NULL,2,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(111,'31210','Capital Contribution',NULL,false,22,NULL,'A','L',NULL,2,NULL,NULL,3,NULL,NULL,NULL,NULL,'312',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(112,'31211','Capital Reserve',NULL,false,22,NULL,'A','L',NULL,2,NULL,NULL,3,NULL,NULL,NULL,NULL,'312',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(113,'31220','Borrowing Redemption reserve',NULL,false,22,NULL,'A','L',NULL,2,NULL,NULL,3,NULL,NULL,NULL,NULL,'312',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(114,'31230','Special Funds (Utilised)',NULL,false,22,NULL,'A','L',NULL,2,NULL,NULL,3,NULL,NULL,NULL,NULL,'312',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(115,'31240','Statutory Reserve',NULL,false,22,NULL,'A','L',NULL,2,NULL,NULL,3,NULL,NULL,NULL,NULL,'312',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(116,'31250','General Reserve',NULL,false,22,NULL,'A','L',NULL,2,NULL,NULL,3,NULL,NULL,NULL,NULL,'312',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(117,'31260','Revaluation Reserve',NULL,false,22,NULL,'A','L',NULL,2,NULL,NULL,3,NULL,NULL,NULL,NULL,'312',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(118,'32010','Central Government',NULL,false,23,NULL,'A','L',NULL,2,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(119,'32020','State Government',NULL,false,23,NULL,'A','L',NULL,2,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(120,'32030','Other Government Agencies',NULL,false,23,NULL,'A','L',NULL,2,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(121,'32040','Financial Institutions',NULL,false,23,NULL,'A','L',NULL,2,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(122,'32050','Welfare Bodies',NULL,false,23,NULL,'A','L',NULL,2,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(123,'32060','International Organizations',NULL,false,23,NULL,'A','L',NULL,2,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(124,'32080','Others',NULL,false,23,NULL,'A','L',NULL,2,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(125,'33010','Loans from Central Government',NULL,false,24,NULL,'A','L',NULL,2,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(126,'33020','Loans from State Government',NULL,false,24,NULL,'A','L',NULL,2,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(127,'33030','Loans from Government Bodies and Association',NULL,false,24,NULL,'A','L',NULL,2,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(128,'33040','Loans from International Agencies',NULL,false,24,NULL,'A','L',NULL,2,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(129,'33050','Loans from Banks and Other Financial Institutions',NULL,false,24,NULL,'A','L',NULL,2,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(130,'33060','Other Term Loans',NULL,false,24,NULL,'A','L',NULL,2,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(131,'33070','Bonds and Debentures',NULL,false,24,NULL,'A','L',NULL,2,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(132,'33080','Other Loans',NULL,false,24,NULL,'A','L',NULL,2,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(133,'33110','Loans from Central Government',NULL,false,25,NULL,'A','L',NULL,2,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(134,'33120','Loans from State Government',NULL,false,25,NULL,'A','L',NULL,2,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(135,'33130','Loans from Government Bodies and Association',NULL,false,25,NULL,'A','L',NULL,2,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(136,'33140','Loans from International Agencies',NULL,false,25,NULL,'A','L',NULL,2,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(137,'33150','Loans from Banks and Other Financial Institutions',NULL,false,25,NULL,'A','L',NULL,2,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(138,'33160','Other Term Loans',NULL,false,25,NULL,'A','L',NULL,2,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(139,'33170','Bonds and Debentures',NULL,false,25,NULL,'A','L',NULL,2,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(140,'33180','Other Loans',NULL,false,25,NULL,'A','L',NULL,2,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(141,'34010','From Contractors Suppliers',NULL,false,26,NULL,'A','L',NULL,2,NULL,NULL,7,NULL,NULL,NULL,NULL,'340',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(142,'34020','Deposits – Revenues',NULL,false,26,NULL,'A','L',NULL,2,NULL,NULL,7,NULL,NULL,NULL,NULL,'340',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(143,'34030','From Staff',NULL,false,26,NULL,'A','L',NULL,2,NULL,NULL,7,NULL,NULL,NULL,NULL,'340',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(144,'34080','From Others',NULL,false,26,NULL,'A','L',NULL,2,NULL,NULL,7,NULL,NULL,NULL,NULL,'340',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(145,'34110','Civil works',NULL,false,27,NULL,'A','L',NULL,2,NULL,NULL,8,NULL,NULL,NULL,NULL,'341',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(146,'34120','Electrical works',NULL,false,27,NULL,'A','L',NULL,2,NULL,NULL,8,NULL,NULL,NULL,NULL,'341',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(147,'34180','Others',NULL,false,27,NULL,'A','L',NULL,2,NULL,NULL,8,NULL,NULL,NULL,NULL,'341',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(148,'35010','Creditors',NULL,false,28,NULL,'A','L',NULL,2,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(149,'35011','Employee liabilities',NULL,false,28,NULL,'A','L',NULL,2,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(150,'35012','Interest Accrued and due',NULL,false,28,NULL,'A','L',NULL,2,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(151,'35020','Recoveries payable',NULL,false,28,NULL,'A','L',NULL,2,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(152,'35030','Government Dues payable',NULL,false,28,NULL,'A','L',NULL,2,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(153,'35040','Refunds payable',NULL,false,28,NULL,'A','L',NULL,2,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(154,'35041','Advance Collection of Revenues',NULL,false,28,NULL,'A','L',NULL,2,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(155,'35080','Others',NULL,false,28,NULL,'A','L',NULL,2,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(156,'35090','Sale Proceeds',NULL,false,28,NULL,'A','L',NULL,2,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(157,'36010','Provisions for Expenses',NULL,false,29,NULL,'A','L',NULL,2,NULL,NULL,10,NULL,NULL,NULL,NULL,'360',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(158,'36020','Provision for Interest',NULL,false,29,NULL,'A','L',NULL,2,NULL,NULL,10,NULL,NULL,NULL,NULL,'360',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(159,'36030','Provision for Other Assets',NULL,false,29,NULL,'A','L',NULL,2,NULL,NULL,10,NULL,NULL,NULL,NULL,'360',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(160,'36040','Provisions for Doubtful receivables',NULL,false,29,NULL,'A','L',NULL,2,NULL,NULL,10,NULL,NULL,NULL,NULL,'360',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(161,'41010','Land',NULL,false,30,NULL,'A','A',NULL,2,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(162,'41020','Buildings',NULL,false,30,NULL,'A','A',NULL,2,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(163,'41030','Roads and Bridges',NULL,false,30,NULL,'A','A',NULL,2,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(164,'41031','Sewerage and Drainage',NULL,false,30,NULL,'A','A',NULL,2,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(165,'41032','Water works',NULL,false,30,NULL,'A','A',NULL,2,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(166,'41033','Public Lighting',NULL,false,30,NULL,'A','A',NULL,2,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(167,'41040','Plant and Machinery',NULL,false,30,NULL,'A','A',NULL,2,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(168,'41050','Vehicles',NULL,false,30,NULL,'A','A',NULL,2,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(169,'41060','Office and Other Equipments',NULL,false,30,NULL,'A','A',NULL,2,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(170,'41070','Furniture, Fixtures, Fittings and Electrical Appliances',NULL,false,30,NULL,'A','A',NULL,2,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(171,'41080','Other Fixed Assets',NULL,false,30,NULL,'A','A',NULL,2,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(172,'41090','Assets under Disposal',NULL,false,30,NULL,'A','A',NULL,2,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(173,'41120','Buildings',NULL,false,31,NULL,'A','A',NULL,2,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(174,'41130','Roads and Bridges',NULL,false,31,NULL,'A','A',NULL,2,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(175,'41131','Sewerage and Drainage',NULL,false,31,NULL,'A','A',NULL,2,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(176,'41132','Waterways',NULL,false,31,NULL,'A','A',NULL,2,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(177,'41133','Public Lighting',NULL,false,31,NULL,'A','A',NULL,2,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(178,'41140','Plant and Machinery',NULL,false,31,NULL,'A','A',NULL,2,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(179,'41150','Vehicles',NULL,false,31,NULL,'A','A',NULL,2,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(180,'41160','Office and Other Equipments',NULL,false,31,NULL,'A','A',NULL,2,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(181,'41170','Furniture, Fixtures, Fittings and Electrical Appliances',NULL,false,31,NULL,'A','A',NULL,2,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(182,'41180','Other Fixed Assets',NULL,false,31,NULL,'A','A',NULL,2,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(183,'41200','General',NULL,false,32,NULL,'A','A',NULL,2,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(184,'41210','Specific Grants',NULL,false,32,NULL,'A','A',NULL,2,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(185,'41220','Special funds',NULL,false,32,NULL,'A','A',NULL,2,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(186,'41230','Specific Schemes',NULL,false,32,NULL,'A','A',NULL,2,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(187,'42010','Central Government Securities',NULL,false,33,NULL,'A','A',NULL,2,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(188,'42020','State Government Securities',NULL,false,33,NULL,'A','A',NULL,2,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(189,'42030','Debentures and Bonds',NULL,false,33,NULL,'A','A',NULL,2,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(190,'42040','Preference Shares',NULL,false,33,NULL,'A','A',NULL,2,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(191,'42050','Equity Shares',NULL,false,33,NULL,'A','A',NULL,2,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(192,'42060','Units of Mutual Funds',NULL,false,33,NULL,'A','A',NULL,2,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(193,'42080','Other Investments',NULL,false,33,NULL,'A','A',NULL,2,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(194,'42090','Accumulated Provision',NULL,false,33,NULL,'A','A',NULL,2,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(195,'42110','Central Government Securities',NULL,false,34,NULL,'A','A',NULL,2,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(196,'42120','State Government Securities',NULL,false,34,NULL,'A','A',NULL,2,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(197,'42130','Debentures and Bonds',NULL,false,34,NULL,'A','A',NULL,2,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(198,'42140','Preference Shares',NULL,false,34,NULL,'A','A',NULL,2,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(199,'42150','Equity Shares',NULL,false,34,NULL,'A','A',NULL,2,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(200,'42160','Units of Mutual Funds',NULL,false,34,NULL,'A','A',NULL,2,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(201,'42180','Other Investments',NULL,false,34,NULL,'A','A',NULL,2,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(202,'42190','Accumulated Provision',NULL,false,34,NULL,'A','A',NULL,2,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(204,'43010','Stores',NULL,false,35,NULL,'A','A',NULL,2,NULL,NULL,16,NULL,NULL,NULL,NULL,'430',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(205,'43020','Loose Tools',NULL,false,35,NULL,'A','A',NULL,2,NULL,NULL,16,NULL,NULL,NULL,NULL,'430',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(206,'43080','Others',NULL,false,35,NULL,'A','A',NULL,2,NULL,NULL,16,NULL,NULL,NULL,NULL,'430',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(207,'43110','Receivables for Property Taxes',NULL,false,36,NULL,'A','A',NULL,2,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(208,'43119','Receivable for Other Taxes',NULL,false,36,NULL,'A','A',NULL,2,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(209,'43120','Receivables for Cess',NULL,false,36,NULL,'A','A',NULL,2,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(210,'43130','Receivable for Fees and User Charges',NULL,false,36,NULL,'A','A',NULL,2,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(211,'43140','Receivable from other sources',NULL,false,36,NULL,'A','A',NULL,2,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(212,'43150','Receivable from Government',NULL,false,36,NULL,'A','A',NULL,2,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(213,'43180','Receivables control accounts',NULL,false,36,NULL,'A','A',NULL,2,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(214,'43191','State Govt Cess/ levies in Property Taxes - Control account',NULL,false,36,NULL,'A','A',NULL,2,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(215,'43192','State Govt Cess/ levies in Water Taxes - Control account',NULL,false,36,NULL,'A','A',NULL,2,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(216,'43199','State Govt Cess/ levies in Other Taxes - Control account',NULL,false,36,NULL,'A','A',NULL,2,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(217,'43210','Provision for outstanding Property Taxes',NULL,false,37,NULL,'A','A',NULL,2,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(218,'43211','Provision for outstanding Water Taxes',NULL,false,37,NULL,'A','A',NULL,2,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(219,'43212','Provision for outstanding Other Taxes',NULL,false,37,NULL,'A','A',NULL,2,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(220,'43220','Provision for outstanding Cess',NULL,false,37,NULL,'A','A',NULL,2,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(221,'43230','Provision for outstanding Fees and User Charges',NULL,false,37,NULL,'A','A',NULL,2,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(222,'43240','Provision for outstanding other receivable',NULL,false,37,NULL,'A','A',NULL,2,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(225,'43291','State Govt Cess/ levies in Property Taxes - Provision account',NULL,false,37,NULL,'A','A',NULL,2,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(226,'43292','State Govt Cess/ levies in Water Taxes - Provision account',NULL,false,37,NULL,'A','A',NULL,2,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(227,'43299','State Govt Cess/ levies in Other Taxes - Provision account',NULL,false,37,NULL,'A','A',NULL,2,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(228,'44010','Establishment',NULL,false,38,NULL,'A','A',NULL,2,NULL,NULL,19,NULL,NULL,NULL,NULL,'440',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(229,'44020','Administration',NULL,false,38,NULL,'A','A',NULL,2,NULL,NULL,19,NULL,NULL,NULL,NULL,'440',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(230,'44030','Operations and Maintenance',NULL,false,38,NULL,'A','A',NULL,2,NULL,NULL,19,NULL,NULL,NULL,NULL,'440',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(231,'45010','Cash',NULL,false,39,NULL,'A','A',NULL,2,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(233,'45021','Nationalised Banks',NULL,false,39,NULL,'A','A',NULL,2,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(234,'45022','Other Scheduled Banks',NULL,false,39,NULL,'A','A',NULL,2,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(235,'45023','Scheduled Co-operative Banks',NULL,false,39,NULL,'A','A',NULL,2,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(236,'45024','Post Office',NULL,false,39,NULL,'A','A',NULL,2,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(237,'45041','Nationalised Banks',NULL,false,39,NULL,'A','A',NULL,2,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(238,'45042','Other Scheduled Banks',NULL,false,39,NULL,'A','A',NULL,2,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(239,'45043','Scheduled Co-operative Banks',NULL,false,39,NULL,'A','A',NULL,2,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(240,'45044','Post Office',NULL,false,39,NULL,'A','A',NULL,2,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(241,'45061','Nationalised Banks',NULL,false,39,NULL,'A','A',NULL,2,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(242,'45062','Other Scheduled Banks',NULL,false,39,NULL,'A','A',NULL,2,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(243,'45063','Scheduled Co-operative Banks',NULL,false,39,NULL,'A','A',NULL,2,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(244,'45064','Post Office',NULL,false,39,NULL,'A','A',NULL,2,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(245,'46010','Loans and advances to employees',NULL,false,40,NULL,'A','A',NULL,2,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(246,'46020','Employee Provident Fund Loans',NULL,false,40,NULL,'A','A',NULL,2,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(247,'46030','Loans to Others',NULL,false,40,NULL,'A','A',NULL,2,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(248,'46040','Advance to Suppliers and contractors',NULL,false,40,NULL,'A','A',NULL,2,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(249,'46050','Advance to others',NULL,false,40,NULL,'A','A',NULL,2,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(250,'46060','Deposits with external Agencies',NULL,false,40,NULL,'A','A',NULL,2,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(251,'46080','Other Current Assets',NULL,false,40,NULL,'A','A',NULL,2,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(252,'46110','Loans to Others',NULL,false,41,NULL,'A','A',NULL,2,NULL,NULL,22,NULL,NULL,NULL,NULL,'461',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(253,'46120','Advances',NULL,false,41,NULL,'A','A',NULL,2,NULL,NULL,22,NULL,NULL,NULL,NULL,'461',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(254,'46130','Deposits',NULL,false,41,NULL,'A','A',NULL,2,NULL,NULL,22,NULL,NULL,NULL,NULL,'461',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(255,'47010','Deposit Works – Expenditure',NULL,false,42,NULL,'A','A',NULL,2,NULL,NULL,23,NULL,NULL,NULL,NULL,'470',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(256,'47020','Inter Unit Accounts',NULL,false,42,NULL,'A','A',NULL,2,NULL,NULL,23,NULL,NULL,NULL,NULL,'470',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(257,'47030','Interest Control Payable',NULL,false,42,NULL,'A','A',NULL,2,NULL,NULL,23,NULL,NULL,NULL,NULL,'470',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(258,'48010','Loan Issue Expenses',NULL,false,43,NULL,'A','A',NULL,2,NULL,NULL,24,NULL,NULL,NULL,NULL,'480',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(259,'48020','Discount on Issue of loans',NULL,false,43,NULL,'A','A',NULL,2,NULL,NULL,24,NULL,NULL,NULL,NULL,'480',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(260,'48030','Others',NULL,false,43,NULL,'A','A',NULL,2,NULL,NULL,24,NULL,NULL,NULL,NULL,'480',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(261,'21010','Salaries, Wages and Bonus',NULL,false,9,NULL,'A','E',NULL,2,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(262,'21020','Benefits and Allowances',NULL,false,9,NULL,'A','E',NULL,2,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(263,'21030','Pension',NULL,false,9,NULL,'A','E',NULL,2,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(264,'21040','Other Terminal and Retirement Benefits',NULL,false,9,NULL,'A','E',NULL,2,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(265,'22010','Rent, Rates and Taxes',NULL,false,10,NULL,'A','E',NULL,2,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(266,'22011','Office maintenance',NULL,false,10,NULL,'A','E',NULL,2,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(267,'22012','Communication Expenses',NULL,false,10,NULL,'A','E',NULL,2,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(268,'22020','Books and Periodicals',NULL,false,10,NULL,'A','E',NULL,2,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(269,'22021','Printing and Stationery',NULL,false,10,NULL,'A','E',NULL,2,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(270,'22030','Traveling and Conveyance',NULL,false,10,NULL,'A','E',NULL,2,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(271,'22040','Insurance',NULL,false,10,NULL,'A','E',NULL,2,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(272,'22050','Audit Fees',NULL,false,10,NULL,'A','E',NULL,2,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(273,'22051','Legal Expenses',NULL,false,10,NULL,'A','E',NULL,2,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(274,'22052','Professional and other Fees',NULL,false,10,NULL,'A','E',NULL,2,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(275,'22060','Advertisement and Publicity',NULL,false,10,NULL,'A','E',NULL,2,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(276,'22061','Membership and subscriptions',NULL,false,10,NULL,'A','E',NULL,2,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(277,'22080','Others',NULL,false,10,NULL,'A','E',NULL,2,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(278,'23010','Power and Fuel',NULL,false,11,NULL,'A','E',NULL,2,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(279,'23020','Bulk Purchases',NULL,false,11,NULL,'A','E',NULL,2,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(280,'23030','Consumption of Stores',NULL,false,11,NULL,'A','E',NULL,2,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(281,'23040','Hire Charges',NULL,false,11,NULL,'A','E',NULL,2,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(282,'23050','Repairs and maintenance Infrastructure Assets',NULL,false,11,NULL,'A','E',NULL,2,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(283,'23051','Repairs and maintenance - Civic Amenities',NULL,false,11,NULL,'A','E',NULL,2,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(284,'23052','Repairs and maintenance – Buildings',NULL,false,11,NULL,'A','E',NULL,2,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(285,'23053','Repairs and maintenance – Vehicles',NULL,false,11,NULL,'A','E',NULL,2,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(286,'23059','Repairs and maintenance – Others',NULL,false,11,NULL,'A','E',NULL,2,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(287,'23080','Other operating and maintenance expenses',NULL,false,11,NULL,'A','E',NULL,2,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(288,'24010','Interest on Loans from Central Government',NULL,false,12,NULL,'A','E',NULL,2,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(289,'24020','Interest on Loans from State Government',NULL,false,12,NULL,'A','E',NULL,2,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(290,'24030','Interest on Loans from Government Bodies and Associations',NULL,false,12,NULL,'A','E',NULL,2,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(291,'24040','Interest on Loans from International Agencies',NULL,false,12,NULL,'A','E',NULL,2,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(292,'24050','Interest on Loans from Banks and Other Financial Institutions',NULL,false,12,NULL,'A','E',NULL,2,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(293,'24060','Other Interest',NULL,false,12,NULL,'A','E',NULL,2,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(294,'24070','Bank Charges',NULL,false,12,NULL,'A','E',NULL,2,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(295,'24080','Other Finance Expenses',NULL,false,12,NULL,'A','E',NULL,2,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(296,'25010','Election Expenses',NULL,false,13,NULL,'A','E',NULL,2,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(297,'25020','Own Programme',NULL,false,13,NULL,'A','E',NULL,2,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(298,'25030','Share in Programme of others',NULL,false,13,NULL,'A','E',NULL,2,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(299,'26010','Grants',NULL,false,14,NULL,'A','E',NULL,2,NULL,NULL,39,NULL,NULL,NULL,NULL,'260',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(300,'26020','Contributions',NULL,false,14,NULL,'A','E',NULL,2,NULL,NULL,39,NULL,NULL,NULL,NULL,'260',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(301,'26030','Subsidies',NULL,false,14,NULL,'A','E',NULL,2,NULL,NULL,39,NULL,NULL,NULL,NULL,'260',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(302,'27010','Provisions for Doubtful receivables',NULL,false,15,NULL,'A','E',NULL,2,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(303,'27020','Provision for other Assets',NULL,false,15,NULL,'A','E',NULL,2,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(304,'27030','Revenues written off',NULL,false,15,NULL,'A','E',NULL,2,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(305,'27040','Assets written off',NULL,false,15,NULL,'A','E',NULL,2,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(306,'27050','Miscellaneous Expense written off',NULL,false,15,NULL,'A','E',NULL,2,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(307,'27110','Loss on disposal of Assets',NULL,false,16,NULL,'A','E',NULL,2,NULL,NULL,41,NULL,NULL,NULL,NULL,'271',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(308,'27120','Loss on disposal of Investments',NULL,false,16,NULL,'A','E',NULL,2,NULL,NULL,41,NULL,NULL,NULL,NULL,'271',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(309,'27130','Decline in Value of Investments',NULL,false,16,NULL,'A','E',NULL,2,NULL,NULL,41,NULL,NULL,NULL,NULL,'271',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(310,'27180','Other miscellaneous expenditure',NULL,false,16,NULL,'A','E',NULL,2,NULL,NULL,41,NULL,NULL,NULL,NULL,'271',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(311,'27220','Buildings',NULL,false,17,NULL,'A','E',NULL,2,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(312,'27230','Roads and Bridges',NULL,false,17,NULL,'A','E',NULL,2,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(313,'27231','Sewerage and Drainage',NULL,false,17,NULL,'A','E',NULL,2,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(314,'27232','Waterways',NULL,false,17,NULL,'A','E',NULL,2,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(315,'27233','Public Lighting',NULL,false,17,NULL,'A','E',NULL,2,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(316,'27240','Plant and machinery',NULL,false,17,NULL,'A','E',NULL,2,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(317,'27250','Vehicles',NULL,false,17,NULL,'A','E',NULL,2,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(318,'27260','Office and Other Equipments',NULL,false,17,NULL,'A','E',NULL,2,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(319,'27270','Furniture, Fixtures, Fittings and Electrical Appliances',NULL,false,17,NULL,'A','E',NULL,2,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(320,'27280','Other Fixed Assets',NULL,false,17,NULL,'A','E',NULL,2,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(321,'28010','Taxes',NULL,false,18,NULL,'A','E',NULL,2,NULL,NULL,43,NULL,NULL,NULL,NULL,'280',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(322,'28020','Other – Revenues',NULL,false,18,NULL,'A','E',NULL,2,NULL,NULL,43,NULL,NULL,NULL,NULL,'280',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(323,'28030','Recovery of revenues written off',NULL,false,18,NULL,'A','E',NULL,2,NULL,NULL,43,NULL,NULL,NULL,NULL,'280',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(324,'28040','Other Income',NULL,false,18,NULL,'A','E',NULL,2,NULL,NULL,43,NULL,NULL,NULL,NULL,'280',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(325,'28050','Refund of Taxes',NULL,false,18,NULL,'A','E',NULL,2,NULL,NULL,43,NULL,NULL,NULL,NULL,'280',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(326,'28060','Refund of Other – Revenues',NULL,false,18,NULL,'A','E',NULL,2,NULL,NULL,43,NULL,NULL,NULL,NULL,'280',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(327,'28080','Other Expenses',NULL,false,18,NULL,'A','E',NULL,2,NULL,NULL,43,NULL,NULL,NULL,NULL,'280',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(328,'29010','Transfer to Capital Funds',NULL,false,19,NULL,'A','E',NULL,2,NULL,NULL,44,NULL,NULL,NULL,NULL,'290',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(329,'29020','Transfer to Earmarked Funds',NULL,false,19,NULL,'A','E',NULL,2,NULL,NULL,44,NULL,NULL,NULL,NULL,'290',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(330,'29030','Revenue Surplus',NULL,false,19,NULL,'A','E',NULL,2,NULL,NULL,44,NULL,NULL,NULL,NULL,'290',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(331,'1100101','Property Tax-Properties – General',NULL,true,44,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(332,'1100102','Property Tax-Vacant Land',NULL,true,44,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(333,'1100103','Property Tax-State Government Properties',NULL,true,44,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(334,'1100104','Property Tax-State Government Undertaking Properties',NULL,true,44,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(335,'1100105','Property Tax-Central Government Undertaking Properties',NULL,true,44,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(336,'1100106','Property Tax-Service Charges in lieu of Property Tax',NULL,true,44,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(337,'1100201','Water Tax-Properties – Water Tax',NULL,true,45,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(338,'1100202','Water Tax-Direct Water Tax',NULL,true,45,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(339,'1100301','Sewerage Tax-Properties – Sewerage Tax',NULL,true,46,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(340,'1100401','Conservancy Tax-Properties – Conservancy Tax',NULL,true,47,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(341,'1100501','Lighting Tax-Properties – Lighting Tax',NULL,true,48,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(342,'1100601','Education Tax-Properties – Education Tax',NULL,true,49,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(343,'1100700','Vehicle Tax-Vehicle Tax',NULL,true,50,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(344,'1100800','Tax on Animals-Tax on Animals',NULL,true,51,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(345,'1101101','Advertisement Tax-Land Hoardings',NULL,true,52,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(346,'1101102','Advertisement Tax-Bus Shelters',NULL,true,52,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(347,'1101103','Advertisement Tax-Hoardings on Vehicles',NULL,true,52,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(348,'1101104','Advertisement Tax-Traffic Signals',NULL,true,52,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(349,'1101105','Advertisement Tax-Adv. Tax on Cable Operators',NULL,true,52,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(350,'1101200','Pilgrimage Tax-Pilgrimage Tax',NULL,true,53,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(351,'1105100','Octroi and Toll-Octroi & Toll',NULL,true,54,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(352,'1105200','Cess-Cess',NULL,true,55,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(353,'1108001','Others Taxes-Tax on Cable Operators',NULL,true,56,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(354,'1109001','Tax Remission and Refund-Vacancy Remission',NULL,true,57,NULL,'A','I',NULL,4,NULL,NULL,25,NULL,NULL,NULL,NULL,'110',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(355,'1201001','Taxes and Duties collected by others-Surcharge on Stamp Duty for Transfer of Immovable Properties',NULL,true,58,NULL,'A','I',NULL,4,NULL,NULL,26,NULL,NULL,NULL,NULL,'120',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(356,'1201002','Taxes and Duties collected by others-Entertainment Tax',NULL,true,58,NULL,'A','I',NULL,4,NULL,NULL,26,NULL,NULL,NULL,NULL,'120',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(357,'1201003','Taxes and Duties collected by others-Profession Tax',NULL,true,58,NULL,'A','I',NULL,4,NULL,NULL,26,NULL,NULL,NULL,NULL,'120',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(358,'1202001','Compensation in lieu of Taxes and duties-Compensation in lieu of Octroi & Toll Tax',NULL,true,59,NULL,'A','I',NULL,4,NULL,NULL,26,NULL,NULL,NULL,NULL,'120',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(359,'1202002','Compensation in lieu of Taxes and duties-Compensation in lieu of Motor Vehicles Tax',NULL,true,59,NULL,'A','I',NULL,4,NULL,NULL,26,NULL,NULL,NULL,NULL,'120',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(360,'1203001','Compensations in lieu of Concessions-Property Tax compensations due to concessions to tax payers',NULL,true,60,NULL,'A','I',NULL,4,NULL,NULL,26,NULL,NULL,NULL,NULL,'120',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(361,'1301001','Rent from Civic Amenities-Markets',NULL,true,61,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(362,'1301002','Rent from Civic Amenities-Auditoriums',NULL,true,61,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(363,'1301003','Rent from Civic Amenities-Function/Community Halls',NULL,true,61,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(364,'1301004','Rent from Civic Amenities-Playgrounds',NULL,true,61,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(365,'1301005','Rent from Civic Amenities-Staff Quarters',NULL,true,61,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(366,'1301006','Rent from Civic Amenities-Canteens',NULL,true,61,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(367,'1301007','Rent from Civic Amenities-Stalls in Slaughter Houses',NULL,true,61,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(368,'1301008','Rent from Civic Amenities-Lease of Canteen/Cycle Stands in slaughter houses',NULL,true,61,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(369,'1301009','Rent from Civic Amenities-Nurseries',NULL,true,61,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(370,'1301010','Rent from Civic Amenities-Lease/Rent from Parking Places',NULL,true,61,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(371,'1301011','Rent from Civic Amenities-Lease of Traffic Islands/ Central Media',NULL,true,61,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(372,'1301012','Rent from Civic Amenities-Lease of Land for Bus Shelters',NULL,true,61,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(373,'1301013','Rent from Civic Amenities-Lease of Jetties & Boats',NULL,true,61,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(374,'1301014','Rent from Civic Amenities-Lease of Advertisement Right',NULL,true,61,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(375,'1301015','Rent from Civic Amenities-Shopping Complexes',NULL,true,61,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(376,'1302001','Rent from Office Buildings-Government',NULL,true,62,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(377,'1302002','Rent from Office Buildings-Private',NULL,true,62,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(378,'1303001','Rent from Guest Houses-Guest House',NULL,true,63,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(379,'1304001','Rent from lease of lands-Lease of Land',NULL,true,64,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(380,'1308000','Other rents-Other rents',NULL,true,65,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(381,'1309000','Rent remission and refund-Rent remission and refund',NULL,true,66,NULL,'A','I',NULL,4,NULL,NULL,27,NULL,NULL,NULL,NULL,'130',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(382,'1401001','Empanelment and Registration Charges-Contractors, Agencies etc',NULL,true,67,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(383,'1401002','Empanelment and Registration Charges-Technical Professionals',NULL,true,67,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(384,'1401003','Empanelment and Registration Charges-P.W. Contractors',NULL,true,67,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(385,'1401004','Empanelment and Registration Charges-Carts',NULL,true,67,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(386,'1401005','Empanelment and Registration Charges-Patients',NULL,true,67,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(387,'1401101','Licensing Fees-Trade License',NULL,true,68,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(388,'1401102','Licensing Fees-Cattle Pounding',NULL,true,68,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(389,'1401103','Licensing Fees-Licensing of Animals',NULL,true,68,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(390,'1401104','Licensing Fees-Slaughter House',NULL,true,68,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(391,'1401105','Licensing Fees-Butchers & Traders of Meat',NULL,true,68,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(392,'1401106','Licensing Fees-Encroachment Fee',NULL,true,68,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(393,'1401201','Fees for Grant of Permit-Layout/Sub-division',NULL,true,69,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(394,'1401202','Fees for Grant of Permit-Building Permit Fee',NULL,true,69,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(395,'1401203','Fees for Grant of Permit-Fee for Erection of Hoardings',NULL,true,69,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(396,'1401204','Fees for Grant of Permit-Intensive Zoning Fee',NULL,true,69,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(397,'1401205','Fees for Grant of Permit-Film Shooting in Parks',NULL,true,69,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(398,'1401206','Fees for Grant of Permit-Animal Slaughtering Fee',NULL,true,69,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(399,'1401207','Fees for Grant of Permit-Beef Export Slaughtering Fee',NULL,true,69,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(400,'1401301','Fees for Certificate or Extract-Copy of Plan/Certificate',NULL,true,70,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(401,'1401302','Fees for Certificate or Extract-Birth & Death certificates',NULL,true,70,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(402,'1401401','Development Charges-Building Development Charges',NULL,true,71,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(403,'1401402','Development Charges-Betterment Charges',NULL,true,71,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(404,'1401403','Development Charges-External Betterment Charges',NULL,true,71,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(405,'1401404','Development Charges-Special Development Contribution',NULL,true,71,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(406,'1401405','Development Charges-Impact Fee',NULL,true,71,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(407,'1401406','Development Charges-Un-Authorised Colony Improvement Contribution',NULL,true,71,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(408,'1401407','Development Charges-Open Space Contribution',NULL,true,71,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(409,'1401408','Development Charges-Parking Contribution',NULL,true,71,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(410,'1401409','Development Charges-Postage & Advertisement Charges',NULL,true,71,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(411,'1401410','Development Charges-Other town planning receipts',NULL,true,71,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(412,'1401501','Regularization Fees-Building Regularization',NULL,true,72,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(413,'1402001','Penalties and Fines-Penalty for Un-authorised Construction',NULL,true,73,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(414,'1402002','Penalties and Fines-Contractors',NULL,true,73,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(415,'1402003','Penalties and Fines-Continuing Penalty for Un- authorised Construction',NULL,true,73,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(416,'1402004','Penalties and Fines-Spot Fines',NULL,true,73,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(417,'1404001','Other Fees-Advertisement Fees',NULL,true,74,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(418,'1404002','Other Fees-Admission Fees',NULL,true,74,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(419,'1404003','Other Fees-Sports Fee',NULL,true,74,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(420,'1404004','Other Fees-Library Fees',NULL,true,74,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(421,'1404005','Other Fees-Survey fees',NULL,true,74,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(422,'1404006','Other Fees-Connection/ Disconnection Charges',NULL,true,74,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(423,'1404007','Other Fees-Notice Fees',NULL,true,74,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(424,'1404008','Other Fees-Warrant & Distraint Fees',NULL,true,74,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(425,'1404009','Other Fees-Mutation Fees',NULL,true,74,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(426,'1404010','Other Fees-Property Transfer Charges',NULL,true,74,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(427,'1404011','Other Fees-Other Fees',NULL,true,74,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(428,'1405001','User Charges-Medicines',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(429,'1405002','User Charges-Examination Charges',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(430,'1405003','User Charges-Ambulance',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(431,'1405004','User Charges-Funeral Van',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(432,'1405005','User Charges-Garbage Collection Charges',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(433,'1405006','User Charges-Littering and Debris collection',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(434,'1405007','User Charges-Septic Tank clearance',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(435,'1405008','User Charges-Special Sanitation Charges',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(436,'1405009','User Charges-Sewerage clearance charges',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(437,'1405010','User Charges-Crematorium Charges',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(438,'1405011','User Charges-Burial Ground Charges',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(439,'1405012','User Charges-Pay & use toilets',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(440,'1405013','User Charges-Water Supply',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(441,'1405014','User Charges-Sale of Electricity',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(442,'1405015','User Charges-Water Tanker',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(443,'1405016','User Charges-Meter charges',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(444,'1405017','User Charges-Fire Extinguishing',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(445,'1405018','User Charges-Lighting charges',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(446,'1405019','User Charges-Ticket charges',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(447,'1405020','User Charges-Luggage charges',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(448,'1405021','User Charges-Parking fees',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(449,'1405022','User Charges-Pre-Paid Parking Fee',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(450,'1405023','User Charges-Post-Paid Parking Fee',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(451,'1405024','User Charges-Quality Control Charges',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(452,'1405025','User Charges-Coaching Camp',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(453,'1405026','User Charges-Gym',NULL,true,75,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(454,'1406001','Entry Fees-Parks',NULL,true,76,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(455,'1406002','Entry Fees-Playgrounds',NULL,true,76,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(456,'1406003','Entry Fees-Swimming Pool',NULL,true,76,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(457,'1406004','Entry Fees-Zoo',NULL,true,76,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(458,'1406005','Entry Fees-Museum',NULL,true,76,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(459,'1406006','Entry Fees-Monuments',NULL,true,76,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(460,'1406007','Entry Fees-Gym',NULL,true,76,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(461,'1407001','Service Administrative Charges-Road Cutting & Restoration Charges',NULL,true,77,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(462,'1407002','Service Administrative Charges-Library Cess Collection Administrative Charges',NULL,true,77,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(463,'1407003','Service Administrative Charges-Plan Preparation Charges',NULL,true,77,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(464,'1407004','Service Administrative Charges-Removal of Un-authorised Hoardings',NULL,true,77,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(465,'1407005','Service Administrative Charges-Removal of Demolition',NULL,true,77,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(466,'1407006','Service Administrative Charges-Removal of Encroachments',NULL,true,77,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(467,'1407007','Service Administrative Charges-Removal of Public Nuisances',NULL,true,77,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(468,'1407008','Service Administrative Charges-Administrative Charges for Deposit Works',NULL,true,77,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(469,'1407009','Service Administrative Charges-NOC of Public Health Section',NULL,true,77,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(470,'1407011','Service Administrative Charges-Water Supply – Tap Estimation Charges',NULL,true,77,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(471,'1407012','Service Administrative Charges-Water Supply – Tap repairs',NULL,true,77,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(472,'1407013','Service Administrative Charges-Solvency Certificate',NULL,true,77,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(473,'1408001','Other Charges-Compounding fee',NULL,true,78,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(474,'1409001','Fees Remission and Refund-Refund of Fees',NULL,true,79,NULL,'A','I',NULL,4,NULL,NULL,28,NULL,NULL,NULL,NULL,'140',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(475,'1501001','Sale of Products-Tree-Guards',NULL,true,80,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(476,'1501002','Sale of Products-House Number Plates',NULL,true,80,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(477,'1501003','Sale of Products-Raw Water',NULL,true,80,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(478,'1501004','Sale of Products-Nursery plants',NULL,true,80,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(479,'1501005','Sale of Products-Grass, Flowers & Fruits',NULL,true,80,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(480,'1501006','Sale of Products-Trees',NULL,true,80,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(481,'1501007','Sale of Products-Rubbish',NULL,true,80,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(482,'1501008','Sale of Products-Garbage',NULL,true,80,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(483,'1501009','Sale of Products-Manure',NULL,true,80,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(484,'1501010','Sale of Products-Compost',NULL,true,80,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(485,'1501011','Sale of Products-Water meter',NULL,true,80,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(486,'1501101','Sale of Forms and Publications-Tenders Schedules',NULL,true,81,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(487,'1501102','Sale of Forms and Publications-Data, Plans & Maps',NULL,true,81,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(488,'1501103','Sale of Forms and Publications-Forms & Pass Books',NULL,true,81,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(489,'1501201','Sale of stores and scrap-Obsolete stores',NULL,true,82,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(490,'1501202','Sale of stores and scrap-Scrap',NULL,true,82,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(491,'1503001','Sale of others-Assets',NULL,true,83,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(492,'1503002','Sale of others-Old newspapers',NULL,true,83,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(493,'1504001','Hire Charges for Vehicles-Cars, Jeeps and Buses',NULL,true,84,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(494,'1504101','Hire Charges on Equipments-Rollers',NULL,true,85,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(495,'1504102','Hire Charges on Equipments-Tools & Equipments',NULL,true,85,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(496,'1504103','Hire Charges on Equipments-Medical Equipments',NULL,true,85,NULL,'A','I',NULL,4,NULL,NULL,29,NULL,NULL,NULL,NULL,'150',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(497,'1601001','Revenue Grant-Road Maintenance Grant',NULL,true,86,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(498,'1601002','Revenue Grant-Per Capita Grant',NULL,true,86,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(499,'1601003','Revenue Grant-Census Grant',NULL,true,86,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(500,'1601004','Revenue Grant-Election Grants',NULL,true,86,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(501,'1601005','Revenue Grant-Family Welfare Grant',NULL,true,86,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(502,'1602001','Re-imbursement of expenses-Election Expenses',NULL,true,87,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(503,'1602002','Re-imbursement of expenses-External-aided Projects',NULL,true,87,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(504,'1602003','Re-imbursement of expenses-Family Planning Centre Expenses',NULL,true,87,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(505,'1602004','Re-imbursement of expenses-Family Planning Incentives',NULL,true,87,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(506,'1602005','Re-imbursement of expenses-Anti Malaria Expenses',NULL,true,87,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(507,'1603001','Contribution towards schemes-Swarna Jayanthi Shari Rojgar Yojana',NULL,true,88,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(508,'1603002','Contribution towards schemes-National Slum Development Project',NULL,true,88,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(509,'1603003','Contribution towards schemes-Integrated Development of Small and Medium Towns',NULL,true,88,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(510,'1603004','Contribution towards schemes-Integrated Low Cost Sanitation',NULL,true,88,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(511,'1603005','Contribution towards schemes-Water Supply –Donation',NULL,true,88,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(512,'1603006','Contribution towards schemes-Sewerage Donation',NULL,true,88,NULL,'A','I',NULL,4,NULL,NULL,30,NULL,NULL,NULL,NULL,'160',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(513,'1701001','Interest-Fixed Deposits with Scheduled Banks',NULL,true,89,NULL,'A','I',NULL,4,NULL,NULL,31,NULL,NULL,NULL,NULL,'170',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(514,'1701002','Interest-Fixed Deposits with Private Banks',NULL,true,89,NULL,'A','I',NULL,4,NULL,NULL,31,NULL,NULL,NULL,NULL,'170',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(515,'1701003','Interest-Government Securities',NULL,true,89,NULL,'A','I',NULL,4,NULL,NULL,31,NULL,NULL,NULL,NULL,'170',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(516,'1701004','Interest-Post office deposits',NULL,true,89,NULL,'A','I',NULL,4,NULL,NULL,31,NULL,NULL,NULL,NULL,'170',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(517,'1702001','Dividend-Mutual Fund Investments',NULL,true,90,NULL,'A','I',NULL,4,NULL,NULL,31,NULL,NULL,NULL,NULL,'170',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(518,'1703000','Income from projects taken up on commercial basis-Income from projects taken up on commercial basis',NULL,true,91,NULL,'A','I',NULL,4,NULL,NULL,31,NULL,NULL,NULL,NULL,'170',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(519,'1704001','Profit in Sale of Investments-Mutual Fund Investment',NULL,true,92,NULL,'A','I',NULL,4,NULL,NULL,31,NULL,NULL,NULL,NULL,'170',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(520,'1708001','Others-Application in Value of Investment',NULL,true,93,NULL,'A','I',NULL,4,NULL,NULL,31,NULL,NULL,NULL,NULL,'170',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(521,'1711001','Interest from Bank Accounts-SB accounts',NULL,true,94,NULL,'A','I',NULL,4,NULL,NULL,32,NULL,NULL,NULL,NULL,'171',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(522,'1712001','Interest on Loans and advances to Employees-House Building Advance',NULL,true,95,NULL,'A','I',NULL,4,NULL,NULL,32,NULL,NULL,NULL,NULL,'171',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(523,'1712002','Interest on Loans and advances to Employees-Hire Purchase Quarters',NULL,true,95,NULL,'A','I',NULL,4,NULL,NULL,32,NULL,NULL,NULL,NULL,'171',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(524,'1712003','Interest on Loans and advances to Employees-Conveyance Advance (Vehicle Advance)',NULL,true,95,NULL,'A','I',NULL,4,NULL,NULL,32,NULL,NULL,NULL,NULL,'171',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(525,'1712004','Interest on Loans and advances to Employees-Marriage Advance',NULL,true,95,NULL,'A','I',NULL,4,NULL,NULL,32,NULL,NULL,NULL,NULL,'171',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(526,'1712005','Interest on Loans and advances to Employees-Computer Advance',NULL,true,95,NULL,'A','I',NULL,4,NULL,NULL,32,NULL,NULL,NULL,NULL,'171',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(527,'1713000','Interest on loans to others-Interest on loans to others',NULL,true,96,NULL,'A','I',NULL,4,NULL,NULL,32,NULL,NULL,NULL,NULL,'171',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(528,'1718001','Other Interest-Interest on Late Payment',NULL,true,97,NULL,'A','I',NULL,4,NULL,NULL,32,NULL,NULL,NULL,NULL,'171',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(529,'1718002','Other Interest-Interest on HP Sales',NULL,true,97,NULL,'A','I',NULL,4,NULL,NULL,32,NULL,NULL,NULL,NULL,'171',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(530,'1801001','Deposits Forfeited-Security Deposits',NULL,true,98,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(531,'1801101','Lapsed Deposits-Earnest Money Deposit',NULL,true,99,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(532,'1801102','Lapsed Deposits-Deposits',NULL,true,99,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(533,'1802000','Insurance Claim Recovery-Insurance Claim Recovery',NULL,true,100,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(534,'1803000','Profit on Disposal of Fixed assets-Profit on Disposal of Fixed assets',NULL,true,101,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(535,'1804001','Recovery from Employees-Recovery of Private Trunk calls charges',NULL,true,102,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(536,'1804002','Recovery from Employees-Recovery of Vehicle Use Charges',NULL,true,102,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(537,'1804003','Recovery from Employees-Recovery of Water & Electricity Charges',NULL,true,102,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(538,'1804004','Recovery from Employees-Recovery of cell phone charges',NULL,true,102,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(539,'1805001','Unclaimed Refund Payable Liabilities Written Back-Liabilities',NULL,true,103,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(540,'1805002','Unclaimed Refund Payable Liabilities Written Back-Stale Cheques',NULL,true,103,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(541,'1806001','Excess Provisions written back-Property Tax',NULL,true,104,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(542,'1806002','Excess Provisions written back-Advertisement Tax',NULL,true,104,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(543,'1806003','Excess Provisions written back-Trade License',NULL,true,104,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(544,'1806004','Excess Provisions written back-confluence.egovernments.org',NULL,true,104,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(545,'1806005','Excess Provisions written back-Loans & Advances to Others',NULL,true,104,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(546,'1806006','Excess Provisions written back-Others',NULL,true,104,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(547,'1808001','Miscellaneous Income-Pension & Leave Salary Contribution',NULL,true,105,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(548,'1808002','Miscellaneous Income-Bounced Cheques Realization Charges',NULL,true,105,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(549,'1808003','Miscellaneous Income-Fines Imposed by the Court',NULL,true,105,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(550,'1808004','Miscellaneous Income-Prior Period Income',NULL,true,105,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(551,'1808005','Miscellaneous Income-Penalties',NULL,true,105,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(552,'1808006','Miscellaneous Income-Other Income Un-Classified',NULL,true,105,NULL,'A','I',NULL,4,NULL,NULL,33,NULL,NULL,NULL,NULL,'180',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(554,'2101001','Salaries, Wages and Bonus-Basic Pay',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(555,'2101002','Salaries, Wages and Bonus-Dearness Allowance',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(556,'2101003','Salaries, Wages and Bonus-House Rent Allowance',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(557,'2101004','Salaries, Wages and Bonus-CCA',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(558,'2101005','Salaries, Wages and Bonus-Conveyance Allowance',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(559,'2101006','Salaries, Wages and Bonus-Interim Relief',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(560,'2101007','Salaries, Wages and Bonus-Surrender Leave Encashment',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(561,'2101008','Salaries, Wages and Bonus-Performance Bonus',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(562,'2101009','Salaries, Wages and Bonus-Honorarium/Sitting Fees to Corporators',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(563,'2101010','Salaries, Wages and Bonus-Honorarium – others',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(564,'2101011','Salaries, Wages and Bonus-Wages to workers through Placement Agencies',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(565,'2101012','Salaries, Wages and Bonus-Pensionary Contribution',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(566,'2101013','Salaries, Wages and Bonus-Anticipatory Pension',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(567,'2101016','Salaries, Wages and Bonus-Family Planning Incentive',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(568,'2101017','Salaries, Wages and Bonus-Graduate Allowance',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(569,'2101018','Salaries, Wages and Bonus-Post Graduate Allowance',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(570,'2101019','Salaries, Wages and Bonus-Stagnation Increment',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(571,'2101020','Salaries, Wages and Bonus-Addl. House Rent Allowance',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(572,'2101021','Salaries, Wages and Bonus-Dearness Pay',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(573,'2101022','Salaries, Wages and Bonus-Special Pay',NULL,true,261,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(574,'2102001','Benefits and Allowances-Leave Travel Concession',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(575,'2102002','Benefits and Allowances-Medical Reimbursement',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(576,'2102003','Benefits and Allowances-Tuition Fees',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(577,'2102004','Benefits and Allowances-Education Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(578,'2102005','Benefits and Allowances-Uniform allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(579,'2102006','Benefits and Allowances-Workmen Compensations',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(580,'2102007','Benefits and Allowances-Training',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(581,'2102008','Benefits and Allowances-Concession in Bus-Pass',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(582,'2102009','Benefits and Allowances-Staff Welfare Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(583,'2102010','Benefits and Allowances-Educational Concession & Scholarships',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(584,'2102011','Benefits and Allowances-Over Time Allowances',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(585,'2102012','Benefits and Allowances-Special Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(586,'2102013','Benefits and Allowances-Addl. Charge Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(587,'2102014','Benefits and Allowances-Suspension Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(588,'2102016','Benefits and Allowances-Night Shift Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(589,'2102017','Benefits and Allowances-Conveyance Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(590,'2102018','Benefits and Allowances-Non Private Practice Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(591,'2102019','Benefits and Allowances-Washing Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(592,'2102020','Benefits and Allowances-Physically Handicapped Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(593,'2102021','Benefits and Allowances-Tribal/Bad Climate Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(594,'2102022','Benefits and Allowances-Risk Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(595,'2102023','Benefits and Allowances-Typist Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(596,'2102024','Benefits and Allowances-Computer Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(597,'2102025','Benefits and Allowances-Academic Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(598,'2102026','Benefits and Allowances-Higher Classes Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(599,'2102027','Benefits and Allowances-Reading Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(600,'2102028','Benefits and Allowances-Supervisory Allowance',NULL,true,262,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(601,'2103001','Pension-Service Pension',NULL,true,263,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(602,'2103002','Pension-Family Pension',NULL,true,263,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(603,'2103003','Pension-Provisional Pension',NULL,true,263,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(604,'2104001','Other Terminal and Retirement Benefits-Leave encashment on retirement',NULL,true,264,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(605,'2104002','Other Terminal and Retirement Benefits-Death cum Retirement gratuity',NULL,true,264,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(606,'2104003','Other Terminal and Retirement Benefits-Commuted Pension',NULL,true,264,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(607,'2104004','Other Terminal and Retirement Benefits-Pension and Leave Salary Contributions for Deputationists',NULL,true,264,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(608,'2104005','Other Terminal and Retirement Benefits-General Provident Fund Shortfall',NULL,true,264,NULL,'A','E',NULL,4,NULL,NULL,34,NULL,NULL,NULL,NULL,'210',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(609,'2201001','Rent, Rates and Taxes-Rents for Hired Premises',NULL,true,265,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(610,'2201002','Rent, Rates and Taxes-Rates and Taxes',NULL,true,265,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(611,'2201101','Office maintenance-Electricity Charges',NULL,true,266,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(612,'2201102','Office maintenance-Water charges security expenses',NULL,true,266,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(613,'2201103','Office maintenance-Office Security',NULL,true,266,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(614,'2201201','Communication Expenses-Telephone',NULL,true,267,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(615,'2201202','Communication Expenses-Mobiles',NULL,true,267,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(616,'2201203','Communication Expenses-Faxes',NULL,true,267,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(617,'2201204','Communication Expenses-Leased Lines & Internet',NULL,true,267,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(618,'2202001','Books and Periodicals-Newspapers & Journals',NULL,true,268,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(619,'2202002','Books and Periodicals-Magazines',NULL,true,268,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(620,'2202101','Printing and Stationery-Printing',NULL,true,269,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(621,'2202102','Printing and Stationery-Stationery',NULL,true,269,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(622,'2202103','Printing and Stationery-Computer Consumables',NULL,true,269,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(623,'2202104','Printing and Stationery-Service postage',NULL,true,269,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(624,'2203001','Traveling and Conveyance-Traveling – In land',NULL,true,270,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(625,'2203002','Traveling and Conveyance-Traveling – Abroad',NULL,true,270,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(626,'2203003','Traveling and Conveyance-Fuel – (Petrol & Diesel)',NULL,true,270,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(627,'2204001','Insurance-Buildings',NULL,true,271,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(628,'2204002','Insurance-Vehicles',NULL,true,271,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(629,'2204003','Insurance-Electronic Equipment',NULL,true,271,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(630,'2204004','Insurance-Furniture',NULL,true,271,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(631,'2204005','Insurance-Inventory',NULL,true,271,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(632,'2205001','Audit Fees-Statutory Audit Fee',NULL,true,272,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(633,'2205002','Audit Fees-Certification Fee',NULL,true,272,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(634,'2205003','Audit Fees-Out of Pocket Expenses',NULL,true,272,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(635,'2205101','Legal Expenses-Legal Fees',NULL,true,273,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(636,'2205102','Legal Expenses-Cost of recoveries of tax revenue',NULL,true,273,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(637,'2205103','Legal Expenses-Cost of recoveries of other revenues',NULL,true,273,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(638,'2205104','Legal Expenses-Compensation ordered by courts',NULL,true,273,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(639,'2205201','Professional and other Fees-Consultancy Charges',NULL,true,274,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(640,'2205202','Professional and other Fees-Other Professional Charges',NULL,true,274,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(641,'2206001','Advertisement and Publicity-Advertisement – Print Media',NULL,true,275,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(642,'2206002','Advertisement and Publicity-Advertisement -TV & Radio media',NULL,true,275,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(643,'2206003','Advertisement and Publicity-Hoardings',NULL,true,275,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(644,'2206004','Advertisement and Publicity-Hospitality Expenses',NULL,true,275,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(645,'2206005','Advertisement and Publicity-Organisation of Festivals',NULL,true,275,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(646,'2206100','Membership and subscriptions-Membership & subscriptions',NULL,true,276,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(647,'2208000','Others-Others',NULL,true,277,NULL,'A','E',NULL,4,NULL,NULL,35,NULL,NULL,NULL,NULL,'220',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(648,'2301001','Power and Fuel-Power Charges for Street Lighting',NULL,true,278,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(649,'2301002','Power and Fuel-Power charges for water pumping',NULL,true,278,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(650,'2301003','Power and Fuel-Power charges for other services',NULL,true,278,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(651,'2301004','Power and Fuel-Fuel to Heavy Vehicles',NULL,true,278,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(652,'2301005','Power and Fuel-Fuel to Light Vehicles',NULL,true,278,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(653,'2301006','Power and Fuel-Fuel supply for field staff vehicles',NULL,true,278,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(654,'2302001','Bulk Purchases-Sanitation/Conservancy Material',NULL,true,279,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(655,'2302002','Bulk Purchases-Purchase of Medicines',NULL,true,279,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(656,'2303001','Consumption of Stores-Engineering Stores',NULL,true,280,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(657,'2303002','Consumption of Stores-Transport Stores',NULL,true,280,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(658,'2303003','Consumption of Stores-Medical Stores',NULL,true,280,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(659,'2303004','Consumption of Stores-Central Stationary Stores',NULL,true,280,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(660,'2304001','Hire Charges-Machinery Rent',NULL,true,281,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(661,'2304002','Hire Charges-Vehicles',NULL,true,281,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(662,'2305001','Repairs and maintenance Infrastructure Assets-Main Roads',NULL,true,282,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(663,'2305002','Repairs and maintenance Infrastructure Assets-By-lane Roads',NULL,true,282,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(664,'2305003','Repairs and maintenance Infrastructure Assets-Bridges',NULL,true,282,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(665,'2305004','Repairs and maintenance Infrastructure Assets-Fly-Overs',NULL,true,282,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(666,'2305005','Repairs and maintenance Infrastructure Assets-Water Supply Lines',NULL,true,282,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(667,'2305006','Repairs and maintenance Infrastructure Assets-Sewerage Lines',NULL,true,282,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(668,'2305007','Repairs and maintenance Infrastructure Assets-Storm Water Drains',NULL,true,282,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(669,'2305008','Repairs and maintenance Infrastructure Assets-Traffic Signals',NULL,true,282,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(670,'2305009','Repairs and maintenance Infrastructure Assets-Street Lighting',NULL,true,282,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(671,'2305010','Repairs and maintenance Infrastructure Assets-Burial Grounds',NULL,true,282,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(672,'2305101','Repairs and maintenance - Civic Amenities-Major Parks',NULL,true,283,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(673,'2305102','Repairs and maintenance - Civic Amenities-Minor Parks',NULL,true,283,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(674,'2305103','Repairs and maintenance - Civic Amenities-Colony Parks',NULL,true,283,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(675,'2305104','Repairs and maintenance - Civic Amenities-Stadium',NULL,true,283,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(676,'2305105','Repairs and maintenance - Civic Amenities-Play Grounds',NULL,true,283,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(677,'2305106','Repairs and maintenance - Civic Amenities-Swimming Pools',NULL,true,283,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(678,'2305107','Repairs and maintenance - Civic Amenities-Nursery',NULL,true,283,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(679,'2305108','Repairs and maintenance - Civic Amenities-Play Materials',NULL,true,283,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(680,'2305109','Repairs and maintenance - Civic Amenities-Public Toilets',NULL,true,283,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(681,'2305110','Repairs and maintenance - Civic Amenities-Market Yards',NULL,true,283,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(682,'2305111','Repairs and maintenance - Civic Amenities-Parking Lots',NULL,true,283,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(683,'2305112','Repairs and maintenance - Civic Amenities-Avenue and Other Plantations',NULL,true,283,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(684,'2305201','Repairs and maintenance – Buildings-Community Halls',NULL,true,284,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(685,'2305202','Repairs and maintenance – Buildings-Commercial Complex',NULL,true,284,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(686,'2305203','Repairs and maintenance – Buildings-Office Buildings',NULL,true,284,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(687,'2305204','Repairs and maintenance – Buildings-Staff Quarters',NULL,true,284,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(688,'2305301','Repairs and maintenance – Vehicles-Heavy Vehicles',NULL,true,285,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(689,'2305302','Repairs and maintenance – Vehicles-Light Vehicles',NULL,true,285,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(690,'2305901','Repairs and maintenance – Others-Furniture & Fixtures',NULL,true,286,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(691,'2305902','Repairs and maintenance – Others-Computers & Net Work',NULL,true,286,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(692,'2305903','Repairs and maintenance – Others-Electronic Equipment',NULL,true,286,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(693,'2305904','Repairs and maintenance – Others-Office Equipment',NULL,true,286,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(694,'2305905','Repairs and maintenance – Others-Survey & Drawing Equipment',NULL,true,286,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(695,'2305906','Repairs and maintenance – Others-Plant & Machinery',NULL,true,286,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(696,'2305907','Repairs and maintenance – Others-Conservancy Tools',NULL,true,286,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(697,'2308001','Other operating and maintenance expenses-Garbage Clearance',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(698,'2308002','Other operating and maintenance expenses-Testing & Inspection',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(699,'2308003','Other operating and maintenance expenses-Field Survey & Inspection',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(700,'2308004','Other operating and maintenance expenses-Water Purification',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(701,'2308005','Other operating and maintenance expenses-Mapping, Plotting & Drawing Expenses',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(702,'2308006','Other operating and maintenance expenses-Naming & Numbering of Streets',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(703,'2308007','Other operating and maintenance expenses-Demolition & Removal Expenses',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(704,'2308008','Other operating and maintenance expenses-Quality Control Expenses',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(705,'2308009','Other operating and maintenance expenses-Prevention of Epidemics',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(706,'2308010','Other operating and maintenance expenses-Collection and Testing of Food Samples',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(707,'2308011','Other operating and maintenance expenses-Expenses on Unclaimed Dead Bodies',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(708,'2308012','Other operating and maintenance expenses-Control of Stray Animals',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(709,'2308013','Other operating and maintenance expenses-Sanitation/Conservancy Expenses',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(710,'2308014','Other operating and maintenance expenses-Intensive/Special Sanitation including for Fairs & Festivals',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(711,'2308015','Other operating and maintenance expenses-Maintenance of Garbage Dumping Yards/Transfer Stations',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(712,'2308016','Other operating and maintenance expenses-Maintenance of slaughter houses',NULL,true,287,NULL,'A','E',NULL,4,NULL,NULL,36,NULL,NULL,NULL,NULL,'230',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(713,'2401000','Interest on Loans from Central Government-Interest on Loans from Central Government',NULL,true,288,NULL,'A','E',NULL,4,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(714,'2402000','Interest on Loans from State Government-Interest on Loans from State Government',NULL,true,289,NULL,'A','E',NULL,4,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(715,'2403000','Interest on Loans from Government Bodies and Associations-Interest on Loans from Government Bodies & Associations',NULL,true,290,NULL,'A','E',NULL,4,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(716,'2404000','Interest on Loans from International Agencies-Interest on Loans from International Agencies',NULL,true,291,NULL,'A','E',NULL,4,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(717,'2405000','Interest on Loans from Banks and Other Financial Institutions-Interest on Loans from Banks and Other Financial Institutions',NULL,true,292,NULL,'A','E',NULL,4,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(718,'2406001','Other Interest-Bonds',NULL,true,293,NULL,'A','E',NULL,4,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(719,'2406002','Other Interest-Finance Lease',NULL,true,293,NULL,'A','E',NULL,4,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(720,'2406003','Other Interest-Hire Purchase',NULL,true,293,NULL,'A','E',NULL,4,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(721,'2407001','Bank Charges-Miscellaneous Bank Charges',NULL,true,294,NULL,'A','E',NULL,4,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(722,'2408001','Other Finance Expenses-Expenses on Issue of Bonds',NULL,true,295,NULL,'A','E',NULL,4,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(723,'2408002','Other Finance Expenses-Surveillance Fee',NULL,true,295,NULL,'A','E',NULL,4,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(724,'2408003','Other Finance Expenses-Transaction Processing For Collections',NULL,true,295,NULL,'A','E',NULL,4,NULL,NULL,37,NULL,NULL,NULL,NULL,'240',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(725,'2501001','Election Expenses-Local Body Elections',NULL,true,296,NULL,'A','E',NULL,4,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(726,'2501002','Election Expenses-MLA/MP Elections',NULL,true,296,NULL,'A','E',NULL,4,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(727,'2502001','Own Programme-Environmental Awareness Programme',NULL,true,297,NULL,'A','E',NULL,4,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(728,'2502002','Own Programme-Urban Malaria Eradication',NULL,true,297,NULL,'A','E',NULL,4,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(729,'2502003','Own Programme-Study Tour/Trainings',NULL,true,297,NULL,'A','E',NULL,4,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(730,'2502004','Own Programme-Seminars',NULL,true,297,NULL,'A','E',NULL,4,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(731,'2502005','Own Programme-Voluntary Garbage Disposal Programme',NULL,true,297,NULL,'A','E',NULL,4,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(732,'2502006','Own Programme-Cultural Programmes',NULL,true,297,NULL,'A','E',NULL,4,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(733,'2502007','Own Programme-Community Organisation / Mobilisation',NULL,true,297,NULL,'A','E',NULL,4,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(734,'2502008','Own Programme-Special Nutritation Programme',NULL,true,297,NULL,'A','E',NULL,4,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(735,'2502009','Own Programme-Summer/Winter Coaching Camps',NULL,true,297,NULL,'A','E',NULL,4,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(736,'2503001','Share in Programme of others-Family Welfare Programme',NULL,true,298,NULL,'A','E',NULL,4,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(737,'2503002','Share in Programme of others-Financial Aid for Urban Self Help Groups',NULL,true,298,NULL,'A','E',NULL,4,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(738,'2503003','Share in Programme of others-Disbursements to T&S / DWCUA and Self Help groups under SJSRY',NULL,true,298,NULL,'A','E',NULL,4,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(739,'2503004','Share in Programme of others-Shilparamam Greening/Others',NULL,true,298,NULL,'A','E',NULL,4,NULL,NULL,38,NULL,NULL,NULL,NULL,'250',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(740,'2601000','Grants-Grants',NULL,true,299,NULL,'A','E',NULL,4,NULL,NULL,39,NULL,NULL,NULL,NULL,'260',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(741,'2602000','Contributions-Contributions',NULL,true,300,NULL,'A','E',NULL,4,NULL,NULL,39,NULL,NULL,NULL,NULL,'260',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(742,'2603000','Subsidies-Subsidies',NULL,true,301,NULL,'A','E',NULL,4,NULL,NULL,39,NULL,NULL,NULL,NULL,'260',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(743,'2701001','Provisions for Doubtful receivables-Property Tax',NULL,true,302,NULL,'A','E',NULL,4,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(744,'2701002','Provisions for Doubtful receivables-Advertisement Tax',NULL,true,302,NULL,'A','E',NULL,4,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(745,'2701003','Provisions for Doubtful receivables-Others',NULL,true,302,NULL,'A','E',NULL,4,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(746,'2702001','Provision for other Assets-Inventory',NULL,true,303,NULL,'A','E',NULL,4,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(747,'2702002','Provision for other Assets-Investments',NULL,true,303,NULL,'A','E',NULL,4,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(748,'2702003','Provision for other Assets-Loans & Advances to others',NULL,true,303,NULL,'A','E',NULL,4,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(749,'2703001','Revenues written off-Property Tax',NULL,true,304,NULL,'A','E',NULL,4,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(750,'2703002','Revenues written off-Advertisement Tax',NULL,true,304,NULL,'A','E',NULL,4,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(751,'2703003','Revenues written off-Assigned Revenue',NULL,true,304,NULL,'A','E',NULL,4,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(752,'2704001','Assets written off-Stores',NULL,true,305,NULL,'A','E',NULL,4,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(753,'2704002','Assets written off-Investments',NULL,true,305,NULL,'A','E',NULL,4,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(754,'2704003','Assets written off-Decline in Fixed Assets',NULL,true,305,NULL,'A','E',NULL,4,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(755,'2704004','Assets written off-Loans & Advances to others',NULL,true,305,NULL,'A','E',NULL,4,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(756,'2705001','Miscellaneous Expense written off-Deferred Revenue Expenses',NULL,true,306,NULL,'A','E',NULL,4,NULL,NULL,40,NULL,NULL,NULL,NULL,'270',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(757,'2711000','Loss on disposal of Assets-Loss on disposal of Assets',NULL,true,307,NULL,'A','E',NULL,4,NULL,NULL,41,NULL,NULL,NULL,NULL,'271',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(758,'2712001','Loss on disposal of Investments-Mutual Fund Investment',NULL,true,308,NULL,'A','E',NULL,4,NULL,NULL,41,NULL,NULL,NULL,NULL,'271',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(759,'2713000','Decline in Value of Investments-Decline in Value of Investments',NULL,true,309,NULL,'A','E',NULL,4,NULL,NULL,41,NULL,NULL,NULL,NULL,'271',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(760,'2718000','Other miscellaneous expenditure-Other miscellaneous expenditure',NULL,true,310,NULL,'A','E',NULL,4,NULL,NULL,41,NULL,NULL,NULL,NULL,'271',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(761,'2722000','Buildings-Buildings',NULL,true,311,NULL,'A','E',NULL,4,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(762,'2723000','Roads and Bridges-Roads & Bridges',NULL,true,312,NULL,'A','E',NULL,4,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(763,'2723100','Sewerage and Drainage-Sewerage and Drainage',NULL,true,313,NULL,'A','E',NULL,4,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(764,'2723200','Waterways-Waterways',NULL,true,314,NULL,'A','E',NULL,4,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(765,'2723300','Public Lighting-Public Lighting',NULL,true,315,NULL,'A','E',NULL,4,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(766,'2724000','Plant and machinery-Plant & machinery',NULL,true,316,NULL,'A','E',NULL,4,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(767,'2725000','Vehicles-Vehicles',NULL,true,317,NULL,'A','E',NULL,4,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(768,'2726000','Office and Other Equipments-Office & Other Equipments',NULL,true,318,NULL,'A','E',NULL,4,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(769,'2727000','Furniture, Fixtures, Fittings and Electrical Appliances-Furniture, Fixtures, Fittings and Electrical Appliances',NULL,true,319,NULL,'A','E',NULL,4,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(770,'2728000','Other Fixed Assets-Other Fixed Assets',NULL,true,320,NULL,'A','E',NULL,4,NULL,NULL,42,NULL,NULL,NULL,NULL,'272',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(771,'2801000','Taxes-Taxes',NULL,true,321,NULL,'A','E',NULL,4,NULL,NULL,43,NULL,NULL,NULL,NULL,'280',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(772,'2802000','Other – Revenues-Other – Revenues',NULL,true,322,NULL,'A','E',NULL,4,NULL,NULL,43,NULL,NULL,NULL,NULL,'280',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(773,'2803000','Recovery of revenues written off-Recovery of revenues written off',NULL,true,323,NULL,'A','E',NULL,4,NULL,NULL,43,NULL,NULL,NULL,NULL,'280',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(774,'2804000','Other Income-Other Income',NULL,true,324,NULL,'A','E',NULL,4,NULL,NULL,43,NULL,NULL,NULL,NULL,'280',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(775,'2805000','Refund of Taxes-Refund of Taxes',NULL,true,325,NULL,'A','E',NULL,4,NULL,NULL,43,NULL,NULL,NULL,NULL,'280',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(776,'2806000','Refund of Other – Revenues-Refund of Other – Revenues',NULL,true,326,NULL,'A','E',NULL,4,NULL,NULL,43,NULL,NULL,NULL,NULL,'280',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(777,'2808000','Other Expenses-Other Expenses',NULL,true,327,NULL,'A','E',NULL,4,NULL,NULL,43,NULL,NULL,NULL,NULL,'280',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(778,'2901001','Transfer to Capital Funds-City Development Fund',NULL,true,328,NULL,'A','E',NULL,4,NULL,NULL,44,NULL,NULL,NULL,NULL,'290',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(779,'2901002','Transfer to Capital Funds-UCD Fund',NULL,true,328,NULL,'A','E',NULL,4,NULL,NULL,44,NULL,NULL,NULL,NULL,'290',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(780,'2902001','Transfer to Earmarked Funds-Sinking Fund',NULL,true,329,NULL,'A','E',NULL,4,NULL,NULL,44,NULL,NULL,NULL,NULL,'290',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(781,'2902002','Transfer to Earmarked Funds-Salary Reserve Fund',NULL,true,329,NULL,'A','E',NULL,4,NULL,NULL,44,NULL,NULL,NULL,NULL,'290',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(782,'2902003','Transfer to Earmarked Funds-Pension Reserve Fund',NULL,true,329,NULL,'A','E',NULL,4,NULL,NULL,44,NULL,NULL,NULL,NULL,'290',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(783,'2903001','Revenue Surplus-General Reserve',NULL,true,330,NULL,'A','E',NULL,4,NULL,NULL,44,NULL,NULL,NULL,NULL,'290',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(784,'2903002','Revenue Surplus-Surplus of Income Over Expenditure',NULL,true,330,NULL,'A','E',NULL,4,NULL,NULL,44,NULL,NULL,NULL,NULL,'290',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(785,'3101001','General Fund-Revenue Transfers',NULL,true,106,NULL,'A','L',NULL,4,NULL,NULL,1,NULL,NULL,NULL,NULL,'310',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(786,'3111001','Special Funds-City Development Fund',NULL,true,108,NULL,'A','L',NULL,4,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(787,'3111002','Special Funds-Urban Poverty Alleviation Fund',NULL,true,108,NULL,'A','L',NULL,4,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(788,'3111003','Special Funds-Abatement of Pollution of Rivers',NULL,true,108,NULL,'A','L',NULL,4,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(789,'3111004','Special Funds-Preservation of Heritage sites fund',NULL,true,108,NULL,'A','L',NULL,4,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(790,'3111005','Special Funds-Revolving Fund',NULL,true,108,NULL,'A','L',NULL,4,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(791,'3115001','Sinking Funds-City Development Bonds',NULL,true,109,NULL,'A','L',NULL,4,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(792,'3117001','Trust or Agency Funds-Salary Reserve Fund',NULL,true,110,NULL,'A','L',NULL,4,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(793,'3117002','Trust or Agency Funds-Pension Fund',NULL,true,110,NULL,'A','L',NULL,4,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(794,'3117003','Trust or Agency Funds-General Provident Fund',NULL,true,110,NULL,'A','L',NULL,4,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(795,'3117004','Trust or Agency Funds-Leave Salary and Gratuity Fund',NULL,true,110,NULL,'A','L',NULL,4,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(796,'3117005','Trust or Agency Funds-Employee Welfare Fund',NULL,true,110,NULL,'A','L',NULL,4,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(797,'3117006','Trust or Agency Funds-Old age Pension',NULL,true,110,NULL,'A','L',NULL,4,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(798,'3117007','Trust or Agency Funds-Widow Pension',NULL,true,110,NULL,'A','L',NULL,4,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(799,'3117008','Trust or Agency Funds-Disabled Pension',NULL,true,110,NULL,'A','L',NULL,4,NULL,NULL,2,NULL,NULL,NULL,NULL,'311',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(801,'3121000','Capital Contribution-Capital Contribution',NULL,true,111,NULL,'A','L',NULL,4,NULL,NULL,3,NULL,NULL,NULL,NULL,'312',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(802,'3121100','Capital Reserve-Capital Reserve',NULL,true,112,NULL,'A','L',NULL,4,NULL,NULL,3,NULL,NULL,NULL,NULL,'312',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(803,'3122000','Borrowing Redemption reserve-Borrowing Redemption reserve',NULL,true,113,NULL,'A','L',NULL,4,NULL,NULL,3,NULL,NULL,NULL,NULL,'312',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(804,'3123000','Special Funds (Utilised)-Special Funds (Utilised)',NULL,true,114,NULL,'A','L',NULL,4,NULL,NULL,3,NULL,NULL,NULL,NULL,'312',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(805,'3124000','Statutory Reserve-Statutory Reserve',NULL,true,115,NULL,'A','L',NULL,4,NULL,NULL,3,NULL,NULL,NULL,NULL,'312',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(806,'3125000','General Reserve-General Reserve',NULL,true,116,NULL,'A','L',NULL,4,NULL,NULL,3,NULL,NULL,NULL,NULL,'312',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(807,'3126001','Revaluation Reserve-Fixed Assets',NULL,true,117,NULL,'A','L',NULL,4,NULL,NULL,3,NULL,NULL,NULL,NULL,'312',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(808,'3201001','Central Government-Adarsh Basti Scheme',NULL,true,118,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(809,'3201002','Central Government-Abatement of Pollution of Rivers',NULL,true,118,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(810,'3202001','State Government-Water Supply',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(811,'3202002','State Government-XIIth Finance Commission',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(812,'3202003','State Government-MP Local Area Development',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(813,'3202004','State Government-Assembly Constituency Development Programme',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(814,'3202005','State Government-Clean & Green Programme',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(815,'3202006','State Government-Janmabhoomi Programme',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(816,'3202007','State Government-Mega City Project (MCP)',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(817,'3202008','State Government-Improvement of Cities',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(818,'3202009','State Government-Charminar Pedestrianisation Project',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(819,'3202010','State Government-Rain Water Harvesting (RWH)',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(820,'3202011','State Government-Make City Green',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(821,'3202012','State Government-MRTS Project',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(822,'3202013','State Government-TWINS/e-Seva Project',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(823,'3202014','State Government-National Slum Development Programme (NSDP)',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(824,'3202015','State Government-Swarna Jayanthi Shahri Rozgar Yojana (SJSRY)/NRY',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(825,'3202016','State Government-Balika Samrudhi Yojana (BSY)',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(826,'3202017','State Government-Local Water Supply & Sewerage in Slums',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(827,'3202018','State Government-Urban Community Development (UCD)',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(828,'3202019','State Government-IPP VIII Project',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(829,'3202020','State Government-Natural Calamities Grant',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(830,'3202021','State Government-Reimbursement from Govt Departments including R&B',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(831,'3202022','State Government-Abatement of Pollution of Rivers',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(832,'3202023','State Government-Others',NULL,true,119,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(833,'3203001','Other Government Agencies-Green House Gas Pollution Prevention Project',NULL,true,120,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(834,'3204000','Financial Institutions-Financial Institutions',NULL,true,121,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(835,'3205000','Welfare Bodies-Welfare Bodies',NULL,true,122,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(836,'3206001','International Organizations-Water & Sanitation Programme (World Bank)',NULL,true,123,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(837,'3206002','International Organizations-Urban Management/Cities Alliance Programme (UNCHS – World Bank)',NULL,true,123,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(838,'3208001','Others-Public Contribution for Works',NULL,true,124,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(839,'3208002','Others-Abatement of Pollution of River (Industry Contribution)',NULL,true,124,NULL,'A','L',NULL,4,NULL,NULL,4,NULL,NULL,NULL,NULL,'320',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(840,'3301001','Loans from Central Government-Short Term Loan',NULL,true,125,NULL,'A','L',NULL,4,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(841,'3301002','Loans from Central Government-Long Term Loan',NULL,true,125,NULL,'A','L',NULL,4,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(842,'3302001','Loans from State Government-Short Term Loan',NULL,true,126,NULL,'A','L',NULL,4,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(843,'3302002','Loans from State Government-Long Term Loan',NULL,true,126,NULL,'A','L',NULL,4,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(844,'3303000','Loans from Government Bodies and Association-Loans from Government Bodies & Association',NULL,true,127,NULL,'A','L',NULL,4,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(845,'3304000','Loans from International Agencies-Loans from International Agencies',NULL,true,128,NULL,'A','L',NULL,4,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(846,'3305001','Loans from Banks and Other Financial Institutions-From Banks',NULL,true,129,NULL,'A','L',NULL,4,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(847,'3305002','Loans from Banks and Other Financial Institutions-From Other Financial Institutions',NULL,true,129,NULL,'A','L',NULL,4,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(848,'3305003','Loans from Banks and Other Financial Institutions-Institutional Agencies',NULL,true,129,NULL,'A','L',NULL,4,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(849,'3306001','Other Term Loans-HUDCO Loans',NULL,true,130,NULL,'A','L',NULL,4,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(850,'3306002','Other Term Loans-Others',NULL,true,130,NULL,'A','L',NULL,4,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(851,'3307001','Bonds and Debentures-City Development Bonds',NULL,true,131,NULL,'A','L',NULL,4,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(852,'3308000','Other Loans-Other Loans',NULL,true,132,NULL,'A','L',NULL,4,NULL,NULL,5,NULL,NULL,NULL,NULL,'330',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(853,'3311001','Loans from Central Government-Short Term Loan',NULL,true,133,NULL,'A','L',NULL,4,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(854,'3311002','Loans from Central Government-Long Term Loan',NULL,true,133,NULL,'A','L',NULL,4,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(855,'3312001','Loans from State Government-Short Term Loan',NULL,true,134,NULL,'A','L',NULL,4,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(856,'3312002','Loans from State Government-Long Term Loan',NULL,true,134,NULL,'A','L',NULL,4,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(857,'3313000','Loans from Government Bodies and Association-Loans from Government Bodies and Association',NULL,true,135,NULL,'A','L',NULL,4,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(858,'3314000','Loans from International Agencies-Loans from International Agencies',NULL,true,136,NULL,'A','L',NULL,4,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(859,'3315001','Loans from Banks and Other Financial Institutions-From Banks',NULL,true,137,NULL,'A','L',NULL,4,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(860,'3315002','Loans from Banks and Other Financial Institutions-From Other Financial Institutions',NULL,true,137,NULL,'A','L',NULL,4,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(861,'3315003','Loans from Banks and Other Financial Institutions-Institutional Agencies',NULL,true,137,NULL,'A','L',NULL,4,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(862,'3316001','Other Term Loans-HUDCO Loans',NULL,true,138,NULL,'A','L',NULL,4,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(863,'3317001','Bonds and Debentures-City Development Bonds',NULL,true,139,NULL,'A','L',NULL,4,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(864,'3318000','Other Loans-',NULL,true,140,NULL,'A','L',NULL,4,NULL,NULL,6,NULL,NULL,NULL,NULL,'331',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(865,'3401001','From Contractors Suppliers-Ernest Money Deposit',NULL,true,141,NULL,'A','L',NULL,4,NULL,NULL,7,NULL,NULL,NULL,NULL,'340',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(866,'3401002','From Contractors Suppliers-Retention Money Deposit',NULL,true,141,NULL,'A','L',NULL,4,NULL,NULL,7,NULL,NULL,NULL,NULL,'340',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(867,'3401003','From Contractors Suppliers-Further Security Deposit',NULL,true,141,NULL,'A','L',NULL,4,NULL,NULL,7,NULL,NULL,NULL,NULL,'340',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(868,'3402001','Deposits – Revenues-Rental Deposits',NULL,true,142,NULL,'A','L',NULL,4,NULL,NULL,7,NULL,NULL,NULL,NULL,'340',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(869,'3402002','Deposits – Revenues-Security Deposits',NULL,true,142,NULL,'A','L',NULL,4,NULL,NULL,7,NULL,NULL,NULL,NULL,'340',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(870,'3403000','From Staff-From Staff',NULL,true,143,NULL,'A','L',NULL,4,NULL,NULL,7,NULL,NULL,NULL,NULL,'340',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(871,'3408000','From Others-From Others',NULL,true,144,NULL,'A','L',NULL,4,NULL,NULL,7,NULL,NULL,NULL,NULL,'340',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(872,'3411001','Civil works-State Government Departments',NULL,true,145,NULL,'A','L',NULL,4,NULL,NULL,8,NULL,NULL,NULL,NULL,'341',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(873,'3411002','Civil works-Public Sector Undertakings',NULL,true,145,NULL,'A','L',NULL,4,NULL,NULL,8,NULL,NULL,NULL,NULL,'341',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(874,'3411003','Civil works-Private Organizations & Citizens',NULL,true,145,NULL,'A','L',NULL,4,NULL,NULL,8,NULL,NULL,NULL,NULL,'341',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(875,'3412001','Electrical works-State Government Departments',NULL,true,146,NULL,'A','L',NULL,4,NULL,NULL,8,NULL,NULL,NULL,NULL,'341',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(876,'3412002','Electrical works-Public Sector Undertakings',NULL,true,146,NULL,'A','L',NULL,4,NULL,NULL,8,NULL,NULL,NULL,NULL,'341',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(877,'3412003','Electrical works-Private Organizations & Citizens',NULL,true,146,NULL,'A','L',NULL,4,NULL,NULL,8,NULL,NULL,NULL,NULL,'341',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(878,'3418001','Others-State Government Departments',NULL,true,147,NULL,'A','L',NULL,4,NULL,NULL,8,NULL,NULL,NULL,NULL,'341',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(879,'3418002','Others-Public Sector Undertakings',NULL,true,147,NULL,'A','L',NULL,4,NULL,NULL,8,NULL,NULL,NULL,NULL,'341',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(880,'3418003','Others-Private Organizations & Citizens',NULL,true,147,NULL,'A','L',NULL,4,NULL,NULL,8,NULL,NULL,NULL,NULL,'341',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(882,'3501001','Creditors-Suppliers',NULL,true,148,27,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(883,'3501002','Creditors-Contractors',NULL,true,148,26,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(884,'3501003','Creditors-Expenses',NULL,true,148,28,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(885,'3501101','Employee liabilities-Salary Payable',NULL,true,149,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(886,'3501102','Employee liabilities-Pension Payable',NULL,true,149,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(887,'3501103','Employee liabilities-Leave Salary Payable',NULL,true,149,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(888,'3501104','Employee liabilities-Terminal Benefits Payable',NULL,true,149,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(889,'3501105','Employee liabilities-GPF Payable',NULL,true,149,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(890,'3501106','Employee liabilities-Unpaid Salaries',NULL,true,149,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(891,'3501107','Employee liabilities-Contributory Pension Payable',NULL,true,149,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(892,'3501201','Interest Accrued and due-Central Government Loans',NULL,true,150,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(893,'3501202','Interest Accrued and due-State Government Loans',NULL,true,150,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(894,'3501203','Interest Accrued and due-International Agencies',NULL,true,150,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(895,'3501204','Interest Accrued and due-Financial Institutions',NULL,true,150,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(896,'3501205','Interest Accrued and due-Bonds',NULL,true,150,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(897,'3501206','Interest Accrued and due-Others',NULL,true,150,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(898,'3502001','Recoveries payable-GPF',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(899,'3502002','Recoveries payable-GPF –Employees on Deputation',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(900,'3502003','Recoveries payable-GIS',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(901,'3502004','Recoveries payable-Profession Tax',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(902,'3502005','Recoveries payable-APGLI',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(903,'3502006','Recoveries payable-LIC',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(904,'3502007','Recoveries payable-Banks Loan',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(905,'3502008','Recoveries payable-TDS from Employees',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(906,'3502009','Recoveries payable-APWEF',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(907,'3502010','Recoveries payable-Court Recoveries',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(908,'3502011','Recoveries payable-House Rent (Other than Municipal Quarters)',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(909,'3502012','Recoveries payable-SCCS(SD)',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(910,'3502013','Recoveries payable-KCCS(SD)',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(911,'3502014','Recoveries payable-NGOCCS',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(912,'3502015','Recoveries payable-SMUCCS',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(913,'3502016','Recoveries payable-MDCCS',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(914,'3502017','Recoveries payable-LCCS',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(915,'3502018','Recoveries payable-DGCCS',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(916,'3502019','Recoveries payable-TWF',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(917,'3502020','Recoveries payable-MBF',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(918,'3502021','Recoveries payable-KNCCS',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(919,'3502022','Recoveries payable-SCCS',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(920,'3502023','Recoveries payable-KCCS',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(921,'3502024','Recoveries payable-Other Employee Deductions',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(922,'3502025','Recoveries payable-TDS from Contractors',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(923,'3502051','Recoveries payable-Turnover Tax',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(924,'3502052','Recoveries payable-APGST',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(925,'3502053','Recoveries payable-CST',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(926,'3502054','Recoveries payable-Service Tax',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(927,'3502055','Recoveries payable-NAC',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(928,'3502056','Recoveries payable-Seignorage Charges',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(929,'3502057','Recoveries payable-TDS Payable Interest',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(930,'3502058','Recoveries payable-Other Recoveries From Contractors',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(931,'3502061','Recoveries payable-Court Attachments',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(932,'3502062','Recoveries payable-PH Employees Coop Society',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(933,'3502063','Recoveries payable-DCC Bank',NULL,true,151,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(934,'3503001','Government Dues payable-Library Cess',NULL,true,152,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(935,'3503002','Government Dues payable-Education Cess',NULL,true,152,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(936,'3503003','Government Dues payable-Court Attachment',NULL,true,152,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(937,'3504001','Refunds payable-Taxes',NULL,true,153,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(938,'3504002','Refunds payable-Family Benefit Fund Settlements',NULL,true,153,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(939,'3504003','Refunds payable-Group Insurance Settlements',NULL,true,153,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(940,'3504004','Refunds payable-Unutilized Grants',NULL,true,153,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(941,'3504005','Refunds payable-Deposit Works',NULL,true,153,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(942,'3504006','Refunds payable-Development Charges to UDA',NULL,true,153,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(943,'3504007','Refunds payable-Others',NULL,true,153,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(944,'3504101','Advance Collection of Revenues-Property Tax',NULL,true,154,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(945,'3504102','Advance Collection of Revenues-Advertisement Tax',NULL,true,154,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(946,'3504103','Advance Collection of Revenues-Trade License',NULL,true,154,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(947,'3504104','Advance Collection of Revenues-Rent',NULL,true,154,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(948,'3508001','Others-Stale Cheque',NULL,true,155,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(949,'3508002','Others-Compensation Payable',NULL,true,155,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(950,'3508003','Others-Lease Charges payable',NULL,true,155,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(951,'3508004','Others-Advances under HP',NULL,true,155,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(952,'3508005','Others-Election deposit from candidates',NULL,true,155,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(953,'3509001','Sale Proceeds-Attached Properties',NULL,true,156,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(954,'3509002','Sale Proceeds-Assets',NULL,true,156,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(955,'3509003','Sale Proceeds-Stores',NULL,true,156,NULL,'A','L',NULL,4,NULL,NULL,9,NULL,NULL,NULL,NULL,'350',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(956,'3601001','Provisions for Expenses-Establishment Expenses',NULL,true,157,NULL,'A','L',NULL,4,NULL,NULL,10,NULL,NULL,NULL,NULL,'360',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(957,'3601002','Provisions for Expenses-Administrative Expenses',NULL,true,157,NULL,'A','L',NULL,4,NULL,NULL,10,NULL,NULL,NULL,NULL,'360',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(958,'3601003','Provisions for Expenses-Operations & Maintenance',NULL,true,157,NULL,'A','L',NULL,4,NULL,NULL,10,NULL,NULL,NULL,NULL,'360',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(959,'3602001','Provision for Interest-Interest Accrued and not due',NULL,true,158,NULL,'A','L',NULL,4,NULL,NULL,10,NULL,NULL,NULL,NULL,'360',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(960,'3603001','Provision for Other Assets-Inventory',NULL,true,159,NULL,'A','L',NULL,4,NULL,NULL,10,NULL,NULL,NULL,NULL,'360',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(961,'3603002','Provision for Other Assets-Investments',NULL,true,159,NULL,'A','L',NULL,4,NULL,NULL,10,NULL,NULL,NULL,NULL,'360',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(962,'3604001','Provisions for Doubtful receivables-Property Tax',NULL,true,160,NULL,'A','L',NULL,4,NULL,NULL,10,NULL,NULL,NULL,NULL,'360',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(963,'3604002','Provisions for Doubtful receivables-Advertisement Tax',NULL,true,160,NULL,'A','L',NULL,4,NULL,NULL,10,NULL,NULL,NULL,NULL,'360',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(964,'3604003','Provisions for Doubtful receivables-Trade License',NULL,true,160,NULL,'A','L',NULL,4,NULL,NULL,10,NULL,NULL,NULL,NULL,'360',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(965,'3604004','Provisions for Doubtful receivables-Rents',NULL,true,160,NULL,'A','L',NULL,4,NULL,NULL,10,NULL,NULL,NULL,NULL,'360',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(966,'4101001','Land-Open Space',NULL,true,161,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(967,'4101002','Land-Grounds',NULL,true,161,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(968,'4101003','Land-Parks',NULL,true,161,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(969,'4101004','Land-Gardens',NULL,true,161,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(970,'4102001','Buildings-Office Buildings',NULL,true,162,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(971,'4102002','Buildings-Commercial Complex',NULL,true,162,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(972,'4102003','Buildings-Hospitals, Dispensaries & Health Posts',NULL,true,162,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(973,'4102004','Buildings-Community Halls & Reading Rooms',NULL,true,162,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(974,'4102005','Buildings-Gust Houses',NULL,true,162,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(975,'4102006','Buildings-Staff Quarters',NULL,true,162,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(976,'4102007','Buildings-Public Latrines & Urinals',NULL,true,162,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(977,'4103001','Roads and Bridges-Concrete Road',NULL,true,163,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(978,'4103002','Roads and Bridges-Black Topped Roads',NULL,true,163,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(979,'4103003','Roads and Bridges-Link Roads, Parallel Roads & Slip Roads',NULL,true,163,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(980,'4103004','Roads and Bridges-Footpaths & Table Drains',NULL,true,163,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(981,'4103005','Roads and Bridges-Bridges & Culverts',NULL,true,163,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(982,'4103006','Roads and Bridges-Fly-overs & Over Bridges',NULL,true,163,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(983,'4103007','Roads and Bridges-Subways & Causeways',NULL,true,163,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(984,'4103101','Sewerage and Drainage-Underground Drains',NULL,true,164,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(985,'4103102','Sewerage and Drainage-Open Drains',NULL,true,164,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(986,'4103201','Water works-Water works',NULL,true,165,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(987,'4103202','Water works-Open/bore Wells',NULL,true,165,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(988,'4103203','Water works-Reservoirs',NULL,true,165,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(989,'4103301','Public Lighting-Modern Lighting On Main Roads',NULL,true,166,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(990,'4103302','Public Lighting-Modern Lighting On Lanes, By- lanes',NULL,true,166,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(991,'4104000','Plant and Machinery-Plant & Machinery',NULL,true,167,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(992,'4105001','Vehicles-Ambulance',NULL,true,168,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(993,'4105002','Vehicles-Buses',NULL,true,168,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(994,'4105003','Vehicles-Cars & Jeeps',NULL,true,168,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(995,'4105004','Vehicles-Cranes',NULL,true,168,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(996,'4105005','Vehicles-Trucks',NULL,true,168,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(997,'4105006','Vehicles-Tankers',NULL,true,168,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(998,'4106001','Office and Other Equipments-Air Conditioners',NULL,true,169,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(999,'4106002','Office and Other Equipments-Computers',NULL,true,169,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1000,'4106003','Office and Other Equipments-Faxes',NULL,true,169,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1001,'4106004','Office and Other Equipments-Photocopiers',NULL,true,169,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1002,'4106005','Office and Other Equipments-Refrigerators',NULL,true,169,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1003,'4106006','Office and Other Equipments-Network Equipment',NULL,true,169,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1004,'4107001','Furniture, Fixtures, Fittings and Electrical Appliances-Cabinets & Partitions',NULL,true,170,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1005,'4107002','Furniture, Fixtures, Fittings and Electrical Appliances-Cupboards',NULL,true,170,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1006,'4107003','Furniture, Fixtures, Fittings and Electrical Appliances-Fans',NULL,true,170,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1007,'4107004','Furniture, Fixtures, Fittings and Electrical Appliances-Electrical Fittings',NULL,true,170,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1008,'4107005','Furniture, Fixtures, Fittings and Electrical Appliances-Tables & Chairs',NULL,true,170,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1009,'4108000','Other Fixed Assets-Other Fixed Assets',NULL,true,171,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1010,'4109001','Assets under Disposal-Valuable Assets',NULL,true,172,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1011,'4109002','Assets under Disposal-Scraped Assets',NULL,true,172,NULL,'A','A',NULL,4,NULL,NULL,11,NULL,NULL,NULL,NULL,'410',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1012,'4112001','Buildings-Office Buildings',NULL,true,173,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1013,'4112002','Buildings-Commercial Complex',NULL,true,173,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1014,'4112003','Buildings-Hospitals, Dispensaries & Health Posts',NULL,true,173,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1015,'4112004','Buildings-Community Halls & Reading Rooms',NULL,true,173,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1016,'4112005','Buildings-Gust Houses',NULL,true,173,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1017,'4112006','Buildings-Staff Quarters',NULL,true,173,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1018,'4112007','Buildings-Public Latrines & Urinals',NULL,true,173,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1019,'4113001','Roads and Bridges-Concrete Road',NULL,true,174,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1020,'4113002','Roads and Bridges-Black Topped Roads',NULL,true,174,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1021,'4113003','Roads and Bridges-Link Roads, Parallel Roads & Slip Roads',NULL,true,174,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1022,'4113004','Roads and Bridges-Footpaths & Table Drains',NULL,true,174,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1023,'4113005','Roads and Bridges-Bridges & Culverts',NULL,true,174,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1024,'4113006','Roads and Bridges-Fly-overs & Over Bridges',NULL,true,174,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1025,'4113007','Roads and Bridges-Subways & Causeways',NULL,true,174,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1026,'4113101','Sewerage and Drainage-Underground Drains',NULL,true,175,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1027,'4113102','Sewerage and Drainage-Open Drains',NULL,true,175,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1028,'4113201','Waterways-Bore wells',NULL,true,176,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1029,'4113202','Waterways-Open Wells',NULL,true,176,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1030,'4113203','Waterways-Reservoirs',NULL,true,176,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1031,'4113301','Public Lighting-Modern Lighting On Main Roads',NULL,true,177,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1032,'4113302','Public Lighting-Modern Lighting On Lanes, By- lanes',NULL,true,177,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1033,'4114000','Plant and Machinery-Plant & Machinery',NULL,true,178,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1034,'4115001','Vehicles-Ambulance',NULL,true,179,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1035,'4115002','Vehicles-Buses',NULL,true,179,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1036,'4115003','Vehicles-Cars & Jeeps',NULL,true,179,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1037,'4115004','Vehicles-Cranes',NULL,true,179,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1038,'4115005','Vehicles-Trucks',NULL,true,179,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1039,'4115006','Vehicles-Tankers',NULL,true,179,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1040,'4116001','Office and Other Equipments-Air Conditioners',NULL,true,180,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1041,'4116002','Office and Other Equipments-Computers',NULL,true,180,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1042,'4116003','Office and Other Equipments-Faxes',NULL,true,180,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1043,'4116004','Office and Other Equipments-Photocopiers',NULL,true,180,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1044,'4116005','Office and Other Equipments-Refrigerators',NULL,true,180,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1045,'4116006','Office and Other Equipments-Network Equipment',NULL,true,180,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1046,'4117001','Furniture, Fixtures, Fittings and Electrical Appliances-Cabinets & Partitions',NULL,true,181,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1047,'4117002','Furniture, Fixtures, Fittings and Electrical Appliances-Cupboards',NULL,true,181,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1048,'4117003','Furniture, Fixtures, Fittings and Electrical Appliances-Fans',NULL,true,181,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1049,'4117004','Furniture, Fixtures, Fittings and Electrical Appliances-Electrical Fittings',NULL,true,181,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1050,'4117005','Furniture, Fixtures, Fittings and Electrical Appliances-Tables & Chairs',NULL,true,181,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1051,'4118000','Other Fixed Assets-Other Fixed Assets',NULL,true,182,NULL,'A','A',NULL,4,NULL,NULL,12,NULL,NULL,NULL,NULL,'411',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1052,'4120011','General-Land Acquisition',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1053,'4120012','General-Garbage Dumping Yards',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1054,'4120013','General-Parking Lots',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1055,'4120014','General-Major Parks',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1056,'4120015','General-Colony Parks',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1057,'4120016','General-Traffic Islands/Central Media',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1058,'4120017','General-Raising of Nurseries',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1059,'4120018','General-Playgrounds/Stadia',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1060,'4120019','General-Purchase of Plants/ Translocation of Plants',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1061,'4120021','General-Office Buildings',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1062,'4120022','General-Markets, Shopping Complexes & Other Remunerative Enterprises',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1063,'4120023','General-Community Halls & Reading Rooms',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1064,'4120024','General-School Buildings',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1065,'4120025','General-Hospitals, Dispensaries, Health Posts',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1066,'4120026','General-Public Latrines & Urinals',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1067,'4120027','General-Burial Grounds/Crematoria',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1068,'4120031','General-Bridges & Culverts',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1069,'4120032','General-Fly-Overs',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1070,'4120033','General-Rail Over/Under-Bridges',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1071,'4120034','General-Subways & Causeways, Foot over Bridges',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1072,'4120041','General-Road Development/Upgradation',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1073,'4120042','General-Link, Parallel and Slip Road',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1074,'4120043','General-Road Widening',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1075,'4120044','General-Footpaths and Table Drains',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1076,'4120045','General-Junction Improvements',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1077,'4120046','General-Station Area Development',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1078,'4120047','General-Traffic Signals & Signage',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1079,'4120048','General-Local Rail Transit Infrastructure',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1080,'4120051','General-Major Strom Water Drains',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1081,'4120052','General-Minor Strom Water Drains',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1082,'4120053','General-Rainwater Harvesting',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1083,'4120054','General-Sewerage Lines',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1084,'4120055','General-Sewerage Treatment Plants',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1085,'4120056','General-Conservation of Rivers/Lakes',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1086,'4120057','General-Construction of Dhobighats',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1087,'4120061','General-Modern Lighting on Major Roads',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1088,'4120062','General-Modern Lighting in Lanes and Bye- lanes',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1089,'4120071','General-Heavy Vehicles',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1090,'4120072','General-Light Vehicles',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1091,'4120073','General-Other Vehicles',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1092,'4120081','General-Office Equipment',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1093,'4120082','General-Computers, Servers & Net Work Equipment',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1094,'4120083','General-Urban Mapping – GIS',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1095,'4120084','General-Hospital Equipment',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1096,'4120085','General-Play and Sports Equipment',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1097,'4120086','General-Water Fountains',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1098,'4120087','General-Dumber Bins/Garbage Dust and Litter Bins',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1099,'4120088','General-Machinery and Equipment',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1100,'4120091','General-Furniture',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1101,'4120092','General-Fixtures and Fittings',NULL,true,183,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1102,'4121000','Specific Grants-Specific Grants',NULL,true,184,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1103,'4122000','Special funds-Special funds',NULL,true,185,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1104,'4123000','Specific Schemes-Specific Schemes',NULL,true,186,NULL,'A','A',NULL,4,NULL,NULL,13,NULL,NULL,NULL,NULL,'412',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1105,'4201000','Central Government Securities-Central Government Securities',NULL,true,187,NULL,'A','A',NULL,4,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1106,'4202000','State Government Securities-State Government Securities',NULL,true,188,NULL,'A','A',NULL,4,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1107,'4203000','Debentures and Bonds-Debentures and Bonds',NULL,true,189,NULL,'A','A',NULL,4,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1108,'4204000','Preference Shares-Preference Shares',NULL,true,190,NULL,'A','A',NULL,4,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1109,'4205000','Equity Shares-Equity Shares',NULL,true,191,NULL,'A','A',NULL,4,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1110,'4206001','Units of Mutual Funds-Open Ended Debt Fund',NULL,true,192,NULL,'A','A',NULL,4,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1111,'4206002','Units of Mutual Funds-Open Ended Equity Fund',NULL,true,192,NULL,'A','A',NULL,4,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1112,'4208001','Other Investments-Fixed Deposits',NULL,true,193,NULL,'A','A',NULL,4,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1113,'4209001','Accumulated Provision-Decline in Investment Value',NULL,true,194,NULL,'A','A',NULL,4,NULL,NULL,14,NULL,NULL,NULL,NULL,'420',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1114,'4211000','Central Government Securities-Central Government Securities',NULL,true,195,NULL,'A','A',NULL,4,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1115,'4212000','State Government Securities-State Government Securities',NULL,true,196,NULL,'A','A',NULL,4,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1116,'4213000','Debentures and Bonds-Debentures and Bonds',NULL,true,197,NULL,'A','A',NULL,4,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1117,'4214000','Preference Shares-Preference Shares',NULL,true,198,NULL,'A','A',NULL,4,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1118,'4215000','Equity Shares-Equity Shares',NULL,true,199,NULL,'A','A',NULL,4,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1119,'4216001','Units of Mutual Funds-Open Ended Debt Fund',NULL,true,200,NULL,'A','A',NULL,4,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1120,'4216002','Units of Mutual Funds-Open Ended Equity Fund',NULL,true,200,NULL,'A','A',NULL,4,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1121,'4218001','Other Investments-Fixed Deposits with Banks',NULL,true,201,NULL,'A','A',NULL,4,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1122,'4219001','Accumulated Provision-Decline in Investment Value',NULL,true,202,NULL,'A','A',NULL,4,NULL,NULL,15,NULL,NULL,NULL,NULL,'421',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1123,'4301001','Stores-Engineering Stores',NULL,true,204,NULL,'A','A',NULL,4,NULL,NULL,16,NULL,NULL,NULL,NULL,'430',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1124,'4301002','Stores-Transport Stores',NULL,true,204,NULL,'A','A',NULL,4,NULL,NULL,16,NULL,NULL,NULL,NULL,'430',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1125,'4301003','Stores-Medical Stores',NULL,true,204,NULL,'A','A',NULL,4,NULL,NULL,16,NULL,NULL,NULL,NULL,'430',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1126,'4301004','Stores-Central Stationary Stores',NULL,true,204,NULL,'A','A',NULL,4,NULL,NULL,16,NULL,NULL,NULL,NULL,'430',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1127,'4302000','Loose Tools-Loose Tools',NULL,true,205,NULL,'A','A',NULL,4,NULL,NULL,16,NULL,NULL,NULL,NULL,'430',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1128,'4308000','Others-Others',NULL,true,206,NULL,'A','A',NULL,4,NULL,NULL,16,NULL,NULL,NULL,NULL,'430',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1129,'4311001','Receivables for Property Taxes-Private Properties',NULL,true,207,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1130,'4311002','Receivables for Property Taxes-Government Properties',NULL,true,207,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1131,'4311003','Receivables for Property Taxes-Vacant Lands',NULL,true,207,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1132,'4311901','Receivable for Other Taxes-Private Properties',NULL,true,208,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1133,'4311902','Receivable for Other Taxes-Government Properties',NULL,true,208,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1134,'4312000','Receivables for Cess-Receivables for Cess',NULL,true,209,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1135,'4313001','Receivable for Fees and User Charges-Water Supply',NULL,true,210,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1136,'4313002','Receivable for Fees and User Charges-Trade Licence',NULL,true,210,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1137,'4314001','Receivable from other sources-Rent',NULL,true,211,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1138,'4314002','Receivable from other sources-Interest Accrued & Due',NULL,true,211,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1139,'4314003','Receivable from other sources-Interest Accrued but not due',NULL,true,211,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1140,'4314004','Receivable from other sources-Interest Receivable On Employee Loans',NULL,true,211,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1141,'4314005','Receivable from other sources-Other Rent',NULL,true,211,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1142,'4315001','Receivable from Government-State Government',NULL,true,212,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1143,'4315002','Receivable from Government-Central Government',NULL,true,212,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1144,'4315003','Receivable from Government-Government Departments',NULL,true,212,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1145,'4315004','Receivable from Government-Public Sector Undertakings',NULL,true,212,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1146,'4318001','Receivables control accounts-Property Taxes',NULL,true,213,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1147,'4319100','State Govt Cess/ levies in Property Taxes - Control account-State Govt Cess/ levies in Property Taxes - Control account',NULL,true,214,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1148,'4319200','State Govt Cess/ levies in Water Taxes - Control account-State Govt Cess/ levies in Water Taxes - Control account',NULL,true,215,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1149,'4319900','State Govt Cess/ levies in Other Taxes - Control account-State Govt Cess/ levies in Other Taxes - Control account',NULL,true,216,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1150,'4321000','Provision for outstanding Property Taxes-Provision for outstanding Property Taxes',NULL,true,217,NULL,'A','A',NULL,4,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1151,'4321100','Provision for outstanding Water Taxes-Provision for outstanding Water Taxes',NULL,true,218,NULL,'A','A',NULL,4,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1152,'4321200','Provision for outstanding Other Taxes-Provision for outstanding Other Taxes',NULL,true,219,NULL,'A','A',NULL,4,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1153,'4322000','Provision for outstanding Cess-Provision for outstanding Cess',NULL,true,220,NULL,'A','A',NULL,4,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1154,'4323000','Provision for outstanding Fees and User Charges-Provision for outstanding Fees & User Charges',NULL,true,221,NULL,'A','A',NULL,4,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1155,'4324000','Provision for outstanding other receivable-Provision for outstanding other receivable',NULL,true,222,NULL,'A','A',NULL,4,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1156,'4329100','State Govt Cess/ levies in Property Taxes - Provision account-State Govt Cess/ levies in Property Taxes - Provision account',NULL,true,225,NULL,'A','A',NULL,4,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1157,'4329200','State Govt Cess/ levies in Water Taxes - Provision account-State Govt Cess/ levies in Water Taxes - Provision account',NULL,true,226,NULL,'A','A',NULL,4,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1158,'4329900','State Govt Cess/ levies in Other Taxes - Provision account-State Govt Cess/ levies in Other Taxes - Provision account',NULL,true,227,NULL,'A','A',NULL,4,NULL,NULL,18,NULL,NULL,NULL,NULL,'432',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1159,'4401000','Establishment-Establishment',NULL,true,228,NULL,'A','A',NULL,4,NULL,NULL,19,NULL,NULL,NULL,NULL,'440',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1160,'4402001','Administration-Rent, Rates Taxes',NULL,true,229,NULL,'A','A',NULL,4,NULL,NULL,19,NULL,NULL,NULL,NULL,'440',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1161,'4402002','Administration-Insurance',NULL,true,229,NULL,'A','A',NULL,4,NULL,NULL,19,NULL,NULL,NULL,NULL,'440',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1162,'4403001','Operations and Maintenance-Annual Maintenance Contracts',NULL,true,230,NULL,'A','A',NULL,4,NULL,NULL,19,NULL,NULL,NULL,NULL,'440',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1163,'4501001','Cash-Cash On Hand',NULL,true,231,4,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1164,'4501002','Cash-Cash In Transit',NULL,true,231,121,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1165,'4501051','Cash-Cheques-in-hand',NULL,true,231,5,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1166,'4501091','Cash-Inter Bank Account Fund Transfer',NULL,true,231,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1167,'4502101','Nationalised Banks-State Bank of India',NULL,true,233,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1168,'4502102','Nationalised Banks-State Bank of Hyderabad',NULL,true,233,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1169,'4502103','Nationalised Banks-Andhra Bank',NULL,true,233,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1170,'4502104','Nationalised Banks-Canara Bank',NULL,true,233,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1171,'4502201','Other Scheduled Banks-ICICI Bank',NULL,true,234,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1172,'4502202','Other Scheduled Banks-IDBI Bank',NULL,true,234,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1173,'4502203','Other Scheduled Banks-HDFC Bank',NULL,true,234,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1174,'4502300','Scheduled Co-operative Banks-Scheduled Co-operative Banks',NULL,true,235,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1175,'4502400','Post Office-Post Office',NULL,true,236,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1176,'4504101','Nationalised Banks-State Bank Of India',NULL,true,237,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1177,'4504102','Nationalised Banks-State Bank Of Hyderabad',NULL,true,237,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1178,'4504103','Nationalised Banks-Andhra Bank',NULL,true,237,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1179,'4504104','Nationalised Banks-Canara Bank',NULL,true,237,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1180,'4504201','Other Scheduled Banks-ICICI Bank',NULL,true,238,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1181,'4504202','Other Scheduled Banks-IDBI Bank',NULL,true,238,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1182,'4504203','Other Scheduled Banks-HDFC Bank',NULL,true,238,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1183,'4504300','Scheduled Co-operative Banks-Scheduled Co-operative Banks',NULL,true,239,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1184,'4504400','Post Office-Post Office',NULL,true,240,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1185,'4506101','Nationalised Banks-State Bank Of India',NULL,true,241,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1186,'4506102','Nationalised Banks-State Bank Of Hyderabad',NULL,true,241,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1187,'4506200','Other Scheduled Banks-Other Scheduled Banks',NULL,true,242,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1188,'4506300','Scheduled Co-operative Banks-Scheduled Co-operative Banks',NULL,true,243,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1189,'4506400','Post Office-Post Office',NULL,true,244,NULL,'A','A',NULL,4,NULL,NULL,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1190,'4601001','Loans and advances to employees-House Building Advance',NULL,true,245,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1191,'4601002','Loans and advances to employees-Conveyance Advance',NULL,true,245,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1192,'4601003','Loans and advances to employees-Computer Advance',NULL,true,245,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1193,'4601004','Loans and advances to employees-Festival Advance',NULL,true,245,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1194,'4601005','Loans and advances to employees-Education Advance',NULL,true,245,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1195,'4601006','Loans and advances to employees-Miscellaneous Advance',NULL,true,245,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1196,'4602000','Employee Provident Fund Loans-Employee Provident Fund Loans',NULL,true,246,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1197,'4603000','Loans to Others-Loans to Others',NULL,true,247,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1198,'4604001','Advance to Suppliers and contractors-Suppliers',NULL,true,248,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1199,'4604002','Advance to Suppliers and contractors-Contractors',NULL,true,248,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1200,'4604003','Advance to Suppliers and contractors-Expenses',NULL,true,248,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1201,'4604004','Advance to Suppliers and contractors-Materials Issued to Contractors',NULL,true,248,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1202,'4605001','Advance to others-Employees for works',NULL,true,249,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1203,'4605002','Advance to others-Travel Advance',NULL,true,249,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1204,'4605003','Advance to others-Imprest',NULL,true,249,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1205,'4605004','Advance to others-Executing agency for projects',NULL,true,249,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1206,'4606001','Deposits with external Agencies-Telephone',NULL,true,250,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1207,'4606002','Deposits with external Agencies-Electricity',NULL,true,250,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1208,'4606003','Deposits with external Agencies-Petrol Pumps',NULL,true,250,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1209,'4608001','Other Current Assets-HP Installments receivable',NULL,true,251,NULL,'A','A',NULL,4,NULL,NULL,21,NULL,NULL,NULL,NULL,'460',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1210,'4611000','Loans to Others-Loans to Others',NULL,true,252,NULL,'A','A',NULL,4,NULL,NULL,22,NULL,NULL,NULL,NULL,'461',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1211,'4612000','Advances-Advances',NULL,true,253,NULL,'A','A',NULL,4,NULL,NULL,22,NULL,NULL,NULL,NULL,'461',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1212,'4613000','Deposits-Deposits',NULL,true,254,NULL,'A','A',NULL,4,NULL,NULL,22,NULL,NULL,NULL,NULL,'461',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1213,'4701001','Deposit Works – Expenditure-Civil Works',NULL,true,255,NULL,'A','A',NULL,4,NULL,NULL,23,NULL,NULL,NULL,NULL,'470',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1214,'4701002','Deposit Works – Expenditure-Electric Works',NULL,true,255,NULL,'A','A',NULL,4,NULL,NULL,23,NULL,NULL,NULL,NULL,'470',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1215,'4701003','Deposit Works – Expenditure-Other Works',NULL,true,255,NULL,'A','A',NULL,4,NULL,NULL,23,NULL,NULL,NULL,NULL,'470',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1216,'4702000','Inter Unit Accounts-Inter Unit Accounts',NULL,true,256,NULL,'A','A',NULL,4,NULL,NULL,23,NULL,NULL,NULL,NULL,'470',NULL,NULL,NULL,NULL,NULL,NULL,0)
;
INSERT INTO chandigarh.chartofaccounts (id,glcode,"name",description,isactiveforposting,parentid,purposeid,operation,"type","class",classification,functionreqd,budgetcheckreq,scheduleid,receiptscheduleid,receiptoperation,paymentscheduleid,paymentoperation,majorcode,fiescheduleid,fieoperation,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1217,'4702051','Inter Unit Accounts-Inter Fund Transfer',NULL,true,256,NULL,'A','A',NULL,4,NULL,NULL,23,NULL,NULL,NULL,NULL,'470',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1218,'4703000','Interest Control Payable-Interest Control Payable',NULL,true,257,NULL,'A','A',NULL,4,NULL,NULL,23,NULL,NULL,NULL,NULL,'470',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1219,'4801001','Loan Issue Expenses-Deferred Revenue Expenses',NULL,true,258,NULL,'A','A',NULL,4,NULL,NULL,24,NULL,NULL,NULL,NULL,'480',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1220,'4802000','Discount on Issue of loans-Discount on Issue of loans',NULL,true,259,NULL,'A','A',NULL,4,NULL,NULL,24,NULL,NULL,NULL,NULL,'480',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1221,'4803000','Others-Others',NULL,true,260,NULL,'A','A',NULL,4,NULL,NULL,24,NULL,NULL,NULL,NULL,'480',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1222,'4311004','Receivables for Property Taxes- Arrears',NULL,true,207,NULL,'A','A',NULL,4,NULL,NULL,17,NULL,NULL,NULL,NULL,'431',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1223,'4504204','Allahabad UP Gramin Bank-Main branch-000000000001','Allahabad UP Gramin Bank-Main branch-000000000001',true,238,NULL,'A','A',NULL,4,true,false,20,NULL,NULL,NULL,NULL,'450',NULL,NULL,NULL,NULL,NULL,NULL,0)
,(1228,'3109000','Excess of Income over Expenditure',NULL,true,107,7,NULL,'L',NULL,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'310',NULL,NULL,'2020-01-29 00:00:00.000',1,NULL,1,0)
;

INSERT INTO chandigarh.EGCL_SERVICE_INSTRUMENTACCOUNTS (id,instrumenttype,servicedetails,chartofaccounts,createdby,createddate,lastmodifiedby,lastmodifieddate) VALUES 
(1,(select id from chandigarh.egf_instrumenttype where "type"='online'),(select id from chandigarh.egcl_servicedetails where "name"='Atom Payment Gateway'),(SELECT id FROM chandigarh.chartofaccounts where "name"='Receivables control accounts-Property Taxes'),1,now(),1,now());

update chandigarh.eg_demand_reason set glcodeid=(select id from chandigarh.chartofaccounts where "name"='Fees for Grant of Permit-Building Permit Fee');

INSERT INTO chandigarh.fund (id,code,"name",llevel,parentid,isactive,isnotleaf,identifier,purpose_id,transactioncreditamount,createddate,lastmodifieddate,lastmodifiedby,createdby,"version") VALUES 
(1,'01','Municipal Fund',0,NULL,true,false,'1',NULL,NULL,now(),NULL,NULL,NULL,0)
,(2,'02','Capital Fund',0,NULL,false,false,'2',NULL,NULL,now(),NULL,NULL,NULL,0)
,(3,'03','Elementary Education Fund',0,NULL,false,false,'3',NULL,NULL,now(),NULL,NULL,NULL,0)
,(4,'04','Earmarked Funds',0,NULL,false,false,'4',NULL,NULL,now(),NULL,NULL,NULL,0)
;

INSERT INTO chandigarh.fundsource (id,code,"name","type",parentid,isactive,financialinstid,funding_type,loan_percentage,source_amount,rate_of_interest,loan_period,moratorium_period,repayment_frequency,no_of_installment,bankaccountid,govt_order,govt_date,dp_code_number,dp_code_resg,fin_inst_letter_num,fin_inst_letter_date,fin_inst_schm_num,fin_inst_schm_date,subschemeid,llevel,isnotleaf,createddate,createdby,lastmodifieddate,lastmodifiedby,"version") VALUES 
(1,'01','Own Sources',NULL,NULL,true,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,false,'2020-01-29',1,'2020-01-29',1,0)
,(10,'02','Loans',NULL,NULL,true,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,false,'2020-01-29',1,'2020-01-29',1,0)
,(11,'03','Grants',NULL,NULL,true,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,false,'2020-01-29',1,'2020-01-29',1,0)
,(14,'04','MLACDS',NULL,NULL,true,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,false,'2020-01-29',1,'2020-01-29',1,0)
,(15,'05','MPLADS',NULL,NULL,true,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,false,'2020-01-29',1,'2020-01-29',1,0)
,(17,'06','JNNURM',NULL,NULL,true,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,false,'2020-01-29',1,'2020-01-29',1,0)
,(21,'07','Deposit Works',NULL,NULL,true,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,false,'2020-01-29',1,'2020-01-29',1,0);

INSERT INTO chandigarh.eg_department (id,"name",createddate,code,createdby,lastmodifiedby,lastmodifieddate,"version") VALUES 
(nextval('seq_eg_department'),'Buildings',now(),'B',1,1,now(),0)
,(nextval('seq_eg_department'),'General',now(),'G',1,1,now(),0)
,(nextval('seq_eg_department'),'Health',now(),'H',1,1,now(),0)
,(nextval('seq_eg_department'),'Electrical',now(),'L',1,1,now(),0)
,(nextval('seq_eg_department'),'Town Planning',now(),'TP',1,1,now(),0)
,(nextval('seq_eg_department'),'Works',now(),'W',1,1,now(),0)
,(nextval('seq_eg_department'),'Engineering',now(),'ENG',1,1,now(),0)
,(nextval('seq_eg_department'),'Administration',now(),'ADM',1,1,now(),0)
,(nextval('seq_eg_department'),'Revenue',now(),'REV',1,1,now(),0);

INSERT INTO chandigarh.bank (id,code,"name",narration,isactive,"type",createddate,lastmodifieddate,lastmodifiedby,"version",createdby) VALUES 
(1,'1','Allahabad UP Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(2,'2','Andhra Pradesh Grameena Vikas Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(3,'3','Andhra Pragathi Grameena Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(4,'4','Arunachal Pradesh Rural Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(5,'5','Aryavart Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(6,'6','Assam Gramin Vikash Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(7,'7','Baitarani Gramya Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(8,'8','Ballia –Etawah Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(9,'9','Bangiya Gramin Vikash Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(10,'10','Baroda Gujarat Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
;
INSERT INTO chandigarh.bank (id,code,"name",narration,isactive,"type",createddate,lastmodifieddate,lastmodifiedby,"version",createdby) VALUES 
(11,'11','Baroda Rajasthan Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(12,'12','Baroda Uttar Pradesh Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(13,'13','Bihar Kshetriya Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(14,'14','Cauvery Kalpatharu Grameena Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(15,'15','Chaitanya Godavari Grameena Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(16,'16','Chhattisgarh Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(17,'17','Chikmagalur-Kodagu Grameena Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(18,'18','Deccan Grameena Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(19,'19','Dena Gujarat Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(20,'20','Durg-Rajnandgaon Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
;
INSERT INTO chandigarh.bank (id,code,"name",narration,isactive,"type",createddate,lastmodifieddate,lastmodifiedby,"version",createdby) VALUES 
(21,'21','Ellaquai Dehati Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(22,'22','Gurgaon Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(23,'23','Hadoti Kshetriya Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(24,'24','Haryana Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(25,'25','Himachal Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(26,'26','Jaipur Thar Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(27,'27','Jhabua Dhar Kshetriya Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(28,'28','Jharkhand Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(29,'29','Kalinga Gramya Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(30,'30','Karnataka Vikas Grameena Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
;
INSERT INTO chandigarh.bank (id,code,"name",narration,isactive,"type",createddate,lastmodifieddate,lastmodifiedby,"version",createdby) VALUES 
(31,'31','Kashi Gomti Samyut Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(32,'32','Kerala Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(33,'33','Krishna Grameena Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(34,'34','Kshetriya Kisan Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(35,'35','Langpi Dehangi Rural Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(36,'36','Madhumalti Building Gupte Marg',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(37,'37','Madhya Bharat Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(38,'38','Madhya Bihar Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(39,'39','Mahakaushal Kshetriya Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(40,'40','Maharashtra Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
;
INSERT INTO chandigarh.bank (id,code,"name",narration,isactive,"type",createddate,lastmodifieddate,lastmodifiedby,"version",createdby) VALUES 
(41,'41','Malwa Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(42,'42','Manipur Rural Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(43,'43','Marwar Ganganagar Bikaner Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(44,'44','Meghalaya Rural Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(45,'45','Mewar Anchalik Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(46,'46','Mizoram Rural Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(47,'47','Nagaland Rural Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(48,'48','Uttrakhand Gramin Bank[1]',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(49,'49','Narmada Malwa Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(50,'50','Neelachal Gramya Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
;
INSERT INTO chandigarh.bank (id,code,"name",narration,isactive,"type",createddate,lastmodifieddate,lastmodifiedby,"version",createdby) VALUES 
(51,'51','Pallavan Grama Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(52,'52','Pandyan Grama Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(53,'53','Parvatiya Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(54,'54','Paschim Banga Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(55,'55','Pragathi Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(56,'56','Prathama Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(57,'57','Puduvai Bharathiar Grama Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(58,'58','Pune District Central Cooperative Bank Ltd.',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(59,'59','Punjab Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(60,'60','Purvanchal Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
;
INSERT INTO chandigarh.bank (id,code,"name",narration,isactive,"type",createddate,lastmodifieddate,lastmodifiedby,"version",createdby) VALUES 
(61,'61','Rajasthan Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(62,'62','Rewa-Sidhi Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(63,'63','Rushikulya Gramya Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(64,'64','Samastipur Kshetriya Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(65,'65','Saptagiri Grameena Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(66,'66','Sarva UP Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(67,'67','Satpura Narmada Kshetriya',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(68,'68','Saurashtra Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(69,'69','Sharda Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(70,'70','Shreyas Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
;
INSERT INTO chandigarh.bank (id,code,"name",narration,isactive,"type",createddate,lastmodifieddate,lastmodifiedby,"version",createdby) VALUES 
(71,'71','Surguja Kshetriya Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(72,'72','Sutlej Kshetriya Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(73,'73','Tripura Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(74,'74','Utkal Gramya Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(75,'75','Uttar Banga Kshetriya Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(76,'76','Uttar Bihar Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(77,'77','Vananchal Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(78,'78','Vidharbha Kshetriya Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(79,'79','Visveshvaraya Grameena Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
,(80,'80','Wainganga Krishna Gramin Bank',NULL,true,NULL,NULL,NULL,NULL,0,NULL)
;

INSERT INTO chandigarh.bankbranch (id,branchcode,branchname,branchaddress1,branchaddress2,branchcity,branchstate,branchpin,branchphone,branchfax,bankid,contactperson,isactive,narration,micr,createddate,lastmodifieddate,lastmodifiedby,"version",createdby) VALUES 
(1,'001','Main branch','Srikakulam',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,true,NULL,NULL,NULL,NULL,NULL,0,NULL)
;

INSERT INTO chandigarh.bankaccount (id,branchid,accountnumber,accounttype,narration,isactive,glcodeid,fundid,payto,"type",createdby,lastmodifiedby,createddate,lastmodifieddate,"version",chequeformatid) VALUES 
(1,1,'000000000001','OTHER SCHEDULED BANKS',NULL,true,1223,1,NULL,'RECEIPTS_PAYMENTS',1,1,'2020-01-29 00:00:00.000','2020-01-29 00:00:00.000',0,NULL)
;

--------------------------------------------------------------------------------------------------

update chandigarh.egp_portalservice set url='javascript:void(0);' where code in ('Apply For Occupancy Certificate','New Occupancy Certificate Plan Scrutiny','Resubmit Occupancy Certificate Plan Scrutiny');
update chandigarh.egp_portalservice set isactive=false where code in ('BPACHANGEOFOCCU','INITIATEINSPECTION','PERMITRENEWAL','OWNERSHIPTRANSFER');

--------------------------------------------------------------------------------------------------

Insert into chandigarh.EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Get occupancy sub usages by sub occupancy','/sub-occupancy/sub-usages',null,(select id from chandigarh.eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from chandigarh.EG_ACTION),'Get occupancy sub usages by sub occupancy','false','bpa',0,1,now(),1,now(),
(select id from chandigarh.eg_module where name='BPA'));

Insert into chandigarh.eg_roleaction (roleid,actionid) values ((select id from state.eg_role where name='BUSINESS'),
(select id from chandigarh.eg_action where name='Get occupancy sub usages by sub occupancy'));

Insert into chandigarh.eg_roleaction (roleid,actionid) values ((select id from state.eg_role where name='CITIZEN'),
(select id from chandigarh.eg_action where name='Get occupancy sub usages by sub occupancy'));

--------------------------------------------------------------------------------------------------------

Insert into chandigarh.eg_installment_master (ID,INSTALLMENT_NUM,INSTALLMENT_YEAR,START_DATE,END_DATE,ID_MODULE,LASTUPDATEDTIMESTAMP, DESCRIPTION,INSTALLMENT_TYPE) values 
(nextval('SEQ_EG_INSTALLMENT_MASTER'),202004,to_date('01-04-20','DD-MM-YY'),to_date('01-04-20','DD-MM-YY'),to_date('31-03-21','DD-MM-YY'), (select id from chandigarh.eg_module where name = 'BPA' and parentmodule is null), current_timestamp,'BPA/20-21','Yearly');

INSERT INTO chandigarh.financialyear (id,financialyear,startingdate,endingdate,isactive,isactiveforposting,isclosed,transferclosingbalance,createddate,lastmodifiedby,lastmodifieddate,"version",createdby) VALUES 
(nextval('seq_financialyear'),'2020-21','2020-04-01 00:00:00.000','2021-03-31 00:00:00.000',true,true,false,false,now(),1,now(),0,1);

Insert into chandigarh.EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) values
(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='OF'   and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='DF'   and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='LDC'  and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='CW'   and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='CCW'  and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='CSDC' and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='PF'   and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='CRC'  and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='CTC'  and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='CPS'  and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='OCF'  and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='AFWC' and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='AFCW' and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='AFSC' and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='AFRC' and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='RF'   and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='LC'   and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='PEF'  and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='PRF'  and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='OTF'  and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='AF'   and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='ADF'  and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='SF'   and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'))
,(nextval('seq_eg_demand_reason'), (select id from chandigarh.eg_demand_reason_master where code='DPF'  and module=(select id from chandigarh.eg_module where name='BPA')), (select id from chandigarh.EG_INSTALLMENT_MASTER where ID_MODULE = (select id from chandigarh.EG_MODULE where name = 'BPA') and start_date = to_date('01-04-20','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chandigarh.chartofaccounts  where glcode='1401202'));

-------------------------------------------------------------------------------------------------------------

insert into chandigarh.eg_roleaction(roleid,actionid) values((select id from state.eg_role where "name"='BPA Approver'),(select id from chandigarh.eg_action where url='/inbox/history'));
insert into chandigarh.eg_roleaction(roleid,actionid) values((select id from state.eg_role where "name"='BPA Approver'),(select id from chandigarh.eg_action where url='/inbox'));
insert into chandigarh.eg_roleaction(roleid,actionid) values((select id from state.eg_role where "name"='BPA Approver'),(select id from chandigarh.eg_action where url='/inbox/draft'));

delete from chandigarh.eg_roleaction where roleid =(select id from state.eg_role where "name"='BPA Approver') and actionid=(select id from chandigarh.eg_action where displayname='Search Application');
delete from chandigarh.eg_roleaction where roleid =(select id from state.eg_role where "name"='BPA Approver') and actionid=(select id from chandigarh.eg_action where displayname='Search Occupancy Certificate');
delete from chandigarh.eg_roleaction where roleid =(select id from state.eg_role where "name"='BPA Approver') and actionid=(select id from chandigarh.eg_action where displayname='Search Inspection');

delete from chandigarh.eg_roleaction where roleid =(select id from state.eg_role where "name"='BPA Approver') and actionid=(select id from chandigarh.eg_action where displayname='Service type wise status report');
delete from chandigarh.eg_roleaction where roleid =(select id from state.eg_role where "name"='BPA Approver') and actionid=(select id from chandigarh.eg_action where displayname='Zone wise service type report');
delete from chandigarh.eg_roleaction where roleid =(select id from state.eg_role where "name"='BPA Approver') and actionid=(select id from chandigarh.eg_action where displayname='Noc clearance report');

delete from chandigarh.eg_roleaction where roleid =(select id from state.eg_role where "name"='EMPLOYEE') and actionid=(select id from chandigarh.eg_action where displayname='Search Inspection');
delete from chandigarh.eg_roleaction where roleid =(select id from state.eg_role where "name"='EMPLOYEE') and actionid=(select id from chandigarh.eg_action where displayname='Search Permit Renewal');
delete from chandigarh.eg_roleaction where roleid =(select id from state.eg_role where "name"='EMPLOYEE') and actionid=(select id from chandigarh.eg_action where displayname='Search Ownership Transfer');

---------------------------------------------------------------------------------------------------------------------------

update chandigarh.egbpa_mstr_servicetype set validity=5;
update chandigarh.egbpa_mstr_servicetype set renewalvalidity=5;

------------------------------------------------------------------------------------------------------------------------------

update chandigarh.egp_portalservice set userservice=false;

------------------------------------------------------------------------------------------------------------------------------

INSERT INTO state.egdcr_layername (id,"key",value,createdby,createddate,lastmodifieddate,lastmodifiedby,"version") VALUES 
(159,'LAYER_NAME_ROOF_AREA','BLK_%s_FLR_%s_ROOF_AREA',1,'2020-03-24 15:00:59.376','2020-03-24 15:00:59.376',1,0)

----------------------------------------------------------------------------------------------------------------------------------















