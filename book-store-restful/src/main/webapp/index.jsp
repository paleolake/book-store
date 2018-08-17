<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>图书商店</title>
</head>
<body>
图书：<br/>
<a href="../books" target="_blank">图书查询[GET][../book]</a><br/>
<a href="../book/1" target="_blank">图书详细信息[GET][../book/1]</a><br/><br/>
购物车/订单：<br/>
<a href="../card" target="_blank">查看购物车[GET][../card]</a><br/><br/>
<a href="javascript:void(0)" src="../card?bookId=1&price=92.65" class="post">添加图书到购物车[POST][../card?bookId=1&price=100]</a>
<br/>
<a href="../orders" target="_blank">查看订单[GET][../card]</a><br/>
<a href="javascript:void(0)" src="../order?bookId=1" class="post">创建购物订单[POST][../order]</a><br/><br/>
会员登录/注册：<br/>
<a href="javascript:void(0)" src="../login?mobileNo=13798538000&password=aa123456" class="post"> 登录[POST][../login]</a><br/>
<a href="javascript:void(0)" src="../register?mobileNo=13798538000&password=aa123456" class="post">注册[POST][../register]</a><br/>
<a href="../logout" target="_blank">登出[GET][../logout]</a><br/>
<form action="#" method="post" target="_blank"></form>
</body>

<script src="https://code.jquery.com/jquery-2.2.4.js" type="application/javascript"></script>
<script type="application/javascript">
    $(document).ready(function () {
        $("a.post").click(function () {
            var href = $(this).attr("src");
            document.forms[0].action = href;
            document.forms[0].submit();
        });
    });
</script>
</html>
s