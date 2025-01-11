package com.restaurant.restaurant.observer.observers;

import com.restaurant.restaurant.observer.IClientTypeObserver;
import org.springframework.stereotype.Component;

@Component
public class ClientTypeNotificationObserver implements IClientTypeObserver {
  @Override
  public void onClientTypeChange(Long clientId, String oldType, String newType) {
    if(oldType.equals("COMUN") && newType.equals("FRECUENT")){
      System.out.println("Client " + clientId + " has promoted to frecuent");
    }
  }
}
