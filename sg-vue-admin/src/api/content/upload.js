import request from "@/utils/request";

// 查询分类列表

export function uploadImg(img) {
  const formData = new FormData();
  formData.append("file", img);
  return request({
    url: "/qiniu/article",
    headers: { "Content-Type": "multipart/form-data" },
    method: "post",
    data: formData,
  });
}
