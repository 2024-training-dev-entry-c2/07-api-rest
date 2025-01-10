package com.restaurant.restaurant.services;

import com.restaurant.restaurant.dtos.DishDTO;
import com.restaurant.restaurant.exceptions.ResourceNotFoundException;
import com.restaurant.restaurant.models.DishModel;
import com.restaurant.restaurant.enums.DishType;
import com.restaurant.restaurant.repositories.DishRepository;
import com.restaurant.restaurant.utils.UtilCost;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishService {
  private final DishRepository dishRepository;

  @Transactional
  public List<DishDTO> findAll(){
    return dishRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Transactional
  public DishDTO findById(Long id){
    return dishRepository.findById(id).map(this::convertToDto).orElseThrow(() -> new ResourceNotFoundException("Dish not found with id " + id));
  }

  @Transactional
  public DishDTO createDish(DishDTO dishDTO){
    DishModel dishModel = new DishModel();
    dishModel.setName(dishDTO.getName());
    dishModel.setPrice(dishDTO.getPrice());
    dishModel.setType(DishType.COMUN);

    return convertToDto(dishRepository.save(dishModel));
  }

  @Transactional
  public DishDTO updateDish(Long id, DishDTO dishDTO){
    DishModel dishModel = dishRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dish not found with id " + id));
    dishModel.setName(dishDTO.getName());
    if(dishModel.getType().equals(DishType.POPULAR)){
      dishModel.setPrice(UtilCost.applyIncrDishPopular(dishDTO.getPrice()));
    }
    else{
      dishModel.setPrice(dishDTO.getPrice());
    }
    return convertToDto(dishRepository.save(dishModel));
  }

  @Transactional
  public void deleteDish(Long id){
    if(!dishRepository.existsById(id)){
      throw new ResourceNotFoundException("Dish not found with id " + id);
    }
    dishRepository.deleteById(id);
  }

  @Transactional
  public boolean isPopular(Long id){
    return dishRepository.findById(id).map(dishModel -> dishModel.getType().equals(DishType.POPULAR)).orElse(false);
  }

  private DishDTO convertToDto(DishModel dishModel) {
    DishDTO dto = new DishDTO();
    dto.setId(dishModel.getId());
    dto.setName(dishModel.getName());
    dto.setPrice(dishModel.getPrice());
    dto.setType(dishModel.getType());
    return dto;
  }

}
