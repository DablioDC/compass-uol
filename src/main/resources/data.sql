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


INSERT INTO vendor (id, name_vendor) VALUES ('VD001', 'Wellington Farias');
INSERT INTO vendor (id, name_vendor) VALUES ('VD002', 'Diego Cardoso');

INSERT INTO charge (id, pay_day, value_charge, payment_status, id_vendor) VALUES ('PAY001', '2024-10-07', 1500.00, null, 'VD001');
INSERT INTO charge (id, pay_day, value_charge, payment_status, id_vendor) VALUES ('PAY002', '2024-11-08', 2000.00, null, 'VD001');
INSERT INTO charge (id, pay_day, value_charge, payment_status, id_vendor) VALUES ('PAY003', '2024-11-05', 2000.00, null, 'VD002');
