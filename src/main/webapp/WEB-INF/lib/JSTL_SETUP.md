# JSTL Setup

quiz.jsp and result.jsp use JSTL tags (`<c:forEach>`, `<c:out>`, `<c:choose>`, `<fmt:formatNumber>`).
Two JARs must be present in this directory before building.

## For Tomcat 10+ / Jakarta EE 10

Download and place both files here (`webapp/WEB-INF/lib/`):

| JAR | Maven coordinates | Direct download |
|-----|-------------------|-----------------|
| `jakarta.servlet.jsp.jstl-3.0.1.jar` | `org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1` | https://repo1.maven.org/maven2/org/glassfish/web/jakarta.servlet.jsp.jstl/3.0.1/jakarta.servlet.jsp.jstl-3.0.1.jar |
| `jakarta.servlet.jsp.jstl-api-3.0.0.jar` | `jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.0` | https://repo1.maven.org/maven2/jakarta/servlet/jsp/jstl/jakarta.servlet.jsp.jstl-api/3.0.0/jakarta.servlet.jsp.jstl-api-3.0.0.jar |

## Using Maven (if you add a pom.xml)

```xml
<dependency>
    <groupId>org.glassfish.web</groupId>
    <artifactId>jakarta.servlet.jsp.jstl</artifactId>
    <version>3.0.1</version>
</dependency>
```

The implementation JAR pulls in the API automatically.

## Taglib URIs used

| Prefix | URI |
|--------|-----|
| `c`    | `jakarta.tags.core` |
| `fmt`  | `jakarta.tags.fmt` |
| `fn`   | `jakarta.tags.functions` |
