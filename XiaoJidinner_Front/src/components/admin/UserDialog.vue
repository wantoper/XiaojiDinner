<template>
  <div id="category-dialog">
    <el-dialog :title="dialog.title" :visible.sync="dialog.show" width="30%">
      <el-form ref="form" :model="form" :rules="form_rules">
        <el-form-item label="会员头像" :label-width="formLabelWidth">
          <el-upload
            class="commodity-uploader"
            action=""
            :show-file-list="false"
            :http-request="uploadSectionFile"
            :before-upload="beforeImgUpload"
          >
            <img
              :src="
                form.avatar == null || form.avatar == ''
                  ? require('../../assets/picture/toorderl.png')
                  : '/api/download?name=' + form.avatar
              "
              class="commodity"
            />
            <!-- <i v-else class="el-icon-plus commodity-uploader-icon"></i> -->
            <div slot="tip" class="el-upload__tip">
              (只能上传jpg文件，且不超过2MB)
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item
          label="会员名称"
          prop="name"
          :label-width="formLabelWidth"
        >
          <el-input
            v-model="form.name"
            autocomplete="off"
            placeholder="请输入会员名称"
          ></el-input>
        </el-form-item>

        <el-form-item label="手机号" prop="phone" :label-width="formLabelWidth">
          <el-input
            v-model="form.phone"
            autocomplete="off"
            placeholder="请输入手机号"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="密码"
          prop="password"
          :label-width="formLabelWidth"
        >
          <el-input
            v-model="form.password"
            autocomplete="off"
            placeholder="请输入密码"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="会员积分"
          prop="rank"
          :label-width="formLabelWidth"
        >
          <el-input
            v-model="form.rank"
            autocomplete="off"
            placeholder="请输入会员积分"
          ></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex" :label-width="formLabelWidth">
          <el-select v-model="form.sex" placeholder="请选择">
            <el-option value="男"></el-option>
            <el-option value="女"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="启用状态" :label-width="formLabelWidth">
          <el-switch
            v-model="form.status"
            active-color="#13ce66"
            inactive-color="#ff4949"
            :active-value="1"
            :inactive-value="0"
          >
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button class="cancel-shadow" @click="dialog.show = false"
          >取 消</el-button
        >
        <el-button type="primary" class="btn-shadow" @click="submitForm('form')"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "UserDialog",
  props: { dialog: Object, form: Object },
  data() {
    return {
      uploadUrl: "/api/upload",
      formLabelWidth: "80px",
      form_rules: {
        name: [
          { required: true, message: "会员名称不能为空！", trigger: "blur" },
        ],
        phone: [{ required: true, message: "手机号不能为空", trigger: "blur" }],
        sex: [{ required: true, message: "性别不能为空", trigger: "blur" }],
        rank: [{ required: true, message: "积分不能为空", trigger: "blur" }],
        status: [
          { required: true, message: "会员状态不能为空", trigger: "blur" },
        ],
        password: [
          {
            required: true,
            message: "密码不能为空",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const url = this.dialog.option === "add" ? "/add" : "/edit";
          this.$axios.post("/api/admin/user" + url, this.form).then((res) => {
            if (res.data.code === 1) {
              this.$message({
                type: "success",
                message: res.data.data,
              });
              this.dialog.show = false;
              this.$emit("update");
            } else {
              this.$message({
                type: "error",
                message: res.data.msg,
              });
            }
          });
        }
      });
    },
    beforeImgUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    uploadSectionFile(params) {
      const file = params.file;
      const imgform = new FormData();
      imgform.append("file", file);
      this.$axios
        .post("/api/upload", imgform, {
          headers: { "content-type": "multipart/form-data" },
        })
        .then((res) => {
          if (res.data.code === 1) {
            this.form.avatar = res.data.data;
            this.form.name = this.form.name;
          } else {
            this.$message({
              type: "error",
              message: "图片上传失败，请重试！",
            });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  mounted() {},
  watch: {
    dialog: {
      immediate: true,
      handler(val) {
        if (val.option == "edit") {
          this.form_rules.password[0].required = false;
        } else {
          this.form_rules.password[0].required = true;
        }
      },
    },
  },
};
</script>

<style scoped>
.commodity-uploader {
  padding: 10px;
}
.commodity-uploader .el-upload {
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.commodity-uploader .el-upload:hover {
  border-color: #409eff;
}
.commodity-uploader-icon {
  margin-top: 1px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.commodity-uploader-icon:hover {
  border-color: #409eff;
}
.commodity {
  width: 100px;
  height: 100px;
  display: block;
}
.cancel-shadow {
  box-shadow: 0px 0px 20px rgba(85, 86, 99, 0.262);
}
.btn-shadow {
  box-shadow: 0px 0px 20px rgba(53, 141, 230, 0.562);
}
</style>
