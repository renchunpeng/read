<%@ page language="java" pageEncoding="UTF-8"%>

<div data-role="panel" id="setting">
    <h2 onclick="op()">面板头部</h2>
    <p onclick="qw()">你可以通过点击面板外部区域或按下 Esc 键或滑动来关闭面板。</p>
</div>

<script>
    function op() {
        alert(123);
    }

    function qw() {
        window.location.href="http://www.baidu.com";
    }
</script>