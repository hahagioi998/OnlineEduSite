import request from '@/utils/request'

export default{
    getComments(courseId,current,limit){
        return request({
            url:`/eduservice/comment/getCommentPage/${courseId}/${current}/${limit}`,
            method:'get'
        })
    },
    addComment(comment){
        return request({
            url:'/eduservice/comment/addComment',
            method:'post',
            data:comment
        })
    }
}