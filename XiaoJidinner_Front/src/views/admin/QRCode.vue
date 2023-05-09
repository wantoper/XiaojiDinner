<template>
  <div id="qrCode">
    <el-row>
      <span class="search-tittle">桌号搜索：</span>
      <el-col :xs="8" :sm="6" :md="6" :lg="5">
        <el-input
          placeholder="请输入桌号"
          prefix-icon="el-icon-search"
          size="small"
          v-model="search"
        >
        </el-input>
      </el-col>

      <el-button
        type="primary"
        size="small"
        class="btn-shadow"
        @click="addTableNum()"
        >添加桌号</el-button
      >
    </el-row>
    <el-row class="row-height">
      <el-col :span="24">
        <el-table
          :data="
            tableData.filter(
              (data) =>
                !search ||
                data.tableNo
                  .toString()
                  .toLowerCase()
                  .includes(search.toLowerCase())
            )
          "
          size="medium"
          :max-height="tableHeight"
          :default-sort="{ prop: 'tableNo', order: 'ascending' }"
          style="width: 100%"
        >
          <el-table-column
            fixed
            align="center"
            prop="tableNo"
            sortable
            label="餐桌号码"
          >
          </el-table-column>
          <el-table-column fixed align="center" prop="url" label="点单链接">
          </el-table-column>
          <el-table-column fixed align="center" label="点餐码">
            <template slot-scope="scope">
              <el-button
                primary="success"
                type="text"
                @click="qrcode(scope.row)"
                >点击查看点餐码</el-button
              >
            </template>
          </el-table-column>
          <el-table-column fixed="right" align="center" label="操作">
            <template slot-scope="scope">
              <el-button
                type="text"
                size="medium"
                @click="delTableNum(scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <TableNumDialog
      :dialog="dialog"
      :form="form"
      @update="loadData"
    ></TableNumDialog>
    <ShowQRCode ref="showQRCode" :qrcodeData="qrcodeData"></ShowQRCode>
  </div>
</template>

<script>
import TableNumDialog from "../../components/admin/TableNumDialog";
import ShowQRCode from "../../components/admin/ShowQRCode";
export default {
  name: "qrCode",
  data() {
    return {
      dialog: {
        show: false,
        title: "添加餐桌号码",
      },
      form: {
        tableNo: "",
      },
      qrcodeData: {
        title: "",
        visible: false,
      },
      tableData: [{ tableNo: 1, url: "http://192.168.43.155:8081/index/1" }],
      tableHeight: window.innerHeight - 142,
      search: "",
    };
  },
  methods: {
    loadData() {
      this.$axios
        .get("/api/admin/table/getall")
        .then((res) => {
          this.tableData = res.data.data;
        })
        .catch((err) => console.log(err));
    },
    addTableNum() {
      this.dialog = {
        show: true,
        title: "添加餐桌号码",
      };
      this.form = {
        tableNo: "",
      };
    },
    delTableNum(row) {
      this.$confirm(`确认删除桌号 “${row.tableNo}” 吗？`)
        .then((_) => {
          this.$axios
            .post("/api/admin/table/del", { tableNo: row.tableNo })
            .then((result) => {
              this.$message({
                type: "success",
                message: result.data.data,
              });
              this.loadData();
            })
            .catch((err) => {
              console.log(err);
            });
        })
        .catch((_) => {});
    },
    qrcode(row) {
      this.qrcodeData = {
        title: row.tableNo,
        visible: true,
      };
      this.$refs.showQRCode.drawQRCode(row.url);
    },
  },
  created() {
    this.loadData();
    window.onresize = () => {
      this.tableHeight = window.innerHeight - 142;
    };
  },
  components: {
    TableNumDialog,
    ShowQRCode,
  },
};
</script>

<style scoped>
#qrCode {
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
</style>
