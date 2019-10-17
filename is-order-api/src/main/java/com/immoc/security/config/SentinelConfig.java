package com.immoc.security.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gyc
 * @Date: 2019/10/15 14:14
 */
@Configuration
public class SentinelConfig implements ApplicationListener<ContextRefreshedEvent> {


    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List ruleList= new ArrayList();
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("getOrder");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(2);
        ruleList.add(flowRule);
        FlowRuleManager.loadRules(ruleList);
    }



}
