<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.dastres.master.action.helper.ActionMessageUtil"%>
<%@ page import="com.dastres.master.config.DefaultValues"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<%@page import="com.dastres.master.action.DataTransferAction"%>
<%@page import="com.dastres.master.domain.SlaveTO"%>
<%@page import="java.util.List"%><html>

<head>
<title>Data Transfer</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/pages/css/blue.css" />
	<script language="javascript">
		function submitForm(method){
			document.getElementById("method").value=method;
			document.getElementById("form").submit();
		}
	</script>
</head>
<body>
<form action="DataTransfer.do" method="post" id="form">
<table width="100%" class="clsPageBody">

	<tr>
		<td class="clsPageHeader">Data Transfer</td>
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
		<table width="100%" class="clsPageBody">
			<tr>
				<td>From: <select name="fromAddress" class="clsSelect">
					<%
					@SuppressWarnings("unchecked")
					List<SlaveTO> fromSlvs = (List<SlaveTO>) request.getAttribute(DataTransferAction.REQ_ATT_ACT_SLVS); 
						if(fromSlvs != null){
							StringBuilder sb = new StringBuilder();
							for(SlaveTO slv : fromSlvs){
								sb.append("<option value=" + slv.getAddress() + ">");
								sb.append(slv.getName() + "(size: " + slv.getRecordSize() + ")");
								sb.append("</option>");
							}
							out.write(sb.toString());
						}
					%>
				</select></td>
				<td>TO: <select name="toAddress" class="clsSelect">
					<%
					@SuppressWarnings("unchecked")
					List<SlaveTO> tpSlvs = (List<SlaveTO>) request.getAttribute(DataTransferAction.REQ_ATT_ACT_SLVS); 
						if(fromSlvs != null){
							StringBuilder sb = new StringBuilder();
							for(SlaveTO slv : fromSlvs){
								sb.append("<option value=" + slv.getAddress()+">");
								sb.append(slv.getName() + "(size: " + slv.getRecordSize() + ")");
								sb.append("</option>");
							}
							out.write(sb.toString());
						}
					%>
				</select></td>
				<td>Record Size: <input type="text" name="recordSize" class="clsText" /></td>
				<td><input type="button" value="Do it"  onclick="submitForm('transfer')"/></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td class="clsPageFooter">&nbsp;</td>
	</tr>
</table>
	<input type="hidden" name="method" id="method" value="" />
</form>
</body>
</html>