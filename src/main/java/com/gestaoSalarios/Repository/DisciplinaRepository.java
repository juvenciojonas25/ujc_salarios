package com.gestaoSalarios.Repository;

import com.gestaoSalarios.Model.Disciplina;
import com.gestaoSalarios.Model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, String> {

    // Listar disciplinas de um determinado docente
    List<Disciplina> findByDocente(Docente docente);

    // Buscar disciplina por código (único)
    Disciplina findByCodigo(String codigo);

    // Listar disciplinas de um determinado curso
    List<Disciplina> findByCurso(String curso);
}