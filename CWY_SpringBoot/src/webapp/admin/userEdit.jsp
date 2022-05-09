<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        <c:if test="${user!=null && user.id!=0}">编辑</c:if>
        <c:if test="${user==null || user.id==0}">添加</c:if>员工信息
    </title>

    <link href="css/main.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>

    <script language="javascript" type="text/javascript">

        $(document).ready(function () {
            var user_sex = "${user.userSex}";
            if (user_sex != '') {
                $("#sex" + user_sex).attr('checked', 'checked');
            } else {
                $("#sex1").attr('checked', 'checked');
            }

            var num = /^\d+$/;
            $("#addBtn").bind('click', function () {
                if ($("#paramsUser\\.user_no").val() == '') {
                    alert('员工编号不能为空');
                    return;
                }
                if ($("#paramsUser\\.user_name").val() == '') {
                    alert('用户名不能为空');
                    return;
                }
                if ($("#paramsUser\\.user_pass").val() == '') {
                    alert('密码不能为空');
                    return;
                }
                if ($("#paramsUser\\.real_name").val() == '') {
                    alert('姓名不能为空');
                    return;
                }
                if ($("#paramsUser\\.dept\\.dept_id").val() == '0') {
                    alert('所在部门不能为空');
                    return;
                }

                $("#paramsUser\\.user_id").val(0);
                $("#info").attr('action', 'addUser').submit();

            });

            $("#editBtn").bind('click', function () {
                if ($("#paramsUser\\.real_name").val() == '') {
                    alert('姓名不能为空');
                    return;
                }
                if ($("#paramsUser\\.dept\\.dept_id").val() == '0') {
                    alert('所在部门不能为空');
                    return;
                }
                $("#info").attr('action', 'saveUser').submit();

            });

        });
    </script>
    <style type="text/css">
    </style>
</head>
<body>
<div class="pageTitle">
    &nbsp;&nbsp;<img src="images/right1.gif" align="middle"/> &nbsp;

    <span id="MainTitle" style="color:white">员工管理&gt;&gt;
                <c:if test="${user!=null && user.id!=0}">编辑</c:if>
                <c:if test="${user==null || user.id==0}">添加</c:if>员工
            </span>
</div>

<form id="info" name="info" action="addUser" method="post">

    <input type="hidden" id="paramsUser.user_id" name="id" value="${user.id}">

    <table width="800" align="center" cellpadding="0" cellspacing="0" style="margin-top:10px;margin-bottom:10px;">
        <tr>
            <td height="24">
                <Table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
                    <TR>
                        <TD height="24" class="edittitleleft">&nbsp;</TD>
                        <TD class="edittitle">
                            <c:if test="${user!=null && user.id!=0}">编辑</c:if>
                            <c:if test="${user==null || user.id==0}">添加</c:if>员工
                        </TD>
                        <TD class="edittitleright">&nbsp;</TD>
                    </TR>
                </TABLE>
            </td>
        </tr>
        <tr>
            <td height="1" bgcolor="#8f8f8f"></td>
        </tr>
        <tr>
            <td>
                <table width="100%" align="center" cellpadding="1" cellspacing="1" class="editbody">
                    <tr>
                        <td width="35%" align="right" style="padding-right:5px"><font color="red">*</font> 员工编号：</td>
                        <td width="65%">
                            <c:if test="${user!=null && user.id!=0}">
                                ${user.userNo}
                            </c:if>
                            <c:if test="${user==null || user.id==0}">
                                <input name="userNo" id="paramsUser.user_no" value="${user.userNo}">
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="padding-right:5px"><font color="red">*</font> 用户名：</td>
                        <td>
                            <c:if test="${user!=null && user.id!=0}">
                                ${user.userName}
                            </c:if>
                            <c:if test="${user==null || user.id==0}">
                                <input name="userName" id="paramsUser.user_name" value="${user.userName}">
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="padding-right:5px"><font color="red">*</font> 密码：</td>
                        <td>
                            <c:if test="${user!=null && user.id!=0}">
                                <input type="password" name="userPass" id="paramsUser.user_pass" value=""
                                       showPassword="true"/>
                            </c:if>
                            <c:if test="${user==null || user.id==0}">
                                <input type="password" name="userPass" id="paramsUser.user_pass" value="111111"
                                       showPassword="true"/>
                                <span id="passTip" style="color:red;">初始密码：111111</span>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="padding-right:5px"><font color="red">*</font> 姓名：</td>
                        <td>
                            <input type="text" name="realName" id="paramsUser.real_name" value="${user.realName}"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="padding-right:5px"><font color="red">*</font> 性别：</td>
                        <td>
                            <input type="radio" id="sex1" name="userSex" value="1"/>男&nbsp;&nbsp;
                            <input type="radio" id="sex2" name="userSex" value="2"/>女
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="padding-right:5px"><font color="red">*</font> 所在部门：</td>
                        <td>
                            <select id="paramsUser.dept.dept_id" name="deptId" class="selectstyle" style="width: 155px">
                                <option>请选择</option>
                                <c:forEach items="${depts}" var="dept">
                                    <c:if test="${user.deptId == dept.id}">
                                        <option value="${dept.id}" selected>${dept.deptName}</option>
                                    </c:if>
                                    <c:if test="${user.deptId != dept.id}">
                                        <option value="${dept.id}">${dept.deptName}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <table width="100%" align="center" cellpadding="0" cellspacing="0" class="editbody">
                    <tr class="editbody">
                        <td align="center" height="30">

                            <c:if test="${user!=null && user.id!=0}">
                                <input type="button" id="editBtn" Class="btnstyle" value="编 辑"/>
                            </c:if>
                            <c:if test="${user==null || user.id==0}">
                                <input type="button" id="addBtn" Class="btnstyle" value="添 加"/>
                            </c:if>

                            &nbsp;<label style="color:red">${errTip}</label>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
</body>
</html>