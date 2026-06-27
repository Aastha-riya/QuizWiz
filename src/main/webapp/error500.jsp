<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         isErrorPage="true" %>
<%
    // Generate a short error reference ID
    String errorId = java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    request.setAttribute("errorId", errorId);

    // Capture exception (won't expose details unless dev mode is on)
    Throwable ex = (Throwable) request.getAttribute("jakarta.servlet.error.exception");

    // Toggle this to true only in development to show stack traces
    boolean devMode = false;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500 - Server Error | QuizWiz</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
    <div class="container">
        <div class="error-card">

            <div class="error-code">500</div>
            <span class="error-illustration">⚙️</span>

            <h1>Unexpected Server Error</h1>

            <p class="error-description">
                Our server encountered an unexpected error. Our team has been notified.<br>
                Please try again in a few minutes.
            </p>

            <!-- Error Reference ID -->
            <div class="error-meta">
                <span class="error-meta-label">Error Reference</span>
                <span class="error-meta-value"><%= errorId %></span>
            </div>

            <!-- Timestamp -->
            <div class="error-meta">
                <span class="error-meta-label">Time</span>
                <span class="error-meta-value"><%= new java.util.Date() %></span>
            </div>

            <%-- Stack trace only in dev mode --%>
            <% if (devMode && ex != null) { %>
            <div class="error-stack">
                <strong>Debug Info (dev mode):</strong><br>
                <% ex.printStackTrace(new java.io.PrintWriter(out)); %>
            </div>
            <% } %>

            <!-- Action Buttons -->
            <div class="action-buttons" style="margin-top: 32px;">
                <a href="javascript:location.reload()" class="btn btn-primary">Retry</a>
                <a href="${pageContext.request.contextPath}/quiz" class="btn btn-secondary">Home</a>
            </div>

            <!-- Contact support -->
            <p class="error-support">
                If the problem persists, <a href="mailto:support@quizwiz.com">contact support</a>
                and quote your error reference above.
            </p>

        </div>

        <div class="error-footer">
            &copy; QuizWiz &mdash; We&rsquo;re on it.
        </div>
    </div>
</body>
</html>
