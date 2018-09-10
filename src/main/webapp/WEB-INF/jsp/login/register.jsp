<%--
  Created by IntelliJ IDEA.
  User: rcp
  Date: 2018/8/14
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/head.jsp" %>
<html>
<head>
    <title>用户注册</title>
    <%@include file="/WEB-INF/jsp/common/jqueryMobile.jsp" %>
    <%@include file="/WEB-INF/jsp/common/supportJsOrCss.jsp" %>
</head>
<body>
<div data-role="page">
    <div id="mask" class="mask"></div>
    <div data-role="header">
        <a href="<%=basePath %>login/goLogin" data-ajax="false" data-role="button">返回登录</a>
        <h1>用户注册</h1>
    </div>
    <div data-role="content">
        <div type="text" id="info" style="display: none;color: red;text-align: center">123</div>

        <form id="form1">
        <div class="ui-field-contain">
            <label for="userNumber">账号：</label>
            <input type="text" name="name" id="userNumber" placeholder="请输入账号"/>
            <label for="userPw">密码：</label>
            <input type="password" name="password" id="userPw" placeholder="请输入密码"/>
            <label for="userName">姓名：</label>
            <input type="text" name="userName" id="userName" placeholder="请输入姓名"/>
            <label for="mobile">联系电话：</label>
            <input type="text" name="mobile" id="mobile" placeholder="请输入联系电话"/>
            <label for="email">联系邮箱：</label>
            <input type="email" name="email" id="email" placeholder="请输入邮箱账号"/>

            <fieldset data-role="fieldcontain">
                <label for="sheng">选择省,市,区</label>
                <select name="sheng" id="sheng">

                </select>
                <select name="shi" id="shi">
                </select>

                <select name="qu" id="qu">
                </select>
            </fieldset>

            <input type="button" value="注册" data-theme="b" onclick="check()" />
        </div>
        </form>
    </div>
    <div data-role="footer" data-position="fixed">
        <h1>版权所有：春哥</h1>
    </div>
</div>
</body>
<script language="javascript">
    /*页面加载完成之后首先对省份下拉框进行渲染*/
    $(function(){
        $("#form1").validate(${validate});//加上校验规则
        getDate("getArea","","sheng");
    });

    /*添加触发事件在省份改变之后市,区下拉框联动加载*/
    $("#sheng").change(function(val){
        /*省份改变之后市和区里面的数据都要清空*/
        $("#shi").empty();
        $("#shi").selectmenu('refresh', true);
        $("#qu").empty();
        $("#qu").selectmenu('refresh', true);
        var options = $("#sheng option:selected"); //获取选中的项
        var sheng = options.val();
        getDate("getArea",sheng,"shi");
    });

    /*添加触发事件在省份改变之后市,区下拉框联动加载*/
    $("#shi").change(function(val){
        $("#qu").empty();
        $("#qu").selectmenu('refresh', true);
        var options = $("#shi option:selected"); //获取选中的项
        var shi = options.val();
        getDate("getArea",shi,"qu");
    });

    /*获取后台地区数据*/
    function getDate(methodName,id,level){
        $.ajax({
            url:"<%=basePath%>weather/"+methodName,
            type:"GET",
            data:{
                id:id
            },
            success:function(result){
                if(result.success){
                    var items = result.data[0].districts;
                    for(var i=0;i<items.length;i++){
                        var item = items[i];
                        var value = item.adcode;
                        var name = item.name;
                        $("<option></option>").val(value).text(name).appendTo("#"+level);
                    }
                    $("#"+level).selectmenu('refresh', true);

                    /*如果省级数据改变会导致市级自动选择一个数据，这时候是不触发市数据change的，所以区的数据无法展示*/
                    if(level == "shi"){
                        var options = $("#shi option:selected"); //获取选中的项
                        var shi = options.val();
                        getDate("getArea",shi,"qu");
                    }

                    /*页面初始加载的省不会触发市下拉框的数据改变*/
                    if(level == "sheng"){
                        var options = $("#sheng option:selected"); //获取选中的项
                        var sheng = options.val();
                        getDate("getArea",sheng,"shi");
                    }
                }
            }
        });
    }

    function check() {
        if(!$('#form1').valid()) {
            return;
        }

        var data =$("#form1").serialize();
        showMask();

        $.ajax({
            url:"<%=basePath%>login/register",
            type:"POST",
            data:data,
            success:function(result){
                hideMask();
                if(result.success){
                    alert("用户注册成功！");
                    window.location.href = "<%=basePath%>login/goLogin";
                }
            }
        });
    }
</script>
</html>
