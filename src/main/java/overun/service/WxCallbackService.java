package overun.service;

/**
 * @ClassName: WxCallbackService
 * @Description:
 * @author: 壹米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/12/6 11:32
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */
public interface WxCallbackService {

    /**
     * 微信回调方法，根据code请求微信资源服务器获取用户昵称头像等
     * @param code
     * @return
     */
    String WxCallback(String code);
}
