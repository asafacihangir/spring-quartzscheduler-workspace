package com.phoenix.quartzscheduler.web;

import com.phoenix.quartzscheduler.domain.ScheduleInfo;
import com.phoenix.quartzscheduler.scheduler.JobData;
import com.phoenix.quartzscheduler.service.base.ScheduleInfoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule-info")
public class SimpleScheduleInfoController {

  private final ScheduleInfoService scheduleInfoService;

  public SimpleScheduleInfoController(ScheduleInfoService scheduleInfoService) {
    this.scheduleInfoService = scheduleInfoService;
  }


  @PostMapping("/schedule")
  public ResponseEntity<Void> schedule(@RequestBody JobData data) {
    scheduleInfoService.schedule(data);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  @GetMapping("/schedule")
  public ResponseEntity<List<ScheduleInfo>> schedule() {
    List<ScheduleInfo> findAllJob = scheduleInfoService.findAllJob();
    return ResponseEntity.status(HttpStatus.OK).body(findAllJob);
  }

  @DeleteMapping("/schedule/{jobName}/{jobGroup}")
  public ResponseEntity<Void> schedule(@PathVariable String jobName,
      @PathVariable String jobGroup) {
    scheduleInfoService.deleteJob(jobName, jobGroup);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
  }


}
