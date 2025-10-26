package org.api.hydrocore.controller.impl;

import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.validation.Valid;
import org.api.hydrocore.controller.AuthController;
import org.api.hydrocore.dto.request.ForgotPasswordRequest;
import org.api.hydrocore.dto.request.LoginRequest;
import org.api.hydrocore.dto.request.ResetPasswordRequest;
import org.api.hydrocore.dto.request.TokenFcmRequest;
import org.api.hydrocore.dto.response.LoginResponse;
import org.api.hydrocore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Autowired
    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<LoginResponse> login(@Valid LoginRequest request) {
        LoginResponse token = authService.login(request.getEmail(), request.getPassword(), request.getCodigoEmpresa());
        return ResponseEntity.ok(token);
    }

    @Override
    public ResponseEntity<Void> forgotPassword(@Valid ForgotPasswordRequest request) throws UnirestException {
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

    // novo endpoint para salvar token FCM
    @Override
    public ResponseEntity<?> salvarFcmToken(@RequestHeader("Authorization") String authorization,
                                            @Valid TokenFcmRequest request) {
        if (request == null || request.getTokenFcm() == null || request.getTokenFcm().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "tokenFcm é obrigatório"));
        }

        try {
            authService.salvarFcmToken(authorization, request.getTokenFcm());
            return ResponseEntity.ok(Map.of("message", "Token FCM salvo com sucesso"));
        } catch (SecurityException se) {
            return ResponseEntity.status(401).body(Map.of("error", se.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao salvar token FCM"));
        }
    }
}
