<template>

  <div>

    <div id="back">
      <canvas id="canvas" class="canvas-back" ref="canvas"></canvas>
      <div class="backRight">
      </div>
      <div class="backLeft">
      </div>
    </div>

    <div id="slideBox">
      <div class="topLayer">
        <div class="left">
          <div class="content">
            <h2>Sign Up</h2>
            <form id="form-signup" method="post" onsubmit="return false;">
              <div class="form-element form-stack">
                <label for="phonenumber" class="form-label">手机号</label>
                <input id="phonenumber" type="text" name="phonenumber" class="form-input" @blur="PhoneConfirmLeave" v-model="phonenumber" style="padding-top: 12px">
                <div id="PhoneWrongConfirm" style="color:#ffc107;font-size: 10px"></div>
              </div>
              <!--是否已存在文本框-->


              <div class="form-element form-stack">
                <label for="phonenumber" class="form-label">验证码</label>

                <input id="phonenumber" type="text" name="code" class="form-input"  v-model="code">
                <b-button  type="submit" id="send-message" @click="getMessage" style="background-color: #ff7f58;-bs-btn-hover-bg: #de704f;">获取验证码</b-button>

              </div>
              <div class="form-element form-stack">
                <label for="username-signup" class="form-label">用户名</label>
                <input id="username-signup" type="text" name="username" class="form-input" v-model="username">
              </div>
              <div class="form-element form-stack">
                <label for="password-signup" class="form-label">密码</label>
                <input id="password-signup" type="password" name="password" class="form-input" v-model="password">
              </div>
              <div class="form-element form-stack">
                <label if="password-signup" for="password-signup" class="form-label" >请再次输入密码</label>
                <input id="password-signup1" type="password" name="password2" class="form-input" @blur="confirmLeave" v-model="password2">
              </div>
              <div id="wrongConfirm"></div>


              <div class="form-element form-submit">
                <button id="signUp" class="signup" type="submit" name="signup" @click="register1">注册</button>
                <button id="goLeft" class="signup off">去登录</button>
              </div>
            </form>
          </div>
        </div>
        <div class="right">
          <div class="content">
<!--            <img id='logo' src="src/assets/logo1.png" width="100px">-->
            <h2>Login</h2>
            <form id="form-login" method="post" onsubmit="return false;">
              <div class="form-element form-stack">
                <label for="user-phone" class="form-label">手机号</label>
                <input id="user-phone" type="text" name="phonenumber" class="form-input" v-model="phoneNumber1">

              </div>
              <div class="form-element form-stack">
                <label for="password-login" class="form-label">密码</label>
                <input id="password-login" type="password" name="password" class="form-input" v-model="password1">
              </div>
              <div class="form-element form-submit">
                <button id="logIn" class="login" type="submit" name="login" @click="login">登录</button>
                <button id="goRight" class="login off" name="signup">去注册</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <div>
<!--      <b-alert-->
<!--               variant="warning"-->
<!--                id="balert" fade input></b-alert>-->
      <b-alert v-if="isError" v-model="isError" variant="danger" >
        {{ msg }}
      </b-alert>
    </div>
  </div>
</template>

<script>

// import {config, formatXml} from '@/assets/js/login.js'
// import paper from "paper";
import {BButton, BAlert} from "bootstrap-vue";
// import register from "@/views/user/Register.vue";
//import * as url from "url";

export default {
  name: "Login",
  computed: {
    // register() {
    //   return register
    // }
  },
  components: {
    // config,
    // formatXml,
    BButton,
    BAlert
  },
  data() {
    return{
      msg:'',
      isError:false,
      phoneNumber1: '',//登录手机号
      password1: '', //登录密码
      password2: '', //注册第二个密码
      password: '', //注册第一个密码
      phonenumber: '', //注册手机号
      code:'', //注册验证码
      username: '', //用户名
    }
  },
  methods:{
    //验证码
    getMessage(){
      this.$api({
        url: '/register/getcode',
        method: 'get', //这个是method,用methods会默认post
        params: {
          phoneNumber: this.phonenumber,
        }
      }).then(res => {
        console.log("1212")
        console.log(res)
      }).catch(function (error){
        console.log(error)
      })
    },

    //手机号格式+是否存在
    PhoneConfirmLeave(){
      this.comfirm = document.getElementById('phonenumber').value
      //1
      if(this.comfirm === ''){
        document.getElementById('phonenumber').setAttribute('class','emailText change')
        document.getElementById('PhoneWrongConfirm').innerText = '手机号不能为空'
      }else{
        document.getElementById('phonenumber').setAttribute('class','emailText')
        if(!(/^1[3456789]\d{9}$/.test(this.comfirm))){
          document.getElementById('phonenumber').setAttribute('class','emailText change')
          document.getElementById('PhoneWrongConfirm').innerText = '手机号格式不正确'
        }else{
          document.getElementById('phonenumber').setAttribute('class','emailText')
          document.getElementById('PhoneWrongConfirm').innerText = ''
          // 再加一个返回数据库校验手机号是否存在
          this.$api({
            url: '/register/check',
            method: 'get', //这个是method,用methods会默认post
            params: {
              phoneNumber: this.phonenumber,
            }
          }).then(res => {
            console.log(res)
            // 20040是已经存在，get-error，20041是已经存在了
            if(res.code === 20061){
              document.getElementById('phonenumber').setAttribute('class','emailText')
              document.getElementById('PhoneWrongConfirm').innerText = ''
            }else if(res.code === 20060){
              document.getElementById('phonenumber').setAttribute('class','emailText')
              document.getElementById('PhoneWrongConfirm').innerText = '该手机号已存在'
            }
          }).catch(function (error){
            console.log(error);
          });

        }
      }
      console.log(document.getElementById('PhoneWrongConfirm').innerText)
    },
    //两次密码比对
    confirmLeave () {
      // this.password = document.getElementById('password-signup').value
      // this.password2 = document.getElementById('password-signup1').value
      console.log(this.password2)
      console.log(this.password)
      console.log(this.username)
      console.log(this.phonenumber)
      console.log(this.code)
      if (this.password === '') {
        document.getElementById('confirmText').setAttribute('class', 'emailText change')
        document.getElementById('wrongConfirm').innerText = '密码不能为空'
      } else {
        document.getElementById('confirmText').setAttribute('class', 'emailText')
        if (this.password !== this.password2) {
          document.getElementById('confirmText').setAttribute('class', 'emailText change')
          document.getElementById('wrongConfirm').innerText = '两次输入的密码不匹配'
        } else {
          document.getElementById('confirmText').setAttribute('class', 'emailText')
          document.getElementById('wrongConfirm').innerText = ''
        }
      }
    },

    // 注册
    register1(){
      console.log(this.password2)
      console.log(this.password)
      console.log(this.username)
      console.log(this.phonenumber)
      console.log(this.code)
      //this.$api.get('/register?phoneNumber=15172118655&password=123456&code=5178&username=Jack8&profilePhoto=')
      // this.$api.get('/register?phoneNumber='+this.phonenumber+'&password='+this.password+'&code='+this.code+'&username='+this.username+'&profilePhoto=')
      // http://117.50.182.208:8080/register?phoneNumber=15172118655&password=123456&code=9323&username=Jack10&profilePhoto=
      this.$api({
        url:'/register',
        //url:'/register?phoneNumber=15172118655&password=123456&code=5178&username=Jack8&profilePhoto=',
        method:'get',
        params:{
          phoneNumber:this.phonenumber,
          password:this.password,
          code:this.code,
          username:this.username,
          profilePhoto:'',
          //'https://img-blog.csdnimg.cn/20200527153605959.png'
        }
      }).then(res =>{
        console.log(res)
        if(res.code === 20011){
          // 注册成功
          console.log(res)
          this.$toast.success('注册成功,去登录吧')
          this.phoneNumber1= this.phonenumber
        }else {
          // 注册失败,用bootstrap警告提醒框提醒
          console.log(res.msg+'1')
          this.isError = true;
          this.msg = res.msg;
        }
      }).catch(function (error){
        console.log(error);
      })
    },

    // 登录验证
    login() {
        this.$api({
          url: '/login/customer',
          method: 'post', //这个是method,用methods会默认post
          params: {
            phoneNumber: this.phoneNumber1,
            password: this.password1
          }
        }).then(res => {
          console.log(res.code)
          if(res.code === 20051){
            // 登录成功
            console.log(res)
            sessionStorage.setItem('phoneNumber',this.phoneNumber1)
            sessionStorage.setItem('customerID', res.data) //这个返回的data里返回的用户id
            this.$router.push({path: '/user/menu'})
          }else {
            // 登录失败,用bootstrap警告提醒框提醒
            console.log(res.msg+'1')
            this.isError = true;
            this.msg = res.msg;


          }
        }).catch(function (error){
          console.log(error);
        });

    }
  },
  mounted() {
    /* ====================== *
*  Initiate Canvas       *
* ====================== */
    paper.install(window);
    paper.setup(this.$refs.canvas);
    console.log(this.$refs.canvas);

// Paper JS Variables
    var canvasWidth,
        canvasHeight,
        canvasMiddleX,
        canvasMiddleY;

    var shapeGroup = new Group();

    var positionArray = [];

    function getCanvasBounds() {
      // Get current canvas size
      canvasWidth = view.size.width;
      canvasHeight = view.size.height;
      canvasMiddleX = canvasWidth / 2;
      canvasMiddleY = canvasHeight / 2;
      // Set path position
      var position1 = {
        x: (canvasMiddleX / 2) + 100,
        y: 100,
      };

      var position2 = {
        x: 200,
        y: canvasMiddleY,
      };

      var position3 = {
        x: (canvasMiddleX - 50) + (canvasMiddleX / 2),
        y: 150,
      };

      var position4 = {
        x: 0,
        y: canvasMiddleY + 100,
      };

      var position5 = {
        x: canvasWidth - 130,
        y: canvasHeight - 75,
      };

      var position6 = {
        x: canvasMiddleX + 80,
        y: canvasHeight - 50,
      };

      var position7 = {
        x: canvasWidth + 60,
        y: canvasMiddleY - 50,
      };

      var position8 = {
        x: canvasMiddleX + 100,
        y: canvasMiddleY + 100,
      };

      positionArray = [position3, position2, position5, position4, position1, position6, position7, position8];
    };


    /* ====================== *
     * Create Shapes          *
     * ====================== */
    function initializeShapes() {
      // Get Canvas Bounds
      getCanvasBounds();

      var shapePathData = [
        'M231,352l445-156L600,0L452,54L331,3L0,48L231,352',
        'M0,0l64,219L29,343l535,30L478,37l-133,4L0,0z',
        'M0,65l16,138l96,107l270-2L470,0L337,4L0,65z',
        'M333,0L0,94l64,219L29,437l570-151l-196-42L333,0',
        'M331.9,3.6l-331,45l231,304l445-156l-76-196l-148,54L331.9,3.6z',
        'M389,352l92-113l195-43l0,0l0,0L445,48l-80,1L122.7,0L0,275.2L162,297L389,352',
        'M 50 100 L 300 150 L 550 50 L 750 300 L 500 250 L 300 450 L 50 100',
        'M 700 350 L 500 350 L 700 500 L 400 400 L 200 450 L 250 350 L 100 300 L 150 50 L 350 100 L 250 150 L 450 150 L 400 50 L 550 150 L 350 250 L 650 150 L 650 50 L 700 150 L 600 250 L 750 250 L 650 300 L 700 350 '
      ];

      for (var i = 0; i <= shapePathData.length; i++) {
        // Create shape
        var headerShape = new Path({
          strokeColor: 'rgba(255, 255, 255, 0.5)',
          strokeWidth: 2,
          parent: shapeGroup,
        });
        // Set path data
        headerShape.pathData = shapePathData[i];
        headerShape.scale(2);
        // Set path position
        headerShape.position = positionArray[i];
      }
    };

    initializeShapes();

    /* ====================== *
     * Animation              *
     * ====================== */
    view.onFrame = function paperOnFrame(event) {
      if (event.count % 4 === 0) {
        // Slows down frame rate
        for (var i = 0; i < shapeGroup.children.length; i++) {
          if (i % 2 === 0) {
            shapeGroup.children[i].rotate(-0.1);
          } else {
            shapeGroup.children[i].rotate(0.1);
          }
        }
      }
    };

    view.onResize = function paperOnResize() {
      getCanvasBounds();

      for (var i = 0; i < shapeGroup.children.length; i++) {
        shapeGroup.children[i].position = positionArray[i];
      }

      if (canvasWidth < 700) {
        shapeGroup.children[3].opacity = 0;
        shapeGroup.children[2].opacity = 0;
        shapeGroup.children[5].opacity = 0;
      } else {
        shapeGroup.children[3].opacity = 1;
        shapeGroup.children[2].opacity = 1;
        shapeGroup.children[5].opacity = 1;
      }
    }
  }
}

</script>

<style lang="scss" scoped>
//$theme-signup: #03f4ec;
//$theme-signup-darken: #02c0d1;
//$theme-signup-background: #fddeaf;
//$theme-login: #FFCD24;
//$theme-login-darken: #eabc22;
//$theme-login-background: #f9f9f9;
//$theme-dark: #212121;
//$theme-light: #e3e3e3;
//$font-default: 'Roboto', sans-serif;

$theme-signup: #ff7f58;
$theme-signup-darken: #ff8c72;
//$theme-signup-background: #fff9ef;
$theme-signup-background: #f9f9f9;;
$theme-login: #fdcc56;
$theme-login-darken: #eabc22;
$theme-login-background: #f9f9f9;
$theme-dark: #212121;
$theme-light: #130000;
$font-default: 'Roboto', sans-serif;

$success: #5cb85c;
$error: #d9534f;

// 登陆失败弹窗
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s;
}

.fade-enter, .fade-leave-to {
  opacity: 0;
}

.b-alert .close {
  right: auto;
  left: 15px;
}
#send-message{
  position: absolute;
  float: right;
  right: 0px;
  padding-left: 5px;
  padding-right: 5px;
  margin-top: 20px;
}
.user-phone-message{
  position: absolute;
  float: left;
  left: 0px;
}


.form-label {
  position: absolute;
  float: left;
  left: 0px;
}
.form-input{
  padding-top: 30px;
}
body {
  margin: 0;
  height: 100%;
  overflow: hidden;
  width: 100% !important;
  box-sizing: border-box;
  font-family: $font-default;
}

.backRight {
  position: absolute;
  right: 0;
  width: 50%;
  height: 100%;
  background: $theme-signup;
}

.backLeft {
  position: absolute;
  left: 0;
  width: 50%;
  height: 100%;
  background: $theme-login;
}

#back {
  width: 100%;
  height: 100%;
  position: absolute;
  z-index: -999;
}

.canvas-back {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 10;
}

#slideBox {
  width: 50%;
  max-height: 100%;
  height: 100%;
  overflow: hidden;
  margin-left: 50%;
  position: absolute;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
  //box-shadow: 0 14px 28px rgba(253, 222, 175, 0.69), 0 10px 10px rgba(253, 222, 175, 0.3);
}

.topLayer {
  width: 200%;
  height: 100%;
  position: relative;
  left: 0;
  left: -100%;
}

label {
  font-size: 0.8em;
  text-transform: uppercase;
}

input {
  background-color: transparent;
  border: 0;
  outline: 0;
  font-size: 1em;
  padding: 8px 1px;
  margin-top: 0.1em;
}

.left {
  width: 50%;
  height: 100%;
  overflow: scroll;
  background: $theme-signup-background;
  left: 0;
  position: absolute;

  label {
    color: $theme-light;
  }

  input {
    border-bottom: 1px solid $theme-light;
    color: $theme-light;

    &:focus, &:active {
      border-color: $theme-signup;
      color: $theme-signup;
    }

    &:-webkit-autofill {
      -webkit-box-shadow: 0 0 0 30px $theme-signup-background inset;
      -webkit-text-fill-color: $theme-light;
    }
  }

  a {
    color: $theme-signup;
  }
}

.right {
  width: 50%;
  height: 100%;
  overflow: scroll;
  background: $theme-login-background;
  right: 0;
  position: absolute;

  label {
    color: $theme-dark;
  }

  input {
    border-bottom: 1px solid $theme-dark;

    &:focus, &:active {
      border-color: $theme-login;
    }

    &:-webkit-autofill {
      -webkit-box-shadow: 0 0 0 30px $theme-login-background inset;
      -webkit-text-fill-color: $theme-dark;
    }
  }
}

.content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  min-height: 100%;
  width: 80%;
  margin: 0 auto;
  position: relative;
}

.content h2 {
  font-weight: 300;
  font-size: 2.6em;
  margin: 0.2em 0 0.1em;
}

.left .content h2 {
  color: $theme-signup;
}

.right .content h2 {
  color: $theme-login;
}

.form-element {
  margin: 1.6em 0;

  &.form-submit {
    margin: 1.6em 0 0;
  }
}

.form-stack {
  display: flex;
  flex-direction: column;
}

.checkbox {
  -webkit-appearance: none;
  outline: none;
  background-color: $theme-light;
  border: 1px solid $theme-light;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05), inset 0px -15px 10px -12px rgba(0, 0, 0, 0.05);
  padding: 12px;
  border-radius: 4px;
  display: inline-block;
  position: relative;
}

.checkbox:focus, .checkbox:checked:focus,
.checkbox:active, .checkbox:checked:active {
  border-color: $theme-signup;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05), inset 0px 1px 3px rgba(0, 0, 0, 0.1);
}

.checkbox:checked {
  outline: none;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05), inset 0px -15px 10px -12px rgba(0, 0, 0, 0.05), inset 15px 10px -12px rgba(255, 255, 255, 0.1);
}

.checkbox:checked:after {
  outline: none;
  content: '\2713';
  color: $theme-signup;
  font-size: 1.4em;
  font-weight: 900;
  position: absolute;
  top: -4px;
  left: 4px;
}

.form-checkbox {
  display: flex;
  align-items: center;

  label {
    margin: 0 6px 0;
    font-size: 0.72em;
  }
}

button {
  padding: 0.8em 1.2em;
  margin: 0 10px 0 0;
  width: auto;
  font-weight: 600;
  text-transform: uppercase;
  font-size: 1em;
  color: #fff;
  line-height: 1em;
  letter-spacing: 0.6px;
  border-radius: 3px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1), 0 3px 6px rgba(0, 0, 0, 0.1);
  border: 0;
  outline: 0;
  transition: all 0.25s;

  &.signup {
    background: $theme-signup;
  }

  &.login {
    background: $theme-login;
  }

  &.off {
    background: none;
    box-shadow: none;
    margin: 0;

    &.signup {
      color: $theme-signup;
    }

    &.login {
      color: $theme-login;
    }
  }
}

button:focus, button:active, button:hover {
  box-shadow: 0 4px 7px rgba(0, 0, 0, 0.1), 0 3px 6px rgba(0, 0, 0, 0.1);

  &.signup {
    background: $theme-signup-darken;
  }

  &.login {
    background: $theme-login-darken;
  }

  &.off {
    box-shadow: none;

    &.signup {
      color: $theme-signup;
      background: $theme-dark;
    }

    &.login {
      color: $theme-login-darken;
      background: $theme-light;
    }
  }
}

@media only screen and (max-width: 768px) {
  #slideBox {
    width: 80%;
    margin-left: 20%;
  }
  .signup-info, .login-info {
    display: none;
  }
}

</style>