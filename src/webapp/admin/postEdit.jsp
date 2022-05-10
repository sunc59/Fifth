<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        <s:if test="#attr.post!=null && #attr.post.post_id!=0">编辑</s:if>
        <s:else>添加</s:else>离岗信息
    </title>
    <link href="css/main.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>

    <script language="javascript" type="text/javascript">
        $(document).ready(function () {
            var num = /^\d+$/;
            $("#addBtn").bind('click', function () {
                if ($("#paramsPost\\.post_date1").val() == '') {
                    alert('离岗起始日期不能为空');
                    return;
                }
                if ($("#paramsPost\\.post_lesson1").val() == '0') {
                    alert('离岗起始上午/下午不能为空');
                    return;
                }
                if ($("#paramsPost\\.post_date2").val() == '') {
                    alert('离岗截止日期不能为空');
                    return;
                }
                if ($("#paramsPost\\.post_lesson2").val() == '0') {
                    alert('离岗截止上午/下午不能为空');
                    return;
                }
                if ($("#paramsPost\\.post_reason").val() == '') {
                    alert('离岗原因不能为空');
                    return;
                }

                $("#paramsPost\\.post_id").val(0);
                $("#info").attr('action', 'addPost').submit();

            });

            $("#editBtn").bind('click', function () {
                if ($("#paramsPost\\.post_date1").val() == '') {
                    alert('离岗起始日期不能为空');
                    return;
                }
                if ($("#paramsPost\\.post_lesson1").val() == '0') {
                    alert('离岗起始上午/下午不能为空');
                    return;
                }
                if ($("#paramsPost\\.post_date2").val() == '') {
                    alert('离岗截止日期不能为空');
                    return;
                }
                if ($("#paramsPost\\.post_lesson2").val() == '0') {
                    alert('离岗截止上午/下午不能为空');
                    return;
                }
                if ($("#paramsPost\\.post_reason").val() == '') {
                    alert('离岗原因不能为空');
                    return;
                }
                $("#info").attr('action', 'savePost').submit();

            });

        });
    </script>
    <style type="text/css">
    </style>
</head>
<body>

<div class="pageTitle">
    &nbsp;&nbsp;<img src="images/right1.gif" align="middle"/> &nbsp;<span id="MainTitle" style="color:white">离岗管理&gt;&gt;
            <c:if test="${post!=null && post.id!=0}">编辑</c:if>
            <c:if test="${post==null || post.id==0}">添加</c:if>离岗信息</span>
</div>

<form id="info" name="info" action="addPost" method="post">

    <input type="hidden" id="paramsPost.post_id" name="id" value="${post.id}"/>

    <table width="800" align="center" cellpadding="0" cellspacing="0" style="margin-top:10px;margin-bottom:10px;">
        <tr>
            <td height="24">
                <Table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
                    <TR>
                        <TD height="24" class="edittitleleft">&nbsp;</TD>
                        <TD class="edittitle">
                            <c:if test="${post!=null && post.id!=0}">编辑</c:if>
                            <c:if test="${post==null || post.id==0}">添加</c:if>离岗信息</span>
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
                        <td width="35%" align="right" style="padding-right:5px"><font color="red">*</font> 离岗起始：</td>
                        <td width="65%">
                            <input type="date" name="postDate1" id="paramsPost.post_date1"
                                   value="${post.postDate1Desc}" style="width:120px"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="padding-right:5px"><font color="red">*</font> 上午/下午：</td>
                        <td>
                            <select name="postLesson1" class="selectstyle" style="width: 100px">
                                <option value="0">请选择</option>
                                <c:if test="${post.postLesson1 == 1}">
                                    <option value="1" selected>上午</option>
                                    <option value="2">下午</option>
                                </c:if>
                                <c:if test="${post.postLesson1 == 2}">
                                    <option value="1">上午</option>
                                    <option value="2" selected>下午</option>
                                </c:if>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="padding-right:5px"><font color="red">*</font> 离岗截止：</td>
                        <td>
                            <input type="date" name="postDate2" id="paramsPost.post_date2"
                                   value="${post.postDate2Desc}" style="width:120px"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="padding-right:5px"><font color="red">*</font> 上午/下午：</td>
                        <td>
                            <select name="postLesson2" class="selectstyle" style="width: 100px">
                                <option value="0">请选择</option>
                                <c:if test="${post.postLesson2 == 1}">
                                    <option value="1" selected>上午</option>
                                    <option value="2">下午</option>
                                </c:if>
                                <c:if test="${post.postLesson2 == 2}">
                                    <option value="1">上午</option>
                                    <option value="2" selected>下午</option>
                                </c:if>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="padding-right:5px"><font color="red">*</font> 离岗原因：</td>
                        <td>
                            <input type="text" name="postReason" id="paramsPost.post_reason"
                                   value="${post.postReason}">
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
                            <c:if test="${post!=null && post.id!=0}">
                                <input type="button" id="editBtn" Class="btnstyle" value="编 辑"/>
                            </c:if>
                            <c:if test="${post==null || post.id==0}">
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