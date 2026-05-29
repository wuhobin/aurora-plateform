package com.aurora.bizflow.config;

import com.aurora.bizflow.define.ActivityRuleMeta;
import com.aurora.bizflow.define.Activity;

import java.util.List;

/**
 * @author wuhognbin
 * @date 2026年03月01日 13:17
 */
public interface FlowActivityContainer {

    /**
     * 决策能力
     * @param activityRuleMeta
     * @return
     */
    List<Activity> decideFlowActivities(ActivityRuleMeta activityRuleMeta);
}
