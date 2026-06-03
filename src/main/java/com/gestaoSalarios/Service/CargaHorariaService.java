package com.gestaoSalarios.Service;

import com.gestaoSalarios.Model.CargaHoraria;
import com.gestaoSalarios.Model.Disciplina;
import com.gestaoSalarios.Model.Docente;
import com.gestaoSalarios.Repository.CargaHorariaRepository;
import com.gestaoSalarios.Repository.DisciplinaRepository;
import com.gestaoSalarios.Repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CargaHorariaService {

    @Autowired
    private CargaHorariaRepository cargaHorariaRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    /**
     * Registrar horas lecionadas (uma aula).
     * Gera ID automaticamente (CH001...)
     */
    @Transactional
    public CargaHoraria registrarCargaHoraria(CargaHoraria cargaHoraria, String docenteId, String disciplinaId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada: " + disciplinaId));

        cargaHoraria.setDocente(docente);
        cargaHoraria.setDisciplina(disciplina);

        // Gerar ID
        String ultimoId = cargaHorariaRepository.findAll().stream()
                .map(CargaHoraria::getId)
                .reduce((first, second) -> second)
                .orElse(null);
        String novoId = CargaHoraria.gerarProximoId(ultimoId);
        cargaHoraria.setId(novoId);

        return cargaHorariaRepository.save(cargaHoraria);
    }

    /**
     * Obter total de horas lecionadas por um docente num determinado mês (formato "yyyy-MM").
     */
    public Double getTotalHorasPorDocenteEMes(String docenteId, String mes) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        Double total = cargaHorariaRepository.sumHorasLecionadasByDocenteAndMes(docente, mes);
        return total != null ? total : 0.0;
    }

    /**
     * Listar todas as cargas horárias de um docente.
     */
    public List<CargaHoraria> listarPorDocente(String docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        return cargaHorariaRepository.findByDocente(docente);
    }
}