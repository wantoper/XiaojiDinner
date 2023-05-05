<template>
  <div class="order">
    <el-row>
      <el-col :span="24">
        <el-table
          :data="tableData"
          :max-height="tableHeight"
          style="width: 100%"
        >
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="订单编号">
                  <span>{{ props.row.id }}</span>
                </el-form-item>
                <el-form-item label="订单桌号">
                  <span>{{ props.row.tableNo }}</span>
                </el-form-item>
                <el-form-item label="订单内容">
                  <ul>
                    <li v-for="item in props.row.orderDetails" :key="item._id">
                      <span>{{ item.name }}</span
                      ><span>x{{ item.count }}</span
                      ><span>￥{{ item.price }}</span>
                    </li>
                  </ul>
                </el-form-item>
                <el-form-item label="订单备注">
                  <span>{{
                    props.row.remark === "" ? "无" : props.row.remark
                  }}</span>
                </el-form-item>
                <el-form-item label="订单金额">
                  <span class="totalPrice">￥{{ props.row.amount }}</span>
                </el-form-item>
                <el-form-item label="用户名">
                  <span>{{ props.row.orderuser.name }}</span>
                </el-form-item>
                <el-form-item label="联系方式">
                  <span>{{ props.row.orderuser.phone }}</span>
                </el-form-item>
                <el-form-item label="下单时间">
                  <span>{{ props.row.createTime }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column
            label="订单桌号"
            width="80"
            align="center"
            prop="tableNo"
          >
          </el-table-column>
          <el-table-column align="center" label="订单内容">
            <template slot-scope="scope">
              <span class="ellipsis">{{
                scope.row.orderDetails | orderList
              }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="订单备注">
            <template slot-scope="scope">
              <span class="ellipsis">{{
                scope.row.remark === "" ? "无" : scope.row.remark
              }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" width="100" label="订单金额">
            <template slot-scope="scope">
              <span class="totalPrice">￥{{ scope.row.amount }}</span>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            fixed="right"
            width="80"
            label="订单状态"
          >
            <template slot-scope="scope">
              <el-tag
                :type="
                  scope.row.status === 0
                    ? 'danger'
                    : scope.row.status === 1
                    ? 'warning'
                    : 'success'
                "
                disable-transitions
              >
                {{ scope.row.status | orderstatus }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            fixed="right"
            width="160"
            label="订单操作"
          >
            <template slot-scope="scope">
              <el-button
                v-if="scope.row.status == 0"
                size="mini"
                type="success"
                @click="changestatus(scope.row.id, 1)"
                >确认</el-button
              >

              <el-button
                v-if="scope.row.status == 1"
                size="mini"
                type="success"
                @click="changestatus(scope.row.id, 2)"
                >已备餐</el-button
              >

              <el-button
                v-if="scope.row.status == 2"
                size="mini"
                type="success"
                @click="changestatus(scope.row.id, 3)"
                >完成</el-button
              >

              &nbsp;
              <el-popconfirm
                title="确定要取消当前订单吗？"
                @confirm="changestatus(scope.row.id, 4)"
              >
                <el-button slot="reference" size="mini" type="danger"
                  >取消</el-button
                >
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "order",
  data() {
    return {
      tableData: [],
      tableHeight: window.innerHeight - 105,
    };
  },
  methods: {
    changestatus(id, status) {
      this.$axios
        .post("/api/admin/order/changestatus", { id: id, status: status })
        .then((res) => {
          this.$message({
            type: "success",
            message: res.data.data,
          });
          this.initTableData();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    initTableData() {
      this.$axios
        .get("/api/admin/order/getallunfinish")
        .then((res) => {
          this.tableData = res.data.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  mounted() {
    this.initTableData();
    window.onresize = () => {
      this.tableHeight = window.innerHeight - 105;
    };
  },
  sockets: {
    checkOrder() {
      this.initTableData();
    },
  },
  filters: {
    orderList(order) {
      let orderData = "";
      order.forEach((el) => {
        orderData += `${el.name}x${el.count} `;
      });
      return orderData;
    },
    orderstatus(status) {
      if (status === 0) {
        return "待确认";
      } else if (status === 1) {
        return "备餐中";
      } else {
        return "待取餐";
      }
    },
  },
};
</script>

<style>
.order {
  height: 100%;
}
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 100%;
}
.demo-table-expand .el-form-item .el-form-item__content ul {
  width: 300px;
}
.demo-table-expand .el-form-item .el-form-item__content ul li {
  display: flex;
}
.demo-table-expand .el-form-item .el-form-item__content ul li span {
  flex: 1;
}
.demo-table-expand .el-form-item .el-form-item__content ul li span:first-child {
  flex: 4;
}
.totalPrice {
  color: red;
}
.ellipsis {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
