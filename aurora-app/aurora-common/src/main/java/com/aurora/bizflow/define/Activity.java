package com.aurora.bizflow.define;

import com.aurora.bizflow.context.CoreDomainContext;

/**
 * @author wuhognbin
 * @date 2026年03月01日 12:20
 */
public interface Activity {


    /**
     * 执行节点
     * @param context
     */
    void execute(CoreDomainContext context);

}
