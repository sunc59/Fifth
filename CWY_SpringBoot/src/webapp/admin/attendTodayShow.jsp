<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>本日签到信息</title>
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
            document.info.action = "listAttendToday";
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
                alert("请至少选择一个要删除的本日签到！");
                return false;
            }
            if (confirm('确认删除吗!?')) {
                document.info.action = "delAttends?ids=" + ids;
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
            document.info.action = "listAttendToday";
            document.info.submit();
        }

        function ChangePage(pagenum) {
            document.getElementById("pageNo").value = pagenum;
            document.info.action = "listAttendToday";
            document.info.submit();
        }
    </script>
</head>
<body>

<div class="pageTitle">
    &nbsp;&nbsp;<img src="images/right1.gif" align="middle"/> &nbsp;<span id="MainTitle" style="color:white">员工签到&gt;&gt;本日签到查询</span>
</div>

<form name="info" id="info" action="listAttendToday" method="post">

    <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>

    <table width="95%" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td colspan="2" height="10px">&nbsp;</td>
        </tr>
        <tr>
            <td width="">本日签到列表</td>
            <td width="" align="right">

                <c:if test="${admin.userType==2}">
                    姓名：
                    <input type="text" id="paramsAttend.real_name" name="realName"
                           value="${paramsAttend.realName}" class="inputstyle" style="width:100px"/>&nbsp;
                    部门：
                    <select name="deptId" class="selectstyle" style="width: 155px">
                        <option value="0">请选择</option>
                        <c:forEach items="${depts}" var="dept">
                            <option value="${dept.id}">${dept.deptName}</option>
                        </c:forEach>
                    </select>&nbsp;
                    签到情况：
                    <select name="attendType" class="selectstyle" style="width: 155px">
                        <option value="0">请选择</option>
                        <option value="1">未签到</option>
                        <option value="2">已签到</option>
                        <option value="3">迟签到</option>
                        <option value="4">请假</option>
                        <option value="5">离岗</option>
                        <option value="6">早退</option>
                    </select>&nbsp;&nbsp;

                    <input type="button" value="搜索" onclick="serch();" class="btnstyle"/>

                </c:if>
            </td>
        </tr>
        <tr>
            <td colspan="2" height="2px"></td>
        </tr>
    </table>

    <table width="95%" align="center" class="table_list" cellpadding="0" cellspacing="0">
        <tr class="listtitle">
            <td width="" align="center">序号</td>
            <td width="" align="center">员工编号</td>
            <td width="" align="center">姓名</td>
            <td width="" align="center">性别</td>
            <td width="" align="center">部门</td>
            <td width="" align="center">上班签到</td>
            <td width="" align="center">下班签到</td>
        </tr>

        <c:if test="${holidayNote==null}">

            <c:if test="${attends!=null && attends.size()>0}">

                <c:forEach items="${attends}" var="attend" varStatus="status">
                    <tr class="
                        <c:if test='${status.index % 2!=0}'>
                                list1
                        </c:if>
                        <c:if test='${status.index % 2==0}'>
                                list0
                        </c:if>" onmouseover="tr_mouseover(this)" onmouseout="tr_mouseout(this)">

                        <td width="" align="center">
                                ${status.index + 1 + ((pageNo - 1) * size)}
                        </td>
                        <td width="" align="center">
                                ${attend.userNo}
                        </td>
                        <td width="" align="center">
                                ${attend.realName}
                        </td>
                        <td width="" align="center">
                                ${attend.userSexDesc}
                        </td>
                        <td width="" align="center">
                                ${attend.deptName}
                        </td>

                        <c:if test="${ap==1 && attend.attendType1==1 && admin.userType==1}">
                            <td width="" align="center">
                                <a href="addAttend?attendLesson=1">点击签到</a>
                            </td>
                        </c:if>
                        <c:if test="${ap!=1 || attend.attendType1!=1 || admin.userType!=1}">
                            <td width="" align="center" style="background-color:${attend.attendType1Color}">
                                ${attend.attendType1Desc}
                            </td>
                        </c:if>

                        <c:if test="${ap==2 && attend.attendType2==1 && admin.userType==1}">
                            <td width="" align="center">
                                <a href="addAttend?attendLesson=2">点击签到</a>
                            </td>
                        </c:if>
                        <c:if test="${(ap!=2 || attend.attendType2!=1 || admin.userType!=1)
                                   && (ap==2 && attend.attendType2!=1)}">
                            <td width="" align="center" style="background-color:${attend.attendType2Color}">
                                    ${attend.attendType2Desc}
                            </td>
                        </c:if>
                        <c:if test="${(ap!=2 || attend.attendType2!=1 || admin.userType!=1)
                                   && (ap!=2 || attend.attendType2==1)}">
                            <td width="" align="center">——</td>
                        </c:if>

                    </tr>

                </c:forEach>

            </c:if>

            <c:if test="${attends==null || attends.size()==0}">
                <tr>
                    <td height="60" colspan="9" align="center"><b>&lt;不存在本日签到信息&gt;</b></td>
                </tr>
            </c:if>

        </c:if>

        <c:if test="${holidayNote!=null}">
            <tr>
                <td height="60" colspan="9" align="center"><b>&lt;今日为节假日（${holidayNote}）&gt;</b></td>
            </tr>
        </c:if>



        <%--<s:if test="#attr.holiday_note==null">
            <s:if test="#attr.attends!=null && #attr.attends.size()>0">
                <s:iterator value="#attr.attends" id="attend" status="status">
                    <tr class="<s:if test='(#status.index + 1)%2==0'>list1</s:if><s:else>list0</s:else>"
                        onmouseover="tr_mouseover(this)" onmouseout="tr_mouseout(this)">

                        <td width="" align="center"><s:property value="#status.index+#attr.paramsAttend.start+1"/></td>
                        <td width="" align="center"><s:property value="#attend.user_no"/></td>
                        <td width="" align="center"><s:property value="#attend.real_name"/></td>
                        <td width="" align="center"><s:property value="#attend.user_sexDesc"/></td>
                        <td width="" align="center"><s:property value="#attend.dept_name"/></td>

                        <s:if test="#attr.ap==1 && #attend.attend_type1==1 && #attr.admin.user_type==1">
                            <td width="" align="center">
                                <s:a href="Admin_addAttend.action?paramsAttend.attend_lesson=1">点击签到</s:a>
                            </td>
                        </s:if>
                        <s:else>
                            <td width="" align="center"
                                style="background-color:<s:property value='#attend.attend_type1Color'/>">
                                <s:property value="#attend.attend_type1Desc"/>
                            </td>
                        </s:else>

                        <s:if test="#attr.ap==2 && #attend.attend_type2==1 && #attr.admin.user_type==1">
                            <td width="" align="center">
                                <s:a href="Admin_addAttend.action?paramsAttend.attend_lesson=2">点击签到</s:a>
                            </td>
                        </s:if>
                        <s:elseif test="#attr.ap==2 && #attend.attend_type2!=1">
                            <td width="" align="center"
                                style="background-color:<s:property value='#attend.attend_type2Color'/>">
                                <s:property value="#attend.attend_type2Desc"/>
                            </td>
                        </s:elseif>
                        <s:else>
                            <td width="" align="center">——</td>
                        </s:else>
                    </tr>
                </s:iterator>
            </s:if>
            <s:else>
                <tr>
                    <td height="60" colspan="9" align="center"><b>&lt;不存在本日签到信息&gt;</b></td>
                </tr>
            </s:else>
        </s:if>

        <s:else>
            <tr>
                <td height="60" colspan="9" align="center"><b>&lt;今日为节假日（<s:property
                        value="#attr.holiday_note"/>）&gt;</b></td>
            </tr>
        </s:else>--%>


    </table>

    <jsp:include page="page.jsp"></jsp:include>
</form>
</body>
</html>