CREATE SCHEMA demoSchema;

CREATE DATABASE demo_database
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;

CREATE TABLE demoSchema.inventory (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    current_quantity INT NOT NULL,
    max_quantity INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE demoSchema.inventory_transactions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_product_id BIGINT NOT NULL,
    transaction_type ENUM('FULFILLMENT', 'RESTOCK', 'RETURN') NOT NULL,
    quantity INT NOT NULL,
    created_at_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    description VARCHAR(255),
    FOREIGN KEY (product_product_id) REFERENCES product(product_id)
);

CREATE TABLE demoSchema.product (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255),
    sub_category VARCHAR(255),
    supplier_supplier_id BIGINT,
    FOREIGN KEY (supplier_supplier_id) REFERENCES supplier(supplier_id)
);

CREATE TABLE demoSchema.supplier (
    supplier_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    supplier_name VARCHAR(255) NOT NULL,
    contact VARCHAR(255),
    address VARCHAR(255)
);

INSERT INTO demoSchema.supplier (supplier_name, contact, address)
VALUES ('Supplier A', '+1234567890', '123 Karol Bagh, Delhi, India'),
       ('Supplier B', '+0987654321', '456 Marine Drive, Mumbai, India');

INSERT INTO demoSchema.product (name, category, sub_category, supplier_supplier_id)
VALUES ('Chicken', 'Cold', 'Cold-Category A', 1),
       ('Fish', 'Sea', 'Sea-Category A', 2);

INSERT INTO demoSchema.inventory (product_id, current_quantity, max_quantity)
VALUES (1, 100, 200),
       (2, 50, 100);

INSERT INTO demoSchema.inventory_transactions (product_product_id, transaction_type, quantity, description)
VALUES (1, 'FULFILLMENT', 50, 'Initial stock for Product A'),
       (2, 'RESTOCK', 20, 'Additional stock for Product B'),
       (2, 'REFUND', 3, 'Returned stock for Product B');