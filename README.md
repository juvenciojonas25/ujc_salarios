```markdown
# 💼 Sistema de Gestão de Salários de Docentes

## 📌 Descrição do Projeto

Este projeto é uma API REST desenvolvida com **Spring Boot** para a gestão de salários de docentes de uma instituição académica.  
O sistema permite gerir docentes, disciplinas, contratos, carga horária e processamento de pagamentos, com controlo de acesso por perfis de utilizador.

---

## ⚙️ Funcionalidades

- Gestão de docentes (CRUD)
- Gestão de disciplinas (CRUD)
- Gestão de contratos de trabalho
- Registo de carga horária dos docentes
- Processamento de pagamentos
- Autenticação e autorização de utilizadores
- Controle de acesso por perfis (ADMIN, FINANCEIRO, DOCENTE)
- Inicialização automática de dados para testes
- API documentada com Swagger/OpenAPI

---

## 🛠️ Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- Hibernate
- MySQL
- Lombok
- Swagger / OpenAPI
- Maven

---

## 🧱 Arquitetura do Projeto

O projeto segue uma arquitetura em camadas:

```
┌─────────────────────────────────────────┐
│           Config (Security, Swagger)    │
├─────────────────────────────────────────┤
│   Controller → Endpoints REST           │
├─────────────────────────────────────────┤
│   Service → Regras de negócio           │
├─────────────────────────────────────────┤
│   Repository → Acesso a dados (JPA)     │
├─────────────────────────────────────────┤
│   Model → Entidades (Docente, User, etc)│
├─────────────────────────────────────────┤
│   Exception → Tratamento de erros       │
└─────────────────────────────────────────┘
```

### Estrutura de pacotes (exemplo)

```
com.ujc.salarios
├── config
├── controller
├── service
├── repository
├── model
├── security
└── exception
```

---

## 🔐 Autenticação e Perfis

O sistema possui autenticação baseada em Spring Security.

### Perfis disponíveis:

- **ADMIN** → Acesso total ao sistema  
- **FINANCEIRO** → Gestão de pagamentos  
- **DOCENTE** → Visualização de dados próprios  

---

## 📊 Entidades Principais

- Docente
- Disciplina
- Contrato
- CargaHoraria
- Pagamento
- User
- Role

---

## 🚀 Como Executar o Projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/juvenciojonas25/ujc_salarios.git
```

### 2. Aceder à pasta do projeto

```bash
cd ujc_salarios
```

### 3. Criar a base de dados (MySQL)

```sql
CREATE DATABASE ujc_salarios;
```

### 4. Configurar ligação à base de dados (MySQL)

Editar o ficheiro:

```
src/main/resources/application.properties
```

Exemplo de configuração para MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ujc_salarios?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

> **Nota:** Certifica-te de que o conector MySQL está no `pom.xml`:
> ```xml
> <dependency>
>     <groupId>mysql</groupId>
>     <artifactId>mysql-connector-java</artifactId>
>     <scope>runtime</scope>
> </dependency>
> ```

### 5. Executar a aplicação

```bash
mvn spring-boot:run
```

ou

```bash
./mvnw spring-boot:run
```

---

## 📚 Documentação da API

Após iniciar o projeto, aceder:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 👤 Utilizadores padrão (DataInitializer)

O sistema cria automaticamente utilizadores para testes:

| Perfil     | Username  | Password  |
|------------|-----------|-----------|
| ADMIN      | admin     | admin123  |
| FINANCEIRO | financeiro| fin123    |
| DOCENTE    | docente1  | doc123    |

---

## 📌 Endpoints principais e exemplos

### Autenticação

**POST** `/auth/login`

```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Resposta (exemplo com token JWT se implementado)**:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "role": "ADMIN"
}
```

### Docentes

| Método | Endpoint               | Descrição           |
|--------|------------------------|---------------------|
| GET    | `/docentes`            | Listar todos        |
| POST   | `/docentes`            | Criar novo docente  |
| PUT    | `/docentes/{id}`       | Atualizar docente   |
| DELETE | `/docentes/{id}`       | Remover docente     |

### Disciplinas

| Método | Endpoint               | Descrição           |
|--------|------------------------|---------------------|
| GET    | `/disciplinas`         | Listar disciplinas  |
| POST   | `/disciplinas`         | Criar disciplina    |

### Pagamentos

| Método | Endpoint               | Descrição           |
|--------|------------------------|---------------------|
| GET    | `/pagamentos`          | Listar pagamentos   |
| POST   | `/pagamentos`          | Processar pagamento |

---

## 📈 Melhorias Futuras

- Implementação de JWT (token authentication)
- Relatórios em PDF
- Dashboard administrativo
- Paginação e filtros avançados
- Testes automatizados (JUnit + Mockito)

---

## 👨‍💻 Autor

Projeto académico desenvolvido para fins de avaliação.
```