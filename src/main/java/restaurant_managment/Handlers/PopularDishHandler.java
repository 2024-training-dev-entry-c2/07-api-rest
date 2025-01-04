package restaurant_managment.Handlers;


import org.springframework.stereotype.Component;
import restaurant_managment.Models.DishModel;
import restaurant_managment.Models.OrderModel;

@Component
public class PopularDishHandler extends OrderHandler {
  @Override
  public void handle(OrderModel order) {
    // Lógica para manejar platos populares
    for (DishModel dish : order.getDishes()) {
      if (dish.getIsPopular()) {
        // Manejar lógica de plato popular
      }
    }

    // Llamar al siguiente manejador en la cadena
    super.handle(order);
  }
}