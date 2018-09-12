<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <style type="text/css">
        div.sys-menubar { color: #fff; font-family: "微软雅黑"; max-height:35px; position:absolute; left: 0px; top: 0px; }
        span.sys-menu { padding:6px 16px 8px 16px; display:block; float: left; cursor: pointer; font-size:15px; letter-spacing:3px;}
        span.sys-focus { color:#000; background-color: #ecf0f5 !important; }
        span.sys-menu:hover { background-color: #077fb1; }
        iframe { margin: 0px; padding-top: 5px; border: 0px; }
        p.headtitle {
            padding:0px;
            margin:0px;
            margin-left:10px;
            color:white;
            font-size:17px;
            font-family:"黑体";
            line-height:35px;
            float: left;
            width:161px;
        }
        .navbar-nav > li > a {
            color: #fff;
            padding-bottom: 7px;
            padding-top: 8px;
        }
        .nav .open > a, .nav .open > a:focus, .nav .open > a:hover {
            color: #fff !important;
            background-color: #23272a !important;
        }
        .navbar-nav > li > a:hover, .navbar-nav > li > a:focus {
            /*  color: #077fb1 ;
             background-color: #23272a !important; */
        }
        span.lineDiv {
            width: 2px;
            height: 35px;
            position: relative;
            display: block;
            float: left;
            background: url("${ctx}/lib/img/mainMenuLine.png") no-repeat;
        }
        .main-header .logo {
            width: 200px;
        }
        .main-header > .navbar {
            margin-left: 200px;
        }
        .navbar-custom-menu .navbar-nav > li > a {
            line-height: 20px;
            padding-bottom: 0px;
            padding-top: 0px;
            color: #fff;
        }
        .main-sidebar, .left-side {
            padding-top:70px;
            width:200px;
        }
        .fixed .content-wrapper, .fixed .right-side {
            padding-top: 29px;
        }
        .content-wrapper, .right-side, .main-footer {
            margin-left: 200px;
        }
        span.hidden-xs {
            display:inline-block !important;
        }
        .navbar-nav > .user-menu .user-image {
            border-radius: 50% !important;
            float: left !important;
            height: 25px !important;
            margin-right: 10px !important;
            margin-top: -2px !important;
            width: 25px !important;
        }
        @media (max-width: 767px) {
            nav#menuNav {
                margin-top: -35px;
            }
        }
    </style>
</head>
<body class="skin-blue sidebar-mini fixed">
<div class="wrapper">



            <c:if test="${ list != null }">
                <c:forEach items="${list}" var="sys">
                    <ul class="sidebar-menu sys-left-menu" id="sys-${sys.id}">
                        <c:set var="menuList" value="${sys.children}" scope="request" />
                        <c:import url="menu.jsp" />
                    </ul>
                </c:forEach>
            </c:if>

</div>





</body>
</html>