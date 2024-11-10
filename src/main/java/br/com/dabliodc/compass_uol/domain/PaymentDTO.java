package br.com.dabliodc.compass_uol.domain;

import br.com.dabliodc.compass_uol.model.Vendor;
import br.com.dabliodc.compass_uol.utils.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO implements Serializable {
    private String id;
    private Vendor vendor;
    private LocalDate payDay;
    private BigDecimal amountPaid;
    private PaymentStatus paymentStatus;
}
