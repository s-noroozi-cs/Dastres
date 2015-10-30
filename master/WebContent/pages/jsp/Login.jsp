<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/pages/css/blue.css" />
<script>
	function pgBodyOnLoad(){
		var usrPassInvalid = document.getElementById("usrPassInvalid").value + "";
		if(usrPassInvalid == "true"){
			document.getElementById("loginMsgErr").style.display="block";
		}
	}
</script>
</head>
<body onload="pgBodyOnLoad();" >

<input type="hidden" id="usrPassInvalid" name="usrPassInvalid" value=<%= request.getAttribute("usrPassInvalid") %> />

<table align="center" style="padding-top: 200px">
	<tr>
	<td>
	<div style="width: 300px" align="center">
		<table class="clsTableBody">
			<tr>
				<td>
				<div class="clsPageHeader">Login Page</div>
				</td>
			</tr>
		</table>
		
		
		
		<table class="clsTableBody">
			<tr>
				<td>
					<div id="loginMsgErr" style="display: none;color: red;" align="center">Your user name or password is not valid.</div>		
				</td>
			</tr>
		</table>
		
		<form action="<%=request.getContextPath() %>/Login.do" method="POST" >
		<table border="0" align="center" class="clsTableBody">
			<tr>
				<td align="right">User name: </td>
				<td align="right"><input dir="ltr" type="text" name="user" /></td>
			</tr>
			<tr>
				<td align="right">Password: </td>
				<td align="right"><input dir="ltr" type="password" name="pass" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" class="clsButton"
					value="Login" /></td>
			</tr>
		</table>
		</form>
		<table class="clsTableBody">
			<tr>
				<td>
				<div class="clsPageFooter">&nbsp;</div>
				</td>
			</tr>
		</table>
	</div>
	</td>
	</tr>
	</table>
</body>
</html>