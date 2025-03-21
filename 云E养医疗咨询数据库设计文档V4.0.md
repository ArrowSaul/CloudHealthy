## 云E养医疗咨询数据库设计文档V4.0

| 序号 | 数据表名    | 中文名称   |
| ---- | ----------- | ---------- |
| 1    | admin       | 管理员表   |
| 2    | user        | 用户表     |
| 3    | chat        | 对话记录表 |
| 4    | info        | 患者信息表 |
| 5    | customer    | 客服表     |
| 6    | evaluations | 评价表     |
| 7    | orders      | 订单表     |
| 8    | payment     | 支付记录表 |

### 1. admin

admin表为后台管理员表，用于存储管理员信息。具体表结构如下：

| 字段名      | 数据类型     | 说明         | 备注          |
| ----------- | ------------ | ------------ | ------------- |
| id          | bigint       | 主键         | 自增          |
| name        | varchar(32)  | 姓名         |               |
| username    | varchar(32)  | 用户名       | 唯一          |
| password    | varchar(255) | 密码         |               |
| sex         | int          | 性别         | 0未知 1男 2女 |
| image       | varchar(255) | 头像         |               |
| status      | int          | 账号状态     | 1正常 0锁定   |
| create_time | datetime     | 创建时间     |               |
| update_time | datetime     | 最后修改时间 |               |
| create_user | bigint       | 创建人id     |               |
| update_user | bigint       | 最后修改人id |               |

### 2. user

user表为用户表，用于存储C端用户的信息。具体表结构如下：

| 字段名      | 数据类型     | 说明               | 备注 |
| ----------- | ------------ | ------------------ | ---- |
| id          | bigint       | 主键               | 自增 |
| openid      | varchar(45)  | 微信用户的唯一标识 | 唯一 |
| nickname    | varchar(32)  | 昵称               |      |
| avatar      | varchar(500) | 微信用户头像路径   |      |
| create_time | datetime     | 注册时间           |      |
| update_time | datetime     | 最后修改时间       |      |

### 3. chat

chat表为对话记录表，用于记录用户和患者的对话信息。具体表结构如下：

| 字段名      | 数据类型 | 说明       | 备注                 |
| ----------- | -------- | ---------- | -------------------- |
| id          | bigint   | 主键       | 自增                 |
| order_id    | bigint   | 订单id     | 外键关联order表id    |
| customer_id | bigint   | 客服id     | 外键关联customer表id |
| send_role   | tinyint  | 发送者类型 | 0用户 1客服          |
| content     | text     | 消息内容   |                      |
| send_time   | datetime | 发送时间   |                      |
| read_status | tinyint  | 是否已读   | 0已读 1未读          |

### 4. patient

patient表为患者信息表，用于补充存储就诊人的信息。具体表结构如下：

| 字段名     | 数据类型    | 说明           | 备注             |
| ---------- | ----------- | -------------- | ---------------- |
| id         | bigint      | 主键           | 自增             |
| user_id    | bigint      | 用户id         | 外键关联user表id |
| name       | varchar(32) | 姓名           |                  |
| phone      | varchar(11) | 手机号         |                  |
| sex        | tinyint     | 性别           | 0未知 1男 2女    |
| id_number  | varchar(18) | 身份证号       |                  |
| is_default | tinyint     | 是否默认就诊人 | 0非默认 1默认    |

### 5. customer

customer表为客服表，用于补充存储C端客服用户的信息。具体表结构如下：

| 字段名        | 数据类型    | 说明         | 备注                       |
| ------------- | ----------- | ------------ | -------------------------- |
| id            | bigint      | 主键         | 自增                       |
| user_id       | bigint      | 用户id       | 外键关联user表id           |
| work_number   | varchar(32) | 工号         |                            |
| online_status | tinyint     | 在线状态     | 0：在线，1：离线 ，2：忙碌 |
| conversations | int         | 当前接待人数 |                            |

### 6. evaluations

  evaluations表为用户完成订单后对于服务的评价。具体表结构如下：

| 字段名      | 数据类型 | 说明         | 备注                 |
| ----------- | -------- | ------------ | -------------------- |
| id          | bigint   | 主键         | 自增                 |
| user_id     | bigint   | 用户id       | 外键关联user表id     |
| order_id    | bigint   | 订单id       | 外键关联order表id    |
| customer_id | bigint   | 客服id       | 外键关联customer表id |
| rating      | tinyint  | 评价星级     | 1-5星（0表示未评分） |
| content     | text     | 评价内容     |                      |
| create_time | datetime | 创建时间     |                      |
| update_time | datetime | 最后修改时间 |                      |

### 7. orders

orders表为订单表，用于存储C端用户的订单数据。具体表结构如下：

| 字段名           | 数据类型      | 说明         | 备注                                            |
| ---------------- | ------------- | ------------ | ----------------------------------------------- |
| id               | bigint        | 主键         | 自增                                            |
| number           | varchar(50)   | 订单号       | 唯一非空                                        |
| status           | tinyint       | 订单状态     | 1待付款 2待接单 3已接单 4服务中 5已完成 6已取消 |
| user_id          | bigint        | 用户id       | 外键关联user表id                                |
| patient_id       | bigint        | 就诊人id     | 外键关联patient表id                             |
| order_time       | datetime      | 下单时间     |                                                 |
| checkout_time    | datetime      | 结账时间     |                                                 |
| amount           | decimal(10,2) | 订单金额     |                                                 |
| cancel_reason    | varchar(255)  | 取消原因     |                                                 |
| rejection_reason | varchar(255)  | 拒单原因     |                                                 |
| cancel_time      | datetime      | 订单取消时间 |                                                 |

### 8. payment

payment表为支付记录表，用于存储支付记录数据。具体表结构如下：

| 字段名         | 数据类型      | 说明         | 备注              |
| -------------- | ------------- | ------------ | ----------------- |
| id             | bigint        | 主键         | 自增              |
| order_id       | bigint        | 订单号       | 外键关联order表id |
| status         | tinyint       | 订单状态     | 0失败 1成功 2退款 |
| transaction_id | varchar(32)   | 支付平台单号 |                   |
| amount         | decimal(10,2) | 订单金额     |                   |
| create_time    | datetime      | 创建时间     |                   |