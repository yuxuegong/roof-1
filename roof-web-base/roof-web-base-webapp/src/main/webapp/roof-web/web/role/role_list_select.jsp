<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/roof-web/head.jsp"%>
<script type="text/javascript" src="${basePath }/roof-web/web/role/js/role_list_select.js"></script>
<script type="text/javascript" src="${basePath }/common/js/ROOF.Class.js"></script>
<script type="text/javascript" src="${basePath }/roof-web/web/js/ROOF.SelectableTable.js"></script>
<link rel="stylesheet" href="${basePath}/common/js/fort_awesome/css/font-awesome.min.css" />
<!--[if IE 7]>
  <link rel="stylesheet" href="${basePath}/common/js/fort_awesome/css/font-awesome-ie7.min.css" />
 <![endif]-->
<title></title>
</head>
<body>
	<div class="ui-table-toolbar">
		<ul class="yright">
			<li><a id="resetBtn" href="#"><i class="icon-repeat icon-large"></i> 重置</a>
			</li>
			<li><a id="searchBtn" href="#"><i class="icon-search icon-large"></i> 搜索</a>
			</li>
		</ul>
	</div>
	<form id="mainForm" action="${basePath }/roleAction!list_select.action" method="post">
		<table border="0" cellpadding="0" cellspacing="1" class="ui-table" width="100%">
			<tr>
				<td class="ui-table-header2" style="text-align: center;" width="20%">名称:</td>
				<td><input type="text" name="roles.name" style="width: 300px;" value="${roles.name }" />
				</td>
			</tr>
		</table>
	<table border="0" cellpadding="0" cellspacing="1" class="ui-table" width="100%" style="background-color: #C5D2E3;">
		<tr>
			<td class="ui-table-header2" style="text-align: center;" width="10%"></td>
			<td class="ui-table-header2" style="text-align: center;" width="10%">序号</td>
			<td class="ui-table-header2" style="text-align: center;" width="20%">名称</td>
			<td class="ui-table-header2" style="text-align: center;" width="30%">说明</td>
			<td class="ui-table-header2" style="text-align: center;" width="30%">创建日期</td>
		</tr>
		<c:forEach var="roles" items="${page.dataList }" varStatus="status">
			<tr>
				<td><input type="checkbox" name="roleses[${status.index }].id" value="${roles.id }" />
				</td>
				<td style="text-align: center;">${status.index + 1 }</td>
				<td style="text-align: center;">${roles.name }</td>
				<td style="text-align: center;">${roles.description }</td>
				<td style="text-align: center;"><fmt:formatDate value="${roles.create_date }" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@include file="/roof-web/page_bar.jsp"%>
	</form>
</body>
</html>