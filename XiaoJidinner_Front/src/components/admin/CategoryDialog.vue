<template>
    <div id="category-dialog">
        <el-dialog :title="dialog.title" :visible.sync="dialog.show" width="30%">
        <el-form ref="form" :model="form" :rules="form_rules">
            <el-form-item label="分类名称" prop="name" :label-width="formLabelWidth">
                <el-input v-model="form.name" autocomplete="off" placeholder="请输入分类名称！"></el-input>
            </el-form-item>
            <el-form-item label="分类排序" prop="sort" :label-width="formLabelWidth">
                <el-input type="number" min=0 max=99 v-model.number="form.sort" autocomplete="off" placeholder="请输入分类排序！"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button class="cancel-shadow" @click="dialog.show = false">取 消</el-button>
            <el-button type="primary" class="btn-shadow" @click="submitForm('form')">确 定</el-button>
        </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
  name: 'categoryDialog',
  props: { dialog: Object, form: Object },
  data () {
    const checkcNo = (rule, value, callback) => {
      if (value < 0 || value > 99) {
        callback(new Error('排序号码为0-99之间的整数！'))
      } else {
        callback()
      }
    }
    return {
      formLabelWidth: '80px',
      form_rules: {
        name: [
          { required: true, message: '分类名称不能为空！', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '分类排序不能为空', trigger: 'blur' },
          { type: 'number', message: '排序号码必须为数值' },
          { validator: checkcNo, message: '排序号码为0-99之间的整数！', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          const url = this.dialog.option === 'add' ? '/add' : '/edit'
          this.$axios.post('/api/admin/category' + url, this.form)
            .then(res => {
              if(res.data.code == 1){
                this.$message({
                type: 'success',
                message: res.data.data
                })
                this.dialog.show = false
                this.$emit('update')
              }else{
                this.$message({
                type: 'error',
                message: "添加失败！"
                })
              }
            })
        }
      })
    }
  },
  mounted () {
  }
}
</script>

<style scoped>
.cancel-shadow{
    box-shadow: 0px 0px 20px rgba(85, 86, 99, 0.262);
}
.btn-shadow{
    box-shadow: 0px 0px 20px rgba(53, 141, 230, 0.562);
}
</style>
