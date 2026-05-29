package com.aurora.bizflow.define;

/**
 * 流程枚举
 * @author wuhongbin
 */
public enum CoreBizFlowEnum {
    /**
     * 测试流程
     */
    TEST_BIZ_FLOW("TEST_BIZ_FLOW", "测试流程"),

    ;
    private final String code;

    private final String description;

    private CoreBizFlowEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CoreBizFlowEnum getByCode(String code) {
        for (CoreBizFlowEnum value : CoreBizFlowEnum.values()) {
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
