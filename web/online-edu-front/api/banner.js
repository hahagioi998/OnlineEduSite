import request from '@/utils/request'

export default{
    getListBanner(){
        return request({
            url:'/educms/banner-user/getAllBanner',
            method:'get'
        })
    }
}