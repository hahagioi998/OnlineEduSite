package com.levy.service_ucenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.levy.commonutils.JwtUtils;
import com.levy.service_ucenter.entity.UcenterMember;
import com.levy.service_ucenter.service.UcenterMemberService;
import com.levy.service_ucenter.utils.ConstantPropertiesUtils;
import com.levy.service_ucenter.utils.HttpClientUtils;
import com.levy.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @description：
 * @author：LevyXie
 * @create：2022-03-21 14:04
 */
@Controller
//@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    @GetMapping("/login")
    public String getWxCode(){
        String appid = ConstantPropertiesUtils.APP_ID;
        //需要urlEncode进行编码
        String redirect_uri = null;
        try {
            redirect_uri = URLEncoder.encode(ConstantPropertiesUtils.REDIRECT_URL, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String state = "atguigu";

        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect?" +
                "appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String url = String.format(baseUrl,appid,redirect_uri,state);
        return "redirect:" + url;
    }

    @GetMapping("/callback")//实际工作中不需要这一步，直接将代码部署至服务器即可
    public String callback(String code,String state){
        //定义需要返回的token
        String token = "";

        String appId = ConstantPropertiesUtils.APP_ID;
        String appSecret = ConstantPropertiesUtils.APP_SECRET;
        String baseUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        String url = String.format(baseUrl, appId,appSecret,code);
        //用code去请求微信平台的access_token接口，拿到用户名和头像等
        try {
            String result = HttpClientUtils.get(url);
            //返回值为access_token,open_id等
            //类型转换为Map类型
            Gson gson = new Gson();
            HashMap map = gson.fromJson(result, HashMap.class);
            String accessToken = (String) map.get("access_token");
            String openId = (String) map.get("openid");


            //首先判断数据库中
            QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
            wrapper.eq("openid", openId);
            UcenterMember existWxMember = memberService.getOne(wrapper);
            if(null == existWxMember){
                //用获取的access_token和openid访问微信的接口
                String finalBaseUrl = "https://api.weixin.qq.com/sns/userinfo?" +
                        "access_token=%s" +
                        "&openid=%s";
                String finalUrl = String.format(finalBaseUrl, accessToken,openId);
                String userInfo = HttpClientUtils.get(finalUrl);
                System.out.println(userInfo);
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);

                String nickname = (String) userInfoMap.get("nickname");
                String avatar = (String) userInfoMap.get("headimgurl");

                UcenterMember member = new UcenterMember();
                member.setOpenid(openId);
                member.setNickname(nickname);
                member.setAvatar(avatar);
                System.out.println(member);
                memberService.save(member);
                //使用JwtUtils生成token
                token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            } else{
                token = JwtUtils.getJwtToken(existWxMember.getId(), existWxMember.getNickname());
            }

        } catch (Exception e) {
            throw new GuliException(20001, "登录失败");
        }
        //直接带着token至返回项目首页
        return "redirect:http://localhost:3000?token=" + token;
    }
}
