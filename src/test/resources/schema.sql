DROP TABLE IF EXISTS author;

CREATE TABLE author (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL UNIQUE,
  image MEDIUMBLOB NOT NULL,
  
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