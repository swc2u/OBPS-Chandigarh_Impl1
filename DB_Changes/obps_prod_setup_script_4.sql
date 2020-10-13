---Comments size changes--

ALTER TABLE chandigarh.eg_wf_state_history ALTER COLUMN "comments" type varchar(3750);

ALTER TABLE state.eg_wf_state_history ALTER COLUMN "comments" type varchar(3750);

ALTER TABLE chandigarh.eg_wf_states ALTER COLUMN "comments" type varchar(3750);

ALTER TABLE state.eg_wf_states ALTER COLUMN "comments" type varchar(3750);
