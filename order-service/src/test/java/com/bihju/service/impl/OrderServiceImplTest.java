package com.bihju.service.impl;

import com.bihju.model.ItemQuantity;
import com.bihju.model.Order;
import com.bihju.model.UserInfo;
import com.bihju.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {
    private OrderRepository orderRepository;
    private OrderServiceImpl orderService;
    private final String restaurantId = "11111111-1111-1111-11111111111111111";
    private UserInfo userInfo;

    @Before
    public void setupMock() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderServiceImpl(orderRepository);
        userInfo = new UserInfo();
        userInfo.setFirstName("First1");
        userInfo.setLastName("Last1");
        userInfo.setPhone("14081234567");
    }

    @Test
    public void whenCreateOrder_returnCreatedOrder() {
        List<ItemQuantity> itemQuantities = new ArrayList<>();
        itemQuantities.add(generateItemQuantity("Menu item 1", 11, 2));
        itemQuantities.add(generateItemQuantity("Menu item 2", 12, 3));
        Order order = generateOrder(restaurantId, itemQuantities, "Special", userInfo);
        when(orderRepository.save(order)).thenReturn(order);

        Order savedOrder = orderService.createOrder(order);
        assertThat(savedOrder.getTotalPrice()).isEqualTo(2 * 11 + 3 * 12);
        assertThat(savedOrder.getOrderTime()).isGreaterThan(0);
    }

    private Order generateOrder(String restaurantId, List<ItemQuantity> items, String specialNote, UserInfo userInfo) {
        Order order = new Order();
        order.setRestaurantId(restaurantId);
        order.setItems(items);
        order.setSpecialNote(specialNote);
        order.setUserInfo(userInfo);
        return order;
    }

    private ItemQuantity generateItemQuantity(String name, int price, int quantity) {
        ItemQuantity itemQuantity = new ItemQuantity();
        itemQuantity.setName(name);
        itemQuantity.setPrice(price);
        itemQuantity.setQuantity(quantity);
        return itemQuantity;
    }
}
