package vn.edu.iuh.fit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.models.OrderItem;
import vn.edu.iuh.fit.services.OrderItemService;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public List<OrderItem> getAll() {
        return orderItemService.getAllItems();
    }

    @GetMapping("/order/{orderId}")
    public List<OrderItem> getByOrder(@PathVariable Long orderId) {
        return orderItemService.getByOrderId(orderId);
    }

    @PostMapping
    public OrderItem create(@RequestBody OrderItem item) {
        return orderItemService.createItem(item);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderItemService.deleteItem(id);
    }
}
