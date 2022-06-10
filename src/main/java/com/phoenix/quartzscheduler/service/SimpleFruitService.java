package com.phoenix.quartzscheduler.service;

import com.phoenix.quartzscheduler.domain.Fruit;
import com.phoenix.quartzscheduler.repository.FruitRepository;
import com.phoenix.quartzscheduler.service.base.FruitService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SimpleFruitService implements FruitService {

  private final FruitRepository fruitRepository;

  public SimpleFruitService(FruitRepository fruitRepository) {
    this.fruitRepository = fruitRepository;
  }

  @Override
  public Fruit saveFruit(Fruit fruit) {
    return fruitRepository.save(fruit);
  }


  @Override
  public void saveAll() {
    final List<String> fruitNames = List.of("Apple", "Orange", "Banana", "Blueberries",
        "Coconut Meat", "Durian", "Pear");

    fruitNames.forEach(fruitName -> {
      final Fruit fruit = new Fruit();
      fruit.setFruitName(fruitName);
      fruitRepository.save(fruit);
    });


  }


  @Override
  public List<Fruit> findAllFruits() {
    Iterable<Fruit> allFruits = fruitRepository.findAll();
    return (List<Fruit>) allFruits;
  }

  @Override
  public void deleteFruit(Long fruitId) {
    Optional<Fruit> fruit = fruitRepository.findById(fruitId);
    fruit.ifPresent(fruitRepository::delete);
  }

  @Override
  public long countByFruitName(String fruitName) {
    return fruitRepository.countByFruitName(fruitName);
  }


}
