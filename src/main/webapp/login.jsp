<%--
  Created by IntelliJ IDEA.
  User: amazfit
  Date: 2022-08-28
  Time: 上午4:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>rbac-登录页</title>
    <link rel="shortcut icon" href="./imgs/logo32.ico" type="image/x-icon">
    <link rel="stylesheet" href="css/login.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="box">
    <div class="pre-box">
        <h1>WELCOME</h1>
        <P>JOIN US!</P>
        <div class="img-box">
            <img src="./imgs/wuwu.jpeg" alt="">
        </div>
    </div>
    <div class="register-form">
        <div class="title-box">
            <h1>注册</h1>
        </div>
        <div class="input-box">
            <input type="text" placeholder="用户名">
            <input type="text" placeholder="密码">
            <input type="text" placeholder="确认密码">
        </div>
        <div class="btn-box">
            <button>注册</button>
            <p onclick="mySwitch()">已有账号?去登录</p>
        </div>
    </div>
    <div class="login-form">
        <div class="title-box">
            <h1>登录</h1>
        </div>
        <div class="input-box">
            <input type="text" id="userName" name="userName" placeholder="用户名">
            <input type="password" id="password" name="password" placeholder="密码">
            <div class="input-image">
                <input type="text" id="codeStr" name="codeStr" placeholder="验证码">
                <img id="code" src="${pageContext.request.contextPath}/user?action=code" onclick="getCode()"
                     alt="验证码">
            </div>
        </div>
        <div class="btn-box">
            <button onclick="login()">登录</button>
            <p onclick="mySwitch()">没有账号?去注册</p>
        </div>
    </div>
</div>
<script>
    let flag = true;
    const mySwitch = () => {
        if (flag == true) {
            $(".pre-box").css("transform", "translateX(100%)");
            $(".pre-box").css("background-color", "#accae1");
            $("img").attr("src", "./imgs/waoku.jpg");
        } else {
            $(".pre-box").css("transform", "translateX(0%)");
            $(".pre-box").css("background-color", "#e2c3d3");
            $("img").attr("src", "./imgs/wuwu.jpeg");
        }
        flag = !flag;
    };
    const login = () => {
        var userName = $("#userName").val();
        var password = $("#password").val();
        var code = $("#codeStr").val();
        if (userName == '' || userName == null || password == '' || password == null) {
            alert("用户名或者密码不能为空！")
            return;
        }

        if (code == '' || code == null) {
            alert("验证码不能为空！")
            return;
        }
        var user = {
            "userName": userName,
            "password": password,
            "code": code
        }
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "${pageContext.request.contextPath}/user?action=login",
            data: user,
            success: function (result) {
                console.log(result);//打印服务端返回的数据(调试用)
                if (result.code === 200) {
                    location.href="${pageContext.request.contextPath}/user?action=main"
                } else if (result.code === 500) {
                    alert(result.msg)
                }
            }
        })
    };
    const getCode = () => {
        $("#code").click(function () {
            var randomStr = Math.random();
            this.src = "${pageContext.request.contextPath}/user?action=code&number=" + randomStr
        });
    }
</script>
</body>
</html>
