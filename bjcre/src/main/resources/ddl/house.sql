DROP TABLE IF EXISTS house;

CREATE TABLE `house` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '类型：出租-0，求租-1，出售-2，求购-3',
  `title` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '标题',
  `house_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '楼盘名称',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='住房表';