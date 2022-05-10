<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        <c:if test="${leave!=null && leave.id!=0}">
            编辑
        </c:if>
        <c:if test="${leave==null || leave.id==0}">
            添加
        </c:if>请假信息
    </title>

    <link href="css/main.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>

    <script language="javascript" type="text/javascript">
        $(document).ready(function () {
            var num = /^\d+$/;
            $("#addBtn").bind('click', function () {
                if ($("#paramsLeave\\.leave_type").val() == '0') {
                    alert('请假类型不能为空');
                    return;
                }
                if ($("#paramsLeave\\.leave_date1").val() == '') {
                    alert('请假起始日期不能为空');
                    return;
                }
                if ($("#paramsLeave\\.leave_lesson1").val() == '0') {
                    alert('请假起始上午/下午不能为空');
                    return;
                }
                if ($("#paramsLeave\\.leave_date2").val() == '') {
                    alert('请假截止日期不能为空');
                    return;
                }
                if ($("#paramsLeave\\.leave_lesson2").val() == '0') {
                    alert('请假截止上午/下午不能为空');
                    return;
                }
                if ($("#paramsLeave\\.leave_reason").val() == '') {
                    alert('请假原因不能为空');
                    return;
                }

                $("#paramsLeave\\.leave_id").val(0);
                $("#info").attr('action', 'addLeave').submit();

            });

            $("#editBtn").bind('click', function () {
                if ($("#paramsLeave\\.leave_type").val() == '0') {
                    alert('请假类型不能为空');
                    return;
                }
                if ($("#paramsLeave\\.leave_date1").val() == '') {
                    alert('请假起始日期不能为空');
                    return;
                }
                if ($("#paramsLeave\\.leave_lesson1").val() == '0') {
                    alert('请假起始上午/下午不能为空');
                    return;
                }
                if ($("#paramsLeave\\.leave_date2").val() == '') {
                    alert('请假截止日期不能为空');
                    return;
                }
                if ($("#paramsLeave\\.leave_lesson2").val() == '0') {
                    alert('请假截止上午/下午不能为空');
                    return;
                }
                if ($("#paramsLeave\\.leave_reason").val() == '') {
                    alert('请假原因不能为空');
                    return;
                }
                $("#info").attr('action', 'saveLeave').submit();

            });

        });
    </script>
</head>

<body>

    <div class="pageTitle">
        &nbsp;&nbsp;<img src="images/right1.gif" align="middle"/> &nbsp;
        <span id="MainTitle" style="color:white">请假管理&gt;&gt;
            <c:if test="${leave!=null && leave.id!=0}">
                编辑
            </c:if>
            <c:if test="${leave==null || leave.id==0}">
                添加
            </c:if>请假信息
        </span>
    </div>

<form id="info" name="info" action="addLeave" method="post">

    <input type="hidden" id="paramsLeave.leave_id" name="id" value="${leave.id}"/>

    <table width="800" align="center" cellpadding="0" cellspacing="0" style="margin-top:10px;margin-bottom:10px;">
        <tr>
            <td height="24">
                <Table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
                    <TR>
                        <TD height="24" class="edittitleleft">&nbsp;</TD>
                        <TD class="edittitle">
                            <c:if test="${leave!=null && leave.id!=0}">
                                编辑
                            </c:if>
                            <c:if test="${leave==null || leave.id==0}">
                                添加
                            </c:if>请假信息
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
                        <td width="35%" align="right" style="padding-right:5px"><font color="red">*</font> 请假类型：</td>
                        <td width="65%">

                            <select id="paramsLeave.leave_type" name="leaveType"
                                    class="selectstyle" style="width: 155px">

                                <option value="0">请选择</option>
                                <c:if test="${leave.leaveType == 1}">
                                    <option value="1" selected>年假</option>
                                    <option value="2">病假</option>
                                    <option value="3">事假</option>
                                </c:if>
                                <c:if test="${leave.leaveType == 2}">
                                    <option value="1">年假</option>
                                    <option value="2" selected>病假</option>
                                    <option value="3">事假</option>
                                </c:if>
                                <c:if test="${leave.leaveType == 3}">
                                    <option value="1">年假</option>
                                    <option value="2">病假</option>
                                    <option value="3" selected>事假</option>
                                </c:if>
                            </select>

                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="padding-right:5px"><font color="red">*</font> 请假起始：</td>
                        <td>

                            <input type="date" name="leaveDate1" id="paramsLeave.leave_date1"
                                   value="${leave.leaveDate1Desc}">

                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="padding-right:5px"><font color="red">*</font> 上午/下午：</td>
                        <td>
                            <select id="paramsLeave.leave_lesson1" name="leaveLesson1"
                                    class="selectstyle" style="width: 155px">

                                <option value="0">请选择</option>

                                <c:if test="${leave.leaveLesson1 == 1}">
                                    <option value="1" selected>上午</option>
                                    <option value="2">下午</option>
                                </c:if>
                                <c:if test="${leave.leaveLesson1 == 2}">
                                    <option value="1">上午</option>
                                    <option value="2" selected>下午</option>
                                </c:if>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="padding-right:5px"><font color="red">*</font> 请假截止：</td>
                        <td>
                            <input type="date" name="leaveDate2" id="paramsLeave.leave_date2"
                                   value="${leave.leaveDate2Desc}">
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="padding-right:5px"><font color="red">*</font> 上午/下午：</td>
                        <td>
                            <select id="paramsLeave.leave_lesson2" name="leaveLesson2"
                                    class="selectstyle" style="width: 155px">
                                <option value="0">请选择</option>

                                <c:if test="${leave.leaveLesson2 == 1}">
                                    <option value="1" selected>上午</option>
                                    <option value="2">下午</option>
                                </c:if>
                                <c:if test="${leave.leaveLesson2 == 2}">
                                    <option value="1">上午</option>
                                    <option value="2" selected>下午</option>
                                </c:if>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="padding-right:5px"><font color="red">*</font> 请假原因：</td>
                        <td>
                            <input type="text" id="paramsLeave.leave_reason" name="leaveReason"
                                   value="${leave.leaveReason}">
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
                            <c:if test="${leave!=null && leave.id!=0}">
                                <input type="button" id="editBtn" Class="btnstyle" value="编 辑"/>
                            </c:if>
                            <c:if test="${leave==null || leave.id==0}">
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