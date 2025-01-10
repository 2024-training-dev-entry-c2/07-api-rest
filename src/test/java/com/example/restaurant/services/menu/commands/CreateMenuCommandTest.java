package com.example.restaurant.services.menu.commands;

import com.example.restaurant.mappers.MenuMapper;
import com.example.restaurant.models.Menu;
import com.example.restaurant.models.dto.menu.MenuRequestDTO;
import com.example.restaurant.models.dto.menu.MenuResponseDTO;
import com.example.restaurant.repositories.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateMenuCommandTest {

    private final MenuRepository customerRepository = mock(MenuRepository.class);
    private final MenuMapper menuMapper = mock(MenuMapper.class);


    @InjectMocks
    private CreateMenuCommand createMenuCommand;

    @BeforeEach
    void setUps() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Creacion de un menu")
    void execute() {

        MenuRequestDTO menuRequestDTO = new MenuRequestDTO();
        menuRequestDTO.setName("Menu de otoño");
        menuRequestDTO.setDescription("Platos variados de la temporada de otoño");

        Menu menu = new Menu();
        menu.setName("Menu de otoño");
        menu.setDescription("Platos variados de la temporada de otoño");

        Menu savedMenu = new Menu();
        savedMenu.setMenuId(1L);
        savedMenu.setName("Menu de otoño");
        savedMenu.setDescription("Platos variados de la temporada de otoño");

        MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
        menuResponseDTO.setMenuId(1L);
        menuResponseDTO.setName("Menu de otoño");

        when(menuMapper.toEntity(any(MenuRequestDTO.class))).thenReturn(menu);
        when(customerRepository.save(any(Menu.class))).thenReturn(savedMenu);
        when(menuMapper.toDTO(any(Menu.class))).thenReturn(menuResponseDTO);

        MenuResponseDTO result = createMenuCommand.execute(menuRequestDTO);

        assertEquals(menuResponseDTO, result);

        Mockito.verify(menuMapper).toEntity(menuRequestDTO);
        Mockito.verify(customerRepository).save(menu);
    }
}