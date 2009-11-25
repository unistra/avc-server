--
-- PostgreSQL database dump
--

-- Started on 2009-11-17 16:21:35 CET

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1493 (class 1259 OID 16408)
-- Dependencies: 1773 3
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
-- TOC entry 1491 (class 1259 OID 16397)
-- Dependencies: 3
-- Name: building; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE building (
    buildingid integer NOT NULL,
    name character varying NOT NULL,
    imagefile character varying NOT NULL
);


ALTER TABLE public.building OWNER TO sqluser;

--
-- TOC entry 1497 (class 1259 OID 16437)
-- Dependencies: 1776 1777 1778 1779 1780 3
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
    mediafolder character varying,
    userid integer,
    adddocname character varying,
    download boolean DEFAULT true NOT NULL,
    restrictionuds boolean DEFAULT false NOT NULL,
    mediatype integer
);


ALTER TABLE public.course OWNER TO sqluser;

--
-- TOC entry 1504 (class 1259 OID 16512)
-- Dependencies: 3
-- Name: selection_selectionid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE selection_selectionid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.selection_selectionid_seq OWNER TO sqluser;

--
-- TOC entry 1503 (class 1259 OID 16504)
-- Dependencies: 1783 3
-- Name: selection; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE selection (
    "position" integer DEFAULT nextval('selection_selectionid_seq'::regclass) NOT NULL,
    idcourseselection integer,
    formationcollection character varying
);


ALTER TABLE public.selection OWNER TO sqluser;

--
-- TOC entry 1499 (class 1259 OID 16456)
-- Dependencies: 3
-- Name: slide; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE slide (
    slideid integer NOT NULL,
    courseid integer NOT NULL,
    slidetime integer NOT NULL
);


ALTER TABLE public.slide OWNER TO sqluser;

--
-- TOC entry 1502 (class 1259 OID 16495)
-- Dependencies: 3
-- Name: tag_tagid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE tag_tagid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tag_tagid_seq OWNER TO sqluser;

--
-- TOC entry 1501 (class 1259 OID 16482)
-- Dependencies: 1782 3
-- Name: tag; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE tag (
    tagid integer DEFAULT nextval('tag_tagid_seq'::regclass) NOT NULL,
    tag text,
    courseid integer
);


ALTER TABLE public.tag OWNER TO sqluser;

--
-- TOC entry 1500 (class 1259 OID 16467)
-- Dependencies: 3
-- Name: univr; Type: TABLE; Schema: public; Owner: sqluser; Tablespace: 
--

CREATE TABLE univr (
    courseid integer NOT NULL,
    uid integer NOT NULL,
    groupcode integer NOT NULL,
    establishment text
);


ALTER TABLE public.univr OWNER TO sqluser;

--
-- TOC entry 1495 (class 1259 OID 16424)
-- Dependencies: 3
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
    activate boolean
);


ALTER TABLE public."user" OWNER TO sqluser;

--
-- TOC entry 1492 (class 1259 OID 16406)
-- Dependencies: 3 1493
-- Name: amphi_amphiid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE amphi_amphiid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.amphi_amphiid_seq OWNER TO sqluser;

--
-- TOC entry 1820 (class 0 OID 0)
-- Dependencies: 1492
-- Name: amphi_amphiid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sqluser
--

ALTER SEQUENCE amphi_amphiid_seq OWNED BY amphi.amphiid;


--
-- TOC entry 1490 (class 1259 OID 16395)
-- Dependencies: 1491 3
-- Name: building_buildingid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE building_buildingid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.building_buildingid_seq OWNER TO sqluser;

--
-- TOC entry 1821 (class 0 OID 0)
-- Dependencies: 1490
-- Name: building_buildingid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sqluser
--

ALTER SEQUENCE building_buildingid_seq OWNED BY building.buildingid;


--
-- TOC entry 1496 (class 1259 OID 16435)
-- Dependencies: 3 1497
-- Name: course_courseid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE course_courseid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.course_courseid_seq OWNER TO sqluser;

--
-- TOC entry 1822 (class 0 OID 0)
-- Dependencies: 1496
-- Name: course_courseid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sqluser
--

ALTER SEQUENCE course_courseid_seq OWNED BY course.courseid;


--
-- TOC entry 1498 (class 1259 OID 16454)
-- Dependencies: 1499 3
-- Name: slide_slideid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE slide_slideid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.slide_slideid_seq OWNER TO sqluser;

--
-- TOC entry 1823 (class 0 OID 0)
-- Dependencies: 1498
-- Name: slide_slideid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sqluser
--

ALTER SEQUENCE slide_slideid_seq OWNED BY slide.slideid;


--
-- TOC entry 1494 (class 1259 OID 16422)
-- Dependencies: 3 1495
-- Name: user_userid_seq; Type: SEQUENCE; Schema: public; Owner: sqluser
--

CREATE SEQUENCE user_userid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.user_userid_seq OWNER TO sqluser;

--
-- TOC entry 1824 (class 0 OID 0)
-- Dependencies: 1494
-- Name: user_userid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sqluser
--

ALTER SEQUENCE user_userid_seq OWNED BY "user".userid;


--
-- TOC entry 1772 (class 2604 OID 16411)
-- Dependencies: 1492 1493 1493
-- Name: amphiid; Type: DEFAULT; Schema: public; Owner: sqluser
--

ALTER TABLE amphi ALTER COLUMN amphiid SET DEFAULT nextval('amphi_amphiid_seq'::regclass);


--
-- TOC entry 1771 (class 2604 OID 16400)
-- Dependencies: 1490 1491 1491
-- Name: buildingid; Type: DEFAULT; Schema: public; Owner: sqluser
--

ALTER TABLE building ALTER COLUMN buildingid SET DEFAULT nextval('building_buildingid_seq'::regclass);


--
-- TOC entry 1775 (class 2604 OID 16440)
-- Dependencies: 1496 1497 1497
-- Name: courseid; Type: DEFAULT; Schema: public; Owner: sqluser
--

ALTER TABLE course ALTER COLUMN courseid SET DEFAULT nextval('course_courseid_seq'::regclass);


--
-- TOC entry 1781 (class 2604 OID 16459)
-- Dependencies: 1499 1498 1499
-- Name: slideid; Type: DEFAULT; Schema: public; Owner: sqluser
--

ALTER TABLE slide ALTER COLUMN slideid SET DEFAULT nextval('slide_slideid_seq'::regclass);


--
-- TOC entry 1774 (class 2604 OID 16427)
-- Dependencies: 1495 1494 1495
-- Name: userid; Type: DEFAULT; Schema: public; Owner: sqluser
--

ALTER TABLE "user" ALTER COLUMN userid SET DEFAULT nextval('user_userid_seq'::regclass);


--
-- TOC entry 1787 (class 2606 OID 16501)
-- Dependencies: 1493 1493
-- Name: amphi_ipaddress_key; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY amphi
    ADD CONSTRAINT amphi_ipaddress_key UNIQUE (ipaddress);


--
-- TOC entry 1789 (class 2606 OID 16416)
-- Dependencies: 1493 1493
-- Name: amphi_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY amphi
    ADD CONSTRAINT amphi_pkey PRIMARY KEY (amphiid);


--
-- TOC entry 1785 (class 2606 OID 16405)
-- Dependencies: 1491 1491
-- Name: building_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY building
    ADD CONSTRAINT building_pkey PRIMARY KEY (buildingid);


--
-- TOC entry 1795 (class 2606 OID 16448)
-- Dependencies: 1497 1497
-- Name: course_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY course
    ADD CONSTRAINT course_pkey PRIMARY KEY (courseid);


--
-- TOC entry 1803 (class 2606 OID 16511)
-- Dependencies: 1503 1503
-- Name: selection_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY selection
    ADD CONSTRAINT selection_pkey PRIMARY KEY ("position");


--
-- TOC entry 1797 (class 2606 OID 16461)
-- Dependencies: 1499 1499
-- Name: slide_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY slide
    ADD CONSTRAINT slide_pkey PRIMARY KEY (slideid);


--
-- TOC entry 1801 (class 2606 OID 16489)
-- Dependencies: 1501 1501
-- Name: tag_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (tagid);


--
-- TOC entry 1799 (class 2606 OID 16474)
-- Dependencies: 1500 1500
-- Name: univr_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY univr
    ADD CONSTRAINT univr_pkey PRIMARY KEY (courseid);


--
-- TOC entry 1791 (class 2606 OID 16481)
-- Dependencies: 1495 1495
-- Name: user_login_key; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_login_key UNIQUE (login);


--
-- TOC entry 1793 (class 2606 OID 16432)
-- Dependencies: 1495 1495
-- Name: user_pkey; Type: CONSTRAINT; Schema: public; Owner: sqluser; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (userid);


--
-- TOC entry 1804 (class 2606 OID 16417)
-- Dependencies: 1491 1493 1784
-- Name: amphi_buildingid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY amphi
    ADD CONSTRAINT amphi_buildingid_fkey FOREIGN KEY (buildingid) REFERENCES building(buildingid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1805 (class 2606 OID 16449)
-- Dependencies: 1495 1792 1497
-- Name: course_userid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY course
    ADD CONSTRAINT course_userid_fkey FOREIGN KEY (userid) REFERENCES "user"(userid);


--
-- TOC entry 1806 (class 2606 OID 16462)
-- Dependencies: 1794 1499 1497
-- Name: slide_courseid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY slide
    ADD CONSTRAINT slide_courseid_fkey FOREIGN KEY (courseid) REFERENCES course(courseid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1808 (class 2606 OID 16490)
-- Dependencies: 1497 1794 1501
-- Name: tag_courseid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY tag
    ADD CONSTRAINT tag_courseid_fkey FOREIGN KEY (courseid) REFERENCES course(courseid);


--
-- TOC entry 1807 (class 2606 OID 16475)
-- Dependencies: 1500 1497 1794
-- Name: univr_courseid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sqluser
--

ALTER TABLE ONLY univr
    ADD CONSTRAINT univr_courseid_fkey FOREIGN KEY (courseid) REFERENCES course(courseid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1813 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- TOC entry 1814 (class 0 OID 0)
-- Dependencies: 1493
-- Name: amphi; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE amphi FROM PUBLIC;
REVOKE ALL ON TABLE amphi FROM sqluser;
GRANT ALL ON TABLE amphi TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE amphi TO PUBLIC;


--
-- TOC entry 1815 (class 0 OID 0)
-- Dependencies: 1491
-- Name: building; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE building FROM PUBLIC;
REVOKE ALL ON TABLE building FROM sqluser;
GRANT ALL ON TABLE building TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE building TO PUBLIC;


--
-- TOC entry 1816 (class 0 OID 0)
-- Dependencies: 1497
-- Name: course; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE course FROM PUBLIC;
REVOKE ALL ON TABLE course FROM sqluser;
GRANT ALL ON TABLE course TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE course TO PUBLIC;


--
-- TOC entry 1817 (class 0 OID 0)
-- Dependencies: 1499
-- Name: slide; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE slide FROM PUBLIC;
REVOKE ALL ON TABLE slide FROM sqluser;
GRANT ALL ON TABLE slide TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE slide TO PUBLIC;


--
-- TOC entry 1818 (class 0 OID 0)
-- Dependencies: 1500
-- Name: univr; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE univr FROM PUBLIC;
REVOKE ALL ON TABLE univr FROM sqluser;
GRANT ALL ON TABLE univr TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE univr TO PUBLIC;


--
-- TOC entry 1819 (class 0 OID 0)
-- Dependencies: 1495
-- Name: user; Type: ACL; Schema: public; Owner: sqluser
--

REVOKE ALL ON TABLE "user" FROM PUBLIC;
REVOKE ALL ON TABLE "user" FROM sqluser;
GRANT ALL ON TABLE "user" TO sqluser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE "user" TO PUBLIC;


-- Completed on 2009-11-17 16:21:35 CET

--
-- PostgreSQL database dump complete
--

