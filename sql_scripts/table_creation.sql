USE duck;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS user;

CREATE TABLE user(
    id int NOT NULL AUTO_INCREMENT,
    username varchar(255) NOT NULL UNIQUE,
    password char(68) NOT NULL,
    email varchar(255),
    active tinyint NOT NULL,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS role;

CREATE TABLE role (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS user_role;

CREATE TABLE user_role (
  user_id int NOT NULL,
  role_id int NOT NULL,
  PRIMARY KEY (user_id,role_id),

  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role (id)
);

DROP TABLE IF EXISTS fun_fact;

CREATE TABLE fun_fact(
    id int NOT NULL AUTO_INCREMENT,
    fact varchar(255) NOT NULL UNIQUE,
    user_id int,
    enabled tinyint DEFAULT 0,
    timestamp timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    CONSTRAINT fk_fun_fact_user FOREIGN KEY (user_id) REFERENCES user(id)

);

DROP TABLE IF EXISTS image;

CREATE TABLE image(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL UNIQUE,
    user_id int,
    enabled tinyint DEFAULT 0,
    PRIMARY KEY(id),
    CONSTRAINT fk_image_user FOREIGN KEY (user_id) REFERENCES user(id)
);


SET FOREIGN_KEY_CHECKS=1;



