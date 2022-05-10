<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>考勤时间信息</title>
    <link href="css/main.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>

    <script type="text/javascript">
        var tempClassName = "";

        function tr_mouseover(obj) {
            tempClassName = obj.className;
            obj.className = "list_mouseover";
        }

        function tr_mouseout(obj) {
            obj.className = tempClassName;
        }

        function CheckAll(obj) {
            var checks = document.getElementsByName("chkid");
            for (var i = 0; i < checks.length; i++) {
                var e = checks[i];
                e.checked = obj.checked;
            }

        }


        function serch() {
            document.info.action = "listConfig";
            document.info.submit();
        }

        function del() {
            var checks = document.getElementsByName("chkid");
            var ids = "";
            for (var i = 0; i < checks.length; i++) {
                var e = checks[i];
                if (e.checked == true) {
                    if (ids == "") {
                        ids = ids + e.value;
                    } else {
                        ids = ids + "," + e.value;
                    }
                }
            }
            if (ids == "") {
                alert("请至少选择一个要删除的考勤时间！");
                return false;
            }
            if (confirm('确认删除吗!?')) {
                document.info.action = "delConfig?ids=" + ids;
                document.info.submit();
            }

        }

        function GoPage() {
            var pagenum = document.getElementById("goPage").value;
            var patten = /^\d+$/;
            if (!patten.exec(pagenum)) {
                alert("页码必须为大于0的数字");
                return false;
            }
            document.getElementById("pageNo").value = pagenum;
            document.info.action = "listConfig";
            document.info.submit();
        }

        function ChangePage(pagenum) {
            document.getElementById("pageNo").value = pagenum;
            document.info.action = "listConfig";
            document.info.submit();
        }

        function save() {
            document.info.submit();
        }

    </script>
</head>
<body>

    <div class="pageTitle">
        &nbsp;&nbsp;<img src="images/right1.gif" align="middle"/> &nbsp;<span id="MainTitle" style="color:white">考勤时间管理&gt;&gt;考勤时间配置</span>
    </div>

<form name="info" id="info" action="saveConfig" method="post">

    <table width="95%" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td colspan="2" height="10px">&nbsp;</td>
        </tr>
        <tr>
            <td width="">考勤时间配置</td>
            <td width="" align="right">
            </td>
        </tr>
        <tr>
            <td colspan="2" height="2px"></td>
        </tr>
    </table>
    <table width="95%" align="center" class="table_list" cellpadding="0" cellspacing="0">

        <tr class="listtitle">
            <td width="" align="center">上班考勤时间</td>
            <td width="" align="center">下班考勤时间</td>
            <td width="" align="center">操作</td>
        </tr>

        <c:if test="${config != null}">
            <tr class="list1" onmouseover="tr_mouseover(this)" onmouseout="tr_mouseout(this)">
                <td width="" align="center">

                    <input type="hidden" name="id" id="paramsConfig.config_id" value="${config.id}"/>

                    <input type="text" name="configDate1" id="paramsConfig.config_date1" value="${config.configDate1}"
                           style="text-align: center" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm'})">
                </td>

                <td width="" align="center">

                    <input type="" name="configDate2" id="paramsConfig.config_date2" value="${config.configDate2}"
                           style="text-align: center" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm'})">


                </td>

                <td width="" align="center">
                    <a href="javascript:void(0)" id="saveConfig" onclick="save()">更新</a>
                </td>
            </tr>
        </c:if>

        <c:if test="${config == null}">
            <tr>
                <td height="60" colspan="5" align="center"><b>&lt;不存在考勤时间信息&gt;</b></td>
            </tr>
        </c:if>


    </table>
</form>
</body>
</html>