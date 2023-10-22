import Vue from "vue";
import Vuex from "vuex";
Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    //全局状态
    urlPath: "http://localhost:7777",
    //判断请求是否成功
    judgeResult: function (res) {
      return res.data.code == 200;
    },
    //token
    token: "",
    //是否处于登陆状态
    isLogin: false,
  },
});

export default store;
