package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.dtos.AddOrderDTO;
import com.example.agrodirect.models.dtos.CartItemsViewDTO;
import com.example.agrodirect.models.dtos.OrderAdminViewDTO;
import com.example.agrodirect.models.dtos.OrderProductInfoDTO;
import com.example.agrodirect.models.entities.*;
import com.example.agrodirect.models.enums.OrderStatus;
import com.example.agrodirect.repositories.*;
import com.example.agrodirect.services.CartService;
import com.example.agrodirect.services.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final CartService cartService;

    private final ProductRepository productRepository;

    private final OrderMessageRepository orderMessageRepository;

    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, CartService cartService, ProductRepository productRepository, OrderMessageRepository orderMessageRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.orderMessageRepository = orderMessageRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    @Override
    public Order createOrder(AddOrderDTO addOrderDTO) {

        if (addOrderDTO.getCartItems() == null || addOrderDTO.getCartItems().isEmpty()) {
            System.out.println("Количката е празна!");
            throw new IllegalArgumentException("No items in cart");
        }

        double totalPrice = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItemsViewDTO cartItemDTO : addOrderDTO.getCartItems()) {

            Product product = productRepository.findById(cartItemDTO.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            if (product.getQuantity() < cartItemDTO.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }

            double price = product.getPrice() * cartItemDTO.getQuantity();
            totalPrice += price;

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItemDTO.getQuantity());
            orderItem.setPrice(price);
            orderItem.setStatus(OrderStatus.PENDING);
            orderItems.add(orderItem);

        }

        User user = userRepository.findById(addOrderDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ShippingDetails shippingDetails = new ShippingDetails(
                addOrderDTO.getShippingDetails().getFirstName(),
                addOrderDTO.getShippingDetails().getLastName(),
                addOrderDTO.getShippingDetails().getEmail(),
                addOrderDTO.getShippingDetails().getStreet(),
                addOrderDTO.getShippingDetails().getStreetNumber(),
                addOrderDTO.getShippingDetails().getCity(),
                addOrderDTO.getShippingDetails().getState(),
                addOrderDTO.getShippingDetails().getZip(),
                addOrderDTO.getShippingDetails().getPhone(),
                addOrderDTO.getShippingDetails().getCountry(),
                addOrderDTO.getShippingDetails().getFormOrderNotes()
        );

        Order order = new Order();
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);
        order.setShippingDetails(shippingDetails);
        order.setPaymentMethod(addOrderDTO.getPaymentMethod());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        order.setDeliveryDate(LocalDate.now().plusDays(3));

        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }
        order.setOrderItems(orderItems);

        orderRepository.save(order);

        updateProductsByFarmer(orderItems);

        cartService.clearCart(user);

        System.out.println("Поръчката е записана успешно: " + order.getId());

        return order;
    }

    @Override
    public void addMessageToOrder(Order order, String messageContent) {
        OrderMessage message = new OrderMessage();
        message.setOrder(order);
        message.setContent(messageContent);
        message.setTimestamp(LocalDateTime.now());

        if (order.getMessages() == null) {
            order.setMessages(new ArrayList<>());
        }

        order.getMessages().add(message);
        orderMessageRepository.save(message);
    }

    @Override
    public void updateOrderItemStatus(Long orderItemId, OrderStatus newStatus, Long farmerId) {
        OrderItem item = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalArgumentException("Продуктът от поръчката не е намерен."));

        if (!item.getProduct().getFarmer().getId().equals(farmerId)) {
            throw new SecurityException("Нямате права да променяте статуса на този продукт.");
        }

        item.setStatus(newStatus);
        orderItemRepository.save(item);

        String message = String.format("Фермерът промени статуса на продукта \"%s\" на: %s",
                item.getProduct().getName(), newStatus.getDisplayName());
        addMessageToOrder(item.getOrder(), message);
    }

    private void updateProductsByFarmer(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            Product product = orderItem.getProduct();
            User farmer = product.getFarmer();

            product.setQuantity(product.getQuantity() - orderItem.getQuantity());
            productRepository.save(product);
        }
    }

    @Override
    public List<OrderAdminViewDTO> getAllOrdersForAdmin() {
        return orderRepository.findAll().stream().map(order -> {
            OrderAdminViewDTO dto = new OrderAdminViewDTO();
            dto.setOrderId(order.getId());
            dto.setCustomerName(order.getUser().getFirstName() + " " + order.getUser().getLastName());
            dto.setOrderDate(order.getOrderDate().atStartOfDay());
            dto.setTotalSum(BigDecimal.valueOf(order.getTotalPrice()));
            dto.setOrderStatus(order.getStatus());

            List<OrderProductInfoDTO> products = order.getOrderItems().stream().map(item -> {
                return new OrderProductInfoDTO(
                        item.getProduct().getName(),
                        item.getProduct().getFarmer().getFirstName() + " " + item.getProduct().getFarmer().getLastName(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getStatus()
                );
            }).toList();

            dto.setProducts(products);
            return dto;
        }).toList();
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Поръчката не е намерена."));
        order.setStatus(newStatus);
        orderRepository.save(order);
    }




}
