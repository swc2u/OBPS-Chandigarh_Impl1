DROP TABLE IF EXISTS eg_plot_master_data;
DROP TABLE IF EXISTS eg_plot_supoccupancy_allowed;
DROP TABLE IF EXISTS eg_plot;

CREATE TABLE eg_plot(
    id bigint NOT NULL,
    boundary bigint,
    plotnum character varying(150),
    name character varying(150) COLLATE pg_catalog."default",
    localname character varying(150) COLLATE pg_catalog."default",
    plotarea numeric(13,6),
    areatype character varying(150) COLLATE pg_catalog."default",
    plotdepth numeric(13,6),
    plotwidth numeric(13,6),
    phase numeric,
    createdby numeric DEFAULT 1,
    createddate timestamp without time zone DEFAULT now(),
    lastmodifiedby numeric DEFAULT 1,
    lastmodifieddate timestamp without time zone DEFAULT now(),
	
    CONSTRAINT eg_plot_pkey PRIMARY KEY (id),
    CONSTRAINT eg_plot_fkey FOREIGN KEY (boundary)
        REFERENCES eg_boundary (id)
);

CREATE TABLE eg_plot_supoccupancy_allowed
(
    id bigint NOT NULL,
	plotid bigint,
	suboccupancyid bigint,
	createdby numeric DEFAULT 1,
    createddate timestamp without time zone DEFAULT now(),
    lastmodifiedby numeric DEFAULT 1,
    lastmodifieddate timestamp without time zone DEFAULT now(),
	
    CONSTRAINT eg_plot_supoccupancy_allowed_pkey PRIMARY KEY (id),
    CONSTRAINT eg_plot_supoccupancy_allowed_fkey_plot FOREIGN KEY (plotid)
        REFERENCES eg_plot (id),
	CONSTRAINT eg_plot_supoccupancy_allowed_fkey_so FOREIGN KEY (subOccupancyid)
        REFERENCES egbpa_sub_occupancy (id)
);

CREATE TABLE eg_plot_master_data
(
    id bigint NOT NULL,
	allowedsuboccupancyid bigint,
	code character varying(10) NOT NULL,    
    backCourtyardWidth numeric(13,6),
    backCourtyardHeight numeric(13,6),  
	permissibleBuildingStories bigint,
	permissibleBuildingHeight numeric(13,6),
	maxmimumPermissibleFAR bigint,
	minimumPermissibleSetback_Front numeric(13,6),
	minimumPermissibleSetback_Rear numeric(13,6),
	minimumPermissibleSetback_left numeric(13,6),
	minimumPermissibleSetback_right numeric(13,6),
    createdby numeric DEFAULT 1,
    createddate timestamp without time zone DEFAULT now(),
    lastmodifiedby numeric DEFAULT 1,
    lastmodifieddate timestamp without time zone DEFAULT now(),
	
    CONSTRAINT eg_plot_master_data_pkey PRIMARY KEY (id),
    CONSTRAINT eg_plot_master_data_fkey FOREIGN KEY (allowedsuboccupancyid)
        REFERENCES eg_plot_supoccupancy_allowed (id)
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
