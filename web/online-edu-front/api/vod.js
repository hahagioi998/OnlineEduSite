import request from '@/utils/request'

export default{
    getAuth(videoId){
        return request({
            url:`/eduvod/video/getPlayAuth/${videoId}`,
            method:'get',
        })
    },
}