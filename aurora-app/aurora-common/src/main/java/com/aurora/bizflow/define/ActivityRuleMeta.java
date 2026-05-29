package com.aurora.bizflow.define;

/**
 * @author wuhognbin
 * @date 2026年03月01日 13:04
 */
public class ActivityRuleMeta {

    private CoreBizFlowEnum bizFlowEnum;

    private Activity currentActivity;

    private Activity lastBaseActivity;

    public ActivityRuleMeta(CoreBizFlowEnum coreBizFlowEnum) {
        this.bizFlowEnum = coreBizFlowEnum;
    }


    public CoreBizFlowEnum getBizFlowEnum() {
        return bizFlowEnum;
    }

    public void setBizFlowEnum(CoreBizFlowEnum bizFlowEnum) {
        this.bizFlowEnum = bizFlowEnum;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public ActivityRuleMeta buildCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
        return this;
    }

    public Activity getLastBaseActivity() {
        return lastBaseActivity;
    }

    public ActivityRuleMeta buildLastBaseActivity(Activity lastBaseActivity) {
        this.lastBaseActivity = lastBaseActivity;
        return this;
    }
}
