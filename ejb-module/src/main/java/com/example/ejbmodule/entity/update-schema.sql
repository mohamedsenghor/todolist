CREATE TABLE tasks
(
    login          VARCHAR(255) NOT NULL,
    password       VARCHAR(255) NULL,
    todolist_login VARCHAR(255) NULL,
    CONSTRAINT pk_tasks PRIMARY KEY (login)
);

CREATE TABLE todolists
(
    login             VARCHAR(255) NOT NULL,
    password          VARCHAR(255) NULL,
    utilisateur_login VARCHAR(255) NULL,
    CONSTRAINT pk_todolists PRIMARY KEY (login)
);

CREATE TABLE users
(
    login    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NULL,
    CONSTRAINT pk_users PRIMARY KEY (login)
);

ALTER TABLE tasks
    ADD CONSTRAINT FK_TASKS_ON_TODOLIST_LOGIN FOREIGN KEY (todolist_login) REFERENCES todolists (login);

ALTER TABLE todolists
    ADD CONSTRAINT FK_TODOLISTS_ON_UTILISATEUR_LOGIN FOREIGN KEY (utilisateur_login) REFERENCES users (login);