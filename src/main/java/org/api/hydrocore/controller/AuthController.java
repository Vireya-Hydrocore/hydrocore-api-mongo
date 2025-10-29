package org.api.hydrocore.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.api.hydrocore.dto.request.ForgotPasswordRequest;
import org.api.hydrocore.dto.request.LoginRequest;
import org.api.hydrocore.dto.request.ResetPasswordRequest;
import org.api.hydrocore.dto.response.ApiResponseDTO;
import org.api.hydrocore.dto.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints de autenticação e gerenciamento de sessão")
public interface AuthController {

    // -------------------------------------------------------------------------
    // LOGIN
    // -------------------------------------------------------------------------
    @PostMapping("/login")
    @Operation(summary = "Login do usuário", description = "Autentica o usuário e retorna um token JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido, retorna o token JWT",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class)))
    })
    ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request);

    // -------------------------------------------------------------------------
    // FORGOT PASSWORD
    // -------------------------------------------------------------------------
    @PostMapping("/forgot-password")
    @Operation(summary = "Esqueci minha senha", description = "Envia um e-mail de recuperação de senha para o usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "E-mail de recuperação enviado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro no serviço de envio de e-mail",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class)))
    })
    ResponseEntity<ApiResponseDTO<Void>> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) throws IOException, UnirestException;

    // -------------------------------------------------------------------------
    // RESET PASSWORD
    // -------------------------------------------------------------------------
    @PostMapping("/reset-password")
    @Operation(summary = "Redefinir senha", description = "Permite redefinir a senha usando o token recebido por e-mail")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "As senhas não coincidem ou Token inválido/expirado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    ResponseEntity<ApiResponseDTO<Void>> resetPassword(@RequestBody @Valid ResetPasswordRequest request);

    // -------------------------------------------------------------------------
    // LOGOUT
    // -------------------------------------------------------------------------
    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Invalida o token JWT do usuário atual")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido no cabeçalho 'Authorization'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    ResponseEntity<ApiResponseDTO<Void>> logout(@RequestHeader("Authorization") String token);

}
