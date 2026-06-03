package com.gestaoSalarios.Repository;

import com.gestaoSalarios.Model.User;
import com.gestaoSalarios.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar usuário por username (usado no login)
    Optional<User> findByUsername(String username);

    // Listar usuários por perfil (role)
    List<User> findByRole(Role role);

    // Verificar se username já existe
    boolean existsByUsername(String username);

    // Buscar usuário associado a um docente (para perfis DOCENTE)
    Optional<User> findByDocenteId(String docenteId);
}