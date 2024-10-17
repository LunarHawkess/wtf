DELETE FROM "category";

ALTER SEQUENCE "category_id_seq" RESTART WITH 1;

INSERT INTO "category" ("name") VALUES ('Electronics');
INSERT INTO "category" ("name") VALUES ('Books');
INSERT INTO "category" ("name") VALUES ('Clothing');


