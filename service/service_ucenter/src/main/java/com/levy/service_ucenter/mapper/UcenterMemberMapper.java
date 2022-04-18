package com.levy.service_ucenter.mapper;

import com.levy.service_ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-03-20
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer getRegisterNum(@Param("day") String day);
}
