# 在线职业教育平台服务端设计



## 服务端统一返回数据

```json
{
    "code": "状态码 0 成功，1 处理中，-1 失败",
    "data": "业务数据",
    "msg" : "描述信息"
}
```



## 接口设计



### 用户操作api

------

#### 用户注册

------

请求类型：`Post`

**api** : `/api/v1/pri/user/register`

参数：

```json
{
    "name":name,
    "phone":phone,
    "pwd":pwd
}
```

接口说明 ： 一个手机号只能注册一次。



#### 用户登陆

------

请求类型：`Post`

**api** : `/api/v1/pri/user/login`

参数：

```json
{
    "phone":phone,
    "pwd":pwd
}
```

接口说明：用户登陆成功后会返回 token 信息，放在返回数据的data中。



#### 用户详情

------

请求类型：`Get`

**api** : `/api/v1/pri/user/find_by_token`

参数：`"token":token `

接口说明：如果token过期，需要用户重新登陆。



### 课程操作api

------

#### 获取首页轮播图

------

请求类型：`Get`

api : `/api/v1/pub/video/list/banner`

参数：无参



#### 获取所有课程

------

请求类型：`Get`

**api：**`/api/v1/pub/video/list/video`

参数：无参



#### 获取视频详情

------

请求类型：`Get`

**api：**`/api/v1/pub/video/find_detail_by_id`

参数：无参



### 订单操作api

------

#### 下单

------

请求类型：`Post`

**api：**`/api/v1/pri/order/save_order`

参数：

```json
{
    "video_id":video_id
}
```

接口说明：需要在header 携带 token信息



#### 获取订单列表

------

请求类型：`Get`

**api：**`/api/v1/pri/order/list_order`

接口说明：需要在header 携带 token信息





## 表设计

- 用户信息表 --- user
- 轮播图表     --- video_banner
- 视频信息表 --- video
- 视频章节表 --- chapter
- 视频集信息 --- episode
- 视频订单表 --- video_order
- 播放记录表 --- play_record

表结构：



### 用户信息表 --- user

```sql
CREATE TABLE `play_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `video_id` int(11) DEFAULT NULL,
  `current_num` int(11) DEFAULT NULL COMMENT '当前播放第⼏几集',
  `episode_id` int(11) DEFAULT NULL COMMENT '当前播放第⼏几集视频id',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
```



### 轮播图表     --- video_banner

```sql
CREATE TABLE `video_banner` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(256) DEFAULT NULL COMMENT '跳转地址',
  `img` varchar(256) DEFAULT NULL COMMENT '图片地址',
  `create_time` datetime DEFAULT NULL,
  `weight` int(11) DEFAULT NULL COMMENT '数字越小排越前',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
```



### 视频信息表 --- video

```sql
CREATE TABLE `video` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(524) DEFAULT NULL COMMENT '视频标题',
  `summary` varchar(1026) DEFAULT NULL COMMENT '概述',
  `cover_img` varchar(524) DEFAULT NULL COMMENT '封面图',
  `price` int(11) DEFAULT NULL COMMENT '价格,分',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `point` double(11,2) DEFAULT '8.70' COMMENT '默认8.7，最高10分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;
```



### 视频章节表 --- chapter

```sql
CREATE TABLE `chapter` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `video_id` int(11) DEFAULT NULL COMMENT '视频主键',
  `title` varchar(128) DEFAULT NULL COMMENT '章节名称',
  `ordered` int(11) DEFAULT NULL COMMENT '章节顺序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=716 DEFAULT CHARSET=utf8;
```



### 视频集信息 --- episode

```sql
CREATE TABLE `episode` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(524) DEFAULT NULL COMMENT '集标题',
  `num` int(10) DEFAULT NULL COMMENT '第几集,全局顺序',
  `ordered` int(11) DEFAULT NULL COMMENT '顺序，章里面的顺序',
  `play_url` varchar(256) DEFAULT NULL COMMENT '播放地址',
  `chapter_id` int(11) DEFAULT NULL COMMENT '章节主键id',
  `free` tinyint(2) DEFAULT '0' COMMENT '0表示免费，1表示首付',
  `video_id` int(10) DEFAULT NULL COMMENT '视频id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12630 DEFAULT CHARSET=utf8;
```



### 视频订单表 --- video_order

```sql
CREATE TABLE `video_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '订单唯一标识',
  `state` int(11) DEFAULT NULL COMMENT '0表示未支付，1表示已支付',
  `create_time` datetime DEFAULT NULL COMMENT '订单生成时间',
  `total_fee` int(11) DEFAULT NULL COMMENT '支付金额，单位分',
  `video_id` int(11) DEFAULT NULL COMMENT '视频主键',
  `video_title` varchar(256) DEFAULT NULL COMMENT '视频标题',
  `video_img` varchar(256) DEFAULT NULL COMMENT '视频图片',
  `user_id` int(12) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
```



### 播放记录表 --- play_record

```sql
CREATE TABLE `play_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `video_id` int(11) DEFAULT NULL,
  `current_num` int(11) DEFAULT NULL COMMENT '当前播放第⼏几集',
  `episode_id` int(11) DEFAULT NULL COMMENT '当前播放第⼏几集视频id',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
```



