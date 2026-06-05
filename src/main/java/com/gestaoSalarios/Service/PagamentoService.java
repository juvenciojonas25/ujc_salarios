package com.gestaoSalarios.Service;

import com.gestaoSalarios.Model.*;
import com.gestaoSalarios.Repository.CargaHorariaRepository;
import com.gestaoSalarios.Repository.ContratoRepository;
import com.gestaoSalarios.Repository.DocenteRepository;
import com.gestaoSalarios.Repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private CargaHorariaRepository cargaHorariaRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    private double calcularDescontoIRPS(double salarioBruto) {
        if (salarioBruto <= 50000) return salarioBruto * 0.10;
        else if (salarioBruto <= 100000) return salarioBruto * 0.15;
        else return salarioBruto * 0.20;
    }

    @Transactional
    public Pagamento processarPagamento(String docenteId, int ano, int mes) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));

        String mesStr = String.format("%d-%02d", ano, mes);
        if (pagamentoRepository.existsByDocenteAndMes(docente, mesStr)) {
            throw new RuntimeException("Pagamento já processado para o docente " + docenteId + " no mês " + mesStr);
        }

        // Buscar contrato ativo na data do primeiro dia do mês
        LocalDate dataReferencia = LocalDate.of(ano, mes, 1);
        List<Contrato> contratosAtivos = contratoRepository.findByDocenteAndDataFimIsNullOrDataFimGreaterThanEqual(docente, dataReferencia);
        Double valorHora;
        if (!contratosAtivos.isEmpty()) {
            // Pega o contrato com maior dataInicio (mais recente)
            valorHora = contratosAtivos.stream()
                    .max((c1, c2) -> c1.getDataInicio().compareTo(c2.getDataInicio()))
                    .get().getValorPagoPorHora();
        } else {
            valorHora = docente.getValorPagoPorHora();
        }

        Double totalHoras = cargaHorariaRepository.sumHorasLecionadasByDocenteAndMes(docente, ano, mes);
        if (totalHoras == null || totalHoras == 0) {
            throw new RuntimeException("Nenhuma hora lecionada encontrada para o docente no mês " + mesStr);
        }

        double salarioBruto = totalHoras * valorHora;
        double desconto = calcularDescontoIRPS(salarioBruto);
        double salarioLiquido = salarioBruto - desconto;

        Pagamento pagamento = Pagamento.builder()
                .docente(docente)
                .referenciaPagamento(gerarReferencia(docenteId, ano, mes))
                .mes(mesStr)
                .totalHoras(totalHoras)
                .valorHora(valorHora)
                .salarioBruto(salarioBruto)
                .descontoIrps(desconto)
                .salarioLiquido(salarioLiquido)
                .dataProcessamento(LocalDateTime.now())
                .estadoPagamento(EstadoPagamento.PROCESSADO)
                .build();

        return pagamentoRepository.save(pagamento);
    }

    private String gerarReferencia(String docenteId, int ano, int mes) {
        return "REF-" + docenteId + "-" + ano + "-" + mes;
    }

    public List<Pagamento> listarPagamentos() {
        return pagamentoRepository.findAll();
    }

    public Pagamento buscarPorId(String id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado: " + id));
    }

    public List<Pagamento> listarPagamentosPorDocente(String docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        return pagamentoRepository.findByDocente(docente);
    }

    @Transactional
    public Pagamento alterarEstadoPagamento(String pagamentoId, EstadoPagamento novoEstado) {
        Pagamento pagamento = pagamentoRepository.findById(pagamentoId)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado: " + pagamentoId));
        pagamento.setEstadoPagamento(novoEstado);
        return pagamentoRepository.save(pagamento);
    }
}