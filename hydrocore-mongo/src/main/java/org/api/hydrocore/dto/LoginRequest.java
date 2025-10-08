package org.api.hydrocore.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequest {

    @Schema(description = "E-mail do usuário", example = "usuario@exemplo.com")
    private String email;

    @Schema(description = "Senha do usuário", example = "123456")
    private String password;

    @Schema(description = "Código da empresa", example = "123456789")
    private String codigoEmpresa;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }
}