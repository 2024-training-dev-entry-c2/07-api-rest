package restaurant_managment.Utils.Handlers;


import org.springframework.stereotype.Component;
import restaurant_managment.Models.OrderModel;

@Component
public class DiscountHandler extends OrderHandler {
  @Override
  public void handle(OrderModel order) {
    // Lógica para aplicar descuento
    if (order.getReservation().getCustomer().getIsFrequent()) {
      // Aplicar descuento al pedido
    }

    // Llamar al siguiente manejador en la cadena
    super.handle(order);
  }
}