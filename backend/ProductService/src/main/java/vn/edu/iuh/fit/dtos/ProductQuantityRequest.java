package vn.edu.iuh.fit.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantityRequest {
    private Long productId;
    private int quantity;

    @Override
    public String toString() {
        return "ProductQuantityRequest{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}


