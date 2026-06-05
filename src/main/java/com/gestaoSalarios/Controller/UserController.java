package com.gestaoSalarios.Controller;

import com.gestaoSalarios.Model.Role;
import com.gestaoSalarios.Model.User;
import com.gestaoSalarios.Service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> criarAdmin(@RequestParam @NotBlank String username,
                                           @RequestParam @NotBlank String password) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registrarUsuario(username, password, Role.ADMIN));
    }

    @PostMapping("/financeiro")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> criarFinanceiro(@RequestParam @NotBlank String username,
                                                @RequestParam @NotBlank String password) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registrarUsuario(username, password, Role.FINANCEIRO));
    }

    @PostMapping("/docente")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> criarDocenteUser(@RequestParam @NotBlank String username,
                                                 @RequestParam @NotBlank String password,
                                                 @RequestParam String docenteId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registrarDocenteUser(username, password, docenteId));
    }
}