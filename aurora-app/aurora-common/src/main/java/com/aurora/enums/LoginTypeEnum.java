package com.aurora.enums;

/**
 * @author: quequnlong
 * @date: 2024/12/29
 * @description:
 */
public enum LoginTypeEnum {

    /**
     * 邮箱登录
     */
    EMAIL(1, "email"),
    /**
     * QQ登录
     */
    QQ(2, "qq"),
    /**
     * 微博登录
     */
    WEIBO(3, "weibo"),

    /**
     * 码云登录
     */
    GITEE(4, "gitee"),

    /**
     * 微信登录
     */
    WECHAT(5, "微信登录"),

    /**
     * github登录
     */
    GITHUB(6, "github");



    private final Integer type;


    private final String desc;

    LoginTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }


    public static Integer getType(String desc) {
        for (LoginTypeEnum value : LoginTypeEnum.values()) {
            if (value.getDesc().equals(desc)) {
                return value.getType();
            }
        }
        return null;
    }
}
