package org.api.hydrocore.dto.response;

import lombok.Data;

@Data
public class ApiResponseDTO {
    private boolean success;
    private String message;

    public ApiResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static  ApiResponseDTO ok(String message) {
        return new ApiResponseDTO(true, message);
    }

    public static ApiResponseDTO error(String message) {
        return new ApiResponseDTO(false, message);
    }

}
