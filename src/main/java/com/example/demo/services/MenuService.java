package com.example.demo.services;

import com.example.demo.DTO.menu.MenuRequestDTO;
import com.example.demo.DTO.menu.MenuResponseDTO;
import com.example.demo.DTO.converterDTO.MenuConverter;
import com.example.demo.models.Menu;
import com.example.demo.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public MenuResponseDTO createMenu(MenuRequestDTO menuRequestDTO) {
        Menu menu = MenuConverter.toEntity(menuRequestDTO);
        return MenuConverter.toResponseDTO(menuRepository.save(menu));
    }

    public List<MenuResponseDTO> getAllMenus() {
        return menuRepository.findAll()
                .stream()
                .map(MenuConverter::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MenuResponseDTO getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));
        return MenuConverter.toResponseDTO(menu);
    }

    public MenuResponseDTO updateMenu(Long id, MenuRequestDTO menuRequestDTO) {
        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        existingMenu.setName(menuRequestDTO.getName());
        Menu updatedMenu = menuRepository.save(existingMenu);

        return MenuConverter.toResponseDTO(updatedMenu);
    }

    public void deleteMenu(Long id) {
        if (!menuRepository.existsById(id)) {
            throw new RuntimeException("Menu not found");
        }
        menuRepository.deleteById(id);
    }
}