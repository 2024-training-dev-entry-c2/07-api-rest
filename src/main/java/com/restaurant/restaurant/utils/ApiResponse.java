package com.restaurant.restaurant.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
  private Boolean success;
  private String message;
  private T data;

  public static <T> ApiResponse<T> success(T data){
    return new ApiResponse<>(true, "Success Operation", data);
  }

  public static <T> ApiResponse<T> success(String message, T data){
    return new ApiResponse<>(true, message, data);
  }
}
