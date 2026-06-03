package com.gestaoSalarios.Service;

import com.gestaoSalarios.Model.*;
import com.gestaoSalarios.Repository.CargaHorariaRepository;
import com.gestaoSalarios.Repository.DocenteRepository;
import com.gestaoSalarios.Repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private CargaHorariaRepository cargaHorariaRepository;

    // Taxa de desconto IRPS simulada (pode ser alterada)
    private double calcularDescontoIRPS(double salarioBruto) {
        if (salarioBruto <= 50000) {
            return salarioBruto * 0.10; // 10%
        } else if (salarioBruto <= 100000) {
            return salarioBruto * 0.15; // 15%
        } else {
            return salarioBruto * 0.20; // 20%
        }
    }

    /**
     * Processar pagamento de um docente para um determinado mês (formato "yyyy-MM").
     * Calcula:
     * - total_horas (horas lecionadas no mês)
     * - salario_bruto = total_horas * valor_hora (do docente ou do contrato ativo)
     * - desconto_irps (aplicado sobre salario_bruto)
     * - salario_liquido = salario_bruto - desconto_irps
     * Cria ou atualiza um pagamento com estado PROCESSADO.
     */
    @Transactional
    public Pagamento processarPagamento(String docenteId, String mes) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));

        // Verificar se já existe processamento para este docente/mês
        if (pagamentoRepository.existsByDocenteAndMes(docente, mes)) {
            throw new RuntimeException("Pagamento já processado para o docente " + docenteId + " no mês " + mes);
        }

        // Obter total de horas lecionadas no mês
        Double totalHoras = cargaHorariaRepository.sumHorasLecionadasByDocenteAndMes(docente, mes);
        if (totalHoras == null || totalHoras == 0) {
            throw new RuntimeException("Nenhuma hora lecionada encontrada para o docente no mês " + mes);
        }

        // Definir valor por hora (prioridade: contrato ativo, caso contrário usa valor do docente)
        // Aqui simplificamos: usa o valor do docente diretamente. Você pode melhorar buscando contrato ativo.
        Double valorHora = docente.getValorPagoPorHora();

        // Cálculos
        double salarioBruto = totalHoras * valorHora;
        double desconto = calcularDescontoIRPS(salarioBruto);
        double salarioLiquido = salarioBruto - desconto;

        // Construir pagamento
        Pagamento pagamento = Pagamento.builder()
                .docente(docente)
                .referenciaPagamento(gerarReferencia(docenteId, mes))
                .mes(mes)
                .totalHoras(totalHoras)
                .valorHora(valorHora)
                .salarioBruto(salarioBruto)
                .descontoIrps(desconto)
                .salarioLiquido(salarioLiquido)
                .dataProcessamento(LocalDateTime.now())
                .estadoPagamento(EstadoPagamento.PROCESSADO)
                .build();

        // Gerar ID do pagamento (PAG001...)
        String ultimoId = pagamentoRepository.findAll().stream()
                .map(Pagamento::getId)
                .reduce((first, second) -> second)
                .orElse(null);
        String novoId = Pagamento.gerarProximoId(ultimoId);
        pagamento.setId(novoId);

        return pagamentoRepository.save(pagamento);
    }

    private String gerarReferencia(String docenteId, String mes) {
        return "REF-" + docenteId + "-" + mes;
    }

    /**
     * Listar todos os pagamentos.
     */
    public List<Pagamento> listarPagamentos() {
        return pagamentoRepository.findAll();
    }

    /**
     * Listar pagamentos de um docente.
     */
    public List<Pagamento> listarPagamentosPorDocente(String docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        return pagamentoRepository.findByDocente(docente);
    }

    /**
     * Alterar estado do pagamento (ex: de PROCESSADO para PAGO).
     */
    @Transactional
    public Pagamento alterarEstadoPagamento(String pagamentoId, EstadoPagamento novoEstado) {
        Pagamento pagamento = pagamentoRepository.findById(pagamentoId)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado: " + pagamentoId));
        pagamento.setEstadoPagamento(novoEstado);
        return pagamentoRepository.save(pagamento);
    }
}