package com.example.demo.services;

import com.example.demo.DTO.converterDTO.MenuConverter;
import com.example.demo.DTO.menu.MenuRequestDTO;
import com.example.demo.DTO.menu.MenuResponseDTO;
import com.example.demo.models.Menu;
import com.example.demo.repositories.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    private MenuRequestDTO dto;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dto= new MenuRequestDTO("Menu verano");
    }

    @Test
    void createMenu() {
        Menu menu = MenuConverter.toEntity(dto);
        when(menuRepository.save(any(Menu.class))).thenReturn(menu);
        MenuResponseDTO responseDTO= menuService.createMenu(dto);
        assertNotNull(responseDTO);
        assertEquals("Menu verano",responseDTO.getName());
        verify(menuRepository).save(any(Menu.class));
    }

    @Test
    void getAllMenus() {
        when(menuRepository.findAll()).thenReturn(getMenuList());
        List<MenuResponseDTO> responseDTO = menuService.getAllMenus();
        assertNotNull(responseDTO);
        assertEquals("Menu verano",responseDTO.get(0).getName());
        assertEquals("Menu mexicano",responseDTO.get(1).getName());
        assertEquals("Menu peruano",responseDTO.get(2).getName());
        verify(menuRepository).findAll();
    }

    @Test
    void getMenuById() {
        Menu menu = MenuConverter.toEntity(dto);
        when(menuRepository.findById(any())).thenReturn(Optional.of(menu));
        MenuResponseDTO menuResponseDTO= menuService.getMenuById(1L);
        assertNotNull(menuResponseDTO);
        assertEquals("Menu verano",menuResponseDTO.getName());
        verify(menuRepository).findById(any());
    }

    @Test
    void updateMenu() {
        Menu menu = new Menu();
        menu.setName("menu prueba");
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(menuRepository.save(menu)).thenReturn(menu);
        MenuResponseDTO responseDTO = menuService.updateMenu(1L,dto);
        assertNotNull(responseDTO);
        assertEquals("Menu verano",responseDTO.getName());
        verify(menuRepository).findById(any());
        verify(menuRepository).save(menu);

    }

    @Test
    void deleteMenu() {
        Long menuId=1L;
        when(menuRepository.existsById(menuId)).thenReturn(true);
        menuService.deleteMenu(menuId);
        verify(menuRepository).existsById(menuId);
        verify(menuRepository).deleteById(menuId);
    }
    @Test
    void deleteMenuError() {
        Long menuId = 1L;
        when(menuRepository.existsById(menuId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> menuService.deleteMenu(menuId));
        assertEquals("Menu not found", exception.getMessage());

        verify(menuRepository).existsById(menuId);
        verify(menuRepository, never()).deleteById(menuId);
    }
    private List<Menu> getMenuList() {
        return List.of(
                Menu.builder().id(1L).name("Menu verano").build(),
                Menu.builder().id(2L).name("Menu mexicano").build(),
                Menu.builder().id(3L).name("Menu peruano").build()
        );

    }

}