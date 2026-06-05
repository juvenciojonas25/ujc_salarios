package com.gestaoSalarios.Service;

import com.gestaoSalarios.Model.Docente;
import com.gestaoSalarios.Model.Role;
import com.gestaoSalarios.Model.User;
import com.gestaoSalarios.Repository.DocenteRepository;
import com.gestaoSalarios.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registrarUsuario(String username, String password, Role role) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username já existe: " + username);
        }
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
        return userRepository.save(user);
    }

    public User registrarDocenteUser(String username, String password, String docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente não encontrado: " + docenteId));
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username já existe: " + username);
        }
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.DOCENTE)
                .docente(docente)
                .build();
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }
}