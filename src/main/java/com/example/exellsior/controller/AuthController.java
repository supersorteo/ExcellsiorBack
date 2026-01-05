package com.example.exellsior.controller;

import com.example.exellsior.configuration.UserResponse;
import com.example.exellsior.entity.AdminUser;
import com.example.exellsior.repository.AdminUserRepository;
import com.example.exellsior.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> body) {
        try {
            authService.register(body.get("username"), body.get("password"));
            return ResponseEntity.ok("Usuario creado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
        try {
            authService.login(body.get("username"), body.get("password"));
            return ResponseEntity.ok("Login exitoso");
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> body) {
        try {
            authService.changePassword(body.get("username"), body.get("oldPassword"), body.get("newPassword"));
            return ResponseEntity.ok("Contraseña cambiada");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET todos los usuarios (devuelve TODO, incluyendo contraseña en texto plano)
    @GetMapping("/users")
    public ResponseEntity<List<AdminUser>> getAllUsers() {
        List<AdminUser> users = adminUserRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // GET usuario por ID (devuelve TODO, incluyendo contraseña en texto plano)
    @GetMapping("/users/{id}")
    public ResponseEntity<AdminUser> getUserById(@PathVariable Long id) {
        Optional<AdminUser> userOpt = adminUserRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userOpt.get());
    }

    // Eliminar usuario por ID
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            if (!adminUserRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }

            adminUserRepository.deleteById(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}
