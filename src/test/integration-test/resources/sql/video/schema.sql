create table video (
  id bigint AUTO_INCREMENT PRIMARY KEY ,
  url VARCHAR(255),
  description VARCHAR(255)
);

ALTER TABLE video
  ADD stat int DEFAULT 0;

ALTER TABLE video
  ADD duration BIGINT DEFAULT 0,
  ADD score double DEFAULT 0.0;
