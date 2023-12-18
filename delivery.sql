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
-- Name: cart; Type: TABLE; Schema: public; Owner: kyrylo
--

CREATE TABLE public.cart (
    cart_id integer NOT NULL,
    user_id integer,
    total_price real NOT NULL
);


ALTER TABLE public.cart OWNER TO kyrylo;

--
-- Name: cart_cart_id_seq; Type: SEQUENCE; Schema: public; Owner: kyrylo
--

CREATE SEQUENCE public.cart_cart_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cart_cart_id_seq OWNER TO kyrylo;

--
-- Name: cart_cart_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kyrylo
--

ALTER SEQUENCE public.cart_cart_id_seq OWNED BY public.cart.cart_id;


--
-- Name: cart_item; Type: TABLE; Schema: public; Owner: kyrylo
--

CREATE TABLE public.cart_item (
    cart_item_id integer NOT NULL,
    cart_id integer,
    product_id bigint,
    quantity integer NOT NULL
);


ALTER TABLE public.cart_item OWNER TO kyrylo;

--
-- Name: cart_item_cart_item_id_seq; Type: SEQUENCE; Schema: public; Owner: kyrylo
--

CREATE SEQUENCE public.cart_item_cart_item_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cart_item_cart_item_id_seq OWNER TO kyrylo;

--
-- Name: cart_item_cart_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: kyrylo
--

ALTER SEQUENCE public.cart_item_cart_item_id_seq OWNED BY public.cart_item.cart_item_id;


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
-- Name: cart cart_id; Type: DEFAULT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.cart ALTER COLUMN cart_id SET DEFAULT nextval('public.cart_cart_id_seq'::regclass);


--
-- Name: cart_item cart_item_id; Type: DEFAULT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.cart_item ALTER COLUMN cart_item_id SET DEFAULT nextval('public.cart_item_cart_item_id_seq'::regclass);


--
-- Name: categories id; Type: DEFAULT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);


--
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);


--
-- Name: products id; Type: DEFAULT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: cart; Type: TABLE DATA; Schema: public; Owner: kyrylo
--

COPY public.cart (cart_id, user_id, total_price) FROM stdin;
1	1	0
\.


--
-- Data for Name: cart_item; Type: TABLE DATA; Schema: public; Owner: kyrylo
--

COPY public.cart_item (cart_item_id, cart_id, product_id, quantity) FROM stdin;
\.


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
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: kyrylo
--

COPY public.orders (id, user_id, total_price, status, order_date) FROM stdin;
\.


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: kyrylo
--

COPY public.products (id, title, price, img, category_id) FROM stdin;
2	Маргарита	9.99	url_to_image_of_margarita_pizza	2
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: kyrylo
--

COPY public.users (id, name, password, email, phone) FROM stdin;
1	John Doe	password123	hello@example.com	\N
\.


--
-- Name: cart_cart_id_seq; Type: SEQUENCE SET; Schema: public; Owner: kyrylo
--

SELECT pg_catalog.setval('public.cart_cart_id_seq', 1, true);


--
-- Name: cart_item_cart_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: kyrylo
--

SELECT pg_catalog.setval('public.cart_item_cart_item_id_seq', 1, true);


--
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: kyrylo
--

SELECT pg_catalog.setval('public.categories_id_seq', 5, true);


--
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: kyrylo
--

SELECT pg_catalog.setval('public.orders_id_seq', 1, false);


--
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: kyrylo
--

SELECT pg_catalog.setval('public.products_id_seq', 2, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: kyrylo
--

SELECT pg_catalog.setval('public.users_id_seq', 1, true);


--
-- Name: cart_item cart_item_pkey; Type: CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.cart_item
    ADD CONSTRAINT cart_item_pkey PRIMARY KEY (cart_item_id);


--
-- Name: cart cart_pkey; Type: CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_pkey PRIMARY KEY (cart_id);


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
-- Name: cart_item cart_item_cart_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.cart_item
    ADD CONSTRAINT cart_item_cart_id_fkey FOREIGN KEY (cart_id) REFERENCES public.cart(cart_id);


--
-- Name: cart_item cart_item_cart_id_fkey1; Type: FK CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.cart_item
    ADD CONSTRAINT cart_item_cart_id_fkey1 FOREIGN KEY (cart_id) REFERENCES public.cart(cart_id);


--
-- Name: cart_item cart_item_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.cart_item
    ADD CONSTRAINT cart_item_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id);


--
-- Name: cart_item cart_item_product_id_fkey1; Type: FK CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.cart_item
    ADD CONSTRAINT cart_item_product_id_fkey1 FOREIGN KEY (product_id) REFERENCES public.products(id);


--
-- Name: cart cart_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: cart cart_user_id_fkey1; Type: FK CONSTRAINT; Schema: public; Owner: kyrylo
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_user_id_fkey1 FOREIGN KEY (user_id) REFERENCES public.users(id);


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
-- PostgreSQL database dump complete
--

