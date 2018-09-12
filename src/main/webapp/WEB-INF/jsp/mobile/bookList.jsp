<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <%@include file="/WEB-INF/jsp/common/jqueryMobile.jsp" %>

    <link rel="stylesheet" href="<%=basePath %>resources/css/commonCss.css" type="text/css">
    <script src="<%=basePath %>resources/scripts/commonJs.js"></script>
    <style>
        span{
            font-size:10px;
        }

        #updateText{
            font-size:0.5em;
            color:red;
            position: relative;
            bottom:5px;
        }
    </style>
</head>
<body>

<div data-role="page" id="home" data-title="追书神器" >
    <div id="mask" class="mask"></div>
    <%@include file="../includePage/setting.jsp" %>

    <div data-role="header" data-position="fixed" >
        <div data-role="navbar" >
            <ul>
                <li><a href="<%=basePath %>mobile/bookList" data-ajax="false" data-icon="home" class="ui-btn-active">我的书架</a></li>
                <li><a href="<%=basePath %>mobile/tosearch" data-ajax="false" data-icon="search">书城</a></li>
            </ul>
        </div>
        <a id="mypanel" href="#setting" data-ajax="false" data-icon="search" style="display: none">面板</a>
    </div>

    <div data-role="content" >
        <div class="custom-notice">
            <i class="icon-notice"></i>
            <marquee id="weather" class="noticeText ng-binding" direction="left" behavior="scroll" scrollamount="1" scrolldelay="1"  width="100%" onmouseover="this.stop();" onmouseout="this.start();"  style="width: 100%;"></marquee>
        </div>

        <ul data-role="listview" data-inset="true">
            <c:forEach items="${lists}" var="item">
                <li>
                    <a url="<%=basePath %>mobile/test?url=${item.bookUrl}" bookMark="${item.bookMark}">
                        <img src="${item.imgUrl }">${item.name }
                        <c:if test="${item.update }">
                            <span id="updateText">更新</span>
                        </c:if>
                        <br/>
                        <span>${item.lastUpdateDate }<br/>${item.lastPageName }</span>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>

    <a href="#myPopup" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" style="display: none">显示弹窗</a>

    <div data-role="popup" id="myPopup" class="ui-content">
        <div data-role="header">
            <h1>信息确认</h1>
        </div>

        <div data-role="main" class="ui-content">
            <h3>确定将本书从书籍列表中移除吗?</h3>
            <div class="ui-grid-a">
                <div class="ui-block-a">
                    <button onclick="removeBook()" class="ui-btn ui-icon-check ui-btn-icon-left">确定</button>
                </div>

                <div class="ui-block-b">
                    <button onclick="closeDialog()" class="ui-btn ui-icon-refresh ui-btn-icon-right">取消</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(function(){
        $("div[data-role='content']").find("a").click(function(){
            var bookMark = $(this).attr("bookMark");
            if(bookMark){
                window.location.href = "<%=basePath %>mobile/getContent?url=" + bookMark;
            }else{
                var pageUrl = $(this).attr("url");
                window.location.href = pageUrl + "&isNewList=" + "true";
            }
        });

        /*获取用户注册地址的天气,后期可以改成根据ip获取地区*/
        $.ajax({
            url:"<%=basePath%>weather/getWeatherNow",
            type:"GET",
            data:{
                extensions : "base"
            },
            success:function(result){
                if(result.success){
                    var info = result.data.lives[0];
                    var value = "";
                    if(info != "" || info != null){
                        value += info.city + "当前" + info.weather + " " + info.temperature + "度 " + info.winddirection + "风" + info.windpower + "级 " +
                                        "空气湿度:" + info.humidity + "%";
                        $("#weather").text(value);
                    }
                }
            }
        });

        /*向左滑动弹出设置面板*/
        $("#home").on("swiperight",function(){
            $("#mypanel").click();
        });

        /*长按删除书籍*/
        $("li a").on("taphold",function(){
            removeBookUrl = $(this).attr("url");
            $("#myPopup").popup("open");
        });
    })

    function removeBook() {
        closeDialog();
        showMask();
        var x = removeBookUrl.indexOf("?");
        var bookUrl = removeBookUrl.substring(x+5,removeBookUrl.length)
        $.ajax({
            url:"<%=basePath %>mobile/removeBookList",
            data:{
                bookUrl:bookUrl
            },
            success:function(result){
                alert("已成功从书籍列表移除！");
                location.reload(true);
            }
        });
    }

    function closeDialog() {
        $("#myPopup").popup("close");
    }

</script>
</body>
</html>