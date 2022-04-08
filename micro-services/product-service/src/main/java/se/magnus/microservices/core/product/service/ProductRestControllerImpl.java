package se.magnus.microservices.core.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import se.magnus.api.core.product.Product;
import se.magnus.api.core.product.ProductRestController;
import se.magnus.util.http.ServiceUtil;

@RestController
public class ProductRestControllerImpl implements ProductRestController {
    private final ServiceUtil serviceUtil;

    @Autowired
    public ProductRestControllerImpl(final ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public ResponseEntity<Product> getProduct(final int productId) {
        return ResponseEntity.ok(new Product(productId, "name-" + productId, 123,
                serviceUtil.getServiceAddress()));
    }
}
