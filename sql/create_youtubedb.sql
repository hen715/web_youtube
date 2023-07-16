DROP DATABASE IF EXISTS  youtubedb;
DROP USER IF EXISTS  201901591user@localhost;
create user 201901591user@localhost identified WITH mysql_native_password  by '201901591pw';
create database youtubedb;
grant all privileges on youtubedb.* to 201901591user@localhost with grant option;
commit;
 