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
