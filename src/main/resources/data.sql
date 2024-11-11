-- Criação de tabelas e script população de dados para utilização nos testes
CREATE TABLE vendor (
    id VARCHAR(20) PRIMARY KEY
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

INSERT INTO vendor (id) VALUES ('VD001');
INSERT INTO vendor (id) VALUES ('VD002');
INSERT INTO vendor (id) VALUES ('WD001');

INSERT INTO charge (id, pay_day, value_charge, payment_status, id_vendor) VALUES ('PAY001', '2024-10-07', 1500.00, null, 'VD001');
INSERT INTO charge (id, pay_day, value_charge, payment_status, id_vendor) VALUES ('PAY002', '2024-11-08', 2000.00, null, 'VD001');
INSERT INTO charge (id, pay_day, value_charge, payment_status, id_vendor) VALUES ('PAY003', '2024-11-05', 2000.00, null, 'VD002');
INSERT INTO charge (id, pay_day, value_charge, payment_status, id_vendor) VALUES ('GRE001', '2024-11-11', 100.00, null, 'WD001');
INSERT INTO charge (id, pay_day, value_charge, payment_status, id_vendor) VALUES ('GRE002', '2024-11-11', 200.00, null, 'WD001');
INSERT INTO charge (id, pay_day, value_charge, payment_status, id_vendor) VALUES ('GRE003', '2024-11-11', 250.00, null, 'WD001');
