package com.restaurante.restaurante.decorator;
import com.restaurante.restaurante.models.Dish;
import com.restaurante.restaurante.models.Orders;

public class BaseOrderPrice implements OrderPrice {
    private final Orders order;

    public BaseOrderPrice(Orders order) {
        this.order = order;
    }

    @Override
    public Double calculatePrice() {
        // Siempre calcula el precio basado en los platos
        Double totalPrice = order.getDishes().stream()
                .mapToDouble(Dish::getPrice)
                .sum();

        // Actualiza el precio en la orden
        order.setTotalPrice(totalPrice);

        return totalPrice;
    }
}