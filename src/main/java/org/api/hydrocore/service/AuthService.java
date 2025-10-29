package org.api.hydrocore.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.api.hydrocore.JwtUtil;
import org.api.hydrocore.dto.response.LoginResponse;
import org.api.hydrocore.model.User;
import org.api.hydrocore.repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository, RedisTemplate<String, String> redisTemplate, JwtUtil jwtUtil, EmailService emailService) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    public LoginResponse login(String email, String password, String codigoEmpresa) {
        User user = userRepository.findByEmailAndCodigoEmpresa(email, codigoEmpresa)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Senha incorreta");
        }

        String token = jwtUtil.generateToken(email);

        redisTemplate.opsForValue().set("token:" + email, "Bearer " + token, Duration.ofHours(2));

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setChaveApi(user.getChaveApi());

        return loginResponse;

    }

    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = jwtUtil.generateTokenWithExpiration(email, 15 * 60 * 1000);
        redisTemplate.opsForValue().set("reset:" + email, "Bearer " + token, Duration.ofMinutes(15));

        emailService.sendResetCode(email, token);

    }

    public void resetPassword(String email, String newPassword) {
        String token = redisTemplate.opsForValue().get("reset:" + email);
        if (token == null) throw new RuntimeException("Email inválido ou expirado");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setPassword(newPassword);
        userRepository.save(user);
    }


    public void logout(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String email = jwtUtil.extractEmail(token);
        long expirationMillis = jwtUtil.getRemainingExpiration(token);

        redisTemplate.opsForValue().set("blacklist:" + token, email, expirationMillis, TimeUnit.MILLISECONDS);

        redisTemplate.delete("token:" + email);
    }

}
