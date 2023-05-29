CREATE TABLE platforms (
    id int PRIMARY KEY,
    name varchar(255),
    logoUrl varchar(255)
);

CREATE TABLE genres (
    id int PRIMARY KEY ,
    name varchar(255)
);

CREATE TABLE movies (
    id int PRIMARY KEY ,
    productionYear int,
    rating FLOAT,
    plot varchar(255),
    coverUtl varchar(255),
    budget float,
    length float,
    platforms int,
    genres int,
    FOREIGN KEY (platforms) REFERENCES platforms(id) ON DELETE CASCADE,
    FOREIGN KEY (genres) REFERENCES genres(id) ON DELETE CASCADE
);

INSERT INTO genres
VALUES (1, 'Horror');

INSERT INTO genres
VALUES (2, 'Comedy');

INSERT INTO platforms
VALUES (1, 'Netflix', 'testURL');

INSERT INTO movies
VALUES (1, 2000, 5.0, 'plot', 'coverUrl', 4234, 122, 1, 1)