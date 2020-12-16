Drop schema public;
CREATE SCHEMA IF NOT EXISTS public;

-- -----------------------------------------------------
-- Table public.Users
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public.Users
(
    id                SERIAL       NOT NULL,
    name              VARCHAR(255) NOT NULL,
    full_name         VARCHAR(255) NOT NULL,
    email             VARCHAR(255) NOT NULL,
    password          VARCHAR(255) NOT NULL,
    avatar            text         NULL,
    registration_date DATE         NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);


-- -----------------------------------------------------
-- Table public.Projects
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public.Projects
(
    id            SERIAL       NOT NULL,
    key           VARCHAR(255) NOT NULL,
    name          VARCHAR(255) NOT NULL,
    icon          VARCHAR(255) NOT NULL,
    owner_id      INT          NOT NULL,
    creation_date DATE         NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id),
    CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES public.Users (id)
);

-- -----------------------------------------------------
-- Table public.Tickets
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public.Tickets
(
    id                  SERIAL       NOT NULL,
    project_id          INT          NOT NULL,
    acceptance_criteria TEXT         NULL,
    epic_id             INT          NULL,
    description         TEXT         NULL,
    due_date            DATE         NULL,
    created_date        TIMESTAMP    NOT NULL DEFAULT NOW(),
    resolved_date       TIMESTAMP    NULL,
    estimated_time      FLOAT        NULL,
    logged_time         FLOAT        NULL,
    name                TEXT         NOT NULL,
    key                 TEXT         NOT NULL,
    status              VARCHAR(255) NOT NULL,
    type                VARCHAR(255) NOT NULL,
    priority            VARCHAR(255) NOT NULL,
    assignee_id         INT          NOT NULL,
    reporter_id         INT          NOT NULL,
    is_sub_task         boolean      NOT NULL default false,
    parent_ticket_id    int          NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES public.Projects (id) ON DELETE NO ACTION,
    FOREIGN KEY (assignee_id) REFERENCES public.Users (id) ON DELETE NO ACTION,
    FOREIGN KEY (reporter_id) REFERENCES public.Users (id) ON DELETE NO ACTION,
    FOREIGN KEY (parent_ticket_id) REFERENCES public.Users (id) ON DELETE NO ACTION
);

-- -----------------------------------------------------
-- Table public.Labels
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public.Labels
(
    id         SERIAL       NOT NULL,
    name       VARCHAR(255) NOT NULL,
    project_id INT          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES public.Projects (id) ON DELETE NO ACTION
);

-- -----------------------------------------------------
-- Table public.Components
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public.Components
(
    id         SERIAL       NOT NULL,
    name       VARCHAR(255) NOT NULL,
    project_id INT          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES public.Projects (id) ON DELETE NO ACTION
);

-- -----------------------------------------------------
-- Table public.Attachments
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public.Attachments
(
    id        SERIAL       NOT NULL,
    name      VARCHAR(255) NOT NULL,
    format    VARCHAR(255) NOT NULL,
    date      TIMESTAMP    NOT NULL DEFAULT NOW(),
    content   TEXT         NOT NULL,
    size      VARCHAR(255) NOT NULL,
    ticket_id int          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (ticket_id) REFERENCES public.Tickets (id) ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS public.Comments
(
    id          SERIAL    NOT NULL,
    reporter_id INT       NOT NULL,
    date        TIMESTAMP NOT NULL DEFAULT NOW(),
    content     TEXT      NOT NULL,
    ticket_id   INT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (reporter_id) REFERENCES public.Users (id) ON DELETE NO ACTION,
    FOREIGN KEY (ticket_id) REFERENCES public.Tickets (id) ON DELETE NO ACTION
);

-- -----------------------------------------------------
-- Table public.Commits
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public.Commits
(
    id        SERIAL       NOT NULL,
    number    INT          NOT NULL,
    comment   VARCHAR(255) NOT NULL,
    date      TIMESTAMP    NOT NULL DEFAULT NOW(),
    user_id   INT          NOT NULL,
    url       TEXT         NULL,
    ticket_id int          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES public.Users (id) ON DELETE NO ACTION,
    FOREIGN KEY (ticket_id) REFERENCES public.Tickets (id) ON DELETE NO ACTION
);

-- -----------------------------------------------------
-- Table public.Builds
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public.Builds
(
    id     SERIAL       NOT NULL,
    number INT          NOT NULL,
    status VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

---------------------------------------------------
-- Table public.Tickets_to_Components
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public.Ticket_Components
(
    id           SERIAL NOT NULL,
    ticket_id    INT    NOT NULL,
    component_id INT    NOT NULL,
    PRIMARY KEY (ticket_id, component_id),
    FOREIGN KEY (ticket_id) REFERENCES public.Tickets (id) ON UPDATE CASCADE,
    FOREIGN KEY (component_id) REFERENCES public.Components (id) ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table public.Tickets_to_Labels
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public.Ticket_Labels
(
    id        SERIAL NOT NULL,
    ticket_id INT    NOT NULL,
    label_id  INT    NOT NULL,
    PRIMARY KEY (ticket_id, label_id),
    FOREIGN KEY (ticket_id) REFERENCES public.Tickets (id) ON UPDATE CASCADE,
    FOREIGN KEY (label_id) REFERENCES public.Labels (id) ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table public.Builds_to_Tickets
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public.Ticket_Builds
(
    id        SERIAL NOT NULL,
    build_id  INT    NOT NULL,
    ticket_id INT    NOT NULL,
    PRIMARY KEY (build_id, ticket_id),
    FOREIGN KEY (build_id) REFERENCES public.Builds (id) ON UPDATE CASCADE,
    FOREIGN KEY (ticket_id) REFERENCES public.Tickets (id) ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table public.Watchers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public.Watchers
(
    id        SERIAL NOT NULL,
    user_id   INT    NOT NULL,
    ticket_id INT    NOT NULL,
    PRIMARY KEY (user_id, ticket_id),
    FOREIGN KEY (user_id) REFERENCES public.Users (id) ON UPDATE CASCADE,
    FOREIGN KEY (ticket_id) REFERENCES public.Tickets (id) ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table public.Project_roles
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public.Project_roles
(
    id         SERIAL       NOT NULL,
    name       VARCHAR(255) NOT NULL,
    project_id INT          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES public.Projects (id) ON DELETE NO ACTION
);

-- -----------------------------------------------------
-- Table public.Users_has_Projects
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public.Project_users
(
    id              SERIAL NOT NULL,
    user_id         INT    NOT NULL,
    project_id      INT    NOT NULL,
    project_role_id INT    NOT NULL,
    PRIMARY KEY (user_id, project_id),
    FOREIGN KEY (user_id) REFERENCES public.Users (id) ON UPDATE CASCADE,
    FOREIGN KEY (project_id) REFERENCES public.Projects (id) ON UPDATE CASCADE,
    FOREIGN KEY (project_role_id) REFERENCES public.Project_roles (id) ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table public.Sub_tasks
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public.Sub_tasks
(
    id          SERIAL       NOT NULL,
    ticket_id   INT          NOT NULL,
    sub_task_id INT          NOT NULL,
    type        VARCHAR(255) NOT NULL,
    PRIMARY KEY (ticket_id, sub_task_id),
    FOREIGN KEY (ticket_id) REFERENCES public.Tickets (id) ON UPDATE CASCADE,
    FOREIGN KEY (sub_task_id) REFERENCES public.Tickets (id) ON UPDATE CASCADE
);
