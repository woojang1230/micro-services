package se.magnus.api.core.review;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReviewRestController {
    @GetMapping(value = "/review", produces = "application/json")
    ResponseEntity<List<Review>> getReviews(@RequestParam(value = "productId", required = true) int productId);
}
