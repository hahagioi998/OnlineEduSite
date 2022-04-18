import request from '@/utils/request'

export default{
    //登录
    loginUser(user){
        return request({
            url:'/educenter/member/login',
            method:'post',
            data:user
        })
    },
    //从request中得到token,从而获得user
    getUserByToken(){
        return request({
            url:'/educenter/member/makeSureLogin',
            method:'get'
        })
    }
}

   
