package br.com.dabliodc.compass_uol.model;

import br.com.dabliodc.compass_uol.utils.PaymentStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "charge")
public class Charge implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "pay_day")
    private LocalDate payDay;
    @Column(name = "value_charge")
    private BigDecimal valueCharge;
    @Column(name = "payment_status")
    private PaymentStatusEnum paymentStatusEnum;
    @ManyToOne
    @JoinColumn(name = "id_vendor", nullable = false)
    private Vendor vendor;

    public Charge builder(){
        return new Charge();
    }

    public Charge setId(String id) {
        this.id = id;
        return this;
    }

    public Charge setPayDay(LocalDate payDay) {
        this.payDay = payDay;
        return this;
    }

    public Charge setValueCharge(BigDecimal valueCharge) {
        this.valueCharge = valueCharge;
        return this;
    }

    public Charge setPaymentStatusEnum(PaymentStatusEnum paymentStatusEnum) {
        this.paymentStatusEnum = paymentStatusEnum;
        return this;
    }

    public Charge setVendor(Vendor vendor) {
        this.vendor = vendor;
        return this;
    }
}
