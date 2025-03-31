package SkyPro.DmitrievIvanCommandHomeWork.dynamicService;

import SkyPro.DmitrievIvanCommandHomeWork.dynamicRuleModel.ConditionElementsRules;
import SkyPro.DmitrievIvanCommandHomeWork.dynamicRuleModel.DynamicRules;
import SkyPro.DmitrievIvanCommandHomeWork.modelAndConstants.ProductTypeConstants;
import SkyPro.DmitrievIvanCommandHomeWork.modelAndConstants.Recommendation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import SkyPro.DmitrievIvanCommandHomeWork.repository.DynamicRulesRepository;
import SkyPro.DmitrievIvanCommandHomeWork.repository.RecommendationsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DynamicRuleService {
    private final Logger logger = LoggerFactory.getLogger(DynamicRules.class);// ошибки в работе приложения (УТОЧНИТЬ)
    private final DynamicRulesRepository dynamicRulesRepository;
    private final RecommendationsRepository recommendationsRepository;

    public DynamicRuleService(DynamicRulesRepository dynamicRulesRepository, RecommendationsRepository recommendationsRepository) {
        this.dynamicRulesRepository = dynamicRulesRepository;
        this.recommendationsRepository = recommendationsRepository;
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


    public List<Recommendation> getRecommendationsByUserId(UUID userId) {
        List<DynamicRules> allDynamicRules = dynamicRulesRepository.findAll();
        List<Recommendation> result = new ArrayList<>();
        for (DynamicRules dynamicRules : allDynamicRules) {
            if (checkDynamicRulesSuitable(userId, dynamicRules)) {
                Recommendation recommendation = dynamicRules.extractRecommendation();
                result.add(recommendation);
            }
        }
        return result;
    }


    public boolean checkDynamicRulesSuitable(UUID id, DynamicRules dynamicRule) {
        for (ConditionElementsRules conditionElementsRule : dynamicRule.getConditions()) {
            boolean conditionElementsRuleResult = processQuery(id, conditionElementsRule);

            if (conditionElementsRule.isNegate()) {
                conditionElementsRuleResult = !conditionElementsRuleResult;

            }
            if (!conditionElementsRuleResult) {
                return false;
            }

        }
        return true;

    }

    public boolean processQuery(UUID userId, ConditionElementsRules conditionElementsRules) {
        System.out.println(userId.toString() + " - " + conditionElementsRules.getQuery());
        switch (conditionElementsRules.getQuery()) {
            case "USER_OF":
                return evaluateUserOf(userId, conditionElementsRules);
            case "ACTIVE_USER_OF":
                return evaluateActiveUserOf(userId, conditionElementsRules);
            case "TRANSACTION_SUM_COMPARE":
                return evaluateTransactionSumCompare(userId, conditionElementsRules);
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW":
                return evaluateTransactionSumCompareDepositWithdraw(userId, conditionElementsRules);
            default:
                throw new ConditionElementsRuleNotFoundException("Условия элементы правила не найдены!");
        }
    }


    public boolean evaluateUserOf(UUID userId, ConditionElementsRules conditionElementsRules) {
        List<String> productTape = conditionElementsRules.getArguments();
        ProductTypeConstants productTypeConstants = ProductTypeConstants.valueOf(productTape.get(0));
        boolean result = recommendationsRepository.checkTransactionProductUser(userId, productTypeConstants);
        return conditionElementsRules.isNegate() ? !result : result;//

    }

    public boolean evaluateActiveUserOf(UUID userId, ConditionElementsRules conditionElementsRules) {
        List<String> productTape = conditionElementsRules.getArguments();
        ProductTypeConstants productTypeConstants = ProductTypeConstants.valueOf(productTape.get(0));
        boolean result = recommendationsRepository.checkTransactionProductUser(userId, productTypeConstants);
        return conditionElementsRules.isNegate() ? !result : result;

    }

    public boolean evaluateTransactionSumCompare(UUID userId, ConditionElementsRules conditionElementsRules) {
        List<String> productTape = conditionElementsRules.getArguments();
        String operator = productTape.get(1);
        ProductTypeConstants productTypeConstantOneArgument = ProductTypeConstants.valueOf(productTape.get(0));
        ProductTypeConstants productTypeConstantsTwoArgument = ProductTypeConstants.valueOf("DEPOSIT");

        // boolean resultOneProductTypeConstants = recommendationsRepository.checkTransactionProductUser(userId, productTypeConstants);
        int sumProductTypeConstants = recommendationsRepository.getTransactionDepositSum(userId, productTypeConstantOneArgument);
        // boolean resultTwoProductTypeConstants = recommendationsRepository.checkTransactionProductUser(userId, productTypeConstants);
        int sumProductTypeConstantsTwoArguments = recommendationsRepository.getTransactionDepositSum(userId, productTypeConstantsTwoArgument);
        boolean result = compareSum(sumProductTypeConstants, sumProductTypeConstantsTwoArguments, operator);
        return conditionElementsRules.isNegate() ? !result : result;

    }

    public boolean evaluateTransactionSumCompareDepositWithdraw(UUID userId, ConditionElementsRules conditionElementsRules) {
        List<String> productTape = conditionElementsRules.getArguments();
        String productType = productTape.get(0);
        String operator = productTape.get(1);
        ProductTypeConstants productTypeConstant = ProductTypeConstants.valueOf(productType);

        int sumDeposit = recommendationsRepository.getTransactionDepositSum(userId, productTypeConstant);
        int sumWithdraw = recommendationsRepository.getTransactionWithdrawSum(userId, productTypeConstant);

        boolean result = compareSum(sumWithdraw, sumDeposit, operator);



        return conditionElementsRules.isNegate() ? !result : result;
    }

    private boolean compareSum(int sum1, int sum2, String operator) {
        switch (operator) {
            case ">":
                return sum1 > sum2;
            case "<":
                return sum1 < sum2;
            case "=":
                return sum1 == sum2;
            case ">=":
                return sum1 >= sum2;
            case "<=":
                return sum1 <= sum2;

            default:
                throw new IllegalArgumentException("Invalid operator" + operator);
        }


    }
}