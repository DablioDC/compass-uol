package br.com.dabliodc.compass_uol.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "vendor")
public class Vendor implements Serializable {
    @Id
    private String id;
}
