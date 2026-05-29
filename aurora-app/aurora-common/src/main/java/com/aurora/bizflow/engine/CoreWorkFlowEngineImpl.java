package com.aurora.bizflow.engine;

import com.aurora.bizflow.config.BizFlowConfigContainer;
import com.aurora.bizflow.context.CoreDomainContext;
import com.aurora.bizflow.define.Activity;
import com.aurora.bizflow.define.ActivityRuleMeta;
import com.aurora.bizflow.define.CoreBizFlowEnum;
import com.aurora.bizflow.define.CoreFlowContextHolder;
import com.aurora.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wuhognbin
 * @date 2026年03月01日 13:36
 */
@Service
public class CoreWorkFlowEngineImpl implements CoreWorkFlowEngine{

    protected static final Logger logger = LoggerFactory.getLogger("WORKFLOW-ENGINE-LOGGER");

    @Autowired
    private BizFlowConfigContainer bizFlowConfigContainer;

    @Override
    public void start(CoreBizFlowEnum coreBizFlowEnum, CoreDomainContext context) {
        try {
            ActivityRuleMeta activityRuleMeta = new ActivityRuleMeta(coreBizFlowEnum);

            //1. 获取基线处理逻辑流程
            List<Activity> activities = bizFlowConfigContainer.getBizBaseActivities(activityRuleMeta);

            //2. 执行基线处理逻辑
            run(activities, activityRuleMeta, context);

        }finally {
            CoreFlowContextHolder.remove();
        }
    }

    private void run(List<Activity> activities, ActivityRuleMeta activityRuleMeta, CoreDomainContext context) {
        if (CollectionUtils.isEmpty(activities)){
            throw new BusinessException(500, "bizFlow not match");
        }

        for (Activity activity : activities) {
            long start = System.currentTimeMillis();

            // 更新当前activity节点
            activityRuleMeta.buildCurrentActivity(activity);
            // 设置当前流程到上下文
            CoreFlowContextHolder.set(activityRuleMeta);

            try {
                activity.execute(context);
            }finally {
                long end = System.currentTimeMillis();
                String activityCost = String.format("currentFlow=[%s], currentActivity=[%s], executeTime=[%s]ms",
                        activityRuleMeta.getBizFlowEnum().getCode(), activity.getClass().getSimpleName(), end - start);
                logger.info(activityCost);
            }
        }

    }
}
