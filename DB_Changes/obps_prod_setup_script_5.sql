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

update chandigarh.EG_WF_STATES set nextaction ='Fee payment by applicant' where nextaction = 'Forwarded to update permit conditions';
update chandigarh.EG_WF_STATES set nextaction ='Property documents verification' where nextaction = 'Forwarded to property documents verification';
update chandigarh.EG_WF_STATES set nextaction ='SDO Building Approval' where nextaction = 'Forwarded to SDO Building for Approval';
update chandigarh.EG_WF_STATES set nextaction ='Plan inspection' where nextaction = 'Forwarded to check NOC updation';
update chandigarh.EG_WF_STATES set nextaction ='Assistant Estate Officer Approval' where nextaction = 'Forwarded to AEO for Approval';
update chandigarh.EG_WF_STATES set nextaction ='Estate Officer Approval' where nextaction = 'Forwarded to EO for Approval';