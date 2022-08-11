DROP TABLE IF EXISTS eg_plot_master_data_aud;
DROP TABLE IF EXISTS eg_plot_supoccupancy_allowed_aud;
DROP TABLE IF EXISTS eg_plot_aud;

CREATE TABLE eg_plot_aud(
    id bigint NOT NULL,
    rev integer,
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
    isActive boolean,
    version numeric DEFAULT 0,
    revtype numeric DEFAULT 0
);
CREATE TABLE eg_plot_supoccupancy_allowed_aud
(
    id bigint NOT NULL,
     rev integer,
	plot bigint,
	suboccupancy bigint,
	createdby numeric DEFAULT 1,
    createddate timestamp without time zone DEFAULT now(),
    lastmodifiedby numeric DEFAULT 1,
    lastmodifieddate timestamp without time zone DEFAULT now(),
	version numeric DEFAULT 0,
	isActive boolean,
	revtype numeric DEFAULT 0
);

CREATE TABLE eg_plot_master_data_aud
(
    id bigint NOT NULL,
     rev integer,
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
	minimumPermissibleSetback_right character varying(30),
	fromdate timestamp without time zone DEFAULT now(),
	todate timestamp without time zone DEFAULT now(),
    createdby numeric DEFAULT 1,
    createddate timestamp without time zone DEFAULT now(),
    lastmodifiedby numeric DEFAULT 1,
    lastmodifieddate timestamp without time zone DEFAULT now(),
	version numeric DEFAULT 0,
	isActive boolean,
	revtype numeric DEFAULT 0
);



CREATE SEQUENCE IF NOT EXISTS seq_eg_plot_aud
       START WITH 1
       INCREMENT BY 1
       NO MINVALUE
       NO MAXVALUE
       CACHE 1;
       
 CREATE SEQUENCE IF NOT EXISTS seq_eg_plot_supoccupancy_allowed_aud
       START WITH 1
       INCREMENT BY 1
       NO MINVALUE
       NO MAXVALUE
       CACHE 1;
       
   CREATE SEQUENCE IF NOT EXISTS seq_eg_plot_master_data_aud
       START WITH 1
       INCREMENT BY 1
       NO MINVALUE
       NO MAXVALUE
       CACHE 1;