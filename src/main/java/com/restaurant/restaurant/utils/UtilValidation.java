package com.restaurant.restaurant.utils;

import com.restaurant.restaurant.exceptions.ResourceNotFoundException;

import java.util.Optional;

public class UtilValidation {
  public static void validateResourceExists(Optional<?> resource, String resourceName, Long id){
    if(resource.isEmpty()){
      throw new ResourceNotFoundException(resourceName + " not found with id " + id);
    }
  }
}
