<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        <c:if test="${holiday!=null && holiday.id!=0}">
            编辑
        </c:if>
        <c:if test="${holiday==null || holiday.id==0}">
            添加
        </c:if>节假日信息</title>

    <link href="css/main.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>

    <script language="javascript" type="text/javascript">

        $(document).ready(function () {
            var num = /^\d+$/;
            $("#addBtn").bind('click', function () {
                if ($("#paramsHoliday\\.holiday_date1").val() == '') {
                    alert('节假日起始日期不能为空');
                    return;
                }
                if ($("#paramsHoliday\\.holiday_date2").val() == '') {
                    alert('节假日截止日期不能为空');
                    return;
                }
                if ($("#paramsHoliday\\.holiday_note").val() == '') {
                    alert('节假日描述不能为空');
                    return;
                }

                $("#paramsHoliday\\.holiday_id").val(0);
                $("#info").attr('action', 'addHoliday').submit();

            });

            $("#editBtn").bind('click', function () {
                if ($("#paramsHoliday\\.holiday_note").val() == '') {
                    alert('节假日描述不能为空');
                    return;
                }
                $("#info").attr('action', 'saveHoliday').submit();

            });

        });
    </script>
    <style type="text/css">
    </style>
</head>
<body>

    <div class="pageTitle">
        &nbsp;&nbsp;<img src="images/right1.gif" align="middle"/> &nbsp;<span id="MainTitle" style="color:white">节假日信息管理&gt;&gt;
        <c:if test="${holiday != null && holiday.id != 0}">
            编辑
        </c:if>
        <c:if test="${holiday == null || holiday.id == 0}">
            添加
        </c:if>节日信息
    </div>

    <form id="info" name="info" action="addHoliday" method="post">

        <input type="hidden" id="paramsHoliday.holiday_id" name="id" value="${holiday.id}">

        <table width="800" align="center" cellpadding="0" cellspacing="0" style="margin-top:10px;margin-bottom:10px;">
            <tr>
                <td height="24">
                    <Table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
                        <TR>
                            <TD height="24" class="edittitleleft">&nbsp;</TD>
                            <TD class="edittitle">
                                <c:if test="${holiday != null && holiday.id != 0}">
                                    编辑
                                </c:if>
                                <c:if test="${holiday == null || holiday.id == 0}">
                                    添加
                                </c:if>节日信息
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

                        <c:if test="${holiday != null && holiday.id != 0}">
                            <tr>
                                <td width="35%" align="right" style="padding-right:5px"><font color="red">*</font> 节假日日期：
                                </td>
                                <td width="65%">
                                    ${holiday.holidayDate}
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${holiday == null || holiday.id == 0}">
                            <tr>
                                <td width="35%" align="right" style="padding-right:5px"><font color="red">*</font> 节假日起始：
                                </td>
                                <td width="65%">

                                    <input type="date" name="holidayDate1" id="paramsHoliday.holiday_date1"
                                           value="${holiday.holidayDate1}"
                                           onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})">
                                </td>
                            </tr>
                            <tr>
                                <td align="right" style="padding-right:5px"><font color="red">*</font> 节假日截止：</td>
                                <td>

                                    <input type="date" name="holidayDate2" id="paramsHoliday.holiday_date2"
                                           value="${holiday.holidayDate2}"
                                           onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})">
                                </td>
                            </tr>
                        </c:if>

                        <tr>
                            <td align="right" style="padding-right:5px"><font color="red">*</font> 节假日描述：</td>
                            <td>

                                <input type="text" name="holidayNote" id="paramsHoliday.holiday_note"
                                       value="${holiday.holidayNote}">
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

                                <c:if test="${holiday != null && holiday.id != 0}">
                                    <input type="button" id="editBtn" Class="btnstyle" value="编 辑"/>
                                </c:if>
                                <c:if test="${holiday == null || holiday.id == 0}">
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