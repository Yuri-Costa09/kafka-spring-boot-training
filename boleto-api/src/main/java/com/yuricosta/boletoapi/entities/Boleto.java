package com.yuricosta.boletoapi.entities;

import com.yuricosta.boletoapi.enums.SituacaoBoleto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Boleto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "codigo_barras")
    private String codigoBarras;
    @Column(name = "situacao_boleto")
    private SituacaoBoleto situacaoBoleto;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;
}
