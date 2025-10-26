package org.api.hydrocore.dto.request;

import jakarta.validation.constraints.NotBlank;

public class TokenFcmRequest {
    @NotBlank
    private String tokenFcm;

    public TokenFcmRequest() {}

    public TokenFcmRequest(String tokenFcm) {
        this.tokenFcm = tokenFcm;
    }

    public String getTokenFcm() {
        return tokenFcm;
    }

    public void setTokenFcm(String tokenFcm) {
        this.tokenFcm = tokenFcm;
    }
}
