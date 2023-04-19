<template>
  <div class="personal-center-container body">
    <div class="header">
        <div class="back" @click="back">
            <i class="el-icon-arrow-left"></i>
        </div>
        <div class="title">个人中心</div>
    </div>
    <!-- <br/> -->
    <img src="../../assets/picture/userpage1.jpg" class="userpage1"/>
    <!--用户头像/昵称/手机号-->
    <div class="personal-center-profile">
      <img :src="this.user.profilePhoto" class="avatar"/>
      <div class="user-info">
        <div class="usernameandrank">
          <div class="user-name">{{ this.user.customer }}</div>
          <div class="usergrade">{{ this.rank}}</div>
        </div>
        
        <div class="user-phone">{{ this.user.phoneNumber }}</div>
      </div>
      <!--差一个会员的logo /男生女生的logo-->
      <!--        <van-icon name="arrow" />-->
      <div class="user-mny">
        <div class="jifennum">{{ this.user.accumulatedAmount }}</div>
        <div class="jifen">积分</div>
        <div class="fenge"></div>
        <div class="dingdannum">{{this.orderNumber}}</div>
        <div class="dingdan">订单</div>
      </div>
    </div>
    <div class="orders">
      <OrderHistory></OrderHistory>
      <br/>
    </div>
    <img src="../../assets/picture/userbg1.png" class="userpage4"/> 
  </div>
</template>

<script>
import OrderHistory from "./OrderHistory.vue";
export default {
  name: "UserPage",
  components:{
    OrderHistory
  },
  data() {
    return {
      user: {
        customer: '爱吃炸鸡的牛牛',
        phoneNumber: '12345678910',
        profilePhoto: 'https://alist.wantoper.top/d/%E5%9B%BE%E7%89%87/preview.jpg',
        accumulatedAmount: 200,
        customerID: 0,
      },
      orderNumber: 3,
    }
  },
  methods: {
    back(){
      this.$router.push("/index/1");;
    },
    //加载用户信息
    loadUser() {
      this.$api.get("/customer/" + sessionStorage.getItem('customerID')).then(res => {
        this.user = res.data;
        this.user.phoneNumber = res.data.phoneNumber.substring(0, 3) + "****" + res.data.phoneNumber.substring(7, 11);
        this.user.accumulatedAmount = res.data.accumulatedAmount;
        if(this.user.profilePhoto == null){
          this.user.profilePhoto = require("../../assets/picture/userpage2.jpg"); //本地的照片要用require，因为是静态资源，不是网络资源，require是webpack的语法,表示引入一个静态资源
        }
        console.log(res.data)
      });

      //用户订单总数
      this.$api.get("/orderinfo/history/ordernumber/" + sessionStorage.getItem('customerID')).then(res => {

        this.orderNumber = res.data;
        console.log(this.orderNumber)
      });
    },
  },
  computed:{
    rank(){
      if(this.user.accumulatedAmount<50) return "青铜吃货"
      if(this.user.accumulatedAmount<100) return "白银吃货"
      if(this.user.accumulatedAmount<200) return "黄金吃货"
      if(this.user.accumulatedAmount<300) return "钻石吃货"
      return "荣耀吃货"
    }
  },
  mounted() {
    // this.loadUser();
  }
}
</script>

<style lang="scss" scoped>
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

.body{
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.userpage1 {
  width: 100%;
  height: 140px;
}
.jifennum {
  position: relative;
  top: 71px;
  left: -250px;
  font-size: 25px;
}

.jifen {
  position: relative;
  top: 76px;
  left: -249px;
  color: #5d5b5b;
  font-size: 15px;
}

.fenge {
  position: relative;
  width: 2px;
  height: 40px;
  left: -150px;
  top: 33px;
  background-color: #e3e1e1;
}

.dingdannum {
  position: relative;
  left: -80px;
  font-size: 25px;
  top: -9px;
}

.dingdan {
  position: relative;
  top: -4px;
  left: -80px;
  color: #5d5b5b;
  font-size: 15px;
}

/*我的订单+我的常点*/
#ordernav {
  margin: 10px;
  border-radius: 30px;
}

#vanordernav {
  border-radius: 30px;
}

#vanordernav1 {
  border-radius: 30px;
}

.van-grid van-hairline--top {
  border-radius: 30px !important;
}

.van-grid-item {
  border-radius: 30px !important;
}

.personal-center-card-body {
  /*background-color: rgba(250,232,208,.75);*/
  /*color: #fff;*/

  border-radius: 10px;
}

.personal-center-card-header {
  font-size: 20px;
  font-weight: 500;
  margin-bottom: 10px;
}

.personal-center-card-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.personal-center-card-text {
  margin-bottom: 5px;
}

.personal-center-container {
  height: 100%;
  //background: #fcfbfb;
  background-image: linear-gradient(180deg, rgba(255,0,0,0), #fddfba, #f4eddd);
  //height: 500px;
}

.orders{
  margin-top: 15px;
  font-size: 30px;
  background-image: linear-gradient(180deg, rgba(255,0,0,0), #fddfba, #f4eddd);
}


/*用户个人信息栏*/
.personal-center-profile {
  margin-left: 10px;
  margin-right: 10px;
  border-radius: 20px;
  background: rgba(255, 255, 255, 1);
  border: 1px solid rgba(128, 128, 128, 1);
  box-shadow: 2px 4px 4px 0px rgba(0, 0, 0, 0.25);
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
  // top: 20px;
  height: 170px;
  /*background: #fff;*/
}

/*头像*/
.avatar {
  position: relative;
  top: -40px;
  left: 15px;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  margin-right: 16px;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
}

.user-info {
  flex-grow: 1;
  position: relative;
  left: 15px;
  top: -35px;
}

.user-name {
  font-size: 18px;
  line-height: 24px;
  margin-bottom: 8px;
  text-align: left;
}

.usernameandrank{
  display: flex;
}

.usergrade {
  height: 20px;
  width: 70px;
  font-size: 12px;
  background-color: #f8e68c;
  border-radius: 20px;
  line-height: 20px;
  color: #7c6a2b;
}

.user-phone {
  position: relative;
  left: 0px;
  top: -10px;
  font-size: 16px;
  line-height: 22px;
  color: #888;
  text-align: left;
}

.personal-center-divider {
  margin: 16px 0;
}

.personal-center-lists {
  margin: 10px;
  background: #fff;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
  border-radius: 30px;
}

.personal-center-cell {
  padding-left: 16px;
  font-size: 16px;
  color: #333;
}

.personal-center-cell-icon {
  font-size: 20px;
  margin-right: 8px;
  color: #ff9815;
}

.van-step__title {
  text-align: center;
  background: transparent;
}

.van-steps {
  overflow: hidden;
  background-color: rgba(255, 255, 255, 0);
  text-align: center;
}

/deep/ .van-step--horizontal .van-step__circle-container {
  position: absolute;
  top: 30px;
  left: -8px;
  z-index: 1;
  padding: 0 8px;
  background-color: rgba(255, 255, 255, 0) !important;
  -webkit-transform: translateY(-50%);
  transform: translateY(-50%);

}

.center-card-text1 {
  position: relative;
  left: -105px;
  font-size: 9px;
  color: rgb(103, 84, 47);
}

.center-card-text2 {
  position: relative;
  left: -33px;
  top: -20px;
  font-size: 14px;
  color: rgb(255, 255, 255);
}

.fenge2 {
  position: relative;
  width: 330px;
  height: 1px;
  background-color: #e5cead;
  top: -10px;
}

.userpage3 {
  position: relative;
  left: -110px;
  top: 5px;
  width: 65px;
  height: 65px;
}

.center-card-text3 {
  position: relative;
  left: 25px;
  top: -50px;
  font-weight: 550;
  font-size: 17px;
}

.center-card-text4 {
  position: relative;
  top: -50px;
  left: 5px;
  font-weight: 200;
  font-size: 14px;
}


//云
.usercloud{
  position:absolute;
  bottom: 234px;
  width: 89px;
  right: 132px;

}

.userpage4 {
  position: relative;
  top: 0px;
  width: 100%;
  height: 15%;
}
</style>