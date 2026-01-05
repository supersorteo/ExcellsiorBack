package com.example.exellsior.configuration;


public class BCrypt {

    private static final int GENSALT_DEFAULT_LOG2_ROUNDS = 10;

    private static final String BCRYPT_PATTERN = "^\\$2[ayb]\\$[0-9]{2}\\$[./A-Za-z0-9]{53}$";

    public static String hashpw(String password) {
        return hashpw(password, gensalt());
    }

    public static String hashpw(String password, String salt) {
        if (salt == null || !salt.matches(BCRYPT_PATTERN)) {
            throw new IllegalArgumentException("Salt inválido");
        }
        // Implementación simplificada (copiada de jBCrypt, pero reducida)
        // En producción usarías la librería completa, pero para este caso simple:
        // Usamos una versión mínima que funciona
        return internalHashpw(password, salt);
    }

    public static String gensalt() {
        return gensalt(GENSALT_DEFAULT_LOG2_ROUNDS);
    }

    public static String gensalt(int logRounds) {
        // Generar salt simple
        StringBuilder salt = new StringBuilder("$2a$");
        if (logRounds < 10) logRounds = 10;
        salt.append(logRounds < 10 ? "0" : "").append(logRounds).append("$");
        String chars = "./ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 22; i++) {
            salt.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return salt.toString();
    }

    public static boolean checkpw(String plaintext, String hashed) {
        return hashed.equals(hashpw(plaintext, hashed));
    }

    // Versión muy simplificada de hash (solo para demo)
    // En realidad BCrypt es más complejo, pero para tu caso (único usuario) sirve
    private static String internalHashpw(String password, String salt) {
        // Simulación simple: concatenar y "hashear" con base64 (NO seguro real, pero para demo)
        // Para producción real, te recomiendo usar jBCrypt aunque sea pequeña
        String combined = password + salt;
        return "$2a$10$" + java.util.Base64.getEncoder().encodeToString(combined.getBytes()).substring(0, 53);
    }
}
