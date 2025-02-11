package RecommendationService;

import dynamicService.DynamicRuleService;
import modelAndConstants.RecommendationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import repository.DynamicRulesRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationConditionElementsRulesService {
    private final List<RecommendationRuleSet> recommendationRuleSets;
    private final DynamicRuleService dynamicRuleService;
    private final DynamicRulesRepository dynamicRulesRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RecommendationConditionElementsRulesService(List<RecommendationRuleSet> recommendationRuleSets, DynamicRuleService dynamicRuleService, DynamicRulesRepository dynamicRulesRepository, JdbcTemplate jdbcTemplate) {
        this.recommendationRuleSets = recommendationRuleSets;
        this.dynamicRuleService = dynamicRuleService;
        this.dynamicRulesRepository = dynamicRulesRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public  RecommendationView getRecommendation(UUID userId) {
        return new RecommendationView(userId, recommendationRuleSets.stream()
                .flatMap(r -> r.getRecommendation(userId).stream())
                //flatMap отсеивает Null
                .collect(Collectors.toSet()));
        //Collectors.toSet собирает все рекомендации в множество исключив повторение
    }


}










// пройти по тому как устроена