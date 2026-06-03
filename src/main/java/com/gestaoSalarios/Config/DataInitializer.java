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
        if (!docenteRepository.existsById("DOC001")) {
            Docente d1 = Docente.builder()
                    .id("DOC001")
                    .nome("João")
                    .apelido("Silva")
                    .nuit("123456789")
                    .genero("M")
                    .email("joao@ujc.mz")
                    .valorPagoPorHora(500.0)
                    .banco("BCI")
                    .nib("1234567890123")
                    .categoria("Assistente")
                    .build();
            docenteRepository.save(d1);
        }
        if (!docenteRepository.existsById("DOC002")) {
            Docente d2 = Docente.builder()
                    .id("DOC002")
                    .nome("Maria")
                    .apelido("Santos")
                    .nuit("987654321")
                    .genero("F")
                    .email("maria@ujc.mz")
                    .valorPagoPorHora(750.0)
                    .banco("BIM")
                    .nib("3210987654321")
                    .categoria("Coordenador")
                    .build();
            docenteRepository.save(d2);
        }

        Docente docente1 = docenteRepository.findById("DOC001").get();
        Docente docente2 = docenteRepository.findById("DOC002").get();

        // ========== DISCIPLINAS ==========
        if (disciplinaRepository.findByCodigo("INF101") == null) {
            Disciplina disc1 = Disciplina.builder()
                    .id("DISC001")
                    .nome("Programação Java")
                    .codigo("INF101")
                    .curso("Informática")
                    .cargaHoraria(60)
                    .docente(docente1)
                    .build();
            disciplinaRepository.save(disc1);
        }
        if (disciplinaRepository.findByCodigo("INF102") == null) {
            Disciplina disc2 = Disciplina.builder()
                    .id("DISC002")
                    .nome("Bases de Dados")
                    .codigo("INF102")
                    .curso("Informática")
                    .cargaHoraria(45)
                    .docente(docente2)
                    .build();
            disciplinaRepository.save(disc2);
        }

        Disciplina disciplina1 = disciplinaRepository.findByCodigo("INF101");
        Disciplina disciplina2 = disciplinaRepository.findByCodigo("INF102");

        // ========== CONTRATOS ==========
        if (contratoRepository.findByNumero("CT-001") == null) {
            Contrato c1 = Contrato.builder()
                    .id("CON001")
                    .numero("CT-001")
                    .docente(docente1)
                    .dataInicio(LocalDate.of(2025, 1, 1))
                    .dataFim(LocalDate.of(2025, 12, 31))
                    .valorPagoPorHora(500.0)
                    .cargaHorariaPrevista(150)
                    .build();
            contratoRepository.save(c1);
        }
        if (contratoRepository.findByNumero("CT-002") == null) {
            Contrato c2 = Contrato.builder()
                    .id("CON002")
                    .numero("CT-002")
                    .docente(docente2)
                    .dataInicio(LocalDate.of(2025, 1, 1))
                    .dataFim(null)
                    .valorPagoPorHora(750.0)
                    .cargaHorariaPrevista(120)
                    .build();
            contratoRepository.save(c2);
        }

        // ========== CARGA HORÁRIA (Aulas em Abril/2025) ==========
        if (cargaHorariaRepository.findById("CH001").isEmpty()) {
            cargaHorariaRepository.save(CargaHoraria.builder()
                    .id("CH001").docente(docente1).disciplina(disciplina1)
                    .dataAula(LocalDate.of(2025, 4, 1)).horasLecionadas(3.0).build());
        }
        if (cargaHorariaRepository.findById("CH002").isEmpty()) {
            cargaHorariaRepository.save(CargaHoraria.builder()
                    .id("CH002").docente(docente1).disciplina(disciplina1)
                    .dataAula(LocalDate.of(2025, 4, 8)).horasLecionadas(3.0).build());
        }
        if (cargaHorariaRepository.findById("CH003").isEmpty()) {
            cargaHorariaRepository.save(CargaHoraria.builder()
                    .id("CH003").docente(docente1).disciplina(disciplina1)
                    .dataAula(LocalDate.of(2025, 4, 15)).horasLecionadas(2.5).build());
        }
        if (cargaHorariaRepository.findById("CH004").isEmpty()) {
            cargaHorariaRepository.save(CargaHoraria.builder()
                    .id("CH004").docente(docente2).disciplina(disciplina2)
                    .dataAula(LocalDate.of(2025, 4, 5)).horasLecionadas(4.0).build());
        }
        if (cargaHorariaRepository.findById("CH005").isEmpty()) {
            cargaHorariaRepository.save(CargaHoraria.builder()
                    .id("CH005").docente(docente2).disciplina(disciplina2)
                    .dataAula(LocalDate.of(2025, 4, 12)).horasLecionadas(4.0).build());
        }

        // ========== USUÁRIOS ==========
        if (userRepository.findByUsername("admin").isEmpty()) {
            userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build());
            System.out.println("Admin criado: admin / admin123");
        }
        if (userRepository.findByUsername("financeiro").isEmpty()) {
            userRepository.save(User.builder()
                    .username("financeiro")
                    .password(passwordEncoder.encode("fin123"))
                    .role(Role.FINANCEIRO)
                    .build());
            System.out.println("Financeiro criado: financeiro / fin123");
        }
        if (userRepository.findByUsername("docente1").isEmpty()) {
            userRepository.save(User.builder()
                    .username("docente1")
                    .password(passwordEncoder.encode("doc123"))
                    .role(Role.DOCENTE)
                    .docente(docente1)
                    .build());
            System.out.println("Usuário docente criado: docente1 / doc123");
        }
    }
}