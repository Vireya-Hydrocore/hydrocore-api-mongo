package org.api.hydrocore.service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class EmailService {

    @Value("${MAILERSEND_API_KEY}")
    private String apiKey;

    public void sendResetPasswordEmail(String to, String token) {
        String link = "intervireya://reset-password?token=" + token;
        String subject = "Redefinição de senha - HydroCore";
        String text = "Olá!\n\nClique no link abaixo para redefinir sua senha:\n" +
                link + "\n\nEste link expira em 15 minutos.";

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String json = "{"
                + "\"from\": {\"email\": \"vireyabrasil@gmail.com\"},"
                + "\"to\": [{\"email\": \"" + to + "\"}],"
                + "\"subject\": \"" + subject + "\","
                + "\"text\": \"" + text + "\""
                + "}";

        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url("https://api.mailersend.com/v1/email")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

    }
}
