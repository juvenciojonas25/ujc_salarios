package com.gestaoSalarios.Service;

import com.gestaoSalarios.Model.Docente;
import com.gestaoSalarios.Repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Transactional
    public Docente registrarDocente(Docente docente) {
        if (docenteRepository.existsByEmail(docente.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + docente.getEmail());
        }
        if (docenteRepository.existsByNuit(docente.getNuit())) {
            throw new RuntimeException("NUIT já cadastrado: " + docente.getNuit());
        }
        return docenteRepository.save(docente);
    }

    public List<Docente> listarDocentes() {
        return docenteRepository.findAll();
    }

    @Transactional
    public Docente atualizarDocente(String id, Docente docenteAtualizado) {
        Docente docenteExistente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado com ID: " + id));

        if (!docenteExistente.getEmail().equals(docenteAtualizado.getEmail()) &&
                docenteRepository.existsByEmail(docenteAtualizado.getEmail())) {
            throw new RuntimeException("Email já está em uso: " + docenteAtualizado.getEmail());
        }
        if (!docenteExistente.getNuit().equals(docenteAtualizado.getNuit()) &&
                docenteRepository.existsByNuit(docenteAtualizado.getNuit())) {
            throw new RuntimeException("NUIT já está em uso: " + docenteAtualizado.getNuit());
        }

        docenteExistente.setNome(docenteAtualizado.getNome());
        docenteExistente.setApelido(docenteAtualizado.getApelido());
        docenteExistente.setNuit(docenteAtualizado.getNuit());
        docenteExistente.setGenero(docenteAtualizado.getGenero());
        docenteExistente.setEmail(docenteAtualizado.getEmail());
        docenteExistente.setValorPagoPorHora(docenteAtualizado.getValorPagoPorHora());
        docenteExistente.setBanco(docenteAtualizado.getBanco());
        docenteExistente.setNib(docenteAtualizado.getNib());
        docenteExistente.setCategoria(docenteAtualizado.getCategoria());

        return docenteRepository.save(docenteExistente);
    }

    @Transactional
    public void removerDocente(String id) {
        if (!docenteRepository.existsById(id)) {
            throw new RuntimeException("Docente não encontrado com ID: " + id);
        }
        docenteRepository.deleteById(id);
    }

    public Docente consultarPorId(String id) {
        return docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado com ID: " + id));
    }

    public List<Docente> consultarPorNome(String nome) {
        List<Docente> docentes = docenteRepository.findByNomeContainingIgnoreCase(nome);
        if (docentes.isEmpty()) {
            throw new RuntimeException("Nenhum docente encontrado com nome: " + nome);
        }
        return docentes;
    }
}