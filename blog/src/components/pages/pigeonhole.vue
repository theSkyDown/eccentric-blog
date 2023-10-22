<!-- 

    归档

 -->
<template>
  <div class="pigeonhole">
    <div class="pigeonhole-img">
      <img src="../../assets/img/xllg2z.jpg" draggable="false" />
      <h1>归 档</h1>
    </div>
    <div class="pigeonhole-detail">
      <div class="pigeonhole-card">
        <ul>
          <li
            v-for="item in articleList"
            v-on:click="toArticleDetailPage(item.id)"
          >
            <div class="bullet"></div>
            <div class="date">{{ item.createTime }}</div>
            <div class="desc">
              <vs-avatar class="pigeonhole-article-img">
                <img :src="item.thumbnail" alt="" />
              </vs-avatar>
              <div class="pigeonhole-article-desc">
                <h2>{{ item.title }}</h2>
                <h4>{{ item.summary }}</h4>
              </div>
            </div>
          </li>
        </ul>
        <!-- 分页 -->
        <div class="pagination">
          <vs-pagination
            infinite
            v-model="page"
            :length="Math.ceil(total / size)"
          />
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
      articleList: [],
      //分页参数
      page: 1, //当前页数
      num: 1, //从第几条开始算
      size: 10, //每页多少条数据
      total: 0, //总共多少条数据
    };
  },
  watch: {
    page: {
      handler(newPage, oldVal) {
        this.num = (newPage - 1) * this.size + 1;
        this.list();
      },
    },
  },
  methods: {
    //获取文章列表，通过时间进行排序
    list() {
      let that = this;
      let loading = this.$vs.loading({
        type: "square",
        text: "loading...",
      });
      axios({
        url: that.$store.state.urlPath + "/article/pigeonholeList",
        method: "GET",
        params: {
          pageNum: that.page,
          pageSize: that.size,
        },
      }).then(function (res) {
        if (that.$store.state.judgeResult(res)) {
          that.articleList = res.data.data;
          that.total = res.data.count;
          loading.close();
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
    },
  },
  mounted() {
    this.list();
  },
};
</script>

<style>
.pigeonhole {
  display: flex;
  width: 100%;
  height: auto;
  overflow: hidden;
  flex-direction: column;
  align-items: center;
}
/* 背景图片 */
.pigeonhole .pigeonhole-img {
  position: relative;
  height: 70vh;
  width: 100%;
  overflow: hidden;
}
.pigeonhole .pigeonhole-img img {
  position: absolute;
  height: 120%;
  width: 100%;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
/* 背景文字 */
.pigeonhole .pigeonhole-img h1 {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-family: "ali-Regular";
  font-size: 40px;
  color: white;
  background-size: 80%;
  user-select: none;
}
/* 详细内容部分 */
.pigeonhole .pigeonhole-detail {
  display: flex;
  width: 70%;
  height: 1300px;
  overflow: hidden;
  flex-direction: column;
}
/* 详细内容卡片 */
.pigeonhole .pigeonhole-detail .pigeonhole-card {
  display: flex;
  flex: 1;
  margin: 20px;
  background: #f6f7f8;
  box-shadow: 0px 5px 20px 0px rgba(0, 0, 0, var(--vs-shadow-opacity));
  border-radius: 20px;
  overflow: hidden;
  flex-direction: column;
}
/* 时间线列表 */
.pigeonhole .pigeonhole-detail .pigeonhole-card ul {
  display: flex;
  padding: 5em 0 0 5em;
  width: 90%;
  margin: 0;
  list-style: none;
  position: relative;
  flex: 9;
  flex-direction: column;
  overflow: hidden;
}
/* 左侧直线 */
.pigeonhole .pigeonhole-detail .pigeonhole-card ul::before {
  content: " ";
  height: 100%;
  width: 5px;
  background-color: #d9d9d9;
  position: absolute;
  top: 5em;
  left: 6em;
}
.pigeonhole .pigeonhole-detail .pigeonhole-card ul li {
  height: 120px;
  position: relative;
}
.pigeonhole .pigeonhole-detail .pigeonhole-card ul li:hover {
  cursor: pointer;
  opacity: 0.8;
}
/* 小圆圈 */
.pigeonhole .pigeonhole-detail .pigeonhole-card ul .bullet {
  position: absolute;
  left: 0.6em;
  top: 50%;
  transform: translateY(-50%);
  width: 1rem;
  height: 1rem;
  box-sizing: border-box;
  border-radius: 50%;
  background-color: lightseagreen;
  border: 3px solid #b0e8e2;
  z-index: 2;
}
/* 时间 */
.pigeonhole .pigeonhole-detail .pigeonhole-card ul li .date {
  position: absolute;
  left: 2.3em;
  top: 50%;
  transform: translateY(-50%);
  width: 13%;
}
/* 文章信息 */
.pigeonhole .pigeonhole-detail .pigeonhole-card ul li .desc {
  position: absolute;
  left: 17%;
  width: 75%;
  height: 100%;
  display: flex;
}
/* 文章图片 */
.pigeonhole
  .pigeonhole-detail
  .pigeonhole-card
  ul
  li
  .desc
  .pigeonhole-article-img {
  display: flex;
  width: 120px;
  height: 100%;
  align-items: center;
}
.pigeonhole
  .pigeonhole-detail
  .pigeonhole-card
  ul
  li
  .desc
  .pigeonhole-article-img
  .vs-avatar {
  width: 100px;
  height: 100px;
}
.pigeonhole
  .pigeonhole-detail
  .pigeonhole-card
  ul
  li
  .desc
  .pigeonhole-article-img
  .vs-avatar
  img {
  width: 100%;
  height: 100%;
}

/* 文章描述 */
.pigeonhole
  .pigeonhole-detail
  .pigeonhole-card
  ul
  li
  .desc
  .pigeonhole-article-desc {
  display: flex;
  flex: 1;
  height: 100%;
  flex-direction: column;
}

/* 文章标题 */
.pigeonhole
  .pigeonhole-detail
  .pigeonhole-card
  ul
  li
  .desc
  .pigeonhole-article-desc
  h2 {
  font-family: "ali-Regular";
  margin-top: 10px;
  /* 超出内容省略 */
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
}
/* 文章内容 */
.pigeonhole .pigeonhole-detail .pigeonhole-card ul li h4 {
  margin-top: 5px;
  font-size: 0.8em;
  color: #808080;
}

/* 分页 */
.pigeonhole .pigeonhole-detail .pigeonhole-card .pagination {
  display: flex;
  width: 100%;
  flex: 1;
  justify-content: center;
}
</style>
