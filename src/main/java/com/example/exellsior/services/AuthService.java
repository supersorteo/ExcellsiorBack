package com.example.exellsior.services;

import com.example.exellsior.configuration.BCrypt;
import com.example.exellsior.entity.AdminUser;
import com.example.exellsior.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Transactional
    public void register(String username, String password) {
        if (adminUserRepository.count() > 0) {
            throw new RuntimeException("Ya existe un usuario administrador");
        }

        AdminUser user = new AdminUser();
        user.setUsername(username);
        user.setPassword(password);  // ← Guardamos en texto plano
        adminUserRepository.save(user);
    }

    // Login
    public AdminUser login(String username, String password) {
        Optional<AdminUser> userOpt = adminUserRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        AdminUser user = userOpt.get();
        if (!user.getPassword().equals(password)) {  // ← Comparación directa
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        return user;
    }

    // Cambiar contraseña
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        AdminUser user = login(username, oldPassword);
        user.setPassword(newPassword);  // ← Guardamos nueva en texto plano
        adminUserRepository.save(user);
    }
}
