package br.com.dabliodc.compass_uol.repository;

import br.com.dabliodc.compass_uol.model.Charge;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChargeRepository extends JpaRepository<Charge, String> {
    Optional<Charge> findByIdAndVendor_Id(String idCharge, String idVendor);
}
