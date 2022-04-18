import request from '@/utils/request'

export default{
    getIndexInfo(){
        return request({
            url:'/eduservice/indexfront/getIndexCourseTeacher',
            method:'get'
        })
    }
}