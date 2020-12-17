CREATE TABLE chandigarh.eg_wf_file (
	id int8 NOT NULL,
	filestore int8 NOT NULL,
	staterefid varchar(100) NOT NULL,
	createdby int8 NOT NULL,
	createddate timestamp NOT NULL,
	lastmodifiedby int8 NOT NULL,
	lastmodifieddate timestamp NOT NULL,
	"version" int8 NULL DEFAULT 0,
	CONSTRAINT pk_eg_wf_file_id PRIMARY KEY (id)
);

ALTER TABLE chandigarh.eg_wf_file ADD CONSTRAINT fk_eg_wf_file_filestore FOREIGN KEY (filestore) REFERENCES chandigarh.eg_filestoremap(id);

CREATE SEQUENCE seq_eg_wf_files;

ALTER TABLE chandigarh.eg_wf_states ADD COLUMN reffileid varchar(100) default null;
ALTER TABLE chandigarh.eg_wf_state_history ADD COLUMN reffileid varchar(100) default null;

update chandigarh.eg_module set displayname ='Building Plan Scrutiny' where "name" ='Digit DCR';
update chandigarh.eg_module set displayname ='Building Plan permission' where "name" ='BPA';

CREATE TABLE chandigarh.egbpa_lettertoparty_fee_mstr (
	id int8 NOT NULL,
	feename varchar(200) NULL,
	floornumber int8 NOT NULL,
	isactive bool NULL DEFAULT true,
	"version" numeric NULL DEFAULT 0,
	createdby int8 NOT NULL,
	createddate timestamp NOT NULL,
	lastmodifiedby int8 NULL,
	lastmodifieddate timestamp NULL,
	CONSTRAINT pk_egbpa_lettertoparty_fee_mstr PRIMARY KEY (id)
);
ALTER TABLE chandigarh.egbpa_lettertoparty_fee_mstr ADD CONSTRAINT fk_egbpa_lettertoparty_fee_mstr_crtby FOREIGN KEY (createdby) REFERENCES state.eg_user(id);
ALTER TABLE chandigarh.egbpa_lettertoparty_fee_mstr ADD CONSTRAINT fk_egbpa_lettertoparty_fee_mstr_mdfdby FOREIGN KEY (lastmodifiedby) REFERENCES state.eg_user(id);

CREATE SEQUENCE chandigarh."seq_egbpa_lettertoparty_fee_mstr";

CREATE TABLE chandigarh.egbpa_permit_lettertoparty_fee (
	id int8 NOT NULL,
	lettertoparty int8 NOT NULL,
	application int8 NOT NULL,
	createdby int8 NOT NULL,
	createddate timestamp NOT NULL,
	lastmodifiedby int8 NULL,
	lastmodifieddate timestamp NULL,
	"version" numeric NULL DEFAULT 0,
	CONSTRAINT pk_egbpa_permit_lettertoparty_fee PRIMARY KEY (id)
);

ALTER TABLE chandigarh.egbpa_permit_lettertoparty_fee ADD CONSTRAINT fk_egbpa_permit_lettertoparty_fee_appln FOREIGN KEY (application) REFERENCES chandigarh.egbpa_application(id);
ALTER TABLE chandigarh.egbpa_permit_lettertoparty_fee ADD CONSTRAINT fk_egbpa_permit_lettertoparty_fee_lp FOREIGN KEY (lettertoparty) REFERENCES chandigarh.egbpa_permit_letter_to_party(id);
ALTER TABLE chandigarh.egbpa_permit_lettertoparty_fee ADD CONSTRAINT fk_egbpa_permit_lettertoparty_fee_crtby FOREIGN KEY (createdby) REFERENCES state.eg_user(id);
ALTER TABLE chandigarh.egbpa_permit_lettertoparty_fee ADD CONSTRAINT fk_egbpa_permit_lettertoparty_fee_mdfdby FOREIGN KEY (lastmodifiedby) REFERENCES state.eg_user(id);

CREATE SEQUENCE chandigarh."seq_egbpa_permit_lettertoparty_fee";

CREATE TABLE chandigarh.egbpa_permit_lettertoparty_fee_details (
	id int8 NOT NULL,
	lettertopartyfee int8 NOT NULL,
	feemstr int8 NOT NULL,
	ismandatory bool NULL DEFAULT false,
	floorarea float8 NULL,
	remarks varchar(1000) NULL,	
	createdby int8 NOT NULL,
	createddate timestamp NOT NULL,
	lastmodifiedby int8 NULL,
	lastmodifieddate timestamp NULL,
	"version" numeric NULL DEFAULT 0,
	CONSTRAINT pk_egbpa_permit_lettertoparty_fee_details PRIMARY KEY (id)
);

ALTER TABLE chandigarh.egbpa_permit_lettertoparty_fee_details ADD CONSTRAINT fk_egbpa_permit_lettertoparty_fee_details_lpfee FOREIGN KEY (lettertopartyfee) REFERENCES chandigarh.egbpa_permit_lettertoparty_fee(id);
ALTER TABLE chandigarh.egbpa_permit_lettertoparty_fee_details ADD CONSTRAINT fk_egbpa_permit_lettertoparty_fee_details_feemstr FOREIGN KEY (feemstr) REFERENCES chandigarh.egbpa_lettertoparty_fee_mstr(id);
ALTER TABLE chandigarh.egbpa_permit_lettertoparty_fee_details ADD CONSTRAINT fk_egbpa_permit_lettertoparty_fee_details_crtby FOREIGN KEY (createdby) REFERENCES state.eg_user(id);
ALTER TABLE chandigarh.egbpa_permit_lettertoparty_fee_details ADD CONSTRAINT fk_egbpa_permit_lettertoparty_fee_details_mdfdby FOREIGN KEY (lastmodifiedby) REFERENCES state.eg_user(id);

CREATE SEQUENCE chandigarh."seq_egbpa_permit_lettertoparty_fee_details";

INSERT INTO chandigarh.egbpa_lettertoparty_fee_mstr(id, feename, floornumber, isactive, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)VALUES
(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Labour cess', 0, true, 0, 1, now(), 1, now())
,(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Labour cess', 1, true, 0, 1, now(), 1, now())
,(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Labour cess', 2, true, 0, 1, now(), 1, now())
,(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Labour cess', 3, true, 0, 1, now(), 1, now())
,(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Labour cess', 4, true, 0, 1, now(), 1, now())
,(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Additional Coverage fee', 0, true, 0, 1, now(), 1, now())
,(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Additional Coverage fee', 1, true, 0, 1, now(), 1, now())
,(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Additional Coverage fee', 2, true, 0, 1, now(), 1, now())
,(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Additional Coverage fee', 3, true, 0, 1, now(), 1, now())
,(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Additional Coverage fee', 4, true, 0, 1, now(), 1, now())
,(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Rule 5 fee', 0, true, 0, 1, now(), 1, now())
,(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Rule 5 fee', 1, true, 0, 1, now(), 1, now())
,(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Rule 5 fee', 2, true, 0, 1, now(), 1, now())
,(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Rule 5 fee', 3, true, 0, 1, now(), 1, now())
,(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Rule 5 fee', 4, true, 0, 1, now(), 1, now())
,(nextval('seq_egbpa_lettertoparty_fee_mstr'), 'Scrutiny fee', 0, true, 0, 1, now(), 1, now())
;