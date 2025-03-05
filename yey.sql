-- 管理员表
CREATE TABLE admin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    username VARCHAR(32) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    sex TINYINT DEFAULT 0 COMMENT '0未知 1男 2女',
    image VARCHAR(255),
    status TINYINT DEFAULT 1 COMMENT '1正常 0锁定',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
    create_user BIGINT,
    update_user BIGINT,
    FOREIGN KEY (create_user) REFERENCES admin(id),
    FOREIGN KEY (update_user) REFERENCES admin(id)
) COMMENT '管理员表';

-- 用户表
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    openid VARCHAR(45) UNIQUE NOT NULL COMMENT '微信唯一标识',
    role TINYINT DEFAULT 0 COMMENT '角色: 0普通用户 1客服',
    nickname VARCHAR(32),
    avatar VARCHAR(500),
    phone VARCHAR(11) COMMENT '加密存储',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP
) COMMENT '用户表';
-- 就诊人信息表
CREATE TABLE patient (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    name VARCHAR(32) NOT NULL,
    phone VARCHAR(11) NOT NULL,
    sex TINYINT DEFAULT 0 COMMENT '0未知 1男 2女',
    id_number VARCHAR(18) NOT NULL,
    is_default TINYINT DEFAULT 0 COMMENT '0非默认 1默认',
    FOREIGN KEY (user_id) REFERENCES user(id),
    UNIQUE INDEX idx_user_id_number (user_id, id_number)
) COMMENT '就诊人信息表';
-- 客服表
CREATE TABLE customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT UNIQUE NOT NULL COMMENT '关联用户表',
    work_number VARCHAR(32) UNIQUE NOT NULL COMMENT '客服工号',
    online_status TINYINT DEFAULT 0 COMMENT '0在线 1离线 2忙碌',
    conversations INT DEFAULT 0 COMMENT '当前接待人数',
    FOREIGN KEY (user_id) REFERENCES user(id)
) COMMENT '客服信息表';
-- 订单表
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    number VARCHAR(50) UNIQUE NOT NULL COMMENT '订单号',
    status TINYINT NOT NULL COMMENT '1待付款 2待接单 3已接单 4服务中 5已完成 6已取消',
    user_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    order_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    checkout_time DATETIME,
    amount DECIMAL(10,2) NOT NULL,
    cancel_reason VARCHAR(255),
    rejection_reason VARCHAR(255),
    cancel_time DATETIME,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (patient_id) REFERENCES patient(id)
) COMMENT '订单表';

-- 对话记录表
CREATE TABLE chat (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    send_role TINYINT NOT NULL COMMENT '0用户 1客服',
    content TEXT NOT NULL,
    send_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    read_status TINYINT DEFAULT 1 COMMENT '0已读 1未读',
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    INDEX idx_order_time (order_id, send_time)
) COMMENT '对话记录表';

CREATE TABLE evaluations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    rating TINYINT NOT NULL DEFAULT 0 COMMENT '0未评分 1-5星',
    content TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES `user`(id), -- 注意: user是MySQL保留字，需反引号包裹
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    UNIQUE (order_id), -- 确保一个订单只能评价一次
    CONSTRAINT chk_rating_range CHECK (rating BETWEEN 0 AND 5) -- 评分范围约束
) COMMENT '服务评价表';

-- 支付记录表
CREATE TABLE payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT UNIQUE NOT NULL,
    transaction_id VARCHAR(32) NOT NULL COMMENT '支付平台单号',
    amount DECIMAL(10,2) NOT NULL,
    status TINYINT COMMENT '0失败 1成功 2退款',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id)
) COMMENT '支付流水表';