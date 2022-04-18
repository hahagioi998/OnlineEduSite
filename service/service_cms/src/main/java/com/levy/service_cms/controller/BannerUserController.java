package com.levy.service_cms.controller;


import com.levy.commonutils.R;
import com.levy.service_cms.entity.CrmBanner;
import com.levy.service_cms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器-前台展示
 * </p>
 *
 * @author testjava
 * @since 2022-03-19
 */
@RestController
@RequestMapping("/educms/banner-user")
//@CrossOrigin
public class BannerUserController {

    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("/getAllBanner")
    @Cacheable(value = "banner",key = "'bannerList'")//注意key的双层引号
    public R getAll(){
        List<CrmBanner> bannerList = crmBannerService.selectAllBanner();
        return R.ok().data("bannerList",bannerList);
    }
}

