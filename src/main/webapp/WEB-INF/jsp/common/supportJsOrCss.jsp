<%--项目地址--%>
<%
    String path2 = request.getContextPath();
    String basePath2 = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path2 + "/";
    pageContext.setAttribute("basePath",basePath2);
%>

<%--form表单验证和提交框架--%>
<script src="<%=basePath2 %>resources/scripts/jquery.validate.js"></script>
<script src="<%=basePath2 %>resources/scripts/jquery-form.js"></script>

<link rel="stylesheet" href="<%=basePath2 %>resources/css/commonCss.css" type="text/css">
<script src="<%=basePath2 %>resources/scripts/commonJs.js"></script>

