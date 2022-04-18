package com.levy.service_cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.levy.commonutils.R;
import com.levy.service_cms.entity.CrmBanner;
import com.levy.service_cms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器-后台管理员
 * </p>
 *
 * @author testjava
 * @since 2022-03-19
 */
@RestController
@RequestMapping("/cms/banner-admin")
//@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation("分页查询")
    @GetMapping("/pageBanner/{page}/{limit}")
    public R getBannerByPage(@PathVariable long page,
                             @PathVariable long limit){
        Page<CrmBanner> pageInfo = new Page<>(page,limit);
        crmBannerService.page(pageInfo, null);
        return R.ok().data("items", pageInfo).data("total", pageInfo.getTotal());
    }

    @ApiOperation("ID查询")
    @GetMapping("/getBanner/{id}")
    public R getBanner(@PathVariable long id){
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("banner", banner);
    }

    @ApiOperation("增")
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner banner){
        crmBannerService.save(banner);
        return R.ok();
    }

    @ApiOperation("改")
    @PostMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner banner){
        crmBannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation("删")
    @DeleteMapping("/deleteBanner/{id}")
    public R deleteBanner(@PathVariable long id){
        crmBannerService.removeById(id);
        return R.ok();
    }

}

