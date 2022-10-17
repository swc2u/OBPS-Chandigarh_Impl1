--------Ar. Bavneet Kaur


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'bavneetk0904@gmail.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '9779032452', current_date, current_date, 1, 1, true, 'Ar. Bavneet Kaur', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='bavneetk0904@gmail.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='bavneetk0904@gmail.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='bavneetk0904@gmail.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA359', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='bavneetk0904@gmail.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA359');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='bavneetk0904@gmail.com' and tenantid='state'), 1, 'CA/2020/124183', 'CA/2020/124183', '17/OCT/2022', 'CA/2020/124183', '31/DEC/2031', null, null, 'H.No.371, Sector 86 (Preet City), Sohana, Mohali.', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', null, null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/2013/60421');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='bavneetk0904@gmail.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='bavneetk0904@gmail.com' and tenantid='state'));


--------Ar. Sushil Ghuma


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'sushil.ghuman@1cloud.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '9816080132', current_date, current_date, 1, 1, true, 'Ar. Sushil Ghuma', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='sushil.ghuman@1cloud.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='sushil.ghuman@1cloud.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='sushil.ghuman@1cloud.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA360', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='sushil.ghuman@1cloud.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA360');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='sushil.ghuman@1cloud.com' and tenantid='state'), 1, 'CA/1991/14304', 'CA/1991/14304', '17/OCT/2022', 'CA/1991/14304', '31/DEC/2029', null, null, 'H.No.129, Amravati Enclave NH 22, Chandimandir, Panchkula, Village Toran, Haryana- 134107', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', null, null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/1991/14304');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='sushil.ghuman@1cloud.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='sushil.ghuman@1cloud.com' and tenantid='state'));


--------Ar. Shilpa Das


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'shilpa.evolve@gmail.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '9356066164', current_date, current_date, 1, 1, true, 'Ar. Shilpa Das', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='shilpa.evolve@gmail.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='shilpa.evolve@gmail.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='shilpa.evolve@gmail.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA361', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='shilpa.evolve@gmail.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA361');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='shilpa.evolve@gmail.com' and tenantid='state'), 1, 'CA/1997/21939', 'CA/1997/21939', '17/OCT/2022', 'CA/1997/21939', '31/DEC/2027', null, null, 'Evolve Architecture, SCO 95, First Floor, Swastik Vihar, Sector 5, MansaDevi Complex, Panchkula_x0002_134109', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', null, null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/1997/21939');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='shilpa.evolve@gmail.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='shilpa.evolve@gmail.com' and tenantid='state'));


--------Ar. Aashray Ahuja


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'aashrayahuja10@gmail.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '9988951179', current_date, current_date, 1, 1, true, 'Ar. Aashray Ahuja', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='aashrayahuja10@gmail.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='aashrayahuja10@gmail.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='aashrayahuja10@gmail.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA362', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='aashrayahuja10@gmail.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA362');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='aashrayahuja10@gmail.com' and tenantid='state'), 1, 'CA/2021/138946', 'CA/2021/138946', '17/OCT/2022', 'CA/2021/138946', '31/DEC/2022', null, null, 'H.No.5046/3, Modern Housing Complex, Sector 13 (Manimajra), 160101, Chandigarh. 99', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', null, null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/2021/138946');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='aashrayahuja10@gmail.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='aashrayahuja10@gmail.com' and tenantid='state'));


--------Ar. Archita Ahuluwalia


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'architaahluwalia2012@gmail.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '9717198699', current_date, current_date, 1, 1, true, 'Ar. Archita Ahuluwalia', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='architaahluwalia2012@gmail.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='architaahluwalia2012@gmail.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='architaahluwalia2012@gmail.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA363', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='architaahluwalia2012@gmail.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA363');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='architaahluwalia2012@gmail.com' and tenantid='state'), 1, 'CA/2018/94291', 'CA/2018/94291', '17/OCT/2022', 'CA/2018/94291', '31/DEC/2022', null, null, 'H.No.5496, Sector 38 West, 160036, Chandigarh', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', '0172- 2636876', null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/2018/94291');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='architaahluwalia2012@gmail.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='architaahluwalia2012@gmail.com' and tenantid='state'));


--------Ar. Rajinder Singh Saini


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'rajindersaini.7472@gmail.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '9815726191', current_date, current_date, 1, 1, true, 'Ar. Rajinder Singh Saini', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='rajindersaini.7472@gmail.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='rajindersaini.7472@gmail.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='rajindersaini.7472@gmail.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA364', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='rajindersaini.7472@gmail.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA364');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='rajindersaini.7472@gmail.com' and tenantid='state'), 1, 'CA/2011/53381', 'CA/2011/53381', '17/OCT/2022', 'CA/2011/53381', '31/DEC/2023', null, null, 'H.No.HE-150 A, Sector 63, Phase 9, SAS Nagar Mohali (Punjab) 160062', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', '7696362091', null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/2011/53381');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='rajindersaini.7472@gmail.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='rajindersaini.7472@gmail.com' and tenantid='state'));

--------Ar. Dhwani Singh  


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'ar.dhwanisingh@gmail.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '9811223345', current_date, current_date, 1, 1, true, 'Ar. Dhwani Singh', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='ar.dhwanisingh@gmail.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='ar.dhwanisingh@gmail.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='ar.dhwanisingh@gmail.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA365', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='ar.dhwanisingh@gmail.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA365');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='ar.dhwanisingh@gmail.com' and tenantid='state'), 1, 'CA/2013/58686', 'CA/2013/58686', '17/OCT/2022', 'CA/2013/58686', '31/DEC/2024', null, null, 'H.No.3335, Sector 21-D, Chandigarh', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', null, null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/2013/58686');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='ar.dhwanisingh@gmail.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='ar.dhwanisingh@gmail.com' and tenantid='state'));


--------Ar. Dheren Sethi  


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'dherensethi@gmail.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '7347562392', current_date, current_date, 1, 1, true, 'Ar. Dheren Sethi', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='dherensethi@gmail.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='dherensethi@gmail.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='dherensethi@gmail.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA366', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='dherensethi@gmail.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA366');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='dherensethi@gmail.com' and tenantid='state'), 1, 'CA/2022/141489', 'CA/2022/141489', '17/OCT/2022', ' CA/2022/141489', '31/DEC/2023', null, null, 'H.No.142-A, Guru Nanak Enclave near Poice Station Dhakoli, Dhakoli, Zirakpur, Mohali', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', '9780872514', null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/2022/141489');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='dherensethi@gmail.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='dherensethi@gmail.com' and tenantid='state'));

--------Ar. Rajat Verma  


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'rajatverma3596@gmail.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '9416411607', current_date, current_date, 1, 1, true, 'Ar. Rajat Verma', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='rajatverma3596@gmail.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='rajatverma3596@gmail.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='rajatverma3596@gmail.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA367', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='rajatverma3596@gmail.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA367');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='rajatverma3596@gmail.com' and tenantid='state'), 1, 'CA/2019/110166', 'CA/2019/110166', '17/OCT/2022', ' CA/2019/110166', '31/DEC/2031', null, null, 'H.No.616, Sector 41-A, Chandigarh', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', null, null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/2019/110166');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='rajatverma3596@gmail.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='rajatverma3596@gmail.com' and tenantid='state'));

--------Ar. Lohita Gupta  


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'lohitagupta94@gmail.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '8264633398', current_date, current_date, 1, 1, true, 'Ar. Lohita Gupta', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='lohitagupta94@gmail.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='lohitagupta94@gmail.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='lohitagupta94@gmail.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA368', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='lohitagupta94@gmail.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA368');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='lohitagupta94@gmail.com' and tenantid='state'), 1, 'CA/2019/107082', 'CA/2019/107082', '17/OCT/2022', ' CA/2019/107082', '31/DEC/2031', null, null, 'F-7/520, Ward No.11, Majitha Road, Kashmir Avenue, Amritsar 142001', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', null, null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/2019/107082');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='lohitagupta94@gmail.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='lohitagupta94@gmail.com' and tenantid='state'));


--------Ar. Sachin K. Gupta  


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'vgcsachin@gmail.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '8968772420', current_date, current_date, 1, 1, true, 'Ar. Sachin K. Gupta', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='vgcsachin@gmail.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='vgcsachin@gmail.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='vgcsachin@gmail.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA369', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='vgcsachin@gmail.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA369');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='vgcsachin@gmail.com' and tenantid='state'), 1, 'CA/2017/82425', 'CA/2017/82425', '17/OCT/2022', ' CA/2017/82425', '31/DEC/2031', null, null, '30712-B, Gurkul Road, Opposite Churchyard, Jogi Nagar, Bathinda Punjab- 151001', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', null, null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/2017/82425');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='vgcsachin@gmail.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='vgcsachin@gmail.com' and tenantid='state'));


--------Ar. Rajender Pal1  


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'architectrajenderpal@gmail.com1', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '7018334281', current_date, current_date, 1, 1, true, 'Ar. Rajender Pal1', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='architectrajenderpal@gmail.com1' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='architectrajenderpal@gmail.com1' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='architectrajenderpal@gmail.com1' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA370', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='architectrajenderpal@gmail.com1' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA370');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='architectrajenderpal@gmail.com1' and tenantid='state'), 1, 'CA/2018/10061', 'CA/2018/10061', '17/OCT/2022', ' CA/2018/10061', '31/DEC/2029', null, null, ' H.No. 327, Mata Gujri Avenue, Bhago Majra, Tehsil_x0002_Kharar, Distt. SAS Nagar Mohali, Punjab-14030', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', null, null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/2018/10061');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='architectrajenderpal@gmail.com1' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='architectrajenderpal@gmail.com1' and tenantid='state'));


--------Ar. Neetu 


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'arch3chd@yahoo.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '7667015583', current_date, current_date, 1, 1, true, 'Ar. Neetu', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='arch3chd@yahoo.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='arch3chd@yahoo.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='arch3chd@yahoo.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA371', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='arch3chd@yahoo.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA371');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='arch3chd@yahoo.com' and tenantid='state'), 1, 'CA/2013/60421', 'CA/2013/60421', '17/OCT/2022', ' CA/2013/60421', '31/DEC/2025', null, null, 'Chief Architect, O/o the SDG Chandigarh, CPWD, Kendriya Sadan, Sector 9A, Chandigarh.', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', null, null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/2013/60421');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='arch3chd@yahoo.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='arch3chd@yahoo.com' and tenantid='state'));