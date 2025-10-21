package org.api.hydrocore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    @Schema(description = "E-mail do usuário", example = "usuario@exemplo.com")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Schema(description = "Senha do usuário", example = "123456")
    private String password;

    @NotBlank(message = "O código da empresa é obrigatório.")
    @Size(min = 1, max = 20, message = "O código da empresa deve ter entre 1 e 20 caracteres.")
    @Schema(description = "Código da empresa", example = "123456789")
    private String codigoEmpresa;


    public @NotBlank(message = "O e-mail é obrigatório.") @Email(message = "Formato de e-mail inválido.") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "O e-mail é obrigatório.") @Email(message = "Formato de e-mail inválido.") String email) {
        this.email = email;
    }

    public @NotBlank(message = "A senha é obrigatória.") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "A senha é obrigatória.") String password) {
        this.password = password;
    }

    public @NotBlank(message = "O código da empresa é obrigatório.") @Size(min = 1, max = 20, message = "O código da empresa deve ter entre 1 e 20 caracteres.") String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(@NotBlank(message = "O código da empresa é obrigatório.") @Size(min = 1, max = 20, message = "O código da empresa deve ter entre 1 e 20 caracteres.") String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }
}