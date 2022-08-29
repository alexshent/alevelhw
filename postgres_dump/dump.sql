--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5 (Debian 14.5-1.pgdg110+1)
-- Dumped by pg_dump version 14.4

-- Started on 2022-08-29 07:54:06 UTC

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
-- TOC entry 210 (class 1259 OID 16398)
-- Name: automobile_manufacturers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.automobile_manufacturers (
    manufacturer_id uuid NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE public.automobile_manufacturers OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16391)
-- Name: automobiles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.automobiles (
    automobile_id uuid NOT NULL,
    model character varying NOT NULL,
    price numeric NOT NULL,
    components character varying[] NOT NULL,
    manufacturer_id uuid NOT NULL,
    body_type character varying NOT NULL,
    created_at timestamp without time zone NOT NULL,
    trip_counter integer NOT NULL,
    engine_id uuid NOT NULL,
    invoice_id uuid
);


ALTER TABLE public.automobiles OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16450)
-- Name: bicycle_manufacturers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bicycle_manufacturers (
    manufacturer_id uuid NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE public.bicycle_manufacturers OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 16443)
-- Name: bicycles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bicycles (
    bicycle_id uuid NOT NULL,
    model character varying NOT NULL,
    price numeric NOT NULL,
    components character varying[] NOT NULL,
    manufacturer_id uuid NOT NULL,
    number_of_wheels integer NOT NULL,
    invoice_id uuid
);


ALTER TABLE public.bicycles OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 16405)
-- Name: engines; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.engines (
    engine_id uuid NOT NULL,
    volume integer NOT NULL,
    brand character varying NOT NULL
);


ALTER TABLE public.engines OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16424)
-- Name: invoices; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.invoices (
    invoice_id uuid NOT NULL,
    created_at timestamp without time zone NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE public.invoices OWNER TO postgres;

--
-- TOC entry 3348 (class 0 OID 16398)
-- Dependencies: 210
-- Data for Name: automobile_manufacturers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.automobile_manufacturers (manufacturer_id, name) FROM stdin;
61e7cd21-6057-464f-8112-c8a3936fd155	KIA
9980453f-d23b-43ea-9f03-99d37a7f6d76	BMW
db979fce-ca11-4976-bc46-0d46f01d7bcf	OPEL
\.


--
-- TOC entry 3347 (class 0 OID 16391)
-- Dependencies: 209
-- Data for Name: automobiles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.automobiles (automobile_id, model, price, components, manufacturer_id, body_type, created_at, trip_counter, engine_id, invoice_id) FROM stdin;
\.


--
-- TOC entry 3352 (class 0 OID 16450)
-- Dependencies: 214
-- Data for Name: bicycle_manufacturers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.bicycle_manufacturers (manufacturer_id, name) FROM stdin;
a7afb0cf-8cf0-49dd-b213-9caf21c4c9a1	CCC
a251f0c8-cf5b-4362-974f-c398c85ecad6	BBB
20f8faef-f163-4428-8499-2837b2a5323e	AAA
\.


--
-- TOC entry 3351 (class 0 OID 16443)
-- Dependencies: 213
-- Data for Name: bicycles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.bicycles (bicycle_id, model, price, components, manufacturer_id, number_of_wheels, invoice_id) FROM stdin;
\.


--
-- TOC entry 3349 (class 0 OID 16405)
-- Dependencies: 211
-- Data for Name: engines; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.engines (engine_id, volume, brand) FROM stdin;
63ef8932-aff0-46ef-b332-83b390b7f3dc	11	Brand 1
73774311-178c-4200-8d38-078f482592f4	22	Brand 2
c448ebd6-471b-4c51-8126-180f684cd326	33	Brand 3
\.


--
-- TOC entry 3350 (class 0 OID 16424)
-- Dependencies: 212
-- Data for Name: invoices; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.invoices (invoice_id, created_at, name) FROM stdin;
\.


--
-- TOC entry 3192 (class 2606 OID 16404)
-- Name: automobile_manufacturers automobile_manufacturers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.automobile_manufacturers
    ADD CONSTRAINT automobile_manufacturers_pkey PRIMARY KEY (manufacturer_id);


--
-- TOC entry 3187 (class 2606 OID 16397)
-- Name: automobiles automobiles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.automobiles
    ADD CONSTRAINT automobiles_pkey PRIMARY KEY (automobile_id);


--
-- TOC entry 3202 (class 2606 OID 16456)
-- Name: bicycle_manufacturers bicycle_manufacturers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bicycle_manufacturers
    ADD CONSTRAINT bicycle_manufacturers_pkey PRIMARY KEY (manufacturer_id);


--
-- TOC entry 3198 (class 2606 OID 16449)
-- Name: bicycles bicycles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bicycles
    ADD CONSTRAINT bicycles_pkey PRIMARY KEY (bicycle_id);


--
-- TOC entry 3194 (class 2606 OID 16411)
-- Name: engines engines_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.engines
    ADD CONSTRAINT engines_pkey PRIMARY KEY (engine_id);


--
-- TOC entry 3196 (class 2606 OID 16430)
-- Name: invoices invoices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoices
    ADD CONSTRAINT invoices_pkey PRIMARY KEY (invoice_id);


--
-- TOC entry 3188 (class 1259 OID 16417)
-- Name: fki_am_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_am_fk ON public.automobiles USING btree (manufacturer_id);


--
-- TOC entry 3189 (class 1259 OID 16423)
-- Name: fki_engine_fkey; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_engine_fkey ON public.automobiles USING btree (engine_id);


--
-- TOC entry 3199 (class 1259 OID 16480)
-- Name: fki_invoice_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_invoice_fk ON public.bicycles USING btree (invoice_id);


--
-- TOC entry 3190 (class 1259 OID 16441)
-- Name: fki_invoice_fkey; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_invoice_fkey ON public.automobiles USING btree (invoice_id);


--
-- TOC entry 3200 (class 1259 OID 16474)
-- Name: fki_manufacturer_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_manufacturer_fk ON public.bicycles USING btree (manufacturer_id);


--
-- TOC entry 3203 (class 2606 OID 16412)
-- Name: automobiles am_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.automobiles
    ADD CONSTRAINT am_fkey FOREIGN KEY (manufacturer_id) REFERENCES public.automobile_manufacturers(manufacturer_id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 3204 (class 2606 OID 16418)
-- Name: automobiles engine_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.automobiles
    ADD CONSTRAINT engine_fkey FOREIGN KEY (engine_id) REFERENCES public.engines(engine_id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 3207 (class 2606 OID 16475)
-- Name: bicycles invoice_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bicycles
    ADD CONSTRAINT invoice_fk FOREIGN KEY (invoice_id) REFERENCES public.invoices(invoice_id) ON UPDATE CASCADE ON DELETE SET NULL NOT VALID;


--
-- TOC entry 3205 (class 2606 OID 16464)
-- Name: automobiles invoice_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.automobiles
    ADD CONSTRAINT invoice_fkey FOREIGN KEY (invoice_id) REFERENCES public.invoices(invoice_id) ON UPDATE CASCADE ON DELETE SET NULL NOT VALID;


--
-- TOC entry 3206 (class 2606 OID 16469)
-- Name: bicycles manufacturer_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bicycles
    ADD CONSTRAINT manufacturer_fk FOREIGN KEY (manufacturer_id) REFERENCES public.bicycle_manufacturers(manufacturer_id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


-- Completed on 2022-08-29 07:54:06 UTC

--
-- PostgreSQL database dump complete
--

