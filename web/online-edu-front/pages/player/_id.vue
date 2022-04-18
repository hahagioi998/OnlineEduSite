<template>
  <div>

    <!-- 阿里云视频播放器样式 -->
    <link rel="stylesheet" href="https://g.alicdn.com/de/prismplayer/2.8.1/skins/default/aliplayer-min.css" >
    <!-- 阿里云视频播放器脚本 -->
    <script charset="utf-8" type="text/javascript" src="https://g.alicdn.com/de/prismplayer/2.8.1/aliplayer-min.js" />

    <!-- 定义播放器dom-->
    <div id="J_prismPlayer" class="prism-player" style="margin:auto"/>
  </div>
</template>

<script>
import vodApi from '@/api/vod'

export default {
    layout:'video',
    //异步请求
    asyncData({params,error}) {
        return vodApi.getAuth(params.id)
        .then((response) => {
            return {
                playAuth:response.data.data.playAuth,
                videoId:params.id
            }
        })
    },
    mounted() {
        new Aliplayer({
        id: 'J_prismPlayer',
        vid: this.videoId, // 视频id
        playauth: this.playAuth, // 播放凭证
        encryptType: '1', // 如果播放加密视频，则需设置encryptType=1，非加密视频无需设置此项
        width: '60%',
        height: '500px'
    }, function(player) {
        console.log('播放器创建成功')
    })
    },
}
</script>