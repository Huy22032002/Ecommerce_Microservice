package vn.edu.iuh.fit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.OrderItem;
import vn.edu.iuh.fit.repositories.OrderItemRepository;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> getAllItems() {
        return (List<OrderItem>) orderItemRepository.findAll();
    }

    public List<OrderItem> getByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public OrderItem createItem(OrderItem item) {
        return orderItemRepository.save(item);
    }

    public void deleteItem(Long id) {
        orderItemRepository.deleteById(id);
    }

    public void saveAll(List<OrderItem> items) {
        orderItemRepository.saveAll(items);
    }
}
