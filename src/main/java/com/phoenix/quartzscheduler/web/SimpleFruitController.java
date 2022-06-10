package com.phoenix.quartzscheduler.web;

import com.phoenix.quartzscheduler.service.base.FruitService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fruit")
public class SimpleFruitController {

  private final FruitService fruitService;

  public SimpleFruitController(FruitService fruitService) {
    this.fruitService = fruitService;
  }


  @PostMapping
  public void saveAll(){
      fruitService.saveAll();
  }

}
