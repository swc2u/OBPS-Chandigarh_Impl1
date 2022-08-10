INSERT INTO chandigarh.egbpa_lettertoparty_fee_mstr(
	id, feename, floornumber, isactive, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
	VALUES (Nextval('chandigarh.seq_egbpa_lettertoparty_fee_mstr'), 'Security fee', 0, true, 0, 1, Now(), 1, Now());