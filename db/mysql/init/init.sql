-- 1. 데이터베이스가 없으면 생성
CREATE DATABASE IF NOT EXISTS schedule;

-- 2. 해당 데이터베이스 사용
USE schedule;

-- 3. 테이블 생성
CREATE TABLE member (
                        id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                        created_at  DATETIME(6) NULL,
                        modified_at DATETIME(6) NULL,
                        email       VARCHAR(255) NULL,
                        name        VARCHAR(255) NULL,
                        password    VARCHAR(255) NOT NULL,
                        CONSTRAINT UKmbmcqelty0fbrvxp1q58dn57t UNIQUE (email)
);

CREATE TABLE schedule (
                          id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                          created_at  DATETIME(6) NULL,
                          modified_at DATETIME(6) NULL,
                          contents    LONGTEXT NULL,
                          title       VARCHAR(255) NOT NULL,
                          member_id   BIGINT NULL,
                          CONSTRAINT FKn7js9p799qcts7le20pec9bxr FOREIGN KEY (member_id) REFERENCES member (id)
);

CREATE TABLE comment (
                         id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                         created_at  DATETIME(6) NULL,
                         modified_at DATETIME(6) NULL,
                         contents    VARCHAR(255) NOT NULL,
                         member_id   BIGINT NULL,
                         schedule_id BIGINT NULL,
                         CONSTRAINT FKmrrrpi513ssu63i2783jyiv9m FOREIGN KEY (member_id) REFERENCES member (id),
                         CONSTRAINT FKsy51iks4dgapu66gfj3mnykch FOREIGN KEY (schedule_id) REFERENCES schedule (id)
);
