-- 数据库建表语句
create table if NOT EXISTS order_detail
(
  detail_id varchar(32) not null
    primary key,
  order_id varchar(32) not null,
  product_id varchar(32) not null,
  product_name varchar(64) not null,
  product_price decimal(8,2) not null,
  product_quantity int not null comment '商品数量',
  product_description varchar(64) null,
  product_icon varchar(512) null,
  create_time timestamp default CURRENT_TIMESTAMP not null,
  update_time timestamp default CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP not null
)
  comment '订单明细表'
;

create table if NOT EXISTS order_master
(
  order_id varchar(32) not null
    primary key,
  buyer_name varchar(32) not null,
  buyer_phone varchar(32) not null,
  buy_address varchar(128) not null,
  buyer_openid varchar(64) null comment '买家微信的 openid',
  order_amount decimal(8,2) not null comment '订单总金额',
  order_status tinyint(3) default '0' not null comment '订单状态 , 默认是0 , 新下单',
  pay_status tinyint(3) default '0' not null comment '支付状态,默认0 , 未支付',
  create_time timestamp default CURRENT_TIMESTAMP not null,
  update_time timestamp default CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP not null
)
  comment '订单表'
;

create table if NOT EXISTS product_category
(
  category_id int auto_increment
    primary key,
  category_name varchar(64) null,
  category_type int not null comment '类目编号',
  create_time timestamp default CURRENT_TIMESTAMP not null,
  update_time timestamp default CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP not null,
  constraint uqe_category_type
  unique (category_type)
)
  comment '分类表'
;

create table if NOT EXISTS product_info
(
  product_id varchar(32) not null
    primary key,
  product_name varchar(64) not null comment '商品名称',
  product_price decimal(8,2) not null,
  product_stock int not null,
  product_description varchar(64) null,
  product_icon varchar(512) null,
  category_type int not null comment '类目编号',
  create_time timestamp default CURRENT_TIMESTAMP not null,
  update_time timestamp default CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP not null
)
  comment '商品表'
;
-- 卖家(登录后台使用, 卖家登录之后可能直接采用微信扫码登录，不使用账号密码)
create table `seller_info` (
  `id` varchar(32) not null,
  `username` varchar(32) not null,
  `password` varchar(32) not null,
  `openid` varchar(64) not null comment '微信openid',
  `create_time` timestamp not null default current_timestamp comment '创建时间',
  `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
  primary key (`id`)
) comment '卖家信息表';
