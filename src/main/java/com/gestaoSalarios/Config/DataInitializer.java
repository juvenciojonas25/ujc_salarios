package com.gestaoSalarios.Config;

import com.gestaoSalarios.Model.*;
import com.gestaoSalarios.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private DocenteRepository docenteRepository;
    @Autowired private DisciplinaRepository disciplinaRepository;
    @Autowired private ContratoRepository contratoRepository;
    @Autowired private CargaHorariaRepository cargaHorariaRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // ========== DOCENTES ==========
        Docente d1 = null;
        if (docenteRepository.findByEmail("joao@ujc.mz").isEmpty()) {
            d1 = Docente.builder()
                    .nome("João").apelido("Silva").nuit("123456789").genero("M")
                    .email("joao@ujc.mz").valorPagoPorHora(500.0).banco("BCI")
                    .nib("1234567890123").categoria("Assistente").build();
            d1 = docenteRepository.save(d1);
        } else {
            d1 = docenteRepository.findByEmail("joao@ujc.mz").get();
        }

        Docente d2 = null;
        if (docenteRepository.findByEmail("maria@ujc.mz").isEmpty()) {
            d2 = Docente.builder()
                    .nome("Maria").apelido("Santos").nuit("987654321").genero("F")
                    .email("maria@ujc.mz").valorPagoPorHora(750.0).banco("BIM")
                    .nib("3210987654321").categoria("Coordenador").build();
            d2 = docenteRepository.save(d2);
        } else {
            d2 = docenteRepository.findByEmail("maria@ujc.mz").get();
        }

        // ========== DISCIPLINAS ==========
        Disciplina disc1 = null;
        if (disciplinaRepository.findByCodigo("INF101") == null) {
            disc1 = Disciplina.builder()
                    .nome("Programação Java").codigo("INF101").curso("Informática")
                    .cargaHoraria(60).docente(d1).build();
            disc1 = disciplinaRepository.save(disc1);
        } else {
            disc1 = disciplinaRepository.findByCodigo("INF101");
        }

        Disciplina disc2 = null;
        if (disciplinaRepository.findByCodigo("INF102") == null) {
            disc2 = Disciplina.builder()
                    .nome("Bases de Dados").codigo("INF102").curso("Informática")
                    .cargaHoraria(45).docente(d2).build();
            disc2 = disciplinaRepository.save(disc2);
        } else {
            disc2 = disciplinaRepository.findByCodigo("INF102");
        }

        // ========== CONTRATOS ==========
        if (contratoRepository.findByNumero("CT-001") == null) {
            Contrato c1 = Contrato.builder()
                    .numero("CT-001").docente(d1)
                    .dataInicio(LocalDate.of(2025, 1, 1)).dataFim(LocalDate.of(2025, 12, 31))
                    .valorPagoPorHora(500.0).cargaHorariaPrevista(150).build();
            contratoRepository.save(c1);
        }
        if (contratoRepository.findByNumero("CT-002") == null) {
            Contrato c2 = Contrato.builder()
                    .numero("CT-002").docente(d2)
                    .dataInicio(LocalDate.of(2025, 1, 1)).dataFim(null)
                    .valorPagoPorHora(750.0).cargaHorariaPrevista(120).build();
            contratoRepository.save(c2);
        }

        // ========== CARGA HORÁRIA ==========
        if (cargaHorariaRepository.findAll().isEmpty()) {
            cargaHorariaRepository.save(CargaHoraria.builder()
                    .docente(d1).disciplina(disc1).dataAula(LocalDate.of(2025, 4, 1)).horasLecionadas(3.0).build());
            cargaHorariaRepository.save(CargaHoraria.builder()
                    .docente(d1).disciplina(disc1).dataAula(LocalDate.of(2025, 4, 8)).horasLecionadas(3.0).build());
            cargaHorariaRepository.save(CargaHoraria.builder()
                    .docente(d1).disciplina(disc1).dataAula(LocalDate.of(2025, 4, 15)).horasLecionadas(2.5).build());
            cargaHorariaRepository.save(CargaHoraria.builder()
                    .docente(d2).disciplina(disc2).dataAula(LocalDate.of(2025, 4, 5)).horasLecionadas(4.0).build());
            cargaHorariaRepository.save(CargaHoraria.builder()
                    .docente(d2).disciplina(disc2).dataAula(LocalDate.of(2025, 4, 12)).horasLecionadas(4.0).build());
        }

        // ========== USUÁRIOS ==========
        if (userRepository.findByUsername("admin").isEmpty()) {
            userRepository.save(User.builder()
                    .username("admin").password(passwordEncoder.encode("admin123")).role(Role.ADMIN).build());
            System.out.println("Admin criado: admin / admin123");
        }
        if (userRepository.findByUsername("financeiro").isEmpty()) {
            userRepository.save(User.builder()
                    .username("financeiro").password(passwordEncoder.encode("fin123")).role(Role.FINANCEIRO).build());
            System.out.println("Financeiro criado: financeiro / fin123");
        }
        if (userRepository.findByUsername("docente1").isEmpty() && d1 != null) {
            userRepository.save(User.builder()
                    .username("docente1").password(passwordEncoder.encode("doc123")).role(Role.DOCENTE).docente(d1).build());
            System.out.println("Usuário docente criado: docente1 / doc123");
        }
    }
}