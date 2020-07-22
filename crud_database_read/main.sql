
create extension "uuid-ossp";

CREATE TABLE public."order" (
	id serial NOT NULL,
	created timestamp NULL DEFAULT now(),
	updated timestamp NULL DEFAULT now(),
	"label" varchar(50) NULL DEFAULT 'label'::character varying,
	"tag" varchar(50) NULL DEFAULT 'tag'::character varying,
	"description" varchar(50) NULL DEFAULT 'description'::character varying,
	magic_number int4 NULL DEFAULT 99,
	CONSTRAINT order_pk PRIMARY KEY (id)
);
CREATE UNIQUE INDEX order_id_idx ON public.order (id);

CREATE TABLE public.item (
	id serial NOT NULL,
	"name" varchar(40) NULL,
	created timestamp NULL DEFAULT now(),
	updated timestamp NULL DEFAULT now(),
	price int4 NOT NULL,
	CONSTRAINT item_pk PRIMARY KEY (id)
);
CREATE UNIQUE INDEX item_id_idx ON public.item (id);

CREATE TABLE public.order_item (
	id_order int4 NOT  NULL,
	id_item int4 NOT NULL,
	CONSTRAINT order_item_order_fk FOREIGN KEY (id_order) REFERENCES public.order(id),
	CONSTRAINT order_item_item_fk FOREIGN KEY (id_item) REFERENCES public.item(id)
);

CREATE TABLE public.customer (
	id serial NOT NULL,
	"name" varchar(80) NOT NULL,
	created timestamp NULL DEFAULT now(),
	updated timestamp NULL DEFAULT now(),
	CONSTRAINT customer_pk PRIMARY KEY (id)
);
CREATE UNIQUE INDEX customer_id_idx ON public.customer (id);


CREATE TABLE public.customer_order (
	id_customer int4 NOT NULL,
	id_order int4 NOT  NULL,
	CONSTRAINT customer_order_order_fk FOREIGN KEY (id_order) REFERENCES public.order(id),
	CONSTRAINT customer_order_customer_fk FOREIGN KEY (id_customer) REFERENCES public.customer(id)
);

-----------------------------------------------------------------------------------------------
-----------------------------
-------------------------------------------
----------------------------------------------------------------------------------------------
INSERT INTO public.customer (name, created, updated)VALUES('Alanna Dunlap', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Lisa-Marie Lindsey', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Caolan Pike', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Emmy Singh', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Ayah Whyte', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Clara Hogg', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Myles Merrill', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Antoine Sparrow', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Violet Dodson', now(), now());

INSERT INTO public.item ("name", created, updated, price) VALUES('Otchi', now(), now(), 199);
INSERT INTO public.item ("name", created, updated, price) VALUES('Iostan', now(), now(), 299);
INSERT INTO public.item ("name", created, updated, price) VALUES('Wood Pitaya', now(), now(), 150);
INSERT INTO public.item ("name", created, updated, price) VALUES('Ene', now(), now(), 1000);
INSERT INTO public.item ("name", created, updated, price) VALUES('Rose Pecan', now(), now(), 1050);
INSERT INTO public.item ("name", created, updated, price) VALUES('Bana', now(), now(), 100);
INSERT INTO public.item ("name", created, updated, price) VALUES('Alho', now(), now(), 50);
INSERT INTO public.item ("name", created, updated, price) VALUES('Uva', now(), now(), 300);

-----------------------------------------------------------------------------------------------
-----------------------------
-------------------------------------------
----------------------------------------------------------------------------------------------
INSERT INTO public.customer (name, created, updated)VALUES('Alanna Dunlap', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Lisa-Marie Lindsey', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Caolan Pike', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Emmy Singh', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Ayah Whyte', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Clara Hogg', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Myles Merrill', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Antoine Sparrow', now(), now());
INSERT INTO public.customer (name, created, updated)VALUES('Violet Dodson', now(), now());

INSERT INTO public.item ("name", created, updated, price) VALUES('Otchi', now(), now(), 199);
INSERT INTO public.item ("name", created, updated, price) VALUES('Iostan', now(), now(), 299);
INSERT INTO public.item ("name", created, updated, price) VALUES('Wood Pitaya', now(), now(), 150);
INSERT INTO public.item ("name", created, updated, price) VALUES('Ene', now(), now(), 1000);
INSERT INTO public.item ("name", created, updated, price) VALUES('Rose Pecan', now(), now(), 1050);
INSERT INTO public.item ("name", created, updated, price) VALUES('Bana', now(), now(), 100);
INSERT INTO public.item ("name", created, updated, price) VALUES('Alho', now(), now(), 50);
INSERT INTO public.item ("name", created, updated, price) VALUES('Uva', now(), now(), 300);


CREATE OR REPLACE VIEW public.custome_expenditure as
SELECT c.name AS customer_name,
    c.id AS custoemr_id,
    o.id AS order_id,
    o.created AS order_created,
    o.updated AS order_updated,
    o.LABEL AS order_label,
    o.tag AS order_tag,
    o.description AS order_description,
    o.magic_number AS order_magic_number,
    i.id AS item_id,
    i.name AS item_name
   FROM customer c
     JOIN customer_order co ON c.id = co.id_customer
     JOIN "order" o ON o.id = co.id_order
     JOIN order_item oi ON oi.id_order = o.id
     JOIN item i ON i.id = oi.id_item;