package com.restaurant.restaurant.observer;

public interface IClientTypeObserver {
    void onClientTypeChange(Long CientId, String oldType, String newType);
}
