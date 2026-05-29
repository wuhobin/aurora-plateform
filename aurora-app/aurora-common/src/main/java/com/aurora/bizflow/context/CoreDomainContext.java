package com.aurora.bizflow.context;

import com.aurora.bizflow.define.CoreContextKeyEnum;
import com.aurora.common.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * 引擎执行上下文
 * @author wuhognbin
 * @date 2026年03月01日 12:27
 */
public class CoreDomainContext {


    private Result result;

    private CoreRuntimeDomain runtimeDomain = new CoreRuntimeDomain();

    private final Map<CoreContextKeyEnum, Object> data = new HashMap<>();

    /**
     * @param key
     * @return T
     */
    @SuppressWarnings("unchecked")
    public <T> T get(CoreContextKeyEnum key) {
        return (T) data.get(key);
    }


    /**
     * @param key
     * @param value
     * @return T
     */
    @SuppressWarnings("unchecked")
    public <T> T  put(CoreContextKeyEnum key, T value) {
       return (T) data.put(key, value);
    }

    /**
     * 拼接已经执行的bizFlow
     * @param bizFlowName
     */
    public void appendExecutedBizFlow(String bizFlowName) {
        String executedBizFlowList  = this.get(CoreContextKeyEnum.EXECUTED_BIZ_FLOW_LIST);
        this.put(CoreContextKeyEnum.EXECUTED_BIZ_FLOW_LIST, appendExecutedBizFlow(executedBizFlowList, bizFlowName));
    }

    /**
     * 拼接已经执行的bizflow
     * @param executedBizFlow 原始执行信息
     * @param executeBizFlow  需要执行的bizFlow
     * @return  已经执行的bizFlowList
     */
    public static String appendExecutedBizFlow(String executedBizFlow, String executeBizFlow) {
        if (executedBizFlow == null) {
            executedBizFlow = "BEGIN";
        }
        return executedBizFlow + "-" + executeBizFlow;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public CoreRuntimeDomain getRuntimeDomain() {
        return runtimeDomain;
    }

    public void setRuntimeDomain(CoreRuntimeDomain runtimeDomain) {
        this.runtimeDomain = runtimeDomain;
    }
}
