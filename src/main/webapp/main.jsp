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
    <%
        if (session.getAttribute("currentUser")==null){
            response.sendRedirect("login.jsp");
        }
    %>
    <title>rbac-主界面</title>
    <link rel="shortcut icon" href="./imgs/logo32.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="./css/common.css">
    <link rel="stylesheet" type="text/css" href="./easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="./easyui/themes/icon.css">
    <script type="text/javascript" src="./easyui/jquery.min.js"></script>
    <script type="text/javascript" src="./easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="./easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<div id="cc" class="easyui-layout" style="width:100%;height:100%;">
    <div data-options="region:'north',title:'RBAC-系统主菜单界面',split:true" style="height:20%;">
        欢迎： ${currentUser.userName}【${currentUser.roleName}】
    </div>
<%--    <div data-options="region:'south',title:'South Title',split:true" style="height:20%;"></div>--%>
<%--    <div data-options="region:'east',title:'East',split:true" style="width:100px;"></div>--%>
    <div data-options="region:'west',title:'菜单',split:true" style="width:20%;">
        <ul id="tree" class="easyui-tree"></ul>
    </div>
    <div data-options="region:'center',title:'内容中心'" style="padding:5px;background-color:#eee;">
        <div id="tabs" class="easyui-tabs" style="width: 100%;height: 100%">
            <div title="主页" style="padding: 20px;display: none">
                欢迎光临
            </div>
        </div>
    </div>
</div>
<div id="updatePasswordDialog" title="修改密码" data-options="iconCls:'icon-save'" class="easyui-dialog" style="width: 400px;height: 230px;padding: 10px" buttons="#dlg-buttons">
    <form method="post" id="fm">
        <table cellspacing="4px" style="margin: auto;">
            <tr><td>用户名：</td><td><input type="text" name="userName" id="userName" readonly="readonly" value="${currentUser.userName}"></td></tr>
            <tr><td>原密码：</td><td><input type="password" class="easyui-validatebox" name="password" id="password" data-options="required:true"></td></tr>
            <tr><td>新密码：</td><td><input type="newPassword" name="newPassword" class="easyui-validatebox" id="newPassword" data-options="required:true" ></td></tr>
            <tr><td>确认密码：</td><td><input type="yesPassword" name="yesPassword" class="easyui-validatebox" id="yesPassword" data-options="required:true"></td></tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="updatePassword()">Submit</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="updatePasswordDialogClose()">Clear</a>
</div>
<script type="text/javascript">
//新建tab，激活tab
$(function (){
    $('#tree').tree({
        lines:true,
        url: 'auth?action=tree',
        onLoadSuccess:function (){
            $("#tree").tree('expandAll');
        },
        onClick: function(node){
            if(node.id===16){
                //安全退出
                logout();
            }else if(node.id===15){
                //修改密码
                $('#updatePasswordDialog').dialog('open');
            }else{
                openTab(node);
            }
        }
    });
    //dialog默认是打开，先关闭掉
    $('#updatePasswordDialog').dialog({
        closed:true
    });

    function openTab(node){
        console.log(node)
        if($("#tabs").tabs("exists",node.text)){
            $("#tabs").tabs("select",node.text);
        }else{
            $("#tabs").tabs("add",{
                title:node.text,
                selected:true,
                iconCls:node.iconCls,
                closable:true,
                content:'<iframe src='+node.attributes.authPath+' style="width:100%;height:100%" scrolling="auto" frameborder="0" ></iframe>'
            });
        }
    };

    function logout(){
        $.messager.confirm('Confirm',"温馨提示，您确定要退出系统吗？",function(r){
            if(r){
                window.location.href='user?action=logout';
            }
        })
    };
});
function updatePassword(){
    $("#fm").form('submit',{
        url:'user?action=updatePassword',
        onSubmit: function (){
            var password = $("#password").val();
            var newPassword = $("#newPassword").val();
            var yesPassword = $("#yesPassword").val();
            if (!$(this).form('validate')){
                return false;
            }
            if (password!='${currentUser.password}'){
                $.messager.alert('系统提示','用户名密码输入错误！');
                return false;
            }
            if (newPassword!=yesPassword){
                $.messager.alert('系统提示','确认密码输入错误！');
                return false;
            }
            return true;
        },
        success: function (result){
            //result是字符串，需要转成对象
            var parse = JSON.parse(result);
            if(parse.code===200){
                $.messager.alert('系统提示','密码修改成功，下一次登录生效！');
                updatePasswordDialogClose();
            }else{
                $.messager.alert('系统提示',result.msg);
            }
        }
    });
};

function updatePasswordDialogClose(){
    $('#updatePasswordDialog').dialog('close');
    $("#password").val("");
    $("#newPassword").val("");
    $("#yesPassword").val("");
}
</script>
</body>
</html>
