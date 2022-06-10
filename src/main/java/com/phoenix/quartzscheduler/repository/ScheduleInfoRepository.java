package com.phoenix.quartzscheduler.repository;

import com.phoenix.quartzscheduler.domain.ScheduleInfo;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleInfoRepository extends CrudRepository<ScheduleInfo, Long> {

  ScheduleInfo findByJobName(String jobName);

}
