package com.phoenix.quartzscheduler.scheduler;

import com.phoenix.quartzscheduler.repository.FruitRepository;
import java.util.List;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class ScheduledJob extends QuartzJobBean {

  private final FruitRepository fruitRepository;

  public ScheduledJob(FruitRepository fruitRepository) {
    this.fruitRepository = fruitRepository;
  }

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
    for (String key : mergedJobDataMap.getKeys()) {
      System.out.println(" from scheduled job :: " + mergedJobDataMap.get(key));
    }

    List<String> fruitName = fruitRepository.findUniqueFruitName();
    for (String fruit : fruitName) {
      System.out.println(
          " fruit = " + fruit + " and count is " + fruitRepository.countByFruitName(fruit));
    }


  }
}
