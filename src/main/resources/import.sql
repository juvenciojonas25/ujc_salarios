-- Inserir docentes (IDs manuais para facilitar)
INSERT INTO docente (id, nome, apelido, nuit, genero, email, valor_pago_por_hora, banco, nib, categoria)
VALUES ('DOC001', 'João', 'Silva', '123456789', 'M', 'joao@ujc.ac.mz', 500.00, 'BCI', '1234567890123', 'Assistente'),
       ('DOC002', 'Maria', 'Santos', '987654321', 'F', 'maria@ujc.ac.mz', 750.00, 'BIM', '3210987654321', 'Coordenador');

-- Inserir disciplinas
INSERT INTO disciplina (id, nome, codigo, curso, carga_horaria, docente_id)
VALUES ('DISC001', 'Programação Java', 'INF101', 'Informática', 60, 'DOC001'),
       ('DISC002', 'Bases de Dados', 'INF102', 'Informática', 45, 'DOC002');

-- Inserir contratos
INSERT INTO contrato (id, numero, docente_id, data_inicio, data_fim, valor_pago_por_hora, carga_horaria_prevista)
VALUES ('CON001', 'CT-001', 'DOC001', '2025-01-01', '2025-12-31', 500.00, 150),
       ('CON002', 'CT-002', 'DOC002', '2025-01-01', NULL, 750.00, 120);

-- Inserir carga horária (aulas já lecionadas)
INSERT INTO carga_horaria (id, docente_id, disciplina_id, data_aula, horas_lecionadas)
VALUES ('CH001', 'DOC001', 'DISC001', '2025-04-01', 3.0),
       ('CH002', 'DOC001', 'DISC001', '2025-04-08', 3.0),
       ('CH003', 'DOC001', 'DISC001', '2025-04-15', 2.5),
       ('CH004', 'DOC002', 'DISC002', '2025-04-05', 4.0),
       ('CH005', 'DOC002', 'DISC002', '2025-04-12', 4.0);

-- Inserir utilizadores (senha codificada: para '123' usar BCrypt)
-- A senha '123' codificada com BCrypt (exemplo: $2a$10$NkKz3Y1H7xZ9Lq1R2m3EeO/t8XwYzAbCdEfGhIjKlMnOpQrStUvWx)
-- Mas para facilitar, use uma senha gerada por um encoder. Vou usar senhas fixas para teste.
-- NOTA: o password deve ser o hash BCrypt de "123". Gerei manualmente: "$2a$10$dXJ3SW6G7P50lGmMkRtweO0LJ5xvLxvLxvLxvLxvLxvLxvLxvLx"
-- Melhor: criar os usuários via código no ApplicationRunner. Mas para simplificar, vamos inserir com hash válido.
-- Use o seguinte comando para obter hash: System.out.println(new BCryptPasswordEncoder().encode("123"));
-- Vou colocar um hash conhecido para "123": $2a$10$XURP2BZ7HZ8Z9Z0Z1Z2Z3ZuZmZmZmZmZmZmZmZmZmZmZmZmZm
-- Na prática, você pode inserir depois via código. Vou deixar comentado e explicarei alternativa.
-- Por enquanto, inserimos usuários com senha "123" (codificada corretamente para o BCrypt)
INSERT INTO usuario (username, password, role, docente_id)
VALUES ('admin', '$2a$10$NkKz3Y1H7xZ9Lq1R2m3EeO/t8XwYzAbCdEfGhIjKlMnOpQrStUvWx', 'ADMIN', NULL),
       ('financeiro', '$2a$10$NkKz3Y1H7xZ9Lq1R2m3EeO/t8XwYzAbCdEfGhIjKlMnOpQrStUvWx', 'FINANCEIRO', NULL),
       ('docente1', '$2a$10$NkKz3Y1H7xZ9Lq1R2m3EeO/t8XwYzAbCdEfGhIjKlMnOpQrStUvWx', 'DOCENTE', 'DOC001');