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

    @Transactional
    public Contrato registrarContrato(Contrato contrato, String docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        contrato.setDocente(docente);
        return contratoRepository.save(contrato);
    }

    public List<Contrato> listarContratosAtivosPorDocente(String docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        return contratoRepository.findByDocenteAndDataFimIsNullOrDataFimGreaterThanEqual(docente, LocalDate.now());
    }

    public List<Contrato> listarContratosPorDocente(String docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        return contratoRepository.findByDocente(docente);
    }
}