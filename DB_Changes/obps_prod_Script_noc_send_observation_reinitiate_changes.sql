INSERT INTO chandigarh.egbpa_status
(id, code, description, moduletype, isactive, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextVal('chandigarh.seq_egbpa_status'), 'Re Initiated', 'When BPA re initiates an application which was marked for send observations', 'NOC', true, 0, 1, now(), NULL, now());


INSERT INTO chandigarh.egbpa_status
(id, code, description, moduletype, isactive, "version", createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES(nextVal('chandigarh.seq_egbpa_status'), 'Send Observations', 'When respective NOC department user marks the application for Send Observations', 'NOC', true, 0, 1, now(), NULL, now());


ALTER TABLE chandigarh.egbpa_nocapplication ADD COLUMN comments varchar(2000) default null;


INSERT INTO chandigarh.eg_action
(id, "name", url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, "version", createdby, createddate, lastmodifiedby, lastmodifieddate, application)
VALUES(nextVal('chandigarh.seq_eg_action'), 'reInitiateNoc', '/nocapplication/reInitiateNoc', NULL, 445, 127, 'reInitiateNoc', false, 'bpa', 0, 1, now(), 1, now(), 444);

INSERT INTO chandigarh.eg_roleaction
(roleid, actionid)
VALUES(32, currVal('chandigarh.seq_eg_action'));
INSERT INTO chandigarh.eg_roleaction
(roleid, actionid)
VALUES(3, currVal('chandigarh.seq_eg_action'));



CREATE TABLE chandigarh.egbpa_nocapplication_history (
	id int8 NOT NULL,
	noc_id int8 NOT NULL,
	noctype varchar(128) NULL,
	status int8 NOT NULL,
	remarks varchar(2000) NULL,
	createddate timestamp NULL,
	createdby int8 NOT NULL,
	lastmodifiedby int8 NULL,
	lastmodifieddate timestamp NULL,
	"version" numeric NULL DEFAULT 0,
	nocapplicationnumber varchar(128) NULL,
	slaenddate date NULL,
	deemedapproveddate date NULL,
	comments varchar(2000) NULL,
	CONSTRAINT pk_egbpa_nocapplication_history_id PRIMARY KEY (id)
);



CREATE SEQUENCE chandigarh.seq_egbpa_nocapplication_history
INCREMENT BY 1  
MINVALUE 1   
MAXVALUE 999999999999999  
START 1 ; 

drop function chandigarh.func_egbpa_nocapplication_audit();
CREATE OR REPLACE FUNCTION chandigarh.func_egbpa_nocapplication_audit() RETURNS TRIGGER AS $$
BEGIN
IF(TG_OP='INSERT' OR TG_OP='UPDATE' OR TG_OP='DELETE') THEN
    INSERT INTO chandigarh.egbpa_nocapplication_history values 
 ( nextVal('chandigarh.seq_egbpa_nocapplication_history'), old.id, old.noctype, old.status, old.remarks, old.createddate, old.createdby, old.lastmodifiedby, old.lastmodifieddate, old."version", old.nocapplicationnumber, old.slaenddate , old.deemedapproveddate , old."comments" );
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;
DROP TRIGGER  trigger_egbpa_nocapplication_audit ON chandigarh.egbpa_nocapplication;
CREATE TRIGGER trigger_egbpa_nocapplication_audit AFTER INSERT OR UPDATE OR DELETE ON chandigarh.egbpa_nocapplication FOR EACH ROW EXECUTE PROCEDURE chandigarh.func_egbpa_nocapplication_audit();





