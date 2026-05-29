package com.aurora.controller.bizflow;

import com.aurora.bizflow.define.AbstractBizFlow;
import com.aurora.bizflow.define.CoreBizFlowEnum;
import org.springframework.stereotype.Service;

/**
 * @author wuhognbin
 * @date 2026年03月01日 13:57
 */
@Service
public class TestBizFlow extends AbstractBizFlow {
    @Override
    public CoreBizFlowEnum flowType() {
        return CoreBizFlowEnum.TEST_BIZ_FLOW;
    }

    @Override
    public void gatherActivities() {
        appendBizFlow(Activity1.class);
        appendBizFlow(Activity2.class);
    }
}
