package SkyPro.DmitrievIvanCommandHomeWork.dynamicService;

import SkyPro.DmitrievIvanCommandHomeWork.dynamicRuleModel.ConditionElementsRules;
import SkyPro.DmitrievIvanCommandHomeWork.dynamicRuleModel.DynamicRules;
import SkyPro.DmitrievIvanCommandHomeWork.dynamicRuleModel.RuleStats;
import SkyPro.DmitrievIvanCommandHomeWork.modelAndConstants.ProductTypeConstants;
import SkyPro.DmitrievIvanCommandHomeWork.modelAndConstants.Recommendation;
import SkyPro.DmitrievIvanCommandHomeWork.repository.DynamicRulesRepository;
import SkyPro.DmitrievIvanCommandHomeWork.repository.RecommendationsRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Array;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@ExtendWith(MockitoExtension.class)
class DynamicRuleServiceTest {
    @Mock
    private DynamicRulesRepository dynamicRulesRepository;

    @Mock
    private RecommendationsRepository recommendationsRepository;

    private Recommendation recommendation;
    private DynamicRules dynamicRules;


    @InjectMocks
    private DynamicRuleService dynamicRuleService;

    @BeforeEach
    void setUpMockito() {
        MockitoAnnotations.openMocks(this);
        dynamicRuleService = new DynamicRuleService(dynamicRulesRepository, recommendationsRepository);
    }

    @Test
    void testCreateDynamicRule() {
        DynamicRules dynamicRules = new DynamicRules();
        dynamicRules.setProductName("R");
        dynamicRules.setProductText("R");
        dynamicRules.setProductId(UUID.randomUUID());
        dynamicRules.setConditions(null);

        when(dynamicRulesRepository.save(any(DynamicRules.class))).thenReturn(dynamicRules);

        DynamicRules result = dynamicRuleService.createDynamicRule(dynamicRules);
        assertNotNull(result);
        assertEquals(dynamicRules.getProductName(), result.getProductName());
        assertEquals(dynamicRules.getProductId(), result.getProductId());
    }

    @Test
    void deleteDynamicRuleTest() {
        DynamicRules dynamicRule = new DynamicRules();
        dynamicRule.setId(1L);
        dynamicRule.setProductName("R");
        dynamicRule.setProductText("R");
        dynamicRule.setProductId(UUID.randomUUID());
        dynamicRule.setConditions(null);

        when(dynamicRulesRepository.save(any(DynamicRules.class))).thenReturn(dynamicRule);
        DynamicRules createDynamicRule = dynamicRuleService.createDynamicRule(dynamicRule);

        when(dynamicRulesRepository.findById(createDynamicRule.getId())).thenReturn(Optional.of(dynamicRule));


        DynamicRules deleteDynamicRule = dynamicRuleService.deleteDynamicRule(1L);

        // verify(dynamicRulesRepository).deleteById(createDynamicRule.getId());
        assertNotNull(deleteDynamicRule);
        assertEquals(1L, deleteDynamicRule.getId().floatValue());
        assertEquals("R", deleteDynamicRule.getProductName());

    }

    @Test
    void findAllTest() {
        DynamicRules dynamicRule1 = new DynamicRules();
        dynamicRule1.setProductName("R");
        dynamicRule1.setProductText("R");
        dynamicRule1.setProductId(UUID.randomUUID());
        dynamicRule1.setConditions(null);

        DynamicRules dynamicRule2 = new DynamicRules();
        dynamicRule2.setProductName("t");
        dynamicRule2.setProductText("t");
        dynamicRule2.setProductId(UUID.randomUUID());
        dynamicRule2.setConditions(null);


        when(dynamicRulesRepository.save(any(DynamicRules.class))).thenReturn(dynamicRule1, dynamicRule2);
        DynamicRules createDynamicRule1 = dynamicRuleService.createDynamicRule(dynamicRule1);
        DynamicRules createDynamicRule2 = dynamicRuleService.createDynamicRule(dynamicRule2);
        List<DynamicRules> listDynamicRules = Arrays.asList(dynamicRule1, dynamicRule2);
        when(dynamicRulesRepository.findAll()).thenReturn(listDynamicRules);
        List<DynamicRules> findDynamicRules = dynamicRuleService.findAll();


        assertNotNull(findDynamicRules);
        assertEquals(findDynamicRules, listDynamicRules);


    }

    @Test
    void getRecommendationsByUserId() {

        DynamicRules dynamicRules1 = mock(DynamicRules.class);
        DynamicRules dynamicRules2 = mock(DynamicRules.class);

        when(dynamicRulesRepository.findAll()).thenReturn(Arrays.asList(dynamicRules1, dynamicRules2));

        List<Recommendation> getRecommendationOne = dynamicRuleService.getRecommendationsByUserId(dynamicRules1.getProductId());
        List<Recommendation> getRecommendationTwo = dynamicRuleService.getRecommendationsByUserId(dynamicRules2.getProductId());
        List<Recommendation> recommendations = new ArrayList<>();
        recommendations.addAll(getRecommendationOne);
        recommendations.addAll(getRecommendationTwo);
        assertNotNull(recommendations);
        assertEquals(4, recommendations.size());

    }


    @Test
    void checkDynamicRulesSuitable() {
        UUID id = UUID.fromString("d884bc3e-01d7-4023-8d4e-dfab92a8404e");
                //UUID.randomUUID();

        List<ConditionElementsRules> condition = getConditionElementsRules();

        DynamicRules dynamicRule = new DynamicRules();
        dynamicRule.setId(2L);
        dynamicRule.setProductName("2");
        dynamicRule.setProductText("2");
        dynamicRule.setProductId(UUID.randomUUID());
        dynamicRule.setConditions(condition);

        doReturn(true)
                .when(recommendationsRepository)
                .checkTransactionProductUser(any(UUID.class), any(ProductTypeConstants.class));

        boolean result = dynamicRuleService.processQuery(id, condition.get(0));

        assertTrue(result);

    }

    @NotNull
    private static List<ConditionElementsRules> getConditionElementsRules() {
        ConditionElementsRules conditionElementsRule = new ConditionElementsRules();
        List<String> list = new ArrayList<>();
        list.add("DEBIT");
        list.add(">=");
        conditionElementsRule.setArguments(list);
        conditionElementsRule.setQuery("USER_OF");
        conditionElementsRule.setNegate(true);

        ConditionElementsRules conditionElementsRule1 = new ConditionElementsRules();
        List<String> list1 = new ArrayList<>();
        list.add("T");
        conditionElementsRule1.setArguments(list1);
        conditionElementsRule1.setQuery("T");
        conditionElementsRule1.setNegate(true);

        List<ConditionElementsRules> condition = new ArrayList<>();
        condition.add(conditionElementsRule);
        condition.add(conditionElementsRule1);
        return condition;
    }

    @Test
    void processQuery() {
        UUID id = UUID.randomUUID();
        ConditionElementsRules conditionElementsRules = mock(ConditionElementsRules.class);
        String user = "user";

        when(conditionElementsRules.getQuery()).thenReturn("USER_OF");
        when(dynamicRuleService.evaluateUserOf(id, conditionElementsRules)).thenReturn(true);

        //мне нужно добавить в conditionElementsRules query чтобы потом вытащить
        boolean result = dynamicRuleService.processQuery(id, conditionElementsRules);
        assertTrue(result);

    }

    @Test
    void evaluateUserOf() {
    }

    @Test
    void evaluateActiveUserOf() {
    }

    @Test
    void evaluateTransactionSumCompare() {
    }

    @Test
    void evaluateTransactionSumCompareDepositWithdraw() {
    }
}