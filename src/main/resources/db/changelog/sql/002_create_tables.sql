-- liquibase formatted sql
-- changeset me:create_tables
create table current_discount (
    id bigserial not null,
    discount int2,
    product_id int8,
    primary key (id)
);
create table customer_statistic (
    id bigserial not null,
    complete_sells_count int8,
    customer_id int8,
    total_full_buy_price int8,
    total_full_discount int8,
    primary key (id)
);
create table customers (
    id int8 not null,
    discount_first int2,
    discount_second int2,
    name varchar(255) not null,
    primary key (id)
);
create table product_statistic (
    id bigserial not null,
    complete_sells_count int8,
    product_id int8,
    total_discount_sells_price int8,
    total_product_sells_price int8,
    primary key (id)
);
create table products (
    id bigserial not null,
    description varchar(255) not null,
    discount int2,
    name varchar(255) not null,
    price int8 not null,
    primary key (id)
);
create table reviews (
    id bigserial not null,
    customer_id int8,
    product_id int8,
    rating int2 not null,
    primary key (id)
);
create table selled_products (
    id bigserial not null,
    customer_id int8,
    discount_price int8,
    original_price int8,
    product_id int8,
    sell_id int8,
    primary key (id)
);
create table sells (
    id bigserial not null,
    complete boolean,
    final_cost int8,
    customer_id int8,
    final_discount int2,
    sell_date timestamp,
    sell_id int2,
    primary key (id)
);