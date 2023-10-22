<template>
  <div class="tag">
    <div class="tag-img">
      <img src="../../assets/img/l3zmwy.jpg" draggable="false" />
      <h1>标 签</h1>
    </div>
    <div class="tag-detail">
      <!-- 标签卡片 -->
      <div class="tag-tag-card">
        <div class="tag-tag-item-div" v-for="item in tagList">
          <div
            class="tag-tag-item"
            :class="getTagTagItemClass(item)"
            v-on:click="changeTagTagItemSelect(item)"
          >
            {{ item.name }}
          </div>
        </div>
      </div>
      <!-- 选择标签后的对应文章列表的卡片 -->
      <div class="tag-list-card" ref="content">
        <!-- 文章item -->
        <div
          class="tag-article-item-div"
          v-for="(item, index) in articleList"
          v-on:click="toArticleDetailPage(item.id)"
        >
          <div class="tag-article-item">
            <div class="tag-article-item-img">
              <img
                src="../../assets/img/default-img.png"
                v-if="item.thumbnail == null || item.thumbnail == ''"
              />
              <img :src="item.thumbnail" v-else />
            </div>
            <div class="tag-article-item-title">
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
      tagTagItemSelect: "",
      //标签列表
      tagList: [],
      //文章列表
      articleList: [],
    };
  },
  methods: {
    // 获取标签标签中每个tag的class
    getTagTagItemClass(item) {
      let list = [];
      if (item.id == this.tagTagItemSelect) {
        list.push("tag-tag-item-select");
      }
      return list;
    },
    //获取列表
    list() {
      let that = this;
      axios({
        url: that.$store.state.urlPath + "/tag/list",
        method: "GET",
      }).then(function (res) {
        if (that.$store.state.judgeResult(res)) {
          that.tagList = res.data.data;
          //选中第一个标签
          that.tagTagItemSelect = that.tagList[0].id;
        }
      });
    },
    //获取文章列表根据标签信息
    getArticleListByTag() {
      let that = this;
      let loading = this.$vs.loading({
        type: "square",
        text: "loading...",
      });
      axios({
        url: that.$store.state.urlPath + "/article/list",
        method: "GET",
        params: {
          tagId: that.tagTagItemSelect,
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
    //切换标签标签
    changeTagTagItemSelect(item) {
      this.tagTagItemSelect = item.id;
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
    tagTagItemSelect: {
      handler(newVal, oldVal) {
        this.getArticleListByTag();
      },
    },
  },
  mounted() {
    this.list();
  },
};
</script>

<style>
.tag {
  display: flex;
  width: 100%;
  height: auto;
  overflow: hidden;
  flex-direction: column;
  align-items: center;
}
/* 背景图片 */
.tag .tag-img {
  position: relative;
  height: 70vh;
  width: 100%;
  overflow: hidden;
}
.tag .tag-img img {
  position: absolute;
  height: 120%;
  width: 100%;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
/* 背景文字 */
.tag .tag-img h1 {
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
.tag .tag-detail {
  display: flex;
  flex: 1;
  width: 70%;
  overflow: hidden;
  flex-direction: column;
}
/* 标签标签卡片 */
.tag .tag-detail .tag-tag-card {
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
/* 标签标签所处的div */
.tag .tag-detail .tag-tag-card .tag-tag-item-div {
  display: flex;
  height: 70px;
  width: auto;
  margin-left: 15px;
  margin-right: 15px;
  justify-content: center;
  align-items: center;
}
/* 具体的标签标签 */
.tag .tag-detail .tag-tag-card .tag-tag-item-div .tag-tag-item {
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
.tag .tag-detail .tag-tag-card .tag-tag-item-div .tag-tag-item:hover {
  cursor: pointer;
  background: #e0e2e4;
}
/* 标签标签选中的样式 */
.tag .tag-detail .tag-tag-card .tag-tag-item-div .tag-tag-item-select {
  background: #195bff;
}
.tag .tag-detail .tag-tag-card .tag-tag-item-div .tag-tag-item-select:hover {
  background: #195bff;
}

/* 标签列表卡片 */
.tag .tag-detail .tag-list-card {
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
.tag .tag-detail .tag-list-card .tag-article-item-div {
  display: flex;
  width: 25%;
  height: 300px;
  justify-content: center;
  align-items: center;
}
.tag .tag-detail .tag-list-card .tag-article-item-div .tag-article-item {
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
.tag .tag-detail .tag-list-card .tag-article-item-div .tag-article-item:hover {
  cursor: pointer;
  transform: scale(1.04);
  transition: all 0.4s ease 0s;
  -webkit-transform: scale(1.04);
}
.tag
  .tag-detail
  .tag-list-card
  .tag-article-item-div
  .tag-article-item
  .tag-article-item-img {
  position: relative;
  width: 100%;
  height: 75%;
  overflow: hidden;
}
.tag
  .tag-detail
  .tag-list-card
  .tag-article-item-div
  .tag-article-item
  .tag-article-item-img
  img {
  position: absolute;
  height: 100%;
  width: 100%;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.tag
  .tag-detail
  .tag-list-card
  .tag-article-item-div
  .tag-article-item
  .tag-article-item-title {
  display: flex;
  height: 25%;
  text-indent: 15px;
  align-items: center;
  font-size: 1.2em;
  font-weight: bold;
  font-family: "ali-Regular";
}
.tag
  .tag-detail
  .tag-list-card
  .tag-article-item-div
  .tag-article-item
  .tag-article-item-title
  div {
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}
</style>
