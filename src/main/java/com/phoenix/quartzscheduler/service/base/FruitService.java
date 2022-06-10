package com.phoenix.quartzscheduler.service.base;

import com.phoenix.quartzscheduler.domain.Fruit;
import java.util.List;

public interface FruitService {

  Fruit saveFruit(Fruit fruit);

  void saveAll();

  List<Fruit> findAllFruits();

  void deleteFruit(Long fruitId);

  long countByFruitName(String fruitName);
}
