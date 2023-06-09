"use strict";

import Vue from "vue";
import axios from "axios";
import { Message, Loading } from "element-ui";
import router from "../router/index";

let loading;
function startLoading() {
  loading = Loading.service({
    lock: true,
    text: "拼命加载中...",
    background: "rgba(0,0,0,0.7)",
  });
}
function endLoading() {
  loading.close();
}

// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

const config = {
  // baseURL: process.env.baseURL || process.env.apiUrl || ""
  // timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
};

const _axios = axios.create(config);

_axios.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    startLoading();
    if (localStorage.Admintoken) {
      config.headers.Authorization = localStorage.Admintoken;
    }

    if (localStorage.Usertoken) {
      config.headers.token = localStorage.Usertoken;
    }
    return config;
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Add a response interceptor
_axios.interceptors.response.use(
  function (response) {
    // Do something with response data
    endLoading();
    // const { status } = response.response;
    if (response.data.code === 21) {
      Message.error("token过期，请重新登录！");
      localStorage.removeItem("Admintoken");
      router.push("/admin/login");
    }

    if (response.data.code === 22) {
      Message.error("token过期，请重新登录！");
      localStorage.removeItem("Usertoken");
      localStorage.removeItem("Userinfo");
      router.push("/user/login");
    }
    return response;
  },
  function (error) {
    // Do something with response error
    endLoading();
    Message.error(error.response.data);
    return Promise.reject(error);
  }
);

Plugin.install = function (Vue, options) {
  Vue.axios = _axios;
  window.axios = _axios;
  Object.defineProperties(Vue.prototype, {
    axios: {
      get() {
        return _axios;
      },
    },
    $axios: {
      get() {
        return _axios;
      },
    },
  });
};

Vue.use(Plugin);

export default Plugin;
