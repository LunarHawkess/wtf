DELETE FROM "product_category";
DELETE FROM "category";
DELETE FROM "product";

ALTER SEQUENCE "product_id_seq" RESTART WITH 1;
ALTER SEQUENCE "category_id_seq" RESTART WITH 1;

INSERT INTO "product" ("name", "description", "price") VALUES ('Product 1', 'Description 1', 100.0);
INSERT INTO "product" ("name", "description", "price") VALUES ('Product 2', 'Description 2', 200.0);
INSERT INTO "product" ("name", "description", "price") VALUES ('Product 3', 'Description 3', 300.0);

INSERT INTO "category" ("name") VALUES ('Category 1');
INSERT INTO "category" ("name") VALUES ('Category 2');
INSERT INTO "category" ("name") VALUES ('Category 3');

INSERT INTO "product_category" ("product_id", "category_id") VALUES (1, 1);
INSERT INTO "product_category" ("product_id", "category_id") VALUES (1, 2);
INSERT INTO "product_category" ("product_id", "category_id") VALUES (2, 1);
INSERT INTO "product_category" ("product_id", "category_id") VALUES (3, 2);
