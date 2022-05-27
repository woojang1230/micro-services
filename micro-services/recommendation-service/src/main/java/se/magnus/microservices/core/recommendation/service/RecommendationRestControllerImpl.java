package se.magnus.microservices.core.recommendation.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import se.magnus.api.core.recommendation.Recommendation;
import se.magnus.api.core.recommendation.RecommendationRestController;
import se.magnus.microservices.core.recommendation.persistence.RecommendationRepository;
import se.magnus.util.exceptions.InvalidInputException;
import se.magnus.util.http.ServiceUtil;

@RestController
public class RecommendationRestControllerImpl implements RecommendationRestController {
    private static final Logger LOG = LoggerFactory.getLogger(RecommendationRestControllerImpl.class);
    private final RecommendationRepository repository;
    private final RecommendationMapper mapper;
    private final ServiceUtil serviceUtil;

    public RecommendationRestControllerImpl(final RecommendationRepository repository,
                                            final RecommendationMapper mapper,
                                            final ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Recommendation createRecommendation(final Recommendation body) {
        return null;
    }

    @Override
    public ResponseEntity<List<Recommendation>> getRecommendations(final int productId) {
        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        if (productId == 113) {
            LOG.debug("No recommendations found for productId: {}", productId);
            return ResponseEntity.ok(new ArrayList<>());
        }

        List<Recommendation> list = new ArrayList<>();
        list.add(new Recommendation(productId, 1, "Author 1", 1, "Content 1", serviceUtil.getServiceAddress()));
        list.add(new Recommendation(productId, 2, "Author 2", 2, "Content 2", serviceUtil.getServiceAddress()));
        list.add(new Recommendation(productId, 3, "Author 3", 3, "Content 3", serviceUtil.getServiceAddress()));

        LOG.debug("/recommendation response size: {}", list.size());

        return ResponseEntity.ok(list);
    }

    @Override
    public void deleteRecommendations(final int productId) {

    }
}
