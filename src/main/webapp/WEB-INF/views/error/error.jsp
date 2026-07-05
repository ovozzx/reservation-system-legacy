<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>에러 발생</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            text-align: center;
            padding-top: 100px;
        }

        .box {
            display: inline-block;
            padding: 40px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .code {
            font-size: 40px;
            font-weight: bold;
            color: #e74c3c;
        }

        .msg {
            margin-top: 20px;
            font-size: 18px;
            color: #333;
        }

        .btn {
            margin-top: 30px;
            padding: 10px 20px;
            background: #3498db;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            text-decoration: none;
        }

        .btn:hover {
            background: #2980b9;
        }
    </style>
</head>

<body>

<div class="box">

    <!-- 상태코드 -->
    <div class="code">
        ${status != null ? status : "ERROR"}
    </div>

    <!-- 메시지 -->
    <div class="msg">
        ${msg != null ? msg : "알 수 없는 오류가 발생했습니다."}
    </div>

    <a href="/" class="btn">홈으로 이동</a>

</div>

</body>
</html>