<template>
  <div id="cmmodity">
    <el-row>
      <span class="search-tittle">商品搜索：</span>
      <el-col
        :xs="8"
        :sm="6"
        :md="6"
        :lg="5"
      >
        <el-input
          placeholder="请输入商品名称"
          size="small"
          prefix-icon="el-icon-search"
          @input="loadData"
          v-model="search"
          clearable
        >
        </el-input>
      </el-col>

      <el-button
        type="primary"
        size="small"
        class="btn-shadow"
        @click="addCommodity()"
      >添加商品</el-button>
    </el-row>
    <el-row class="row-height">
      <el-col :span="24">
        <el-table
          :data="tableData"
          size="medium"
          :max-height="tableHeight"
          style="width: 100%"
        >
          <el-table-column
            align="center"
            width="100"
            label="商品图片"
          >
            <template slot-scope="scope">
              <img
                :src="'/api/download?name='+scope.row.image"
                width="80px"
                height="80px"
                alt=""
              >
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="name"
            width="150"
            label="商品名称"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="description"
            label="商品描述"
          ></el-table-column>
          <el-table-column
            align="center"
            width="120"
            label="商品分类"
          >
            <template slot-scope="scope">
              {{ 
                categoryList.filter((x)=>x.id==scope.row.categoryId)[0].name
              }}
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="price"
            width="100"
            label="商品价格"
          ></el-table-column>
          <el-table-column
            align="center"
            width="100"
            label="售卖状态"
          >
            <template slot-scope="scope">
              {{ scope.row.status?'是':'否' }}
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            align="center"
            width="120"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button
                type="text"
                size="small"
                @click="editCommodity(scope.row)"
              >编辑</el-button>
              <el-button
                type="text"
                size="small"
                @click="delCommodity(scope.row)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-pagination
          class="pagination"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="paginations.page_index"
          :page-sizes="paginations.page_sizes"
          :page-size="paginations.page_size"
          :layout="paginations.layout"
          :total="paginations.total"
        >
        </el-pagination>
      </el-col>
    </el-row>

    <CommodityDialog
      :dialog="dialog"
      :categoryList="categoryList"
      :form="form"
      @update="loadData"
    ></CommodityDialog>
  </div>
</template>

<script>
import CommodityDialog from '@/components/admin/CommodityDialog'
export default {
  name: 'cmmodity',
  data () {
    return {
      dialog: {
        show: false,
        title: '',
        option: 'add'
      },
      form: {
        image: '',
        name: '',
        description: '',
        categoryId: '',
        price: 0,
        status: 1
      },
      tableData: [
        
      ],
      tableHeight: window.innerHeight - 194,
      categoryList: [],
      search: '',
      suggests: [],
      paginations: {
        page_index: 1,
        total: 0,
        page_size: 5,
        page_sizes: [5, 10, 15, 20],
        layout: 'total, sizes, prev, pager, next, jumper'
      }
    }
  },
  methods: {
    handleSizeChange (val) {
      this.paginations.page_size = val
      this.loadData()
    },
    handleCurrentChange (val) {
      this.loadData()
    },
    loadData () {
      const page = {
        index: this.paginations.page_index,
        size: this.paginations.page_size,
        name: this.search
      }
      this.$axios.post('/api/admin/dish/getall', page)
        .then(res => {
          this.tableData = res.data.data.data
          this.paginations.total = res.data.data.total
        })
        .catch(err => console.log(err))
    },
    addCommodity () {
      this.dialog = {
        show: true,
        title: '添加商品',
        option: 'add'
      }
      this.form = {
        image: '',
        name: '',
        description: '',
        categoryId: '',
        price: 0,
        status: 1
      }
    },
    editCommodity (row) {
      this.dialog = {
        show: true,
        title: '编辑商品',
        option: 'edit'
      }
      this.form = row
    },
    delCommodity (row) {
      this.$confirm(`确认删除商品 “${row.name}” 吗？`)
        .then(_ => {
          this.$axios.post('/api/admin/dish/del', { id: row.id })
            .then(result => {
              if(result.data.code === 1){
                this.$message({
                  type: 'success',
                  message: result.data.data
                })
                this.loadData()
              }else{
                this.$message({
                  type: 'error',
                  message: result.data.msg
                })
                this.loadData()
              }
            })
            .catch(err => {
              console.log(err)
            })
        })
        .catch(_ => { })
    }
  },
  created () {
    this.loadData()
    this.$axios.get('/api/admin/category/getall')
      .then(res => {
        this.categoryList = res.data.data
      })
      .catch(err => console.log(err))
    window.onresize = () => {
      this.tableHeight = window.innerHeight - 194
    }
  },
  components: {
    CommodityDialog
  }
}
</script>
<style scoped>
#cmmodity {
  height: 100%;
}
.row-height {
  margin-top: 10px;
  overflow: auto;
}
.btn-shadow {
  float: right;
  top: 0;
  box-shadow: 0px 0px 20px rgba(53, 141, 230, 0.562);
}
.warn-shadow {
  box-shadow: 0px 0px 20px rgba(255, 255, 220, 0.3);
}
.box-card {
  box-shadow: 1px 1px 15px rgba(58, 59, 69, 0.3) !important;
  border-radius: 6px;
}
.search-tittle {
  display: inline-block;
  float: left;
  height: 32px;
  line-height: 32px;
  color: #111;
}
.pagination {
  margin-top: 10px;
  float: right;
}
</style>
