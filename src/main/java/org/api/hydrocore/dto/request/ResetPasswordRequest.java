package org.api.hydrocore.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message = "O email de redefinição não pode estar vazio.")
    @Email
    private String email;

    @NotBlank(message = "A nova senha é obrigatória.")
    @Size(min = 6, max = 30, message = "A senha deve ter entre 6 e 30 caracteres.")
    private String novaSenha;

    @NotBlank(message = "A confirmação da senha é obrigatória.")
    private String confirmaSenha;

}