/**
 * QuizWiz Timer Module
 * Implements a 10-minute countdown timer with auto-submit functionality
 */

// Timer configuration
const QUIZ_DURATION_SECONDS = 600; // 10 minutes = 600 seconds
let timeRemaining = QUIZ_DURATION_SECONDS;
let timerInterval;

/**
 * Formats seconds into MM:SS format
 * @param {number} seconds - Total seconds to format
 * @returns {string} Formatted time string (MM:SS)
 */
function formatTime(seconds) {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    
    // Pad with leading zeros
    const minutesStr = String(minutes).padStart(2, '0');
    const secondsStr = String(remainingSeconds).padStart(2, '0');
    
    return `${minutesStr}:${secondsStr}`;
}

/**
 * Updates the timer display element
 */
function updateTimerDisplay() {
    const timerElement = document.getElementById('timer');
    if (timerElement) {
        timerElement.textContent = formatTime(timeRemaining);
        
        // Add warning class when time is running low (less than 1 minute)
        if (timeRemaining <= 60) {
            timerElement.classList.add('timer-warning');
        }
    }
}

/**
 * Countdown function that decrements time and updates display
 * Automatically submits the form when timer reaches 00:00
 */
function countdown() {
    timeRemaining--;
    updateTimerDisplay();
    
    // Check if time has expired
    if (timeRemaining <= 0) {
        clearInterval(timerInterval);
        autoSubmitQuiz();
    }
}

/**
 * Automatically submits the quiz form when timer expires
 */
function autoSubmitQuiz() {
    const quizForm = document.getElementById('quizForm');
    if (quizForm) {
        console.log('Time expired! Auto-submitting quiz...');
        quizForm.submit();
    }
}

/**
 * Initializes and starts the countdown timer
 */
function startTimer() {
    // Set initial display
    updateTimerDisplay();
    
    // Start countdown (update every second)
    timerInterval = setInterval(countdown, 1000);
    
    console.log('Quiz timer started: 10 minutes');
}

/**
 * Initialize timer when page loads
 */
window.onload = function() {
    startTimer();
};

/**
 * Optional: Warn user before leaving page with unsaved answers
 */
window.onbeforeunload = function(e) {
    if (timeRemaining > 0 && timeRemaining < QUIZ_DURATION_SECONDS) {
        const message = 'Your quiz is in progress. Are you sure you want to leave?';
        e.returnValue = message;
        return message;
    }
};
