<template>
  <div class="article">
    <!-- 文章图片 -->
    <div class="article-img">
      <img
        src="../../assets/img/default-img.png"
        v-if="article.thumbnail == null || article.thumbnail == ''"
      />
      <img :src="article.thumbnail" v-else />
    </div>

    <!-- 文章卡片 -->
    <div class="article-card">
      <!-- 文章头部信息 -->
      <div class="article-header">
        <div class="article-title">
          <h1>{{ article.title }}</h1>
        </div>
        <div class="article-info">
          发布于:{{ article.createTime }} 浏览量:{{ article.viewCount }}
        </div>
      </div>

      <!-- 文章具体内容 -->
      <mavon-editor
        class="article-content"
        v-model="article.content"
        :subfield="false"
        defaultOpen="preview"
        :toolbarsFlag="false"
        :editable="false"
      ></mavon-editor>

      <!-- 文章页脚 -->
      <div class="article-footer">
        <!-- 分类标签div -->
        <div class="article-category-div">
          <div class="article-category-tag">
            <span>
              <i class="iconfont icon-category"></i>
              {{ article.categoryName }}
            </span>
          </div>
        </div>
        <!-- 上一篇文章和下一篇文章 -->
        <div class="article-around">
          <div class="article-before">
            <vs-button
              v-on:click="toOtherPage(true)"
              :disabled="aroundArticle.beforeArticle == null"
            >
              上一篇
              <template #animate>
                <i class="iconfont icon-a-danxiangzuo"></i>
              </template>
            </vs-button>
            <span>{{
              aroundArticle.beforeArticle != null
                ? aroundArticle.beforeArticle.title
                : ""
            }}</span>
          </div>
          <div class="article-after">
            <span>{{
              aroundArticle.afterArticle != null
                ? aroundArticle.afterArticle.title
                : ""
            }}</span>
            <vs-button
              v-on:click="toOtherPage(false)"
              :disabled="aroundArticle.afterArticle == null"
            >
              下一篇
              <template #animate>
                <i class="iconfont icon-a-danxiangyou"></i>
              </template>
            </vs-button>
          </div>
        </div>
      </div>

      <!-- 评论区 -->
      <div class="article-comment" v-if="article.isComment == '1'">
        <!-- 评论输入div -->
        <div class="article-comment-input-div">
          <textarea
            class="comment-textarea"
            :placeholder="textPlaceholder"
            v-model="commentObj.content"
            id="comment-textarea"
          ></textarea>
        </div>
        <!-- 评论发送按钮div -->
        <div class="article-comment-buttons-div">
          <vs-button
            size="large"
            dark
            flat
            animation-type="vertical"
            v-on:click="cancelReply()"
          >
            取消回复
          </vs-button>

          <vs-button
            size="large"
            flat
            success
            animation-type="vertical"
            v-on:click="sendComment()"
          >
            发送
          </vs-button>
        </div>
        <!-- 评论内容显示 -->
        <div class="articlee-comment-display-div">
          <div class="noComment" v-if="commentList.length <= 0">暂无评论</div>
          <div class="hasComments" v-else>
            <!-- 每一条评论 -->
            <div class="comment-item" v-for="item in commentList">
              <!-- 根评论 -->
              <div class="root-comment">
                <!-- 头像 -->
                <div class="comment-avatar">
                  <vs-avatar circle>
                    <img :src="item.avatar" />
                  </vs-avatar>
                </div>
                <!-- 其他信息 -->
                <div class="comment-other-info">
                  <!-- 名称 -->
                  <div class="comment-username">
                    {{ item.nickName }}
                  </div>
                  <!-- 评论内容 -->
                  <div class="comment-content">
                    {{ item.content }}
                  </div>
                  <!-- 评论时间 -->
                  <div class="comment-time">
                    <span>{{ item.createTime }}</span>
                    <div
                      class="reply-button"
                      v-on:click="
                        reply(item.id, item.id, item.createBy, item.nickName)
                      "
                    >
                      回复
                    </div>
                  </div>
                </div>
              </div>
              <!-- 回复列表 -->
              <div class="reply-comment" v-if="item.children.length > 0">
                <!-- 占位div -->
                <div class="occupied-div"></div>
                <!-- 具体回复列表 -->
                <div class="reply-comment-div">
                  <div class="reply-comment-item" v-for="temp in item.children">
                    <!-- 头像 -->
                    <div class="comment-avatar">
                      <vs-avatar circle>
                        <img :src="temp.avatar" />
                      </vs-avatar>
                    </div>
                    <!-- 其他信息 -->
                    <div class="comment-other-info">
                      <!-- 评论内容 -->
                      <div class="comment-content">
                        <span style="margin-right: 5px; color: #7c6c8f">{{
                          temp.nickName
                        }}</span>
                        <span>回复</span>
                        <span style="margin-left: 5px; color: #7c6c8f">{{
                          temp.toCommentUserName
                        }}</span>
                        <span>：</span>
                        <span>{{ temp.content }}</span>
                      </div>
                      <!-- 评论时间 -->
                      <div class="comment-time">
                        <span>{{ temp.createTime }}</span>
                        <div
                          class="reply-button"
                          v-on:click="
                            reply(
                              temp.rootId,
                              temp.id,
                              temp.createBy,
                              temp.nickName
                            )
                          "
                        >
                          回复
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- 加载更多 -->
            <div class="more-comment">
              <vs-button
                flat
                animation-type="vertical"
                v-on:click="loadMoreComment()"
              >
                加载更多
              </vs-button>
            </div>
          </div>
        </div>
      </div>
      <!-- 关闭评论区显示内容 -->
      <div class="article-comment-disable" v-if="article.isComment == '0'">
        <span>作者关闭了评论</span>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import "highlight.js/styles/monokai-sublime.css"; // 引入高亮样式 这里我用的是sublime样式

export default {
  data() {
    return {
      article: {},
      aroundArticle: {},
      commentList: [],
      // 评论分页参数
      commentPage: 1,
      commentNum: 1,
      commentSize: 5,
      commentTotle: 0,
      //评论该文章的对象
      commentObj: {
        articleId: -1,
        content: "",
        rootId: -1,
        toCommentId: -1,
        toCommentUserId: -1,
        type: "0",
      },
      //输入框提示
      textPlaceholder: "请输入评论内容",
    };
  },
  //进入页面时调用
  // beforeRouteEnter(to, from, next) {
  //   next((vm) => {
  //     vm.reload();
  //   });
  // },
  methods: {
    getArticleDetail(id) {
      let that = this;
      axios({
        url: that.$store.state.urlPath + "/article/" + id,
        method: "GET",
      }).then(function (res) {
        if (that.$store.state.judgeResult(res)) {
          that.article = res.data.data;
        }
      });
    },
    //更新流量
    updateViewCount(id) {
      let that = this;
      axios({
        url: that.$store.state.urlPath + "/article/updateViewCount/" + id,
        method: "PUT",
      }).then(function (res) {});
    },
    //获取文章的上一篇和下一篇
    getAroundArticle(id) {
      let that = this;
      axios({
        url: that.$store.state.urlPath + "/article/aroundArticle/" + id,
        method: "GET",
      }).then(function (res) {
        if (that.$store.state.judgeResult(res)) {
          that.aroundArticle = res.data.data;
        }
      });
    },
    //跳转到文章具体内容
    toOtherPage(isBefore) {
      let that = this;
      if (this.aroundArticle == null) {
        this.$vs.notification({
          square: true,
          flat: true,
          sticky: true,
          position: "bottom-right",
          progress: "auto",
          color: "danger",
          text: "参数异常",
        });
      }
      let id = null;
      if (isBefore && this.aroundArticle.beforeArticle != null) {
        id = this.aroundArticle.beforeArticle.id;
      } else if (!isBefore && this.aroundArticle.afterArticle != null) {
        id = this.aroundArticle.afterArticle.id;
      } else {
        this.$vs.notification({
          square: true,
          flat: true,
          sticky: true,
          position: "bottom-right",
          progress: "auto",
          color: "danger",
          text: "参数异常",
        });
      }
      // this.$router.push("/temp");
      this.$router.push({
        path: "/article",
        query: {
          id: id,
        },
      });
    },
    //获取评论列表
    getCommentList(id) {
      let that = this;
      axios({
        url: that.$store.state.urlPath + "/comment/list",
        method: "GET",
        params: {
          articleId: id,
          pageNum: that.commentNum,
          pageSize: that.commentSize,
        },
      }).then(function (res) {
        if (that.$store.state.judgeResult(res)) {
          that.commentList = res.data.data;
          that.commentTotle = res.data.count;
        }
      });
    },
    //发送评论
    sendComment() {
      //拦截空信息
      if (this.commentObj.content == "") {
        this.$vs.notification({
          square: true,
          flat: true,
          sticky: true,
          position: "bottom-right",
          progress: "auto",
          color: "danger",
          text: "请输入内容后在发送",
        });
        return;
      }

      let that = this;
      axios({
        url: that.$store.state.urlPath + "/comment/sendComment",
        method: "POST",
        data: that.commentObj,
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
          text: judgeResult ? "发送成功" : res.data.message,
        });
        if (judgeResult) {
          that.commentObj.content = "";
          that.getCommentList(that.$route.query.id);
        }
      });
    },
    //刷新
    reload() {
      window.scrollTo(0, 0);

      //获取文章具体内容
      this.getArticleDetail(this.$route.query.id);
      this.updateViewCount(this.$route.query.id);
      this.getAroundArticle(this.$route.query.id);
      this.getCommentList(this.$route.query.id);

      //将评论对象中的文章id修改
      this.commentObj.articleId = 1 * this.$route.query.id;
    },
    //回复按钮
    reply(rootId, toCommentId, toCommentUserId, toCommentUserName) {
      this.textPlaceholder = "回复 " + toCommentUserName;
      this.commentObj.rootId = rootId;
      this.commentObj.toCommentId = toCommentId;
      this.commentObj.toCommentUserId = toCommentUserId;
      //锚跳转
      document.getElementById("comment-textarea").scrollIntoView();
      window.scrollBy(0, -100); // 往上滚动100px
    },
    //取消回复
    cancelReply() {
      this.textPlaceholder = "请输入评论内容";
      this.commentObj.rootId = -1;
      this.commentObj.toCommentId = -1;
      this.commentObj.toCommentUserId = -1;
    },
    //加载更多评论
    loadMoreComment() {
      if (this.commentList.length === this.commentTotle) {
        this.$vs.notification({
          square: true,
          flat: true,
          sticky: true,
          position: "bottom-right",
          progress: "auto",
          color: "warning",
          text: "已经加载所有评论",
        });
        return;
      }
      this.commentSize = this.commentSize + 5;
      this.getCommentList(this.$route.query.id);
    },
  },
  mounted() {
    this.reload();
  },
  watch: {
    //监听query中的id是否发生变化
    $route(to, from) {
      this.reload();
    },
  },
};
</script>

<style>
.article {
  display: flex;
  width: 100%;
  height: auto;
  overflow: hidden;
  flex-direction: column;
  align-items: center;
}

.article .article-img {
  position: relative;
  height: 70vh;
  width: 100%;
  overflow: hidden;
}
.article .article-img img {
  position: absolute;
  height: 120%;
  width: 100%;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

/* 文章卡片 */
.article .article-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 70%;
  height: auto;
  box-shadow: 0px 5px 20px 0px rgba(0, 0, 0, var(--vs-shadow-opacity));
  border-radius: 20px;
  background: white;
  margin-top: 50px;
}

/* 文章头部信息 */
.article .article-card .article-header {
  position: relative;
  display: flex;
  width: 100%;
  height: 200px;
  flex-direction: column;
}
.article .article-card .article-header .article-title {
  display: flex;
  flex: 2;
  justify-content: center;
  align-items: flex-end;
}
.article .article-card .article-header .article-info {
  display: flex;
  flex: 1;
  justify-content: center;
  align-items: center;
}

/* 文章内容 */
.article .article-card .article-content {
  display: flex;
  flex-direction: column;
  width: 90%;
  overflow: hidden;
  margin-bottom: 50px;
}

/* 文章页脚 */
.article .article-card .article-footer {
  display: flex;
  width: 90%;
  height: 150px;
  border-top: 1px solid black;
  flex-direction: column;
}
.article .article-card .article-footer .article-category-div {
  display: flex;
  height: 50px;
  width: 100%;
  flex-direction: row;
  align-items: center;
}
.article
  .article-card
  .article-footer
  .article-category-div
  .article-category-tag {
  display: flex;
  width: auto;
  height: 30px;
  padding-left: 8px;
  padding-right: 8px;
  border: 1px solid #3bdec8;
  background: #dff7f4;
  border-radius: 10px;
  margin-left: 10px;
}
.article .article-card .article-footer .article-around {
  display: flex;
  flex: 1;
  flex-direction: row;
}
.article .article-card .article-footer .article-around div {
  flex: 1;
  display: flex;
  align-items: center;
}
.article .article-card .article-footer .article-around div span {
  /* 超出内容省略 */
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  margin: 10px;
}
.article .article-card .article-footer .article-around .article-after {
  justify-content: flex-end;
}

/* 文章评论区 */
.article .article-card .article-comment {
  width: 90%;
  height: auto;
  margin-bottom: 30px;
}

/* 关闭了评论区 */
.article .article-card .article-comment-disable {
  display: flex;
  width: 90%;
  height: 100px;
  flex-direction: column;
  margin-bottom: 50px;
  justify-content: center;
  align-items: center;
  background: #e9edf3;
  border-radius: 10px;
}

/* 评论区输入 */
.article .article-card .article-comment .article-comment-input-div {
  display: flex;
  height: auto;
  width: 100%;
  justify-content: center;
  align-items: center;
}
.article
  .article-card
  .article-comment
  .article-comment-input-div
  .comment-textarea {
  width: 100%;
  min-height: 100px;
  resize: vertical;
  outline: none;
  padding: 10px;
  font-size: 16px;
  border-radius: 10px;
  border: 1px solid black;
  background: white;
  height: 100px;
}

/* 评论区提交按钮 */
.article .article-card .article-comment .article-comment-buttons-div {
  display: flex;
  height: 70px;
  width: 100%;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;
}
.article .article-card .article-comment .article-comment-buttons-div button {
  width: 100px;
}

/* 评论区显示 */
.article .article-card .article-comment .articlee-comment-display-div {
  position: relative;
  display: flex;
  height: auto;
  width: 100%;
  flex-direction: column;
  border-radius: 10px;
  overflow: hidden;
}
/* 无评论时显示 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .noComment {
  height: 100px;
  width: 100%;
  text-align: center;
  line-height: 100px;
  background: #e9edf3;
}
/* 有评论时显示 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments {
  display: flex;
  width: 100%;
  height: auto;
  flex-direction: column;
}
/* 每条评论 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item {
  width: 100%;
  height: auto;
  border-bottom: 1px double #e3e5e7;
  margin-top: 10px;
}
/* 加载更多评论 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .more-comment {
  display: flex;
  width: 100%;
  height: 40px;
  margin-top: 20px;
  justify-content: center;
  align-items: center;
}

/* 根评论 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .root-comment {
  display: flex;
  width: 100%;
  background: white;
  flex-direction: row;
}
/* 头像 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .root-comment
  .comment-avatar {
  display: flex;
  height: 80px;
  width: 80px;
  justify-content: center;
  align-items: center;
}

.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .root-comment
  .comment-other-info {
  flex: 1;
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
}
/* 用户名称 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .root-comment
  .comment-other-info
  .comment-username {
  height: 30px;
  line-height: 30px;
  width: 100%;
  font-size: 14px;
  color: #7c6c8f;
}
/* 评论内容 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .root-comment
  .comment-other-info
  .comment-content {
  height: auto;
  width: 100%;
  font-size: 16px;
  /*这两行代码可以解决大部分场景下的换行问题*/
  word-break: break-all;
  word-wrap: break-word;
  /*但在有些场景中，还需要加上下面这行代码*/
  white-space: normal;
  margin: 5px;
  margin-left: 0;
}
/* 评论时间 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .root-comment
  .comment-other-info
  .comment-time {
  width: 100%;
  height: 25px;
  line-height: 25px;
  font-size: 13px;
  color: #aea0a1;
  display: flex;
  flex-direction: row;
}
/* 回复按钮 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .root-comment
  .comment-other-info
  .comment-time
  .reply-button {
  margin-left: 10px;
}
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .root-comment
  .comment-other-info
  .comment-time
  .reply-button:hover {
  cursor: pointer;
  color: #3bdec8;
}
/* 回复内容div */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .reply-comment {
  display: flex;
  margin-top: 5px;
  margin-bottom: 5px;
  flex-direction: row;
}
/* 占位div */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .reply-comment
  .occupied-div {
  width: 80px;
  height: 100%;
}
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .reply-comment
  .reply-comment-div {
  display: flex;
  flex: 1;
  flex-direction: column;
}
/* 每一条回复评论 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .reply-comment
  .reply-comment-div
  .reply-comment-item {
  display: flex;
  width: 100%;
  height: auto;
  border-top: 1px double #e3e5e7;
  margin-top: 5px;
  flex-direction: row;
}
/* 头像 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .reply-comment
  .reply-comment-div
  .reply-comment-item
  .comment-avatar {
  display: flex;
  height: 55px;
  width: 55px;
  justify-content: center;
  align-items: center;
}
/* 其他信息 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .reply-comment
  .reply-comment-div
  .reply-comment-item
  .comment-other-info {
  flex: 1;
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
}
/* 评论内容 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .reply-comment
  .reply-comment-div
  .reply-comment-item
  .comment-other-info
  .comment-content {
  height: auto;
  width: 100%;
  font-size: 14px;
  /*这两行代码可以解决大部分场景下的换行问题*/
  word-break: break-all;
  word-wrap: break-word;
  /*但在有些场景中，还需要加上下面这行代码*/
  white-space: normal;
  margin: 5px;
  margin-left: 0;
}

/* 评论时间 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .reply-comment
  .reply-comment-div
  .reply-comment-item
  .comment-other-info
  .comment-time {
  width: 100%;
  height: 25px;
  line-height: 25px;
  font-size: 13px;
  color: #aea0a1;
  display: flex;
  flex-direction: row;
}
/* 回复按钮 */
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .reply-comment
  .reply-comment-div
  .reply-comment-item
  .comment-other-info
  .comment-time
  .reply-button {
  margin-left: 10px;
}
.article
  .article-card
  .article-comment
  .articlee-comment-display-div
  .hasComments
  .comment-item
  .reply-comment
  .reply-comment-div
  .reply-comment-item
  .comment-other-info
  .comment-time
  .reply-button:hover {
  cursor: pointer;
  color: #3bdec8;
}
</style>
