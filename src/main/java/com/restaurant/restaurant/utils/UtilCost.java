package com.restaurant.restaurant.utils;

public class UtilCost {
  private static final double DESC_CLIENT_FRECUENT = 0.0238;
  private static final double INCR_DISH_POPULAR = 0.0573;

  public static Double applyDescClientFrecuent(Double price){
    Double descFrecuent = price * DESC_CLIENT_FRECUENT;
    return price - descFrecuent;
  }

  public static Double applyIncrDishPopular(Double price){
    Double incrPopular = price * INCR_DISH_POPULAR;
    return price + incrPopular;
  }
}
