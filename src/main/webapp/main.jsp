<%--
  Created by IntelliJ IDEA.
  User: amazfit
  Date: 2022-08-28
  Time: 上午5:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>rbac-主界面</title>
    <link rel="stylesheet" type="text/css" href="./css/common.css">
    <link rel="stylesheet" type="text/css" href="./easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="./easyui/themes/icon.css">
    <script type="text/javascript" src="./easyui/jquery.min.js"></script>
    <script type="text/javascript" src="./easyui/jquery.easyui.min.js"></script>
</head>
<body>
<div id="cc" class="easyui-layout" style="width:100%;height:100%;">
    <div data-options="region:'north',title:'RBAC-系统主菜单界面',split:true" style="height:20%;">
        系统主界面 ${currentUser.userName}
    </div>
<%--    <div data-options="region:'south',title:'South Title',split:true" style="height:20%;"></div>--%>
<%--    <div data-options="region:'east',title:'East',split:true" style="width:100px;"></div>--%>
    <div data-options="region:'west',title:'菜单',split:true" style="width:20%;"></div>
    <div data-options="region:'center',title:'内容中心'" style="padding:5px;background-color:#eee;"></div>
</div>


</body>
</html>
