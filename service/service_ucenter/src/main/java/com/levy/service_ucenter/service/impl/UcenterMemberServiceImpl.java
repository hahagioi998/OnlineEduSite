package com.levy.service_ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.levy.commonutils.JwtUtils;
import com.levy.commonutils.MD5Util;
import com.levy.service_ucenter.entity.UcenterMember;
import com.levy.service_ucenter.entity.vo.LoginVo;
import com.levy.service_ucenter.entity.vo.RegisterVo;
import com.levy.service_ucenter.mapper.UcenterMemberMapper;
import com.levy.service_ucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levy.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-20
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(LoginVo member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember selectedMember = baseMapper.selectOne(wrapper);
        if(null == selectedMember){
            //数据库中没有该手机号
            throw new GuliException(20001, "手机号不存在");
        }
        if(!MD5Util.encrypt(password).equals(selectedMember.getPassword())){
            throw new GuliException(20001, "密码错误");
        }
        if(selectedMember.getIsDisabled()){
            throw new GuliException(20001, "用户已被禁用");
        }

        //返回token
        String token = JwtUtils.getJwtToken(selectedMember.getId(), selectedMember.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo member) {
        String code = member.getCode();
        String nickname = member.getNickname();
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)
                 || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001, "注册失败");
        }
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new GuliException(20001, "验证码错误");
        }
        QueryWrapper<UcenterMember> mWrapper = new QueryWrapper<>();
        mWrapper.eq("mobile", mobile);
        Long mobileCount = baseMapper.selectCount(mWrapper);

        if(mobileCount > 0){
            throw new GuliException(20001, "手机号已被注册");
        }


        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setNickname(nickname);
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5Util.encrypt(password));

        baseMapper.insert(ucenterMember);
    }

    @Override
    public Integer getRegister(String day) {
        return baseMapper.getRegisterNum(day);
    }
}
