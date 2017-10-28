package com.bihju.service;

import com.bihju.model.Restaurant;

public interface RestaurantService {
    void createRestaurant(Restaurant restaurant);
    Restaurant getRestaurantByName(String name);
}
