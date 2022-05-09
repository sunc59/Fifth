<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        <c:if test="${dept!=null && dept.id!=0}">编辑</c:if>
        <c:if test="${dept==null || dept.id==0}">添加</c:if>部门信息
    </title>

    <link href="css/main.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>

    <script language="javascript" type="text/javascript">
        $(document).ready(function () {

            var num = /^\d+(\.\d+)?$/;
            $("#addBtn").bind('click', function () {
                if ($("#paramsDept\\.dept_name").val() == '') {
                    alert('部门名称不能为空');
                    return;
                }
                $("#paramsDept\\.dept_id").val(0);
                $("#info").attr('action', 'addDept').submit();

            });

            $("#editBtn").bind('click', function () {
                if ($("#paramsDept\\.dept_name").val() == '') {
                    alert('部门名称不能为空');
                    return;
                }
                $("#info").attr('action', 'saveDept').submit();

            });

        });
    </script>
    <style type="text/css">
    </style>
</head>
<body>
<div class="pageTitle">
    &nbsp;&nbsp;<img src="images/right1.gif" align="middle"/> &nbsp;
    <span id="MainTitle" style="color:white">部门管理&gt;&gt;
            <c:if test="${dept!=null and dept.id!=0}">
                编辑
            </c:if>
            <c:if test="${dept==null or dept.id==0}">
                添加
            </c:if>部门
        </span>
</div>

<form id="info" name="info" action="addDept" method="post">
    <input type="hidden" id="paramsDept.dept_id" name="id" value="${dept.id}"/>
    <table width="800" align="center" cellpadding="0" cellspacing="0" style="margin-top:10px;margin-bottom:10px;">
        <tr>
            <td height="24">
                <Table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
                    <TR>
                        <TD height="24" class="edittitleleft">&nbsp;</TD>
                        <TD class="edittitle">
                            <c:if test="${dept!=null && dept.id!=0}">编辑</c:if>
                            <c:if test="${dept==null || dept.id==0}">添加</c:if>部门
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
                        <td width="35%" align="right" style="padding-right:5px"><font color="red">*</font> 部门名称：</td>
                        <td width="65%">
                            <input type="text" name="deptName" id="paramsDept.dept_name" value="${dept.deptName}"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="35%" align="right" style="padding-right:5px">部门描述：</td>
                        <td width="65%">
                            <textarea rows="4" cols="30" name="deptNote"
                                      id="paramsDept.dept_note">${dept.deptNote}</textarea>
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
                            <c:if test="${dept!=null && dept.id!=0}">
                                <input type="button" id="editBtn" Class="btnstyle" value="编 辑"/>
                            </c:if>
                            <c:if test="${dept==null || dept.id==0}">
                                <input type="button" id="addBtn" Class="btnstyle" value="添 加"/>
                            </c:if>
                            <label id="errTip" style="color:red">${errTip}</label>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
</body>
</html>