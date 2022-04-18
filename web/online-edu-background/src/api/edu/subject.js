import request from '@/utils/request'

export default {
    //1、课程树查询
    getSubjectTree() {
        return request({
            url: '/eduservice/subject/getSubjectTree',
            method: 'post',
          })
    },
    getSubjectOnes(){
        return request({
            url: '/eduservice/subject/getSubjectOnes',
            method: 'post',
          })
    },
    getSubjectTwos(id){
        return request({
            url: `/eduservice/subject/getSubjectTwosByParentId/${id}`,
            method: 'post',
          })
    }
}
