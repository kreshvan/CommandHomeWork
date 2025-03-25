package SkyPro.DmitrievIvanCommandHomeWork.controller;

import SkyPro.DmitrievIvanCommandHomeWork.dynamicRuleModel.DynamicRules;
import SkyPro.DmitrievIvanCommandHomeWork.dynamicService.DynamicRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {
    private final DynamicRuleService dynamicRuleService;

    public DynamicRuleController(DynamicRuleService dynamicRuleService) {
        this.dynamicRuleService = dynamicRuleService;
    }

    @PostMapping("/create")

    public DynamicRules createDynamicRule(@RequestBody DynamicRules dynamicRules) {
        return dynamicRuleService.createDynamicRule(dynamicRules);
    }

    @DeleteMapping("/delete/{id}")
    public DynamicRules deleteDynamicRule(@RequestParam long id) {
        return dynamicRuleService.deleteDynamicRule(id);
    }

    @GetMapping("/find_all")
    public List<DynamicRules> findAll() {

        return dynamicRuleService.findAll();
    }


}
