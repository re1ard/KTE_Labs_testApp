-- liquibase formatted sql
-- changeset me:drop_tables
DROP TABLE IF EXISTS current_discount cascade;
drop table if exists customer_statistic cascade;
drop table if exists customers cascade;
drop table if exists product_statistic cascade;
drop table if exists products cascade;
drop table if exists reviews cascade;
drop table if exists selled_products cascade;
drop table if exists sells cascade;