CREATE TABLE StoryReprint (
  id INT,
  origin_id INT NOT NULL,
  target_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (origin_id) REFERENCES Story (id),
  FOREIGN KEY (target_id) REFERENCES Story (id)
);

CREATE TABLE IssueReprint (
  id INT,
  origin_issue_id INT NOT NULL,
  target_issue_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (origin_issue_id) REFERENCES Issue (id,
  FOREIGN KEY (target_issue_id) REFERENCES Issue (id)
);

CREATE TABLE Country (
  id INT,
  code CHAR(4) NOT NULL,
  name CHAR(36) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (code),
  UNIQUE (name)
);

CREATE TABLE Story (
  id INT,
  title VARCHAR(50) NOT NULL,
  issue_id INT NOT NULL,
  feature VARCHAR(50),
  characters_id INT,
  synopsis VARCHAR(150),
  reprint_notes VARCHAR(50),
  notes VARCHAR(50),
  PRIMARY KEY (id),
  FOREIGN KEY (issue_id) REFERENCES Issue (id)
);

CREATE TABLE Editor (
  id INT,
  name VARCHAR(50),
  PRIMARY KEY (id),
  UNIQUE (name)
);

CREATE TABLE Editing (
  story_id INT,
  editor_id INT,
  PRIMARY KEY (story_id, editor_id),
  FOREIGN KEY (story_id) REFERENCES Story (id),
  FOREIGN KEY (editor_id) REFERENCES Editor (id)
);

CREATE TABLE Genre (
  id INT,
  name VARCHAR(50),
  PRIMARY KEY (id),
  UNIQUE (name)
);

CREATE TABLE Has_Genre (
  story_id INT,
  genre_id INT,
  PRIMARY KEY (story_id, genre_id),
  FOREIGN KEY (story_id) REFERENCES Story (id),
  FOREIGN KEY (genre_id) REFERENCES Genre (id)
);

CREATE TABLE Story_type (
  id INT,
  name VARCHAR(50),
  PRIMARY KEY (id),
  UNIQUE (name)
);

CREATE TABLE Has_Story_Type (
  story_id INT,
  type_id INT,
  PRIMARY KEY(story_id, type_id),
  FOREIGN KEY (story_id) REFERENCES Story (id),
  FOREIGN KEY (type_id) REFERENCES Story_type (id)
);

CREATE TABLE Artists (
  id INT,
  name VARCHAR(50),
  PRIMARY KEY (id),
  UNIQUE (name)
);

CREATE TABLE Inks (
  story_id INT,
  artist_id INT,
  PRIMARY KEY (story_id, artist_id),
  FOREIGN KEY (story_id) REFERENCES Story (id),
  FOREIGN KEY (artist_id) REFERENCES Artists (id)
);

CREATE TABLE Colors (
  story_id INT,
  artist_id INT,
  PRIMARY KEY (story_id, artist_id),
  FOREIGN KEY (story_id) REFERENCES Story (id),
  FOREIGN KEY (artist_id) REFERENCES Artists (id)
);

CREATE TABLE Pencils (
  story_id INT,
  artist_id INT,
  PRIMARY KEY (story_id, artist_id),
  FOREIGN KEY (story_id) REFERENCES Story (id),
  FOREIGN KEY (artist_id) REFERENCES Artists (id)
);

CREATE TABLE Authors (
  id INT,
  name VARCHAR(50),
  PRIMARY KEY (id),
  UNIQUE (name)
);

CREATE TABLE Script (
  story_id INT,
  author_id INT,
  PRIMARY KEY (story_id, author_id),
  FOREIGN KEY (story_id) REFERENCES Story (id),
  FOREIGN KEY (author_id) REFERENCES Authors (id)
);

CREATE TABLE Characters (
  id INT,
  name VARCHAR(50),
  PRIMARY KEY (id),
  UNIQUE (name)
);

CREATE TABLE Has_charaters (
  story_id INT,
  character_id INT,
  PRIMARY KEY (story_id, character_id),
  FOREIGN KEY (story_id) REFERENCES Story (id),
  FOREIGN KEY (character_id) REFERENCES Characters (id)
);

CREATE TABLE Creators (
  id INT,
  name VARCHAR(50),
  PRIMARY KEY (id),
  UNIQUE (name)
);

CREATE TABLE Letters (
  story_id INT,
  creators_id INT,
  PRIMARY KEY (story_id, creators_id),
  FOREIGN KEY (story_id) REFERENCES Story (id),
  FOREIGN KEY (creators_id) REFERENCES Creators (id)
);

CREATE TABLE Issue (
  id INT,
  issue_number INT,
  indicia_publisher_id INT NOT NULL,
  publication_date CHAR(15),
  price CHAR(10),
  page_count INT,
  indicia_frequency CHAR(10),
  editing VARCHAR(20),
  notes VARCHAR(1000),
  isbn VARCHAR(20),
  valid_isbn VARCHAR(20),
  barcode VARCHAR(10),
  title VARCHAR(30),
  on_sale_date INT,
  rating INT,
  PRIMARY KEY (id),
  FOREIGN KEY (indicia_publisher_id) REFERENCES IndiciaPublisher (id)
);

CREATE TABLE BrandGroup (
  id INT,
  name VARCHAR2(128) NOT NULL,
  publisher_id INT NOT NULL,
  year_began SMALLINT,
  year_ended SMALLINT,
  notes VARCHAR2(1023),
  url VARCHAR(128),
  PRIMARY KEY (id),
  UNIQUE (name),
  FOREIGN KEY (publisher_id) REFERENCES Publisher (id)
);

CREATE TABLE Publisher (
  id INT,
  name VARCHAR2(128) NOT NULL,
  year_began SMALLINT,
  year_ended SMALLINT,
  notes VARCHAR2(3500),
  url VARCHAR(128),
  country_id INT,
  PRIMARY KEY (id),
  UNIQUE (name),
  FOREIGN KEY (country_id) REFERENCES Country (id)
);

CREATE TABLE IndiciaPublisher (
  id INT,
  name VARCHAR2(128) NOT NULL,
  year_began SMALLINT,
  year_ended SMALLINT,
  notes VARCHAR2(3500),
  url VARCHAR(128),
  country_id INT,
  publisher_id INT,
  is_surrogate NUMBER(3) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (name),
  FOREIGN KEY (country_id) REFERENCES Country (id),
  FOREIGN KEY (publisher_id) REFERENCES Publisher (id)
);

CREATE TABLE Language (
  id INT,
  code CHAR(3) NOT NULL,
  name CHAR(36) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (code),
  UNIQUE (name)
);

CREATE TABLE Series (
  id INT,
  name VARCHAR2(1000) NOT NULL,
  format VARCHAR(30),
  publication_dates VARCHAR(200),
  year_began SMALLINT,
  year_ended SMALLINT,
  first_issue_id INT,
  last_issue_id INT,
  publisher_id INT,
  country_id INT,
  language_id INT,
  notes VARCHAR2(4000),
  color VARCHAR(200),
  dimensions VARCHAR(200),
  paper_stock VARCHAR(200),
  binding VARCHAR(200),
  publishing_format VARCHAR(200),
  PRIMARY KEY (id),
  UNIQUE (name),
  FOREIGN KEY (first_issue_id) REFERENCES Issue (id),
  FOREIGN KEY (last_issue_id) REFERENCES Issue (id),
  FOREIGN KEY (publisher_id) REFERENCES Publisher (id),
  FOREIGN KEY (country_id) REFERENCES Country (id),
  FOREIGN KEY (language_id) REFERENCES Language (id)
);

CREATE TABLE Serie_type (
  id INT,
  name VARCHAR(50),
  PRIMARY KEY (id),
  UNIQUE (name)
);

CREATE TABLE Has_Serie_Type (
  serie_id INT,
  type_id INT,
  PRIMARY KEY(serie_id, type_id),
  FOREIGN KEY (serie_id) REFERENCES Series (id),
  FOREIGN KEY (type_id) REFERENCES Serie_type (id)
);
