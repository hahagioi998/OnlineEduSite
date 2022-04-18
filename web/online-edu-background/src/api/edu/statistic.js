import request from '@/utils/request'

export default {

    createDataByDay(day) {
        return request({
            url: `/servicesta/daily/saveData/${day}`,
            method: 'get',
          })
    },
    getChartData(searchObj){
        return request({
            url: `/servicesta/daily//getChart`,
            method: 'post',
            data:searchObj
          })
    }
    
}
