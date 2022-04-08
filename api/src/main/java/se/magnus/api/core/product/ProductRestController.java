package se.magnus.api.core.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductRestController {
    @GetMapping(value = "/product/{productId}", produces = "application/json")
    ResponseEntity<Product> getProduct(@PathVariable("productId") int productId);
}
