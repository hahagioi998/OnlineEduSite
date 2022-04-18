package com.levy.service_ucenter.service;

import com.levy.service_ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levy.service_ucenter.entity.vo.LoginVo;
import com.levy.service_ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-20
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo member);

    void register(RegisterVo member);

    Integer getRegister(String day);
}
