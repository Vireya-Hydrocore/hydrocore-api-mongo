package org.api.hydrocore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.api.hydrocore.dto.ForgotPasswordRequest;
import org.api.hydrocore.dto.LoginRequest;
import org.api.hydrocore.dto.ResetPasswordRequest;
import org.api.hydrocore.dto.TokenFcmRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints de autenticação e gerenciamento de sessão")
public interface AuthController {

    class TokenResponse {
        public String token;
    }

    class MessageResponse {
        public String message;
    }

    // -------------------------------------------------------------------------
    // LOGIN
    // -------------------------------------------------------------------------

    @PostMapping("/login")
    @Operation(summary = "Login do usuário", description = "Autentica o usuário e retorna um token JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido, retorna o token JWT",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas (usuário/senha/código empresa)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)))
    })
    ResponseEntity<String> login(@RequestBody @Valid LoginRequest request);

    // -------------------------------------------------------------------------
    // FORGOT PASSWORD
    // -------------------------------------------------------------------------

    @PostMapping("/forgot-password")
    @Operation(summary = "Esqueci minha senha", description = "Envia um e-mail de recuperação de senha para o usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "E-mail de recuperação enviado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro no serviço de envio de e-mail.")
    })
    ResponseEntity<Void> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) throws IOException;

    // -------------------------------------------------------------------------
    // RESET PASSWORD
    // -------------------------------------------------------------------------
    @PostMapping("/reset-password")
    @Operation(summary = "Redefinir senha", description = "Permite redefinir a senha usando o token recebido por e-mail")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "As senhas não coincidem ou Token inválido/expirado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)))
    })
    ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request);

    // -------------------------------------------------------------------------
    // LOGOUT
    // -------------------------------------------------------------------------

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Invalida o token JWT do usuário atual")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido no cabeçalho 'Authorization'")
    })
    ResponseEntity<?> logout(@RequestHeader("Authorization") String token);



    // -------------------------------------------------------------------------
    // SALVAR TOKEN FCM
    // -------------------------------------------------------------------------
    @PostMapping("/fcm")
    @Operation(summary = "Registrar token FCM", description = "Registra o token FCM do dispositivo para o usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Token inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    ResponseEntity<?> salvarFcmToken(
            @RequestHeader("Authorization") String authorization,
            @RequestBody @Valid TokenFcmRequest request);


}
