package com.levy.service_ucenter.controller;


import com.levy.commonutils.JwtUtils;
import com.levy.commonutils.R;
import com.levy.commonutils.orderVo.UcenterMemberVo;
import com.levy.service_ucenter.entity.UcenterMember;
import com.levy.service_ucenter.entity.vo.LoginVo;
import com.levy.service_ucenter.entity.vo.RegisterVo;
import com.levy.service_ucenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-20
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;


    @PostMapping("/register")
    public R userRegister(@RequestBody RegisterVo member){
        memberService.register(member);
        return R.ok();
    }

    @PostMapping("/login")
    public R userLogin(@RequestBody LoginVo member){
        String token = memberService.login(member);
        return R.ok().data("token", token);
    }

    @GetMapping("/makeSureLogin")
    public R makeSureLogin(HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(id);
        return R.ok().data("member", member);
    }

    @GetMapping("/getMemberById/{id}")
    public UcenterMemberVo getMemberById(@PathVariable String id){
        UcenterMember member = memberService.getById(id);
        UcenterMemberVo orderMember = new UcenterMemberVo();
        BeanUtils.copyProperties(member, orderMember);
        return orderMember;
    }

    @GetMapping("getRegiesterDaily/{day}")
    public R getRegisterDaily(@PathVariable String day){
        Integer num = memberService.getRegister(day);
        return R.ok().data("num", num);
    }

}

