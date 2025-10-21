package org.api.hydrocore.controller.impl;

import jakarta.validation.Valid;
import org.api.hydrocore.controller.AuthController;
import org.api.hydrocore.dto.ForgotPasswordRequest;
import org.api.hydrocore.dto.LoginRequest;
import org.api.hydrocore.dto.ResetPasswordRequest;
import org.api.hydrocore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Autowired
    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<String> login(@Valid LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword(), request.getCodigoEmpresa());
        return ResponseEntity.ok(token);
    }

    @Override
    public ResponseEntity<Void> forgotPassword(@Valid ForgotPasswordRequest request) {
        authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordRequest request) {
        if (request.getNovaSenha() == null || request.getConfirmaSenha() == null ||
                !request.getNovaSenha().equals(request.getConfirmaSenha())) {
            return ResponseEntity.badRequest().body(Map.of("error", "As senhas não coincidem"));
        }

        authService.resetPassword(request.getToken(), request.getNovaSenha());
        return ResponseEntity.ok(Map.of("message", "Senha redefinida com sucesso"));
    }

    @Override
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.ok(Map.of("message", "Logout realizado com sucesso"));
    }
}