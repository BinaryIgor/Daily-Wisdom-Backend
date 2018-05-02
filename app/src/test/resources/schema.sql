DROP TABLE IF EXISTS author;

CREATE TABLE author (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL UNIQUE,
  image_path VARCHAR(255),
  
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS author_description;

CREATE TABLE author_description (
  id INT NOT NULL AUTO_INCREMENT,
  author_id INT NOT NULL UNIQUE,
  description TEXT NOT NULL,
  
  PRIMARY KEY(id),
  FOREIGN KEY (author_id) 
  REFERENCES author (id) ON DELETE CASCADE
); 

DROP TABLE IF EXISTS quote;

CREATE TABLE quote (
  id INT NOT NULL AUTO_INCREMENT,
  author_id INT NOT NULL,
  content TEXT NOT NULL,
  
  PRIMARY KEY (id),
  FOREIGN KEY (author_id) 
  REFERENCES author (id) ON DELETE CASCADE,
); 

DROP TABLE IF EXISTS category;

CREATE TABLE category (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL UNIQUE,
	
  PRIMARY KEY (id)
); 

DROP TABLE IF EXISTS category_quote;

CREATE TABLE category_quote (
  category_id INT NOT NULL,
  quote_id INT NOT NULL,
  
  PRIMARY KEY (category_id, quote_id),
  UNIQUE (category_id, quote_id),
  FOREIGN KEY (category_id) 
  REFERENCES category (id) ON DELETE CASCADE,
  FOREIGN KEY (quote_id) 
  REFERENCES quote (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role (
  id int(11) NOT NULL AUTO_INCREMENT,
  role varchar(50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY role (role)
);

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  password varchar(50) NOT NULL,
  enabled tinyint(4) NOT NULL DEFAULT 1,
  user_role_id int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY name (name),
  FOREIGN KEY (user_role_id) REFERENCES user_role (id)
);