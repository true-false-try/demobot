CREATE TABLE LECTORS(
                        ID BIGINT NOT NULL ,
                        DEGREE VARCHAR(255) NOT NULL,
                        NAME VARCHAR(255) NOT NULL,
                        SURNAME VARCHAR(255) NOT NULL,
                        SALARY BIGINT NOT NULL,
                        PRIMARY KEY (ID));

CREATE TABLE DEPARTMENTS(
                            ID BIGINT NOT NULL ,
                            NAME VARCHAR(255) NOT NULL,
                            HEAD_DEPARTMENTS BOOLEAN NOT NULL,
                            LECTOR_ID BIGINT NOT NULL,
                            PRIMARY KEY (ID),
                            CONSTRAINT LECTOR_ID
                                FOREIGN KEY (LECTOR_ID) REFERENCES LECTORS (ID));