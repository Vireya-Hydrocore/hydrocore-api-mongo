package org.api.hydrocore.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.api.hydrocore.feign.MessageFeign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${MAILGUN_ENV}")
    private String mailgunEnv;

    @Value("${MAILGUN_DOMAIN_SANDBOX}")
    private String sandboxDomain;

    @Value("${MAILGUN_DOMAIN_PROD}")
    private String prodDomain;

    @Value("${MAILGUN_FROM_SANDBOX}")
    private String sandboxFrom;

    @Value("${MAILGUN_FROM_PROD}")
    private String prodFrom;

    @Value("${MAILGUN_APP_LINK}")
    private String appLink;

    public void sendResetPasswordEmail(String to, String token) {
        String link = appLink + "?token=" + token;
        String subject = "Redefinição de senha - Vireya";
        String text = "Olá!\n\nClique no link abaixo para redefinir sua senha:\n" +
                link + "\n\nEste link expira em 15 minutos.";

        String from = "sandbox".equalsIgnoreCase(mailgunEnv) ? sandboxFrom : prodFrom;
        String domain = "sandbox".equalsIgnoreCase(mailgunEnv) ? sandboxDomain : prodDomain;

        try {
            MessageFeign.sendEmail(from, to, subject, text, domain);
            System.out.println("E-mail enviado com sucesso para " + to + " usando " + mailgunEnv);
        } catch (UnirestException e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
            throw new RuntimeException("Erro ao enviar e-mail: " + e.getMessage(), e);
        }
    }
}
