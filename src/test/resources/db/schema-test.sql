DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
    id bigserial not null,
    name varchar(50) not null ,
    nip varchar(25) not null ,
    gender varchar(15) not null ,
    age int not null ,
    primary key (id)
);

INSERT INTO employee (id, name, nip, gender, age) VALUES (1, 'baz', '1234', 'Male', 4);
INSERT INTO employee (id, name, nip, gender, age) VALUES (2, 'maz', '1235', 'Female', 2);
