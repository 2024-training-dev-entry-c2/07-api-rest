package com.restaurant.restaurant.observer;

import com.restaurant.restaurant.models.ClientModel;
import com.restaurant.restaurant.models.DishModel;

public class ObserverRegister {

  private final Subject<ClientModel> clientSubject = new Subject<>();
  private final Subject<DishModel> dishSubject = new Subject<>();

  public Subject<ClientModel> getClientSubject() {
    return clientSubject;
  }

  public Subject<DishModel> getDishSubject() {
    return dishSubject;
  }
}
