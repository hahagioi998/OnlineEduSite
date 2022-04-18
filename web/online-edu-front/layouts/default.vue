<template>
  <div class="in-wrap">
    <!-- 公共头引入 -->
    <header id="header">
      <section class="container">
        <h1 id="logo">
          <a href="#" title="谷粒学院">
            <img src="~/assets/img/logo.png" width="100%" alt="谷粒学院">
          </a>
        </h1>
        <div class="h-r-nsl">
          <ul class="nav">
            <router-link to="/" tag="li" active-class="current" exact>
              <a>首页</a>
            </router-link>
            <router-link to="/course" tag="li" active-class="current">
              <a>课程</a>
            </router-link>
            <router-link to="/teacher" tag="li" active-class="current">
              <a>名师</a>
            </router-link>
            <router-link to="/#" tag="li" @click.native="warnInfo()">
              <a>文章</a>
            </router-link>
            <router-link to="/#" tag="li" @click.native="warnInfo()">
              <a>问答</a>
            </router-link>
          </ul>
          <!-- / nav -->
          <ul class="h-r-login">
              <li v-if="!loginUser.id" id="no-login">
                  <a href="/login" title="登录">
                      <em class="icon18 login-icon">&nbsp;</em>
                      <span class="vam ml5">登录</span>
                  </a>
                  |
                  <a href="/register" title="注册">
                      <span class="vam ml5">注册</span>
                  </a>
              </li>
              <li v-if="loginUser.id" id="is-login-one" class="mr10">
                  <a id="headerMsgCountId" href="#" title="消息">
                      <em class="icon18 news-icon">&nbsp;</em>
                  </a>
                  <q class="red-point" style="display: none">&nbsp;</q>
              </li>
              <li v-if="loginUser.id" id="is-login-two" class="h-r-user">
                  <a href="/ucenter" title>
                      <img
                          :src="loginUser.avatar"
                          width="30"
                          height="30"
                          class="vam picImg"
                          alt
                          >
                      <span id="userName" class="vam disIb">{{ loginUser.nickname }}</span>
                  </a>
                  <a href="javascript:void(0);" title="退出" @click="logout()" class="ml5">退出</a>
              </li>
              <!-- /未登录显示第1 li；登录后显示第2，3 li -->
          </ul>
          <aside class="h-r-search">
            <form action="#" method="post">
              <label class="h-r-s-box">
                <input type="text" placeholder="输入你想学的课程" name="queryCourse.courseName" value>
                <button type="submit" class="s-btn">
                  <em class="icon18">&nbsp;</em>
                </button>
              </label>
            </form>
          </aside>
        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
    </header>
      
    <!-- /公共头引入 -->
    <nuxt/>

    <!-- 公共底引入 -->
    <footer id="footer">
      <section class="container">
        <div class>
          <h4 class="hLh30">
            <span class="fsize18 f-fM c-999">友情链接</span>
          </h4>
          <ul class="of flink-list">
            <li>
              <a href="http://www.atguigu.com/" title="尚硅谷" target="_blank">尚硅谷</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
        <div class="b-foot">
          <section class="fl col-7">
            <section class="mr20">
              <section class="b-f-link">
                <a href="#" title="关于我们" target="_blank">关于我们</a>|
                <a href="#" title="联系我们" target="_blank">联系我们</a>|
                <a href="#" title="帮助中心" target="_blank">帮助中心</a>|
                <a href="#" title="资源下载" target="_blank">资源下载</a>|
                <span>服务热线：010-56253825(北京) 0755-85293825(深圳)</span>
                <span>Email：info@atguigu.com</span>
              </section>
              <section class="b-f-link mt10">
                <span>©2018课程版权均归谷粒学院所有 京ICP备17055252号</span>
              </section>
            </section>
          </section>
          <aside class="fl col-3 tac mt15">
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wx-icon.png" alt>
              </span>
            </section>
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wb-icon.png" alt>
              </span>
            </section>
          </aside>
          <div class="clear"></div>
        </div>
      </section>
    </footer>
    <!-- /公共底引入 -->
  </div>
</template>
<script>
import '~/assets/css/reset.css'
import '~/assets/css/theme.css'
import '~/assets/css/global.css'
import '~/assets/css/web.css'
import '~/assets/css/base.css'
import '~/assets/css/activity_tab.css'
import '~/assets/css/bottom_rec.css'
import '~/assets/css/nice_select.css'
import '~/assets/css/order.css'
import '~/assets/css/swiper-3.3.1.min.css'
import "~/assets/css/pages-weixinpay.css"

import loginApi from '@/api/login'
import cookie from 'js-cookie'

export default {
  data() {
    return {
      loginUser:{
        id: '',
        age: '',
        avatar: '',
        mobile: '',
        nickname: '',
        sex: ''
      },
      token:'',
    }
  },
  created() {
    if(this.$route.query && this.$route.query.token){
      this.wxlogin()
    }
    this.getUserInfo()
  },
  methods: {
    warnInfo(){
      this.$message({
        type: 'success',
        message: '页面正在施工中，请稍后重试~'
      })
    },
    getUserInfo(){
      //cookie中只以字符串形式存储值，因此取出后需做转换，转换为对象
      var userStr = cookie.get('guli_ucenter')
      if(userStr){
        this.loginUser = JSON.parse(userStr)
      }
    },
    wxlogin(){//微信登录方法
      //从路径中取值
      this.token = this.$route.query.token
      cookie.set('guli_token',this.token,{domain:'localhost'})
      cookie.set('guli_ucenter','',{domain:'localhost'})

      loginApi.getUserByToken()
      .then((response) => {
        this.loginUser = response.data.data.member
        cookie.set('guli_ucenter',this.loginUser,{domain:'localhost'})
      })
    },
    
    logout(){
      // cookie.remove('guli_ucenter')
      // cookie.remove('guli_token')
      cookie.set('guli_token','',{domain:'localhost'})
      cookie.set('guli_ucenter','',{domain:'localhost'})
      window.location.href = '/'
      // this.loginUser = {}
      // this.$router.push({path:'/'}) //此处路由跳转需清空loginUser，建议原生跳转
    }
  },
};
</script>