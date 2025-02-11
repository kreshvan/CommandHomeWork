package RecommendationService;

import modelAndConstants.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<Recommendation>getRecommendation(UUID userId);
}
