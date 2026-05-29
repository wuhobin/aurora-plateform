package com.aurora.controller.bizflow;

import com.aurora.bizflow.context.CoreDomainContext;
import com.aurora.bizflow.define.AbstractActivity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wuhognbin
 * @date 2026年03月01日 13:56
 */
@Service
@Slf4j
public class Activity2 extends AbstractActivity {
    @Override
    public void process(CoreDomainContext context) {
        log.info("activity2 process");
    }
}
