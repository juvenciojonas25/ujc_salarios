package com.gestaoSalarios.Service;

import com.gestaoSalarios.Model.Disciplina;
import com.gestaoSalarios.Model.Docente;
import com.gestaoSalarios.Repository.DisciplinaRepository;
import com.gestaoSalarios.Repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Transactional
    public Disciplina cadastrarDisciplina(Disciplina disciplina, String docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        disciplina.setDocente(docente);
        return disciplinaRepository.save(disciplina);
    }

    @Transactional
    public Disciplina associarDocente(String disciplinaId, String docenteId) {
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada: " + disciplinaId));
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        disciplina.setDocente(docente);
        return disciplinaRepository.save(disciplina);
    }

    @Transactional
    public Disciplina atribuirHorasAoDocente(String disciplinaId, Integer cargaHoraria) {
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada: " + disciplinaId));
        disciplina.setCargaHoraria(cargaHoraria);
        return disciplinaRepository.save(disciplina);
    }

    public List<Disciplina> listarDisciplinas() {
        return disciplinaRepository.findAll();
    }

    public Disciplina buscarPorId(String id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada: " + id));
    }

    @Transactional
    public void removerDisciplina(String id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new RuntimeException("Disciplina não encontrada: " + id);
        }
        disciplinaRepository.deleteById(id);
    }
}