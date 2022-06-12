package com.phoenix.quartzscheduler.email;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {

  private final MailSenderInfoModelProperties mailProperties;

  public EmailConfiguration(MailSenderInfoModelProperties mailProperties) {
    this.mailProperties = mailProperties;
  }


  @Bean
  JavaMailSender javaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(mailProperties.getHost());
    mailSender.setPort(Integer.parseInt(mailProperties.getPort()));
    mailSender.setUsername(mailProperties.getUsername());
    mailSender.setPassword(mailProperties.getPassword());
    mailSender.setProtocol("smtp");



    Properties javaMailProperties = new Properties();
    javaMailProperties.put("mail.smtp.starttls.enable", "true");
    javaMailProperties.put("mail.transport.protocol", "smtp");
    javaMailProperties.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
    javaMailProperties.put("mail.smtp.socketFactory.port", "465"); //SSL Port
    javaMailProperties.put("mail.smtp.socketFactory.class",
        "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
    javaMailProperties.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
    javaMailProperties.put("mail.smtp.port", "465"); //SMTP Port



    mailSender.setJavaMailProperties(javaMailProperties);
    return mailSender;
  }

}
