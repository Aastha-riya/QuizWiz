-- QuizWiz Database Schema
-- Create database and populate with sample technical questions

-- Create database
CREATE DATABASE IF NOT EXISTS quizwiz_db;
USE quizwiz_db;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'STUDENT') DEFAULT 'STUDENT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create categories table
CREATE TABLE IF NOT EXISTS categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE
);

-- Insert sample categories
INSERT INTO categories (category_name) VALUES
('Java'),
('Python'),
('C++'),
('DBMS'),
('Operating System'),
('Computer Networks'),
('Data Structures'),
('Aptitude');

-- Create questions table
CREATE TABLE IF NOT EXISTS questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    question_text VARCHAR(500) NOT NULL,
    option_a VARCHAR(200) NOT NULL,
    option_b VARCHAR(200) NOT NULL,
    option_c VARCHAR(200) NOT NULL,
    option_d VARCHAR(200) NOT NULL,
    correct_answer VARCHAR(1) NOT NULL CHECK (correct_answer IN ('A', 'B', 'C', 'D')),
    category_id INT,
    difficulty ENUM('Easy', 'Medium', 'Hard') DEFAULT 'Easy',
    marks INT DEFAULT 1,
    explanation TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- Insert 5 sample technical questions (Java/Data Structures)
INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_answer, category_id, difficulty, marks, explanation) VALUES
('What is the time complexity of searching an element in a balanced Binary Search Tree?',
 'O(n)',
 'O(log n)',
 'O(n log n)',
 'O(1)',
 'B',
 1, 'Medium', 2,
 'In a balanced BST, each comparison eliminates half the remaining nodes, resulting in O(log n) time complexity.'),

('Which Java keyword is used to prevent method overriding?',
 'static',
 'final',
 'abstract',
 'private',
 'B',
 1, 'Easy', 1,
 'The "final" keyword on a method prevents subclasses from overriding it.'),

('What data structure uses LIFO (Last In First Out) principle?',
 'Queue',
 'Stack',
 'LinkedList',
 'Tree',
 'B',
 1, 'Easy', 1,
 'A Stack follows LIFO — the last element pushed is the first one popped.'),

('In Java, which collection does NOT allow duplicate elements?',
 'ArrayList',
 'LinkedList',
 'HashSet',
 'Vector',
 'C',
 2, 'Easy', 1,
 'HashSet implements the Set interface, which does not permit duplicate elements.'),

('What is the worst-case time complexity of QuickSort algorithm?',
 'O(n)',
 'O(n log n)',
 'O(n²)',
 'O(log n)',
 'C',
 1, 'Hard', 3,
 'QuickSort degrades to O(n²) in the worst case when the pivot is always the smallest or largest element (e.g., already sorted input with naive pivot selection).');

-- Verify data insertion
SELECT * FROM questions;

-- Create quiz_attempts table
CREATE TABLE IF NOT EXISTS quiz_attempts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    score INT,
    total_questions INT,
    percentage DECIMAL(5,2),
    time_taken_seconds INT,
    started_at TIMESTAMP,
    completed_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create quiz_answers table
CREATE TABLE IF NOT EXISTS quiz_answers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    attempt_id INT,
    question_id INT,
    selected_option CHAR(1),
    is_correct BOOLEAN,
    FOREIGN KEY (attempt_id) REFERENCES quiz_attempts(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);
