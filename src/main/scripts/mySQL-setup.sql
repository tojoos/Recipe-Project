CREATE DATABASE mysql_dev;
CREATE DATABASE mysql_prod;

CREATE USER 'dev_user'@'localhost' IDENTIFIED BY 'tojoos';
CREATE USER 'prod_user'@'localhost' IDENTIFIED BY 'tojoos';
CREATE USER 'dev_user'@'%' IDENTIFIED BY 'tojoos'; # wildcard for the host (external IP for the container)
CREATE USER 'prod_user'@'%' IDENTIFIED BY 'tojoos';

GRANT SELECT, INSERT, DELETE, UPDATE ON mysql_dev.* to 'dev_user'@'localhost';
GRANT SELECT, INSERT, DELETE, UPDATE ON mysql_prod.* to 'prod_user'@'localhost';

GRANT SELECT, INSERT, DELETE, UPDATE ON mysql_dev.* to 'dev_user'@'%';
GRANT SELECT, INSERT, DELETE, UPDATE ON mysql_prod.* to 'prod_user'@'%';