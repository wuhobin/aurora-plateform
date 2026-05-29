package com.aurora.bizflow.define;

/**
 * @author wuhognbin
 * @date 2026年03月01日 12:33
 */
public enum CoreContextKeyEnum {

    Todo_WAIT_FILL("Todo_WAIT_FILL", "等后续代码补充"),

    EXECUTED_BIZ_FLOW_LIST("EXECUTED_BIZ_FLOW_LIST", "已执行业务流程列表"),
    ;

    private final String code;

    private final String description;


    private CoreContextKeyEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CoreContextKeyEnum getByCode(String code) {
        for (CoreContextKeyEnum value : CoreContextKeyEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
