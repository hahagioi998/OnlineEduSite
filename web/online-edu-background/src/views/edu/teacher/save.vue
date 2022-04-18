<template>
    <div class="app-container">
        <el-form label-width="120px">
        <el-form-item label="讲师名称">
            <el-input v-model="teacher.name"/>
        </el-form-item>
        
        <el-form-item label="讲师排序">
            <el-input-number v-model="teacher.sort" controls-position="right" min="0"/>
        </el-form-item>
        <el-form-item label="讲师头衔">
            <el-select v-model="teacher.level" clearable placeholder="请选择">
            <!--
                数据类型一定要和取出的json中的一致，否则没法回填
                因此，这里value使用动态绑定的值，保证其数据类型是number
            -->
            <el-option :value="1" label="高级讲师"/>
            <el-option :value="2" label="首席讲师"/>
            </el-select>
        </el-form-item>
        <el-form-item label="讲师资历">
            <el-input v-model="teacher.career"/>
        </el-form-item>
        <el-form-item label="讲师简介">
            <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
        </el-form-item>

        <!-- 讲师头像 -->
        <el-form-item label="讲师头像">
            <!-- 头衔缩略图 -->
            <pan-thumb :image="teacher.avatar"/>
            <!-- 文件上传按钮 -->
            <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
            </el-button>
            <!--
                v-show：是否显示上传组件
                :key：类似于id，如果一个页面多个图片上传控件，可以做区分
                :url：后台上传的url地址
                @close：关闭上传组件
                @crop-upload-success：上传成功后的回调 -->
            <image-cropper
                        v-show="imagecropperShow"
                        :width="300"
                        :height="300"
                        :key="imagecropperKey"
                        :url="BASE_API+'/eduoss/fileoss'"
                        field="file"
                        @close="close"
                        @crop-upload-success="cropSuccess"/>
        </el-form-item>

        <el-form-item>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
        </el-form-item>
        </el-form>
    </div>
</template>

<script>
import teacherApi from '@/api/edu/teacher'
//实现头像上传功能的组件
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
    components:{ ImageCropper,PanThumb},
    //核心逻辑代码
    data() {
        return {
            teacher:{
                name:'',
                sort:0,
                level:1,
                career:'',
                intro:'',
                avatar:''
            },
            imagecropperShow:false,
            imagecropperKey:0,
            BASE_API:process.env.BASE_API,
            saveBtnDisabled:false
        }
    },
    created() {
        //路径参数的取值方法
        if(this.$route.params && this.$route.params.id){
            const id = this.$route.params.id
            this.showEditTeacherInfo(id)
        }else{
            this.init()
        }
    },
    watch: {
        $route(to,from){//路由发生变化，方法即执行
            this.init()
        }
    },
    methods: {
        init(){
            this.teacher = {}
        },
        saveOrUpdate(){
            if(this.teacher.id){
                this.updateTeacher()
            }else{
                this.saveTeacher()
            }
        },
        saveTeacher(){
            teacherApi.addTeacher(this.teacher)
            .then(response => {
                this.$message({
                    type: 'success',
                    message: '添加成功!'
                })
                this.$router.push({path:'/teacher/table'})
            })
        },
        updateTeacher(){
            teacherApi.editTeacher(this.teacher)
            .then(response => {
                this.$message({
                    type: 'success',
                    message: '编辑成功!'
                })
                this.$router.push({path:'/teacher/table'})
            })
        },
        showEditTeacherInfo(id){
            teacherApi.getTeacherInfo(id)
            .then(response => {
                this.teacher = response.data.teacher
            })
        },
        close(){//关闭上传弹框的方法
            this.imagecropperShow = false
            //上传组件初始化
            this.imagecropperKey = this.imagecropperKey + 1
        },
        cropSuccess(data){//上传成功的方法
            this.teacher.avatar = data.url
            this.imagecropperShow = false
            //上传组件初始化
            this.imagecropperKey = this.imagecropperKey + 1
        }


        
    }
}
</script>