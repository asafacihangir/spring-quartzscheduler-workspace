package com.phoenix.quartzscheduler.scheduler;

import com.phoenix.quartzscheduler.email.MailSenderInfoModelProperties;
import java.nio.charset.StandardCharsets;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class EmailJob extends QuartzJobBean {

  private final JavaMailSender javaMailSender;

  private final MailSenderInfoModelProperties mailProperties;

  public EmailJob(JavaMailSender javaMailSender, MailSenderInfoModelProperties mailProperties) {
    this.javaMailSender = javaMailSender;
    this.mailProperties = mailProperties;
  }


  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    JobDataMap jobDataMap = context.getMergedJobDataMap();

    String subject = jobDataMap.getString("subject");
    String body = jobDataMap.getString("body");
    String recipientEmail = jobDataMap.getString("email");

    sendEmail(mailProperties.getUsername(), recipientEmail, subject, body);
  }

  private void sendEmail(String fromEmail, String toEmail, String subject, String body) {

    try {
      MimeMessage message = javaMailSender.createMimeMessage();

      MimeMessageHelper messageHelper = new MimeMessageHelper(message,
          StandardCharsets.UTF_8.toString());
      messageHelper.setSubject(subject);
      messageHelper.setText(body, true);
      messageHelper.setFrom(fromEmail);
      messageHelper.setTo(toEmail);

      javaMailSender.send(message);

    } catch (MessagingException ex) {
      System.out.println(ex.getCause().getMessage());
    }


  }
}
