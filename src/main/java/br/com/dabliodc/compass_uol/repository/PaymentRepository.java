package br.com.dabliodc.compass_uol.repository;

import br.com.dabliodc.compass_uol.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
