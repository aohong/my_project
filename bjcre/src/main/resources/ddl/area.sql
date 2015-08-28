DROP TABLE IF EXISTS `area`;

CREATE TABLE `area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO bjcre.area (id, name) VALUES (1, '朝阳');
INSERT INTO bjcre.area (id, name) VALUES (2, '海淀');
INSERT INTO bjcre.area (id, name) VALUES (3, '东城');
INSERT INTO bjcre.area (id, name) VALUES (4, '西城');
INSERT INTO bjcre.area (id, name) VALUES (5, '崇文');
INSERT INTO bjcre.area (id, name) VALUES (6, '丰台');
INSERT INTO bjcre.area (id, name) VALUES (7, '宣武');
INSERT INTO bjcre.area (id, name) VALUES (8, '大兴');
INSERT INTO bjcre.area (id, name) VALUES (9, '昌平');
INSERT INTO bjcre.area (id, name) VALUES (10, '石景山');
INSERT INTO bjcre.area (id, name) VALUES (11, '通州');
INSERT INTO bjcre.area (id, name) VALUES (12, '顺义');
INSERT INTO bjcre.area (id, name) VALUES (13, '房山');
INSERT INTO bjcre.area (id, name) VALUES (14, '门头沟');
INSERT INTO bjcre.area (id, name) VALUES (15, '密云');
INSERT INTO bjcre.area (id, name) VALUES (16, '怀柔');
INSERT INTO bjcre.area (id, name) VALUES (17, '延庆');
INSERT INTO bjcre.area (id, name) VALUES (18, '平谷');
INSERT INTO bjcre.area (id, name) VALUES (100, '近郊');
INSERT INTO bjcre.area (id, name) VALUES (101, '周边');

