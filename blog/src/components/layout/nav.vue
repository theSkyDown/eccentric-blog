<!-- 

  导航

 -->
<template>
  <div class="nav">
    <vs-navbar fixed square shadow v-model="select">
      <template #left>
        <span
          class="logo"
          v-on:click="
            changeRouter('/home');
            select = 'home';
          "
          >Eccentric</span
        >
      </template>

      <vs-navbar-item
        v-on:click="changeRouter('/home')"
        :active="select == 'home'"
        id="home"
      >
        <i class="iconfont icon-home"></i>
        首页
      </vs-navbar-item>
      <vs-navbar-item
        v-on:click="changeRouter('/category')"
        :active="select == 'category'"
        id="category"
      >
        <i class="iconfont icon-category"></i>
        分类
      </vs-navbar-item>
      <vs-navbar-item
        :active="select == 'tag'"
        v-on:click="changeRouter('/tag')"
        id="tag"
      >
        <i class="iconfont icon-tag"></i>
        标签
      </vs-navbar-item>
      <vs-navbar-item
        :active="select == 'pigeonhole'"
        v-on:click="changeRouter('/pigeonhole')"
        id="pigeonhole"
      >
        <i class="iconfont icon-guidang"></i>
        归档
      </vs-navbar-item>
      <!-- <vs-navbar-item :active="select == 'about'" id="about">
        <i class="iconfont icon-guanyuwomen"></i>
        关于我
      </vs-navbar-item> -->

      <template #right>
        <!-- <vs-switch class="modelSwitch" dark v-model="switchModel">
          <template #circle>
            <i v-if="switchModel" class="iconfont icon-yueliang"></i>
            <i v-else class="iconfont icon-qing"></i>
          </template>
        </vs-switch> -->

        <vs-button
          gradient
          v-on:click="showLoginDialog"
          v-if="!$store.state.isLogin"
        >
          Login
          <template #animate>
            <i class="iconfont icon-denglu"></i>
          </template>
        </vs-button>

        <div v-if="$store.state.isLogin" class="username-div">
          <span>{{ userInfo.nickName }}</span>
          <div class="user-menu-show-help"></div>
          <div class="user-menu">
            <!-- <div>修改密码</div> -->
            <div v-on:click="logout">退出</div>
          </div>
        </div>
      </template>
    </vs-navbar>

    <!-- 登陆弹窗 -->
    <vs-dialog class="login-dialog" blur v-model="loginDialog">
      <div class="login-header">
        <h3><b>Login</b></h3>
      </div>

      <div class="login-form">
        <vs-input v-model="user.username" placeholder="username">
          <template #icon> @ </template>
        </vs-input>

        <vs-input
          type="password"
          v-model="user.password"
          placeholder="Password"
        >
          <template #icon>
            <i class="iconfont icon-a-miyuemima"></i>
          </template>
        </vs-input>
      </div>

      <div class="login-footer">
        <vs-button block v-on:click="login()"> Sign In </vs-button>
      </div>
    </vs-dialog>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      select: "home",
      switchModel: false,
      loginDialog: false,
      user: {
        username: "",
        password: "",
      },
      //登陆成功的用户信息
      userInfo: {},
    };
  },
  methods: {
    // 切换为暗黑主题
    changeDark() {
      document.documentElement.style.setProperty(
        "--Theme",
        "invert(1) hue-rotate(180deg)"
      );
    },
    // 切换为亮色主题
    changeLight() {
      document.documentElement.style.setProperty("--Theme", "null");
    },
    //页面跳转
    changeRouter(path) {
      this.$router.push(path);
    },
    //打开登陆弹窗
    showLoginDialog() {
      this.loginDialog = true;
      this.user = {
        username: "",
        password: "",
      };
    },
    //用户登陆
    login() {
      let that = this;
      axios({
        url: that.$store.state.urlPath + "/user/login",
        method: "POST",
        data: that.user,
      }).then(function (res) {
        let judgeResult = that.$store.state.judgeResult(res);
        that.$vs.notification({
          square: true,
          flat: true,
          sticky: true,
          position: "bottom-right",
          progress: "auto",
          color: judgeResult ? "success" : "danger",
          text: judgeResult ? "登陆成功" : res.data.message,
        });
        if (judgeResult) {
          that.loginDialog = false;
          that.$store.state.token = res.data.data.token;
          that.$store.state.isLogin = true;
          that.userInfo = res.data.data.userInfo;
          window.localStorage.setItem("user", JSON.stringify(res.data.data));
        }
      });
    },
    // 用户退出
    logout() {
      let that = this;
      axios({
        url: that.$store.state.urlPath + "/user/logout",
        method: "POST",
        headers: {
          token: that.$store.state.token,
        },
      }).then(function (res) {
        let judgeResult = that.$store.state.judgeResult(res);
        that.$vs.notification({
          square: true,
          flat: true,
          sticky: true,
          position: "bottom-right",
          progress: "auto",
          color: judgeResult ? "success" : "danger",
          text: judgeResult ? "退出成功" : res.data.message,
        });
        if (judgeResult) {
          that.$store.state.token = "";
          that.$store.state.isLogin = false;
          that.userInfo = {};
          window.localStorage.removeItem("user");
        }
      });
    },
  },
  watch: {
    switchModel: {
      handler(newVal, oldVal) {
        if (newVal) {
          this.changeDark();
        } else {
          this.changeLight();
        }
      },
    },
  },
  mounted() {
    let user_json = window.localStorage.getItem("user");
    let user = null;
    if (user_json != null) {
      user = JSON.parse(user_json);
      this.$store.state.token = user.token;
      this.$store.state.isLogin = true;
      this.userInfo = user.userInfo;
    }
  },
};
</script>

<style>
.nav {
  width: 100%;
  height: 100%;
  overflow: hidden;
  position: absolute !important;
}
/* 导航 */
.vs-navbar-content {
  height: 50px !important;
  /* 毛玻璃 */
  background: rgba(255, 255, 255, 0.2) !important;
  backdrop-filter: saturate(180%) blur(20px) !important;
}
.vs-navbar {
  height: 100%;
  /* color: white !important; */
  color: black !important;
}
/* 导航下划线 */
.vs-navbar__line {
  /* background: white !important; */
  background: black !important;
}

/* logo */
.logo {
  /* 字体 ：阿里 */
  font-family: "ali-Regular";
  font-size: 25px;
  font-weight: bold;
  user-select: none;
}
.logo:hover {
  cursor: pointer;
}
/* 切换主题开关 */
.modelSwitch {
  margin-right: 20px;
}

/* 切换导航栏样式(未使用) */
.change-navigate-style {
  background-color: rgba(0, 0, 0, 0.2) !important;
}
.change-navigate-font-color-style {
  color: white !important;
}

/* 登陆弹窗 */
.login-dialog .vs-dialog .vs-dialog__content {
  display: flex;
  flex-direction: column;
  margin-top: 10px;
  margin-bottom: 10px;
}
/* 登陆弹窗-头部 */
.login-dialog .vs-dialog .vs-dialog__content .login-header {
  display: flex;
  height: 30px;
  width: 100%;
  justify-content: center;
  align-items: center;
  font-family: "ali-Regular";
}
.login-dialog .vs-dialog .vs-dialog__content .login-form {
  margin-top: 10px;
  margin-bottom: 10px;
}
/* 用户名输入框 */
.login-dialog .vs-dialog .vs-dialog__content .login-form .vs-input-parent {
  margin-top: 10px;
  margin-bottom: 10px;
}
.login-dialog .vs-dialog .vs-dialog__content .login-form .vs-input {
  width: 100%;
  height: 100%;
}
/* 修改密码/退出 菜单 */
.nav .vs-navbar__right .username-div {
  display: flex;
  position: relative;
  height: 50px;
  width: 100px;
  justify-content: center;
  align-items: center;
}
/* 具体用户名 */
.nav .vs-navbar__right .username-div span {
  /* 超出内容省略 */
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
}
/* hover显示下拉菜单 */
.nav .vs-navbar__right .username-div:hover .user-menu,
.nav .vs-navbar__right .username-div .user-menu:hover .user-menu {
  display: block;
}
/* 中间缝隙不影响显示下拉菜单 */
.nav .vs-navbar__right .username-div .user-menu-show-help {
  position: absolute;
  height: 5px;
  width: 100px;
  top: 50px;
}
/* 下拉的具体菜单 */
.nav .vs-navbar__right .username-div .user-menu {
  position: absolute;
  height: auto;
  width: 100px;
  top: 55px;
  background: white;
  border-radius: 10px;
  /* 毛玻璃 */
  background: rgba(255, 255, 255, 0.2) !important;
  backdrop-filter: saturate(180%) blur(20px) !important;
  display: none;
  overflow: hidden;
}
/* 菜单内容（选项） */
.nav .vs-navbar__right .username-div .user-menu div {
  display: flex;
  height: 40px;
  justify-content: center;
  align-items: center;
}
.nav .vs-navbar__right .username-div .user-menu div:hover {
  background: rgba(20, 20, 20, 0.2) !important;
  cursor: pointer;
}
</style>
