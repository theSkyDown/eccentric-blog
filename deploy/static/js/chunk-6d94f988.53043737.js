(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-6d94f988"],{"1b3b":function(t,e,r){"use strict";r.d(e,"a",(function(){return a})),r.d(e,"d",(function(){return o})),r.d(e,"b",(function(){return i})),r.d(e,"c",(function(){return l})),r.d(e,"e",(function(){return c}));var n=r("b775");function a(t){return Object(n["a"])({url:"/article/add",method:"post",data:t})}function o(t){return Object(n["a"])({url:"/article/list",method:"get",params:t})}function i(t){return Object(n["a"])({url:"/article/delete/"+t,method:"delete"})}function l(t){return Object(n["a"])({url:"/article/"+t,method:"get"})}function c(t){return Object(n["a"])({url:"/article/update",method:"put",data:t})}},4561:function(t,e,r){"use strict";r("b757")},"90fa":function(t,e,r){"use strict";r.d(e,"g",(function(){return d})),r.d(e,"f",(function(){return m})),r.d(e,"e",(function(){return f})),r.d(e,"a",(function(){return p})),r.d(e,"h",(function(){return h})),r.d(e,"c",(function(){return b})),r.d(e,"d",(function(){return g})),r.d(e,"b",(function(){return v}));var n=r("b775"),a=r("53ca"),o=(r("b64b"),r("fb6a"),r("d3b7"),r("3ca3"),r("ddb0"),r("2b3d"),r("9861"),r("caad"),r("2532"),r("bc3a")),i=r.n(o),l=r("5c96"),c=r("5f87");i.a.defaults.headers["Content-Type"]="application/json;charset=utf-8";var s=i.a.create({baseURL:"http://localhost:8989",responseType:"blob"});s.interceptors.request.use((function(t){var e=!1===(t.headers||{}).isToken;if(Object(c["a"])()&&!e&&(t.headers["token"]=Object(c["a"])()),"get"===t.method&&t.params){for(var r=t.url+"?",n=0,o=Object.keys(t.params);n<o.length;n++){var i=o[n],l=t.params[i],s=encodeURIComponent(i)+"=";if(null!==l&&"undefined"!==typeof l)if("object"===Object(a["a"])(l))for(var u=0,d=Object.keys(l);u<d.length;u++){var m=d[u];if(null!==l[m]&&"undefined"!==typeof l[m]){var f=i+"["+m+"]",p=encodeURIComponent(f)+"=";r+=p+encodeURIComponent(l[m])+"&"}}else r+=s+encodeURIComponent(l)+"&"}r=r.slice(0,-1),t.params={},t.url=r}return t}),(function(t){console.log(t),Promise.reject(t)})),s.interceptors.response.use((function(t){if(console.log(t),t.data){var e=new Blob([t.data]),r=t.headers["content-disposition"],n="test";r&&(n=window.decodeURI(t.headers["content-disposition"].split("=")[1],"UTF-8"));var a=window.URL.createObjectURL(e),o=document.createElement("a");o.style.display="none",o.href=a,o.setAttribute("download",n),document.body.appendChild(o),o.click(),document.body.removeChild(o),window.URL.revokeObjectURL(a)}}),(function(t){console.log("err"+t);var e=t.message;return"Network Error"===e?e="后端接口连接异常":e.includes("timeout")?e="系统接口请求超时":e.includes("Request failed with status code")&&(e="系统接口"+e.substr(e.length-3)+"异常"),Object(l["Message"])({message:e,type:"error",duration:5e3}),Promise.reject(t)}));var u=s;function d(t){return Object(n["a"])({url:"/category/list",method:"get",params:t})}function m(){return Object(n["a"])({url:"/category/listAllCategory",method:"get"})}function f(t){return Object(n["a"])({url:"/category/"+t,method:"get"})}function p(t){return Object(n["a"])({url:"/category/add",method:"post",data:t})}function h(t){return Object(n["a"])({url:"/category/update",method:"put",data:t})}function b(t){return Object(n["a"])({url:"/category/delete/"+t,method:"delete"})}function g(){return u({url:"/content/category/export",method:"get"})}function v(t,e){var r={id:t,status:e};return Object(n["a"])({url:"/category/changeStatus",method:"put",data:r})}},"9dd4":function(t,e,r){"use strict";r.d(e,"e",(function(){return a})),r.d(e,"d",(function(){return o})),r.d(e,"c",(function(){return i})),r.d(e,"a",(function(){return l})),r.d(e,"f",(function(){return c})),r.d(e,"b",(function(){return s}));var n=r("b775");function a(t){return Object(n["a"])({url:"/tag/list",method:"get",params:t})}function o(){return Object(n["a"])({url:"/tag/listAllTag",method:"get"})}function i(t){return Object(n["a"])({url:"/tag/"+t,method:"get"})}function l(t){return Object(n["a"])({url:"/tag/add",method:"post",data:t})}function c(t){return Object(n["a"])({url:"/tag/update",method:"put",data:t})}function s(t){return Object(n["a"])({url:"/tag/delete/"+t,method:"delete"})}},b757:function(t,e,r){},d6ce:function(t,e,r){"use strict";r.r(e);var n=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"app-container"},[r("el-form",{ref:"form",attrs:{model:t.form,"label-width":"90px"}},[r("el-row",{attrs:{gutter:20}},[r("el-col",{attrs:{span:12}},[r("el-form-item",{attrs:{label:"文章标题",prop:"title"}},[r("el-input",{attrs:{placeholder:"请输入文章标题",maxlength:"30"},model:{value:t.form.title,callback:function(e){t.$set(t.form,"title",e)},expression:"form.title"}})],1)],1),r("el-col",{attrs:{span:6}},[r("el-form-item",{attrs:{label:"分类"}},[r("el-select",{attrs:{placeholder:"请选择"},model:{value:t.form.categoryId,callback:function(e){t.$set(t.form,"categoryId",e)},expression:"form.categoryId"}},t._l(t.categoryList,(function(t){return r("el-option",{key:t.id,attrs:{label:t.name,value:t.id}})})),1)],1)],1),r("el-col",{attrs:{span:6}},[r("el-form-item",{attrs:{label:"标签"}},[r("el-select",{attrs:{placeholder:"请选择",multiple:""},model:{value:t.form.tags,callback:function(e){t.$set(t.form,"tags",e)},expression:"form.tags"}},t._l(t.tagList,(function(t){return r("el-option",{key:t.id,attrs:{label:t.name,value:t.id}})})),1)],1)],1)],1),r("el-row",{attrs:{gutter:20}},[r("el-col",{attrs:{span:12}},[r("el-form-item",{attrs:{label:"文章摘要"}},[r("el-input",{attrs:{type:"textarea"},model:{value:t.form.summary,callback:function(e){t.$set(t.form,"summary",e)},expression:"form.summary"}})],1)],1),r("el-col",{attrs:{span:6}},[r("el-form-item",{attrs:{label:"允许评论"}},[r("el-radio-group",{model:{value:t.form.isComment,callback:function(e){t.$set(t.form,"isComment",e)},expression:"form.isComment"}},[r("el-radio",{key:"1",attrs:{label:"1"}},[t._v("正常")]),r("el-radio",{key:"0",attrs:{label:"0"}},[t._v("停用")])],1)],1)],1),r("el-col",{attrs:{span:6}},[r("el-form-item",{attrs:{label:"是否置顶"}},[r("el-radio-group",{model:{value:t.form.isTop,callback:function(e){t.$set(t.form,"isTop",e)},expression:"form.isTop"}},[r("el-radio",{key:"1",attrs:{label:"1"}},[t._v("是")]),r("el-radio",{key:"0",attrs:{label:"0"}},[t._v("否")])],1)],1)],1)],1),r("el-row",{attrs:{gutter:20}}),r("el-row",{attrs:{gutter:20}},[r("el-col",{attrs:{span:12}},[r("el-form-item",{attrs:{label:"缩略图"}},[r("el-upload",{staticClass:"upload-demo",attrs:{"file-list":t.fileList,"list-type":"picture",drag:"",name:"img",action:"upload","on-remove":t.fileRemove,limit:1,"http-request":t.handleUpload,"on-exceed":t.onExceed}},[r("i",{staticClass:"el-icon-upload"}),r("div",{staticClass:"el-upload__text"},[t._v(" 将文件拖到此处，或"),r("em",[t._v("点击上传")])]),r("div",{staticClass:"el-upload__tip",attrs:{slot:"tip"},slot:"tip"},[t._v(" 只能上传jpg/png文件，且不超过500kb ")])])],1)],1),r("el-col",{attrs:{span:12}},[r("el-form-item",[r("el-button",{attrs:{type:"primary",size:"medium"},on:{click:t.handleSubmit}},[t._v(t._s(t.aId?"更新":"发布"))])],1),r("el-form-item",[t.aId?t._e():r("el-button",{attrs:{type:"info"},on:{click:t.handleSave}},[t._v("保存到草稿箱")])],1)],1)],1),r("el-row",[r("mavon-editor",{ref:"md",on:{imgAdd:t.addImg},model:{value:t.form.content,callback:function(e){t.$set(t.form,"content",e)},expression:"form.content"}})],1)],1)],1)},a=[],o=(r("b0c0"),r("90fa")),i=r("b775");function l(t){var e=new FormData;return e.append("file",t),Object(i["a"])({url:"/qiniu/article",headers:{"Content-Type":"multipart/form-data"},method:"post",data:e})}var c=r("1b3b"),s=r("9dd4"),u={name:"Write",data:function(){return{form:{title:"",thumbnail:"",isTop:"1",isComment:"0",content:""},categoryList:[],tagList:[],aId:-1,fileList:[]}},watch:{$route:{handler:function(t){this.aId=t.query&&t.query.id,console.log(this.aId)},immediate:!0}},created:function(){this.getCategoryAndTag(),this.aId&&this.getArticle()},methods:{getArticle:function(){var t=this;Object(c["c"])(this.aId).then((function(e){t.form=e,t.fileList.push({name:"缩略图",url:e.thumbnail})}))},handleSave:function(){var t=this;this.form.status="1",Object(c["a"])(this.form).then((function(e){t.$modal.msgSuccess("保存草稿成功"),t.$router.push({path:"/content/article"})}))},handleSubmit:function(){var t=this;this.aId?Object(c["e"])(this.form).then((function(e){t.$modal.msgSuccess("博客更新成功"),t.$router.push({path:"/content/article"})})):(this.form.status="0",Object(c["a"])(this.form).then((function(e){t.$modal.msgSuccess("博客发布成功"),t.$router.push({path:"/content/article"})})))},onExceed:function(){this.$message.error("只能上传一个图片")},handleUpload:function(t){var e=this;l(t.file).then((function(r){e.form.thumbnail=r,e.fileList.push({name:t.file.name,url:r})})).catch((function(t){e.$message.error(t.msg)}))},fileRemove:function(t,e){this.fileList.pop()},addImg:function(t,e){var r=this;l(e).then((function(e){r.$refs.md.$img2Url(t,e)})).catch((function(t){r.$message.error(t.msg)}))},getCategoryAndTag:function(){var t=this;Object(o["f"])().then((function(e){t.categoryList=e})),Object(s["d"])().then((function(e){t.tagList=e}))},beforeAvatarUpload:function(t){var e="image/jpeg"===t.type,r=t.size/1024/1024<2;return e||this.$message.error("上传头像图片只能是 JPG 格式!"),r||this.$message.error("上传头像图片大小不能超过 2MB!"),e&&r}}},d=u,m=(r("4561"),r("2877")),f=Object(m["a"])(d,n,a,!1,null,"24d63bc1",null);e["default"]=f.exports}}]);