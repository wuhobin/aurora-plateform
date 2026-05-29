package com.aurora.bizflow.config;

import com.aurora.bizflow.define.Activity;
import com.aurora.bizflow.define.ActivityRuleMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuhognbin
 * @date 2026年03月01日 13:32
 */
@Service
public class BizFlowConfigContainer {

    @Autowired
    private BaseFlowActivityContainer baseFlowActivityContainer;

    public List<Activity> getBizBaseActivities(ActivityRuleMeta activityRuleMeta){
        return baseFlowActivityContainer.decideFlowActivities(activityRuleMeta);
    }
}
