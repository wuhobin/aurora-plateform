package com.aurora.bizflow.define;

import com.aurora.bizflow.config.BaseFlowActivityContainer;
import com.aurora.bizflow.config.SpringBeanReferenceClient;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuhognbin
 * @date 2026年03月01日 13:14
 */
public abstract class AbstractBizFlow implements SmartInitializingSingleton {

    @Autowired
    private SpringBeanReferenceClient springBeanReferenceClient;

    private final List<Activity> activityList = new ArrayList<>();

    @Override
    public void afterSingletonsInstantiated() {
        BaseFlowActivityContainer.register(flowType(), getActivityList());
    }

    /**
     * 获取流程类型
     * @return
     */
    public abstract CoreBizFlowEnum flowType();

    /**
     * 收集流程节点
     */
    public abstract void gatherActivities();

    public List<Activity> getActivityList() {
        if (activityList.isEmpty()) {
            synchronized (this){
                if (activityList.isEmpty()) {
                    gatherActivities();
                }
            }
        }
        return activityList;
    }

    protected void appendBizFlow(Class<? extends Activity> activityClass){
        this.activityList.add(springBeanReferenceClient.getBean(activityClass));
    }

}
