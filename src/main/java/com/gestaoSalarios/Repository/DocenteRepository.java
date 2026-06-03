package com.gestaoSalarios.Repository;

import com.gestaoSalarios.Model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, String> {

    // Consultar docente por nome (ignorando maiúsculas/minúsculas e buscando parte do nome)
    List<Docente> findByNomeContainingIgnoreCase(String nome);

    // Consultar docente por apelido
    List<Docente> findByApelidoContainingIgnoreCase(String apelido);

    // Consultar docente por email (único)
    Optional<Docente> findByEmail(String email);

    // Consultar docente por NUIT (único)
    Optional<Docente> findByNuit(String nuit);

    // Verificar se existe docente com determinado email
    boolean existsByEmail(String email);

    // Verificar se existe docente com determinado NUIT
    boolean existsByNuit(String nuit);
}
