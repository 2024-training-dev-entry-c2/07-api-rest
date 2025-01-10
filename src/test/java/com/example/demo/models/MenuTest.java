package com.example.demo.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class MenuTest {

    private Menu menu;

    @BeforeEach
    void setUp() {
        menu = new Menu();
    }

    @Test
    void testMenuDefaultConstructor() {
        assertNull(menu.getId());
        assertNull(menu.getName());
        assertNull(menu.getDishfoods());
    }

    @Test
    void testMenuParameterizedConstructor() {
        menu = new Menu("Dinner Menu");

        assertNull(menu.getId());
        assertEquals("Dinner Menu", menu.getName());
        assertNull(menu.getDishfoods());
    }

    @Test
    void testMenuBuilder() {
        Dishfood dish1 = Dishfood.builder().id(1L).name("Pizza").price(15.0).build();
        Dishfood dish2 = Dishfood.builder().id(2L).name("Pasta").price(12.0).build();

        menu = Menu.builder()
                .id(1L)
                .name("Lunch Menu")
                .dishfoods(List.of(dish1, dish2))
                .build();

        assertEquals(1L, menu.getId());
        assertEquals("Lunch Menu", menu.getName());
        assertNotNull(menu.getDishfoods());
        assertEquals(2, menu.getDishfoods().size());
    }

    @Test
    void testSettersAndGetters() {
        Dishfood dish1 = Dishfood.builder().id(1L).name("Burger").price(10.0).build();

        menu.setId(2L);
        menu.setName("Breakfast Menu");
        menu.setDishfoods(List.of(dish1));

        assertEquals(2L, menu.getId());
        assertEquals("Breakfast Menu", menu.getName());
        assertNotNull(menu.getDishfoods());
        assertEquals(1, menu.getDishfoods().size());
        assertEquals("Burger", menu.getDishfoods().get(0).getName());
    }

    @Test
    void testDishfoodAssociation() {
        Dishfood dish1 = Dishfood.builder().id(1L).name("Sushi").price(20.0).build();
        Dishfood dish2 = Dishfood.builder().id(2L).name("Ramen").price(18.0).build();

        menu.setDishfoods(List.of(dish1, dish2));

        assertEquals(2, menu.getDishfoods().size());
        assertEquals("Sushi", menu.getDishfoods().get(0).getName());
        assertEquals("Ramen", menu.getDishfoods().get(1).getName());
    }
}
