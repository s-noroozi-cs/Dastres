<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.dastres.master.action.helper.ActionMessageUtil"%>
<%@page import="com.dastres.config.Constant"%>
<%@page import="com.dastres.master.action.SystemSettingAction"%><html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>System Settings</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/pages/css/blue.css" />
	
	<script type="text/javascript">
		function save(){
			document.getElementById("method").value="save";
			document.getElementById("form").submit();	
		}

		
	</script>

</head>

<body>
<form action="SystemSettings.do" method="post" id="form">
<table width="100%" class="clsPageBody">

	<tr>
		<td class="clsPageHeader">System Settings</td>
	</tr>
	<%
		if (ActionMessageUtil.hasError(request)) {
	%>
	<tr>
		<td class="clsErrorMessage">
			<%=ActionMessageUtil.getErrorMessage(request)%>
		</td>
	</tr>
	<%
		}
	%>
	
	<%
			if (ActionMessageUtil.hasSucess(request)) {
		%>
	<tr>
		<td class="clsSuccessMessage">
			<%=ActionMessageUtil.getSucessMessage(request)%>
		</td>
	</tr>
	<%
		}
	%>
	
	<tr>
		<td>
		<table>
			<tr>
				<td>Slave ping time (seconds):</td>
				<td><input name="slavePingTime" type="text" class="clsText" value="<%= request.getAttribute("slavePingTime") %>" /></td>
			</tr>
			<tr>
				<td>Slave Response Rule:</td>
				<td>
					<% 
						boolean onlyMaster = false;
						boolean masterSlave = false;
						try{
							String responseRule = (String)request.getAttribute("responseRule");
							if(responseRule != null){
								onlyMaster = responseRule.equals(SystemSettingAction.RESPONSE_RULE_ONLY_MASTER); 
								masterSlave = responseRule.equals(SystemSettingAction.RESPONSE_RULE_MASTER_SLAVE);
							}
						}catch(Throwable ex){}
					%>
					<% if(onlyMaster){ %>
						<input name="responseRule" type="radio" checked="checked"   value="<%= SystemSettingAction.RESPONSE_RULE_ONLY_MASTER %>" />Only Master
					<%}else{ %>
						<input name="responseRule" type="radio"   value="<%= SystemSettingAction.RESPONSE_RULE_ONLY_MASTER %>" />Only Master
					<%}if(masterSlave){ %>
						<input name="responseRule" type="radio" checked="checked"  value="<%= SystemSettingAction.RESPONSE_RULE_MASTER_SLAVE %>" />Master-Slave
					<%}else{ %>
						<input name="responseRule" type="radio"  value="<%= SystemSettingAction.RESPONSE_RULE_MASTER_SLAVE %>" />Master-Slave
					<%} %>
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="button" class="clsButton"
					value="Save" onclick="save();"/></td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td class="clsPageFooter">&nbsp;</td>
	</tr>

</table>
<input type="hidden"  name="method" value="" id="method"/>
</form>
</body>
</html>