package com.gestaoSalarios.Repository;

import com.gestaoSalarios.Model.Pagamento;
import com.gestaoSalarios.Model.Docente;
import com.gestaoSalarios.Model.EstadoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, String> {

    // Pagamentos de um docente
    List<Pagamento> findByDocente(Docente docente);

    // Pagamentos por estado (Processado, Pago, Pendente)
    List<Pagamento> findByEstadoPagamento(EstadoPagamento estado);

    // Buscar pagamento por referência (única)
    Optional<Pagamento> findByReferenciaPagamento(String referencia);

    // Verificar se já existe processamento para um docente e mês específicos
    boolean existsByDocenteAndMes(Docente docente, String mes);

    // Pagamentos de um docente num determinado mês
    Optional<Pagamento> findByDocenteAndMes(Docente docente, String mes);
}