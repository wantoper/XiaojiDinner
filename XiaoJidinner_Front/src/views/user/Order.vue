<template>
  <div id="body">
    <div class="header">
      <div class="back" @click="back">
        <i class="el-icon-arrow-left"></i>
      </div>
      <div class="title">订单详情</div>
    </div>

    <div id="truebody">
      <!--取餐码-->
      <div id="numberbar" style="text-align: left">
        <h1 id="odernumber">{{ orderNumber }}</h1>
        <van-badge :v-show="1" content="取餐码" id="numberindex" />
      </div>

      <!--订单状态-->
      <div id="step">
        <van-steps :active="currentSwipeItem" id="orderstep">
          <van-step v-for="(item, idx) in 4" :key="idx">
            <template v-slot:active-icon>
              <!-- 自定义激活时的样式 -->
              <div class="step-item">
                <div class="steps-active-icon">
                  <!-- 如果有icon或者图片可以更换放在这里，我的比较简单，我直接css画个圆 -->
                  <van-icon
                    :name="`${stepicon[idx]}`"
                    size="40px"
                    style="position: absolute; left: -13px; top: -48px"
                    color="#ffcd56"
                  />
                </div>
                <!-- 一定要和激活状态写在一起，不然文字就去进度条上方了，不要文字这个可以不要 -->
                <span :class="currentSwipeItem === idx ? 'title-active' : ''">
                  {{ step[idx] }}
                </span>
              </div>
            </template>
            <template v-slot:inactive-icon>
              <!-- 自定义未激活时的样式 由于版本问题，目前我只有slot两种，vant版本v2.12.7以上可以直接slot三种-->
              <div class="step-item">
                <!-- 未激活的样式 -->
                <div v-if="currentSwipeItem > idx" class="steps-inactive-icon">
                  <!-- 如果有icon或者图片可以更换放在这里，我的比较简单，我直接css画个圆 -->
                  <van-icon
                    :name="`${stepicon[idx]}`"
                    size="30px"
                    style="position: absolute; left: -9px; top: -43px"
                  />
                </div>
                <!-- 激活并且完成的样式 -->
                <div v-else class="steps-finish-icon">
                  <!-- 如果有icon或者图片可以更换放在这里，我的比较简单，我直接css画个圆 -->
                  <van-icon
                    :name="`${stepicon[idx]}`"
                    size="30px"
                    style="position: absolute; left: -9px; top: -43px"
                  />
                </div>
                <!-- 一定要和激活状态写在一起，不然文字就去进度条上方了，不要文字这个可以不要 -->
                <span
                  :class="
                    currentSwipeItem > idx ? 'title-inactive' : 'title-finish'
                  "
                >
                  {{ step[idx] }}
                </span>
              </div>
            </template>
          </van-step>
        </van-steps>
      </div>

      <!--订单详情-->
      <div id="order-detail-payed" v-for="(dish, index) in orderDetailList">
        <van-card
          :num="`${dish.count}`"
          :price="(dish.price / dish.count).toFixed(2)"
          :desc="`${dish.description}`"
          :title="`${dish.name}`"
          :thumb="'/api/download?name=' + dish.image"
          style="background-color: #ffffff; text-align: left"
        />
        <van-divider dashed></van-divider>
      </div>
      <div id="order-detail-payed-total" style="text-align: right">
        <p>
          实付 ￥ <strong>{{ amount }}</strong>
        </p>
      </div>

      <!--订单信息-->
      <div id="order-detail-message">
        <h5 style="text-align: left">订单信息</h5>
        <van-cell-group>
          <van-cell
            style="font-size: 13.2px"
            title="订单编号"
            :value="`${orderId}`"
          />
          <van-cell title="下单时间" :value="`${orderTime}`" />
          <van-cell title="取餐码" :value="`${orderNumber}`" />
          <van-cell title="订单金额" :value="`￥${amount}`" />
        </van-cell-group>
      </div>
    </div>

    <br />
    <div id="orderfooter">
      <van-button
        plain
        hairline
        round
        color="#000000"
        class="footer-button"
        size="formal"
        @click="finish"
        >我已取餐
      </van-button>
    </div>
  </div>
</template>

<script>
import { Icon, Step, Steps, Card } from "vant";

export default {
  name: "Order",
  components: {
    Icon,
    Step,
    Steps,
    Card,
  },
  data() {
    return {
      orderId: "",
      orderNumber: "2000",
      currentSwipeItem: 0,
      step: ["确认中", "配餐中", "待取餐", "已完成"],
      stepicon: ["todo-list-o", "fire-o", "bulb-o", "passed"],
      orderTime: "2022-23-18",
      amount: 0,
      orderDetailList: [],
    };
  },

  methods: {
    //初始化页面，获取订单数据
    getOrderDetial() {
      this.$axios
        .get("/api/user/order/Details/" + this.$route.query.orderID)
        .then((res) => {
          if (res.data.code == 1) {
            this.orderId = res.data.data.order.id;
            this.orderNumber = res.data.data.order.code;
            this.currentSwipeItem = res.data.data.order.status;
            this.orderDetailList = res.data.data.dishs;

            this.amount = res.data.data.order.amount;
            var time = String(res.data.data.order.createTime);
            this.orderTime = time.substring(0, 19);
          } else {
            this.$message({
              type: "error",
              message: res.data.msg,
            });
            this.$router.push("/user");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },

    // 点击 已取餐
    finish() {
      if (this.currentSwipeItem < 2) {
        this.$toast.fail("厨师还没做好\n请耐心等待");
        return;
      }
      this.$api({
        url: "/orderinfo/updatestatus",
        method: "post",
        params: {
          orderID: sessionStorage.getItem("orderID1"),
          status: 3,
        },
      })
        .then((res) => {
          if (res.code === 20031) {
          } else {
            console.log(error);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
      this.currentSwipeItem = 3;
    },
    back() {
      console.log("返回订单");
      this.$router.replace("/user");
    },
  },
  mounted() {
    this.getOrderDetial();
  },
};
</script>

<style lang="scss" scoped>
#orderstep {
  margin: 60px auto;
}

.header {
  background: rgb(0, 160, 220);
  height: 45px;
  width: 100%;
  position: relative;
  overflow: hidden;
  color: #fff;
}
.header > .back {
  position: absolute;
  top: 2px;
  left: 0;
}
.header > .back > .el-icon-arrow-left {
  display: block;
  padding: 10px;
  font-size: 20px;
  color: #fff;
}
.header > .title {
  font-size: 20px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.footer-button {
  margin-left: 10px;
  margin-right: 10px;
  margin-top: 3px;
  width: 100px;
}

// footer
#orderfooter {
  text-align: right;
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 55px;
  background-color: #f5f5f5;
  border-top: 1px solid #e5e5e5;
  padding-bottom: 15px;
}

//这个是取餐码
#numberindex {
  position: absolute;
  top: 0px;
  left: 120px;
  height: 20px;
  width: 50px;
  font-size: 13px;
  color: #ffffff;
  text-align: center;
}

//取餐号数字
#odernumber {
  position: relative;
  top: 0px;
  margin-bottom: -5px;
  font-size: 30px;
  color: #000000;
  font-weight: bold;
}

//取餐吗部分
#numberbar {
  margin-left: 180px;
  height: 30px;
  z-index: 100;
  text-align: center;
  line-height: 20px;
  font-size: 20px;
  color: #0d0b0b;
}

//出去头部的高度和底部的高度
#truebody {
  width: 88%;
  height: 100%;
  position: relative;
  margin-top: 50px;
  margin-bottom: 50px;
}

//实付金额
#order-detail-payed-total {
  width: 95%;
  position: static;
  margin-top: -20px;
  margin-left: 10px;
  margin-right: 10px;
  padding-right: 20px;
  padding-left: 20px;
  padding-top: 15px;
  padding-bottom: 10px;
  background: #ffffff;
  border-radius: 10px;
}

//订单信息
#order-detail-message {
  width: 95%;
  position: static;
  margin-top: 20px;
  margin-left: 10px;
  margin-right: 10px;
  margin-bottom: 20px;
  padding-right: 20px;
  padding-left: 20px;
  padding-top: 15px;
  background: #ffffff;
  border-radius: 10px;
}

#order-detail-payed {
  width: 95%;
  position: static;
  margin-top: -20px;
  margin-left: 10px;
  margin-right: 10px;
  padding-right: 20px;
  padding-left: 20px;
  padding-top: 15px;
  background: #ffffff;
  border-radius: 10px;
}

.van-steps {
  background: transparent; // 如果背景是白色就不需要了，默认是白色的
  width: 80%;
  margin: 80px auto;
  overflow: visible; //如果不需要文字，或者不需要调整位置，可以不设置

  .step-item {
    position: relative;

    span {
      position: absolute;
      top: 20px;
      left: -20px;
      width: 50px;
      z-index: 1;
      font-size: 13px;
      color: #999999;
    }

    .title-active {
      // 自定义文字激活
      font-weight: bold;
      color: #0a0101;
      left: -20px;
      width: 50px;
      font-size: 13px;
    }

    .title-inactive {
      // 自定义文字激活并且完成
      color: #999999;
      left: -20px;
      width: 50px;
      font-size: 13px;
    }

    .title-finish {
      //自定义未激活
      color: #999999;
      //left: -20px;
      //width: 50px;
      //font-size: 13px
    }
  }

  .steps-active-icon {
    width: 14px;
    height: 14px;
    box-sizing: border-box;
    background: #f5d30f;
    border: 3px solid #ffc4c4;
    border-radius: 50%;
  }

  .steps-inactive-icon,
  .steps-finish-icon {
    width: 12px;
    height: 12px;
    box-sizing: border-box;
    background: #ffffff;
    border-radius: 50%;
  }

  .steps-inactive-icon {
    border: none;
  }

  .steps-finish-icon {
    border: 3px solid #c8c8c8;
  }

  ::v-deep .van-step__circle-container {
    background-color: transparent; // 如果背景是白色就不需要了，默认是白色的
  }

  ::v-deep .van-step--horizontal .van-step__line {
    height: 4px; // 自定义调整进度条的粗细
    top: 28px; // 自定义调整进度条的位置
    background-color: #e4e4e4;
  }

  ::v-deep .van-step--finish .van-step__line {
    background-color: #fdcc56; //自定义激活时进度条的颜色
    border: none;
  }
}

#backtitle {
  position: fixed;
  top: 0;
  width: 100%;
  height: 50px;
  background-color: #f5f5f5;
  border-top: 1px solid #e5e5e5;
}

#body {
  background-color: #f5f5f5;
}

#step {
  margin-top: -20px;
  margin-left: 35px;
  position: relative;
  left: 0;
}
</style>
