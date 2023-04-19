<template>
  <div class="body">
    <!--  背景图片  -->
    <div id="home" ></div>
    <!-- 上部导航栏  -->
    <b-navbar class="navbar" toggleable="lg" type="dark">
      <div>
        <!-- Just an image -->
        <b-navbar variant="faded" type="light">
          <b-navbar-brand href="#">
            <img id='logo' src="../../assets/logo1.png" alt="Kitten" width="100px">
          </b-navbar-brand>
        </b-navbar>
      </div>
<!--      <b-navbar-brand href="#">菜单</b-navbar-brand>-->

<!--      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>-->

<!--      <b-collapse id="nav-collapse" is-nav>-->
<!--        <b-navbar-nav>-->
<!--          <b-navbar-brand href="#">订单</b-navbar-brand>-->
<!--        </b-navbar-nav>-->

<!--        &lt;!&ndash; Right aligned nav items &ndash;&gt;-->
<!--        <b-navbar-nav class="ml-auto">-->
<!--          <b-nav-form>-->
<!--            <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>-->
<!--            <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>-->
<!--          </b-nav-form>-->

<!--          <b-nav-item-dropdown right>-->
<!--            &lt;!&ndash; Using 'button-content' slot &ndash;&gt;-->
<!--            <template v-slot:button-content>-->
<!--              <em>User</em>-->
<!--            </template>-->
<!--            <b-dropdown-item href="#">Profile</b-dropdown-item>-->
<!--            <b-dropdown-item href="#">Sign Out</b-dropdown-item>-->
<!--          </b-nav-item-dropdown>-->
<!--        </b-navbar-nav>-->
<!--      </b-collapse>-->
    </b-navbar>
    <!-- 顶部导航栏结束-->

    <!--  主题模块  -->
<!--    <div>-->
<!--      &lt;!&ndash;  侧边栏  &ndash;&gt;-->
<!--      <div id="sidebar-div">-->
<!--        &lt;!&ndash;   侧边导航   &ndash;&gt;-->
<!--        <nav>-->
<!--          <ul>-->
<!--            <li><a href="#">新品热卖</a></li>-->
<!--            <li><a href="#">汉堡</a></li>-->
<!--            <li><a href="#">Link 3</a></li>-->
<!--          </ul>-->
<!--        </nav>-->
<!--      </div>-->
<!--      &lt;!&ndash;  侧边栏结束  &ndash;&gt;-->
<!--      &lt;!&ndash;  主题内容    &ndash;&gt;-->

<!--    </div>-->
  </div>
</template>

<script>
import {
  BNavbar,
  BNavbarBrand,
  BNavbarToggle,
  BCollapse,
  BNavbarNav,
  BNavForm,
  BFormInput,
  BButton,
  BDropdownItem,
  BSidebar
} from 'bootstrap-vue'

export default {
  name: "Upbar",
  components: {
    BNavbar,
    BNavbarBrand,
    BNavbarToggle,
    BCollapse,
    BNavbarNav,
    BNavForm,
    BFormInput,
    BButton,
    BDropdownItem,
    BSidebar
  },
  methods: {
    toggleSidebar() {
      this.$refs.sidebar.toggle()
    }
  }

}
</script>

<style scoped>
.body{
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: left;
  color: #2c3e50;
}

.navbar {
  position: relative;
  min-height: 50px;
  margin-bottom: 1px;
  background-color: #FFCD56;
}

#home {
  width: 100%;
  height: 100vh;
  /*background: url("~@/assets/homebg.jpg") center center no-repeat;*/
  background-size: 100% 100%;
  position: absolute;
}

#logo {
  margin-left: 10px;
}


#sidebar-div {
  position: absolute;
  width: 22%;
  background: #F7F7F7;
  top: 90px;
  left: 0;
  z-index: 1000;
  height: 80%;
  box-shadow: 0 0 4px rgb(0 0 0 / 12%);
}

</style>