--
-- PostgreSQL database dump
--

-- Started on 2013-10-09 11:48:39 CEST

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 140 (class 1259 OID 16386)
-- Dependencies: 1841 6
-- Name: amphi; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE amphi (
    amphiid integer NOT NULL,
    buildingid integer NOT NULL,
    name character varying NOT NULL,
    ipaddress character varying NOT NULL,
    status boolean NOT NULL,
    gmapurl character varying,
    version character varying,
    restrictionuds boolean DEFAULT false NOT NULL
);


ALTER TABLE public.amphi OWNER TO sqluser;

--
-- TOC entry 141 (class 1259 OID 16393)
-- Dependencies: 6 140
-- Name: amphi_amphiid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE amphi_amphiid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.amphi_amphiid_seq OWNER TO sqluser;

--
-- TOC entry 1900 (class 0 OID 0)
-- Dependencies: 141
-- Name: amphi_amphiid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sqluser
--

ALTER SEQUENCE amphi_amphiid_seq OWNED BY amphi.amphiid;


--
-- TOC entry 142 (class 1259 OID 16395)
-- Dependencies: 6
-- Name: building; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE building (
    buildingid integer NOT NULL,
    name character varying NOT NULL,
    imagefile character varying NOT NULL
);


ALTER TABLE public.building OWNER TO sqluser;

--
-- TOC entry 143 (class 1259 OID 16401)
-- Dependencies: 6 142
-- Name: building_buildingid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE building_buildingid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.building_buildingid_seq OWNER TO sqluser;

--
-- TOC entry 1902 (class 0 OID 0)
-- Dependencies: 143
-- Name: building_buildingid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sqluser
--

ALTER SEQUENCE building_buildingid_seq OWNED BY building.buildingid;


--
-- TOC entry 144 (class 1259 OID 16403)
-- Dependencies: 1844 1845 1846 1847 1848 1849 6
-- Name: course; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE course (
    courseid integer NOT NULL,
    date timestamp without time zone NOT NULL,
    type character varying,
    title character varying,
    description character varying,
    formation character varying,
    name character varying,
    firstname character varying,
    ipaddress character varying NOT NULL,
    duration integer NOT NULL,
    genre character varying,
    visible boolean DEFAULT true,
    consultations integer DEFAULT 0,
    timing character varying DEFAULT 'n-1'::character varying,
    userid integer,
    adddocname character varying,
    download boolean DEFAULT true NOT NULL,
    restrictionuds boolean DEFAULT false NOT NULL,
    mediatype integer,
    volume smallint DEFAULT 1 NOT NULL,
    recorddate timestamp without time zone,
    slidesoffset integer
);


ALTER TABLE public.course OWNER TO sqluser;

--
-- TOC entry 145 (class 1259 OID 16415)
-- Dependencies: 144 6
-- Name: course_courseid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE course_courseid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.course_courseid_seq OWNER TO sqluser;

--
-- TOC entry 1904 (class 0 OID 0)
-- Dependencies: 145
-- Name: course_courseid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sqluser
--

ALTER SEQUENCE course_courseid_seq OWNED BY course.courseid;


--
-- TOC entry 146 (class 1259 OID 16417)
-- Dependencies: 6
-- Name: discipline_disciplineid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE discipline_disciplineid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.discipline_disciplineid_seq OWNER TO sqluser;

--
-- TOC entry 147 (class 1259 OID 16419)
-- Dependencies: 1851 6
-- Name: discipline; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE discipline (
    disciplineid integer DEFAULT nextval('discipline_disciplineid_seq'::regclass) NOT NULL,
    codecomp character varying NOT NULL,
    namecomp character varying,
    codedom character varying,
    namedom character varying
);


ALTER TABLE public.discipline OWNER TO sqluser;

--
-- TOC entry 148 (class 1259 OID 16426)
-- Dependencies: 6
-- Name: job; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE job (
    jobid integer NOT NULL,
    courseid integer NOT NULL,
    status character varying NOT NULL,
    mediatype integer NOT NULL,
    coursetype character varying NOT NULL,
    mediapath character varying,
    extension character varying NOT NULL
);


ALTER TABLE public.job OWNER TO sqluser;

--
-- TOC entry 149 (class 1259 OID 16432)
-- Dependencies: 6
-- Name: job_jobid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE job_jobid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.job_jobid_seq OWNER TO sqluser;

--
-- TOC entry 150 (class 1259 OID 16434)
-- Dependencies: 6
-- Name: level_levelid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE level_levelid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.level_levelid_seq OWNER TO sqluser;

--
-- TOC entry 151 (class 1259 OID 16436)
-- Dependencies: 1852 6
-- Name: level; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE level (
    levelid integer DEFAULT nextval('level_levelid_seq'::regclass) NOT NULL,
    code character varying NOT NULL,
    name character varying
);


ALTER TABLE public.level OWNER TO sqluser;

--
-- TOC entry 161 (class 1259 OID 148538)
-- Dependencies: 6
-- Name: log_user_action_logid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE log_user_action_logid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.log_user_action_logid_seq OWNER TO sqluser;

--
-- TOC entry 160 (class 1259 OID 148535)
-- Dependencies: 1857 6
-- Name: log_user_action; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE log_user_action (
    logid integer DEFAULT nextval('log_user_action_logid_seq'::regclass) NOT NULL,
    date timestamp without time zone,
    userid integer,
    courseid integer,
    action character varying NOT NULL,
    url character varying NOT NULL,
    type character varying NOT NULL,
    information character varying
);


ALTER TABLE public.log_user_action OWNER TO sqluser;

--
-- TOC entry 152 (class 1259 OID 16443)
-- Dependencies: 6
-- Name: selection_selectionid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE selection_selectionid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.selection_selectionid_seq OWNER TO sqluser;

--
-- TOC entry 153 (class 1259 OID 16445)
-- Dependencies: 1853 6
-- Name: selection; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE selection (
    "position" integer DEFAULT nextval('selection_selectionid_seq'::regclass) NOT NULL,
    idcourseselection integer,
    formationcollection character varying
);


ALTER TABLE public.selection OWNER TO sqluser;

--
-- TOC entry 154 (class 1259 OID 16452)
-- Dependencies: 6
-- Name: slide; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE slide (
    slideid integer NOT NULL,
    courseid integer NOT NULL,
    slidetime integer NOT NULL
);


ALTER TABLE public.slide OWNER TO sqluser;

--
-- TOC entry 155 (class 1259 OID 16455)
-- Dependencies: 154 6
-- Name: slide_slideid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE slide_slideid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.slide_slideid_seq OWNER TO sqluser;

--
-- TOC entry 1906 (class 0 OID 0)
-- Dependencies: 155
-- Name: slide_slideid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sqluser
--

ALTER SEQUENCE slide_slideid_seq OWNED BY slide.slideid;


--
-- TOC entry 156 (class 1259 OID 16457)
-- Dependencies: 6
-- Name: tag_tagid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE tag_tagid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tag_tagid_seq OWNER TO sqluser;

--
-- TOC entry 157 (class 1259 OID 16459)
-- Dependencies: 1855 6
-- Name: tag; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE tag (
    tagid integer DEFAULT nextval('tag_tagid_seq'::regclass) NOT NULL,
    tag text,
    courseid integer
);


ALTER TABLE public.tag OWNER TO sqluser;

--
-- TOC entry 158 (class 1259 OID 16466)
-- Dependencies: 6
-- Name: user; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE "user" (
    userid integer NOT NULL,
    login character varying NOT NULL,
    email character varying,
    firstname character varying,
    lastname character varying,
    profile character varying,
    establishment character varying,
    password character varying,
    passwordtype character varying,
    type character varying,
    activate boolean,
    etp character varying,
    resetcode character varying,
    resetcodetype character varying,
    dateresetcode timestamp without time zone
);


ALTER TABLE public."user" OWNER TO sqluser;

--
-- TOC entry 159 (class 1259 OID 16472)
-- Dependencies: 6 158
-- Name: user_userid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE user_userid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.user_userid_seq OWNER TO sqluser;

--
-- TOC entry 1908 (class 0 OID 0)
-- Dependencies: 159
-- Name: user_userid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sqluser
--

ALTER SEQUENCE user_userid_seq OWNED BY "user".userid;


--
-- TOC entry 1842 (class 2604 OID 16474)
-- Dependencies: 141 140
-- Name: amphiid; Type: DEFAULT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY amphi ALTER COLUMN amphiid SET DEFAULT nextval('amphi_amphiid_seq'::regclass);


--
-- TOC entry 1843 (class 2604 OID 16475)
-- Dependencies: 143 142
-- Name: buildingid; Type: DEFAULT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY building ALTER COLUMN buildingid SET DEFAULT nextval('building_buildingid_seq'::regclass);


--
-- TOC entry 1850 (class 2604 OID 16476)
-- Dependencies: 145 144
-- Name: courseid; Type: DEFAULT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY course ALTER COLUMN courseid SET DEFAULT nextval('course_courseid_seq'::regclass);


--
-- TOC entry 1854 (class 2604 OID 16477)
-- Dependencies: 155 154
-- Name: slideid; Type: DEFAULT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY slide ALTER COLUMN slideid SET DEFAULT nextval('slide_slideid_seq'::regclass);


--
-- TOC entry 1856 (class 2604 OID 16478)
-- Dependencies: 159 158
-- Name: userid; Type: DEFAULT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY "user" ALTER COLUMN userid SET DEFAULT nextval('user_userid_seq'::regclass);


--
-- TOC entry 1859 (class 2606 OID 16480)
-- Dependencies: 140 140
-- Name: amphi_ipaddress_key; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY amphi
    ADD CONSTRAINT amphi_ipaddress_key UNIQUE (ipaddress);


--
-- TOC entry 1861 (class 2606 OID 16482)
-- Dependencies: 140 140
-- Name: amphi_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY amphi
    ADD CONSTRAINT amphi_pkey PRIMARY KEY (amphiid);


--
-- TOC entry 1863 (class 2606 OID 16484)
-- Dependencies: 142 142
-- Name: building_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY building
    ADD CONSTRAINT building_pkey PRIMARY KEY (buildingid);


--
-- TOC entry 1865 (class 2606 OID 16486)
-- Dependencies: 144 144
-- Name: course_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY course
    ADD CONSTRAINT course_pkey PRIMARY KEY (courseid);


--
-- TOC entry 1867 (class 2606 OID 16488)
-- Dependencies: 147 147
-- Name: discipline_codecomp_key; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY discipline
    ADD CONSTRAINT discipline_codecomp_key UNIQUE (codecomp);


--
-- TOC entry 1869 (class 2606 OID 16490)
-- Dependencies: 147 147
-- Name: discipline_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY discipline
    ADD CONSTRAINT discipline_pkey PRIMARY KEY (disciplineid);


--
-- TOC entry 1871 (class 2606 OID 16492)
-- Dependencies: 148 148
-- Name: job_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY job
    ADD CONSTRAINT job_pkey PRIMARY KEY (jobid);


--
-- TOC entry 1873 (class 2606 OID 16494)
-- Dependencies: 151 151
-- Name: level_code_key; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY level
    ADD CONSTRAINT level_code_key UNIQUE (code);


--
-- TOC entry 1875 (class 2606 OID 16496)
-- Dependencies: 151 151
-- Name: level_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY level
    ADD CONSTRAINT level_pkey PRIMARY KEY (levelid);


--
-- TOC entry 1889 (class 2606 OID 148545)
-- Dependencies: 160 160
-- Name: log_user_action_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY log_user_action
    ADD CONSTRAINT log_user_action_pkey PRIMARY KEY (logid);


--
-- TOC entry 1877 (class 2606 OID 16498)
-- Dependencies: 153 153
-- Name: selection_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY selection
    ADD CONSTRAINT selection_pkey PRIMARY KEY ("position");


--
-- TOC entry 1879 (class 2606 OID 16501)
-- Dependencies: 154 154
-- Name: slide_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY slide
    ADD CONSTRAINT slide_pkey PRIMARY KEY (slideid);


--
-- TOC entry 1881 (class 2606 OID 16503)
-- Dependencies: 157 157
-- Name: tag_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (tagid);


--
-- TOC entry 1883 (class 2606 OID 16505)
-- Dependencies: 158 158
-- Name: user_login_key; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_login_key UNIQUE (login);


--
-- TOC entry 1885 (class 2606 OID 16507)
-- Dependencies: 158 158
-- Name: user_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (userid);


--
-- TOC entry 1886 (class 1259 OID 148561)
-- Dependencies: 160
-- Name: fki_log_user_action_courseid_fkey; Type: INDEX; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE INDEX fki_log_user_action_courseid_fkey ON log_user_action USING btree (courseid);


--
-- TOC entry 1887 (class 1259 OID 148555)
-- Dependencies: 160
-- Name: fki_log_user_action_userid_fkey; Type: INDEX; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE INDEX fki_log_user_action_userid_fkey ON log_user_action USING btree (userid);


--
-- TOC entry 1890 (class 2606 OID 16508)
-- Dependencies: 140 142 1862
-- Name: amphi_buildingid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY amphi
    ADD CONSTRAINT amphi_buildingid_fkey FOREIGN KEY (buildingid) REFERENCES building(buildingid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1891 (class 2606 OID 16513)
-- Dependencies: 144 1884 158
-- Name: course_userid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY course
    ADD CONSTRAINT course_userid_fkey FOREIGN KEY (userid) REFERENCES "user"(userid);


--
-- TOC entry 1892 (class 2606 OID 16518)
-- Dependencies: 144 1864 154
-- Name: slide_courseid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY slide
    ADD CONSTRAINT slide_courseid_fkey FOREIGN KEY (courseid) REFERENCES course(courseid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1893 (class 2606 OID 16523)
-- Dependencies: 144 157 1864
-- Name: tag_courseid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY tag
    ADD CONSTRAINT tag_courseid_fkey FOREIGN KEY (courseid) REFERENCES course(courseid);


--
-- TOC entry 1898 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- TOC entry 1899 (class 0 OID 0)
-- Dependencies: 140
-- Name: amphi; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE amphi FROM PUBLIC;
REVOKE ALL ON TABLE amphi FROM sqluser;
GRANT ALL ON TABLE amphi TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE amphi TO PUBLIC;


--
-- TOC entry 1901 (class 0 OID 0)
-- Dependencies: 142
-- Name: building; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE building FROM PUBLIC;
REVOKE ALL ON TABLE building FROM sqluser;
GRANT ALL ON TABLE building TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE building TO PUBLIC;


--
-- TOC entry 1903 (class 0 OID 0)
-- Dependencies: 144
-- Name: course; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE course FROM PUBLIC;
REVOKE ALL ON TABLE course FROM sqluser;
GRANT ALL ON TABLE course TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE course TO PUBLIC;


--
-- TOC entry 1905 (class 0 OID 0)
-- Dependencies: 154
-- Name: slide; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE slide FROM PUBLIC;
REVOKE ALL ON TABLE slide FROM sqluser;
GRANT ALL ON TABLE slide TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE slide TO PUBLIC;


--
-- TOC entry 1907 (class 0 OID 0)
-- Dependencies: 158
-- Name: user; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE "user" FROM PUBLIC;
REVOKE ALL ON TABLE "user" FROM sqluser;
GRANT ALL ON TABLE "user" TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE "user" TO PUBLIC;


-- Completed on 2013-10-09 11:48:40 CEST

--
-- PostgreSQL database dump complete
--

-----------------------------------------------------------------------

-- INSERT DATA

--
-- TOC entry 1841 (class 0 OID 16549)
-- Dependencies: 1515
-- Data for Name: discipline; Type: TABLE DATA; Schema: public; Owner: sqluser
--

INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (37, 'SP', 'Institut d''Etudes Politiques', 'DSP', 'Droit et Sciences politiques');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (6, 'DA', 'Faculté de Droit', 'DSP', 'Droit et Sciences politiques');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (26, 'PI', 'Centre d''Etudes Internationales de la Propriété Intellectuel', 'DSP', 'Droit et Sciences politiques');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (17, 'GO', 'Faculté de Geographie et d''Aménagement', 'EDD', 'Environnement et Développement Durable');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (34, 'SC', 'IUT de Schiltigheim', 'SIT', 'Sciences de l''Ingénieur et Technologie');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (40, 'TS', 'IUT d Illkirch', 'SIT', 'Sciences de l''Ingénieur et Technologie');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (15, 'HA', 'IUT de Haguenau', 'SIT', 'Sciences de l''Ingénieur et Technologie');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (14, 'FM', 'Institut Universitaire de Formation des Maitres', 'O', 'Autres');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (33, 'RI', 'Institut Traducteurs,Interprètes & Relations Internationales', 'SHSLA', 'Sciences humaines et sociales, Langues et Art');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (35, 'SH', 'UFR des Sciences Historiques', 'SHSLA', 'Sciences humaines et sociales, Langues et Art');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (36, 'SO', 'UFR Sciences Sociales, Pratiques Sociales & Développement', 'SHSLA', 'Sciences humaines et sociales, Langues et Art');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (38, 'TC', 'Institut de Théologie Catholique', 'SHSLA', 'Sciences humaines et sociales, Langues et Art');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (39, 'TP', 'Institut de Théologie Protestante', 'SHSLA', 'Sciences humaines et sociales, Langues et Art');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (3, 'AR', 'UFR des Arts', 'SHSLA', 'Sciences humaines et sociales, Langues et Art');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (8, 'ED', 'UFR Sciences de l''Education', 'SHSLA', 'Sciences humaines et sociales, Langues et Art');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (9, 'EF', 'Institut International d''Etudes Françaises', 'SHSLA', 'Sciences humaines et sociales, Langues et Art');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (18, 'JR', 'Centre Universitaire d''Enseignement du Journalisme', 'SHSLA', 'Sciences humaines et sociales, Langues et Art');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (19, 'LA', 'UFR des Langues & Sciences Humaines Appliquées', 'SHSLA', 'Sciences humaines et sociales, Langues et Art');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (20, 'LT', 'UFR des Lettres', 'SHSLA', 'Sciences humaines et sociales, Langues et Art');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (21, 'LV', 'UFR des Langues Vivantes', 'SHSLA', 'Sciences humaines et sociales, Langues et Art');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (24, 'MU', 'Centre de Formation de Musiciens Intervenants', 'SHSLA', 'Sciences humaines et sociales, Langues et Art');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (27, 'PL', 'UFR Philosophie, Linguistique, Sciences de l''Education', 'SHSLA', 'Sciences humaines et sociales, Langues et Art');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (28, 'PS', 'Faculté de Psychologie', 'SHSLA', 'Sciences humaines et sociales, Langues et Art');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (32, 'OD', 'Faculté de Chirurgie Dentaire', 'SS', 'Santé et Sport');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (42, '00', 'Autres', 'O', 'Autres');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (2, 'AP', 'UFR Sc. & Techniques des Activites Physiques & Sportives', 'SS', 'Santé et Sport');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (22, 'MD', 'Faculté de Médecine ', 'SS', 'Santé et Sport');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (43, '01', 'Jardin des Sciences', 'O', 'Autres');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (44, '02', 'Logcri', 'O', 'Autres');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (25, 'PH', 'Faculté de Pharmacie', 'SS', 'Santé et Sport');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (1, 'AG', 'Institut de Préparation à l''Administration Génerale', 'EG', 'Economie et Gestion');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (10, 'EG', 'Faculté de Sciences Economiques & de Gestion', 'EG', 'Economie et Gestion');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (11, 'EM', 'Ecole de Management de Strasbourg', 'EG', 'Economie et Gestion');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (13, 'ET', 'Département des Etudes Territoriales', 'EG', 'Economie et Gestion');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (16, 'HE', 'Institut des Hautes Etudes Européennes', 'EG', 'Economie et Gestion');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (30, 'OT', 'Ecole & Observatoire des Sciences de la Terre', 'SF', 'Sciences fondamentales');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (31, 'OB', 'Observatoire Astronomique', 'SF', 'Sciences fondamentales');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (41, 'VI', 'Faculte des Sciences de la Vie', 'SF', 'Sciences fondamentales');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (4, 'CH', 'Faculté de Chimie', 'SF', 'Sciences fondamentales');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (5, 'CP', 'Ecole Européenne de Chimie, Polymères & Matériaux', 'SF', 'Sciences fondamentales');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (7, 'EB', 'Ecole Supérieure de Biotechnologie de Strasbourg', 'SF', 'Sciences fondamentales');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (12, 'EP', 'Ecole Nationale Supérieure de Physique', 'SF', 'Sciences fondamentales');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (23, 'MI', 'UFR de Mathématique & Informatique ', 'SF', 'Sciences fondamentales');
INSERT INTO discipline (disciplineid, codecomp, namecomp, codedom, namedom) VALUES (29, 'PY', 'UFR de Physique & Ingénierie', 'SF', 'Sciences fondamentales');


--
-- TOC entry 1842 (class 0 OID 16562)
-- Dependencies: 1517
-- Data for Name: level; Type: TABLE DATA; Schema: public; Owner: sqluser
--

INSERT INTO level (levelid, code, name) VALUES (3, '31', 'Licence 1ère année');
INSERT INTO level (levelid, code, name) VALUES (4, '32', 'Licence 2ème année');
INSERT INTO level (levelid, code, name) VALUES (5, '33', 'Licence 3ème année');
INSERT INTO level (levelid, code, name) VALUES (6, '51', 'Master 1ère année');
INSERT INTO level (levelid, code, name) VALUES (7, '52', 'Master 2ème année');
INSERT INTO level (levelid, code, name) VALUES (8, '6X', 'Doctorat');
INSERT INTO level (levelid, code, name) VALUES (1, 'O0', 'Autres');
INSERT INTO level (levelid, code, name) VALUES (2, 'C0', 'Conférence');

SELECT setval('discipline_disciplineid_seq',44);
SELECT setval('level_levelid_seq',8);