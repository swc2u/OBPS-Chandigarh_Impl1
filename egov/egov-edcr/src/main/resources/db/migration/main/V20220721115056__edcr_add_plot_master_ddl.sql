DROP TABLE IF EXISTS eg_plot_master_data;
DROP TABLE IF EXISTS eg_plot_suboccupancy_allowed;
DROP TABLE IF EXISTS eg_plot;

CREATE TABLE eg_plot(
    id bigint NOT NULL,
    boundary bigint,
    plotnum character varying(150),
    name character varying(150) COLLATE pg_catalog."default",
    localname character varying(150) COLLATE pg_catalog."default",
    plotarea numeric(13,6),
    areatype character varying(150) COLLATE pg_catalog."default",
    plotdepth character varying(30),
    plotwidth character varying(30),
    phase numeric,
    createdby numeric DEFAULT 1,
    createddate timestamp without time zone DEFAULT now(),
    lastmodifiedby numeric DEFAULT 1,
    lastmodifieddate timestamp without time zone DEFAULT now(),
    version numeric DEFAULT 0,
    isActive boolean,
	
    CONSTRAINT eg_plot_pkey PRIMARY KEY (id),
    CONSTRAINT eg_plot_fkey FOREIGN KEY (boundary)
        REFERENCES eg_boundary (id),
     CONSTRAINT eg_plot_unique_pnum_bndry UNIQUE (plotnum,boundary)
);

CREATE TABLE eg_plot_suboccupancy_allowed
(
    id bigint NOT NULL,
	plot bigint,
	suboccupancy bigint,
	createdby numeric DEFAULT 1,
    createddate timestamp without time zone DEFAULT now(),
    lastmodifiedby numeric DEFAULT 1,
    lastmodifieddate timestamp without time zone DEFAULT now(),
	version numeric DEFAULT 0,
	isActive boolean,
    CONSTRAINT eg_plot_supoccupancy_allowed_pkey PRIMARY KEY (id),
    CONSTRAINT eg_plot_supoccupancy_allowed_fkey_plot FOREIGN KEY (plot)
        REFERENCES eg_plot (id),
	CONSTRAINT eg_plot_supoccupancy_allowed_fkey_so FOREIGN KEY (subOccupancy)
        REFERENCES egbpa_sub_occupancy (id)
);

CREATE TABLE eg_plot_master_data
(
    id bigint NOT NULL,
	allowedsuboccupancy bigint,
	code character varying(10) NOT NULL,    
    backCourtyardWidth character varying(30),
    backCourtyardHeight character varying(30),  
	permissibleBuildingStories bigint,
	permissibleBuildingHeight numeric(13,6),
	maxmimumPermissibleFAR numeric(13,6),
	minimumPermissibleSetback_Front character varying(30),
	minimumPermissibleSetback_Rear character varying(30),
	minimumPermissibleSetback_left character varying(30),
	minimumPermissibleSetback_left_depth character varying(30),
	minimumPermissibleSetback_left_width character varying(30),
	minimumPermissibleSetback_right character varying(30),
	minimumPermissibleSetback_right_depth character varying(30),
	minimumPermissibleSetback_right_width character varying(30),
	fromdate timestamp without time zone DEFAULT now(),
	todate timestamp without time zone DEFAULT now(),
    createdby numeric DEFAULT 1,
    createddate timestamp without time zone DEFAULT now(),
    lastmodifiedby numeric DEFAULT 1,
    lastmodifieddate timestamp without time zone DEFAULT now(),
	version numeric DEFAULT 0,
	isActive boolean,
	
    CONSTRAINT eg_plot_master_data_pkey PRIMARY KEY (id),
    CONSTRAINT eg_plot_master_data_fkey FOREIGN KEY (allowedsuboccupancy)
        REFERENCES eg_plot_suboccupancy_allowed (id)
);

CREATE SEQUENCE IF NOT EXISTS seq_eg_plot
       START WITH 1
       INCREMENT BY 1
       NO MINVALUE
       NO MAXVALUE
       CACHE 1;
 CREATE SEQUENCE IF NOT EXISTS seq_eg_plot_supoccupancy_allowed
       START WITH 1
       INCREMENT BY 1
       NO MINVALUE
       NO MAXVALUE
       CACHE 1;
       
   CREATE SEQUENCE IF NOT EXISTS seq_eg_plot_master_data
       START WITH 1
       INCREMENT BY 1
       NO MINVALUE
       NO MAXVALUE
       CACHE 1;
