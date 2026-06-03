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

    /**
     * Registar um novo docente.
     * Gera ID automaticamente (DOC001, DOC002...)
     * Verifica unicidade de email e NUIT.
     */
    @Transactional
    public Docente registrarDocente(Docente docente) {
        // Verificar se email já existe
        if (docenteRepository.existsByEmail(docente.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + docente.getEmail());
        }
        // Verificar se NUIT já existe
        if (docenteRepository.existsByNuit(docente.getNuit())) {
            throw new RuntimeException("NUIT já cadastrado: " + docente.getNuit());
        }

        // Gerar próximo ID
        String ultimoId = docenteRepository.findAll().stream()
                .map(Docente::getId)
                .reduce((first, second) -> second) // pega o último ID
                .orElse(null);
        String novoId = Docente.gerarProximoId(ultimoId);
        docente.setId(novoId);

        return docenteRepository.save(docente);
    }

    /**
     * Listar todos os docentes.
     */
    public List<Docente> listarDocentes() {
        return docenteRepository.findAll();
    }

    /**
     * Actualizar dados de um docente.
     */
    @Transactional
    public Docente atualizarDocente(String id, Docente docenteAtualizado) {
        Docente docenteExistente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado com ID: " + id));

        // Validar email único (se foi alterado)
        if (!docenteExistente.getEmail().equals(docenteAtualizado.getEmail()) &&
                docenteRepository.existsByEmail(docenteAtualizado.getEmail())) {
            throw new RuntimeException("Email já está em uso: " + docenteAtualizado.getEmail());
        }

        // Validar NUIT único (se foi alterado)
        if (!docenteExistente.getNuit().equals(docenteAtualizado.getNuit()) &&
                docenteRepository.existsByNuit(docenteAtualizado.getNuit())) {
            throw new RuntimeException("NUIT já está em uso: " + docenteAtualizado.getNuit());
        }

        // Atualizar campos permitidos
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

    /**
     * Remover um docente.
     * Verifica se existem dependências (disciplinas, contratos, etc.)? Por simplicidade, apenas remove.
     * Mas na prática, você pode lançar exceção se houver registros ligados.
     */
    @Transactional
    public void removerDocente(String id) {
        if (!docenteRepository.existsById(id)) {
            throw new RuntimeException("Docente não encontrado com ID: " + id);
        }
        docenteRepository.deleteById(id);
    }

    /**
     * Consultar docente por ID.
     */
    public Docente consultarPorId(String id) {
        return docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado com ID: " + id));
    }

    /**
     * Consultar docente por nome (ignora maiúsculas/minúsculas, busca parcial).
     */
    public List<Docente> consultarPorNome(String nome) {
        List<Docente> docentes = docenteRepository.findByNomeContainingIgnoreCase(nome);
        if (docentes.isEmpty()) {
            throw new RuntimeException("Nenhum docente encontrado com nome: " + nome);
        }
        return docentes;
    }
}