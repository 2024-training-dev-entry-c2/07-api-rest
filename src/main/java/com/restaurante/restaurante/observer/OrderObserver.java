package com.restaurante.restaurante.observer;

import com.restaurante.restaurante.models.Orders;

public interface OrderObserver {
    void onOrderCreated(Orders order);
}
