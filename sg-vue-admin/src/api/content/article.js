import request from "@/utils/request";

// 添加文章
export function addArticle(data) {
  return request({
    url: "/article/add",
    method: "post",
    data: data,
  });
}

// 查询文章列表
export function listArticle(query) {
  return request({
    url: "/article/list",
    method: "get",
    params: query,
  });
}

// 删除文章
export function delArticle(id) {
  return request({
    url: "/article/delete/" + id,
    method: "delete",
  });
}

// 查询文章详细
export function getArticle(id) {
  return request({
    url: "/article/" + id,
    method: "get",
  });
}

// 修改文章
export function updateArticle(data) {
  return request({
    url: "/article/update",
    method: "put",
    data: data,
  });
}
