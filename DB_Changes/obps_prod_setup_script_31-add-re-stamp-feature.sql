	INSERT INTO chandigarh.eg_feature(
	id, name, description, module, version)
	VALUES (Nextval('chandigarh.seq_eg_feature'), 'Re-stamp documents', 'Re-stamp documents', 1, 0);
	
INSERT INTO chandigarh.eg_feature_role(
	role, feature)
	VALUES (5, (select id from chandigarh.eg_feature where name='Re-stamp documents'));