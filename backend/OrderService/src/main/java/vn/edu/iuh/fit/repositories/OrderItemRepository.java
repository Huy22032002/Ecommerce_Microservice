package vn.edu.iuh.fit.repositories;

import org.springframework.data.repository.CrudRepository;
import vn.edu.iuh.fit.models.OrderItem;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);
}
