<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师列表 开始 -->
    <section class="container">
      <header class="comm-title all-teacher-title">
        <h2 class="fl tac">
          <span class="c-333">全部讲师</span>
        </h2>
        <section class="c-tab-title">
          <a id="subjectAll" title="全部" href="#">全部</a>
          <!-- <c:forEach var="subject" items="${subjectList }">
                            <a id="${subject.subjectId}" title="${subject.subjectName }" href="javascript:void(0)" onclick="submitForm(${subject.subjectId})">${subject.subjectName }</a>
          </c:forEach>-->
        </section>
      </header>
      <section class="c-sort-box unBr">
        <div>
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="pageInfo.total == 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="i-teacher-list" v-if="pageInfo.total > 0">
            <ul class="of">
              <li v-for="teacher in pageInfo.records" :key="teacher.id">
                <section class="i-teach-wrap">
                  <div class="i-teach-pic">
                    <a :href="'/teacher/' + teacher.id" :title="teacher.name" target="_blank">
                      <img :src="teacher.avatar" alt>
                    </a>
                  </div>
                  <div class="mt10 hLh30 txtOf tac">
                    <a :href="'/teacher/' + teacher.id" :title="teacher.name" target="_blank" class="fsize18 c-666">{{teacher.name}}</a>
                  </div>
                  <div class="hLh30 txtOf tac">
                    <span class="fsize14 c-999">{{teacher.intro}}</span>
                  </div>
                  <div class="mt15 i-q-txt">
                    <p class="c-999 f-fA">{{teacher.career}}</p>
                  </div>
                </section>
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
              :class="{undisable: !pageInfo.hasPrevious}"
              href="#"
              title="首页"
              @click.prevent="getPage(1)">首</a>
            <a
              v-if="pageInfo.hasPrevious"
              href="#"
              title="前一页"
              @click.prevent="getPage(pageInfo.current-1)">&lt;</a>
            <a
              v-for="page in pageInfo.pages"
              :key="page"
              :class="{current: pageInfo.current == page, undisable: pageInfo.current == page}"
              :title="'第'+page+'页'"
              href="#"
              @click.prevent="getPage(page)">{{ page }}</a>
            <a
              v-if="pageInfo.hasNext"
              href="#"
              title="后一页"
              @click.prevent="getPage(pageInfo.current+1)">&gt;</a>
            <a
              :class="{undisable: !pageInfo.hasNext}"
              href="#"
              title="末页"
              @click.prevent="getPage(pageInfo.pages)">末</a>
            <div class="clear"/>
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /讲师列表 结束 -->
  </div>
</template>
<script>

import teacherApi from '@/api/teacher'

export default {
  // data() {
  //   return {
  //     pageInfo:[]
  //   }
  // },
  //异步调用的一种形式,只调用一次
  asyncData({params, error}) {//params为this.$route.params
    return teacherApi.getTeacherPage(1,8)
    .then((response) => {
      return { pageInfo: response.data.data }//简写的形式，不用定义data中的内容
    })
  },
  methods: {
    getPage(current){
      teacherApi.getTeacherPage(current,8)
      .then((response) => {
        this.pageInfo = response.data.data
      })
    }
  },
};
</script>