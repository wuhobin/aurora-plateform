package com.aurora.bizflow.define;

import com.aurora.bizflow.context.CoreDomainContext;

/**
 * @author wuhognbin
 * @date 2026年03月01日 13:03
 */
public class CoreDomainContextHolder {

    private static final ThreadLocal<CoreDomainContext> CORE_DOMAIN_CONTEXT = new ThreadLocal<>();

    public static CoreDomainContext get(){
        return CORE_DOMAIN_CONTEXT.get();
    }

    public static void set(CoreDomainContext context){
        CORE_DOMAIN_CONTEXT.set(context);
    }

    public static void remove(){
        CORE_DOMAIN_CONTEXT.remove();
    }
}
