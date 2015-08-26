DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(45) NOT NULL COMMENT '登陆用户名',
  `password` varchar(32) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '0-手机号，1-邮箱地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';


