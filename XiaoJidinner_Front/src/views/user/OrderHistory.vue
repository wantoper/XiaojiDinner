<template>
  <div>
    <div v-if="orders.length > 0" id="orderMenu">
      <div>历史订单</div>
      <!--历史订单-->
      <div
        class="historyOrderCard"
        v-for="order in orders"
        :key="order.orderinfo.id"
        @click="selectOrder(order.orderinfo.id)"
      >
        <!--如果订单是没完成的，显示取餐码-->
        <div>
          <van-cell
            icon="shop-o"
            :value="step[order.orderinfo.status]"
            is-link
            size="large"
            title-style="font-size: 23px;"
            value-class="step"
          >
            <template #title v-if="order.orderinfo.status < 3">
              <van-tag type="danger" style="top: -4px"
                >取餐码 {{ order.orderinfo.code }}</van-tag
              >
            </template>
            <template #title v-else>
              <span class="custom-title" style="font-size: 18px">{{
                order.orderinfo.createTime
              }}</span>
            </template>
          </van-cell>

          <van-cell center value-class="totalAmount">
            <template #title>
              <van-image
                v-for="item in order.orderDetaillist"
                :key="item.id"
                :src="'/api/download?name=' + item.image"
                height="60px"
                fit="cover"
                style="
                  margin-right: 10px;
                  margin-top: 10px;
                  margin-bottom: 10px;
                "
              />
            </template>
            <span class="custom-price" style="margin-right: 10px"
              >¥{{ order.orderinfo.amount }}</span
            >
          </van-cell>
        </div>
      </div>
    </div>

    <div v-else>
      还没有订单记录哦~
      <br />
      快去下单吧！
    </div>
  </div>
</template>

<script>
export default {
  name: "OrderHistory",
  data() {
    return {
      step: ["确认中", "配餐中", "待取餐", "已完成", "已取消"],
      orders: [],
    };
  },
  methods: {
    //点击跳转到订单详情页面
    selectOrder(orderID) {
      this.$router.push({ path: "/user/order", query: { orderID: orderID } });
    },

    //加载历史订单
    loadOrderHistory() {
      this.$axios
        .get("/api/user/order/myorders")
        .then((res) => {
          if (res.data.code == 1) {
            this.orders = res.data.data.reverse();
          } else {
            this.$message({
              type: "error",
              message: res.data.msg,
            });
            // this.$router.push("/user");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  mounted() {
    this.loadOrderHistory();
  },
};
</script>

<style scoped>
/*背景图片*/
/* #orderMenu{
  background: url("~@/assets/homebg.jpg") no-repeat;
} */

/*历史订单栏*/
.custom-title {
  margin-right: 4px;
  vertical-align: middle;
}
/*订单图片那部分设置宽一点,右边的部分少一点*/
.van-cell__value {
  max-width: 20%;
}

/*请取餐样式*/
.step {
  color: #000000;
}

/*价格样式*/
.totalAmount {
  color: rgba(0, 0, 0, 0.98);
  font-size: 15px;
}

/*每个历史订单card的样式*/
.historyOrderCard {
  border-radius: 10px;
  background-color: #ffffff;
  margin: 16px 16px 0 16px;
}

.van-cell {
  /*background-color: #f7e6e6;*/
  //border-radius: 10px;
}

/*加deep 改 vant样式*/
/deep/.van-cell--large {
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  box-shadow: 5px 0px 10px rgba(0, 0, 0, 0.1);
}
/deep/.van-cell--center {
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  /*再card下边加个阴影*/
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
}

.van-cell__title {
  text-align: left !important;
}
.search-icon {
  font-size: 16px;
  line-height: inherit;
}
</style>
