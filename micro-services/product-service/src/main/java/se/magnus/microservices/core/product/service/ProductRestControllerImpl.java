package se.magnus.microservices.core.product.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import se.magnus.api.core.product.Product;
import se.magnus.api.core.product.ProductRestController;
import se.magnus.util.exceptions.InvalidInputException;
import se.magnus.util.exceptions.NotFoundException;
import se.magnus.util.http.ServiceUtil;

@RestController
public class ProductRestControllerImpl implements ProductRestController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductRestControllerImpl.class);
    private final ServiceUtil serviceUtil;

    @Autowired
    public ProductRestControllerImpl(final ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public ResponseEntity<Product> getProduct(final int productId) {
        validateTestProductId(productId);
        return ResponseEntity.ok(new Product(productId, "name-" + productId, 123,
                serviceUtil.getServiceAddress()));
    }

    private void validateTestProductId(final int productId) {
        LOG.debug("/product return the found product for productId={}", productId);
        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }
        if (productId == 13) {
            throw new NotFoundException("No product found for productId: " + productId);
        }
    }
}
