package org.api.hydrocore.controller.impl;

import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.validation.Valid;
import org.api.hydrocore.controller.AuthController;
import org.api.hydrocore.dto.request.ForgotPasswordRequest;
import org.api.hydrocore.dto.request.LoginRequest;
import org.api.hydrocore.dto.request.ResetPasswordRequest;
import org.api.hydrocore.dto.response.ApiResponseDTO;
import org.api.hydrocore.dto.response.LoginResponse;
import org.api.hydrocore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ApiResponseDTO<Void>> forgotPassword(@Valid ForgotPasswordRequest request) throws UnirestException {
        authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok(ApiResponseDTO.ok("E-mail de recuperação enviado com sucesso"));
    }

    @Override
    public ResponseEntity<ApiResponseDTO<Void>> resetPassword(@Valid ResetPasswordRequest request) {
        if (!request.getNovaSenha().equals(request.getConfirmaSenha())) {
            throw new IllegalArgumentException("As senhas não coincidem");
        }

        authService.resetPassword(request.getEmail(), request.getNovaSenha());
        return ResponseEntity.ok(ApiResponseDTO.ok("Senha redefinida com sucesso"));
    }

    @Override
    public ResponseEntity<ApiResponseDTO<Void>> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.ok(ApiResponseDTO.ok("Logout realizado com sucesso"));
    }

}
