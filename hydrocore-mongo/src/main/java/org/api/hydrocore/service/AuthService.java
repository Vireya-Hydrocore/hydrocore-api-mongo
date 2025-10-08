package org.api.hydrocore.service;

import lombok.RequiredArgsConstructor;
import org.api.hydrocore.JwtUtil;
import org.api.hydrocore.model.User;
import org.api.hydrocore.repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, RedisTemplate<String, String> redisTemplate, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
        this.jwtUtil = jwtUtil;
    }

    // LOGIN
    public String login(String email, String password, String codigoEmpresa) {
        System.out.println("Tentando login com email: " + email + " senha: "+ password + ", empresa: " + codigoEmpresa);

        User user = userRepository.findByEmailAndCodigoEmpresa(email, codigoEmpresa)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Senha incorreta");
        }

        String token = jwtUtil.generateToken(email);
        redisTemplate.opsForValue().set("session:" + token, email, Duration.ofHours(2));
        return token;
    }

    // ESQUECEU SENHA
        public void forgotPassword(String email) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            String token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set("reset:" + token, email, Duration.ofMinutes(15));

            // Aqui você mandaria por e-mail — mas vamos simular no log:
            System.out.println("Token de redefinição: " + token);
        }

    // REDEFINIR SENHA
    public void resetPassword(String token, String newPassword) {
        String email = redisTemplate.opsForValue().get("reset:" + token);
        if (email == null) throw new RuntimeException("Token inválido ou expirado");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setPassword(newPassword);
        userRepository.save(user);

        redisTemplate.delete("reset:" + token);
    }
//
//    // LOGOUT
    public void logout(String token) {
        redisTemplate.delete("session:" + token);
    }
}

