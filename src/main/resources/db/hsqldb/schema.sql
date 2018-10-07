DROP TABLE vet_specialty IF EXISTS;
DROP TABLE vet IF EXISTS;
DROP TABLE specialty IF EXISTS;
DROP TABLE visit IF EXISTS;
DROP TABLE pet IF EXISTS;
DROP TABLE pet_type IF EXISTS;
DROP TABLE owner IF EXISTS;


CREATE TABLE vet (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR(30)
);
CREATE INDEX vet_last_name ON vet (last_name);

CREATE TABLE specialty (
  id   INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(80)
);
CREATE INDEX specialty_name ON specialty (name);

CREATE TABLE vet_specialty (
  vet       INTEGER NOT NULL,
  specialty INTEGER NOT NULL
);
ALTER TABLE vet_specialty ADD CONSTRAINT fk_vet_specialty_vet FOREIGN KEY (vet) REFERENCES vet (id);
ALTER TABLE vet_specialty ADD CONSTRAINT fk_vet_specialty_specialty FOREIGN KEY (specialty) REFERENCES specialty (id);

CREATE TABLE pet_type (
  id   INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(80)
);
CREATE INDEX pet_type_name ON pet_type (name);

CREATE TABLE owner (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR_IGNORECASE(30),
  address    VARCHAR(255),
  city       VARCHAR(80),
  telephone  VARCHAR(20)
);
CREATE INDEX owners_last_name ON owner (last_name);

CREATE TABLE pet (
  id         INTEGER IDENTITY PRIMARY KEY,
  name       VARCHAR(30),
  birth_date DATE,
  type_id    INTEGER NOT NULL,
  owner_id   INTEGER NOT NULL
);
ALTER TABLE pet ADD CONSTRAINT fk_pet_owners FOREIGN KEY (owner_id) REFERENCES owner (id);
ALTER TABLE pet ADD CONSTRAINT fk_pet_pet_type FOREIGN KEY (type_id) REFERENCES pet_type (id);
CREATE INDEX pets_name ON pet (name);

CREATE TABLE visit (
  id          INTEGER IDENTITY PRIMARY KEY,
  pet_id      INTEGER NOT NULL,
  date  DATE,
  description VARCHAR(255)
);
ALTER TABLE visit ADD CONSTRAINT fk_visit_pets FOREIGN KEY (pet_id) REFERENCES pet (id);
CREATE INDEX visit_pet_id ON visit (pet_id);
