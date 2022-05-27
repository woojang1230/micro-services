package se.magnus.microservices.composite.product.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import se.magnus.api.composite.product.*;
import se.magnus.api.core.product.Product;
import se.magnus.api.core.recommendation.Recommendation;
import se.magnus.api.core.review.Review;
import se.magnus.util.exceptions.NotFoundException;
import se.magnus.util.http.ServiceUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class ProductCompositeRestControllerImpl implements ProductCompositeRestController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeRestControllerImpl.class);
    private final ServiceUtil serviceUtil;
    private final ProductCompositeIntegration integration;

    @Autowired
    public ProductCompositeRestControllerImpl(ServiceUtil serviceUtil, ProductCompositeIntegration integration) {
        this.serviceUtil = serviceUtil;
        this.integration = integration;
    }

    @Override
    public void createCompositeProduct(final ProductAggregate body) {
        try {

            LOG.debug("createCompositeProduct: creates a new composite entity for productId: {}", body.getProductId());

            Product product = new Product(body.getProductId(), body.getName(), body.getWeight(), null);
            integration.createProduct(product);

            if (body.getRecommendations() != null) {
                body.getRecommendations().forEach(r -> {
                    Recommendation recommendation = new Recommendation(body.getProductId(), r.getRecommendationId(), r.getAuthor(), r.getRate(), r.getContent(), null);
                    integration.createRecommendation(recommendation);
                });
            }

            if (body.getReviews() != null) {
                body.getReviews().forEach(r -> {
                    Review review = new Review(body.getProductId(), r.getReviewId(), r.getAuthor(), r.getSubject(), r.getContent(), null);
                    integration.createReview(review);
                });
            }

            LOG.debug("createCompositeProduct: composite entites created for productId: {}", body.getProductId());

        } catch (RuntimeException re) {
            LOG.warn("createCompositeProduct failed", re);
            throw re;
        }
    }

    @Override
    public ResponseEntity<ProductAggregate> getProduct(int productId) {
        Product product = integration.getProduct(productId).getBody();
        validateProductIsNotNull(productId, product);
        final List<Recommendation> recommendations = integration.getRecommendations(productId).getBody();
        final List<Review> reviews = integration.getReviews(productId).getBody();
        return ResponseEntity.ok(createProductAggregate(product, recommendations, reviews, serviceUtil.getServiceAddress()));
    }

    @Override
    public void deleteProduct(final int productId) {

    }

    private void validateProductIsNotNull(final int productId, final Product product) {
        if (product == null) {
            throw new NotFoundException("No product found for productId: " + productId);
        }
    }

    private ProductAggregate createProductAggregate(final Product product, final List<Recommendation> recommendations,
                                                    final List<Review> reviews, final String serviceAddress) {
        // 1. Setup product info
        int productId = product.getProductId();
        String name = product.getName();
        int weight = product.getWeight();
        // 2. Copy summary recommendation info, if available
        List<RecommendationSummary> recommendationSummaries = getRecommendationSummaries(recommendations);
        // 3. Copy summary review info, if available
        List<ReviewSummary> reviewSummaries = getReviewSummaries(reviews);
        // 4. Create info regarding the involved microservices addresses
        ServiceAddresses serviceAddresses = makeServiceAddress(product, recommendations, reviews, serviceAddress);
        return new ProductAggregate(productId, name, weight, recommendationSummaries, reviewSummaries, serviceAddresses);
    }

    private List<RecommendationSummary> getRecommendationSummaries(final List<Recommendation> recommendations) {
        if (Objects.isNull(recommendations)) {
            return null;
        }
        return recommendations.stream()
                .map(r -> new RecommendationSummary(r.getRecommendationId(), r.getAuthor(), r.getRate(), r.getContent()))
                .collect(Collectors.toList());
    }

    private List<ReviewSummary> getReviewSummaries(final List<Review> reviews) {
        if (Objects.isNull(reviews)) {
            return null;
        }
        return reviews.stream()
                .map(r -> new ReviewSummary(r.getReviewId(), r.getAuthor(), r.getSubject(), r.getContent()))
                .collect(Collectors.toList());
    }

    private ServiceAddresses makeServiceAddress(final Product product, final List<Recommendation> recommendations,
                                                final List<Review> reviews, final String serviceAddress) {
        String productAddress = product.getServiceAddress();
        String reviewAddress = getReviewAddress(reviews);
        String recommendationAddress = getRecommendationAddress(recommendations);
        return new ServiceAddresses(serviceAddress, productAddress, reviewAddress, recommendationAddress);
    }

    private String getRecommendationAddress(final List<Recommendation> recommendations) {
        if (!Objects.isNull(recommendations) && !recommendations.isEmpty()) {
            return recommendations.get(0).getServiceAddress();
        }
        return "";
    }

    private String getReviewAddress(final List<Review> reviews) {
        if (!Objects.isNull(reviews) && !reviews.isEmpty()) {
            return reviews.get(0).getServiceAddress();
        }
        return "";
    }
}
