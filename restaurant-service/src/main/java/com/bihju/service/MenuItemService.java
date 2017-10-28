package com.bihju.service;

import com.bihju.model.MenuItem;

import java.util.List;

public interface MenuItemService {
    List<MenuItem> findAllByRestaurantId(String rid);
    void createMenuItem(MenuItem menuItem);
    void uploadMenuItems(List<MenuItem> menuItems);
}
