<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${tableDisplay}管理</title>

<%@include file="/web_common/web_head_boot.jsp"%>
<script type="text/javascript" src="${includeBase}/selin/${alias}/${alias}_list.js"></script>
</head>
<#assign key = primaryKey[0] />
<body>
	<!-- 增删改成模块 start -->
	<div class="panel panel-default" style="background-color: #f8f8f8; overflow: hidden;">
		<div class="panel-heading">${tableDisplay}列表</div>
		<div class="panel-body">
			<form id="${sysName}_${alias}_search_form" class="form-inline" method="post" action="${includeBase}/selin/${actionName}Action/list.action">
				<%@include file="/web_common/web_page_bar_cond_default.jsp"%>
				<#list fields as field>
				<div class="form-group">
					<label class="" for="exampleInputName2">${(field.fieldDisplay)!''}:</label> 
					<input id="${alias}_${field.fieldName}" <#if (field.len ??)>maxlength="${field.len?c}" </#if>name="${field.fieldName}" type="text" class="form-control input-sm"
					<#if (field.fieldType == "Date")>readonly value="<fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd" />"
					<#else>value="${el$}{${alias}.${field.fieldName} }" </#if> >
				</div>
				</#list>
				<button id="serchBtn" type="button" class="btn btn-default">查询</button>
				<button id="reset" type="button" class="btn btn-default">重置</button>
			</form>
		</div>
		<div style="margin: 0 15px; background-color: white;">
			<div class="oper-bar">
				<button id="${sysName}_${alias}_create_btn" type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				</button>
				<button id="${sysName}_${alias}_delete_btn" type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
				</button>
				<button id="${sysName}_${alias}_update_btn" type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑
				</button>
			</div>
			<!-- Table -->
			<table id="${sysName}_${alias}_table" class="table table-bordered table-hover" style="margin-bottom: 0;">
				<thead>
					<tr class='active'>
						<th>选择</th> 
						<#list fields as field> 
						<#if field.fieldName != key>
						<th>${(field.fieldDisplay)!''}</th> 
						</#if> 
						</#list>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="${alias}" items="${el$}{page.dataList }" varStatus="status">
						<tr>
							<td><input type="checkbox" name="${key}" class="ck" value="${el$}{${alias}.${key} }"> <#if (drdsId ??)> 
							<input type="hidden" name="${drdsId}" class="ck" value="${el$}{${alias}.${drdsId} }"> </#if></td> 
							<#list fields as field> 
							<#if field.fieldName != key> 
							<#if field.dbType == "datetime">
							<td><fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
							<#elseif field.dbType == "date">
							<td><fmt:formatDate value="${el$}{${alias}.${field.fieldName} }" pattern="yyyy-MM-dd" /></td> 
							<#else>
							<td>${el$}{${alias}.${field.fieldName}}</td> 
							</#if> 
							</#if>
							</#list>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<%@include file="/web_common/web_page_bar_boot.jsp"%>
	</div>
	<!-- 增删改成模块 end -->
</body>
</html>


{
"ruleScoreMax": "从得分来看，你分数最高的是：${scoreMaxColorName}色。",
"ruleCharacterColorDefn": "${scoreMaxColorName}色性格是：${characterColorDefn}",
<#if characterColorLows !="">"ruleCharacterColorLows": "在你的性格中，比较缺少的是：${characterColorLows}",</#if>
<#if characterAdvantage !="">"ruleCharacterAdvantage": "你最突出的优势是：${characterAdvantage}",</#if>
<#if characterAdvantageOther !="">"ruleCharacterAdvantageOther": "同时，你${characterAdvantageOther}",</#if>
<#if characterInsufficient !="">"ruleCharacterInsufficient": "你最明显的不足是：${characterInsufficient}",</#if>
<#if characterInsufficientOther !="">"ruleCharacterInsufficientOther": "同时，你：${characterInsufficientOther}",</#if>
<#if characterCare !="">"ruleCharacterCare": "对你来说，你最在意的是：${characterCare}",</#if>
<#if characterCareOther !="">"ruleCharacterCareOther": "其次，你也：${characterCareOther},"</#if>
"ruleScore": "你的四种性格色彩分数是:红${redScore} 黄${yellowScore} 蓝${blueScore} 绿${greenScore}"

}