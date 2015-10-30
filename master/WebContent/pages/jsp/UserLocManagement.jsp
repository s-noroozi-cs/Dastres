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
	<script type="text/javascript">

		function getSelectedIds(){
			var ids = "";
			var i=0;
			var elm = document.getElementById("chk_" + i);
			while(elm != null){
				if(elm.checked == true){
					ids += document.getElementById("help_" + i).value + ",";
				}
				i++;
				elm = document.getElementById("chk_" + i);
			}
			return ids;
		}
		function reload(){
			window.location = window.location;
		}
		function submitForm(method){
			var confirm = window.confirm("Are you sure to submit form for '" + method +  "' ?");
			if (confirm == true){
				var ids = getSelectedIds();
				if(ids != ""){
					document.getElementById("method").value=method;
					document.getElementById("ids").value=ids;
					document.getElementById("form").submit();
				}else{
					alert("You should be select at least one item.");
				}
			}
		}
		
		function checkAllClick(checked){
			var i=0;
			var elm = document.getElementById("chk_" + i);
			while(elm != null){
				elm.checked = checked;
				i++;
				elm = document.getElementById("chk_" + i);
			}
		}
	</script>
</head>
<body>
<table width="100%" class="clsPageBody">
	<tr>
		<td class="clsPageHeader">User Location Management</td>
	</tr>
	<tr>
		<td align="left">
			<input type="button" onclick="submitForm('accept')" value="Accept">
			<input type="button" onclick="submitForm('remove')" value="Remove">
			<input type="button" onclick="reload()" value="Reload">
		</td>
	</tr>
	<tr>
		<td align="center">
			<display:table name="requestScope.userLocListHandler" offset="1" id="tbl"
							pagesize="<%= DefaultValues.PAGE_SIZE %>"
							decorator="com.dastres.master.web.table.UserLocationWrapper" sort="external"
							requestURI="/UserLoc.do?method=list"
							class="clsTableBody" border="0" width="100%"
							messageBundle="com.dastres.master.web.table.TableTag">
				<display:column property="checkBox" sortable="false" width="5px"
								title="<input type ='checkbox' onclick='checkAllClick(this.checked)'>" headerClass="clsTableHeader"
								class="clsTableCellNormal" />
				<display:column property="id" sortable="true"
								title="ID" width="100px" headerClass="clsTableHeader"
								class="clsTableCellNormal" />
				<display:column property="date" sortable="false"
								title="Date" width="150px" headerClass="clsTableHeader"
								class="clsTableCellNormal" />
				<display:column property="name" sortable="false"
								title="Location Name" width="100px" headerClass="clsTableHeader"
								class="clsTableCellNormal" />								
				<display:column property="longitude" sortable="false"
							title="Longitude" width="80px" headerClass="clsTableHeader"
								class="clsTableCellNormal" />
				<display:column property="latitude" sortable="false"
								title="Latitude" width="80px" headerClass="clsTableHeader"
								class="clsTableCellNormal" />
				<display:column property="tags" sortable="false"
								title="Tags" width="80px" headerClass="clsTableHeader"
								class="clsTableCellNormal" />
				<display:column property="moreInfo" sortable="false"
								title="More Info" width="200px" headerClass="clsTableHeader"
								class="clsTableCellNormal" />
				<display:column property="accept" sortable="false"
								title="Accept" width="10px" headerClass="clsTableHeader"
								class="clsTableCellNormal" />
			</display:table>
		</td>
	</tr>
	<tr>
		<td align="left">
			<input type="button" onclick="submitForm('accept')" value="Accept">
			<input type="button" onclick="submitForm('remove')" value="Remove">
			<input type="button" onclick="reload()" value="Reload">
		</td>
	</tr>
	<tr>
		<td class="clsPageFooter">&nbsp;</td>
	</tr>
</table>
	<form method="POST" action="<%=request.getContextPath() %>/UserLoc.do" id="form">
		<input type="hidden" name="method" id="method" value="">
		<input type="hidden" name="ids" id="ids" value="">
		
	</form>
</body>
</html>