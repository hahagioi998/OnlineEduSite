import request from '@/utils/request'

export default{
    getTeacherPage(current,limit){
        return request({
            url:`/eduservice/teacherfront/pageTeacher/${current}/${limit}`,
            method:'get'
        })
    },
    getTeacherById(id){
        return request({
            url:`/eduservice/teacherfront/getFrontTeacher/${id}`,
            method:'get'
        })
    }
}