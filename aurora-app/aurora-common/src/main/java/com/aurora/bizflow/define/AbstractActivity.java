package com.aurora.bizflow.define;

import com.aurora.bizflow.context.CoreDomainContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wuhognbin
 * @date 2026年03月01日 12:56
 */
public abstract class AbstractActivity implements  Activity{

    protected static final Logger logger = LoggerFactory.getLogger("WORKFLOW-ENGINE-LOGGER");

    /**
     * 返回当前活动名称
     * @return
     */
    public String getActivityName(){
        return this.getClass().getSimpleName();
    }

    /**
     * 标准的流程节点默认执行
     * @param context 上下文
     * @return
     */
    public boolean needProcess(CoreDomainContext context){
        return true;
    }

    /**
     * 执行activity的具体实现
     * @param context
     */
    public abstract void process(CoreDomainContext context);

    @Override
    public void execute(CoreDomainContext context){

        if (needProcess(context)){
            // 责任链命中则执行
            context.appendExecutedBizFlow(getActivityName());
            process(context);
        }else {
            CoreBizFlowEnum bizFlowEnum = CoreFlowContextHolder.get().getBizFlowEnum();
            String skipLog = String.format("[skip execute activity], currentFlow=[%s], currentActivity=[%s] not processed", bizFlowEnum, getActivityName());
            logger.info(skipLog);
        }
    }

}
