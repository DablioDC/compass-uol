package br.com.dabliodc.compass_uol.model;

import br.com.dabliodc.compass_uol.utils.PaymentStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "payment")
public class Payment implements Serializable {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "id_vendor", nullable = false)
    private Vendor vendor;
    @Column(name = "pay_day")
    private LocalDate payDay;
    @Column(name = "amount_paid")
    private BigDecimal amountPaid;
    @Column(name = "payment_status")
    private PaymentStatusEnum paymentStatusEnum;

    public Payment builder(){
        return new Payment();
    }

    public Payment setId(String id) {
        this.id = id;
        return this;
    }

    public Payment setVendor(Vendor vendor) {
        this.vendor = vendor;
        return this;
    }

    public Payment setPayDay(LocalDate payDay) {
        this.payDay = payDay;
        return this;
    }

    public Payment setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
        return this;
    }

    public Payment setPaymentStatusEnum(PaymentStatusEnum paymentStatusEnum) {
        this.paymentStatusEnum = paymentStatusEnum;
        return this;
    }
}
