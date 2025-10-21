package org.api.hydrocore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${USER_EMAIL}")
    private String fromEmail;

    public void sendResetPasswordEmail(String to, String token) {
        String link = "intervireya://reset-password?token=" + token;
        String subject = "Redefinição de senha - Vireya";
        String text = "Olá!\n\nClique no link abaixo para redefinir sua senha:\n" +
                link + "\n\nEste link expira em 15 minutos.";

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            System.out.println("E-mail enviado com sucesso para " + to);
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
            throw new RuntimeException("Erro ao enviar e-mail: " + e.getMessage(), e);
        }
    }
}
