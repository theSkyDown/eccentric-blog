<!-- 

  页脚

-->
<template>
  <div class="footer">
    <div class="footer-info">
      <div class="footer-site-time">© 2023 By Eccentric</div>
      <div class="footer-beian">
        <a href="https://beian.miit.gov.cn" target="_blank"
          >琼ICP备2023004081号</a
        >
      </div>
    </div>
  </div>
</template>

<script></script>

<style>
.footer {
  position: relative;
  width: 100%;
  /* height: 200px; */
  height: 100%;
  bottom: 0;
  left: 0;
  font-family: "ali-Regular";
}

/* 底部信息 */
.footer-info {
  width: 100%;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}
.footer-site-time,
.footer-beian {
  text-align: center;
  margin: 10px;
}
/* 链接样式 */
.footer-beian a {
  text-decoration: none;
}
.footer-beian a:hover {
  opacity: 0.5;
}
.footer-beian a:link,
.footer-beian a:visited,
.footer-beian a:visited {
  color: black;
}
</style>
