package vn.edu.iuh.fit.services;

import org.springframework.beans.factory.annotation.Autowired;
import vn.edu.iuh.fit.dtos.OrderCreatedEvent;
import vn.edu.iuh.fit.dtos.ProductQuantityRequest;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void handleQueue(OrderCreatedEvent event) {

        for (ProductQuantityRequest productQuantityRequest: event.getProducts()) {
            Product p = productRepository.findById(productQuantityRequest.getProductId()).orElse(null);
            if (p != null) {
                p.setStockQuantity(p.getStockQuantity() - productQuantityRequest.getQuantity());
                productRepository.save(p);
                System.out.println("So luong con lai: " + p.getStockQuantity());
            }
        }

    }
}
