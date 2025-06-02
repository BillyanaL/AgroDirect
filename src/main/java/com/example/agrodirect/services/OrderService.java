package com.example.agrodirect.services;

import com.example.agrodirect.models.dtos.AddOrderDTO;
import com.example.agrodirect.models.dtos.OrderAdminViewDTO;
import com.example.agrodirect.models.entities.Order;
import com.example.agrodirect.models.entities.OrderItem;
import com.example.agrodirect.models.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    Order createOrder(AddOrderDTO addOrderDTO);

    void addMessageToOrder(Order order, String messageContent);

    void updateOrderItemStatus(Long orderItemId, OrderStatus newStatus, Long farmerId);

    List<OrderAdminViewDTO> getAllOrdersForAdmin();
    void updateOrderStatus(Long orderId, OrderStatus status);


}
