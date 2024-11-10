package br.com.dabliodc.compass_uol.domain;

import br.com.dabliodc.compass_uol.utils.PaymentStatusEnum;
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
    private LocalDate payDay;
    private BigDecimal amountPaid;
    private PaymentStatusEnum paymentStatusEnum;
}
