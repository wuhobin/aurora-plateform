package com.aurora.bizflow.define;

/**
 * @author wuhognbin
 * @date 2026年03月01日 13:03
 */
public class CoreFlowContextHolder {

    private static final ThreadLocal<ActivityRuleMeta> EXPCORE_FLOW_CTX_LOCAL = new ThreadLocal<>();

    public static ActivityRuleMeta get(){
        return EXPCORE_FLOW_CTX_LOCAL.get();
    }

    public static void set(ActivityRuleMeta activityRuleMeta){
        EXPCORE_FLOW_CTX_LOCAL.set(activityRuleMeta);
    }

    public static void remove(){
        EXPCORE_FLOW_CTX_LOCAL.remove();
    }
}
