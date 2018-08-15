<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>会员登录</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-2.2.4.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        h2 {
            margin-top: 20px;
            margin-bottom: 30px;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>会员登录</h2>
    <form class="form-horizontal" action="/login.htm" method="post">
        <div class="form-group">
            <label class="control-label col-sm-2" for="mobileNo">手机号码</label>
            <div class="col-sm-10">
                <input type="tel" class="form-control" id="mobileNo" placeholder="请输入手机号码" name="mobileNo">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="password">密码:</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="password" placeholder="请输入密码" name="password">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <div class="checkbox">
                    <label><input type="checkbox" name="remember"> 记住我</label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="button" class="btn btn-default">登录</button>
                <a class="hyperlink" style="margin-left: 10px;" href="/register.htm">注册</a>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <span style="color: red;" id="error">${error}</span>
            </div>
        </div>
    </form>
</div>

</body>
<script>
    $(document).ready(function () {
        $(".btn-default").click(function () {
            var mobileNo = $("input[name='mobileNo']").val();
            if (mobileNo == null || mobileNo == '') {
                $("#error").text("手机号码不能为空！");
                return;
            }
            var password = $("input[name='password']").val();
            if (password == null || password == '') {
                $("#error").text("密码不能为空！");
                return;
            }
            document.forms[0].submit();
        });
    });
</script>
</html>