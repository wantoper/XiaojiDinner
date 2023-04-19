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
        <h1 id="odernumber">{{ orderNumber}}</h1>
        <van-badge content="取餐码" id="numberindex"/>
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
                  <van-icon :name="`${stepicon[idx]}`" size="40px" style="position: absolute;left: -13px;top: -48px"
                            color="#ffcd56"/>
                </div>
                <!-- 一定要和激活状态写在一起，不然文字就去进度条上方了，不要文字这个可以不要 -->
                <span :class="currentSwipeItem === idx?'title-active':''">
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
                  <van-icon :name="`${stepicon[idx]}`" size="30px" style="position: absolute;left: -9px;top: -43px;"/>
                </div>
                <!-- 激活并且完成的样式 -->
                <div v-else class="steps-finish-icon">
                  <!-- 如果有icon或者图片可以更换放在这里，我的比较简单，我直接css画个圆 -->
                  <van-icon :name="`${stepicon[idx]}`" size="30px" style="position: absolute;left: -9px;top: -43px;"/>
                </div>
                <!-- 一定要和激活状态写在一起，不然文字就去进度条上方了，不要文字这个可以不要 -->
                <span :class="currentSwipeItem > idx?'title-inactive':'title-finish'">
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
            :num="`${dish.number}`"
            :price="`${dish.dishAmount/dish.number}`"
            :desc="`${dish.description}`"
            :title="`${dish.dishName}`"
            :thumb="`${dish.dishPhoto}`"
            style="background-color: #ffffff; text-align: left;"
        />
        <van-divider dashed></van-divider>
      </div>
      <div id="order-detail-payed-total" style="text-align: right">
        <p>实付 ￥ <strong>{{ totalAmount }}</strong></p>
      </div>

      <!--订单信息-->
      <div id="order-detail-message">
        <h5 style="text-align: left; ">订单信息</h5>
        <van-cell-group>
          <van-cell title="订单编号" :value="`${orderId}`"/>
          <van-cell title="下单时间" :value="`${orderTime}`"/>
          <van-cell title="取餐码" :value="`${orderNumber}`"/>
          <van-cell title="订单金额" :value="`￥${totalAmount}`"/>
        </van-cell-group>
      </div>
    </div>

    <br/>
    <div id="orderfooter">
      <van-button plain hairline round color="#000000" class="footer-button" size="formal" @click="finish">我已取餐
      </van-button>
    </div>


  </div>
</template>

<script>

import {Icon, Step, Steps, Card} from 'vant';

export default {
  name: "Order",
  components: {
    Icon,
    Step,
    Steps,
    Card
  },
  data() {
    return {


      //取餐号
      orderNumber:"2000",
      // 步骤条
      currentSwipeItem: 1,
      step: [
        "确认中",
        "配餐中",
        "待取餐",
        "已完成"
      ],

      stepicon: [
        "todo-list-o",
        "fire-o",
        "bulb-o",
        "passed"
      ],

      //订单信息
      orderId: "", //有可能是哈希后的，uuid
      orderTime: "2022-23-18",
      totalAmount: 0,
      //订单菜品列表
      orderDetailList: [
        {
          dishName: "汉堡",
          description: "好吃的汉堡",
          number: 3,
          dishAmount: 39.0,
          dishPhoto: "https://i.postimg.cc/NFpkQDCW/image.png"
        },
        {
          dishName: "汉堡",
          description: "好吃的汉堡",
          number: 1,
          dishAmount: 15.0,
          dishPhoto: "https://i.postimg.cc/NFpkQDCW/image.png"
        },
        {
          dishName: "汉堡",
          description: "好吃的汉堡",
          number: 2,
          dishAmount: 39.0,
          dishPhoto: "https://i.postimg.cc/NFpkQDCW/image.png"
        },
        {
          dishName: "汉堡",
          description: "好吃的汉堡",
          number: 3,
          dishAmount: 39.0,
          dishPhoto: "https://i.postimg.cc/NFpkQDCW/image.png"
        },
        {
          dishName: "汉堡",
          description: "好吃的汉堡",
          number: 3,
          dishAmount: 39.0,
          dishPhoto: "https://i.postimg.cc/NFpkQDCW/image.png"
        }

      ],
    }
  },

  methods: {

    //初始化页面，获取订单数据
    getOrderDetial(){

      //判断是从订单列表页进来的还是从支付成功页进来的
      if (sessionStorage.getItem('orderID') == null) {
        var url = '/orderinfo/orderdetail/'+this.$route.query.orderID
        sessionStorage.setItem('orderID1',this.$route.query.orderID)
        console.log("从历史订单页进来的")
      }else{
        var url = '/orderinfo/orderdetail/'+sessionStorage.getItem('orderID')
        sessionStorage.setItem('orderID1',sessionStorage.getItem('orderID'))
        console.log("从支付成功页进来的")
      }


      //先请求后端数据，接口文档51,也不一定是
      this.$api({
        url:url,
        method:'get',
      }).then(res=>{
        sessionStorage.removeItem('orderID')
        console.log(res)
        //获取订单id取后四位数，不够前边补0
        let orderId=res.data.orderId
        let orderIdStr=orderId.toString()
        let orderIdStrLength=orderIdStr.length
        if(orderIdStrLength<4){
          for(let i=0;i<4-orderIdStrLength;i++){
            orderIdStr="0"+orderIdStr
          }
        }else {
          orderIdStr=orderIdStr.substring(orderIdStrLength-4,orderIdStrLength)
        }

        // 拼接成取餐码, 年最后一位+月日+订单id，订单id变成4位数，不够前边补零
        //this.orderNumber=res.data.createdTime.substring(3,10).replace(/-/g,"")+orderIdStr
        this.orderNumber = orderId
        console.log(this.orderNumber)

        //获取订单状态
        this.currentSwipeItem=res.data.status

        //获取订单详情
        this.orderDetailList=res.data.orderDetailList2

        //获取订单信息
        console.log(res)
        this.orderId=res.data.uuid.substring(0,19) //orderId,存uuid吧

        this.orderTime=res.data.createdTime
        this.totalAmount=res.data.totalAmount

      })



    },

    // 点击 已取餐
    finish() {
      if(this.currentSwipeItem<2){
        this.$toast.fail('厨师还没做好\n请耐心等待');
        return;
      }
      this.$api({
        url: '/orderinfo/updatestatus',
        method: 'post',
        params: {
          orderID: sessionStorage.getItem('orderID1'),
          status: 3
        }
      }).then(res => {
        if(res.code===20031){
        }
        else {
          console.log(error);
        }
      }).catch(function (error){
        console.log(error);
      });
      this.currentSwipeItem = 3;
    },
    //


    //获取订单菜品列表
    // getOrderDishes(){
    //   // 通过后端API获取菜品列表(orderinfo/{status}/{number}) 这里是模拟数据，后期要改，
    //   this.$api({
    //     url: 'orderinfo/0/3',
    //     method: 'get'
    //   }).then(res => {
    //     console.log(res)
    //     this.orderDishList = res.data[0].orderDetailList;
    //     console.log(this.orderDishList);
    //   }).catch(function (error) {
    //     console.log(error);
    //   });
    // },
    //返回上一层，没完全实现
    back() {
      console.log("返回订单")
      this.$router.replace('/user');
    }
  }
  ,
  mounted() {
    this.getOrderDetial()
    // this.getOrderDishes();

    let _this = this// 声明一个变量指向Vue实例this，保证作用域一致
    this.timer = setInterval(() => {
      _this.getOrderDetial();
    }, 5000)

  }
}
</script>

<style lang="scss" scoped>

#orderstep {
  margin: 60px auto
}

.header{
    background:rgb(0,160,220);
    height: 45px;
    width: 100%;
    position: relative;
    overflow: hidden;
    color: #fff;
}
.header > .back{
    position: absolute;
    top: 2px;
    left: 0;
}
.header > .back > .el-icon-arrow-left{
    display: block;
    padding: 10px;
    font-size: 20px;
    color: #fff;
}
.header > .title{
    font-size: 20px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
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
  color: #FFFFFF;
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
      font-size: 13px
    }

    .title-inactive {
      // 自定义文字激活并且完成
      color: #999999;
      left: -20px;
      width: 50px;
      font-size: 13px
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