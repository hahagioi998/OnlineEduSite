import axios from 'axios'
import cookie from 'js-cookie'//下载的插件，用于cookie中传递数据
import {Message,MessageBox} from 'element-ui'

// 创建axios实例
const service = axios.create({
  // baseURL: 'http://192.168.200.130:9001', // nginx的地址，用nginx代理访问
  baseURL: 'http://localhost:9999', // 网关地址
  timeout: 5000 // 请求超时时间
})

//定义拦截器,每个请求均执行该拦截器
service.interceptors.request.use(
  config => {
    //如果token存在，将token放入header中
    if (cookie.get('guli_token')) {
      config.headers['token'] = cookie.get('guli_token');
    }
      return config
    },
  err => {
  return Promise.reject(err);
  })
// http response 拦截器
service.interceptors.response.use(
  response => {
    //debugger
    if (response.data.code == 28004) {
        console.log("response.data.resultCode是28004")
        // 返回 错误代码-1 清除ticket信息并跳转到登录页面
        //debugger
        window.location.href="/login"
        return
    }else{
      if (response.data.code !== 20000) {
        //25000：订单支付中，不做任何提示
        if(response.data.code != 25000) {
          Message({
            message: response.data.message || 'error',
            type: 'error',
            duration: 5 * 1000
          })
        }
      } else {
        return response;
      }
    }
  },
  error => {
    return Promise.reject(error.response)   // 返回接口返回的错误信息
});
export default service