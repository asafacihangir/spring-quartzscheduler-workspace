package com.phoenix.quartzscheduler.service.base;

import com.phoenix.quartzscheduler.domain.ScheduleInfo;
import com.phoenix.quartzscheduler.scheduler.JobData;
import java.util.List;

public interface ScheduleInfoService {

  void schedule(JobData data);

  void deleteJob(String jobName, String jobGroup);

  List<ScheduleInfo> findAllJob();
}
