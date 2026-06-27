<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%!
    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("&",  "&amp;")
                .replace("<",  "&lt;")
                .replace(">",  "&gt;")
                .replace("\"", "&quot;")
                .replace("'",  "&#x27;");
    }
%>
<%
    String appName = application.getInitParameter("appName");
    if (appName == null) appName = "QuizWiz";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= appName %> | 404 Not Found</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <style>
        .error-illustration-svg {
            width: 200px;
            height: 160px;
            margin: 10px auto 20px;
            display: block;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="error-card">

            <div class="error-code">404</div>

            <!-- Lost astronaut SVG illustration -->
            <svg class="error-illustration-svg" viewBox="0 0 200 160" xmlns="http://www.w3.org/2000/svg" aria-hidden="true">
                <!-- Stars -->
                <circle cx="20"  cy="20"  r="2"   fill="#667eea" opacity="0.6"/>
                <circle cx="170" cy="15"  r="1.5" fill="#764ba2" opacity="0.7"/>
                <circle cx="140" cy="40"  r="1"   fill="#667eea" opacity="0.5"/>
                <circle cx="50"  cy="50"  r="1.5" fill="#764ba2" opacity="0.4"/>
                <circle cx="185" cy="60"  r="2"   fill="#667eea" opacity="0.6"/>
                <circle cx="10"  cy="80"  r="1"   fill="#764ba2" opacity="0.5"/>
                <circle cx="160" cy="100" r="1.5" fill="#667eea" opacity="0.4"/>
                <!-- Planet -->
                <circle cx="155" cy="115" r="28" fill="#eef2ff" stroke="#667eea" stroke-width="2"/>
                <ellipse cx="155" cy="115" rx="42" ry="8" fill="none" stroke="#764ba2" stroke-width="2" opacity="0.6"/>
                <!-- Astronaut body -->
                <rect x="82" y="72" width="36" height="40" rx="10" fill="#667eea"/>
                <!-- Helmet -->
                <circle cx="100" cy="62" r="18" fill="#eef2ff" stroke="#667eea" stroke-width="2.5"/>
                <circle cx="100" cy="62" r="11" fill="#c7d2fe" opacity="0.5"/>
                <!-- Visor shine -->
                <circle cx="94" cy="57" r="3" fill="white" opacity="0.7"/>
                <!-- Arms -->
                <rect x="64"  y="76" width="18" height="10" rx="5" fill="#764ba2"/>
                <rect x="118" y="76" width="18" height="10" rx="5" fill="#764ba2"/>
                <!-- Legs -->
                <rect x="86"  y="108" width="12" height="20" rx="5" fill="#764ba2"/>
                <rect x="102" y="108" width="12" height="20" rx="5" fill="#764ba2"/>
                <!-- Boots -->
                <rect x="84"  y="124" width="16" height="8" rx="4" fill="#667eea"/>
                <rect x="100" y="124" width="16" height="8" rx="4" fill="#667eea"/>
                <!-- Chest badge -->
                <rect x="93" y="84" width="14" height="10" rx="3" fill="white" opacity="0.3"/>
            </svg>

            <h1>Oops! We couldn't find that page.</h1>

            <p class="error-description">
                The page you're looking for doesn't exist or may have been moved.
            </p>

            <div class="action-buttons" style="margin-top: 32px;">
                <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">🏠 Home</a>
                <a href="${pageContext.request.contextPath}/login"     class="btn btn-secondary">🔐 Login</a>
                <a href="${pageContext.request.contextPath}/register"  class="btn btn-secondary">📝 Register</a>
                <a href="${pageContext.request.contextPath}/quiz"      class="btn btn-secondary">🧠 Start Quiz</a>
            </div>

            <p class="error-url">
                Requested URL: <%= esc(request.getRequestURI()) %>
            </p>

            <p class="error-support">
                If you believe this is a mistake,
                <a href="mailto:support@quizwiz.com">contact support</a>.
            </p>

        </div>

        <div class="error-footer">
            &copy; <%= appName %> &mdash; Nothing to see here.
        </div>
    </div>
</body>
</html>
