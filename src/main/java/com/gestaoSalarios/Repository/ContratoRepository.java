package com.gestaoSalarios.Repository;

import com.gestaoSalarios.Model.Contrato;
import com.gestaoSalarios.Model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, String> {

    // Contratos ativos de um docente (data_fim nula ou data_fim >= data atual)
    List<Contrato> findByDocenteAndDataFimIsNullOrDataFimGreaterThanEqual(Docente docente, LocalDate dataAtual);

    // Todos os contratos de um docente
    List<Contrato> findByDocente(Docente docente);

    // Buscar contrato por número (único)
    Contrato findByNumero(String numero);
}