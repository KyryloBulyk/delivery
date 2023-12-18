--
-- PostgreSQL database dump
--

-- Dumped from database version 14.9 (Homebrew)
-- Dumped by pg_dump version 14.9 (Homebrew)

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
-- Name: categories; Type: TABLE; Schema: public; Owner: kyrylo
--

CREATE TABLE public.categories (
    id bigint NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.categories OWNER TO kyrylo;

--
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: kyrylo
--

CREATE SEQUENCE public.categories_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categories_id_seq OWNER TO kyrylo;

--
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kyrylo
--

ALTER SEQUENCE public.categories_id_seq OWNED BY public.categories.id;


--
-- Name: coupons; Type: TABLE; Schema: public; Owner: kyrylo
--

CREATE TABLE public.coupons (
    id bigint NOT NULL,
    code character varying(255) NOT NULL,
    discount integer NOT NULL,
    valid_until timestamp(6) without time zone NOT NULL
);


ALTER TABLE public.coupons OWNER TO kyrylo;

--
-- Name: coupons_id_seq; Type: SEQUENCE; Schema: public; Owner: kyrylo
--

CREATE SEQUENCE public.coupons_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.coupons_id_seq OWNER TO kyrylo;

--
-- Name: coupons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kyrylo
--

ALTER SEQUENCE public.coupons_id_seq OWNED BY public.coupons.id;


--
-- Name: orders; Type: TABLE; Schema: public; Owner: kyrylo
--

CREATE TABLE public.orders (
    id bigint NOT NULL,
    user_id integer NOT NULL,
    total_price double precision NOT NULL,
    status character varying(50),
    order_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.orders OWNER TO kyrylo;

--
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: kyrylo
--

CREATE SEQUENCE public.orders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.orders_id_seq OWNER TO kyrylo;

--
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kyrylo
--

ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;


--
-- Name: products; Type: TABLE; Schema: public; Owner: kyrylo
--

CREATE TABLE public.products (
    id bigint NOT NULL,
    title character varying(255) NOT NULL,
    price double precision NOT NULL,
    img text,
    category_id bigint
);


ALTER TABLE public.products OWNER TO kyrylo;

--
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: kyrylo
--

CREATE SEQUENCE public.products_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.products_id_seq OWNER TO kyrylo;

--
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kyrylo
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- Name: user_coupons; Type: TABLE; Schema: public; Owner: kyrylo
--

CREATE TABLE public.user_coupons (
    id bigint NOT NULL,
    user_id integer NOT NULL,
    coupon_id bigint NOT NULL
);


ALTER TABLE public.user_coupons OWNER TO kyrylo;

--
-- Name: user_coupons_id_seq; Type: SEQUENCE; Schema: public; Owner: kyrylo
--

CREATE SEQUENCE public.user_coupons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_coupons_id_seq OWNER TO kyrylo;

--
-- Name: user_coupons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kyrylo
--

ALTER SEQUENCE public.user_coupons_id_seq OWNED BY public.user_coupons.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: kyrylo
--

CREATE TABLE public.users (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    phone character varying(255)
);


ALTER TABLE public.users OWNER TO kyrylo;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: kyrylo
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO kyrylo;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kyrylo
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: categories id; Type: DEFAULT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);


--
-- Name: coupons id; Type: DEFAULT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.coupons ALTER COLUMN id SET DEFAULT nextval('public.coupons_id_seq'::regclass);


--
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);


--
-- Name: products id; Type: DEFAULT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- Name: user_coupons id; Type: DEFAULT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.user_coupons ALTER COLUMN id SET DEFAULT nextval('public.user_coupons_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: kyrylo
--

COPY public.categories (id, name) FROM stdin;
1	Drinks
2	Pizza
3	Burgers
4	Desserts
\.


--
-- Data for Name: coupons; Type: TABLE DATA; Schema: public; Owner: kyrylo
--

COPY public.coupons (id, code, discount, valid_until) FROM stdin;
1	SUMMER2023	15	2023-08-31 02:00:00
6	WINTER2023	10	2023-12-31 01:00:00
\.


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: kyrylo
--

COPY public.orders (id, user_id, total_price, status, order_date) FROM stdin;
2	1	50	new	2023-12-15 11:00:00
1	1	20	new	2023-12-15 11:00:00
\.


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: kyrylo
--

COPY public.products (id, title, price, img, category_id) FROM stdin;
\.


--
-- Data for Name: user_coupons; Type: TABLE DATA; Schema: public; Owner: kyrylo
--

COPY public.user_coupons (id, user_id, coupon_id) FROM stdin;
1	1	1
3	3	1
4	3	6
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: kyrylo
--

COPY public.users (id, name, password, email, phone) FROM stdin;
1	Kyrylo	12345678	kyrylo.bulyk@gmail.com	\N
3	Nazar	12345	nazar.korchevsky@gmail.com	\N
\.


--
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: kyrylo
--

SELECT pg_catalog.setval('public.categories_id_seq', 5, true);


--
-- Name: coupons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: kyrylo
--

SELECT pg_catalog.setval('public.coupons_id_seq', 7, true);


--
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: kyrylo
--

SELECT pg_catalog.setval('public.orders_id_seq', 3, true);


--
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: kyrylo
--

SELECT pg_catalog.setval('public.products_id_seq', 1, true);


--
-- Name: user_coupons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: kyrylo
--

SELECT pg_catalog.setval('public.user_coupons_id_seq', 4, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: kyrylo
--

SELECT pg_catalog.setval('public.users_id_seq', 3, true);


--
-- Name: categories categories_name_key; Type: CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_name_key UNIQUE (name);


--
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- Name: coupons coupons_code_key; Type: CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.coupons
    ADD CONSTRAINT coupons_code_key UNIQUE (code);


--
-- Name: coupons coupons_pkey; Type: CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.coupons
    ADD CONSTRAINT coupons_pkey PRIMARY KEY (id);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- Name: user_coupons user_coupons_pkey; Type: CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.user_coupons
    ADD CONSTRAINT user_coupons_pkey PRIMARY KEY (id);


--
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: orders orders_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: products products_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.categories(id);


--
-- Name: user_coupons user_coupons_coupon_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.user_coupons
    ADD CONSTRAINT user_coupons_coupon_id_fkey FOREIGN KEY (coupon_id) REFERENCES public.coupons(id);


--
-- Name: user_coupons user_coupons_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.user_coupons
    ADD CONSTRAINT user_coupons_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

