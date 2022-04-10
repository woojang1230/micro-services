package se.magnus.microservices.composite.product.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import se.magnus.api.core.product.Product;
import se.magnus.api.core.product.ProductRestController;
import se.magnus.api.core.recommendation.Recommendation;
import se.magnus.api.core.recommendation.RecommendationRestController;
import se.magnus.api.core.review.Review;
import se.magnus.api.core.review.ReviewRestController;
import se.magnus.util.exceptions.InvalidInputException;
import se.magnus.util.exceptions.NotFoundException;
import se.magnus.util.http.HttpErrorInfo;

@Component
public class ProductCompositeIntegration implements ProductRestController, RecommendationRestController,
        ReviewRestController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeIntegration.class);
    public static final String PRE_URL_HTTP = "http://";
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;

    @Autowired
    public ProductCompositeIntegration(final RestTemplate restTemplate, final ObjectMapper mapper,
                                       @Value("${app.product-service.host}") final String productServiceHost,
                                       @Value("${app.product-service.port}") final int productServicePort,
                                       @Value("${app.recommendation-service.host}") final String recommendationServiceHost,
                                       @Value("${app.recommendation-service.port}") final int recommendationServicePort,
                                       @Value("${app.review-service.host}") final String reviewServiceHost,
                                       @Value("${app.review-service.port}") final int reviewServicePort) {

        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.productServiceUrl = PRE_URL_HTTP + productServiceHost + ":" + productServicePort + "/product/";
        this.recommendationServiceUrl = PRE_URL_HTTP + recommendationServiceHost + ":" + recommendationServicePort + "/recommendation?productId=";
        this.reviewServiceUrl = PRE_URL_HTTP + reviewServiceHost + ":" + reviewServicePort + "/review?productId=";
    }

    @Override
    public ResponseEntity<Product> getProduct(int productId) {
        try {
            String url = productServiceUrl + productId;
            LOG.debug("Will call getProduct API on URL: {}", url);
            Product product = restTemplate.getForObject(url, Product.class);
            LOG.debug("Found a product with id: {}", product.getProductId());
            return ResponseEntity.ok(product);
        } catch (HttpClientErrorException ex) {
            throwException(ex);
            LOG.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
            LOG.warn("Error body: {}", ex.getResponseBodyAsString());
            throw ex;
        }
    }

    private void throwException(final HttpClientErrorException ex) {
        final HttpStatus statusCode = ex.getStatusCode();
        if (statusCode.equals(NOT_FOUND)) {
            throw new NotFoundException(getErrorMessage(ex));
        }
        if (statusCode.equals(UNPROCESSABLE_ENTITY)) {
            throw new InvalidInputException(getErrorMessage(ex));
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioex) {
            return ex.getMessage();
        }
    }

    @Override
    public ResponseEntity<List<Recommendation>> getRecommendations(int productId) {
        try {
            String url = recommendationServiceUrl + productId;
            LOG.debug("Will call getRecommendations API on URL: {}", url);
            final ResponseEntity<List<Recommendation>> recommendations = restTemplate.exchange(url, GET, null,
                    new ParameterizedTypeReference<List<Recommendation>>() {
                    });
            LOG.debug("Found {} recommendations for a product with id: {}", recommendations.getBody().size(), productId);
            return recommendations;
        } catch (Exception ex) {
            LOG.warn("Got an exception while requesting recommendations, return zero recommendations: {}", ex.getMessage());
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @Override
    public ResponseEntity<List<Review>> getReviews(int productId) {
        try {
            String url = reviewServiceUrl + productId;
            LOG.debug("Will call getReviews API on URL: {}", url);
            ResponseEntity<List<Review>> reviews = restTemplate.exchange(url, GET, null,
                    new ParameterizedTypeReference<List<Review>>() {});
            LOG.debug("Found {} reviews for a product with id: {}", reviews.getBody().size(), productId);
            return reviews;
        } catch (Exception ex) {
            LOG.warn("Got an exception while requesting reviews, return zero reviews: {}", ex.getMessage());
            return ResponseEntity.ok(new ArrayList<>());
        }
    }
}
