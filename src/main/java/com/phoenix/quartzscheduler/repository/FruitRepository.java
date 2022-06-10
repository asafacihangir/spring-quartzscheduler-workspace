package com.phoenix.quartzscheduler.repository;

import com.phoenix.quartzscheduler.domain.Fruit;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FruitRepository extends CrudRepository<Fruit, Long> {


  long countByFruitName(String fruitName);

  @Query(value = "select distinct fruit_name from Fruit", nativeQuery = true)
  List<String> findUniqueFruitName();

}
