package com.aurora.bizflow.template;

import cn.hutool.json.JSONUtil;
import com.aurora.bizflow.context.CoreDomainContext;
import com.aurora.bizflow.define.CoreBizFlowEnum;
import com.aurora.bizflow.define.CoreDomainContextHolder;
import com.aurora.bizflow.engine.CoreWorkFlowEngine;
import com.aurora.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuhognbin
 * @date 2026年03月01日 13:47
 */
@Service
public class CoreBizTemplate {

    @Autowired
    private CoreWorkFlowEngine coreWorkFlowEngine;

    public void execute(CoreBizCallBack coreBizCallBack){
        CoreDomainContext coreDomainContext = CoreDomainContextHolder.get();

        try {
            CoreBizFlowEnum coreBizFlowEnum = coreBizCallBack.determineBizFlow(coreDomainContext);
            if (coreBizFlowEnum ==  null){
                throw new BusinessException(400, "bizFlow must be exist! context: " + JSONUtil.toJsonStr(coreDomainContext));
            }
            coreWorkFlowEngine.start(coreBizFlowEnum, coreDomainContext);
        }catch (Exception e){
            throw new BusinessException(500, "bizFlow execute error! context: " + JSONUtil.toJsonStr(coreDomainContext), e);
        }
    }
}
