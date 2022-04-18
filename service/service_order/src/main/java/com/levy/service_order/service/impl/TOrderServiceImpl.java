package com.levy.service_order.service.impl;

import com.levy.commonutils.orderVo.CourseOrderVo;
import com.levy.commonutils.orderVo.UcenterMemberVo;
import com.levy.service_order.entity.TOrder;
import com.levy.service_order.entity.UcenterMember;
import com.levy.service_order.mapper.TOrderMapper;
import com.levy.service_order.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levy.service_order.service.client.CourseClient;
import com.levy.service_order.service.client.UcenterClient;
import com.levy.service_order.utils.OrderNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-24
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private CourseClient courseClient;

    @Override
    public String saveOrder(String courseId, String memberId) {
        UcenterMemberVo member = ucenterClient.getMemberById(memberId);
        CourseOrderVo course = courseClient.getCourseOrder(courseId);

        TOrder order = new TOrder();
        order.setCourseId(courseId);
        order.setCourseTitle(course.getTitle());
        order.setCourseCover(course.getCover());
        order.setTeacherName(course.getTeacherName());
        order.setMemberId(memberId);
        order.setNickname(member.getNickname());
        order.setMobile(member.getMobile());
        order.setTotalFee(course.getPrice());
        order.setPayType(1);
        order.setStatus(0);

        order.setOrderNo(OrderNoUtils.getOrderNo());

        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
