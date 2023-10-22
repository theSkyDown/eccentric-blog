<template>
  <div class="category">
    <div class="category-img">
      <img src="../../assets/img/6k1dj7.jpg" draggable="false" />
      <h1>分 类</h1>
    </div>
    <div class="category-detail">
      <!-- 分类标签卡片 -->
      <div class="category-tag-card">
        <div class="category-tag-item-div" v-for="item in categoryList">
          <div
            class="category-tag-item"
            :class="getCategoryTagItemClass(item)"
            v-on:click="changeCategoryTagItemSelect(item)"
          >
            {{ item.name }}
          </div>
        </div>
      </div>
      <!-- 选择标签后的对应文章列表的卡片 -->
      <div class="category-list-card" ref="content">
        <!-- 文章item -->
        <div
          class="category-article-item-div"
          v-for="(item, index) in articleList"
          v-on:click="toArticleDetailPage(item.id)"
        >
          <div class="category-article-item">
            <div class="category-article-item-img">
              <img
                src="../../assets/img/default-img.png"
                v-if="item.thumbnail == null || item.thumbnail == ''"
              />
              <img :src="item.thumbnail" v-else />
            </div>
            <div class="category-article-item-title">
              <div>
                {{ item.title }}
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
      categoryTagItemSelect: "",
      //分类列表
      categoryList: [],
      //文章列表
      articleList: [],
    };
  },
  methods: {
    // 获取分类标签中每个tag的class
    getCategoryTagItemClass(item) {
      let list = [];
      if (item.id == this.categoryTagItemSelect) {
        list.push("category-tag-item-select");
      }
      return list;
    },
    //获取列表
    list() {
      let that = this;
      axios({
        url: that.$store.state.urlPath + "/category/list",
        method: "GET",
      }).then(function (res) {
        if (that.$store.state.judgeResult(res)) {
          that.categoryList = res.data.data;
          //选中第一个分类
          that.categoryTagItemSelect = that.categoryList[0].id;
        }
      });
    },
    //获取文章列表根据分类信息
    getArticleListByCategory() {
      let that = this;
      let loading = this.$vs.loading({
        type: "square",
        text: "loading...",
      });
      axios({
        url: that.$store.state.urlPath + "/article/list",
        method: "GET",
        params: {
          categoryId: that.categoryTagItemSelect,
          pageNum: 1,
          pageSize: 100,
        },
      }).then(function (res) {
        if (that.$store.state.judgeResult(res)) {
          that.articleList = res.data.data;
          loading.close();
        }
      });
    },
    //切换分类标签
    changeCategoryTagItemSelect(item) {
      this.categoryTagItemSelect = item.id;
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
  watch: {
    categoryTagItemSelect: {
      handler(newVal, oldVal) {
        this.getArticleListByCategory();
      },
    },
  },
  mounted() {
    this.list();
  },
};
</script>

<style>
.category {
  display: flex;
  width: 100%;
  height: auto;
  overflow: hidden;
  flex-direction: column;
  align-items: center;
}
/* 背景图片 */
.category .category-img {
  position: relative;
  height: 70vh;
  width: 100%;
  overflow: hidden;
}
.category .category-img img {
  position: absolute;
  height: 120%;
  width: 100%;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
/* 背景文字 */
.category .category-img h1 {
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
.category .category-detail {
  display: flex;
  flex: 1;
  width: 70%;
  overflow: hidden;
  flex-direction: column;
}
/* 分类标签卡片 */
.category .category-detail .category-tag-card {
  display: flex;
  flex: 1;
  margin: 20px;
  background: white;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-content: stretch;
  box-shadow: 0px 5px 20px 0px rgba(0, 0, 0, var(--vs-shadow-opacity));
  border-radius: 20px;
  overflow: hidden;
}
/* 分类标签所处的div */
.category .category-detail .category-tag-card .category-tag-item-div {
  display: flex;
  height: 70px;
  width: auto;
  margin-left: 15px;
  margin-right: 15px;
  justify-content: center;
  align-items: center;
}
/* 具体的分类标签 */
.category
  .category-detail
  .category-tag-card
  .category-tag-item-div
  .category-tag-item {
  display: flex;
  width: auto;
  padding-left: 15px;
  padding-right: 15px;
  height: 35px;
  background: #f6f7f8;
  border-radius: 8px;
  font-size: small;
  overflow: hidden;
  justify-content: center;
  align-items: center;
}
.category
  .category-detail
  .category-tag-card
  .category-tag-item-div
  .category-tag-item:hover {
  cursor: pointer;
  background: #e0e2e4;
}
/* 分类标签选中的样式 */
.category
  .category-detail
  .category-tag-card
  .category-tag-item-div
  .category-tag-item-select {
  background: #195bff;
}
.category
  .category-detail
  .category-tag-card
  .category-tag-item-div
  .category-tag-item-select:hover {
  background: #195bff;
}

/* 分类列表卡片 */
.category .category-detail .category-list-card {
  display: flex;
  flex: 8;
  margin: 20px;
  background: #f6f7f8;
  box-shadow: 0px 5px 20px 0px rgba(0, 0, 0, var(--vs-shadow-opacity));
  border-radius: 20px;
  overflow: scroll;
  flex-direction: row;
  justify-content: flex-start;
  align-content: flex-start;
  flex-wrap: wrap;
}
/* 文章item */
.category .category-detail .category-list-card .category-article-item-div {
  display: flex;
  width: 25%;
  height: 300px;
  justify-content: center;
  align-items: center;
}
.category
  .category-detail
  .category-list-card
  .category-article-item-div
  .category-article-item {
  display: flex;
  width: 85%;
  height: 85%;
  box-shadow: 0px 5px 20px 0px rgba(0, 0, 0, var(--vs-shadow-opacity));
  background: white;
  border-radius: 20px;
  /* 辅助鼠标移动动画 */
  transition: all 0.4s ease 0s;
  overflow: hidden;
  flex-direction: column;
}
.category
  .category-detail
  .category-list-card
  .category-article-item-div
  .category-article-item:hover {
  cursor: pointer;
  transform: scale(1.04);
  transition: all 0.4s ease 0s;
  -webkit-transform: scale(1.04);
}
.category
  .category-detail
  .category-list-card
  .category-article-item-div
  .category-article-item
  .category-article-item-img {
  position: relative;
  width: 100%;
  height: 75%;
  overflow: hidden;
}
.category
  .category-detail
  .category-list-card
  .category-article-item-div
  .category-article-item
  .category-article-item-img
  img {
  position: absolute;
  height: 100%;
  width: 100%;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.category
  .category-detail
  .category-list-card
  .category-article-item-div
  .category-article-item
  .category-article-item-title {
  display: flex;
  height: 25%;
  text-indent: 15px;
  align-items: center;
  font-size: 1.2em;
  font-weight: bold;
  font-family: "ali-Regular";
}
.category
  .category-detail
  .category-list-card
  .category-article-item-div
  .category-article-item
  .category-article-item-title
  div {
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}
</style>
