package org.api.hydrocore.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.api.hydrocore.JwtUtil;
import org.api.hydrocore.dto.response.LoginResponse;
import org.api.hydrocore.model.User;
import org.api.hydrocore.repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
        redisTemplate.opsForValue().set("session:" + token, email, Duration.ofHours(2));

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setChaveApi(user.getChaveApi());

        return loginResponse;

    }

    public void forgotPassword(String email) throws UnirestException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = jwtUtil.generateTokenWithExpiration(email, 15 * 60 * 1000);
        redisTemplate.opsForValue().set("reset:" + token, email, Duration.ofMinutes(15));

        emailService.sendResetPasswordEmail(email, token);
    }

    public void resetPassword(String token, String newPassword) {
        String email = redisTemplate.opsForValue().get("reset:" + token);
        if (email == null) throw new RuntimeException("Token inválido ou expirado");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setPassword(newPassword);
        userRepository.save(user);

        redisTemplate.delete("reset:" + token);
    }


    public void logout(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String email = jwtUtil.extractEmail(token);
        long expirationMillis = jwtUtil.getRemainingExpiration(token);

        redisTemplate.opsForValue().set("blacklist:" + token, email, expirationMillis, TimeUnit.MILLISECONDS);
    }

    public void salvarFcmToken(String authorizationHeader, String fcmToken) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new SecurityException("Authorization header inválido");
        }
        String jwt = authorizationHeader.substring(7).trim();
        String email = jwtUtil.extractEmail(jwt);

        if (email == null || email.isBlank()) {
            throw new SecurityException("Token inválido ou usuário não encontrado");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setFcmToken(fcmToken);
        userRepository.save(user);
    }
}
