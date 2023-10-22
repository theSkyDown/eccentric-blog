<template>
  <div id="app">
    <!-- 头部 -->
    <header>
      <Navigate />
    </header>

    <!-- 主体内容 -->
    <main>
      <transition name="bounce">
        <router-view />
      </transition>
    </main>

    <!-- 页脚 -->
    <footer>
      <Foot />
    </footer>

    <!-- 置顶按钮 -->
    <transition name="slide-up">
      <vs-button icon class="to-top" v-show="showToTop" v-on:click="goToTop()">
        <i class="iconfont icon-a-xiangshangjiantou"></i>
      </vs-button>
    </transition>
  </div>
</template>

<script>
import Navigate from "./components/layout/nav.vue";
import Foot from "./components/layout/footer.vue";
export default {
  name: "App",
  components: { Navigate, Foot },
  data() {
    return {
      showToTop: false,
    };
  },
  methods: {
    // 滚动事件的回调函数
    handleScroll() {
      if (document.documentElement.scrollTop > window.innerHeight) {
        this.showToTop = true;
      } else {
        this.showToTop = false;
      }
    },
    goToTop() {
      //用计时器来实现回到置顶处，因为这样不会一下子闪到顶端
      const time = setInterval(() => {
        document.documentElement.scrollTop -= 60;
        if (document.documentElement.scrollTop <= 0) {
          clearInterval(time);
        }
      }, 10);
    },
  },
  mounted() {
    // 跳转至首页
    this.$router.push("/home");
    //监听滚轮，滚轮发生变化调用方法handleScroll
    window.addEventListener("scroll", this.handleScroll, true); // 这里加上true是为了保证浏览器滚动的及时性
  },
};
</script>

<style>
@import url("./assets/css/style.css");
#app {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: auto;
  background: #e9edf3;
}

/* 头部 */
header {
  width: 100%;
  height: 50px;
  z-index: 1501;
  position: fixed !important;
  overflow: hidden !important;
}

/* 主体内容 */
main {
  width: 100%;
  height: auto;
}

/* 页脚 */
footer {
  width: 100%;
  height: 220px;
  overflow: hidden !important;
}

/* 切换动画 */
.bounce-enter-active {
  animation: bounce-in 0.5s;
}
.bounce-leave-active {
  animation: bounce-in 0.5s reverse;
}
@keyframes bounce-in {
  0% {
    transform: scale(0);
  }
  50% {
    transform: scale(1.5);
  }
  100% {
    transform: scale(1);
  }
}

/* 谷歌美化滚动条 */
*::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

*::-webkit-scrollbar-track {
  background: #f3f4f9;
  border-radius: 2px;
}

*::-webkit-scrollbar-thumb {
  background: #c8d2e0;
  border-radius: 10px;
}

*::-webkit-scrollbar-thumb:hover {
  background: #333;
}

*::-webkit-scrollbar-corner {
  background: #c8d2e0;
}

/* 置顶按钮 */
.to-top {
  position: fixed !important;
  bottom: 20px;
  right: 20px;
  width: 40px;
  height: 40px;
}
.slide-up-enter-active,
.slide-up-leave-active {
  transition: transform 0.5s;
}
.slide-up-enter {
  bottom: -20px;
}
.slide-up-enter-to,
.slide-up-leave {
  bottom: 20px;
}
.slide-up-leave-to {
  bottom: -20px;
}
</style>
