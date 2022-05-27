package se.magnus.api.core.review;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ReviewRestController {
    @PostMapping(
            value    = "/review",
            consumes = "application/json",
            produces = "application/json")
    Review createReview(@RequestBody Review body);

    @GetMapping(value = "/review", produces = "application/json")
    ResponseEntity<List<Review>> getReviews(@RequestParam(value = "productId", required = true) int productId);

    @DeleteMapping(value = "/review")
    void deleteReviews(@RequestParam(value = "productId", required = true)  int productId);
}
