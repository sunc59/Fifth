<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<table width="95%" align="center" cellpadding="0" cellspacing="0">
  <tr><td colspan="2" height="5px"></td></tr>
  <tr>
    <td width="50%" style="text-align:right;padding-right:5px;">共 ${totalCount} 条，第 ${pageNo}/${pageCount} 页</td>

	  <td width="50%" style="text-align:right;padding-right:5px;">

			<c:if test="${pageNo > 1}">
				<a href="javascript:ChangePage('1');" class="alinkstyle">首页</a>
				<a href="javascript:ChangePage('${pageNo-1}');" class="alinkstyle">前页</a>
			</c:if>
			<c:if test="${pageNo == 1}">
				&nbsp;首页
				&nbsp;前页
			</c:if>


			<c:if test="${pageNo < pageCount}">
		        <a href="javascript:ChangePage('${pageNo+1}');" class="alinkstyle">后页</a>&nbsp;&nbsp;<a href="javascript:ChangePage('${pageCount}');" class="alinkstyle">末页</a>
		    </c:if>
		    <c:if test="${pageNo >= pageCount}">
				&nbsp;后页
			    &nbsp;末页
			</c:if>

	        &nbsp;<input type="text" name="goPage" id="goPage" class="inputpagestyle" />
	        &nbsp;<input type="button" value="GO" onclick="GoPage();" class="btnstyle"/>
	</td>
  </tr>
</table>  
	 