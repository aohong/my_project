DROP TABLE IF EXISTS `property`;

CREATE TABLE bjcre.property
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    type VARCHAR(20) NOT NULL,
    type_description VARCHAR(50) ,
    type_key VARCHAR(20) NOT NULL,
    value VARCHAR(50) NOT NULL,
    description VARCHAR(100), 
    UNIQUE (type,type_key)
);

INSERT INTO bjcre.property (type, type_description, type_key, value, description) VALUES ('acreage', '面积类型', '0', '100平米以下', '100平米以下');
INSERT INTO bjcre.property (type, type_description, type_key, value, description) VALUES ('acreage', '面积类型', '1', '100-150平米', '100-150平米');
INSERT INTO bjcre.property (type, type_description, type_key, value, description) VALUES ('acreage', '面积类型', '2', '150-200平米', '150-200平米');
INSERT INTO bjcre.property (type, type_description, type_key, value, description) VALUES ('acreage', '面积类型', '3', '200-300平米', '200-300平米');
INSERT INTO bjcre.property (type, type_description, type_key, value, description) VALUES ('acreage', '面积类型', '4', '300-500平米', '300-500平米');
INSERT INTO bjcre.property (type, type_description, type_key, value, description) VALUES ('acreage', '面积类型', '5', '500-1000平米', '500-1000平米');
INSERT INTO bjcre.property (type, type_description, type_key, value, description) VALUES ('acreage', '面积类型', '6', '1000平米以上', '1000平米以上');
INSERT INTO bjcre.property (type, type_description, type_key, value, description) VALUES ('day_rent', '日租金', '0', '3元/平米•天以下', null);
INSERT INTO bjcre.property (type, type_description, type_key, value, description) VALUES ('day_rent', '日租金', '1', '3-4元', null);
INSERT INTO bjcre.property (type, type_description, type_key, value, description) VALUES ('day_rent', '日租金', '2', '4-5元', null);
INSERT INTO bjcre.property (type, type_description, type_key, value, description) VALUES ('day_rent', '日租金', '3', '5-6元', null);
INSERT INTO bjcre.property (type, type_description, type_key, value, description) VALUES ('day_rent', '日租金', '4', '6-8元', null);
INSERT INTO bjcre.property (type, type_description, type_key, value, description) VALUES ('day_rent', '日租金', '5', '8-10元', null);
INSERT INTO bjcre.property (type, type_description, type_key, value, description) VALUES ('day_rent', '日租金', '6', '10元以上', null);
