<!-- 

  主页

 -->
<template>
  <div class="home">
    <div class="content-img">
      <img src="../../assets/img/background.jpg" draggable="false" />
      <h1 data-text="Eccentric">Eccentric</h1>
    </div>
    <!-- 文章列表/侧边栏 -->
    <div class="content-detail">
      <div class="article-list">
        <!-- 文章卡片 -->
        <vs-card
          class="article-item"
          type="3"
          v-for="item in articleList"
          v-bind:key="item.id"
          v-on:click="toArticleDetailPage(item.id)"
        >
          <template #title>
            <h2 style="font-family: 'ali-Regular'">{{ item.title }}</h2>
          </template>
          <template #img>
            <img
              src="../../assets/img/default-img.png"
              v-if="item.thumbnail == null || item.thumbnail == ''"
            />
            <img :src="item.thumbnail" v-else />
          </template>
          <template #text>
            <p>{{ item.summary }}</p>
          </template>
          <template #interactions>
            <!-- 访问量/评论数量 -->
            <vs-button class="btn-chat" shadow primary>
              <span class="span">
                <i class="iconfont icon-liulanliang"></i>
                {{ item.viewCount > 999 ? "999+" : item.viewCount }}
              </span>
              &nbsp;&nbsp;&nbsp;
              <!-- <i class="iconfont icon-pinglun1"></i>
              <span class="span"> 54 </span> -->
            </vs-button>
          </template>
        </vs-card>

        <!-- 分页 -->
        <div class="pagination">
          <vs-pagination
            infinite
            v-model="page"
            :length="Math.ceil(total / size)"
          />
        </div>
      </div>

      <!-- 侧边栏 -->
      <div class="article-aside">
        <!-- 头像和个人基本信息 -->
        <div class="avatar-card">
          <div class="avatar-info">
            <!-- 头像图片 -->
            <div class="avatar-img">
              <img src="../../assets/img/avatar.png" alt="" />
            </div>
            <!-- 名称 -->
            <div class="info-name">
              <span>Eccentric</span>
            </div>
            <!-- 签名 -->
            <div class="info-signature">
              <span>这是签名</span>
            </div>
          </div>
          <!-- 文章/分类/标签 -->
          <div class="article-category-tag">
            <div class="cursor-item">
              <h3>文章</h3>
              <div>{{ articleCount }}</div>
            </div>
            <div class="cursor-item">
              <h3>分类</h3>
              <div>{{ categoryCount }}</div>
            </div>
            <div class="cursor-item">
              <h3>标签</h3>
              <div>{{ tagCount }}</div>
            </div>
          </div>
          <!-- 社交logo -->
          <div class="socializing-logo">
            <a href="https://github.com/theSkyDown" target="_blank"
              ><i class="iconfont icon-github-fill"></i
            ></a>
            <a href=""
              ><i style="color: #fc5430" class="iconfont icon-gmail"></i
            ></a>
            <a href=""
              ><i style="color: #1296db" class="iconfont icon-QQ"></i
            ></a>
          </div>
        </div>

        <!-- 热门文章卡片 -->
        <div class="hot-card">
          <div class="hot-card-header">
            <h2>
              <i
                class="iconfont icon-caiyouduo_liulanliang"
                style="color: #fc5430"
              ></i>
              热门文章
            </h2>
          </div>
          <div class="hot-article-list">
            <div
              v-for="(item, index) in hotAricleList"
              v-bind:key="item.id"
              class="hot-article-item"
              v-on:click="toArticleDetailPage(item.id)"
            >
              <div class="hot-article-header">
                {{ 1 + index + "." + item.title }}
              </div>
              <div class="hot-article-viewCount-tag">
                <i
                  style="margin-left: 3px"
                  class="iconfont icon-liulanliang"
                ></i>
                {{ item.viewCount > 999 ? "999+" : item.viewCount }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      //主页文章
      articleList: [],
      //热门文章列表
      hotAricleList: [],
      //分页参数
      page: 1, //当前页数
      num: 1, //从第几条开始算
      size: 4, //每页多少条数据
      total: 0, //总共多少条数据
      //文章数量
      articleCount: 0,
      //分类数量
      categoryCount: 0,
      //标签数量
      tagCount: 0,
    };
  },
  methods: {
    list() {
      let that = this;
      let loading = this.$vs.loading({
        type: "square",
        text: "loading...",
      });
      axios({
        url: that.$store.state.urlPath + "/article/list",
        method: "GET",
        params: {
          pageNum: that.num,
          pageSize: that.size,
          categoryId: "",
        },
      }).then(function (res) {
        if (that.$store.state.judgeResult(res)) {
          that.articleList = res.data.data;
          that.total = res.data.count;
          loading.close();
        }
      });
    },
    //获取热门文章列表
    getHotArticleList() {
      let that = this;
      axios({
        url: that.$store.state.urlPath + "/article/hotArticleList",
        method: "GET",
      }).then(function (res) {
        if (that.$store.state.judgeResult(res)) {
          that.hotAricleList = res.data.data;
        }
      });
    },
    //获取文章的数量
    getArticleCount() {
      let that = this;
      axios({
        url: that.$store.state.urlPath + "/article/articleCount",
        method: "GET",
      }).then(function (res) {
        if (that.$store.state.judgeResult(res)) {
          that.articleCount = res.data.data;
        }
      });
    },
    //获取分类的数量
    getCategoryCount() {
      let that = this;
      axios({
        url: that.$store.state.urlPath + "/category/categoryCount",
        method: "GET",
      }).then(function (res) {
        if (that.$store.state.judgeResult(res)) {
          that.categoryCount = res.data.data;
        }
      });
    },
    //获取标签数量
    getTagCount() {
      let that = this;
      axios({
        url: that.$store.state.urlPath + "/tag/tagCount",
        method: "GET",
      }).then(function (res) {
        if (that.$store.state.judgeResult(res)) {
          that.tagCount = res.data.data;
        }
      });
    },
    //跳转到文章具体内容
    toArticleDetailPage(id) {
      this.$router.push({
        path: "/article",
        query: {
          id: id,
        },
      });
      // this.$router.push("/article/" + id);
    },
  },
  watch: {
    page: {
      handler(newPage, oldVal) {
        this.num = (newPage - 1) * this.size + 1;
        this.list();
      },
    },
  },
  mounted() {
    this.list();
    //获取热门文章
    this.getHotArticleList();
    //获取文章数量
    this.getArticleCount();
    //获取分类数量
    this.getCategoryCount();
    //获取标签数量
    this.getTagCount();
  },
};
</script>

<style>
.home {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

/* 背景图片 */
.content-img {
  position: relative;
  height: 100vh !important;
  width: 100%;
  overflow: hidden;
}
.content-img img {
  position: absolute;
  height: 120%;
  width: 100%;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
/* 背景文字 */
.content-img h1 {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-family: "ali-Regular";
  font-size: 70px;
  color: white;
  background-size: 80%;
  user-select: none;
}
/* 故障风格 */
.content-img h1::before {
  content: attr(data-text);
  position: absolute;
  text-shadow: 2px 0 red;
  left: -2px;
  clip-path: inset(50px 0 0 0);
  animation: animation-before 2.5s infinite linear alternate-reverse;
}
.content-img h1::after {
  content: attr(data-text);
  position: absolute;
  text-shadow: 2px 0 blue;
  right: -2px;
  clip-path: inset(50px 0 0 0);
  animation: animation-after 2.5s infinite linear alternate-reverse;
}

/* 文章列表/侧边栏 */
.content-detail {
  width: 60%;
  height: 1050px;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
/* 文章列表 */
.article-list {
  position: relative;
  width: 600px;
  height: 100%;
  overflow: hidden;
}
/* 侧边栏 */
.article-aside {
  width: 400px;
  height: 100%;
  overflow: hidden;
}

/* 文章卡片样式 */
.content-detail .article-list .article-item {
  height: 230px;
  width: 100%;
  padding: 20px;
  margin-top: 10px;
  overflow: hidden !important;
  display: flex;
  justify-content: center;
  align-items: center;
}
.content-detail .article-list .article-item .vs-card {
  height: 190px !important;
  width: 550px !important;
  background: white;
  max-width: 900px;
  /* 辅助鼠标移动动画 */
  transition: all 0.4s ease 0s;
}
/* 实现鼠标移动到卡片上的动画 */
.content-detail .article-list .article-item .vs-card:hover {
  transform: scale(1.04);
  transition: all 0.4s ease 0s;
  -webkit-transform: scale(1.04);
}
/* 文章粗略图 */
.content-detail .article-list .article-item .vs-card .vs-card__img {
  width: 350px;
  margin-left: 20px;
}
.content-detail .article-list .article-item .vs-card .vs-card__img img {
  width: 250px;
  height: 150px;
}

/* 文章粗略内容 */
.content-detail .article-list .article-item .vs-card .vs-card__text {
  width: 400px;
}

/* 超出范围省略 */
.content-detail .article-list .article-item .vs-card .vs-card__text h3 {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
}
.content-detail .article-list .article-item .vs-card .vs-card__text p {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 6;
  -webkit-box-orient: vertical;
}

/* 实现文章列表交错显示 */
.content-detail .article-list .article-item:nth-child(2n) .vs-card {
  flex-direction: row-reverse;
}
.content-detail
  .article-list
  .article-item:nth-child(2n)
  .vs-card
  .vs-card__img {
  margin-left: 0;
  margin-right: 20px;
}

/* 分页 */
.content-detail .pagination {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  bottom: 20px;
  width: 70%;
}

/* 头像卡片 */
.article-aside .avatar-card {
  position: relative;
  width: 96%;
  height: 450px;
  margin: 33px auto;
  border-radius: 20px;
  background: white;
  box-shadow: 0px 5px 20px 0px rgba(0, 0, 0, var(--vs-shadow-opacity));
  overflow: hidden;
}

/* 头像信息 */
.article-aside .avatar-card .avatar-info {
  position: absolute;
  width: 95%;
  height: 250px;
  left: 50%;
  top: 15px;
  transform: translateX(-50%);
  overflow: hidden;
}

/* 头像图片 */
.article-aside .avatar-card .avatar-info .avatar-img {
  width: 160px;
  height: 160px;
  overflow: hidden;
  margin: 0 auto;
  border-radius: 100%;
  border: 5px solid grey;
}
.article-aside .avatar-card .avatar-info .avatar-img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 信息名称 */
.article-aside .avatar-card .avatar-info .info-name {
  text-align: center;
  font-size: larger;
  font-weight: bolder;
  margin-top: 0.5rem;
}

/* 信息签名 */
.article-aside .avatar-card .avatar-info .info-signature {
  text-align: center;
  font-size: large;
  margin-top: 0.5rem;
}

/* 文章/分类/标签 */
.article-aside .avatar-card .article-category-tag {
  position: absolute;
  top: 300px;
  /* width: 60%; */
  width: 200px;
  height: 60px;
  left: 50%;
  transform: translateX(-50%);
  overflow: hidden;
}

.article-aside .avatar-card .article-category-tag > div {
  width: 33%;
  height: 100%;
  display: inline-block;
}
.article-aside .avatar-card .article-category-tag > div:nth-child(2) {
  border-left: 1px solid grey;
  border-right: 1px solid grey;
}
.article-aside .avatar-card .article-category-tag > div > h3,
.article-aside .avatar-card .article-category-tag > div > div {
  text-align: center;
}

/* 社交logo */
.article-aside .avatar-card .socializing-logo {
  position: absolute;
  bottom: 10px;
  width: 100%;
  height: 80px;
  text-align: center;
  line-height: 80px;
}
.article-aside .avatar-card .socializing-logo i {
  margin: 10px;
  font-size: 30px;
}

/* 链接样式 */
.article-aside .avatar-card .socializing-logo a {
  text-decoration: none;
}
.article-aside .avatar-card .socializing-logo a:hover {
  opacity: 0.5;
}
.article-aside .avatar-card .socializing-logo a:link,
.article-aside .avatar-card .socializing-logo a:visited,
.article-aside .avatar-card .socializing-logo a:visited {
  color: black;
}

/* 热门文章样式 */
.article-aside .hot-card {
  position: relative;
  width: 96%;
  height: 400px;
  margin: 0 auto;
  border-radius: 20px;
  background: white;
  box-shadow: 0px 5px 20px 0px rgba(0, 0, 0, var(--vs-shadow-opacity));
  overflow: hidden;
}

/* 热门文章中的标题 */
.article-aside .hot-card .hot-card-header {
  /* 字体 ：阿里 */
  font-family: "ali-Regular";
  text-align: center;
  margin-top: 15px;
  font-size: 20px;
  height: 35px;
  overflow: hidden;
}
.article-aside .hot-card .hot-card-header .icon-caiyouduo_liulanliang {
  font-size: 25px;
}
/* 热门文章列表 */
.article-aside .hot-card .hot-article-list {
  height: 300px;
  width: 95%;
  margin: 20px auto;
}
/* 每一个热门文章div */
.article-aside .hot-card .hot-article-list .hot-article-item {
  position: relative;
  height: 30px;
  width: 300px;
  margin: 0 auto;
  overflow: hidden;
}
.article-aside .hot-card .hot-article-list .hot-article-item:hover {
  cursor: pointer;
  opacity: 0.6;
}
/* 每一个热门文章标题 */
.article-aside
  .hot-card
  .hot-article-list
  .hot-article-item
  .hot-article-header {
  position: absolute;
  left: 0;
  height: 100%;
  width: 70%;
  overflow: hidden;
  font-size: 17px;
  /* 超出内容省略 */
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
}
/* 每一个热门文章的访问量tag */
.article-aside
  .hot-card
  .hot-article-list
  .hot-article-item
  .hot-article-viewCount-tag {
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 60px;
  height: 80%;
  border: 1px solid #1a5cff;
  border-radius: 7px;
  background: #eaeefa;
}

/* 故障风格动画 */
@keyframes animation-before {
  0% {
    clip-path: inset(0 0 0 0);
  }

  5% {
    clip-path: inset(0.8em 0 0.4em 0);
  }

  10% {
    clip-path: inset(0.4em 0 0.8em 0);
  }

  15% {
    clip-path: inset(0.1em 0 1em 0);
  }

  20% {
    clip-path: inset(0.3em 0 0.6em 0);
  }

  25% {
    clip-path: inset(0.6em 0 0.3em 0);
  }

  30% {
    clip-path: inset(0.8em 0 0.5em 0);
  }

  35% {
    clip-path: inset(1em 0 0.1em 0);
  }

  40% {
    clip-path: inset(0.7em 0 0.35em 0);
  }

  45% {
    clip-path: inset(0.5em 0 0.2em 0);
  }

  50% {
    clip-path: inset(0.2em 0 0.5em 0);
  }

  55% {
    clip-path: inset(0.35em 0 0.7em 0);
  }

  60% {
    clip-path: inset(0.1em 0 0.9em 0);
  }

  65% {
    clip-path: inset(0.8em 0 0.46em 0);
  }

  70% {
    clip-path: inset(0.66em 0 0.33em 0);
  }

  75% {
    clip-path: inset(0.48em 0 0.23em 0);
  }

  80% {
    clip-path: inset(0.23em 0 0.48em 0);
  }

  85% {
    clip-path: inset(0.39em 0 0.79em 0);
  }

  90% {
    clip-path: inset(0.33em 0 0.66em 0);
  }

  95% {
    clip-path: inset(1em 0 0.3em 0);
  }

  100% {
    clip-path: inset(0.62em 0 0.29em 0);
  }
}
@keyframes animation-after {
  0% {
    clip-path: inset(0 0 0 0);
  }

  5% {
    clip-path: inset(0.4em 0 0.8em 0);
  }

  10% {
    clip-path: inset(0.8em 0 0.4em 0);
  }

  15% {
    clip-path: inset(1em 0 0.1em 0);
  }

  20% {
    clip-path: inset(0.6em 0 0.3em 0);
  }

  25% {
    clip-path: inset(0.3em 0 0.6em 0);
  }

  30% {
    clip-path: inset(0.5em 0 0.8em 0);
  }

  35% {
    clip-path: inset(0.1em 0 1em 0);
  }

  40% {
    clip-path: inset(0.35em 0 0.7em 0);
  }

  45% {
    clip-path: inset(0.2em 0 0.5em 0);
  }

  50% {
    clip-path: inset(0.5em 0 0.2em 0);
  }

  55% {
    clip-path: inset(0.7em 0 0.35em 0);
  }

  60% {
    clip-path: inset(0.9em 0 0.1em 0);
  }

  65% {
    clip-path: inset(0.46em 0 0.8em 0);
  }

  70% {
    clip-path: inset(0.3em 0 0.66em 0);
  }

  75% {
    clip-path: inset(0.23em 0 0.48em 0);
  }

  80% {
    clip-path: inset(0.48em 0 0.23em 0);
  }

  85% {
    clip-path: inset(0.79em 0 0.39em 0);
  }

  90% {
    clip-path: inset(0.66em 0 0.33em 0);
  }

  95% {
    clip-path: inset(0.3em 0 1em 0);
  }

  100% {
    clip-path: inset(0.29em 0 0.62em 0);
  }
}
</style>
