package org.api.hydrocore.dto;


public class ForgotPasswordRequest {
    private String email;
    private String codigoEmpresa;

    public ForgotPasswordRequest(String email, String codigoEmpresa) {
        this.email = email;
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }
}

