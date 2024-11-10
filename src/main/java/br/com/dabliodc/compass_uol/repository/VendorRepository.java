package br.com.dabliodc.compass_uol.repository;

import br.com.dabliodc.compass_uol.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, String> {
}
