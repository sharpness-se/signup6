--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.11
-- Dumped by pg_dump version 13.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: signup4
--

-- CREATE SCHEMA public;


-- ALTER SCHEMA public OWNER TO signup4;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: signup4
--

-- COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

--
-- Name: events; Type: TABLE; Schema: public; Owner: signup4
--

CREATE TABLE public.events (
                               id integer NOT NULL,
                               name character varying(127) NOT NULL,
                               description character varying(10240) DEFAULT ''::character varying,
                               venue character varying(127) DEFAULT ''::character varying,
                               start_time timestamp without time zone NOT NULL,
                               end_time timestamp without time zone NOT NULL,
                               groupx integer NOT NULL,
                               last_signup_date timestamp without time zone NOT NULL,
                               allow_extra_friends boolean DEFAULT false NOT NULL,
                               event_status character varying(32) DEFAULT 'Created'::character varying NOT NULL,
                               max_participants integer,
                               cancellation_reason character varying(512)
);


-- ALTER TABLE public.events OWNER TO signup4;

--
-- Name: events_id_seq; Type: SEQUENCE; Schema: public; Owner: signup4
--

CREATE SEQUENCE public.events_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


-- ALTER TABLE public.events_id_seq OWNER TO signup4;

--
-- Name: events_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: signup4
--

ALTER SEQUENCE public.events_id_seq OWNED BY public.events.id;


--
-- Name: groups; Type: TABLE; Schema: public; Owner: signup4
--

CREATE TABLE public.groups (
                               id integer NOT NULL,
                               name character varying(127) NOT NULL,
                               description character varying(127) DEFAULT ''::character varying,
                               mail_from character varying(127) DEFAULT ''::character varying,
                               mail_subject_prefix character varying(127) DEFAULT ''::character varying NOT NULL
);


-- ALTER TABLE public.groups OWNER TO signup4;

--
-- Name: groups_id_seq; Type: SEQUENCE; Schema: public; Owner: signup4
--

CREATE SEQUENCE public.groups_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


-- ALTER TABLE public.groups_id_seq OWNER TO signup4;

--
-- Name: groups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: signup4
--

ALTER SEQUENCE public.groups_id_seq OWNED BY public.groups.id;


--
-- Name: log_entries; Type: TABLE; Schema: public; Owner: signup4
--

CREATE TABLE public.log_entries (
                                    id integer NOT NULL,
                                    event integer,
                                    message character varying(512) NOT NULL,
                                    whenx timestamp without time zone NOT NULL
);


-- ALTER TABLE public.log_entries OWNER TO signup4;

--
-- Name: log_entries_id_seq; Type: SEQUENCE; Schema: public; Owner: signup4
--

CREATE SEQUENCE public.log_entries_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


-- ALTER TABLE public.log_entries_id_seq OWNER TO signup4;

--
-- Name: log_entries_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: signup4
--

ALTER SEQUENCE public.log_entries_id_seq OWNED BY public.log_entries.id;


--
-- Name: memberships; Type: TABLE; Schema: public; Owner: signup4
--

CREATE TABLE public.memberships (
                                    id integer NOT NULL,
                                    userx integer NOT NULL,
                                    groupx integer NOT NULL
);


-- ALTER TABLE public.memberships OWNER TO signup4;

--
-- Name: memberships_id_seq; Type: SEQUENCE; Schema: public; Owner: signup4
--

CREATE SEQUENCE public.memberships_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


-- ALTER TABLE public.memberships_id_seq OWNER TO signup4;

--
-- Name: memberships_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: signup4
--

ALTER SEQUENCE public.memberships_id_seq OWNED BY public.memberships.id;


--
-- Name: participations; Type: TABLE; Schema: public; Owner: signup4
--

CREATE TABLE public.participations (
                                       id integer NOT NULL,
                                       status character varying(20) NOT NULL,
                                       comment character varying(127) DEFAULT ''::character varying,
                                       userx integer,
                                       event integer,
                                       number_of_participants integer DEFAULT 1 NOT NULL,
                                       signup_time timestamp without time zone
);


-- ALTER TABLE public.participations OWNER TO signup4;

--
-- Name: participations_id_seq; Type: SEQUENCE; Schema: public; Owner: signup4
--

CREATE SEQUENCE public.participations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


-- ALTER TABLE public.participations_id_seq OWNER TO signup4;

--
-- Name: participations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: signup4
--

ALTER SEQUENCE public.participations_id_seq OWNED BY public.participations.id;


--
-- Name: play_evolutions; Type: TABLE; Schema: public; Owner: signup4
--

CREATE TABLE public.play_evolutions (
                                        id integer NOT NULL,
                                        hash character varying(255) NOT NULL,
                                        applied_at timestamp without time zone NOT NULL,
                                        apply_script text,
                                        revert_script text,
                                        state character varying(255),
                                        last_problem text
);


-- ALTER TABLE public.play_evolutions OWNER TO signup4;

--
-- Name: reminders; Type: TABLE; Schema: public; Owner: signup4
--

CREATE TABLE public.reminders (
                                  id integer NOT NULL,
                                  event integer,
                                  datex date NOT NULL
);


-- ALTER TABLE public.reminders OWNER TO signup4;

--
-- Name: reminders_id_seq; Type: SEQUENCE; Schema: public; Owner: signup4
--

CREATE SEQUENCE public.reminders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


-- ALTER TABLE public.reminders_id_seq OWNER TO signup4;

--
-- Name: reminders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: signup4
--

ALTER SEQUENCE public.reminders_id_seq OWNED BY public.reminders.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: signup4
--

CREATE TABLE public.users (
                              id integer NOT NULL,
                              first_name character varying(127) NOT NULL,
                              last_name character varying(127) DEFAULT ''::character varying NOT NULL,
                              comment text DEFAULT ''::text,
                              email character varying(127) NOT NULL,
                              phone character varying(127) DEFAULT ''::character varying,
                              permission character varying(20) DEFAULT 'NormalUser'::character varying NOT NULL,
                              pwd character varying(127) DEFAULT '*'::character varying,
                              image_provider character varying(2500) NOT NULL,
                              image_version character varying(32),
                              provider_key character varying(32),
                              auth_info text
);


-- ALTER TABLE public.users OWNER TO signup4;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: signup4
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


-- ALTER TABLE public.users_id_seq OWNER TO signup4;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: signup4
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: events id; Type: DEFAULT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.events ALTER COLUMN id SET DEFAULT nextval('public.events_id_seq'::regclass);


--
-- Name: groups id; Type: DEFAULT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.groups ALTER COLUMN id SET DEFAULT nextval('public.groups_id_seq'::regclass);


--
-- Name: log_entries id; Type: DEFAULT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.log_entries ALTER COLUMN id SET DEFAULT nextval('public.log_entries_id_seq'::regclass);


--
-- Name: memberships id; Type: DEFAULT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.memberships ALTER COLUMN id SET DEFAULT nextval('public.memberships_id_seq'::regclass);


--
-- Name: participations id; Type: DEFAULT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.participations ALTER COLUMN id SET DEFAULT nextval('public.participations_id_seq'::regclass);


--
-- Name: reminders id; Type: DEFAULT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.reminders ALTER COLUMN id SET DEFAULT nextval('public.reminders_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);





-- INSERTs removed





--
-- Name: events_id_seq; Type: SEQUENCE SET; Schema: public; Owner: signup4
--

SELECT pg_catalog.setval('public.events_id_seq', 1, false);


--
-- Name: groups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: signup4
--

SELECT pg_catalog.setval('public.groups_id_seq', 1, false);


--
-- Name: log_entries_id_seq; Type: SEQUENCE SET; Schema: public; Owner: signup4
--

SELECT pg_catalog.setval('public.log_entries_id_seq', 1, false);


--
-- Name: memberships_id_seq; Type: SEQUENCE SET; Schema: public; Owner: signup4
--

SELECT pg_catalog.setval('public.memberships_id_seq', 1, false);


--
-- Name: participations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: signup4
--

SELECT pg_catalog.setval('public.participations_id_seq', 3, true);


--
-- Name: reminders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: signup4
--

SELECT pg_catalog.setval('public.reminders_id_seq', 1, false);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: signup4
--

SELECT pg_catalog.setval('public.users_id_seq', 1, false);


--
-- Name: events events_pkey; Type: CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_pkey PRIMARY KEY (id);


--
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- Name: log_entries log_entries_pkey; Type: CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.log_entries
    ADD CONSTRAINT log_entries_pkey PRIMARY KEY (id);


--
-- Name: memberships memberships_pkey; Type: CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.memberships
    ADD CONSTRAINT memberships_pkey PRIMARY KEY (id);


--
-- Name: participations participations_pkey; Type: CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.participations
    ADD CONSTRAINT participations_pkey PRIMARY KEY (id);


--
-- Name: play_evolutions play_evolutions_pkey; Type: CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.play_evolutions
    ADD CONSTRAINT play_evolutions_pkey PRIMARY KEY (id);


--
-- Name: reminders reminders_pkey; Type: CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.reminders
    ADD CONSTRAINT reminders_pkey PRIMARY KEY (id);


--
-- Name: users unique_email; Type: CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT unique_email UNIQUE (email);


--
-- Name: participations unique_user_event; Type: CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.participations
    ADD CONSTRAINT unique_user_event UNIQUE (userx, event);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: events fk1; Type: FK CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT fk1 FOREIGN KEY (groupx) REFERENCES public.groups(id);


--
-- Name: log_entries log_entries_event_fkey; Type: FK CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.log_entries
    ADD CONSTRAINT log_entries_event_fkey FOREIGN KEY (event) REFERENCES public.events(id);


--
-- Name: memberships memberships_groupx_fkey; Type: FK CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.memberships
    ADD CONSTRAINT memberships_groupx_fkey FOREIGN KEY (groupx) REFERENCES public.groups(id);


--
-- Name: memberships memberships_userx_fkey; Type: FK CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.memberships
    ADD CONSTRAINT memberships_userx_fkey FOREIGN KEY (userx) REFERENCES public.users(id);


--
-- Name: participations participations_event_fkey; Type: FK CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.participations
    ADD CONSTRAINT participations_event_fkey FOREIGN KEY (event) REFERENCES public.events(id);


--
-- Name: participations participations_userx_fkey; Type: FK CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.participations
    ADD CONSTRAINT participations_userx_fkey FOREIGN KEY (userx) REFERENCES public.users(id);


--
-- Name: reminders reminders_event_fkey; Type: FK CONSTRAINT; Schema: public; Owner: signup4
--

ALTER TABLE ONLY public.reminders
    ADD CONSTRAINT reminders_event_fkey FOREIGN KEY (event) REFERENCES public.events(id);


--
-- PostgreSQL database dump complete
--

