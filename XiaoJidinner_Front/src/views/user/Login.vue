<template>

<div class="form-structor">
  <div class="login">
    <div class="center">
      <h2 class="form-title" id="login">用户登录</h2>
      <div class="form-holder">
        <input type="text" class="input" placeholder="手机号" />
        <input type="password" class="input" placeholder="密码" />
      </div>
      <i class="el-icon-warning-outline">未注册的用户登录后自动注册！</i>
      <button class="submit-btn">登录</button>
    </div>
    <img src="../../assets/picture/userbg1.png" class="userpage4"/> 
  </div>
</div>
</template>

<script>

export default {
  name: "Login",
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
  }
}

</script>

<style scoped>
html, body {
  position: relative;
  min-height: 100vh;
  background-color: #E1E8EE;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Fira Sans", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.form-structor {
  /* background-color: #222; */
  border-radius: 15px;
  height: 100%;
  width: 100%;
  position: relative;
  overflow: hidden;
}
.form-structor::after {
  content: "";
  opacity: 0.8;
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  height: 70%;
  width: 100%;
  background-repeat: no-repeat;
  background-position: left bottom;
  background-size: 500px;
  /* background-color: #fddfba; */
  background-image: url("../../assets/picture/lunbo1.jpg");
}


.center i{
  margin-top: 15px;
  font-size: 15px;
  color: darkorange;
}

.form-structor .login {
  position: absolute;
  top: 0%;
  left: 0;
  right: 0;
  bottom: 0;
  /* height: 100%; */
  /* background-color: #fff; */
  background-image: linear-gradient(180deg, rgba(255,0,0,0), #fae5cc, #faf7f0);
  z-index: 5;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login::before {
  content: "";
  position: absolute;
  left: 50%;
  top: -20px;
  -webkit-transform: translate(-50%, 0);
  /* background-color: #fff; */
  /* background-image: linear-gradient(180deg, rgba(255,255,0,0), #f7cd9a98, #f3dfaf); */
  width: 200%;
  height: 250px;
  border-radius: 50%;
  z-index: 4;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login .center {
  position: absolute;
  top: calc(50% - 10%);
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  width: 75%;
  z-index: 5;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login .center .form-title {
  color: #eb7302e8;
  /* margin-top: -50px; */
  font-size: 40px;
  font-weight: 600;
  text-align: center;
}
.form-structor .login .center .form-title span {
  color: rgba(0, 0, 0, 0.4);
  opacity: 0;
  visibility: hidden;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login .center .form-holder {
  border-radius: 15px;
  background-color: #fff;
  border: 1px solid #eee;
  overflow: hidden;
  margin-top: 50px;
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login .center .form-holder .input {
  border: 0;
  outline: none;
  box-shadow: none;
  display: block;
  height: 35px;
  line-height: 30px;
  padding: 8px 15px;
  border-bottom: 1px solid #eee;
  width: 100%;
  font-size: 15px;
}
.form-structor .login .center .form-holder .input:last-child {
  border-bottom: 0;
}
.form-structor .login .center .form-holder .input::-webkit-input-placeholder {
  color: rgba(0, 0, 0, 0.4);
}
.form-structor .login .center .submit-btn {
  background-color: #6B92A4;
  color: rgba(255, 255, 255, 0.7);
  border: 0;
  border-radius: 15px;
  display: block;
  margin: 15px auto;
  padding: 15px 45px;
  width: 100%;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login .center .submit-btn:hover {
  transition: all 0.3s ease;
  background-color: rgba(0, 0, 0, 0.8);
}
.form-structor .login.slide-up {
  top: 90%;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login.slide-up .center {
  top: 10%;
  -webkit-transform: translate(-50%, 0%);
  -webkit-transition: all 0.3s ease;
}
.form-structor .login.slide-up .form-holder, .form-structor .login.slide-up .submit-btn {
  opacity: 0;
  visibility: hidden;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login.slide-up .form-title {
  font-size: 1em;
  margin: 0;
  padding: 0;
  cursor: pointer;
  -webkit-transition: all 0.3s ease;
}
.form-structor .login.slide-up .form-title span {
  margin-right: 5px;
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.3s ease;
}
.userpage4 {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 25%;
}

</style>