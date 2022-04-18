import request from '@/utils/request'

export default {
    addVideo(video){
        return request({
            url: '/eduservice/video/addVideo',
            method: 'post',
            data:video
          })
    },
    deleteVideo(id){
        return request({
            url: `/eduservice/video/deleteVideo/${id}`,
            method: 'delete',
          })
    },
    getVideo(id){
        return request({
            url: `/eduservice/video/getVideo/${id}`,
            method: 'get',
          })
    },
    updateVideo(video){
        return request({
            url: '/eduservice/video/updateVideo',
            method: 'post',
            data:video
          })
    },
    deleteUploadVideo(videoSourceId){
        return request({
            url: `/eduvod/video/delete/${videoSourceId}`,
            method: 'delete',
          })
    }

}