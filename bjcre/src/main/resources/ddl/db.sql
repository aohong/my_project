GRANT all PRIVILEGES on *.* to 'root'@'10.1.169.132' IDENTIFIED BY '123456' with grant OPTION;
flush PRIVILEGES ;
select host,user FROM mysql.user;
-- update mysql.user set host = '%' where user = 'root';