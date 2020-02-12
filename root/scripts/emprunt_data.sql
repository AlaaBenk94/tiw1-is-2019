--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1 (Debian 12.1-1.pgdg100+1)
-- Dumped by pg_dump version 12.1 (Debian 12.1-1.pgdg100+1)

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: abonne; Type: TABLE; Schema: public; Owner: emprunt_user
--

CREATE TABLE public.abonne (
    id bigint NOT NULL,
    date_debut date,
    date_fin date,
    name character varying(255),
    owner character varying(255)
);


ALTER TABLE public.abonne OWNER TO emprunt_user;

--
-- Name: emprunt; Type: TABLE; Schema: public; Owner: emprunt_user
--

CREATE TABLE public.emprunt (
    id bigint NOT NULL,
    activated boolean,
    activation_number character varying(255),
    date date,
    id_abonne bigint,
    id_trottinette bigint,
    owner character varying(255)
);


ALTER TABLE public.emprunt OWNER TO emprunt_user;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: emprunt_user
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO emprunt_user;

--
-- Data for Name: abonne; Type: TABLE DATA; Schema: public; Owner: emprunt_user
--

COPY public.abonne (id, date_debut, date_fin, name, owner) FROM stdin;
\.


--
-- Data for Name: emprunt; Type: TABLE DATA; Schema: public; Owner: emprunt_user
--

COPY public.emprunt (id, activated, activation_number, date, id_abonne, id_trottinette, owner) FROM stdin;
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: emprunt_user
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);


--
-- Name: abonne abonne_pkey; Type: CONSTRAINT; Schema: public; Owner: emprunt_user
--

ALTER TABLE ONLY public.abonne
    ADD CONSTRAINT abonne_pkey PRIMARY KEY (id);


--
-- Name: emprunt emprunt_pkey; Type: CONSTRAINT; Schema: public; Owner: emprunt_user
--

ALTER TABLE ONLY public.emprunt
    ADD CONSTRAINT emprunt_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--