DROP TABLE IF EXISTS students_courses;

CREATE TABLE students_courses (
	student_id int(11) NOT NULL,
    course_id int(11) NOT NULL,

    PRIMARY KEY (student_id, course_id),

    CONSTRAINT FK_STUDENT FOREIGN KEY (student_id)
    REFERENCES students (id)
    ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_COURSE FOREIGN KEY (course_id)
    REFERENCES courses (id)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB CHARSET=utf8;