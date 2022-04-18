import request from '@/utils/request'

export default{
    sendMessage(phone){
        return request({
            url:`/edumsm/msm/sendMessage/${phone}`,
            method:'get'
        })
    },
    registerUser(formItem){
        return request({
            url:'/educenter/member/register',
            method:'post',
            data:formItem
        })
    }
}