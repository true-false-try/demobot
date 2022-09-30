INSERT INTO lectors (id, degree, name, surname, salary) VALUES (1,'ASSISTANT','Ivan','Ivanov', 2000);
INSERT INTO lectors (id, degree, name, surname, salary) VALUES (2,'ASSOCIATE_PROFESSOR','Lui','Petrov', 3000);
INSERT INTO lectors (id, degree, name, surname, salary) VALUES (3,'PROFESSOR','Alex','Pupkin', 1500);
INSERT INTO lectors (id, degree, name, surname, salary) VALUES (4,'PROFESSOR','Alona','Sallina', 2200);


INSERT INTO departments (id, name, head_departments, lector_id) VALUES (1,'INTERNATIONAL_RELATIONS', true, 1);
INSERT INTO departments (id, name, head_departments, lector_id) VALUES (2,'POLITICAL_SCIENCE', false, 1);
INSERT INTO departments (id, name, head_departments, lector_id) VALUES (3,'SOCIOLOGY', false, 1);
INSERT INTO departments (id, name, head_departments, lector_id) VALUES (4,'PSYCHOLOGY', false, 4);
INSERT INTO departments (id, name, head_departments, lector_id) VALUES (5,'FOREIGN_LANGUAGES', true, 2);
INSERT INTO departments (id, name, head_departments, lector_id) VALUES (6,'PSYCHOLOGY', true, 3);



