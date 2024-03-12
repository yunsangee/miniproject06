<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.domain.Product" %>
<%@ page import="com.model2.mvc.common.*" %>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>
    
<%
   Page resultPage = (Page)request.getAttribute("resultPage");
   
   Search search =(Search)request.getAttribute("search");
   String searchCondition = CommonUtil.null2str(search.getSearchCondition());
   String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
   
%>
<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetPurchaseList(currentPage){
    document.getElementById("currentPage").value = currentPage;
    document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listUser.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">>전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage } 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>

	
	  <c:set var="i" value="0" />
<c:forEach var="purchase" items="${list}">
    <c:set var="i" value="${i + 1}" />
    <tr class="ct_list_pop">
        <td align="center"><a href="/getPurchase.do?tranNo=${purchase.tranNo }">${i}</a></td>
        <td></td>
        <td align="left"><a href="/getUser.do?">${user.userId}</a></td>
        <td></td>
        <td align="left">${purchase.receiverName}</td>
        <td></td>
        <td align="left">${purchase.receiverPhone}</td>
         <td></td>
          <td align="left"></td>
         <td></td>
        <td align="left"><c:choose>
        <c:when test="${vo.proTranCode eq '0'}">
            판매중
        </c:when>
        <c:otherwise>
            재고없음
        </c:otherwise>
    </c:choose></td>
    </tr>
    <tr>
        <td colspan="11" bgcolor="D6D7D6" height="1"></td>
    </tr>
</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"   style="margin-top:10px;">
   <tr><td colspan="11">
         <input type="hidden" id="currentPage" name="currentPage" value=""/>
            <div style="text-align: center; margin-top: 10px;">
                <%-- 이전 페이지로 이동하는 링크 버튼 --%>
                <a href="javascript:fncGetPurchaseList(<%= resultPage.getBeginUnitPage() - 1 %>)">&lt;&lt; 이전</a>

                <%-- 페이지 번호 표시 부분 --%>
                <% for (int i = resultPage.getBeginUnitPage(); i <= resultPage.getEndUnitPage(); i++) { %>
                    <a href="javascript:fncGetPurchaseList(<%= i %>)"><%= i %></a>
                <% } %>

                <%-- 다음 페이지로 이동하는 링크 버튼 --%>
                <a href="javascript:fncGetPurchaseList(<%= resultPage.getEndUnitPage() + 1 %>)">다음 &gt;&gt;</a>
            </div>
        </td>
   </tr>

</table>
<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
</html>