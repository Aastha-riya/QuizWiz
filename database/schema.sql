-- QuizWiz Database Schema
-- Create database and populate with sample technical questions

-- Create database
CREATE DATABASE IF NOT EXISTS quizwiz_db;
USE quizwiz_db;

-- Create questions table
CREATE TABLE IF NOT EXISTS questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    question_text VARCHAR(500) NOT NULL,
    option_a VARCHAR(200) NOT NULL,
    option_b VARCHAR(200) NOT NULL,
    option_c VARCHAR(200) NOT NULL,
    option_d VARCHAR(200) NOT NULL,
    correct_answer VARCHAR(1) NOT NULL CHECK (correct_answer IN ('A', 'B', 'C', 'D'))
);

-- Insert 5 sample technical questions (Java/Data Structures)
INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_answer) VALUES
('What is the time complexity of searching an element in a balanced Binary Search Tree?',
 'O(n)',
 'O(log n)',
 'O(n log n)',
 'O(1)',
 'B'),

('Which Java keyword is used to prevent method overriding?',
 'static',
 'final',
 'abstract',
 'private',
 'B'),

('What data structure uses LIFO (Last In First Out) principle?',
 'Queue',
 'Stack',
 'LinkedList',
 'Tree',
 'B'),

('In Java, which collection does NOT allow duplicate elements?',
 'ArrayList',
 'LinkedList',
 'HashSet',
 'Vector',
 'C'),

('What is the worst-case time complexity of QuickSort algorithm?',
 'O(n)',
 'O(n log n)',
 'O(n²)',
 'O(log n)',
 'C');

-- Verify data insertion
SELECT * FROM questions;
