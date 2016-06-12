# File-Upload


## UploadFileServiceI  上传api
* <code>public void Upload(UploadFile<?> file);</code> 

>### UploadFile  上传对象
* 服务器配置
* 文件目录
* 文件名
* 文件 支持 _byte_ _String_ _File_
* 操作 枚举 支持 _上传_ _删除_


---

## UploadTarget  服务器配置接口
包括：
* 上传类型 sftp ftp 本地
* hosts   可以多个,隔开
* 用户
* 密码
* 端口
* 根目录
* 目录分隔符 ----linux和Windows不一样
---