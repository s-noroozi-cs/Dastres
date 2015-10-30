<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>

<%@page import="com.dastres.master.action.helper.ActionMessageUtil"%>
<%@page import="com.dastres.config.Constant"%><html>
<head>
<title>Import Data</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/pages/css/blue.css" />
	
	<script type="text/javascript">
		function submitForm(method){
			document.getElementById("method").value=method;
			document.getElementById("form").submit();
		}
	</script>
	
</head>
<body>
<form action="ImportData.do" method="post" id="form"
	enctype="multipart/form-data">
<table width="100%" class="clsPageBody">

	<tr>
		<td class="clsPageHeader">Import Data</td>
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
				<td>ZIP File(CSV Files):</td>
				<td><input type="file" name="dataFile"></td>
			</tr>
			<tr>
				<td>CSV Separator:</td>
				<td>
					<input type="radio" name="separator" value="<%=Constant.CSV_SEP_COMMA%>" checked="checked" /> Comma(",") 
					<input type="radio" name="separator" value="<%=Constant.CSV_SEP_SEMICOLON%>" />Semicolon(";") 
					<input type="radio" name="separator" value="<%=Constant.CSV_SEP_TAB%>" />Tab ("TAB")
				</td>
			</tr>
			<tr>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td colspan="2" align="left">
					<input type="button" value="Send Data" onclick="submitForm('uploadZipFile');" />
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td class="clsPageFooter">&nbsp;</td>
	</tr>
</table>
<input type="hidden" name="method" id="method" value="">
</form>
</body>
</html>