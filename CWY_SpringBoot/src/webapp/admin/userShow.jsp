<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>员工信息</title>
    <link href="css/main.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>

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
            document.info.action = "listUsers";
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
                alert("请至少选择一个要删除的员工！");
                return false;
            }
            if (confirm('确认删除吗!?')) {
                document.info.action = "delUsers?ids=" + ids;
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
            document.info.action = "listUsers";
            document.info.submit();
        }

        function ChangePage(pagenum) {
            document.getElementById("pageNo").value = pagenum;
            document.info.action = "listUsers";
            document.info.submit();
        }
    </script>
</head>
<body>

    <div class="pageTitle">
        &nbsp;&nbsp;<img src="images/right1.gif" align="middle"/> &nbsp;<span id="MainTitle" style="color:white">员工管理&gt;&gt;员工查询</span>
    </div>

<form name="info" id="info" action="listUsers" method="post">

    <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>

    <table width="95%" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td colspan="2" height="10px">&nbsp;</td>
        </tr>
        <tr>
            <td width="">员工列表</td>
            <td width="" align="right">
                员工编号：
                <input type="text" id="paramsUser.user_no" name="userNo" value="${paramsUser.userNo}"
                       class="inputstyle" style="width:100px"/>&nbsp;
                姓名：
                <input type="text" id="paramsUser.real_name" name="realName" value="${paramsUser.realName}"
                       class="inputstyle" style="width:100px"/>&nbsp;
                部门：
                <select id="paramsUser.dept.dept_id" name="deptId" class="selectstyle" style="width: 155px">
                    <option value="0">请选择</option>
                    <c:forEach items="${depts}" var="dept">
                        <option value="${dept.id}">${dept.deptName}</option>
                    </c:forEach>
                </select>

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
            <td width="" align="center">员工编号</td>
            <td width="" align="center">用户名</td>
            <td width="" align="center">姓名</td>
            <td width="" align="center">性别</td>
            <td width="" align="center">部门</td>
            <td width="" align="center">操作</td>
        </tr>

        <c:if test="${users!=null && users.size()>0}">

            <c:forEach items="${users}" var="user" varStatus="status">

                <tr class="
                    <c:if test='${status.index % 2!=0}'>
                                list1
                           </c:if>
                           <c:if test='${status.index % 2==0}'>
                                list0
                           </c:if>" onmouseover="tr_mouseover(this)" onmouseout="tr_mouseout(this)">

                    <td width="" align="center">
                        <input type="checkbox" value="${user.id}" name="chkid" cssStyle="vertical-align:text-bottom;">
                    </td>
                    <td width="" align="center">
                            ${status.index + 1 + ((pageNo - 1) * size)}
                    </td>
                    <td width="" align="center">
                            ${user.userNo}
                    </td>
                    <td width="" align="center">
                            ${user.userName}
                    </td>
                    <td width="" align="center">
                            ${user.realName}
                    </td>
                    <td width="" align="center">
                            ${user.userSexDesc}
                    </td>
                    <td width="" align="center">
                            ${user.dept.deptName}
                    </td>
                    <td width="" align="center">
                        <img src="images/edit.png"/>&nbsp;
                        <a href="editUser?id=${user.id}">编辑</a>
                    </td>

                </tr>
            </c:forEach>
        </c:if>

        <c:if test="${users==null || users.size()==0}">
            <tr>
                <td height="60" colspan="8" align="center"><b>&lt;不存在员工信息&gt;</b></td>
            </tr>
        </c:if>

    </table>

    <jsp:include page="page.jsp"></jsp:include>
</form>
</body>
</html>