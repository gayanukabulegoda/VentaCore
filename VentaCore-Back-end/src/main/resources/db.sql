CREATE DATABASE IF NOT EXISTS ventaCore;

USE ventaCore;

CREATE TABLE customer (
                          id VARCHAR(60) PRIMARY KEY,
                          name VARCHAR(60) NOT NULL,
                          email VARCHAR(110) NOT NULL,
                          city VARCHAR(40) NOT NULL
);

CREATE TABLE item (
                      id VARCHAR(60) PRIMARY KEY,
                      name VARCHAR(60) NOT NULL,
                      qty INT(40) NOT NULL,
                      price FLOAT(40) NOT NULL
);

CREATE TABLE `order` (
                         id VARCHAR(60) PRIMARY KEY,
                         date VARCHAR(60) NOT NULL,
                         customer_id VARCHAR(60) NOT NULL,
                         total FLOAT(40) NOT NULL,
                         FOREIGN KEY (customer_id) REFERENCES customer(id)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE
);

CREATE TABLE order_item_detail (
                            order_id VARCHAR(60),
                            item_id VARCHAR(60),
                            qty INT(40) NOT NULL,
                            PRIMARY KEY (order_id, item_id),
                            FOREIGN KEY (order_id) REFERENCES `order`(id)
                                ON UPDATE CASCADE
                                ON DELETE CASCADE,
                            FOREIGN KEY (item_id) REFERENCES item(id)
                                ON UPDATE CASCADE
                                ON DELETE CASCADE
);
