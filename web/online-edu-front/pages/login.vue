<template>
  <div class="main">
    <div class="title">
      <a class="active" href="/login">登录</a>
      <span>·</span>
      <a href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <el-form ref="userForm" :model="user">

        <el-form-item class="input-prepend restyle" prop="mobile" :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' },{validator: checkPhone, trigger: 'blur'}]">
          <div >
            <el-input type="text" placeholder="手机号" v-model="user.mobile"/>
            <i class="iconfont icon-phone" />
          </div>
        </el-form-item>

        <el-form-item class="input-prepend" prop="password" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <div>
            <el-input type="password" placeholder="密码" v-model="user.password"/>
            <i class="iconfont icon-password"/>
          </div>
        </el-form-item>

        <div class="btn">
          <input type="button" class="sign-in-button" value="登录" @click="submitLogin()">
        </div>
      </el-form>
      <!-- 更多登录方式 -->
      <div class="more-sign">
        <h6>社交帐号登录</h6>
        <ul>
          <li><a id="weixin" class="weixin" target="_blank" href="http://localhost:8160/api/ucenter/wx/login"><i class="iconfont icon-weixin"/></a></li>
          <li><a id="qq" class="qq" target="_blank" href="#"><i class="iconfont icon-qq"/></a></li>
        </ul>
      </div>
    </div>

  </div>
</template>

<script>
  import '~/assets/css/sign.css'
  import '~/assets/css/iconfont.css'

  import cookie from 'js-cookie'//下载的插件，用于cookie中传递数据
  import loginApi from '@/api/login'

  export default {
    layout: 'sign',
    data () {
      return {
        user: {
          username:'',
          mobile: '',
          password: ''
        },
        loginUser:{//封装登录后获取的用户信息
        },
        token:''
      }
    },

    methods: {
        submitLogin(){
          loginApi.loginUser(this.user)
          .then((response) => {
            //1、从后端接口中获取登录的token值
            this.token = response.data.data.token

            //2、将token放入cookie中[cookie值名，token值，cookie传递的作用域]
            cookie.set('guli_token',this.token,{domain:'localhost'})

            //3、request的api中定义拦截器，其中已将token放入request的header中
            //4、调用从接口中获取用户信息方法

            loginApi.getUserByToken()
            .then((response) => {
              this.loginUser = response.data.data.member
              //5、将登录的用户信息放入cookie中
              cookie.set('guli_ucenter',JSON.stringify(this.loginUser),{domain:'localhost'})
              //6、跳转首页面
              this.$router.push({path:'/'})
              // 另一种跳转方式的写法:window.location.href='/'
            })

          })
        },
        //检查手机号格式
        checkPhone (rule, value, callback) {
            if (!(/^1[34578]\d{9}$/.test(value))) {
            return callback(new Error('手机号码格式不正确'))
            }
            return callback()
        }
    }
  }
</script>
<style>
   .el-form-item__error{
    z-index: 9999999;
  }
</style>