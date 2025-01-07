package com.restaurante.restaurante.services.impl;

import com.restaurante.restaurante.dto.DishDTO;
import com.restaurante.restaurante.mapper.DishMapper;
import com.restaurante.restaurante.models.Dish;
import com.restaurante.restaurante.repositories.DishRepository;
import com.restaurante.restaurante.services.IDishService;
import com.restaurante.restaurante.utils.DishType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service

public class DishServiceImpl implements IDishService {

    private final DishRepository dishRepository;


    @Autowired
    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public DishDTO addDish(DishDTO dishDTO) {
        Dish dish = DishMapper.toEntity(dishDTO); // Mapeamos el DTO a la entidad Dish
        validateAndSetDefaults(dish); // Validamos y establecemos valores por defecto
        Dish savedDish = dishRepository.save(dish); // Guardamos la entidad en el repositorio
        return DishMapper.toDTO(savedDish); // Devolvemos el DTO del plato guardado
    }

    @Override
    public Optional<DishDTO> getDish(Long id) {
        return dishRepository.findById(id) // Buscamos el plato por ID
                .map(DishMapper::toDTO); // Si se encuentra, lo mapeamos a un DTO
    }

    @Override
    public List<DishDTO> getDishes() {
        return dishRepository.findAll() // Obtenemos todos los platos
                .stream()
                .map(DishMapper::toDTO) // Convertimos cada entidad en un DTO
                .toList();
    }

    @Override
    public void deleteDish(Long id) {
        dishRepository.deleteById(id); // Eliminamos el plato por su ID
    }

    @Override
    public DishDTO updateDish(Long id, DishDTO dishDTO) {
        return dishRepository.findById(id) // Buscamos el plato por ID
                .map(dish -> {
                    // Actualizamos los campos del plato con los valores del DTO
                    dish.setName(dishDTO.getName());
                    dish.setPrice(dishDTO.getPrice());
                    dish.setDishType(dishDTO.getDishType());
                    validateAndSetDefaults(dish);
                    Dish updatedDish = dishRepository.save(dish); // Guardamos el plato actualizado
                    return DishMapper.toDTO(updatedDish); // Devolvemos el DTO del plato actualizado
                })
                .orElseThrow(() -> new RuntimeException("El plato con el id " + id + " no pudo ser actualizado"));
    }

    @Override
    public void validateAndSetDefaults(Dish dish) {
        if (dish.getDishType() == null || dish.getDishType().isEmpty()) {
            dish.setDishType(DishType.COMMON.toString());
        }
    }
}