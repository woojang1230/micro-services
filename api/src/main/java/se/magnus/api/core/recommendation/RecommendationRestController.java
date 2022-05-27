package se.magnus.api.core.recommendation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface RecommendationRestController {
    @PostMapping(
            value    = "/recommendation",
            consumes = "application/json",
            produces = "application/json")
    Recommendation createRecommendation(@RequestBody Recommendation body);

    @GetMapping(value = "/recommendation", produces = "application/json")
    ResponseEntity<List<Recommendation>> getRecommendations(@RequestParam(value = "productId", required = true) int productId);

    @DeleteMapping(value = "/recommendation")
    void deleteRecommendations(@RequestParam(value = "productId", required = true)  int productId);
}
