package com.phoenix.quartzscheduler.scheduler;

import com.phoenix.quartzscheduler.domain.ScheduleInfo;
import com.phoenix.quartzscheduler.repository.ScheduleInfoRepository;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Component;

@Component
public class MySchedulerListener implements SchedulerListener {

  private final ScheduleInfoRepository scheduleInfoRepository;

  public MySchedulerListener(ScheduleInfoRepository scheduleInfoRepository) {
    this.scheduleInfoRepository = scheduleInfoRepository;
  }

  @Override
  public void jobScheduled(Trigger trigger) {

  }

  @Override
  public void jobUnscheduled(TriggerKey triggerKey) {

  }

  @Override
  public void triggerFinalized(Trigger trigger) {
    System.out.println("cleanup after job execution.....");
    JobKey jobKey = trigger.getJobKey();

    String jobName = jobKey.getName();
    ScheduleInfo persistedScheduledInfo = scheduleInfoRepository.findByJobName(jobName);
    if (persistedScheduledInfo != null) {
      scheduleInfoRepository.delete(persistedScheduledInfo);
    }

  }

  @Override
  public void triggerPaused(TriggerKey triggerKey) {

  }

  @Override
  public void triggersPaused(String triggerGroup) {

  }

  @Override
  public void triggerResumed(TriggerKey triggerKey) {

  }

  @Override
  public void triggersResumed(String triggerGroup) {

  }

  @Override
  public void jobAdded(JobDetail jobDetail) {

  }

  @Override
  public void jobDeleted(JobKey jobKey) {

  }

  @Override
  public void jobPaused(JobKey jobKey) {

  }

  @Override
  public void jobsPaused(String jobGroup) {

  }

  @Override
  public void jobResumed(JobKey jobKey) {

  }

  @Override
  public void jobsResumed(String jobGroup) {

  }

  @Override
  public void schedulerError(String msg, SchedulerException cause) {

  }

  @Override
  public void schedulerInStandbyMode() {

  }

  @Override
  public void schedulerStarted() {

  }

  @Override
  public void schedulerStarting() {

  }

  @Override
  public void schedulerShutdown() {

  }

  @Override
  public void schedulerShuttingdown() {

  }

  @Override
  public void schedulingDataCleared() {

  }
}
