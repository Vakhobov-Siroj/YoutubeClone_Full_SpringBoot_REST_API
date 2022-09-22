package com.company.service;

import com.company.entity.email.EmailEntity;
import com.company.repository.email.EmailRepository;
import com.company.repository.email.MailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

@Service
public class EmailService {
    @Autowired
    private MailHistoryRepository mailHistoryRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromAccount;

    @Value("${server.url}")
    private String serverUrl;

    public void sendRegistrationEmail(String toAccount, Integer id) {
//        String message = "Your Activation lin: adsdasdasdasda";
//        sendSimpleEmail(toAccount, "Registration", message);
        String url = String.format("<a href='%s/auth/email/verification/%d'>Verification Link</a>", serverUrl, id);

        StringBuilder builder = new StringBuilder();
        builder.append("<h1 style='align-text:center'>Salom Siroj aka Qaleysiz</h1>");
        builder.append("<b>Dars tugadimi--z|</b>");
        builder.append("<p>");
        builder.append(url);
        builder.append("</p>");

        sendEmail(toAccount, "Registration", builder.toString());
    }

    private void sendEmail(String toAccount, String subject, String text) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);

            EmailEntity emailEntity = new EmailEntity();
            emailEntity.setToAccount(toAccount);
            emailEntity.setSubject(subject);
            emailEntity.setText(text);
            emailEntity.setCreatedDate(LocalDateTime.now());
            emailRepository.save(emailEntity);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendSimpleEmail(String toAccount, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(text);
        msg.setFrom(fromAccount);
        javaMailSender.send(msg);
    }

    public Long getEmailCount(Integer id) {
            return mailHistoryRepository.getEmailCount(id);

    }
}
