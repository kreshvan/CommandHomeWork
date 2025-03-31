package SkyPro.DmitrievIvanCommandHomeWork.Test;

import SkyPro.DmitrievIvanCommandHomeWork.dynamicRuleModel.RuleStats;
import SkyPro.DmitrievIvanCommandHomeWork.dynamicService.RuleStatsService;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class RuleStatsServiceTest {


    private final RuleStatsService ruleStatsService = new RuleStatsService();

    @Test
    public void testIncreaseRuleStats() {

        Long ruleId = 1L;

        ruleStatsService.increaseRuleStats(ruleId);

        List<RuleStats> result = ruleStatsService.getAllStatsRule();

        assertEquals(ruleId, result.size());

    }

    @Test
    public void testIncreaseRuleStatsNotNull() {
        Long ruleId = 1L;

        ruleStatsService.increaseRuleStats(ruleId);

        List<RuleStats> result = ruleStatsService.getAllStatsRule();

        assertNotNull(result);

    }
    @Test
    public void testDeleteStatsRule(){
        Long ruleId = 1L;
        ruleStatsService.increaseRuleStats(ruleId);

        ruleStatsService.deleteStatsRule(ruleId);

        List<RuleStats> result = ruleStatsService.getAllStatsRule();

        assertTrue(result.isEmpty());
    }
}


