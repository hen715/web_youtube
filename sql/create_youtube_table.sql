USE youtubedb;

DROP TABLE IF exists Comment;
DROP TABLE IF exists Video;
DROP TABLE IF exists Member;



CREATE TABLE Member (
                      id INT primary key,
                      u_id VARCHAR(50) unique,
                      password VARCHAR(50),
                      name VARCHAR(50),
                      phone VARCHAR(50),
                      nickName VARCHAR(50)
);

CREATE TABLE Video (
                     id INT PRIMARY KEY,
                     u_id VARCHAR(50),
                     nickName VARCHAR(50),
                     title VARCHAR(50),
                     description VARCHAR(255),
                     playList VARCHAR(255),
                     category VARCHAR(50),
                     FOREIGN KEY (u_id) REFERENCES Member(u_id)
);

CREATE TABLE Comment (
                       id INT PRIMARY KEY,
                       video_id INT,
                       u_id VARCHAR(50),
                       nickName VARCHAR(50),
                       reply VARCHAR(255),
                       date DATE,
                       FOREIGN KEY (video_id) REFERENCES Video(id),
                       FOREIGN KEY (u_id) REFERENCES Member(u_id)
);