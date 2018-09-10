<%@ page language="java" pageEncoding="UTF-8"%>

<div data-role="panel" id="setting">
    <h2>功能测试</h2>
    <button id="btn1" onclick="btn1()">获取当前位置</button>
</div>

<script>
    function btn1() {
        document.location.href = "<%=basePath %>/map/goMap";
    }

</script>