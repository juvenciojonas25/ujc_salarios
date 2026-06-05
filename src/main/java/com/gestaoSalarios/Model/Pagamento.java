package com.gestaoSalarios.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;

    @NotBlank
    @Column(unique = true)
    private String referenciaPagamento;

    @NotBlank
    private String mes;

    @Positive
    private Double totalHoras;

    @Positive
    private Double valorHora;

    @Positive
    private Double salarioBruto;

    @Positive
    private Double descontoIrps;

    @Positive
    private Double salarioLiquido;

    @NotNull
    private LocalDateTime dataProcessamento;

    @Enumerated(EnumType.STRING)
    private EstadoPagamento estadoPagamento;
}