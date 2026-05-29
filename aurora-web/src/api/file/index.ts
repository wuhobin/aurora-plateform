import request from '@/utils/request'

// 上传文件
export function uploadApi(data: any) {
  return request({
    url: '/file/upload',
    method: 'post',
    headers: { "Content-Type": "multipart/articles-data" },
    data
  })
}

