package com.phoenix.quartzscheduler.service;

import com.phoenix.quartzscheduler.scheduler.EmailJob;
import com.phoenix.quartzscheduler.scheduler.MySchedulerListener;
import com.phoenix.quartzscheduler.service.base.ScheduleInfoService;
import com.phoenix.quartzscheduler.service.model.EmailRequest;
import com.phoenix.quartzscheduler.service.model.EmailResponse;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SimpleScheduleInfoService implements ScheduleInfoService {


  private final Scheduler quartzScheduler;
  private final MySchedulerListener mySchedulerListener;

  public SimpleScheduleInfoService(Scheduler quartzScheduler,
      MySchedulerListener mySchedulerListener) {
    this.quartzScheduler = quartzScheduler;
    this.mySchedulerListener = mySchedulerListener;
  }

  @PostConstruct
  public void postConstruct() {
    try {
      quartzScheduler.start();
      quartzScheduler.getListenerManager().addSchedulerListener(mySchedulerListener);
    } catch (SchedulerException exception) {
      System.out.println("scheduler thorws exception " + exception);
    }
  }

  @PreDestroy
  public void preDestroy() {
    try {
      quartzScheduler.shutdown();
    } catch (SchedulerException exception) {
      System.out.println("scheduler thorws exception " + exception);
    }
  }


  private JobDetail buildJobDetail(EmailRequest emailRequest) {
    JobDataMap dataMap = new JobDataMap();
    dataMap.put("email", emailRequest.getEmail());
    dataMap.put("subject", emailRequest.getSubject());
    dataMap.put("body", emailRequest.getBody());

    return JobBuilder.newJob(EmailJob.class)
        .withIdentity(UUID.randomUUID().toString(), "email-jobs")
        .withDescription("Send Email Job")
        .usingJobData(dataMap).storeDurably(false)
        .build();

  }

  private Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
    return TriggerBuilder.newTrigger()
        .forJob(jobDetail)
        .withIdentity(jobDetail.getKey().getName(), "email-triggers")
        .withDescription("Send Email Trigger")
        .startAt(Date.from(startAt.toInstant()))
        .withSchedule(SimpleScheduleBuilder
            .simpleSchedule().withMisfireHandlingInstructionFireNow()).build();
  }



  @Override
  public ResponseEntity<EmailResponse> scheduleEmail(EmailRequest request) {

    ZonedDateTime dateTime = ZonedDateTime.of(request.getDateTime(), request.getTimeZone());

    if (dateTime.isBefore(ZonedDateTime.now())) {
      EmailResponse emailResponse = new EmailResponse();
      emailResponse.setSuccess(false);
      emailResponse.setMessage("DateTime must be after current time");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emailResponse);
    }

    JobDetail jobDetail = buildJobDetail(request);
    Trigger trigger = buildTrigger(jobDetail, dateTime);

    try {
      quartzScheduler.scheduleJob(jobDetail, trigger);
      EmailResponse emailResponse = new EmailResponse();
      emailResponse.setSuccess(true);
      emailResponse.setJobGroup(jobDetail.getKey().getGroup());
      emailResponse.setJobId(jobDetail.getKey().getName());
      emailResponse.setMessage("Email Scheduled Successfully!");

      return ResponseEntity.status(HttpStatus.OK).body(emailResponse);
    } catch (SchedulerException e) {
      EmailResponse emailResponse = new EmailResponse();
      emailResponse.setSuccess(false);
      emailResponse.setMessage("Error while scheduling email. Please try again later!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emailResponse);
    }

  }



}
