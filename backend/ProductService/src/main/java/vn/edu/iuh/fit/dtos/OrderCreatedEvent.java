package vn.edu.iuh.fit.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
    private String event = "order.created";
    private Long orderId;
    private List<ProductQuantityRequest> products;
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "event='" + event + '\'' +
                ", orderId=" + orderId +
                ", products=" + products +
                ", timestamp=" + timestamp +
                '}';
    }
}
