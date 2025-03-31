package SkyPro.DmitrievIvanCommandHomeWork.dynamicService;

import SkyPro.DmitrievIvanCommandHomeWork.dynamicRuleModel.RuleStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RuleStatsServiceTest {
    private RuleStatsService ruleStatsService;

    @BeforeEach
    public void setUp() {
        ruleStatsService = new RuleStatsService();
    }

    @Test
    void increaseRuleStats() {
        Long ruleId = 1L;
        ruleStatsService.increaseRuleStats(ruleId);
        List<RuleStats> ruleStats = ruleStatsService.getAllStatsRule();

        assertNotNull(ruleStats);
        assertEquals(1, ruleStats.size());
        assertEquals(1L, ruleStats.get(0).getRuleId());


    }

    @Test
    void getAllStatsRule() {
        Long ruleId = 1L;
        ruleStatsService.increaseRuleStats(ruleId);
        List<RuleStats> ruleStats = ruleStatsService.getAllStatsRule();
        assertNotNull(ruleStats);
        assertEquals(1, ruleStats.size());
        assertEquals(1L, ruleStats.get(0).getRuleId());


    }

    @Test
    void deleteStatsRule() {
        Long ruleId = 1L;
        ruleStatsService.increaseRuleStats(ruleId);
        ruleStatsService.deleteStatsRule(ruleId);
        int ruleStats = ruleStatsService.getAllStatsRule().size();
        assertEquals(0,ruleStats);
    }
}