USE neptune_schedule_tracker;

CREATE TABLE IF NOT EXISTS users(
    user_id int NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(255) NOT NULL,
    neptune_code VARCHAR(255) NOT NULL UNIQUE,
    date_of_birth DATE NOT NULL,
    start_of_studies DATE,
    current_semester int,
    subject_id int NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS subjects(
    subject_id int NOT NULL AUTO_INCREMENT,
    subject_name VARCHAR(255) NOT NULL UNIQUE,
    subject_type VARCHAR(255) NOT NULL,
    credit_index int NOT NULL,
    exam_id int UNIQUE,
    subject_code VARCHAR(255) UNICODE NOT NULL,
    task_id int UNIQUE,
    PRIMARY KEY (subject_id)
);

CREATE TABLE IF NOT EXISTS tasks(
    task_id int NOT NULL AUTO_INCREMENT,
    subject_name VARCHAR(255) NOT NULL,
    approximate_time int,
    is_done int,
    finished_percentage int,
    week int,
    PRIMARY KEY (task_id)
);

CREATE TABLE IF NOT EXISTS exams(
    exam_id int NOT NULL AUTO_INCREMENT,
    exam_date date NOT NULL,
    is_finished int,
    task_id int NOT NULL UNIQUE,
    PRIMARY KEY (exam_id)
);

ALTER TABLE users
ADD CONSTRAINT FK_UserSubject
FOREIGN KEY (subject_id) REFERENCES subjects(subject_id);

ALTER TABLE subjects
ADD CONSTRAINT FK_SubjectExam
FOREIGN KEY (exam_id) REFERENCES exams(exam_id);

ALTER TABLE subjects
ADD CONSTRAINT FK_SubjectTask
FOREIGN KEY (task_id) REFERENCES tasks(task_id);

ALTER TABLE exams
ADD CONSTRAINT FK_ExamTask
FOREIGN KEY (task_id) REFERENCES tasks(task_id);