-- Criação de tabelas e script população de dados para utilização nos testes
CREATE TABLE vendor (
    id VARCHAR(20) PRIMARY KEY,
    name_vendor VARCHAR(150)
);

CREATE TABLE charge (
    id VARCHAR(20) PRIMARY KEY,
    id_vendor VARCHAR(20),
    pay_day DATE,
    value_charge DECIMAL(12, 2),
    payment_status VARCHAR(10),
    CONSTRAINT fk_id_vendor FOREIGN KEY (id_vendor) REFERENCES vendor(id)
);

CREATE TABLE payment (
    id VARCHAR(20) PRIMARY KEY,
    id_vendor VARCHAR(20),
    pay_day DATE,
    amount_paid DECIMAL(12, 2),
    payment_status VARCHAR(10),
    CONSTRAINT fk_id_vendor_pay FOREIGN KEY (id_vendor) REFERENCES vendor(id)
);


