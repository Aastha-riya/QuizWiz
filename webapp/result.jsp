<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pct">
    <c:choose>
        <c:when test="${not empty score and not empty total and total > 0}">
            <fmt:formatNumber value="${score * 100.0 / total}" maxFractionDigits="1" minFractionDigits="1"/>
        </c:when>
        <c:otherwise>0</c:otherwise>
    </c:choose>
</c:set>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QuizWiz - Results</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        /* ── Circular Score Ring ──────────────────────────── */
        .score-ring-wrap {
            display: flex;
            justify-content: center;
            margin: 32px 0 20px;
        }

        .score-ring {
            position: relative;
            width: 160px;
            height: 160px;
        }

        .score-ring svg {
            transform: rotate(-90deg);
        }

        .score-ring__track {
            fill: none;
            stroke: #e0e0e0;
            stroke-width: 12;
        }

        .score-ring__fill {
            fill: none;
            stroke: url(#ringGradient);
            stroke-width: 12;
            stroke-linecap: round;
            stroke-dasharray: 408; /* 2π × 65 */
            stroke-dashoffset: 408;
            transition: stroke-dashoffset 1.4s ease;
        }

        .score-ring__label {
            position: absolute;
            inset: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            line-height: 1.2;
        }

        .score-ring__pct {
            font-size: 2rem;
            font-weight: 700;
            color: var(--primary);
        }

        .score-ring__fraction {
            font-size: 0.9rem;
            color: #888;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="result-container">

            <!-- Header -->
            <div class="result-header">
                <h1>Quiz Completed!</h1>
                <div class="result-icon">
                    <c:choose>
                        <c:when test="${not empty score and not empty total and score * 100.0 / total >= 80}">🎉</c:when>
                        <c:when test="${not empty score and not empty total and score * 100.0 / total >= 60}">👍</c:when>
                        <c:otherwise>📚</c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Score Display -->
            <div class="score-display">
                <c:choose>
                    <c:when test="${not empty score and not empty total}">

                        <!-- #5 Circular Score Ring -->
                        <div class="score-ring-wrap">
                            <div class="score-ring" id="scoreRing"
                                 data-pct="${score * 100.0 / total}">
                                <svg viewBox="0 0 160 160" width="160" height="160">
                                    <defs>
                                        <linearGradient id="ringGradient" x1="0%" y1="0%" x2="100%" y2="0%">
                                            <stop offset="0%"   stop-color="#667eea"/>
                                            <stop offset="100%" stop-color="#764ba2"/>
                                        </linearGradient>
                                    </defs>
                                    <circle class="score-ring__track" cx="80" cy="80" r="65"/>
                                    <circle class="score-ring__fill"  cx="80" cy="80" r="65" id="ringFill"/>
                                </svg>
                                <div class="score-ring__label">
                                    <span class="score-ring__pct" id="pctDisplay">0%</span>
                                    <span class="score-ring__fraction">
                                        <c:out value="${score}"/>/<c:out value="${total}"/>
                                    </span>
                                </div>
                            </div>
                        </div>

                        <!-- Performance Message -->
                        <div class="performance-message">
                            <c:choose>
                                <c:when test="${score * 100.0 / total >= 80}">
                                    <p class="excellent">Excellent work! You have a strong understanding of the concepts.</p>
                                </c:when>
                                <c:when test="${score * 100.0 / total >= 60}">
                                    <p class="good">Good job! Keep practicing to improve further.</p>
                                </c:when>
                                <c:otherwise>
                                    <p class="needs-improvement">Keep learning! Review the topics and try again.</p>
                                </c:otherwise>
                            </c:choose>
                        </div>

                    </c:when>
                    <c:otherwise>
                        <div class="error-message">
                            <p>Unable to calculate score. Please try again.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- #2 Better Action Buttons -->
            <div class="action-buttons">
                <a href="${pageContext.request.contextPath}/quiz"   class="btn btn-primary">🔁 Try Again</a>
                <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary">🏠 Home</a>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary">🚪 Logout</a>
            </div>

        </div>
    </div>

    <script>
        (function () {
            /* ── Circular ring animation ─────────────────── */
            var ring     = document.getElementById('scoreRing');
            var fill     = document.getElementById('ringFill');
            var pctLabel = document.getElementById('pctDisplay');

            if (ring && fill) {
                var rawPct    = parseFloat(ring.dataset.pct) || 0;
                var pct       = Math.min(Math.max(rawPct, 0), 100);
                var circumference = 2 * Math.PI * 65; // 408.41

                // Animate ring fill
                requestAnimationFrame(function () {
                    fill.style.strokeDashoffset = circumference - (pct / 100) * circumference;
                });

                // Animate percentage counter
                var start     = null;
                var duration  = 1400;
                function animatePct(ts) {
                    if (!start) start = ts;
                    var progress = Math.min((ts - start) / duration, 1);
                    pctLabel.textContent = (progress * pct).toFixed(1) + '%';
                    if (progress < 1) requestAnimationFrame(animatePct);
                }
                requestAnimationFrame(animatePct);
            }

            /* ── #4 Confetti for >= 80% ──────────────────── */
            var pctVal = ring ? parseFloat(ring.dataset.pct) || 0 : 0;
            if (pctVal >= 80) {
                launchConfetti();
            }

            function launchConfetti() {
                var canvas  = document.createElement('canvas');
                canvas.style.cssText = 'position:fixed;top:0;left:0;width:100%;height:100%;pointer-events:none;z-index:9999';
                document.body.appendChild(canvas);
                var ctx     = canvas.getContext('2d');
                var W = canvas.width  = window.innerWidth;
                var H = canvas.height = window.innerHeight;
                var colors  = ['#667eea','#764ba2','#6dd5fa','#f9ca24','#f0932b','#eb4d4b','#6ab04c'];
                var pieces  = Array.from({length: 120}, function () {
                    return {
                        x:     Math.random() * W,
                        y:     Math.random() * H - H,
                        w:     6 + Math.random() * 8,
                        h:     10 + Math.random() * 6,
                        color: colors[Math.floor(Math.random() * colors.length)],
                        angle: Math.random() * 360,
                        spin:  (Math.random() - 0.5) * 6,
                        vy:    3 + Math.random() * 4,
                        vx:    (Math.random() - 0.5) * 2
                    };
                });

                var running = true;
                var startTime = Date.now();

                function draw() {
                    if (!running) return;
                    ctx.clearRect(0, 0, W, H);
                    pieces.forEach(function (p) {
                        ctx.save();
                        ctx.translate(p.x + p.w / 2, p.y + p.h / 2);
                        ctx.rotate(p.angle * Math.PI / 180);
                        ctx.fillStyle = p.color;
                        ctx.fillRect(-p.w / 2, -p.h / 2, p.w, p.h);
                        ctx.restore();
                        p.x     += p.vx;
                        p.y     += p.vy;
                        p.angle += p.spin;
                        if (p.y > H) { p.y = -p.h; p.x = Math.random() * W; }
                    });
                    if (Date.now() - startTime < 4000) {
                        requestAnimationFrame(draw);
                    } else {
                        running = false;
                        canvas.remove();
                    }
                }
                requestAnimationFrame(draw);
            }
        }());
    </script>
</body>
</html>
