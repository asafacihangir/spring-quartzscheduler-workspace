package com.phoenix.quartzscheduler.web;

import com.phoenix.quartzscheduler.service.base.ScheduleInfoService;
import com.phoenix.quartzscheduler.service.model.EmailRequest;
import com.phoenix.quartzscheduler.service.model.EmailResponse;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<EmailResponse> schedule(@RequestBody EmailRequest emailRequest) {
    return scheduleInfoService.scheduleEmail(emailRequest);
  }

}
