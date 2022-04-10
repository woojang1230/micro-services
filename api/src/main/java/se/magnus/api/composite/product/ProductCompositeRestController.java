package se.magnus.api.composite.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductCompositeRestController {
    @GetMapping(value = "/product-composite/{productId}", produces = "application/json")
    ResponseEntity<ProductAggregate> getProduct(@PathVariable int productId);
}
