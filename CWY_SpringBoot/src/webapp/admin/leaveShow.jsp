<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>员工请假信息</title>
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
            document.info.action = "listLeaves";
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
                alert("请至少选择一个要删除的员工请假！");
                return false;
            }
            if (confirm('确认删除吗!?')) {
                document.info.action = "delLeaves?ids=" + ids;
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
            document.info.action = "listLeaves";
            document.info.submit();
        }

        function ChangePage(pagenum) {
            document.getElementById("pageNo").value = pagenum;
            document.info.action = "listLeaves";
            document.info.submit();
        }
    </script>
</head>
<body>

<div class="pageTitle">
    &nbsp;&nbsp;<img src="images/right1.gif" align="middle"/> &nbsp;<span id="MainTitle" style="color:white">员工请假&gt;&gt;员工请假查询</span>
</div>

<form name="info" id="info" action="listLeaves" method="post">

    <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>

    <table width="95%" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td colspan="2" height="10px">&nbsp;</td>
        </tr>
        <tr>
            <td width="">员工请假列表</td>
            <td width="" align="right">
                日期：
                <input type="date" name="leaveDate1" id="paramsLeave.leave_date1"
                       value="${paramsLeave.leaveDate1Desc}" style="width:120px"/>
                ---

                <input type="date" name="leaveDate2" id="paramsLeave.leave_date2"
                       value="${paramsLeave.leaveDate2Desc}" style="width:120px"/>&nbsp;


                <c:if test="${admin.userType==2}">
                    姓名：
                    <input type="text" id="paramsLeave.real_name" name="realName"
                           value="${paramsLeave.realName}" class="inputstyle" style="width:100px"/>&nbsp;

                    部门：
                    <select name="deptId" class="selectstyle" style="width: 155px">
                        <option value="0">请选择</option>
                        <c:forEach items="${depts}" var="dept">
                            <option value="${dept.id}">${dept.deptName}</option>
                        </c:forEach>
                    </select>
                </c:if>

                审核状态：
                <select name="leaveFlag" class="selectstyle" style="width: 100px">
                    <option value="0">请选择</option>
                    <option value="1">待审核</option>
                    <option value="2">审核通过</option>
                    <option value="3">审核未通过</option>
                </select>&nbsp;&nbsp;

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
            <td width="40" align="center">
                <input type="checkbox" onclick="CheckAll(this);" style="vertical-align:text-bottom;" title="全选/取消全选"/>
            </td>
            <td width="" align="center">序号</td>
            <td width="" align="center">姓名</td>
            <td width="" align="center">部门</td>
            <td width="" align="center">请假类型</td>
            <td width="" align="center">请假起始</td>
            <td width="" align="center">请假截止</td>
            <td width="" align="center">请假原因</td>
            <td width="" align="center">状态</td>
            <td width="" align="center">操作</td>
        </tr>

        <c:if test="${leaves != null && leaves.size()>0}">

            <c:forEach items="${leaves}" var="leave" varStatus="status">

                <tr class="
                    <c:if test='${status.index % 2!=0}'>
                                list1
                           </c:if>
                           <c:if test='${status.index % 2==0}'>
                                list0
                           </c:if>" onmouseover="tr_mouseover(this)" onmouseout="tr_mouseout(this)">

                    <td width="" align="center">
                        <input type="checkbox" name="chkid" value="${leave.id}"
                               style="vertical-align: text-bottom"/>
                    </td>
                    <td width="" align="center">
                            ${status.index + 1 + ((pageNo - 1) * size)}
                    </td>
                    <td width="" align="center">
                            ${leave.user.realName}
                    </td>
                    <td width="" align="center">
                            ${leave.deptName}
                    </td>
                    <td width="" align="center">
                            ${leave.leaveTypeDesc}
                    </td>
                    <td width="" align="center">
                            ${leave.leaveDate1Desc}
                            ${leave.leaveLesson1Desc}
                    </td>
                    <td width="" align="center">
                            ${leave.leaveDate2Desc}
                            ${leave.leaveLesson2Desc}
                    </td>
                    <td width="" align="center">
                            ${leave.leaveReason}
                    </td>
                    <td width="" align="center">
                            ${leave.leaveFlagDesc}
                    </td>

                    <td width="" align="center">&nbsp;
                        <c:if test="${leave.leaveFlag==1 && admin.userType==1}">
                            <a href="editLeave?id=${leave.id}">编辑</a>
                        </c:if>
                        <c:if test="${leave.leaveFlag==1 && admin.userType==2}">
                            <a href="assessLeave?id=${leave.id}&leaveFlag=2">审核通过</a>
                            <br/>&nbsp;
                            <a href="assessLeave?id=${leave.id}&leaveFlag=3">审核驳回</a>
                        </c:if>
                    </td>

                </tr>

            </c:forEach>

        </c:if>


        <c:if test="${leaves == null || leaves.size()==0}">
            <tr>
                <td height="60" colspan="10" align="center"><b>&lt;不存在员工请假信息&gt;</b></td>
            </tr>
        </c:if>

    </table>

    <jsp:include page="page.jsp"></jsp:include>
</form>
</body>
</html>