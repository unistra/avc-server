--
-- PostgreSQL database dump
--

-- Started on 2010-05-27 09:18:26 CEST

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1499 (class 1259 OID 16386)
-- Dependencies: 1785 6
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
-- TOC entry 1508 (class 1259 OID 16443)
-- Dependencies: 1499 6
-- Name: amphi_amphiid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE amphi_amphiid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.amphi_amphiid_seq OWNER TO sqluser;

--
-- TOC entry 1839 (class 0 OID 0)
-- Dependencies: 1508
-- Name: amphi_amphiid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sqluser
--

ALTER SEQUENCE amphi_amphiid_seq OWNED BY amphi.amphiid;


--
-- TOC entry 1500 (class 1259 OID 16393)
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
-- TOC entry 1509 (class 1259 OID 16445)
-- Dependencies: 1500 6
-- Name: building_buildingid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE building_buildingid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.building_buildingid_seq OWNER TO sqluser;

--
-- TOC entry 1841 (class 0 OID 0)
-- Dependencies: 1509
-- Name: building_buildingid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sqluser
--

ALTER SEQUENCE building_buildingid_seq OWNED BY building.buildingid;


--
-- TOC entry 1501 (class 1259 OID 16399)
-- Dependencies: 1788 1789 1790 1791 1792 1794 6
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
-- TOC entry 1510 (class 1259 OID 16447)
-- Dependencies: 6 1501
-- Name: course_courseid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE course_courseid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.course_courseid_seq OWNER TO sqluser;

--
-- TOC entry 1843 (class 0 OID 0)
-- Dependencies: 1510
-- Name: course_courseid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sqluser
--

ALTER SEQUENCE course_courseid_seq OWNED BY course.courseid;


--
-- TOC entry 1516 (class 1259 OID 16559)
-- Dependencies: 6
-- Name: discipline_disciplineid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE discipline_disciplineid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.discipline_disciplineid_seq OWNER TO sqluser;

--
-- TOC entry 1515 (class 1259 OID 16549)
-- Dependencies: 1799 6
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
-- TOC entry 1513 (class 1259 OID 16539)
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
-- TOC entry 1514 (class 1259 OID 16547)
-- Dependencies: 6
-- Name: job_jobid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE job_jobid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.job_jobid_seq OWNER TO sqluser;

--
-- TOC entry 1518 (class 1259 OID 16570)
-- Dependencies: 6
-- Name: level_levelid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE level_levelid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.level_levelid_seq OWNER TO sqluser;

--
-- TOC entry 1517 (class 1259 OID 16562)
-- Dependencies: 1800 6
-- Name: level; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE level (
    levelid integer DEFAULT nextval('level_levelid_seq'::regclass) NOT NULL,
    code character varying NOT NULL,
    name character varying
);


ALTER TABLE public.level OWNER TO sqluser;

--
-- TOC entry 1502 (class 1259 OID 16410)
-- Dependencies: 6
-- Name: selection_selectionid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE selection_selectionid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.selection_selectionid_seq OWNER TO sqluser;

--
-- TOC entry 1503 (class 1259 OID 16412)
-- Dependencies: 1795 6
-- Name: selection; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE selection (
    "position" integer DEFAULT nextval('selection_selectionid_seq'::regclass) NOT NULL,
    idcourseselection integer,
    formationcollection character varying
);


ALTER TABLE public.selection OWNER TO sqluser;

--
-- TOC entry 1504 (class 1259 OID 16419)
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
-- TOC entry 1511 (class 1259 OID 16449)
-- Dependencies: 1504 6
-- Name: slide_slideid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE slide_slideid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.slide_slideid_seq OWNER TO sqluser;

--
-- TOC entry 1845 (class 0 OID 0)
-- Dependencies: 1511
-- Name: slide_slideid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sqluser
--

ALTER SEQUENCE slide_slideid_seq OWNED BY slide.slideid;


--
-- TOC entry 1505 (class 1259 OID 16422)
-- Dependencies: 6
-- Name: tag_tagid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE tag_tagid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tag_tagid_seq OWNER TO sqluser;

--
-- TOC entry 1506 (class 1259 OID 16424)
-- Dependencies: 1797 6
-- Name: tag; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE tag (
    tagid integer DEFAULT nextval('tag_tagid_seq'::regclass) NOT NULL,
    tag text,
    courseid integer
);


ALTER TABLE public.tag OWNER TO sqluser;

--
-- TOC entry 1507 (class 1259 OID 16437)
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
-- TOC entry 1512 (class 1259 OID 16451)
-- Dependencies: 1507 6
-- Name: user_userid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE user_userid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.user_userid_seq OWNER TO sqluser;

--
-- TOC entry 1847 (class 0 OID 0)
-- Dependencies: 1512
-- Name: user_userid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sqluser
--

ALTER SEQUENCE user_userid_seq OWNED BY "user".userid;


--
-- TOC entry 1786 (class 2604 OID 16453)
-- Dependencies: 1508 1499
-- Name: amphiid; Type: DEFAULT; Schema: public; Owner: sqluser
--

ALTER TABLE amphi ALTER COLUMN amphiid SET DEFAULT nextval('amphi_amphiid_seq'::regclass);


--
-- TOC entry 1787 (class 2604 OID 16454)
-- Dependencies: 1509 1500
-- Name: buildingid; Type: DEFAULT; Schema: public; Owner: sqluser
--

ALTER TABLE building ALTER COLUMN buildingid SET DEFAULT nextval('building_buildingid_seq'::regclass);


--
-- TOC entry 1793 (class 2604 OID 16455)
-- Dependencies: 1510 1501
-- Name: courseid; Type: DEFAULT; Schema: public; Owner: sqluser
--

ALTER TABLE course ALTER COLUMN courseid SET DEFAULT nextval('course_courseid_seq'::regclass);


--
-- TOC entry 1796 (class 2604 OID 16456)
-- Dependencies: 1511 1504
-- Name: slideid; Type: DEFAULT; Schema: public; Owner: sqluser
--

ALTER TABLE slide ALTER COLUMN slideid SET DEFAULT nextval('slide_slideid_seq'::regclass);


--
-- TOC entry 1798 (class 2604 OID 16457)
-- Dependencies: 1512 1507
-- Name: userid; Type: DEFAULT; Schema: public; Owner: sqluser
--

ALTER TABLE "user" ALTER COLUMN userid SET DEFAULT nextval('user_userid_seq'::regclass);


--
-- TOC entry 1802 (class 2606 OID 16459)
-- Dependencies: 1499 1499
-- Name: amphi_ipaddress_key; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY amphi
    ADD CONSTRAINT amphi_ipaddress_key UNIQUE (ipaddress);


--
-- TOC entry 1804 (class 2606 OID 16461)
-- Dependencies: 1499 1499
-- Name: amphi_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY amphi
    ADD CONSTRAINT amphi_pkey PRIMARY KEY (amphiid);


--
-- TOC entry 1806 (class 2606 OID 16463)
-- Dependencies: 1500 1500
-- Name: building_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY building
    ADD CONSTRAINT building_pkey PRIMARY KEY (buildingid);


--
-- TOC entry 1808 (class 2606 OID 16465)
-- Dependencies: 1501 1501
-- Name: course_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY course
    ADD CONSTRAINT course_pkey PRIMARY KEY (courseid);


--
-- TOC entry 1822 (class 2606 OID 16574)
-- Dependencies: 1515 1515
-- Name: discipline_codecomp_key; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY discipline
    ADD CONSTRAINT discipline_codecomp_key UNIQUE (codecomp);


--
-- TOC entry 1824 (class 2606 OID 16553)
-- Dependencies: 1515 1515
-- Name: discipline_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY discipline
    ADD CONSTRAINT discipline_pkey PRIMARY KEY (disciplineid);


--
-- TOC entry 1820 (class 2606 OID 16546)
-- Dependencies: 1513 1513
-- Name: job_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY job
    ADD CONSTRAINT job_pkey PRIMARY KEY (jobid);


--
-- TOC entry 1826 (class 2606 OID 16576)
-- Dependencies: 1517 1517
-- Name: level_code_key; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY level
    ADD CONSTRAINT level_code_key UNIQUE (code);


--
-- TOC entry 1828 (class 2606 OID 16569)
-- Dependencies: 1517 1517
-- Name: level_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY level
    ADD CONSTRAINT level_pkey PRIMARY KEY (levelid);


--
-- TOC entry 1810 (class 2606 OID 16467)
-- Dependencies: 1503 1503
-- Name: selection_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY selection
    ADD CONSTRAINT selection_pkey PRIMARY KEY ("position");


--
-- TOC entry 1812 (class 2606 OID 16469)
-- Dependencies: 1504 1504
-- Name: slide_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY slide
    ADD CONSTRAINT slide_pkey PRIMARY KEY (slideid);


--
-- TOC entry 1814 (class 2606 OID 16471)
-- Dependencies: 1506 1506
-- Name: tag_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (tagid);


--
-- TOC entry 1816 (class 2606 OID 16475)
-- Dependencies: 1507 1507
-- Name: user_login_key; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_login_key UNIQUE (login);


--
-- TOC entry 1818 (class 2606 OID 16477)
-- Dependencies: 1507 1507
-- Name: user_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (userid);


--
-- TOC entry 1829 (class 2606 OID 16478)
-- Dependencies: 1499 1805 1500
-- Name: amphi_buildingid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY amphi
    ADD CONSTRAINT amphi_buildingid_fkey FOREIGN KEY (buildingid) REFERENCES building(buildingid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1830 (class 2606 OID 16483)
-- Dependencies: 1501 1507 1817
-- Name: course_userid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY course
    ADD CONSTRAINT course_userid_fkey FOREIGN KEY (userid) REFERENCES "user"(userid);


--
-- TOC entry 1831 (class 2606 OID 16488)
-- Dependencies: 1504 1501 1807
-- Name: slide_courseid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY slide
    ADD CONSTRAINT slide_courseid_fkey FOREIGN KEY (courseid) REFERENCES course(courseid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1832 (class 2606 OID 16493)
-- Dependencies: 1506 1501 1807
-- Name: tag_courseid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY tag
    ADD CONSTRAINT tag_courseid_fkey FOREIGN KEY (courseid) REFERENCES course(courseid);


--
-- TOC entry 1837 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- TOC entry 1838 (class 0 OID 0)
-- Dependencies: 1499
-- Name: amphi; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE amphi FROM PUBLIC;
REVOKE ALL ON TABLE amphi FROM sqluser;
GRANT ALL ON TABLE amphi TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE amphi TO PUBLIC;


--
-- TOC entry 1840 (class 0 OID 0)
-- Dependencies: 1500
-- Name: building; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE building FROM PUBLIC;
REVOKE ALL ON TABLE building FROM sqluser;
GRANT ALL ON TABLE building TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE building TO PUBLIC;


--
-- TOC entry 1842 (class 0 OID 0)
-- Dependencies: 1501
-- Name: course; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE course FROM PUBLIC;
REVOKE ALL ON TABLE course FROM sqluser;
GRANT ALL ON TABLE course TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE course TO PUBLIC;


--
-- TOC entry 1844 (class 0 OID 0)
-- Dependencies: 1504
-- Name: slide; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE slide FROM PUBLIC;
REVOKE ALL ON TABLE slide FROM sqluser;
GRANT ALL ON TABLE slide TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE slide TO PUBLIC;


--
-- TOC entry 1846 (class 0 OID 0)
-- Dependencies: 1507
-- Name: user; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE "user" FROM PUBLIC;
REVOKE ALL ON TABLE "user" FROM sqluser;
GRANT ALL ON TABLE "user" TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE "user" TO PUBLIC;


-- Completed on 2010-05-27 09:18:27 CEST

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