--------Ar. Kanav Khosla


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'kanavkhosla@gmail.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '9814009514', current_date, current_date, 1, 1, true, 'Ar. Kanav Khosla', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='kanavkhosla@gmail.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='kanavkhosla@gmail.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='kanavkhosla@gmail.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA372', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='kanavkhosla@gmail.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA372');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='kanavkhosla@gmail.com' and tenantid='state'), 1, 'CA/1998/23591', 'CA/1998/23591', '28/NOV/2022', 'CA/1998/23591', '31/DEC/2030', null, null, 'H.No.568-A1, Police House Link Road, Near Fountain Chowk, Ci vil Lines, Ludhiana-141001 ', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', null, null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/1998/23591');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='kanavkhosla@gmail.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='kanavkhosla@gmail.com' and tenantid='state'));

--------Ar. Avinash Khosla


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'kanavkhosla@gmail.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '9780821780', current_date, current_date, 1, 1, true, 'Ar. Avinash Khosla', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='kanavkhosla@gmail.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='kanavkhosla@gmail.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='kanavkhosla@gmail.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA373', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='kanavkhosla@gmail.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA373');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='kanavkhosla@gmail.com' and tenantid='state'), 1, ' CA.1975/1829', ' CA.1975/1829', '28/NOV/2022', ' CA.1975/1829', '31/DEC/2029', null, null, 'H.No.568-A1, Police House Link Road, Near Fountain Chowk, Ci vil Lines, Ludhiana-141001 ', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', null, null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code=' CA.1975/1829');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='kanavkhosla@gmail.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='kanavkhosla@gmail.com' and tenantid='state'));


--------Ar. Amit Saini


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'office@architectsatelier.co.in amitsaini@architectsatelier.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '9814920453', current_date, current_date, 1, 1, true, 'Ar. Amit Saini', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='office@architectsatelier.co.in amitsaini@architectsatelier.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='office@architectsatelier.co.in amitsaini@architectsatelier.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='office@architectsatelier.co.in amitsaini@architectsatelier.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA374', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='office@architectsatelier.co.in amitsaini@architectsatelier.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA374');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='office@architectsatelier.co.in amitsaini@architectsatelier.com' and tenantid='state'), 1, 'CA/1999/25076', 'CA/1999/25076', '28/NOV/2022', 'CA/1999/25076', '31/DEC/2030', null, null, 'Architects atelier, 1246, Sector 8C, Chandigarh-160009', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', null, null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/1999/25076');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='office@architectsatelier.co.in amitsaini@architectsatelier.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='office@architectsatelier.co.in amitsaini@architectsatelier.com' and tenantid='state'));


--------Ar. Divya Pardal


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'pardaldivya06@gmail.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '9646792989', current_date, current_date, 1, 1, true, 'Ar. Divya Pardal', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='pardaldivya06@gmail.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='pardaldivya06@gmail.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='pardaldivya06@gmail.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA375', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='pardaldivya06@gmail.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA375');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='pardaldivya06@gmail.com' and tenantid='state'), 1, 'CA/2022/144368', 'CA/2022/144368', '28/NOV/2022', 'CA/2022/144368', '31/DEC/2023', null, null, 'Flat No. 203, Divya Apartments GH 21, Sector 27, Panchkula Haryana ', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', null, null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/2022/144368');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='pardaldivya06@gmail.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='pardaldivya06@gmail.com' and tenantid='state'));


--------Ar. Noor Dasmesh  


insert into state.eg_user (id, tenantid, locale, username, password, pwdexpirydate, mobilenumber, createddate, lastmodifieddate, createdby, lastmodifiedby, active, name, gender, type, version) 
select nextval('state.seq_eg_user'), 'state', 'en_IN', 'noorarchitects@gmail.com', '$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK', '31-Dec-2099', '9988996667', current_date, current_date, 1, 1, true, 'Ar. Noor Dasmesh', 1, 'BUSINESS', 0 where not exists (select id from state.eg_user where username='noorarchitects@gmail.com' and tenantid='state');


insert into state.eg_userrole (roleid, userid) 
select (select id from state.eg_role where upper(name)='BUSINESS'), (select id from state.eg_user where username='noorarchitects@gmail.com' and tenantid='state') where not exists (select roleid,userid from state.eg_userrole where roleid in (select id from state.eg_role where upper(name)='BUSINESS') and userid in (select id from state.eg_user where username='noorarchitects@gmail.com' and tenantid='state'));

INSERT INTO state.eg_address (housenobldgapt, subdistrict, postoffice, landmark, country, userid, type, streetroadline, citytownvillage, arealocalitysector, district, state, pincode, id, version)
 select 'NA376', null, 'Chandigarh', null, 'india', (select id from state.eg_user where username='noorarchitects@gmail.com' and tenantid='state'), 'CORRESPONDENCE', 'Building over the New Bridge', 'Chandigarh', 'Chandigarh', 'Chandigarh', 'Chandigarh', null, nextval('state.seq_eg_address'), 0 where not exists (select id from state.eg_address where housenobldgapt='NA376');

INSERT INTO state.egbpa_mstr_stakeholder (id, stakeholdertype, code, licencenumber, buildinglicenceissuedate, coaenrolmentnumber, coaenrolmentduedate, isenrolwithlocalbody, organizationname, organizationaddress, organizationurl, organizationmobno, isonbehalfoforganization, tinnumber, version, createduser, createdate, lastupdateduser, lastupdateddate, buildinglicenceexpirydate, contactperson, designation, source, comments, status, isaddresssame, nooftimesrejected, nooftimesblocked, demand, cinnumber)
 select (select id from state.eg_user where username='noorarchitects@gmail.com' and tenantid='state'), 1, 'CA/2004/33559', 'CA/2004/33559', '28/NOV/2022', 'CA/2004/33559', '31/DEC/2028', null, null, 'Flat No. 203, Divya Apartments GH 21, Sector 27, Panchkula Haryana ', null, null, false, null, 0, 1, now(), 1, now(), '2030-03-31 13:20:49.463', null, null, 3, '', 'APPROVED', true, null, null, null, null where not exists (select id from state.egbpa_mstr_stakeholder where code='CA/2004/33559');

insert into state.eg_businessuser (id) 
select id from state.eg_user where username='noorarchitects@gmail.com' and tenantid='state' and not exists (select id from state.eg_businessuser where id = (select id from state.eg_user where username='noorarchitects@gmail.com' and tenantid='state'));