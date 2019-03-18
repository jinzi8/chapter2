<%--
  Created by IntelliJ IDEA.
  User: 金子
  Date: 2019/3/17
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>你好啊</h1>
<%
    out.write(application.getRealPath("/hello.jsp"));
%>
<c:out value="大佬"/>
</body>
</html>
