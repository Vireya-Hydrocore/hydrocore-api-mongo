package org.api.hydrocore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendResetPasswordEmail(String to, String token) {
        String link = "intervireya://reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Redefinição de senha - HydroCore");
        message.setText("Olá!\n\nClique no link abaixo para redefinir sua senha:\n" + link +
                "\n\nEste link expira em 15 minutos.");

        mailSender.send(message);
    }
}
