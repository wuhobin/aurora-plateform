package com.aurora.bizflow.engine;

import com.aurora.bizflow.context.CoreDomainContext;
import com.aurora.bizflow.define.CoreBizFlowEnum;

/**
 * @author wuhognbin
 * @date 2026年03月01日 13:35
 */
public interface CoreWorkFlowEngine {

    /**
     * 启动流程引擎
     * @param coreBizFlowEnum 流程名称
     * @param context 上下文
     */
    void start(CoreBizFlowEnum coreBizFlowEnum, CoreDomainContext context);

}
