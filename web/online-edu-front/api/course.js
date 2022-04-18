import request from '@/utils/request'

export default{
    getCoursePage(current,limit,courseQuery){
        return request({
            url:`/eduservice/coursefront/getCoursePage/${current}/${limit}`,
            method:'post',
            data:courseQuery
        })
    },
    getSubjectOnes(){
        return request({
            url:`/eduservice/subject/getSubjectOnes`,
            method:'post'
        })
    },
    getSubjectTwoByOne(id){
        return request({
            url:`/eduservice/subject/getSubjectTwosByParentId/${id}`,
            method:'post'
        })
    },
    getCourseShowInfo(id){
        return request({
            url:`/eduservice/coursefront/showCourseInfo/${id}`,
            method:'get'
        })
    },
}