package com.gestaoSalarios.Service;

import com.gestaoSalarios.Model.Contrato;
import com.gestaoSalarios.Model.Docente;
import com.gestaoSalarios.Repository.ContratoRepository;
import com.gestaoSalarios.Repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    /**
     * Registrar um novo contrato para um docente.
     * Gera ID automaticamente (CON001...)
     */
    @Transactional
    public Contrato registrarContrato(Contrato contrato, String docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        contrato.setDocente(docente);

        // Gerar próximo ID
        String ultimoId = contratoRepository.findAll().stream()
                .map(Contrato::getId)
                .reduce((first, second) -> second)
                .orElse(null);
        String novoId = Contrato.gerarProximoId(ultimoId);
        contrato.setId(novoId);

        // Se data_fim for nula, considera-se ativo
        return contratoRepository.save(contrato);
    }

    /**
     * Listar contratos ativos de um docente (data_fim nula ou data_fim >= hoje).
     */
    public List<Contrato> listarContratosAtivosPorDocente(String docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        return contratoRepository.findByDocenteAndDataFimIsNullOrDataFimGreaterThanEqual(docente, LocalDate.now());
    }

    /**
     * Listar todos os contratos de um docente.
     */
    public List<Contrato> listarContratosPorDocente(String docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        return contratoRepository.findByDocente(docente);
    }
}