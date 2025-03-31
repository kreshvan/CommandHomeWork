package SkyPro.DmitrievIvanCommandHomeWork.dynamicService;

import SkyPro.DmitrievIvanCommandHomeWork.dynamicRuleModel.RuleStats;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RuleStatsService {
    private Map<Long, RuleStats> ruleStatsMap = new HashMap<>();


    public void increaseRuleStats(Long ruleId) {
        RuleStats stats = ruleStatsMap.get(ruleId);
        if (stats == null) {
            stats = new RuleStats(ruleId, 0);
            ruleStatsMap.put(ruleId, stats);
        }
        stats.increaseCount();
    }

    public List<RuleStats> getAllStatsRule() {

        return new ArrayList<>(ruleStatsMap.values());
    }

    public void deleteStatsRule(Long ruleId) {
        ruleStatsMap.remove(ruleId);

    }
}
