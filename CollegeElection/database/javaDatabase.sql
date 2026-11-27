
-- 1. Create database
CREATE DATABASE IF NOT EXISTS CollegeElection
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE CollegeElection;

-- 2. Create Students table
CREATE TABLE IF NOT EXISTS Students (
    student_id VARCHAR(20) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    student_email VARCHAR(150) NOT NULL UNIQUE,
    has_voted BOOLEAN NOT NULL DEFAULT FALSE
);

-- 3. Create Admin table
CREATE TABLE IF NOT EXISTS Admin (
    admin_id VARCHAR(20) PRIMARY KEY,
    password VARCHAR(100) NOT NULL
);

-- 4. Create Elections table
CREATE TABLE IF NOT EXISTS Elections (
    id INT PRIMARY KEY,
    status ENUM('active', 'inactive') NOT NULL DEFAULT 'inactive'
);

-- 5. Create Candidates table
CREATE TABLE IF NOT EXISTS Candidates (
    student_id VARCHAR(20) NOT NULL,
    position VARCHAR(50) NOT NULL,
    candidate_name VARCHAR(100) NOT NULL,
    votes INT NOT NULL DEFAULT 0,

    PRIMARY KEY (student_id, position),

    FOREIGN KEY (student_id)
    REFERENCES Students(student_id)
    ON DELETE CASCADE
);

-- 6. Insert sample students
INSERT INTO Students
    (student_id, password, student_name, student_email, has_voted)
VALUES
    ('01', '0101', 'Aarav Sharma', 'aarav@example.com', FALSE),
    ('02', '0202', 'Priya Singh', 'priya@example.com', FALSE),
    ('03', '0303', 'Rahul Verma', 'rahul@example.com', FALSE),
    ('04', '0404', 'Neha Gupta', 'neha@example.com', FALSE)
AS new_student
ON DUPLICATE KEY UPDATE
    student_name = new_student.student_name,
    student_email = new_student.student_email;

-- 7. Insert sample admins
INSERT INTO Admin (admin_id, password)
VALUES
    ('91', '9191'),
    ('92', '9292')
AS new_admin
ON DUPLICATE KEY UPDATE
    password = new_admin.password;

-- 8. Create initial election
INSERT INTO Elections (id, status)
VALUES (1, 'inactive')
AS new_election
ON DUPLICATE KEY UPDATE
    id = new_election.id;

-- 9. Verify tables and records
SHOW TABLES;

SELECT * FROM Students;
SELECT * FROM Admin;
SELECT * FROM Elections;
SELECT * FROM Candidates;
