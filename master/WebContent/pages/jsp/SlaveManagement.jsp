<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.dastres.master.action.helper.ActionMessageUtil"%>
<%@ page import="com.dastres.master.config.DefaultValues"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html>

<head>

<title>System Settings</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/pages/css/blue.css" />

<script type="text/javascript">

		function submitForm(method){
			document.getElementById("method").value=method;
			document.getElementById("form").submit();	
		}

		function onSlvReload(slvId){
			document.getElementById("slaveId").value=slvId;
			submitForm('reload');
		}
		
		function onSlvEdit(slvId){
			var name = document.getElementById(slvId + "_name").value;
			var address = document.getElementById(slvId + "_address").value;
			var disable = document.getElementById(slvId + "_disabled").value;

			document.getElementById("slaveId").value=slvId;
			document.getElementById("slaveName").value=name;
			document.getElementById("slaveAddress").value=address;
			if(disable == 'true')
				document.getElementById("slaveDisabled").checked="checked";
			else
				document.getElementById("slaveEnabled").checked="checked";
				
			
		}

		function onSlvDelete(slvId){
			document.getElementById("slaveId").value=slvId;
			submitForm('remove');
		}

		
	</script>

</head>

<body>
<form action="SlaveManagement.do" method="post" id="form">
<table width="100%" class="clsPageBody">

	<tr>
		<td class="clsPageHeader">Slave Management</td>
	</tr>
	<%
		if (ActionMessageUtil.hasError(request)) {
	%>
	<tr>
		<td class="clsErrorMessage"><%=ActionMessageUtil.getErrorMessage(request)%>
		</td>
	</tr>
	<%
		}
	%>

	<%
		if (ActionMessageUtil.hasSucess(request)) {
	%>
	<tr>
		<td class="clsSuccessMessage"><%=ActionMessageUtil.getSucessMessage(request)%>
		</td>
	</tr>
	<%
		}
	%>

	<tr>
		<td>
		<table>
			<tr>
				<td>Name:</td>
				<td><input name="slaveName" id="slaveName" type="text"
					class="clsText" size="30" /></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><input name="slaveAddress" id="slaveAddress" type="text"
					class="clsText" size="80" /></td>
			</tr>
			<tr>
				<td>Status:</td>
				<td>Enabled <input type="radio" name="slaveDisabled"
					id="slaveEnabled" value="false" checked="checked" /> Disabled <input
					type="radio" name="slaveDisabled" id="slaveDisabled" value="true" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="button" class="clsButton"
					value="Save" onclick="submitForm('save');" /></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center"><display:table
			name="requestScope.SlvMachiveList" offset="1" id="tbl"
			pagesize="<%= DefaultValues.PAGE_SIZE %>"
			decorator="com.dastres.master.web.table.SlaveWrapper" sort="external"
			requestURI="/SlaveManagement.do?method=disTagAct"
			class="clsTableBody" border="0" width="100%"
			messageBundle="com.dastres.master.web.table.TableTag">
			<display:column property="nameColumn" sortable="false"
				title="Machine Name" width="150px" headerClass="clsTableHeader"
				class="clsTableCellNormal" />
			<display:column property="addressColumn" sortable="false"
				title="Machine Address" width="300px" headerClass="clsTableHeader"
				class="clsTableCellNormal" />
			<display:column property="statusColumn" sortable="false"
				title="Machine Status" width="10px" headerClass="clsTableHeader"
				class="clsTableCellNormal" />
			<display:column property="actionColumn" sortable="false"
				title="Action" width="50px" headerClass="clsTableHeader"
				class="clsTableCellCenter" />
		</display:table></td>
	</tr>
	<tr>
		<td class="clsPageFooter">&nbsp;</td>
	</tr>

</table>
<input type="hidden" name="method" value="" id="method" /> <input
	type="hidden" name="slaveId" value="" id="slaveId" /></form>
</body>
</html>