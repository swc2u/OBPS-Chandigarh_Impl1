---Comments size changes--

ALTER TABLE chandigarh.eg_wf_state_history ALTER COLUMN "comments" type varchar(4000);

ALTER TABLE state.eg_wf_state_history ALTER COLUMN "comments" type varchar(4000);

ALTER TABLE chandigarh.eg_wf_states ALTER COLUMN "comments" type varchar(4000);

ALTER TABLE state.eg_wf_states ALTER COLUMN "comments" type varchar(4000);
