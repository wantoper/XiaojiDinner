import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    meta: {
      title: "点餐系统",
    },
    redirect: "/index/1",
  },
  {
    path: "/admin/login",
    name: "Login",
    meta: {
      title: "系统登录",
    },
    component: () => import("../views/admin/Login"),
  },
  {
    path: "/admin",
    component: () => import("../views/admin/Index"),
    children: [
      {
        path: "",
        meta: {
          title: "首页",
        },
        component: () => import("../views/admin/Home"),
      },
      {
        path: "/admin/home",
        name: "Home",
        meta: {
          title: "首页",
        },
        component: () => import("../views/admin/Home"),
      },
      {
        path: "/admin/order",
        name: "Order",
        meta: {
          title: "订单管理",
        },
        component: () => import("../views/admin/Order"),
      },
      {
        path: "/admin/history",
        name: "History",
        meta: {
          title: "历史订单",
        },
        component: () => import("../views/admin/History"),
      },
      {
        path: "/admin/category",
        name: "Category",
        meta: {
          title: "分类管理",
        },
        component: () => import("../views/admin/Category"),
      },
      {
        path: "/admin/commodity",
        name: "Commodity",
        meta: {
          title: "商品管理",
        },
        component: () => import("../views/admin/Commodity"),
      },
      {
        path: "/admin/qrcode",
        name: "QRcode",
        meta: {
          title: "点餐码管理",
        },
        component: () => import("../views/admin/QRCode"),
      },
      {
        path: "/admin/shop",
        name: "Shop",
        meta: {
          title: "店铺信息",
        },
        component: () => import("../views/admin/Shop"),
      },
      {
        path: "/admin/user",
        name: "User",
        meta: {
          title: "个人信息",
        },
        component: () => import("../views/admin/User"),
      },
      {
        path: "/admin/*",
        name: "AdminNotFount",
        meta: {
          title: "出错了",
        },
        component: () => import("../views/404"),
      },
    ],
  },
  {
    path: "/index/:id",
    name: "UserShop",
    props: true,
    meta: {
      title: "在线点餐",
    },
    component: () => import("../views/user/Index"),
  },
  {
    path: "/user",
    name: "Home",
    meta: {
      title: "历史订单",
    },
    component: () => import("../views/user/UserPage"),
  },
  {
    path: "/user/login",
    name: "UserLogin",
    meta: {
      title: "用户登录",
    },
    component: () => import("../views/user/Login"),
  },
  {
    path: "/user/order",
    name: "Order",
    component: () => import("../views/user/Order"),
  },
  {
    path: "/*",
    name: "NotFound",
    meta: {
      title: "出错了",
    },
    component: () => import("../views/404"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

function setTitle(to) {
  if (to.meta.title) {
    document.title = to.meta.title;
  }
}

// 路由守卫
router.beforeEach((to, from, next) => {
  setTitle(to);

  if (
    to.path.startsWith("/index/") ||
    to.path.startsWith("/user/login") ||
    to.path.startsWith("/admin/login")
  ) {
    next();
    return;
  }

  if (to.path.startsWith("/user")) {
    const isLogin = !!localStorage.getItem("Usertoken");
    isLogin ? next() : next("/user/login");
    return;
  }

  if (to.path.startsWith("/admin")) {
    const isLogin = !!localStorage.getItem("Admintoken");
    isLogin ? next() : next("/admin/login");
    return;
  }

  next();
});

export default router;
