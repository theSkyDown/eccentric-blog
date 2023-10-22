import request from "@/utils/request";
import downloadService from "@/utils/downloadService";

// 查询分类列表
export function listCategory(query) {
  return request({
    url: "/category/list",
    method: "get",
    params: query,
  });
}

// 查询分类列表
export function listAllCategory() {
  return request({
    url: "/category/listAllCategory",
    method: "get",
  });
}
// 查询分类详细
export function getCategory(id) {
  return request({
    url: "/category/" + id,
    method: "get",
  });
}

// 新增分类
export function addCategory(data) {
  return request({
    url: "/category/add",
    method: "post",
    data: data,
  });
}

// 修改分类
export function updateCategory(data) {
  return request({
    url: "/category/update",
    method: "put",
    data: data,
  });
}

// 删除分类
export function delCategory(id) {
  return request({
    url: "/category/delete/" + id,
    method: "delete",
  });
}

// 导出分类
export function exportCategory() {
  return downloadService({
    url: "/content/category/export",
    method: "get",
  });
}

// 分类状态修改
export function changeCategoryStatus(id, status) {
  const data = {
    id,
    status,
  };
  return request({
    url: "/category/changeStatus",
    method: "put",
    data: data,
  });
}
