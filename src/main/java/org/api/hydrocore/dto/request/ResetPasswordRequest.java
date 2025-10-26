package org.api.hydrocore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message = "O token de redefinição não pode estar vazio.")
    private String token;

    @NotBlank(message = "A nova senha é obrigatória.")
    @Size(min = 6, max = 30, message = "A senha deve ter entre 6 e 30 caracteres.")
    private String novaSenha;

    @NotBlank(message = "A confirmação da senha é obrigatória.")
    private String confirmaSenha;

    public @NotBlank(message = "O token de redefinição não pode estar vazio.") String getToken() {
        return token;
    }

    public void setToken(@NotBlank(message = "O token de redefinição não pode estar vazio.") String token) {
        this.token = token;
    }

    public @NotBlank(message = "A nova senha é obrigatória.") @Size(min = 6, max = 30, message = "A senha deve ter entre 6 e 30 caracteres.") String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(@NotBlank(message = "A nova senha é obrigatória.") @Size(min = 6, max = 30, message = "A senha deve ter entre 6 e 30 caracteres.") String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public @NotBlank(message = "A confirmação da senha é obrigatória.") String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(@NotBlank(message = "A confirmação da senha é obrigatória.") String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }
}