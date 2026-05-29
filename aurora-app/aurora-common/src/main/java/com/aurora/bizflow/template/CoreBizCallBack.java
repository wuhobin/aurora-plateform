package com.aurora.bizflow.template;

import com.aurora.bizflow.context.CoreDomainContext;
import com.aurora.bizflow.define.CoreBizFlowEnum;

/**
 * @author wuhognbin
 * @date 2026年03月01日 13:46
 */
public interface CoreBizCallBack {


    /**
     * 决策处理流
     * @param context 领域模型
     * @return 业务流枚举
     */
    CoreBizFlowEnum determineBizFlow(CoreDomainContext context);
}
