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

    /**
     * Cadastro de disciplina com associação a um docente.
     * Gera ID automaticamente (DISC001...)
     */
    @Transactional
    public Disciplina cadastrarDisciplina(Disciplina disciplina, String docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado com ID: " + docenteId));
        disciplina.setDocente(docente);

        // Gerar próximo ID
        String ultimoId = disciplinaRepository.findAll().stream()
                .map(Disciplina::getId)
                .reduce((first, second) -> second)
                .orElse(null);
        String novoId = Disciplina.gerarProximoId(ultimoId);
        disciplina.setId(novoId);

        return disciplinaRepository.save(disciplina);
    }

    /**
     * Associar (ou alterar) docente responsável por uma disciplina.
     */
    @Transactional
    public Disciplina associarDocente(String disciplinaId, String docenteId) {
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada com ID: " + disciplinaId));
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado com ID: " + docenteId));
        disciplina.setDocente(docente);
        return disciplinaRepository.save(disciplina);
    }

    /**
     * Atribuir horas ao docente (na verdade é alterar a carga horária da disciplina).
     * O requisito "Atribuir horas ao Docente" pode ser interpretado como definir
     * a carga horária total da disciplina que o docente irá lecionar.
     */
    @Transactional
    public Disciplina atribuirHorasAoDocente(String disciplinaId, Integer cargaHoraria) {
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada com ID: " + disciplinaId));
        disciplina.setCargaHoraria(cargaHoraria);
        return disciplinaRepository.save(disciplina);
    }

    /**
     * Listar todas as disciplinas.
     */
    public List<Disciplina> listarDisciplinas() {
        return disciplinaRepository.findAll();
    }

    /**
     * Buscar disciplina por ID.
     */
    public Disciplina buscarPorId(String id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada: " + id));
    }
}