package com.gestaoSalarios.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "docente")
public class Docente {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;  // formato DOC001, DOC002...

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "apelido", nullable = false)
    private String apelido;

    @Column(name = "nuit", nullable = false, unique = true)
    private String nuit;

    @Column(name = "genero", nullable = false)
    private String genero;  // "M" ou "F"

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "valor_pago_por_hora", nullable = false)
    private Double valorPagoPorHora;

    @Column(name = "banco")
    private String banco;

    @Column(name = "nib", unique = true)
    private String nib;

    @Column(name = "categoria", nullable = false)
    private String categoria;  // ex: "Assistente", "Coordenador"

    // Relacionamentos (serão mapeados pelas outras entidades)
    // Um docente pode ter muitas disciplinas, contratos, cargas horárias e pagamentos

    // Método auxiliar para gerar ID sequencial
    public static String gerarProximoId(String ultimoId) {
        if (ultimoId == null || ultimoId.isEmpty()) {
            return "DOC001";
        }
        String numeroStr = ultimoId.substring(3);
        int numero = Integer.parseInt(numeroStr);
        int proximoNumero = numero + 1;
        return String.format("DOC%03d", proximoNumero);
    }
}