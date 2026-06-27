<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fn"  uri="jakarta.tags.functions" %>
<%
    // Generate and store CSRF token if not already present in session
    if (session.getAttribute("csrfToken") == null) {
        String csrfToken = java.util.UUID.randomUUID().toString();
        session.setAttribute("csrfToken", csrfToken);
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QuizWiz - Online Assessment</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<%-- Total question count for JS and counter display --%>
<c:set var="totalQuestions" value="${fn:length(questions)}"/>
<body data-quiz-duration="600">

    <!-- Timer Display (outside main so it stays fixed in context) -->
    <div class="timer-container">
        <h2>Time Remaining: <span id="timer">10:00</span></h2>
        <div id="timerProgressBar" class="timer-progress-track" role="progressbar"
             aria-valuemin="0" aria-valuemax="100" aria-valuenow="100"
             aria-label="Time remaining">
            <div id="timerProgress" class="timer-progress-fill"></div>
        </div>
    </div>

    <main class="container">

        <!-- Quiz Header -->
        <div class="quiz-header">
            <h1>QuizWiz Online Assessment</h1>
            <p>Answer all questions and click Submit. The quiz will auto-submit after 10 minutes.</p>

            <!-- Progress Indicator (#12) -->
            <c:if test="${totalQuestions > 0}">
                <p class="quiz-progress" id="progressText" aria-live="polite">
                    Answered 0 / ${totalQuestions}
                </p>
            </c:if>
        </div>

        <!-- Quiz Form (#9 autocomplete off) -->
        <form id="quizForm" action="${pageContext.request.contextPath}/submit" method="POST" autocomplete="off">

            <!-- Security & timing hidden fields -->
            <input type="hidden" id="startedAt"  name="started_at"  value="">
            <input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>">

            <c:choose>
                <c:when test="${not empty questions}">
                    <c:forEach var="question" items="${questions}" varStatus="status">

                        <!-- Question card with data-index for focus-scroll (#10) -->
                        <div class="question-card" data-index="${status.index}">

                            <%-- #7 Question counter: "Question N of Total" --%>
                            <h3>Question ${status.count} of ${totalQuestions}</h3>
                            <p class="question-text"><c:out value="${question.questionText}"/></p>

                            <div class="options">
                                <label class="option">
                                    <input type="radio" name="answer_${question.id}" value="A" required>
                                    <span>A. <c:out value="${question.optionA}"/></span>
                                </label>
                                <label class="option">
                                    <input type="radio" name="answer_${question.id}" value="B">
                                    <span>B. <c:out value="${question.optionB}"/></span>
                                </label>
                                <label class="option">
                                    <input type="radio" name="answer_${question.id}" value="C">
                                    <span>C. <c:out value="${question.optionC}"/></span>
                                </label>
                                <label class="option">
                                    <input type="radio" name="answer_${question.id}" value="D">
                                    <span>D. <c:out value="${question.optionD}"/></span>
                                </label>
                            </div>
                        </div>

                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="error-message">
                        <p>No questions available. Please contact the administrator.</p>
                    </div>
                </c:otherwise>
            </c:choose>

            <!-- Submit Button -->
            <div class="submit-container">
                <button type="submit" class="submit-btn">Submit Quiz</button>
            </div>
        </form>

    </main>

    <!-- JavaScript for Timer -->
    <script src="${pageContext.request.contextPath}/js/timer.js"></script>

    <script>
        (function () {
            'use strict';

            var form        = document.getElementById('quizForm');
            var submitBtn   = form.querySelector('.submit-btn');
            var progressTxt = document.getElementById('progressText');
            var totalQ      = ${totalQuestions > 0 ? totalQuestions : 0};
            var submitted   = false;

            /* ── #4 started_at timestamp ─────────────────────── */
            document.getElementById('startedAt').value = Date.now();

            /* ── #8 Auto-save to localStorage ───────────────── */
            var STORAGE_KEY = 'quizwiz_answers';

            function saveAnswers() {
                var saved = {};
                form.querySelectorAll('input[type="radio"]:checked').forEach(function (r) {
                    saved[r.name] = r.value;
                });
                try { localStorage.setItem(STORAGE_KEY, JSON.stringify(saved)); } catch (e) {}
            }

            function restoreAnswers() {
                var raw;
                try { raw = localStorage.getItem(STORAGE_KEY); } catch (e) { return; }
                if (!raw) return;
                var saved = JSON.parse(raw);
                Object.keys(saved).forEach(function (name) {
                    var radio = form.querySelector('input[name="' + name + '"][value="' + saved[name] + '"]');
                    if (radio) {
                        radio.checked = true;
                        radio.closest('.option').classList.add('selected');
                    }
                });
            }

            /* ── #12 Progress counter ────────────────────────── */
            function updateProgress() {
                if (!progressTxt || totalQ === 0) return;
                var answered = new Set(
                    Array.from(form.querySelectorAll('input[type="radio"]:checked'))
                         .map(function (r) { return r.name; })
                ).size;
                progressTxt.textContent = 'Answered ' + answered + ' / ' + totalQ;
            }

            /* ── Option selected class + save + progress ─────── */
            form.querySelectorAll('.option input[type="radio"]').forEach(function (radio) {
                radio.addEventListener('change', function () {
                    form.querySelectorAll('.option input[name="' + this.name + '"]')
                        .forEach(function (r) { r.closest('.option').classList.remove('selected'); });
                    this.closest('.option').classList.add('selected');
                    saveAnswers();
                    updateProgress();
                });
            });

            /* Restore saved answers on page load */
            restoreAnswers();
            updateProgress();

            /* ── #13 Loading state on submit ─────────────────── */
            form.addEventListener('submit', function () {
                submitted = true;
                submitBtn.classList.add('loading');
                submitBtn.disabled = true;
                submitBtn.textContent = 'Submitting...';
                try { localStorage.removeItem(STORAGE_KEY); } catch (e) {}
            });

            /* ── #10 Focus first unanswered on invalid submit ─── */
            form.addEventListener('invalid', function (e) {
                e.preventDefault(); // suppress per-field browser popups
                var firstUnanswered = form.querySelector('.question-card:has(input[required]:not(:checked))');
                if (!firstUnanswered) {
                    // fallback for browsers without :has()
                    var groups = {};
                    form.querySelectorAll('input[type="radio"]').forEach(function (r) {
                        if (!groups[r.name]) groups[r.name] = r;
                    });
                    var checked = new Set(
                        Array.from(form.querySelectorAll('input[type="radio"]:checked')).map(function(r){ return r.name; })
                    );
                    Object.keys(groups).forEach(function (name) {
                        if (!checked.has(name) && !firstUnanswered) {
                            firstUnanswered = groups[name].closest('.question-card');
                        }
                    });
                }
                if (firstUnanswered) {
                    firstUnanswered.scrollIntoView({ behavior: 'smooth', block: 'center' });
                    firstUnanswered.style.outline = '3px solid var(--danger)';
                    setTimeout(function () { firstUnanswered.style.outline = ''; }, 2000);
                }
            }, true /* capture so it fires before browser default */);

            /* ── #6 Warn before leaving mid-quiz ─────────────── */
            window.addEventListener('beforeunload', function (e) {
                if (!submitted) {
                    e.preventDefault();
                    e.returnValue = '';
                }
            });

        }());
    </script>
</body>
</html>
