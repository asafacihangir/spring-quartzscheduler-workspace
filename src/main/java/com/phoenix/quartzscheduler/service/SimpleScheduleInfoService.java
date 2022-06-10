package com.phoenix.quartzscheduler.service;

import com.phoenix.quartzscheduler.domain.ScheduleInfo;
import com.phoenix.quartzscheduler.repository.ScheduleInfoRepository;
import com.phoenix.quartzscheduler.scheduler.JobData;
import com.phoenix.quartzscheduler.scheduler.MySchedulerListener;
import com.phoenix.quartzscheduler.scheduler.ScheduledJob;
import com.phoenix.quartzscheduler.service.base.ScheduleInfoService;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

@Service
public class SimpleScheduleInfoService implements ScheduleInfoService {

  private final ScheduleInfoRepository scheduleInfoRepository;

  private final Scheduler quartzScheduler;

  private final MySchedulerListener mySchedulerListener;

  public SimpleScheduleInfoService(ScheduleInfoRepository scheduleInfoRepository,
      Scheduler quartzScheduler, MySchedulerListener mySchedulerListener) {
    this.scheduleInfoRepository = scheduleInfoRepository;
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


  @Override
  public void schedule(JobData data) {
    String jobName = data.getJobName();
    String jobGroup = data.getJobGroup();

    int counter = data.getCounter();
    int gapDuration = data.getGapDuration();

    ZonedDateTime zonedDateTime = ZonedDateTime.of(data.getStartTime(), ZoneId.of("Europe/Istanbul"));
    JobDataMap dataMap = new JobDataMap();
    dataMap.put("test", "this is just for demo");

    final ScheduleInfo scheduleInfo = new ScheduleInfo();
    scheduleInfo.setCounter(counter);
    scheduleInfo.setGapDuration(gapDuration);
    scheduleInfo.setJobGroup(jobGroup);
    scheduleInfo.setJobName(jobName);
    scheduleInfo.setStartTime(data.getStartTime());

    JobDetail detail = JobBuilder.newJob(ScheduledJob.class)
        .withIdentity(jobName, jobGroup).usingJobData(dataMap).storeDurably(false)
        .build();

    Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
        .startAt(Date.from(zonedDateTime.toInstant()))
        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(gapDuration)
            .withRepeatCount(counter))
        .build();

    try {
      quartzScheduler.scheduleJob(detail, trigger);
      scheduleInfoRepository.save(scheduleInfo);

    } catch (SchedulerException e) {
      e.printStackTrace();
    }


  }

  @Override
  public void deleteJob(String jobName, String jobGroup) {
    JobKey jobKey = new JobKey(jobName, jobGroup);
    try {
      quartzScheduler.deleteJob(jobKey);
      ScheduleInfo scheduledInfo = scheduleInfoRepository.findByJobName(jobName);
      if (scheduledInfo != null) {
        scheduleInfoRepository.delete(scheduledInfo);
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<ScheduleInfo> findAllJob() {
    Iterable<ScheduleInfo> findAll = scheduleInfoRepository.findAll();
    Iterator<ScheduleInfo> iterator = findAll.iterator();
    List<ScheduleInfo> allScheduledInfo = new ArrayList<>();
    while (iterator.hasNext()) {
      allScheduledInfo.add(iterator.next());
    }
    return allScheduledInfo;
  }

}
