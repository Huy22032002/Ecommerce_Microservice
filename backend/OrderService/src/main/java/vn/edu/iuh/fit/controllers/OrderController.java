package vn.edu.iuh.fit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.dtos.OrderCreatedEvent;
import vn.edu.iuh.fit.dtos.OrderItemRequest;
import vn.edu.iuh.fit.dtos.OrderRequest;
import vn.edu.iuh.fit.dtos.ProductQuantityRequest;
import vn.edu.iuh.fit.models.Order;
import vn.edu.iuh.fit.models.OrderItem;
import vn.edu.iuh.fit.services.MessageProducer;
import vn.edu.iuh.fit.services.OrderItemService;
import vn.edu.iuh.fit.services.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private MessageProducer messageProducer;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderService.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemRequest orderItemRequest: request.getListOrderItemRequest()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setProductId(orderItemRequest.getProductId());

            orderItems.add(orderItem);
        }
        orderItemService.saveAll(orderItems);
        //goi message queue gui qua Product Service
        List<ProductQuantityRequest> listProductQuantityRequest = new ArrayList<>();
        for (OrderItem orderItem: orderItems) {
            ProductQuantityRequest productQuantityRequest = new ProductQuantityRequest();
            productQuantityRequest.setProductId(orderItem.getProductId());
            productQuantityRequest.setQuantity(orderItem.getQuantity());

            listProductQuantityRequest.add(productQuantityRequest);
        }
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        orderCreatedEvent.setOrderId(savedOrder.getId());
        orderCreatedEvent.setTimestamp(LocalDateTime.now());
        orderCreatedEvent.setProducts(listProductQuantityRequest);

        messageProducer.sendProductQuantity(orderCreatedEvent);

        return ResponseEntity.ok(savedOrder);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.ok("Deleted Order: " + id );
    }
}
