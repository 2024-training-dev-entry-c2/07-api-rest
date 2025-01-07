package com.example.demo.services;

import com.example.demo.DTO.dishfood.DishfoodRequestDTO;
import com.example.demo.DTO.dishfood.DishfoodResponseDTO;
import com.example.demo.DTO.converterDTO.DishfoodConverter;
import com.example.demo.models.Dishfood;
import com.example.demo.models.Menu;
import com.example.demo.repositories.DishfoodRepository;
import com.example.demo.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishFoodService {

    @Autowired
    private final DishfoodRepository dishfoodRepository;
    @Autowired
    private final MenuRepository menuRepository;



    public DishFoodService(DishfoodRepository dishfoodRepository, MenuRepository menuRepository) {
        this.dishfoodRepository = dishfoodRepository;
        this.menuRepository = menuRepository;
    }
    // Crear un nuevo plato
    public  DishfoodResponseDTO createDishFood(DishfoodRequestDTO dto){
        Menu menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(()-> new RuntimeException("Producto no pudo ser actualizado"));
        Dishfood dishfood = DishfoodConverter.toEntity(dto,menu);
        return  DishfoodConverter.toResponseDTO(dishfoodRepository.save(dishfood));

    }

    // Obtener todos los platos
    public List<DishfoodResponseDTO> getAllDishfoods() {
        return dishfoodRepository.findAll()
                .stream()
                .map(DishfoodConverter::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Obtener plato por ID
    public DishfoodResponseDTO getDishfoodById(Long id) {
        Dishfood dishfood = dishfoodRepository.findById(id).orElseThrow(() -> new RuntimeException("Dishfood not found"));
        return DishfoodConverter.toResponseDTO(dishfood);
    }

    // Actualizar un plato
    public DishfoodResponseDTO updateDishfood(Long id, DishfoodRequestDTO dishfoodRequestDTO) {
        Dishfood existingDishfood = dishfoodRepository.findById(id).orElseThrow(() -> new RuntimeException("Dishfood not found"));
        Menu menu = menuRepository.findById(dishfoodRequestDTO.getMenuId())
                .orElseThrow(() -> new RuntimeException("Menu not found"));
        existingDishfood.setName(dishfoodRequestDTO.getName());
        existingDishfood.setPrice(dishfoodRequestDTO.getPrice());
        existingDishfood.setIsPopular(dishfoodRequestDTO.getIsPopular());
        existingDishfood.setMenu(menu);

        return DishfoodConverter.toResponseDTO(dishfoodRepository.save(existingDishfood));
    }

    // Eliminar un plato
    public void deleteDishfood(Long id) {
        if (!dishfoodRepository.existsById(id)) {
            throw new RuntimeException("Dishfood not found");
        }
        dishfoodRepository.deleteById(id);
    }
}
