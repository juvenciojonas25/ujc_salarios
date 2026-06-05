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
import java.util.List;

@Service
public class CargaHorariaService {

    @Autowired
    private CargaHorariaRepository cargaHorariaRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Transactional
    public CargaHoraria registrarCargaHoraria(CargaHoraria cargaHoraria, String docenteId, String disciplinaId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada: " + disciplinaId));

        cargaHoraria.setDocente(docente);
        cargaHoraria.setDisciplina(disciplina);
        return cargaHorariaRepository.save(cargaHoraria);
    }

    public Double getTotalHorasPorDocenteEMes(String docenteId, int ano, int mes) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        Double total = cargaHorariaRepository.sumHorasLecionadasByDocenteAndMes(docente, ano, mes);
        return total != null ? total : 0.0;
    }

    public List<CargaHoraria> listarPorDocente(String docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        return cargaHorariaRepository.findByDocente(docente);
    }

    @Transactional
    public void removerCargaHoraria(String id) {
        if (!cargaHorariaRepository.existsById(id)) {
            throw new RuntimeException("Registo de carga horária não encontrado: " + id);
        }
        cargaHorariaRepository.deleteById(id);
    }
}