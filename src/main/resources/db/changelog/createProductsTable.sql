CREATE TABLE "products"
(
    id serial primary key,
    uid varchar not null,
    active boolean not null DEFAULT FALSE,
    product_name varchar not null,
    main_desc TEXT not null,
    desc_html TEXT not null,
    price decimal(12,2) not null,
    image_urls varchar[] not null,
    parameters TEXT,
    created_at DATE,
    category integer REFERENCES "categories" (id)
);