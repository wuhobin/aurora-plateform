package com.aurora.controller.bizflow;

import com.aurora.bizflow.context.CoreDomainContext;
import com.aurora.bizflow.define.CoreBizFlowEnum;
import com.aurora.bizflow.define.CoreDomainContextHolder;
import com.aurora.bizflow.template.CoreBizCallBack;
import com.aurora.bizflow.template.CoreBizTemplate;
import com.aurora.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuhognbin
 * @date 2026年03月01日 13:58
 */
@RestController
@RequestMapping("/bizFlow")
public class BizFlowController {

    @Autowired
    private CoreBizTemplate coreBizTemplate;

    @GetMapping("/test")
    public Result test(){
        CoreDomainContext coreDomainContext = new CoreDomainContext();
        CoreDomainContextHolder.set(coreDomainContext);
        coreBizTemplate.execute(new CoreBizCallBack() {
            @Override
            public CoreBizFlowEnum determineBizFlow(CoreDomainContext context) {
                return CoreBizFlowEnum.TEST_BIZ_FLOW;
            }
        });
        return Result.success();
    }

}
