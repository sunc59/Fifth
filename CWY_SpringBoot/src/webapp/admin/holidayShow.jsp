<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>节假日信息</title>
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
            document.info.action = "listHolidays";
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
                alert("请至少选择一个要删除的节假日！");
                return false;
            }
            if (confirm('确认删除吗!?')) {
                document.info.action = "delHolidays?ids=" + ids;
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
            document.info.action = "listHolidays";
            document.info.submit();
        }

        function ChangePage(pagenum) {
            document.getElementById("pageNo").value = pagenum;
            document.info.action = "listHolidays";
            document.info.submit();
        }
    </script>
</head>

<body>
    <div class="pageTitle">
        &nbsp;&nbsp;<img src="images/right1.gif" align="middle"/> &nbsp;<span id="MainTitle" style="color:white">节假日信息管理&gt;&gt;节假日信息查询</span>
    </div>

<form name="info" id="info" action="istHolidays" method="post">

    <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>

    <table width="95%" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td colspan="2" height="10px">&nbsp;</td>
        </tr>
        <tr>
            <td width="">节假日信息列表</td>

            <td width="" align="right">

                日期：
                <input type="date" name="holidayDate1" id="paramsHoliday.holiday_date1"
                       value="${paramsHoliday.holidayDate1}" style="width:120px"/>

                ---

                <input type="date" name="holidayDate2" id="paramsHoliday.holiday_date2"
                       value="${paramsHoliday.holidayDate2}" style="width:120px"/>&nbsp;&nbsp;

                节假日描述：
                <input type="text" name="holidayNote" id="paramsHoliday.holiday_note"
                       value="${paramsHoliday.holidayNote}" class="inputstyle" style="width:100px"/>&nbsp;&nbsp;

                <input type="button" value="搜索" onclick="serch();" class="btnstyle"/>&nbsp;

                <input type="button" value="删除" onclick="del();" class="btnstyle"/>

            </td>
        </tr>
        <tr>
            <td colspan="2" height="2px"></td>
        </tr>
    </table>

    <table width="95%" align="center" class="table_list" cellpadding="0" cellspacing="0">

        <tr class="listtitle">
            <td width="40" align="center"><input type="checkbox" onclick="CheckAll(this);"
                                                 style="vertical-align:text-bottom;" title="全选/取消全选"/></td>
            <td width="" align="center">序号</td>
            <td width="" align="center">节假日日期</td>
            <td width="" align="center">节假日描述</td>
            <td width="" align="center">操作</td>
        </tr>

        <c:if test="${holidays!=null && holidays.size()>0}">

            <c:forEach items="${holidays}" var="holiday" varStatus="status">

                <tr class="
                    <c:if test='${status.index % 2!=0}'>
                                list1
                           </c:if>
                           <c:if test='${status.index % 2==0}'>
                                list0
                           </c:if>" onmouseover="tr_mouseover(this)" onmouseout="tr_mouseout(this)">

                    <td width="" align="center">
                        <input type="checkbox" name="chkid" value="${holiday.id}"
                               cssStyle="vertical-align:text-bottom;"/>
                    </td>
                    <td width="" align="center">
                            ${status.index + 1 + ((pageNo - 1) * size)}
                    </td>
                    <td width="" align="center">
                            ${holiday.holidayDate}
                    </td>
                    <td width="" align="center">
                            ${holiday.holidayNote}
                    </td>
                    <td width="" align="center">
                        <img src="images/edit.png"/>&nbsp;
                        <a href="editHoliday?id=${holiday.id}">编辑</a>
                    </td>
                </tr>

            </c:forEach>

        </c:if>

        <c:if test="${holidays==null || holidays.size()==0}">
            <tr>
                <td height="60" colspan="5" align="center"><b>&lt;不存在节假日信息&gt;</b></td>
            </tr>
        </c:if>

    </table>

    <jsp:include page="page.jsp"></jsp:include>

</form>
</body>
</html>