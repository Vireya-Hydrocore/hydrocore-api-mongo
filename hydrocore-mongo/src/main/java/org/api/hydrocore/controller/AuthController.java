package org.api.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.api.hydrocore.dto.LoginRequest;
import org.api.hydrocore.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints de autenticação e gerenciamento de sessão")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Login do usuário", description = "Autentica o usuário e retorna um token JWT")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword(),request.getCodigoEmpresa());
        return ResponseEntity.ok(token);
    }


    @Operation(summary = "Esqueci minha senha", description = "Envia um e-mail de recuperação de senha para o usuário")
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody Map<String, String> request) {
        authService.forgotPassword(request.get("email"));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Redefinir senha", description = "Permite redefinir a senha usando o token recebido por e-mail")
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody Map<String, String> request) {
        authService.resetPassword(request.get("token"), request.get("newPassword"));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Logout do usuário", description = "Invalida o token JWT do usuário atual")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.ok().build();
    }
}
