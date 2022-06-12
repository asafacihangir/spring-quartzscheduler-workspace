package com.phoenix.quartzscheduler.service.base;

import com.phoenix.quartzscheduler.service.model.EmailRequest;
import com.phoenix.quartzscheduler.service.model.EmailResponse;
import org.springframework.http.ResponseEntity;

public interface ScheduleInfoService {

  ResponseEntity<EmailResponse> scheduleEmail(EmailRequest request);
}
