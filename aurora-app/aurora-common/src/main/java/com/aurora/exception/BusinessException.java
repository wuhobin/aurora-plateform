package com.aurora.exception;

import com.aurora.common.ResultCode;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    /** 异常代码 */
    protected Integer code;

    /** 异常消息 */
    protected String message;

    public BusinessException(String msg,Throwable e) {
        super(msg,e);
    }

    public BusinessException(ResultCode resultCode) {
        super(resultCode.desc);
        this.code = resultCode.code;
        this.message = resultCode.desc;
    }

    public BusinessException(String msg) {
        super(msg);
        this.code = ResultCode.ERROR_DEFAULT.code;
        this.message = msg;
    }

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.message = msg;
    }

    public BusinessException(Integer code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.message = msg;
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.code = ResultCode.ERROR.code;
        this.message = cause.getMessage();
    }

    @Override
    public String toString() {
        return "errorCode: " + code + ", message: " + message;
    }
}
