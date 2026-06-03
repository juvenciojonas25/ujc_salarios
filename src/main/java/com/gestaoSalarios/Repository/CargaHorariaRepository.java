package com.gestaoSalarios.Repository;

import com.gestaoSalarios.Model.CargaHoraria;
import com.gestaoSalarios.Model.Disciplina;
import com.gestaoSalarios.Model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CargaHorariaRepository extends JpaRepository<CargaHoraria, String> {

    // Horas lecionadas por docente num determinado mês (ano-mês, ex: "2025-04")
    @Query("SELECT ch FROM CargaHoraria ch WHERE ch.docente = :docente AND FUNCTION('DATE_FORMAT', ch.dataAula, '%Y-%m') = :mes")
    List<CargaHoraria> findByDocenteAndMes(@Param("docente") Docente docente, @Param("mes") String mes);

    // Todas as cargas horárias de um docente
    List<CargaHoraria> findByDocente(Docente docente);

    // Todas as cargas horárias de uma disciplina
    List<CargaHoraria> findByDisciplina(Disciplina disciplina);

    // Somar total de horas lecionadas por docente num determinado mês
    @Query("SELECT SUM(ch.horasLecionadas) FROM CargaHoraria ch WHERE ch.docente = :docente AND FUNCTION('DATE_FORMAT', ch.dataAula, '%Y-%m') = :mes")
    Double sumHorasLecionadasByDocenteAndMes(@Param("docente") Docente docente, @Param("mes") String mes);
}