import request from '@/utils/request'

export default {
    addCourse(courseInfo){
        return request({
            url: '/eduservice/course/addCourseInfo',
            method: 'post',
            data:courseInfo//用json方式传递数据
          })
    },
    getCourseInfo(id){
        return request({
            url: `/eduservice/course/getCourseInfo/${id}`,
            method: 'get',
          })
    },
    updateCourseInfo(courseInfo){
        return request({
            url: '/eduservice/course/updateCourseInfo',
            method: 'post',
            data:courseInfo//用json方式传递数据
          })
    },
    getPublishCourseInfo(id){
        return request({
            url: `/eduservice/course/getBeforePublish/${id}`,
            method: 'get',
          })
    },
    finalPublish(id){
        return request({
            url: `/eduservice/course/finalPublish/${id}`,
            method: 'post',
          })
    },
    getAllCourse(){
        return request({
            url: '/eduservice/course/getCourseList',
            method: 'get',
          })
    },
    deleteCourseById(id){
        return request({
            url: `/eduservice/course/deleteCourseById/${id}`,
            method: 'delete',
          })
    },
    getCourseConditionPage(current,limit,courseQuery){
        return request({
            url: `/eduservice/course/pageCourseCondition/${current}/${limit}`,
            method: 'post',
            data:courseQuery
          })
    },

}