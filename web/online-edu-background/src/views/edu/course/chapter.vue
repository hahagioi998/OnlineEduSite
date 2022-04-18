<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-button type="text" @click="openSaveDialog">添加章节</el-button>

    <ul class="chapterList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>{{chapter.title}}</p>

            <span class="acts">
                <el-button type="text" @click="openVideoAddDialog(chapter.id)">添加课时</el-button>
                <el-button style="" type="text" @click="openEditDialog(chapter.id)">编辑</el-button>
                <el-button type="text" @click="deleteChapter(chapter.id)">删除</el-button>
            </span>

        <ul class="videoList">
          <li v-for="video in chapter.children" :key="video.id">

            <p>{{video.title}}</p>
            
            <span class="acts">
                <el-button style="" type="text" @click="openVideoEditDialog(video.id)">编辑</el-button>
                <el-button type="text" @click="deleteVideo(video.id)">删除</el-button>
            </span>
          </li>
        </ul>
      </li>
    </ul>
    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
    </div>

        <!-- 添加和修改章节表单:弹出框 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" :title="flag">
        <el-form :model="chapter" label-width="120px">
            <el-form-item label="章节标题">
                <el-input v-model="chapter.title"/>
            </el-form-item>
            <el-form-item label="章节排序">
                <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
        </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API+'/eduvod/video/upload'"
            :limit="1"
            class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
              <el-tooltip placement="right-end">
                <div slot="content">最大支持1G，<br>
                    支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                    GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                    MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                    SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
                <i class="el-icon-question"/>
              </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import chapterApi from '@/api/edu/chapter'
import videoApi from '@/api/edu/video'

export default {
    data() {
        return {
            saveBtnDisabled: false,
            courseId:'',
            chapterVideoList:[],
            chapter:{
              courseId:'',
              title:'',
              sort: 0
            },
            video:{
              title: '',
              sort: 0,
              free: 0,
              videoSourceId: '',
              videoOriginalName:''
            },
            dialogChapterFormVisible: false,
            dialogVideoFormVisible: false,
            saveVideoBtnDisabled: false,
            flag: '',
            //文件上传相关
            fileList:[],
            BASE_API:process.env.BASE_API
        }
    },
    created() {
      //路径参数的判断
      if(this.$route.params && this.$route.params.id){
        this.courseId = this.$route.params.id
        this.getChapterVideo(this.courseId)
      }
    },
    methods: {
      //=======================以下为上传视频的方法=================
      handleVodUploadSuccess(response,file,fileList){
        this.video.videoSourceId = response.data.videoId
        this.video.videoOriginalName = file.name
        this.$message({
          type:"success",
          message:"上传视频成功"
        })
      },
      handleVodRemove(){
        videoApi.deleteUploadVideo(this.video.videoSourceId)
        .then((response) => {
          this.video.videoSourceId = '',
          this.video.videoOriginalName = '',
          this.$message({
                        type: 'success',
                        message: '删除成功!'
                    })
        })
      },
      beforeVodRemove(file,fileList){
        return this.$confirm('确定删除' + file.name + '?')
      },
      handleUploadExceed(file,fileList){
        this.$message.warning('只支持上传一个视频，请删除已上传的视频')
      },
      //=======================以下为video增删改查的方法============
      saveOrUpdateVideo(){
        if(this.video.id){
          this.updateVideo()
        }else{
          this.addVideo()
        }
      },
      openVideoAddDialog(chapterId){
        //清空原数据
        this.video = {}
        this.fileList = []
        this.dialogVideoFormVisible = true
        this.video.chapterId = chapterId
      },
      openVideoEditDialog(videoId){
        this.getVideo(videoId)
        this.dialogVideoFormVisible = true
      },
      updateVideo(){
        videoApi.updateVideo(this.video)
        .then((result) => {
          this.$message({
          type: 'success',
          message: '修改成功!'
        })
        this.dialogVideoFormVisible = false
        //刷新页面
        this.getChapterVideo(this.courseId)
        })
      },
      getVideo(id){
        videoApi.getVideo(id)
        .then((response) => {
          this.video = response.data.video
        })
      },
      deleteVideo(videoId){
        this.$confirm('此操作将删除课时,是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
            .then(() => {
                videoApi.deleteVideo(videoId)
                .then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    })
                    //刷新页面
                    this.getChapterVideo(this.courseId)
                })
            })
      },
      addVideo(){
        this.video.courseId = this.courseId
        videoApi.addVideo(this.video)
        .then((response) => {
          //关闭弹框
          this.dialogVideoFormVisible = false
          //提示信息
          this.$message({
            type:'success',
            message:'添加课时成功'
          })
          //刷新页面
          this.getChapterVideo(this.courseId)
        })
      },
      //=======================以下为章节的方法======================
      deleteChapter(id){
        this.$confirm('此操作将删除章节,是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
            .then(() => {
                chapterApi.deleteChapter(id)
                .then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    })
                    //刷新页面
                    this.getChapterVideo(this.courseId)
                })
            })
      },
      updateChapter(){
        // this.chapter.courseId = this.courseId
        chapterApi.updateChapter(this.chapter)
        .then((response) => {
          //关闭弹框
          this.dialogChapterFormVisible = false
          //提示信息
          this.$message({
            type:'success',
            message:'修改章节成功'
          })
          //刷新页面
          this.getChapterVideo(this.courseId)
        })
      },
      saveChapter(){
        this.chapter.courseId = this.courseId
        chapterApi.addChapter(this.chapter)
        .then((response) => {
          //关闭弹框
          this.dialogChapterFormVisible = false
          //提示信息
          this.$message({
            type:'success',
            message:'添加章节成功'
          })
          //刷新页面
          this.getChapterVideo(this.courseId)
        })
      },
      openEditDialog(chapterId){
        this.flag = '修改章节'
        this.dialogChapterFormVisible = true
        chapterApi.getChapter(chapterId)
        .then((response) => {
          this.chapter = response.data.chapter
        })
      },
      openSaveDialog(){
        this.flag = '添加章节'
        this.dialogChapterFormVisible = true
        //清空表单
        this.chapter.title = ''
        this.chapter.sort = 0
      },
      saveOrUpdate(){
        if(this.chapter.id){
          this.updateChapter()
        }else{
          this.saveChapter()
        }
      },
      next(){
          //跳转
          this.$router.push({path: '/course/publish/' + this.courseId})
      },
      previous(){
          //跳转
          this.$router.push({path: '/course/info/' + this.courseId})
      },
      getChapterVideo(courseId){
        chapterApi.getChapterVideo(courseId)
        .then((response) => {
          this.chapterVideoList = response.data.chapterTree
        })
      },
    },
}
</script>

<style scoped>
/* .chapterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
}

.chapterList li{
  position: relative;
}

.chapterList p{
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}

.chapterList .acts {
    float: right;
    font-size: 14px;
}

.videoList{
  padding-left: 50px;
}
.videoList p{
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
} */
</style>