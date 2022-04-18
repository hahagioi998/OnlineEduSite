<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-form label-width="120px">
        <el-form-item label="课程标题">
            <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
        </el-form-item>

        <!-- 所属分类 TODO -->
        <el-form-item label="课程类别">
            <el-select v-model="courseInfo.subjectParentId" @change="getSubjectTwos" placeholder="请选择一级分类">
                <el-option
                    v-for="subjectOne in subjectOnes"
                    :key="subjectOne.id"
                    :label="subjectOne.title"
                    :value="subjectOne.id">
                </el-option>
            </el-select>
            <el-select v-model="courseInfo.subjectId" placeholder="请选择二级分类">
                <el-option
                    v-for="subjectTwo in subjectTwos"
                    :key="subjectTwo.id"
                    :label="subjectTwo.title"
                    :value="subjectTwo.id">
                </el-option>
            </el-select>
        </el-form-item>
        <!-- 课程讲师 TODO -->
        <el-form-item label="课程讲师">
            <el-select v-model="courseInfo.teacherId" placeholder="请选择">
                <el-option
                    v-for="teacher in teachers"
                    :key="teacher.id"
                    :label="teacher.name"
                    :value="teacher.id">
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="总课时">
            <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
        </el-form-item>

        <!-- 课程简介 -->
        <el-form-item label="课程简介">
            <tinymce v-model="courseInfo.description" :height="300"/>
        </el-form-item>

        <!-- 课程封面 -->
        <el-form-item label="课程封面">
            <el-upload
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                :action="BASE_API+'/eduoss/fileoss'"
                class="avatar-uploader">
                <img :src="courseInfo.cover" style="height:200px;">
            </el-upload>
        </el-form-item>

        <el-form-item label="课程价格">
            <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
        </el-form-item>

        <el-form-item>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
        </el-form-item>
    </el-form>
  </div>
  
</template>

<script>
import courseApi from '@/api/edu/course'
import teacherApi from '@/api/edu/teacher'
import subjectApi from '@/api/edu/subject'
import Tinymce from '@/components/Tinymce'//引入富文本编辑器组件

export default {
    data() {
        return {
            saveBtnDisabled: false,
            courseInfo:{
                title:'',
                lessonNum:'',
                price:'',
                subjectId: '',
                teacherId: '',
                description: '',
                subjectParentId:'',
                cover: '/static/default.jpg',
            },
            id:'',
            teachers:[],
            subjectOnes:[],
            subjectTwos:[],
            BASE_API: process.env.BASE_API
        }
          
    },
    components: {
        Tinymce//声明富文本编辑器组件
    },
    created() {
        this.getAllTeachers()
        this.getSubjectOnes()
        if(this.$route.params && this.$route.params.id){
            this.courseId = this.$route.params.id
            this.getCourseInfo(this.courseId)
        }
    },
    methods: {
        saveOrUpdate(){
            if(this.$route.params && this.$route.params.id){
                this.id = this.$route.params.id
                courseApi.updateCourseInfo(this.courseInfo)
                .then((response) => {
                    this.$message({
                        type: 'success',
                        message: '修改课程信息成功!'
                    });
                    this.$router.push({path: '/course/chapter/' + this.id})
                })
            }else{
                courseApi.addCourse(this.courseInfo)
                .then(response => {
                    this.id = response.data.id
                    this.$message({
                        type: 'success',
                        message: '录入课程信息成功!'
                    });
                    this.$router.push({path: '/course/chapter/' + this.id})
                })
            }
        },
        getAllTeachers(){
            teacherApi.getAllTeachers()
            .then((response) => {
                this.teachers = response.data.items
            })
        },
        getSubjectOnes(){
            subjectApi.getSubjectOnes()
            .then((response) => {
                this.subjectOnes = response.data.subjectOnes
            })
        },
        getSubjectTwos(id){
            //首先清空原始的二级分类信息，避免更改一级分类时原二级信息保留
            this.courseInfo.subjectId = ''

            subjectApi.getSubjectTwos(id)
            .then((response) => {
                this.subjectTwos = response.data.subjectTwos
            })
        },
        handleAvatarSuccess(res,file){
            this.courseInfo.cover = res.data.url
        },
        beforeAvatarUpload(file){
            //判断上传文件的格式和大小
            const isJPG = file.type === 'image/jpeg'
            const isLt2M = file.size / 1024 / 1024 < 2

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!')
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!')
            }
            return isJPG && isLt2M
        },
        getCourseInfo(id){
            courseApi.getCourseInfo(id)
            .then((response) => {
                this.courseInfo = response.data.courseInfoVo
                //下面是为了实现二级分类回显，将二级分类给读取到
                subjectApi.getSubjectTwos(this.courseInfo.subjectParentId)
                    .then((response) => {
                    this.subjectTwos = response.data.subjectTwos
                })
            })
        },
        
        

    },
}
</script>

<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>