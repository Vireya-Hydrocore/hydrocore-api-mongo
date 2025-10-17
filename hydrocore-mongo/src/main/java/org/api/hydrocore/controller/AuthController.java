package org.api.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.api.hydrocore.dto.ForgotPasswordRequest;
import org.api.hydrocore.dto.LoginRequest;
import org.api.hydrocore.dto.ResetPasswordRequest;
import org.api.hydrocore.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints de autenticação e gerenciamento de sessão")
public class AuthController {

    private final AuthService authService;

     public AuthController(AuthService authService) {
         this.authService = authService;
     }

    @Operation(summary = "Login do usuário", description = "Autentica o usuário e retorna um token JWT")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword(), request.getCodigoEmpresa());
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Esqueci minha senha", description = "Envia um e-mail de recuperação de senha para o usuário")
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request.getEmail(), request.getCodigoEmpresa());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Redefinir senha", description = "Permite redefinir a senha usando o token recebido por e-mail")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        if (request.getNovaSenha() == null || request.getConfirmaSenha() == null ||
                !request.getNovaSenha().equals(request.getConfirmaSenha())) {
            return ResponseEntity.badRequest().body(Map.of("error", "As senhas não coincidem"));
        }

        authService.resetPassword(request.getToken(), request.getNovaSenha());
        return ResponseEntity.ok(Map.of("message", "Senha redefinida com sucesso"));
    }

    @Operation(summary = "Logout", description = "Invalida o token JWT do usuário atual")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.ok(Map.of("message", "Logout realizado com sucesso"));
    }

}