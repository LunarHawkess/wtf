CREATE TABLE "product"
(
    "id"          BIGSERIAL      NOT NULL,
    "name"        VARCHAR(64)    NOT NULL,
    "description" TEXT,
    "price"       DECIMAL(10, 2) NOT NULL,
    CONSTRAINT "pk_product" PRIMARY KEY ("id")
);

CREATE TABLE "category"
(
    "id"   BIGSERIAL   NOT NULL,
    "name" VARCHAR(64) NOT NULL,
    CONSTRAINT "pk_category" PRIMARY KEY ("id"),
    CONSTRAINT "uq_category_name" UNIQUE ("name")
);

CREATE TABLE "product_category"
(
    "product_id"  BIGINT NOT NULL,
    "category_id" BIGINT NOT NULL,
    CONSTRAINT "pk_product_category" PRIMARY KEY ("product_id", "category_id"),
    CONSTRAINT "fk_product_category_on_product" FOREIGN KEY ("product_id") REFERENCES "product" ("id"),
    CONSTRAINT "fk_product_category_on_category" FOREIGN KEY ("category_id") REFERENCES "category" ("id")
);

CREATE TABLE "review"
(
    "id"         BIGSERIAL    NOT NULL,
    "comment"    VARCHAR(255) NOT NULL,
    "rating"     VARCHAR(9)   NOT NULL,
    "product_id" BIGINT       NOT NULL,
    CONSTRAINT "pk_review" PRIMARY KEY ("id"),
    CONSTRAINT "fk_review_on_product" FOREIGN KEY ("product_id") REFERENCES "product" ("id")
)
