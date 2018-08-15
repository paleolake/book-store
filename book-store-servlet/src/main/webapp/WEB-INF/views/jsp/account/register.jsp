<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>会员注册</title>
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
    <h2>会员注册</h2>
    <form class="form-horizontal" action="/register.htm" method="post">
        <div class="form-group">
            <label class="control-label col-sm-2" for="mobileNo">手机号码</label>
            <div class="col-sm-10">
                <input type="tel" class="form-control" id="mobileNo" placeholder="请输入手机号码" name="mobileNo"
                       maxlength="11">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="password">密码:</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="password" placeholder="请输入密码" name="password"
                       maxlength="16">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="password">确认密码:</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="confirmPsw" placeholder="请再次输入密码" name="confirmPsw">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="button" class="btn btn-default">注册</button>
                <a class="hyperlink" style="margin-left: 10px;" href="/login.htm">登录</a>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <span style="color: red;" id="error">${error}</span>
            </div>
        </div>
    </form>

    <%--<form action="/register.htm" method="post">--%>
    <%--手机号码: <input type="text" name="mobileNo" value="" maxlength="11"/><br>--%>
    <%--密码: <input type="password" name="password" value="" maxlength="16"/><br>--%>
    <%--确认密码: <input type="password" name="confirmPsw" value="" maxlength="16"/><br>--%>
    <%--<input type="submit" value="注册"/>--%>
    <%--</form>--%>
</div>
</body>
<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
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
            if (password.length < 8 || password.length > 16) {
                $("#error").text("密码的长度必须大于8位且小于16位！");
                return;
            }
            var confirmPsw = $("input[name='confirmPsw']").val();
            if (confirmPsw == null || confirmPsw == '') {
                $("#error").text("确认密码不能为空！");
                return;
            }
            if (confirmPsw != password) {
                $("#error").text("两次输入的密码不一致！");
                return;
            }
            document.forms[0].submit();
        });
    });
</script>
</html>