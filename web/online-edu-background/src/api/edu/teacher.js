import request from '@/utils/request'

// export function getList(params) {
//   return request({
//     url: '/table/list',
//     method: 'get',
//     params
//   })
// }

export default {
    //1、讲师列表
    getTeacherListPage(current,limit,teacherQuery) {
        return request({
            // url带参数的两种写法：
            url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
            // url: '/edu/teacher/pageTeacherCondition/' + current + '/' + limit,
            method: 'post',
            data:teacherQuery//后端用@RequestBody时,用此种写法，表示用json方式传递数据
          })
    },
    //2、讲师删除
    deleteTeacherById(id) {
        return request({
            // url带参数的两种写法：
            url: `/eduservice/teacher/${id}`,
            method: 'delete',
          })
    },
    //3、讲师添加
    addTeacher(teacher) {
        return request({
            // url带参数的两种写法：
            url: '/eduservice/teacher/addTeacher',
            method: 'post',
            data:teacher
          })
    },
    //4、根据id查询讲师
    getTeacherInfo(id) {
        return request({
            url: `/eduservice/teacher/getTeacher/${id}`,
            method: 'get',
          })
    },
    //5、编辑讲师
    editTeacher(teacher) {
        return request({
            url: '/eduservice/teacher/updateTeacher',
            method: 'post',
            data:teacher
          })
    },
    //6、所有讲师
    getAllTeachers() {
        return request({
            url: '/eduservice/teacher/findAll',
            method: 'get',
        })
    },
}
