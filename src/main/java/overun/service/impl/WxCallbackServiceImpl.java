package overun.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import overun.domain.Token;
import overun.domain.User;
import overun.service.WxCallbackService;
import overun.utils.HttpclientTools;

/**
 * @ClassName: WxCallbackServiceImpl
 * @Description:
 * @author: 壹米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/12/6 11:33
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */
@Service
public class WxCallbackServiceImpl implements WxCallbackService {

    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.secret}")
    private String secret;
    @Value("${wx.token_url}")
    private String tokenUrl;
    @Value("${wx.user_url}")
    private String userUrl;

    @Override
    public String WxCallback(String code) {

        /** 定义获取微信token地址 */
        String tokenUrls = String.format(tokenUrl,appid,secret,code);
        /** 发送请求 */
        String tokenResult = HttpclientTools.doGet(tokenUrls);
        /** json转换 */
        Token token = JSON.parseObject(tokenResult, Token.class);
        if (token != null && StringUtils.isNotBlank(token.getAccess_token()) && StringUtils.isNotBlank(token.getOpenid())) {
            /** 定义根据用户openid获取用户昵称头像等信息 */
            String userUrls = String.format(userUrl,token.getAccess_token(),token.getOpenid());
            /** 发送请求 */
            String userResult = HttpclientTools.doGet(userUrls);
            /** json转换 */
            User user = JSON.parseObject(userResult, User.class);

            if (user != null) {
                /** 此处user不为空标识用户授权成功，对应的也是登陆成功，后面处理自己的业务逻辑如果数据库有openId拉取该用户对应的资源权限
                 *  如果没有标识新用户，则走注册流程，具体细节具体场景 */
                return JSONObject.toJSONString(user);
            }
        }
        return "";
    }
}
