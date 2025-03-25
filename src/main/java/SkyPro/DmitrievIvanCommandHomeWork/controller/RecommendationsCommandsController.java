package SkyPro.DmitrievIvanCommandHomeWork.controller;

import SkyPro.DmitrievIvanCommandHomeWork.modelAndConstants.RecommendationView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import SkyPro.DmitrievIvanCommandHomeWork.RecommendationService.RecommendationConditionElementsRulesService;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RecommendationsCommandsController {
    private RecommendationConditionElementsRulesService recommendationService;

    public RecommendationsCommandsController(RecommendationConditionElementsRulesService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @GetMapping("/recommendation/{user_id}")//ПОЗЖЕ изменить НАЗВАНИЯ по базе данных
    public RecommendationView getRecommendation(@PathVariable("user_id") UUID userId) {
        return recommendationService.getRecommendation(userId);
    }


}
