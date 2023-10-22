import Vue from "vue";
import Router from "vue-router";
import Home from "../components/pages/home.vue";
import Category from "../components/pages/category.vue";
import Tag from "../components/pages/tag.vue";
import Pigeonhole from "../components/pages/pigeonhole.vue";
import Temp from "../components/pages/temp.vue";
import Article from "../components/pages/article.vue";
Vue.use(Router);
const router = new Router({
  routes: [
    {
      name: "home",
      path: "/home",
      component: Home,
    },
    {
      name: "category",
      path: "/category",
      component: Category,
    },
    {
      name: "tag",
      path: "/tag",
      component: Tag,
    },
    {
      name: "pigeonhole",
      path: "/pigeonhole",
      component: Pigeonhole,
    },
    {
      name: "article",
      path: "/article",
      component: Article,
      meta: {
        keepAlive: false, // 禁止组件复用
      },
    },

    //空白页
    {
      name: "temp",
      path: "/temp",
      component: Temp,
    },
  ],
});

// 导航报错问题
const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err);
};

export default router;
