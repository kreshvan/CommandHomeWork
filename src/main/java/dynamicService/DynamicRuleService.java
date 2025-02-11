package dynamicService;

import dynamicRuleModel.ConditionElementsRules;
import dynamicRuleModel.DynamicRules;
import modelAndConstants.Info;
import modelAndConstants.Recommendation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import repository.DynamicRulesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DynamicRuleService {
    private final Logger logger = LoggerFactory.getLogger(DynamicRules.class);// ошибки в работе приложения (УТОЧНИТЬ)
    private final DynamicRulesRepository dynamicRulesRepository;


    public DynamicRuleService(DynamicRulesRepository dynamicRulesRepository) {
        this.dynamicRulesRepository = dynamicRulesRepository;
    }

    public DynamicRules createDynamicRule(DynamicRules dynamicRules) {
        logger.info("Создание нового дин.правила + новое {id}", dynamicRules.getProductId(), dynamicRules.getProductName());
        return dynamicRulesRepository.save(dynamicRules);
    }

    public DynamicRules deleteDynamicRule(long id) {

        DynamicRules dynamicRulesForDelete = dynamicRulesRepository.findById(id).orElseThrow(() -> new DynamicRulesNotFoundException(id));
        dynamicRulesRepository.deleteById(id);
        return dynamicRulesForDelete;

    }

    public List<DynamicRules> findAll() {
        return dynamicRulesRepository.findAll();
    }

    public List<Recommendation> getRecommendationByDynamicRules(UUID userId) {
        List<DynamicRules> allDynamicRules = dynamicRulesRepository.findAll();
        List<Info> newInfo = new ArrayList<>();
        for (DynamicRules dynamicRules : allDynamicRules) {
            boolean dynamicRuleResult =
        }


    }


    public boolean isDynamicRulesSuitable(DynamicRules dynamicRule, Long id) {
        for (ConditionElementsRules conditionElementsRule : dynamicRule.getConditions()) {
            boolean conditionElementsRuleResult =

            return true;
            //метод должен определять подходит или нет дин правило. (рекомендация)

            //если все условия выполня то  тру

            //пройтись по всем condition
        }


    }

    public boolean processQuery(Long userId, ConditionElementsRules conditionElementsRules) {
        switch (conditionElementsRules.getQuery()) { //switch что-то вроде if
            case "USER_OF":
                return evaluateUserOf(userId, conditionElementsRules);
            case "ACTIVE_USER_OF":
                return evvaluateActiveUserOf(userId, conditionElementsRules);
            case "TRANSACTION_SUM_COMPARE":
                return evvaluateTransactionSumCompare(userId, conditionElementsRules);
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW":
                return evvaluateTransactionSumCompareDepositWithdraw(userId, conditionElementsRules);

        }
    }


    public boolean evaluateUserOf(Long userId, ConditionElementsRules conditionElementsRules) {
        String productTape = conditionElementsRules.getArguments()[0];

    }

}





