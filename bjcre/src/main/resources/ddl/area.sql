DROP TABLE IF EXISTS `area`;

CREATE TABLE `area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL,
  `type` int(11) NOT NULL COMMENT '区域类型:0-国,1-省,2-市,3-区,4-域',
  `name_code` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名称代码',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# INSERT INTO bjcre.area (parent_id, name, type) VALUES (0, '中国', 0);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (1, '北京市', 1);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (2, '北京市', 2);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '朝阳', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '海淀', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '东城', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '西城', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '崇文', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '丰台', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '宣武', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '大兴', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '昌平', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '石景山', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '通州', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '顺义', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '房山', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '门头沟', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '密云', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '怀柔', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '延庆', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '平谷', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '近郊', 3);
# INSERT INTO bjcre.area (parent_id, name, type) VALUES (3, '周边', 3);

