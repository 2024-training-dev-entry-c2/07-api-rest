package com.restaurante.restaurante.chain;

import com.restaurante.restaurante.models.Dish;
import com.restaurante.restaurante.models.Orders;
import com.restaurante.restaurante.repositories.DishRepository;
import com.restaurante.restaurante.repositories.OrderRepository;
import com.restaurante.restaurante.utils.DishType;
import org.springframework.stereotype.Component;

@Component
public class DishPopularityHandler extends Handler {
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private static final Integer POPULAR_THRESHOLD = 100;
    private static final Double PRICE_INCREASE = 0.0573; // 5.73%

    public DishPopularityHandler(OrderRepository orderRepository, DishRepository dishRepository) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public void process(Orders order) {
        for (Dish dish : order.getDishes()) {
            // Obtener la versión actualizada del plato de la base de datos
            Dish currentDish = dishRepository.findById(dish.getId())
                    .orElseThrow(() -> new RuntimeException("Plato no encontrado: " + dish.getId()));

            // Incrementar el contador
            currentDish.setTotalOrdered(currentDish.getTotalOrdered() != null ?
                    currentDish.getTotalOrdered() + 1 : 1);

            // Verificar si alcanzó el 100 de popularidad
            if (currentDish.getTotalOrdered() >= POPULAR_THRESHOLD &&
                    !DishType.POPULAR.toString().equals(currentDish.getDishType())) {
                currentDish.setDishType(DishType.POPULAR.toString());
                Double newPrice = currentDish.getPrice() * (1 +PRICE_INCREASE);
                currentDish.setPrice(newPrice);
            }

            // Guardar los cambios
            dishRepository.save(currentDish);
        }

        if (nextHandler != null) {
            nextHandler.process(order);
        }
    }
}