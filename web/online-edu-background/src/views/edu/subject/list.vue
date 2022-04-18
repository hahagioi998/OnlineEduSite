<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;" />

    <el-tree
      ref="tree2"
      :data="tree"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>

<script>
import subjectApi from '@/api/edu/subject'
export default {
    data() {
        return {
        filterText: '',
        tree: [],
        defaultProps: {
            children: 'children',
            label: 'title'
            }
        }
    },
    created() {
        this.getDataTree()
    },
    watch: {
        filterText(val) {
        this.$refs.tree2.filter(val)
        }
    },
    methods: {
        filterNode(value, data) {
        if (!value) return true
        return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
        },
        getDataTree(){
            subjectApi.getSubjectTree()
            .then(response => {
                    this.tree = response.data.subjectTree
                })
            }
    }
}
</script>

