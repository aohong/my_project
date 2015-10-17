DROP TABLE IF EXISTS `house_info`;

CREATE TABLE `house_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `info_type` TINYINT NOT NULL DEFAULT '0' COMMENT '类型：出租-0，求租-1，出售-2，求购-3',
  `title` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '标题',
  `house_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '楼盘名称',
  `area_id` int(11)  NULL COMMENT '区id',
  `block_id` int(11)  NULL COMMENT '域id',
  `address` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '地址',
  `house_type` TINYINT NULL COMMENT '类型',
  `is_register_company` TINYINT NULL COMMENT '是否注册公司',
  `day_rent` DECIMAL(10,2) NULL COMMENT '日租金',
  `total_rent` DECIMAL(16,2) NULL COMMENT '总租金',
  `acreage` DECIMAL(16,2) NULL COMMENT '面积',
  `connector` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '联系人',
  `connector_mobile` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '联系电话',
  `description` varchar(1000) COLLATE utf8_bin  NULL COMMENT '说明',
  `image` varchar(200) COLLATE utf8_bin  NULL COMMENT '图片',
  `create_time` DATETIME COMMENT '创建时间',
  `update_time` TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='住房表';