import request from '@/utils/request'

export default{
    createOrder(courseId){
        return request({
            url:`/eduorder/order/createOrder/${courseId}`,
            method:'get'
        })
    },
    getOrderInfo(orderNo){
        return request({
            url:`/eduorder/order/getOrderInfo/${orderNo}`,
            method:'get'
        })
    },
    createPayPic(orderNo){
        return request({
            url:`/eduorder/paylog/createPayPic/${orderNo}`,
            method:'get'
        })
    },
    checkPayStatus(orderNo){
        return request({
            url:`/eduorder/paylog/checkPayStatus/${orderNo}`,
            method:'get'
        })
    }
}