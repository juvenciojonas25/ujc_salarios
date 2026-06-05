package com.gestaoSalarios.Repository;

import com.gestaoSalarios.Model.CargaHoraria;
import com.gestaoSalarios.Model.Disciplina;
import com.gestaoSalarios.Model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargaHorariaRepository extends JpaRepository<CargaHoraria, String> {

    List<CargaHoraria> findByDocente(Docente docente);

    List<CargaHoraria> findByDisciplina(Disciplina disciplina);

    @Query("SELECT SUM(ch.horasLecionadas) FROM CargaHoraria ch WHERE ch.docente = :docente AND YEAR(ch.dataAula) = :ano AND MONTH(ch.dataAula) = :mes")
    Double sumHorasLecionadasByDocenteAndMes(@Param("docente") Docente docente, @Param("ano") int ano, @Param("mes") int mes);
}