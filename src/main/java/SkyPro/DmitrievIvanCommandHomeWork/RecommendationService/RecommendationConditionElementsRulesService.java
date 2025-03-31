package SkyPro.DmitrievIvanCommandHomeWork.RecommendationService;

import SkyPro.DmitrievIvanCommandHomeWork.dynamicService.DynamicRuleService;
import SkyPro.DmitrievIvanCommandHomeWork.modelAndConstants.Recommendation;
import SkyPro.DmitrievIvanCommandHomeWork.modelAndConstants.RecommendationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationConditionElementsRulesService {
    private final List<RecommendationRuleSet> recommendationRuleSets;
    private final DynamicRuleService dynamicRuleService;


    public RecommendationConditionElementsRulesService(List<RecommendationRuleSet> recommendationRuleSets, DynamicRuleService dynamicRuleService) {
        this.recommendationRuleSets = recommendationRuleSets;
        this.dynamicRuleService = dynamicRuleService;

    }

    public RecommendationView getRecommendation(UUID userId) {

        RecommendationView recommendationView = new RecommendationView(userId, recommendationRuleSets.stream()
                .flatMap(r -> r.getRecommendation(userId).stream())
                .collect(Collectors.toSet()));


        List<Recommendation> recommendationByDynamicRules = dynamicRuleService.getRecommendationsByUserId(userId);
        recommendationView.addRecommendations(recommendationByDynamicRules);
        return recommendationView;
    }


}


