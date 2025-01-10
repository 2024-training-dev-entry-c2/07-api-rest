package com.restaurant.restaurant_management;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

class RestaurantManagementApplicationTest {

  @Test
  @DisplayName("Start Application")
  void main_startApplicationWithoutErrors() {
    try (var mockStatic = mockStatic(SpringApplication.class)) {

      RestaurantManagementApplication.main(new String[]{});

      mockStatic.verify(() -> SpringApplication.run(RestaurantManagementApplication.class, new String[]{}));
    }
  }
}