# Nova File  schema
 
# --- !Ups
 
CREATE TABLE novafiles (
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    contenttype varchar(255) NOT NULL
);

INSERT INTO novafiles (name,contenttype) Values('File1','.js');
INSERT INTO novafiles (name,contenttype) Values('File2','.css');
INSERT INTO novafiles (name,contenttype) Values('File3','.html');
 
# --- !Downs
 
DROP TABLE novafiles;
