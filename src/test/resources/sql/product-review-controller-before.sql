DELETE FROM "review";
DELETE FROM "product";

ALTER SEQUENCE "product_id_seq" RESTART WITH 1;
ALTER SEQUENCE "review_id_seq" RESTART WITH 1;

INSERT INTO "product" ("name", "description", "price") VALUES ('Product 1', 'Description 1', 100.0);
INSERT INTO "product" ("name", "description", "price") VALUES ('Product 2', 'Description 2', 200.0);
INSERT INTO "product" ("name", "description", "price") VALUES ('Product 3', 'Description 3', 300.0);

INSERT INTO "review" ( "comment", "rating", "product_id") VALUES ('Review 1', 'BAD', 1);
INSERT INTO "review" ( "comment", "rating", "product_id") VALUES ('Review 2', 'GOOD', 1);
INSERT INTO "review" ( "comment", "rating", "product_id") VALUES ('Review 3', 'AVERAGE', 2);
INSERT INTO "review" ( "comment", "rating", "product_id") VALUES ('Review 4', 'GOOD', 3);
