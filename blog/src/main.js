import Vue from "vue";
import App from "./App.vue";

Vue.config.productionTip = false;

//vuesax ui 组件库
import Vuesax from "vuesax";
import "vuesax/dist/vuesax.css"; //Vuesax styles
Vue.use(Vuesax);

//引入全局icon
import "../src/assets/icon/iconfont.css";

// 引入router路由
import router from "./router/index.js";

// vuex，全局状态管理
import store from "./store/index.js";

//引入mavon-editor
import mavonEditor from "mavon-editor";
import "mavon-editor/dist/css/index.css";
Vue.use(mavonEditor);

new Vue({
  //路由
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
