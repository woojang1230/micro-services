package se.magnus.api.core.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ProductRestController {
    @PostMapping(
            value    = "/product",
            consumes = "application/json",
            produces = "application/json")
    Product createProduct(@RequestBody Product body);

    @GetMapping(value = "/product/{productId}", produces = "application/json")
    ResponseEntity<Product> getProduct(@PathVariable("productId") int productId);

    @DeleteMapping(value = "/product/{productId}")
    void deleteProduct(@PathVariable int productId);
}
