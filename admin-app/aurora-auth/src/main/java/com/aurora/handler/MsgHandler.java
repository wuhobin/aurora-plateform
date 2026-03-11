package com.aurora.handler;

import com.aurora.builder.TextBuilder;
import com.aurora.service.AuthService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Autowired
    private AuthService authService;

    private final Pattern pattern = Pattern.compile("(?i)^AU\\d{4}$");

    private final String DEFAULT_MESSAGE = "您好！ 您的消息已收到，小编看到了会回复的奥~ ";

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        String content = wxMessage.getContent();
        if (content.toLowerCase().contains("au")) {
            Matcher matcher = pattern.matcher(content);
            if (!matcher.matches()) {
                return new TextBuilder().build("验证码格式错误，请重新输入", wxMessage, weixinService);
            }else {
                String msg = authService.wechatLogin(wxMessage);
                return new TextBuilder().build(msg, wxMessage, weixinService);
            }
        }

        return new TextBuilder().build(DEFAULT_MESSAGE, wxMessage, weixinService);
    }

}
