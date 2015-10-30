<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.dastres.master.action.helper.ActionMessageUtil"%>
<%@ page import="com.dastres.master.config.DefaultValues"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html>

<head>

<title>Search Log</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/pages/css/blue.css" />
</head>
<body>
<table width="100%" class="clsPageBody">
	<tr>
		<td class="clsPageHeader">Search Log</td>
	</tr>
	<tr>
		<td align="center">
			<display:table name="requestScope.logSearchHandler" offset="1" id="tbl"
							pagesize="<%= DefaultValues.PAGE_SIZE %>"
							decorator="com.dastres.master.web.table.SearchLogWrapper" sort="external"
							requestURI="/SearchLog.do?method=disTagAct"
							class="clsTableBody" border="0" width="100%"
							messageBundle="com.dastres.master.web.table.TableTag">
				<display:column property="id" sortable="true"
								title="ID" width="150px" headerClass="clsTableHeader"
								class="clsTableCellNormal" />
				<display:column property="date" sortable="true"
								title="Date" width="150px" headerClass="clsTableHeader"
								class="clsTableCellNormal" />
				<display:column property="keyword" sortable="true"
								title="Keyword" width="150px" headerClass="clsTableHeader"
								class="clsTableCellNormal" />								
				<display:column property="region" sortable="false"
								title="Region" width="150px" headerClass="clsTableHeader"
								class="clsTableCellNormal" />
				<display:column property="userAgent" sortable="false"
								title="User-Agent" width="150px" headerClass="clsTableHeader"
								class="clsTableCellNormal" />
				<display:column property="waitTime" sortable="true"
								title="Search Time" width="150px" headerClass="clsTableHeader"
								class="clsTableCellNormal" />
			</display:table>
		</td>
	</tr>
	<tr>
		<td class="clsPageFooter">&nbsp;</td>
	</tr>
</table>
</body>
</html>