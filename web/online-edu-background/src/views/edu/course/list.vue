<template>
    <div class="app-container">
        <!--查询表单-->
        <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
            <el-input v-model="courseQuery.title" placeholder="课程名"/>
        </el-form-item>

        <el-form-item>
            <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
            <el-option :value="'Draft'" label="未发布"/>
            <el-option :value="'Normal'" label="已发布"/>
            </el-select>
        </el-form-item>

        <el-form-item label="添加时间">
            <el-date-picker
            v-model="courseQuery.begin"
            type="datetime"
            placeholder="选择开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="00:00:00"
            />
        </el-form-item>
        <el-form-item>
            <el-date-picker
            v-model="courseQuery.end"
            type="datetime"
            placeholder="选择截止时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="00:00:00"
            />
        </el-form-item>

        <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
        </el-form>

          <!-- 表格 -->
        <el-table
        :data="list"
        border
        fit
        highlight-current-row>

        <el-table-column
            label="序号"
            width="70"
            align="center">
            <template slot-scope="scope">
            {{ (page - 1) * limit + scope.$index + 1 }}
            </template>
        </el-table-column>

        <el-table-column prop="title" label="课程名称" width="400" />

            <!-- 判断的写法 -->
        <el-table-column label="课程状态" width="200">
            <template slot-scope="scope">
                {{ scope.row.status==='Normal'?'已发布':'未发布' }}
            </template>
        </el-table-column>

        <el-table-column prop="lessonNum" label="课时数目" width="160"/>

        <el-table-column prop="gmtCreate" label="添加时间" width="230"/>

        <el-table-column prop="viewCount" label="浏览数量" width="222" />

        <el-table-column label="操作" width="400" align="center">
            <template slot-scope="scope">
                <router-link :to="'/course/info/'+scope.row.id">
                    <el-button type="primary" size="mini" icon="el-icon-edit">编辑基本信息</el-button>
                </router-link>
                <router-link :to="'/course/chapter/'+scope.row.id">
                    <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程大纲</el-button>
                </router-link>
                
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">课程删除</el-button>
            </template>
        </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination
            :current-page="page"
            :page-size="limit"
            :total="total"
            style="padding: 30px 0; text-align: center;"
            layout="total, prev, pager, next, jumper"
            @current-change="getList"
        />
    </div>
</template>

<script>
import course from '@/api/edu/course'

export default {
    //核心逻辑代码
    data() {//定义变量和初始值
        return {
            list:null,//查询之后返回的数据

            //以下为初始参数值
            total:0,
            page:1,
            limit:10,
            courseQuery:{}
        }
    },
    created() {//页面渲染之前执行，一般是调用methods中的方法
        this.getList()
    },
    methods: {
        //显示列表
        getList(){
            this.page = 1
            course.getCourseConditionPage(this.page,this.limit,this.courseQuery)
            .then(response => {
                this.list = response.data.rows
                this.total = response.data.total
            })
        },
        //清空搜素框
        resetData(){
            this.courseQuery = {}
            this.getList()
        },
        //讲师删除
        removeDataById(id) {
            this.$confirm('此操作将删除课程, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
            .then(() => {
                course.deleteCourseById(id)
                .then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    })
                    this.getList()
                })
            })
        }
    }
}
</script>