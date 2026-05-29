package com.aurora.bizflow.config;

import com.aurora.bizflow.define.ActivityRuleMeta;
import com.aurora.bizflow.define.Activity;
import com.aurora.bizflow.define.CoreBizFlowEnum;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuhognbin
 * @date 2026年03月01日 13:16
 */
@Service
public class BaseFlowActivityContainer implements FlowActivityContainer{


    private static final Map<CoreBizFlowEnum, List<Activity>> FLOW_ACTIVITY_MAP = new HashMap<>();

    @Override
    public List<Activity> decideFlowActivities(ActivityRuleMeta activityRuleMeta) {
        return FLOW_ACTIVITY_MAP.get(activityRuleMeta.getBizFlowEnum());
    }

    public static void register(CoreBizFlowEnum bizFlowEnum, List<Activity> activityList) {
        FLOW_ACTIVITY_MAP.put(bizFlowEnum, activityList);
    }
}
