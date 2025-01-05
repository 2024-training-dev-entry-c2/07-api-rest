package restaurant_managment.Utils.Handlers;

import org.springframework.stereotype.Component;
import restaurant_managment.Models.OrderModel;

@Component
public class FrequentCustomerHandler extends OrderHandler {
  @Override
  public void handle(OrderModel order) {
    // Lógica para manejar clientes frecuentes
    if (order.getReservation().getCustomer().getIsFrequent()) {
      // Manejar lógica de cliente frecuente
    }

    // Llamar al siguiente manejador en la cadena
    super.handle(order);
  }
}