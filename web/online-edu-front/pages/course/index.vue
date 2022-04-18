<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="#" @click.prevent="showAll">全部</a>
                </li>
                <li v-for="subjectOne in subjectOnes" :key="subjectOne.id">
                  <a :title="subjectOne.title" href="#" @click.prevent="getCourseBySubjectOne(subjectOne.id)"
                  :class="{active:selectedOne === subjectOne.id}">{{subjectOne.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li v-for="subjectTwo in subjectTwos" :key="subjectTwo.id">
                  <a :title="subjectTwo.title" href="#" @click.prevent="getCourseBySubjectTwo(subjectTwo.id)"
                  :class="{active:selectedTwo === subjectTwo.id}">{{subjectTwo.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li :class="{'current bg-orange':buyCountSort!=''}">
                <a title="关注度" href="#" @click.prevent="sortByBuy">关注度
                <span :class="{hide:buyCountSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':gmtCreateSort!=''}">
                <a title="最新" href="#" @click.prevent="sortByTime">最新
                <span :class="{hide:gmtCreateSort==''}">↓</span>
                </a>
              </li>
              <li  :class="{'current bg-orange':priceSort!=''}">
                <a title="价格" href="#" @click.prevent="sortByPrice">价格&nbsp;
                  <span :class="{hide:priceSort==''}">↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="courseInfo.total == 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-if="courseInfo.total != 0">
            <ul class="of" id="bna">
              <li v-for="course in courseInfo.records" :key="course.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="course.cover" class="img-responsive" :alt="course.title">
                    <div class="cc-mask">
                      <a :href="'/course/' + course.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/' + course.id" :title="course.title" class="course-title fsize18 c-333">{{course.title}}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span class="fr jgTag bg-green" v-if="course.price == 0">
                      <i class="c-fff fsize12 f-fA">免费</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{course.buyCount}}人学习</i>
                      |
                      <i class="c-999 f-fA">评论</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始,因nuxt没有定义好的分页方法,需自定义 -->
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{undisable: !courseInfo.hasPrevious}"
              href="#"
              title="首页"
              @click.prevent="getPage(1)">首</a>
            <a
              v-if="courseInfo.hasPrevious"
              href="#"
              title="前一页"
              @click.prevent="getPage(courseInfo.current-1)">&lt;</a>
            <a
              v-for="page in courseInfo.pages"
              :key="page"
              :class="{current: courseInfo.current == page, undisable: courseInfo.current == page}"
              :title="'第'+page+'页'"
              href="#"
              @click.prevent="getPage(page)">{{ page }}</a>
            <a
              v-if="courseInfo.hasNext"
              href="#"
              title="后一页"
              @click.prevent="getPage(courseInfo.current+1)">&gt;</a>
            <a
              :class="{undisable: !courseInfo.hasNext}"
              href="#"
              title="末页"
              @click.prevent="getPage(courseInfo.pages)">末</a>
            <div class="clear"/>
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
import courseApi from '@/api/course'

export default {
  data() {
    return {
      current:1,
      //返回值
      courseInfo:[],
      subjectOnes:[],
      subjectTwos:[],
      courseQuery:{},
      //以下两个参数用于css样式的判断
      selectedOne:-1,
      selectedTwo:-1,

      buyCountSort:"",
      gmtCreateSort:"",
      priceSort:""

    }
  },
  created() {
    this.initCourse()
    this.initSubject()
  },
  methods: {
    initCourse(){
      courseApi.getCoursePage(1,8,this.courseQuery)
      .then((response) => {
        this.courseInfo = response.data.data
      })
    },
    initSubject(){
      courseApi.getSubjectOnes()
      .then((response) => {
        this.subjectOnes = response.data.data.subjectOnes
      })
    },
    getPage(page){
      this.current = page
      courseApi.getCoursePage(page,8,this.courseQuery)
        .then((response) => {
          this.courseInfo = response.data.data
        })
    },
    getCourseBySubjectOne(subjectParentId){
      this.selectedOne = subjectParentId
      //清空原数据
      this.selectedTwo = -1
      this.courseQuery = {}
      //获取数据
      courseApi.getSubjectTwoByOne(subjectParentId)
      .then((response) => {
        
        this.courseQuery.subjectParentId = subjectParentId
        this.subjectTwos = response.data.data.subjectTwos
        this.getPage(1)
      })
    },
    showAll(){
      this.subjectTwos = ''
      this.courseQuery = {}
      this.getPage(1)
    },
    getCourseBySubjectTwo(id){
      this.selectedTwo = id
      this.courseQuery.subjectId = id
      this.getPage(1)
    },
    //以下为排序的方法,重复代码太多,待优化
    sortByBuy(){
      this.courseQuery.gmtCreateSort = ''
      this.courseQuery.priceSort = ''
      this.courseQuery.buyCountSort = '1'
      this.buyCountSort = '1'
      this.priceSort = ''
      this.gmtCreateSort = ''
      this.getPage(1)
    },
    sortByTime(){
      this.courseQuery.gmtCreateSort = '1'
      this.courseQuery.priceSort = ''
      this.courseQuery.buyCountSort = ''
      this.gmtCreateSort = '1'
      this.buyCountSort = ''
      this.priceSort = ''
      this.getPage(1)
    },
    sortByPrice(){
      this.courseQuery.gmtCreateSort = ''
      this.courseQuery.priceSort = '1'
      this.courseQuery.buyCountSort = ''
      this.priceSort = '1'
      this.gmtCreateSort = ''
      this.buyCountSort = ''
      this.getPage(1)
    }
  },
};
</script>

<style scoped>
  .active {
    background: #7777;
  }
  .hide {
    display: none;
  }
  .show {
    display: block;
  }
</style>