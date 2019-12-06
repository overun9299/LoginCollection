package overun.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import overun.domain.User;
import overun.service.WxCallbackService;

/**
 * @ClassName: WxCallbackController
 * @Description:
 * @author: 壹米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/12/6 10:58
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */

@Controller
public class WxCallbackController {

    @Autowired
    private WxCallbackService wxCallbackService;

    /**
     * 微信扫码登陆后微信回调
     * @return
     */
    @RequestMapping(value = "/WxCallback")
    public String WxCallback(String code , Model model) {
        if (StringUtils.isNotBlank(code)) {
            String user = wxCallbackService.WxCallback(code);
            if (StringUtils.isNotBlank(user)) {
                /** json转换 */
                User userBean = JSON.parseObject(user, User.class);
                if (userBean != null) {
                    model.addAttribute("result","登陆成功！");
                    model.addAttribute("name","昵称："+userBean.getNickname());
                    model.addAttribute("headImgUrl",userBean.getHeadimgurl());
                    model.addAttribute("province","省份："+userBean.getProvince());
                    String sex = "1".equals(userBean.getSex()) ? "男":"女";
                    model.addAttribute("sex","性别："+ sex);
                } else {
                    model.addAttribute("result","登陆失败！");
                }
            } else {
                model.addAttribute("result","登陆失败！");
            }

        } else {
            model.addAttribute("result","登陆失败！");
        }
        return "result";
    }
}
