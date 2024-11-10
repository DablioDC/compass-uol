package br.com.dabliodc.compass_uol.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class VendorDTO implements Serializable{
    @JsonProperty("vendor_id")
    private String id;
    @JsonProperty("payments")
    private List<PaymentDTO> paymentDTOS;

    public VendorDTO(String id, List<PaymentDTO> listPaymentDTO) {
        this.id = id;
        this.paymentDTOS = listPaymentDTO;
    }
}